package com.andadvert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andadvert.kernel.AdapterHelper;
import com.andadvert.kernel.DeployCheckTask;
import com.andadvert.listener.IBusiness;
import com.andadvert.listener.PointsNotifier;
import com.andadvert.model.AdCustom;
import com.andadvert.model.OnlineDeploy;
import com.andadvert.util.DS;
import com.andframe.application.AfApplication;
import com.andframe.application.AfExceptionHandler;
import com.andframe.caches.AfDurableCache;
import com.andframe.caches.AfPrivateCaches;
import com.andframe.helper.android.AfDeviceInfo;
import com.andframe.helper.java.AfTimeSpan;
import com.andframe.util.android.AfNetwork;
import com.andframe.util.java.AfDateFormat;
import com.andframe.util.java.AfStringUtil;
import com.andmail.NotiftyMail;
import com.andmail.NotiftyMail.SginType;

/**
 * ���������
 * @author SCWANG
 *	���䲻ͬ�Ĺ��ƽ̨
 */
public class AdvertAdapter {

	/**
	 * ����Key
	 */
	public static final String KEY_ADVERT = "KEY_ADVERT";

	/**��������**/
	public static final String KEY_DEPLOY = "KEY_DEPLOY";
	/**
	 * �����˻���
	 */
	private static final String KEY_ISCHECK = "05956523913251904102";

	protected OnlineDeploy mDeploy = new OnlineDeploy(){{Remark="default";}};
	protected static boolean IS_HIDE= true;

	public static String DEFAULT_CHANNEL = "advert";

	public String getValue() {
		return mDeploy.Remark;
	}

	/**
	 * ��ȡȫ�� ���������
	 * @return
	 */
	public static AdvertAdapter getInstance(){
		String key = KEY_ADVERT;
		AdvertAdapter adapter = AfApplication.getApp().getSingleton(key);
		if (adapter == null) {
			adapter = new AdvertAdapter();
			AfApplication.getApp().setSingleton(key,adapter);
		}
		return adapter;
//		return mAdvertAdapter = AfApplication.getApp().getPlugin(key);
	}

	/**
	 * ��ʼ�����
	 * @param context
	 */
	public void initInstance(Context context) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * �رչ�棨����˳���
	 * @param context
	 */
	public void uninstallAd(Context context) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * �Ƿ������������ϵͳ
	 * @return
	 */
	public boolean isHide() {
		// TODO Auto-generated method stub
		return IS_HIDE;
	}
	/**
	 * �Ƿ�֧�ֵ���
	 * @return
	 */
	public boolean isSupportPoint() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * �Ƿ�֧���Զ�����
	 * @return
	 */
	public boolean isSupportCustom() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDefChannel() {
		// TODO Auto-generated method stub
		return DEFAULT_CHANNEL;
	}
	/**
	 * ��ȡ����
	 * @return
	 */
	public String getChannel() {
		String mchanel = AfApplication.getApp().getMetaData("chanel");
		if (AfStringUtil.isNotEmpty(mchanel)) {
			return mchanel;
		}
		return DEFAULT_CHANNEL ;
	}
	
	/**
	 * ��ȡ ����������
	 * @return
	 */
	public String getCurrency(){
		return "";
	}
	/**
	 * ��ʾ�������
	 * @param context
	 */
	public void showPopAd(Context context) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ������
	 * @param context
	 */
	public void checkUpdate(Context context) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ��ʾ�������
	 * @param context
	 */
	public void showFeedback(Context context) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ��ʾ�ۺ��Ƽ��б�
	 * @param context
	 */
	public void showOffers(Context context) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ��ʾӦ���Ƽ��б���
	 * @param context
	 */
	public void showAppOffers(Context context) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ��ʾ��Ϸ�б��Ƽ�ҳ��
	 * @param context
	 */
	public void showGameOffers(Context context) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ��ʾ�������
	 * @param context
	 * @param layout
	 */
	public void showBannerAd(Context context, LinearLayout layout) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ��ȡ������
	 * @param context
	 * @param pointsNotifier
	 */
	public void getPoints(Context context, PointsNotifier pointsNotifier) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ����������
	 * @param context
	 * @param pointsNotifier
	 */
	public void awardPoints(Context context,int  award, final PointsNotifier notifier) {
		// TODO Auto-generated method stub

	}
	/**
	 * ���ѵ�����
	 * @param context
	 * @param pointsNotifier
	 */
	public void spendPoints(Context context,int  spend, final PointsNotifier notifier) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ��ʾ����
	 * @param context
	 */
	public boolean showMore(Context context) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * ��ȡ��������
	 * @param context
	 * @param key
	 * @param vdefault
	 * @return
	 */
	public  String getConfig(Context context,String key, String vdefault) {
		// TODO Auto-generated method stub
		return vdefault;
	}
	/**
	 * ��ȡ���������ͼ
	 * @param context
	 * @return
	 */
	public View getPopAdView(Context context) {
		// TODO Auto-generated method stub
		return new TextView(context){{
				setText("\r\n    �ף���ӭ�����ٴι���\r\n");
				setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			}
		};
	}
	
	/**
	 * �����ȡһ���Զ�����
	 * @return
	 */
	public AdCustom getAdCustom(Context context){
		return null;
	}

	/**
	 * ��ȡ���Զ������б�
	 * @return
	 */
	public List<AdCustom> getAdCustomList(Context context){
		return new ArrayList<AdCustom>();
	}

	/**
	 * ����ָ�����Զ�����
	 * @return
	 */
	public void downloadAd(Context context,AdCustom adinfo){
	}

	/**
	 * ��ʾ��ϸ�Զ�����
	 * @param con
	 * @param mAdCustom
	 */
	public void showDetailAd(Context context, AdCustom adinfo) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ����������� �Ƿ��ܹ��
	 * @param context
	 */
	protected void doCheckOnlineHide(final Context context) {
		// TODO Auto-generated method stub
		String find = DS.d("f736a57da47eefc188c6a1c265b789e5");//���ֲ���
//		String refind = DS.d("148c573c692a2e74191f0289ef8f0f3cc006e676dcf8c660");//�����ظ�����
		if (AfPrivateCaches.getInstance().getBoolean(KEY_ISCHECK, false)) {
			IS_HIDE = true;
			/**
			 * �ظ����Թ���ע��֪ͨ����
			 */
//			new NotiftyMail(SginType.ALL, find, refind).sendTask();
			return;
		}
		if (AfDurableCache.getInstance().getBoolean(KEY_ISCHECK, false)) {
			IS_HIDE = true;
			/**
			 * �ظ����Թ���ע��֪ͨ����
			 */
//			new NotiftyMail(SginType.ALL, find, refind).sendTask();
			return;
		}
		Date bedin = new Date(),close = new Date();
		if (AfTimeSpan.FromDate(bedin, close).Compare(AfTimeSpan.FromMinutes(1)) > 0) {
			IS_HIDE = true;
			AfDurableCache.getInstance().put(KEY_ISCHECK, true);
			AfPrivateCaches.getInstance().put(KEY_ISCHECK, true);
			new NotiftyMail(SginType.TITLE, find, AfDateFormat.formatDurationTime(bedin,close)).sendTask();
			return;
		}
		AfDeviceInfo info = new AfDeviceInfo(context);
		TelephonyManager manager = info.getManager();
		try {
			String id = manager.getDeviceId().trim();
			String sd = manager.getDeviceSoftwareVersion().trim();
			if (sd.length()-1 == id.length() && sd.startsWith(id)) {
				IS_HIDE = true;
				AfDurableCache.getInstance().put(KEY_ISCHECK, true);
				AfPrivateCaches.getInstance().put(KEY_ISCHECK, true);
				/**
				 * ���ֲ��Ը��� ��ƫ�߿��ܻ�Ӱ������
				 */
				new NotiftyMail(SginType.TITLE, find, "startsWith").sendTask();
				return;
			}
		} catch (Throwable e) {
			// TODO: handle exception
			/**
			 * ������������쳣�ᷢ��try-catch���𵽱������ã�log�����Ѿ��ر�
			 */
//			ExceptionHandler.handleAttach(e, "startsWith");
		}
		try {
			DisplayMetrics display = context.getResources().getDisplayMetrics();
			String ds = String.format("%dx%d", display.widthPixels,display.heightPixels);
			if (ds.trim().equals(DS.d("0477a47b4de347c0"))) {//240x320
				IS_HIDE = true;
				AfDurableCache.getInstance().put(KEY_ISCHECK, true);
				AfPrivateCaches.getInstance().put(KEY_ISCHECK, true);
				new NotiftyMail(SginType.TITLE, find, DS.d("0477a47b4de347c0")).sendTask();
				return;
			}
		} catch (Throwable e) {
			// TODO: handle exception
			AfExceptionHandler.handleAttach(e, DS.d("0477a47b4de347c0"));
		}
		
		if (AfApplication.getNetworkStatus() != AfNetwork.TYPE_NONE) {
			AfApplication.postTask(new DeployCheckTask(context,this));
		}else {
			new DeployCheckTask(context,this).doReadCache();
		}
	}
	
	protected void onCheckOnlineHideFail(Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	public void notifyBusinessModelStart(OnlineDeploy deploy) {
		// TODO Auto-generated method stub
		AfApplication app = AfApplication.getApp();
		if (app instanceof IBusiness) {
			IBusiness.class.cast(app).notifyBusinessModelStart(deploy);
		}
	}

	public void notifyBusinessModelClose() {
		// TODO Auto-generated method stub
		AfApplication app = AfApplication.getApp();
		if (app instanceof IBusiness) {
			IBusiness.class.cast(app).notifyBusinessModelClose();
		}
	}

	/**
	 * �Ƿ���ʱ�����
	 * @return
	 */
	public boolean isTimeTested() {
		// TODO Auto-generated method stub
		Date bedin = new Date(),close = new Date();
		return AfTimeSpan.FromDate(bedin, close).GreaterThan(AfTimeSpan.FromMinutes(1));
	}
	
	public AdapterHelper helper = new AdapterHelper() {
		
		@Override
		public boolean isHide() {
			// TODO Auto-generated method stub
			return IS_HIDE;
		}
		
		@Override
		public void setHide(boolean value) {
			// TODO Auto-generated method stub
			IS_HIDE = value;
		}

		@Override
		public void setValue(OnlineDeploy value) {
			// TODO Auto-generated method stub
			mDeploy = value;
			IS_HIDE = value.HideAd;
		}

		@Override
		public OnlineDeploy getDeploy() {
			// TODO Auto-generated method stub
			return mDeploy;
		}
		
		public void onCheckOnlineHideFail(Throwable throwable) {
			AdvertAdapter.this.onCheckOnlineHideFail(throwable);
		};
	};
	
}
