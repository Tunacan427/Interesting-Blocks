package mod.pixelstorm.interestingblocks.mixin;

import mod.pixelstorm.interestingblocks.client.texture.SkyboxBlockTexture;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MixinMinecraftClient
{
	@Inject(method = "<init>", at = @At("RETURN"))
	private void onInit(CallbackInfo callbackInfo)
	{
		MinecraftClient.getInstance().getTextureManager().registerTexture(SkyboxBlockTexture.ID, SkyboxBlockTexture.getInstance());
	}

	@Inject(method = "onResolutionChanged", at = @At("TAIL"))
	private void onResolutionChanged(CallbackInfo callbackInfo)
	{
		SkyboxBlockTexture.getInstance().ensureFramebufferSize();
	}
}
