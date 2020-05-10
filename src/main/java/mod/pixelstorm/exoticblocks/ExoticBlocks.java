package mod.pixelstorm.exoticblocks;

import java.util.function.Consumer;
import java.util.function.Supplier;
import mod.pixelstorm.exoticblocks.block.*;
import mod.pixelstorm.exoticblocks.block.entity.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		registerBlock("living_rainbow_block", new LivingRainbowBlock(FabricBlockSettings.of(Material.STONE).strength(1.8F).lightLevel(9)));
		registerBlockEntity("cosmetic_end_portal_block",
							new CosmeticEndPortalBlock(FabricBlockSettings.of(Material.PORTAL).lightLevel(15).strength(0.8F)),
							CosmeticEndPortalBlockEntity::new,
							(blockEntityType) -> CosmeticEndPortalBlockEntity.blockEntityType = blockEntityType);

		log(Level.INFO, "Finished initialization.");
	}

	private ItemStack registerBlock(String identifier, Block block)
	{
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, identifier), block);

		BlockItem blockItem = new BlockItem(block, new Item.Settings());
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, identifier), blockItem);

		return new ItemStack(blockItem);
	}

	private <T extends BlockEntity> ItemStack registerBlockEntity(String identifier, Block block, Supplier<? extends T> blockEntitySupplier, Consumer<BlockEntityType> blockEntityConsumer)
	{
		// Register Block
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, identifier), block);

		// Register BlockEntity
		BlockEntityType blockEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, identifier), BlockEntityType.Builder.create(blockEntitySupplier, block).build(null));

		blockEntityConsumer.accept(blockEntityType);

		// Instantiate and register BlockItem
		BlockItem blockItem = new BlockItem(block, new Item.Settings());
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, identifier), blockItem);

		return new ItemStack(blockItem);
	}

	public static void log(Level level, String message)
	{
		LOGGER.log(level, "["+MOD_NAME+"] " + message);
	}
}
