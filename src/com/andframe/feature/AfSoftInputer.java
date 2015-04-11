package com.andframe.feature;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.andframe.activity.framework.AfPageListener;
/**
 * �����������
 * @author SCWANG
 */
public class AfSoftInputer implements OnGlobalLayoutListener {

	private AfPageListener mPageListener;
	private View mRootView;
	private Context mContext;

	public AfSoftInputer(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public void setBindListener(View view, AfPageListener pageListener) {
		// TODO Auto-generated method stub
		if (view != null && pageListener != null) {
			mRootView = view;
			mPageListener = pageListener;
			view.getViewTreeObserver().addOnGlobalLayoutListener(this);
		}
	}
	/**
	 * ʵ�� onGlobalLayout 
	 * 	���ڼ��� ����̵ĵ���������
	 * 	�����ڶ� onGlobalLayout ��д��ʱ������� 
	 * 		super.onGlobalLayout();
	 * 	�����ܶ�����̽��м���
	 */
	private int lastdiff = -1;
	@Override
	public void onGlobalLayout() {
		// TODO Auto-generated method stub
		if(mRootView != null){
			int diff = mRootView.getRootView().getHeight() - mRootView.getHeight();
			if(lastdiff > -1){
				if(lastdiff < diff){
					mPageListener .onSoftInputShown();
				}else if(lastdiff > diff){
					mPageListener .onSoftInputHiden();
				}
			}
			lastdiff = diff;	
		}
//		if (diff > 100) {
//			// ��С����100ʱ��һ��Ϊ��ʾ��������¼�
//			this.onSoftInputShown();
//		} else {
//			// ��СС��100ʱ��Ϊ����ʾ������̻������������
//			this.onSoftInputHiden();
//		}

	}
	

	/**
	 * ��ȡ����̴��״̬
	 * @return true �� false �ر�
	 */
	public boolean getSoftInputStatus() {
		// TODO Auto-generated method stub
		InputMethodManager imm = null;
		String Server = Context.INPUT_METHOD_SERVICE;
		imm = (InputMethodManager)mContext.getSystemService(Server);
		return imm.isActive();
	}

	/**
	 * ��ȡ����̴��״̬
	 * @param view ����view
	 * @return true �� false �ر�
	 */
	public boolean getSoftInputStatus(View view) {
		// TODO Auto-generated method stub
		InputMethodManager imm = null;
		String Server = Context.INPUT_METHOD_SERVICE;
		imm = (InputMethodManager)mContext.getSystemService(Server);
		return imm.isActive(view);
	}

	/**
	 * 
	 * @param editview
	 * @param enable
	 */
	public void setSoftInputEnable(EditText editview, boolean enable) {
		// TODO Auto-generated method stub
		InputMethodManager imm = null;
		String Server = Context.INPUT_METHOD_SERVICE;
		imm = (InputMethodManager) mContext.getSystemService(Server);
		if (enable) {
			editview.setFocusable(true);
			editview.setFocusableInTouchMode(true);
			editview.requestFocus();
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		} else {
			IBinder token;
			if(editview != null){
				token = editview.getWindowToken();
			}else if (mContext instanceof Activity) {
				View focus = Activity.class.cast(mContext).getCurrentFocus();
				if(focus!=null){
					token = focus.getWindowToken();
				}else{
					return;
				}
			}else{
				return;
			}
			imm.hideSoftInputFromWindow(token, 0);
		}
	}
}
