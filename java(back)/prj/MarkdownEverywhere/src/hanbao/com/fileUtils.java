package hanbao.com;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
}
