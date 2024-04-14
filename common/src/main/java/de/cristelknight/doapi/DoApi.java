package de.cristelknight.doapi;

import de.cristelknight.doapi.common.registry.DoApiBlockEntityTypes;
import de.cristelknight.doapi.common.registry.DoApiBlocks;
import de.cristelknight.doapi.common.registry.DoApiEntityTypes;
import de.cristelknight.doapi.common.registry.DoApiRecipes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DoApi {
    public static final String MOD_ID = "doapi";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void init() {
        DoApiBlocks.init();
        DoApiBlockEntityTypes.init();
        DoApiEntityTypes.init();
        DoApiRecipes.init();
    }

}
