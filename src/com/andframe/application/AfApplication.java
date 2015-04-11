package com.andframe.application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Environment;
import android.os.Looper;

import com.andframe.BuildConfig;
import com.andframe.activity.AfActivity;
import com.andframe.activity.AfMainActivity;
import com.andframe.broadcast.ConnectionChangeReceiver;
import com.andframe.caches.AfImageCaches;
import com.andframe.caches.AfSharedPreference;
import com.andframe.fragment.AfFragment;
import com.andframe.helper.android.AfDeviceInfo;
import com.andframe.network.AfFileService;
import com.andframe.network.AfImageService;
import com.andframe.network.AfSoapService;
import com.andframe.network.AfSoapServiceOld;
import com.andframe.thread.AfDispatch;
import com.andframe.thread.AfTask;
import com.andframe.thread.AfThreadWorker;
import com.andframe.util.DatabaseUtil;
import com.andframe.util.android.AfNetwork;
import com.andframe.util.java.AfMD5;
import com.andframe.util.java.AfVersion;

/**
 * AfApplication ������ ���̳�ʹ�ñ���̳в�ʹ�ã�������ܹ��������� AfApplication��
 * 
 * @author SCWANG ���� ȫ�����ݵĴ洢 �� �¼�֪ͨ
 * 
 *         ����ʵ�� ָ�������� ����ҳ�� ���� getForegroundClass
 * 
 *         �ṩ��̬ȫ�ֽӿ� public static getApp() ��ȡȫ��App public static getAppContext()
 *         ��ȡApp public static getLooper() ��ȡȫ����Ϣѭ���������ڹ���UI Handler�� public
 *         static postTask(AfTask task) ����ȫ������AfTask�� public static
 *         postHandle(AfUIHandle handle) ����UIHandle����AfUIHandle�� public static
 *         getNetworkStatus() ��ȡ����״̬ ֵ������AfNetworkEnum�� public static
 *         getDebugMode() ��ȡ��ǰ����ģʽ public static setDebugMode(int mode) ���õ���ģʽ
 *         public static getVersion() ��ȡ��ǰApp�汾 public static getVersionCode()
 *         ��ȡ��ǰApp�汾Code
 * 
 *         �Ǿ�̬�ӿ� public exitForeground(Object power) �˳�ǰ̨ public
 *         startForeground(Activity activity) ����ǰ̨ public getCachesPath(String
 *         type) ��ȡ����������·�� public getWorkspacePath(String type) ��ȡ����������·��
 * 
 *         �̳�֮�������Ҫ�Ĺ��� ��д ��Ӧ�ĺ��� public getExceptionHandler() //ȫ���쳣���� public
 *         getAppSetting() //ȫ������ public getImageService() //ͼƬ���� public
 *         getFileService() //�ļ����� public getUpdateService() //���·��� public
 *         getLogoId() //App Logo�����µȹ��ܻ��õ��� public getAppName() //App ����
 *         
 * 			public boolean isBackground() //�ж��Ƿ��ں�̨������HOME֮��
 * 			public Random getRandom()//��ȡͳһ�����
 */
public abstract class AfApplication extends Application {

	/**
	 * interface INotifyNeedUpdate
	 * 
	 * @author SCWANG ��Ҫ����֪ͨ�ӿ�
	 */
	public interface INotifyNeedUpdate {
		void onNotifyNeedUpdate(String curver, String server);
	}

	/**
	 * interface INotifyUpdate
	 * 
	 * @author SCWANG ��Ҫ����֪ͨ�ӿ�
	 */
	public interface INotifyUpdate {
		void onNotifyUpdate(String curver, String server, String describe);
	}

	/**
	 * interface INotifyNetworkStatus
	 * 
	 * @author SCWANG ����״̬�ı�֪ͨ�ӿ�
	 */
	public static interface INotifyNetworkStatus {
		void onNetworkStatusChanged(int networkStatus);
	}

	public static final int DEBUG_NONE = 0;
	public static final int DEBUG_TESTDATA = 1;
	public static final int DEBUG_TEST = 2;
	public static final String STATE_RUNNING = "APP_CACHE_RUNNING";

	protected static final String STATE_TIME = "STATE_TIME";
	protected static final String STATE_NETWORKSTATUS = "STATE_NETWORKSTATUS";
	protected static final String STATE_FIXEDPOSITION = "STATE_FIXEDPOSITION";
	protected static final String STATE_DEBUGMODE = "STATE_DEBUGMODE";
	protected static final String STATE_VERSION = "STATE_VERSION";
	protected static final String STATE_SERVERVERSION = "STATE_SERVERVERSION";
	protected static final String STATE_ISINITIALIZED = "STATE_ISINITIALIZED";
	protected static final String STATE_ISFORERUNNING = "STATE_ISFORERUNNING";

	public static AfApplication mApp = null;

	public static synchronized AfApplication getApp() {
		return mApp;
	}

	public static synchronized Context getAppContext() {
		return mApp.getApplicationContext();
	}

	public static synchronized Looper getLooper() {
		return mApp.mLooper;
	}

	public static synchronized AfTask postTask(AfTask task) {
		if (mApp.mWorker != null) {
			return mApp.mWorker.postTask(task);
		}
		return AfDaemonThread.postTask(task);
	}
	
	public static synchronized AfTask postTaskDelayed(AfTask task, int delay) {
		if (mApp.mWorker != null) {
			return mApp.mWorker.postTaskDelayed(task, delay);
		}
		return AfDaemonThread.postTaskDelayed(task,delay);
	}

	public static synchronized AfDispatch dispatch(AfDispatch handle) {
		handle.dispatch(mApp.mLooper);
		return handle;
	}

	public static synchronized int getNetworkStatus() {
		return AfNetwork.getNetworkState(mApp);
		// return mApp.mNetworkStatus;
	}

	public static synchronized int getDebugMode() {
		// TODO Auto-generated method stub
		return mApp.mDebugMode;
	}

	public static synchronized void setDebugMode(int mode) {
		// TODO Auto-generated method stub
		mApp.mDebugMode = mode;
	}

	/**
	 * ��ȡ�ڲ��汾
	 * 
	 * @return
	 */
	public static synchronized String getVersion() {
		// TODO Auto-generated method stub
		return mApp.mVersion;
	}

	/**
	 * ��ȡ�ڲ��汾����
	 * 
	 * @return
	 */
	public static synchronized int getVersionCode() {
		// TODO Auto-generated method stub
		return AfVersion.transformVersion(mApp.mVersion);
	}

	/**
	 * ��ȡ activity ��ҳ�� ��
	 * 
	 * @param fragment
	 * @return
	 */
	public abstract Class<? extends AfMainActivity> getForegroundClass();

	// Debug Mode
	protected int mDebugMode = DEBUG_NONE;
	// ��ǰ������������ Ĭ��Ϊ δ����
	protected int mNetworkStatus = AfNetwork.TYPE_NONE;
	// ��ǰ��ҳ��
	protected AfActivity mCurActivity = null;
	// ��ǰ��ҳ��
	protected AfFragment mCurFragment = null;
	// ��ҳ��
	protected AfMainActivity mMainActivity = null;
	// ��ǰ�汾
	protected String mVersion = "0.0.0.0";
	// ���°汾
	protected String mServerVersion = "0.0.0.0";
	// ���°汾����
	protected String mUpdateDescribe = "";
	//�����������
	protected Random mRandom = new Random();

	// ��������߳�Worker
	protected Looper mLooper = null;

	protected AfThreadWorker mWorker = null;

	protected boolean mIsExiting = false;
	protected boolean mIsGoingHome = false;
	protected boolean mIsInitialized = false;
	// ���ǰ̨�Ƿ�������
	protected boolean mIsForegroundRunning = false;
	// ��������
	protected Date mStateTime = new Date();
	protected AfSharedPreference mRunningState = null;
	private PackageInfo mPackageInfo;

	public AfApplication() {
		// TODO Auto-generated constructor stub
		mApp = this;
		mLooper = Looper.myLooper();
		AfExceptionHandler.register();
		AfFileService.setServer("/FileService", "/FileServlet");
		AfSoapService.setServer("http://tempuri.org/", "MobileService.svc",
				"IMobileService/");
		AfSoapServiceOld.setServer("http://tempuri.org/", "MobileService.svc",
				"IMobileService/");
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

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		try {
			mRunningState = new AfSharedPreference(this, STATE_RUNNING);
			// ��ʼ���豸��Ϣ
			AfDeviceInfo.initialize(getAppContext());
			// ��ʼ���𶯿���̨
			AfVibratorConsole.initialize(getAppContext());
			// ��ʼ��AppSettings
			AfAppSettings.initialize(getAppContext());
			// ����ͼƬ����·��
			AfImageCaches.initialize(this, getCachesPath("image"));
			// ��ʼ�� ͼƬ����
			AfImageService.initialize(getAppContext());
			// ��ʼ�� �ļ�����
			AfFileService.initialize(getAppContext());
			// ��ʼ�� ���·���
			AfUpdateService.initialize(getAppContext());
			// App ������ʱ�� ��������������
			mNetworkStatus = AfNetwork.getNetworkState(this);
			// ���÷�����
			AfAppSettings set = AfAppSettings.getInstance();
			AfFileService.setServer(set.getFileServerIP(),
					set.getFileServerPort());
			AfSoapService.setServer(set.getDataServerIP(),
					set.getDataServerPort());
			mDebugMode = set.getDebugMode();
			// ��ʼ��֪ͨ����
			AfNotifyCenter.initailize(getAppContext());
			// ��ʼ���汾��
			getPackageVersion();
			// ������ݿ�
			new DatabaseUtil(getAppContext()).checkDataBaseVersion();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// handled
			AfExceptionHandler.handler(e, "AfApplication��onCreate ��ʼ��ʧ��");
		}
	}
	
	public boolean isDebug() {
		// TODO Auto-generated method stub
		return BuildConfig.DEBUG;
	}
	
	private void getPackageVersion() throws Exception {
		// TODO Auto-generated method stub
		int get = PackageManager.GET_CONFIGURATIONS;
		String tPackageName = getPackageName();
		PackageManager magager = getPackageManager();
		mPackageInfo = magager.getPackageInfo(tPackageName, get);
		mVersion = mPackageInfo.versionName;
	}

	/**
	 * ��ÿ�γ���������ʱ���ʼ��һ��
	 * 
	 * @deprecated �Ѿ�����
	 * @param power
	 *            ����Ȩ����֤
	 */
	public synchronized void initialize(AfActivity power) {
		if (power instanceof AfActivity && !mIsInitialized) {
			try {
				// ��ʶ��ʼ�����
				mIsInitialized = true;
			} catch (Throwable e) {
				// TODO: handle exception
				AfExceptionHandler.handler(e, "AfApplication��initialize ��ʼ��ʧ��");
			}
		}
	}

	/**
	 * ��ȡApp����Ŀ¼
	 * 
	 * @param type
	 */
	public synchronized String getPrivatePath(String type) {
		File file = new File(getCacheDir(), type);
		if (!file.exists()) {
			file.mkdir();
		}
		return file.getPath();
	}

	/**
	 * ��ȡApp����Ŀ¼
	 * 
	 * @param type
	 */
	public synchronized String getWorkspacePath(String type) {
		// TODO Auto-generated method stub
		File workspace = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String sdcard = Environment.getExternalStorageDirectory().getPath();
			workspace = new File(sdcard + "/" + getAppName() + "/" + type);
			if (!workspace.exists()) {
				workspace.mkdir();
			}
		} else {
			return getPrivatePath(type);
		}
		return workspace.getPath();
	}

	/**
	 * ��ȡcachesĿ¼
	 * 
	 * @param type
	 */
	public synchronized String getCachesPath(String type) {
		// TODO Auto-generated method stub
		File caches = new File(getWorkspacePath("caches"));
		caches = new File(caches, type);
		if (!caches.exists()) {
			caches.mkdir();
		}
		return caches.getPath();
	}

	/**
	 * ��ȡ����Ϣ
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		return mPackageInfo;
	}

	/**
	 * ��ȡ���������°汾
	 * 
	 * @return
	 */
	public String getServerVersion() {
		return mServerVersion;
	}

	/**
	 * ��ȡ���������°汾��������
	 * 
	 * @return
	 */
	public String getUpdateDescribe() {
		return mUpdateDescribe;
	}

	/**
	 * ��ȡApp�Ƿ� ��Ҫ����
	 * 
	 * @return
	 */
	public boolean isNeedUpdate() {
		// TODO Auto-generated method stub
		int curver = AfVersion.transformVersion(mVersion);
		int server = AfVersion.transformVersion(mServerVersion);
		return curver < server;
	}

	/**
	 * ��ȡApp�Ƿ� ִ�й� initialize
	 * 
	 * @deprecated �Ѿ�����
	 * @return
	 */
	public synchronized boolean isInitialize() {
		return mIsInitialized;
	}

	/**
	 * ��ȡCurActivity
	 * 
	 * @return
	 */
	public synchronized AfActivity getCurActivity() {
		// TODO Auto-generated method stub
		return mCurActivity;
	}

	/**
	 * ��ȡAfMainActivity
	 * 
	 * @return
	 */
	public synchronized AfMainActivity getMainActivity() {
		// TODO Auto-generated method stub
		return mMainActivity;
	}

	/**
	 * ������ҳ��
	 * 
	 * @param activity
	 *            ��ҳ��
	 */
	public void setMainActivity(AfMainActivity activity) {
		// TODO Auto-generated method stub
		mMainActivity = activity;
		mIsForegroundRunning = true;
	}

	/**
	 * ���õ�ǰ��ҳ��
	 * 
	 * @param type
	 *            ����Ȩ����֤
	 * @param activity
	 *            ��ǰ�� Activity
	 */
	public synchronized void setCurActivity(Object power, AfActivity activity) {
		if (power instanceof AfActivity || power instanceof AfActivity) {
			// ������ڷ�����ҳ��
			// if(activity instanceof AfMainActivity){
			// mMainActivity = (AfMainActivity)activity;
			// mIsForegroundRunning = true;
			// }
			if (mIsGoingHome || mIsExiting) {
				// ����Ѿ�������ҳ��
				if (activity instanceof AfMainActivity) {
					// �رշ�����ҳ�湦��
					if (mIsExiting) {
						activity.finish();
					}
					mIsExiting = false;
					mIsGoingHome = false;
				} else {
					// �ز���ǰҳ��ص���ҳ��
					activity.finish();
					return;
				}
			}
			mCurActivity = activity;
		}
	}

	/**
	 * ���õ�ǰ��ҳ��
	 * 
	 * @param type
	 *            ����Ȩ����֤
	 * @param fragment
	 *            ��ǰ�� Fragment
	 */
	public synchronized void setCurFragment(Object power, AfFragment fragment) {
		if (power instanceof AfFragment) {
			mCurFragment = fragment;
		}
	}

	/**
	 * ����App����״̬
	 * 
	 * @param power
	 *            ����thisָ�� ������֤Ȩ��
	 * @param networkState
	 *            ָ��������״̬
	 */
	public synchronized void setNetworkStatus(Object power, int networkState) {
		// TODO Auto-generated method stub
		if (power instanceof ConnectionChangeReceiver) {
			mNetworkStatus = networkState;

			notifyNetworkStatus(mCurActivity, networkState);
			notifyNetworkStatus(mCurFragment, networkState);

			// �������������
			if (mNetworkStatus != AfNetwork.TYPE_NONE) {
				// �����û�гɹ���λ
			}
		}
	}

	/**
	 * ���� ������ App �汾
	 * 
	 * @param power
	 *            ����thisָ�� ������֤Ȩ��
	 * @param version
	 *            �������汾
	 */
	public synchronized void setServerVersion(Object power, String version) {
		// TODO Auto-generated method stub
		mServerVersion = version;
		if (isNeedUpdate()) {
			notifyNeedUpdate(mCurActivity, mVersion, mServerVersion);
			notifyNeedUpdate(mCurFragment, mVersion, mServerVersion);
		}
	}

	/**
	 * ���� ������ App �汾 ��������
	 * 
	 * @param power
	 *            ����thisָ�� ������֤Ȩ��
	 * @param describe
	 *            �������汾
	 */
	public synchronized void setServerVersion(Object power, String version,
			String describe) {
		// TODO Auto-generated method stub
		mServerVersion = version;
		mUpdateDescribe = describe;
		if (isNeedUpdate()) {
			notifyUpdate(mCurActivity, mVersion, mServerVersion, describe);
			notifyUpdate(mCurFragment, mVersion, mServerVersion, describe);
		}
	}

	/**
	 * �� power ���� ��Ҫ����֪ͨ
	 * 
	 * @param power
	 *            ֪ͨ�Ķ��� ����ʵ�� INotifyNeedUpdate �ӿ�
	 * @param networkState
	 *            ��ǰ����״̬
	 */
	private void notifyUpdate(Object power, String curver, String server,
			String describe) {
		// TODO Auto-generated method stub
		if (power instanceof INotifyUpdate) {
			try {
				INotifyUpdate tINotify = (INotifyUpdate) power;
				tINotify.onNotifyUpdate(curver, server, describe);
			} catch (Throwable e) {
				// TODO: handle exception
				e.printStackTrace();// handled
				AfExceptionHandler.handler(e, "AfApplication��֪ͨ��Ҫ����ʱ�׳��쳣");
			}
		}
	}

	/**
	 * �� power ���� ��Ҫ����֪ͨ
	 * 
	 * @param power
	 *            ֪ͨ�Ķ��� ����ʵ�� INotifyNeedUpdate �ӿ�
	 * @param networkState
	 *            ��ǰ����״̬
	 */
	private void notifyNeedUpdate(Object power, String curver, String server) {
		// TODO Auto-generated method stub
		if (power instanceof INotifyNeedUpdate) {
			try {
				INotifyNeedUpdate tINotify = (INotifyNeedUpdate) power;
				tINotify.onNotifyNeedUpdate(curver, server);
			} catch (Throwable e) {
				// TODO: handle exception
				e.printStackTrace();// handled
				AfExceptionHandler.handler(e, "AfApplication��֪ͨ��Ҫ����ʱ�׳��쳣");
			}
		}
	}

	/**
	 * �� power ���� ����״̬�ı�֪ͨ
	 * 
	 * @param power
	 *            ֪ͨ�Ķ��� ����ʵ�� INotifyNetworkStatus �ӿ�
	 * @param networkState
	 *            ��ǰ����״̬
	 */
	private synchronized void notifyNetworkStatus(Object power, int networkState) {
		// TODO Auto-generated method stub
		if (power instanceof INotifyNetworkStatus) {
			try {
				INotifyNetworkStatus tINotify = (INotifyNetworkStatus) power;
				tINotify.onNetworkStatusChanged(networkState);
			} catch (Throwable e) {
				// TODO: handle exception
				e.printStackTrace();// handled
				AfExceptionHandler.handler(e, "AfApplication��֪ͨ����״̬�ı�ʱ�׳��쳣");
			}
		}
	}

	/**
	 * ֪ͨAPP ������̨ҳ��
	 * 
	 * @param fragment
	 */
	public synchronized void startForeground() {
		// TODO Auto-generated method stub
		if (mIsForegroundRunning == false) {
			mIsForegroundRunning = true;
			mMainActivity = null;
			Intent intent = new Intent(this, getForegroundClass());
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(intent);
		}
	}

	public synchronized void exitForeground(Object power) {
		// TODO Auto-generated method stub
		/** (2014-7-30 ע�� ֻ�е�notifyForegroundClosedʱ����Ϊfalse) **/
		// mIsForegroundRunning = false;
		if (mCurActivity != null) {
			if (mCurActivity instanceof AfMainActivity) {
				mCurActivity.finish();
			} else {
				mIsExiting = true;
				mCurActivity.finish();
			}
		}
	}

	/**
	 * ��ȡ ǰ̨ҳ���Ƿ�������
	 * 
	 * @return
	 */
	public synchronized boolean isForegroundRunning() {
		// TODO Auto-generated method stub
		return mIsForegroundRunning;
	}

	/**
	 * ֪ͨAPP ǰ̨�Ѿ��ر�
	 * 
	 * @param activity
	 *            Ȩ�޶��� ����this
	 */
	public synchronized void notifyForegroundClosed(AfMainActivity activity) {
		// TODO Auto-generated method stub
		if (activity instanceof AfMainActivity && mIsForegroundRunning) {
			// ��������Ϣ
			mCurActivity = null;
			mMainActivity = null;
			mCurFragment = null;
			mServerVersion = "0.0.0.0";
			mUpdateDescribe = "";
			mIsForegroundRunning = false;
		}
	}

	/**
	 * ��ȡ ExceptionHandler
	 * 
	 * @return
	 */
	public AfExceptionHandler getExceptionHandler() {
		// TODO Auto-generated method stub
		return new AfExceptionHandler();
	}

	/**
	 * ��ȡ AfAppSettings
	 * 
	 * @return
	 */
	public AfAppSettings getAppSetting() {
		// TODO Auto-generated method stub
		return new AfAppSettings(this);
	}

	/**
	 * ��ȡ AfImageService
	 * 
	 * @return
	 */
	public AfImageService getImageService() {
		// TODO Auto-generated method stub
		return new AfImageService();
	}

	/**
	 * ��ȡ AfFileService
	 * 
	 * @return
	 */
	public AfFileService getFileService() {
		// TODO Auto-generated method stub
		return new AfFileService();
	}

	/**
	 * ��ȡ AfUpdateService
	 * 
	 * @return
	 */
	public AfUpdateService getUpdateService() {
		// TODO Auto-generated method stub
		return new AfUpdateService(this);
	}

	/**
	 * ��ȡAPPͼ��ID,������Լ̳з���R.drawable.app_logo
	 * 
	 * @return APPͼ��ID
	 */
	public int getLogoId() {
		// TODO Auto-generated method stub
		return android.R.drawable.zoom_plate;
	}

	/**
	 * ��ȡAPP����,������Լ̳з���getString(R.string.app_name);
	 * 
	 * @return APP����
	 */
	public String getAppName() {
		// TODO Auto-generated method stub
		return "AndFrame";
	}
	/**
	 * ��ȡ DesĬ�ϼ�����Կ
	 * @return
	 */
	public String getDesKey() {
		// TODO Auto-generated method stub
		return AfMD5.getMD5("");
	}
	/**
	 * �������
	 * 
	 * @param password
	 *            ��������
	 * @return ���ܵ����� �������д����������ļ����㷨��Ĭ�� MD5��
	 */
	public String encryptionPassword(String password) {
		// TODO Auto-generated method stub
		return AfMD5.getMD5(password);
	}

	/**
	 * ��APP����ʱ����ʱ����App ״̬ ��AfActivity �е���
	 */
	public final void onRestoreInstanceState() {
		// TODO Auto-generated method stub
		Date date = mRunningState.getDate(STATE_TIME);
		if (date != null) {
			onRestoreInstanceState(mRunningState);
			mRunningState.clear();
		}
	}

	/**
	 * ��APP����ʱ����ʱ����App ״̬ ��onRestoreInstanceState() �е���
	 * 
	 * @param state
	 */
	protected void onRestoreInstanceState(AfSharedPreference state) {
		// TODO Auto-generated method stub
		mDebugMode = state.getInt(STATE_DEBUGMODE, mDebugMode);
		mNetworkStatus = state.getInt(STATE_NETWORKSTATUS, mNetworkStatus);
		mVersion = state.getString(STATE_VERSION, mVersion);
		mServerVersion = state.getString(STATE_SERVERVERSION, mServerVersion);
		mIsInitialized = state.getBoolean(STATE_ISINITIALIZED, mIsInitialized);
		mIsForegroundRunning = state.getBoolean(STATE_ISFORERUNNING,
				mIsForegroundRunning);
	}

	/**
	 * ���±���ת̨ʱ�䣬����Ѿ������´�Ҳ��ִ�� onSaveInstanceState
	 */
	public void updateStateTime() {
		// TODO Auto-generated method stub
		mStateTime = new Date();
	}

	/**
	 * ��APP����ԭʱ��ԭԭ��״̬ ��AfActivity �е���
	 */
	public final void onSaveInstanceState() {
		// TODO Auto-generated method stub
		// �������ʱ����һֱ�����ñ���
		Date date = mRunningState.getDate(STATE_TIME);
		if (date != null && date.equals(mStateTime)) {
			return;
		}
		mRunningState.putDate(STATE_TIME, mStateTime);
		onSaveInstanceState(mRunningState);
	}

	/**
	 * ��APP����ԭʱ��ԭԭ��״̬ ��AfActivity �е���
	 */
	protected void onSaveInstanceState(AfSharedPreference state) {
		// TODO Auto-generated method stub
		Editor editor = mRunningState.getSharePrefereEditor();
		editor.putInt(STATE_NETWORKSTATUS, mNetworkStatus);
		editor.putInt(STATE_DEBUGMODE, mDebugMode);
		editor.putString(STATE_VERSION, mVersion);
		editor.putString(STATE_SERVERVERSION, mServerVersion);
		editor.putBoolean(STATE_ISINITIALIZED, mIsInitialized);
		editor.putBoolean(STATE_ISFORERUNNING, mIsForegroundRunning);
		editor.commit();
	}

	/**
	 * ��ȡǩֵ��Ϣ
	 * 
	 * @author allen
	 * @version 2013-8-27 ����4:15:04
	 * @return
	 */
	public X509Certificate getSignInfo() {
		try {
			PackageManager manager = getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo(getPackageName(),
					PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			CertificateFactory certFactory = CertificateFactory
					.getInstance("X.509");
			ByteArrayInputStream stream = new ByteArrayInputStream(
					signs[0].toByteArray());
			return (X509Certificate) certFactory.generateCertificate(stream);
			// String pubKey = cert.getPublicKey().toString();
			// String signNumber = cert.getSerialNumber().toString();
			// System.out.println("signName:" + cert.getSigAlgName());
			// System.out.println("pubKey:" + pubKey);
			// System.out.println("signNumber:" + signNumber);
			// System.out.println("subjectDN:" +
			// cert.getSubjectDN().toString());
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * �ж��Ƿ��ں�̨���У���HOME֮��
	 * ��Ҫ����Ȩ�� android.permission.GET_TASKS 
	 * @return
	 */
	public boolean isBackground() {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		try {
			List<RunningTaskInfo> tasks = am.getRunningTasks(1);
			if (tasks.size() > 0) {
				ComponentName topActivity = tasks.get(0).topActivity;
				if (!topActivity.getPackageName().equals(getPackageName())) {
					return true;
				}
			}
		} catch (Throwable e) {
			// TODO: handle exception
		}
		return false;
	}
	/**
	 * ��ȡͳһ�����
	 * @return
	 */
	public Random getRandom(){
		return mRandom;
	}
}
