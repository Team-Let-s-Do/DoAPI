package de.cristelknight.doapi.fabric;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.terraform.boat.item.TerraformBoatItemHelper;
import net.fabricmc.api.ModInitializer;

public class DoApiFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DoApi.init();
        TerraformBoatItemHelper.registerDispenserBehaviours();
    }
}
