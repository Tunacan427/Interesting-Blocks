package mod.pixelstorm.interestingblocks.client.render.model;

import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import mod.pixelstorm.interestingblocks.client.texture.SkyboxBlockTexture;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SkyboxBlockUnbakedModel extends ForwardingUnbakedModel
{
	public SkyboxBlockUnbakedModel(UnbakedModel wrapped)
	{
		this.wrapped = wrapped;
	}

	@Override
	public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences)
	{
		Collection<SpriteIdentifier> dependencies = super.getTextureDependencies(unbakedModelGetter, unresolvedTextureReferences);
		dependencies.add(SkyboxBlockTexture.SPRITE_ID);
		return dependencies;
	}

	@Override
	public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId)
	{
		return new SkyboxBlockBakedModel(super.bake(loader, textureGetter, rotationContainer, modelId), textureGetter);
	}
}
