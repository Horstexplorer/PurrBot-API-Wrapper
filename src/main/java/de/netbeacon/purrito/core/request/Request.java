/*
 *     Copyright 2021 Horstexplorer @ https://www.netbeacon.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.netbeacon.purrito.core.request;

import de.netbeacon.purrito.core.PurritoRaw;
import de.netbeacon.purrito.core.responses.IResponse;
import de.netbeacon.purrito.core.responses.ResponseData;
import de.netbeacon.purrito.core.responses.ResponseError;
import de.netbeacon.utils.json.verification.JSONMatcher;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * This class represents a request to the api
 */
public class Request {

    public enum Method {
        GET(),
        POST(),
        PUT(),
        DELETE();
    }

    private final Logger logger = LoggerFactory.getLogger(PurritoRaw.class);

    private final OkHttpClient okHttpClient;
    private final Executor executor;
    private final okhttp3.Request request;

    private final Endpoint endpoint;
    private final Endpoint.ReturnType returnType;
    private final JSONObject transmissionData;

    /**
     * Creates a new instance of this class
     * @param okHttpClient okhttp client
     * @param executor executor
     * @param request okhttp request
     * @param endpoint selected endpoint
     * @param returnType expected return data
     * @param transmissionData added transmission data
     */
    protected Request(OkHttpClient okHttpClient, Executor executor, okhttp3.Request request, Endpoint endpoint, Endpoint.ReturnType returnType, JSONObject transmissionData){
        this.okHttpClient = okHttpClient;
        this.executor = executor;
        this.request = request;

        this.endpoint = endpoint;
        this.returnType = returnType;
        this.transmissionData = transmissionData;
    }

    /**
     * Returns the selected endpoint
     * @return
     */
    public Endpoint getEndpoint() {
        return endpoint;
    }

    /**
     * Returns the expected return type
     * @return Endpoint.ReturnType
     */
    public Endpoint.ReturnType getReturnType() {
        return returnType;
    }

    /**
     * Returns the included transmission data
     * @return transmissiondata as json
     */
    public JSONObject getTransmissionData() {
        return transmissionData;
    }

    /**
     * This will process the response and return the response
     * (Sync)
     * @return ResponseData on success, ResponseError on failure
     */
    public IResponse execute(){
        try(Response response = okHttpClient.newCall(request).execute()){
            logger.debug("Received status code "+response.code());
            if(response.code() < 200 || response.code() > 299){
                return new ResponseError("Received bad response from api: Status code "+response.code());
            }
            if(response.header("Content-Type") == null){
                logger.debug("Content-Type header is missing");
                return new ResponseError("Received bad response from api: no content type");
            }
            if(response.header("Content-Type").toLowerCase(Locale.ROOT).startsWith(returnType.getContentType())){
                return new ResponseData(returnType, response.body().bytes());
            }
            logger.debug("Failed to match response type of header ("+response.header("Content-Type")+") to the expected return type ("+returnType.getContentType()+")");
            return new ResponseError("Received bad response from api: Requested return type "+returnType+" but received "+response.header("Content-Type"));
        }catch (Exception e){
            return new ResponseError("Received bad response from api:", e);
        }
    }

    /**
     * Will process the response and call the success consumer as defined. Errors will be ignored
     * (Async)
     * @param onSuccess what should happen if the communication succeeded
     */
    public void execute(Consumer<IResponse.Success> onSuccess){
        execute(onSuccess, null);
    }

    /**
     * Will process the response and call the success / error consumer as defined when the response is ready
     * (Async)
     * @param onSuccess what should happen if the communication succeeded
     * @param onError what should happen if the communication failed
     */
    public void execute(Consumer<IResponse.Success> onSuccess, Consumer<Exception> onError){
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                logger.debug("IO Exception ", e);
                if(onError != null){
                    executor.execute(() -> onError.accept(new ResponseError(e)));
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (response) {
                    logger.debug("Received status code "+response.code());
                    if (response.code() < 200 || response.code() > 299) {
                        if (onError != null) {
                            executor.execute(() -> onError.accept(new ResponseError("Received bad response from api: Status code " + response.code())));
                            return;
                        }
                    }
                    if (response.header("Content-Type") == null) {
                        if (onError != null) {
                            logger.debug("Content-Type header is missing");
                            executor.execute(() -> onError.accept(new ResponseError("Received bad response from api: no content type")));
                            return;
                        }
                    }
                    if(response.header("Content-Type").toLowerCase(Locale.ROOT).startsWith(returnType.getContentType())){
                        ResponseData responseData = new ResponseData(returnType, response.body().bytes());
                        executor.execute(() -> onSuccess.accept(responseData));
                    }else if (onError != null) {
                        logger.debug("Failed to match response type of header ("+response.header("Content-Type")+") to the expected return type ("+returnType.getContentType()+")");
                        executor.execute(() -> onError.accept(new ResponseError("Received bad response from api: Requested return type " + returnType + " but received " + response.header("Content-Type"))));
                    }
                }
            }
        });
    }

    public static class Builder {

        private final OkHttpClient okHttpClient;
        private final Executor executor;
        private Endpoint endpoint;
        private Endpoint.ReturnType returnType;
        private JSONObject transmissionData;

        /**
         * Creates a new instance of the builder
         * Object can be provided manually but it is prefered to use the Purrito instance to create instances of this class
         * @param okHttpClient the okhttp client
         * @param executor the executor
         */
        public Builder(OkHttpClient okHttpClient, Executor executor){
            this.okHttpClient = okHttpClient;
            this.executor = executor;
        }

        /**
         * Should be used to set which endpoint should be used for the request
         * @param endpoint the desired endpoint
         * @return the current instance of the builder
         */
        public Builder useEndpoint(Endpoint endpoint){
            this.endpoint = endpoint;
            return this;
        }

        /**
         * Can be used to define the expected return type of the request
         * @throws IllegalArgumentException when the input provided does not match the expectations
         * @param returnType expected return type
         * @return the current instance of the builder
         */
        public Builder getReturnType(Endpoint.ReturnType returnType){
            if(endpoint == null){
                throw new IllegalArgumentException("No endpoint defined to set the return type");
            }else if(!endpoint.getReturnTypes().contains(returnType)){
                throw new IllegalArgumentException("Return type unavailable for this endpoint");
            }
            this.returnType = returnType;
            return this;
        }

        /**
         * Can be used to add additional payload data to the request
         * @throws IllegalArgumentException when the input provided does not match the expectations
         * @param transmissionData json containing the transmission data
         * @return the current instance of the builder
         */
        public Builder addTransmissionData(JSONObject transmissionData){
            if(endpoint == null){
                throw new IllegalArgumentException("No endpoint defined to add data to the transmission");
            }else if(endpoint.getRequiredData() == null && transmissionData != null){
                throw new IllegalArgumentException("Data not required");
            }else if(transmissionData == null || !JSONMatcher.structureMatch(transmissionData, endpoint.getRequiredData())){
                throw new IllegalArgumentException("The data does not match the type requirements");
            }
            this.transmissionData = transmissionData;
            return this;
        }

        /**
         * Prepares a new request to the backend with the specified data
         * @throws IllegalArgumentException when the input provided does not match the expectations
         * @return Request
         */
        public Request prepare(){
            if(endpoint == null){
                throw new IllegalArgumentException("No endpoint specified");
            }else if(returnType == null){
                returnType = endpoint.getReturnType(); // use default return type
            }else if(endpoint.getRequiredData() != null && transmissionData == null){
                throw new IllegalArgumentException("Request requires transmission data");
            }
            // build request (and hand over data for easy access because why not)
            return new Request(okHttpClient, executor, buildOkHTTPRequest(), endpoint, returnType, transmissionData);
        }

        /**
         * Internally used to build an okhttp request from the included data
         * @return okhttp request
         */
        private okhttp3.Request buildOkHTTPRequest(){
            okhttp3.Request.Builder reqBuilder = new okhttp3.Request.Builder()
            // set url
                    .url(endpoint.getRequestURL(returnType));
            // set method & payload
            switch(endpoint.getRequestMethod()){
                case GET:
                    reqBuilder.get();
                    break;
                case POST:
                    if(transmissionData != null){
                        reqBuilder.post(RequestBody.create(transmissionData.toString().getBytes(StandardCharsets.UTF_8), MediaType.get("application/json")));
                    }else{
                        reqBuilder.post(RequestBody.create("{}".getBytes(StandardCharsets.UTF_8), MediaType.get("application/json")));
                    }
                    break;
                case PUT:
                    if(transmissionData != null){
                        reqBuilder.put(RequestBody.create(transmissionData.toString().getBytes(StandardCharsets.UTF_8), MediaType.get("application/json")));
                    }else{
                        reqBuilder.put(RequestBody.create("{}".getBytes(StandardCharsets.UTF_8), MediaType.get("application/json")));
                    }
                    break;
                case DELETE:
                    if(transmissionData != null){
                        reqBuilder.delete(RequestBody.create(transmissionData.toString().getBytes(StandardCharsets.UTF_8), MediaType.get("application/json")));
                    }else{
                        reqBuilder.delete();
                    }
                    break;
            }
            return reqBuilder.build();
        }
    }
}
