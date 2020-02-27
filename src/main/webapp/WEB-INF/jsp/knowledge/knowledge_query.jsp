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
<%@ include file="../system/index/top.jsp"%>
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
					<!--  -->
					<div class="row">
						<div class="col-xs-12">
						<div class="page-header">
						<form class="form-inline" method="post" action="knowledge/queryKnowledgeInfo.do" id="knowledgeForm" name="knowledgeForm">
							<label class="pull-left" style="padding: 5px;">筛选条件：</label>
							<%-- <span  class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
								<div class="selectTree" id="selectTree" multiMode="true"
									allSelectable="false" noGroup="false"></div>
								<input type="text" id="RPT_DEPT" hidden></input>
							</span> --%>
							
							<span class="pull-left nav-search input-icon" style="margin-left:20px;margin-top:2px;">
								<input type="text" placeholder="这里输入知识标题" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
								<i class="ace-icon fa fa-search nav-search-icon"></i>
							</span>
							
							<span class="pull-left" style="margin-right: 5px;margin-left:-10px;"> 
								<select
									class="chosen-select form-control" name="KNOWLEDGE_TYPE"
									id="KNOWLEDGE_TYPE" data-placeholder="请选择知识类别"
									style="vertical-align: top; height: 32px; width: 150px;">
										<option value="">请选择知识类别</option>
										<c:forEach items="${knowledgeTypeList}" var="knowledgeType">
											<option value="${knowledgeType.KNOWLEDGE_TYPE_ID}"
												<c:if test="${pd.KNOWLEDGE_TYPE==knowledgeType.KNOWLEDGE_TYPE_ID}">selected</c:if>>${knowledgeType.KNOWLEDGE_TYPE_NAME}
											</option>
										</c:forEach>
								</select>
							</span>
							
							<button type="button" class="btn btn-info btn-xs"
								onclick="searchs();">
								<i class="ace-icon fa fa-search bigger-110"></i>
							</button>
						</div>
				
					<!-- <div class="row">
						<div class="col-xs-12"> -->
							<table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
								<thead>
									<tr>
										<!-- <th class="center" style="width:35px;">
										<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
										</th> -->
										<th class="center" style="width:50px;">序号</th>
										
										<th class="center">知识标题</th>
										<th class="center">知识类别</th>
										<th class="center">标签</th>
										<th class="center">作者/出处</th>
										<th class="center">浏览数</th>
										<!-- <th class="center">创建人</th> -->
										<!-- <th class="center">创建日期</th> -->
										<th class="center">状态</th>
										
										<th class="center">详情</th>
										<!-- <th class="center">附件</th> -->
									</tr>
								</thead>
														
								<tbody>
									
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="knowledge" varStatus="vs">	
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												
												<td class="center">${knowledge.KNOWLEDGE_TITLE }</td>
												<td class="center">${knowledge.KNOWLEDGE_TYPE_NAME }</td>
												<td class="center">${knowledge.KNOWLEDGE_TAG }</td>
												<td class="center">${knowledge.AUTHOR}</td>
												<td class="center">${knowledge.READ_NUM}</td>
												<%-- <td class="center">${knowledge.CREATE_USER }</td> --%>
												<%-- <td class="center">${knowledge.CREATE_DATE }</td> --%>
												<td style="width: 80px;" class="center">
													<c:if test="${knowledge.STATE == '0' }"><span class="label label-important arrowed-in">停用</span></c:if>
													<c:if test="${knowledge.STATE == '1' }"><span class="label label-success arrowed">正常</span></c:if>
												</td>
												<td class="center">
													<div class="hidden-sm hidden-xs btn-group">
														<%-- <a class="btn btn-xs btn-success" title="xiangqing" onclick="editUser('${user.USER_ID}');">
															<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
														</a> --%>
														<button class="btn btn-xs btn-yellow" onClick="viewKnowledgeDetail('${knowledge.KNOWLEDGE_ID}')">详情</button>
													</div>
												</td>
												<%-- <td class="center">
													<div class="hidden-sm hidden-xs btn-group">
														<button class="btn btn-xs btn-orange" onClick="viewAttachment('KNOWLEDGE','${knowledge.KNOWLEDGE_ID}')">附件</button>
													</div>
												</td> --%>
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
							
							<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>	
							
						</form>	
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
	<%@ include file="../system/index/foot.jsp"%>
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
	$("#knowledgeForm").submit();
}

/**
 * 查看知识详情
 */
function viewKnowledgeDetail(knowledgeID){
	
	top.jzts();
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title ="知识详情";
	diag.URL = '<%=basePath%>knowledge/goView.do?KNOWLEDGE_ID='+knowledgeID;
	diag.Width = 600;
	diag.Height = 460;
	diag.CancelEvent = function(){ //关闭事件
		diag.close();
	};
	diag.show();
}

/**
 * 查看知识详情
 */
function viewAttachment(attachmentType,knowledgeID){
	
	top.jzts();
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title ="知识详情";
	diag.URL = '<%=basePath%>attachment/goView.do?BUSINESS_TYPE='+attachmentType+'&BILL_CODE='+knowledgeID;
	diag.Width = 650;
	diag.Height = 460;
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
</script>
</html>
