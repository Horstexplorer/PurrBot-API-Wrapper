package de.netbeacon.purrito.requests.resolver.request;

import de.netbeacon.purrito.requests.resolver.DataRequest;
import de.netbeacon.purrito.requests.resolver.DataResolveException;
import de.netbeacon.purrito.requests.resolver.data.ImageData;
import okhttp3.Request;
import okhttp3.Response;

public class ImageFetchRequest extends DataRequest<ImageData>{

	protected ImageFetchRequest(Request request){
		super(request);
	}

	public static ImageFetchRequest of(String url){
		var request = new Request.Builder()
			.get()
			.url(url)
			.build();
		return new ImageFetchRequest(request);
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
