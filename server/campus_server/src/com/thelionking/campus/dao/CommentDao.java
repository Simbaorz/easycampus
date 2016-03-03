package com.thelionking.campus.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thelionking.campus.model.Comment;
import com.thelionking.campus.util.CommonUtil;

public class CommentDao extends BaseDao<Comment>{

	@Override
	public Comment get(int id) {
		return null;
	}

	@Override
	public boolean insert(Comment t) {
		String sql = "insert into comments values(null,?,?,?,?,?)";
		try {
			this.conn = CommonUtil.connect();
			this.statement = conn.prepareStatement(sql);
			this.statement.setString(1 , t.getCommentUrl());
			this.statement.setString(2, t.getCommentUsername());
			this.statement.setInt(3, t.getCommentUserId());
			this.statement.setString(4, t.getCommentContent());
			this.statement.setInt(5 , t.getDiscussId());
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
	public boolean insert(List<Comment> t) {
		return false;
	}

	@Override
	public boolean update(Comment t) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public List<Comment> getBySQL(String sql, String[] args) {
		List<Comment> comments = new ArrayList<Comment>();
		Comment c = null;
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
				c = new Comment();
				c.setCommentId(resultSet.getInt(1));
				c.setCommentUrl(resultSet.getString(2));
				c.setCommentUsername(resultSet.getString(3));
				c.setCommentUserId(resultSet.getInt(4));
				c.setCommentContent(resultSet.getString(5));
				c.setDiscussId(resultSet.getInt(6));
				comments.add(c);
			}
			return comments;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.close();
		}
	}

}
