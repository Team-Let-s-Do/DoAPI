package de.cristelknight.doapi.common.util.datafixer;

import de.cristelknight.doapi.DoApi;

import java.util.HashMap;

public class StringPairs {

    private final HashMap<String, String> paths;



    protected StringPairs() {
        paths = new HashMap<>();
    }

    public boolean containsOldPath(String oldPath){
        return paths.containsKey(oldPath);
    }

    public void add(String oldPath, String newPathOrRL){
        if(oldPath == null || newPathOrRL == null){
            DoApi.LOGGER.error("Old path: " + oldPath + " or new path/RL: " + newPathOrRL + " is null!");
            return;
        }
        if(paths.containsKey(oldPath)){
            DoApi.LOGGER.error("Path: " + oldPath + "is already added to a datafixer.");
            return;
        }
        paths.put(oldPath, newPathOrRL);
    }

    public String getNewPathOrRL(String oldPath){
        return paths.get(oldPath);
    }

}
