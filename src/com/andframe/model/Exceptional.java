package com.andframe.model;

import java.util.UUID;

import com.andframe.model.framework.AfModel;
import com.andframe.util.UUIDUtil;

public class Exceptional extends AfModel{
	
	//�µĴ���
	//�Ѿ����գ����ڴ���
	//������ϲ��ύ
	//ȷ�������Ѿ����
	public static final int STATUS_NEW = 0;		
	public static final int STATUS_RECEIVE = 1;
	public static final int STATUS_SUBMIT = 2;
	public static final int STATUS_CONFIRM = 3;

    public UUID UserID=UUIDUtil.Empty;
    public String Device = "";
    public String Version = "";
    public String Message = "";
    public String Stack = "";
    public String Thread = "";
    public String Platform = "Android";
    public int Status = STATUS_NEW;
	
	public Exceptional(){
	}

	public Exceptional(String Name,String Message,
			String Stack,String Version,String Thread){
		this.ID = UUID.randomUUID();
		this.Name = Name;
		this.Message = Message;
		this.Stack = Stack;
		this.Version = Version;
		this.Thread = Thread;
	}
	
}
