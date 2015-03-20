package com.ontheway.thread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.support.v4.app.NotificationCompat.Builder;

import com.ontheway.application.AfApplication;
import com.ontheway.application.AfExceptionHandler;
import com.ontheway.util.AfFileUtil;
import com.ontheway.util.AfIntent;
import com.ontheway.util.AfStringUtil;

/**
 * @Description: ��������
 * @Author: scwang
 * @Version: V1.0, 2015-3-13 ����5:27:48
 * @Modified: ���δ���AfDownloader��
 */
public class AfDownloader {
	/**
	 * �㲥����
	 */
	public static final String FILE_NOTIFICATION = "FILE_NOTIFICATION";

	/**
	 * @Description: �첽���� url �� path �ļ���Ϊ name
	 * @param url ��������
	 * @param path ����Ŀ¼
	 * @param name �����ļ���
	 */
	public static void download(String url,String path,String name) {
		// TODO Auto-generated method stub
		DownloadEntity entity = new DownloadEntity();
		entity.Name = name;
		entity.DownloadUrl = url;
		entity.DownloadPath = path;
		AfApplication.postTask(new DownloadTask(entity,null));
	}

	/**
	 * @Description: �첽���� url �� path
	 * @param url ��������
	 * @param path ����·��
	 */
	public static void download(String url,String path) {
		// TODO Auto-generated method stub
		DownloadEntity entity = new DownloadEntity();
		entity.DownloadUrl = url;
		entity.DownloadPath = path;
		AfApplication.postTask(new DownloadTask(entity,null));
	}

	/**
	 * @Description: �첽���� url �� path
	 * @param url ��������
	 * @param path ����·��
	 */
	public static void download(String url,String path,DownloadListener listener) {
		// TODO Auto-generated method stub
		DownloadEntity entity = new DownloadEntity();
		entity.DownloadUrl = url;
		entity.DownloadPath = path;
		AfApplication.postTask(new DownloadTask(entity,listener));
	}
	/**
	 * @Description: �첽����
	 * @param entity ��������ʵ��
	 */
	public static void download(DownloadEntity entity) {
		// TODO Auto-generated method stub
		AfApplication.postTask(new DownloadTask(entity,null));
	}

	/**
	 * @Description: �첽����
	 * @param entity ��������ʵ��
	 * @param listener ���ؽ��ȼ�����
	 */
	public static void download(DownloadEntity entity,DownloadListener listener) {
		// TODO Auto-generated method stub
		AfApplication.postTask(new DownloadTask(entity,listener));
	}
	/**
	 * @Description: �ж� url�Ƿ���������
	 * @param url
	 * @return true �������� false û��������
	 */
	public static boolean isDownloading(DownloadEntity entity) {
		// TODO Auto-generated method stub
		return isDownloading(entity.DownloadUrl);
	}
	/**
	 * @Description: �ж� url�Ƿ���������
	 * @param url
	 * @return true �������� false û��������
	 */
	public static boolean isDownloading(String url) {
		// TODO Auto-generated method stub
		for (DownloadTask task : DownloadTask.mltDownloading) {
			if (AfStringUtil.equals(task.mEndityUrl, url)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * @Description: �ж� url�Ƿ���������
	 * @param tag 
	 * @return true �������� false û��������
	 */
	public static boolean isDownloading(Object tag) {
		// TODO Auto-generated method stub
		for (DownloadTask task : DownloadTask.mltDownloading) {
			if (task.mEndity.tag == tag) {
				return true;
			}
		}
		return false;
	}
	/**
	 * @Description: �µİ󶨽��ȼ�����
	 * @param entity ��������ʵ��
	 * @param listener ���ؽ��ȼ�����
	 * @return �������µļ����� 
	 * ������� ����ļ����� �󶨳ɹ� ���� ��һ���������ܾ��µİ�
	 * ���null ƥ�䲻����������
	 */
	public static DownloadListener setListener(DownloadEntity entity, DownloadListener listener) {
		// TODO Auto-generated method stub
		return setListener(entity.DownloadUrl, listener);
	}
	/**
	 * @Description: �µİ󶨽��ȼ�����
	 * @param url ����·��
	 * @param listener ���ؽ��ȼ�����
	 * @return �������µļ����� 
	 * ������� ����ļ����� �󶨳ɹ� ���� ��һ���������ܾ��µİ�
	 * ���null ƥ�䲻����������
	 */
	public static DownloadListener setListener(String url, DownloadListener listener) {
		// TODO Auto-generated method stub
		for (DownloadTask task : DownloadTask.mltDownloading) {
			if (listener != null && AfStringUtil.equals(task.mEndityUrl, url)) {
				DownloadListener cListener = task.setDownloadListener(listener);
				if (cListener != listener && cListener instanceof DownloadManagerListener
						&& listener instanceof DownloadViewerListener) {
					DownloadViewerListener vListener = (DownloadViewerListener)listener;
					DownloadManagerListener mListener = (DownloadManagerListener)cListener;
					if (mListener.setDownloadListener(vListener)) {
						return vListener;
					}
				}
				return cListener;
			}
		}
		return null;
	}
	/**
	 * @Description: ����������
	 */
	public static class NotifyEntity{

		public String ContentTitle;
		public String FinishText;
		private String FailText;
		/**
		 * @return "��������XXXX"
		 */
		public String getContentTitle() {
			// TODO Auto-generated method stub
			if (ContentTitle == null) {
				return "�ļ�����";
			}
			return ContentTitle;
		}
		/**
		 * @return "�ļ�XXXXX�������,��СXXXX"
		 */
		public String getFinishText() {
			// TODO Auto-generated method stub
			if (FinishText == null) {
				return "�ļ��������";
			}
			return FinishText;
		}
		/**
		 * @return "�ļ�XXXXX����ʧ��"
		 */
		public String getFailText() {
			// TODO Auto-generated method stub
			if (FinishText == null) {
				return "�ļ�����ʧ��";
			}
			return FailText;
		}
		
	}
	
	public static class DownloadEntity{
		/**
		 * ���غ���ļ�����Ӱ�� DownloadPath������
		 */
		public String Name = "";
		public String Size = "";
		public String DownloadUrl = "";
		/**
		 * �� Name ��ֵʱ�� ��ʾĿ¼,����ȫ·�� 
		 */
		public String DownloadPath = "";
		/**
		 * ����������֪ͨ����
		 */
		public NotifyEntity Notify;
		/**
		 * �������
		 */
		public Object tag;
		
		private boolean isDownloaded = false;
		
		String Error = "";
		Throwable Excption = null;
		
		public boolean isDownloaded() {
			// TODO Auto-generated method stub
			return isDownloaded;
		}
		
		void setDownloaded() {
			// TODO Auto-generated method stub
			isDownloaded = true;
		}
		/**
		 * �ж��Ƿ�����ʧ��
		 */
		public boolean isDownloadFail(){
			return Excption != null;
		}
		/**
		 * ��ȡ������Ϣ
		 */
		public String getError() {
			return Error;
		}
		/**
		 * ��ȡ�쳣��Ϣ
		 */
		public Throwable getExcption() {
			return Excption;
		}
		/**
		 * ��ȡ����Ŀ¼
		 */
		public String getDir(){
			if (AfStringUtil.isNotEmpty(Name)) {
				return DownloadPath;
			}else {
				return new File(DownloadPath).getParent();
			}
		}
		/**
		 * ��ȡ����ȫ·��
		 */
		public String getFullPath(){
			if (AfStringUtil.isNotEmpty(Name)) {
				return DownloadPath +"/"+Name;
			}else {
				return DownloadPath;
			}
		}
	}
	
	public interface DownloadListener{
		/**
		 * @Description ���ؿ�ʼ
		 */
		void onDownloadStart();
		/**
		 * @Description �ӿ�����
		 * @return false �ܾ����� true ͬ������
		 */
		boolean onBreakAway();
		/**
		 * @Description: ���ؽ���
		 * @param rate �ٷֱ�
		 * @param loaded �����س���
		 * @param total �ļ��ܴ�С
		 */
		void onDownloadProgress(float rate, long loaded, long total);
		/**
		 * @Description: ֪ͨ������¼�
		 * @param endity ����ʵ������
		 */
		void notifyClick(DownloadEntity endity);
		/**
		 * @Description: �������
		 * @return true �Ѿ����� false û�д���Ӱ�쵽notifyClick��
		 */
		boolean onDownloadFinish();
		/**
		 * @Description: ����ʧ��
		 * @param error ������Ϣ
		 * @param e �����쳣
		 * @return true �Ѿ����� false û�д���Ӱ�쵽notifyClick��
		 */
		boolean onDownloadFail(String error, Throwable e);
	}
	/**
	 * @Description: ���ü����ӿ�
	 */
	public static abstract class DownloadViewerListener implements DownloadListener{

		@Override
		public void onDownloadStart() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onBreakAway() {
			// TODO Auto-generated method stub
			return true;//ͬ������
		}

		@Override
		public void notifyClick(DownloadEntity endity) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onDownloadFinish() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onDownloadFail(String error, Throwable e) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	/**
	 * ���ع�������ӿ�
	 */
	public static abstract class DownloadManagerListener implements DownloadListener{

		protected DownloadViewerListener listener;

		public DownloadManagerListener() {
			// TODO Auto-generated constructor stub
		}
		
		public DownloadManagerListener(DownloadViewerListener listener) {
			super();
			this.listener = listener;
		}

		@Override
		public void onDownloadStart() {
			// TODO Auto-generated method stub
			if (listener != null) {
				listener.onDownloadStart();
			}
		}

		@Override
		public boolean onBreakAway() {
			// TODO Auto-generated method stub
			return false;//ͬ������
		}

		@Override
		public void onDownloadProgress(float rate, long loaded, long total) {
			// TODO Auto-generated method stub
			if (listener != null) {
				listener.onDownloadProgress(rate, loaded, total);
			}
		}

		@Override
		public boolean onDownloadFinish() {
			// TODO Auto-generated method stub
			if (listener != null) {
				return listener.onDownloadFinish();
			}
			return false;
		}

		@Override
		public boolean onDownloadFail(String error, Throwable e) {
			// TODO Auto-generated method stub
			if (listener != null) {
				return listener.onDownloadFail(error,e);
			}
			return false;
		}
		/**
		 * ���µļ�����
		 * @return true �󶨳ɹ� false ��һ�������� �ܾ�
		 */
		public boolean setDownloadListener(DownloadViewerListener listener) {
			// TODO Auto-generated method stub
			if (this.listener == null || this.listener.onBreakAway()) {
				this.listener = listener;
				return true;
			}
			return false;
		}
	}
	
	protected static class Notifier{
		
		private static Random rand = new Random();

		private Builder mBuilder;
		private NotifyEntity mEotify;
		private NotificationManager mManager;
		private int notifyid = 1000 + rand.nextInt(1000);
		
		public Notifier(Context context, DownloadEntity entity) {
			// TODO Auto-generated constructor stub
			mEotify = entity.Notify;
			mBuilder = new Builder(context);
			mBuilder.setSmallIcon(android.R.drawable.stat_sys_download);

//			if (Build.VERSION.SDK_INT < 11) {
				int flag = PendingIntent.FLAG_CANCEL_CURRENT;
				AfIntent intent = new AfIntent(FILE_NOTIFICATION);
				intent.put(FILE_NOTIFICATION, entity.DownloadUrl);
				PendingIntent pintent = PendingIntent.getBroadcast(context, 0, intent, flag);
				mBuilder.setContentIntent(pintent);
//			}

			// ���� Manager
			String server = Context.NOTIFICATION_SERVICE;
			mManager = (NotificationManager) context.getSystemService(server);
		}

		public void notifyProgress(int max, int precent, boolean indeterminate) {
			// TODO Auto-generated method stub
			mBuilder.setProgress(max, precent, false);// ����Ϊfalse����ʾ�̶�
			mBuilder.setContentText("������ "+precent+"% ");
			mManager.notify(notifyid, mBuilder.build());
		}

		public void notifyStart() {
			// TODO Auto-generated method stub
			mBuilder.setContentTitle(mEotify.getContentTitle());
			mBuilder.setTicker(mEotify.getContentTitle());
//			mBuilder.setTicker("��������...");
			mBuilder.setAutoCancel(false);
			mBuilder.setOngoing(true);
			mManager.notify(notifyid, mBuilder.build());
		}

		public void notifyFinish() {
			// TODO Auto-generated method stub
			mBuilder.setProgress(100, 100, false);// ����Ϊfalse����ʾ�̶�
			mBuilder.setSmallIcon(android.R.drawable.stat_sys_download_done);
			mBuilder.setTicker(mEotify.getFinishText());
			mBuilder.setContentText(mEotify.getFinishText());
//			mBuilder.setContentText("�ļ�������ɣ���С"+mBack.Size);
//			mBuilder.setTicker("����������ɣ��������");
			mBuilder.setAutoCancel(true);
			mBuilder.setOngoing(false);
			
			mManager.notify(notifyid, mBuilder.build());
		}

		public void notifyFail() {
			// TODO Auto-generated method stub
			mBuilder.setSmallIcon(android.R.drawable.stat_sys_download_done);
			mBuilder.setTicker(mEotify.getFailText());
			mBuilder.setContentText(mEotify.getFailText());
			mBuilder.setAutoCancel(true);
			mBuilder.setOngoing(false);
			mManager.notify(notifyid, mBuilder.build());
		}

		public void cancel() {
			// TODO Auto-generated method stub
			mManager.cancel(notifyid);
		}
		
	}
	
	public static class DownloadTask extends AfHandlerTask{

		public static final int DOWNLOAD_FINISH = 20;
		public static final int DOWNLOAD_PROGRESS = 10;
		
		private DownloadEntity mEndity;
		private Notifier mNotifier;
		private String mEndityUrl;
		private String mDownloadPath;
		private DownloadListener mListener;
		private File mTempFile;
		private int mPrecent = 0;
		
		private long mCount = 0;
		private long mTotal = 0;
		/**
		 * �������������б�
		 */
		private static List<DownloadTask> mltDownloading = new ArrayList<DownloadTask>();
		/**
		 * �ɹ�����ʧ�� ֪ͨ�������б�
		 */
		private static Map<String,DownloadTask> mNotifyMap = new HashMap<String, DownloadTask>();

		public DownloadTask(DownloadEntity endity,DownloadListener listener) {
			// TODO Auto-generated constructor stub
			mEndity = endity;
			mListener = listener;
			mEndityUrl = endity.DownloadUrl;
			mDownloadPath = endity.getFullPath();
		}
		/**
		 * @Description: ���°󶨼�����
		 * @param listener �µļ�����
		 * @return �������µļ����� 
		 * ������� ����ļ����� �󶨳ɹ� ���� ��һ���������ܾ��µİ�
		 */
		public DownloadListener setDownloadListener(DownloadListener listener) {
			// TODO Auto-generated method stub
			if (mListener == null || mListener.onBreakAway()) {
				mListener = listener;
				if (mListener != null) {
					mListener.onDownloadStart();
				}
			}
			return mListener;
		}

		@Override
		public boolean onPrepare() {
			// TODO Auto-generated method stub
			mltDownloading.add(this);
			// ���� ֪ͨ
			if (mEndity.Notify != null) {
				mNotifier = new Notifier(AfApplication.getApp(),mEndity);
				mNotifier.notifyStart();
			}
			if (mListener != null) {
				mListener.onDownloadStart();
			}
			return super.onPrepare();
		}
		
		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			if (!mEndity.isDownloaded()) {
				
				HttpGet get = new HttpGet(mEndityUrl);
				HttpResponse response = new DefaultHttpClient().execute(get);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();

				//�����ļ�����ʼ����
				mTempFile = new File(mDownloadPath);
				if(mTempFile.exists()){
					mTempFile.delete();
				}else{
					String spath = mTempFile.getParent();
					if(spath != null){
						File path = new File(spath);
						if (!path.exists()) {
							path.mkdir();
						}
					}
				}
				mTempFile.createNewFile();
				
				// ����һ���µ�д����������ȡ����ͼ������д�뵽�ļ���
				FileOutputStream fos = new FileOutputStream(mTempFile);
				// �Ѷ�������Ϊ��������һ�����л���������
				BufferedInputStream bis = new BufferedInputStream(is);
				// ��д������Ϊ��������һ�����л����д����
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				int read = 0, precent = 0;
				byte[] buffer = new byte[1024];
				long now, last = System.currentTimeMillis();
				long count = 0, length = entity.getContentLength();
				mEndity.Size = AfFileUtil.getFileSize(length);
				mCount = count;
				mTotal = length;
				mHandler.sendMessage(mHandler.obtainMessage(DOWNLOAD_PROGRESS, this));
				while ((read = bis.read(buffer)) != -1 && !mIsCanceled) {
					count += read;
					bos.write(buffer, 0, read);
					precent = (int) (((double) count / length) * 100);
					// ÿ�������3%��֪ͨ�����������޸����ؽ���
					now = System.currentTimeMillis();
					if (precent - mPrecent >= 3 || now - last > 500) {
						last = now;
						mPrecent = precent;
						mCount = count;
						mTotal = length;
						mHandler.sendMessage(mHandler.obtainMessage(DOWNLOAD_PROGRESS, this));
					}
				}
				bos.flush();
				fos.flush();
				bos.close();
				fos.close();
				bis.close();
				is.close();
				
				if (mIsCanceled) {
					mTempFile.delete();
				}else{
					mHandler.sendMessage(mHandler.obtainMessage(DOWNLOAD_FINISH, this));
				}
				mIsCanceled = true;
			}else {
				mHandler.sendMessage(mHandler.obtainMessage(DOWNLOAD_FINISH, this));
			}
		}

		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == AfTask.RESULT_FINISH) {
				if (msg.what == DOWNLOAD_PROGRESS) {
					// ����״̬���ϵ����ؽ�����Ϣ
					if (mNotifier != null) {
						mNotifier.notifyProgress(100, mPrecent, false);
					}
					if (mListener != null) {
						mListener.onDownloadProgress(0.01f*mPrecent, mCount, mTotal);
					}
				} else if (msg.what == DOWNLOAD_FINISH) {
					mEndity.setDownloaded();
					mltDownloading.remove(this);
					boolean neednotify = true;
					if (mListener != null && mListener.onDownloadFinish()) {
						neednotify = false;
					}
					if (mNotifier != null) {
						if (neednotify) {
							mNotifier.notifyFinish();
							mNotifyMap.put(mEndityUrl, this);
						}else {
							mNotifier.cancel();
						}
					}
				}
			} else if (mResult == AfTask.RESULT_FAIL) {
				mltDownloading.remove(this);
				boolean neednotify = true;
				if (mListener != null && mListener.onDownloadFail(mErrors, mException)) {
					neednotify = false;
				}
				if (mNotifier != null) {
					if (neednotify) {
						mEndity.Error = mErrors;
						mEndity.Excption = mException;
						mNotifier.notifyFail();
						mNotifyMap.put(mEndityUrl, this);
					}else {
						mNotifier.cancel();
					}
				}
			}
			return true;
		}

		public void notifyClick() {
			// TODO Auto-generated method stub
			if (mListener != null) {
				mListener.notifyClick(mEndity);
			}
		}

	}

	protected static class DownloadBroadcast extends BroadcastReceiver {
		
		// �÷�������ʵ�ֽ��յ��㲥�ľ��崦�����в���intent��Ϊ���ܵ���intent
		@Override
		public void onReceive(Context context, Intent intent) {
			// ��ȡ��ͼ�Ķ���
			try {
				if (FILE_NOTIFICATION.equals(intent.getAction())) {
					String Url = new AfIntent(intent).getString(FILE_NOTIFICATION, null);
					DownloadTask task = DownloadTask.mNotifyMap.get(Url);
					if (task != null) {
						task.notifyClick();
						DownloadTask.mNotifyMap.remove(Url);
					}
//					for (DownloadTask task : DownloadTask.mltDownloading) {
//						if (AfStringUtil.equals(task.mEndityUrl, Url)) {
//							task.notifyClick();
//							break;
//						}
//					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				AfExceptionHandler.handler(e, "DownloadBroadcast.onReceive");
			}
		}
	}
	
	static{
		try {
			IntentFilter filter = new IntentFilter();
			filter.addAction(FILE_NOTIFICATION);
			DownloadBroadcast receiver = new DownloadBroadcast();
			AfApplication.getApp().registerReceiver(receiver, filter);
		} catch (Throwable e) {
			// TODO: handle exception
			AfExceptionHandler.handleAttach(e, "DownloadBroadcast.registerReceiver error");
		}
	}
}
