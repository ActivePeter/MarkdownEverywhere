package hanbaoaaa.xyz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class fileUtils {
	public static List<String> getFiles(String path){
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
                //文件名，不包含路径
                System.out.println(tempList[i].toString());
                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            }
        }
        return files;
    }
	public static List<String> getFileNames(String path) throws UnsupportedEncodingException{
        List<String> files = new ArrayList<String>();
        File file = new File(new String(path.getBytes("utf-8"), "utf-8"));
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
            	
            	String name=tempList[i].getName();
                System.out.println(name);
                files.add(name);
                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            }
        }
        return files;
    }
	public static List<String> getDirectoryNames(String path) throws IOException{
        List<String> files = new ArrayList<String>();
        File file = new File(new String(path.getBytes("utf-8"), "utf-8"));
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            	
                //文件名，不包含路径
            	String name=tempList[i].getName();
                System.out.println(name);
                files.add(name);
                //String fileName = tempList[i].getName();
            }
        }
        return files;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        Boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    public static String getFileTree(File file,String context) {

        File[] listFiles = file.listFiles();
        StringBuffer sb= new StringBuffer();
        sb.append('[');
        for (File file2 : listFiles) {

            if (file2.isDirectory()) {
                //System.out.println(file2.getName());
                String directory=getFileTree(file2,context);
                if(directory.length()>0){
                    sb.append("{\"filename\":\"")
                            .append(file2.getName())
                            .append("\",\"children\":")
                            .append(directory)
                            .append("},");
                }
                //sb.append(directory); //递归调用的地方
            } else {
                String filename=file2.getName();
                int index = filename.lastIndexOf(context);
                if(filename.length()-index==context.length()){
                    sb.append("{\"filename\":\"")
                            .append(file2.getName().substring(0,index))
                            .append("\"},");
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
    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}
