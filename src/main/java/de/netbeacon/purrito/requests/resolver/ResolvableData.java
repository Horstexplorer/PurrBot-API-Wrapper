package de.netbeacon.purrito.requests.resolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public abstract class ResolvableData{

	protected byte[] data;

	public ResolvableData(byte[] data){
		this.data = data;
	}

	public byte[] dataAsBytes(){
		return data;
	}

	public InputStream dataAsInputStream(){
		return new ByteArrayInputStream(dataAsBytes());
	}

}
