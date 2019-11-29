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
							<label class="pull-left" style="padding: 5px;">已创建问题：</label>
							<span class="pull-left" style="margin-right: 5px;"> 
								<select
									class="chosen-select form-control" name="FMISACC"
									id="FMISACC" data-placeholder="请选择问题"
									style="vertical-align: top; height: 32px; width: 200px;">
										<option value="">请选择问题</option>
										<c:forEach items="${fmisacc}" var="fmi">
											<option value="${fmi.DICT_CODE}">${fmi.NAME}</option>
										</c:forEach>
								</select>
							</span> 
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
						<div class="col-xs-4">
							<div class="widget-box transparent">
								<div class="widget-header widget-header-flat">
									<h5 class="widget-title lighter">
										<i class="ace-icon fa fa-list blue"></i>
										问题信息录入
									</h5>

									<div class="widget-toolbar">
										<!-- <a href="#" data-action="collapse">
											<i class="ace-icon fa fa-chevron-up"></i>
										</a> -->
									</div>
								</div>

								<div class="widget-body">
									<div class="widget-main no-padding">
										<%-- <div class="nav-search" style="margin:10px 0px;">
											<span class="input-icon">
												<input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词" />
												<i class="ace-icon fa fa-search nav-search-icon"></i>
											</span>
											<button style="margin-bottom:3px;" class="btn btn-light btn-minier" onclick="searchs();"  title="检索">
												<i id="nav-search-icon" class="ace-icon fa fa-search bigger-120 nav-search-icon blue"></i>
												<!-- <i class="ace-icon fa fa-signal icon-only bigger-150"></i> -->
											</button>
										</div> --%>
										
										<!-- <h4 class="smaller lighter green">
											<i class="ace-icon fa fa-list"></i>
											Sortable Lists
										</h4> -->
	
										<!-- #section:pages/dashboard.tasks -->
										<form action="problemAssign/savaProblemAssign.do" name="problemAssignForm" id="problemAssignForm" method="post">
											<div id="zhongxin" style="padding-top: 13px;">
												<!-- <div style="margin:20px 0px;">
													<span>问题单号：</span><span>201905230001</span>									
												</div>
												<hr /> -->
												<div style="margin:10px 0px;">
													<label for="form-field-1">问题单号</label>
													<input type="text" id="form-field-1" class="form-control" placeholder="自动生成"/>
												</div>
												
												<div style="margin:10px 0px;">
													<label for="form-field-2">问题标题</label>
													<input type="text" id="form-field-2" class="form-control" placeholder="请输入问题标题"/>
												</div>
												
												<div style="margin:10px 0px;">
													<label for="form-field-select-1">上报人</label>
													<select class="form-control" id="form-field-select-1">
														<option value=""></option>
														<option value="AL">Alabama</option>
														<option value="AK">Alaska</option>
													</select>
												</div>
												
												<div style="margin:10px 0px;">
													<label for="form-field-select-1">受理人</label>
													<select class="form-control" id="form-field-select-1">
														<option value=""></option>
														<option value="AL">Alabama</option>
														<option value="AK">Alaska</option>
													</select>
												</div>
												
												<div style="margin:10px 0px;">
													<label for="form-field-select-2">问题模块</label>
													<select class="form-control" id="form-field-select-2">
														<option value=""></option>
														<option value="AL">ERP系统</option>
														<option value="AK">财务系统</option>
													</select>
												</div>
												
												<div style="margin:10px 0px;">
													<label for="form-field-select-2">问题类型</label>
													<select class="form-control" id="form-field-select-2">
														<option value=""></option>
														<option value="AL">ERP问题</option>
														<option value="AK">财务问题</option>
													</select>
												</div>
												
												<div style="margin:10px 0px;">
													<label for="form-field-2">问题标签（多个标签使用空格分隔）</label>
													<input type="text" id="form-field-2" class="form-control" placeholder="请输入标签"/>
												</div>
												
												<div style="margin:10px 0px;">
													<label for="form-field-select-3">优先级</label>
													<select class="form-control" id="form-field-select-3">
														<option value=""></option>
														<option value="AL">紧急</option>
														<option value="AK">一般</option>
													</select>
												</div>
												<%-- <input type="number" name="PHONE" id="PHONE"  value="${pd.PHONE }"  maxlength="32" placeholder="这里输入手机号" title="手机号" style="width:98%;"/> --%>
												
												<hr />
												<div>
													<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
													<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
												</div>		
											</div>
											<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
										</form>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-8">
							<div class="no-border">
								<ul class="nav nav-tabs" id="recent-tab">
									<li class="active">
										<a data-toggle="tab" href="#detail-tab">动态关联</a>
									</li>
	
									<li>
										<a data-toggle="tab" href="#assigh-tab">手动检索</a>
									</li>
								</ul>
							</div>
							<div class="tab-content padding-8">
								<div id="assigh-tab" class="tab-pane">
									<div class="nav-search" style="margin:10px 0px;">
										<span class="input-icon" style="width:92%">
											<input style="width:100%" class="nav-search-input " autocomplete="off" id="nav-search-input" type="text" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词" />
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
										<button style="margin-bottom:3px;" class="btn btn-light btn-minier" onclick="searchs();"  title="检索">
											<i id="nav-search-icon" class="ace-icon fa fa-search bigger-120 nav-search-icon blue"></i>
											<!-- <i class="ace-icon fa fa-signal icon-only bigger-150"></i> -->
										</button>
									</div>
									<ul id="tasks" class="item-list">
										<li class="item-grey clearfix">
											<div>
												<label class="inline" style="margin-bottom:5px;">
													<span class="list-item-value-title">测试问题1</span>
												</label>
											<div>
											<div>
												<label class="inline">
													<span class="list-item-info"> 内容：</span>
													<span class="list-item-value">中国石油辽河油田兴隆台采油厂×××××××××××××××××××××××××××××××××××××××××××××</span>
												</label>
											</div>
											
											<div>
												<label class="inline">
													<span class="list-item-info"> 作者：</span>
													<span class="list-item-value">张三</span>
												</label>
												<label class="inline">
													<span class="list-item-info"><i class="ace-icon fa fa-clock-o"></i> 时间：</span>
													<span class="list-item-value red">
														<span>2019-07-23</span>
													</span>
												</label>
											</div>
											<!-- <div class="time">
												<i class="ace-icon fa fa-clock-o"></i>
												<span class="green">6 min</span>
											</div> -->
										</li>
		
										<li class="item-grey clearfix">
											<div>
												<label class="inline" style="margin-bottom:5px;">
													<span class="list-item-value-title">测试问题2</span>
												</label>
											<div>
											<div>
												<label class="inline">
													<span class="list-item-info"> 内容：</span>
													<span class="list-item-value">中国石油辽河油田兴隆台采油厂×××××××××××××××××××××××××××××××××××××××××××××</span>
												</label>
											</div>
											
											<div>
												<label class="inline">
													<span class="list-item-info"> 作者：</span>
													<span class="list-item-value green">李四</span>
												</label>
												<label class="inline">
													<span class="list-item-info"><i class="ace-icon fa fa-clock-o"></i> 时间：</span>
													<span class="list-item-value red">
														<span>2019-07-23</span>
													</span>
												</label>
											</div>
										</li>
									</ul>		
									<div class="page-header position-relative">
										<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>	
									</div>
								</div>
								
								<div id="detail-tab" class="tab-pane active">
									<ul id="tasks" class="item-list">
										<li class="item-grey clearfix">
											<div>
												<label class="inline" style="margin-bottom:5px;">
													<span class="list-item-value-title">测试问题1</span>
												</label>
											<div>
											<div>
												<label class="inline">
													<span class="list-item-info"> 内容：</span>
													<span class="list-item-value">中国石油辽河油田兴隆台采油厂×××××××××××××××××××××××××××××××××××××××××××××</span>
												</label>
											</div>
											
											<div>
												<label class="inline">
													<span class="list-item-info"> 作者：</span>
													<span class="list-item-value">张三</span>
												</label>
												<label class="inline">
													<span class="list-item-info"><i class="ace-icon fa fa-clock-o"></i> 时间：</span>
													<span class="list-item-value red">
														<span>2019-07-23</span>
													</span>
												</label>
											</div>

										</li>
		
										<li class="item-grey clearfix">
											<div>
												<label class="inline" style="margin-bottom:5px;">
													<span class="list-item-value-title">测试问题2</span>
												</label>
											<div>
											<div>
												<label class="inline">
													<span class="list-item-info"> 内容：</span>
													<span class="list-item-value">中国石油辽河油田兴隆台采油厂×××××××××××××××××××××××××××××××××××××××××××××</span>
												</label>
											</div>
											
											<div>
												<label class="inline">
													<span class="list-item-info"> 作者：</span>
													<span class="list-item-value green">李四</span>
												</label>
												<label class="inline">
													<span class="list-item-info"><i class="ace-icon fa fa-clock-o"></i> 时间：</span>
													<span class="list-item-value red">
														<span>2019-07-23</span>
													</span>
												</label>
											</div>
										</li>
									</ul>		
									<div class="page-header position-relative">
										<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>	
									</div>
								</div>
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
