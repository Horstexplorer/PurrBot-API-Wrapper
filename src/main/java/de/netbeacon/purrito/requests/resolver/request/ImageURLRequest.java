package de.netbeacon.purrito.requests.resolver.request;

import de.netbeacon.purrito.requests.image.ImageContent;
import de.netbeacon.purrito.requests.image.ImageType;
import de.netbeacon.purrito.requests.resolver.DataRequest;
import de.netbeacon.purrito.requests.resolver.DataResolveException;
import de.netbeacon.purrito.requests.resolver.DataResolver;
import de.netbeacon.purrito.requests.resolver.data.AnimeImageURLData;
import okhttp3.Request;
import okhttp3.Response;
import org.bson.Document;

public class ImageURLRequest extends DataRequest<AnimeImageURLData>{

	private final DataResolver dataResolver;

	protected ImageURLRequest(DataResolver dataResolver, Request request){
		super(request);
		this.dataResolver = dataResolver;
	}

	public static ImageURLRequest of(DataResolver dataResolver, ImageContent imageContent, ImageType imageType){
		var imageEndpointImp = imageContent.getEndpoint().getEndpointImpData();
		if(!imageEndpointImp.isAvailableFor(imageType)){
			throw new DataResolveException("ImageType " + imageType + " is not available for the image content " + imageContent);
		}
		var realType = imageType == ImageType.RANDOM ? imageEndpointImp.getRandomAvailableType() : imageType;
		var request = new Request.Builder()
			.get()
			.url(imageEndpointImp.getRequestURL(realType))
			.build();
		return new ImageURLRequest(dataResolver, request);
	}

	@Override
	public AnimeImageURLData resolveData(Response response){
		if(!response.isSuccessful()){
			throw new DataResolveException("Request failed with status code " + response.code());
		}
		try(var body = response.body()){
			var document = Document.parse(new String(body.bytes()));
			if(document.getBoolean("error")){
				throw new DataResolveException("Request failed with message " + document.getString("message"));
			}
			return new AnimeImageURLData(dataResolver, document.getString("link"));
		}
		catch(Exception e){
			throw new DataResolveException("An exception occurred while trying to read the response", e);
		}
	}

}
