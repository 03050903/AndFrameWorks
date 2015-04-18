package com.andframe.activity.framework;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
/**
 * AfViewable �����ͼ�ӿ�
 * @author SCWANG
 *	��Ҫ���� �Ż� ��ͳ�� findViewById ����
 */
public interface AfViewable {

	public Context getContext();

	public Resources getResources();

	public View findViewById(int id);

	public <T extends View> T findViewByID(int id);

	public <T extends View> T findViewById(int id,Class<T> clazz);
}
