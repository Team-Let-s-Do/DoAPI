package de.cristelknight.doapi.common.util.datafixer;

import de.cristelknight.doapi.DoApi;

import java.util.HashMap;

public class StringPairs {

    private HashMap<String, String> ids;

    protected StringPairs() {
        ids = new HashMap<>();
    }

    public boolean containsOldID(String oldID){
        return ids.containsKey(oldID);
    }

    public void addPair(String oldID, String newID){
        if(oldID == null || newID == null){
            DoApi.LOGGER.error("ID: " + oldID + " or ID: " + newID + " is null!");
            return;
        }
        if(ids.containsKey(oldID)){
            DoApi.LOGGER.error("ID: " + oldID + "is already added to a datafixer.");
            return;
        }
        ids.put(oldID, newID);
    }

    public String getNewId(String oldID){
        return ids.get(oldID);
    }

}
