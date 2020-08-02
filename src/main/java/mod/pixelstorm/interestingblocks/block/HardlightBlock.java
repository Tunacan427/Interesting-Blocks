package mod.pixelstorm.interestingblocks.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class HardlightBlock extends Block
{
	public static final FabricBlockSettings DEFAULT_SETTINGS = FabricBlockSettings.of(Material.GLASS).strength(0.4F).lightLevel(3).sounds(BlockSoundGroup.GLASS).emissiveLighting((state, world, pos) -> true);

	public HardlightBlock()
	{
		super(DEFAULT_SETTINGS);
	}

	public HardlightBlock(Settings settings)
	{
		super(settings);
	}
/*
	@Override
	@Environment(EnvType.CLIENT)
	public boolean hasEmissiveLighting(BlockView world, BlockPos pos) { return true; }*/
}
