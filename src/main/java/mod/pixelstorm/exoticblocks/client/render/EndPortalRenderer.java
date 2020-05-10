package mod.pixelstorm.exoticblocks.client.render;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class EndPortalRenderer
{
	private static final Random RANDOM = new Random(31100L);

	private static final List<RenderLayer> renderLayers;

	static
	{
		renderLayers = IntStream.range(0, 16).mapToObj(i -> RenderLayer.getEndPortal(i + 1)).collect(ImmutableList.toImmutableList());
	}

	public static void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, double cameraDistance)
	{
		RANDOM.setSeed(31100L);

		// double cameraDistance = endPortalBlockEntity.getPos().getSquaredDistance((Position) this.dispatcher.camera.getPos(), true);
		int iterations = renderIterations(cameraDistance);
		Matrix4f matrix = matrixStack.peek().getModel();

		renderLayer(matrix, vertexConsumerProvider.getBuffer(renderLayers.get(0)), 0.15F);
		for (int i = 1; i < iterations; ++i)
			renderLayer(matrix, vertexConsumerProvider.getBuffer(renderLayers.get(i)), 2.0F / (18 - i));
	}

	private static void renderLayer(Matrix4f matrix, VertexConsumer vertexConsumer, float colourScalar)
	{
		float red = (RANDOM.nextFloat() * 0.5F + 0.1F) * colourScalar;
		float green = (RANDOM.nextFloat() * 0.5F + 0.4F) * colourScalar;
		float blue = (RANDOM.nextFloat() * 0.5F + 0.5F) * colourScalar;
		renderSide(matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, red, green, blue); // Direction.NORTH
		renderSide(matrix, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, red, green, blue); // Direction.EAST
		renderSide(matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, red, green, blue); // Direction.SOUTH
		renderSide(matrix, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, red, green, blue); // Direction.WEST
		renderSide(matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, red, green, blue); // Direction.DOWN
		renderSide(matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, red, green, blue); // Direction.UP
	}

	private static void renderSide(Matrix4f matrix, VertexConsumer vertexConsumer, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, float red, float green, float blue)
	{
		vertexConsumer.vertex(matrix, x1, y1, z1).color(red, green, blue, 1.0F).next();
		vertexConsumer.vertex(matrix, x2, y1, z2).color(red, green, blue, 1.0F).next();
		vertexConsumer.vertex(matrix, x2, y2, z3).color(red, green, blue, 1.0F).next();
		vertexConsumer.vertex(matrix, x1, y2, z4).color(red, green, blue, 1.0F).next();
	}

	protected static int renderIterations(double cameraDistance)
	{
		if (cameraDistance > 36864.0D)
			return 1;
		if (cameraDistance > 25600.0D)
			return 3;
		if (cameraDistance > 16384.0D)
			return 5;
		if (cameraDistance > 9216.0D)
			return 7;
		if (cameraDistance > 4096.0D)
			return 9;
		if (cameraDistance > 1024.0D)
			return 11;
		if (cameraDistance > 576.0D)
			return 13;
		return (cameraDistance > 256.0D) ? 14 : 15;
	}
}
