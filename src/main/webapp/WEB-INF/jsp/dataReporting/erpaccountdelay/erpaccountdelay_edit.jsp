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
								<form action="erptempacctapplication/savedelay.do" name="userForm" id="userForm" method="post">
									<input type="hidden" name="IF_CREATE_FROM" id="IF_CREATE_FROM" value="${pd.IF_CREATE_FROM }"/>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">员工编号:</td>
											<td><input type="text" name="STAFF_CODE" id="STAFF_CODE" value="${pd.USERNAME }" maxlength="8" title="用户名" readonly= "readonly" style="width:98%;"/></td>
										</tr>	
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">用户名:</td>
											<td><input type="text" name="STAFF_NAME" id="STAFF_NAME" value="${pd.NAME }" maxlength="8" title="用户名" readonly= "readonly" style="width:98%;"/></td>
										</tr>								
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">所属单位:</td>
											<td><input type="text" name="UNIT_NAME" id="UNIT_NAME"  value="${pd.UNIT_NAME }" maxlength="32" title="所属单位" readonly= "readonly" style="width:98%;"/></td>
										</tr>										
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">延至日期:</td>
											<td>
<%-- 											<input type="text" name="ACCOUNT_DELETE_REASON" id="ACCOUNT_DELETE_REASON"value="${pd.ACCOUNT_DELETE_REASON }" placeholder="这里输入删除原因" maxlength="64" title="删除原因" style="width:98%;"/> --%>
										<div class="input-group input-group-sm">
										<input type="text" id="END_DATE" name="END_DATE"  value="${pd.END_DATE}" class="form-control"  data-date-format="yyyy-mm-dd" autocomplete="off" placeholder="请选择开始日期"/>
										<span class="input-group-addon">
											<i class="ace-icon fa fa-calendar" ></i>
										</span>
									</div>
											</td>
										</tr>							
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">生成延期申请</a>
												<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
											</td>
										</tr>
									</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
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
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());

	//日期
	$("#END_DATE" ).datepicker({
	showOtherMonths: true,
	selectOtherMonths: false,
	autoclose: true,
	todayHighlight: true
	});
	//保存
	function save(){
	
		if($("#ACCOUNT_DELETE_REASON").val()=="" && $("#ACCOUNT_DELETE_REASON").val()==""){
			$("#ACCOUNT_DELETE_REASON").tips({
				side:3,
	            msg:'请输入账号删除原因',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ACCOUNT_DELETE_REASON").focus();
			return false;
		}
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	}

	
	$(function() {
		//下拉框
		if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			});
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		}
	});
	

</script>
</html>