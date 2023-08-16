package de.cristelknight.doapi.fabric;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.fabric.common.registry.DoApiRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class DoApiFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DoApi.init();
        DoApi.commonTerraformInit();

        boolean portingLibLoaded = FabricLoader.getInstance().isModLoaded("porting_lib");
        boolean portingLibBaseLoaded = FabricLoader.getInstance().isModLoaded("porting_lib_base");

        if (!portingLibLoaded && !portingLibBaseLoaded) {
            DoApiRecipes.loadClass();
        }
    }
}
