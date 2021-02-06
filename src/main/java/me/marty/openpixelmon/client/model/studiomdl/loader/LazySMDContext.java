package me.marty.openpixelmon.client.model.studiomdl.loader;

public class LazySMDContext {

	private final String location;
	private SMDContext context;

	protected LazySMDContext(String location) {
		this.location = location;
	}

	public SMDContext getContext(){
		if(context == null){
			context = SMDReader.readPokemonMdl(location);
		}
		return context;
	}
}
