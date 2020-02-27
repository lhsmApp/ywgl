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
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<!-- 树形下拉框start -->
	<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
	<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
	<link rel="stylesheet" type="text/css" href="plugins/selectZtree/import_fh.css"/>
	<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
	<link type="text/css" rel="stylesheet" href="plugins/selectZtree/ztree/ztree.css"></link>
	<!-- 树形下拉框end -->
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">申请单号:</td>
								<td><input type="text" name="BILL_CODE" id="BILL_CODE" value="${pd.BILL_CODE}" maxlength="100" title="申请单号" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">单位简称:</td>
								<td><input type="text" name="UNIT_CODE" id="UNIT_CODE" value="${pd.UNIT_NAME}" maxlength="30" title="单位简称" style="width:98%;"/></td>		 
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">部门简称:</td>
								<td><input type="text" name="DEPT_CODE" id="DEPT_CODE" value="${pd.DEPT_NAME}" maxlength="30" title="部门简称" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">填表日期:</td>
								<td><input type="text" name="ENTRY_DATE" id="ENTRY_DATE" value="${pd.ENTRY_DATE}" maxlength="30" title="填表日期" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">序号:</td>
								<td><input type="text" name="SERIAL_NUM" id="SERIAL_NUM" value="${pd.SERIAL_NUM}" maxlength="20"  title="序号" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">申请人:</td>
								<td><input type="text" name="USER_CODE" id="USER_CODE" value="${pd.USERNAME}" maxlength="20"  title="申请人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">申请人部门:</td>
								<td><input type="text" name="USER_DEPT" id="USER_DEPT" value="${pd.USER_DEPTNAME}" maxlength="30"  title="申请人部门" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">申请人岗位:</td>
								<td><input type="text" name="USER_JOB" id="USER_JOB" value="${pd.USER_JOB}" maxlength="50"  title="申请人岗位" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">联系方式:</td>
								<td><input type="text" name="USER_CONTACT" id="USER_CONTACT" value="${pd.USER_CONTACT}" maxlength="50"  title="联系方式" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">变更名称:</td>
								<td><input type="text" name="BG_NAME" id="BG_NAME" value="${pd.BG_NAME}" maxlength="50" title="变更名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">ERP系统名称:</td>
								<td><input type="text" name="SYSTEM" id="SYSTEM" value="${pd.SYSTEM}" maxlength="20"  title="ERP系统名称" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">变更类型:</td>
								<td><input type="text" name="BG_TYPE" id="BG_TYPE" value="${pd.BG_TYPE}" maxlength="100" title="变更类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">变更原因:</td>
								<td><input type="text" name="BG_REASON" id="BG_REASON" value="${pd.BG_REASON}" maxlength="300"  title="变更原因" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">单据状态:</td>
								<td><input type="text" name="BILL_STATE" id="BILL_STATE" value="${pd.BILL_STATE}" maxlength="10"  title="单据状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">创建人:</td>
								<td><input type="text" name="BILL_USER" id="BILL_USER" value="${pd.BILL_USER}" maxlength="20"  title="创建人" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">创建日期:</td>
								<td><input type="text" name="BILL_DATE" id="BILL_DATE" value="${pd.BILL_DATE}" maxlength="30" title="创建日期" style="width:98%;"/></td>
							</tr>

						</table>
						</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
	
		</script>
</body>
</html>