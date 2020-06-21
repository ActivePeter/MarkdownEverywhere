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
                //�ļ�����������·��
                System.out.println(tempList[i].toString());
                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) {
                //����Ͳ��ݹ��ˣ�
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
                //����Ͳ��ݹ��ˣ�
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
                //����Ͳ��ݹ��ˣ�
            	
                //�ļ�����������·��
            	String name=tempList[i].getName();
                System.out.println(name);
                files.add(name);
                //String fileName = tempList[i].getName();
            }
        }
        return files;
    }

    /**
     * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�
     * @param   sPath ��ɾ��Ŀ¼���ļ�·��
     * @return  Ŀ¼ɾ���ɹ�����true�����򷵻�false
     */
    public static boolean deleteDirectory(String sPath) {
        //���sPath�����ļ��ָ�����β���Զ�����ļ��ָ���
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        Boolean flag = true;
        //ɾ���ļ����µ������ļ�(������Ŀ¼)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //ɾ�����ļ�
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //ɾ����Ŀ¼
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //ɾ����ǰĿ¼
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * ɾ�������ļ�
     * @param   sPath    ��ɾ���ļ����ļ���
     * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
     */
    public static boolean deleteFile(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
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
                //sb.append(directory); //�ݹ���õĵط�
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
