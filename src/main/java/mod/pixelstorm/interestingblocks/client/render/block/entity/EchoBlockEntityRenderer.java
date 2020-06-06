package mod.pixelstorm.interestingblocks.client.render.block.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.Function;
import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.block.ConnectedBlock;
import mod.pixelstorm.interestingblocks.block.entity.EchoBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class EchoBlockEntityRenderer extends BlockEntityRenderer<EchoBlockEntity>
{
	public static final RenderPhase.Transparency TRANSLUCENCE = new RenderPhase.Transparency("translucence", () ->
																							{
																								RenderSystem.enableBlend();
																								RenderSystem.defaultBlendFunc();
																							},
																							() -> RenderSystem.disableBlend());

	public static final Direction[][] PERPENDICULAR =	{
															{
																null, // Should never be accessed
																Direction.EAST,
																Direction.SOUTH
															},
															{
																Direction.EAST,
																null, // Should never be accessed
																Direction.UP
															},
															{
																Direction.NORTH,
																Direction.UP
															}
														};

	public static final int[][] PACKING_FLAGS =	{	// Value of 0 = should never be accessed
													{ 0, 0, 1, 4, 8, 2 },
													{ 0, 0, 4, 1, 8, 2 },
													{ 4, 1, 0, 0, 8, 2 },
													{ 1, 4, 0, 0, 8, 2 },
													{ 1, 4, 8, 2, 0, 0 },
													{ 4, 1, 8, 2, 0, 0 }
												};

	public static final int[][] DIAGONAL_PACKING_FLAGS =	{
																{ 16, 32, 64, 128 },
																{ 32, 16, 128, 64 },
																{ 16, 32, 64, 128 },
																{ 32, 16, 128, 64 },
																{ 64, 128, 16, 32 },
																{ 128, 64, 32, 16 }
															};

	public static final int[][] DIAGONAL_INDEXES =	{	// Value of 8 = should never be accessed
														{ 8, 8, 1, 2, 2, 1 },
														{ 8, 8, 0, 3, 3, 0 },
														{ 1, 0, 8, 8, 3, 0 },
														{ 2, 3, 8, 8, 2, 1 },
														{ 2, 3, 3, 2, 8, 8 },
														{ 1, 0, 0, 1, 8, 8 }
													};

	public static final RenderLayer[] RENDERLAYERS = new RenderLayer[768];

	public static final Block ECHO_BLOCK = Registry.BLOCK.get(new Identifier(InterestingBlocks.MOD_ID, "echo_block"));

	private static final Direction[] DIRECTIONS = Direction.values();

	static
	{
		String path = "textures/block/echo/";

		createRenderLayers(0b0000_0000, path + "single");
		createRenderLayers(0b0000_1111, path + "corners");
		createRenderLayers(0b1111_1111, path + "invisible");

		createRenderLayers(0b0000_0101, path + "vertical", 0b1111);
		createRenderLayers(0b0000_1010, path + "horizontal", 0b1111);

		createRenderLayers(0b0000_0001, path + "bottom_cap", 0b1111);
		createRenderLayers(0b0000_0010, path + "left_cap", 0b1111);
		createRenderLayers(0b0000_0100, path + "top_cap", 0b1111);
		createRenderLayers(0b0000_1000, path + "right_cap", 0b1111);

		createRenderLayers(0b0000_0011, path + "doublecorner_bottomleft", 0b1110);
		createRenderLayers(0b0000_0110, path + "doublecorner_topleft", 0b1101);
		createRenderLayers(0b0000_1100, path + "doublecorner_topright", 0b1011);
		createRenderLayers(0b0000_1001, path + "doublecorner_bottomright", 0b0111);

		createRenderLayers(0b0001_0011, path + "bottomleft_corner", 0b1111);
		createRenderLayers(0b0010_0110, path + "topleft_corner", 0b1111);
		createRenderLayers(0b0100_1100, path + "topright_corner", 0b1111);
		createRenderLayers(0b1000_1001, path + "bottomright_corner", 0b1111);

		createRenderLayers(0b0000_0111, path + "leftedge_corners", 0b1100);
		createRenderLayers(0b0000_1011, path + "bottomedge_corners", 0b0110);
		createRenderLayers(0b0000_1101, path + "rightedge_corners", 0b0011);
		createRenderLayers(0b0000_1110, path + "topedge_corners", 0b1001);

		createRenderLayers(0b0001_1011, path + "bottomedge_leftcorner", 0b0110);
		createRenderLayers(0b0010_0111, path + "leftedge_topcorner", 0b1100);
		createRenderLayers(0b0100_1110, path + "topedge_rightcorner", 0b1001);
		createRenderLayers(0b1000_1101, path + "rightedge_bottomcorner", 0b0011);

		createRenderLayers(0b0001_0111, path + "leftedge_bottomcorner", 0b1100);
		createRenderLayers(0b0010_1110, path + "topedge_leftcorner", 0b1001);
		createRenderLayers(0b0100_1101, path + "rightedge_topcorner", 0b0011);
		createRenderLayers(0b1000_1011, path + "bottomedge_rightcorner", 0b0110);

		createRenderLayers(0b0011_0111, path + "left_edge", 0b1100);
		createRenderLayers(0b0110_1110, path + "top_edge", 0b1001);
		createRenderLayers(0b1001_1011, path + "bottom_edge", 0b0110);
		createRenderLayers(0b1100_1101, path + "right_edge", 0b0011);

		createRenderLayers(0b0001_1111, path + "corners_no-topright");
		createRenderLayers(0b0010_1111, path + "corners_no-bottomright");
		createRenderLayers(0b0100_1111, path + "corners_no-bottomleft");
		createRenderLayers(0b1000_1111, path + "corners_no-topleft");

		createRenderLayers(0b0011_1111, path + "corners_left");
		createRenderLayers(0b0110_1111, path + "corners_top");
		createRenderLayers(0b1100_1111, path + "corners_right");
		createRenderLayers(0b1001_1111, path + "corners_bottom");

		createRenderLayers(0b0111_1111, path + "corner_topleft");
		createRenderLayers(0b1011_1111, path + "corner_bottomleft");
		createRenderLayers(0b1101_1111, path + "corner_bottomright");
		createRenderLayers(0b1110_1111, path + "corner_topright");

		createRenderLayers(0b0101_1111, path + "corners_topleft_bottomright");
		createRenderLayers(0b1010_1111, path + "corners_topright_bottomleft");
	}

	public EchoBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public void render(EchoBlockEntity blockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay)
	{
		World world = blockEntity.getWorld();
		BlockPos pos = blockEntity.getPos();
		BlockState state = world.getBlockState(pos);

		if(state.getBlock() != ECHO_BLOCK)
			return;

		renderCube(world,
					pos,
					state,
					((int) ((world.getTime() + tickDelta) / 10f)) % 3 * 256,
					matrixStack.peek().getModel(),
					vertexConsumerProvider,
					blockEntity::shouldDrawSide);
	}

	private static void renderCube(World world, BlockPos blockPos, BlockState state, int frame, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, Function<Direction, Boolean> shouldDrawSide)
	{
		renderFace(world, blockPos, state, Direction.NORTH,	frame, matrix, vertexConsumerProvider, 0, 1, 1, 0, 0, 0, 0, 0, shouldDrawSide);
		renderFace(world, blockPos, state, Direction.EAST,	frame, matrix, vertexConsumerProvider, 1, 1, 1, 0, 0, 1, 1, 0, shouldDrawSide);
		renderFace(world, blockPos, state, Direction.SOUTH,	frame, matrix, vertexConsumerProvider, 0, 1, 0, 1, 1, 1, 1, 1, shouldDrawSide);
		renderFace(world, blockPos, state, Direction.WEST,	frame, matrix, vertexConsumerProvider, 0, 0, 0, 1, 0, 1, 1, 0, shouldDrawSide);
		renderFace(world, blockPos, state, Direction.DOWN,	frame, matrix, vertexConsumerProvider, 0, 1, 0, 0, 0, 0, 1, 1, shouldDrawSide);
		renderFace(world, blockPos, state, Direction.UP,	frame, matrix, vertexConsumerProvider, 0, 1, 1, 1, 1, 1, 0, 0, shouldDrawSide);
	}

	private static void renderFace(World world, BlockPos blockPos, BlockState state, Direction direction, int frame, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Function<Direction, Boolean> shouldDrawSide)
	{
		if(shouldDrawSide.apply(direction))
		{
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(getRenderLayer(world, blockPos, state, direction, frame));
			vertexConsumer.vertex(matrix, x1, y1, z1).color(255, 255, 255, 255).texture(0f, 0f).next();
			vertexConsumer.vertex(matrix, x2, y1, z2).color(255, 255, 255, 255).texture(1f, 0f).next();
			vertexConsumer.vertex(matrix, x2, y2, z3).color(255, 255, 255, 255).texture(1f, 1f).next();
			vertexConsumer.vertex(matrix, x1, y2, z4).color(255, 255, 255, 255).texture(0f, 1f).next();
		}
	}

	private static void createRenderLayers(int baseIndex, String id)
	{
		createRenderLayers(baseIndex, id, 0);
	}

	private static void createRenderLayers(int baseIndex, String id, int extraIndexes)
	{
		VertexFormat format = VertexFormats.POSITION_COLOR_TEXTURE;

		if(extraIndexes == 0)
		{
			RENDERLAYERS[baseIndex] = getRenderLayer(id + "_1.png", format);
			RENDERLAYERS[baseIndex | 0b01_0000_0000] = getRenderLayer(id + "_2.png", format);
			RENDERLAYERS[baseIndex | 0b10_0000_0000] = getRenderLayer(id + "_3.png", format);
		}
		else
		{
			for(int i = 0; i < 16; ++i)
			{
				int flag = (i & extraIndexes) << 4;

				RENDERLAYERS[flag | baseIndex] = getRenderLayer(id + "_1.png", format);
				RENDERLAYERS[flag | baseIndex | 0b01_0000_0000] = getRenderLayer(id + "_2.png", format);
				RENDERLAYERS[flag | baseIndex | 0b10_0000_0000] = getRenderLayer(id + "_3.png", format);
			}
		}
	}

	public static RenderLayer getRenderLayer(String id, VertexFormat format)
	{
		Identifier identifier = new Identifier(InterestingBlocks.MOD_ID, id);
		return RenderLayer.of(id, format, 7, format.getVertexSize(), RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(identifier, false, false)).transparency(TRANSLUCENCE).build(false));
	}

	public static RenderLayer getRenderLayer(World world, BlockPos blockPos, BlockState state, Direction direction, int packedIndex)
	{
		for(Direction d : DIRECTIONS)
		{
			if(d == direction || d == direction.getOpposite())
				continue;

			if(state.get(ConnectedBlock.FACING_PROPERTIES.get(d)))
			{
				int directionId = direction.getId();
				int dId = d.getId();

				packedIndex |= PACKING_FLAGS[directionId][dId];

				BlockState adjacentState = world.getBlockState(blockPos.offset(d));

				if(adjacentState.getBlock() != ECHO_BLOCK)
					continue;

				Direction diagonal = PERPENDICULAR[directionId / 2][dId / 2];
				Direction oppositeDiagonal = diagonal.getOpposite();

				if(adjacentState.get(ConnectedBlock.FACING_PROPERTIES.get(diagonal)))
					packedIndex |= DIAGONAL_PACKING_FLAGS[directionId][DIAGONAL_INDEXES[dId][diagonal.getId()]];

				if(adjacentState.get(ConnectedBlock.FACING_PROPERTIES.get(oppositeDiagonal)))
					packedIndex |= DIAGONAL_PACKING_FLAGS[directionId][DIAGONAL_INDEXES[dId][oppositeDiagonal.getId()]];
			}
		}

		return RENDERLAYERS[packedIndex];
	}
}
