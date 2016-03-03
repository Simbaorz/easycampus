package com.thelionking.datafetch.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thelionking.datafetch.model.Joke;
import com.thelionking.datafetch.model.Source;
import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;


public class JokeDao extends BaseDao<Joke>{
	public static final String JOKES_TEXT = "jokes_text";
	public static final String JOKES_PICTURE = "jokes_picture";
	public static final String JOKES_VEDIO = "jokes_vedio";
	
	public static final int FROM_TOP = 0;
	
	public static final int FROM_BOTTOM = 1;
	
	@Override
	public boolean insert(Joke joke, String table) {
		return false;
	}

	@Override
	public boolean insert(List<Joke> jokes,String table) {
		
		String sql = "insert into " + table +" values (null, ?,?,?,?,?,?)";
		try {
			this.conn = CommonUtil.connect();
			this.statement = conn.prepareStatement(sql);
			for(Joke j : jokes) {
				this.statement.setLong(1, j.getDate());
				this.statement.setString(2, j.getAuthor());
				this.statement.setInt(3, j.getSource().ordinal());
				this.statement.setString(4, j.getContent());
				this.statement.setString(5, j.getImgurl());
				this.statement.setInt(6, j.getReadingCount());
//				this.statement.setInt(7, j.getType());
				this.statement.execute();
			}
			if(Constant.DEBUG) {
				CommonUtil.print("数据库" + table + "表中添加了 " + jokes.size() + " 项数据");
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			this.close();
		}
	}
	
	public boolean deleteAll() {
		String sql = "delete from jokes";
		try {
			this.conn = CommonUtil.connect();
			this.statement = conn.prepareStatement(sql);
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
	public Joke get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Joke t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Joke> getBySQL(String sql, String[] args) {
		List<Joke> jokes = new ArrayList<Joke>();
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
				Joke j = new Joke();
				j.setId(resultSet.getLong(1));
				j.setDate(resultSet.getLong(2));
				
				j.setAuthor(resultSet.getString(3));
				j.setSource(Source.values()[resultSet.getInt(4)]);
				j.setContent(resultSet.getString(5));
				j.setImgurl(resultSet.getString(6));
				j.setReadingCount(resultSet.getInt(7));
				jokes.add(j);
			}
			return jokes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.close();
		}
	}
	
}
