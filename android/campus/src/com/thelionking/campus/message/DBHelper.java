package com.thelionking.campus.message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by the lion king on 2014/9/18.
 */
public final class DBHelper extends SQLiteOpenHelper{
    public static final String TAG = "DBHelper";

    public static final String DB_NAME = "JOKES_STORY";
    public static final String JOKE_TEXT_TABLE_NAME = "JOKE_TEXT";
    public static final String JOKE_PICTURE_TABLE_NAME = "JOKE_PICTURE";
    public static final String JOKE_VEDIO_TABLE_NAME = "JOKE_VEDIO";
    public static final int DB_VERSION = 1;

    public static final String JOKE_TEXT_TABLE_CREATE_SQL = "create table if not exists " + JOKE_TEXT_TABLE_NAME + ""
            + " (id integer primary key not null, date integer, author text, source text, content text, imgurl text, readingCount integer," +
            " type integer)";

    public static final String JOKE_PICTURE_TABLE_CREATE_SQL = "create table if not exists " + JOKE_PICTURE_TABLE_NAME + ""
            + " (id integer primary key not null, date integer, author text, source text, content text, imgurl text, readingCount integer," +
            " type integer)";

    public static final String JOKE_VEDIO_TABLE_CREATE_SQL = "create table if not exists " + JOKE_VEDIO_TABLE_NAME + ""
            + " (id integer primary key not null, date integer, author text, source text, content text, imgurl text, readingCount integer," +
            " type integer)";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createAllTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更换版本时使用，暂时不使用
    }

    /**
     * 创建数据库表，如果是本地取到的数据库则自己负责关闭（谁创建谁关闭）
     * @param db
     */
    public void createAllTables(SQLiteDatabase db) {
        boolean isLocal = false;
        if(db == null) {
            db = getWritableDatabase();
            isLocal = true;
        }
        try{
            db.execSQL(JOKE_TEXT_TABLE_CREATE_SQL);
            db.execSQL(JOKE_PICTURE_TABLE_CREATE_SQL);
            db.execSQL(JOKE_VEDIO_TABLE_CREATE_SQL);
            Log.d(TAG, "jokes表建表成功");
            //more operation added here
        }catch (Exception e){
            Log.d(TAG, "table : jokes create failed");
        }finally {
            if(isLocal && db != null) {
                db.close();
            }
        }
    }
}
