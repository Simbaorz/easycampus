package com.thelionking.campus.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thelionking.campus.model.Building;
import com.thelionking.campus.model.Province;
import com.thelionking.campus.util.CommonUtil;


public class BuildingDao extends BaseDao<Building>{

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
		List<Building> buildings = new ArrayList<Building>();
		Building b = null;
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
				b = new Building();
				b.setBuildingId(resultSet.getInt(1));
				b.setBuildingName(resultSet.getString(2));
				b.setBuildingLocation(resultSet.getString(3));
				b.setBuildingUrl(resultSet.getString(4));
				b.setSchoolId(resultSet.getInt(5));
				buildings.add(b);
			}
			return buildings;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.close();
		}
	}
}
