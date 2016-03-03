package com.thelionking.campus.model;

public class School {
	private int schoolId;
	private String schoolName;
	private String schoolLocation;
	private String schoolDescribe;
	private int provinceId;
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolLocation() {
		return schoolLocation;
	}
	public void setSchoolLocation(String schoolLocation) {
		this.schoolLocation = schoolLocation;
	}
	public String getSchoolDescribe() {
		return schoolDescribe;
	}
	public void setSchoolDescribe(String schoolDescribe) {
		this.schoolDescribe = schoolDescribe;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	@Override
	public String toString() {
		return "School [schoolId=" + schoolId + ", schoolName=" + schoolName
				+ ", schoolLocation=" + schoolLocation + ", schoolDescribe="
				+ schoolDescribe + ", provinceId=" + provinceId + "]";
	}

	

}
