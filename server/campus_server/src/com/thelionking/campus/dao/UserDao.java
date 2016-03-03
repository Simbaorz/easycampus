package com.thelionking.campus.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thelionking.campus.model.Province;
import com.thelionking.campus.model.User;
import com.thelionking.campus.util.CommonUtil;

public class UserDao extends BaseDao<User>{

	@Override
	public User get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(User t) {
		String sql = "insert into users values(null,?,?,?,?,?,?,?)";
		try {
			this.conn = CommonUtil.connect();
			this.statement = conn.prepareStatement(sql);
			
			this.statement.setString(1 , t.getUserName());
			this.statement.setInt(2, 20);
			this.statement.setInt(3, 1);
			this.statement.setString(4, "default");
			this.statement.setString(5 , "null");
			this.statement.setString(6 , t.getUserName());
			this.statement.setString(7 , t.getUserPassword());
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
	public boolean insert(List<User> t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getBySQL(String sql, String[] args) {
		List<User> users = new ArrayList<User>();
		User u = null;
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
				u = new User();
				u.setUserId(resultSet.getInt(1));
				u.setUserName(resultSet.getString(2));
				u.setUserScore(resultSet.getInt(3));
				u.setUserGrade(resultSet.getInt(4));
				u.setUserImg(resultSet.getString(5));
				u.setUserSign(resultSet.getString(6));
				u.setUserNickname(resultSet.getString(7));
				u.setUserPassword(resultSet.getString(8));
				users.add(u);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.close();
		}
	}

	public boolean updateScore(int userId, int score) {
		String sql = "update users set user_score=? where user_id=? ";
		int base = this.getScore(userId);
		if(base == -1){
			return false;
		}
		score += base;
		try {
			this.conn = CommonUtil.connect();
			this.statement = conn.prepareStatement(sql);
			this.statement.setInt(1, score);
			this.statement.setInt(2, userId);
			this.statement.execute();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			this.close();
		}
	}
	
	private int getScore(int userId){
		int score = -1;
		String sql = "select user_score from users where user_id=? ";
		try {
			this.conn = CommonUtil.connect();
			this.statement = conn.prepareStatement(sql);
			this.statement.setInt(1, userId);
			this.resultSet = this.statement.executeQuery();
			while (resultSet.next()) {
				score = resultSet.getInt(1);
			}
			return score;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			this.close();
		}
	}

}
