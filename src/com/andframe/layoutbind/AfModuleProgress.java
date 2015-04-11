package com.andframe.layoutbind;

import android.widget.TextView;

import com.andframe.activity.framework.AfViewable;
import com.andframe.layoutbind.AfLayoutModule;
/**
 * ��ܼ������
 * @author Administrator
 *
 */
public abstract class AfModuleProgress extends AfLayoutModule{

	public TextView mTvDescription = null;

	public AfModuleProgress(AfViewable view) {
		super(view);
		// TODO Auto-generated constructor stub
		if(isValid()){
			mTvDescription = findDescription(view);
			mTvDescription.setText("���ڼ���...");
		}
	}
	
	/**
	 * ��ȡ��Ϣ��ʾ TextView
	 * @param view
	 * @return
	 */
	protected abstract TextView findDescription(AfViewable view);
	
	public void setDescription(String description) {
		// TODO Auto-generated constructor stub
		if(isValid()){
			mTvDescription.setText(description);
		}
	}

	public void setDescription(int resid) {
		// TODO Auto-generated constructor stub
		if(isValid()){
			mTvDescription.setText(resid);
		}
	}

}
