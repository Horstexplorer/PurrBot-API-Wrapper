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

import de.netbeacon.purrito.core.PurritoRaw;
import de.netbeacon.purrito.qol.execution.ExecutionStage;
import de.netbeacon.purrito.qol.execution.tasks.*;
import de.netbeacon.purrito.qol.typewrap.ContentType;
import de.netbeacon.purrito.qol.typewrap.Image;
import de.netbeacon.purrito.qol.typewrap.ImageType;
import java.io.InputStream;

public class Purrito {

    private final PurritoRaw purritoRaw;

    /**
     * Creates a new instance of this wrapper
     */
    public Purrito() {
        this.purritoRaw = new PurritoRaw();
    }

    /**
     * Creates a new instance of this wrapper with the specified purrito instance
     *
     * @param purritoRaw instance
     */
    public Purrito(PurritoRaw purritoRaw) {
        this.purritoRaw = purritoRaw;
    }


    // ********** TASKS **********

    /**
     * Retrieve the url of an anime image specified with the parameter
     *
     * @param imageType   of the image
     * @param contentType of the image
     *
     * @return ExecutionStage<String>
     */
    public ExecutionStage<String> getAnimeImageUrlOf(ImageType imageType, ContentType contentType) {
        return new ExecutionStage<>(purritoRaw, new GetAnimeImageUrl(imageType, contentType));
    }

    /**
     * Retrieve the input stream of an anime image specified with the parameter
     *
     * @param imageType   of the image
     * @param contentType of the image
     *
     * @return ExecutionStage<InputStream>
     */
    public ExecutionStage<InputStream> getAnimeImageInputStreamOf(ImageType imageType, ContentType contentType) {
        return new ExecutionStage<>(purritoRaw, new GetAnimeImageInputStream(imageType, contentType));
    }

    /**
     * Retrieve the image data of an anime image specified with the parameter
     *
     * @param imageType   of the image
     * @param contentType of the image
     *
     * @return ExecutionStage<Image>
     */
    public ExecutionStage<Image> getAnimeImageOf(ImageType imageType, ContentType contentType) {
        return new ExecutionStage<>(purritoRaw, new GetAnimeImage(imageType, contentType));
    }

    /**
     * Retrieve the image data of a quote
     *
     * @param avatarUrl of the user
     * @param username  of the user
     * @param message   of the user
     *
     * @return ExecutionStage<Image>
     */
    public ExecutionStage<Image> getQuoteImageOf(String avatarUrl, String username, String message) {
        return getQuoteImageOf(avatarUrl, username, message, "hex:ffffff", "dd. MMM yyyy");
    }

    /**
     * Retrieve the image data of a quote
     *
     * @param avatarUrl  of the user
     * @param username   of the user
     * @param message    of the user
     * @param nameColor  color of the name (example: "hex:ffffff")
     * @param dateformat to format the shown data (example: "dd. MMM yyyy")
     *
     * @return ExecutionStage<Image>
     */
    public ExecutionStage<Image> getQuoteImageOf(String avatarUrl,
                                                 String username,
                                                 String message,
                                                 String nameColor,
                                                 String dateformat) {
        return new ExecutionStage<>(purritoRaw, new GetQuoteImage(avatarUrl, username, message, nameColor, dateformat));
    }

    /**
     * Retrieve the image data of a status
     *
     * @param avatarUrl of the user
     * @param status    of the user (options: online, idle, do_not_disturb, dnd, streaming, offline)
     *
     * @return ExecutionStage<Image>
     */
    public ExecutionStage<Image> getStatusImageOf(String avatarUrl, String status) {
        return getStatusImageOf(avatarUrl, status, false);
    }

    /**
     * Retrieve the image data of a status
     *
     * @param avatarUrl of the user
     * @param status    of the user (options: online, idle, do_not_disturb, dnd, streaming, offline)
     * @param isMobile  if the status should show as if he is using a mobile device
     *
     * @return ExecutionStage<Image>
     */
    public ExecutionStage<Image> getStatusImageOf(String avatarUrl, String status, boolean isMobile) {
        return new ExecutionStage<>(purritoRaw, new GetStatusImage(avatarUrl, status, isMobile));
    }
}
