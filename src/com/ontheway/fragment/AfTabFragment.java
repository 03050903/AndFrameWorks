package com.ontheway.fragment;

import com.ontheway.activity.framework.AfView;
import com.ontheway.application.AfApplication;
import com.ontheway.util.AfBundle;

public abstract class AfTabFragment extends AfFragment{

	// �л���Fragmentҳ�� �Ĵ���ͳ��
	private int mSwitchCount = 0;
	// ��ʶ�Ƿ񴴽���ͼ
	private Boolean mIsCreateView = false;
	// ��ʶ������ͼ��ʱ���Ƿ���ҪSwitch
	private Boolean mIsNeedSwitch = false;

	/**
	 * �Զ��� View onCreate(Bundle)
	 */
	protected void onCreated(AfBundle bundle,AfView view)throws Exception{
		
	}

	@Override
	protected final void onCreated(AfView rootView, AfBundle bundle)throws Exception {
		// TODO Auto-generated method stub
		mIsCreateView = true;
		onCreated(bundle,rootView);
		if (mIsNeedSwitch == true) {
			mIsNeedSwitch = false;
			AfApplication.getApp().setCurFragment(this, this);
			if (mSwitchCount == 0) {
				this.onFirstSwitchOver();
			}
			this.onSwitchOver(mSwitchCount++);
			this.onQueryChanged();
		}
	}
	@Override
	public final void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (mIsCreateView) {
				if (mSwitchCount == 0) {
					this.onFirstSwitchOver();
				}
				AfApplication.getApp().setCurFragment(this, this);
				this.onSwitchOver(mSwitchCount++);
				this.onQueryChanged();
			} else {
				mIsNeedSwitch = true;
			}
		} else {
			this.onSwitchLeave();
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		mIsCreateView = false;
		mIsNeedSwitch = false;
	}


	/**
	 * ��һ���л�����ҳ��
	 */
	protected void onFirstSwitchOver() {

	}

	/**
	 * ÿ���л�����ҳ��
	 * 
	 * @param count
	 *            �л����
	 */
	protected void onSwitchOver(int count) {

	}

	/**
	 * �뿪��ҳ��
	 */
	protected void onSwitchLeave() {

	}

	/**
	 * ��ѯϵͳ���ݱ䶯
	 */
	public void onQueryChanged() {
		// TODO Auto-generated method stub
		
	}
}
