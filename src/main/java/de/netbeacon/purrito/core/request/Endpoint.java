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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

/**
 * Contains a list of all endpoints with their specific data
 */
public enum Endpoint {

    // MISC
    MSC_Quote(Request.Method.POST,
            "/quote",
            false,
            new JSONObject().put("avatar", "").put("dateFormat", "").put("message", "").put("nameColor", "")
                    .put("timestamp", Long.MAX_VALUE).put("username", ""),
            ReturnType.RAW_IMAGE),
    MSC_Status(Request.Method.POST,
            "/status",
            false,
            new JSONObject().put("avatar", "").put("status", "").put("mobile", false),
            ReturnType.RAW_IMAGE),
    // SFW IMAGES
    SFW_Background(Request.Method.GET, "/img/sfw/background/", false, null, ReturnType.JSON, ReturnType.JSON_w_IMG),
    SFW_Bite(Request.Method.GET, "/img/sfw/bite/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Blush(Request.Method.GET, "/img/sfw/blush/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Cry(Request.Method.GET, "/img/sfw/cry/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Cuddle(Request.Method.GET, "/img/sfw/cuddle/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Dance(Request.Method.GET, "/img/sfw/dance/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Eevee(Request.Method.GET,
            "/img/sfw/eevee/",
            false,
            null,
            ReturnType.JSON,
            ReturnType.JSON_w_IMG,
            ReturnType.JSON_w_GIF),
    SFW_Feed(Request.Method.GET, "/img/sfw/feed/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Fluff(Request.Method.GET, "/img/sfw/fluff/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Holo(Request.Method.GET, "/img/sfw/", false, null, ReturnType.JSON, ReturnType.JSON_w_IMG),
    SFW_Hug(Request.Method.GET, "/img/sfw/hug/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Icon(Request.Method.GET, "/img/sfw/icon/", false, null, ReturnType.JSON, ReturnType.JSON_w_IMG),
    SFW_Kiss(Request.Method.GET, "/img/sfw/kiss/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Kitsune(Request.Method.GET, "/img/sfw/kitsune/", false, null, ReturnType.JSON, ReturnType.JSON_w_IMG),
    SFW_Lick(Request.Method.GET, "/img/sfw/lick/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Neko(Request.Method.GET,
            "/img/sfw/neko/",
            false,
            null,
            ReturnType.JSON,
            ReturnType.JSON_w_IMG,
            ReturnType.JSON_w_GIF),
    SFW_Ookami(Request.Method.GET, "/img/sfw/okami/", false, null, ReturnType.JSON, ReturnType.JSON_w_IMG),
    SFW_Pat(Request.Method.GET, "/img/sfw/pat/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Poke(Request.Method.GET, "/img/sfw/poke/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Senko(Request.Method.GET, "/img/sfw/senko/", false, null, ReturnType.JSON, ReturnType.JSON_w_IMG),
    SFW_Slap(Request.Method.GET, "/img/sfw/slap/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Smile(Request.Method.GET, "/img/sfw/smile/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Tail(Request.Method.GET, "/img/sfw/tail/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    SFW_Tickle(Request.Method.GET, "/img/sfw/tickle/", false, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    // NSFW IMAGES
    NSFW_Anal(Request.Method.GET, "/img/nsfw/anal/", true, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    NSFW_Blowjob(Request.Method.GET, "/img/nsfw/blowjob/", true, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    NSFW_Cum(Request.Method.GET, "/img/nsfw/cum/", true, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    NSFW_Fuck(Request.Method.GET, "/img/nsfw/fuck/", true, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    NSFW_Neko(Request.Method.GET,
            "/img/nsfw/neko/",
            true,
            null,
            ReturnType.JSON,
            ReturnType.JSON_w_IMG,
            ReturnType.JSON_w_GIF),
    NSFW_Pussylick(Request.Method.GET, "/img/nsfw/pussylick/", true, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    NSFW_Solo(Request.Method.GET, "/img/nsfw/solo/", true, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    NSFW_Threesome_fff(Request.Method.GET,
            "/img/nsfw/threesome_fff/",
            true,
            null,
            ReturnType.JSON,
            ReturnType.JSON_w_GIF),
    NSFW_Threesome_ffm(Request.Method.GET,
            "/img/nsfw/threesome_ffm/",
            true,
            null,
            ReturnType.JSON,
            ReturnType.JSON_w_GIF),
    NSFW_Threesome_mmf(Request.Method.GET,
            "/img/nsfw/threesome_mmf/",
            true,
            null,
            ReturnType.JSON,
            ReturnType.JSON_w_GIF),
    NSFW_Yaoi(Request.Method.GET, "/img/nsfw/yaoi", true, null, ReturnType.JSON, ReturnType.JSON_w_GIF),
    NSFW_Yuri(Request.Method.GET, "/img/nsfw/yuri", true, null, ReturnType.JSON, ReturnType.JSON_w_GIF);


    /**
     * The base url of the api
     */
    public static final String BASE_URL = "https://purrbot.site/api";
    private final String path;
    private final boolean isNsfw;
    private final JSONObject requiredData;
    private final Request.Method requestMethod;
    private final HashSet<ReturnType> returnTypes;
    Endpoint(Request.Method method, String path, boolean isNsfw, JSONObject requiredData, ReturnType... returnTypes) {
        this.requestMethod = method;
        this.path = path;
        this.isNsfw = isNsfw;
        this.requiredData = requiredData;
        this.returnTypes = new HashSet<>(List.of(returnTypes));
    }

    /**
     * Returns the required http request method
     *
     * @return request method
     */
    public Request.Method getRequestMethod() {
        return requestMethod;
    }

    /**
     * Returns the expected request url with the default request type
     *
     * @return request url
     */
    public String getRequestURL() {
        return getRequestURL(getReturnType());
    }

    /**
     * Returns the expected request url with the given request type
     *
     * @param returnType desired type
     *
     * @return request url
     *
     * @throws IllegalArgumentException if the endpoint does not support the given request type
     */
    public String getRequestURL(ReturnType returnType) {
        if (!returnTypes.contains(returnType)) {
            throw new IllegalArgumentException("Invalid return type for this endpoint");
        }
        return BASE_URL + path + returnType.getAdditionalPath();
    }

    /**
     * Returns the url path for this endpoint
     *
     * @return url path
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns if this endpoint is nsfw
     *
     * @return endpoint is nsfw
     */
    public boolean isNsfw() {
        return isNsfw;
    }

    /**
     * Returns the required json structure for additional data
     *
     * @return jsonobject or null
     */
    public JSONObject getRequiredData() {
        return requiredData;
    }

    /**
     * Returns a set of all available return types for this endpoint
     *
     * @return set of return types
     */
    public Set<ReturnType> getReturnTypes() {
        return returnTypes;
    }

    /**
     * Returns the default return type
     *
     * @return default return type or null if broken
     */
    public ReturnType getReturnType() {
        return returnTypes.stream().filter(f -> !f.equals(ReturnType.JSON)).findFirst().orElse(null);
    }

    /**
     * Expected return types from the endpoint
     */
    public enum ReturnType {
        JSON_w_IMG("img", "application/json"),
        JSON_w_GIF("gif", "application/json"),
        JSON("", "application/json"),
        RAW_IMAGE("", "application/png");

        private final String additionalPath;
        private final String contentType;

        ReturnType(String additionalPath, String contentType) {
            this.additionalPath = additionalPath;
            this.contentType = contentType;
        }

        public String getAdditionalPath() {
            return additionalPath;
        }

        public String getContentType() {
            return contentType;
        }
    }
}
