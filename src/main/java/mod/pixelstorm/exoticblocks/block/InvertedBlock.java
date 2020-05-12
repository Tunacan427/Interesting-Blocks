package mod.pixelstorm.exoticblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.TransparentBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class InvertedBlock extends TransparentBlock
{
	public static final FabricBlockSettings DEFAULT_SETTINGS = FabricBlockSettings.of(Material.STONE).strength(1.8F).nonOpaque();

	public InvertedBlock()
	{
		super(DEFAULT_SETTINGS);
	}

	public InvertedBlock(Settings settings)
	{
		super(settings);
	}

	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos) {
		return 1.0F;
	}
}
