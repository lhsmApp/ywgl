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
<!-- 树形下拉框start -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
<link rel="stylesheet" type="text/css" href="plugins/selectZtree/import_fh.css" />
<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="plugins/selectZtree/ztree/ztree.css"></link>
<!-- 树形下拉框end -->
<!-- 标准页面统一样式 -->
<link rel="stylesheet" href="static/css/normal.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!--自由拉动  -->
 <link rel="stylesheet" href="static/ace/css/jquery-ui.css" />
 <!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
 <style>
    .mtable{width:auto;border-collapse:collapse;}
    .mtable input{background: #FFF !important;border: none;}
</style>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="page-header">
			
					</div>
					<div class="row">
						<form action="erp/erpApprovalList.do" method="post" name="Form" id="Form">
							<table style="margin-bottom:6px; float:left;">
								<tr>
									<td>
										<div class="input-group input-group-sm">
											<input type="text" id="busiDate" name="busiDate"   class="form-control"   placeholder="请选择年月（默认全部）" autocomplete="off" />
											<span class="input-group-addon">
												<i class="ace-icon fa fa-calendar" ></i>
											</span>
										</div>
									</td>
									<td style="vertical-align:top;padding-left:5px;">
									 	<span class="pull-left" style="margin-right: 5px;">
												<div class="selectTree" id="selectTree" multiMode="false" allSelectable="false" noGroup="false"></div>
											    <input type="hidden" id="SelectedDepartCode" name="SelectedDepartCode"/>
											     <input type="hidden" id="name" name="name"/>
											</span>
									</td>
									<td>
										<select class="form-control" id="confirmState" name="confirmState" style="width:150px;" onchange="tosearch()">
											<option value="2" <c:if test="${pd.confirmState == 2}">selected="selected"</c:if>>待审批</option>
											<option value="3" <c:if test="${pd.confirmState == 3}">selected="selected"</c:if>>已审批</option>
										</select>
									</td>	<td>
										<select class="form-control" id="APPLY_TYPE" name="APPLY_TYPE" style="width:150px;" onchange="tosearch()">
											<option value="1" <c:if test="${pd.APPLY_TYPE ==1||pd.APPLY_TYPE ==3}">selected="selected"</c:if>>新增</option>
											<option value="2" <c:if test="${pd.APPLY_TYPE == 2}">selected="selected"</c:if>>删除</option>
										</select>
									</td>
									<td style="vertical-align:top;padding-left:3px;">
										<a class="btn btn-info btn-sm" onclick="tosearch()"><i class="ace-icon fa fa-search bigger-110"></i></a>
										<c:if test="${pd.confirmState == 2}">
											<a class="btn btn-white btn-info btn-bold" onclick="oaaReport('确定要审批通过当前选中的申请吗?')"><i class="ace-icon fa fa-check-square-o bigger-110"></i>批量审批</a>
										</c:if>
										<c:if test="${pd.confirmState == 3}">
											<a class="btn btn-white btn-info btn-bold" onclick="oaaBackReport('确定要驳回当前选中的申请吗?');" title="批量驳回" ><i class="ace-icon fa fa-exclamation-triangle red bigger-110"></i>批量驳回</a>
										</c:if>
										<a class="btn btn-white btn-info btn-bold" onclick="toExcel()"><span class="ace-icon fa fa-cloud-download"></span>导出</a>
									</td>
								</tr>
							</table>
							<div style="width:100%;overflow: auto;min-height: 500px;">
							<table id="simple-table" class="mtable table table-bordered" style="margin-top:10px; width:2015px;">
								<thead>
									<tr>
										<th class="center" style="width:35px; padding-left: 5px;padding-right:5px;">
										<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
										</th>
										<th class="center" style="width:45px;  padding-left: 5px;padding-right:5px;">序号</th>
										<th style="width:110px; height:30px;  text-align: center; padding-left: 12px;padding-right:12px;height: 30px;">日期</th>
										<th style="width:110px; height:30px;  text-align: center; padding-left: 12px;padding-right:12px;height: 30px;">申请类别</th>
										<th style="width:110px; height:30px;  text-align: center; padding-left: 12px;padding-right:12px;height: 30px;">员工编号</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">姓名</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">单位</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">模块</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">联系电话</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">帐号类别</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">上报人</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">上报人电话</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">备注</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">审批状态</th>
									</tr>
								</thead>
								<tbody id="copyTable">
								<!-- 开始循环 -->	
										<c:forEach items="${varList}" var="var" varStatus="vs">
											<tr>
												<th class='center'>
													<label class="pos-rel"><input type='checkbox' name='ids' value="${var.ID}&${var.ACCOUNT_SIGN}" class="ace" /><span class="lbl"></span></label>
												</th>
												<th class='center'>
													${vs.index+1}
													<input type="hidden" name='ids' value="${var.ID}" class="ace" />
												</th>
												
												<th style="height: 30px;">${var.APPLY_DATE}</th>
												<th style="height: 30px;">
													<c:if test="${var.APPLY_TYPE==2}">
													<span>刪除 </span>
													</c:if>
													<c:if test="${var.APPLY_TYPE ==1||var.APPLY_TYPE ==3}">
														<span>新增</span>
													</c:if>
												</th>
												<th style="height: 30px;">${var.STAFF_CODE}</th>
												<th>${var.STAFF_NAME}</th>
												<th>${var.DEPART_CODE}</th>
												<th>${var.STAFF_MODULE}</th>
												<th>${var.PHONE}</th>
													<th class="center">
													<input type="hidden" id="ACCOUNT_SIGN" name="ACCOUNT_SIGN" value="${var.ACCOUNT_SIGN}"/>
													<c:if test="${var.ACCOUNT_SIGN == 1}"><span>正式帐号</span></c:if>
													<c:if test="${var.ACCOUNT_SIGN == 2}"><span>临时帐号</span></c:if>
												</th>
												<th>${var.BILL_USERNAME}</th>
													<th>${var.PHONE}</th>
												<th>${var.NOTE}</th>
												<th class="center">
													<c:if test="${var.CONFIRM_STATE == 2}"><span class="label label-warning arrowed">待审批</span></c:if>
													<c:if test="${var.CONFIRM_STATE == 3}"><span class="label label-success arrowed">已审批</span></c:if>
												</th>
											</tr>
										</c:forEach>
								</tbody>
							</table>
							</div>
								<div class="position-relative page-header pull-right">
									<table style="width:100%;">
										<tr>
											<td style="vertical-align:top;"><div class="pagination">${page.pageStr}</div></td>
										</tr>
									</table>
								</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 自由拉动 -->
	<script type="text/javascript" src="static/ace/js/jquery-ui.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		
		$(function(){
			//table自由拉动
			var tablewidth = 0;
			$("th").resizable({
				start:function(event,ui){
					tablewidth = $("#simple-table").width()-ui.size.width;
				},resize:function(event,ui){
					$("#simple-table").css("width",tablewidth+ui.size.width+"px")
				},stop:function(event,ui){
					$("#simple-table").css("width",tablewidth+ui.size.width+"px")
				}
			})
			$("th > div:last-child").removeClass();		
			//日期
			$("#busiDate").datepicker({
				format: 'yyyymm', 
			    language: "zh-CN",
			    autoclose:true,
			   	startView: 1,
			    minViewMode: 1,
			    maxViewMode: 1,
			});
			let busiDate = '${pd.busiDate}'
			if(busiDate){
				$('#busiDate').datepicker("update",new Date(busiDate.substring(0,busiDate.length-2)+'-'+busiDate.substring(busiDate.length-2)));
			}
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		})
		/* 检索 */
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		
		/* ERP正式账号申请 */
		function toERPOaa(){
			window.location.href='<%=basePath%>erp/erpOaaList.do';
		}
		
		/* ERP临时账号申请 */
		function toERPTaa(){
			window.location.href='<%=basePath%>erp/erpTaaList.do';
		}
		
		/* ERP删除账号申请 */
		function toERPDaa(){
			window.location.href='<%=basePath%>erp/erpDaaList.do';
		}
		
		/*批量审批*/
		function oaaReport(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
						if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						}
					}
					if(str == ''){
						bootbox.dialog({
							message: "<span class='bigger-110'>当前未选中任何申请!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						return;
				  	} 
					if($("#confirmState").val() != 2){
						bootbox.dialog({
							message: "<span class='bigger-110'>只能对未审批数据进行操作!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						return;
					}
					var url;
					if('${pd.APPLY_TYPE}'==2){
						url='<%=basePath%>erp/daaReport.do?tm='+new Date().getTime()
					}else{
						url='<%=basePath%>erp/oaaReport.do?tm='+new Date().getTime()
					}
						top.jzts();
						$.ajax({
							type: "POST",
							url: url,
					    	data: {DATA_IDS:str,"CONFIRM_STATE":"3"},
							dataType:'json',
							cache: false,
							success: function(response){
								if(response.code==0){
									$(top.hangge());//关闭加载状态
									$("#subTitle").tips({
										side:3,
							            msg:'批量审批成功',
							            bg:'#009933',
							            time:3
							        });
									history.go(0); //刷新页面
								}else{
									$(top.hangge());//关闭加载状态
									$("#subTitle").tips({
										side:3,
							            msg:'批量审批失败,'+response.message,
							            bg:'#cc0033',
							            time:3
							        });
								}
							}
						});
				}
			});
		};
		
		/*批量驳回*/
		function oaaBackReport(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
						if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						}
					}
					if(str == ''){
						bootbox.dialog({
							message: "<span class='bigger-110'>请选择需要驳回的申请!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						return;
				  	} 
					if($("#confirmState").val() != 3){
						bootbox.dialog({
							message: "<span class='bigger-110'>只能对未审批数据进行操作!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						return;
					}
					var url;
					if('${pd.APPLY_TYPE}'==2){
						url='<%=basePath%>erp/daaReport.do?tm='+new Date().getTime()
					}else{
						url='<%=basePath%>erp/oaaReport.do?tm='+new Date().getTime()
					}
						top.jzts();
						$.ajax({
							type: "POST",
							url: url,
<%-- 							url: '<%=basePath%>erp/oaaReport.do?tm='+new Date().getTime(), --%>
					    	data: {DATA_IDS:str,"CONFIRM_STATE":"2"},
							dataType:'json',
							cache: false,
							success: function(response){
								if(response.code==0){
									$(top.hangge());//关闭加载状态
									$("#subTitle").tips({
										side:3,
							            msg:'批量驳回成功',
							            bg:'#009933',
							            time:3
							        });
									history.go(0); //刷新页面
								}else{
									$(top.hangge());//关闭加载状态
									$("#subTitle").tips({
										side:3,
							            msg:'批量驳回失败,'+response.message,
							            bg:'#cc0033',
							            time:3
							        });
								}
							}
						});
				}
			});
		};
		
		//导出excel
		function toExcel(){
			debugger;
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++){
				if(document.getElementsByName('ids')[i].checked){
				  	if(str=='') str += document.getElementsByName('ids')[i].value;
				  	else str += ',' + document.getElementsByName('ids')[i].value;
				}
			}
			str=str.replace(/\&/g,"%26");
			window.location.href='<%=basePath%>erp/oaaExcel.do?confirmState='+$("#confirmState").val()+'&APPLY_TYPE='+$("#APPLY_TYPE").val()+'&busiDate='+$("#busiDate").val()+'&USER_DEPART='+$("#SelectedDepartCode").val()+'&DATA_IDS='+str;
		}		
		//下拉树
		var defaultNodes = {"treeNodes":eval('${zTreeNodes}')};
		var name = "${pd.name}";
		function initComplete(){
			//绑定change事件
			$("#selectTree").bind("change",function(){
				if($(this).attr("relValue")){
					$("#SelectedDepartCode").val($(this).attr("relValue"));
					$("#name").val($(this).attr("reltext"));
			    }
			});
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			if("" == name){
				name = "请选择单位";
			}
			$("#SelectedDepartCode").val('${pd.SelectedDepartCode}');
			$("#name").val(name);
			$("#selectTree2_input").val(name);
		}
	</script>
</body>
</html>