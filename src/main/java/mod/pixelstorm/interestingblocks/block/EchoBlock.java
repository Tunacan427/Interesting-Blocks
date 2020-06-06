package mod.pixelstorm.interestingblocks.block;

import java.util.Random;
import mod.pixelstorm.interestingblocks.block.entity.EchoBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EchoBlock extends ConnectedBlock implements BlockEntityProvider
{
	public static final FabricBlockSettings DEFAULT_SETTINGS = FabricBlockSettings.of(Material.GLASS).strength(0.8F).nonOpaque().sounds(BlockSoundGroup.GLASS);

	public EchoBlock()
	{
		super(DEFAULT_SETTINGS);
	}

	public EchoBlock(Block.Settings settings)
	{
		super(settings);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}

	@Override
	public BlockEntity createBlockEntity(BlockView blockView)
	{
		return new EchoBlockEntity();
	}

	@Environment(EnvType.CLIENT)
	@Override
	public boolean isSideInvisible(BlockState state, BlockState neighbor, Direction facing)
	{
		return (neighbor.getBlock() == this) ? true : super.isSideInvisible(state, neighbor, facing);
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		super.onBreak(world, pos, state, player);

		if(!world.isClient)
			return;

		for(int i = 0; i < 12; ++i)
		{
			// Spawn particles at random points within the block's bounds
			double x = world.random.nextDouble();
			double y = world.random.nextDouble();
			double z = world.random.nextDouble();

			// Random velocities pointing away from block center
			double velX = (x - 0.5) * world.random.nextDouble();
			double velY = (y - 0.5) * world.random.nextDouble();
			double velZ = (z - 0.5) * world.random.nextDouble();

			//world.addParticle(ParticleTypes.DRAGON_BREATH, x + pos.getX(), y + pos.getY(), z + pos.getZ(), velX * 0.05, velY * 0.05, velZ * 0.05);
		}
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public boolean onBlockAction(BlockState state, World world, BlockPos pos, int type, int data)
	{
		super.onBlockAction(state, world, pos, type, data);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return (blockEntity == null) ? false : blockEntity.onBlockAction(type, data);
	}

	@Override
	public NameableContainerFactory createContainerFactory(BlockState state, World world, BlockPos pos)
	{
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return (blockEntity instanceof NameableContainerFactory) ? (NameableContainerFactory) blockEntity : null;
	}
}
