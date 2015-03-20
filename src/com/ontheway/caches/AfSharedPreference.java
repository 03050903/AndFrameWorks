package com.ontheway.caches;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.ontheway.exception.AfException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;

/**
 * AfSharedPreference
 * 
 * @author SCWANG ��װ android SharedPreferences ��Ҫʵ�� 1.��̬����·�� path ����ָ�����浽SD����
 *         public AfSharedPreference(Context context,String path,String name)��
 *         2.������ڸ�ʽ֧�� public Date getDate(String key, long value) public Date
 *         getDate(String key, Date value) 3.�Ͱ汾���� public Set<String>
 *         getStringSet(String key, Set<String> value) public void
 *         putStringSet(String key, Set<String> value)
 */
public class AfSharedPreference {
	private SharedPreferences mShared = null;

	public AfSharedPreference(Context context, String name) {
		mShared = context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	public AfSharedPreference(Context context, String name, int mode) {
		mShared = context.getSharedPreferences(name, mode);
	}

	/**
	 * 1.��̬����·�� path ����ָ�����浽SD����
	 * 
	 * @param context
	 * @param path
	 *            ����·��
	 * @param name
	 * @throws Exception
	 *             ת��·��ʧ���쳣��SD����ռ�ã�
	 */
	public AfSharedPreference(Context context, String path, String name)
			throws Exception {
		try {
			File file = setPreferencesPath(context, new File(path));
			mShared = context.getSharedPreferences(name, Context.MODE_PRIVATE);
			setPreferencesPath(context, file);
		} catch (Throwable e) {
			// TODO: handle exception
			AfException.handle(e, "����ת��·������ mShared="
					+ (mShared == null ? "null" : mShared.toString()));
			throw new Exception(e);
		}
	}

	private File setPreferencesPath(Context context, File file) {
		// TODO Auto-generated method stub
		File oldfile = file;
		try {
			Field field;
			// ��ȡContextWrapper�����е�mBase�������ñ���������ContextImpl����
			field = ContextWrapper.class.getDeclaredField("mBase");
			field.setAccessible(true);
			// ��ȡmBase����
			Object obj = field.get(context);
			// ��ȡContextImpl.mPreferencesDir�������ñ��������������ļ��ı���·��
			field = obj.getClass().getDeclaredField("mPreferencesDir");
			field.setAccessible(true);
			// �����Զ���·��
			// �޸�mPreferencesDir������ֵ
			oldfile = (File) field.get(obj);
			field.set(obj, file);
		} catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return oldfile;
	}

	public SharedPreferences getSharedPreferences() {
		return mShared;
	}

	public Editor getSharePrefereEditor() {
		return mShared.edit();
	}

	public boolean getBoolean(String key, boolean value) {
		return mShared.getBoolean(key, value);
	}

	public String getString(String key, String value) {
		return mShared.getString(key, value);
	}

	public float getFloat(String key, float value) {
		return mShared.getFloat(key, value);
	}

	public int getInt(String key, int value) {
		return mShared.getInt(key, value);
	}

	public long getLong(String key, long value) {
		return mShared.getLong(key, value);
	}

	public Date getDate(String key) {
		return getDate(key, null);
	}

	public Date getDate(String key, long value) {
		return new Date(getLong(key, value));
	}

	public Date getDate(String key, Date value) {
		long time = getLong(key, -1);
		return time == -1 ? value : new Date(time);
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	public Set<String> getStringSet(String key, Set<String> value) {
		if (VERSION.SDK_INT < 11) {
			String jvalue = mShared.getString(key, null);
			if (jvalue == null) {
				return value;
			}
			try {
				Gson json = new Gson();
				value = json.fromJson(jvalue, value.getClass());
			} catch (Throwable e) {
				// TODO: handle exception
			}
			return value;
		}
		return mShared.getStringSet(key, value);
	}

	@SuppressLint("NewApi")
	public void putStringSet(String key, Set<String> value) {
		Editor editor = getSharePrefereEditor();
		if (VERSION.SDK_INT < 11) {
			String jvalue = "";
			try {
				Gson json = new Gson();
				jvalue = json.toJson(value);
			} catch (Throwable e) {
				// TODO: handle exception
			}
			editor.putString(key, jvalue);
		} else {
			editor.putStringSet(key, value);
		}
		editor.commit();
	}

	@SuppressWarnings("unchecked")
	public List<String> getStringList(String key, List<String> value) {
		String jvalue = mShared.getString(key, null);
		if (jvalue == null) {
			return value;
		}
		try {
			Gson json = new Gson();
			value = json.fromJson(jvalue, value.getClass());
		} catch (Throwable e) {
			// TODO: handle exception
		}
		return value;
	}

	public void putStringList(String key, List<String> value) {
		Editor editor = getSharePrefereEditor();
		String jvalue = "";
		try {
			Gson json = new Gson();
			jvalue = json.toJson(value);
		} catch (Throwable e) {
			// TODO: handle exception
		}
		editor.putString(key, jvalue);
		editor.commit();
	}

	public void putBoolean(String key, boolean value) {
		Editor editor = getSharePrefereEditor();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void putString(String key, String value) {
		Editor editor = getSharePrefereEditor();
		editor.putString(key, value);
		editor.commit();
	}

	public void putFloat(String key, float value) {
		Editor editor = getSharePrefereEditor();
		editor.putFloat(key, value);
		editor.commit();
	}

	public void putInt(String key, int value) {
		Editor editor = getSharePrefereEditor();
		editor.putInt(key, value);
		editor.commit();
	}

	public void putLong(String key, long value) {
		Editor editor = getSharePrefereEditor();
		editor.putLong(key, value);
		editor.commit();
	}

	public void putDate(String key, Date date) {
		// TODO Auto-generated method stub
		putLong(key, date.getTime());
	}

	public void clear() {
		Editor editor = getSharePrefereEditor();
		editor.clear();
		editor.commit();
	}
}
