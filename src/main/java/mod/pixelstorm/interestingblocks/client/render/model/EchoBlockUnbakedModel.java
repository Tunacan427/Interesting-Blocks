package mod.pixelstorm.interestingblocks.client.render.model;

import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import mod.pixelstorm.interestingblocks.InterestingBlocks;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;

public class EchoBlockUnbakedModel implements UnbakedModel
{
	private final UnbakedModel wrapped;

	public EchoBlockUnbakedModel(UnbakedModel wrapped)
	{
		this.wrapped = wrapped;
	}

	@Override
	public Collection<Identifier> getModelDependencies()
	{
		return Collections.emptySet();
	}

	@Override
	public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences)
	{
		return Arrays.asList(EchoBlockSpriteManager.getSpriteIdentifiers());
	}

	@Override
	public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId)
	{
		return new EchoBlockBakedModel(wrapped.bake(loader, textureGetter, rotationContainer, modelId), textureGetter);
	}
}
