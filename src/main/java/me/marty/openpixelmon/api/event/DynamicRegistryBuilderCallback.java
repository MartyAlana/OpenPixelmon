package me.marty.openpixelmon.api.event;

import me.marty.openpixelmon.api.util.DynamicRegistryBuilder;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface DynamicRegistryBuilderCallback {

	Event<DynamicRegistryBuilderCallback> EVENT = EventFactory.createArrayBacked(DynamicRegistryBuilderCallback.class, (callbacks) -> (builder) -> {
		for (DynamicRegistryBuilderCallback callback : callbacks) {
			callback.register(builder);
		}
	});

	void register(DynamicRegistryBuilder builder);
}
