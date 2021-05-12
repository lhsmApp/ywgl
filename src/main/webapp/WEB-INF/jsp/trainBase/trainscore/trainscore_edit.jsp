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
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">			
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">考试时间:</td>
<%-- 								<td><input type="text" name="TEST_DATE" id="TEST_DATE" value="${pd.TEST_DATE}"  placeholder="这里选择考试时间" title="考试时间" style="width:98%;"/></td> --%>
							<td>	
							<div class="input-group input-group-sm">
										<input type="text" id="TEST_DATE" name="TEST_DATE" value="${pd.TEST_DATE}" class="form-control"  data-date-format="yyyymmdd" placeholder="请选择开始日期"/>
										<span class="input-group-addon">
											<i class="ace-icon fa fa-calendar" ></i>
										</span>
									</div></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">考试名称:</td>
								<td><input type="text" name="TEST_NAME" id="TEST_NAME" value="${pd.TEST_NAME}" maxlength="100" placeholder="这里输入考试名称" title="考试名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">员工编号:</td>
								<td><input type="text" name="USERNAME" id="USERNAME" value="${pd.USERNAME}" maxlength="100" placeholder="这里输入员工编号" title="员工编号" style="width:98%;" /></td>
	
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">考生姓名:</td>
								<td><input type="text" name="NAME1" id="NAME1" value="${pd.NAME1}" maxlength="100" placeholder="这里输入考生姓名" title="考生姓名" style="width:98%;"/></td>
	
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">考生单位:</td>
<%-- 								<td><input type="text" name="UNIT_CODE" id="UNIT_CODE" value="${pd.UNIT_CODE}" maxlength="100" placeholder="这里输入考生单位" title="考生单位" style="width:98%;"/></td> --%>
								<td >
								<select class="form-control" name="UNIT_CODE" id="UNIT_CODE" title="所属单位 ">
									<option value="">--请选择单位--</option>
									<c:forEach items="${itemUnitList}" var="unit">
										<option value="${unit.id }" <c:if test="${unit.id == pd.UNIT_CODE }">selected</c:if>>${unit.name }</option>
									</c:forEach>
								</select>			
								</td>	
							</tr>
														<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">业务模块:</td>
<%-- 								<td><input type="text" name="MODULE" id="MODULE" value="${pd.MODULE}" maxlength="100" placeholder="这里输入业务模块" title="业务模块" style="width:98%;"/></td> --%>
								<td>		
									<select class="form-control" name="MODULE" id="MODULE" title="业务模块 ">
										<option value="">--请选择业务模块--</option>
										<option value="财务" <c:if test="${ pd.MODULE=='财务'}">selected</c:if>>财务</option>
										<option value="设备" <c:if test="${ pd.MODULE=='设备'}">selected</c:if>>设备</option>
										<option value="生产" <c:if test="${ pd.MODULE=='生产'}">selected</c:if>>生产</option>
										<option value="物资" <c:if test="${ pd.MODULE=='物资'}">selected</c:if>>物资</option>
										<option value="项目" <c:if test="${ pd.MODULE=='项目'}">selected</c:if>>项目</option>
										<option value="销售" <c:if test="${ pd.MODULE=='销售'}">selected</c:if>>销售</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">考试分数:</td>
								<td><input type="number" name="TEST_SCORE" id="TEST_SCORE" value="${pd.TEST_SCORE}" maxlength="100" placeholder="这里输入考试分数" title="考试分数" style="width:98%;" oninput = "value=value.replace(/[^\d]/g,'')"/></td>
	
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">证书编号:</td>
								<td><input type="text" name="CERTIFICATE_NUM" id="CERTIFICATE_NUM" value="${pd.CERTIFICATE_NUM}" maxlength="100" placeholder="这里输入证书编号 " title="证书编号 " style="width:98%;"/></td>
	
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
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//考试日期
		$("#TEST_DATE" ).datepicker({
		showOtherMonths: true,
		selectOtherMonths: false,
		autoclose: true,
		todayHighlight: true,
		endDate : new Date()
		});

		//保存
		function save(){
			var userValue= $("#USERNAME").val();
			 var reg = new RegExp(/^\d{8}$/);   //用户名必须是8位数字
	        if(!reg.test(userValue)) {
	        	$("#USERNAME").tips({
					side:3,
		            msg:'员工编码必须为8位数字',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USERNAME").focus();
				return false;
	        }	
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
		function userChange(){
			var userValue= $("#USERNAME").val();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>trainscore/getTrainScore.do',
		    	data: {USERNAME:userValue},
				dataType:'json',
				cache: false,
				success: function(data){
				console.log(data);
				}
			});
		}
	
		</script>
</body>
</html>