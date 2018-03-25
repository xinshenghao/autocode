package com.hitoo.general.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	
	
	/**
	 * 判断对应文件（夹）是否存在
	 * @author: 辛晟昊   
	 * @param: @param name
	 * @param: @return      
	 * @return: boolean
	 */
	public static boolean exist(String name){
		File file=new File(name);
		return file.exists();
	}
	/**
	 * 创建文件（非文件夹）
	 * @author: 辛晟昊   
	 * @param: @param name      
	 * @return: void
	 */
	public static void createFile(String name){
		File file=new File(name);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建文件夹
	 * @author: 辛晟昊   
	 * @param: @param name      
	 * @return: void
	 */
	public static void createDirs(String name){
		File file=new File(name);
		file.mkdirs();
	}
	/**
	 * 删除文件
	 * @param path
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		if(file.exists()) {
			file.delete();
		}else {
			try {
				throw new Exception("文件:"+path+"不存在!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 删除文件夹
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
	/**
	 * 查看某个文件夹下是否有文件
	 * @param filePath
	 * @return
	 */
	public static boolean hasChildFile(String filePath) {
		File file = new File(filePath);
		if(file.list().length> 0) {
			return true;
		}
		return false;
	}
	
	 //复制方法
    public static void copy(String src, String des) {
        //初始化文件复制
        File file1=new File(src);
        //把文件里面内容放进数组
        File[] fs=file1.listFiles();
        //初始化文件粘贴
        File file2=new File(des);
        //判断是否有这个文件有不管没有创建
        if(!file2.exists()){
            file2.mkdirs();
        }
        //遍历文件及文件夹
        for (File f : fs) {
            if(f.isFile()){
                //文件
                fileCopy(f.getPath(),des+"/"+f.getName()); //调用文件拷贝的方法
            }else if(f.isDirectory()){
                //文件夹
                copy(f.getPath(),des+"/"+f.getName());//继续调用复制方法      递归的地方,自己调用自己的方法,就可以复制文件夹的文件夹了
            }
        }
        
    }
	
    /**
     * 文件复制的具体方法
     */
    private static void fileCopy(String src, String des) {
        //io流固定格式
        BufferedInputStream bis = null ;
        BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(src));
			bos = new BufferedOutputStream(new FileOutputStream(des));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        int i = -1;//记录获取长度
        byte[] bt = new byte[2014];//缓冲区
        try {
			while ((i = bis.read(bt))!=-1) {
			    bos.write(bt, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
        //关闭流
    }
}
