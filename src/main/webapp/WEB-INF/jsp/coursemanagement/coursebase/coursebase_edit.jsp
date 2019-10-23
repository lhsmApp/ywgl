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
					<form action="coursebase/${msg }.do" name="Form" id="Form" method="post">
						<div class="col-xs-6" style="padding-left:0px; margin-bottom:-15px;">
						<input type="hidden" name="COURSEBASE_ID" id="COURSEBASE_ID" value="${pd.COURSEBASE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;" class="form-inline">
						<table id="table_report" class="table table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">课程名称:</td>
								<td><input type="text" name="COURSE_NAME" id="COURSE_NAME" value="${pd.COURSE_NAME}" maxlength="50" placeholder="请输入课程名称" title="请输入课程名称" style="width:99%;"/></td>
							</tr>
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 13px;">课程分类:</td>
								<td>
									<select class="chosen-select form-control" name="COURSE_TYPE" id="COURSE_TYPE" data-placeholder="请选择课程分类" style="vertical-align: top; height: 32px; width: 99%;">
										<c:forEach items="${treeList}" var="tl">
											<option value="${tl.COURSE_TYPE_ID}" <c:if test="${tl.COURSE_TYPE_ID==pd.COURSE_TYPE}">selected="selected"</c:if>>${tl.COURSE_TYPE_NAME}</option>
											
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 13px;">课程讲师:</td>
								<td><input type="text" name="COURSE_TEACHER" id="COURSE_TEACHER" value="${pd.COURSE_TEACHER}" maxlength="30" placeholder="请输入课程讲师" title="请输入课程讲师" style="width:99%;"/></td>
							</tr>
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 13px;">课程标签</td>
								<td><input type="text" name="COURSE_TAG" id="COURSE_TAG" value="${pd.COURSE_TAG}" maxlength="50" placeholder="请输入课程标签" title="请输入课程标签" style="width:99%;"/></td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</div>
					<div class="col-xs-6" style="text-align:center; padding-top:5%;">
							<a href="" data-rel="colorbox" class="cboxElement">
								<img width="160" height="160" alt="160x160" src="static/html_UI/assets/images/gallery/thumb-3.jpg">
							</a>
					</div>
					<table class="table table-bordered table-hover">
						<tr>
							<td style="width:115px;text-align: right;padding-top: 13px;">完成可获得积分:</td>
								<td><input type="number" name="COURSE_TAG" id="COURSE_TAG" value="${pd.COURSE_TAG}" maxlength="50" placeholder="这里输入备注5" title="备注5" style="width:32%;"/></td>
						</tr>
						<tr>
							<td style="width:75px;text-align: center;padding-top: 13px;">课程简介:</td>
								<td><textarea name="COURSE_TAG" id="COURSE_TAG" maxlength="50" placeholder="请输入课程介绍" title="备注5" style="width:99%;"></textarea></td>
						</tr>
						<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr> 
					</table>
					</form>
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
			if($("#COURSE_NAME").val()==""){
				$("#COURSE_NAME").tips({
					side:3,
		            msg:'请输入课程名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_NAME").focus();
			return false;
			}
			if($("#COURSE_TYPE").val()==""){
				$("#COURSE_TYPE").tips({
					side:3,
		            msg:'请输入课程分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_TYPE").focus();
			return false;
			}
			if($("#COURSE_TEACHER").val()==""){
				$("#COURSE_TEACHER").tips({
					side:3,
		            msg:'请输入课程讲师',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_TEACHER").focus();
			return false;
			}
			if($("#COURSE_TAG").val()==""){
				$("#COURSE_TAG").tips({
					side:3,
		            msg:'请输入课程标签',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_TAG").focus();
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