package mod.pixelstorm.exoticblocks;

import net.fabricmc.api.ModInitializer;
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
		//TODO: Initializer
	}

	public static void log(Level level, String message)
	{
		LOGGER.log(level, "["+MOD_NAME+"] " + message);
	}
}
