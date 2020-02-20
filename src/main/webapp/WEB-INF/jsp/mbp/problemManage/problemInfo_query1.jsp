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
						<form class="form-inline" action="mbp/queryProblemInfo.do" id="problemForm" name="problemForm">
							<label class="pull-left" style="padding: 5px;">筛选条件：</label>
							<%-- <span class="pull-left" style="margin-right: 5px;"> 
								<select
									class="chosen-select form-control" name="FMISACC"
									id="FMISACC" data-placeholder="请选择帐套"
									style="vertical-align: top; height: 32px; width: 150px;">
										<option value="">请选择帐套</option>
										<c:forEach items="${fmisacc}" var="fmi">
											<option value="${fmi.DICT_CODE}">${fmi.NAME}</option>
										</c:forEach>
								</select>
							</span> --%>
							<%-- <span  class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
								<div class="selectTree" id="selectTree" multiMode="true"
									allSelectable="false" noGroup="false"></div>
								<input type="text" id="RPT_DEPT" hidden></input>
							</span> --%>
							<span class="pull-left" style="margin-right: 5px;"> 
								<select
									class="chosen-select form-control" name="PRO_STATE"
									id="PRO_STATE" data-placeholder="请选择处理状态"
									style="vertical-align: top; height: 32px; width: 150px;">
										<option value="">请选择处理状态</option>
										<c:forEach items="${proStateList}" var="proState">
											<option value="${proState.nameKey}"
												<c:if test="${pd.PRO_STATE==proState.nameKey}">selected</c:if>>${proState.nameValue}</option>
										</c:forEach>
								</select>
							</span>
							
							<button type="button" class="btn btn-info btn-xs"
								onclick="searchs();">
								<i class="ace-icon fa fa-search bigger-110"></i>
							</button>
							
							<!-- <div class="pull-right">
								<button id="btnAdd" class="btn btn-success btn-xs"
									onclick="showQueryCondi()">
									<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>新增</span>
								</button>
							</div> -->
						</form>
					</div>
				
					<div class="row">
						<div class="col-xs-12">
							<table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
								<thead>
									<tr>
										<!-- <th class="center" style="width:35px;">
										<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
										</th> -->
										<th class="center" style="width:50px;">序号</th>
										<th class="center">问题单号</th>
										<th class="center">问题标题</th>
										<th class="center">上报人</th>
										<th class="center">受理人</th>
										<th class="center">上报单位</th>
										<th class="center">系统类型</th>
										
										
										<th class="center">问题类型</th>
										<th class="center">问题标签</th>
										<th class="center">优先级</th>
										
										<th class="center">问题解决时间</th>
										<th class="center">处理状态</th>
										<th class="center">更新时间</th>
										<th class="center">单据状态</th>
										
										<th class="center">详情</th>
									</tr>
								</thead>
														
								<tbody>
									
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="problem" varStatus="vs">	
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class="center">${problem.PRO_CODE }</td>
												<td class="center">${problem.PRO_TITLE }</td>
												<td class="center">${problem.PRO_REPORT_USER_NAME }</td>
												<td class="center">${problem.PRO_ACCEPT_USER_NAME }</td>
												<td class="center">${problem.PRO_DEPART_NAME}</td>
												<td class="center">${problem.PRO_SYS_TYPE_NAME}</td>
												<td class="center">${problem.PRO_TYPE_NAME }</td>
												<td class="center">${problem.PRO_TAG }</td>
												<td class="center">${problem.PRO_PRIORITY_NAME }</td>
												<td class="center">${problem.PRO_RESOLVE_TIME }</td>
												<%-- <td class="center">${problem.PRO_CONTENT }</td> --%>
												<%-- <td class="center">${problem.PRO_STATE }</td> --%>
												
												<td style="width: 100px;" class="center">
													<c:if test="${problem.PRO_STATE == '2' }"><span class="label blue">已提交</span></c:if>
													<c:if test="${problem.PRO_STATE == '3' }"><span class="label orange">受理中</span></c:if>
													<c:if test="${problem.PRO_STATE == '4' }"><span class="label grey">已关闭</span></c:if>
													<c:if test="${problem.PRO_STATE == '1' }"><span class="label green">新发起</span></c:if>
												</td>
												
												<td class="center">${problem.UPDATE_DATE }</td>
												<td style="width: 60px;" class="center">
													<c:if test="${problem.BILL_STATE == '0' }"><span class="label label-important arrowed-in">停用</span></c:if>
													<c:if test="${problem.BILL_STATE == '1' }"><span class="label label-success arrowed">正常</span></c:if>
												</td>
												<td class="center">
													<div class="hidden-sm hidden-xs btn-group">
														<%-- <a class="btn btn-xs btn-success" title="xiangqing" onclick="editUser('${user.USER_ID}');">
															<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
														</a> --%>
														<button class="btn btn-xs btn-yellow" onClick="viewProblemDetail('${problem.PRO_CODE}')">详情</button>
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
	$("#problemForm").submit();
}

/**
 * 查看知识详情
 */
function viewProblemDetail(problemCode){
	
	top.jzts();
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title ="知识详情";
	diag.URL = '<%=basePath%>mbp/goView.do?PRO_CODE='+problemCode;
	diag.Width = 600;
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
