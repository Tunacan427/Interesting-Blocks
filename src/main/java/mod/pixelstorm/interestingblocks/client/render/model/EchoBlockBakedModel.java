package mod.pixelstorm.interestingblocks.client.render.model;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
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
public class EchoBlockBakedModel extends ForwardingBakedModel
{
	public EchoBlockBakedModel(BakedModel wrapped, Function<SpriteIdentifier, Sprite> spriteGetter)
	{
		this.wrapped = wrapped;
		EchoBlockSpriteManager.updateSpriteGetter(spriteGetter);
	}

	@Override
	public boolean isVanillaAdapter()
	{
		return false;
	}

	@Override
	public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context)
	{
		context.pushTransform(quad -> { quad.spriteBake(0, EchoBlockSpriteManager.getSprite(quad.nominalFace(), state, pos, blockView), MutableQuadView.BAKE_LOCK_UV); return true; });
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
	}

	@Override
	public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context)
	{
		throw new UnsupportedOperationException("Itemstack attempted to render EchoBlock model as an item: " + stack);
	}
}
