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
					
					<form action="coursedetail/${msg }.do" name="Form" id="Form" method="post">
						<%-- <input type="hidden" name="COURSEDETAIL_ID" id="COURSEDETAIL_ID" value="${pd.COURSEDETAIL_ID}"/> --%>
						<div id="zhongxin" style="padding-top: 13px;">
						<div id="task-tab" class="tab-pane active">
							<ul id="tasks" class="item-list ui-sortable">
								<li class="item-orange clearfix ui-sortable-handle">
									<label class="inline">
											<span class="inline">章节名称 :<input type="text" id="" name="" placeholder="章节名称"></span>
											<i class="ace-icon fa fa-pencil bigger-130 blue"></i>
									</label>
								</li>
								<li class="item-red clearfix ui-sortable-handle">
									<label class="inline">
										<span class="lbl">章节类型 :<input type="text" id="" name="" ></span>
										<i class="ace-icon fa fa-flag bigger-130 green"></i>
									</label>
									<div class="pull-right action-buttons">
										<span class="vbar"></span>
									</div>
								</li>
								<li class="item-default clearfix ui-sortable-handle">
									<label class="inline">
										<i class="ace-icon fa fa-leaf blue"></i><span><a class="btn btn-minier bigger btn-primary">新增小节</a></span>
									</label>
									<div class="inline" style="padding-left: 30px;">
										<i class="ace-icon fa fa-leaf green"></i><span><a onclick="copyUl();" class="btn btn-minier bigger btn-primary">新增章节</a></span>
									</div>
								</li>
							</ul>
						</div>
						
						
						
						<div id="hideTable" style="display: none;">
							<ul id="tasks" class="item-list ui-sortable">
								<li class="item-orange clearfix ui-sortable-handle">
									<label class="inline">
											<span class="inline">章节名称 :<input type="text" id="" name="" placeholder="章节名称"></span>
											<i class="ace-icon fa fa-pencil bigger-130 blue"></i>
									</label>
								</li>

								<li class="item-red clearfix ui-sortable-handle">
									<label class="inline">
										<span class="lbl">章节类型 :<input type="text" id="" name="" ></span>
										<i class="ace-icon fa fa-flag bigger-130 green"></i>
									</label>
									<div class="pull-right action-buttons">
										<span class="vbar"></span>
										
									</div>
								</li>
								<li class="item-default clearfix ui-sortable-handle">
									<label class="inline">
										<i class="ace-icon fa fa-leaf blue"></i><span><a class="btn btn-minier bigger btn-primary">新增小节</a></span>
									</label>
									<div class="inline" style="padding-left: 50px;">
										<i class="ace-icon fa fa-leaf green"></i><span><a onclick="copyUl();" class="btn btn-minier bigger btn-primary">新增章节</a></span>
									</div>
								</li>
							</ul>
						</div>
						<table class="table table-striped table-bordered table-hover">
							<tbody>
								<tr>
									<td style="text-align: center;" colspan="10">
										<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
										<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									</td>
								</tr>
							</tbody>
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
		//新增章节
		function copyUl(){
			$("#hideTable ul li").clone().appendTo("#tasks");
		}
		//保存
		function save(){
			if($("#CHAPTER_NAME").val()==""){
				$("#CHAPTER_NAME").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CHAPTER_NAME").focus();
			return false;
			}
			if($("#COURSE_ID").val()==""){
				$("#COURSE_ID").tips({
					side:3,
		            msg:'请输入备注3',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_ID").focus();
			return false;
			}
			if($("#CHAPTER_PARENT_ID").val()==""){
				$("#CHAPTER_PARENT_ID").tips({
					side:3,
		            msg:'请输入备注4',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CHAPTER_PARENT_ID").focus();
			return false;
			}
			if($("#CHAPTER_TYPE").val()==""){
				$("#CHAPTER_TYPE").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CHAPTER_TYPE").focus();
			return false;
			}
			if($("#VIDEO_DURATION").val()==""){
				$("#VIDEO_DURATION").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VIDEO_DURATION").focus();
			return false;
			}
			if($("#STATE").val()==""){
				$("#STATE").tips({
					side:3,
		            msg:'请输入备注7',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATE").focus();
			return false;
			}
			if($("#CREATE_USER").val()==""){
				$("#CREATE_USER").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATE_USER").focus();
			return false;
			}
			if($("#CREATE_TIME").val()==""){
				$("#CREATE_TIME").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATE_TIME").focus();
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
<%-- <table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注2:</td>
								<td><input type="text" name="CHAPTER_NAME" id="CHAPTER_NAME" value="${pd.CHAPTER_NAME}" maxlength="100" placeholder="这里输入备注2" title="备注2" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注3:</td>
								<td><input type="number" name="COURSE_ID" id="COURSE_ID" value="${pd.COURSE_ID}" maxlength="32" placeholder="这里输入备注3" title="备注3" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注4:</td>
								<td><input type="number" name="CHAPTER_PARENT_ID" id="CHAPTER_PARENT_ID" value="${pd.CHAPTER_PARENT_ID}" maxlength="32" placeholder="这里输入备注4" title="备注4" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注5:</td>
								<td><input type="text" name="CHAPTER_TYPE" id="CHAPTER_TYPE" value="${pd.CHAPTER_TYPE}" maxlength="30" placeholder="这里输入备注5" title="备注5" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注6:</td>
								<td><input type="text" name="VIDEO_DURATION" id="VIDEO_DURATION" value="${pd.VIDEO_DURATION}" maxlength="30" placeholder="这里输入备注6" title="备注6" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注7:</td>
								<td><input type="text" name="STATE" id="STATE" value="${pd.STATE}" maxlength="1" placeholder="这里输入备注7" title="备注7" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注8:</td>
								<td><input type="text" name="CREATE_USER" id="CREATE_USER" value="${pd.CREATE_USER}" maxlength="30" placeholder="这里输入备注8" title="备注8" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注9:</td>
								<td><input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="30" placeholder="这里输入备注9" title="备注9" style="width:98%;"/></td>
							</tr>
							
						</table> --%>
</html>