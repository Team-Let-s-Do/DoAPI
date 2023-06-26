package de.cristelknight.doapi.common.registry;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.DoApiRL;
import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import de.cristelknight.doapi.common.entity.ChairEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class DoApiEntityTypes {


    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(DoApi.MOD_ID, Registries.ENTITY_TYPE);


    public static final RegistrySupplier<EntityType<ChairEntity>> CHAIR = create("chair", () -> EntityType.Builder.of(ChairEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build(new DoApiRL("chair").toString()));



    private static <T extends EntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(path, type);
    }

    public static void init() {
        ENTITY_TYPES.register();
    }
}
