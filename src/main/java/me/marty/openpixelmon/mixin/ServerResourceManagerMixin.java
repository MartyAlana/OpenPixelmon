package me.marty.openpixelmon.mixin;

import me.marty.openpixelmon.data.DataLoaders;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerResourceManager.class)
public class ServerResourceManagerMixin {

	@Shadow @Final private ReloadableResourceManager resourceManager;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void loadPixelmonResources(DynamicRegistryManager dynamicRegistryManager, CommandManager.RegistrationEnvironment registrationEnvironment, int i, CallbackInfo ci){
		this.resourceManager.registerListener(DataLoaders.GENERATION_MANAGER);
		this.resourceManager.registerListener(DataLoaders.MOVE_MANAGER);
		this.resourceManager.registerListener(DataLoaders.PIXELMON_MANAGER);
		this.resourceManager.registerListener(DataLoaders.PIXELMON_TYPE_MANAGER);
	}
}
