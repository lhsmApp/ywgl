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
<%@ include file="../index/top.jsp"%>
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
						<form action="user/listUsers.do" method="post" name="userForm" id="userForm">
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
								<%-- <td style="padding-left:2px;"><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="最近登录开始"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastLoginEnd" name="lastLoginEnd"  value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="最近登录结束"/></td> --%>
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="ROLE_ID" id="role_id" data-placeholder="请选择角色" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<c:forEach items="${roleList}" var="role">
										<option value="${role.ROLE_ID }" <c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME }</option>
									</c:forEach>
								  	</select>
								</td>
								<td style="vertical-align:top;padding-left:2px;"> 
								 	<select class="chosen-select form-control" name="STATUS" id="STATUS" data-placeholder="状态" style="vertical-align:top;width: 79px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="1" <c:if test="${pd.STATUS == '1' }">selected</c:if> >正常</option>
									<option value="0" <c:if test="${pd.STATUS == '0' }">selected</c:if> >停用</option>
									</select>
								</td>
								<c:if test="${userName == 'admin'}">
									<td  style="padding-left:5px">
										<div class="selectTree" id="selectTree"></div>
									</td>
								</c:if>
								<td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								<td style="padding-left:6px;">	
									<c:if test="${pd.ROLE_TYPE  == 'gly'}">
									<label class="btn btn-sm btn-primary" onclick="importData()">
										 <i class="ace-icon fa fa-cloud-upload  bigger-110"></i>导入
									</label></c:if>	 	</td>
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
									<!-- <th class="center">编号</th> -->
									<th class="center">用户名</th>
									<th class="center">姓名</th>
									<th class="center">角色</th>
									<th class="center">单位</th>
									<th class="center">部门</th>
									<th class="center">手机号</th>
									<th class="center">是否SAP账号</th>
									<!-- <th class="center"><i class="ace-icon fa fa-envelope-o"></i>邮箱</th> -->
									<th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>最近登录</th>
									<th class="center">上次登录IP</th>
									<th class="center">备注</th>
									<th class="center">状态</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
								
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty userList}">
									<c:forEach items="${userList}" var="user" varStatus="vs">
												
										<tr>
											<td class='center' style="width: 30px;">
												<c:if test="${user.USERNAME != 'admin'}"><label><input type='checkbox' name='ids' value="${user.USER_ID }" id="${user.EMAIL }" alt="${user.PHONE }" title="${user.USERNAME }" class="ace"/><span class="lbl"></span></label></c:if>
												<c:if test="${user.USERNAME == 'admin'}"><label><input type='checkbox' disabled="disabled" class="ace" /><span class="lbl"></span></label></c:if>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<%-- <td class="center">${user.NUMBER }</td> --%>
											<td class="center">${user.USERNAME }<%-- <a onclick="viewUser('${user.USERNAME}')" style="cursor:pointer;">${user.USERNAME }</a> --%></td>
											<td class="center">${user.NAME }</td>
											<td class="center">${user.ROLE_NAME }</td>
											<td class="center">${user.UNIT_NAME }</td>
											<td class="center">${user.DEPARTMENT_NAME }</td>
											<td class="center">${user.PHONE }</td>
											<td class="center">
												<c:if test="${user.USER_PROPERTY  == 1}"><span>是</span></c:if>
												<c:if test="${user.USER_PROPERTY  == 0}"><span>否</span></c:if>
												<c:if test="${user.USER_PROPERTY  == null}"><span style="color:red">未维护</span></c:if>
													</td>
											<%-- <td class="center"><a title="发送电子邮件" style="text-decoration:none;cursor:pointer;" <c:if test="${QX.email == 1 }">onclick="sendEmail('${user.EMAIL }');"</c:if>>${user.EMAIL }&nbsp;<i class="ace-icon fa fa-envelope-o"></i></a></td> --%>
											<td class="center">${user.LAST_LOGIN}</td>
											<td class="center">${user.IP}</td>
											<td class="center">${user.BZ }</td>
											<td style="width: 60px;" class="center">
												<c:if test="${user.STATUS == '0' }"><span class="label label-important arrowed-in">停用</span></c:if>
												<c:if test="${user.STATUS == '1' }"><span class="label label-success arrowed">正常</span></c:if>
											</td>
											<td class="center">
												<div class="hidden-sm hidden-xs btn-group" >
													<a class="btn btn-xs btn-success" title="编辑" onclick="editUser('${user.USER_ID}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													<c:if test="${user.USER_PROPERTY  != 1}">
														<a class="btn btn-xs btn-danger" onclick="delUser('${user.USER_ID }','${user.USERNAME }');">
															<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
														</a>
													</c:if>
													<c:if test="${user.USER_PROPERTY  == 1}">
														<a   class="btn btn-xs btn-danger" onclick="delSapUser();">
															<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
														</a>
													</c:if>
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
																<a istyle="cursor:pointer;" onclick="delUser('${user.USER_ID }','${user.USERNAME }');" class="tooltip-error" data-rel="tooltip" title="删除">
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
						
					<div class="page-header position-relative">
					<table style="width:100%;">
						<tr>
							<td style="vertical-align:top;">
								
								<a class="btn btn-mini btn-success" onclick="add();">新增</a>
								<%-- </c:if> --%>
								<%-- <c:if test="${QX.FHSMS == 1 }"><a title="批量发送站内信" class="btn btn-mini btn-info" onclick="makeAll('确定要给选中的用户发送站内信吗?');"><i class="ace-icon fa fa-envelope-o bigger-120"></i></a></c:if>
								<c:if test="${QX.email == 1 }"><a title="批量发送电子邮件" class="btn btn-mini btn-primary" onclick="makeAll('确定要给选中的用户发送邮件吗?');"><i class="ace-icon fa fa-envelope bigger-120"></i></a></c:if> 
								<c:if test="${QX.sms == 1 }"><a title="批量发送短信" class="btn btn-mini btn-warning" onclick="makeAll('确定要给选中的用户发送短信吗?');"><i class="ace-icon fa fa-envelope-o bigger-120"></i></a></c:if>--%>
								<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
							</td>
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
						</tr>
					</table>
					</div>
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
	<%@ include file="../index/foot.jsp"%>
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

//删除
function delUser(userId,msg){
	bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
		if(result) {
			top.jzts();
			var url = "<%=basePath%>user/deleteU.do?USER_ID="+userId+"&tm="+new Date().getTime();
			$.get(url,function(data){
				nextPage(${page.currentPage});
			});
		};
	});
}
function delSapUser(){

	bootbox.dialog({
		message: "<span class='bigger-110'>该用户为SAP用户，需要按照ERP账号删除流程进行删除!</span>",
		buttons: 			
		{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
	});
}
/**
 * 导入
 */
function importData(){
	   top.jzts();
   var diag = new top.Dialog();
   diag.Drag=true;
   diag.Title ="SAP用户导入";
   diag.URL = '<%=basePath%>user/goUploadExcel.do';
   diag.Width = 300;
   diag.Height = 150;
   diag.CancelEvent = function(){ //关闭事件
	  top.jzts();

	  $(top.hangge());//关闭加载状态
      diag.close();
   };
   diag.show();
}
//新增
function add(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="新增";
	 diag.URL = '<%=basePath%>user/goAddU.do';
	 diag.Width = 469;
	 diag.Height = 600;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 if('${page.currentPage}' == '0'){
				 top.jzts();
				 setTimeout("self.location=self.location",100);
			 }else{
				 nextPage(${page.currentPage});
			 }
		}
		diag.close();
	 };
	 diag.show();
}

//修改
function editUser(user_id){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="资料";
	 diag.URL = '<%=basePath%>user/goEditU.do?USER_ID='+user_id;
	 diag.Width = 469;
	 diag.Height = 600;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			nextPage(${page.currentPage});
		}
		diag.close();
	 };
	 diag.show();
}

//批量操作
function makeAll(msg){
	bootbox.confirm(msg, function(result) {
		if(result) {
			var str = '';
			var emstr = '';
			var phones = '';
			var username = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				  if(document.getElementsByName('ids')[i].checked){
				  	if(str=='') str += document.getElementsByName('ids')[i].value;
				  	else str += ',' + document.getElementsByName('ids')[i].value;
				  	
				  	if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
				  	else emstr += ';' + document.getElementsByName('ids')[i].id;
				  	
				  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
				  	else phones += ';' + document.getElementsByName('ids')[i].alt;
				  	
				  	if(username=='') username += document.getElementsByName('ids')[i].title;
				  	else username += ';' + document.getElementsByName('ids')[i].title;
				  }
			}
			if(str==''){
				bootbox.dialog({
					message: "<span class='bigger-110'>您没有选择任何内容!</span>",
					buttons: 			
					{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
				});
				$("#zcheckbox").tips({
					side:3,
		            msg:'点这里全选',
		            bg:'#AE81FF',
		            time:8
		        });
				
				return;
			}else{
				if(msg == '确定要删除选中的数据吗?'){
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>user/deleteAllU.do?tm='+new Date().getTime(),
				    	data: {USER_IDS:str},
						dataType:'json',
						//beforeSend: validateData,
						cache: false,
						success: function(data){
							 $.each(data.list, function(i, list){
									nextPage(${page.currentPage});
							 });
						}
					});
				}else if(msg == '确定要给选中的用户发送邮件吗?'){
					sendEmail(emstr);
				}else if(msg == '确定要给选中的用户发送短信吗?'){
					sendSms(phones);
				}else if(msg == '确定要给选中的用户发送站内信吗?'){
					sendFhsms(username);
				}
			}
		}
	});
}

//去发送短信页面
function sendSms(phone){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="发送短信";
	 diag.URL = '<%=basePath%>head/goSendSms.do?PHONE='+phone+'&msg=appuser';
	 diag.Width = 600;
	 diag.Height = 265;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//去发送电子邮件页面
function sendEmail(EMAIL){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="发送电子邮件";
	 diag.URL = '<%=basePath%>head/goSendEmail.do?EMAIL='+EMAIL;
	 diag.Width = 660;
	 diag.Height = 486;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//发站内信
function sendFhsms(username){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="站内信";
	 diag.URL = '<%=basePath%>fhsms/goAdd.do?username='+username;
	 diag.Width = 660;
	 diag.Height = 444;
	 diag.CancelEvent = function(){ //关闭事件
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
		
function initComplete(){
	//下拉树
	var defaultNodes = {"treeNodes":${zTreeNodes}};
	//绑定change事件
	$("#selectTree").bind("change",function(){
		if(!$(this).attr("relValue")){
	      //  top.Dialog.alert("没有选择节点");
	    }else{
			//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
			$("#DEPARTMENT_ID").val($(this).attr("relValue"));
	    }
	});
	//赋给data属性
	$("#selectTree").data("data",defaultNodes);  
	$("#selectTree").render();
	$("#selectTree2_input").val("${null==depname||''==depname?'请选择单位':depname}");
	
	$("#selectTree2_button").closest('.ali01').append("<input value=''  id='reset_button' type='button' class='selBtn' style='background-image: url(plugins/selectZtree/ztree/img/diy/2.png);border: 1px solid rgb(124,193,235);border-left: 0;background-position: center;' onclick='reset1()'>");
}

function reset1(){
	/* console.log(${pd.DEPARTMENT_ID });
	console.log('${pd.DEPARTMENT_ID }'); */
	$("#DEPARTMENT_ID").val('');
	$("#selectTree2_input").val("请选择单位");
}
</script>
</html>
