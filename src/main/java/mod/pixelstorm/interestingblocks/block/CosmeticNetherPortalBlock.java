package mod.pixelstorm.interestingblocks.block;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CosmeticNetherPortalBlock extends TransparentBlock
{
	public static final FabricBlockSettings DEFAULT_SETTINGS = FabricBlockSettings.of(Material.PORTAL).lightLevel(11).strength(0.8F).noCollision().sounds(BlockSoundGroup.GLASS);

	public CosmeticNetherPortalBlock()
	{
		super(DEFAULT_SETTINGS);
	}

	public CosmeticNetherPortalBlock(Block.Settings settings)
	{
		super(settings);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if(random.nextInt(100) == 0)
			world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);

		for(int i = 0; i < 4; ++i)
		{
			int xScalar = random.nextInt(2) * 2 - 1;
			double x = pos.getX() + 0.5 + 0.25 * xScalar;
			double velX = random.nextFloat() * 2.0F * xScalar;

			double y = pos.getY() + random.nextFloat();
			double velY = (random.nextFloat() - 0.5) * 0.5;

			int zScalar = random.nextInt(2) * 2 - 1;
			double z = pos.getZ() + 0.5 + 0.25 * zScalar;
			double velZ = random.nextFloat() * 2.0F * zScalar;

			world.addParticle(ParticleTypes.PORTAL, x, y, z, velX, velY, velZ);
		}
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

			world.addParticle(ParticleTypes.DRAGON_BREATH, x + pos.getX(), y + pos.getY(), z + pos.getZ(), velX * 0.05, velY * 0.05, velZ * 0.05);
		}
	}
}
