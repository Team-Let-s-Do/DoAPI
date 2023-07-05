package de.cristelknight.doapi.forge;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.client.DoApiClient;
import dev.architectury.platform.Platform;
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

        if(Platform.getEnv().isClient()){
            DoApiClient.preClientInit();
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //DoApi.commonInit();
    }
}
