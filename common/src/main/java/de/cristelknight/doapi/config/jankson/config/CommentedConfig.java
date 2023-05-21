package de.cristelknight.doapi.config.jankson.config;

import com.mojang.serialization.Codec;
import de.cristelknight.doapi.DoApiExpectPlatform;
import de.cristelknight.doapi.config.jankson.ConfigUtil;
import de.cristelknight.doapi.config.jankson.JanksonOps;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.HashMap;

public interface CommentedConfig<T extends Record> {

    String getSubPath();

    T getInstance();

    T getDefault();

    Codec<T> getCodec();

    @Nullable HashMap<String, String> getComments();

    @Nullable String getHeader();

    boolean isSorted();

    void setInstance(T instance);


    default T getConfig() {
        return getConfig(false, false);
    }


    default Path getConfigPath() {
        return DoApiExpectPlatform.getConfigDirectory().resolve(getSubPath() + ".json5");
    }

    default T getConfig(boolean serialize, boolean recreate) {
        if (getInstance() == null || serialize || recreate) {
            setInstance(readConfig(recreate));
        }
        return getInstance();
    }


    private T readConfig(boolean recreate) {
        if (!getConfigPath().toFile().exists() || recreate) {
            createConfig(getConfigPath());
        }
        return ConfigUtil.readConfig(getConfigPath(), getCodec(), JanksonOps.INSTANCE);
    }

    private void createConfig(Path path) {
        ConfigUtil.createConfig(path, getCodec(), getMap(getComments()), JanksonOps.INSTANCE, getDefault(), isSorted(), getComment(getHeader()));
    }

    private String getComment(String header){
        if(header != null){
            return "/*\n" + header + "\n*/";
        }
        return null;
    }

    private HashMap<String, String> getMap(HashMap<String, String> comments){
        if(comments == null) comments = new HashMap<>();
        return comments;
    }
}
