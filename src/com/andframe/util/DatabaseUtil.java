package com.andframe.util;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build.VERSION;

import com.andframe.dao.AfVersionDao;
import com.andframe.database.AfDbOpenHelper;
import com.andframe.entity.VersionEntity;

public class DatabaseUtil
{
    private File file;
    private Context context;
    public static String dbName = AfDbOpenHelper.DBNAME;// ���ݿ������
    public static String DATABASE_PATH;// ���ݿ����ֻ����·��

    public DatabaseUtil(Context context) {
        this.context = context;
        this.file = context.getDatabasePath(dbName);
        DatabaseUtil.DATABASE_PATH = file.getPath();
        //String packageName = context.getPackageName();
        //DATABASE_PATH = "/data/data/" + packageName + "/databases/";
    }

    /**
     * ɾ�����ݿ�
     * @param file
     */
    @SuppressLint("NewApi")
	public void deleteDatabase(File file) 
    {
        if(VERSION.SDK_INT >= 16){
            SQLiteDatabase.deleteDatabase(file);
        }else{
            file.delete();
        }
    }
    /**
     * ������ݿ�汾
     *  ����汾���ɻ��߲����� ɾ�������
     */
    public void checkDataBaseVersion() {
    	checkDataBaseVersion(0);
    }
    public void checkDataBaseVersion(int index) {
        // TODO Auto-generated method stub
        try
        {
            AfVersionDao dao = new AfVersionDao(context);
            VersionEntity version = dao.getVersion();
            if(version.Version != AfDbOpenHelper.DATABASE_VERSION)
            {
                dao.close();
//                XmlCacheUtil.clear();
                deleteDatabase(file);
                if (index == 0) {
                    checkDataBaseVersion(1);
				}
                //String msg = "�ɹ��������ݿ��"+version.Version+"->"+DatabaseOpenHelper.DATABASE_VERSION+"��";
                //Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        }
        catch (Throwable e)//�汾�����޷���ȡ
        {
            // TODO: handle exception
//        	AfExceptionHandler.handleAttach(e, "���ݿ�汾�����޷���ȡ");
        	if (file != null && file.exists()) {
        		file.delete();
                if (index == 0) {
                    checkDataBaseVersion(1);
				}
			}
            //deleteDatabase(file);
            //checkDataBaseVersion();
            return;
        }
        

    }
    /**
     * �ж����ݿ��Ƿ����
     * 
     * @return false or true
     */
    public boolean checkDataBase() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {

        }
        if (db != null) {
            db.close();
        }
        return db != null;
    }

    /**
     * �������ݿ⵽�ֻ�ָ���ļ�����
     * 
     * @throws IOException
     */
    public void copyDataBase() throws IOException {
//        File dir = new File(DATABASE_PATH);
//        if (!dir.getParentFile().exists()){
//            // �ж��ļ����Ƿ���ڣ������ھ��½�һ��
//            dir.getParentFile().mkdir();
//        }
//        FileOutputStream os = new FileOutputStream(DATABASE_PATH);// �õ����ݿ��ļ���д����
//        InputStream is = context.getResources().openRawResource(R.raw.data);// �õ����ݿ��ļ���������
//        byte[] buffer = new byte[8192];
//        int count = 0;
//        while ((count = is.read(buffer)) > 0) {
//            os.write(buffer, 0, count);
//            os.flush();
//        }
//        is.close();
//        os.close();
    }
}
