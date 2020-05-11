package mod.pixelstorm.exoticblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class HardlightBlock extends Block
{
	public HardlightBlock(Settings settings)
	{
		super(settings);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean hasEmissiveLighting(BlockState state)
	{
		return true;
	}
}