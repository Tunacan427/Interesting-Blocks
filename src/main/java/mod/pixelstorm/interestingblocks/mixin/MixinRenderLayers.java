package mod.pixelstorm.interestingblocks.mixin;

import mod.pixelstorm.interestingblocks.InterestingBlocksClient;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(RenderLayers.class)
public class MixinRenderLayers
{
	@Inject(method = "getItemLayer", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE_ASSIGN", target = "net/minecraft/item/ItemStack.getItem()Lnet/minecraft/item/Item;"), cancellable = true)
	private static void onGetItemLayer(ItemStack stack, boolean bl, CallbackInfoReturnable<RenderLayer> callbackInfo)
	{
		RenderLayer itemLayer = InterestingBlocksClient.getItemRenderLayer(stack.getItem());
		if(itemLayer != null)
			callbackInfo.setReturnValue(itemLayer);
	}
}
