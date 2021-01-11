package me.marty.openpixelmon.api.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import me.marty.openpixelmon.entity.data.Party;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;

import java.util.Objects;

public class PartyComponent implements ComponentV3, AutoSyncedComponent {
	private final Party party = new Party();
	private final DefaultedList<ItemStack> pixelmonItemInventory = DefaultedList.ofSize(6, ItemStack.EMPTY);

	@Override
	public boolean shouldSyncWith(ServerPlayerEntity player) {
		return true; // TODO
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		CompoundTag inv = tag.getCompound("Inventory");
		party.readFromTag(tag);
		Inventories.fromTag(tag, pixelmonItemInventory);
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		party.writeToTag(tag);
		CompoundTag inv = new CompoundTag();
		Inventories.toTag(inv, pixelmonItemInventory);
		tag.put("Inventory", inv);
	}

	public Party getParty() {
		return party;
	}

	public DefaultedList<ItemStack> getpixelmonItemInventory() {
		return pixelmonItemInventory;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PartyComponent that = (PartyComponent) o;
		return Objects.equals(party, that.party) && Objects.equals(pixelmonItemInventory.delegate, that.pixelmonItemInventory.delegate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(party, pixelmonItemInventory);
	}
}