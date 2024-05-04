package de.cristelknight.doapi.common.util.datafixer;

import java.util.HashMap;

public class DataFixers {
    private static final HashMap<String, StringPairs> DATA_FIXERS = new HashMap<>();

    public static StringPairs getOrCreate(String updateFromNamespace){
        if(DATA_FIXERS.containsKey(updateFromNamespace)) return DATA_FIXERS.get(updateFromNamespace);
        else {
            StringPairs pairs = new StringPairs();
            DATA_FIXERS.put(updateFromNamespace, pairs);
            return pairs;
        }
    }

    public static StringPairs get(String namespace){
        return DATA_FIXERS.getOrDefault(namespace, null);
    }

}
