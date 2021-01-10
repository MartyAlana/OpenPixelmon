package me.marty.openpixelmon.entity.data;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.Arrays;
import java.util.Objects;

public class Party {
	private static final CompoundTag NULL_TAG = Util.make(new CompoundTag(), tag -> tag.putBoolean("Null", true));
	private final PartyEntry[] entries = new PartyEntry[6];

	public PartyEntry[] getEntries() {
		return entries;
	}

	public void writeToTag(CompoundTag tag) {
		ListTag list = new ListTag();
		for (PartyEntry entry : entries) {
			if (entry == null) {
				list.add(NULL_TAG);
			} else {
				list.add(entry.writeToTag(new CompoundTag()));
			}
		}
		tag.put("Party", list);
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

	public int partySize() {
		return (int) Arrays.stream(entries).filter(Objects::nonNull).count();
	}

	public void add(ServerPlayerEntity player, PartyEntry partyEntry) {
		boolean couldFitInPokemon = false;
		for (int i = 0; i < entries.length; i++) {
			if(entries[i] == null && !couldFitInPokemon) {
				entries[i] = partyEntry;
				player.sendMessage(new TranslatableText("text.open_pixelmon.caught").formatted(Formatting.GRAY, Formatting.ITALIC), false);
				couldFitInPokemon = true;
			}
		}
		if(!couldFitInPokemon) {
			player.sendMessage(new TranslatableText("text.open_pixelmon.caught_pc").formatted(Formatting.GRAY, Formatting.ITALIC), false);
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