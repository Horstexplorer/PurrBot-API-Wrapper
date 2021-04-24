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
import de.netbeacon.purrito.core.request.Endpoint;
import de.netbeacon.purrito.core.responses.IResponse;
import de.netbeacon.purrito.core.responses.ResponseData;
import de.netbeacon.purrito.core.responses.ResponseError;
import de.netbeacon.purrito.qol.execution.ExecutionTask;
import de.netbeacon.purrito.qol.typewrap.Image;
import org.json.JSONObject;

import java.util.function.Consumer;

public class GetQuoteImage extends ExecutionTask<Image>{

	private final String avatarUrl;
	private final String username;
	private final String message;
	private final String nameColor;
	private final String dateformat;

	public GetQuoteImage(String avatarUrl, String username, String message){
		this.avatarUrl = avatarUrl;
		this.username = username;
		this.message = message;
		this.nameColor = "hex:ffffff";
		this.dateformat = "dd. MMM yyyy";
	}

	public GetQuoteImage(String avatarUrl, String username, String message, String nameColor, String dateformat){
		this.avatarUrl = avatarUrl;
		this.username = username;
		this.message = message;
		this.nameColor = nameColor;
		this.dateformat = dateformat;
	}


	@Override
	protected Image sync(PurritoRaw purritoRaw){
		try{
			JSONObject payload = new JSONObject()
				.put("avatar", avatarUrl).put("username", username).put("message", message).put("nameColor", nameColor).put("dateFormat", dateformat);
			IResponse iResponse = purritoRaw.newRequest()
				.useEndpoint(Endpoint.MSC_Quote)
				.getReturnType(Endpoint.ReturnType.RAW_IMAGE)
				.addTransmissionData(payload)
				.prepare()
				.execute();
			if(iResponse instanceof ResponseError){
				throw (ResponseError) iResponse;
			}
			return new Image(((ResponseData) iResponse).getBytePayload());
		}
		catch(Exception e){
			logger.error("Something went wrong getting the image data:", e);
		}
		return null;
	}

	@Override
	protected void async(PurritoRaw purritoRaw, Consumer<Image> onSuccess, Consumer<Exception> onError){
		try{
			JSONObject payload = new JSONObject()
				.put("avatar", avatarUrl).put("username", username).put("message", message).put("nameColor", nameColor).put("dateFormat", dateformat);
			purritoRaw.newRequest()
				.useEndpoint(Endpoint.MSC_Quote)
				.getReturnType(Endpoint.ReturnType.RAW_IMAGE)
				.addTransmissionData(payload)
				.prepare()
				.execute(success -> {
					onSuccess.accept(new Image(success.getBytePayload()));
				}, onError);
		}
		catch(Exception e){
			if(onError != null){
				logger.debug("Something went wrong getting the image data:", e);
				onError.accept(new ResponseError(e));
			}
			else{
				logger.error("Something went wrong getting the image data:", e);
			}
		}
	}

}
