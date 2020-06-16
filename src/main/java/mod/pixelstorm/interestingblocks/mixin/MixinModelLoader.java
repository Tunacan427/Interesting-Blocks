package mod.pixelstorm.interestingblocks.mixin;

import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;
import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.client.render.model.EchoBlockUnbakedModel;
import mod.pixelstorm.interestingblocks.client.render.model.SkyboxBlockUnbakedModel;
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
	private static final Map<ModelIdentifier, Function<UnbakedModel, UnbakedModel>> MODEL_MAP = new HashMap<ModelIdentifier, Function<UnbakedModel, UnbakedModel>>();

	static
	{
		MODEL_MAP.put(new ModelIdentifier(InterestingBlocks.MOD_ID + ":echo_block#"), EchoBlockUnbakedModel::new);
		//MODEL_MAP.put(new ModelIdentifier(InterestingBlocks.MOD_ID + ":skybox_block#"), SkyboxBlockUnbakedModel::new);
	}

	@ModifyVariable(method = "addModel", at = @At("STORE"))
	private UnbakedModel modifyUnbakedModel(UnbakedModel model, ModelIdentifier modelId)
	{
		Function<UnbakedModel, UnbakedModel> modelGetter = MODEL_MAP.get(modelId);

		if(modelGetter == null)
			return model;

		return modelGetter.apply(model);
	}
}
