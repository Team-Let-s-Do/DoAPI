package de.cristelknight.doapi.forge.common.packs;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.PackSource;
import org.jetbrains.annotations.NotNull;

public class BuiltinResourcePackSource implements PackSource {
    private final String modId;
    public BuiltinResourcePackSource(String modId) {
        this.modId = modId;
    }

    @Override
    public boolean shouldAddAutomatically() {
        return true;
    }

    @Override
    public @NotNull Component decorate(@NotNull Component packName) {
        return Component.translatable("pack.nameAndSource", packName, Component.translatable("pack.source.builtinMod", modId)).withStyle(ChatFormatting.GRAY);
    }
}
