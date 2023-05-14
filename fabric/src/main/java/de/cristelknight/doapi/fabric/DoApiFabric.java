package de.cristelknight.doapi.fabric;

import de.cristelknight.doapi.DoApi;
import net.fabricmc.api.ModInitializer;

public class DoApiFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DoApi.init();
        DoApi.commonTerraformInit();
    }
}
