package me.martyalana.openpixelmon.mixin;

import me.martyalana.openpixelmon.OpenPixelmon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
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

	private static final Identifier SIDEBAR = OpenPixelmon.id("textures/gui/ingame/sidebar.png");

	@Inject(method = "renderHotbar", at = @At("HEAD"))
	private void renderPixelmonParty(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
		client.getTextureManager().bindTexture(SIDEBAR);
		this.drawTexture(matrices, 0, this.scaledHeight / 2 - 83, 0, 0, 36, 167);
	}
}
