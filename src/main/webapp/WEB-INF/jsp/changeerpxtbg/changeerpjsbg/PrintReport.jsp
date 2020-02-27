<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
	<div style="width:800;height:600;">	
		<object classid="clsid:ABB64AAC-D7E8-4733-B052-1B141C92F3CE" width="100%" height="97%">
			<param name="ReportURL" value='<%=basePath%>${ReportURL}' />
			<param name="DataURL" value='<%=basePath%>${DataURL}' />
			<param name="AutoRun" value="true" />
			<param name="SerialNo" value="GA1F6NS5D6CPYN6FD1G6061B8EFLI5KI0L4Y1233TR5C74WND6898W9DJRJ9Y0AR69VTS4FNJN8L2SD5J9GK3AVET4TGTG4CWFZ4V9E98AWRM5SW4F817198A3UA5Y4TZ9EBIN44QNM56BIA988BR4" />
			<param name="UserName" value="广州锐浪软件技术有限公司" />
			<param name="wmode" value="transparent">
			<div>请<a href="static/js/gridReport/Grid++Report6.5.exe"><span style="color:#0066FF;text-decoration:underline">点击</span></a>安装插件！</div>
		</object>
	</div>

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script src="static/js/gridReport/CreateControl.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(top.hangge());
<%-- 		var Report='<%=basePath%>${report}'; --%>
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});

		</script>
</body>
</html>
