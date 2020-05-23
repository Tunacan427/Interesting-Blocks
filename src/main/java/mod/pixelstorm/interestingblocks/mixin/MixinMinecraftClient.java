package mod.pixelstorm.interestingblocks.mixin;

import mod.pixelstorm.interestingblocks.InterestingBlocksClient;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public class MixinMinecraftClient
{
	@Inject(method = "onResolutionChanged", at = @At("TAIL"))
	private void onResolutionChanged(CallbackInfo callbackInfo)
	{
		InterestingBlocksClient.setupSkyboxBuffer();
	}
}
