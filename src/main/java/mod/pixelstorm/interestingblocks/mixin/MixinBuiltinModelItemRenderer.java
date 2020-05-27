package mod.pixelstorm.interestingblocks.mixin;

import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.client.render.EndPortalRenderer;
import mod.pixelstorm.interestingblocks.client.render.block.entity.SkyboxBlockEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.apache.logging.log4j.Level;

@Mixin(BuiltinModelItemRenderer.class)
public class MixinBuiltinModelItemRenderer
{
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void render(ItemStack itemStack, MatrixStack matrix, VertexConsumerProvider vertexConsumerProvider, int light, int overlay, CallbackInfo callbackInfo)
	{
		if(itemStack.getItem() instanceof BlockItem)
		{
			Block block = ((BlockItem) itemStack.getItem()).getBlock();
			if(block == Registry.BLOCK.get(new Identifier(InterestingBlocks.MOD_ID, "cosmetic_end_portal_block")))
			{
				EndPortalRenderer.render(matrix, vertexConsumerProvider, 0);
				callbackInfo.cancel();
			}
			else if(block == Registry.BLOCK.get(new Identifier(InterestingBlocks.MOD_ID, "skybox_block")))
			{
				SkyboxBlockEntityRenderer.renderBlock(matrix, vertexConsumerProvider, (direction) -> true);
				callbackInfo.cancel();
			}
		}
	}
}
