package de.cristelknight.doapi.forge.client;

import de.cristelknight.doapi.client.terraform.TerraformBoatClientHelper;
import de.cristelknight.doapi.forge.terraform.TerraformApiForge;
import de.cristelknight.doapi.forge.terraform.boat.impl.client.TerraformBoatClientInitializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterTextureAtlasSpriteLoadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = TerraformApiForge.TERRAFORM_MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TerraformClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        TerraformBoatClientInitializer.init(event);
    }
}
