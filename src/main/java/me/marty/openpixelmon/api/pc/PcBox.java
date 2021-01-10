package me.marty.openpixelmon.api.pc;

import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;

import java.util.ArrayList;
import java.util.List;

public class PcBox {

	public final String displayName;
	public final List<PixelmonEntity> pixelmon = new ArrayList<>();

	public PcBox(String displayName) {
		this.displayName = displayName;
	}
}
