package me.marty.openpixelmon.mixin;

import me.marty.openpixelmon.OpenPixelmon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashScreen.class)
public class SplashScreenMixin {

	@Inject(method = "render", at = @At("RETURN"))
	private void renderStatus(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
		if(OpenPixelmon.maxPixelmon != 0) {
			MinecraftClient.getInstance().textRenderer.draw(matrices, "Loading " + OpenPixelmon.loadingPixelmon, 4, 4, 0xFFFFFFFF);
			MinecraftClient.getInstance().textRenderer.draw(matrices, "(" + OpenPixelmon.currentPixelmon + "/" + OpenPixelmon.maxPixelmon + ")", 4, 16, 0xFFFFFFFF);
		}
	}
}
