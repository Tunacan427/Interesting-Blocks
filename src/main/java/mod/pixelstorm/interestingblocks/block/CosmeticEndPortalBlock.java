package mod.pixelstorm.interestingblocks.block;

import java.util.Random;
import mod.pixelstorm.interestingblocks.block.entity.CosmeticEndPortalBlockEntity;
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

public class CosmeticEndPortalBlock extends BlockWithEntity
{
	public static final FabricBlockSettings DEFAULT_SETTINGS = FabricBlockSettings.of(Material.PORTAL).lightLevel(15).strength(0.8F).sounds(BlockSoundGroup.WOOL);

	public CosmeticEndPortalBlock()
	{
		super(DEFAULT_SETTINGS);
	}

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
			double x = world.random.nextDouble();
			double y = world.random.nextDouble();
			double z = world.random.nextDouble();

			// Random velocities pointing away from block center
			double velX = (x - 0.5) * world.random.nextDouble();
			double velY = (y - 0.5) * world.random.nextDouble();
			double velZ = (z - 0.5) * world.random.nextDouble();

			world.addParticle(ParticleTypes.SMOKE, x + pos.getX(), y + pos.getY(), z + pos.getZ(), velX * 0.2, velY * 0.2, velZ * 0.2);
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
