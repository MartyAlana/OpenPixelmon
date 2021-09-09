package me.marty.openpixelmon.client.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ScalableTexturedButtonWidget extends TexturedButtonWidget {

    private final float idealX;
    private final float idealY;

    public ScalableTexturedButtonWidget(float x, float y, int width, int height, Identifier texture, PressAction pressAction, Text text, AnchorLocation anchor) {
        this(x, y, width, height, texture, pressAction, EMPTY, text, anchor);
    }

    public ScalableTexturedButtonWidget(float x, float y, int width, int height, Identifier texture, PressAction pressAction, TooltipSupplier tooltipSupplier, Text text, AnchorLocation anchor) {
        super(anchor == AnchorLocation.CENTER ? width / 2 : width, anchor == AnchorLocation.CENTER ? height / 2 : height, width, height, 0, 0, 0, texture, width, height, pressAction, tooltipSupplier, text);
        this.idealX = x;
        this.idealY = y;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
            int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
            int adjustedX = (int) (this.idealX * scaledWidth) - this.x;
            int adjustedY = (int) (this.idealY * scaledHeight) - this.y;

            this.hovered = mouseX >= adjustedX && mouseY >= adjustedY && mouseX < adjustedX + this.width && mouseY < adjustedY + this.height;
            this.renderButton(matrices, mouseX, mouseY, delta);
        }
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.enableBlend();

        int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int adjustedX = (int) (this.idealX * scaledWidth);
        int adjustedY = (int) (this.idealY * scaledHeight);

        RenderSystem.enableDepthTest();
        if (this.isHovered()) {

        }
        drawTexture(matrices, adjustedX - this.x, adjustedY - this.y, this.u, this.v, this.width, this.height, this.width, this.height);
        if (this.isHovered()) {
            this.renderTooltip(matrices, mouseX, mouseY);
        }
    }

    public enum AnchorLocation {
        CENTER,
        RIGHT
    }
}
