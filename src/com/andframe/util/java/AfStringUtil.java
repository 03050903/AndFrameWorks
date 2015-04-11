package com.andframe.util.java;

import java.util.regex.Pattern;

/**
 * @Description: �ַ������ù�����
 * @Author: scwang
 * @Version: V1.0, 2015-1-26 ����12:23:15
 * @Modified: ���δ���AfStringUtil��
 */
public class AfStringUtil {

	public static boolean isEmpty(String string) {
		// TODO Auto-generated method stub
		return string == null || string.trim().length() == 0;
	}
	
	public static boolean isNotEmpty(String string) {
		// TODO Auto-generated method stub
		return string != null && string.trim().length() > 0;
	}

	public static boolean equals(String l,String r){
		if (l != r) {
			if (l != null || r != null) {
				return l.equals(r);
			}
			return false;
		}
		return true;
	}

	/**
	 * ��֤����������ʽ�Ƿ����
	 * 
	 * @param email
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean emailFormat(String email) {
		String pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return Pattern.compile(pattern).matcher(email).find();
	}
	
}
