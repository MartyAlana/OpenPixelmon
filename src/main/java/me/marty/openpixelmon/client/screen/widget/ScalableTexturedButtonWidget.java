package me.marty.openpixelmon.client.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import me.marty.openpixelmon.client.OpenPixelmonClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class ScalableTexturedButtonWidget extends TexturedButtonWidget {

    private final float idealX;
    private final float idealY;
    private final Text text;
    private final AnchorLocation anchor;
    private final int color;
    private final Identifier bgTexture;

    public ScalableTexturedButtonWidget(float x, float y, int width, int height, Identifier texture, Identifier bgTexture, PressAction pressAction, Text text, int color, AnchorLocation anchor) {
        this(x, y, width, height, texture, bgTexture, pressAction, EMPTY, text, color, anchor);
    }

    public ScalableTexturedButtonWidget(float x, float y, int width, int height, Identifier texture, Identifier bgTexture, PressAction pressAction, TooltipSupplier tooltipSupplier, Text text, int color, AnchorLocation anchor) {
        super(anchor == AnchorLocation.CENTER ? width / 2 : width, anchor == AnchorLocation.CENTER ? height / 2 : height, width, height, 0, 0, 0, texture, width, height, pressAction, tooltipSupplier, text);
        this.bgTexture = bgTexture;
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
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.setShaderTexture(1, this.bgTexture);
        RenderSystem.enableBlend();

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int adjustedX = (int) (this.idealX * scaledWidth);
        int adjustedY = (int) (this.idealY * scaledHeight);
        int textX = this.anchor == AnchorLocation.CENTER ? adjustedX - this.x / 2 : (int) (adjustedX - this.x / 1.6);
        int textY = (int) (adjustedY - this.y / 1.5);

        int width = this.width;
        int height = this.height;
        if (this.isHovered()) {
            width = this.width + 2;
            height = this.height + 2;
        }
        drawBattleTexture(matrices, adjustedX - this.x, adjustedY - this.y, width, height, this.u, this.v);

        drawCenteredTextWithShadow(matrices, textRenderer, text.asOrderedText(), textX, textY, this.color);
        if (this.isHovered()) {
            this.renderTooltip(matrices, mouseX, mouseY);
        }
    }

    public static void drawBattleTexture(MatrixStack matrices, int x, int y, int width, int height, float u, float v) {
        drawTexture(matrices, x, x + width, y, y + height, width, height, u, v, width, height);
    }

    private static void drawTexture(MatrixStack matrices, int x0, int x1, int y0, int y1, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight) {
        drawTexturedQuad(matrices.peek().getPositionMatrix(), x0, x1, y0, y1, (u + 0.0F) / (float) textureWidth, (u + (float) regionWidth) / (float) textureWidth, (v + 0.0F) / (float) textureHeight, (v + (float) regionHeight) / (float) textureHeight);
    }

    private static void drawTexturedQuad(Matrix4f matrix, int x0, int x1, int y0, int y1, float u0, float u1, float v0, float v1) {
        RenderSystem.setShader(() -> OpenPixelmonClient.battleButtonShader);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix, x0, y1, 0).texture(u0, v1).next();
        bufferBuilder.vertex(matrix, x1, y1, 0).texture(u1, v1).next();
        bufferBuilder.vertex(matrix, x1, y0, 0).texture(u1, v0).next();
        bufferBuilder.vertex(matrix, x0, y0, 0).texture(u0, v0).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
    }

    public enum AnchorLocation {
        CENTER, RIGHT
    }
}
