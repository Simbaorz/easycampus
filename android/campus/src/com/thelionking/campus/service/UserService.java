package com.thelionking.campus.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract.CommonDataKinds;

import com.thelionking.campus.dao.ProvinceDao;
import com.thelionking.campus.model.Building;
import com.thelionking.campus.model.Province;
import com.thelionking.campus.model.Room;
import com.thelionking.campus.model.User;
import com.thelionking.campus.util.Constant;
import com.thelionking.campus.util.Parser;


public class UserService {
	private Context context;
	
	public UserService(Context context){
		this.context = context;
	}
	
	public List<Province> getAllProvinces() {
		String sql = "select * from provinces";
		String[] args = null;
		ProvinceDao pd = new ProvinceDao(context);
		return pd.getBySQL(sql, args);
	}
	
	public List<Building> getBuildingsBySchoolId(int schoolId) {
		return null;
	}
	
	public List<Room> getRoomsByBuildingId(int buildingId) {
		return null;
	}
	
}
