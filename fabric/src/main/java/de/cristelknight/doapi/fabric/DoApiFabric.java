package de.cristelknight.doapi.fabric;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.fabric.common.registry.DoApiRecipes;
import net.fabricmc.api.ModInitializer;

public class DoApiFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DoApi.init();
        DoApi.commonTerraformInit();
        DoApiRecipes.loadClass();
    }
}
