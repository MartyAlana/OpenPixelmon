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
	private final DefaultedList<ItemStack> pokemonItemInventory = DefaultedList.ofSize(6, ItemStack.EMPTY);

	@Override
	public boolean shouldSyncWith(ServerPlayerEntity player) {
		return true; // TODO
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		CompoundTag inv = tag.getCompound("Inventory");
		party.readFromTag(tag);
		Inventories.fromTag(tag, pokemonItemInventory);
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		party.writeToTag(tag);
		CompoundTag inv = new CompoundTag();
		Inventories.toTag(inv, pokemonItemInventory);
		tag.put("Inventory", inv);
	}

	public Party getParty() {
		return party;
	}

	public DefaultedList<ItemStack> getPokemonItemInventory() {
		return pokemonItemInventory;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PartyComponent that = (PartyComponent) o;
		return Objects.equals(party, that.party) && Objects.equals(pokemonItemInventory.delegate, that.pokemonItemInventory.delegate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(party, pokemonItemInventory);
	}
}