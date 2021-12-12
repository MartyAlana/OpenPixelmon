package me.marty.openpixelmon.mixin.client;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.compatibility.PixelmonAssetProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.file.Files;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    private boolean assetProviderPresent;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void pixelmon_checkForProblems(CallbackInfo ci) {
        this.assetProviderPresent = OpenPixelmon.isAssetProviderPresent();

        if (!assetProviderPresent && !Files.exists(PixelmonAssetProvider.JAR_PATH)) {
            try {
                Files.createDirectories(PixelmonAssetProvider.JAR_PATH);
            } catch (IOException ignored) {
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void pixelmon_renderAssetError(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!assetProviderPresent) {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            matrices.push();
            renderBackground(matrices);
            drawCenteredTextWithShadow(matrices, textRenderer, new TranslatableText("Open Pixelmon has failed to load!").asOrderedText(), width / 2, (height / 2) - 28, 0xFFFF4444);
            drawCenteredTextWithShadow(matrices, textRenderer, new TranslatableText("Make sure you have Pixelmon: Generations in your 'compatability' folder in .minecraft").asOrderedText(), width / 2, (height / 2) - 12, 0xFFFFFFFF);
            matrices.pop();
            ci.cancel();
        }
    }
}
