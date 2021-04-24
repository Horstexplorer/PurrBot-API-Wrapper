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

package de.netbeacon.purrito.core.responses;

import org.json.JSONObject;

public interface IResponse{

	/**
	 * Returns the response type - data on success, error on failure
	 *
	 * @return
	 */
	Type getResponseType();

	enum Type{
		DATA,
		ERROR;
	}

	interface Success extends IResponse{

		/**
		 * Can be used to get the payload as bytes
		 *
		 * @return byte[]
		 */
		byte[] getBytePayload();

		/**
		 * Can be used to convert the payload to a json object
		 *
		 * @return json object
		 */
		JSONObject getAsJSONPayload();

	}

	interface Error extends IResponse{

		/**
		 * Returns the throwable
		 *
		 * @return throwable
		 */
		Throwable getThrowable();

	}

}
