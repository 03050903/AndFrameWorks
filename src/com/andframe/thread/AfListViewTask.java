package com.andframe.thread;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.andframe.activity.AfActivity;
import com.andframe.adapter.AfListAdapter;
import com.andframe.application.AfApplication;
import com.andframe.bean.Page;

public abstract class AfListViewTask<T> extends AfListTask<T>
{
    //ö����������
    public static final int TASK_NULL = 1000;    //����ˢ��
	/**
	 * ����������캯�����Դ��� TASK_LOAD ����
	 * �����ϴλ������ݣ�������ڽ����� TASK_REFRESH ����������
	 * ����ʹ�õ� KEY_CACHELIST = this.getClass().getName()
	 * 		KEY_CACHELIST Ϊ����ı�ʶ
	 * ����Ĺ��� ���Ϊ AfListTask.CACHETIMEOUTSECOND
	 * 		���ʱ����������App��ʼ��ʱ�����
	 * @param clazz ����Model�������jsonҪ�õ���
	 */
	public AfListViewTask(Class<T> clazz) {
		super(clazz);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ����������캯�����Դ��� TASK_LOAD ����
	 * �����ϴλ������ݣ�������ڽ����� TASK_REFRESH ����������
	 * ����ʹ�õ� KEY_CACHELIST �����Զ���
	 * ����Ĺ��� ���Ϊ AfListTask.CACHETIMEOUTSECOND
	 * 		���ʱ����������App��ʼ��ʱ�����
	 * @param clazz ����Model�������jsonҪ�õ���
	 * @param KEY_CACHELIST �����KEY��ʶ
	 */
	public AfListViewTask(Class<T> clazz, String KEY_CACHELIST) {
		super(clazz, KEY_CACHELIST);
		// TODO Auto-generated constructor stub
	}


	public AfListViewTask(int task) {
		super(task);
		// TODO Auto-generated constructor stub
	}
	
	public AfListViewTask(int task,int first) {
		super(task,first);
		// TODO Auto-generated constructor stub
	}
	
	public AfListViewTask(Handler handle, int task) {
		super(handle, task);
		// TODO Auto-generated constructor stub
	}
	

	public AfListViewTask(List<T> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	public AfListViewTask(AfListAdapter<T> adapter) {
		super(adapter);
		// TODO Auto-generated constructor stub
	}
	/**
	 * ���ݷ�ҳ���أ����첽�߳���ִ�У������Ը���ҳ�������
	 * @param page ��ҳ����
	 * @param task ����id
	 * @return ���ص��������б�
	 * @throws Exception
	 */
	protected abstract List<T> onListByPage(Page page, int task) throws Exception ;

	@Override
	protected List<T> onRefresh(Page page) throws Exception{
		return onListByPage(page,mTask);
	}

	@Override
	protected List<T> onMore(Page page) throws Exception {
		return onListByPage(page,mTask);
	}

	@Override
	protected boolean onHandle(Message msg) {
		// TODO Auto-generated method stub
		boolean isfinish = isFinish();
		boolean dealerror = false;
		if (mTask == TASK_LOAD) {
			dealerror = this.onLoaded(isfinish,mltData,getCacheTime());
		}else if (mTask == TASK_MORE) {
			dealerror = this.onMored(isfinish,mltData,mltData.size() < PAGE_SIZE);
		}else if (mTask == TASK_REFRESH) {
			dealerror = this.onRefreshed(isfinish,mltData);
		}else {
			dealerror = this.onWorked(mTask,isfinish,mltData);
		}
		if (!dealerror && !isfinish) {
			this.onDealError(mTask,mErrors,mException);
		}
		return false;
	}
	/**
	 * ����ִ�г��� ֮��ͳһ���������񣩴��������ʾ
	 * 	����� onRefreshed��onMored��onWorked��onLoaded ����true ��ʾ�Ѿ���������ʾ
	 * �󾮰������ڵ��� onDealError 
	 * @param task
	 * @param rrrors
	 * @param exception
	 */
	protected void onDealError(int task, String rrrors, Throwable exception) {
		// TODO Auto-generated method stub
		AfActivity activity = AfApplication.getApp().getCurActivity();
		if (activity != null && !activity.isRecycled()) {
			if (exception instanceof IOException) {
				activity.makeToastLong("��������쳣");
			}else {
				activity.makeToastLong(rrrors);
			}
		}
	}
	/**
	 * ˢ������ִ�н��� ��ҳ�洦����
	 * @param isfinish true �ɹ�ִ������ˢ�� false ʧ��
	 * @param ltdata ˢ�µ�����
	 * @return ���� true ��ʾ isfinish=false ʱ�� �Ѿ�����ʧ����ʾ���� �����ڵ��� onDealError
	 */
	protected abstract boolean onRefreshed(boolean isfinish, List<T> ltdata);

	/**
	 * ���ظ�������ִ�н��� ��ҳ�洦����
	 * @param isfinish true �ɹ�ִ������ˢ�� false ʧ��
	 * @param ltdata ˢ�µ�����
	 * @param ended true ��ʾ�Ƿ��Ѿ�������� ���ڿ��Ƹ��ఴť����ʾ
	 * @return ���� true ��ʾ isfinish=false ʱ�� �Ѿ�����ʧ����ʾ���� �����ڵ��� onDealError
	 */
	protected abstract boolean onMored(boolean isfinish, List<T> ltdata,boolean ended);

	/**
	 * ��������ִ�н��� ��ҳ�洦����
	 * @param task �����ʶ
	 * @param isfinish true �ɹ�ִ������ˢ�� false ʧ��
	 * @param ltdata ˢ�µ�����
	 * @return ���� true ��ʾ isfinish=false ʱ�� �Ѿ�����ʧ����ʾ���� �����ڵ��� onDealError
	 */
	protected boolean onWorked(int task, boolean isfinish, List<T> ltdata) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * ���ػ������ ��ҳ�洦����
	 * @param isfinish true �ɹ�ִ������ˢ�� false ʧ��
	 * @param ltdata ˢ�µ�����
	 * @return ���� true ��ʾ isfinish=false ʱ�� �Ѿ�����ʧ����ʾ���� �����ڵ��� onDealError
	 */
	protected boolean onLoaded(boolean isfinish, List<T> ltdata,Date cachetime) {
		// TODO Auto-generated method stub
		return onRefreshed(isfinish, ltdata);
	}
	
	@SuppressWarnings("rawtypes")
	public static AfListViewTask getTask(Message msg) {
		// TODO Auto-generated method stub
		if (msg.obj instanceof AfListViewTask) {
			return (AfListViewTask) msg.obj;
		}
		return null;
	}
}
