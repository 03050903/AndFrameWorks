package com.andframe.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andframe.activity.AfActivity;
import com.andframe.activity.framework.AfPageable;
import com.andframe.activity.framework.AfView;
import com.andframe.application.AfApplication;
import com.andframe.application.AfDaemonThread;
import com.andframe.application.AfExceptionHandler;
import com.andframe.exception.AfException;
import com.andframe.exception.AfToastException;
import com.andframe.feature.AfBundle;
import com.andframe.feature.AfDailog;
import com.andframe.feature.AfIntent;
import com.andframe.feature.AfSoftInputer;
import com.andframe.feature.AfViewBinder;
import com.andframe.thread.AfTask;
import com.andframe.thread.AfThreadWorker;
/**
 * ��� AfFragment
 * @author SCWANG
 *
 *	������ AfFragment �������ṩ�� ���ܷ���
 *
	protected void buildThreadWorker()
	 * Ϊ��ҳ�濪��һ��������̨�߳� �� postTask �� ����(AfTask)���� ע�⣺�����߳�֮�� postTask
	 * �κ����񶼻��ڸ��߳������С� ��� postTask ǰһ������δ��ɣ���һ�����񽫵ȴ�
	 * 
	protected AfTask postTask(AfTask task)
	 * ��������Workerִ��

	AfPageable �ӿ��еķ���
	public Activity getActivity();
	public void makeToastLong(String tip);
	public void makeToastShort(String tip);
	public void makeToastLong(int resid);
	public void makeToastShort(int resid);
	public boolean getSoftInputStatus();
	public boolean getSoftInputStatus(View view);
	public void setSoftInputEnable(EditText editview, boolean enable);
	public void showProgressDialog(String message);
	public void showProgressDialog(String message, boolean cancel);
	public void showProgressDialog(String message, boolean cancel,int textsize);
	public void showProgressDialog(String message, listener);
	public void showProgressDialog(String message, listener, int textsize);
	public void hideProgressDialog();
	public void startActivity(Class<? extends AfActivity> tclass);
	public void startActivityForResult(Class<AfActivity> tclass,int request);
	
	public void doShowDialog(String title, String message);
	public void doShowDialog(String title, String message,OnClickListener);
	public void doShowDialog(String title, String message,String ,OnClickListener);
	public void doShowDialog(String, String,String,OnClickListener,String,OnClickListener);
	public void doShowDialog(int,String,String,String,OnClickListener,String,OnClickListener);
	public void doShowDialog(int,String,String,String,Listener,String,Listener,String,Listener);
	
	public void doShowViewDialog(title, View view,String positive, OnClickListener );
	public void doShowViewDialog(title, View view,String positive, OnClickListener , String negative,OnClickListener );
	public void doShowViewDialog(title,view,String,Listener,String,Listener,String,Listener);
	public void doShowViewDialog(int iconres, title,  view,String, OnClickListener,String,OnClickListener );
	public void doShowViewDialog(int iconres,title,view,String,Listener,String,Listener,String,Listener);
	
	public void doSelectItem(String title,String[] items,OnClickListener);
	public void doSelectItem(String title,String[] items,OnClickListener,cancel);
	public void doSelectItem(String title,String[] items,OnClickListener,oncancel);
	
	public void doInputText(String title,InputTextListener listener);
	public void doInputText(String title,int type,InputTextListener listener);
	public void doInputText(String title,String defaul,int type,InputTextListener listener);
	
	AfPageListener �ӿ��еķ���
	public void onSoftInputShown();
	public void onSoftInputHiden();
	public void onQueryChanged();
}
 */
public abstract class AfFragment extends Fragment implements AfPageable {

	public static final String EXTRA_DATA = "EXTRA_DATA";
	public static final String EXTRA_INDEX = "EXTRA_INDEX";
	public static final String EXTRA_RESULT = "EXTRA_RESULT";

	public static final int LP_MP = LayoutParams.MATCH_PARENT;
	public static final int LP_WC = LayoutParams.WRAP_CONTENT;
	// ����ͼ
	protected View mRootView = null;

	protected AfThreadWorker mWorker = null;
	protected ProgressDialog mProgress = null;
	protected boolean mIsRecycled = false;

	/**
	 * @Description: ��ȡLOG��־ TAG �� AfFragment �ķ���
	 * �û�Ҳ������д�Զ���TAG,���ֵAfActivity����־��¼ʱ���ʹ��
	 * ����ʵ��Ҳ����ʹ��
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-14 ����10:58:00
	 * @Modified: ���δ���TAG����
	 * @return
	 */
	protected String TAG() {
		// TODO Auto-generated method stub
		return "AfFragment("+getClass().getName()+")";
	}
	protected String TAG(String tag) {
		// TODO Auto-generated method stub
		return "AfFragment("+getClass().getName()+")."+tag;
	}
	/**
	 * �ж��Ƿ񱻻���
	 * @return true �Ѿ�������
	 */
	@Override
	public boolean isRecycled() {
		// TODO Auto-generated method stub
		return mIsRecycled ;
	}
	
	/**
	 * Ϊ��ҳ�濪��һ��������̨�߳� �� postTask �� ����(AfTask)���� ע�⣺�����߳�֮�� postTask
	 * �κ����񶼻��ڸ��߳������С� ��� postTask ǰһ������δ��ɣ���һ�����񽫵ȴ�
	 */
	protected void buildThreadWorker() {
		// TODO Auto-generated method stub
		if (mWorker == null) {
			mWorker = new AfThreadWorker(this.getClass().getSimpleName());
		}
	}
	/**
	 * ��������Workerִ��
	 * @param task
	 */
	public AfTask postTask(AfTask task) {
		// TODO Auto-generated method stub
		if (mWorker != null) {
			return mWorker.postTask(task);
		}
		return AfDaemonThread.postTask(task);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	public void startActivity(Class<? extends AfActivity> tclass) {
		// TODO Auto-generated method stub
		startActivity(new Intent(getActivity(), tclass));
	}
	
	public void startActivityForResult(Class<? extends AfActivity> tclass,int request) {
		// TODO Auto-generated method stub
		startActivityForResult(new Intent(getActivity(),tclass), request);
	}

	/**
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 * final ��д onActivityResult ʹ�� try-catch ���� 
	 * 		onActivityResult(AfIntent intent, int questcode,int resultcode)
	 * @see AfFragment#onActivityResult(AfIntent intent, int questcode,int resultcode)
	 * {@link AfFragment#onActivityResult(AfIntent intent, int questcode,int resultcode)}
	 */
	@Override
	public final void onActivityResult(int questcode, int resultcode, Intent data) {
		// TODO Auto-generated method stub
		try {
			onActivityResult(new AfIntent(data), questcode, resultcode);
		} catch (Throwable e) {
			// TODO: handle exception
			if (!(e instanceof AfToastException)) {
				AfExceptionHandler.handler(e, TAG("onActivityResult"));
			}
			makeToastLong("������Ϣ��ȡ����",e);
		}
	}

	/**
	 * ��ȫ onActivityResult(AfIntent intent, int questcode,int resultcode) 
	 * ��onActivityResult(int questCode, int resultCode, Intent data) �е���
	 * ��ʹ�� try-catch ��߰�ȫ�ԣ���������д������� 
	 * @see AfFragment#onActivityResult(int, int, android.content.Intent)
	 * {@link AfFragment#onActivityResult(int, int, android.content.Intent)}
	 * @param intent
	 * @param questcode
	 * @param resultcode
	 */
	protected void onActivityResult(AfIntent intent, int questcode,int resultcode) {
		// TODO Auto-generated method stub
		super.onActivityResult(questcode, resultcode, intent);
	}

	/**
	 * �Զ��� View onCreate(Bundle)
	 */
	protected abstract void onCreated(AfView rootView, AfBundle bundle)throws Exception;

	/**
	 * �Զ��� View onCreateView(LayoutInflater, ViewGroup)
	 */
	protected abstract View onCreateView(LayoutInflater inflater,
			ViewGroup container);

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.onQueryChanged();
	}
	
	@Override
	public final void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
	}
	
	/**
	 * ��ס �ϼ��� View onCreateView(LayoutInflater, ViewGroup, Bundle)
	 */
	@Override
	public final View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle bundle) {
		// TODO Auto-generated method stub
		mRootView = onCreateView(inflater, container);
		if (mRootView == null) {
			mRootView = super.onCreateView(inflater, container,bundle);
		}
		try {
//			mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
			AfViewBinder binder = new AfViewBinder(this);
			binder.doBind(mRootView);
			AfSoftInputer inputer = new AfSoftInputer(getActivity());
			inputer.setBindListener(mRootView, this);
			onCreated(new AfView(mRootView), new AfBundle(getArguments()));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			if (!(e instanceof AfToastException)) {
				AfExceptionHandler.handler(e, TAG("onCreateView"));
			}
			makeToastLong("ҳ���ʼ���쳣��",e);
		}
		return mRootView;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
//		mRootView = null;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mIsRecycled = true;
		if (mWorker != null) {
			mWorker.quit();
		}
	}

	/**
	 * ��һ���л�����ҳ��
	 */
	protected void onFirstSwitchOver() {
	}

	/**
	 * ÿ���л�����ҳ��
	 * 
	 * @param count
	 *            �л����
	 */
	protected void onSwitchOver(int count) {
	}

	/**
	 * �뿪��ҳ��
	 */
	protected void onSwitchLeave() {
	}

	/**
	 * ��ѯϵͳ���ݱ䶯
	 */
	public void onQueryChanged() {
		// TODO Auto-generated method stub
	}

	
	@Override
	public boolean getSoftInputStatus() {
		// TODO Auto-generated method stub
		return new AfSoftInputer(getActivity()).getSoftInputStatus();
	}
	
	@Override
	public boolean getSoftInputStatus(View view) {
		// TODO Auto-generated method stub
		return new AfSoftInputer(getActivity()).getSoftInputStatus(view);
	}
	
	@Override
	public void setSoftInputEnable(EditText editview, boolean enable) {
		// TODO Auto-generated method stub
		new AfSoftInputer(getActivity()).setSoftInputEnable(editview, enable);
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		Activity activity = getActivity();
		if (activity == null) {
			return AfApplication.getAppContext();
		}
		return activity;
	}

	@Override
	public void makeToastLong(String tip) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), tip, Toast.LENGTH_LONG).show();
	}

	@Override
	public void makeToastShort(String tip) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), tip, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void makeToastLong(int resid) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), resid, Toast.LENGTH_LONG).show();
	}

	@Override
	public void makeToastShort(int resid) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), resid, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void makeToastLong(String tip,Throwable e) {
		// TODO Auto-generated method stub
		tip = AfException.handle(e, tip);
		Toast.makeText(getContext(), tip, Toast.LENGTH_LONG).show();
	}

	
	@Override
	public final View findViewById(int id) {
		if (mRootView != null) {
			return mRootView.findViewById(id);
		}
		return null;
	}

	@Override
	public <T extends View> T findViewById(int id, Class<T> clazz) {
		// TODO Auto-generated method stub
		View view = findViewById(id);
		if (clazz.isInstance(view)) {
			return clazz.cast(view);
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends View> T findViewByID(int id) {
		// TODO Auto-generated method stub
		try {
			return (T)findViewById(id);
		} catch (Exception e) {
			// TODO: handle exception
			AfExceptionHandler.handler(e, TAG("findViewByID"));
		}
		return null;
	}
	/**
	 * ��ʾ ���ȶԻ���
	 * 
	 * @param message
	 *            ��Ϣ
	 */
	public final void showProgressDialog(String message) {
		// TODO Auto-generated method stub
		showProgressDialog(message, false, 25);
	}

	/**
	 * ��ʾ ���ȶԻ���
	 * 
	 * @param message
	 *            ��Ϣ
	 * @param cancel
	 *            �Ƿ��ȡ��
	 */
	public final void showProgressDialog(String message, boolean cancel) {
		// TODO Auto-generated method stub
		showProgressDialog(message, cancel, 25);
	}

	/**
	 * ��ʾ ���ȶԻ���
	 * 
	 * @param message
	 *            ��Ϣ
	 * @param cancel
	 *            �Ƿ��ȡ��
	 * @param textsize
	 *            �����С
	 */
	public final void showProgressDialog(String message, boolean cancel,
			int textsize) {
		// TODO Auto-generated method stub
		try {
			mProgress = new ProgressDialog(getActivity());
			mProgress.setMessage(message);
			mProgress.setCancelable(cancel);
			mProgress.setOnCancelListener(null);
			mProgress.show();

			setDialogFontSize(mProgress, textsize);
		} catch (Exception e) {
			// TODO: handle exception
			//������־��֤������쳣�ᷢ�ͣ����Ǹ��ʷǳ�С��ע�͵��쳣֪ͨ
//			AfExceptionHandler.handler(e, "AfActivity.showProgressDialog");
		}
	}

	/**
	 * ��ʾ ���ȶԻ���
	 * 
	 * @param message
	 *            ��Ϣ
	 * @param cancel
	 *            �Ƿ��ȡ��
	 * @param textsize
	 *            �����С
	 */
	public final void showProgressDialog(String message,
			OnCancelListener listener) {
		// TODO Auto-generated method stub
		try {
			mProgress = new ProgressDialog(getActivity());
			mProgress.setMessage(message);
			mProgress.setCancelable(true);
			mProgress.setOnCancelListener(listener);
			mProgress.show();

			setDialogFontSize(mProgress, 25);
		} catch (Exception e) {
			// TODO: handle exception
			//������־��֤������쳣�ᷢ�ͣ����Ǹ��ʷǳ�С��ע�͵��쳣֪ͨ
//			AfExceptionHandler.handler(e, "AfActivity.showProgressDialog");
		}
	}

	/**
	 * ��ʾ ���ȶԻ���
	 * 
	 * @param message
	 *            ��Ϣ
	 * @param cancel
	 *            �Ƿ��ȡ��
	 * @param textsize
	 *            �����С
	 */
	public final void showProgressDialog(String message,
			OnCancelListener listener, int textsize) {
		// TODO Auto-generated method stub
		try {
			mProgress = new ProgressDialog(getActivity());
			mProgress.setMessage(message);
			mProgress.setCancelable(true);
			mProgress.setOnCancelListener(listener);
			mProgress.show();

			setDialogFontSize(mProgress, textsize);
		} catch (Exception e) {
			// TODO: handle exception
			//������־��֤������쳣�ᷢ�ͣ����Ǹ��ʷǳ�С��ע�͵��쳣֪ͨ
//			AfExceptionHandler.handler(e, "AfActivity.showProgressDialog");
		}
	}

	@Override
	public void onSoftInputHiden() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onSoftInputShown() {
		// TODO Auto-generated method stub
	}
	/**
	 * ���� ���ȶԻ���
	 */
	public final void hideProgressDialog() {
		// TODO Auto-generated method stub
		try {
			if (mProgress != null && !isRecycled()) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			AfExceptionHandler.handler(e, "AfActivity.hideProgressDialog");
		}
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
	@Override
	public void doShowDialog(String title, String message, 
			String positive, OnClickListener lpositive, 
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative) {
		// TODO Auto-generated method stub
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
	@Override
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
	@Override
	public void doShowDialog(int theme, int iconres, 
			String title,String message, 
			String positive, OnClickListener lpositive,
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative) {
		// TODO Auto-generated method stub
		new AfDailog(getActivity()).doShowDialog(theme, iconres, title, message, positive, lpositive, neutral, lneutral, negative, lnegative);
	}
	/**
	 * ��ʾ��ͼ�Ի��� 
	 * @param title ��ʾ����
	 * @param view ��ʾ����
	 * @param positive ȷ�� ��ť��ʾ��Ϣ
	 * @param lpositive ���  ȷ�� ��ť ��Ӧ�¼�
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
	public void doShowViewDialog(int theme, 
			int iconres, String title,View view, 
			String positive, OnClickListener lpositive,
			String neutral, OnClickListener lneutral, 
			String negative,OnClickListener lnegative) {
		// TODO Auto-generated method stub
		new AfDailog(getActivity()).doShowViewDialog(theme, iconres, title, view, positive, lpositive, neutral, lneutral, negative, lnegative);
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
		new AfDailog(getActivity()).doSelectItem(title, items, listener, cancel);
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
		new AfDailog(getActivity()).doSelectItem(title, items, listener, oncancel);
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
		new AfDailog(getActivity()).doInputText(title, defaul, type, listener);
	}
	
	protected void setProgressDialogText(ProgressDialog dialog, String text) {
		Window window = dialog.getWindow();
		View view = window.getDecorView();
		setViewFontText(view, text);
	}

	private void setViewFontText(View view, String text) {
		// TODO Auto-generated method stub
		if (view instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) view;
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++) {
				setViewFontText(parent.getChildAt(i), text);
			}
		} else if (view instanceof TextView) {
			TextView textview = (TextView) view;
			textview.setText(text);
		}
	}
	
	private void setDialogFontSize(Dialog dialog, int size) {
		Window window = dialog.getWindow();
		View view = window.getDecorView();
		setViewFontSize(view, size);
	}

	private void setViewFontSize(View view, int size) {
		if (view instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) view;
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++) {
				setViewFontSize(parent.getChildAt(i), size);
			}
		} else if (view instanceof TextView) {
			TextView textview = (TextView) view;
			textview.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
		}
	}

	/**
	 * ���·��ذ���
	 * @return ���� true ��ʾ�Ѿ����� ���� Activity �ᴦ��
	 */
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * ���������¼�
	 * @return ���� true ��ʾ�Ѿ����� ���� Activity �ᴦ��
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * ���������¼�
	 * @return ���� true ��ʾ�Ѿ����� ���� Activity �ᴦ��
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * �����ظ��¼�
	 * @return ���� true ��ʾ�Ѿ����� ���� Activity �ᴦ��
	 */
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * ����onKeyShortcut�¼�
	 * @return ���� true ��ʾ�Ѿ����� ���� Activity �ᴦ��
	 */
	public boolean onKeyShortcut(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * ����onKeyLongPress�¼�
	 * @return ���� true ��ʾ�Ѿ����� ���� Activity �ᴦ��
	 */
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
