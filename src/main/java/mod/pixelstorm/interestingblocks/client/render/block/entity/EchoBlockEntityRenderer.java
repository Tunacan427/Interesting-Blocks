package mod.pixelstorm.interestingblocks.client.render.block.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import mod.pixelstorm.interestingblocks.InterestingBlocks;
import mod.pixelstorm.interestingblocks.block.ConnectedBlock;
import mod.pixelstorm.interestingblocks.block.entity.EchoBlockEntity;
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
import net.minecraft.util.math.Direction;
import org.apache.logging.log4j.Level;

public class EchoBlockEntityRenderer extends BlockEntityRenderer<EchoBlockEntity>
{
	public static final RenderPhase.Transparency TRANSLUCENCE = new RenderPhase.Transparency("translucence", () ->
																							{
																								RenderSystem.enableBlend();
																								RenderSystem.defaultBlendFunc();
																							},
																							() -> RenderSystem.disableBlend());
	public static final RenderLayer[] RENDERLAYERS = new RenderLayer[48];
	public static final Map<Direction, Map<Direction, Integer>> PACKING_OFFSETS;

	public static final String[] RENDERLAYER_NAMES = new String[767];

	public static final EchoBlock ECHO_BLOCK = Registry.BLOCK.get(new Identifier(InterestingBlocks.MOD_ID, "echo_block"));

	static
	{
		VertexFormat format = VertexFormats.POSITION_COLOR_TEXTURE;
		String path = "textures/block/echo/";

		createRenderLayers(0b0000_0000, path + "single", format);
		createRenderLayers(0b0000_0001, path + "bottom_cap", format);
		createRenderLayers(0b0000_0010, path + "left_cap", format);
		createRenderLayers(0b0000_0011, path + "doublecorner_bottomleft", format);
		createRenderLayers(0b0000_0100, path + "top_cap", format);
		createRenderLayers(0b0000_0101, path + "vertical", format);
		createRenderLayers(0b0000_0110, path + "doublecorner_topleft", format);
		createRenderLayers(0b0000_0111, path + "leftedge_corners", format);
		createRenderLayers(0b0000_1000, path + "right_cap", format);
		createRenderLayers(0b0000_1001, path + "doublecorner_bottomright", format);
		createRenderLayers(0b0000_1010, path + "horizontal", format);
		createRenderLayers(0b0000_1011, path + "bottomedge_corners", format);
		createRenderLayers(0b0000_1100, path + "doublecorner_topright", format);
		createRenderLayers(0b0000_1101, path + "rightedge_corners", format);
		createRenderLayers(0b0000_1110, path + "topedge_corners", format);
		createRenderLayers(0b0000_1111, path + "corners", format);

		createRenderLayers(0b0001_1011, path + "bottomedge_leftcorner", format);
		createRenderLayers(0b0010_0111, path + "leftedge_topcorner", format);
		createRenderLayers(0b0100_1110, path + "topedge_rightcorner", format);
		createRenderLayers(0b1000_1101, path + "rightedge_bottomcorner", format);

		createRenderLayers(0b0001_0111, path + "leftedge_bottomcorner", format);
		createRenderLayers(0b0010_1110, path + "topedge_leftcorner", format);
		createRenderLayers(0b0100_1101, path + "rightedge_topcorner", format);
		createRenderLayers(0b1000_1011, path + "bottomedge_rightcorner", format);

		createRenderLayers(0b0011_0111, path + "left_edge", format);
		createRenderLayers(0b0110_1110, path + "top_edge", format);
		createRenderLayers(0b1001_1011, path + "bottom_edge", format);
		createRenderLayers(0b1100_1101, path + "right_edge", format);

		createRenderLayers(0b0001_1111, path + "corners_no-topright", format);
		createRenderLayers(0b0010_1111, path + "corners_no-bottomright", format);
		createRenderLayers(0b0100_1111, path + "corners_no-bottomleft", format);
		createRenderLayers(0b1000_1111, path + "corners_no-topleft", format);

		createRenderLayers(0b0011_1111, path + "corners_left", format);
		createRenderLayers(0b0110_1111, path + "corners_top", format);
		createRenderLayers(0b1100_1111, path + "corners_right", format);
		createRenderLayers(0b1001_1111, path + "corners_bottom", format);

		createRenderLayers(0b0111_1111, path + "corner_topleft", format);
		createRenderLayers(0b1011_1111, path + "corner_bottomleft", format);
		createRenderLayers(0b1101_1111, path + "corner_bottomright", format);
		createRenderLayers(0b1110_1111, path + "corner_topright", format);

		createRenderLayers(0b0101_1111, path + "corners_topleft_bottomright", format);
		createRenderLayers(0b1010_1111, path + "corners_topright_bottomleft", format);
		createRenderLayers(0b1111_1111, path + "invisible", format);

		PACKING_OFFSETS = new HashMap<Direction, Map<Direction, Integer>>(6, 1);

		Map<Direction, Integer> up = new HashMap<Direction, Integer>(4, 1);
		up.put(Direction.NORTH, 2);
		up.put(Direction.EAST, 1);
		up.put(Direction.SOUTH, 0);
		up.put(Direction.WEST, 3);
		PACKING_OFFSETS.put(Direction.UP, up);

		Map<Direction, Integer> down = new HashMap<Direction, Integer>(4, 1);
		down.put(Direction.NORTH, 0);
		down.put(Direction.EAST, 1);
		down.put(Direction.SOUTH, 2);
		down.put(Direction.WEST, 3);
		PACKING_OFFSETS.put(Direction.DOWN, down);

		Map<Direction, Integer> north = new HashMap<Direction, Integer>(4, 1);
		north.put(Direction.UP, 0);
		north.put(Direction.EAST, 1);
		north.put(Direction.DOWN, 2);
		north.put(Direction.WEST, 3);
		PACKING_OFFSETS.put(Direction.NORTH, north);

		Map<Direction, Integer> east = new HashMap<Direction, Integer>(4, 1);
		east.put(Direction.UP, 0);
		east.put(Direction.NORTH, 3);
		east.put(Direction.DOWN, 2);
		east.put(Direction.SOUTH, 1);
		PACKING_OFFSETS.put(Direction.EAST, east);

		Map<Direction, Integer> south = new HashMap<Direction, Integer>(4, 1);
		south.put(Direction.UP, 2);
		south.put(Direction.EAST, 1);
		south.put(Direction.DOWN, 0);
		south.put(Direction.WEST, 3);
		PACKING_OFFSETS.put(Direction.SOUTH, south);

		Map<Direction, Integer> west = new HashMap<Direction, Integer>(4, 1);
		west.put(Direction.UP, 2);
		west.put(Direction.NORTH, 3);
		west.put(Direction.DOWN, 0);
		west.put(Direction.SOUTH, 1);
		PACKING_OFFSETS.put(Direction.WEST, west);
	}

	public EchoBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public void render(EchoBlockEntity blockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay)
	{
		renderCube(blockEntity.getWorld().getBlockState(blockEntity.getPos()), ((int) ((blockEntity.getWorld().getTime() + tickDelta) / 10f)) % 3 * 16, matrixStack.peek().getModel(), vertexConsumerProvider, blockEntity::shouldDrawSide);
	}

	private static void renderCube(BlockState state, int frame, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, Function<Direction, Boolean> shouldDrawSide)
	{
		renderFace(state, matrix, vertexConsumerProvider, 0, 1, 1, 0, 0, 0, 0, 0, Direction.NORTH, frame, shouldDrawSide);
		renderFace(state, matrix, vertexConsumerProvider, 1, 1, 1, 0, 0, 1, 1, 0, Direction.EAST, frame, shouldDrawSide);
		renderFace(state, matrix, vertexConsumerProvider, 0, 1, 0, 1, 1, 1, 1, 1, Direction.SOUTH, frame, shouldDrawSide);
		renderFace(state, matrix, vertexConsumerProvider, 0, 0, 0, 1, 0, 1, 1, 0, Direction.WEST, frame, shouldDrawSide);
		renderFace(state, matrix, vertexConsumerProvider, 0, 1, 0, 0, 0, 0, 1, 1, Direction.DOWN, frame, shouldDrawSide);
		renderFace(state, matrix, vertexConsumerProvider, 0, 1, 1, 1, 1, 1, 0, 0, Direction.UP, frame, shouldDrawSide);
	}

	private static void renderFace(BlockState state, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Direction direction, int frame, Function<Direction, Boolean> shouldDrawSide)
	{
		if(shouldDrawSide.apply(direction))
		{
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(getRenderLayer(state, direction, frame));
			vertexConsumer.vertex(matrix, x1, y1, z1).color(255, 255, 255, 255).texture(0f, 0f).next();
			vertexConsumer.vertex(matrix, x2, y1, z2).color(255, 255, 255, 255).texture(1f, 0f).next();
			vertexConsumer.vertex(matrix, x2, y2, z3).color(255, 255, 255, 255).texture(1f, 1f).next();
			vertexConsumer.vertex(matrix, x1, y2, z4).color(255, 255, 255, 255).texture(0f, 1f).next();
		}
	}

	private static void createRenderLayers(int baseIndex, String id, VertexFormat format)
	{
		// RENDERLAYERS[baseIndex] = getRenderLayer(id + "_1.png", format);
		// RENDERLAYERS[baseIndex | 0b10000] = getRenderLayer(id + "_2.png", format);
		// RENDERLAYERS[baseIndex | 0b100000] = getRenderLayer(id + "_3.png", format);

		RENDERLAYER_NAMES[baseIndex] = id + "_1.png";
		RENDERLAYER_NAMES[baseIndex | 0b01_0000_0000] = id + "_2.png";
		RENDERLAYER_NAMES[baseIndex | 0b10_0000_0000] = id + "_3.png";
	}

	public static RenderLayer getRenderLayer(String id, VertexFormat format)
	{
		Identifier identifier = new Identifier(InterestingBlocks.MOD_ID, id);
		return RenderLayer.of(id, format, 7, format.getVertexSize(), RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(identifier, false, false)).transparency(TRANSLUCENCE).build(false));
	}

	public static RenderLayer getRenderLayer(BlockState state, Direction direction, int packedIndex)
	{
		for(Direction d : Direction.values())
		{
			if(d == direction || d == direction.getOpposite())
				continue;

			packedIndex |= (state.get(ConnectedBlock.FACING_PROPERTIES.get(d)) ? 1 : 0) << PACKING_OFFSETS.get(direction).get(d);
		}

		return getRenderLayer(RENDERLAYER_NAMES[packedIndex], VertexFormats.POSITION_COLOR_TEXTURE);

		//return RENDERLAYERS[packedIndex];
	}
}
