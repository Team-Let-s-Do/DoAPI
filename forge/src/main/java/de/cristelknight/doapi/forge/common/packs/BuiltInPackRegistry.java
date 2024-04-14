package de.cristelknight.doapi.forge.common.packs;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.DoApi;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BuiltInPackRegistry {
    public static final Map<ResourceLocation, Pair<PackResources, Boolean>> packResources = new HashMap<>();

    public static void getPacks(Consumer<Pack> consumer, boolean client){
        if(packResources.isEmpty()) return;
        for (Map.Entry<ResourceLocation, Pair<PackResources, Boolean>> entry : packResources.entrySet()) {
            Pair<PackResources, Boolean> pair = entry.getValue();
            PackResources packResources = pair.getFirst();
            ResourceLocation id = entry.getKey();

            if(packResources == null){
                DoApi.LOGGER.error("Pack for location: {} is null, skipping it!", id);
                continue;
            }
            if(client && packResources.getNamespaces(PackType.CLIENT_RESOURCES).isEmpty()){
                DoApi.LOGGER.debug(packResources.packId() + " has no assets, skipping it!");
                continue;
            }
            else if (!client && packResources.getNamespaces(PackType.SERVER_DATA).isEmpty()){
                DoApi.LOGGER.debug(packResources.packId() + " has no data, skipping it!");
                continue;
            }
            Pack pack = Pack.readMetaAndCreate(
                    packResources.packId(),
                    Component.literal(id.getNamespace() + "/" + id.getPath()),
                    pair.getSecond(),
                    ignored -> packResources,
                    PackType.SERVER_DATA,
                    Pack.Position.TOP,
                    new BuiltinResourcePackSource(id.getNamespace())
            );
            if (pack != null) {
                consumer.accept(pack);
            }
            else DoApi.LOGGER.error(packResources.packId() + " couldn't be created");
        }
    }
}
