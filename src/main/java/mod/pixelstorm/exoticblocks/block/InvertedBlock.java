package mod.pixelstorm.exoticblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.TransparentBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class InvertedBlock extends TransparentBlock
{
	public InvertedBlock(Settings settings)
	{
		super(settings);
	}

	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos) {
		return 1.0F;
	}
}
