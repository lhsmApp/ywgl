﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
						
						<!-- 检索  -->
						<form action="erptempacctapplication/listUsers.do" method="post" name="userForm" id="userForm">
						<input name="ZDEPARTMENT_ID" id="ZDEPARTMENT_ID" type="hidden" value="${pd.ZDEPARTMENT_ID }" />
						<input name="DEPARTMENT_ID" id="DEPARTMENT_ID" type="hidden" value="${pd.DEPARTMENT_ID }" />
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
									<span class="input-icon">
										<input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词" />
										<i class="ace-icon fa fa-search nav-search-icon"></i>
									</span>
									</div>
								</td>	
								<td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
												
							</tr>
						</table>
					
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">员工编号</th>
									<th class="center">员工姓名</th>
									<th class="center">所属单位</th>								
<!-- 									<th class="center">所属部门</th> -->
									<th class="center">职务</th>
									<th class="center">模块</th>							
									<th class="center">联络电话</th>
									<th class="center">UKEY编号</th>
									<th class="center">申请日期</th>
									<th class="center">撤销日期</th>
									<th class="center">单据状态</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
								
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="user" varStatus="vs">
												
										<tr>
											<td class='center' style="width: 30px;">
												<c:if test="${user.USERNAME != 'admin'}"><label><input type='checkbox' name='ids' value="${user.USER_ID }" id="${user.EMAIL }" alt="${user.PHONE }" title="${user.USERNAME }" class="ace"/><span class="lbl"></span></label></c:if>
												<c:if test="${user.USERNAME == 'admin'}"><label><input type='checkbox' disabled="disabled" class="ace" /><span class="lbl"></span></label></c:if>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class="center">${user.STAFF_CODE }<%-- <a onclick="viewUser('${user.USERNAME}')" style="cursor:pointer;">${user.USERNAME }</a> --%></td>
											<td class="center">${user.STAFF_NAME }</td>
											<td class="center">${user.DEPART_CODE}</td>
<%-- 											<td class="center">${user.UNITS_DEPART}</td> --%>
											<td class="center">${user.STAFF_POSITION}</td>
											<td class="center">${user.STAFF_MODULE}</td>
											<td class="center">${user.PHONE}</td>							
											<td class="center">${user.UKEY_NUM }</td>
											<td class="center">${user.APPLY_DATE }</td>
											<td class="center">${user.CANCEL_DATE }</td>
											<td style="width: 60px;" class="center">
												<c:if test="${user.IF_CREATE_FROM == '1' }"><span>已生成</span></c:if>
												<c:if test="${user.IF_CREATE_FROM == '0' ||user.IF_CREATE_FROM==null}"><span>未生成 </span></c:if>
											</td>
											<td class="center">
												<div class="hidden-sm hidden-xs btn-group">
<%-- 												<a class="btn btn-xs btn-success" title="编辑" onclick="view('${user.USERNAME}');"> --%>
<!-- 														查看 -->
<!-- 													</a> -->
													<a class="btn btn-xs btn-primary" onclick="create('${user.STAFF_NAME }','${user.STAFF_CODE }','${user.IF_CREATE_FROM }','${user.DEPART_CODE }');">
														生成
													</a>
													<a class="btn btn-xs btn-danger" onclick="cancel('${user.STAFF_NAME }','${user.STAFF_CODE }','${user.IF_CREATE_FROM }','${user.CONFIRM_STATE }');">
														撤销
													</a>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<li>
																<a style="cursor:pointer;" onclick="editUser('${user.USER_ID}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															<li>
																<a style="cursor:pointer;" onclick="edit('${user.USER_ID }','${user.USERNAME }');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
														</ul>
													</div>
												</div>
											</td>
										</tr>
									
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="10" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						
	
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

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	</body>

<script type="text/javascript">
$(top.hangge());

//检索
function searchs(){
	top.jzts();
	$("#userForm").submit();
}

// //撤销删除申请
// function cancel(name,userName){
// 	bootbox.confirm("确定要撤销员工编号：["+userName+"]姓名为：["+name+"]的ERP账号删除申请吗？", function(result) {
// 		if(result) {
<%-- 			var url = "<%=basePath%>erptempacctapplication/cancelDel.do?STAFF_CODE="+userName; --%>
// 			$.get(url,function(data){
// 				nextPage(${page.currentPage});
// 			});
// 		};
// 	});
// }
//撤销延期申请
function cancel(name,userName,ifCreat,confirm){
	if(ifCreat!=1){
		bootbox.dialog({
			message: "<span class='bigger-110'>帐号"+userName+"还未生成延期申请！</span>",
			buttons: 			
			{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
		});
		return;
	}
// 	if(confirm=='3'){
// 		bootbox.dialog({
// 			message: "<span class='bigger-110'>帐号"+userName+"延期申请已确认，不能撤销！</span>",
// 			buttons: 			
// 			{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
// 		});
// 		return;
// 	}
	bootbox.confirm("确定要撤销员工编号：["+userName+"]姓名为：["+name+"]的ERP账号延期申请吗？", function(result) {
		if(result) {
			var url = "<%=basePath%>erptempacctapplication/savedelay.do?STAFF_CODE="+userName+"&IF_CREATE_FROM=0";
			$.get(url,function(data){
				//nextPage(${page.currentPage});
				window.location.href='<%=basePath%>erptempacctapplication/listUsers.do';
		bootbox.dialog({
			message: "<span class='bigger-110'>帐号"+userName+"延期申请撤销成功！</span>",
			buttons: 			
			{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
		});
			});
		};
	});
}
//打印
function view(userName,ifCreat){
	if(ifCreat!=1){
		bootbox.dialog({
			message: "<span class='bigger-110'>帐号"+userName+"还未生成延期申请！</span>",
			buttons: 			
			{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
		});
		return;
	}
	top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="ERP帐号延期申请";
	 diag.URL = '<%=basePath%>erptempacctapplication/viewApply.do?STAFF_CODE='+userName; 
	 diag.Width = 800;
	 diag.Height = 350;
	 diag.Modal = true;				//有无遮罩窗口
	 diag. ShowMaxButton = true;	//最大化按钮
    diag.ShowMinButton = true;		//最小化按钮 
	 diag.show();
}
//生成延期申请
function create(name,userName,ifCreat,departCode){
	if(ifCreat==1){
		bootbox.dialog({
			message: "<span class='bigger-110'>帐号"+userName+"已生成延期申请，不能重复生成！</span>",
			buttons: 			
			{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
		});
		return;
	}
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="账号删延期";
	 diag.URL = '<%=basePath%>erptempacctapplication/goEdit.do?USERNAME='+userName+'&UNIT_NAME='+departCode+'&NAME='+name+'&IF_CREATE_FROM=1';
	 diag.Width = 750;
	 diag.Height = 400;
	 diag.Modal = true;				//有无遮罩窗口
	 diag. ShowMaxButton = true;	//最大化按钮
     diag.ShowMinButton = true;		//最小化按钮 
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			// nextPage(${page.currentPage});
			bootbox.dialog({
							message: "<span class='bigger-110'>帐号"+userName+"生成延期申请成功！</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
		}
			window.location.href='<%=basePath%>erptempacctapplication/listUsers.do'
		diag.close();
	 };
	 diag.show();
}

$(function() {
	//日期框
	$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
	
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

	
	//复选框全选控制
	var active_class = 'active';
	$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
		var th_checked = this.checked;//checkbox inside "TH" table header
		$(this).closest('table').find('tbody > tr').each(function(){
			var row = this;
			if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
			else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
		});
	});
});

//导出excel
function toExcel(){
	var keywords = $("#nav-search-input").val();
	var lastLoginStart = $("#lastLoginStart").val();
	var lastLoginEnd = $("#lastLoginEnd").val();
	var ROLE_ID = $("#role_id").val();
	window.location.href='<%=basePath%>user/excel.do?keywords='+keywords+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&ROLE_ID='+ROLE_ID;
}

//打开上传excel页面
function fromExcel(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="EXCEL 导入到数据库";
	 diag.URL = '<%=basePath%>user/goUploadExcel.do';
	 diag.Width = 300;
	 diag.Height = 150;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 if('${page.currentPage}' == '0'){
				 top.jzts();
				 setTimeout("self.location.reload()",100);
			 }else{
				 nextPage(${page.currentPage});
			 }
		}
		diag.close();
	 };
	 diag.show();
}	

//查看用户
function viewUser(USERNAME){
	if('admin' == USERNAME){
		bootbox.dialog({
			message: "<span class='bigger-110'>不能查看admin用户!</span>",
			buttons: 			
			{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
		});
		return;
	}
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="资料";
	 diag.URL = '<%=basePath%>user/view.do?USERNAME='+USERNAME;
	 diag.Width = 469;
	 diag.Height = 380;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}
		

</script>
</html>
