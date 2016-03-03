package com.thelionking.campus.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thelionking.campus.model.Province;
import com.thelionking.campus.util.DBHelper;


public class ProvinceDao extends BaseDao<Province>{

	public ProvinceDao(Context context) {
		super(context);
	}

	@Override
	public Province get(int id) {
		return null;
	}

	@Override
	public boolean insert(Province t) {
		return false;
	}

	@Override
	public boolean insert(List<Province> t) {
		DBHelper helper = new DBHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "insert into provinces values (?, ?)";
		String[] args = new String[2];
		for(Province p : t){
			args[0] = p.getProvinceId() + "";
			args[1] = p.getProvinceName();
			db.execSQL(sql, args);
		}
		helper.close();
		return true;
	}

	@Override
	public boolean update(Province t) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public List<Province> getBySQL(String sql, String[] args) {
		List<Province> provinces = new ArrayList<Province>();
		Province province = null;
		DBHelper helper = new DBHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, args);
		while(cursor.moveToNext()){
			province = new Province();
			province.setProvinceId(cursor.getInt(1));
			province.setProvinceName(cursor.getString(2));
			provinces.add(province);
		}
		cursor.close();
		helper.close();
		return provinces;
	}
}
