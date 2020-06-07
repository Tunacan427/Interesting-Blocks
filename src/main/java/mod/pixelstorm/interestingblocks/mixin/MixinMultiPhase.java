package mod.pixelstorm.interestingblocks.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.render.RenderLayer.MultiPhase")
@Environment(EnvType.CLIENT)
public interface MixinMultiPhase
{
	@Accessor
	RenderLayer.MultiPhaseParameters getPhases();
}
