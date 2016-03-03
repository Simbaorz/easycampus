<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.thelionking.datafetch.model.fetcher.FetchUtil" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<body>
	<center>
	<br/><br/><br/><br/><br/>
		<table border=1 bordercolor="gray" style="text-align:center">
			<tr height="60px"> 
				<td width="250px"><font size="6px" color="black" >打开栏</font></td>
				<td width="250px"><font size="6px" color="black" >关闭栏</font></td>
				<td width="250px"><font size="6px" color="black" >状态栏</font></td>
			</tr>
			<tr height="60px"> 
				<td width="250px"><a href="openAll">打开所有抓取器</a></td>
				<td width="250px"><a href="closeAll">关闭所有抓取器</a></td>
				<td width="250px">-------</td>
			</tr>
			
			<tr height="60px">
				<td><a href="open?name=NeiHanDuanZi" >打开内涵段子抓取器</a></td>
				<td><a href="close?name=NeiHanDuanZi">关闭内涵段子抓取器</a></td>
				<%
					if(FetchUtil.threadState.get(FetchUtil.NeiHanDuanZi).equals(FetchUtil.CLOSED_STATE)){
				%>
				<td><font color="red"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.NeiHanDuanZi"/></font></td>
				<%
					}
							else{
				%>
				<td><font color="green"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.NeiHanDuanZi"/></font></td>
				<%
					}
				%>
			</tr>

			<tr height="60px">
				<td><a href="open?name=QiuShiBaiKe">打开糗事百科抓取器</a></td>
				<td><a href="close?name=QiuShiBaiKe">关闭糗事百科抓取器</a></td>
				<%
					if(FetchUtil.threadState.get(FetchUtil.QiuShiBaiKe).equals(FetchUtil.CLOSED_STATE)){
				%>
				<td><font color="red"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.QiuShiBaiKe"/></font></td>
				<%
					}
							else{
				%>
				<td><font color="green"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.QiuShiBaiKe"/></font></td>
				<%
					}
				%>
			</tr>

			<tr height="60px">
				<td><a href="open?name=NeiHanBa">打开内涵吧抓取器</a></td>
				<td><a href="close?name=NeiHanBa">关闭内涵吧抓取器</a></td>
				<%
					if(FetchUtil.threadState.get(FetchUtil.NeiHanBa).equals(FetchUtil.CLOSED_STATE)){
				%>
				<td><font color="red"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.NeiHanBa"/></font></td>
				<%
					}
							else{
				%>
				<td><font color="green"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.NeiHanBa"/></font></td>
				<%
					}
				%>
			</tr>

			<tr height="60px">
				<td><a href="open?name=BaiSiBuDeJie">打开百思不得姐抓取器</a></td>
				<td><a href="close?name=BaiSiBuDeJie">关闭百思不得姐抓取器</a></td>
				<%
					if(FetchUtil.threadState.get(FetchUtil.BaiSiBuDeJie).equals(FetchUtil.CLOSED_STATE)){
				%>
				<td><font color="red"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.BaiSiBuDeJie"/></font></td>
				<%
					}
							else{
				%>
				<td><font color="green"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.BaiSiBuDeJie"/></font></td>
				<%
					}
				%>
			</tr>

			<tr height="60px">
				<td><a href="open?name=BaoZouManHua">打开暴走漫画抓取器</a></td>
				<td><a href="close?name=BaoZouManHua">关闭暴走漫画抓取器</a></td>
				<%
					if(FetchUtil.threadState.get(FetchUtil.BaoZouManHua).equals(FetchUtil.CLOSED_STATE)){
				%>
				<td><font color="red"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.BaoZouManHua"/></font></td>
				<%
					}
							else{
				%>
				<td><font color="green"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.BaoZouManHua"/></font></td>
				<%
					}
				%>
			</tr>

			<tr height="60px">
				<td><a href="open?name=NeiHanSheQu">打开内涵社区抓取器</a></td>
				<td><a href="close?name=NeiHanSheQu">关闭内涵社区抓取器</a></td>
				<%
					if(FetchUtil.threadState.get(FetchUtil.NeiHanSheQu).equals(FetchUtil.CLOSED_STATE)){
				%>
				<td><font color="red"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.NeiHanSheQu"/></font></td>
				<%
					}
					else{
				%>
				<td><font color="green"><s:property value="@com.thelionking.datafetch.model.fetcher.FetchUtil@threadState.NeiHanSheQu"/></font></td>
				<%
					}
				%>
			</tr>

		</table>

	</center>
</body>
</html>
