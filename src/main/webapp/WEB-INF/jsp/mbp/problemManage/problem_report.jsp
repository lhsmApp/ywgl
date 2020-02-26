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

<!-- 树形下拉框start -->
<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
<link rel="stylesheet" type="text/css"
	href="plugins/selectZtree/import_fh.css" />
<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet"
	href="plugins/selectZtree/ztree/ztree.css"></link>
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
						<div class="col-xs-4">
						<form action='mbp/getPageList.do?ProOperType=PROBLEM_INFO'></form>
						<!-- 检索  -->
						<!-- <form style="margin:5px;" method="post" name="problemForm" id="problemForm"> -->
							<div class="nav-search" style="margin:10px 0px;">
								<span class="input-icon" style="width:86%">
									<input style="width:100%" class="nav-search-input" autocomplete="off" id="keywords" type="text" name="keywords" value="${pd.keywords }" placeholder="这里输入问题标题" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
								<button style="margin-bottom:3px;" class="btn btn-light btn-minier" onclick="searchs();"  title="检索">
									<i id="nav-search-icon" class="ace-icon fa fa-search bigger-120 nav-search-icon blue"></i>
									<!-- <i class="ace-icon fa fa-signal icon-only bigger-150"></i> -->
								</button>
							</div>
							
							<ul id="problemList" class="item-list">
								
							</ul>						
							<%-- <div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div> --%>
							<div id="page" class="pagination" style="float: right;padding-top: 5px;margin-top: 0px;font-size:12px;"></div>
						<!-- </form> -->
					</div>
					<!-- /.col4 -->
					
					<div class="col-xs-8">
						<div class="widget-box transparent" id="recent-box">
							<div class="widget-header">
								<h4 class="widget-title lighter smaller blue">
									<i class="ace-icon fa fa-rss orange"></i><span id="currentTabTitle">详情</span>
								</h4>
		
								<div class="widget-toolbar no-border">
									<ul class="nav nav-tabs" id="problem-tab">
										
										
										<li class="active" tag="report-tab">
											<a data-toggle="tab" href="#report-tab">提报</a>
										</li>
										
													
										<li tag="answer-tab">
											<a data-toggle="tab" href="#answer-tab">回复</a>
										</li>
		
										
									</ul>
								</div>
							</div>
		
							<div class="widget-body">
								<div class="widget-main padding-4">
									<div class="tab-content padding-8">
					
										<!-- 问题提报 -->
										<div id="report-tab" class="tab-pane active">
											<h4>
												<!-- <i class="ace-icon fa fa-list"></i>
												Sortable Lists -->
												<button id="btnAdd" class="btn btn-success btn-xs"
													onclick="add()">
													<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>新增</span>
												</button>
												<button id="btnEdit" class="btn btn-warning btn-xs"
													onclick="edit()">
													<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>编辑</span>
												</button>
												<button id="btnSave" class="btn btn-info btn-xs"
													onclick="save()">
													<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>保存</span>
												</button>
												<button id="btnDelete" class="btn btn-danger btn-xs"
													onclick="del()">
													<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>作废</span>
												</button>
												<button id="btnCommit" class="btn btn-primary btn-xs"
													onclick="commit()">
													<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>提交</span>
												</button>
												<button id="btnCancel" class="btn btn-inverse btn-xs"
													onclick="cancel()">
													<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>取消</span>
												</button>
											</h4>
											<div class="row">
												<div class="col-xs-5">
													<form name="problemReportForm" id="problemReportForm" method="post">
														<input type="hidden" name="PRO_CODE" id="form-field-pro-code" value="${pd.PRO_CODE }"/>
														<div id="zhongxin" style="padding-top: 13px;">
															<div style="margin:10px 0px;">
																<label for="form-field-pro-title">问题标题</label>
																<input type="text" name="PRO_TITLE" id="form-field-pro-title" class="form-control" placeholder="请输入问题标题"/>
															</div>
															
															<div style="margin:10px 0px;">
																<label for="form-field-pro-report-user">上报人</label>
																<select class="form-control" name="PRO_REPORT_USER" id="form-field-pro-report-user" disabled='true'>
																	<option value=""></option>
																	<c:forEach items="${userList}" var="user">
																		<!-- <option value="AL">Alabama</option>
																		<option value="AK">Alaska</option> -->
																		<%-- <option value="${system.DICT_CODE }" <c:if test="${system.DICT_CODE == pd.DICT_CODE }">selected</c:if>>${system.NAME }</option> --%>
																		<option value="${user.USER_ID}">${user.NAME}</option>
																	</c:forEach>
																</select>
															</div>
														
															<div style="margin:10px 0px;">
																<label for="form-field-pro-accept-user">受理人</label>
																<select class="form-control" name="PRO_ACCEPT_USER" id="form-field-pro-accept-user">
																	<option value=""></option>
																	<c:forEach items="${receiveUserList}" var="user">
																		<!-- <option value="AL">Alabama</option>
																		<option value="AK">Alaska</option> -->
																		<%-- <option value="${system.DICT_CODE }" <c:if test="${system.DICT_CODE == pd.DICT_CODE }">selected</c:if>>${system.NAME }</option> --%>
																		<option value="${user.USER_ID}">${user.NAME}</option>
																	</c:forEach>
																</select>
															</div>
															
															<div style="margin:10px 0px;">
																<label for="form-field-pro-sys-type">问题模块</label>
																<select class="form-control" name="PRO_SYS_TYPE" id="form-field-pro-sys-type">
																	<option value=""></option>
																	<c:forEach items="${systemList}" var="system">
																		<!-- <option value="AL">Alabama</option>
																		<option value="AK">Alaska</option> -->
																		<%-- <option value="${system.DICT_CODE }" <c:if test="${system.DICT_CODE == pd.DICT_CODE }">selected</c:if>>${system.NAME }</option> --%>
																		<option value="${system.DICT_CODE}">${system.NAME}</option>
																	</c:forEach>
																</select>
															</div>
															
															<div style="margin:10px 0px;">
																<label for="form-field-pro-type-id">问题类型</label>
																<input type="hidden" name="PRO_TYPE_ID" id="form-field-pro-type-id" />
																<div class="selectTree" id="selectTree" style="float:none;display:block;"></div>
																
															</div>
															
															<div style="margin:10px 0px;">
																<label for="form-field-pro-tag">问题标签（多个标签使用空格分隔）</label>
																<input type="text" name="PRO_TAG" id="form-field-pro-tag" class="form-control" placeholder="请输入标签"/>
															</div>
															
															<div style="margin:10px 0px;">
																<label for="form-field-pro-priority">优先级</label>
																<select class="form-control" name="PRO_PRIORITY" id="form-field-pro-priority">
																	<option value=""></option>
																	<c:forEach items="${proPriorityList}" var="priority">
																		<option value="${priority.nameKey}">${priority.nameValue}</option>
																	</c:forEach>
																</select>
															</div>
															
															<div style="margin:10px 0px;">
																<label for="form-field-pro-resolve-time">问题解决时间</label>
																<!-- <input type="text" name="PRO_RESOLVE_TIME" id="form-field-pro-resolve-time" class="form-control" placeholder="请输入问题解决时间"/> -->
																<div class="input-group">
																<input type="text" name="PRO_RESOLVE_TIME" id="form-field-pro-resolve-time" class="form-control date-picker" placeholder="请输入问题解决时间" data-date-format="yyyy-mm-dd" />
																<span class="input-group-addon">
																	<i class="fa fa-calendar bigger-110"></i>
																</span>
																</div>
															</div>
															<textarea name="PRO_CONTENT" id="form-field-pro-content" style="display:none" ></textarea>
															<!-- <div style="margin:10px 0px;">
																<label for="editor">问题描述</label>
																<script id="editor" type="text/plain" style="width:100%;height:259px;"></script>
															</div> -->
															
															<%-- <input type="number" name="PHONE" id="PHONE"  value="${pd.PHONE }"  maxlength="32" placeholder="这里输入手机号" title="手机号" style="width:98%;"/> --%>
															
															<!--<hr />
															<div>
																<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
																<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
															</div> -->		
														</div>
													</form>
												</div>
												<div class="col-xs-7">
													<div class="no-border" style="margin-top:30px;">
														<ul class="nav nav-tabs" id="recent-tab">
															<li class="active">
																<a data-toggle="tab" href="#dt-tab">动态关联</a>
															</li>
							
															<li>
																<a data-toggle="tab" href="#sd-tab">手动检索</a>
															</li>
														</ul>
													</div>
													<div class="tab-content padding-8">
														<div id="sd-tab" class="tab-pane">
															<div class="nav-search" style="margin:10px 0px;">
																<span class="input-icon" style="width:90%">
																	<input style="width:100%" class="nav-search-input " autocomplete="off" id="keywordsKnowledge" type="text" value="" placeholder="这里输入关键词" />
																	<i class="ace-icon fa fa-search nav-search-icon"></i>
																</span>
																<button style="margin-bottom:3px;" class="btn btn-light btn-minier" onclick="searchKnowledgeList();"  title="检索">
																	<i id="nav-search-icon" class="ace-icon fa fa-search bigger-120 nav-search-icon blue"></i>
																	<!-- <i class="ace-icon fa fa-signal icon-only bigger-150"></i> -->
																</button>
															</div>
															<ul id="ulSd" class="item-list">
																
															</ul>		
															<div class="page-header position-relative">
																<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>	
															</div>
														</div>
														
														<div id="dt-tab" class="tab-pane active">
															<ul id="ulDt" class="item-list">
																<!-- <li class="item-grey clearfix">
																	<div>
																		<label class="inline" style="margin-bottom:5px;">
																			<span class="list-item-value-title">测试问题1</span>
																		</label>
																		<div class="pull-right">
																			<button class="btn btn-xs btn-yellow" onClick="viewKnowledgeDetail(1)">详情</button>
																		</div>
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
																</li> -->
															</ul>
																	
															<div class="page-header position-relative">
																<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>	
															</div>
														</div>
													</div>
												</div>
				
											</div>
											<div class="row">
												<div style="margin:10px 12px;">
													<label for="editor">问题描述</label>
													<script id="editor" type="text/plain" style="width:100%;height:259px;"></script>
												</div>
											</div>
											<div class="row">
												<div style="margin:10px 12px;">
													<h4>
														<button id="btnAddProInfoAttachment" class="btn btn-success btn-xs"
															onclick="addProAttachmentByType('PROBLEM_INFO')">
															<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>上传附件</span>
														</button>
													</h4>
													<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
														<thead>
															<tr>
																<th class="center" style="width:50px;">序号</th>
																<th class="center">附件名</th>
																<th class="center">附件说明</th>
																<th class="center">附件大小</th>
																<th class="center">上传人</th>
																<th class="center">上传日期</th>
																<th class="center">操作</th>
															</tr>
														</thead>
																				
														<tbody id="tbodyProInfoAttachment">
															
														</tbody>
													</table>
												</div>
											</div>
										</div>
								
										<!-- 问题回复 -->
										<div id="answer-tab" class="tab-pane">
											<form id="problemAnswerForm" method="post">
												<!-- <input type="hidden" id="ff-answer-answer-id"/> -->
												<div>
													<div style="margin:10px 0px;">
														<label>选择信息</label>
														<select class="form-control" id="ff-answer-info" onChange="answerChange()">
						
														</select>
													</div>
													
													<div style="margin:10px 0px;">
														<!-- <label for="editor">回复信息</label> -->
														<script id="editorAnswer" type="text/plain" style="width:100%;height:259px;"></script>
													</div>
												</div>
											</form>
											
											<div >
												<h4>
													<!-- <button id="btnAddProAnswerAttachment" class="btn btn-success btn-xs"
														onclick="addProAttachmentByType('PROBLEM_ANSWER')">
														<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>附件</span>
													</button> -->
													<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>附件</span>
												</h4>
												<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
													<thead>
														<tr>
															<th class="center" style="width:50px;">序号</th>
															<th class="center">附件名</th>
															<th class="center">附件说明</th>
															<th class="center">附件大小</th>
															<th class="center">上传人</th>
															<th class="center">上传日期</th>
															<th class="center">操作</th>
														</tr>
													</thead>
																			
													<tbody id="tbodyProAnswerAttachment">
														
													</tbody>
												</table>
											</div>
										</div>
										
								</div>
							</div>
						</div>	
					</div>
					<!-- /.col8 -->
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
	
	<!-- 步骤条 -->
	<script src="static/ace/js/fuelux/fuelux.wizard.js"></script>
	<script src="static/ace/js/ace/elements.wizard.js"></script>
	
	<!-- ace scripts -->
	<!-- <script src="static/ace/js/ace.js"></script> -->
	<!-- <script src="static/ace/js/ace/ace.js"></script> -->
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	
	<!-- 编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
	<!-- 编辑框-->
	</body>

<script type="text/javascript">
var currentItem;

$(top.hangge());

/**
 * html文档加载完成后执行初始化方法，初始化界面元素样式，初始化基础数据，列表等信息
 */
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
	
	/* 加载进度条 */
	$('#modal-wizard-container').ace_wizard();
	
	/* 加载富文本 */
	setTimeout("ueditor()",500);
	
	//初始化字段为只读
	initFieldDisabled(true);
	
	//初始化问题列表数据
	initList('<%=basePath%>mbp/getPageList.do?ProOperType=PROBLEM_INFO');
	
	$("#problem-tab a").click(function(e){
		$('#currentTabTitle').text($(this).text());
		e.preventDefault();
    });
});

/**
 * 加载富文本 
 */
function ueditor(){
	var ue = UE.getEditor('editor');
	//UE.getEditor('editor').setDisabled();
	
	var ueAnswer = UE.getEditor('editorAnswer');
}

/**
 * 初始化字段为只读
 */
function initFieldDisabled(disabled){
	//$("#form-field-pro-code").attr("readonly",true);
	$("#form-field-pro-title").attr("disabled",disabled);
	//$("#form-field-pro-report-user").attr("disabled",disabled);
	$("#form-field-pro-accept-user").attr("disabled",disabled);
	$("#form-field-pro-sys-type").attr("disabled",disabled);
	$("#form-field-pro-type-id").attr("disabled",disabled);
	$("#form-field-pro-tag").attr("disabled",disabled);
	$("#form-field-pro-priority").attr("disabled",disabled);
	$("#form-field-pro-resolve-time").attr("disabled",disabled);
	$("#form-field-pro-content").attr("disabled",disabled);
	
	UE.getEditor('editor').addListener("ready", function () {
		UE.getEditor('editor').setDisabled();
	});
	
	if(UE.getEditor('editor').isReady){
		if(disabled){
			UE.getEditor('editor').setDisabled();
		}else{
			UE.getEditor('editor').setEnabled();
		}
	}
}

/**
 * 初始化列表信息
 */
function initList(url){
	$("#problemList li").remove(); 
	top.jzts();
	var keywords = $("#keywords").val();
	$.ajax({
			type: "POST",
			url: url,
	    	data: {keywords:keywords},
			dataType:'json',
			cache: false,
			success: function(data){
				$("#page").html(data.pageHtml);
				var first;
				if(data.rows){
					$.each(data.rows, function(i, item){
						if(i==0){
							first=item;
						}
						addItem(item); 
				 	});
					if(first){
						getDetail(first.PRO_CODE);
					}
				}
				else{
					addEmpty();
				}
				top.hangge();
			}
	});
}

/**
 * 增加Item数据
 */
function addItem(item){
	var htmlProState='';
	if(item.PRO_STATE=="2"){
		htmlProState='<span class="label label-xs label-info">'+item.PRO_STATE_NAME+'</span>'
	}else if(item.PRO_STATE=="3"){
		htmlProState='<span class="label label-xs label-warning">'+item.PRO_STATE_NAME+'</span>'
	}else if(item.PRO_STATE=="4"){
		htmlProState='<span class="label label-xs label-inverse">'+item.PRO_STATE_NAME+'</span>'
	}else{
		htmlProState='<span class="label label-xs label-success">'+item.PRO_STATE_NAME+'</span>'
	}
	var htmlItem='<li class="item-grey clearfix list-item-hover" onclick=getDetail("'+item.PRO_CODE+'")>'
	+'<input name="PRO_CODE" id="PRO_CODE" type="hidden" value="'+item.PRO_CODE+'" />'
		+'<div>'
			+'<label class="inline" style="margin-bottom:5px;">'
				+'<span class="list-item-value-title">'+item.PRO_TITLE+'</span>'
			+'</label>'
		+'<div>'
		+'<div>'
			+'<label class="inline">'
				+'<span class="list-item-info"> 单位：</span>'
				+'<span class="list-item-value">'+item.PRO_DEPART_NAME+'</span>'
			+'</label>'
			+'<label class="inline pull-right">'
				+'<span class="list-item-info"> 系统：</span>'
				+'<span class="list-item-value">'+item.PRO_SYS_TYPE_NAME+'</span>'
			+'</label>'
		+'</div>'
		
		+'<div>'
			+'<label class="inline">'
				+'<span class="list-item-info"> 处理状态：</span>'
				/* +'<span class="list-item-value green">'+item.PRO_STATE_NAME+'</span>' */
				+htmlProState
				+'</label>'
			+'<label class="inline pull-right">'
				+'<span class="list-item-info"> 问题类型：</span>'
				+'<span class="list-item-value">'+item.PRO_TYPE_NAME+'</span>'
			+'</label>'
		+'</div>'
		+'<div class="time">'
			+'<i class="ace-icon fa fa-clock-o"></i>'
			+'<span class="grey">'+item.UPDATE_DATE+'</span>'
		+'</div>'
	+'</li>';
	$("#problemList").append(htmlItem);
}

/**
 * 增加空数据提示
 */
function addEmpty(){
	var htmlEmpty='<li class="item-grey clearfix">'
		+'<div>'
			+'<label class="inline" style="margin-bottom:5px;">'
				+'<span class="list-item-value-title">没有相关数据</span>'
			+'</label>'
		+'<div>'
	+'</li>';
	$("#problemList").append(htmlEmpty);
}


/**
 * 获取明细信息
 */
function getDetail(problemCode){
	/* $("#problem-tab li").each(function(){
		var item = this;
		if($(item).attr("tag")=="detail-tab"){
			$(this).tab("show");
			$(this).click();
		}
	}); */
	
	/* $("#report-tab").removeClass("active");
	$("#assign-tab").removeClass("active");
	$("#close-tab").removeClass("active");
	$("#detail-tab").addClass("active"); */
	
	if(event){
		$("#problemList li").each(function(){
			var item = this;
			$(item).removeClass("bc-light-orange");
		}); 
		$($(event.srcElement).closest('li')).addClass("bc-light-orange");
	}else{
		$("#problemList li").first().addClass("bc-light-orange");
		//$($(event.srcElement).parents('li')).addClass("bc-light-orange");
	}
	//add();//清空原有问题提报中信息
	initFieldDisabled(true);
	
	$.ajax({
		type: "GET",
		url: '<%=basePath%>mbp/getDetail.do?PRO_CODE='+problemCode,
		dataType:'json',
		cache: false,
		success: function(data){
			 if(data){
				 currentItem=data;
				 
				 /* 设置提报中的字段动态获取值 */
				 setReportFieldValue(data);
				 
				 /* 获取提报中附件信息 */
				 getProAttachment("PROBLEM_INFO");
				 
				 /* 获取回复信息 */
				 getProAnswers();
				 
				 
			 }
			 top.hangge();
		}
	});
}

//检索
function searchs(){
	initList('<%=basePath%>mbp/getPageList.do?ProOperType=PROBLEM_INFO');
	//$("#problemForm").submit();
}

/**
 * 保存
 */
function save(){
	if ($("#form-field-pro-title").attr('disabled') == 'disabled') {
		$("#btnSave").tips({
			side : 3,
			msg : '请先编辑后再保存',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnSave").focus();
		return false;
	}
	
	if ($.trim($("#form-field-pro-title").val()) == "") {
		$("#form-field-pro-title").tips({
			side : 3,
			msg : '请输入问题标题',
			bg : '#AE81FF',
			time : 2
		});
		$("#form-field-pro-title").focus();
		return false;
	}
	if ($("#form-field-pro-type-id").val()==null||$("#form-field-pro-type-id").val()=="") {
		$("#selectTree").tips({
			side : 3,
			msg : '请选择问题类型',
			bg : '#AE81FF',
			time : 2
		});
		$("#selectTree").focus();
		return false;
	}
	
	var content;
	var arr = [];
    arr.push(UE.getEditor('editor').getContent());
    content=arr.join("");
	$("#form-field-pro-content").val(content);
	
	top.jzts();
	var proCode=$("#form-field-pro-code").val();//问题单号
	var proTitle=$("#form-field-pro-title").val();//问题标题
	var proReportUser=$("#form-field-pro-report-user").val();//上报人
	var proAcceptUser=$("#form-field-pro-accept-user").val();//受理人
	//上报单位
	var proSysType=$("#form-field-pro-sys-type").val();//系统类型
	var proTypeID=$("#form-field-pro-type-id").val();//问题类型
	var proTag=$("#form-field-pro-tag").val();//问题标签
	var proPriority=$("#form-field-pro-priority").val();//优先级
	var proResolveTime=$("#form-field-pro-resolve-time").val();//问题解决时间
	var proContent=$("#form-field-pro-content").val();//问题描述
	$.ajax({
		type: "POST",
		url: '<%=basePath%>mbp/save.do',
		data:{PRO_CODE:proCode,PRO_TITLE:proTitle,PRO_REPORT_USER:proReportUser,PRO_ACCEPT_USER:proAcceptUser,PRO_SYS_TYPE:proSysType,PRO_TYPE_ID:proTypeID,PRO_TAG:proTag,PRO_PRIORITY:proPriority,PRO_RESOLVE_TIME:proResolveTime,PRO_CONTENT:proContent},
    	dataType:'json',
		cache: false,
		success: function(response){
			if(response.code==0){
				$(top.hangge());//关闭加载状态
				$("#btnSave").tips({
					side:3,
		            msg:'保存任务成功',
		            bg:'#009933',
		            time:3
		        });
				initList('<%=basePath%>mbp/getPageList.do?ProOperType=PROBLEM_INFO');
			}else{
				$(top.hangge());//关闭加载状态
				$("#btnSave").tips({
					side:3,
		            msg:'保存任务失败,'+response.message,
		            bg:'#cc0033',
		            time:3
		        });
			}
		},
    	error: function(response) {
    		var msgObj=JSON.parse(response.responseText);
    		$(top.hangge());//关闭加载状态
			$("#btnSave").tips({
				side:3,
	            msg:'保存任务失败,'+msgObj.message,
	            bg:'#cc0033',
	            time:3
	        });
    	}
	});
}

/**
 * 增加
 */
function add(){
	$("#problemList li").each(function(){
		var item = this;
		$(item).removeClass("bc-light-orange");
	}); 
	currentItem=null;
	
	initFieldDisabled(false);
	
	$("#form-field-pro-code").val("");
	$("#form-field-pro-title").val("");
	$("#form-field-pro-report-user").val(${currentUser});
	$("#form-field-pro-accept-user").val("");
	$("#form-field-pro-sys-type").val("");
	$("#form-field-pro-type-id").val("");
	$("#selectTree2_input").val("");
	$("#form-field-pro-tag").val("");
	$("#form-field-pro-priority").val("");
	$("#form-field-pro-resolve-time").val("");
	$("#form-field-pro-content").val("");
	UE.getEditor('editor').setContent("");
	
	$("#tbodyProInfoAttachment").html('');
}

/**
 * 编辑
 */
function edit(){
	if(currentItem.PRO_STATE=='2'){//已提交
		$("#btnEdit").tips({
			side : 3,
			msg : '当前问题"已提交"，不能再编辑',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnEdit").focus();
		return false;
	}else if(currentItem.PRO_STATE=='3'){//受理中
		$("#btnEdit").tips({
			side : 3,
			msg : '当前问题"受理中"，不能再编辑',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnEdit").focus();
		return false;
	}else if(currentItem.PRO_STATE=='4'){//已关闭
		$("#btnEdit").tips({
			side : 3,
			msg : '当前问题"已关闭"，不能再编辑',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnEdit").focus();
		return false;
	}
	
	initFieldDisabled(false);
	
	//setReportFieldValue(currentItem);
}

/**
 * 设置提报中字段值
 */
function setReportFieldValue(item){
	$("#form-field-pro-code").val(item.PRO_CODE);
	$("#form-field-pro-title").val(item.PRO_TITLE);
	$("#form-field-pro-report-user").val(item.PRO_REPORT_USER);
	$("#form-field-pro-accept-user").val(item.PRO_ACCEPT_USER);
	$("#form-field-pro-sys-type").val(item.PRO_SYS_TYPE);
	$("#form-field-pro-type-id").val(item.PRO_TYPE_ID);
	var proTypeName="";
	if(item.PRO_TYPE_ID!=null&&item.PRO_TYPE_ID!=""){
		proTypeName=item.PRO_TYPE_NAME;
	}else{
		proTypeName="请选择问题类型"
	}
	$("#selectTree2_input").val(proTypeName);
	$("#form-field-pro-tag").val(item.PRO_TAG);
	$("#form-field-pro-priority").val(item.PRO_PRIORITY);
	$("#form-field-pro-resolve-time").val(item.PRO_RESOLVE_TIME);
	$("#form-field-pro-content").val(item.PRO_CONTENT);
	
	
	
	UE.getEditor('editor').addListener("ready", function () {
		UE.getEditor('editor').setContent(item.PRO_CONTENT);
	});
	if(UE.getEditor('editor').isReady&&item.PRO_CONTENT){
    	UE.getEditor('editor').setContent(item.PRO_CONTENT);
	}
}

/**
 * 作废
 */
function del(){
	if(currentItem.PRO_STATE=='2'){//已提交
		$("#btnDelete").tips({
			side : 3,
			msg : '当前问题"已提交"，不能再作废',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnDelete").focus();
		return false;
	}else if(currentItem.PRO_STATE=='3'){//受理中
		$("#btnDelete").tips({
			side : 3,
			msg : '当前问题"受理中"，不能再作废',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnDelete").focus();
		return false;
	}else if(currentItem.PRO_STATE=='4'){//已关闭
		$("#btnDelete").tips({
			side : 3,
			msg : '当前问题"已关闭"，不能再作废',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnDelete").focus();
		return false;
	}
	
	bootbox.confirm("确定要作废当前问题吗?", function(result) {
		if(result) {
			top.jzts();
			var url = "<%=basePath%>mbp/delete.do?PRO_CODE="+currentItem.PRO_CODE;
			$.get(url,function(data){
				initList('<%=basePath%>mbp/getPageList.do?ProOperType=PROBLEM_INFO');
			});
		};
	});
}

/**
 * 提交
 */
function commit(){
	if(currentItem.PRO_STATE=='2'){//已提交
		$("#btnCommit").tips({
			side : 3,
			msg : '当前问题"已提交"，不能再提交',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnCommit").focus();
		return false;
	}else if(currentItem.PRO_STATE=='3'){//受理中
		$("#btnCommit").tips({
			side : 3,
			msg : '当前问题"受理中"，不能再提交',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnCommit").focus();
		return false;
	}else if(currentItem.PRO_STATE=='4'){//已关闭
		$("#btnCommit").tips({
			side : 3,
			msg : '当前问题"已关闭"，不能再提交',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnCommit").focus();
		return false;
	}
	
	var proCode=currentItem.PRO_CODE;//问题单号
	top.jzts();
	
	$.ajax({
		type: "POST",
		url: '<%=basePath%>mbp/commit.do',
		data:{PRO_CODE:proCode},
    	dataType:'json',
		cache: false,
		success: function(response){
			if(response.code==0){
				$(top.hangge());//关闭加载状态
				$("#btnCommit").tips({
					side:3,
		            msg:'提交成功',
		            bg:'#009933',
		            time:3
		        });
				initList('<%=basePath%>mbp/getPageList.do?ProOperType=PROBLEM_INFO');
			}else{
				$(top.hangge());//关闭加载状态
				$("#btnCommit").tips({
					side:3,
		            msg:'提交失败,'+response.message,
		            bg:'#cc0033',
		            time:3
		        });
			}
		},
    	error: function(response) {
    		$(top.hangge());//关闭加载状态
    		var msgObj=JSON.parse(response.responseText);
			$("#btnCommit").tips({
				side:3,
	            msg:'提交失败,'+msgObj.message,
	            bg:'#cc0033',
	            time:3
	        });
    	}
	});
}

/**
 * 取消提交
 */
function cancel(){
	if(currentItem.PRO_STATE=='1'){//新发起
		$("#btnCancel").tips({
			side : 3,
			msg : '当前问题"新发起"，不能取消',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnCancel").focus();
		return false;
	}else if(currentItem.PRO_STATE=='3'){//受理中
		$("#btnCancel").tips({
			side : 3,
			msg : '当前问题"受理中"，不能再取消',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnCancel").focus();
		return false;
	}else if(currentItem.PRO_STATE=='4'){//已关闭
		$("#btnCancel").tips({
			side : 3,
			msg : '当前问题"已关闭"，不能再取消',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnCancel").focus();
		return false;
	}

	
	var proCode=currentItem.PRO_CODE;//问题单号
	top.jzts();
	
	$.ajax({
		type: "POST",
		url: '<%=basePath%>mbp/cancel.do',
		data:{PRO_CODE:proCode},
    	dataType:'json',
		cache: false,
		success: function(response){
			if(response.code==0){
				$(top.hangge());//关闭加载状态
				$("#btnCancel").tips({
					side:3,
		            msg:'取消提交成功',
		            bg:'#009933',
		            time:3
		        });
				initList('<%=basePath%>mbp/getPageList.do?ProOperType=PROBLEM_INFO');
			}else{
				$(top.hangge());//关闭加载状态
				$("#btnCancel").tips({
					side:3,
		            msg:'取消提交失败,'+response.message,
		            bg:'#cc0033',
		            time:3
		        });
			}
		},
    	error: function(response) {
    		$(top.hangge());//关闭加载状态
    		var msgObj=JSON.parse(response.responseText);
			$("#btnCancel").tips({
				side:3,
	            msg:'取消提交失败,'+msgObj.message,
	            bg:'#cc0033',
	            time:3
	        });
    	}
	});
}

/**
 * 获取问题附件信息
 */
function getProAttachment(attachmentType){
	var billCode;
	if(attachmentType=="PROBLEM_INFO"){
		billCode=currentItem.PRO_CODE;//问题单号
		$("#tbodyProInfoAttachment").html('');
	}else if(attachmentType=="PROBLEM_ANSWER"){
		/* var billCode=$("#ff-answer-info").val();
		if(billCode == null){
			billCode='';
		} */
		billCode=currentItem.PRO_CODE;//问题单号
		$("#tbodyProAnswerAttachment").html('');
	}
	
	top.jzts();
	//var proCode=currentItem.PRO_CODE;//问题单号
	$.ajax({
			type: "GET",
			url: '<%=basePath%>attachment/getAttachmentByType.do?BUSINESS_TYPE='+attachmentType+'&BILL_CODE='+billCode,
	    	//data: {PRO_CODE:proCode},
			//dataType:'json',
			cache: false,
			success: function(data){
				if(data){
					$.each(data, function(i, item){
						var tr=addItemAttachment(item,i+1,attachmentType); 
						if(attachmentType=="PROBLEM_INFO"){
							$('#tbodyProInfoAttachment').append(tr);
						}else if(attachmentType=="PROBLEM_ANSWER"){
							$('#tbodyProAnswerAttachment').append(tr);
						}else if(attachmentType=="PROBLEM_CLOSE"){
							$('#tbodyProCloseAttachment').append(tr);
						}
				 	});
				}
				top.hangge();
			}
	});
}

/**
 * 增加附件tr
 */
function addItemAttachment(item,index,type){
	var href='<%=basePath%>/attachment/download.do?ATTACHMENT_ID='+item.ATTACHMENT_ID;
	var ext=item.ATTACHMENT_PATH.substring(19,item.ATTACHMENT_PATH.length);
	var htmlLog='<tr>'
		+'<td class="center" style="width: 30px;">'+index+'</td>'
		+'<td class="center">'+item.ATTACHMENT_NAME+ext+'</td>'
		+'<td class="center">'+item.ATTACHMENT_MEMO+'</td>'
		+'<td class="center">'+item.ATTACHMENT_SIZE+'&nbsp;KB</td>'
		+'<td class="center">'+item.CREATE_USER+'</td>'
		+'<td class="center">'+item.CREATE_DATE+'</td>'
		+'<td class="center">'
			+'<div class="hidden-sm hidden-xs btn-group">'
				+'<a class="btn btn-xs btn-success" onclick=window.location.href="'+href+'">'
					+'<i class="ace-icon fa fa-cloud-download bigger-120" title="下载"></i>'
				+'</a>'
				+'<a class="btn btn-xs btn-danger" onclick=delProAttachment("'+item.ATTACHMENT_ID+'","'+type+'")>'
					+'<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>'
				+'</a>'
			+'</div>'
		+'</td>'
	+'</tr>';
	return htmlLog;
}

/**
 * 上传附件
 */
function addProAttachmentByType(type){
	if(currentItem==null){
		$("#btnAddProInfoAttachment").tips({
			side:3,
            msg:'请先保存问题信息后，再上传附件',
            bg:'#cc0033',
            time:3
        });
		return;
	}
	if(currentItem.PRO_STATE=='2'){//已提交
		$("#btnAddProInfoAttachment").tips({
			side : 3,
			msg : '当前问题"已提交"，不能再上传附件',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnAddProInfoAttachment").focus();
		return false;
	}else if(currentItem.PRO_STATE=='3'){//受理中
		$("#btnAddProInfoAttachment").tips({
			side : 3,
			msg : '当前问题"受理中"，不能再上传附件',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnAddProInfoAttachment").focus();
		return false;
	}else if(currentItem.PRO_STATE=='4'){//已关闭
		$("#btnAddProInfoAttachment").tips({
			side : 3,
			msg : '当前问题"已关闭"，不能再上传附件',
			bg : '#AE81FF',
			time : 2
		});
		$("#btnAddProInfoAttachment").focus();
		return false;
	}
	
	 var proCode=currentItem.PRO_CODE;
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="上传附件";
	 diag.URL = '<%=basePath%>attachment/goAdd.do?BUSINESS_TYPE='+type+'&BILL_CODE='+proCode;
	 diag.Width = 460;
	 diag.Height = 290;
	 diag.Modal = true;				//有无遮罩窗口
	 diag. ShowMaxButton = true;	//最大化按钮
     diag.ShowMinButton = true;		//最小化按钮
	 diag.CancelEvent = function(){ //关闭事件
		if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
		 	getProAttachment(type);
		}
		diag.close();
	 };
	 diag.show();
}

//删除
function delProAttachment(Id,type){
	bootbox.confirm("确定要删除吗?", function(result) {
		if(result) {
			top.jzts();
			var url = "<%=basePath%>attachment/delete.do?ATTACHMENT_ID="+Id;
			$.get(url,function(data){
				getProAttachment(type);
			});
		}
	});
}

/**
 * 初始化知识库列表信息(手动)
 */
function searchKnowledgeList(){
	$("#ulSd li").remove(); 
	top.jzts();
	var keywords = $("#keywordsKnowledge").val();
	$.ajax({
			type: "POST",
			url: '<%=basePath%>knowledge/getPageListKnowledge.do',
	    	data: {keywords:keywords},
			dataType:'json',
			cache: false,
			success: function(data){
				if(data){
					$.each(data, function(i, item){
						var htmlItem=addItemKnowledge(item); 
						$("#ulSd").append(htmlItem);
				 	});
				}
				else{
					//addEmpty();
				}
				top.hangge();
			}
	});
}

/**
 * 初始化知识库列表信息(动态)
 */
function searchKnowledgeListDT(keywords){
	$("#ulDt li").remove(); 
	top.jzts();
	$.ajax({
			type: "POST",
			url: '<%=basePath%>knowledge/getPageListKnowledge.do',
	    	data: {keywords:keywords},
			dataType:'json',
			cache: false,
			success: function(data){
				if(data){
					$.each(data, function(i, item){
						var htmlItem=addItemKnowledge(item); 
						$("#ulDt").append(htmlItem);
				 	});
				}
				else{
					//addEmpty();
				}
				top.hangge();
			}
	});
}

/*
 * 增加Item知识库数据
 */
function addItemKnowledge(item){
	//var content=item.DETAIL.substring(0,50);
	var content=item.DETAIL;
	var htmlItem='<li class="item-grey clearfix" onclick=getDetailKnowledge("'+item.KNOWLEDGE_ID+'")>'
	/* +'<input name="PRO_CODE" id="PRO_CODE" type="hidden" value="'+item.PRO_CODE+'" />' */
		+'<div>'
			+'<label class="inline" style="margin-bottom:5px;">'
				+'<span class="list-item-value-title">'+item.KNOWLEDGE_TITLE+'</span>'
			+'</label>'
		+'<div>'
		+'<div class="pull-right">'
			+'<button class="btn btn-xs btn-yellow" onClick=viewKnowledgeDetail("'+item.KNOWLEDGE_ID+'")>详情</button>'
		+'</div>'
		/* +'<div>'
			+'<label class="inline">'
				+'<span class="list-item-info"> 内容：</span>'
				+'<span class="list-item-value">'+content+'</span>'
			+'</label>'
		+'</div>' */
		
		+'<div>'
			+'<label class="inline">'
				+'<span class="list-item-info"> 作者：</span>'
				+'<span class="list-item-value">'+item.AUTHOR+'</span>'
			+'</label>'
			+'<label class="inline">'
				+'<span class="list-item-info"><i class="ace-icon fa fa-clock-o"></i> 时间：</span>'
				+'<span class="list-item-value grey">'+item.CREATE_DATE+'</span>'
			+'</label>'
		+'</div>'
	+'</li>';
	return htmlItem;
}

/**
 * 查看知识详情
 */
function viewKnowledgeDetail(knowledgeId){
	
	top.jzts();
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title ="知识详情";
	diag.URL = '<%=basePath%>knowledge/goView.do?KNOWLEDGE_ID='+knowledgeId;
	diag.Width = 600;
	diag.Height = 460;
	diag.CancelEvent = function(){ //关闭事件
		diag.close();
	};
	diag.show();
}

/**
 * 知识行变化
 */
function getDetailKnowledge(knowledgeId){
	
}

/**
 * 获取问题回复信息
 */
function getProAnswers(){
	$("#ff-answer-info").empty();
	top.jzts();
	var proCode=currentItem.PRO_CODE;//问题单号
	var first;
	$.ajax({
			type: "POST",
			url: '<%=basePath%>mbp/getProAnswers.do',
	    	data: {PRO_CODE:proCode},
			dataType:'json',
			cache: false,
			success: function(data){
				if(data&&data.length>0){
					$.each(data, function(i, item){
						if(i==0){
							first=item;
						}
						var selected=true;
						var option = new Option(item.NAME+'  '+item.BILL_DATE, item.ANSWER_ID, selected, true);
						$('#ff-answer-info').append(option);
				 	});
					if(first){
						getAnswerContent();//根据ID获取回复内容
						/* 获取回复中附件信息 */
						getProAttachment("PROBLEM_ANSWER");
					}
				}else{
					//清空内容
					UE.getEditor('editorAnswer').addListener("ready", function () {
						UE.getEditor('editorAnswer').setContent('');
					});
					if(UE.getEditor('editorAnswer').isReady){
				    	UE.getEditor('editorAnswer').setContent('');
					}
					//清空附件
					$("#tbodyProAnswerAttachment").html('');
				}
				top.hangge();
			}
	});
}

/**
 * 回复列表变化
 */
function answerChange(){
	//currentAnswerID=answerID;
	getAnswerContent();//根据ID获取回复内容
	/* 获取回复中附件信息 */
	getProAttachment("PROBLEM_ANSWER");
}

/**
 * 获取回复内容
 */
function getAnswerContent(){
	var answerID=$("#ff-answer-info").val();
	if(answerID == null){
    	answerID='';
	}
	top.jzts();
	var proCode=currentItem.PRO_CODE;//问题单号
	$.ajax({
			type: "POST",
			url: '<%=basePath%>mbp/getAnswerContent.do',
	    	data: {ANSWER_ID:answerID},
			dataType:'json',
			cache: false,
			success: function(data){
				if(data){
					UE.getEditor('editorAnswer').addListener("ready", function () {
						UE.getEditor('editorAnswer').setContent(data.ANSWER_CONTENT);
					});
					if(UE.getEditor('editorAnswer').isReady&&data.ANSWER_CONTENT){
				    	UE.getEditor('editorAnswer').setContent(data.ANSWER_CONTENT);
					}
					/* UE.getEditor('').setContent(data.ANSWER_CONTENT); */
				}
				else{
					UE.getEditor('editorAnswer').setContent('');
				}
				top.hangge();
			}
	});
}


/**
 * 延时加载动态搜索知识库内容
 */
var last;
$("#form-field-pro-title").on('input propertychange', function(event){
    //"#fix为你的输入框
       last = event.timeStamp;
       //利用event的timeStamp来标记时间，这样每次事件都会修改last的值，注意last必需为全局变量
       setTimeout(function(){    //设时延迟0.5s执行
            if(last-event.timeStamp==0)
            //如果时间差为0（也就是你停止输入0.5s之内都没有其它的keyup事件发生）则做你想要做的事
            {
                 console.log($("#form-field-pro-title").val());
                 if($("#form-field-pro-title").val()!=""){
                 	searchKnowledgeListDT($("#form-field-pro-title").val());
                 }
            }
 
        },1000);
});

//下拉树
var defaultNodes = {"treeNodes":${zTreeNodes}};
function initComplete(){
	
	//绑定change事件
	$("#selectTree").bind("change",function(){
		if(!$(this).attr("relValue")){
	      //  top.Dialog.alert("没有选择节点");
	    }else{
			//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
			$("#form-field-pro-type-id").val($(this).attr("relValue"));
	    }
	});
	//赋给data属性
	$("#selectTree").data("data",defaultNodes);  
	$("#selectTree").render();
	//$("#selectTree2_input").val("${null==depname?'请选择问题类型':depname}");
	
}
</script>
</html>
