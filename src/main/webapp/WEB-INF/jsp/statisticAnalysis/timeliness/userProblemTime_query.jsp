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
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="page-header">
						<!-- 检索  -->
						<form action="mbp/queryProblemUserTime.do" method="post" name="xtbgForm" id="userTimeForm">
						<table style="margin-top:5px;">
								<tr>							
								<td style="padding-left:2px;">
									<div class="input-group input-group-sm">
										<input type="text" id="START_DATE" name="START_DATE"  class="form-control"  data-date-format="yyyy-mm-dd" placeholder="请选择开始日期"/>
										<span class="input-group-addon">
											<i class="ace-icon fa fa-calendar" ></i>
										</span>
									</div>
								</td>	
								<td style="padding-left:2px;">
									<div class="input-group input-group-sm">
										<input type="text" id="END_DATE" name="END_DATE"  class="form-control"  data-date-format="yyyy-mm-dd" placeholder="请选择结束日期"/>
										<span class="input-group-addon">
											<i class="ace-icon fa fa-calendar" ></i>
										</span>
									</div>
								</td>							
									<td style="padding-left:2px;">																		 
													<button type="button" class="btn btn-info btn-xs" onclick="tosearch();">
													    <i class="ace-icon fa fa-search bigger-110"></i>
													</button>	
									</td>	
									<td style="padding-left:6px;"><a class="btn btn-primary btn-xs inline pull-right" onclick="toExcel()">
								<i class="ace-icon fa fa-share bigger-110"></i> <span>导出</span>
							</a></td>
								</tr>
							</table>
						</form>
					</div>
						<!-- 检索  -->
				<div class="row">
					<div class="col-xs-12">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">问题类型</th>
									<th class="center">问题数量</th>
									<th class="center">领取数量</th>
									<th class="center">关闭数量</th>
									<th class="center">解决中问题数量</th>
									<th class="center">平均处理时长</th>
									<th class="center">运维人员</th>									
								</tr>
							</thead>													
							<tbody>							
							<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="var" varStatus="vs">	
												<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class='center'style="width: 200px;">${var.PRO_TYPE_NAME}</td>
												<td class='center'style="width: 200px;">${var.WTNUMS}</td>
												<td class='center'style="width: 200px;">${var.LQNUMS}</td>
												<td class='center'style="width: 200px;">${var.GBNUMS}</td>
												<td class='center'style="width: 200px;">${var.JJZNUMS}</td>					
												<td class='center'style="width: 200px;">${var.PJSC}</td>
												<td class='center'style="width: 200px;">${var.USERNAME}</td>																						
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
			</div>
		</div>
	</div>
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
			$("#userTimeForm").submit();
		}
		$(function() {
		
			//开始日期
			$("#START_DATE" ).datepicker({
			showOtherMonths: true,
			selectOtherMonths: false,
			autoclose: true,
			todayHighlight: true
			});
				//结束日期
				$("#END_DATE" ).datepicker({
				showOtherMonths: true,
				selectOtherMonths: false,
				autoclose: true,
				todayHighlight: true
			});
			
		});
				
		//修改
		function showDetail(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="系统变更单"+Id+"详情";
			 diag.URL = '<%=basePath%>changeerpxtbg/detailView.do?BILL_CODE='+Id;
			 diag.Width = 800;
			 diag.Height = 405;			
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
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
								url: '<%=basePath%>changeerpxtbg/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>changeerpxtbg/exportXtbg.do';
		}
	</script>


</body>
</html>