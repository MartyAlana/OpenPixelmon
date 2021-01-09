package me.martyalana.openpixelmon.item.pokeball;

import me.martyalana.openpixelmon.entity.pokeball.AbstractPokeballEntity;
import me.martyalana.openpixelmon.entity.pokeball.PokeballEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PokeballItem extends Item {

	public PokeballItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient()) {
			AbstractPokeballEntity pokeball = new PokeballEntity(world, user);
			pokeball.setProperties(user, user.pitch, user.yaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(pokeball);
			return TypedActionResult.success(user.getStackInHand(hand));
		}
		return super.use(world, user, hand);
	}
}
