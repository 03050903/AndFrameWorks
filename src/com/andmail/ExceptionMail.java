package com.andmail;

import com.andframe.application.AfApplication;
import com.andframe.caches.AfPrivateCaches;
import com.andframe.model.Exceptional;
import com.andframe.util.java.AfDateFormat;
import com.andframe.util.java.AfMD5;
import com.andmail.kernel.AppinfoMail;

public class ExceptionMail extends AppinfoMail{

	private String md5 = AfApplication.getApp().getDesKey();
	private Exceptional mExceptional;

	public ExceptionMail(Exceptional ex) {
		super(title(ex), "");
		// TODO Auto-generated constructor stub
		mailtype = "�쳣��׽";
		mExceptional = ex;
		md5 = AfMD5.getMD5(ex.Name+ex.Message+ex.Stack);
	}

	private static String title(Exceptional ex) {
		// TODO Auto-generated method stub
		if (ex.Message == null || ex.Message.length() == 0) {
			return ex.Name;
		}else if(ex.Message.length() > 32){
			return ex.Name;
		}
		return ex.Message;
	}

	protected void packAppinfo() {
		// TODO Auto-generated method stub
		mContent = "����ʱ��:" +AfDateFormat.FULL.format(mExceptional.RegDate) + 
				"\r\n\r\n�쳣����:\r\n" + mExceptional.Name + 
				"\r\n\r\n�쳣��Ϣ:\r\n" + mExceptional.Message + 
				"\r\n\r\n��ע��Ϣ:\r\n" + mExceptional.Remark;
		super.packAppinfo();
		mContent = mContent + 
				"\r\n\r\n�쳣�߳�:\r\n" + mExceptional.Thread + 
				"\r\n\r\n" + mExceptional.Stack;
	}

	@Override
	public void send() throws Exception {
		// TODO Auto-generated method stub
		AfPrivateCaches cache = AfPrivateCaches.getInstance();
		if (cache.get(md5, String.class) == null) {//�����ͬ����ֻ����һ��
			super.send();
			cache.put(md5, md5);
		}
	}

	@Override
	protected void onException(Throwable e) {
		// TODO Auto-generated method stub
		super.onException(e);
		AfPrivateCaches.getInstance().put(md5, md5);
	}
}
