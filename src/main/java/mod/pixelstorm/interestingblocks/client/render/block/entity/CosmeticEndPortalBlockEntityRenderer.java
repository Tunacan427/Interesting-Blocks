package mod.pixelstorm.interestingblocks.client.render.block.entity;

import mod.pixelstorm.interestingblocks.block.entity.CosmeticEndPortalBlockEntity;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public class CosmeticEndPortalBlockEntityRenderer extends EndPortalBlockEntityRenderer<CosmeticEndPortalBlockEntity>
{
	public CosmeticEndPortalBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	protected float method_3594()
	{
		// Height of top face
		return 1;
	}
}
