package hanbaoaaa.xyz;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SaveDataStruct {
    long latestID;
    HashMap<String,Long> pathToIDMap;
    public long getLatestID(){
        return latestID;
    }
    public HashMap<String,Long> getPathToIDMap(){
        return pathToIDMap;
    }
    public void setLatestID(long latestID){
        this.latestID=latestID;
    }
    public void setPathToIDMap(HashMap<String,Long> pathToIDMap){
        this.pathToIDMap=pathToIDMap;
    }
    SaveDataStruct(){

    }
    SaveDataStruct(long latestID,HashMap<String,Long> pathToIDMap){
        this.pathToIDMap=pathToIDMap;
        this.latestID=latestID;
    }
}
