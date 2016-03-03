package com.thelionking.campus.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author the lion king
 */
public final class DBHelper extends SQLiteOpenHelper{
    public static final String TAG = "DBHelper";
    //数据库名
    public static final String DB_NAME = "CAMPUS";
    //表名
    public static final String PROVINCE_TABLE = "provinces";
    public static final String SCHOOL_TABLE = "schools";
    public static final String BUILDING_TABLE = "buildings";
    public static final String ROOM_TABLE = "rooms";
    
    public static final String USER_TABLE = "users";
    
    public static final int DB_VERSION = 1;
    //省份表
    public static final String PROVINCE_TABLE_CREATE_SQL = "create table if not exists " + PROVINCE_TABLE + ""
            + " (province_id integer primary key not null, province_name text";
    //学校表
    public static final String SCHOOL_TABLE_CREATE_SQL = "create table if not exists " + SCHOOL_TABLE + ""
            + " (school_id integer primary key not null, school_name text, school_location text, school_describe text, province_id integer not null)";
    //自习楼表
    public static final String BUILDING_TABLE_CREATE_SQL = "create table if not exists " + BUILDING_TABLE + ""
            + " (building_id integer primary key not null, building_name text, building_location text, building_url text, school_id integer)";
    //自习室表
    public static final String ROOM_TABLE_CREATE_SQL = "create table if not exists " + ROOM_TABLE + ""
            + " (room_id integer primary key not null, room_name text, room_location text, room_capacity integer, room_use_rate integer, building_id integer)";
    //用户表， 本地用户表只记录用户id, 用户名，用户密码(供自动登录使用)
    public static final String USER_TABLE_CREATE_SQL = "create table if not exists " + USER_TABLE + ""
    		+ " (user_id integer primary key not null, user_name text, user_password text)";
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
        	db.execSQL(PROVINCE_TABLE_CREATE_SQL);
            db.execSQL(SCHOOL_TABLE_CREATE_SQL);
            db.execSQL(BUILDING_TABLE_CREATE_SQL);
            db.execSQL(ROOM_TABLE_CREATE_SQL);
            db.execSQL(USER_TABLE_CREATE_SQL);
            Log.d(TAG, "school表建表成功！");
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
