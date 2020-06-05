package mod.pixelstorm.interestingblocks.block;

import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ConnectedBlock extends Block
{
	public static final BooleanProperty UP = ConnectingBlock.UP;
	public static final BooleanProperty DOWN = ConnectingBlock.DOWN;
	public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
	public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
	public static final BooleanProperty EAST = ConnectingBlock.EAST;
	public static final BooleanProperty WEST = ConnectingBlock.WEST;

	public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES;

	public ConnectedBlock(Block.Settings settings)
	{
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(UP, false).with(DOWN, false).with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext placementContext)
	{
		World world = placementContext.getWorld();
		BlockPos blockPos = placementContext.getBlockPos();

		return getDefaultState().with(UP,		world.getBlockState(blockPos.up()).getBlock() == this)
								.with(DOWN,		world.getBlockState(blockPos.down()).getBlock() == this)
								.with(NORTH,	world.getBlockState(blockPos.north()).getBlock() == this)
								.with(SOUTH,	world.getBlockState(blockPos.south()).getBlock() == this)
								.with(EAST,		world.getBlockState(blockPos.east()).getBlock() == this)
								.with(WEST,		world.getBlockState(blockPos.west()).getBlock() == this);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		return state.with(FACING_PROPERTIES.get(facing), neighborState.getBlock() == this);
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return state.with(FACING_PROPERTIES.get(rotation.rotate(Direction.UP)),		state.get(UP))
					.with(FACING_PROPERTIES.get(rotation.rotate(Direction.DOWN)),	state.get(DOWN))
					.with(FACING_PROPERTIES.get(rotation.rotate(Direction.NORTH)),	state.get(NORTH))
					.with(FACING_PROPERTIES.get(rotation.rotate(Direction.SOUTH)),	state.get(SOUTH))
					.with(FACING_PROPERTIES.get(rotation.rotate(Direction.EAST)),	state.get(EAST))
					.with(FACING_PROPERTIES.get(rotation.rotate(Direction.WEST)),	state.get(WEST));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror)
	{
		return state.with(FACING_PROPERTIES.get(mirror.apply(Direction.UP)),	state.get(UP))
					.with(FACING_PROPERTIES.get(mirror.apply(Direction.DOWN)),	state.get(DOWN))
					.with(FACING_PROPERTIES.get(mirror.apply(Direction.NORTH)),	state.get(NORTH))
					.with(FACING_PROPERTIES.get(mirror.apply(Direction.SOUTH)),	state.get(SOUTH))
					.with(FACING_PROPERTIES.get(mirror.apply(Direction.EAST)),	state.get(EAST))
					.with(FACING_PROPERTIES.get(mirror.apply(Direction.WEST)),	state.get(WEST));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
	}
}
