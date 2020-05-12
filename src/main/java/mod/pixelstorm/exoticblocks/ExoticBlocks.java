package mod.pixelstorm.exoticblocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import mod.pixelstorm.exoticblocks.block.*;
import mod.pixelstorm.exoticblocks.block.entity.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
		log(Level.INFO, "Starting initialization.");

		registerItemGroup("cosmetic", registerBlocks(), 19);

		log(Level.INFO, "Finished initialization.");
	}

	private ItemGroup registerItemGroup(String identifier, List<ItemStack> items, int iconIndex)
	{
		return registerItemGroup(identifier, items, items.get(iconIndex));
	}

	private ItemGroup registerItemGroup(String identifier, List<ItemStack> items, ItemStack icon)
	{
		if(items.isEmpty())
			log(Level.WARN, "Itemgroup '" + identifier + "' recieved no items!");
		else
			log(Level.INFO, "Registering itemgroup '" + identifier + "' with " + items.size() + " items.");

		return FabricItemGroupBuilder.create(new Identifier(MOD_ID, identifier))
											.icon(() -> icon)
											.appendItems(stacks -> stacks.addAll(items))
											.build();
	}

	private List<ItemStack> registerBlocks()
	{
		List<ItemStack> items = new ArrayList<ItemStack>(20);

		items.add(registerBlock("cosmetic_nether_portal_block", new CosmeticNetherPortalBlock()));

		items.add(registerBlock("inverted_block", new InvertedBlock()));

		items.add(registerBlock("hardlight/living_rainbow", new HardlightBlock(HardlightBlock.DEFAULT_SETTINGS.lightLevel(6))));
		items.add(registerBlock("hardlight/black", new HardlightBlock()));
		items.add(registerBlock("hardlight/blue", new HardlightBlock()));
		items.add(registerBlock("hardlight/brown", new HardlightBlock()));
		items.add(registerBlock("hardlight/cyan", new HardlightBlock()));
		items.add(registerBlock("hardlight/gray", new HardlightBlock()));
		items.add(registerBlock("hardlight/green", new HardlightBlock()));
		items.add(registerBlock("hardlight/light_blue", new HardlightBlock()));
		items.add(registerBlock("hardlight/light_gray", new HardlightBlock()));
		items.add(registerBlock("hardlight/lime", new HardlightBlock()));
		items.add(registerBlock("hardlight/magenta", new HardlightBlock()));
		items.add(registerBlock("hardlight/orange", new HardlightBlock()));
		items.add(registerBlock("hardlight/pink", new HardlightBlock()));
		items.add(registerBlock("hardlight/purple", new HardlightBlock()));
		items.add(registerBlock("hardlight/red", new HardlightBlock()));
		items.add(registerBlock("hardlight/white", new HardlightBlock()));
		items.add(registerBlock("hardlight/yellow", new HardlightBlock()));

		items.add(registerBlock("hardlight/shadow", new HardlightBlock()));
		items.add(registerBlock("hardlight/light", new HardlightBlock()));
		items.add(registerBlock("hardlight/vivid_red", new HardlightBlock()));
		items.add(registerBlock("hardlight/vivid_green", new HardlightBlock()));
		items.add(registerBlock("hardlight/vivid_blue", new HardlightBlock()));

		items.add(registerBlockEntity("cosmetic_end_portal_block",
							new CosmeticEndPortalBlock(),
							CosmeticEndPortalBlockEntity::new,
							(blockEntityType) -> CosmeticEndPortalBlockEntity.blockEntityType = blockEntityType));

		log(Level.INFO, "Registered " + items.size() + " blocks.");

		return items;
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
