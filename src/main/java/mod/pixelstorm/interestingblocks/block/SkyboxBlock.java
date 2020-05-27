package mod.pixelstorm.interestingblocks.block;

import mod.pixelstorm.interestingblocks.block.entity.SkyboxBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SkyboxBlock extends BlockWithEntity
{
	public static final FabricBlockSettings DEFAULT_SETTINGS = FabricBlockSettings.of(Material.WOOL).strength(0.8F).sounds(BlockSoundGroup.WOOL);

	public SkyboxBlock()
	{
		super(DEFAULT_SETTINGS);
	}

	public SkyboxBlock(Settings settings)
	{
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView blockView)
	{
		return new SkyboxBlockEntity();
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		super.onBreak(world, pos, state, player);

		if(!world.isClient)
			return;

		for(int i = 0; i < 8; ++i)
		{
			// Spawn particles at random points within the block's bounds
			double x = world.random.nextDouble();
			double y = world.random.nextDouble();
			double z = world.random.nextDouble();

			// Random velocities pointing away from block center
			double velX = (x - 0.5) * world.random.nextDouble();
			double velY = (y - 0.5) * world.random.nextDouble();
			double velZ = (z - 0.5) * world.random.nextDouble();

			world.addParticle(ParticleTypes.END_ROD, x + pos.getX(), y + pos.getY(), z + pos.getZ(), velX * 0.1, velY * 0.1, velZ * 0.1);
		}
	}
}
