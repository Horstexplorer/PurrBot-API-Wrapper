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
import de.netbeacon.purrito.core.request.Endpoint;
import de.netbeacon.purrito.core.responses.IResponse;
import de.netbeacon.purrito.core.responses.ResponseData;
import de.netbeacon.purrito.core.responses.ResponseError;
import de.netbeacon.purrito.qol.typewrap.ContentType;
import de.netbeacon.purrito.qol.typewrap.Image;
import de.netbeacon.purrito.qol.typewrap.ImageType;
import org.jetbrains.annotations.Nullable;
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
                logger.error("Something went wrong getting the image url:", e);
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
                    throw new Exception("Failed to get the image url");
                }
                return new URL(url).openStream();
            }catch (Exception e){
                logger.error("Something went wrong getting the image input stream:", e);
            }
            return null;
        }

        /**
         * Easy to use method to handle the the data of the image directly
         * @param imageType the desired image type
         * @param contentType the expected content / media type of the image
         * @return Image object or null if anything goes wrong
         */
        public Image getImage(ImageType.IT imageType, ContentType contentType) {
            try{
                InputStream inputStream = getImageInputStream(imageType, contentType);
                if (inputStream == null) {
                    throw new Exception("Failed to get the image input stream");
                }
                try(inputStream){
                    return new Image(inputStream.readAllBytes());
                }
            }catch (Exception e){
                logger.error("Something went wrong getting the image data:", e);
            }
            return null;
        }

        /**
         * Easy to use method to get a quote as image with some default parameters for the color and dateformat
         * @param avatarUrl of the user avatar
         * @param username the username
         * @param message the message
         * @return Image or null if anything goes wrong
         */
        public Image getQuoteAsImage(String avatarUrl, String username, String message){
            return getQuoteAsImage(avatarUrl, username, message, "hex:ffffff", "dd. MMM yyyy");
        }

        /**
         * Easy to use method to get a quote as image
         * @param avatarUrl of the user avatar
         * @param username the username
         * @param message the message
         * @param nameColor the color of the name (default: hex:ffffff)
         * @param dateformat how to format the data (default: dd. MMM yyyy)
         * @return Image or null if anything goes wrong
         */
        public Image getQuoteAsImage(String avatarUrl, String username, String message, String nameColor, String dateformat){
            try{
                JSONObject payload = new JSONObject()
                        .put("avatar", avatarUrl).put("username", username).put("message", message).put("nameColor", nameColor).put("dateFormat", dateformat);
                IResponse iResponse = purrito.newRequest()
                        .useEndpoint(Endpoint.MSC_Quote)
                        .getReturnType(Endpoint.ReturnType.RAW_IMAGE)
                        .addTransmissionData(payload)
                        .prepare()
                        .execute();
                if(iResponse instanceof ResponseError){
                    throw (ResponseError)iResponse;
                }
                return new Image(((ResponseData) iResponse).getBytePayload());
            }catch (Exception e){
                logger.error("Something went wrong getting the image data:", e);
            }
            return null;
        }

        /**
         * Easy to use method to get a status as image
         * @param avatarUrl of the user avatar
         * @param status of the status type (options: online, idle, do_not_disturb, dnd, streaming, offline)
         * @return Image or null if anything goes wrong
         */
        public Image getStatusAsImage(String avatarUrl, String status){
            return getStatusAsImage(avatarUrl, status, false);
        }

        /**
         * Easy to use method to get a status as image
         * @param avatarUrl of the user avatar
         * @param status of the status type (options: online, idle, do_not_disturb, dnd, streaming, offline)
         * @param isMobile if the user is using his mobile device
         * @return Image or null if anything goes wrong
         */
        public Image getStatusAsImage(String avatarUrl, String status, boolean isMobile){
            try{
                JSONObject payload = new JSONObject()
                        .put("avatar", avatarUrl).put("status", status).put("mobile", isMobile);
                IResponse iResponse = purrito.newRequest()
                        .useEndpoint(Endpoint.MSC_Status)
                        .getReturnType(Endpoint.ReturnType.RAW_IMAGE)
                        .addTransmissionData(payload)
                        .prepare()
                        .execute();
                if(iResponse instanceof ResponseError){
                    throw (ResponseError)iResponse;
                }
                return new Image(((ResponseData) iResponse).getBytePayload());
            }catch (Exception e){
                logger.error("Something went wrong getting the image data:", e);
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
        public void getImageURL(ImageType.IT imageType, ContentType contentType, Consumer<String> urlOnSuccess, @Nullable Consumer<IResponse.Error> onError){
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
        public void getImageInputStream(ImageType.IT imageType, ContentType contentType, Consumer<InputStream> inputStreamOnSuccess, @Nullable Consumer<IResponse.Error> onError){
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

        /**
         * Easy to use method to handle the the data of the image directly while ignoring errors
         * @param imageType the desired image type
         * @param contentType the expected content / media type of the image
         * @param imageOnSuccess to be called with the result
         */
        public void getImage(ImageType.IT imageType, ContentType contentType, Consumer<Image> imageOnSuccess){
            getImage(imageType, contentType, imageOnSuccess, null);
        }


        /**
         * Easy to use method to handle the the data of the image directly
         * @param imageType the desired image type
         * @param contentType the expected content / media type of the image
         * @param imageOnSuccess to be called with the result
         * @param onError to be called if anything goes wrong
         */
        public void getImage(ImageType.IT imageType, ContentType contentType, Consumer<Image> imageOnSuccess, @Nullable Consumer<IResponse.Error> onError){
            getImageInputStream(imageType, contentType, inputStream -> {
                try(inputStream){
                    imageOnSuccess.accept(new Image(inputStream.readAllBytes()));
                }catch (Exception e){
                    if(onError != null){
                        logger.debug("Something went wrong getting the image data:", e);
                        onError.accept(new ResponseError(e));
                    }else{
                        logger.error("Something went wrong getting the image data:", e);
                    }
                }
            }, onError);
        }

        /**
         * Easy to use method to get a quote as image with some default parameters for the color and dateformat
         * @param avatarUrl of the user avatar
         * @param username the username
         * @param message the message
         * @param imageOnSuccess to be called with the result
         * @param onError to be called if anything goes wrong
         */
        public void  getQuoteAsImage(String avatarUrl, String username, String message, Consumer<Image> imageOnSuccess, @Nullable Consumer<IResponse.Error> onError){
            getQuoteAsImage(avatarUrl, username, message, "hex:ffffff", "dd. MMM yyyy", imageOnSuccess, onError);
        }

        /**
         * Easy to use method to get a quote as image
         * @param avatarUrl of the user avatar
         * @param username the username
         * @param message the message
         * @param nameColor the color of the name (default: hex:ffffff)
         * @param dateformat how to format the data (default: dd. MMM yyyy)
         * @param imageOnSuccess to be called with the result
         * @param onError to be called if anything goes wrong
         */
        public Image getQuoteAsImage(String avatarUrl, String username, String message, String nameColor, String dateformat, Consumer<Image> imageOnSuccess, @Nullable Consumer<IResponse.Error> onError){
            try{
                JSONObject payload = new JSONObject()
                        .put("avatar", avatarUrl).put("username", username).put("message", message).put("nameColor", nameColor).put("dateFormat", dateformat);
                purrito.newRequest()
                        .useEndpoint(Endpoint.MSC_Quote)
                        .getReturnType(Endpoint.ReturnType.RAW_IMAGE)
                        .addTransmissionData(payload)
                        .prepare()
                        .execute(success -> {
                            imageOnSuccess.accept(new Image(success.getBytePayload()));
                        }, onError);
            }catch (Exception e){
                if(onError != null){
                    logger.debug("Something went wrong getting the image data:", e);
                    onError.accept(new ResponseError(e));
                }else {
                    logger.error("Something went wrong getting the image data:", e);
                }
            }
            return null;
        }

        /**
         * Easy to use method to get a status as image
         * @param avatarUrl of the user avatar
         * @param status of the status type (options: online, idle, do_not_disturb, dnd, streaming, offline)
         * @param imageOnSuccess to be called with the result
         * @param onError to be called if anything goes wrong
         */
        public void getStatusAsImage(String avatarUrl, String status, Consumer<Image> imageOnSuccess, @Nullable Consumer<IResponse.Error> onError){
            getStatusAsImage(avatarUrl, status, false, imageOnSuccess, onError);
        }

        /**
         * Easy to use method to get a status as image
         * @param avatarUrl of the user avatar
         * @param status of the status type (options: online, idle, do_not_disturb, dnd, streaming, offline)
         * @param isMobile if the user is using his mobile device
         * @param imageOnSuccess to be called with the result
         * @param onError to be called if anything goes wrong
         */
        public void getStatusAsImage(String avatarUrl, String status, boolean isMobile,  Consumer<Image> imageOnSuccess, @Nullable Consumer<IResponse.Error> onError){
            try{
                JSONObject payload = new JSONObject()
                        .put("avatar", avatarUrl).put("status", status).put("mobile", isMobile);
                purrito.newRequest()
                        .useEndpoint(Endpoint.MSC_Status)
                        .getReturnType(Endpoint.ReturnType.RAW_IMAGE)
                        .addTransmissionData(payload)
                        .prepare()
                        .execute(success -> {
                            imageOnSuccess.accept(new Image(success.getBytePayload()));
                        }, onError);
            }catch (Exception e){
                if(onError != null){
                    logger.debug("Something went wrong getting the image data:", e);
                    onError.accept(new ResponseError(e));
                }else {
                    logger.error("Something went wrong getting the image data:", e);
                }
            }
        }
    }
}
