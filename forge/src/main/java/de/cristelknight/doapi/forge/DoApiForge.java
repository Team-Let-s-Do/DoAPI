package de.cristelknight.doapi.forge;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.forge.common.packs.RepositorySourceMaker;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.event.AddPackFindersEvent;
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
        modEventBus.addListener(this::injectPackRepositories);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //DoApi.commonInit();
    }

    private void injectPackRepositories(AddPackFindersEvent event) {
        event.addRepositorySource(new RepositorySourceMaker(event.getPackType().equals(PackType.CLIENT_RESOURCES)));
    }
}
