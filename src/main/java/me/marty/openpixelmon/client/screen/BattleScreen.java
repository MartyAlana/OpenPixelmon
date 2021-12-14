package me.marty.openpixelmon.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.client.screen.widget.ScalableTexturedButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class BattleScreen extends Screen {

    public static final Identifier POKEBALL_BG_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/pokeball_bg.png");
    public static final Identifier BAG_BG_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/bag_bg.png");
    public static final Identifier ATTACK_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/attack_button.png");
    public static final Identifier PIXELMON_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/pixelmon_button.png");
    public static final Identifier BAG_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/bag_button.png");
    public static final Identifier RUN_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/run_button.png");

    public BattleScreen() {
        super(new TranslatableText("screen.pixelmon.battle"));
    }

    @Override
    protected void init() {
        createActionButton(66, ATTACK_TEXTURE, POKEBALL_BG_TEXTURE, "text.pixelmon.battle", 0xff3f46);
        createActionButton(44, PIXELMON_TEXTURE, POKEBALL_BG_TEXTURE, "text.pixelmon.pixelmon", 0x87ff91);
        createActionButton(22, BAG_TEXTURE, BAG_BG_TEXTURE, "text.pixelmon.bag", 0xff8142);
        createActionButton(0, RUN_TEXTURE, BAG_BG_TEXTURE, "text.pixelmon.run", 0x56bbff);
    }

    private void createActionButton(int actionOffset, Identifier buttonTexture, Identifier bgTexture, String text, int color) {
        this.addDrawableChild(new ScalableTexturedButtonWidget(() -> this.width - 1, () -> this.height - actionOffset, 100, 25, buttonTexture, bgTexture, button -> System.out.println("Pixelmon"), text(text, color), 0xFF000000, ScalableTexturedButtonWidget.AnchorLocation.RIGHT));
    }

    public static TranslatableText text(String translation, int rgb) {
        return (TranslatableText) new TranslatableText(translation).styled(style -> style.withColor(rgb));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
