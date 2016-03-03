package com.thelionking.campus.message;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by the lion king on 2014/9/27.
 */
public class JokeDao extends BaseDao<Joke> {
    public static final String TAG = "JokeDao";

    public JokeDao(Context context) {
        super(context);
    }

    @Override
    public boolean save(List<Joke> data, String tableName) {
        String sql = "insert into " + tableName +" values (?,?,?,?,?,?,?,?)";

        SQLiteDatabase db = null;
        try{
            db = helper.getWritableDatabase();
            for(Joke g : data){
                String[] args = new String[8];
                args[0] = g.getId() + "";
                args[1] = g.getDate() + "";
                args[2] = g.getAuthor();
                args[3] = g.getSource() + "";
                args[4] = g.getContent();
                args[5] = g.getImgurl();
                args[6] = g.getReadingCount() + "";
                args[7] = g.getType() + "";
                db.execSQL(sql, args);
            }
            Log.d(TAG, "向数据库中添加了" + data.size() + "条记录");
            return true;
        }catch(Exception e){
            Log.d(TAG, "save a batch error");
            return false;
        }finally {
            if(db != null){
                db.close();
            }
        }
    }

    @Override
    public List<Joke> getAll(String tableName) {
        String sql = "select * from " + tableName;
        List<Joke> gg = new ArrayList<Joke>();
        Joke g = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = helper.getReadableDatabase();
//            cursor = db.rawQuery(sql, args);
            cursor = db.rawQuery(sql, null);
            while(cursor.moveToNext()){
                g = new Joke();
                g.setId(cursor.getInt(0));
                g.setDate(cursor.getLong(1));
                g.setAuthor(cursor.getString(2));
                g.setSource(cursor.getString(3));
                g.setContent(cursor.getString(4));
                g.setImgurl(cursor.getString(5));
                g.setReadingCount(cursor.getInt(6));
                g.setType(cursor.getInt(7));
                gg.add(g);
            }
            Log.d(TAG, "从数据库中取了" + gg.size()  + "条记录");
            return gg;
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "get a batch data error");
            return null;
        }finally {
            if(cursor != null) {
                cursor.close();
            }
            if(db != null) {
                db.close();
            }
        }
    }


}
