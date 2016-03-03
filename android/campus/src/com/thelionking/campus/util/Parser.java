package com.thelionking.campus.util;

import android.util.*;
import android.util.Log;

import com.thelionking.campus.model.Building;
import com.thelionking.campus.model.Province;
import com.thelionking.campus.model.Room;
import com.thelionking.campus.model.School;
import com.thelionking.campus.model.User;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author the lion king
 */
public final class Parser {
	public static final String TAG = "Parser";

	private Parser() {
	}

	/**
	 * 解析Building.xml
	 * 
	 * @param in
	 * @return
	 */
	public static List<Building> parseBuilding(InputStream in) {
		List<Building> buildings = null;
		Building building = null;
		List<Room> rooms = null;
		Room room = null;
		int schoolId = -1;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in, "utf-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT: {
					buildings = new ArrayList<Building>();
					break;
				}
				case XmlPullParser.START_TAG: {
					if (parser.getName().equals("schoolId")) {
						schoolId = Integer.parseInt(parser.nextText());
					} else if (parser.getName().equals("building")) {
						building = new Building();
						building.setSchoolId(schoolId);
					} else if (parser.getName().equals("buildingId")) {
						building.setBuildingId(Integer.parseInt(parser
								.nextText()));
					} else if (parser.getName().equals("buildingName")) {
						building.setBuildingName(parser.nextText());
					} else if (parser.getName().equals("buildingLocation")) {
						building.setBuildingLocation(parser.nextText());
					} else if (parser.getName().equals("buildingUrl")) {
						building.setBuildingUrl(parser.nextText());
					} else if (parser.getName().equals("rooms")) {
						rooms = new ArrayList<Room>();
					} else if (parser.getName().equals("room")) {
						room = new Room();
						room.setBuildingId(building.getBuildingId());
					} else if (parser.getName().equals("roomId")) {
						room.setRoomId(Integer.parseInt(parser.nextText()));
					} else if (parser.getName().equals("roomName")) {
						room.setRoomName(parser.nextText());
					} else if (parser.getName().equals("roomLocation")) {
						room.setRoomLocation(parser.nextText());
					} else if (parser.getName().equals("roomCapacity")) {
						room.setRoomCapacity(Integer.parseInt(parser.nextText()));
					} else if (parser.getName().equals("roomUseRate")) {
						room.setRoomUseRate(Integer.parseInt(parser.nextText()));
					} else {

					}
					break;
				}
				case XmlPullParser.END_TAG: {

					if (parser.getName().equals("room")) {
						rooms.add(room);
					} else if (parser.getName().equals("rooms")) {
						building.setRooms(rooms);
					} else if (parser.getName().equals("building")) {
						buildings.add(building);
					}
					break;
				}
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {

		} catch (IOException e) {

		}
		for (Building b : buildings) {
			Log.d(TAG, b.toString());
		}
		return buildings;
	}

	/**
	 * 解析rooms.xml
	 * 
	 * @param in
	 * @return
	 */
	public static List<Room> parseRooms(InputStream in) {
		List<Room> rooms = null;
		Room room = null;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in, "utf-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT: {
					rooms = new ArrayList<Room>();
					break;
				}
				case XmlPullParser.START_TAG: {
					if (parser.getName().equals("room")) {
						room = new Room();
					} else if (parser.getName().equals("roomId")) {
						room.setRoomId(Integer.parseInt(parser.nextText()));
					} else if (parser.getName().equals("roomName")) {
						room.setRoomName(parser.nextText());
					} else if (parser.getName().equals("roomLocation")) {
						room.setRoomLocation(parser.nextText());
					} else if (parser.getName().equals("roomCapacity")) {
						room.setRoomCapacity(Integer.parseInt(parser.nextText()));
					} else if (parser.getName().equals("roomree")) {
						room.setRoomUseRate(Integer.parseInt(parser.nextText()));
					} else {
					}
					break;
				}
				case XmlPullParser.END_TAG: {
					if (parser.getName().equals("room")) {
						rooms.add(room);
					}
					break;
				}
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {

		} catch (IOException e) {

		}
		return rooms;
	}

	/**
	 * 解析provinces.xml
	 * 
	 * @param in
	 * @return
	 */
	public static List<Province> parseProvince(InputStream in) {
		List<Province> provinces = null;
		Province province = null;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in, "utf-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT: {
					provinces = new ArrayList<Province>();
					break;
				}
				case XmlPullParser.START_TAG: {
					if (parser.getName().equals("province")) {
						province = new Province();
					} else if (parser.getName().equals("provinceId")) {
						province.setProvinceId(Integer.parseInt(parser
								.nextText()));
					} else if (parser.getName().equals("provinceName")) {
						province.setProvinceName(parser.nextText());
					} else {
					}
					break;
				}
				case XmlPullParser.END_TAG: {
					if (parser.getName().equals("province")) {
						provinces.add(province);
					}
					break;
				}
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {

		} catch (IOException e) {

		}
		return provinces;
	}

	public static User parserUser(InputStream is) {
		User user = null;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT: {
					break;
				}
				case XmlPullParser.START_TAG: {
					if (parser.getName().equals("user")) {
						user = new User();
					} else if (parser.getName().equals("userId")) {
						user.setUserId(Integer.parseInt(parser.nextText()
								.trim()));
					} else if (parser.getName().equals("userName")) {
						user.setUserName(parser.nextText());
					} else if (parser.getName().equals("userScore")) {
						user.setUserScore(Integer.parseInt(parser.nextText()
								.trim()));
					} else if (parser.getName().equals("userGrade")) {
						user.setUserGrade(Integer.parseInt(parser.nextText()
								.trim()));
					} else if (parser.getName().equals("userImg")) {
						user.setUserImg(parser.nextText());
					} else if (parser.getName().equals("userSign")) {
						user.setUserSign(parser.nextText());
					} else if (parser.getName().equals("userNickname")) {
						user.setUserNickname(parser.nextText());
					} else if (parser.getName().equals("userPassword")) {
						user.setUserPassword(parser.nextText());
					} else if (parser.getName().equals("error")) {
						return null;
					}
					break;
				}
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static boolean parseResult(InputStream is) {
		int status = -1;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT: {
					break;
				}
				case XmlPullParser.START_TAG: {
					if (parser.getName().equals("status")) {
						status = Integer.parseInt(parser.nextText());
						break;
					}
				}	
				}
				eventType = parser.next();
			}
			return (status == Result.RIGHT_CODE);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Result<User> parseRegister(InputStream is) {
		Result<User> result = new Result<User>();
		result.status = Result.ERROR_CODE;
		List<User> users = new ArrayList<User>();
		User user = null;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT: {
					break;
				}
				case XmlPullParser.START_TAG: {
					if (parser.getName().equals("status")) {
						result.status = Integer.parseInt(parser.nextText());
						if(result.status == Result.ERROR_CODE_REPEAT_USERNAME || result.status == Result.ERROR_CODE){
							return result;
						}
						break;
					}else if (parser.getName().equals("user")) {
						user = new User();
					} else if (parser.getName().equals("userId")) {
						user.setUserId(Integer.parseInt(parser.nextText().trim()));
					} else if (parser.getName().equals("userName")) {
						user.setUserName(parser.nextText());
					} else if (parser.getName().equals("userScore")) {
						user.setUserScore(Integer.parseInt(parser.nextText()
								.trim()));
					} else if (parser.getName().equals("userGrade")) {
						user.setUserGrade(Integer.parseInt(parser.nextText()
								.trim()));
					} else if (parser.getName().equals("userImg")) {
						user.setUserImg(parser.nextText());
					} else if (parser.getName().equals("userSign")) {
						user.setUserSign(parser.nextText());
					} else if (parser.getName().equals("userNickname")) {
						user.setUserNickname(parser.nextText());
					} else if (parser.getName().equals("userPassword")) {
						user.setUserPassword(parser.nextText());
					} 
					break;
				}	
				}
				eventType = parser.next();
			}
			users.add(user);
			result.content = users;
			return result;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			result.status = Result.ERROR_CODE;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result.status = Result.ERROR_CODE;
			return result;
		}
	}

	/**
	 * 返回的数据格式
	 * <result>
	 * 	<status></status>
	 * 	<content></content>
	 * </result>
	 * @param <T>
	 */
	public static final class Result<T>{
		public static final int ERROR_CODE_ACCOUNT_NOT_EXISTS = 2;
		public static final int ERROR_CODE_PASSWORD_ERROR = 3;
		public static final int ERROR_CODE_ADD_SCORE_ERROR = 4;
		
		public static final int ERROR_CODE_REPEAT_USERNAME = 5;
		public static final int ERROR_CODE = 1;
		public static final int RIGHT_CODE = 0;
		public int status = RIGHT_CODE;
		public List<T> content;	
		
		public Result(){
			this.content = null;
			this.status = RIGHT_CODE;
		}
	}

	public static List<School> parseSchools(InputStream in) {
		List<School> schools = null;
		School school = null;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in, "utf-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT: {
					schools = new ArrayList<School>();
					break;
				}
				case XmlPullParser.START_TAG: {
					if (parser.getName().equals("school")) {
						school = new School();
					} else if (parser.getName().equals("schoolId")) {
						school.setSchoolId(Integer.parseInt(parser.nextText()));
					} else if (parser.getName().equals("schoolName")) {
						school.setSchoolName(parser.nextText());
					} else if (parser.getName().equals("schoolLocation")) {
						school.setSchoolLocation(parser.nextText());
					} else if (parser.getName().equals("schoolDescribe")) {
						school.setSchoolDescribe(parser.nextText());
					} else if (parser.getName().equals("provinceId")) {
						school.setProvinceId(Integer.parseInt(parser.nextText()));
					} else {
					}
					break;
				}
				case XmlPullParser.END_TAG: {
					if (parser.getName().equals("school")) {
						schools.add(school);
					}
					break;
				}
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return schools;
	}

}
