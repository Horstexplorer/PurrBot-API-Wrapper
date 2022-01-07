package de.netbeacon.purrito.requests.endpoints;

import de.netbeacon.purrito.requests.image.ImageType;

import java.util.Locale;

public class APIEndpoint{

	public static final String BASE_URL = "https://purrbot.site/api";

	private final RequestMethod method;
	private final String path;

	public APIEndpoint(RequestMethod method, String path){
		this.method = method;
		this.path = path;
	}

	public RequestMethod getMethod(){
		return method;
	}

	public String getPath(){
		return path;
	}

	public String getRequestURL(){
		return BASE_URL + getPath();
	}

	public String getRequestURL(ImageType imageType){
		return BASE_URL + getPath() + imageType.name().toLowerCase(Locale.ROOT);
	}

	public enum RequestMethod{
		GET,
		POST,
		PUT,
		PATCH,
		DELETE
	}

}
