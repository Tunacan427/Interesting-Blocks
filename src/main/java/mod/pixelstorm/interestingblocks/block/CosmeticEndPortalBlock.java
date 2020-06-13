package mod.pixelstorm.interestingblocks.block;

import java.util.Random;
import mod.pixelstorm.interestingblocks.block.entity.CosmeticEndPortalBlockEntity;
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

		Random random = world.random;

		for(int i = 0; i < 24; ++i)
		{
			// Spawn particles at random points within the block's bounds
			double x = random.nextDouble();
			double y = random.nextDouble();
			double z = random.nextDouble();

			double scalar = 0.2;

			// Random velocities pointing away from block center
			double velX = (x - 0.5) * random.nextDouble() * scalar;
			double velY = (y - 0.5) * random.nextDouble() * scalar;
			double velZ = (z - 0.5) * random.nextDouble() * scalar;

			world.addParticle(ParticleTypes.SMOKE, x + pos.getX(), y + pos.getY(), z + pos.getZ(), velX, velY, velZ);
		}
	}
}
