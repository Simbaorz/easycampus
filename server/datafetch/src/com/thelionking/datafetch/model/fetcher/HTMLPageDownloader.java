package com.thelionking.datafetch.model.fetcher;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

/**
 * HTML下载器,处于使用频率的考虑不使用单例，作为无状态工具类使用
 * 
 * @author thelionking
 * 
 */
public final class HTMLPageDownloader {

	public final static String UTF_8 = "UTF-8";
	public final static String GB2312 = "GB2312";
	public final static String GBK = "GBK";

	/**
	 * 下载HTML，直接返回字符串,暂时都是用get模式提交，以后有需求再修改
	 * 
	 * @param url
	 * @param show
	 * @return
	 * 
	 */
	public static String download(String url, boolean show, String encoding) {
		
		HttpClient hc = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		// 伪装成firefox浏览器
		getMethod.addRequestHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 2.0.50727");
		
		CommonUtil.print(url);
		try {
			int statusCode = hc.executeMethod(getMethod);

			// 遇到300， 301， 302错误时
			if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
					|| (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				Header header = getMethod.getResponseHeader("location");
				if (header != null) {
					String newurl = header.getValue();
					if (newurl != null && !newurl.equals("")) {
						// 递归
						return download(newurl, show, encoding);
						
					}
				}
			}
			// 正常情况200
			else if (statusCode == HttpStatus.SC_OK) {
				
				String result = new String(getMethod.getResponseBody(), encoding);
				if (show) {
					CommonUtil.print(result);
					CommonUtil.print(result.length() + "");
				}
				return result;
			}

		} catch (HttpException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
				if(Constant.DEBUG) {
					CommonUtil.print("GetMethod connection release!");
				}
			}
		}
		return null;
	}
	
}
