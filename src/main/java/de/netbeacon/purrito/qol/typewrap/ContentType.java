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

package de.netbeacon.purrito.qol.typewrap;

import de.netbeacon.purrito.core.request.Endpoint;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Contains all supported content types
 */
public enum ContentType {
        IMAGE(Endpoint.ReturnType.JSON_w_IMG),
        GIF(Endpoint.ReturnType.JSON_w_GIF),
        AVAILABLE(),
        RANDOM()
    ;

	private final Endpoint.ReturnType returnType;

	ContentType(){
		this.returnType = null;
	}

	ContentType(Endpoint.ReturnType returnType){
		this.returnType = returnType;
	}

    /**
     * Returns the return type equivalent
     * @return return type
     */
    public Endpoint.ReturnType getReturnTypes() {
        return returnType;
    }

    /**
     * Helper method
     * @param imageType
     * @return available content type of image; either gif or image
     */
    public static ContentType findAvailable(ImageType imageType){
        var types = imageType.getEndpoint().getReturnTypes();
        if(types.contains(Endpoint.ReturnType.JSON_w_GIF)){
            return ContentType.GIF;
        }else if(types.contains(Endpoint.ReturnType.JSON_w_IMG)){
            return ContentType.IMAGE;
        }else{
            return null;
        }
    }

    /**
     * Helper method
     * @param imageType
     * @return available content type of image; either gif or image
     */
    public static ContentType findRandom(ImageType imageType){
        var types = imageType.getEndpoint().getReturnTypes().stream()
                .filter(r -> r.equals(Endpoint.ReturnType.JSON_w_GIF) || r.equals(Endpoint.ReturnType.JSON_w_IMG))
                .collect(Collectors.toList());
        if(types.isEmpty()) return null;
        Collections.shuffle(types);
        switch (types.get(0)){
            case JSON_w_GIF:
                return ContentType.GIF;
            case JSON_w_IMG:
                return ContentType.IMAGE;
            default:
                return null;
        }
    }
}
