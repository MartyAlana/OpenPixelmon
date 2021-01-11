package me.marty.openpixelmon.mixin;

import me.marty.openpixelmon.data.PixelmonTypeManager;
import me.marty.openpixelmon.data.GenerationManager;
import me.marty.openpixelmon.data.MoveManager;
import me.marty.openpixelmon.data.PixelmonManager;
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
		this.resourceManager.registerListener(new PixelmonManager());
		this.resourceManager.registerListener(new GenerationManager());
		this.resourceManager.registerListener(new MoveManager());
		this.resourceManager.registerListener(new PixelmonTypeManager());
	}
}
