package com.andadver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andframe.application.AfApplication;

/**
 * ���������
 * @author SCWANG
 *	���䲻ͬ�Ĺ��ƽ̨
 */
public class AdvertAdapter {

	public static class AdCustom{
		public String Id = "";
		public int Points = 0;
		public transient Bitmap Icon = null;
		public String Name = "";
		public String Text = "";
		public String Action = "";
		public String Package = "";
		public String Filesize = "";
		public String Provider = "";
		public String Version = "";
		public String Description = "";
		public String[] ImageUrls = null;
	}

	/**
	 * ����Key
	 */
	public static final String KEY_PLUGIN = "PLUGIN_ADVERT";

	/**��������**/
	public static final String KEY_DEPLOY = "KEY_DEPLOY";


	protected static String mValue = "advert";
	protected static boolean IS_HIDE= true;
//	private static AdvertAdapter mAdvertAdapter;

	public static String DEFAULT_CHANNEL = "advert";
	
	public String getValue() {
		return mValue;
	}

	/**
	 * ��ȡȫ�� ���������
	 * @return
	 */
	public static AdvertAdapter getInstance(){
		String key = KEY_PLUGIN;
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
	/**
	 * ��ȡ����
	 * @return
	 */
	public String getChannel() {
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
	public boolean mIsOnlineHideChecking;
	protected void doCheckOnlineHide(final Context context) {
		// TODO Auto-generated method stub
	}
	public void onCheckOnlineHideFail(Throwable mException) {
		// TODO Auto-generated method stub
		
	}

	void setHide(boolean value) {
		// TODO Auto-generated method stub
		IS_HIDE = value;
	}

	void setValue(String value) {
		// TODO Auto-generated method stub
		mValue = value;
	}
	
}
