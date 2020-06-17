package mod.pixelstorm.interestingblocks.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import mod.pixelstorm.interestingblocks.client.texture.SkyboxBlockTexture;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer
{
	@Shadow
	abstract void renderLayer(RenderLayer renderLayer, MatrixStack matrixStack, double d, double e, double f);

	@Inject(method = "render", at = @At(value = "INVOKE_STRING", target = "net/minecraft/util/profiler/Profiler.swap(Ljava/lang/String;)V", args = { "ldc=fog" }))
	private void onRender(CallbackInfo callbackInfo)
	{
		Framebuffer buffer = MinecraftClient.getInstance().getFramebuffer();

		buffer.endWrite();

		blitFramebuffer(buffer, SkyboxBlockTexture.getInstance().getFramebuffer());

		buffer.beginWrite(true);
	}

	@Inject(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/render/WorldRenderer.renderLayer(Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack;DDD)V", ordinal = 0))
	private void onRenderLayer(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo callbackInfo)
	{
		Vec3d vector = camera.getPos();
		renderLayer(SkyboxBlockTexture.RENDERLAYER, matrices, vector.getX(), vector.getY(), vector.getZ());
	}

	private static void blitFramebuffer(Framebuffer from, Framebuffer to)
	{
		GlStateManager.bindFramebuffer(GL30.GL_READ_FRAMEBUFFER, from.fbo);
		GlStateManager.bindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, to.fbo);
		GL30.glBlitFramebuffer(0, 0, from.textureWidth, from.textureHeight, 0, 0, to.textureWidth, to.textureHeight, GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
		GlStateManager.bindFramebuffer(GL30.GL_READ_FRAMEBUFFER, 0);
		GlStateManager.bindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
	}
}
