package com.andframe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;

import com.andframe.activity.framework.AfView;
import com.andframe.application.AfExceptionHandler;

public abstract class AfListAdapter<T> extends BaseAdapter {
	
	protected static final int LP_MP = LayoutParams.MATCH_PARENT;
	protected static final int LP_WC = LayoutParams.WRAP_CONTENT;
	
	protected LayoutInflater mInflater;
	protected List<T> mltArray = new ArrayList<T>();

	protected abstract IAfLayoutItem<T> getItemLayout(T data);

	public AfListAdapter(Context context, List<T> ltdata) {
		mltArray = ltdata;
		mInflater = LayoutInflater.from(context);
	}
	/**
	 * ��ȡ�����б�
	 * @return
	 */
	public List<T> getList(){
		return mltArray;
	}
	/**
	 * ���������� ������� ����׷�ӽӿ�
	 * 
	 * @param ltdata
	 */
	public void addData(T data) {
		// TODO Auto-generated method stub
		mltArray.add(data);
		notifyDataSetChanged();
	}
	/**
	 * ���������� ������� ����׷�ӽӿ�
	 * 
	 * @param ltdata
	 */
	public void addData(List<T> ltdata) {
		// TODO Auto-generated method stub
		mltArray.addAll(ltdata);
		notifyDataSetChanged();
	}

	/**
	 * ���������� ����ˢ�� �ӿ�
	 * 
	 * @param ltdata
	 */
	public void setData(List<T> ltdata) {
		// TODO Auto-generated method stub
		mltArray = new ArrayList<T>(ltdata);
		notifyDataSetChanged();
	}

	/**
	 * ���������� ��������ˢ�� �ӿ�
	 * 
	 * @param index
	 * @param obj
	 */
	public void setData(int index, T obj) {
		// TODO Auto-generated method stub
		if (mltArray.size() > index) {
			mltArray.set(index, obj);
			notifyDataSetChanged();
		}
	}

	/**
	 * ���������� ����ɾ�� �ӿ�
	 * 
	 * @param ltdata
	 */
	public void remove(int index) {
		// TODO Auto-generated method stub
		if (mltArray.size() > index) {
			mltArray.remove(index);
			notifyDataSetChanged();
		}
	}

	/**
	 * ���������� ���ݲ��� �ӿ�
	 * 
	 * @param ltdata
	 */
	public void insert(int index, T object) {
		// TODO Auto-generated method stub
		if (mltArray.size() >= index) {
			mltArray.add(index, object);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mltArray.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mltArray.get(arg0);
	}

	public T getItemAt(int index) {
		// TODO Auto-generated method stub
		return mltArray.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		// �б���ͼ��ȡ������ view �Ƿ�Ϊ�� ����ÿ�ζ� inflate �����ֻ��ڴ渺�ز���
		IAfLayoutItem<T> item = null;
		try {
			if (view == null) {
				item = getItemLayout(mltArray,position);
				item.onHandle(new AfView(view = onInflateItem(item, parent)));
				view.setTag(item);
			} else {
				item = (IAfLayoutItem<T>) view.getTag();
			}
			bindingItem(item, position);
		} catch (Throwable e) {
			// TODO: handle exception
			String remark = "AfListAdapter.getView �����쳣\r\n";
			remark += "class = " + getClass().toString();
			AfExceptionHandler.handler(e, remark);
		}
		return view;
	}

	protected IAfLayoutItem<T> getItemLayout(List<T> ltarray, int position) {
		// TODO Auto-generated method stub
		return getItemLayout(ltarray.get(position));
	}

	protected View onInflateItem(IAfLayoutItem<T> item, ViewGroup parent) {
		return mInflater.inflate(item.getLayoutId(), null);
	}

	protected boolean bindingItem(IAfLayoutItem<T> item, int index) {
		item.onBinding(mltArray.get(index),index);
		return true;
	}

	public static interface IAfLayoutItem<T> {
		/**
		 * ����ͼ��ȡ���ؼ�
		 * @param view
		 */
		public abstract void onHandle(AfView view);
		/**
		 * �����ݰ󶨵��ؼ���ʾ
		 * @param model
		 */
		public abstract void onBinding(T model,int index);
		/**
		 * ��ȡ Item ������ Layout ID
		 * @return
		 */
		public abstract int getLayoutId();
	}
}
