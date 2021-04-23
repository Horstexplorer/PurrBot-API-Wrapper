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

package de.netbeacon.purrito.qol.execution.tasks;

import de.netbeacon.purrito.core.PurritoRaw;
import de.netbeacon.purrito.core.responses.IResponse;
import de.netbeacon.purrito.core.responses.ResponseData;
import de.netbeacon.purrito.core.responses.ResponseError;
import de.netbeacon.purrito.qol.execution.ExecutionTask;
import de.netbeacon.purrito.qol.typewrap.ContentType;
import de.netbeacon.purrito.qol.typewrap.ImageType;
import org.json.JSONObject;

import java.util.function.Consumer;

public class GetAnimeImageUrl extends ExecutionTask<String> {

    private final ImageType imageType;
    private final ContentType contentType;

    public GetAnimeImageUrl(ImageType imageType, ContentType contentType){
        this.imageType = imageType;
        this.contentType = contentType.equals(ContentType.AVAILABLE) ? ContentType.findAvailable(imageType) : contentType;
    }

    @Override
    protected String sync(PurritoRaw purritoRaw) {
        try{
            IResponse iResponse = purritoRaw.newRequest()
                    .useEndpoint(imageType.getEndpoint())
                    .getReturnType(contentType.getReturnTypes())
                    .prepare()
                    .execute();
            if(iResponse instanceof ResponseError){
                throw (ResponseError)iResponse;
            }
            JSONObject payload = ((ResponseData) iResponse).getAsJSONPayload();
            if(payload.getBoolean("error")){
                throw new ResponseError(payload.getString("message"));
            }
            return payload.getString("link");
        }catch (Exception e){
            logger.error("Something went wrong getting the image url:", e);
        }
        return null;
    }

    @Override
    protected void async(PurritoRaw purritoRaw, Consumer<String> onSuccess, Consumer<Exception> onError) {
        try{
            purritoRaw.newRequest()
                    .useEndpoint(imageType.getEndpoint())
                    .getReturnType(contentType.getReturnTypes())
                    .prepare().execute((s) ->{
                try{
                    JSONObject payload = s.getAsJSONPayload();
                    if(payload.getBoolean("error")){
                        if(onError != null){
                            logger.error("Something went wrong getting the image url: "+payload.get("message"));
                            onError.accept(new ResponseError(payload.getString("message")));
                        }
                    }
                    onSuccess.accept(payload.getString("link"));
                }catch (Exception e){
                    if(onError != null){
                        logger.debug("Something went wrong getting the image url:", e);
                        onError.accept(new ResponseError(e));
                    }else{
                        logger.error("Something went wrong getting the image url:", e);
                    }
                }
            }, onError);
        }catch (Exception e){
            if(onError != null){
                logger.debug("Something went wrong getting the image url:", e);
                onError.accept(new ResponseError(e));
            }else{
                logger.error("Something went wrong getting the image url:", e);
            }
        }
    }
}
