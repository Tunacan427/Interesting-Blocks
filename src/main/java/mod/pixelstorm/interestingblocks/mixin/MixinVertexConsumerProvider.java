package mod.pixelstorm.interestingblocks.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VertexConsumerProvider.Immediate.class)
@Environment(EnvType.CLIENT)
public class MixinVertexConsumerProvider
{
	@Shadow
	@Final
	private BufferBuilder fallbackBuffer;

	@Inject(method = "getBuffer", at = @At("HEAD"), cancellable = true)
	private void onGetBuffer(RenderLayer renderLayer, CallbackInfoReturnable callbackInfo)
	{
		if(renderLayer == null)
			callbackInfo.setReturnValue(fallbackBuffer);
	}
}
