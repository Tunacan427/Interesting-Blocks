package mod.pixelstorm.interestingblocks.mixin;

import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.client.texture.SkyboxBlockTexture;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.apache.logging.log4j.Level;

@Environment(EnvType.CLIENT)
@Mixin(TextureManager.class)
public class MixinTextureManager
{
	@Inject(method = "<init>", at = @At("RETURN"))
	private void onInit(CallbackInfo callbackInfo)
	{
		//TextureManager manager = (TextureManager) (Object) this;
		//SkyboxBlockTexture.getInstance().registerTexture(manager, null, SkyboxBlockTexture.ID, null);
		//manager.bindTexture(SkyboxBlockTexture.ID);
	}
}
