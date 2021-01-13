package me.marty.openpixelmon.mixin;

import me.marty.openpixelmon.sound.Sounds;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.AmbientSoundLoops;
import net.minecraft.client.sound.AmbientSoundPlayer;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AmbientSoundPlayer.class)
public class AmbientSoundPlayerMixin {

	@Shadow private int ticksUntilPlay;

	@Shadow @Final private ClientPlayerEntity player;

	@Shadow @Final private SoundManager soundManager;

	@Inject(method = "tick", at = @At("TAIL"))
	private void tickPixelmonSounds(CallbackInfo ci) {
		if (this.ticksUntilPlay <= 0) {
			float f = this.player.world.random.nextFloat();
			if (f < 0.01F) {
				this.ticksUntilPlay = 0;
				this.soundManager.play(new AmbientSoundLoops.MusicLoop(this.player, Sounds.AMBIENT_1));
			}
		}
	}
}
