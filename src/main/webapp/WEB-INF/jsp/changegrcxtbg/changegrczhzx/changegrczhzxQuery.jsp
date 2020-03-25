<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
						<form class="form-inline" action="changegrczhzx/queryList.do" id="grczhzxForm" name="grczhzxForm" method="post">
									<div class="nav-search">
										<span class="input-icon">
										<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
										<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
										<button type="button" class="btn btn-info btn-xs" onclick="tosearch();">
											<i class="ace-icon fa fa-search bigger-110"></i>
										</button>
									</div>
						</form>
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">申请单号</th>
									<th class="center">申请单位</th>
									<th class="center">申请部门</th>
									<th class="center">帐号</th>
									<th class="center">帐号撤销原因</th>
									<th class="center">申请人</th>
									<th class="center">申请人部门</th>
									<th class="center">申请人岗位</th>
									<th class="center">联系方式</th>
									<th class="center">申请日期</th>	
									<th class="center">处理状态</th>	
									<th class="center">单据状态</th>								
									<th class="center">操作</th>
								</tr>
							</thead>													
							<tbody>							
							<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="var" varStatus="vs">	
											<c:if test="${var.APPROVAL_STATE != null }">
												<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class='center'>${var.BILL_CODE}</td>
												<td class='center'>${var.UNIT_NAME}</td>
												<td class='center'>${var.DEPT_NAME}</td>
												<td class='center'>${var.ACCOUNT_NAME}</td>
												<td class='center'>${var.CANCLE_REASON}</td>					
												<td class='center'>${var.USERNAME}</td>
												<td class='center'>${var.USER_DEPTNAME}</td>
												<td class='center'>${var.USER_JOB}</td>
												<td class='center'>${var.USER_CONTACT}</td>
												<td class='center'>${var.ENTRY_DATE}</td>
													<td style="width: 100px;" class="center">
												<c:if test="${var.APPROVAL_STATE == '0' }"><span class="label blue">审批中</span></c:if>
												<c:if test="${var.APPROVAL_STATE == '2' }"><span class="label orange">退回</span></c:if>
												<c:if test="${var.APPROVAL_STATE == '1' }"><span class="label grey">已完成</span></c:if>
												<c:if test="${var.APPROVAL_STATE == null }"><span class="label grey">未上报</span></c:if>
													</td>
													<td style="width: 60px;" class="center">
														<c:if test="${var.BILL_STATE == '0' }"><span class="label label-important arrowed-in">停用</span></c:if>
														<c:if test="${var.BILL_STATE == '1' }"><span class="label label-success arrowed">正常</span></c:if>
													</td>
													<td class="center">
														<div class="hidden-sm hidden-xs btn-group">
															<button class="btn btn-xs btn-yellow" onClick="showDetail('${var.BILL_CODE}')">详情</button>
														</div>
													</td>
												</tr>
											</c:if>
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
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#grczhzxForm").submit();
		}
		$(function() {
		
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
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
		

		//显示详情
		function showDetail(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="GRC帐号撤销单"+Id+"详情";
			 diag.URL = '<%=basePath%>changegrczhzx/detailView.do?BILL_CODE='+Id;
			 diag.Width = 800;
			 diag.Height = 405;			
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}

		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>changeerpxtbg/excel.do';
		}
	</script>


</body>
</html>