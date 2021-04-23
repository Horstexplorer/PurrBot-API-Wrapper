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
import de.netbeacon.purrito.qol.typewrap.ImageType;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;

public class GetAnimeImageInputStream extends ExecutionTask<InputStream> {

    private final ImageType imageType;
    private final ContentType contentType;

    public GetAnimeImageInputStream(ImageType imageType, ContentType contentType) {
        this.imageType = imageType;
        this.contentType =
                contentType.equals(ContentType.AVAILABLE) ? ContentType.findAvailable(imageType) : contentType;
    }

    @Override
    protected InputStream sync(PurritoRaw purritoRaw) {
        try {
            ExecutionStage<String> executionStage =
                    new ExecutionStage<>(purritoRaw, new GetAnimeImageUrl(imageType, contentType));
            String url = executionStage.sync();
            if (url == null) {
                return null;
            }
            return new URL(url).openStream();
        }
        catch (Exception e) {
            logger.error("Something went wrong getting the image input stream:", e);
        }
        return null;
    }

    @Override
    protected void async(PurritoRaw purritoRaw, Consumer<InputStream> onSuccess, Consumer<Exception> onError) {
        ExecutionStage<String> executionStage =
                new ExecutionStage<>(purritoRaw, new GetAnimeImageUrl(imageType, contentType));
        executionStage.async((url) -> {
            try {
                onSuccess.accept(new URL(url).openStream());
            }
            catch (Exception e) {
                if (onError != null) {
                    logger.debug("Something went wrong getting the image input stream:", e);
                    onError.accept(new ResponseError(e));
                }
                else {
                    logger.error("Something went wrong getting the image input stream:", e);
                }
            }
        }, onError);
    }
}
