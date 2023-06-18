package de.cristelknight.doapi.config.builtin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.cristelknight.doapi.config.jankson.config.CommentedConfig;
import net.minecraft.Util;

import java.util.HashMap;


public record RBConfig(boolean recipeBookOpen, boolean craftableToggle)
        implements CommentedConfig<RBConfig> {

    private static RBConfig INSTANCE = null;

    public static final RBConfig DEFAULT = new RBConfig(false, true);

    public static final Codec<RBConfig> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.BOOL.fieldOf("recipe_book_open").orElse(DEFAULT.recipeBookOpen).forGetter(c -> c.recipeBookOpen),
                    Codec.BOOL.fieldOf("craftable_toggle").orElse(DEFAULT.craftableToggle).forGetter(c -> c.craftableToggle)
            ).apply(builder, RBConfig::new)
    );

    @Override
    public HashMap<String, String> getComments() {
        return Util.make(new HashMap<>(), map -> {
            map.put("recipe_book_open", """
                    Is the recipe book open""");
            map.put("craftable_toggle", """
                    Only show craftable items""");
                }
        );
    }

    @Override
    public String getHeader() {
        return """
               Do Api Recipe Book values""";
    }

    @Override
    public String getSubPath() {
        return "doapi/recipebook";
    }

    @Override
    public RBConfig getInstance() {

        return INSTANCE;
    }

    @Override
    public RBConfig getDefault() {
        return DEFAULT;
    }

    @Override
    public Codec<RBConfig> getCodec() {
        return CODEC;
    }

    @Override
    public boolean isSorted() {
        return false;
    }

    @Override
    public void setInstance(RBConfig instance) {
        INSTANCE = instance;
    }

    public static void setCraftableToggle(boolean bl){
        RBConfig c = RBConfig.DEFAULT.getConfig();
        c.setInstance(new RBConfig(c.recipeBookOpen(), bl));
        c.getConfig(true, true);
    }

    public static void setRecipeBookOpenToggle(boolean bl){
        RBConfig c = RBConfig.DEFAULT.getConfig();
        c.setInstance(new RBConfig(bl, c.craftableToggle()));
        c.getConfig(true, true);
    }
}
