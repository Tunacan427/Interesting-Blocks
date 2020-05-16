package mod.pixelstorm.exoticblocks.client.render.block.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Random;
import mod.pixelstorm.exoticblocks.block.entity.CosmeticEndPortalBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.GlAllocationUtils;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class CosmeticEndPortalBlockEntityRenderer extends BlockEntityRenderer<CosmeticEndPortalBlockEntity>
{
	private static final Identifier SKY_TEX = new Identifier("textures/environment/end_sky.png");

	private static final Identifier PORTAL_TEX = new Identifier("textures/entity/end_portal.png");

	private static final Random RANDOM = new Random(31100L);

	private static final FloatBuffer field_4408 = GlAllocationUtils.allocateFloatBuffer(16);

	private static final FloatBuffer field_4404 = GlAllocationUtils.allocateFloatBuffer(16);

	private final FloatBuffer field_4403 = GlAllocationUtils.allocateFloatBuffer(16);

	public void render(CosmeticEndPortalBlockEntity blockEntity, double xOffset, double yOffset, double zOffset, float tickDelta, int blockBreakStage)
	{
		GlStateManager.disableLighting();
		RANDOM.setSeed(31100L);
		GlStateManager.getMatrix(2982, field_4408);
		GlStateManager.getMatrix(2983, field_4404);
		double distance = xOffset * xOffset + yOffset * yOffset + zOffset * zOffset;
		int iterations = renderIterations(distance);
		boolean blackFog = false;
		GameRenderer gameRenderer = (MinecraftClient.getInstance()).gameRenderer;
		for(int i = 0; i < iterations; ++i)
		{
			GlStateManager.pushMatrix();
			float m = 2.0F / (18 - i);
			if(i == 0)
			{
				bindTexture(SKY_TEX);
				m = 0.15F;
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			}
			if(i >= 1)
			{
				bindTexture(PORTAL_TEX);
				blackFog = true;
				gameRenderer.setFogBlack(true);
			}
			if(i == 1)
			{
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			}
			GlStateManager.texGenMode(GlStateManager.TexCoord.S, 9216);
			GlStateManager.texGenMode(GlStateManager.TexCoord.T, 9216);
			GlStateManager.texGenMode(GlStateManager.TexCoord.R, 9216);
			GlStateManager.texGenParam(GlStateManager.TexCoord.S, 9474, method_3593(1.0F, 0.0F, 0.0F, 0.0F));
			GlStateManager.texGenParam(GlStateManager.TexCoord.T, 9474, method_3593(0.0F, 1.0F, 0.0F, 0.0F));
			GlStateManager.texGenParam(GlStateManager.TexCoord.R, 9474, method_3593(0.0F, 0.0F, 1.0F, 0.0F));
			GlStateManager.enableTexGen(GlStateManager.TexCoord.S);
			GlStateManager.enableTexGen(GlStateManager.TexCoord.T);
			GlStateManager.enableTexGen(GlStateManager.TexCoord.R);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.loadIdentity();
			GlStateManager.translatef(0.5F, 0.5F, 0.0F);
			GlStateManager.scalef(0.5F, 0.5F, 1.0F);
			float n = (i + 1);
			GlStateManager.translatef(17.0F / n, (2.0F + n / 1.5F) * (float)(Util.getMeasuringTimeMs() % 800000L) / 800000.0F, 0.0F);
			GlStateManager.rotatef((n * n * 4321.0F + n * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.scalef(4.5F - n / 4.0F, 4.5F - n / 4.0F, 1.0F);
			GlStateManager.multMatrix(field_4404);
			GlStateManager.multMatrix(field_4408);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();
			bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
			float o = (RANDOM.nextFloat() * 0.5F + 0.1F) * m;
			float p = (RANDOM.nextFloat() * 0.5F + 0.4F) * m;
			float q = (RANDOM.nextFloat() * 0.5F + 0.5F) * m;
			if(blockEntity.shouldDrawSide(Direction.SOUTH))
			{
				bufferBuilder.vertex(xOffset, yOffset, zOffset + 1.0D).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset, zOffset + 1.0D).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset + 1.0D, zOffset + 1.0D).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset, yOffset + 1.0D, zOffset + 1.0D).color(o, p, q, 1.0F).next();
			}
			if(blockEntity.shouldDrawSide(Direction.NORTH))
			{
				bufferBuilder.vertex(xOffset, yOffset + 1.0D, zOffset).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset + 1.0D, zOffset).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset, zOffset).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset, yOffset, zOffset).color(o, p, q, 1.0F).next();
			}
			if(blockEntity.shouldDrawSide(Direction.EAST))
			{
				bufferBuilder.vertex(xOffset + 1.0D, yOffset + 1.0D, zOffset).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset + 1.0D, zOffset + 1.0D).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset, zOffset + 1.0D).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset, zOffset).color(o, p, q, 1.0F).next();
			}
			if(blockEntity.shouldDrawSide(Direction.WEST))
			{
				bufferBuilder.vertex(xOffset, yOffset, zOffset).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset, yOffset, zOffset + 1.0D).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset, yOffset + 1.0D, zOffset + 1.0D).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset, yOffset + 1.0D, zOffset).color(o, p, q, 1.0F).next();
			}
			if(blockEntity.shouldDrawSide(Direction.DOWN))
			{
				bufferBuilder.vertex(xOffset, yOffset, zOffset).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset, zOffset).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset, zOffset + 1.0D).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset, yOffset, zOffset + 1.0D).color(o, p, q, 1.0F).next();
			}
			if(blockEntity.shouldDrawSide(Direction.UP))
			{
				bufferBuilder.vertex(xOffset, yOffset + 1, zOffset + 1.0D).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset + 1, zOffset + 1.0D).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset + 1.0D, yOffset + 1, zOffset).color(o, p, q, 1.0F).next();
				bufferBuilder.vertex(xOffset, yOffset + 1, zOffset).color(o, p, q, 1.0F).next();
			}
			tessellator.draw();
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
			bindTexture(SKY_TEX);
		}
		GlStateManager.disableBlend();
		GlStateManager.disableTexGen(GlStateManager.TexCoord.S);
		GlStateManager.disableTexGen(GlStateManager.TexCoord.T);
		GlStateManager.disableTexGen(GlStateManager.TexCoord.R);
		GlStateManager.enableLighting();
		if (blackFog)
			gameRenderer.setFogBlack(false);
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

	private FloatBuffer method_3593(float f, float g, float h, float i)
	{
		((Buffer) field_4403).clear();
		field_4403.put(f).put(g).put(h).put(i);
		((Buffer) field_4403).flip();
		return field_4403;
	}
}
