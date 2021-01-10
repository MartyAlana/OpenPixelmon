package me.marty.openpixelmon.block.misc;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class AcornBush extends Block implements Fertilizable {

	public static final IntProperty GROWN = IntProperty.of("grown", 1, getMaxAge());
	private final Item dropItem;

	public AcornBush(Item dropItem) {
		super(FabricBlockSettings.of(Material.LEAVES).nonOpaque());
		this.dropItem = dropItem;
		setDefaultState(getStateManager().getDefaultState().with(GROWN, 1));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(GROWN);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.up()).isAir() && world.getBlockState(pos).isAir() && world.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (state.get(GROWN) == 3) {
			world.setBlockState(pos, state.with(GROWN, 1));
			player.giveItemStack(new ItemStack(dropItem));
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0,0,0, 1, 2, 1);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getBaseLightLevel(pos, 0) >= 9) {
			int i = this.getAge(state);
			if (i < getMaxAge()) {
				if (random.nextInt((int) (25.0F / 4) + 1) == 0) {
					world.setBlockState(pos, this.getDefaultState().with(GROWN, i + 1), 2);
				}
			}
		}
	}

	private static int getMaxAge() {
		return 3;
	}

	private int getAge(BlockState state) {
		return state.get(GROWN);
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return getAge(state) != getMaxAge();
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return isFertilizable(world, pos, state, world.isClient());
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state.with(GROWN, getAge(state) + 1));
	}
}
