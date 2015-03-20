package com.ontheway.activity.framework;


import android.app.Activity;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.ontheway.activity.AfActivity;
import com.ontheway.thread.AfTask;
/**
 * ���ҳ��ӿ� AfPageable
 * @author SCWANG
 *	�̳��� AfViewable 
 */
public interface AfPageable extends AfViewable,AfPageListener{

	/** 
	 * ��������Workerִ��
	 * @param task �������
	 */
	public AfTask postTask(AfTask task);
	/**
	 * �ж��Ƿ񱻻���
	 * @return true �Ѿ�������
	 */
	public boolean isRecycled();
	/**
	 * ��ȡҳ����ص� Activity
	 * @return ��ص� Activity
	 */
	public Activity getActivity();
	
	public String getString(int resid);

	public void makeToastLong(String tip);

	public void makeToastShort(String tip);

	public void makeToastLong(String tip, Throwable e);
	/**
	 * ��ʾ ��ԴID Ϊ resid �� Toast
	 * @param resid
	 */
	public void makeToastLong(int resid);
	/**
	 * ��ʾ ��ԴID Ϊ resid �� Toast
	 * @param resid
	 */
	public void makeToastShort(int resid);

	/**
	 * ��ȡ����̴��״̬
	 * @return true �� false �ر�
	 */
	public boolean getSoftInputStatus();
	
	/**
	 * ��ȡ����̴��״̬
	 * @param view ����view
	 * @return true �� false �ر�
	 */
	public boolean getSoftInputStatus(View view);
	
	/**
	 * �����������ʾ�͹ر�
	 * @param enable ��ʾ���߹ر�
	 */
	public void setSoftInputEnable(EditText editview, boolean enable);

	/**
	 * ��ʾ ���ȶԻ���
	 * @param message
	 *            ��Ϣ
	 */
	public void showProgressDialog(String message);

	/**
	 * ��ʾ ���ȶԻ���
	 * @param message ��Ϣ
	 * @param cancel �Ƿ��ȡ��
	 */
	public void showProgressDialog(String message, boolean cancel);
	/**
	 * ��ʾ ���ȶԻ���
	 * @param message ��Ϣ
	 * @param cancel �Ƿ��ȡ��
	 * @param textsize �����С
	 */
	public void showProgressDialog(String message, boolean cancel,
			int textsize);

	/**
	 * ��ʾ ���ȶԻ���
	 * @param message ��Ϣ
	 * @param cancel �Ƿ��ȡ��
	 * @param textsize �����С
	 */
	public void showProgressDialog(String message,
			OnCancelListener listener);

	/**
	 * ��ʾ ���ȶԻ���
	 * @param message ��Ϣ
	 * @param cancel �Ƿ��ȡ��
	 * @param textsize �����С
	 */
	public void showProgressDialog(String message,
			OnCancelListener listener, int textsize);

	/**
	 * ���� ���ȶԻ���
	 */
	public void hideProgressDialog();

	/**
	 * ���� Activity
	 */
	public void startActivity(Intent intent);
	/**
	 * �������� AfActivity
	 * @param tclass
	 * 	ʡȥ���� Intent �Ĵ���
	 */
	public void startActivity(Class<? extends AfActivity> tclass);

	/**
	 * �������� AfActivity ForResult
	 * @param tclass
	 * @param request
	 * 	ʡȥ���� Intent �Ĵ���
	 */
	public void startActivityForResult(Class<? extends AfActivity> tclass,int request);
	

	/**
	 * ��ʾ�Ի��� �����Ĭ�ϰ�ť "��֪����"
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 */
	public void doShowDialog(String title, String message);
	/**
	 * ��ʾ�Ի��� �����Ĭ�ϰ�ť "��֪����"
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 * @param lpositive ���  "��֪����" ��Ӧ�¼�
	 */
	public void doShowDialog(String title, String message,OnClickListener lpositive);
	/**
	 * ��ʾ�Ի��� 
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 */
	public void doShowDialog(String title, String message,String positive,OnClickListener lpositive);
	/**
	 * ��ʾ�Ի��� 
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowDialog(String title, String message,
			String positive, OnClickListener lpositive, String negative,
			OnClickListener lnegative) ;

	/**
	 * ��ʾ�Ի��� 
	 * @param iconres �Ի���ͼ��
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowDialog(int iconres, String title, String message,
			String positive, OnClickListener lpositive, String negative,
			OnClickListener lnegative);

	/**
	 * ��ʾ�Ի��� 
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param neutral ��ϸ ��ť��ʾ��Ϣ
	 * @param lneutral ���  ��ϸ ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowDialog(String title, String message,
			String positive, OnClickListener lpositive, 
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative);
	
	/**
	 * ��ʾ�Ի��� 
	 * @param iconres �Ի���ͼ��
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param neutral ��ϸ ��ť��ʾ��Ϣ
	 * @param lneutral ���  ��ϸ ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowDialog(int iconres, String title, String message,
			String positive, OnClickListener lpositive, 
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative);

	/**
	 * ��ʾ�Ի��� 
	 * @param theme ���� 
	 * @param iconres �Ի���ͼ��
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param neutral ��ϸ ��ť��ʾ��Ϣ
	 * @param lneutral ���  ��ϸ ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowDialog(int theme,int iconres, String title, String message,
			String positive, OnClickListener lpositive, 
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative);
	
	/**
	 * ��ʾ��ͼ�Ի��� 
	 * @param title ��ʾ����
	 * @param view ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 */
	public void doShowViewDialog(String title, View view,String positive, OnClickListener lpositive);
	
	/**
	 * ��ʾ��ͼ�Ի��� 
	 * @param title ��ʾ����
	 * @param view ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowViewDialog(String title, View view,
			String positive, OnClickListener lpositive, String negative,
			OnClickListener lnegative);
	
	/**
	 * ��ʾ��ͼ�Ի��� 
	 * @param title ��ʾ����
	 * @param view ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param neutral ��ϸ ��ť��ʾ��Ϣ
	 * @param lneutral ���  ��ϸ ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowViewDialog(String title, View view,
			String positive, OnClickListener lpositive, 
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative) ;
	/**
	 * ��ʾ��ͼ�Ի��� 
	 * @param iconres �Ի���ͼ��
	 * @param title ��ʾ����
	 * @param view ��ʾ����
	 * @param positive ��ť��ʾ��Ϣ
	 * @param lpositive ���  positive ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  negative ��ť ��Ӧ�¼�
	 */
	public void doShowViewDialog(int iconres, String title, View view,
			String positive, OnClickListener lpositive, String negative,
			OnClickListener lnegative);

	/**
	 * ��ʾ��ͼ�Ի��� 
	 * @param iconres �Ի���ͼ��
	 * @param title ��ʾ����
	 * @param view ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param neutral ��ϸ ��ť��ʾ��Ϣ
	 * @param lneutral ���  ��ϸ ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowViewDialog(int iconres, String title, View view,
			String positive, OnClickListener lpositive, 
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative) ;

	/**
	 * ��ʾ��ͼ�Ի��� 
	 * @param theme ���� 
	 * @param iconres �Ի���ͼ��
	 * @param title ��ʾ����
	 * @param view ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param neutral ��ϸ ��ť��ʾ��Ϣ
	 * @param lneutral ���  ��ϸ ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowViewDialog(int theme,int iconres, String title, View view,
			String positive, OnClickListener lpositive, 
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative) ;

	/**
	 * ��ʾһ����ѡ�Ի��� 
	 * @param title �Ի������
	 * @param items ѡ��˵���
	 * @param listener ѡ�������
	 * @param oncancel ȡ��ѡ�������
	 */
	public void doSelectItem(String title,String[] items,OnClickListener listener,OnClickListener oncancel);

	/**
	 * ��ʾһ����ѡ�Ի��� ��Ĭ�Ͽ�ȡ����
	 * @param title �Ի������
	 * @param items ѡ��˵���
	 * @param listener ѡ�������
	 */
	public void doSelectItem(String title,String[] items,OnClickListener listener);
	
	/**
	 * ��ʾһ����ѡ�Ի��� �����ÿ�ȡ����
	 * @param title �Ի������
	 * @param items ѡ��˵���
	 * @param listener ѡ�������
	 * @param cancel �Ƿ����ȡ��
	 */
	public void doSelectItem(String title,String[] items,OnClickListener listener,boolean cancel);
	

	/**
	 * doInputText �ļ�����
	 * @author SCWANG
	 */
	public interface InputTextListener{
		void onInputTextComfirm(EditText input);
	}

	/**
	 * ��ȡ���� InputTextListener
	 * @author SCWANG
	 */
	public interface InputTextCancelable extends InputTextListener{
		void onInputTextCancel(EditText input);
	}

	/**
	 * ������������
	 */
	public static final int INPUTTYPE_PASSWORD =  InputType.TYPE_CLASS_TEXT 
	| InputType.TYPE_TEXT_VARIATION_PASSWORD;
	
	/**
	 * ����һ���ı������
	 * @param title ����
	 * @param listener ������
	 */
	public void doInputText(String title,InputTextListener listener);

	/**
	 * ����һ���ı������
	 * @param title ����
	 * @param type android.text.InputType
	 * @param listener ������
	 */
	public void doInputText(String title,int type,InputTextListener listener);

	/**
	 * ����һ���ı������
	 * @param title ����
	 * @param defaul Ĭ��ֵ
	 * @param type android.text.InputType
	 * @param listener ������
	 */
	public void doInputText(String title,String defaul,int type,InputTextListener listener);
}
