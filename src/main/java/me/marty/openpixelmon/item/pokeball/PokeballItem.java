package me.marty.openpixelmon.item.pokeball;

import me.marty.openpixelmon.entity.pokeball.PokeballEntity;
import me.marty.openpixelmon.item.OpenPixelmonItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PokeballItem extends Item {

	public PokeballItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.6F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!world.isClient()) {
			PokeballEntity pokeball = new PokeballEntity(user, world, this);
			pokeball.setProperties(user, user.pitch, user.yaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(pokeball);
			user.getStackInHand(hand).decrement(1);
			return TypedActionResult.success(user.getStackInHand(hand));
		}
		return super.use(world, user, hand);
	}
}
