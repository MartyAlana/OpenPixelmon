package me.martyalana.openpixelmon.mixin;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.api.pc.PcBox;
import me.martyalana.openpixelmon.api.player.PixelmonPlayer;
import me.martyalana.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements PixelmonPlayer {

	private int pp;
	private final List<PixelmonEntity> party = new ArrayList<>();
	private final List<PcBox> pcBoxes = new ArrayList<>();

	@Override
	public int getPP() {
		return pp;
	}

	@Override
	public List<PixelmonEntity> getParty() {
		return party;
	}

	@Override
	public List<PcBox> getPcBoxes() {
		return pcBoxes;
	}

	@Override
	public void setPP(int pp) {
		this.pp = pp;
	}

	@Override
	public void clearParty() {
		this.party.clear();
	}

	@Override
	public void givePixelmon(PixelmonEntity entity) {
		if(party.size() > 5) {
			//TODO: pc logic
			OpenPixelmon.LOGGER.warn("Player did not recieve pokemon in pc due to missing implementation :(");
		} else {
			party.add(entity);
		}
	}
}