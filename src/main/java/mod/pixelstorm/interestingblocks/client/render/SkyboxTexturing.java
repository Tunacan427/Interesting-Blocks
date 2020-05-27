package mod.pixelstorm.interestingblocks.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.util.GlAllocationUtils;
import org.lwjgl.opengl.GL11;

public class SkyboxTexturing extends RenderPhase.Texturing
{
	public static final FloatBuffer buffer = GlAllocationUtils.allocateFloatBuffer(4);

	public SkyboxTexturing()
	{
		super("skybox_texturing",
			() ->
			{
				RenderSystem.matrixMode(GL11.GL_TEXTURE);
				RenderSystem.pushMatrix();
				RenderSystem.loadIdentity();
				RenderSystem.translatef(0.5F, 0.5F, 0.0F);
				RenderSystem.scalef(0.5F, 0.5F, 1.0F);
				RenderSystem.mulTextureByProjModelView();
				RenderSystem.matrixMode(GL11.GL_MODELVIEW);

				// GL_OBJECT_LINEAR, GL_EYE_LINEAR, GL_SPHERE_MAP
				GlStateManager.texGenMode(GlStateManager.TexCoord.S, GL11.GL_EYE_LINEAR);
				GlStateManager.texGenMode(GlStateManager.TexCoord.T, GL11.GL_EYE_LINEAR);
				GlStateManager.texGenMode(GlStateManager.TexCoord.R, GL11.GL_EYE_LINEAR);

				// GL_TEXTURE_GEN_MODE, GL_OBJECT_PLANE, GL_EYE_PLANE
				GlStateManager.texGenParam(GlStateManager.TexCoord.S, GL11.GL_EYE_PLANE, getBuffer(1, 0, 0, 0));
				GlStateManager.texGenParam(GlStateManager.TexCoord.T, GL11.GL_EYE_PLANE, getBuffer(0, 1, 0, 0));
				GlStateManager.texGenParam(GlStateManager.TexCoord.R, GL11.GL_EYE_PLANE, getBuffer(0, 0, 1, 0));

				GlStateManager.enableTexGen(GlStateManager.TexCoord.S);
				GlStateManager.enableTexGen(GlStateManager.TexCoord.T);
				GlStateManager.enableTexGen(GlStateManager.TexCoord.R);
			},
			() ->
			{
				RenderSystem.matrixMode(GL11.GL_TEXTURE);
				RenderSystem.popMatrix();

				RenderSystem.matrixMode(GL11.GL_MODELVIEW);
				RenderSystem.clearTexGen();
			}
		);
	}

	public static FloatBuffer getBuffer(float a, float b, float c, float d)
	{
		((Buffer) buffer).clear();
		buffer.put(a).put(b).put(c).put(d);
		((Buffer) buffer).flip();
		return buffer;
	}
}
