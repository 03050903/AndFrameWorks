package com.ontheway.layoutbind;

import com.ontheway.activity.framework.AfViewable;
import com.ontheway.layoutbind.framework.IAfLayoutModule;

import android.view.View;

public abstract class AfLayoutModule implements IAfLayoutModule {
	
	protected View mLayout = null;
	protected Boolean mIsValid = false;

	protected AfLayoutModule(AfViewable view){
		InitializeComponent(view);
	}
	/**
	 * ���๹�캯���б�������������
	 * @param view
	 */
	protected void InitializeComponent(AfViewable view){
		mLayout = findLayout(view);
		mIsValid = mLayout != null;
	}
	/**
	 * �� AfViewable �ҵ���Ӧ�� ����Layout
	 * @param view
	 * @return ���� Layout ��Ч�����
	 */
	protected abstract View findLayout(AfViewable view);

	public View getLayout() {
		return mLayout;
	}

	public void hide() {
		// TODO Auto-generated constructor stub
		if (mLayout != null) {
			mLayout.setVisibility(View.GONE);
		}
	}

	public void show() {
		// TODO Auto-generated constructor stub
		if (mLayout != null) {
			mLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}

	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		if (mLayout != null) {
			mLayout.setEnabled(enabled);
		}
	}

	@Override
	public boolean isVisibility() {
		// TODO Auto-generated method stub
		if(isValid()){
			return mLayout.getVisibility() == View.VISIBLE;
		}
		return false;
	}
	
}
