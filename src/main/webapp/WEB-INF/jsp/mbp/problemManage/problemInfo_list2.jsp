<%@ page language="java" contentType="text/html; charset=UTF-8"
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
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="page-header">
						<form class="form-inline">
							<label class="pull-left" style="padding: 5px;">筛选条件：</label>
							<span class="pull-left" style="margin-right: 5px;"> 
								<select
									class="chosen-select form-control" name="FMISACC"
									id="FMISACC" data-placeholder="请选择帐套"
									style="vertical-align: top; height: 32px; width: 150px;">
										<option value="">请选择帐套</option>
										<c:forEach items="${fmisacc}" var="fmi">
											<option value="${fmi.DICT_CODE}">${fmi.NAME}</option>
										</c:forEach>
								</select>
							</span> 
							<span  class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
								<div class="selectTree" id="selectTree" multiMode="true"
									allSelectable="false" noGroup="false"></div>
								<input type="text" id="RPT_DEPT" hidden></input>
							 </span>
							<span class="pull-left" style="margin-right: 5px;"> 
								<select
									class="chosen-select form-control" name="BILL_TYPE"
									id="BILL_TYPE" data-placeholder="请选择类型"
									style="vertical-align: top; height: 32px; width: 150px;">
										<option value="">请选择类型</option>
										<c:forEach items="${billTypeList}" var="billType">
											<option value="${billType.nameKey}"
												<c:if test="${pd.BILL_TYPE==billType.nameKey}">selected</c:if>>${billType.nameValue}</option>
										</c:forEach>
								</select>
							</span>
							
							<!-- <span style="margin-right: 5px;"> 
								<select class="chosen-select form-control"
									name="STATUS" id="STATUS" data-placeholder="请选状态"
									style="vertical-align: top; height: 32px; width: 150px;">
										<option value="">请选择状态</option>
										<option value="0">解封</option>
										<option value="1">封存</option>
								</select>
							</span> -->
							<button type="button" class="btn btn-info btn-xs"
								onclick="tosearch();">
								<i class="ace-icon fa fa-search bigger-110"></i>
							</button>
							
							<div class="pull-right">
								<button id="btnAdd" class="btn btn-success btn-xs"
									onclick="showQueryCondi()">
									<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>新增</span>
								</button>
								<button id="btnDelete" class="btn btn-danger btn-xs"
									onclick="showQueryCondi()">
									<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>删除</span>
								</button>
								<button id="btnEdit" class="btn btn-info btn-xs"
									onclick="showQueryCondi()">
									<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>编辑</span>
								</button>
							</div>
						</form>
					</div>
				
					<div class="row">
						<div class="col-xs-3">
							<div class="widget-box transparent">
								<div class="widget-header widget-header-flat">
									<h5 class="widget-title lighter">
										<i class="ace-icon fa fa-list orange"></i>
										组织机构
									</h5>

									<div class="widget-toolbar">
										<!-- <a href="#" data-action="collapse">
											<i class="ace-icon fa fa-chevron-up"></i>
										</a> -->
									</div>
								</div>

								<div class="widget-body">
									<div class="widget-main no-padding">
										<div class="nav-search" style="margin:10px 0px;">
											<span class="input-icon">
												<input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词" />
												<i class="ace-icon fa fa-search nav-search-icon"></i>
											</span>
											<button style="margin-bottom:3px;" class="btn btn-light btn-minier" onclick="searchs();"  title="检索">
												<i id="nav-search-icon" class="ace-icon fa fa-search bigger-120 nav-search-icon blue"></i>
												<!-- <i class="ace-icon fa fa-signal icon-only bigger-150"></i> -->
											</button>
										</div>
										
										<!-- <h4 class="smaller lighter green">
											<i class="ace-icon fa fa-list"></i>
											Sortable Lists
										</h4> -->
	
										<!-- #section:pages/dashboard.tasks -->
										<ul id="tasks" class="item-list">
											<li class="item-orange clearfix">
												<label class="inline">
													<input type="checkbox" class="ace" />
													<span class="lbl"> 辽河数码</span>
												</label>
											</li>
	
											<li class="item-red clearfix">
												<label class="inline">
													<input type="checkbox" class="ace" />
													<span class="lbl"> 中石油</span>
												</label>
											</li>
	
											<li class="item-default clearfix">
												<label class="inline">
													<input type="checkbox" class="ace" />
													<span class="lbl"> 辽河油田</span>
												</label>
											</li>
	
											<li class="item-blue clearfix">
												<label class="inline">
													<input type="checkbox" class="ace" />
													<span class="lbl"> 长城钻探</span>
												</label>
											</li>
	
											<li class="item-grey clearfix">
												<label class="inline">
													<input type="checkbox" class="ace" />
													<span class="lbl"> 研究院</span>
												</label>
											</li>
	
											<li class="item-green clearfix">
												<label class="inline">
													<input type="checkbox" class="ace" />
													<span class="lbl"> 工程院</span>
												</label>
											</li>
	
											<li class="item-pink clearfix">
												<label class="inline">
													<input type="checkbox" class="ace" />
													<span class="lbl"> Cleaning up</span>
												</label>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-9">
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
										<th class="center">手机号</th>
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
										<c:if test="${QX.cha == 1 }">
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
												<td class="center">${user.DEPARTMENT_NAME }</td>
												<td class="center">${user.PHONE }</td>
												<%-- <td class="center"><a title="发送电子邮件" style="text-decoration:none;cursor:pointer;" <c:if test="${QX.email == 1 }">onclick="sendEmail('${user.EMAIL }');"</c:if>>${user.EMAIL }&nbsp;<i class="ace-icon fa fa-envelope-o"></i></a></td> --%>
												<td class="center">${user.LAST_LOGIN}</td>
												<td class="center">${user.IP}</td>
												<td class="center">${user.BZ }</td>
												<td style="width: 60px;" class="center">
													<c:if test="${user.STATUS == '0' }"><span class="label label-important arrowed-in">停用</span></c:if>
													<c:if test="${user.STATUS == '1' }"><span class="label label-success arrowed">正常</span></c:if>
												</td>
												<td class="center">
													<c:if test="${QX.edit != 1 && QX.del != 1 }">
													<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
													</c:if>
													<div class="hidden-sm hidden-xs btn-group">
														<%-- <c:if test="${QX.FHSMS == 1 }">
														<a class="btn btn-xs btn-info" title='发送站内信' onclick="sendFhsms('${user.USERNAME }');">
															<i class="ace-icon fa fa-envelope-o bigger-120" title="发送站内信"></i>
														</a>
														</c:if>
														<c:if test="${QX.sms == 1 }">
														<a class="btn btn-xs btn-warning" title='发送短信' onclick="sendSms('${user.PHONE }');">
															<i class="ace-icon fa fa-envelope-o bigger-120" title="发送短信"></i>
														</a>
														</c:if> --%>
														<c:if test="${QX.edit == 1 }">
														<a class="btn btn-xs btn-success" title="编辑" onclick="editUser('${user.USER_ID}');">
															<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
														</a>
														</c:if>
														<%-- <c:if test="${QX.del == 1 }">
														<a class="btn btn-xs btn-danger" onclick="delUser('${user.USER_ID }','${user.USERNAME }');">
															<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
														</a>
														</c:if> --%>
													</div>
													<div class="hidden-md hidden-lg">
														<div class="inline pos-rel">
															<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
																<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
															</button>
															<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																<%-- <c:if test="${QX.FHSMS == 1 }">
																<li>
																	<a style="cursor:pointer;" onclick="sendFhsms('${user.USERNAME }');" class="tooltip-info" data-rel="tooltip" title="发送站内信">
																		<span class="blue">
																			<i class="ace-icon fa fa-envelope bigger-120"></i>
																		</span>
																	</a>
																</li>
																</c:if>
																<c:if test="${QX.sms == 1 }">
																<li>
																	<a style="cursor:pointer;" onclick="sendSms('${user.PHONE }');" class="tooltip-success" data-rel="tooltip" title="发送短信">
																		<span class="blue">
																			<i class="ace-icon fa fa-envelope-o bigger-120"></i>
																		</span>
																	</a>
																</li>
																</c:if> --%>
																<c:if test="${QX.edit == 1 }">
																<li>
																	<a style="cursor:pointer;" onclick="editUser('${user.USER_ID}');" class="tooltip-success" data-rel="tooltip" title="修改">
																		<span class="green">
																			<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																		</span>
																	</a>
																</li>
																</c:if>
																<%-- <c:if test="${QX.del == 1 }">
																<li>
																	<a style="cursor:pointer;" onclick="delUser('${user.USER_ID }','${user.USERNAME }');" class="tooltip-error" data-rel="tooltip" title="删除">
																		<span class="red">
																			<i class="ace-icon fa fa-trash-o bigger-120"></i>
																		</span>
																	</a>
																</li>
																</c:if> --%>
															</ul>
														</div>
													</div>
												</td>
											</tr>
										
										</c:forEach>
										</c:if>
										<c:if test="${QX.cha == 0 }">
											<tr>
												<td colspan="10" class="center">您无权查看</td>
											</tr>
										</c:if>
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
								<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>	
							</div>
						</div>
					</div>
						
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

//新增
function add(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="新增";
	 diag.URL = '<%=basePath%>user/goAddU.do';
	 diag.Width = 469;
	 diag.Height = 505;
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
	 diag.Height = 505;
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
