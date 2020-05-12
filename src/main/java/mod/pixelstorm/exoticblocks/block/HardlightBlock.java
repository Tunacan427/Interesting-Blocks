package mod.pixelstorm.exoticblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class HardlightBlock extends Block
{
	public static final FabricBlockSettings DEFAULT_SETTINGS = FabricBlockSettings.of(Material.STONE).strength(1.8F).lightLevel(3).sounds(BlockSoundGroup.GLASS);

	public HardlightBlock()
	{
		super(DEFAULT_SETTINGS);
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
