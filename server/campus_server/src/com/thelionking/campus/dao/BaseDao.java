package com.thelionking.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public abstract class BaseDao<T> {
	
	protected Connection conn;
	protected PreparedStatement statement;
	protected ResultSet resultSet;

	public abstract T get(int id);

	public abstract boolean insert(T t);
	
	public abstract boolean insert(List<T> t);

	public abstract boolean update(T t);

	public abstract boolean delete(int id);

	public abstract List<T> getBySQL(String sql, String[] args);
	
	protected void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
