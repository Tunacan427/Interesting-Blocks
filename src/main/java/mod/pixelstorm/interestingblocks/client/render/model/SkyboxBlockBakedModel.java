package mod.pixelstorm.interestingblocks.client.render.model;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import mod.pixelstorm.interestingblocks.client.texture.SkyboxBlockTexture;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

@Environment(EnvType.CLIENT)
public class SkyboxBlockBakedModel extends ForwardingBakedModel
{
	private final Function<SpriteIdentifier, Sprite> spriteGetter;

	//private static final NativeImage IMAGE = new NativeImage(NativeImage.Format.RGBA, buffer.textureWidth, buffer.textureHeight, true);
	//private static final Sprite SPRITE = new Sprite(null, new Info(SkyboxBlockTexture.ID, buffer.textureWidth, buffer.textureHeight, null), 0, 1, 1, 0, 0, IMAGE);

	public SkyboxBlockBakedModel(BakedModel wrapped, Function<SpriteIdentifier, Sprite> spriteGetter)
	{
		this.wrapped = wrapped;
		this.spriteGetter = spriteGetter;
	}

	@Override
	public boolean isVanillaAdapter()
	{
		return false;
	}

	@Override
	public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context)
	{
		//context.pushTransform(quad -> { quad.spriteBake(0, SPRITE, MutableQuadView.BAKE_LOCK_UV); return true; });
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		//context.popTransform();
	}

	@Override
	public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context)
	{
		throw new UnsupportedOperationException("Itemstack attempted to render SkyboxBlock model as an item: " + stack);
	}
}
