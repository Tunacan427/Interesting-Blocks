package mod.pixelstorm.interestingblocks.client.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.client.render.SkyboxTexturing;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.Window;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SkyboxBlockTexture extends AbstractTexture
{
	public static final Identifier ID = InterestingBlocks.getId("special/skybox_block");

	public static final RenderLayer RENDERLAYER = RenderLayer.of("skybox", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, 7, 2097152,
																			RenderLayer.MultiPhaseParameters.builder()
																			.texture(new RenderPhase.Texture(ID, false, false))
																			.texturing(new SkyboxTexturing())
																			.build(false));

	private static final SkyboxBlockTexture INSTANCE = new SkyboxBlockTexture();

	private Framebuffer framebuffer;

	public static SkyboxBlockTexture getInstance()
	{
		return INSTANCE;
	}

	@Override
	public void load(ResourceManager manager) { }

	public Framebuffer getFramebuffer()
	{
		RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);

		if (framebuffer == null)
		{
			Window window = MinecraftClient.getInstance().getWindow();
			framebuffer = new Framebuffer(window.getFramebufferWidth(), window.getFramebufferHeight(), true, MinecraftClient.IS_SYSTEM_MAC);
			glId = framebuffer.colorAttachment;
		}

		return framebuffer;
	}

	public void ensureFramebufferSize()
	{
		Window window = MinecraftClient.getInstance().getWindow();
		getFramebuffer().resize(window.getFramebufferWidth(), window.getFramebufferHeight(), MinecraftClient.IS_SYSTEM_MAC);
	}

	@Override
	public int getGlId()
	{
		return getFramebuffer().colorAttachment;
	}

	@Override
	public void clearGlId()
	{
		if (RenderSystem.isOnRenderThread())
			onClearGlId();
		else
			RenderSystem.recordRenderCall(this::onClearGlId);
	}

	private void onClearGlId()
	{
		if (framebuffer != null)
		{
			framebuffer.delete();
			framebuffer = null;
		}

		glId = -1;
	}
}
