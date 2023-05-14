package de.cristelknight.doapi;

import net.minecraft.resources.ResourceLocation;


public class DoApiRL extends ResourceLocation {

    public DoApiRL(String path) {
        super(DoApi.MOD_ID, path);
    }

    public static String asString(String path) {
        return (DoApi.MOD_ID + ":" + path);
    }
}
