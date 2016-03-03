package com.thelionking.campus.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thelionking.campus.model.Discuss;
import com.thelionking.campus.util.CommonUtil;

public class DiscussDao extends BaseDao<Discuss>{

	@Override
	public Discuss get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Discuss t) {
		String sql = "insert into discusses values(null,?,?,?,?,?,?,?,?)";
		try {
			this.conn = CommonUtil.connect();
			this.statement = conn.prepareStatement(sql);
			this.statement.setInt(1 , t.getDiscussType());
			this.statement.setString(2, t.getDiscussTitle());
			this.statement.setString(3, t.getDiscussContent());
			this.statement.setString(4, t.getDiscussUrl());
			this.statement.setInt(5 , t.getDiscussUserId());
			this.statement.setString(6 , t.getDiscussUsername());
			this.statement.setInt(7 , t.getRoomId());
			this.statement.setLong(8 , System.currentTimeMillis());
			this.statement.execute();
			return true;
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		} finally {
			this.close();
		}
	}


	@Override
	public boolean insert(List<Discuss> t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Discuss t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Discuss> getBySQL(String sql, String[] args) {
		List<Discuss> discusses = new ArrayList<Discuss>();
		Discuss d = null;
		try {
			this.conn = CommonUtil.connect();
			this.statement = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					this.statement.setString(i + 1, args[0]);
				}
			}
			this.resultSet = this.statement.executeQuery();
			while (resultSet.next()) {
				d = new Discuss();
				d.setDiscussId(resultSet.getInt(1));
				d.setDiscussType(resultSet.getInt(2));
				d.setDiscussTitle(resultSet.getString(3));
				d.setDiscussContent(resultSet.getString(4));
				d.setDiscussUrl(resultSet.getString(5));
				d.setDiscussUserId(resultSet.getInt(6));
				d.setDiscussUsername(resultSet.getString(7));
				d.setRoomId(resultSet.getInt(8));
				d.setDiscussTime(resultSet.getLong(9));
				discusses.add(d);
			}
			return discusses;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.close();
		}
	}

}
