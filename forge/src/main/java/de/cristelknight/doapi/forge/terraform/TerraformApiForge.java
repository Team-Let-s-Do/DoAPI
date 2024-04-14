package de.cristelknight.doapi.forge.terraform;

import de.cristelknight.doapi.forge.terraform.boat.impl.TerraformBoatInitializer;
import de.cristelknight.doapi.terraform.boat.item.TerraformBoatItemHelper;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TerraformApiForge.TERRAFORM_MOD_ID)
public class TerraformApiForge {

    public static final String TERRAFORM_MOD_ID = "terraform";
    public TerraformApiForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(TERRAFORM_MOD_ID, modEventBus);

        TerraformBoatInitializer.init();
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(TerraformBoatItemHelper::registerDispenserBehaviours);
    }
}
