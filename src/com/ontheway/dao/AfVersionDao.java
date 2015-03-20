package com.ontheway.dao;

import android.content.Context;
import android.database.Cursor;

import com.ontheway.database.AfDbOpenHelper;
import com.ontheway.entity.VersionEntity;

public class AfVersionDao extends AfDao<VersionEntity> {
	
	public AfVersionDao(Context context) {
		// TODO Auto-generated method stub
		super(context, VersionEntity.class);
	}

	public VersionEntity getVersion() {
		// TODO Auto-generated method stub
		Cursor cur = getCursorLimit("*", 1, 0);
		if (cur.moveToNext()) {
			return getEntityFromCursor(cur);
		} else {
			// ���ݿ�û������ ˵���ոմ��� ��Ӱ汾��Ϣ
			VersionEntity tEntity = new VersionEntity();
			add(tEntity);
			return tEntity;
		}
	}

	/**
	 * ��Cursor�й��������ֶ� ���� Entity
	 * @param cur
	 * @return
	 */
	protected VersionEntity getEntityFromCursor(Cursor cur) {
		// TODO Auto-generated method stub
		VersionEntity tEntity = new VersionEntity();
		tEntity.Version = AfDbOpenHelper.getInt(cur, "Version");
		return tEntity;
	}
}
