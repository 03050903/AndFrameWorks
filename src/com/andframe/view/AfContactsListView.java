package com.andframe.view;

import java.util.HashMap;

import com.andframe.adapter.AfContactsAdapter;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ExpandableListView.OnGroupClickListener;

public class AfContactsListView extends ExpandableListView implements
		OnScrollListener, OnGroupClickListener {

	public static final int MAX_ALPHA = 255;
	public static final int PINNED_HEADER_GONE = 0;
	public static final int PINNED_HEADER_VISIBLE = 1;
	public static final int PINNED_HEADER_PUSHED_UP = 2;

	@SuppressWarnings("rawtypes")
	private AfContactsAdapter mAdapter;

	private HashMap<String, Integer> mGroupStatus = new HashMap<String, Integer>();

	public AfContactsListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		registerListener();
	}

	public AfContactsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		registerListener();
	}

	public AfContactsListView(Context context) {
		super(context);
		registerListener();
	}

	public int getGroupClickStatus(int group) {
		// TODO Auto-generated method stub
		if (mGroupStatus.containsKey(String.valueOf(group))) {
			return mGroupStatus.get(String.valueOf(group));
		} else {
			return 0;
		}
	}

	public void setGroupClickStatus(int group, int status) {
		// TODO Auto-generated method stub
		mGroupStatus.put(String.valueOf(group), status);
	}

	/**
	 * �������б�ͷ��ʾ�� View,mHeaderViewVisible Ϊ true �ſɼ�
	 */
	private View mHeaderView;

	/**
	 * �б�ͷ�Ƿ�ɼ�
	 */
	private boolean mHeaderViewVisible;

	private int mHeaderViewWidth;

	private int mHeaderViewHeight;

	public void setHeaderView(View view) {
		mHeaderView = view;
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(lp);

		if (mHeaderView != null) {
			setFadingEdgeLength(0);
		}

		requestLayout();
	}

	private void registerListener() {
		setOnScrollListener(this);
		setOnGroupClickListener(this);
	}

	/**
	 * ��� HeaderView �������¼�
	 */
	private void headerViewClick() {
		long packed = getExpandableListPosition(this.getFirstVisiblePosition());

		int group = ExpandableListView.getPackedPositionGroup(packed);

		if (this.getGroupClickStatus(group) == 1) {
			this.collapseGroup(group);
			this.setGroupClickStatus(group, 0);
		} else {
			this.expandGroup(group);
			this.setGroupClickStatus(group, 1);
		}

		this.setSelectedGroup(group);
	}

	private float mDownX;
	private float mDownY;

	/**
	 * ��� HeaderView �ǿɼ��� , �˺��������ж��Ƿ����� HeaderView, ��������Ӧ�Ĵ��� , ��Ϊ HeaderView
	 * �ǻ���ȥ�� , ���������¼���������Ч�� , ֻ�����п��� .
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mHeaderViewVisible) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mDownX = ev.getX();
				mDownY = ev.getY();
				if (mDownX <= mHeaderViewWidth && mDownY <= mHeaderViewHeight) {
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
				float x = ev.getX();
				float y = ev.getY();
				float offsetX = Math.abs(x - mDownX);
				float offsetY = Math.abs(y - mDownY);
				// ��� HeaderView �ǿɼ��� , ����� HeaderView �� , ��ô���� headerClick()
				if (x <= mHeaderViewWidth && y <= mHeaderViewHeight
						&& offsetX <= mHeaderViewWidth
						&& offsetY <= mHeaderViewHeight) {
					if (mHeaderView != null) {
						headerViewClick();
					}

					return true;
				}
				break;
			default:
				break;
			}
		}

		return super.onTouchEvent(ev);

	}

	/**
	 * @deprecated User {@link AfContactsListView#setAdapter(AfContactsAdapter)}} from now on.
	 */
	@Override
	public void setAdapter(ExpandableListAdapter adapter) {
		super.setAdapter(adapter);
		throw new NullPointerException("��ʹ�� setAdapter(AfContactsAdapter adapter)");
	}

	/**
	 * @deprecated User {@link AfContactsListView#setAdapter(AfContactsAdapter)}} from now on.
	 */
	@Override
	public void setAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
		throw new NullPointerException("��ʹ�� setAdapter(AfContactsAdapter adapter)");
	}


	@SuppressWarnings("rawtypes")
	public void setAdapter(AfContactsAdapter adapter) {
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
		mGroupStatus.clear();
		mAdapter = adapter;
	}
	
	
	/**
	 * 
	 * ����� Group �������¼� , Ҫ���ݸ��ݵ�ǰ��� Group ��״̬��
	 */
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v, int group,
			long id) {
		if (this.getGroupClickStatus(group) == 0) {
			this.setGroupClickStatus(group, 1);
			parent.expandGroup(group);
			parent.setSelectedGroup(group);
		} else if (this.getGroupClickStatus(group) == 1) {
			this.setGroupClickStatus(group, 0);
			parent.collapseGroup(group);
		}

		// ���� true �ſ��Ե��ص�һ�� , ��֪��Ϊʲô
		return true;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (mHeaderView != null) {
			measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
			mHeaderViewWidth = mHeaderView.getMeasuredWidth();
			mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		}
	}

	private int mOldState = -1;

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		final long flat = getExpandableListPosition(getFirstVisiblePosition());
		final int group = ExpandableListView.getPackedPositionGroup(flat);
		final int child = ExpandableListView.getPackedPositionChild(flat);
		int state = /* mAdapter// */this.getQQHeaderState(group, child);
		if (mHeaderView != null && mAdapter != null && state != mOldState) {
			mOldState = state;
			mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
		}

		configureHeaderView(group, child);
	}

	private int getQQHeaderState(int group, int child) {
		// TODO Auto-generated method stub
		ExpandableListAdapter adapter = getExpandableListAdapter();
		if(adapter != null){
			final int childCount = adapter.getChildrenCount(group);
			if (child == childCount - 1) {
				return PINNED_HEADER_PUSHED_UP;
			} else if (child == -1 && !isGroupExpanded(group)) {
				return PINNED_HEADER_GONE;
			} else {
				return PINNED_HEADER_VISIBLE;
			}
		}
		return 0;
	}

	public void configureHeaderView(int group, int child) {
		if (mHeaderView == null || mAdapter == null
				|| ((ExpandableListAdapter) mAdapter).getGroupCount() == 0) {
			return;
		}

		// int state = mAdapter.getQQHeaderState(group, child);
		int state = /* mAdapter// */this.getQQHeaderState(group, child);

		switch (state) {
		case PINNED_HEADER_GONE: {
			mHeaderViewVisible = false;
			break;
		}

		case PINNED_HEADER_VISIBLE: {
			mAdapter.bindHeader(mHeaderView, group, child, MAX_ALPHA);

			if (mHeaderView.getTop() != 0) {
				mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
			}

			mHeaderViewVisible = true;

			break;
		}

		case PINNED_HEADER_PUSHED_UP: {
			View firstView = getChildAt(0);
			int bottom = firstView.getBottom();

			// intitemHeight = firstView.getHeight();
			int headerHeight = mHeaderView.getHeight();

			int y;

			int alpha;

			if (bottom < headerHeight) {
				y = (bottom - headerHeight);
				alpha = MAX_ALPHA * (headerHeight + y) / headerHeight;
			} else {
				y = 0;
				alpha = MAX_ALPHA;
			}

			mAdapter.bindHeader(mHeaderView, group, child, alpha);

			if (mHeaderView.getTop() != y) {
				mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight
						+ y);
			}

			mHeaderViewVisible = true;
			break;
		}
		}
	}

	@Override
	/**
	 * �б�������ʱ���ø÷���(�����ʱ)
	 */
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mHeaderViewVisible) {
			// ��������ֱ�ӻ��Ƶ������У������Ǽ��뵽ViewGroup��
			drawChild(canvas, mHeaderView, getDrawingTime());
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		final long flat = getExpandableListPosition(firstVisibleItem);
		int group = ExpandableListView.getPackedPositionGroup(flat);
		int child = ExpandableListView.getPackedPositionChild(flat);
		configureHeaderView(group, child);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
}
