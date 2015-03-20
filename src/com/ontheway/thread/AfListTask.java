package com.ontheway.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.ontheway.adapter.AfListAdapter;
import com.ontheway.application.AfApplication;
import com.ontheway.bean.Page;
import com.ontheway.caches.AfPrivateCaches;
import com.ontheway.constant.AfNetworkEnum;
import com.ontheway.exception.AfToastException;
import com.ontheway.util.AfTimeSpan;

public abstract class AfListTask<T> extends AfHandlerTask {
	// ���ص�ҳ����
	public static int PAGE_SIZE = 15;
	//������Чʱ�䣨5���ӣ�
	public static AfTimeSpan CACHETIMEOUTSECOND = AfTimeSpan.FromMinutes(5);
	// ö����������
	public static final int TASK_REFRESH = 100; // ����ˢ��
	public static final int TASK_MORE = 101; // �������
	public static final int TASK_SORT = 102; // ����
	public static final int TASK_CACHES = 103; // �������ݿ⻺��
	public static final int TASK_CACHESADD = 104; // �������ݿ⻺��׷��

	public int mFirstResult = 0;
	public int mPageSize = AfListTask.PAGE_SIZE;
	public List<T> mltData = new ArrayList<T>();

	/**
	 * ����ʹ�õ� class ����jsonҪ�õ���
	 * ���� ��������Ϊ TASK_LOAD AfListTask ���Զ�ʹ�û��湦��
	 */
	public Class<T> mCacheClazz = null;
	/** 
	 *  ����ʹ�õ� KEY_CACHELIST = this.getClass().getName()
	 * 		KEY_CACHELIST Ϊ����ı�ʶ
	 */
	public String KEY_CACHELIST = this.getClass().getName();
	public String KEY_CACHETIME = "KEY_CACHETIME";
	//������Чʱ��
	public AfTimeSpan mCacheSpan = AfListTask.CACHETIMEOUTSECOND;
	/**
	 * ����������캯�����Դ��� TASK_LOAD ����
	 * �����ϴλ������ݣ�������ڽ����� TASK_REFRESH ����������
	 * ����ʹ�õ� KEY_CACHELIST = this.getClass().getName()
	 * 		KEY_CACHELIST Ϊ����ı�ʶ
	 * ����Ĺ��� ���Ϊ AfListTask.CACHETIMEOUTSECOND
	 * 		���ʱ����������App��ʼ��ʱ�����
	 * @param clazz ����Model������� ��jsonҪ�õ���
	 */
	public AfListTask(Class<T> clazz){
		super(TASK_LOAD);
		this.mCacheClazz = clazz;
	}
	/**
	 * ����������캯�����Դ��� TASK_LOAD ����
	 * �����ϴλ������ݣ�������ڽ����� TASK_REFRESH ����������
	 * ����ʹ�õ� KEY_CACHELIST �����Զ���
	 * ����Ĺ��� ���Ϊ AfListTask.CACHETIMEOUTSECOND
	 * 		���ʱ����������App��ʼ��ʱ�����
	 * @param clazz ����Model������� ��jsonҪ�õ���
	 * @param KEY_CACHELIST �����KEY��ʶ
	 */
	public AfListTask(Class<T> clazz,String KEY_CACHELIST){
		super(TASK_LOAD);
		this.mCacheClazz = clazz;
		this.KEY_CACHELIST = KEY_CACHELIST;
	}
	
	public AfListTask(int task) {
		super(task);
		// TODO Auto-generated constructor stub
	}
	
	public AfListTask(Handler handler, int task) {
		super(handler, task);
		// TODO Auto-generated constructor stub
	}

	public AfListTask(int task,int first) {
		super(task);
		// TODO Auto-generated constructor stub
		mFirstResult = first;
	}
	
	public AfListTask(List<T> list) {
		super(list!=null?TASK_MORE:TASK_LOAD);
		// TODO Auto-generated constructor stub
		if (list!= null && list.size() > 0) {
			mFirstResult = list.size();
		}
	}
	//���ظ���ר��
	public AfListTask(AfListAdapter<T> adapter) {
		super(adapter!=null?TASK_MORE:TASK_LOAD);
		// TODO Auto-generated constructor stub
		if (adapter!= null && adapter.getCount() > 0) {
			mFirstResult = adapter.getCount();
		}
	}
	/**
	 * ��ȡ����ʱ�� ���û�л��� ���� null
	 * @return
	 */
	public Date getCacheTime(){
		return AfPrivateCaches.getInstance(KEY_CACHELIST).getDate(KEY_CACHETIME, new Date(0));
	}

	/**
	 * ��ȡ�����Ƿ���� 
	 * mCacheTimeOutSecond ����������Чʱ��
	 * @return
	 */
	protected boolean isCacheTimeout() {
		// TODO Auto-generated method stub
		Date date = AfPrivateCaches.getInstance(KEY_CACHELIST).getDate(KEY_CACHETIME, new Date(0));
		return AfTimeSpan.FromDate(date, new Date()).GreaterThan(mCacheSpan);
	}
	
	/**
	 * ���ػ���
	 * @param ischecktimeout �Ƿ��⻺����ڣ�ˢ��ʧ��ʱ����Լ��ػ��棩
	 * @return
	 */
	protected List<T> onLoad(){
		if (mCacheClazz != null) {
			return AfPrivateCaches.getInstance(KEY_CACHELIST).getList(KEY_CACHELIST, mCacheClazz);
		}
		return mltData;
	}

	protected abstract List<T> onRefresh(Page page) throws Exception;

	protected abstract List<T> onMore(Page page) throws Exception;
	
	protected boolean onWorking(int task) throws Exception {
		return false;
	}

	@Override
	protected final void onWorking(Message tMessage) throws Exception {
		// TODO Auto-generated method stub
		AfPrivateCaches cache = AfPrivateCaches.getInstance(KEY_CACHELIST);
		switch (mTask) {
		case AfListTask.TASK_LOAD:
			/**
			 * Ϊ�˰�ȫ���ǣ�ϵͳ��ܹ涨 onLoad �����׳��쳣
			 * 	onLoad ��Ҫ���� �������ݿ���ػ��棬���㱾��û������
			 * 	Ҳ�ɷ��ؿ�List 
			 * 		�Է���һʹ��try catch ��ֹ�쳣
			 */
			if (isCacheTimeout() || (mltData = onLoad()) == null || mltData.size() == 0) {
				try {
					mTask = TASK_REFRESH;
					mltData = onRefresh(new Page(mPageSize,0));
					if (mCacheClazz != null) {
						cache.putList(KEY_CACHELIST, mltData, mCacheClazz);
						cache.put(KEY_CACHETIME, new Date());
					}
				} catch (Exception e) {
					// TODO: handle exception
					mTask = TASK_LOAD;
					e.printStackTrace();
					mltData = onLoad();
					//������Խ׶� �׳��쳣
					if (AfApplication.getApp().isDebug()) {
						if (mltData==null||mltData.size()==0) {
							mTask = TASK_REFRESH;
							throw e;
						}
					}
				}
			}
			break;
		case AfListTask.TASK_REFRESH:
			mltData = onRefresh(new Page(mPageSize,0));
			if (mCacheClazz != null) {
				cache.putList(KEY_CACHELIST, mltData, mCacheClazz);
				cache.put(KEY_CACHETIME, new Date());
			}
			break;
		case AfListTask.TASK_MORE:
			mltData = onMore(new Page(mPageSize,mFirstResult));
			//cache.pushList(KEY_CACHELIST, mltData, mClazz);
			break;
		default :
			this.onWorking(mTask);
			return;
		}
//		try {
//			Collections.sort(mltData);
//		} catch (Throwable e) {
//			// TODO: handle exception
//			e.printStackTrace();//handled
//			String remark = "AfListViewTask.onWorking.sort �׳��쳣\r\n";
//			remark += "task = " + mTask;
//			remark += "class = " + getClass().toString();
//			AfExceptionHandler.handler(e, remark);
//		}
	}

	@Override
	protected boolean onHandle(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void onException(Throwable e) {
		// TODO Auto-generated method stub
		if (AfApplication.getNetworkStatus() == AfNetworkEnum.TYPE_NONE
				&& mTask != AfTask.TASK_LOAD) {
			mErrors = "��ǰ���粻���ã�";
			mException = new AfToastException(mErrors);
		}
	}

	@SuppressWarnings("rawtypes")
	public static AfListTask getTask(Message msg) {
		// TODO Auto-generated method stub
		if (msg.obj instanceof AfListTask) {
			return (AfListTask) msg.obj;
		}
		return null;
	}
}
