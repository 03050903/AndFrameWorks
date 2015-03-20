package com.ontheway.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class AfDensity {

	/**
	 * dip ת���� ���أ�px��
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * ���أ�px��ת����dip
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	protected DisplayMetrics mDisplayMetrics = null;

	public AfDensity(Context context) {
		// TODO Auto-generated constructor stub
		mDisplayMetrics = context.getResources().getDisplayMetrics();
	}
	
	public AfDensity(DisplayMetrics metrics) {
		// TODO Auto-generated constructor stub
		mDisplayMetrics = metrics;
	}
	/**
	 * dip ת���� ���أ�px��
	 * @param dipValue
	 * @return
	 */
	public int dip2px(float dipValue) {
		final float scale = mDisplayMetrics.density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * ���أ�px��ת����dip
	 * @param pxValue
	 * @return
	 */
	public int px2dip(float pxValue) {
		final float scale = mDisplayMetrics.density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * ��ȡ��Ļ���ؿ�ȵ� ratio �ٷֱ�
	 * @param ratio
	 * @return
	 */
	public int proportion(float ratio) {
		// TODO Auto-generated method stub
		if(ratio > 1){
			return (int)(ratio+0.5f);
		}
		return (int)(mDisplayMetrics.widthPixels*ratio+0.5f);
	}

	/**
	 * ��ȡ��Ļ���ؿ�ȵ� ratio �ٷֱȣ�offset ��Ļ����ƫ������
	 * @param ratio
	 * @param offset ���ڲ���û��ռ����Ļ�����߿򣩣�
	 * 		��ô offset = 0 - �߿�
	 * @return
	 */
	public int proportion(float ratio,int offset) {
		// TODO Auto-generated method stub
		if(ratio > 1){
			return (int)(ratio+0.5f+offset);
		}
		return (int)((mDisplayMetrics.widthPixels+offset)*ratio+0.5f);
	}

	/**
	 * ��ȡ��Ļ���ؿ��
	 * @return
	 */
	public int getWidthPixels(){
		return mDisplayMetrics.widthPixels;
	}
	/**
	 * ��ȡ��Ļ���ظ߶�
	 * @return
	 */
	public int getHeightPixels(){
		return mDisplayMetrics.heightPixels;
	}
}
