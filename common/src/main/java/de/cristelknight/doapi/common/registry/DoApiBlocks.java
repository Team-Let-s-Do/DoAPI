package de.cristelknight.doapi.common.registry;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.DoApiRL;
import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.common.block.StandardBlock;
import de.cristelknight.doapi.common.block.StandardWallBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.function.Supplier;

public class DoApiBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(DoApi.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    public static final Supplier<Block> STANDARD = registerWithoutItem("standard", () -> new StandardBlock(properties(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD)));
    public static final Supplier<Block> WALL_STANDARD = registerWithoutItem("wall_standard", () -> new StandardWallBlock(properties(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD).dropsLike(STANDARD.get())));


    public static void init(){
        BLOCKS.register();
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return Util.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new DoApiRL(path), block);
    }

    public static BlockBehaviour.Properties properties(float strength) {
        return properties(strength, strength);
    }

    public static BlockBehaviour.Properties properties(float breakSpeed, float explosionResist) {
        return BlockBehaviour.Properties.of().strength(breakSpeed, explosionResist);
    }
}
