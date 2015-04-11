package com.andframe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

import com.andframe.view.multichoice.AfMultiChoiceGridView;
import com.andframe.view.pulltorefresh.PullRefreshFooterImpl;
import com.andframe.view.pulltorefresh.PullRefreshHeaderImpl;

/**
 * ������ˢ�µ� ��������� listview 
 * @author SCWANG
 */
public class AfMultiGridView extends AfMultiChoiceGridView{
	
	private static GridView mGridView = null;

	public AfMultiGridView(GridView listView) {
		super((mGridView=listView).getContext());
		// TODO Auto-generated constructor stub
		setPullFooterLayout(new PullRefreshFooterImpl(listView.getContext()));
		setPullHeaderLayout(new PullRefreshHeaderImpl(listView.getContext()));
	}
	
	public AfMultiGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setPullFooterLayout(new PullRefreshFooterImpl(context));
		setPullHeaderLayout(new PullRefreshHeaderImpl(context));
	}

	public AfMultiGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setPullFooterLayout(new PullRefreshFooterImpl(context));
		setPullHeaderLayout(new PullRefreshHeaderImpl(context));
	}

	public AfMultiGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		setPullFooterLayout(new PullRefreshFooterImpl(context));
		setPullHeaderLayout(new PullRefreshHeaderImpl(context));
	}

	@Override
	protected GridView onCreateGridView(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		if (mGridView != null) {
			if (getParent() == null && mGridView.getParent() instanceof ViewGroup) {
				ViewGroup parent = ViewGroup.class.cast(mGridView.getParent());
				int index = parent.indexOfChild(mGridView);
				parent.removeView(mGridView);
				parent.addView(this, index,mGridView.getLayoutParams());
				mTargetView = mGridView;
				mGridView = null;
			}
			return mTargetView;
		}
		return new GridView(context);
	}

	@Override
	protected GridView onCreateRefreshableView(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		mGridView = onCreateGridView(context,attrs);//new ListView(context)
		// ���listview���϶���ʱ�򱳾�ͼƬ��ʧ��ɺ�ɫ����
		mGridView.setCacheColorHint(0);
		mGridView.setScrollingCacheEnabled(false);
		// ���listview���ϱߺ��±��к�ɫ����Ӱ
		mGridView.setFadingEdgeLength(0);
		return mGridView;
	}
}
