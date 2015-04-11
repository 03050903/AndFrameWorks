package com.andframe.util.android;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.widget.Toast;
/**
 * ����ϵͳ�ļ�ѡ�������Ƭѡ��
 * @Description: AfFileSelector
 * @Author: scwang
 * @Version: V1.0, 2015-4-3 ����3:46:13
 * @Modified: ���δ���AfFileSelector��
 */
public class AfFileSelector {

	
	private static File mOutputpath;

	/**
	 * showPhotograph ������Ӧ���ܺ���
	 * ���ʧ�ܽ����� null
	 */
	public static File onActivityPhotographResult(Activity activity,int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			return mOutputpath;
		}
		return null;
	}
	
	/**
	 * showFileChooser������Ӧ���ܺ���
	 * ���ʧ�ܽ����� ""(�մ�)
	 */
	public static String onActivityFileResult(Activity activity,int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && null != data) {
			return getPath(activity, data.getData());
		}
		return "";
	}

	/**
	 * showImageChooser������Ӧ���ܺ���
	 * ���ʧ�ܽ����� ""(�մ�)
	 */
	public static String onActivityImageResult(Activity activity,int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { Media.DATA };

			Cursor cursor = activity.getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			return picturePath;
		}
		return "";
	}
	/**
	 * ������ᣨͼƬ�������ѡ��ͼƬ
	 * @param outputpath ���պ����ֻ��Ĵ���·��
	 * @param request startActivityForResult ������ request 
	 * ���� onActivityResult �м��
	 */
	public static void showPhotograph(Activity activity,File outputpath, int request) {
		// TODO Auto-generated method stub
		try {
			mOutputpath = outputpath;
			Uri imageUri = Uri.fromFile(outputpath);//The Uri to store the big bitmap 
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture 
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); 
			activity.startActivityForResult(intent, request);//or TAKE_SMALL_PICTURE 
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(activity, "û���ҵ�ϵͳ��������~", Toast.LENGTH_SHORT)
			.show();
		}
	}
	/**
	 * ������ᣨͼƬ�������ѡ��ͼƬ
	 * @param request startActivityForResult ������ request 
	 * ����	onActivityResult �м��
	 */
	public static void showImageChooser(Activity activity, int request) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
		try {
			activity.startActivityForResult(i, request);
		} catch (ActivityNotFoundException ex) {
			Toast.makeText(activity, "�ֻ��ϻ�û�а�װͼƬ�����.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * �����ļ������ѡ���ļ�
	 * @param request startActivityForResult ������ request 
	 * ����	onActivityResult �м��
	 */
	public static void showFileChooser(Activity activity, int request) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			intent = Intent.createChooser(intent, "ѡ��Ҫ�ϴ����ļ�");
			activity.startActivityForResult(intent, request);
		} catch (ActivityNotFoundException ex) {
			Toast.makeText(activity, "�ֻ��ϻ�û�а�װ�ļ�������.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public static String getPath(Context context, Uri uri) {
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;
			try {
				cursor = context.getContentResolver().query(uri, projection,
						null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Throwable e) {
				return "";
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return "";
	}

	public static String onActivityResult(Activity activity,int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK){
			return getPath(activity,data.getData());
		}
		return "";
	}

	/**
	 * �����ļ������ѡ���ļ�
	 * @param title �Զ���ѡ��ҳ�����
	 * @param request startActivityForResult ������ request 
	 * ����	onActivityResult �м��
	 */
	public static void showFileChooser(Activity activity,String title,int request) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			intent = Intent.createChooser(intent, title);
			activity.startActivityForResult(intent, request);
		} catch (ActivityNotFoundException ex) {
			Toast.makeText(activity, "�ֻ��ϻ�û�а�װ�ļ�������.",
					Toast.LENGTH_SHORT).show();
		}
	}

}
