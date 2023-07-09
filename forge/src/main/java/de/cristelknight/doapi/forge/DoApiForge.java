package de.cristelknight.doapi.forge;

import de.cristelknight.doapi.DoApi;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DoApi.MOD_ID)
public class DoApiForge {

    public DoApiForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(DoApi.MOD_ID, modEventBus);
        DoApi.init();
        modEventBus.addListener(this::commonSetup);

        /*
        if(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK) instanceof BlockSettingsLock){
            DoApi.LOGGER.error("IS instance of");
        }
        else DoApi.LOGGER.error("is NOT instance of");

         */


    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //DoApi.commonInit();
    }
}
