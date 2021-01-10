package me.marty.openpixelmon.entity.data;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Util;

import java.util.Arrays;

public class Party {
	private static final CompoundTag NULL_TAG = Util.make(new CompoundTag(), tag -> tag.putBoolean("Null", true));
	private final PartyEntry[] entries = new PartyEntry[6];

	public PartyEntry[] getEntries() {
		return entries;
	}

	public CompoundTag writeToTag(CompoundTag tag) {
		ListTag list = new ListTag();
		for (PartyEntry entry : entries) {
			if (entry == null) {
				list.add(NULL_TAG);
			} else {
				list.add(entry.writeToTag(new CompoundTag()));
			}
		}
		tag.put("Party", list);
		return tag;
	}

	public void readFromTag(CompoundTag tag) {
		ListTag list = tag.getList("Party", NbtType.COMPOUND);
		for (int i = 0; i < list.size(); i++) {
			Tag t = list.get(i);
			CompoundTag compoundTag = (CompoundTag) t;
			if (!compoundTag.equals(NULL_TAG)) {
				this.entries[i] = PartyEntry.readFromTag(compoundTag);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Party party = (Party) o;
		return Arrays.equals(entries, party.entries);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(entries);
	}
}