package com.andframe.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;

import com.andframe.application.AfAppSettings;
import com.andframe.application.AfApplication;
import com.andframe.application.AfApplication.INotifyNeedUpdate;
import com.andframe.application.AfApplication.INotifyNetworkStatus;
import com.andframe.application.AfApplication.INotifyUpdate;
import com.andframe.application.AfUpdateService;
import com.andframe.caches.AfPrivateCaches;
import com.andframe.constant.AfNetworkEnum;
import com.andframe.feature.AfIntent;
import com.andframe.util.java.AfVersion;
/**
 * ���App��ҳ�� 
 * @author SCWANG
 *	ʵ�� ����֪ͨ������ı�֪ͨ�ӿ�
 *		��Ҫʵ���˸�����ʾ��������Ч��ʾ
 *		onCreate �� App ע����ҳ��
 *		onDestroy �� App �����ҳ��
 *
 *		���ذ��� ��ʾ "�ٰ�һ���˳�����"
 */
public abstract class AfMainActivity extends AfActivity
	implements INotifyNeedUpdate,INotifyUpdate,INotifyNetworkStatus,OnClickListener{

	protected static final String KEY_IGNORE = "39894915342252804102";
	
	protected long mExitTime;
	protected long mExitInterval = 2000;
	protected boolean mNotifyNetInvaild = true;

	protected abstract void onActivityCreate(Bundle savedInstanceState);

	@Override
	protected void onCreate(Bundle bundle, AfIntent intent) throws Exception {
		// TODO Auto-generated method stub
		super.onCreate(bundle, intent);
		AfApplication app = AfApplication.getApp();
		app.setMainActivity(this);
//		if(!app.isInitialize()){
//			app.initialize(this);
//			if(onRestoreApp()){
//				makeToastShort("�������޸�");
//			}else{
//				this.finish();
//				return;
//			}
//		} 
		
		onActivityCreate(bundle);
		if (AfApplication.getNetworkStatus() == AfNetworkEnum.TYPE_NONE) {
			// ��ʾ���粻���öԻ���
			if(mNotifyNetInvaild)showNetworkInAvailable();
//		}else if(app.isNeedUpdate()){
//			// ��ʾȥҪ���¶Ի���
//			showNeedUpdate();
		}else if (AfAppSettings.getInstance().isAutoUpdate()) {
			//�Զ�����
			AfUpdateService.checkUpdate();
		}
	}

	/**
	 * @deprecated �Ѿ�����
	 * @return
	 */
	protected boolean onRestoreApp() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * ֪ͨAPP ǰ̨�Ѿ��ر�
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		AfApplication app = AfApplication.getApp();
		// ֪ͨAPP �����Ѿ��˳�
		app.notifyForegroundClosed(this);
		super.onDestroy();
	}

//	@Override
//	public boolean dispatchKeyEvent(KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
//				&& event.getAction() == KeyEvent.ACTION_DOWN) {
//			if ((System.currentTimeMillis() - mExitTime) > mExitInterval) {
//				makeToastShort("�ٰ�һ���˳�����");
//				mExitTime = System.currentTimeMillis();
//			} else {
//				this.finish();
//			}
//			return true;
//		}
//		return super.dispatchKeyEvent(event);
//	}
	
	@Override
	protected boolean onBackKeyPressed() {
		// TODO Auto-generated method stub
		boolean isHandled = super.onBackKeyPressed();
		if (!isHandled) {
			isHandled = true;
			if ((System.currentTimeMillis() - mExitTime) > mExitInterval) {
				makeToastShort("�ٰ�һ���˳�����");
				mExitTime = System.currentTimeMillis();
			} else {
				this.finish();
			}
		}
		return isHandled;
	}

	@Override
	public void onNotifyUpdate(String curver, String server, String describe) {
		// TODO Auto-generated method stub
		// ��ʾȥҪ���¶Ի���
		AfApplication app = AfApplication.getApp();
		if (app.isForegroundRunning() && app.getCurActivity() == this) {
			showNeedUpdate();
		}
	}
	
	@Override
	public void onNotifyNeedUpdate(String curver, String server) {
		// TODO Auto-generated method stub
		// ��ʾȥҪ���¶Ի���
		AfApplication app = AfApplication.getApp();
		if (app.isForegroundRunning() && app.getCurActivity() == this) {
			showNeedUpdate();
		}
	}
	
	@Override
	public void onNetworkStatusChanged(int networkStatus) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}


	/**
	 * ��ʾ���粻���öԻ���
	 */
	protected void showNetworkInAvailable() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("��ǰ���粻����");
		builder.setMessage("��������������Ϣ���������������ӡ�");
		builder.setNegativeButton("��������",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// ���û���������ӣ�������������ý���
						startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
					}
				});
		builder.setPositiveButton("���������Ϣ",null);
		builder.create();
		builder.show();
	}

	protected void showNeedUpdate() {
		// TODO Auto-generated method stub
		AfPrivateCaches caches = AfPrivateCaches.getInstance();
		String curversion = AfApplication.getVersion();
		String serversion = AfApplication.getApp().getServerVersion();
		String sedescribe = AfApplication.getApp().getUpdateDescribe();
		if (caches.getString(KEY_IGNORE, "").equals(serversion)) {
			return ;
		}
		int curver = AfVersion.transformVersion(curversion);
		int server = AfVersion.transformVersion(serversion);
		if (curver < server) {
			Builder builder = new Builder(this);
//			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setTitle("���ø���");
			if(sedescribe == null || sedescribe.length() == 0){
				builder.setMessage("ϵͳ��鵽���ø���\r\n    ���°汾��"
						+ serversion + "\r\n    ��ǰ�汾��" + curversion);
			}else{
				builder.setMessage("ϵͳ��鵽���ø���\r\n    "
						+ curversion  + " -> " + serversion+ "\r\n\r\n" +sedescribe);
			}
			builder.setPositiveButton("���ظ���", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String serversion = AfApplication.getApp().getServerVersion();
					String url = AfUpdateService.getInstance().getApkUrl(serversion);
					AfUpdateService.startDownLoadUpate(getContext(), url, serversion);
				}
			});
			builder.setNeutralButton("���Դ˰汾",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					AfPrivateCaches caches = AfPrivateCaches.getInstance();
					String serversion = AfApplication.getApp().getServerVersion();
					caches.put(KEY_IGNORE, serversion);
				}
			});
			builder.setNegativeButton("�ݲ�����",null);
			builder.create();
			builder.show();
		}
	}
}
