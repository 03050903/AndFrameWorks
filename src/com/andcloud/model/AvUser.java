package com.andcloud.model;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
/**
 * �û�
 * @author SCWANG
 */
public class AvUser extends AVUser {
	
	/**
	 * �ǳ�
	 */
	private static final String NickName = "NickName";
	/**
	 * ���Ի�ͷ��
	 */
	private static final String Avator = "Avator";
	/**
	 * �Ա�
	 */
	private static final String Gender = "Gender";
	/**
	 * ����ǰ��
	 */
	private static final String Signature = "Signature";

	public AvUser() {
		// TODO Auto-generated constructor stub
	}
	
	public void setNickName(String name) {
		this.put(NickName, name);
	}
	
	public String getNickName() {
		return this.getString(NickName);
	}
	
	public AVFile getAvator() {
		return getAVFile(Avator);
	}
	
	public void setAvator(AVFile avator) {
		put(Avator, avator);
	}
	
	public boolean getGender() {
		// TODO Auto-generated method stub
		return getBoolean(Gender);
	}
	
	public void setGender(boolean gender) {
		// TODO Auto-generated method stub
		put(Gender, gender);
	}
	
	public String getSignature() {
		// TODO Auto-generated method stub
		return getString(Signature);
	}
	
	public void setSignature(String signature){
		// TODO Auto-generated method stub
		put(Signature, signature);
	}
}
