package me.martyalana.openpixelmon.api.player;

import me.martyalana.openpixelmon.api.pc.PcBox;
import me.martyalana.openpixelmon.entity.pixelmon.PixelmonEntity;

import java.util.List;

public interface PixelmonPlayer {

	int getPP();

	List<PixelmonEntity> getParty();

	List<PcBox> getPcBoxes();

	void setPP(int pp);

	void clearParty();

	void givePixelmon(PixelmonEntity entity);
}
