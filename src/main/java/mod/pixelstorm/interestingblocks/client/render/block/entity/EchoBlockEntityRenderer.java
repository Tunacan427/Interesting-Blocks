package mod.pixelstorm.interestingblocks.client.render.block.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.block.ConnectedBlock;
import mod.pixelstorm.interestingblocks.block.entity.EchoBlockEntity;
import mod.pixelstorm.interestingblocks.mixin.MixinMultiPhase;
import mod.pixelstorm.interestingblocks.mixin.MixinMultiPhaseParameters;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EchoBlockEntityRenderer extends BlockEntityRenderer<EchoBlockEntity>
{
	public static final RenderPhase.Alpha HALF_ALPHA = new RenderPhase.Alpha(0.5f);

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

	public static final Block ECHO_BLOCK = Registry.BLOCK.get(new Identifier(InterestingBlocks.MOD_ID, "echo_block"));

	public static final VertexFormat FORMAT = VertexFormats.POSITION_TEXTURE;

	private static final Direction[] DIRECTIONS = Direction.values();

	private static final RenderPhase[][] LAYERPHASES = new RenderPhase[786][15];

	private static final Identifier[] IDENTIFIERS = new Identifier[786];

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

		Profiler profiler = world.getProfiler();

		profiler.push("EchoBlock");

		profiler.push("Prep");

		BlockPos pos = blockEntity.getPos();
		BlockState state = blockEntity.getCachedState();

		if(state.getBlock() != ECHO_BLOCK)
			return;

		renderCube(profiler, blockEntity,
					world,
					pos,
					state,
					((int) ((world.getTime() + tickDelta) / 10f)) % 3 * 256,
					matrixStack.peek().getModel(),
					vertexConsumerProvider);

		profiler.pop();
		profiler.pop();
	}

	private static void renderCube(Profiler profiler, EchoBlockEntity blockEntity, World world, BlockPos blockPos, BlockState state, int frame, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider)
	{
		profiler.swap("Render");

		BufferBuilder vertexConsumer = (BufferBuilder) vertexConsumerProvider.getBuffer(null);

		profiler.push("Prep");

		renderFace(profiler, vertexConsumer, blockEntity, world, blockPos, state, Direction.NORTH,	frame, matrix, vertexConsumerProvider, 0, 1, 1, 0, 0, 0, 0, 0);
		renderFace(profiler, vertexConsumer, blockEntity, world, blockPos, state, Direction.EAST,		frame, matrix, vertexConsumerProvider, 1, 1, 1, 0, 0, 1, 1, 0);
		renderFace(profiler, vertexConsumer, blockEntity, world, blockPos, state, Direction.SOUTH,	frame, matrix, vertexConsumerProvider, 0, 1, 0, 1, 1, 1, 1, 1);
		renderFace(profiler, vertexConsumer, blockEntity, world, blockPos, state, Direction.WEST,		frame, matrix, vertexConsumerProvider, 0, 0, 0, 1, 0, 1, 1, 0);
		renderFace(profiler, vertexConsumer, blockEntity, world, blockPos, state, Direction.DOWN,		frame, matrix, vertexConsumerProvider, 0, 1, 0, 0, 0, 0, 1, 1);
		renderFace(profiler, vertexConsumer, blockEntity, world, blockPos, state, Direction.UP,		frame, matrix, vertexConsumerProvider, 0, 1, 1, 1, 1, 1, 0, 0);

		blockEntity.isDirty = false;

		profiler.pop();
	}

	private static void renderFace(Profiler profiler, BufferBuilder vertexConsumer, EchoBlockEntity blockEntity, World world, BlockPos blockPos, BlockState state, Direction direction, int frame, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4)
	{
		if(blockEntity.isDirty)
			blockEntity.cachedIndexes[direction.getId()] = getRenderLayerIndex(world, blockPos, state, direction);

		int index = blockEntity.cachedIndexes[direction.getId()];
		if(index == 255 || state.isSideInvisible(world.getBlockState(blockPos.offset(direction)), direction))
			return;

		if(vertexConsumer.isBuilding())
			((VertexConsumerProvider.Immediate) vertexConsumerProvider).draw();

		profiler.swap("Vertex");

		vertexConsumer.begin(7, FORMAT);
		vertexConsumer.vertex(matrix, x1, y1, z1).texture(0, 0).next();
		vertexConsumer.vertex(matrix, x2, y1, z2).texture(1, 0).next();
		vertexConsumer.vertex(matrix, x2, y2, z3).texture(1, 1).next();
		vertexConsumer.vertex(matrix, x1, y2, z4).texture(0, 1).next();
		vertexConsumer.end();

		//RenderPhase[] phases = LAYERPHASES[index | frame];

		profiler.swap("Setup draw");

		//for(RenderPhase phase : phases)
		//	phase.startDrawing();

		setupDrawState(IDENTIFIERS[index | frame], profiler);

		profiler.swap("Draw");

		BufferRenderer.draw(vertexConsumer);

		profiler.swap("Teardown draw");

		//for(RenderPhase phase : phases)
		//	phase.endDrawing();

		teardownDrawState(profiler);

		profiler.swap("Prep");
	}

	private static void setupDrawState(Identifier identifier, Profiler profiler)
	{
		// Texture
		profiler.push("Texture");
		RenderSystem.enableTexture();
		MinecraftClient.getInstance().getTextureManager().bindTexture(identifier);

		// Transparency
		profiler.swap("Transparency");
		RenderSystem.disableBlend();

		// ShadeModel
		profiler.swap("ShadeModel");
		RenderSystem.shadeModel(7424);

		// Alpha
		profiler.swap("Alpha");
		RenderSystem.enableAlphaTest();
		RenderSystem.alphaFunc(516, 0.1f);

		// DepthTest
		profiler.swap("DepthTest");
		RenderSystem.enableDepthTest();
		RenderSystem.depthFunc(515);

		// Cull
		profiler.swap("Cull");
		RenderSystem.enableCull();

		// Fog
		profiler.swap("Fog");
		RenderSystem.enableFog();

		profiler.pop();
	}

	private static void teardownDrawState(Profiler profiler)
	{
		// Alpha
		profiler.push("Alpha");
		RenderSystem.disableAlphaTest();

		// DepthTest
		profiler.swap("DepthTest");
		RenderSystem.disableDepthTest();

		// Cull
		profiler.swap("Cull");
		RenderSystem.disableCull();

		// Fog
		profiler.swap("Fog");
		RenderSystem.disableFog();

		profiler.pop();
	}

	private static void createRenderLayers(int baseIndex, String id)
	{
		createRenderLayers(baseIndex, id, 0);
	}

	private static void createRenderLayers(int baseIndex, String id, int extraIndexes)
	{
		if(extraIndexes == 0)
		{
			IDENTIFIERS[baseIndex] = new Identifier(InterestingBlocks.MOD_ID, id + "_1.png");
			IDENTIFIERS[baseIndex | 0b01_0000_0000] = new Identifier(InterestingBlocks.MOD_ID, id + "_2.png");
			IDENTIFIERS[baseIndex | 0b10_0000_0000] = new Identifier(InterestingBlocks.MOD_ID, id + "_3.png");

			//getRenderPhases(getRenderLayer(id + "_1.png")).toArray(LAYERPHASES[baseIndex]);
			//getRenderPhases(getRenderLayer(id + "_2.png")).toArray(LAYERPHASES[baseIndex | 0b01_0000_0000]);
			//getRenderPhases(getRenderLayer(id + "_3.png")).toArray(LAYERPHASES[baseIndex | 0b10_0000_0000]);
		}
		else
		{
			for(int i = 0; i < 16; ++i)
			{
				int flag = (i & extraIndexes) << 4;

				IDENTIFIERS[flag | baseIndex] = new Identifier(InterestingBlocks.MOD_ID, id + "_1.png");
				IDENTIFIERS[flag | baseIndex | 0b01_0000_0000] = new Identifier(InterestingBlocks.MOD_ID, id + "_2.png");
				IDENTIFIERS[flag | baseIndex | 0b10_0000_0000] = new Identifier(InterestingBlocks.MOD_ID, id + "_3.png");

				//getRenderPhases(getRenderLayer(id + "_1.png")).toArray(LAYERPHASES[flag | baseIndex]);
				//getRenderPhases(getRenderLayer(id + "_2.png")).toArray(LAYERPHASES[flag | baseIndex | 0b01_0000_0000]);
				//getRenderPhases(getRenderLayer(id + "_3.png")).toArray(LAYERPHASES[flag | baseIndex | 0b10_0000_0000]);
			}
		}
	}

	private static ImmutableList<RenderPhase> getRenderPhases(RenderLayer layer)
	{
		return ((MixinMultiPhaseParameters) ((Object) ((MixinMultiPhase) layer).getPhases())).getPhases();
	}

	public static RenderLayer getRenderLayer(String id)
	{
		Identifier identifier = new Identifier(InterestingBlocks.MOD_ID, id);
		return RenderLayer.of(id, FORMAT, 7, FORMAT.getVertexSize(), RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(identifier, false, false)).alpha(HALF_ALPHA).build(false));
	}

	public static int getRenderLayerIndex(World world, BlockPos blockPos, BlockState state, Direction direction)
	{
		int packedIndex = 0;
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

		return packedIndex;
	}
}
