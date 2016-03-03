package com.thelionking.campus.dao;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thelionking.campus.model.User;
import com.thelionking.campus.util.DBHelper;

public class UserDao {
	private Context context;
	
	public UserDao(Context context) {
		this.context = context;
	}
	
	public void save(final User user){

	}
	
	public User getDefaultUser(){
		String sql = "select * from users limit 0,1";
		User user = null;
		DBHelper helper = new DBHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			user = new User();
			user.setUserId(cursor.getInt(1));
			user.setUserName(cursor.getString(2));
			user.setUserPassword(cursor.getString(3));
		}
		cursor.close();
		helper.close();
		return user;
	}
	
	public boolean insert(User user){
		DBHelper helper = new DBHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "insert into user values (?, ?, ?)";
		String[] args = new String[3];
		args[0] = user.getUserId() + "";
		args[1] = user.getUserName();
		args[2] = user.getUserPassword();
		db.execSQL(sql, args);
		helper.close();
		return true;
	}
	
	public boolean isExist(int userId){
		return false;
	}
	
	public List<User> getAllUser(){
		return null;
	}

	public void update(User u) {
	}
}
