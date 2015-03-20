package com.ontheway.view.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;


public class PullRefreshFooterImpl extends AfPullFooterLayout{

	public PullRefreshFooterImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public PullRefreshFooterImpl(Context context, AttributeSet attrs)
    {
		super(context,attrs);
        // TODO Auto-generated method stub
    }

	@Override
	protected String getFooterString(Context context, EnumFooterString string) {
		// TODO Auto-generated method stub
		if(string == EnumFooterString.footer_loading)
		{
			return "���ڼ���";
		}
		else if(string == EnumFooterString.footer_release)
		{
			return "�ͷŻ�ȡ����";
		}
		else if(string == EnumFooterString.footer_updatetime)
		{
			return "�ϴθ���";
		}
		else if(string == EnumFooterString.footer_pullup)
		{
			return "������ȡ����";
		}
		return "";
	}

	@Override
	protected AfPullHeaderLayout getHeader() {
		// TODO Auto-generated method stub
		return new PullRefreshHeaderImpl(getContext());
	}

}
