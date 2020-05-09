package mod.pixelstorm.exoticblocks.client.render.block.entity;

import mod.pixelstorm.exoticblocks.ExoticBlocks;
import mod.pixelstorm.exoticblocks.block.entity.CosmeticEndPortalBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.logging.log4j.Level;

public class CosmeticEndPortalBlockEntityRenderer extends EndPortalBlockEntityRenderer<CosmeticEndPortalBlockEntity>
{
	public CosmeticEndPortalBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	protected float method_3594()
	{
		// Height?
		return 1;
	}
}
