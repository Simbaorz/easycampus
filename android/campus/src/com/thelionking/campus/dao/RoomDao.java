package com.thelionking.campus.dao;

import java.util.List;

import android.content.Context;

import com.thelionking.campus.model.Room;


public class RoomDao extends BaseDao<Room>{

	public RoomDao(Context context) {
		super(context);
	}

	@Override
	public Room get(int id) {
		return null;
	}

	@Override
	public boolean insert(Room t) {
		return false;
	}

	@Override
	public boolean insert(List<Room> t) {
		return false;
	}

	@Override
	public boolean update(Room t) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public List<Room> getBySQL(String sql, String[] args) {
		return null;
	}
}
