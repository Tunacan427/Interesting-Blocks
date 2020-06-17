package mod.pixelstorm.interestingblocks;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.client.render.block.entity.*;
import mod.pixelstorm.interestingblocks.client.texture.SkyboxBlockTexture;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class InterestingBlocksClient implements ClientModInitializer
{
	private static Map<Item, RenderLayer> itemRenderLayerMap = new HashMap<Item, RenderLayer>();

	@Override
	public void onInitializeClient()
	{
		registerBlockEntityRenderer("cosmetic_end_portal_block", CosmeticEndPortalBlockEntityRenderer::new);

		putBlockRenderLayer("cosmetic_nether_portal_block", RenderLayer.getTranslucent());
		putBlockRenderLayer("echo_block", RenderLayer.getTranslucent());
		putBlockRenderLayer("skybox_block", SkyboxBlockTexture.RENDERLAYER);

		putItemRenderLayer("skybox_block", SkyboxBlockTexture.RENDERLAYER);
	}

	public static void putItemRenderLayer(Item item, RenderLayer layer)
	{
		itemRenderLayerMap.put(item, layer);
	}

	public static RenderLayer getItemRenderLayer(Item item)
	{
		return itemRenderLayerMap.get(item);
	}

	private <E extends BlockEntity> void registerBlockEntityRenderer(String identifier, Function<BlockEntityRenderDispatcher, BlockEntityRenderer<E>> blockEntityRenderer)
	{
		BlockEntityRendererRegistry.INSTANCE.register((BlockEntityType<E>) Registry.BLOCK_ENTITY_TYPE.get(InterestingBlocks.getId(identifier)), blockEntityRenderer);
	}

	private void putBlockRenderLayer(String identifier, RenderLayer layer)
	{
		BlockRenderLayerMap.INSTANCE.putBlock(Registry.BLOCK.get(InterestingBlocks.getId(identifier)), layer);
	}

	private void putItemRenderLayer(String identifier, RenderLayer layer)
	{
		putItemRenderLayer(Registry.ITEM.get(InterestingBlocks.getId(identifier)), layer);
	}
}
