package mod.pixelstorm.interestingblocks;

import java.util.function.Function;
import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.block.entity.CosmeticEndPortalBlockEntity;
import mod.pixelstorm.interestingblocks.client.render.block.entity.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import org.apache.logging.log4j.Level;

public class InterestingBlocksClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		BlockEntityRendererRegistry.INSTANCE.register(CosmeticEndPortalBlockEntity.class, new CosmeticEndPortalBlockEntityRenderer());

		InterestingBlocks.log(Level.INFO, "Finished client initialization.");
	}
}
