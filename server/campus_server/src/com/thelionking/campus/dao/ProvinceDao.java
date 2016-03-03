package com.thelionking.campus.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thelionking.campus.model.Province;
import com.thelionking.campus.util.CommonUtil;


public class ProvinceDao extends BaseDao<Province>{

	@Override
	public Province get(int id) {
		return null;
	}

	@Override
	public boolean insert(Province t) {
		return false;
	}

	@Override
	public boolean insert(List<Province> t) {
		return false;
	}

	@Override
	public boolean update(Province t) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public List<Province> getBySQL(String sql, String[] args) {
		List<Province> provinces = new ArrayList<Province>();
		Province p = null;
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
				p = new Province();
				p.setProvinceId(resultSet.getInt(1));
				p.setProvinceName(resultSet.getString(2));
				provinces.add(p);
			}
			return provinces;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.close();
		}
	}
}
