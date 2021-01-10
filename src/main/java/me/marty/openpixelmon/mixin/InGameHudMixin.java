package me.marty.openpixelmon.mixin;

import me.marty.openpixelmon.client.render.gui.Overlays;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper {

	@Shadow private int scaledHeight;

	@Shadow @Final private MinecraftClient client;

	@Inject(method = "renderHotbar", at = @At("HEAD"))
	private void renderPixelmonParty(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
		Overlays.renderPartyOverlay(matrices, client, scaledHeight);
	}
}
