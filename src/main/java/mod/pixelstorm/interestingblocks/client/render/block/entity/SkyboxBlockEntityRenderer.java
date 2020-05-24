package mod.pixelstorm.interestingblocks.client.render.block.entity;

import mod.pixelstorm.interestingblocks.block.entity.SkyboxBlockEntity;
import mod.pixelstorm.interestingblocks.client.texture.SkyboxBlockTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.Matrix3f;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;

public class SkyboxBlockEntityRenderer extends BlockEntityRenderer<SkyboxBlockEntity>
{
	public SkyboxBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public void render(SkyboxBlockEntity blockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay)
	{
		renderCube(matrixStack.peek().getModel(), vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(SkyboxBlockTexture.ID)), overlay, matrixStack.peek().getNormal());
	}

	private static void renderCube(Matrix4f matrix, VertexConsumer vertexConsumer, int overlay, Matrix3f normal)
	{
		renderFace(matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, overlay, normal); // Direction.NORTH
		renderFace(matrix, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, overlay, normal); // Direction.EAST
		renderFace(matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, overlay, normal); // Direction.SOUTH
		renderFace(matrix, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, overlay, normal); // Direction.WEST
		renderFace(matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, overlay, normal); // Direction.DOWN
		renderFace(matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, overlay, normal); // Direction.UP
	}

	private static void renderFace(Matrix4f matrix, VertexConsumer vertexConsumer, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, int overlay, Matrix3f normal)
	{
		vertexConsumer.vertex(matrix, x1, y1, z1).color(1, 1, 1, 1).texture(0, 0).overlay(overlay).light(0xF000F0).normal(normal, 0, 0, 1).next();
		vertexConsumer.vertex(matrix, x2, y1, z2).color(1, 1, 1, 1).texture(1, 0).overlay(overlay).light(0xF000F0).normal(normal, 0, 0, 1).next();
		vertexConsumer.vertex(matrix, x2, y2, z3).color(1, 1, 1, 1).texture(1, 1).overlay(overlay).light(0xF000F0).normal(normal, 0, 0, 1).next();
		vertexConsumer.vertex(matrix, x1, y2, z4).color(1, 1, 1, 1).texture(0, 1).overlay(overlay).light(0xF000F0).normal(normal, 0, 0, 1).next();
	}
}
