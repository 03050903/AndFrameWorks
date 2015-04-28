package com.andcloud;

import android.content.Context;

import com.andframe.application.AfApplication;
import com.andframe.application.AfExceptionHandler;
import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

public class AndCloud {

	public static void registerSubclass(Class<? extends AVObject> clazz){
		try {
			AVObject.registerSubclass(clazz);
		} catch (Exception e) {
			// TODO: handle exception
			AfExceptionHandler.handler(e, "AndCloud.registerSubclass");
		}
	}
	
	public static void initializeAvos(Context context,String appid,String appkey,String channel) {
		try {
		    // ��ʼ��Ӧ�� Id �� Ӧ�� Key����������Ӧ�����ò˵����ҵ���Щ��Ϣ
		    AVOSCloud.initialize(context,appid,appkey);
//		    AVAnalytics.start(this);
		    AVAnalytics.enableCrashReport(context, true);
		    AVAnalytics.setAppChannel(channel);
		} catch (Exception e) {
			// TODO: handle exception
			AfExceptionHandler.handler(e, "AndCloud.initialize");
		}
	}
	
	public static void initializeUmeng(Context context,String appkey,String channel) {
		try {
			boolean isDebug = AfApplication.getApp().isDebug();
			AnalyticsConfig.setAppkey(appkey);
			AnalyticsConfig.setChannel(channel);
			MobclickAgent.setDebugMode(isDebug);
//			SDK��ͳ��Fragmentʱ����Ҫ�ر�Activity�Դ���ҳ��ͳ�ƣ�
//			Ȼ����ÿ��ҳ�������¼���ҳ��ͳ�ƵĴ���(���������� onResume �� onPause ��Activity)��
			MobclickAgent.openActivityDurationTrack(false);
//			MobclickAgent.setAutoLocation(true);
//			MobclickAgent.setSessionContinueMillis(1000);
			MobclickAgent.updateOnlineConfig(context);
		} catch (Exception e) {
			// TODO: handle exception
			AfExceptionHandler.handler(e, "MobclickAgent.updateOnlineConfig");
		}
	}
}
