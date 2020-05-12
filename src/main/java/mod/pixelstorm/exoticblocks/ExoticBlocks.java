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
import net.minecraft.sound.BlockSoundGroup;
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
		registerBlock("cosmetic_nether_portal_block", new CosmeticNetherPortalBlock(FabricBlockSettings.of(Material.PORTAL).lightLevel(11).strength(0.8F).noCollision().sounds(BlockSoundGroup.GLASS)));
		registerBlock("inverted_block", new InvertedBlock(FabricBlockSettings.of(Material.STONE).strength(1.8F).nonOpaque()));

		registerBlock("living_rainbow_block", new HardlightBlock());
		registerBlock("hardlight/black", new HardlightBlock());
		registerBlock("hardlight/blue", new HardlightBlock());
		registerBlock("hardlight/brown", new HardlightBlock());
		registerBlock("hardlight/cyan", new HardlightBlock());
		registerBlock("hardlight/gray", new HardlightBlock());
		registerBlock("hardlight/green", new HardlightBlock());
		registerBlock("hardlight/light_blue", new HardlightBlock());
		registerBlock("hardlight/light_gray", new HardlightBlock());
		registerBlock("hardlight/lime", new HardlightBlock());
		registerBlock("hardlight/magenta", new HardlightBlock());
		registerBlock("hardlight/orange", new HardlightBlock());
		registerBlock("hardlight/pink", new HardlightBlock());
		registerBlock("hardlight/purple", new HardlightBlock());
		registerBlock("hardlight/red", new HardlightBlock());
		registerBlock("hardlight/white", new HardlightBlock());
		registerBlock("hardlight/yellow", new HardlightBlock());

		registerBlockEntity("cosmetic_end_portal_block",
							new CosmeticEndPortalBlock(FabricBlockSettings.of(Material.PORTAL).lightLevel(15).strength(0.8F).sounds(BlockSoundGroup.WOOL)),
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
