package com.year2018.ndk.util;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * Author: zyh
 * Date: 2018/9/20 10:57
 */
public class FileManager {
    private Application mApplication = null;

    private FileManager(){ }

    private static class FileManagerHolder{
        private static final FileManager instance = new FileManager();
    }

    public static final FileManager getInstance(){
        return FileManagerHolder.instance;
    }

    public void init(Application application){
        this.mApplication = application;
    }

    /** 将byte数组写入内置存储器 **/
    private void writeBytesToInnerStorage(String fileName, byte[] data, int size){
        if(mApplication !=null) {
            FileOutputStream fos = null;
            InputStream is = null;
            try {
                fos = mApplication.openFileOutput(fileName, Context.MODE_APPEND);
                byte[] temp = new byte[size];
                System.arraycopy(data, 0, temp, 0, size);
                is = new ByteArrayInputStream(temp);
                byte[] buff = new byte[1024];
                int sum = 0;
                int len = -1;
                while ((len = is.read(buff)) != -1) {
                    sum = sum + len;
                    fos.write(buff, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 将字符串写入内置存储器
     * @param fileName
     * @param str
     */
    public void writeStringToInnerStorage(String fileName, String str){
        FileOutputStream fos = null;
        OutputStreamWriter osw =null;
        BufferedWriter bw = null;
        try {
            fos = mApplication.openFileOutput(fileName, Context.MODE_APPEND);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            bw.write(str);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bw!=null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(osw!=null){
                try{
                    osw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将字符串写入外置存储卡
     * @param fileName
     * @param str
     */
    public void writeStringToExternalStorage(String fileName,String str){
        File file = new File(Environment.getExternalStorageDirectory(),fileName);

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(file.exists()){
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file,true);
                fileWriter.write(str);
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (fileWriter!=null){
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
