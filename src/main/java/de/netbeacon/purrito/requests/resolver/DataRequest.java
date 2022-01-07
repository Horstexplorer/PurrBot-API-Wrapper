package de.netbeacon.purrito.requests.resolver;

import okhttp3.Request;
import okhttp3.Response;

public abstract class DataRequest<T extends ResolvableData>{

	protected final Request request;

	public DataRequest(Request request){
		this.request = request;
	}

	public Request getRequest(){
		return request;
	}

	public abstract T resolveData(Response response);

}
