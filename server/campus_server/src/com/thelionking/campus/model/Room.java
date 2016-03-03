package com.thelionking.campus.model;

import java.io.Serializable;

/**
 * @author the lion king
 *
 */
public class Room implements Serializable{
	public static final int CAPACITY_0_TO_PERENT_50 = 0;
	public static final int CAPACITY_50_TO_PERENT_100 = 1;
	public static final int CAPACITY_100_TO_PERENT_150 = 2;
	public static final int CAPACITY_150_TO_PERENT_250 = 3;
	public static final int MORE_THAN_250 = 4;
	
	private int roomId;
	
	private String roomName;
	
	private String roomLocation;
	
	//5个等级中选择一个
	private int roomCapacity;
	
	//不超过100不少于0
	private int roomUseRate;
	
	private int buildingId;
	
	public Room() {
		
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomLocation() {
		return roomLocation;
	}

	public void setRoomLocation(String roomLocation) {
		this.roomLocation = roomLocation;
	}

	public int getRoomCapacity() {
		return roomCapacity;
	}

	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public int getRoomUseRate() {
		return roomUseRate;
	}

	public void setRoomUseRate(int roomUseRate) {
		this.roomUseRate = roomUseRate;
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomName=" + roomName
				+ ", roomLocation=" + roomLocation + ", roomCapacity="
				+ roomCapacity + ", roomUseRate=" + roomUseRate + ", buildingId="
				+ buildingId + "]";
	}
	
}
