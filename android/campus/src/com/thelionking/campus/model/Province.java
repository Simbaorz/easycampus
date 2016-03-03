package com.thelionking.campus.model;

public class Province {
	private int provinceId;
	private String provinceName;
	
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	@Override
	public String toString() {
		return provinceName;
	}
}
