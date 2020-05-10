package mod.pixelstorm.exoticblocks.mixin;

import mod.pixelstorm.exoticblocks.ExoticBlocks;
import mod.pixelstorm.exoticblocks.client.render.EndPortalRenderer;
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
			if(((BlockItem) itemStack.getItem()).getBlock() == Registry.BLOCK.get(new Identifier(ExoticBlocks.MOD_ID, "cosmetic_end_portal_block")))
			{
				EndPortalRenderer.render(matrix, vertexConsumerProvider, 1.0D);
				callbackInfo.cancel();
			}
		}
	}
}
