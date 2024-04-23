package de.cristelknight.doapi.forge.client;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.client.DoApiClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = DoApi.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DoApiClientEvents {

    private static boolean initialized = false;
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        DoApiClient.onClientInit();
    }

    @SubscribeEvent
    public static void preClientSetup(RegisterEvent event) {
        if(!initialized){
            DoApiClient.preClientInit();
            initialized = true;
        }
    }

}
