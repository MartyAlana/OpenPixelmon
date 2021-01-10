package me.marty.openpixelmon.api.player;

import me.marty.openpixelmon.api.pc.PcBox;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;

import java.util.List;

public interface PixelmonPlayer {

	int getPP();

	List<PixelmonEntity> getParty();

	List<PcBox> getPcBoxes();

	void setPP(int pp);

	void givePixelmon(PixelmonEntity entity);
}
