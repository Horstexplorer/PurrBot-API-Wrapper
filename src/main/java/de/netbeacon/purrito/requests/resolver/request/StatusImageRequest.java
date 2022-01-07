package de.netbeacon.purrito.requests.resolver.request;

import de.netbeacon.purrito.requests.endpoints.ImageGenerationEndpoints;
import de.netbeacon.purrito.requests.resolver.DataRequest;
import de.netbeacon.purrito.requests.resolver.DataResolveException;
import de.netbeacon.purrito.requests.resolver.data.ImageData;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.bson.Document;

import java.nio.charset.StandardCharsets;

public class StatusImageRequest extends DataRequest<ImageData>{

	protected StatusImageRequest(Request request){
		super(request);
	}

	public static StatusImageRequest of(String avatarUrl, String status, boolean isMobile){
		var document = new Document(){{
			put("avatarUrl", avatarUrl);
			put("status", status);
			put("isMobile", isMobile);
		}};
		var request = new Request.Builder()
			.post(RequestBody.create(document.toJson().getBytes(StandardCharsets.UTF_16), MediaType.parse("application/json")))
			.url(ImageGenerationEndpoints.Status.getEndpointImpData().getRequestURL())
			.build();
		return new StatusImageRequest(request);
	}

	@Override
	public ImageData resolveData(Response response){
		if(!response.isSuccessful()){
			throw new DataResolveException("Request failed with status code " + response.code());
		}
		try(var body = response.body()){
			return new ImageData(body.bytes());
		}
		catch(Exception e){
			throw new DataResolveException("An exception occurred while trying to read the response", e);
		}
	}

}
