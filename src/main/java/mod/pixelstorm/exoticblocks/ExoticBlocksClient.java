package mod.pixelstorm.exoticblocks;

import mod.pixelstorm.exoticblocks.ExoticBlocks;
import mod.pixelstorm.exoticblocks.block.entity.CosmeticEndPortalBlockEntity;
import mod.pixelstorm.exoticblocks.client.render.block.entity.CosmeticEndPortalBlockEntityRenderer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import org.apache.logging.log4j.Level;

public class ExoticBlocksClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		BlockEntityRendererRegistry.INSTANCE.register((BlockEntityType<CosmeticEndPortalBlockEntity>) Registry.BLOCK_ENTITY_TYPE.get(new Identifier(ExoticBlocks.MOD_ID, "cosmetic_end_portal_block")), CosmeticEndPortalBlockEntityRenderer::new);
		ExoticBlocks.log(Level.INFO, "Finished client initialization.");
	}
}