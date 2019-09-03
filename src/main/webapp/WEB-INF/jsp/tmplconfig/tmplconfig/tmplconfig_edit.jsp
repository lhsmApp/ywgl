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
					
					<form action="tmplconfig/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="TMPLCONFIG_ID" id="TMPLCONFIG_ID" value="${pd.TMPLCONFIG_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位编码:</td>
								<td><input type="text" name="DEPT_CODE" id="DEPT_CODE" value="${pd.DEPT_CODE}" maxlength="30" placeholder="这里输入单位编码" title="单位编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">表名:</td>
								<td><input type="text" name="TABLE_CODE" id="TABLE_CODE" value="${pd.TABLE_CODE}" maxlength="40" placeholder="这里输入表名" title="表名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">列编码:</td>
								<td><input type="text" name="COL_CODE" id="COL_CODE" value="${pd.COL_CODE}" maxlength="20" placeholder="这里输入列编码" title="列编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">列名称:</td>
								<td><input type="text" name="COL_NAME" id="COL_NAME" value="${pd.COL_NAME}" maxlength="50" placeholder="这里输入列名称" title="列名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">显示序号:</td>
								<td><input type="number" name="DISP_ORDER" id="DISP_ORDER" value="${pd.DISP_ORDER}" maxlength="32" placeholder="这里输入显示序号" title="显示序号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">字典翻译:</td>
								<td><input type="text" name="DICT_TRANS" id="DICT_TRANS" value="${pd.DICT_TRANS}" maxlength="30" placeholder="这里输入字典翻译" title="字典翻译" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">列隐藏:</td>
								<td><input type="text" name="COL_HIDE" id="COL_HIDE" value="${pd.COL_HIDE}" maxlength="1" placeholder="这里输入列隐藏" title="列隐藏" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">列汇总:</td>
								<td><input type="text" name="COL_SUM" id="COL_SUM" value="${pd.COL_SUM}" maxlength="1" placeholder="这里输入列汇总" title="列汇总" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">列平均值:</td>
								<td><input type="text" name="COL_AVE" id="COL_AVE" value="${pd.COL_AVE}" maxlength="1" placeholder="这里输入列平均值" title="列平均值" style="width:98%;"/></td>
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
			if($("#DEPT_CODE").val()==""){
				$("#DEPT_CODE").tips({
					side:3,
		            msg:'请输入单位编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPT_CODE").focus();
			return false;
			}
			if($("#TABLE_CODE").val()==""){
				$("#TABLE_CODE").tips({
					side:3,
		            msg:'请输入表名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TABLE_CODE").focus();
			return false;
			}
			if($("#COL_CODE").val()==""){
				$("#COL_CODE").tips({
					side:3,
		            msg:'请输入列编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COL_CODE").focus();
			return false;
			}
			if($("#COL_NAME").val()==""){
				$("#COL_NAME").tips({
					side:3,
		            msg:'请输入列名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COL_NAME").focus();
			return false;
			}
			if($("#DISP_ORDER").val()==""){
				$("#DISP_ORDER").tips({
					side:3,
		            msg:'请输入显示序号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DISP_ORDER").focus();
			return false;
			}
			if($("#DICT_TRANS").val()==""){
				$("#DICT_TRANS").tips({
					side:3,
		            msg:'请输入字典翻译',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DICT_TRANS").focus();
			return false;
			}
			if($("#COL_HIDE").val()==""){
				$("#COL_HIDE").tips({
					side:3,
		            msg:'请输入列隐藏',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COL_HIDE").focus();
			return false;
			}
			if($("#COL_SUM").val()==""){
				$("#COL_SUM").tips({
					side:3,
		            msg:'请输入列汇总',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COL_SUM").focus();
			return false;
			}
			if($("#COL_AVE").val()==""){
				$("#COL_AVE").tips({
					side:3,
		            msg:'请输入列平均值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COL_AVE").focus();
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