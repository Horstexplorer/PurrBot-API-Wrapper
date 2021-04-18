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
import de.netbeacon.purrito.core.responses.ResponseError;
import de.netbeacon.purrito.qol.execution.ExecutionStage;
import de.netbeacon.purrito.qol.execution.ExecutionTask;
import de.netbeacon.purrito.qol.typewrap.ContentType;
import de.netbeacon.purrito.qol.typewrap.Image;
import de.netbeacon.purrito.qol.typewrap.ImageType;

import java.io.InputStream;
import java.util.function.Consumer;

public class GetAnimeImage extends ExecutionTask<Image> {

    private final ImageType imageType;
    private final ContentType contentType;

    public GetAnimeImage(ImageType imageType, ContentType contentType){
        this.imageType = imageType;
        this.contentType = contentType;
    }

    @Override
    protected Image sync(PurritoRaw purritoRaw) {
        try{
            ExecutionStage<InputStream> executionStage = new ExecutionStage<>(purritoRaw, new GetAnimeImageInputStream(imageType, contentType));
            InputStream inputStream = executionStage.sync();
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

    @Override
    protected void async(PurritoRaw purritoRaw, Consumer<Image> onSuccess, Consumer<Exception> onError) {
        ExecutionStage<InputStream> executionStage = new ExecutionStage<>(purritoRaw, new GetAnimeImageInputStream(imageType, contentType));
        executionStage.async((inputStream) -> {
            try(inputStream){
                onSuccess.accept(new Image(inputStream.readAllBytes()));
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