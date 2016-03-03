package com.thelionking.campus.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thelionking.campus.model.Province;
import com.thelionking.campus.model.Room;
import com.thelionking.campus.util.CommonUtil;


public class RoomDao extends BaseDao<Room>{

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
		List<Room> rooms = new ArrayList<Room>();
		Room r = null;
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
				r = new Room();
				r.setRoomId(resultSet.getInt(1));
				r.setRoomName(resultSet.getString(2));
				r.setRoomLocation(resultSet.getString(3));
				r.setRoomCapacity(resultSet.getInt(4));
				r.setRoomUseRate(resultSet.getInt(5));
				r.setBuildingId(resultSet.getInt(6));
				rooms.add(r);
			}
			return rooms;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.close();
		}
	}
}
