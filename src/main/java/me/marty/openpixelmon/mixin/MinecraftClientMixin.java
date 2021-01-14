package me.marty.openpixelmon.mixin;

import me.marty.openpixelmon.sound.Sounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MinecraftClient.class, priority = 999)
public class MinecraftClientMixin {

	@Inject(method = "getMusicType", at = @At("RETURN"), cancellable = true)
	private void getPixelmonMusicType(CallbackInfoReturnable<MusicSound> cir) {
		if (cir.getReturnValue().getSound() == SoundEvents.MUSIC_GAME) {
			cir.setReturnValue(Sounds.AMBIENT_1_LOOP);
		}
	}
}
