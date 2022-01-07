package de.netbeacon.purrito;

import de.hypercdn.commons.imp.executionaction.SupplierExecutionAction;
import de.netbeacon.purrito.requests.image.ImageContent;
import de.netbeacon.purrito.requests.image.ImageType;
import de.netbeacon.purrito.requests.resolver.DataResolver;
import de.netbeacon.purrito.requests.resolver.data.AnimeImageURLData;
import de.netbeacon.purrito.requests.resolver.data.ImageData;
import de.netbeacon.purrito.requests.resolver.request.ImageURLRequest;
import de.netbeacon.purrito.requests.resolver.request.QuoteImageRequest;
import de.netbeacon.purrito.requests.resolver.request.StatusImageRequest;
import okhttp3.OkHttpClient;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 */
public class Purrito{

	private final OkHttpClient okHttpClient;
	private final Executor executor;
	private final DataResolver dataResolver;

	/**
	 * Creates a new instance of the wrapper
	 */
	public Purrito(){
		this(
			new OkHttpClient(),
			Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
		);
	}

	/**
	 * Creates a new instance of the wrapper
	 *
	 * @param okHttpClient which should be used to make requests
	 * @param executor which should be used for async callbacks
	 */
	public Purrito(OkHttpClient okHttpClient, Executor executor){
		Objects.requireNonNull(okHttpClient);
		Objects.requireNonNull(executor);
		this.okHttpClient = okHttpClient;
		this.executor = executor;
		this.dataResolver = new DataResolver(okHttpClient, executor);
	}

	public OkHttpClient getOkHttpClient(){
		return okHttpClient;
	}

	public Executor getExecutor(){
		return executor;
	}

	/**
	 * Returns an action which can be used to retrieve an url for a random anime image
	 *
	 * @param imageContent wanted content topic
	 * @param imageType wanted image type
	 * @return action supplying image url data
	 */
	public SupplierExecutionAction<AnimeImageURLData> retrieveAnimeImageFor(ImageContent imageContent, ImageType imageType){
		Objects.requireNonNull(imageContent);
		Objects.requireNonNull(imageType);
		return dataResolver.newResolveTask(ImageURLRequest.of(dataResolver, imageContent, imageType));
	}

	/**
	 * Returns an action which can be used to generate a quote image
	 *
	 * @param avatarUrl of the user avatar
	 * @param username of the user
	 * @param message to be quoted
	 * @param nameColor of the name
	 * @param dateformat to be shown
	 * @return action supplying image data
	 */
	public SupplierExecutionAction<ImageData> retrieveQuoteImageOf(String avatarUrl, String username, String message, String nameColor, String dateformat){
		Objects.requireNonNull(avatarUrl);
		Objects.requireNonNull(username);
		Objects.requireNonNull(message);
		Objects.requireNonNull(nameColor);
		Objects.requireNonNull(dateformat);
		return dataResolver.newResolveTask(QuoteImageRequest.of(avatarUrl, username, message, nameColor, dateformat));
	}

	/**
	 * Returns an action which can be used to generate a status image
	 *
	 * @param avatarUrl of the user
	 * @param status to display
	 * @param isMobile is using his mobile device
	 * @return action supplying image data
	 */
	public SupplierExecutionAction<ImageData> retrieveStatusImageOf(String avatarUrl, String status, boolean isMobile){
		Objects.requireNonNull(avatarUrl);
		Objects.requireNonNull(status);
		return dataResolver.newResolveTask(StatusImageRequest.of(avatarUrl, status, isMobile));
	}

}
