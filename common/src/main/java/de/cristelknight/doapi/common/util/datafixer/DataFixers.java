package de.cristelknight.doapi.common.util.datafixer;

import java.util.HashMap;

public class DataFixers {
    private static HashMap<String, StringPairs> DATA_FIXERS = new HashMap<>();

    public static StringPairs getDataFixerForMod(String modId){
        if(DATA_FIXERS.containsKey(modId)){
            return DATA_FIXERS.get(modId);
        }
        else {
            StringPairs pairs = new StringPairs();
            DATA_FIXERS.put(modId, pairs);
            return pairs;
        }
    }

}
