package com.andframe.activity.framework;


/**
 * ���ҳ������ӿ� AfPageListener
 * @author SCWANG
 * 	��Ҫ����������ʾ������
 * 	ҳ����Ҫ��ѯ���ݱ䶯
 */
public interface AfPageListener{
	/**
	 * ���������ʾ
	 */
	public void onSoftInputShown();
	/**
	 * �������������
	 */
	public void onSoftInputHiden();
	/**
	 * ��ѯϵͳ���ݱ䶯
	 */
	public void onQueryChanged();
}
