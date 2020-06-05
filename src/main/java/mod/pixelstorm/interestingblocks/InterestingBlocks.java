package mod.pixelstorm.interestingblocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import mod.pixelstorm.interestingblocks.block.*;
import mod.pixelstorm.interestingblocks.block.entity.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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

public class InterestingBlocks implements ModInitializer
{
	public static Logger LOGGER = LogManager.getLogger();

	public static final String MOD_ID = "interesting_blocks";
	public static final String MOD_NAME = "Interesting Blocks";

	@Override
	public void onInitialize()
	{
		log(Level.INFO, "Starting initialization.");

		registerItemGroup("cosmetic", registerBlocks(), 16);

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

		items.add(registerBlock("inverted/black", new InvertedBlock()));
		items.add(registerBlock("inverted/blue", new InvertedBlock()));
		items.add(registerBlock("inverted/brown", new InvertedBlock()));
		items.add(registerBlock("inverted/cyan", new InvertedBlock()));
		items.add(registerBlock("inverted/gray", new InvertedBlock()));
		items.add(registerBlock("inverted/green", new InvertedBlock()));
		items.add(registerBlock("inverted/light_blue", new InvertedBlock()));
		items.add(registerBlock("inverted/light_gray", new InvertedBlock()));
		items.add(registerBlock("inverted/lime", new InvertedBlock()));
		items.add(registerBlock("inverted/magenta", new InvertedBlock()));
		items.add(registerBlock("inverted/orange", new InvertedBlock()));
		items.add(registerBlock("inverted/pink", new InvertedBlock()));
		items.add(registerBlock("inverted/purple", new InvertedBlock()));
		items.add(registerBlock("inverted/red", new InvertedBlock()));
		items.add(registerBlock("inverted/white", new InvertedBlock()));
		items.add(registerBlock("inverted/yellow", new InvertedBlock()));
		items.add(registerBlock("inverted/living_rainbow", new InvertedBlock()));

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

		FabricBlockSettings vividSettings = HardlightBlock.DEFAULT_SETTINGS.lightLevel(6);
		items.add(registerBlock("hardlight/living_rainbow", new HardlightBlock(vividSettings)));
		items.add(registerBlock("hardlight/vivid_black", new HardlightBlock(vividSettings)));
		items.add(registerBlock("hardlight/vivid_white", new HardlightBlock(vividSettings)));
		items.add(registerBlock("hardlight/vivid_red", new HardlightBlock(vividSettings)));
		items.add(registerBlock("hardlight/vivid_green", new HardlightBlock(vividSettings)));
		items.add(registerBlock("hardlight/vivid_blue", new HardlightBlock(vividSettings)));

		items.add(registerBlock("cosmetic_nether_portal_block", new CosmeticNetherPortalBlock()));

		items.add(registerBlockEntity("cosmetic_end_portal_block",
							new CosmeticEndPortalBlock(),
							CosmeticEndPortalBlockEntity::new,
							(blockEntityType) -> CosmeticEndPortalBlockEntity.blockEntityType = blockEntityType));

		items.add(registerBlockEntity("skybox_block",
							new SkyboxBlock(),
							SkyboxBlockEntity::new,
							(blockEntityType) -> SkyboxBlockEntity.blockEntityType = blockEntityType));

		items.add(registerBlockEntity("echo_block",
							new EchoBlock(),
							EchoBlockEntity::new,
							(blockEntityType) -> EchoBlockEntity.blockEntityType = blockEntityType));

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
