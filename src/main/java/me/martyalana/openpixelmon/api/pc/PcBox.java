package me.martyalana.openpixelmon.api.pc;

import me.martyalana.openpixelmon.entity.pixelmon.PixelmonEntity;

import java.util.ArrayList;
import java.util.List;

public class PcBox {

	public final String displayName;
	public final List<PixelmonEntity> pixelmon = new ArrayList<>();

	public PcBox(String displayName) {
		this.displayName = displayName;
	}
}
