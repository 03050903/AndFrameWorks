package com.andframe.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.andframe.application.AfExceptionHandler;
import com.andframe.exception.AfToastException;
import com.andframe.feature.AfIntent;
import com.andframe.util.java.AfStackTrace;
/**
 * ��� Activity
 * @author SCWANG
 */
public abstract class AfActivity extends com.andframe.activity.framework.AfActivity implements OnItemClickListener {

	/**
	 * final ԭʼ onCreate(Bundle bundle)
	 * ����ֻ����д onCreate(Bundle bundle,AfIntent intent)
	 */
	@Override
	protected final void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		try {
			if (AfStackTrace.isLoopCall()) {
				//System.out.println("�ݹ���");
				return;
			}
			this.onCreate(bundle, new AfIntent(getIntent()));
		} catch (final Throwable e) {
			// TODO: handle exception
			//handler ���ܻ���� Activity ������ʾ������Ϣ
			//��ǰ Activity �����رգ���ʾ����Ҳ��ر�
			//�ö�ʱ�� �ȵ�ԭʼ Activity ����ʾ����
			if (!(e instanceof AfToastException)) {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						AfExceptionHandler.handler(e, TAG()+".onCreate");
					}
				},500);
			}
			makeToastLong("ҳ������ʧ��",e);
			this.finish();
		}
	}

	/**
	 * �µ� onCreate ʵ��
	 * @param bundle
	 * @param intent 
	 * @throws Exception ��ȫ�쳣
	 * 	��д�� ʱ�� һ��������� ����
	 * 		super.onCreate(bundle,intent);
	 */
	protected void onCreate(Bundle bundle,AfIntent intent) throws Exception{
		super.onCreate(bundle);
	}
	
	/**
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 * final ��д onActivityResult ʹ�� try-catch ���� 
	 * 		onActivityResult(AfIntent intent, int questcode,int resultcode)
	 * @see AfActivity#onActivityResult(AfIntent intent, int questcode,int resultcode)
	 * {@link AfActivity#onActivityResult(AfIntent intent, int questcode,int resultcode)}
	 */
	@Override
	protected final void onActivityResult(int questcode, int resultcode, Intent data) {
		// TODO Auto-generated method stub
		try {
			if (AfStackTrace.isLoopCall()) {
				//System.out.println("�ݹ���");
				return;
			}
			onActivityResult(new AfIntent(data), questcode, resultcode);
		} catch (Throwable e) {
			// TODO: handle exception
			if (!(e instanceof AfToastException)) {
				AfExceptionHandler.handler(e, TAG()+".onActivityResult");
			}
			makeToastLong("������Ϣ��ȡ����",e);
		}
	}
	/**
	 * @Description: final ��װ onItemClick �¼����� ��ֹ�׳��쳣����
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-14 ����10:34:56
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	@Override
	public final void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		try {
			if (AfStackTrace.isLoopCall()) {
				//System.out.println("�ݹ���");
				return;
			}
			this.onItemClick(parent,view,id,position);
		} catch (Exception e) {
			// TODO: handle exception
			AfExceptionHandler.handler(e, TAG()+".onItemClick");
		}
	}

	/**
	 * @Description: 
	 * ��ȫonItemClick��ܻᲶ׽�쳣��ֹ����
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-14 ����10:38:56
	 * @Modified: ���δ���onItemClick����
	 * @param parent
	 * @param item
	 * @param id
	 * @param index
	 */
	protected void onItemClick(AdapterView<?> parent, View item, long id,int index) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ��ȫ onActivityResult(AfIntent intent, int questcode,int resultcode) 
	 * ��onActivityResult(int questCode, int resultCode, Intent data) �е���
	 * ��ʹ�� try-catch ��߰�ȫ�ԣ���������д������� 
	 * @see AfActivity#onActivityResult(int, int, android.content.Intent)
	 * {@link AfActivity#onActivityResult(int, int, android.content.Intent)}
	 * @param intent
	 * @param questcode
	 * @param resultcode
	 */
	protected void onActivityResult(AfIntent intent, int questcode,int resultcode) {
		// TODO Auto-generated method stub
		super.onActivityResult(questcode, resultcode, intent);
	}

	/**
	 * ת�� onBackPressed �¼��� AfFragment
	 */
	@Override
	public final void onBackPressed() {
		// TODO Auto-generated method stub
		if (AfStackTrace.isLoopCall()) {
			super.onBackPressed();
			return;
		}
		
		if(!this.onBackKeyPressed()){
			super.onBackPressed();
		}
	}

	/**
	 * ת�� onBackPressed �¼��� AfFragment
	 */
	protected boolean onBackKeyPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}
