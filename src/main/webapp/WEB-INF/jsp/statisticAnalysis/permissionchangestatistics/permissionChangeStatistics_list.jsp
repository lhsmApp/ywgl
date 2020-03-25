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
				<form action="permissionchangestatistics/listPermissonChangeStatistic.do" method="post" name="Form" id="Form">
					<div class="row">
						<div class="col-xs-12">							
						<!-- 检索  -->
							<table style="margin-top:5px;" >
								<tr>							
								<td style="padding-left:2px;">
									<div class="input-group input-group-sm">
										<input type="text" id="YEAR_MONTH" name="YEAR_MONTH"  class="form-control"   placeholder="请选择查询年月" />
										<span class="input-group-addon">
											<i class="ace-icon fa fa-calendar" ></i>
										</span>
									</div>
								</td>	
									<td ><span>选择周：</span></td>
									<input type="hidden" id="WEEKSTEXT" name="WEEKSTEXT" />
									<td width="40%">
										<select class="form-control" name="WEEKS" id="WEEKS"  >
											
										</select>					
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
						</div>
					</div>
						</form>
						<!-- 检索  -->
					
						<table id="simple-table" class="mtable" style="margin-top:20px; width: 100%;">	
							<thead>
									<tr>
									<th style="background-color: #BEBEC5; text-align: center;">序号</th>
									<th style="background-color: #BEBEC5; text-align: center;">公司名称</th>
									<th  style="background-color: #BEBEC5; text-align: center;">帐号延期</th>
									<th  style="background-color: #BEBEC5; text-align: center;">帐号解除锁定</th>
									<th style="background-color: #BEBEC5; text-align: center;">新增角色</th>
									<th style="background-color: #BEBEC5; text-align: center;">删除角色</th>
									<th  style="background-color: #BEBEC5; text-align: center;">帐号新增</th>
									<th  style="background-color: #BEBEC5; text-align: center;">帐号删除</th>
									<th style="background-color: #BEBEC5; text-align: center;">增加FMIS角色</th>
									<th style="background-color: #BEBEC5; text-align: center;">删除FMIS角色</th>
									<th style="background-color: #BEBEC5; text-align: center;">变更用户组</th>
									<th style="background-color: #BEBEC5; text-align: center;">变更人次</th>
								</tr>
							</thead>												
							<tbody id="tobodyUser">
							<!-- 开始循环 -->	
							<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="var" varStatus="vs">	
										<tr>
											<td class='center'>${vs.index+1}</td>
											<td class='center'>${var.COMPANY_NAME}</td>
											<td class='center'>${var.ACCOUNT_DELAY}</td>
											<td class='center'>${var.ACCOUNT_UNLOCK}</td>
											<td class='center'>${var.NEW_ROLES}</td>
											<td class='center'>${var.DELETE_ROLES}</td>
											<td class='center'>${var.NEW_ACCOUNTS}</td>
											<td class='center'>${var.DELETE_ACCOUNTS}</td>
											<td class='center'>${var.NEW_FMIS_ROLES}</td>
											<td class='center'>${var.DELETE_FMIS_ROLES}</td>
											<td class='center'>${var.CHANGE_USER_GROUP}</td>
											<td class='center'>${var.CHANGE_PERSON_COUNT}</td>
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
			function tosearch111(){
				top.jzts();	
				var value=$("#WEEKS").val();
				opts= $("#WEEKS").find("option[value="+ value +"]").text();
				$("#WEEKSTEXT").val(opts);			
				$("#Form").submit();
			}
			$(function() {
				//日期
				$("#YEAR_MONTH").datepicker({
					format: 'yyyy-mm',
				    language: "zh-CN",
				    autoclose:true,
				    startView: 1,
				    minViewMode: 1,
				    maxViewMode: 1
				});
				
				var date = new Date();	
				var year=date.getFullYear(); //获取完整的年份(4位)
				var month=date.getMonth()+1; //获取当前月份
				if(month<10){
					month = '0' + month
				}
				$("#YEAR_MONTH").val(year+'-'+month);
				getWeeks();
			})
			
			var lastDate = ''//因为日历插件有问题，每次切换日期都会连续3次触发下面的事件，所以我这里用一个变量来控制向后台的请求次数
			$("#YEAR_MONTH").unbind('change').bind("change",function(){
				var currDate = $(this).val()
				if(currDate != lastDate){
					lastDate = currDate;
					getWeeks();
				}
			});
			function getWeeks(){
				$.ajax({
					   type: "POST",
					   url: '<%=basePath%>permissionchangestatistics/getWeeks.do',
					   data: {'YEAR_MONTH':$("#YEAR_MONTH").val()},
					   dataType:'json',
					   cache: false,
					   success: function (data) {
						   $('#WEEKS').empty();
					                $('#WEEKS').append("<option value='0'></option>");
					                //遍历成功返回的数据
					                $.each(data, function (index,item) {
					                    var Id = data[index].ID;
					                    var name = data[index].Name;
					            
					                    //构造动态option
					                    $('#WEEKS').append("<option value='"+Id+"'>"+name+"</option>")
					                });
					            },
					            error: function () {
			
					            }
					  });
			}
			//检索
			function tosearch(){
				$("#tobodyUser tr").remove();
				top.jzts();	
				var value=$("#WEEKS").val();
				opts= $("#WEEKS").find("option[value="+ value +"]").text();
				$("#WEEKSTEXT").val(opts);	
				var yearMonth=$("#YEAR_MONTH").val();
				$.ajax({
						type: "POST",
						url: '<%=basePath%>permissionchangestatistics/queryPermissonData.do',
				    	data: {WEEKS:value,WEEKSTEXT:opts,YEAR_MONTH:yearMonth},
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
				rows='<tr><td class="center" style="width: 30px;">'
					+i 
					+"</td><td class='center'>"
					+item.COMPANY_NAME
					+"</td><td class='center'>"
					+item.ACCOUNT_DELAY
					+"</td><td class='center'>"
					+item.ACCOUNT_UNLOCK
					+"</td><td class='center'>"
					+item.NEW_ROLES
					+"</td><td class='center'>"
					+item.DELETE_ROLES
					+"</td><td class='center'>"
					+item.NEW_ACCOUNTS
					+"</td><td class='center'>"
					+item.DELETE_ACCOUNTS
					+"</td><td class='center'>"
					+item.NEW_FMIS_ROLES
					+"</td><td class='center'>"
					+item.DELETE_FMIS_ROLES
					+"</td><td class='center'>"
					+item.CHANGE_USER_GROUP
					+"</td><td class='center'>"
					+item.CHANGE_PERSON_COUNT
					+'</td></tr>';				
					return rows;	
			}	
			  function toExcel(){
					var value=$("#WEEKS").val();
					var level_select=$("#level_select").val();
					opts= $("#WEEKS").find("option[value="+ value +"]").text();		
				    	window.location.href='<%=basePath%>permissionchangestatistics/qxStatisticExcel.do?YEAR_MONTH='+$("#YEAR_MONTH").val()
			             +'&WEEKSTEXT='+opts
			             +'&WEEKS='+value;
    
			    }
	</script>
</body>
</html>