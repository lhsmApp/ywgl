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
				<div class="page-header">
						<table style="width:100%;">
							<tbody>
								<tr>
									<td>
										<div class="pull-right">
											<span class="green middle bolder">填报类型: &nbsp;</span>
											<div class="btn-toolbar inline middle no-margin">
												<div data-toggle="buttons" class="btn-group no-margin">
													<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toGRCPerson()">
														<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>GRC人员信息</span>
													</button>
													<button id="btnEdit" class="btn btn-info btn-xs" onclick="toGRCApprovalMatrix()">
															<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>GRC审批矩阵</span>
													</button>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="row">
						<div class="col-xs-12">							
						<!-- 检索  -->
						<form action="grcperson/queryList.do" method="post" name="Form" id="Form">
							<table style="margin-top:5px;">
								<tr>							
								<td style="padding-left:2px;">
									<div class="input-group input-group-sm">
										<input type="text" id="busiDate" name="busiDate"  class="form-control"   placeholder="请选择查询年月" value="${ pd.busiDate}"/>
										<span class="input-group-addon">
											<i class="ace-icon fa fa-calendar" ></i>
										</span>
									</div>
								</td>								
	<!-- 								<td style="padding-left:2px;"><input class="span10 date-picker" name="START_DATE" id="START_DATE"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td> -->
	<!-- 								<td style="padding-left:2px;"><input class="span10 date-picker" name="END_DATE" name="END_DATE"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td> -->
									<td>
											<div style="margin:10px 0px;">
													<input type="hidden" name="UNIT_CODE" id="UNIT_CODE"   />
													<div class="selectTree" id="selectTree" style="float:none;display:block;"></div>												
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
						<!-- 检索  -->
					
						<table id="simple-table" class="mtable" style="margin-top:20px; width: 100%;">	
							<thead>
								<tr>
									<th style="width:60px; height:30px; background-color: #BEBEC5; text-align: center;">序号</th>
									<th style="width:110px; height:30px; background-color: #BEBEC5; text-align: center;">员工编号</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">员工姓名</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">单位</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">部门</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">职务</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">岗位</th>
									<th style="width:100px; background-color: #BEBEC5; text-align: center;">办公室电话</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">手机号</th>
									<th style="width:120px; background-color: #BEBEC5; text-align: center;">中国石油邮箱</th>
								</tr>
							</thead>												
							<tbody id="tobodyUser">
							<!-- 开始循环 -->	
							<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="var" varStatus="vs">	
										<tr>
											<td class='center'>${vs.index+1}</td>
											<td class='center'>${var.STAFF_CODE}</td>
											<td class='center'>${var.STAFF_NAME}</td>
											<td class='center'>${var.STAFF_UNIT}</td>
											<td class='center'>${var.STAFF_DEPART}</td>
											<td class='center'>${var.STAFF_POSITION}</td>
											<td class='center'>${var.STAFF_JOB}</td>
											<td class='center'>${var.PHONE}</td>
											<td class='center'>${var.MOBILE_PHONE}</td>
											<td class='center'>${var.ZSY_MAIL}</td>
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
		
			//检索
			function tosearch1111(){
				top.jzts();
				$("#Form").submit();
			}
			$(function() {
				//日期
				$("#busiDate").datepicker({
					format: 'yyyymm',
				    language: "zh-CN",
				    autoclose:true,
				    startView: 1,
				    minViewMode: 1,
				    maxViewMode: 1
				});
			})
		
			  function toExcel(){
				window.location.href='<%=basePath%>grcperson/listStatisticExcel.do?BUSI_DATE='+$("#busiDate").val()+'&UNIT_CODE='+$("#UNIT_CODE").val();  
			    }
			/* GRC人员信息 */
			function toGRCPerson(){
				window.location.href='<%=basePath%>grcperson/queryList.do';
			}
			
			/* GRC审批矩阵 */
			function toGRCApprovalMatrix(){
				window.location.href='<%=basePath%>grcapprovalmatrix/queryList.do';
			}
			//检索
			function tosearch(){
				location.href = '<%=basePath%>grcperson/queryList.do?busiDate='+$("#busiDate").val()+'&UNIT_CODE='+$("#UNIT_CODE").val()
				<%-- $("#tobodyUser tr").remove();
				top.jzts();
				var busiDate = $("#busiDate").val();
				var unitCode = $("#UNIT_CODE").val();				
				$.ajax({
						type: "POST",
						url: '<%=basePath%>grcperson/queryDataList.do',
				    	data: {busiDate:busiDate,UNIT_CODE:unitCode},
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
				}); --%>
			}
			function setUserTable(item,i){
				rows='<tr><td class="center" style="width: 30px;">'
					+i 
					+"</td><td class='center'>"
					+item.STAFF_CODE
					+"</td><td class='center'>"
					+item.STAFF_NAME
					+"</td><td class='center'>"
					+item.STAFF_UNIT
					+"</td><td class='center'>"
					+item.STAFF_DEPART
					+"</td><td class='center'>"
					+item.STAFF_POSITION
					+"</td><td class='center'>"
					+item.STAFF_JOB
					+"</td><td class='center'>"
					+item.PHONE
					+"</td><td class='center'>"
					+item.MOBILE_PHONE
					+"</td><td class='center'>"
					+item.ZSY_MAIL
					+'</td></tr>';				
					return rows;	
			}		
			function initComplete(){
					//下拉树
					var defaultNodes = {"treeNodes":${zTreeNodes}};
					//绑定change事件
					$("#selectTree").bind("change",function(){

						if(!$(this).attr("relValue")){
					    }else{
							$("#UNIT_CODE").val($(this).attr("relValue"));	
					    }
					});
					//赋给data属性
					$("#selectTree").data("data",defaultNodes);  
					$("#selectTree").render();
				}	
	</script>


</body>
</html>