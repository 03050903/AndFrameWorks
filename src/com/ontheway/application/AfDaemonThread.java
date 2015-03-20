package com.ontheway.application;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

import com.ontheway.thread.AfDispatch;
import com.ontheway.thread.AfTask;
import com.ontheway.util.AfDateGuid;

/**
 * DaemonThread ��̨�̹߳���
 * 
 * @author SCWANG
 * 
 */
public class AfDaemonThread {
	// �������е�����߳�����
	private static final int MAXTHREADNUM = 5;
	// ����ȴ��������
	private static Queue<AfTask> mqeTask = new LinkedList<AfTask>();
	// �����߳��б�����
	private static List<ThreadWorker> mltWorker = new ArrayList<ThreadWorker>();

	/**
	 * @Description:
	 * ��ʱ����̨����һ�� Task ���񣨻���UI�߳���dispatch������ʼ��
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-26 ����2:34:06
	 * @Modified: ���δ���postTaskDelayed����
	 * @param task ����
	 * @return ���ش����  task �������
	 */
	public static AfTask postTask(AfTask task) {
		return postTaskDelayed(task, 0);
	}
	
	/**
	 * @Description:
	 * ��ʱ����̨����һ�� Task ����
	 * �������UI�߳���dispatch������UI�߳�������ʱ��
	 * ��Ӱ�쵽 AfTask.onPrepare ���̵߳���
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-26 ����2:34:06
	 * @Modified: ���δ���postTaskDelayed����
	 * @param task ����
	 * @return ���ش����  task �������
	 */
	public static AfTask postTaskNoDispatch(AfTask task) {
		return postTaskDelayedNoDispatch(task, 0);
	}

	/**
	 * @Description:
	 * ��ʱ����̨����һ�� Task ����
	 * ����UI�߳���dispatch������ʼ
	 * AfTask.onPrepare ����UI�߳��е���
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-26 ����2:34:06
	 * @Modified: ���δ���postTaskDelayed����
	 * @param task ����
	 * @param delay ��ʱִ��
	 * @return ���ش����  task �������
	 */
	public static AfTask postTaskDelayed(final AfTask task,final long delay) {
		AfApplication.dispatch(new AfDispatch() {
			@Override
			protected void onDispatch() {
				// TODO Auto-generated method stub
				synchronized (mltWorker) {
					if (mltWorker.size() < MAXTHREADNUM) {
						if (task.onPrepare()) {
							ThreadWorker tWorker = new ThreadWorker(AfDateGuid.NewID(),task,delay);
							mltWorker.add(tWorker);
							tWorker.start();
						}
						return;
					}
				}
				synchronized (mqeTask) {
					mqeTask.offer(task);
				}
			}
		});
		return task;
	}
	
	/**
	 * @Description:
	 * ��ʱ����̨����һ�� Task ����
	 * �������UI�߳���dispatch������UI�߳�������ʱ��
	 * ��Ӱ�쵽 AfTask.onPrepare ���̵߳���
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-26 ����2:34:06
	 * @Modified: ���δ���postTaskDelayed����
	 * @param task ����
	 * @param delay ��ʱִ��
	 * @return ���ش����  task �������
	 */
	public static AfTask postTaskDelayedNoDispatch(final AfTask task,final long delay) {
		// TODO Auto-generated method stub
		synchronized (mltWorker) {
			if (mltWorker.size() < MAXTHREADNUM) {
				if (task.onPrepare()) {
					ThreadWorker tWorker = new ThreadWorker(AfDateGuid.NewID(),task,delay);
					mltWorker.add(tWorker);
					tWorker.start();
				}
				return task;
			}
		}
		synchronized (mqeTask) {
			mqeTask.offer(task);
		}
		return task;
	}

	// һ���߳�����ִ�����
	private static void onTaskFinish(ThreadWorker worker) {
		synchronized (mltWorker) {
			mltWorker.remove(worker);
		}
		synchronized (mqeTask) {
			if (mqeTask.size() > 0) {
				postTask(mqeTask.poll());
			}
		}
	}
	
	private static class ThreadWorker extends Thread {
		private long mDelay = 0;
		private Runnable mTask = null;

		public ThreadWorker(String name, Runnable task, long delay) {
			super(name);
			// TODO Auto-generated constructor stub
			mTask = task;
			mDelay = delay;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mDelay > 0) {
				try {
					Thread.sleep(mDelay);
				} catch (Throwable e) {
					// TODO: handle exception
				}
			}
			try {
				mTask.run();
			} catch (Throwable e) {
				// TODO: handle exception
				AfExceptionHandler.handler(e, "AfDaemonThread.ThreadWorker.run �����쳣");
			}
			onTaskFinish(this);
		}

	}
}
