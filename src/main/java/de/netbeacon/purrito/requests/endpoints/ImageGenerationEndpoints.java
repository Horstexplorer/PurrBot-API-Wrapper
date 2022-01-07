package de.netbeacon.purrito.requests.endpoints;

public enum ImageGenerationEndpoints{
	Quote(new Imp("/quote")),
	Status(new Imp("/status"));

	private final Imp imp;

	ImageGenerationEndpoints(Imp imp){
		this.imp = imp;
	}

	public Imp getEndpointImpData(){
		return imp;
	}


	public static class Imp extends APIEndpoint{

		private Imp(String path){
			super(RequestMethod.POST, path);
		}

	}


}
