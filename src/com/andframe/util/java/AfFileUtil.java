package com.andframe.util.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;

public class AfFileUtil {

	/**
	 * �ݹ�ɾ���ļ� file
	 * @param file
	 * @return
	 */
	@SuppressWarnings("serial")
	public static boolean deleteFile(final File file){
		if (file != null && file.exists()) {
			Stack<File> stack = new Stack<File>(){{push(file);}};
			File top = null;
			while (!stack.empty() && (top = stack.peek()) != null) {
				File[] files = top.listFiles();
				if (files != null && files.length > 0) {
					for (File item : files) {
						stack.push(item);
					}
				}else {
					File to = new File(top.getAbsolutePath() + System.currentTimeMillis());
					top.renameTo(to);
					if (to.delete()) {
						stack.pop();
					}else {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}


	/**
	 * @Description: ���ļ�����
	 * @param fileFrom
	 * @param fileTo
	 * @return false ����ʧ��
	 */
	public static boolean copyFile(String fileFrom, String fileTo) {  
        try {  
            FileInputStream in = new java.io.FileInputStream(fileFrom);  
            FileOutputStream out = new FileOutputStream(fileTo);  
            byte[] bt = new byte[1024];  
            int count;  
            while ((count = in.read(bt)) > 0) {  
                out.write(bt, 0, count);  
            }  
            in.close();  
            out.close();  
            return true;  
        } catch (IOException ex) {  
            return false;  
        }  
    } 
	/**
	 * �ݹ��ƶ��ļ�file�� Ŀ¼path
	 * @param file Ҫ�ƶ����ļ���������Ŀ¼����ݹ��ƶ���Ŀ¼���ļ���
	 * @param path ָ������Ŀ¼ ���������Զ�������
	 * @return false �ƶ�ʧ��
	 */
	public static boolean moveFile(File file,File path){
		if (!file.exists() || (path.exists() && path.isFile())) {
			return false;
		}
		if (!path.exists()) {
			path.mkdir();
		}
		File to = new File(path, file.getName());
		if (file.isDirectory()) {
			for (File element : file.listFiles()) {
				if (!moveFile(element,to)) {
					return false;
				}
			}
			to = new File(file.getAbsolutePath()+ System.currentTimeMillis());
			file.renameTo(to);
			to.delete();
			return true;
		}else {
			if (to.exists()) {
				File tto = new File(path, ""+ System.currentTimeMillis());
				to.renameTo(tto);
				tto.delete();
			}
			return file.renameTo(to);
		}
	}
	/**
	 * @Description: ת���ļ���С��String��ʾ���� �� 648KB,5MB ����
	 * @Author: scwang
	 * @Version: V1.0, 2015-4-3 ����3:28:55
	 * @Modified: ���δ���getFileSize����
	 * @param size
	 * @return
	 */
	public static String getFileSize(long size){
		int index = 0;
		String[] units = new String[]{"bt","KB","MB","GB","TB"};
		while (index < units.length - 1 && size > 1024) {
			index++;
			size = size/1024;
		}
		return size + units[index];
	}
}
