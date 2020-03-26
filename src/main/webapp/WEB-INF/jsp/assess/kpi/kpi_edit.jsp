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
					
					<form action="kpi/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="KPI_ID" id="KPI_ID" value="${pd.KPI_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">指标分类:</td>
								<td><input type="text" name="KPI_TYPE" id="KPI_TYPE" value="${pd.KPI_TYPE}" maxlength="30" placeholder="这里输入指标分类" title="指标分类" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">指标代码:</td>
								<td><input type="text" name="KPI_CODE" id="KPI_CODE" value="${pd.KPI_CODE}" maxlength="20" placeholder="这里输入指标代码" title="指标代码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">指标作用:</td>
								<td><input type="text" name="KPI_EFFECT" id="KPI_EFFECT" value="${pd.KPI_EFFECT}" maxlength="200" placeholder="这里输入指标作用" title="指标作用" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">考核对象:</td>
								<td><input type="text" name="ASSESS_OBJECT" id="ASSESS_OBJECT" value="${pd.ASSESS_OBJECT}" maxlength="200" placeholder="这里输入考核对象" title="考核对象" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">指标名称:</td>
								<td><input type="text" name="KPI_NAME" id="KPI_NAME" value="${pd.KPI_NAME}" maxlength="100" placeholder="这里输入指标名称" title="指标名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">总分:</td>
								<td><input type="text" name="TOTAL_SCORE" id="TOTAL_SCORE" value="${pd.TOTAL_SCORE}" maxlength="100" placeholder="这里输入总分" title="总分" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">指标权重:</td>
								<td><input type="text" name="PROPORTION" id="PROPORTION" value="${pd.PROPORTION}" maxlength="100" placeholder="这里输入指标权重" title="指标权重" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">判断标准:</td>
								<td><input type="text" name="JUDGEMENT_STANDARD" id="JUDGEMENT_STANDARD" value="${pd.JUDGEMENT_STANDARD}" maxlength="200" placeholder="这里输入判断标准" title="判断标准" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">计算公式:</td>
								<td>
									<%-- <input type="text" name="FORMULA" id="FORMULA" value="${pd.FORMULA}" maxlength="500" placeholder="这里输入计算公式" title="计算公式" style="width:98%;"/> --%>
									<textarea rows="2" cols="46" name="FORMULA" id="FORMULA" placeholder="这里输入计算公式" title="计算公式"  style="width:98%;">${pd.FORMULA}</textarea>
								</td>
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
			/* if($("#KPI_TYPE").val()==""){
				$("#KPI_TYPE").tips({
					side:3,
		            msg:'请输入指标分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#KPI_TYPE").focus();
			return false;
			}
			if($("#KPI_CODE").val()==""){
				$("#KPI_CODE").tips({
					side:3,
		            msg:'请输入指标代码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#KPI_CODE").focus();
			return false;
			}
			if($("#KPI_EFFECT").val()==""){
				$("#KPI_EFFECT").tips({
					side:3,
		            msg:'请输入指标作用',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#KPI_EFFECT").focus();
			return false;
			}
			if($("#ASSESS_OBJECT").val()==""){
				$("#ASSESS_OBJECT").tips({
					side:3,
		            msg:'请输入考核对象',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ASSESS_OBJECT").focus();
			return false;
			} */
			if($("#KPI_NAME").val()==""){
				$("#KPI_NAME").tips({
					side:3,
		            msg:'请输入指标名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#KPI_NAME").focus();
			return false;
			}
			/* if($("#JUDGEMENT_STANDARD").val()==""){
				$("#JUDGEMENT_STANDARD").tips({
					side:3,
		            msg:'请输入判断标准',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#JUDGEMENT_STANDARD").focus();
			return false;
			}
			if($("#FORMULA").val()==""){
				$("#FORMULA").tips({
					side:3,
		            msg:'请输入计算公式',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FORMULA").focus();
			return false;
			} */
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