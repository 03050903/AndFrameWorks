package com.andframe.layoutbind;

import com.andframe.activity.framework.AfView;
import com.andframe.adapter.AfListAdapter.IAfLayoutItem;
/**
 * �����б���������
 * @author scwang
 * @param <T> ʵ����Ҫ����ģ��
 * @param <TT> ���������ģ��
 */
public abstract class AfLayoutItemAdapter <T,TT> implements IAfLayoutItem<T>{
	
	private IAfLayoutItem<TT> item;
	/**
	 * һ����������ҳ��Ĳ���
	 * @param item
	 */
	public AfLayoutItemAdapter(IAfLayoutItem<TT> item) {
		// TODO Auto-generated constructor stub
		this.item = item;
	}

	@Override
	public void onHandle(AfView view) {
		// TODO Auto-generated method stub
		item.onHandle(view);
	}

	@Override
	public void onBinding(T model, int index) {
		// TODO Auto-generated method stub
		item.onBinding(this.convert(model), index);
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return item.getLayoutId();
	}
	/**
	 * ��ģ������ T ת�� TT
	 * @param model
	 * @return
	 */
	protected abstract TT convert(T model);
}
