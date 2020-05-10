package mod.pixelstorm.exoticblocks.block;

import java.util.Random;
import mod.pixelstorm.exoticblocks.block.entity.CosmeticEndPortalBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CosmeticEndPortalBlock extends BlockWithEntity
{
	public CosmeticEndPortalBlock(Settings settings)
	{
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView blockView)
	{
		return new CosmeticEndPortalBlockEntity();
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		super.onBreak(world, pos, state, player);

		if(!world.isClient)
			return;

		for(int i = 0; i < 24; ++i)
		{
			// Spawn particles at random points within the block's bounds
			double x = pos.getX() + world.random.nextDouble();
			double y = pos.getY() + world.random.nextDouble();
			double z = pos.getZ() + world.random.nextDouble();

			// Random velocities in range of -1 to +1
			double velX = world.random.nextDouble() * 2 - 1;
			double velY = world.random.nextDouble() * 2 - 1;
			double velZ = world.random.nextDouble() * 2 - 1;

			world.addParticle(ParticleTypes.SMOKE, x, y, z, velX * 0.1, velY * 0.1, velZ * 0.1);
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		// TODO: Fix
		// double x = pos.getX() + random.nextFloat();
		// double y = pos.getY() + 0.8D;
		// double z = pos.getZ() + random.nextFloat();
		// world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
	}
}
