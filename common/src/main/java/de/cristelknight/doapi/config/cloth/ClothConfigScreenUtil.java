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

@Environment(EnvType.CLIENT)
public class ClothConfigScreenUtil {
    public static Component entryName(String id, String modid) {
        return Component.translatable(modid + ".config.entry." + id);
    }
    public static Component categoryName(String id, String modid) {
        return Component.translatable(modid + ".config.category." + id);
    }

    private BooleanListEntry createBooleanField(String modid, String id, boolean value, boolean defaultValue, ConfigEntryBuilder builder) {
        BooleanToggleBuilder e = builder.startBooleanToggle(entryName(id, modid), value)
                .setDefaultValue(defaultValue);

        return e.build();
    }

    private IntegerListEntry createIntField(String modid, String id, int value, int defaultValue, ConfigEntryBuilder builder) {
        IntegerListEntry entry = builder.startIntField(entryName(id, modid), value)
                .setDefaultValue(defaultValue)
                .setMin(0).setMax(100)
                .build();

        return entry;
    }

    private FloatListEntry createFloatField(String modid, String id, int value, int defaultValue, ConfigEntryBuilder builder) {
        FloatListEntry entry = builder.startFloatField(entryName(id, modid), value)
                .setDefaultValue(defaultValue)
                .setMin(0).setMax(100)
                .build();

        return entry;
    }

    private void linkButtons(String modid, ConfigCategory category, ConfigEntryBuilder builder, String dcLink, String cfLink, Screen configScreen){
        TextListEntry tle = builder.startTextDescription(Component.literal(" ")).build();
        category.addEntry(tle);
        category.addEntry(new LinkEntry(entryName(modid,"dc"), buttonWidget -> Minecraft.getInstance().setScreen(new ConfirmLinkScreen(confirmed -> {
            if (confirmed) {
                Util.getPlatform().openUri(dcLink);
            }
            Minecraft.getInstance().setScreen(configScreen); }, dcLink, true)), new DoApiRL("textures/gui/dc.png"), 3));
        category.addEntry(tle);
        category.addEntry(new LinkEntry(entryName(modid, "cf"), buttonWidget -> Minecraft.getInstance().setScreen(new ConfirmLinkScreen(confirmed -> {
            if (confirmed) {
                Util.getPlatform().openUri(cfLink);
            }
            Minecraft.getInstance().setScreen(configScreen); }, cfLink, true)), new DoApiRL("textures/gui/cf.png"), 10));
    }
}
