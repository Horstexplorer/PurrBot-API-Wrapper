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

import de.netbeacon.purrito.core.request.Endpoint;
import org.json.JSONObject;

/**
 * Used when a data reception was successful.
 * The data received might still point to an issue with the transmission
 */
public class ResponseData implements IResponse.Success{

	private final Endpoint.ReturnType returnType;
	private final byte[] bytePayload;

	/**
	 * Creates a new instance of this class
	 *
	 * @param returnType  selected return type which should be included in the bytes
	 * @param bytePayload the payload
	 */
	public ResponseData(Endpoint.ReturnType returnType, byte[] bytePayload){
		this.returnType = returnType;
		this.bytePayload = bytePayload;
	}

	/**
	 * Return type of the data included
	 *
	 * @return return type
	 */
	public Endpoint.ReturnType getReturnType(){
		return returnType;
	}

	@Override
	public Type getResponseType(){
		return Type.DATA;
	}

	@Override
	public byte[] getBytePayload(){
		return bytePayload;
	}

	@Override
	public JSONObject getAsJSONPayload(){
		return new JSONObject(new String(bytePayload));
	}

}
