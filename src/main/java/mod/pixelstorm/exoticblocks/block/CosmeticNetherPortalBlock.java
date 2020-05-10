package mod.pixelstorm.exoticblocks.block;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CosmeticNetherPortalBlock extends TransparentBlock
{
	public CosmeticNetherPortalBlock(Block.Settings settings)
	{
		super(settings);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if(random.nextInt(100) == 0)
			world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);

		for(int i = 0; i < 4; ++i)
		{
			int xScalar = random.nextInt(2) * 2 - 1;
			double x = pos.getX() + 0.5D + 0.25D * xScalar;
			double velX = random.nextFloat() * 2.0F * xScalar;

			double y = pos.getY() + random.nextFloat();
			double velY = (random.nextFloat() - 0.5D) * 0.5D;

			int zScalar = random.nextInt(2) * 2 - 1;
			double z = pos.getZ() + 0.5D + 0.25D * zScalar;
			double velZ = random.nextFloat() * 2.0F * zScalar;

			world.addParticle(ParticleTypes.PORTAL, x, y, z, velX, velY, velZ);
		}
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean canSuffocate(BlockState state, BlockView view, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean isSimpleFullBlock(BlockState state, BlockView view, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean allowsSpawning(BlockState state, BlockView view, BlockPos pos, EntityType type)
	{
		return false;
	}
}
