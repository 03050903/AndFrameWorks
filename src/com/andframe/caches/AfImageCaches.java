package com.andframe.caches;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.andframe.application.AfExceptionHandler;
import com.andframe.util.java.AfMD5;
/**
 * AfImageCaches ͼƬ����
 * @author SCWANG
 *		1.�ڴ滺�� �����ڼ���ͼƬ�Ķ�ȡ��û�е�ʱ�����ļ����棩
 *		2.�ļ����� ���ļ�����>=�ڴ滺�棩
 *		
 *		a.���� Application ��ʼ����ʱ�� ���� initialize ��ʼ�� path ���ļ�����·��
 *			public static synchronized void initialize(Context context, String path) 
 *		b.ʹ�÷���Ϊ����ģʽ������getInstance��ȡ����ʵ��
 *			public static synchronized AfImageCaches getInstance()
 *		c.��ϸʹ��
				 * ��ȡ���� BitmapDrawable 
				public BitmapDrawable get(Context context, String url) 
				 * ��ȡ���� Bitmap 
				public Bitmap get(String url) 
				 * ��ӻ��� 
				public void put(String url, Bitmap bitmap) 
				 * ��ӻ��� 
				public void put(String url, BitmapDrawable bitmap) 
				 * ������л���
				public void clear() 
				 * ��ȡ�����С
				public int getCachesSize() 
				 * ��ȡ����·���ļ�
				public File getCachePath() 
				 * ɾ��ָ���� ͼƬ����
				 * @param key
				public void remove(String url) 
 */
public class AfImageCaches {
	// ʹ�� 12.5% ���ڴ���Ϊ�ڴ�洢
	private int mMemorySize = 10 * 1024 * 1024;// (int)(Runtime.getRuntime().maxMemory()/8);
	private static File mCacheDirFile = null;
	private static AfImageCaches mInstance = null;

	public static synchronized void initialize(Context context, String path) {
		mCacheDirFile = new File(path);
		mInstance = new AfImageCaches(context);
		if (mCacheDirFile.exists() == false) {
			mCacheDirFile.mkdirs();
		}
	}

	public static synchronized AfImageCaches getInstance() {
		return mInstance;
	}

	private LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(
			mMemorySize) {
		private String last_key = "";

		@Override
		protected int sizeOf(String key, Bitmap bitmap) {
			last_key = key;
			int size = bitmap.getHeight() * bitmap.getWidth() * 2;
			return size;
		}

		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldBitmap, Bitmap newBitmap) {
			if (!key.equalsIgnoreCase(last_key)) {
				oldBitmap.recycle();
				oldBitmap = null;
				System.gc();
			}
		}
	};

	/**
	 * 
	 * @param context
	 */
	private AfImageCaches(Context context) {
		// mImageCacheDao = new ImageCacheDao(context);
		if (mCacheDirFile == null) {
			mCacheDirFile = context.getCacheDir();
		}

		if (mCacheDirFile.exists() == false) {
			mCacheDirFile.mkdirs();
		}
	}

	/**
	 * ��ȡ���� BitmapDrawable 
	 * @param context 
	 * @param url ͼƬ��URl
	 * @return BitmapDrawable ���� null
	 */
	public BitmapDrawable get(Context context, String url) {
		// TODO Auto-generated method stub
		Bitmap bitmap = get(url);
		if(bitmap == null){
			return null;
		}
		return new BitmapDrawable(context.getResources(), bitmap);
	}
	/**
	 * ��ȡ���� Bitmap 
	 * @param url ͼƬ��URl
	 * @return Bitmap ���� null
	 */
	public Bitmap get(String url) {
		Log.e("BitmapCaches", url);

		String key = AfMD5.getMD5(url.getBytes());
		// ���ڴ���ȡ
		synchronized (mMemoryCache) {
			Bitmap bitmap = mMemoryCache.get(key);
			if (bitmap != null) {
				if (bitmap.isRecycled()) {
					mMemoryCache.remove(key);
					return null;
				}
				return bitmap;
			}
		}
		// ���ļ���ȡ
		try {
			String Path = mCacheDirFile.getAbsolutePath() + File.separator
					+ key;
			File file = new File(Path);
			if (!file.isFile() || !file.exists()) {
				return null;
			}
			Bitmap bitmap = BitmapFactory.decodeFile(Path);
			if (bitmap != null) {
				synchronized (mMemoryCache) {
					mMemoryCache.put(key, bitmap);
				}
			}
			return bitmap;
		} catch (Throwable e) {
			e.printStackTrace();// handled
//			AfExceptionHandler.handler(e, "ͼƬ���棬get �����쳣");
		}
		return null;
	}

	/**
	 * ��ӻ��� 
	 * @param url ͼƬ��URl
	 * @param bitmap ����ͼƬ
	 */
	public void put(String url, Bitmap bitmap) {
		String key = AfMD5.getMD5(url.getBytes());
		if (key != null && bitmap != null) {
			synchronized (mMemoryCache) {
//				Log.w("Bitmpa Cache", "add cache: " + key);
				mMemoryCache.put(key, bitmap);
				// �Ƶ��ļ�����
				this.putToFile(key, bitmap);
			}
		}
	}

	/**
	 * ��ӻ��� 
	 * @param url ͼƬ��URl
	 * @param bitmap ����ͼƬ
	 */
	public void put(String url, BitmapDrawable bitmap) {
		// TODO Auto-generated method stub
		put(url, bitmap.getBitmap());
	}
	/**
	 * ������л���
	 */
	public void clear() {
		if (mMemoryCache != null) {
			mMemoryCache.evictAll();
		}

		File[] files = mCacheDirFile.listFiles();
		// mImageCacheDao.delAll();
		if (files != null) {
			for (File file : files)
				file.delete();
		}
		System.gc();
	}

	/**
	 * ��ȡ�����С
	 */
	public int getCachesSize() {
		int size = 0;
		File[] files = mCacheDirFile.listFiles();
		if (files != null) {
			for (File file : files) {
				size += file.length();
			}
		}
		return size;
	}

	/**
	 * ��ȡ����·���ļ�
	 * @return
	 */
	public File getCachePath() {
		// TODO Auto-generated method stub
		return mCacheDirFile;
	}

	/**
	 * ɾ��ָ���� ͼƬ����
	 * @param key
	 */
	public void remove(String url) {
		// TODO Auto-generated method stub
		try {
			String key = AfMD5.getMD5(url.getBytes());
			String Path = mCacheDirFile.getAbsolutePath() + File.separator
					+ key;
			File file = new File(Path);
			if(file.exists()){
				file.delete();
			}
		} catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
			AfExceptionHandler.handler(e, "AfImageCaches.remove.Exception");
		}
	}

	/**
	 * 
	 * @param key
	 * @param bitmap
	 */
	private void putToFile(String key, Bitmap bitmap) {
		try {
			// if(mImageCacheDao.Exist(key)){
			// return;
			// }
			String Path = mCacheDirFile.getAbsolutePath() + File.separator
					+ key;
			// ImageCache tImageCache = new ImageCache("", key, Path, "");

			FileOutputStream tFileOutputStream = new FileOutputStream(Path);

			if (bitmap.compress(CompressFormat.JPEG, 100, tFileOutputStream)) {
				// mImageCacheDao.add(tImageCache);
			}

			tFileOutputStream.flush();
			tFileOutputStream.close();
		} catch (Throwable e) {
			e.printStackTrace();// handled
//			AfExceptionHandler.handler(e, "ͼƬ���棬putToFile �����쳣");
		}
	}
	/**
	 * ӳ��һ��url�����ػ���·����
	 * @param mLinkUrl
	 * @return
	 */
	public String mapPath(String mLinkUrl) {
		// TODO Auto-generated method stub
		String key = AfMD5.getMD5(mLinkUrl.getBytes());
		return mCacheDirFile.getAbsolutePath() + File.separator
				+ key;
	}

}
