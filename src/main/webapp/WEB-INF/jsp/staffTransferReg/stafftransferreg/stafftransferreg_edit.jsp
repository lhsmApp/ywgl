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
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="stafftransferreg/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="STAFFTRANSFERREG_ID" id="STAFFTRANSFERREG_ID" value="${pd.STAFFTRANSFERREG_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">员工编号:</td>
								<td><input type="text" name="USER_CODE" id="USER_CODE" value="${pd.USER_CODE}" maxlength="30" placeholder="这里输入员工编号" title="员工编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">员工名称:</td>
								<td><input type="text" name="USER_NAME" id="USER_NAME" value="${pd.USER_NAME}" maxlength="20" placeholder="这里输入员工名称" title="员工名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">身份证号:</td>
								<td><input type="text" name="STAFF_IDENT" id="STAFF_IDENT" value="${pd.STAFF_IDENT}" maxlength="18" placeholder="这里输入身份证号" title="身份证号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">责任中心:</td>
								<td><input type="text" name="DEPT_CODE" id="DEPT_CODE" value="${pd.DEPT_CODE}" maxlength="30" placeholder="这里输入责任中心" title="责任中心" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属二级单位:</td>
								<td><input type="text" name="UNITS_CODE" id="UNITS_CODE" value="${pd.UNITS_CODE}" maxlength="30" placeholder="这里输入所属二级单位" title="所属二级单位" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
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
		function save(){
			if($("#USER_CODE").val()==""){
				$("#USER_CODE").tips({
					side:3,
		            msg:'请输入员工编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_CODE").focus();
			return false;
			}
			if($("#USER_NAME").val()==""){
				$("#USER_NAME").tips({
					side:3,
		            msg:'请输入员工名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_NAME").focus();
			return false;
			}
			if($("#STAFF_IDENT").val()==""){
				$("#STAFF_IDENT").tips({
					side:3,
		            msg:'请输入身份证号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_IDENT").focus();
			return false;
			}
			if($("#DEPT_CODE").val()==""){
				$("#DEPT_CODE").tips({
					side:3,
		            msg:'请输入责任中心',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPT_CODE").focus();
			return false;
			}
			if($("#UNITS_CODE").val()==""){
				$("#UNITS_CODE").tips({
					side:3,
		            msg:'请输入所属二级单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UNITS_CODE").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>