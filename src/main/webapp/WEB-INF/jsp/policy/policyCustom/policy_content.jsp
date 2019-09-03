<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
</head>
<body class="no-skin" bgcolor="white">
	<!-- #section:custom/widget-box -->
	<div class="widget-box" style="margin: 0px 0px;">
		<div class="widget-header">
			<h5 class="widget-title">${pd.TITLE}</h5>

			<!-- #section:custom/widget-box.toolbar -->
			<div class="widget-toolbar">
				<a href="JavaScript:history.back(-1)" class="btn-back-message-list">
					<i class="ace-icon fa fa-arrow-left blue bigger-110 middle"></i> <b
					class="bigger-110 middle">返回</b>
				</a>
			</div>

			<!-- /section:custom/widget-box.toolbar -->
		</div>
		<div class="widget-body">
			<div class="widget-main">
				<div>${pd.TITL_CONT}</div>
			</div>
		</div>
	</div>
	<!-- /section:custom/widget-box -->
</body>
</html>