package me.marty.openpixelmon.client.screen;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.client.screen.widget.ScalableTexturedButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class BattleScreen extends Screen {

    public static final Identifier BUTTON_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/battle_button.png");

    public BattleScreen() {
        super(new TranslatableText("screen.pixelmon.battle"));
        MinecraftClient client = MinecraftClient.getInstance();
    }

    @Override
    protected void init() {
        this.addDrawable(new ScalableTexturedButtonWidget(0.96f, 0.96f, 128, 32, BUTTON_TEXTURE, button -> System.out.println("E"), new TranslatableText("E"), ScalableTexturedButtonWidget.AnchorLocation.RIGHT));
    }
}
