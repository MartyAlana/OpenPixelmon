package me.marty.openpixelmon.client.model.studiomdl.loader;

public class LazySMDContext {

	private final String location;
	private SmdModel context;

	protected LazySMDContext(String location) {
		this.location = location;
	}

	public SmdModel getContext(){
		if(context == null){
			context = SMDReader.readPokemonMdl(location);
		}
		return context;
	}
}
