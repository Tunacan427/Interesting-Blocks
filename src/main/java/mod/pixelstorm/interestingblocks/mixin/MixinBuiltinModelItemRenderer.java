package mod.pixelstorm.interestingblocks.mixin;

import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.block.entity.CosmeticEndPortalBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.apache.logging.log4j.Level;

@Mixin(BuiltinModelItemRenderer.class)
public class MixinBuiltinModelItemRenderer
{
	private final CosmeticEndPortalBlockEntity renderEndPortalBlock = new CosmeticEndPortalBlockEntity();

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void onRender(ItemStack stack, CallbackInfo callbackInfo)
	{
		if(stack.getItem() instanceof BlockItem)
		{
			if(((BlockItem) stack.getItem()).getBlock() == Registry.BLOCK.get(new Identifier(InterestingBlocks.MOD_ID, "cosmetic_end_portal_block")))
			{
				BlockEntityRenderDispatcher.INSTANCE.renderEntity(renderEndPortalBlock);
				callbackInfo.cancel();
			}
		}
	}
}
