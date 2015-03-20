package com.ontheway.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import android.app.Notification;
import android.os.Message;

import com.ontheway.thread.AfHandlerTask;

public abstract class AfServiceNotify<T> {
	
	public static interface INotify<T>{
		/**
		 * ֪ͨ�¼�
		 * @param list ֪ͨ�����б�
		 * @return �����Ƿ�����������Ļ��򲻽���ϵͳ֪ͨ��
		 */
		boolean onNotify(Collection<T> list);
	}
	
	protected static class NotifyInstance extends 
		HashMap<Class<?>, AfServiceNotify<?>>{
		private static final long serialVersionUID = 1L;
	}
	
	protected static NotifyInstance mInstance = new NotifyInstance();
	
	protected static Random rand = new Random(); 
	
	protected UUID mServiceID = null;
	protected List<T> mltNotify = new ArrayList<T>();
	protected int notifyid = 1000+rand.nextInt(1000);
	protected List<INotify<T>> mltListener = new ArrayList<INotify<T>>();
	protected boolean mSingleNotify = false;
	

	protected AfServiceNotify(){
		
	}
	
	protected static <T extends AfServiceNotify<?>> T getInstance(Class<T> clazz){
		AfServiceNotify<?> instance = mInstance.get(clazz);
		if(instance == null){
			try {
				instance = (AfServiceNotify<?>) clazz.newInstance();
				mInstance.put(clazz, instance);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
		return clazz.cast(instance);
	}
	/**
	 * ��������
	 * @param id
	 *            ��ǰ�� ����ID �� ��¼�û� ID
	 */
	public void start(UUID id){
		if(!id.equals(mServiceID)){
			mServiceID = id;
			AfApplication.postTask(new ServiceTask(id));
		}
	}
	
	public void stop(){
		mServiceID = null;
		mltNotify.clear();
		mltListener.clear();
	}

	public boolean isStarted(){
		return mServiceID != null;
	}
	
	public void read(T model) {
		// TODO Auto-generated method stub
		if(isStarted()){
			for (int i = 0; i < mltNotify.size(); i++) {
				T announce = mltNotify.get(i);
				if(announce.equals(model)){
					mltNotify.remove(i);
					notify(mltNotify,false);
					AfNotifyCenter.cancel(notifyid);
					return;
				}
			}
		}
	}

	public List<T> getNotify() {
		// TODO Auto-generated method stub
		return new ArrayList<T>(mltNotify);
	}

	public void addListener(INotify<T> listener){
		if(isStarted()){
			for (INotify<T> tlistener : mltListener) {
				if(tlistener == listener){
					return;
				}
			}
			mltListener.add(listener);
		}
	}

	public void removeListener(INotify<T> listener){
		if(mltListener.size() > 0){
			for (INotify<T> tlistener : mltListener) {
				if(tlistener == listener){
					mltListener.remove(listener);
					break;
				}
			}
		}
	}
	
	/**
	 * ������ӿ�֪ͨ ���ݸı�
	 * @param list
	 */
	public void notify(List<T> list,boolean notify){
		boolean handled = false;
		for (INotify<T> listener : mltListener) {
			try {
				handled = listener.onNotify(list) || handled;
			} catch (Throwable e) {
				// TODO: handle exception
				AfExceptionHandler.handler(e, "AfServiceNotify.notify - Exception");
			}
		}
		if(!handled && notify){
			if (mSingleNotify) {
				for (T t : list) {
					AfNotifyCenter.notify(getNotification(t), 1000+rand.nextInt(1000));
				}
			}else {
				AfNotifyCenter.notify(getNotification(list), notifyid);
			}
		}
	}
	
	private class ServiceTask extends AfHandlerTask{

		private UUID tID = null;
		private List<T> tltNotify = null;
		
		public ServiceTask(UUID user) {
			// TODO Auto-generated constructor stub
			tID = user;
		}
		
		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if(isStarted()&&mServiceID.equals(tID)){
				if(RESULT_FINISH == mResult){
					mltNotify.clear();
					for (T item : tltNotify) {
						if(!isReaded(item)){
							mltNotify.add(item);
						}
					}
					if(mltNotify.size() > 0){
						AfServiceNotify.this.notify(mltNotify,true);
					}
				}
			}
			return true;
		}

		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			tltNotify = onLoadNotify();
		}
	}

	protected abstract List<T> onLoadNotify() throws Exception;
//	protected List<T> onLoadNotify() throws Exception {
//		// TODO Auto-generated method stub
//		return new ArrayList<T>();
//	}

	protected abstract boolean isReaded(T item);
//	protected boolean isReaded(T item) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
//	public void onReceiveNewNotify(T item) {
//		if(mServiceID != null){
//			mltNotify.add(item);
//			AfNotifyCenter.notify(getNotification(item), notifyid);
//		}
//	}
//
//	protected Notification getNotification(T item) {
//		// TODO Auto-generated method stub
//		return AfNotifyCenter.getNotification("�����µ���Ϣ", null);
//	}

	public void onReceiveFromService(List<T> list) {
		if(mServiceID != null){
			mltNotify.addAll(list);
			notify(mltNotify, true);
		}
	}

	protected Notification getNotification(List<T> list) {
		// TODO Auto-generated method stub
		return AfNotifyCenter.getNotification("�����µ���Ϣ", null);
	}

	protected Notification getNotification(T t) {
		// TODO Auto-generated method stub
		return AfNotifyCenter.getNotification("�����µ���Ϣ", null);
	}


}
