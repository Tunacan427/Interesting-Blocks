package mod.pixelstorm.interestingblocks.mixin;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderLayer.MultiPhaseParameters.class)
@Environment(EnvType.CLIENT)
public interface MixinMultiPhaseParameters
{
	@Accessor
	ImmutableList<RenderPhase> getPhases();
}
