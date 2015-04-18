package com.andframe.activity.framework;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.andframe.application.AfExceptionHandler;
/**
 * AfView �����ͼ��
 * @author SCWANG
 *	ʵ���� AfViewable �ӿ� �Ż� findViewById ����
 */
public class AfView implements AfViewable {

	private View mRootView = null;

	public AfView(View view) {
		// TODO Auto-generated constructor stub
		mRootView = view;
	}
	/**
	 * ʹAfView ���ص� view �Ӹ���ͼ�����������Ϊ������ view 
	 * 	��Ҫ��������view ��ת��
	 * ���� null ��ʶ ת��ʧ�� ���򷵻���������� view
	 */
	public View breakaway() {
		if (mRootView != null) {
			ViewParent parent = mRootView.getParent();
			if (parent instanceof ViewGroup) {
				ViewGroup group = ViewGroup.class.cast(parent);
				group.removeView(mRootView);
				return mRootView;
			}
		}
		return null;
	}
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		if (mRootView != null) {
			return mRootView.getContext();
		}
		return null;
	}

	@Override
	public Resources getResources() {
		// TODO Auto-generated method stub
		if (mRootView != null) {
			return mRootView.getResources();
		}
		return null;
	}

	public View getView() {
		// TODO Auto-generated method stub
		return mRootView;
	}
	
	@Override
	public View findViewById(int id) {
		// TODO Auto-generated method stub
		if (mRootView != null) {
			return mRootView.findViewById(id);
		}
		return null;
	}

	@Override
	public <T extends View> T findViewById(int id, Class<T> clazz) {
		// TODO Auto-generated method stub
		View view = findViewById(id);
		if (clazz.isInstance(view)) {
			return clazz.cast(view);
		}
		return null;
	}
	@Override
	@SuppressWarnings("unchecked")
	public <T extends View> T findViewByID(int id) {
		// TODO Auto-generated method stub
		try {
			return (T)findViewById(id);
		} catch (Exception e) {
			// TODO: handle exception
			AfExceptionHandler.handler(e, "AfView.findViewByID");
		}
		return null;
	}
}
