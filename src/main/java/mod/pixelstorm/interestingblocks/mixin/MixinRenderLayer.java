package mod.pixelstorm.interestingblocks.mixin;

import com.google.common.collect.ImmutableList;
import mod.pixelstorm.interestingblocks.client.texture.SkyboxBlockTexture;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(RenderLayer.class)
public class MixinRenderLayer
{
	@Redirect(method = "getBlockLayers", at = @At(value = "INVOKE", target = "com/google/common/collect/ImmutableList.of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;"))
	private static ImmutableList redirectBlockLayers(Object solid, Object cutoutMipped, Object cutout, Object translucent, Object tripwire)
	{
		return ImmutableList.of(solid, cutoutMipped, cutout, translucent, tripwire, SkyboxBlockTexture.RENDERLAYER);
	}
}
