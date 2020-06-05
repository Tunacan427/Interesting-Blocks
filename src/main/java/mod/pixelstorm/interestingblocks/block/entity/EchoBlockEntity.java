package mod.pixelstorm.interestingblocks.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class EchoBlockEntity extends BlockEntity
{
	public static BlockEntityType blockEntityType;

	public EchoBlockEntity()
	{
		super(blockEntityType);
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldDrawSide(Direction direction)
	{
		BlockState state = world.getBlockState(getPos());
		return state.getBlock().shouldDrawSide(state, (BlockView) world, getPos(), direction);
	}
}
