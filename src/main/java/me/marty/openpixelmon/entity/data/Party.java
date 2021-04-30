package me.marty.openpixelmon.entity.data;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.Arrays;
import java.util.Objects;

public class Party {
	private static final NbtCompound NULL_TAG = Util.make(new NbtCompound(), tag -> tag.putBoolean("Null", true));
	private final PartyEntry[] entries = new PartyEntry[6];

	public PartyEntry[] getEntries() {
		return entries;
	}

	public void writeToTag(NbtCompound tag) {
	    NbtList list = new NbtList();
		for (PartyEntry entry : entries) {
			if (entry == null) {
				list.add(NULL_TAG);
			} else {
				list.add(entry.writeToTag(new NbtCompound()));
			}
		}
		tag.put("Party", list);
	}

	public void readFromTag(NbtCompound tag) {
        NbtList list = tag.getList("Party", NbtType.COMPOUND);
		for (int i = 0; i < list.size(); i++) {
            NbtElement t = list.get(i);
			NbtCompound NbtCompound = (NbtCompound) t;
			if (!NbtCompound.equals(NULL_TAG)) {
				this.entries[i] = PartyEntry.readFromTag(NbtCompound);
			}
		}
	}

	public int partySize() {
		return (int) Arrays.stream(entries).filter(Objects::nonNull).count();
	}

	public void add(ServerPlayerEntity player, PartyEntry partyEntry) {
		boolean couldFitInpixelmon = false;
		for (int i = 0; i < entries.length; i++) {
			if (entries[i] == null && !couldFitInpixelmon) {
				entries[i] = partyEntry;
				player.sendMessage(new TranslatableText("dialogue.pixelmon.caught", partyEntry.getName()).formatted(Formatting.GRAY, Formatting.ITALIC), false);
				couldFitInpixelmon = true;
			}
		}
		if (!couldFitInpixelmon) {
			player.sendMessage(new TranslatableText("dialogue.pixelmon.sent_to_pc", partyEntry.getName()).formatted(Formatting.GRAY, Formatting.ITALIC), false);
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