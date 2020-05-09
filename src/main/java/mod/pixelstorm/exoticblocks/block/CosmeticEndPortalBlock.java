package mod.pixelstorm.exoticblocks.block;

import java.util.Random;
import mod.pixelstorm.exoticblocks.block.entity.CosmeticEndPortalBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
