package com.tx.filedown.common.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileUtil {
    public static String dateToString(Date dateDate, String formate) {
        SimpleDateFormat formatter = new SimpleDateFormat(formate);
        String dateString = formatter.format(dateDate);
        return dateString;
    }
    //创建文件
    public static void createFile(File filename) {
        try {
            if(!filename.exists()) {
                filename.createNewFile();
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    //写入txt 内容不被覆盖 追加写入
    public static boolean filechaseWrite(String Content,String filepath) {
        boolean flag=false;
        try {
            //new OutputStreamWriter (new FileOutputStream (filePath,true),"UTF-8"));
            // FileWriter fw=new FileWriter(filepath,true);
              Writer fw = new BufferedWriter(
                                             new OutputStreamWriter(
                                                     new FileOutputStream(filepath), "UTF-8"));

            fw.write(Content);
            fw.flush();
            fw.close();
            flag=true;
        }catch (Exception e) {
            //
            e.printStackTrace();
        }
        return flag;
    }


    //写入txt内容 覆盖原内容
    public static boolean writetxtfile(String Content,String filepath) {
        boolean flag=false;
        try {
            //写入的txt文档的路径
            PrintWriter pw=new PrintWriter(filepath);
            //写入的内容
            pw.write(Content);
            pw.flush();
            pw.close();
            flag=true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //读取txt内容
    public static String readtxtFile(File file) {
        String sResult="";
        try {
            InputStreamReader reader=new InputStreamReader(new FileInputStream(file),"gbk");
            BufferedReader br=new BufferedReader(reader);
            String s=null;
            while((s=br.readLine())!=null) {
                sResult+=s+"\n";
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return sResult;
    }


    /**
     * 获取目录一级目录
     * @param path
     * @param fileNameList
     */
    public static void getRootDirName(String path,List<String> fileNameList) {
        //ArrayList<String> files = new ArrayList<String>();
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(null!=tempList) {
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isDirectory()) {
                    //System.out.println("文件夹：" + tempList[i]);
                    fileNameList.add(tempList[i].getName());
                }
            }
        }
        return;
    }
    /**
     * 获取某个文件夹下的所有文件
     *
     * @param fileNameList 存放文件名称的list
     * @param path 文件夹的路径
     * @return
     */
    public static void getAllFileName(String path, List<String> fileNameList) {
        //ArrayList<String> files = new ArrayList<String>();
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile() && !tempList[i].isHidden()) {
//              System.out.println("文     件：" + tempList[i]);
                fileNameList.add(tempList[i].toString());
                // fileNameList.add(tempList[i].getName());
            }
            if (tempList[i].isDirectory()) {
                // System.out.println("文件夹：" + tempList[i]);
                getAllFileName(tempList[i].getAbsolutePath(),fileNameList);
            }
        }
        return;
    }
}
