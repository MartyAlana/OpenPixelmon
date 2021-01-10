package me.marty.openpixelmon.item.pokeball;

import me.marty.openpixelmon.entity.pokeball.AbstractPokeballEntity;
import me.marty.openpixelmon.entity.pokeball.GreatballEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GreatballItem extends PokeballItem{

	public GreatballItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient()) {
			AbstractPokeballEntity pokeball = new GreatballEntity(world, user);
			pokeball.setProperties(user, user.pitch, user.yaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(pokeball);
			return TypedActionResult.success(user.getStackInHand(hand));
		}
		return super.use(world, user, hand);
	}
}
