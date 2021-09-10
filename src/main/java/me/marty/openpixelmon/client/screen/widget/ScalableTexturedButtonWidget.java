package me.marty.openpixelmon.client.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ScalableTexturedButtonWidget extends TexturedButtonWidget {

    private final float idealX;
    private final float idealY;
    private final Text text;
    private final AnchorLocation anchor;
    private final int color;

    public ScalableTexturedButtonWidget(float x, float y, int width, int height, Identifier texture, PressAction pressAction, Text text, int color, AnchorLocation anchor) {
        this(x, y, width, height, texture, pressAction, EMPTY, text, color, anchor);
    }

    public ScalableTexturedButtonWidget(float x, float y, int width, int height, Identifier texture, PressAction pressAction, TooltipSupplier tooltipSupplier, Text text, int color, AnchorLocation anchor) {
        super(anchor == AnchorLocation.CENTER ? width / 2 : width, anchor == AnchorLocation.CENTER ? height / 2 : height, width, height, 0, 0, 0, texture, width, height, pressAction, tooltipSupplier, text);
        this.idealX = x;
        this.idealY = y;
        this.text = text;
        this.anchor = anchor;
        this.color = color;
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int adjustedX = (int) (this.idealX * scaledWidth) - this.x;
        int adjustedY = (int) (this.idealY * scaledHeight) - this.y;

        return mouseX >= adjustedX && mouseY >= adjustedY && mouseX < adjustedX + this.width && mouseY < adjustedY + this.height;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(button)) {
                boolean clicked = this.clicked(mouseX, mouseY);
                if (clicked) {
                    this.playDownSound(MinecraftClient.getInstance().getSoundManager());
                    this.onClick(mouseX, mouseY);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            this.hovered = clicked(mouseX, mouseY);
            this.renderButton(matrices, mouseX, mouseY, delta);
        }
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.enableBlend();

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int adjustedX = (int) (this.idealX * scaledWidth);
        int adjustedY = (int) (this.idealY * scaledHeight);
        int textX = this.anchor == AnchorLocation.CENTER ? adjustedX - this.x / 2 : (int) (adjustedX - this.x / 1.6);
        int textY = (int) (adjustedY - this.y / 1.5);

        RenderSystem.enableDepthTest();

        int width = this.width;
        int height = this.height;
        if (this.isHovered()) {
            width = this.width + 2;
            height = this.height + 2;
        }
        drawTexture(matrices, adjustedX - this.x, adjustedY - this.y, this.u, this.v, width, height, width, height);

        drawCenteredTextWithShadow(matrices, textRenderer, text.asOrderedText(), textX, textY, this.color);
        if (this.isHovered()) {
            this.renderTooltip(matrices, mouseX, mouseY);
        }
    }

    public enum AnchorLocation {
        CENTER,
        RIGHT
    }
}
