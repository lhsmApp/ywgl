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
						<table style="width:100%;">
							<tbody>
								<tr>
									<td>
										<div class="pull-right">
											<span class="green middle bolder">审批类型: &nbsp;</span>
											<div class="btn-toolbar inline middle no-margin">
												<div data-toggle="buttons" class="btn-group no-margin">
													<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toERPOaa()">
														<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>ERP正式账号审批</span>
													</button>
													<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toERPTaa()">
														<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>ERP临时账号审批</span>
													</button>
													<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toERPDaa()">
														<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>ERP删除账号审批</span>
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
						<form action="erp/erpOaaList.do" method="post" name="Form" id="Form">
							<table style="margin-bottom:6px; float:left;">
								<tr>
									<td>
										<c:if test="${not empty listBusiDate}"> 	
											<select class="form-control" id="busiDate" name="busiDate" style="width:150px;margin-left: 5px;">
												<option value="">全部</option>
												<c:forEach items="${listBusiDate}" var="var">
													<option value="${var.BUSI_DATE}" <c:if test="${pd.busiDate == var.BUSI_DATE}">selected="selected"</c:if>>${var.BUSI_DATE}</option>
												</c:forEach>
											</select>
										</c:if>
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
									</td>
									<td style="vertical-align:top;padding-left:3px;">
										<a class="btn btn-info btn-sm" onclick="tosearch()"><i class="ace-icon fa fa-search bigger-110"></i></a>
										<c:if test="${pd.confirmState == 2}">
										<a class="btn btn-white btn-info btn-bold" onclick="oaaReport('确定要审批通过当前页面所有申请吗?')"><i class="ace-icon fa fa-check-square-o bigger-110"></i>批量审批</a>
										<a class="btn btn-white btn-info btn-bold" onclick="oaaBackReport('确定要驳回当前页面所有申请吗?');" title="批量驳回" ><i class="ace-icon fa fa-exclamation-triangle red bigger-110"></i>批量驳回</a>
										</c:if>
										<a class="btn btn-white btn-info btn-bold" onclick="toExcel()"><span class="ace-icon fa fa-cloud-download"></span>导出</a>
									</td>
								</tr>
							</table>
							<div style="width:100%;overflow: auto;min-height: 500px;">
							<table id="simple-table" class="mtable table table-bordered" style="margin-top:10px; width:2015px;">
								<thead>
									<tr>
										<th class="center" style="width:45px;  padding-left: 5px;padding-right:5px;">序号</th>
										<th style="width:110px; height:30px;  text-align: center; padding-left: 12px;padding-right:12px;height: 30px;">员工编号</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">员工姓名</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">二级单位</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">三级单位</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">职务</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">岗位</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">模块</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">联络电话</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">电子邮件</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">是否培训</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">培训方式</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">培训时间</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">培训成绩</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">证书编号</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">UKey编号</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">申请日期</th>
										<th style="width:110px;  text-align: center;">备注</th>
										<th style="width:110px;  text-align: center;padding-left: 12px;padding-right:12px;">审批状态</th>
									</tr>
								</thead>
								<tbody id="copyTable">
								<!-- 开始循环 -->	
										<c:forEach items="${varList}" var="var" varStatus="vs">
											<tr>
												<th class='center'>
													${vs.index+1}
													<input type="hidden" name='ids' value="${var.ID}" class="ace" />
												</th>
												<th style="height: 30px;">${var.STAFF_CODE}</th>
												<th>${var.STAFF_NAME}</th>
												<th>${var.DEPART_CODE}</th>
												<th>${var.UNITS_DEPART}</th>
												<th>${var.STAFF_POSITION}</th>
												<th>${var.STAFF_JOB}</th>
												<th>${var.STAFF_MODULE}</th>
												<th>${var.PHONE}</th>
												<th>${var.MAIL}</th>
												<th>${var.IF_TRAINING}</th>
												<th>${var.TRAINING_METHOD}</th>
												<th>${var.TRAINING_TIME}</th>
												<th>${var.TRAINING_RECORD}</th>
												<th>${var.CERTIFICATE_NUM}</th>
												<th>${var.UKEY_NUM}</th>
												<th>${var.APPLY_DATE}</th>
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
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
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
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					}
					if($("#confirmState").val() != 2){
						bootbox.dialog({
							message: "<span class='bigger-110'>只能对未审批数据进行操作!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						return;
					}
					if(msg == '确定要审批通过当前页面所有申请吗?'){
						top.jzts();
						$.ajax({
							type: "POST",
							url: '<%=basePath%>erp/oaaReport.do?tm='+new Date().getTime(),
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
				}
			});
		};
		
		/*批量驳回*/
		function oaaBackReport(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					}
					if($("#confirmState").val() != 2){
						bootbox.dialog({
							message: "<span class='bigger-110'>只能对未审批数据进行操作!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						return;
					}
					if(msg == '确定要驳回当前页面所有申请吗?'){
						top.jzts();
						$.ajax({
							type: "POST",
							url: '<%=basePath%>erp/oaaReport.do?tm='+new Date().getTime(),
					    	data: {DATA_IDS:str,"CONFIRM_STATE":"4"},
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
				}
			});
		};
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>erp/oaaExcel.do?confirmState='+$("#confirmState").val()+'&busiDate='+$("#busiDate").val()+'&USER_DEPART='+$("#SelectedDepartCode").val();
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
			$("#selectTree2_input").val(name);
		}
	</script>
</body>
</html>