package com.thelionking.campus.dao;

import java.util.List;

import android.content.Context;

import com.thelionking.campus.model.School;

public class SchoolDao extends BaseDao<School>{

	public SchoolDao(Context context) {
		super(context);
	}

	@Override
	public School get(int id) {
		return null;
	}

	@Override
	public boolean insert(School t) {
		return false;
	}

	@Override
	public boolean insert(List<School> t) {
		return false;
	}

	@Override
	public boolean update(School t) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public List<School> getBySQL(String sql, String[] args) {
		return null;
	}

}
