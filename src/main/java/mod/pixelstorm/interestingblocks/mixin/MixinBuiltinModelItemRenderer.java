package mod.pixelstorm.interestingblocks.mixin;

import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.client.render.EndPortalRenderer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BuiltinModelItemRenderer.class)
public class MixinBuiltinModelItemRenderer
{
	private static final Item END_PORTAL_ITEM = Registry.ITEM.get(InterestingBlocks.getId("cosmetic_end_portal_block"));

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo callbackInfo)
	{
		if(stack.getItem() == END_PORTAL_ITEM)
		{
			EndPortalRenderer.render(matrixStack, vertexConsumerProvider, 0);
			callbackInfo.cancel();
		}
	}
}
