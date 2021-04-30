package me.marty.openpixelmon.mixin;

import me.marty.openpixelmon.client.render.gui.Overlays;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void pleaseDontSueMe(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Overlays.renderLegalOverlay(matrices, this.client, this.height, this.width);
    }
}
