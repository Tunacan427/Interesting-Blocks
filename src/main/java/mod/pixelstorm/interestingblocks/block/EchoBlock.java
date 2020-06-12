package mod.pixelstorm.interestingblocks.block;

import java.util.Random;
import mod.pixelstorm.interestingblocks.block.entity.EchoBlockEntity;
import mod.pixelstorm.interestingblocks.client.render.model.EchoBlockBakedModel;
import mod.pixelstorm.interestingblocks.client.render.model.EchoBlockSpriteManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractGlassBlock;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class EchoBlock extends AbstractGlassBlock
{
	public static final FabricBlockSettings DEFAULT_SETTINGS = FabricBlockSettings.of(Material.GLASS).strength(0.8F).nonOpaque().lightLevel(3).sounds(BlockSoundGroup.GLASS);

	public EchoBlock()
	{
		super(DEFAULT_SETTINGS);
	}

	public EchoBlock(Block.Settings settings)
	{
		super(settings);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		if(world.isClient())
			EchoBlockSpriteManager.updatePos(state, pos, world);

		return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
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
}
