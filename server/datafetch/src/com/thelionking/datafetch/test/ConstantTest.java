package com.thelionking.datafetch.test;

import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

public class ConstantTest {
	public static void main(String[] args) {
		if(Constant.DEBUG){
			CommonUtil.print(Constant.LOCAL + "");
			CommonUtil.print(Constant.DEBUG + "");
			CommonUtil.print(Constant.FETCH_INTERVAL + "");
			
			CommonUtil.print(Constant.DB_NAME);
			CommonUtil.print(Constant.USER);
			CommonUtil.print(Constant.PASSWORD);
			CommonUtil.print(Constant.PORT);
			CommonUtil.print(Constant.HOST);
		}
	}
}
