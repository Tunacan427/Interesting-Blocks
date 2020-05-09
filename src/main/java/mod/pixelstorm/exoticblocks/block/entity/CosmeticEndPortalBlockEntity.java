package mod.pixelstorm.exoticblocks.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class CosmeticEndPortalBlockEntity extends EndPortalBlockEntity
{
	public static BlockEntityType blockEntityType;

	public CosmeticEndPortalBlockEntity()
	{
		super(blockEntityType);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldDrawSide(Direction direction)
	{
		BlockState state = world.getBlockState(getPos());
		return state.getBlock().shouldDrawSide(state, (BlockView) world, getPos(), direction);
	}
}
