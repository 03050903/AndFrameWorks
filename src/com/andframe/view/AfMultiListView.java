package com.andframe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

import com.andframe.view.multichoice.AfMultiChoiceListView;
import com.andframe.view.pulltorefresh.PullRefreshFooterImpl;
import com.andframe.view.pulltorefresh.PullRefreshHeaderImpl;

/**
 * ������ˢ�µ� ��������� listview 
 * @author SCWANG
 *
 */
public class AfMultiListView extends AfMultiChoiceListView{
	
	private static ListView mlistView = null;

	public AfMultiListView(ListView listView) {
		super((mlistView=listView).getContext());
		// TODO Auto-generated constructor stub
		setPullFooterLayout(new PullRefreshFooterImpl(listView.getContext()));
		setPullHeaderLayout(new PullRefreshHeaderImpl(listView.getContext()));
	}
	
	public AfMultiListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setPullFooterLayout(new PullRefreshFooterImpl(context));
		setPullHeaderLayout(new PullRefreshHeaderImpl(context));
	}

	public AfMultiListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setPullFooterLayout(new PullRefreshFooterImpl(context));
		setPullHeaderLayout(new PullRefreshHeaderImpl(context));
	}

	public AfMultiListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		setPullFooterLayout(new PullRefreshFooterImpl(context));
		setPullHeaderLayout(new PullRefreshHeaderImpl(context));
	}

	@Override
	protected ListView onCreateListView(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		if (mlistView != null) {
			if (getParent() == null && mlistView.getParent() instanceof ViewGroup) {
				ViewGroup parent = ViewGroup.class.cast(mlistView.getParent());
				int index = parent.indexOfChild(mlistView);
				parent.removeView(mlistView);
				parent.addView(this, index,mlistView.getLayoutParams());
				mTargetView = mlistView;
				mlistView = null;
			}
			return mTargetView;
		}
		return new ListView(context);
	}

	@Override
	protected ListView onCreateRefreshableView(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		mListView = onCreateListView(context,attrs);//new ListView(context)
		// ���listview���϶���ʱ�򱳾�ͼƬ��ʧ��ɺ�ɫ����
		mListView.setCacheColorHint(0);
		mListView.setScrollingCacheEnabled(false);
		// ���listview���ϱߺ��±��к�ɫ����Ӱ
		mListView.setFadingEdgeLength(0);
		return mListView;
	}
}
