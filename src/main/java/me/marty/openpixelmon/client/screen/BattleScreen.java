package me.marty.openpixelmon.client.screen;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.client.screen.widget.ScalableTexturedButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class BattleScreen extends Screen {

    public static final Identifier ATTACK_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/attack_button.png");
    public static final Identifier PIXELMON_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/pixelmon_button.png");
    public static final Identifier BAG_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/bag_button.png");
    public static final Identifier RUN_TEXTURE = OpenPixelmon.id("textures/gui/ingame/battle/run_button.png");

    public BattleScreen() {
        super(new TranslatableText("screen.pixelmon.battle"));
        MinecraftClient client = MinecraftClient.getInstance();
    }

    @Override
    protected void init() {
        this.addDrawableChild(new ScalableTexturedButtonWidget(0.96f, 0.96f - (3 / 10f), 100, 25, ATTACK_TEXTURE, button -> System.out.println("E"), text("text.pixelmon.battle", 0xff3f46), 0xFF463FFF, ScalableTexturedButtonWidget.AnchorLocation.RIGHT));
        this.addDrawableChild(new ScalableTexturedButtonWidget(0.96f, 0.96f - (2 / 10f), 100, 25, PIXELMON_TEXTURE, button -> System.out.println("E"), text("text.pixelmon.pixelmon", 0x87ff91), 0xFF000000, ScalableTexturedButtonWidget.AnchorLocation.RIGHT));
        this.addDrawableChild(new ScalableTexturedButtonWidget(0.96f, 0.96f - (1 / 10f), 100, 25, BAG_TEXTURE, button -> System.out.println("E"), text("text.pixelmon.bag", 0xff8142), 0xFF000000, ScalableTexturedButtonWidget.AnchorLocation.RIGHT));
        this.addDrawableChild(new ScalableTexturedButtonWidget(0.96f, 0.96f, 100, 25, RUN_TEXTURE, button -> System.out.println("E"), text("text.pixelmon.run", 0x56bbff), 0xFF463FFF, ScalableTexturedButtonWidget.AnchorLocation.RIGHT));
    }

    public static TranslatableText text(String translation, int rgb) {
        return (TranslatableText) new TranslatableText(translation)
                .styled(style -> style.withColor(rgb));
    }
}
