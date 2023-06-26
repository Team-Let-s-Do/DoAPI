package de.cristelknight.doapi.config.cloth;

import de.cristelknight.doapi.DoApiRL;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.FloatListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import me.shedaniel.clothconfig2.gui.entries.TextListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class CCUtil {
    public static Component entryName(String id, String modid) {
        return Component.translatable(modid + ".config.entry." + id);
    }
    public static Component categoryName(String id, String modid) {
        return Component.translatable(modid + ".config.category." + id);
    }

    public static Component translatableText(String id, String modid) {
        return Component.translatable(modid + ".config.text." + id);
    }

    public static BooleanListEntry createBooleanField(String modid, String id, boolean value, boolean defaultValue, ConfigEntryBuilder builder) {
        BooleanToggleBuilder e = builder.startBooleanToggle(entryName(id, modid), value)
                .setDefaultValue(defaultValue);

        return e.build();
    }

    public static IntegerListEntry createIntField(String modid, String id, int value, int defaultValue, ConfigEntryBuilder builder) {
        IntegerListEntry entry = builder.startIntField(entryName(id, modid), value)
                .setDefaultValue(defaultValue)
                .setMin(0).setMax(100)
                .build();

        return entry;
    }

    public static FloatListEntry createFloatField(String modid, String id, int value, int defaultValue, ConfigEntryBuilder builder) {
        FloatListEntry entry = builder.startFloatField(entryName(id, modid), value)
                .setDefaultValue(defaultValue)
                .setMin(0).setMax(100)
                .build();

        return entry;
    }
}
