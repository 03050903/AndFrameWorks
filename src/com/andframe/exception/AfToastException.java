package com.andframe.exception;

/**
 * ToastException 
 * 1.������Ԥ��֮�ڻᵼ�µ��쳣�����û���������
 * 2.ֱ�������û�������Message����������������������
 * 3.��������쳣֪ͨ��ToastException ���������֪�쳣�����߿ɽ����쳣
 * @author SCWANG
 */
public class AfToastException extends AfException{
	private static final long serialVersionUID = -4134318918697162517L;

	public AfToastException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AfToastException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	public AfToastException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public AfToastException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

	
}
