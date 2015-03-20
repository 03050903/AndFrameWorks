package com.ontheway.util;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Message;
import android.widget.ImageView;

import com.ontheway.application.AfApplication;
import com.ontheway.caches.AfImageCaches;
import com.ontheway.thread.AfHandlerTask;
/**
 * @Description: ͼƬѹ��������
 * @Author: scwang
 * @Version: V1.0, 2015-3-13 ����4:54:23
 * @Modified: ���δ���AfImageThumb��
 */
public class AfImageThumb {

	/**
	 * @Description: �� image �� ImageView
	 * 	ʹ���ļ����棬�Ͷ��̣߳���߰�������Ƕ�
	 *  ͼƬ��С������Ļ����ֹ�ڴ��쳣
	 * @Author: scwang
	 * @Version: V1.0, 2015-1-27 ����9:45:50
	 * @Modified: ���δ���bindImage����
	 * @param iv
	 * @param image
	 */
	public static void bindImage(final ImageView iv, final String image) {
		// TODO Auto-generated method stub
		AfImageCaches caches = AfImageCaches.getInstance();
		Bitmap bitmap = caches.get(image);
		if (bitmap == null) {
			AfApplication.postTask(new AfHandlerTask() {
				Bitmap bitmap = null;
				@Override
				protected void onWorking(Message msg) throws Exception {
					// TODO Auto-generated method stub
					AfImageCaches caches = AfImageCaches.getInstance();
					bitmap = revitionImageSize(image);
					caches.put(image, bitmap);
				}
				@Override
				protected boolean onHandle(Message msg) {
					// TODO Auto-generated method stub
					if (bitmap != null) {
						iv.setImageBitmap(bitmap);
					}
					return false;
				}
			});
		}else {
			iv.setImageBitmap(bitmap);
		}
	}

	/**
	 * @Description: 
	 * ���ļ�����һ�Ų����� ��Ļ/3 ��ͼƬ
	 * ������ڽ�����С���� ������Ļ/3 (2�ı�����С��ԭ����)
	 * @Author: scwang
	 * @Version: V1.0, 2015-1-27 ����9:34:25
	 * @Modified: ���δ���revitionImageSize����
	 * @param path �ļ�·��
	 * @return
	 * @throws IOException
	 */
	public static Bitmap revitionImageSize(String path) {
		AfDensity density = new AfDensity(AfApplication.getApp());
		int maxw = density.getWidthPixels()/3;
		int maxh = density.getHeightPixels()/3;
		return revitionImageSize(path,maxw,maxh);
	}

	/**
	 * @Description: 
	 * ���ļ�����һ�Ų����� screenscale ��ͼƬ
	 * ������ڽ�����С��������screenscale (2�ı�����С��ԭ����)
	 * @Author: scwang
	 * @Version: V1.0, 2015-1-27 ����9:34:25
	 * @Modified: ���δ���revitionImageSize����
	 * @param path �ļ�·��
	 * @param screenscale ����ȣ�����ֻ���Ļ����0.3����Ļ��
	 * @return
	 * @throws IOException
	 */
	public static Bitmap revitionImageSize(String path,float screenscale) {
		AfDensity density = new AfDensity(AfApplication.getApp());
		int maxw = (int) (density.getWidthPixels()*screenscale);
		int maxh = (int) (density.getHeightPixels()*screenscale);
		return revitionImageSize(path,maxw,maxh);
	}

	/**
	 * @Description: 
	 * ���ļ�����һ�Ų����� maxw maxh ��ͼƬ
	 * ������ڽ�����С��������maxw maxh (2�ı�����С��ԭ����)
	 * @Author: scwang
	 * @Version: V1.0, 2015-1-27 ����9:34:25
	 * @Modified: ���δ���revitionImageSize����
	 * @param path �ļ�·��
	 * @param maxw �����
	 * @param maxh ���߶�
	 * @return
	 * @throws IOException
	 */
	public static Bitmap revitionImageSize(String path,int maxw,int maxh) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		for (int i = 0; i < 10 ; i++) {
			if ((options.outWidth >> i <= maxw)
					&& (options.outHeight >> i <= maxh)) {
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeFile(path, options);
				break;
			}
		}
		return bitmap;
	}
	


	/**
	 * @Description: ��������������ͼ����Ĭ�ϻ���ԭͼ��
	 * @Author: scwang
	 * @Version: V1.0, 2015-3-13 ����4:56:10
	 * @Modified: ���δ���extractMiniThumb����
	 * @param source ԭͼ
	 * @param width ���ô�С
	 * @param height ���ô�С
	 * @return ����ͼ
	 */
	public static Bitmap extractMiniThumb(Bitmap source, int width, int height) {
		return extractMiniThumb(source, width, height, true);
	}

	/**
	 * @Description: ��������������ͼ��
	 * @Author: scwang
	 * @Version: V1.0, 2015-3-13 ����4:56:10
	 * @Modified: ���δ���extractMiniThumb����
	 * @param source ԭͼ
	 * @param width ���ô�С
	 * @param height ���ô�С
	 * @param recycle �Ƿ����ԭͼ
	 * @return ����ͼ
	 */
	public static Bitmap extractMiniThumb(Bitmap source, int width, int height,
			boolean recycle) {
		if (source == null) {
			return null;
		}

		float scale;
		if (source.getWidth() < source.getHeight()) {
			scale = width / (float) source.getWidth();
		} else {
			scale = height / (float) source.getHeight();
		}
		Matrix matrix = new Matrix();
		matrix.setScale(scale, scale);
		Bitmap miniThumbnail = transform(matrix, source, width, height, false);

		if (recycle && miniThumbnail != source) {
			source.recycle();
		}
		return miniThumbnail;
	}

	public static Bitmap transform(Matrix scaler, Bitmap source,
			int targetWidth, int targetHeight, boolean scaleUp) {
		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			/*
			 * In this case the bitmap is smaller, at least in one dimension,
			 * than the target. Transform it by placing as much of the image as
			 * possible into the target and leaving the top/bottom or left/right
			 * (or both) black.
			 */
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
					Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b2);

			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
					+ Math.min(targetWidth, source.getWidth()), deltaYHalf
					+ Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth - src.width()) / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
					- dstY);
			c.drawBitmap(source, src, dst, null);
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		float bitmapAspect = bitmapWidthF / bitmapHeightF;
		float viewAspect = (float) targetWidth / targetHeight;

		if (bitmapAspect > viewAspect) {
			float scale = targetHeight / bitmapHeightF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		} else {
			float scale = targetWidth / bitmapWidthF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		}

		Bitmap b1;
		if (scaler != null) {
			// this is used for minithumb and crop, so we want to filter here.
			b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
					source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}

		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);

		Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
				targetHeight);

		if (b1 != source) {
			b1.recycle();
		}

		return b2;
	}
}
