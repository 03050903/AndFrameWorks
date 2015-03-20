package com.ontheway.bean;

import java.util.Date;
import java.util.UUID;

import com.ontheway.util.UUIDUtil;

public class Version {

	private UUID ID = UUIDUtil.Empty;

	// �汾�����ַ���
	public String Version = "";
	// �汾����ʱ��
	public Date UpdateTime = new Date();
	// �°汾�������ص�ַ
	public String DownloadURL = "";
	// ����������Ϣ
	public String DisplayMessage = "";
	// �汾��
	public int VersionCode = 0;
	// apk����
	public String ApkName = "";

	public Version() {
		// TODO Auto-generated constructor stub
		this.ID = UUID.randomUUID();
	}

	public Version(String version, Date date, String url, String msg, int code,
			String apk) {
		// TODO Auto-generated constructor stub
		this.ID = UUID.randomUUID();
		this.VersionCode = code;
		this.Version = version;
		this.UpdateTime = date;
		this.DownloadURL = url;
		this.DisplayMessage = msg;
		this.ApkName = apk;
	}

	public Version(Version version) {
		// TODO Auto-generated constructor stub
		this.VersionCode = version.VersionCode;
		this.ID = version.ID;
		this.Version = version.Version;
		this.UpdateTime = version.UpdateTime;
		this.DownloadURL = version.DownloadURL;
		this.DisplayMessage = version.DisplayMessage;
		this.ApkName = version.ApkName;
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID id) {
		ID = id;
	}
}
