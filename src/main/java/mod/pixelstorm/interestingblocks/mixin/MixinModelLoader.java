package mod.pixelstorm.interestingblocks.mixin;

import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.client.render.model.EchoBlockUnbakedModel;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.apache.logging.log4j.Level;

@Environment(EnvType.CLIENT)
@Mixin(ModelLoader.class)
public class MixinModelLoader
{
	private static final ModelIdentifier ECHO_BLOCK_ID = new ModelIdentifier(InterestingBlocks.MOD_ID + ":echo_block#");

	@ModifyVariable(method = "addModel", at = @At("STORE"))
	private UnbakedModel modifyUnbakedModel(UnbakedModel model, ModelIdentifier modelId)
	{
		return ECHO_BLOCK_ID.equals(modelId) ? new EchoBlockUnbakedModel(model) : model;
	}
}
