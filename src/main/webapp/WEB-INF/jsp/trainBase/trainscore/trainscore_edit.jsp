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
					
					<form action="trainscore/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="UNIT_CODE" id="UNIT_CODE" value="${pd.UNIT_CODE}"/>
						<input type="hidden" name="COURSE_TYPE_PARENT_ID" id="COURSE_TYPE_PARENT_ID" value="${null == pd.COURSE_TYPE_PARENT_ID ? pd.COURSETYPE_ID:pd.COURSE_TYPE_PARENT_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">			
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">考试时间:</td>
								<td><input type="text" name="TEST_DATE" id="TEST_DATE" value="${pd.TEST_DATE}"  placeholder="这里选择考试时间" title="考试时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">考试名称:</td>
								<td><input type="text" name="TEST_NAME" id="TEST_NAME" value="${pd.TEST_NAME}" maxlength="100" placeholder="这里输入考试名称" title="考试名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">员工编号:</td>
								<td><input type="text" name="USERNAME" id="USERNAME" value="${pd.USERNAME}" maxlength="100" placeholder="这里输入员工编号" title="员工编号" style="width:98%;"/></td>
	
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">考生姓名:</td>
								<td><input type="text" name="NAME1" id="NAME1" value="${pd.NAME1}" maxlength="100" placeholder="这里输入考生姓名" title="考生姓名" style="width:98%;"/></td>
	
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">考生单位:</td>
								<td><input type="text" name="UNIT_CODE" id="UNIT_CODE" value="${pd.UNIT_CODE}" maxlength="100" placeholder="这里输入考生单位" title="考生单位" style="width:98%;"/></td>
	
							</tr>
														<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">业务模块:</td>
								<td><input type="text" name="MODULE" id="MODULE" value="${pd.MODULE}" maxlength="100" placeholder="这里输入业务模块" title="业务模块" style="width:98%;"/></td>
	
							</tr>
														<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">考试分数:</td>
								<td><input type="text" name="TEST_SCORE" id="TEST_SCORE" value="${pd.TEST_SCORE}" maxlength="100" placeholder="这里输入考试分数" title="考试分数" style="width:98%;"/></td>
	
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">合格分数:</td>
								<td><input type="text" name="QUALIFIED_SCORE" id="QUALIFIED_SCORE" value="${pd.QUALIFIED_SCORE}" maxlength="100" placeholder="这里输入合格分数 " title="合格分数" style="width:98%;"/></td>
	
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
			if($("#COURSE_TYPE_NAME").val()==""){
				$("#COURSE_TYPE_NAME").tips({
					side:3,
		            msg:'请输入课程分类名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_TYPE_NAME").focus();
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