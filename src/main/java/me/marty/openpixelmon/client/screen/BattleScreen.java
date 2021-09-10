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
		this.addDrawable(new ScalableTexturedButtonWidget(0.96f, 0.96f - (3 / 7.4f), 128, 32, ATTACK_TEXTURE, button -> System.out.println("E"), new TranslatableText("text.pixelmon.battle"), 0xFF463FFF, ScalableTexturedButtonWidget.AnchorLocation.RIGHT));
		this.addDrawable(new ScalableTexturedButtonWidget(0.96f, 0.96f - (2 / 7.4f), 128, 32, PIXELMON_TEXTURE, button -> System.out.println("E"), new TranslatableText("text.pixelmon.pixelmon"), 0xFF000000, ScalableTexturedButtonWidget.AnchorLocation.RIGHT));
		this.addDrawable(new ScalableTexturedButtonWidget(0.96f, 0.96f - (1 / 7.4f), 128, 32, BAG_TEXTURE, button -> System.out.println("E"), new TranslatableText("text.pixelmon.bag"), 0xFF000000, ScalableTexturedButtonWidget.AnchorLocation.RIGHT));
		this.addDrawable(new ScalableTexturedButtonWidget(0.96f, 0.96f, 128, 32, RUN_TEXTURE, button -> System.out.println("E"), new TranslatableText("text.pixelmon.run"), 0xFF463FFF, ScalableTexturedButtonWidget.AnchorLocation.RIGHT));
    }
}
