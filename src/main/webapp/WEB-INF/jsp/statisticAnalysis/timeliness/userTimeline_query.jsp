﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
 <style>
    .mtable{width:auto;border-collapse:collapse;border:1px solid black;}
    .mtable th, .mtable td{height:30px;text-align:center;border:1px solid black;}
    .mtable th, .mtable td{position:relative;background-clip:padding-box;}
</style>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">							
						<!-- 检索  -->
						<form action="mbp/queryProblemUserTime.do" method="post" name="Form" id="Form">
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
								<td ><span>问题类型：</span></td>
			
									<td width="150px">
									<select class="form-control" name="PROBLEM_TYPE" id="PROBLEM_TYPE" >
									<option value="">请选择问题类型</option>
									<c:forEach items="${proTypeList}" var="type">
									<option value="${type.PRO_TYPE_ID}">${type.PRO_TYPE_NAME}</option>
									</c:forEach>
								</select>				
									</td>
									<td><span class="nav-search input-icon" style="margin-left:20px;margin-top:2px;">
										<input type="text" placeholder="请输入运维人员" class="nav-search-input" id="USER_NAME" autocomplete="off" name="USER_NAME" />
										<i class="ace-icon fa fa-search nav-search-icon"></i>
									</span>
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
						<!-- 检索  -->
					
						<table id="simple-table" class="mtable" style="margin-top:20px; width: 100%;">	
							<thead>
								<tr>
								<th class="center" style="width:30px;">序号</th>
									<th class="center"style="width: 100px;">问题类型</th>
									<th class="center"style="width: 100px;">问题数量</th>
									<th class="center"style="width: 100px;">领取数量</th>
									<th class="center"style="width: 100px;">关闭数量</th>
									<th class="center"style="width: 100px;">解决中问题数量</th>
									<th class="center"style="width: 150px;">平均处理时长</th>
									<th class="center"style="width: 100px;">运维人员</th>		
								</tr>
							</thead>												
							<tbody id="tobodyUser" >
							<!-- 开始循环 -->	
							<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="var" varStatus="vs">	
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class='center'style="width: 100px;">${var.PRO_TYPE_NAME}</td>
												<td class='center'style="width: 100px;">${var.WTNUMS}</td>
												<td class='center'style="width: 100px;">${var.LQNUMS}</td>
												<td class='center'style="width: 100px;">${var.GBNUMS}</td>
												<td class='center'style="width: 100px;">${var.JJZNUMS}</td>					
												<td class='center'style="width: 150px;">${var.PJSC}</td>
												<td class='center'style="width: 100px;">${var.USERNAME}</td>
										</tr>									
									</c:forEach>																		
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
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
	 var gridBase_selector = "#jqGridBase";  
	    var pagerBase_selector = "#jqGridBasePager";  
			$(top.hangge());//关闭加载状态
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
			//检索
			function tosearch111(){
				top.jzts();
				$("#Form").submit();
			}
			$(function() {
// 				$("#tobodyUser tr").click(function(){ //给每个tr 绑定点击事件 主要锁定每个tr
// 					var trs = $(this).parent().find('tr'); //获取所有tr
// 				})
			
			})
			
		//检索
			function tosearch(){
				$("#tobodyUser tr").remove();
				top.jzts();	
				var startDate=$("#START_DATE").val();
				var endDate=$("#END_DATE").val();
				var problemType=$("#PROBLEM_TYPE").val();
				var uesrName=$("#USER_NAME").val();
				$.ajax({
						type: "POST",
						url: '<%=basePath%>mbp/dataListProblemUserTime.do',
				    	data: {START_DATE:startDate,END_DATE:endDate,PRO_TYPE_ID:problemType,USER_NAME:uesrName},
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.length>0){
								$.each(data, function(i, item){
							    	var html = '';
							        html += setUserTable(item,i+1);
								$("#tobodyUser").append(html);
							 	});
							}
							top.hangge();
						}
				});
			}
			function setUserTable(item,i){
				rows='<tr>'
					+"</td><td class='center'>"
					+i
					+"</td><td class='center'>"
					+item.PRO_TYPE_NAME
					+"</td><td class='center'>"
					+item.WTNUMS
					+"</td><td class='center'>"
					+item.LQNUMS
					+"</td><td class='center'>"
					+item.GBNUMS
					+"</td><td class='center'>"
					+item.JJZNUMS
					+"</td><td class='center'>"
					+item.PJSC
					+"</td><td class='center'>"
					+item.USERNAME
					+'</td></tr>';				
					return rows;	
			}
			
			  function toExcel(){
				    	window.location.href='<%=basePath%>mbp/listUserTimeExcel.do?START_DATE='+$("#START_DATE").val()
			             +'&END_DATE='+$("#END_DATE").val() 
			              +'&PRO_TYPE_ID='+$("#PROBLEM_TYPE").val() 
			               +'&USER_NAME='+$("#USER_NAME").val() 

			    }
	</script>


</body>
</html>