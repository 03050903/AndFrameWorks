package com.ontheway.util;

import java.util.regex.Pattern;

public class AfEmailCheck {
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