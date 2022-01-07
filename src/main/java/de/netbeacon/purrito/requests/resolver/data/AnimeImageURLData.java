package de.netbeacon.purrito.requests.resolver.data;

import de.hypercdn.commons.imp.executionaction.SupplierExecutionAction;
import de.netbeacon.purrito.requests.resolver.DataResolver;
import de.netbeacon.purrito.requests.resolver.ResolvableData;
import de.netbeacon.purrito.requests.resolver.request.ImageFetchRequest;

import java.nio.charset.StandardCharsets;

/**
 * Represents the fetched url to an image
 */
public class AnimeImageURLData extends ResolvableData{

	private final DataResolver dataResolver;

	public AnimeImageURLData(DataResolver dataResolver, String imageUrl){
		super(imageUrl.getBytes(StandardCharsets.UTF_8));
		this.dataResolver = dataResolver;
	}

	/**
	 * Returns the fetched image url
	 *
	 * @return image url
	 */
	public String getImageURL(){
		return new String(this.dataAsBytes());
	}

	/**
	 * Returns an action to fetch the image data behind the url
	 *
	 * @return action providing image data
	 */
	public SupplierExecutionAction<ImageData> retrieveImage(){
		return dataResolver.newResolveTask(ImageFetchRequest.of(getImageURL()));
	}

}
