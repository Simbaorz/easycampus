package com.thelionking.campus.model;

import java.util.List;

/**
 * @author the lion king
 * 
 */
public class Building {
    
    private int buildingId;
    
    private String buildingName;
   
    private String buildingLocation;
 
    private String buildingUrl;

    private List<Room> rooms;
    
    private int schoolId;
    
    public Building() {

    }

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getBuildingLocation() {
		return buildingLocation;
	}

	public void setBuildingLocation(String buildingLocation) {
		this.buildingLocation = buildingLocation;
	}

	public String getBuildingUrl() {
		return buildingUrl;
	}

	public void setBuildingUrl(String buildingUrl) {
		this.buildingUrl = buildingUrl;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	@Override
	public String toString() {
		return "Building [buildingId=" + buildingId + ", buildingName="
				+ buildingName + ", buildingLocation=" + buildingLocation
				+ ", buildingUrl=" + buildingUrl + ", rooms=" + rooms
				+ ", schoolId=" + schoolId + "]";
	}

    
}
