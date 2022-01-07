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

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Purrito{

	private final OkHttpClient okHttpClient;
	private final Executor executor;
	private final DataResolver dataResolver;

	public Purrito(){
		this(
			new OkHttpClient(),
			Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
		);
	}

	public Purrito(OkHttpClient okHttpClient, Executor executor){
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

	public SupplierExecutionAction<AnimeImageURLData> retrieveAnimeImageFor(ImageContent imageContent, ImageType imageType){
		return dataResolver.newResolveTask(ImageURLRequest.of(dataResolver, imageContent, imageType));
	}

	public SupplierExecutionAction<ImageData> retrieveQuoteImageOf(String avatarUrl, String username, String message, String nameColor, String dateformat){
		return dataResolver.newResolveTask(QuoteImageRequest.of(avatarUrl, username, message, nameColor, dateformat));
	}

	public SupplierExecutionAction<ImageData> retrieveStatusImageOf(String avatarUrl, String status, boolean isMobile){
		return dataResolver.newResolveTask(StatusImageRequest.of(avatarUrl, status, isMobile));
	}

}
