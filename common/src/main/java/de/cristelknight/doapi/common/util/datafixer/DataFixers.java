package de.cristelknight.doapi.common.util.datafixer;

import java.util.HashMap;

public class DataFixers {

    private static boolean freezed = false;

    public static boolean isFreezed(){
        return freezed;
    }

    public static void freeze(){
        freezed = true;
    }
    private static final HashMap<String, StringPairs> DATA_FIXERS = new HashMap<>();

    public static StringPairs getOrCreate(String namespace){
        if(DATA_FIXERS.containsKey(namespace)) return DATA_FIXERS.get(namespace);
        else {
            StringPairs pairs = new StringPairs();
            DATA_FIXERS.put(namespace, pairs);
            return pairs;
        }
    }

    public static StringPairs get(String namespace){
        return DATA_FIXERS.getOrDefault(namespace, null);
    }

}
