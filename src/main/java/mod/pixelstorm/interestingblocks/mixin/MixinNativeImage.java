package mod.pixelstorm.interestingblocks.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.texture.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(NativeImage.class)
public interface MixinNativeImage
{
	@Accessor
	long getPointer();
}
