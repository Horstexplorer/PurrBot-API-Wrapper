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

package de.netbeacon.purrito.qol;

import de.netbeacon.purrito.core.Purrito;
import de.netbeacon.purrito.core.responses.IResponse;
import de.netbeacon.purrito.core.responses.ResponseData;
import de.netbeacon.purrito.core.responses.ResponseError;
import de.netbeacon.purrito.qol.typewrap.ContentType;
import de.netbeacon.purrito.qol.typewrap.ImageType;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;

public class Purritowo {

    private final Purrito purrito;
    private final Purritowo_SYNC sync;
    private final Purritowo_ASYNC async;

    /**
     * Creates a new instance of this wrapper
     */
    public Purritowo(){
        this.purrito = new Purrito();
        this.sync = new Purritowo_SYNC(purrito);
        this.async = new Purritowo_ASYNC(purrito);
    }

    /**
     * Creates a new instance of this wrapper with the specified purrito instance
     * @param purrito instance
     */
    public Purritowo(Purrito purrito){
        this.purrito = purrito;
        this.sync = new Purritowo_SYNC(purrito);
        this.async = new Purritowo_ASYNC(purrito);
    }

    /**
     * Returns a linking object containing a collection of all sync-called methods
     * @return Purritowo_SYNC
     */
    public Purritowo_SYNC doSync(){
        return sync;
    }

    /**
     * Returns a linking object containing a collection of all async-called methods
     * @return Purritowo_ASYNC
     */
    public Purritowo_ASYNC doAsync(){
        return async;
    }

    public static class Purritowo_SYNC {

        private final Purrito purrito;
        private final Logger logger = LoggerFactory.getLogger(Purrito.class);

        /**
         * Creates a new instance of this class
         * @param purrito
         */
        protected Purritowo_SYNC(Purrito purrito){
            this.purrito = purrito;
        }

        /**
         * Easy to use method to get the image url
         * @param imageType the desired image type
         * @param contentType the expected content / media type of the image
         * @return the image url or null if anything goes wrong
         */
        public String getImageURL(ImageType.IT imageType, ContentType contentType){
            try{
                IResponse iResponse = purrito.newRequest()
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
                logger.error("Something went wrong getting the image url", e);
            }
            return null;
        }

        /**
         * Easy to use method to get the input stream of an image
         * @param imageType the desired image type
         * @param contentType the expected content / media type of the image
         * @return the input stream of the image or null if anything goes wrong
         */
        public InputStream getImageInputStream(ImageType.IT imageType, ContentType contentType){
            try{
                String url = getImageURL(imageType, contentType);
                if(url == null){
                    return null;
                }
                return new URL(url).openStream();
            }catch (Exception e){
                logger.error("Something went wrong getting the image input stream", e);
            }
            return null;
        }

    }

    public static class Purritowo_ASYNC {

        private final Purrito purrito;
        private final Logger logger = LoggerFactory.getLogger(Purrito.class);

        /**
         * Creates a new instance of this class
         * @param purrito
         */
        public Purritowo_ASYNC(Purrito purrito){
            this.purrito = purrito;
        }

        /**
         * Easy to use method to get the image url while ignoring errors
         * @param imageType the desired image type
         * @param contentType the expected content / media type of the image
         * @param urlOnSuccess to be called with the result
         */
        public void getImageURL(ImageType.IT imageType, ContentType contentType, Consumer<String> urlOnSuccess){
            getImageURL(imageType, contentType, urlOnSuccess);
        }

        /**
         * Easy to use method to get the image url
         * @param imageType the desired image type
         * @param contentType the expected content / media type of the image
         * @param urlOnSuccess to be called with the result
         * @param onError to be called if anything goes wrong
         */
        public void getImageURL(ImageType.IT imageType, ContentType contentType, Consumer<String> urlOnSuccess, Consumer<IResponse.Error> onError){
            try{
                purrito.newRequest()
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
                                urlOnSuccess.accept(payload.getString("link"));
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

        /**
         * Easy to use method to get the input stream of an image while ignoring errors
         * @param imageType the desired image type
         * @param contentType the expected content / media type of the image
         * @param inputStreamOnSuccess to be called with the result
         */
        public void getImageInputStream(ImageType.IT imageType, ContentType contentType, Consumer<InputStream> inputStreamOnSuccess){
            getImageInputStream(imageType, contentType, inputStreamOnSuccess, null);
        }

        /**
         * Easy to use method to get the input stream of an image
         * @param imageType the desired image type
         * @param contentType the expected content / media type of the image
         * @param inputStreamOnSuccess to be called with the result
         * @param onError to be called if anything goes wrong
         */
        public void getImageInputStream(ImageType.IT imageType, ContentType contentType, Consumer<InputStream> inputStreamOnSuccess, Consumer<IResponse.Error> onError){
            getImageURL(imageType, contentType, (url) -> {
                try{
                    inputStreamOnSuccess.accept(new URL(url).openStream());
                }catch (Exception e){
                    if(onError != null){
                        logger.debug("Something went wrong getting the image input stream:", e);
                        onError.accept(new ResponseError(e));
                    }else{
                        logger.error("Something went wrong getting the image input stream:", e);
                    }
                }
            }, onError);
        }

    }

}
