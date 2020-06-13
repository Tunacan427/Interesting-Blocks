package mod.pixelstorm.interestingblocks.client.render.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import mod.pixelstorm.interestingblocks.InterestingBlocks;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

@Environment(EnvType.CLIENT)
public class EchoBlockSpriteManager
{
	private static final Block ECHO_BLOCK = Registry.BLOCK.get(new Identifier(InterestingBlocks.MOD_ID, "echo_block"));

	private static final Direction[] DIRECTIONS = Direction.values();

	private static final Direction[][] PERPENDICULAR =	{
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

	private static final int[][] PACKING_FLAGS =	{	// Value of 0 = should never be accessed
														{ 0, 0, 4, 1, 8, 2 },
														{ 0, 0, 4, 1, 8, 2 },
														{ 4, 1, 0, 0, 2, 8 },
														{ 4, 1, 0, 0, 8, 2 },
														{ 4, 1, 8, 2, 0, 0 },
														{ 4, 1, 2, 8, 0, 0 }
													};

	private static final int[][] DIAGONAL_PACKING_FLAGS =	{
																{ 32, 16, 128, 64 },
																{ 32, 16, 128, 64 },
																{ 128, 64, 32, 16 },
																{ 16, 32, 64, 128 },
																{ 128, 64, 32, 16 },
																{ 16, 32, 64, 128 }
															};

	private static final int[][] DIAGONAL_INDEXES =	{	// Value of 9 = should never be accessed
														{ 9, 9, 1, 2, 2, 1 },
														{ 9, 9, 0, 3, 3, 0 },
														{ 1, 0, 9, 9, 3, 0 },
														{ 2, 3, 9, 9, 2, 1 },
														{ 2, 3, 3, 2, 9, 9 },
														{ 1, 0, 0, 1, 9, 9 }
													};

	private static final SpriteIdentifier[] SPRITE_IDENTIFIERS =
		{
			getSpriteIdentifier("single"),
			getSpriteIdentifier("corners"),
			getSpriteIdentifier("invisible"),
			getSpriteIdentifier("vertical"),
			getSpriteIdentifier("horizontal"),
			getSpriteIdentifier("bottom_cap"),
			getSpriteIdentifier("left_cap"),
			getSpriteIdentifier("top_cap"),
			getSpriteIdentifier("right_cap"),
			getSpriteIdentifier("doublecorner_bottomleft"),
			getSpriteIdentifier("doublecorner_topleft"),
			getSpriteIdentifier("doublecorner_topright"),
			getSpriteIdentifier("doublecorner_bottomright"),
			getSpriteIdentifier("bottomleft_corner"),
			getSpriteIdentifier("topleft_corner"),
			getSpriteIdentifier("topright_corner"),
			getSpriteIdentifier("bottomright_corner"),
			getSpriteIdentifier("leftedge_corners"),
			getSpriteIdentifier("bottomedge_corners"),
			getSpriteIdentifier("rightedge_corners"),
			getSpriteIdentifier("topedge_corners"),
			getSpriteIdentifier("bottomedge_leftcorner"),
			getSpriteIdentifier("leftedge_topcorner"),
			getSpriteIdentifier("topedge_rightcorner"),
			getSpriteIdentifier("rightedge_bottomcorner"),
			getSpriteIdentifier("leftedge_bottomcorner"),
			getSpriteIdentifier("topedge_leftcorner"),
			getSpriteIdentifier("rightedge_topcorner"),
			getSpriteIdentifier("bottomedge_rightcorner"),
			getSpriteIdentifier("left_edge"),
			getSpriteIdentifier("top_edge"),
			getSpriteIdentifier("bottom_edge"),
			getSpriteIdentifier("right_edge"),
			getSpriteIdentifier("corners_no-topright"),
			getSpriteIdentifier("corners_no-bottomright"),
			getSpriteIdentifier("corners_no-bottomleft"),
			getSpriteIdentifier("corners_no-topleft"),
			getSpriteIdentifier("corners_left"),
			getSpriteIdentifier("corners_top"),
			getSpriteIdentifier("corners_right"),
			getSpriteIdentifier("corners_bottom"),
			getSpriteIdentifier("corner_topleft"),
			getSpriteIdentifier("corner_bottomleft"),
			getSpriteIdentifier("corner_bottomright"),
			getSpriteIdentifier("corner_topright"),
			getSpriteIdentifier("corners_topleft_bottomright"),
			getSpriteIdentifier("corners_topright_bottomleft")
		};

	private static final Sprite[] SPRITES = new Sprite[256];

	private static Map<BlockPos, Sprite[]> states = new HashMap<BlockPos, Sprite[]>();

	private static Function<SpriteIdentifier, Sprite> spriteGetter;

	public static void updateSpriteGetter(Function<SpriteIdentifier, Sprite> newGetter)
	{
		spriteGetter = newGetter;
		cacheAllSprites();
	}

	public static SpriteIdentifier[] getSpriteIdentifiers()
	{
		return SPRITE_IDENTIFIERS;
	}

	public static Sprite[] updatePos(BlockState state, BlockPos pos, BlockView view)
	{
		Sprite[] sprites = states.get(pos);

		if(sprites == null)
			sprites = new Sprite[6];

		for(Direction d : DIRECTIONS)
			sprites[d.getId()] = SPRITES[getStateFlags(d, state, pos, view)];

		states.put(pos, sprites);
		return sprites;
	}

	public static Sprite getSprite(Direction dir, BlockState state, BlockPos pos, BlockView view)
	{
		Sprite[] sprites = states.get(pos);

		if(sprites == null)
			sprites = updatePos(state, pos, view);

		return sprites[dir.getId()];
	}

	private static SpriteIdentifier getSpriteIdentifier(String from)
	{
		return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(InterestingBlocks.MOD_ID, "block/echo/" + from));
	}

	private static void cacheAllSprites()
	{
		cacheSprites(0b0000_0000, SPRITE_IDENTIFIERS[0]);
		cacheSprites(0b0000_1111, SPRITE_IDENTIFIERS[1]);
		cacheSprites(0b1111_1111, SPRITE_IDENTIFIERS[2]);

		cacheSprites(0b0000_0101, SPRITE_IDENTIFIERS[3], 0b1111);
		cacheSprites(0b0000_1010, SPRITE_IDENTIFIERS[4], 0b1111);

		cacheSprites(0b0000_0001, SPRITE_IDENTIFIERS[5], 0b1111);
		cacheSprites(0b0000_0010, SPRITE_IDENTIFIERS[6], 0b1111);
		cacheSprites(0b0000_0100, SPRITE_IDENTIFIERS[7], 0b1111);
		cacheSprites(0b0000_1000, SPRITE_IDENTIFIERS[8], 0b1111);

		cacheSprites(0b0000_0011, SPRITE_IDENTIFIERS[9], 0b1110);
		cacheSprites(0b0000_0110, SPRITE_IDENTIFIERS[10], 0b1101);
		cacheSprites(0b0000_1100, SPRITE_IDENTIFIERS[11], 0b1011);
		cacheSprites(0b0000_1001, SPRITE_IDENTIFIERS[12], 0b0111);

		cacheSprites(0b0001_0011, SPRITE_IDENTIFIERS[13], 0b1111);
		cacheSprites(0b0010_0110, SPRITE_IDENTIFIERS[14], 0b1111);
		cacheSprites(0b0100_1100, SPRITE_IDENTIFIERS[15], 0b1111);
		cacheSprites(0b1000_1001, SPRITE_IDENTIFIERS[16], 0b1111);

		cacheSprites(0b0000_0111, SPRITE_IDENTIFIERS[17], 0b1100);
		cacheSprites(0b0000_1011, SPRITE_IDENTIFIERS[18], 0b0110);
		cacheSprites(0b0000_1101, SPRITE_IDENTIFIERS[19], 0b0011);
		cacheSprites(0b0000_1110, SPRITE_IDENTIFIERS[20], 0b1001);

		cacheSprites(0b0001_1011, SPRITE_IDENTIFIERS[21], 0b0110);
		cacheSprites(0b0010_0111, SPRITE_IDENTIFIERS[22], 0b1100);
		cacheSprites(0b0100_1110, SPRITE_IDENTIFIERS[23], 0b1001);
		cacheSprites(0b1000_1101, SPRITE_IDENTIFIERS[24], 0b0011);

		cacheSprites(0b0001_0111, SPRITE_IDENTIFIERS[25], 0b1100);
		cacheSprites(0b0010_1110, SPRITE_IDENTIFIERS[26], 0b1001);
		cacheSprites(0b0100_1101, SPRITE_IDENTIFIERS[27], 0b0011);
		cacheSprites(0b1000_1011, SPRITE_IDENTIFIERS[28], 0b0110);

		cacheSprites(0b0011_0111, SPRITE_IDENTIFIERS[29], 0b1100);
		cacheSprites(0b0110_1110, SPRITE_IDENTIFIERS[30], 0b1001);
		cacheSprites(0b1001_1011, SPRITE_IDENTIFIERS[31], 0b0110);
		cacheSprites(0b1100_1101, SPRITE_IDENTIFIERS[32], 0b0011);

		cacheSprites(0b0001_1111, SPRITE_IDENTIFIERS[33]);
		cacheSprites(0b0010_1111, SPRITE_IDENTIFIERS[34]);
		cacheSprites(0b0100_1111, SPRITE_IDENTIFIERS[35]);
		cacheSprites(0b1000_1111, SPRITE_IDENTIFIERS[36]);

		cacheSprites(0b0011_1111, SPRITE_IDENTIFIERS[37]);
		cacheSprites(0b0110_1111, SPRITE_IDENTIFIERS[38]);
		cacheSprites(0b1100_1111, SPRITE_IDENTIFIERS[39]);
		cacheSprites(0b1001_1111, SPRITE_IDENTIFIERS[40]);

		cacheSprites(0b0111_1111, SPRITE_IDENTIFIERS[41]);
		cacheSprites(0b1011_1111, SPRITE_IDENTIFIERS[42]);
		cacheSprites(0b1101_1111, SPRITE_IDENTIFIERS[43]);
		cacheSprites(0b1110_1111, SPRITE_IDENTIFIERS[44]);

		cacheSprites(0b0101_1111, SPRITE_IDENTIFIERS[45]);
		cacheSprites(0b1010_1111, SPRITE_IDENTIFIERS[46]);
	}

	private static void cacheSprites(int baseIndex, SpriteIdentifier id)
	{
		cacheSprites(baseIndex, id, 0);
	}

	private static void cacheSprites(int baseIndex, SpriteIdentifier id, int extraIndexes)
	{
		if(extraIndexes == 0)
			SPRITES[baseIndex] = spriteGetter.apply(id);
		else
		{
			for(int i = 0; i < 16; ++i)
				SPRITES[(i & extraIndexes) << 4 | baseIndex] = spriteGetter.apply(id);
		}
	}

	private static int getStateFlags(Direction direction, BlockState state, BlockPos blockPos, BlockView view)
	{
		int packedIndex = 0;
		for(Direction d : DIRECTIONS)
		{
			if(d == direction || d == direction.getOpposite())
				continue;

			BlockPos adjacentPos = blockPos.offset(d);

			if(view.getBlockState(adjacentPos).getBlock() != ECHO_BLOCK)
				continue;

			int directionId = direction.getId();
			int dId = d.getId();

			packedIndex |= PACKING_FLAGS[directionId][dId];

			Direction diagonal = PERPENDICULAR[directionId / 2][dId / 2];
			Direction oppositeDiagonal = diagonal.getOpposite();

			if(view.getBlockState(adjacentPos.offset(diagonal)).getBlock() == ECHO_BLOCK)
				packedIndex |= DIAGONAL_PACKING_FLAGS[directionId][DIAGONAL_INDEXES[dId][diagonal.getId()]];

			if(view.getBlockState(adjacentPos.offset(oppositeDiagonal)).getBlock() == ECHO_BLOCK)
				packedIndex |= DIAGONAL_PACKING_FLAGS[directionId][DIAGONAL_INDEXES[dId][oppositeDiagonal.getId()]];
		}

		return packedIndex;
	}
}
