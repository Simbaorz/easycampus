package com.thelionking.campus.dao;

import java.util.List;

import android.content.Context;

public abstract class BaseDao<T> {
	protected Context context;
	
	public BaseDao(Context context) {
		this.context = context;
	}
	
	public abstract T get(int id);

	public abstract boolean insert(T t);
	
	public abstract boolean insert(List<T> t);

	public abstract boolean update(T t);

	public abstract boolean delete(int id);

	public abstract List<T> getBySQL(String sql, String[] args);

}
