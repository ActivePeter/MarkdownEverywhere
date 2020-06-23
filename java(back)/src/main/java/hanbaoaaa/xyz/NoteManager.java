package hanbaoaaa.xyz;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchResult;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NoteManager {
    static NoteManager noteManager;
    String prjPath;
    String repoPath;
    String fileTreeJSON;
    String currentCode;
    long latestID;
    HashMap<String,Long> pathToIDMap;
    ConcurrentHashMap<Long,String> IDToPathMap;
    boolean hookHandling=false;
    public String getFileTreeJSON() {
        return fileTreeJSON;
    }

    public static NoteManager getInstance(){
        if(noteManager==null){
            System.out.println("new Notemanager"+File.separator);

            noteManager=new NoteManager();
            //noteManager.loadConfig();
        }
        return noteManager;
    }
    NoteManager(){
        this.pathToIDMap=new HashMap<String, Long>();
        this.IDToPathMap=new ConcurrentHashMap<Long, String>();
        this.loadConfig();
    }
    private void loadConfig(){
        prjPath=this.getClass().getClassLoader().getResource(".").getPath();
        System.out.println(prjPath);
        File json = new File(prjPath+"config.json");
        ObjectMapper mapper = new ObjectMapper();//此处引入的是jackson中的ObjectMapper类
        Map<String, Object> dataNode = null;
        try {
            dataNode = mapper.readValue(json, Map.class);
            System.out.println("loadConfig success");
        } catch (IOException e) {
            System.out.println("loadConfig failed");
            e.printStackTrace();
        }
        if(dataNode!=null){

            repoPath= (String) dataNode.get("path");
            System.out.println("repoPath:"+repoPath);
            initRepo();
        }
    }
    public enum PullNotesState {
        Changed,NoChange,Unknown,ERROR
    }
    public synchronized void onHooked(){
        hookHandling=true;
        System.out.printf("onHooked:");
        switch (pullNotes()){//检查更新数据
            case Changed:
                File file=new File( prjPath+"gitRepo" );
                fileTreeJSON=this.checkFileAndBuildMap("",file,".md");
                this.saveData();
                System.out.println("Changed");
                break;
            case NoChange:
                System.out.println("NoChange");
                break;
            case Unknown:
                System.out.println("Unkown pull State");
                break;
            case ERROR:
                System.out.println("ERROR when pull");
                break;
        }
        hookHandling=false;
    }
    void initRepo(){

        try {
            File file=new File( prjPath+"gitRepo" );
            Git git = Git.open( file );
            System.out.println("already has file");
            boolean loadSucc=this.loadData();//读取latestID和pathToIDMap
            switch (pullNotes()){//检查更新数据
                case Changed:
                    fileTreeJSON=this.checkFileAndBuildMap("",file,".md");
                    this.saveData();
                    break;
                case NoChange:
                    fileTreeJSON=this.checkFileAndBuildMap("",file,".md");
                    if(!loadSucc){
                        this.saveData();
                    }
                    break;
                case Unknown:
                    System.out.println("Unkown pull State");
                    break;
                case ERROR:
                    System.out.println("ERROR when pull");
                    break;
            }
            //遍历，更新map和json还有latestID

            //fileTreeJSON=fileUtils.getFileTree(file,".md");
        } catch (IOException e) {
            if(e.getMessage().indexOf("repository not found")!=-1){//
                fileUtils.deleteDirectory(prjPath+"gitRepo");
                try {
                    File file=new File(prjPath+"gitRepo");
                    Git git= Git.cloneRepository()
                            .setURI(repoPath)
                            .setDirectory(file)
                            .call();
                    Iterable<RevCommit> iterable=git.log().call();
                    Iterator<RevCommit> iter=iterable.iterator();
                    String code =iter.next().getName();
                    System.out.println("new Clone!"+code);
                    this.currentCode=code;
                    fileTreeJSON=this.checkFileAndBuildMap("",file,".md");
                    this.saveData();
                } catch (GitAPIException e1) {
                    e1.printStackTrace();
                }
            }

            e.printStackTrace();
        }

    }
    public String checkFileAndBuildMapRunner(String fathername,File file,String context,HashMap<String,Long> newMap){
        File[] listFiles = file.listFiles();
        StringBuffer sb= new StringBuffer();
        sb.append('[');
//        String encoding = System.getProperty("sun.jnu.encoding");
//        System.out.println(encoding);
        Collections.sort(Arrays.asList(listFiles), new Comparator<File>(){
            @Override
            public int compare(File o1, File o2) {
                if(o1.isDirectory() && o2.isFile())
                    return -1;
                if(o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (File file2 : listFiles) {
            String filename=file2.getName();


//            try {
//                //将系统编码encoding转换为utf-8编码
//                filename=new String(filename.getBytes("UTF-8"),"UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            if (file2.isDirectory()) {
                //System.out.println(file2.getName());
                String directory=checkFileAndBuildMapRunner(fathername+"/"+filename,file2,context,newMap);
                if(directory.length()>0){
                    sb.append("{\"filename\":\"")
                            .append(filename)
                            .append("\",\"children\":")
                            .append(directory)
                            .append("},");
                }
                //sb.append(directory); //递归调用的地方
            } else {

                int index = filename.lastIndexOf(context);
                if(filename.length()-index==context.length()){
//                    System.out.println();
                    String filePath=fathername+"/"+filename;
                    long id;
                    if(pathToIDMap.containsKey(filePath)){
                        id=pathToIDMap.get(filePath);
                    }else{
                        latestID++;
                        id=latestID;
                        //pathToIDMap.put(filePath,id);
                    }
                    newMap.put(filePath,id);
                    IDToPathMap.put(id,filePath);
                    sb.append("{\"filename\":\"")
                            .append(filename.substring(0,index))
                            .append("\",\"id\":")
                            .append(id)
                            .append("},");
                }
                //System.out.println(file2.getName());

            }

        }
        if(sb.length()==1){
            return "";
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(']');
        return sb.toString();
    }
    public String checkFileAndBuildMap(String fathername,File file,String context){
        IDToPathMap.clear();
        HashMap<String,Long> newMap=new HashMap<String, Long>();
        String json=checkFileAndBuildMapRunner(fathername,file,context,newMap);
        StringBuffer sb=new StringBuffer();
        sb.append("[\"").append(this.currentCode).append("\",").append(json).append("]");
        this.pathToIDMap=newMap;
        return sb.toString();
    }
    void saveData(){//每次更新完数据列表，保存一次当前记录序号
        SaveDataStruct saveDataStruct=new SaveDataStruct(this.latestID,this.pathToIDMap);
        ObjectMapper mapper = new ObjectMapper();
        try {
            OutputStream outputStream = new FileOutputStream(new File(prjPath+"save.json"));
            mapper.writeValue(outputStream, saveDataStruct);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }//after checkFileAndBuildMap
    boolean loadData(){//firstInit
        File json = new File(prjPath+"save.json");
        ObjectMapper mapper = new ObjectMapper();//此处引入的是jackson中的ObjectMapper类
        SaveDataStruct saveDataStruct = null;
        if(!json.exists()){
            return false;
        }
        try {
            saveDataStruct = mapper.readValue(json, SaveDataStruct.class);
            System.out.println("loadSave success");
        } catch (IOException e) {
            System.out.println("loadSave failed");
            e.printStackTrace();
        }
        if(saveDataStruct!=null){

            this.latestID=  saveDataStruct.getLatestID();
            this.pathToIDMap=saveDataStruct.getPathToIDMap();
            return true;
        }else {
            return false;
        }
    }
    public synchronized PullNotesState pullNotes(){
        File file=new File( prjPath+"gitRepo" );
        try {
            Git git = Git.open( file );
            System.out.println("Starting fetch");
            FetchResult result = git.fetch().setCheckFetchedObjects(true).call();



            PullResult call = git.pull().call();

            String callString =call.toString();
            //System.out.println("Messages: " + callString);

            Iterable<RevCommit> iterable=git.log().call();
            Iterator<RevCommit> iter=iterable.iterator();
            this.currentCode=iter.next().getName();

            if(callString.contains("Fast-forward")){
                System.out.println("Fast-forward");
                return PullNotesState.Changed;
            }else if(callString.contains("Already-up-to-date")){
                System.out.println("Already-up-to-date");
                return PullNotesState.NoChange;
            }else{
                System.out.println("unknown state pull:" + call);
                return PullNotesState.Unknown;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRemoteException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return PullNotesState.ERROR;
    }
    public synchronized String readMD(long id,String code){
        if(hookHandling){
            return null;
        }
        if(!code.equals(this.currentCode)){
            return "codeNotSame";
        }
        if(IDToPathMap.containsKey(id)){
            String mdString=fileUtils.readToString(prjPath+"gitRepo/"+IDToPathMap.get(id));

            return mdString;
        }else{
            System.out.println("no id");
            return null;
        }

    }
}
