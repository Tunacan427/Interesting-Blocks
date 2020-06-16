package mod.pixelstorm.interestingblocks.client.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.pixelstorm.interestingblocks.InterestingBlocks;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.Window;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;

@Environment(EnvType.CLIENT)
public class SkyboxBlockTexture extends AbstractTexture
{
	public static final Identifier ID = new Identifier(InterestingBlocks.MOD_ID, "special/skybox_block");
	public static final SpriteIdentifier SPRITE_ID = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, ID);

	private static final SkyboxBlockTexture INSTANCE = new SkyboxBlockTexture();

	private Framebuffer framebuffer;

	public static SkyboxBlockTexture getInstance()
	{
		return INSTANCE;
	}

	@Override
	public void load(ResourceManager manager)
	{
		InterestingBlocks.log(Level.INFO, "Loading SkyboxBlockTexture");
	}

	public Framebuffer getFramebuffer()
	{
		RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);

		if (framebuffer == null)
		{
			Window window = MinecraftClient.getInstance().getWindow();
			framebuffer = new Framebuffer(window.getFramebufferWidth(), window.getFramebufferHeight(), true, MinecraftClient.IS_SYSTEM_MAC);
			glId = framebuffer.colorAttachment; // Just in case
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
		if (!RenderSystem.isOnRenderThread())
			RenderSystem.recordRenderCall(this::onClearGlId);
		else
			onClearGlId();
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
