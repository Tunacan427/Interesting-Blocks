package mod.pixelstorm.interestingblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public class HardlightBlock extends Block
{
	public static final FabricBlockSettings DEFAULT_SETTINGS = FabricBlockSettings.of(Material.GLASS).strength(0.4F, 0.4F).lightLevel(3).sounds(BlockSoundGroup.GLASS);

	public HardlightBlock()
	{
		super(DEFAULT_SETTINGS.build());
	}

	public HardlightBlock(Settings settings)
	{
		super(settings);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public int getBlockBrightness(BlockState state, BlockRenderView view, BlockPos pos)
	{
		return 15728880;
	}
}
