package me.marty.openpixelmon.client.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import me.marty.openpixelmon.client.OpenPixelmonClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

import java.util.function.Supplier;

public class ScalableTexturedButtonWidget extends TexturedButtonWidget {

    private final Supplier<Integer> xSupplier;
    private final Supplier<Integer> ySupplier;
    private final Text text;
    private final AnchorLocation anchor;
    private final int color;
    private final Identifier bgTexture;

    public ScalableTexturedButtonWidget(Supplier<Integer> x, Supplier<Integer> y, int width, int height, Identifier texture, Identifier bgTexture, PressAction pressAction, Text text, int color, AnchorLocation anchor) {
        this(x, y, width, height, texture, bgTexture, pressAction, EMPTY, text, color, anchor);
    }

    public ScalableTexturedButtonWidget(Supplier<Integer> x, Supplier<Integer> y, int width, int height, Identifier texture, Identifier bgTexture, PressAction pressAction, TooltipSupplier tooltipSupplier, Text text, int color, AnchorLocation anchor) {
        super(width, height, width, height, 0, 0, 0, texture, width, height, pressAction, tooltipSupplier, text);
        this.bgTexture = bgTexture;
        this.text = text;
        this.anchor = anchor;
        this.color = color;
        this.xSupplier = x;
        this.ySupplier = y;
        tryFixFiltering();
    }

    private void tryFixFiltering() {
        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
        AbstractTexture abstractTexture = textureManager.getTexture(texture);
        abstractTexture.setFilter(true, false);

        abstractTexture = textureManager.getTexture(bgTexture);
        abstractTexture.setFilter(true, false);
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        int adjustedX = this.xSupplier.get() - this.width;
        int adjustedY = this.ySupplier.get() - this.height + 3;

        return mouseX >= adjustedX && mouseY >= adjustedY && mouseX < adjustedX + this.width && mouseY < adjustedY + this.height - 8;
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
            matrices.push();
            this.hovered = clicked(mouseX, mouseY);
            this.renderButton(matrices, mouseX, mouseY, delta);
            matrices.pop();
        }
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.setShaderTexture(1, this.bgTexture);
        RenderSystem.enableBlend();

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int adjustedX = this.xSupplier.get();
        int adjustedY = this.ySupplier.get();
        int textX = this.anchor == AnchorLocation.CENTER ? adjustedX - this.x / 2 : (int) (adjustedX - this.x / 1.6);
        int textY = (int) (adjustedY - this.y / 1.5);

        int width = this.width;
        int height = this.height;
        if (this.isHovered()) {
            width = this.width + 1;
            height = this.height + 1;
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
