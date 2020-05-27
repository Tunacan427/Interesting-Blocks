package mod.pixelstorm.interestingblocks.client.render.block.entity;

import java.util.function.Function;
import mod.pixelstorm.interestingblocks.block.entity.SkyboxBlockEntity;
import mod.pixelstorm.interestingblocks.client.render.SkyboxTexturing;
import mod.pixelstorm.interestingblocks.client.texture.SkyboxBlockTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;

public class SkyboxBlockEntityRenderer extends BlockEntityRenderer<SkyboxBlockEntity>
{
	public static final RenderLayer SKYBOX_RENDERLAYER = RenderLayer.of("skybox", VertexFormats.POSITION_COLOR, 7, 256, RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(SkyboxBlockTexture.ID, false, false)).texturing(new SkyboxTexturing()).build(false));

	public SkyboxBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public void render(SkyboxBlockEntity blockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay)
	{
		renderBlock(matrixStack, vertexConsumerProvider, blockEntity::shouldDrawSide);
	}

	public static void renderBlock(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, Function<Direction, Boolean> shouldDrawSide)
	{
		SkyboxBlockTexture.getInstance().getFramebuffer().checkFramebufferStatus();
		renderCube(matrixStack.peek().getModel(), vertexConsumerProvider.getBuffer(SKYBOX_RENDERLAYER), shouldDrawSide);
	}

	private static void renderCube(Matrix4f matrix, VertexConsumer vertexConsumer, Function<Direction, Boolean> shouldDrawSide)
	{
		renderFace(matrix, vertexConsumer, 0, 1, 1, 0, 0, 0, 0, 0, Direction.NORTH, shouldDrawSide);
		renderFace(matrix, vertexConsumer, 1, 1, 1, 0, 0, 1, 1, 0, Direction.EAST, shouldDrawSide);
		renderFace(matrix, vertexConsumer, 0, 1, 0, 1, 1, 1, 1, 1, Direction.SOUTH, shouldDrawSide);
		renderFace(matrix, vertexConsumer, 0, 0, 0, 1, 0, 1, 1, 0, Direction.WEST, shouldDrawSide);
		renderFace(matrix, vertexConsumer, 0, 1, 0, 0, 0, 0, 1, 1, Direction.DOWN, shouldDrawSide);
		renderFace(matrix, vertexConsumer, 0, 1, 1, 1, 1, 1, 0, 0, Direction.UP, shouldDrawSide);
	}

	private static void renderFace(Matrix4f matrix, VertexConsumer vertexConsumer, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Direction direction, Function<Direction, Boolean> shouldDrawSide)
	{
		if(shouldDrawSide.apply(direction))
		{
			vertexConsumer.vertex(matrix, x1, y1, z1).color(255, 255, 255, 255).next();
			vertexConsumer.vertex(matrix, x2, y1, z2).color(255, 255, 255, 255).next();
			vertexConsumer.vertex(matrix, x2, y2, z3).color(255, 255, 255, 255).next();
			vertexConsumer.vertex(matrix, x1, y2, z4).color(255, 255, 255, 255).next();
		}
	}
}
