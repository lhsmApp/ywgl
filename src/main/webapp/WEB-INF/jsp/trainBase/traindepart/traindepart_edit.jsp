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
					
					<form action="traindepart/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="DEPART_ID" id="DEPART_ID" value="${pd.DEPART_ID}"/>
						<%-- <input type="hidden" name="DEPART_CODE" id="DEPART_CODE" value="${pd.DEPART_CODE}"/> --%>
						<input type="hidden" name="DEPART_PARENT_CODE" id="DEPART_PARENT_CODE" value="${null == pd.DEPART_PARENT_CODE ? TRAINDEPART_ID:pd.DEPART_PARENT_CODE}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">上级:</td>
								<td>
									<div class="col-xs-4 label label-lg label-light arrowed-in arrowed-right">
										<b>${null == pds.DEPART_NAME ?'(无) 此为顶级':pds.DEPART_NAME}</b>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">部门编码:</td>
								<td><input type="text" name="DEPART_CODE" id="DEPART_CODE" <c:if test="${pd.DEPART_CODE!=null and pd.DEPART_CODE!=''}">disabled="true"</c:if> value="${pd.DEPART_CODE}" maxlength="100" placeholder="这里输入部门编码" title="部门编码" style="width:98%;" onblur="hasBianma('${pd.DEPART_PARENT_CODE }');"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">部门名称:</td>
								<td><input type="text" name="DEPART_NAME" id="DEPART_NAME" value="${pd.DEPART_NAME}" maxlength="100" placeholder="这里输入部门名称" title="部门名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">负责人:</td>
								<td><input type="text" name="LEADER" id="LEADER" value="${pd.LEADER}" maxlength="100" placeholder="这里输入LEADER" title="LEADER" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">激活状态:</td>
								<td>
									<select name="STATE" title="状态">
										<option value="1" <c:if test="${pd.STATE == '1' }">selected</c:if> >正常</option>
										<option value="0" <c:if test="${pd.STATE == '0' }">selected</c:if> >停用</option>
									</select>
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
			if($("#DEPART_CODE").val()==""){
				$("#DEPART_CODE").tips({
					side:3,
		            msg:'请输入部门编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_CODE").focus();
				return false;
			}
			
			if($("#DEPART_NAME").val()==""){
				$("#DEPART_NAME").tips({
					side:3,
		            msg:'请输入部门名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_NAME").focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		//判断编码是否存在
		function hasBianma(departmentID){
			var DEPARTMENT_CODE = $.trim($("#DEPART_CODE").val());
			if("" == DEPARTMENT_CODE)return;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>department/hasBianma.do',
		    	data: {DEPARTMENT_CODE:DEPARTMENT_CODE,DEPARTMENT_ID:departmentID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data.result){
					 }else{
						$("#DEPART_CODE").tips({
							side:1,
				            msg:'编码'+DEPARTMENT_CODE+'已存在,重新输入',
				            bg:'#AE81FF',
				            time:5
				        });
						$('#DEPART_CODE').val('');
					 }
				}
			});
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>