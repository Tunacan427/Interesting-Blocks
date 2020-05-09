package mod.pixelstorm.exoticblocks;

import mod.pixelstorm.exoticblocks.block.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExoticBlocks implements ModInitializer
{
	public static Logger LOGGER = LogManager.getLogger();

	public static final String MOD_ID = "exotic_blocks";
	public static final String MOD_NAME = "Exotic Blocks";

	@Override
	public void onInitialize()
	{
		log(Level.INFO, "Initializing");

		registerBlock("living_rainbow_block", new LivingRainbowBlock());
	}

	public void registerBlock(String identifier, Block block)
	{
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, identifier), block);

		BlockItem blockItem = new BlockItem(block, new Item.Settings());
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, identifier), blockItem);
	}

	public static void log(Level level, String message)
	{
		LOGGER.log(level, "["+MOD_NAME+"] " + message);
	}
}
