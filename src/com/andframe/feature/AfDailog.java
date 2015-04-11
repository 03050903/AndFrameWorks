package com.andframe.feature;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.andframe.activity.AfActivity;
import com.andframe.activity.framework.AfPageable.InputTextCancelable;
import com.andframe.activity.framework.AfPageable.InputTextListener;

public class AfDailog {
	
	private Context mContext;
	
	public AfDailog(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	/**
	 * ��ʾ�Ի��� �����Ĭ�ϰ�ť "��֪����"
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 */
	public void doShowDialog(String title, String message) {
		doShowDialog(0,title,message,"��֪����", null, "", null);
	}
	/**
	 * ��ʾ�Ի��� �����Ĭ�ϰ�ť "��֪����"
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 * @param lpositive ���  "��֪����" ��Ӧ�¼�
	 */
	public void doShowDialog(String title, String message,OnClickListener lpositive) {
		doShowDialog(0,title,message,"��֪����", lpositive, "", null);
	}
	/**
	 * ��ʾ�Ի��� 
	 * @param title ��ʾ����
	 * @param message ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 */
	public void doShowDialog(String title, String message,String positive,OnClickListener lpositive) {
		doShowDialog(0,title,message,positive, lpositive, "", null);
	}
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
			OnClickListener lnegative) {
		doShowDialog(0,title,message,positive, lpositive,negative,lnegative);
	}
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
			String negative,OnClickListener lnegative){
		doShowDialog(0, title, message,positive, lpositive, neutral, lneutral, negative,lnegative);
	}
	
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
			OnClickListener lnegative) {
		doShowDialog(iconres, title, message,positive, lpositive, "", null, negative,lnegative);
	}

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
			String negative,OnClickListener lnegative) {
		// TODO Auto-generated method stub
		doShowDialog(-1, iconres, title, message, positive, lpositive, neutral, lneutral, negative, lnegative);
	}

	/**
	 * ��ʾ��ͼ�Ի��� 
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
	@SuppressLint("NewApi")
	public void doShowDialog(int theme, int iconres, 
			String title,String message, 
			String positive, OnClickListener lpositive,
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative) {
		// TODO Auto-generated method stub
		Builder builder = null;
		try {
			builder = new Builder(mContext);
		} catch (Throwable ex) {
			// TODO: handle exception
			return;
		}
		if (theme > 0) {
			try {
				builder = new Builder(mContext, theme);
			} catch (Throwable e) {
				// TODO: handle exception
				builder = new Builder(mContext);
			}
		}
		builder.setTitle(title);
		builder.setMessage(message);
		if (iconres > 0) {
			builder.setIcon(iconres);
		}
		if (positive != null && positive.length() > 0) {
			builder.setPositiveButton(positive, lpositive);
		}
		if (neutral != null && neutral.length() > 0) {
			builder.setNeutralButton(neutral, lneutral);
		}
		if (negative != null && negative.length() > 0) {
			builder.setNegativeButton(negative, lnegative);
		}
		builder.setCancelable(false);
		builder.create();
		builder.show();
	}
	/**
	 * ��ʾ��ͼ�Ի��� 
	 * @param title ��ʾ����
	 * @param view ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 */
	public void doShowViewDialog(String title, View view, String positive,
			OnClickListener lpositive) {
		// TODO Auto-generated method stub
		doShowViewDialog(title, view, positive, lpositive,"",null);
	}

	/**
	 * ��ʾ��ͼ�Ի��� 
	 * @param title ��ʾ����
	 * @param view ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowViewDialog(String title, View view, String positive,
			OnClickListener lpositive, String negative,
			OnClickListener lnegative) {
		// TODO Auto-generated method stub
		doShowViewDialog(0,title,view,positive, lpositive,negative,lnegative);
	}
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
			String negative,OnClickListener lnegative) {
		doShowViewDialog(0,title,view,positive, lpositive,neutral,lneutral,negative,lnegative);
	}
	/**
	 * ��ʾ��ͼ�Ի��� 
	 * @param iconres �Ի���ͼ��
	 * @param title ��ʾ����
	 * @param view ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 * @param negative ��ť��ʾ��Ϣ
	 * @param lnegative ���  �ܾ� ��ť ��Ӧ�¼�
	 */
	public void doShowViewDialog(int iconres, String title, View view,
			String positive, OnClickListener lpositive, 
			String negative,OnClickListener lnegative) {
		doShowViewDialog(0,title,view,positive, lpositive,"",null,negative,lnegative);
	}
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
			String negative,OnClickListener lnegative) {
		// TODO Auto-generated method stub
		doShowViewDialog(-1, iconres, title, view, positive, lpositive, neutral, lneutral, negative, lnegative);
	}

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
	@SuppressLint("NewApi")
	public void doShowViewDialog(int theme, 
			int iconres, String title,View view, 
			String positive, OnClickListener lpositive,
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative) {
		// TODO Auto-generated method stub
		Builder builder = null;
		try {
			builder = new Builder(mContext);
		} catch (Throwable ex) {
			// TODO: handle exception
			return;
		}
		if (theme > 0) {
			try {
				builder = new Builder(mContext, theme);
			} catch (Throwable e) {
				// TODO: handle exception
				builder = new Builder(mContext);
			}
		}
		builder.setTitle(title);
		RelativeLayout.LayoutParams lp = null;
		lp = new RelativeLayout.LayoutParams(AfActivity.LP_WC,AfActivity.LP_WC);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		RelativeLayout layout = new RelativeLayout(mContext);
		layout.addView(view, lp);
		builder.setView(layout);
		if (iconres > 0) {
			builder.setIcon(iconres);
		}
		if (positive != null && positive.length() > 0) {
			builder.setPositiveButton(positive, lpositive);
		}
		if (neutral != null && neutral.length() > 0) {
			builder.setNeutralButton(neutral, lneutral);
		}
		if (negative != null && negative.length() > 0) {
			builder.setNegativeButton(negative, lnegative);
		}
		builder.setCancelable(false);
		builder.create();
		builder.show();
	}
	/**
	 * ��ʾһ����ѡ�Ի��� �����ÿ�ȡ����
	 * @param title �Ի������
	 * @param items ѡ��˵���
	 * @param listener ѡ�������
	 * @param cancel ȡ��ѡ�������
	 */
	public void doSelectItem(String title,String[] items,OnClickListener listener,
			boolean cancel){
		Builder dialog = new Builder(mContext);
		dialog.setItems(items,listener);
		if(title != null){
			dialog.setTitle(title);
			dialog.setCancelable(false);
			if(cancel){
				dialog.setNegativeButton("ȡ��", null);
			}
		}else{
			dialog.setCancelable(cancel);
		}
		dialog.show();
	}

	/**
	 * ��ʾһ����ѡ�Ի��� 
	 * @param title �Ի������
	 * @param items ѡ��˵���
	 * @param listener ѡ�������
	 * @param oncancel ȡ��ѡ�������
	 */
	public void doSelectItem(String title,String[] items,OnClickListener listener,
			final OnClickListener oncancel) {
		// TODO Auto-generated method stub
		Builder dialog = new Builder(mContext);
		if(title != null){
			dialog.setTitle(title);
			dialog.setCancelable(false);
			dialog.setNegativeButton("ȡ��", oncancel);
		}else if(oncancel != null){
			dialog.setCancelable(true);
			dialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					oncancel.onClick(dialog, 0);
				}
			});
		}
		dialog.setItems(items,listener);
		dialog.show();
	}

	/**
	 * ��ʾһ����ѡ�Ի��� ��Ĭ�Ͽ�ȡ����
	 * @param title �Ի������
	 * @param items ѡ��˵���
	 * @param listener ѡ�������
	 */
	public void doSelectItem(String title,String[] items,OnClickListener listener) {
		// TODO Auto-generated method stub
		doSelectItem(title, items, listener, null);
	}

	/**
	 * ����һ���ı������
	 * @param title ����
	 * @param listener ������
	 */
	public void doInputText(String title,InputTextListener listener) {
		doInputText(title, "", InputType.TYPE_CLASS_TEXT, listener);
	}

	/**
	 * ����һ���ı������
	 * @param title ����
	 * @param type android.text.InputType
	 * @param listener ������
	 */
	public void doInputText(String title,int type,InputTextListener listener) {
		doInputText(title, "", type, listener);
	}

	/**
	 * ����һ���ı������
	 * @param title ����
	 * @param defaul Ĭ��ֵ
	 * @param type android.text.InputType
	 * @param listener ������
	 */
	public void doInputText(String title,String defaul,int type,InputTextListener listener) {
		final EditText input = new EditText(mContext);
		final int defaullength = defaul.length();
		final InputTextListener flistener = listener;
		input.setText(defaul);
		input.clearFocus();
		input.setInputType(type);
		Builder builder = new AlertDialog.Builder(mContext);
		builder.setView(input);
		builder.setCancelable(false);
		builder.setTitle(title);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				new AfSoftInputer(mContext).setSoftInputEnable(input, false);
				dialog.dismiss();
				flistener.onInputTextComfirm(input);
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				new AfSoftInputer(mContext).setSoftInputEnable(input, false);
				dialog.dismiss();
				if(flistener instanceof InputTextCancelable){
					InputTextCancelable cancel = (InputTextCancelable)flistener;
					cancel.onInputTextCancel(input);
				}
			}
		});
		final AlertDialog dialog = builder.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			public void onShow(DialogInterface dialog) {
				new AfSoftInputer(mContext).setSoftInputEnable(input, true);
				input.setSelection(0,defaullength);
			}
		});
		dialog.show();
	}
	
}
