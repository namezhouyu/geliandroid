package com.geli.m.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;


import com.geli.m.app.GlobalData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * 文件操作工具
 *
 */
public class FilesUtils {

    private FilesUtils(){
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * SDCard是否存<p>
     *     true:挂载了 false:没有<br>
     */
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    /**
     * 获取SD卡路径<p>
     * 我华为手机的是这个:/storage/emulated/0/   <br>
     *     /mnt/sdcard/                       <p>
     * "SD卡"绝对路径+File.separator（"/"调用这个API拿到"正斜杠"还是"反斜杠"）
     * @return
     */
    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 不存在就创建"文件夹"<p>
     * 拿到后使用： files.exists() 再判断一次，比如文件创建失败
     *
     * @param path      文件夹路径
     */
    public static File createFile(String path){
        if(externalMemoryAvailable()){
            //LogUtils.i("挂载了");
        }else {
            //LogUtils.i("没有挂载");
            return null;
        }
        File dir = null;
        File file = null;
        if(!TextUtils.isEmpty(path)) {
            dir = new File(path);
            if (!dir.exists()) {                              // 不存在文件夹，先创建文件夹
                try {

                    if (dir.mkdirs()) {                         // 按照指定的路径创建文件夹

                    } else {
                        LogUtils.i("创建文件失败:" + dir.getAbsolutePath());
                    }

                } catch (Exception e) {
                    LogUtils.i("创建文件失败:" + dir.getAbsolutePath(),  e);
                }
            }
        }
        return file;           // 拿到后使用： files.exists() 再判断一次，比如文件创建失败
    }


    /**
     * 不存在就创建"文件夹"<p>
     * 拿到后使用： files.exists() 再判断一次，比如文件创建失败
     *
     * @param path      文件夹路径
     */
    public static File createFile(String path, String fileName){
        if(externalMemoryAvailable()){
            //LogUtils.i("挂载了");
        }else {
            //LogUtils.i("没有挂载");
            return null;
        }
        File dir = null;
        File file = null;
        if(!TextUtils.isEmpty(path)) {
            dir = new File(path);
            if (!dir.exists()) {                              // 不存在文件夹，先创建文件夹
                try {

                    if (dir.mkdirs()) {                         // 按照指定的路径创建文件夹
                        file = new File(path, fileName);
                        if(!file.exists()) {
                            file.createNewFile();
                            broadCreateFile(file);
                        }
                    } else {
                        LogUtils.i("创建文件夹失败:" + dir.getAbsolutePath());
                    }

                } catch (Exception e) {
                    LogUtils.i("创建文件夹失败:" + dir.getAbsolutePath(),  e);
                }
            }else {
                file = new File(path, fileName);
                if(!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogUtils.i("创建文件夹失败:" + file.getAbsolutePath(), e);
                    }
                    broadCreateFile(file);
                }
            }
        }
        return file;           // 拿到后使用： files.exists() 再判断一次，比如文件创建失败
    }


    /**
     * 不存在就创建"文件夹"<p>
     * 拿到后使用： files.exists() 再判断一次，比如文件创建失败
     * @param path      文件夹路径
     */
    public static File createDir(String path){
        if(externalMemoryAvailable()){
            //LogUtils.i("挂载了");
        }else {
            //LogUtils.i("没有挂载");
            return null;
        }
        File files = null;
        if(!TextUtils.isEmpty(path)) {
            files = new File(path);
            if (!files.exists()) {                              // 文件夹不存在，才创建
                try {
                    if (files.mkdirs()) {
                        broadCreateFile(files);
                    } else {
                        LogUtils.i("创建文件夹失败:" + files.getAbsolutePath());
                    }

                } catch (Exception e) {
                    LogUtils.i("创建文件夹失败:" + files.getAbsolutePath(), e);
                }
            }else{
                if(!files.isDirectory()){
                    files.delete();
                    files.mkdirs();
                }
            }
        }
        return files;           // 拿到后使用： files.exists() 再判断一次，比如文件创建失败
    }


    /**
     * 不存在就创建"文件夹"<p>
     * 拿到后使用： files.exists() 再判断一次，比如文件创建失败
     * @param path      文件夹路径
     */
    public static File createDirTemp(String path){
        if(externalMemoryAvailable()){
            //LogUtils.i("挂载了");
        }else {
            //LogUtils.i("没有挂载");
            return null;
        }
        File files = null;
        if(!TextUtils.isEmpty(path)) {
            files = new File(path);
            if (!files.exists()) {                              // 文件夹不存在，才创建
                try {
                    if (files.mkdir()) {
                        broadCreateFile(files);
                    } else {
                        LogUtils.i("创建文件夹失败:" + files.getAbsolutePath());
                    }

                } catch (Exception e) {
                    LogUtils.i("创建文件夹失败:" + files.getAbsolutePath(), e);
                }
            }else{
                if(!files.isDirectory()){
                    files.delete();
                    files.mkdir();
                }
            }
        }
        return files;           // 拿到后使用： files.exists() 再判断一次，比如文件创建失败
    }

    /*----------------------------------------------------------------------------------------*/
    /*                           文件复制  -- 流                                               */
    /*----------------------------------------------------------------------------------------*/

    //    如果有同名文件，自动覆盖。不存在时自动建立。
    //    FileOutputStream的默认构造方法是直接覆盖掉原来的文件，
    //    而FileOutputStream(File file, boolean append) 的构造方法
    //    如果后面的append为true的时候就是追加到尾部而不是直接覆盖了。

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {                  // 源文件存在时
                InputStream inStream = new FileInputStream(oldPath);      //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[2014];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;            //字节数 文件大小
                    // System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
        }  catch (Exception e) {
            LogUtils.i("复制单个文件操作出错:" ,e);
            e.printStackTrace();
        }

        broadCreateFile(newPath);
    }

    /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {


        try {
            createDir(newPath);                                         //如果文件夹不存在 则建立新文件夹
            File oldDir = new File(oldPath);
            String[] file = oldDir.list();
            File temp = null;

            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){                   // 检查，拼接时候的 "/"
                    temp = new File(oldPath + file[i]);
                }else{
                    temp = new File(oldPath+ File.separator + file[i]);
                }


                if(temp.isFile()){                                      // 如果是 -- 子文件
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(
                            newPath + File.separator + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }

                if(temp.isDirectory()){                                 // 如果是 -- 子文件夹
                    copyFolder(oldPath + File.separator +file[i] ,newPath+File.separator+file[i]);
                }
            }
        }  catch (Exception e) {
            LogUtils.i("复制整个文件夹内容操作出错:", e);
            e.printStackTrace();
        }
    }

    /**
     * 将 InputStream 写入到指定的文件
     * @param is        InputStream
     * @param path  将 InputStream 写入到指定的文件 如：f:/fqf.txt
     * @return boolean
     */
    public static File InputStream2File(InputStream is, String path) throws IOException {
        int byteSum = 0;
        int byteRead = 0;
        FileOutputStream fs = null;

        try {
            File file = new File(path);
            File fileDir = new File(getFileDir(file.getAbsolutePath()));
            if (!fileDir.exists()){
                fileDir.mkdirs();
            }
            if (is != null) {                                           // InputStream流不为空
                fs = new FileOutputStream(file);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteRead = is.read(buffer)) != -1) {
                    byteSum += byteRead;                                    //字节数 文件大小
                    // System.out.println(byteSum);
                    fs.write(buffer, 0, byteRead);
                }
            }

            return file;
        }  catch (IOException e) {
            LogUtils.i("将InputStream写入到指定的文件，操作出错:", e);
            e.printStackTrace();
        } finally {
            if(is != null)
                is.close();
            if(fs != null)
                fs.close();
        }
        return null;
    }


    /**
     * 复制资源(assets目录下的文件)到指定路径
     * @param path          指定路径
     * @param imageName     图片文件名(文件名)
     */
    public static void assets2File(Context context, String path, String imageName){
        File files = createDir(path);               // 创建"文件夹"
        File file = new File(files, imageName);
        if(file.exists()){
            file.delete();
        }
        file = new File(files, imageName);

        InputStream stream = null;
        FileOutputStream fos = null;

        //2,输入流读取assets目录下的文件
        try {
            // getAssets()拿到"资产目录"的文件夹（工程目录下的assets目录）
            // ***打开"dbName名字的文件"    （拿到他的输入流）
            stream = context.getAssets().open(imageName);

            //3,将读取的内容写入到指定文件夹的文件中去
            // ***拿到"file文件"的"输出流"
            fos = new FileOutputStream(file);

            //4,每次的读取内容大小
            byte[] bs = new byte[1024];
            int temp = -1;
            while( (temp = stream.read(bs))!=-1){	// 將"输入流"（stream）读到"bs"
                fos.write(bs, 0, temp);				// 將"bs"写到"fos"（输出流）
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.i("复制资源(assets目录下的文件)到指定路径，操作出错:", e);
        }finally{
            if(stream!=null && fos!=null){	// "流"非等于"null",说明没有关闭
                try {
                    // 关闭流
                    stream.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*----------------------------------------------------------------------------------------
    //                              删除文件
    //----------------------------------------------------------------------------------------*/
    /**
     * 删除文件,广播一下
     * @param path
     */
    public static void deleteFile(String path){
        File file = new File(path);
        if(file.exists()){
            file.delete();
            broadCreateFile(file);
        }
    }


    /**
     * 删除文件,广播一下
     * @param file
     */
    public static void deleteFile(File file){
        if(file.exists()){
            file.delete();
            broadCreateFile(file);
        }
    }

    /**
     * 删除文件,不广播
     * @param file
     */
    public static void deleteFileNoBroadCreate(File file){
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 删除文件夹和文件夹里面的文件
     * @param path
     */
    public static void deleteDir(String path) {
        File dir = new File(path);
        deleteDirWihtFile(dir);
        broadCreateFile(dir);
    }

    /**
     * 删除文件夹和文件夹里面的文件
     * @param dir
     */
    public static void deleteDir(File dir) {
        deleteDirWihtFile(dir);
        broadCreateFile(dir);
    }

    private static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                deleteFile(file);                  // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file);        // 递规的方式删除文件夹
        }
        deleteFile(dir);                        // 删除目录本身
    }


    /*----------------------------------------------------------------------------------------
    //                              广播
    //----------------------------------------------------------------------------------------*/

    public static void broadCreateFile(File file){
        try {
//            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); // 在电脑上马上可以看到创建的文件
//            Uri uri = Uri.fromFile(file);
//            intent.setData(uri);
//            GlobalData.getInstance().sendBroadcast(intent);


            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://" + file.getAbsolutePath()));
            GlobalData.getInstance().sendBroadcast(intent);
        }catch (Exception e){
            LogUtils.i("广播失败：", e);
        }
    }


    public static void broadCreateFile(String filePath){
        try {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.parse("file://" + Uri.encode(filePath)));
            GlobalData.getInstance().sendBroadcast(intent);
        }catch (Exception e){
            LogUtils.i("广播失败：", e);
        }
    }


    /*----------------------------------------------------------------------------------------
    //                              文件名
    //----------------------------------------------------------------------------------------*/


    //if (android.os.Environment.getExternalStorageState().
    //        equals(android.os.Environment.MEDIA_MOUNTED);){
    //    File path = Environment.getExternalStorageDirectory();      // 获得SD卡路径(挂载文件夹)
    //    File[] files = path.listFiles();                            // 读取
    //    getSortItem(files);
    //}
    /**
     * 获取"某文件路劲下"下所有(指定后缀名)的"文件(不是文件夹哦)"
     * @param files           如：某文件下的下一层的目录(但是不所有的,还有第二第三层...)
     * @param fileSuffixes    后缀名 如：.txt
     */
    private static ArrayList getFileName(File[] files, String fileSuffixes) {
        ArrayList fileNameList = new ArrayList();

        if (files != null) {                                        // 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    LogUtils.i("shen", "若是文件目录。继续读1" + file.getName().toString()
                            + file.getPath().toString());

                    getFileName(file.listFiles(), fileSuffixes);
                    LogUtils.i("shen", "若是文件目录。继续读2" + file.getName().toString()
                            + file.getPath().toString());
                } else {                                        // 不是"目录"
                    String fileName = file.getName();
                    if (fileName.endsWith(fileSuffixes)) {
                        // String s = fileName.substring(0, fileName.lastIndexOf(".")).toString();   // 去掉后缀
                        fileNameList.add(fileName);
                    }
                }
            }
        }
        return fileNameList;
    }

    /**
     * 获取文件的目录
     *
     * @param fileAbsolutePath   绝对路径
     * @return
     */
    public static String getFileDir(String fileAbsolutePath) {
        String fileDir = "";

        fileAbsolutePath = fileAbsolutePath.trim();
        fileDir = fileAbsolutePath.substring(0, fileAbsolutePath.lastIndexOf("/"));

        return fileDir;
    }


    /**
     * 绝对路径中截取  -- 文件名
     *
     * @param fileAbsolutePath   绝对路径
     * @return
     */
    public static String getFileName(String fileAbsolutePath) {

        // 举例：
        //String fileAbsolutePath =" G:\\Java_Source\\navigation_tigra_menu\\demo1\\img\\lev1_arrow.gif ";
        String fileName = "";

//        // 方法一：
//        File tempFile =new File(fileAbsolutePath.trim());
//        fileName = tempFile.getName();
//        System.out.println("方法一：fileName = " + fileName);

        // 方法二：
//        fileAbsolutePath = fileAbsolutePath.trim();
//        fileName = fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("/")+1);
        // 或者
        // fileName = fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("\\")+1);
        // System.out.println("方法二：fileName = " + fileName);

//        /* 方法三：*/
//        fileAbsolutePath = fileAbsolutePath.trim();
//        String temp[] = fileAbsolutePath.split("\\\\"); /** split里面必须是正则表达式，"\\"的作用是对字符串转义 */
//        //temp[] = [G:, Java_Source, navigation_tigra_menu, demo1, img, lev1_arrow.gif]
//        System.out.println("temp[] = " + Arrays.toString(temp));
//        fileName = temp[temp.length-1];
//        System.out.println("方法三：fileName = " + fileName);

        try {
            fileAbsolutePath = fileAbsolutePath.trim();
            fileName = fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("/") + 1);
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis());
            }
        }

        return fileName;
    }


    /**
     * Java文件操作 获取 -- 文件扩展名
     *
     * @param fileName 文件名 或 绝对路径
     * @return
     */
    public static String getExtensionName(String fileName) {

        String temp = fileName;

        if(fileName.contains(File.separator))
            temp = getFileName(fileName);

        if ((temp != null) && (temp.length() > 0)) {
            int dot = temp.lastIndexOf('.');
            if ((dot >-1) && (dot < (temp.length() - 1))) {
                return temp.substring(dot + 1);
            }
        }
        return temp;
    }

    /**
     * Java文件操作 获取 -- 不带扩展名的文件名
     *
     * @param fileName  文件名 或 绝对路径
     * @return
     */
    public static String getFileNameNoEx(String fileName) {

        String temp = fileName;

        if(fileName.contains(File.separator))
            temp = getFileName(fileName);

        if ((temp != null) && (temp.length() > 0)) {
            int dot = temp.lastIndexOf('.');
            if ((dot >-1) && (dot < (temp.length()))) {
                return temp.substring(0, dot);
            }
        }
        return temp;
    }

    /**
     * Java文件操作 获取 -- 文件的扩展名
     *
     * @param fileName  文件名 或 绝对路径
     * @return
     */
    public static String getFileExtensions(String fileName) {

        String temp = fileName;

        if(fileName.contains(File.separator))
            temp = getFileName(fileName);

        String fileType = temp.substring(temp.lastIndexOf(".") + 1, temp.length());		// 后缀名

        return fileType;
    }

//
//    /**
//     * 根据Uri获取文件的绝对路径
//     *
//     * @param context
//     * @param contentURI
//     * @return
//     */
//    public static String getRealPathFromURI(Context context, Uri contentURI) {
//        String result;
//        Cursor cursor = context.getContentResolver().query(contentURI,
//                new String[]{MediaStore.Images.ImageColumns.DATA},
//                null, null, null);
//        if (cursor == null) {
//            result = contentURI.getPath();
//        }else {
//            cursor.moveToFirst();
//            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            result = cursor.getString(index);
//            cursor.close();
//        }
//        return result;
//    }



    private static File getSDPath() {
        File storageDirectory = Environment.getExternalStorageDirectory();
        File file = new File(storageDirectory, "appinstall");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    static String path = "LaiGeWanAccessibilityService1.0.apk";
    public static String getApk(Context context) {
        File file = new File(getSDPath(), path);
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = context.getAssets().open(path);
            os = new FileOutputStream(file);
            int len;
            byte[] b = new byte[1024];
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }

    /*----------------------------------------------------------------------------------------
    //                              获取文件类型
    //----------------------------------------------------------------------------------------*/
    public static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private static final String[][] MIME_MapTable = {
            // {后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"}, {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"},
            {".sh", "text/plain"}, {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"}, {".txt", "text/plain"},
            {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"}, {"", "*/*"}};

    /*----------------------------------------------------------------------------------------
    //                              调用系统文件管理器获取文件
    //----------------------------------------------------------------------------------------*/


    /**
     * 4.4以下下系统调用方法
     * @param context
     * @param contentUri
     * @return
     */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }

        }catch (IllegalArgumentException e){
            LogUtils.i("getDataColumn:" , e);

        }finally {
            if (cursor != null)
                cursor.close();
        }
        return uri.getPath();
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
