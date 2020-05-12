package mod.pixelstorm.exoticblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;

public class HardlightBlock extends Block
{
	public HardlightBlock()
	{
		super(FabricBlockSettings.of(Material.STONE).strength(1.8F).lightLevel(3));
	}

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
