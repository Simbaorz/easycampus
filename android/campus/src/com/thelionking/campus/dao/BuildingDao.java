package com.thelionking.campus.dao;

import java.util.List;

import android.content.Context;

import com.thelionking.campus.model.Building;


public class BuildingDao extends BaseDao<Building>{

	public BuildingDao(Context context) {
		super(context);
	}

	@Override
	public Building get(int id) {
		return null;
	}

	@Override
	public boolean insert(Building t) {
		return false;
	}

	@Override
	public boolean insert(List<Building> t) {
		return false;
	}

	@Override
	public boolean update(Building t) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public List<Building> getBySQL(String sql, String[] args) {
		return null;
	}
}
