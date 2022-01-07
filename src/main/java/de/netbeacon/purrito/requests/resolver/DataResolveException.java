package de.netbeacon.purrito.requests.resolver;

public class DataResolveException extends RuntimeException{

	public DataResolveException(String message){
		super(message);
	}

	public DataResolveException(String message, Exception e){
		super(message, e);
	}

}
