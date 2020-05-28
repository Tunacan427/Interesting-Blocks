package mod.pixelstorm.interestingblocks.mixin;

import mod.pixelstorm.interestingblocks.client.texture.SkyboxBlockTexture;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.WorldRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinWorldRenderer
{
	@Inject(method = "render", at = @At(value = "INVOKE_STRING", target = "net/minecraft/util/profiler/Profiler.swap(Ljava/lang/String;)V", args = { "ldc=fog" }))
	private void render(CallbackInfo callbackInfo)
	{
		Framebuffer buffer = MinecraftClient.getInstance().getFramebuffer();
		buffer.endWrite();
		blitFramebuffer(buffer, SkyboxBlockTexture.getInstance().getFramebuffer());
		buffer.beginWrite(true);
	}

	private void blitFramebuffer(Framebuffer from, Framebuffer to)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, from.fbo);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, to.fbo);
		GL30.glBlitFramebuffer(0, 0, from.textureWidth, from.textureHeight, 0, 0, to.textureWidth, to.textureHeight, GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, 0);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
	}
}
