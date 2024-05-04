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

    public void addPair(String oldPath, String newPath){
        if(DataFixers.isFreezed()){
            DoApi.LOGGER.error("Data fixer registry is already freezed. Cannot add oldPath " + oldPath + " and newPath " + newPath + ".");
            return;
        }
        if(oldPath == null || newPath == null){
            DoApi.LOGGER.error("Path: " + oldPath + " or Path: " + newPath + " is null!");
            return;
        }
        if(paths.containsKey(oldPath)){
            DoApi.LOGGER.error("Path: " + oldPath + "is already added to a datafixer.");
            return;
        }
        paths.put(oldPath, newPath);
    }

    public String getNewPath(String oldPath){
        return paths.get(oldPath);
    }

}
