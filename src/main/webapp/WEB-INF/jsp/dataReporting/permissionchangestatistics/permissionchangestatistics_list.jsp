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
<!--自由拉动  -->
 <link rel="stylesheet" href="static/ace/css/jquery-ui.css" />
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
						<form action="permissionchangestatistics/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="vertical-align:top;padding-left:6px;">
								 	<select class="chosen-select form-control" name="name" id="id" data-placeholder="请选择" style="vertical-align:top;width: 220px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="">1</option>
									<option value="">2</option>
								  	</select>
								</td>
								<td style="vertical-align:top;padding-left:3px;">
						<a class="btn btn-info btn-sm" onclick="tosearch()"><i class="ace-icon fa fa-search bigger-110"></i></a>
						<a class="btn btn-white btn-info btn-bold" onclick="addRows()"><span class="ace-icon fa fa-plus-circle purple"></span>添加</a>						
						<a class="btn btn-white btn-info btn-bold" onclick="edit()"><span class="ace-icon fa fa-pencil-square-o purple"></span>编辑</a>
						<a class="btn btn-white btn-info btn-bold" onclick="save()"><i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>保存</a>
						<a class="btn btn-white btn-info btn-bold" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class="ace-icon fa fa-trash-o bigger-120 orange"></i>删除</a>
						<a class="btn btn-white btn-info btn-bold" onclick="importExcel()"><span class="ace-icon fa fa-cloud-upload"></span>导入</a>
						<a class="btn btn-white btn-info btn-bold" onclick="toExcel()"><span class="ace-icon fa fa-cloud-download"></span>导出</a>
							</td>
							</tr>
						</table>
						<div class="tabbable" style="margin-top:5px">
							<ul class="nav nav-tabs padding-18">
								<li class="active"><a data-toggle="tab" href="#voucherTransfer"> <i class="green ace-icon fa fa-user bigger-120"></i> 权限变更统计
								</a></li>
							</ul>
							<div class="tab-content no-border" style="margin-top: -15px"></div>
						</div>
						<!-- 检索  -->
						<table id="simple-table" border='1' class="" style="margin-top:5px;">		
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">公司名称</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">账号延期</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">账号解除锁定</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">新增角色</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">删除角色</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">账号新增</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">账号删除</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">增加FMIS角色</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">删除FMIS角色</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">变更用户组</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">变更人次</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">创建人</th>
									<th style="width:110px; background-color: #BEBEC5; text-align: center;">创建日期</th>
								</tr>
							</thead>
													
							<tbody id="copyTable">
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<th><input type="text" name="COMPANY_NAME" id="COMPANY_NAME" readonly="readonly" value="${var.COMPANY_NAME}" maxlength="100" title="公司名称" style="width:100%;"/></th>
											<th><input type="number" name="ACCOUNT_DELAY" id="ACCOUNT_DELAY" readonly="readonly" value="${var.ACCOUNT_DELAY}" maxlength="11" title="账号延期" style="width:100%;"/></th>
											<th><input type="number" name="ACCOUNT_UNLOCK" id="ACCOUNT_UNLOCK" readonly="readonly" value="${var.ACCOUNT_UNLOCK}" maxlength="11" title="账号解除锁定" style="width:100%;"/></th>
											<th><input type="number" name="NEW_ROLES" id="NEW_ROLES" readonly="readonly" value="${var.NEW_ROLES}" maxlength="11" title="新增角色" style="width:100%;"/></th>
											<th><input type="number" name="DELETE_ROLES" id="DELETE_ROLES" readonly="readonly" value="${var.DELETE_ROLES}" maxlength="11" title="删除角色" style="width:100%;"/></th>
											<th><input type="number" name="NEW_ACCOUNTS" id="NEW_ACCOUNTS" readonly="readonly" value="${var.NEW_ACCOUNTS}" maxlength="11" title="账号新增" style="width:100%;"/></th>
											<th><input type="number" name="DELETE_ACCOUNTS" id="DELETE_ACCOUNTS" readonly="readonly" value="${var.DELETE_ACCOUNTS}" maxlength="11" title="账号删除" style="width:100%;"/></th>
											<th><input type="number" name="NEW_FMIS_ROLES" id="NEW_FMIS_ROLES" readonly="readonly" value="${var.NEW_FMIS_ROLES}" maxlength="11" title="增加FMIS角色" style="width:100%;"/></th>
											<th><input type="number" name="DELETE_FMIS_ROLES" id="DELETE_FMIS_ROLES" readonly="readonly" value="${var.DELETE_FMIS_ROLES}" maxlength="11" title="删除FMIS角色" style="width:100%;"/></th>
											<th><input type="text" name="CHANGE_USER_GROUP" id="CHANGE_USER_GROUP" readonly="readonly" value="${var.CHANGE_USER_GROUP}" maxlength="50" title="变更用户组" style="width:100%;"/></th>
											<th><input type="number" name="CHANGE_PERSON_COUNT" id="CHANGE_PERSON_COUNT" readonly="readonly" value="${var.CHANGE_PERSON_COUNT}" maxlength="11" title="变更人次" style="width:100%;"/></th>
											<th><input type="text" name="BILL_USER" id="BILL_USER" readonly="readonly" value="${var.BILL_USER}" maxlength="20" title="创建人" style="width:100%;"/></th>
											<th><input type="text" name="BILL_DATE" id="BILL_DATE" readonly="readonly" value="${var.BILL_DATE}" maxlength="30" title="创建日期" style="width:100%;"/></th>
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
						</form>
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
	<div id="hideTable" style="display: none;">
	<table>
		<tbody>
			<tr>
			<td class='center'>
					<label class="pos-rel"><input type='checkbox' name='ids' value="${var.ID}" class="ace" /><span class="lbl"></span></label>
				</td>
				<th><input type="text" name="COMPANY_NAME" id="COMPANY_NAME" value="" maxlength="100" title="公司名称" style="width:100%;"/></th>
				<th><input type="number" name="ACCOUNT_DELAY" id="ACCOUNT_DELAY" value="" maxlength="11" title="账号延期" style="width:100%;"/></th>
				<th><input type="number" name="ACCOUNT_UNLOCK" id="ACCOUNT_UNLOCK" value="" maxlength="11" title="账号解除锁定" style="width:100%;"/></th>
				<th><input type="number" name="NEW_ROLES" id="NEW_ROLES" value="" maxlength="11" title="新增角色" style="width:100%;"/></th>
				<th><input type="number" name="DELETE_ROLES" id="DELETE_ROLES" value="" maxlength="11" title="删除角色" style="width:100%;"/></th>
				<th><input type="number" name="NEW_ACCOUNTS" id="NEW_ACCOUNTS" value="" maxlength="11" title="账号新增" style="width:100%;"/></th>
				<th><input type="number" name="DELETE_ACCOUNTS" id="DELETE_ACCOUNTS" value="" maxlength="11" title="账号删除" style="width:100%;"/></th>
				<th><input type="number" name="NEW_FMIS_ROLES" id="NEW_FMIS_ROLES" value="${var.NEW_FMIS_ROLES}" maxlength="11" title="增加FMIS角色" style="width:100%;"/></th>
				<th><input type="number" name="DELETE_FMIS_ROLES" id="DELETE_FMIS_ROLES" value="" maxlength="11" title="删除FMIS角色" style="width:100%;"/></th>
				<th><input type="text" name="CHANGE_USER_GROUP" id="CHANGE_USER_GROUP" value="" maxlength="50" title="变更用户组" style="width:100%;"/></th>
				<th><input type="number" name="CHANGE_PERSON_COUNT" id="CHANGE_PERSON_COUNT" value="" maxlength="11" title="变更人次" style="width:100%;"/></th>
				<th><input type="text" name="BILL_USER" id="BILL_USER" value="" maxlength="20" title="创建人" style="width:100%;"/></th>
				<th><input type="text" name="BILL_DATE" id="BILL_DATE" value="" maxlength="30" title="创建日期" style="width:100%;"/></th>
			</tr>
		</tbody>
	</table>
	</div>
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
	/* 复选框全选控制 */
	$(function() {
		$("th").resizable(); //调用方法，实现可自由调整
		$("th > div:last-child").removeClass();
		
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
	
	/* 检索 */
	function tosearch(){
		top.jzts();
		$("#Form").submit();
	}
	
	/* ERP正式账号申请 */
	function toERPOfficialAcctApplication(){
		window.location.href='<%=basePath%>erpofficialacctapplication/list.do';
	}
	
	/* ERP临时账号申请 */
	function toERPTempacctApplication(){
		window.location.href='<%=basePath%>erptempacctapplication/list.do';
	}
	/* ERP删除账号申请 */
	function toERPDelAcctApplication(){
		window.location.href='<%=basePath%>erpdelacctapplication/list.do';
	}
	
	/* 新增一行 */
	function addRows(){
    	$("#hideTable table tbody tr").clone().appendTo("#copyTable");	           
    }
	
	/* 去除所有input标签的只读属性 */
	function edit(){
		$('input,select,textarea',$('form[name="Form"]')).prop('readonly',false);
	}
	
	/* 保存 */
	function save(){
		var listData = new Array();
		for(var i=0;i < document.getElementsByName('COMPANY_NAME').length-1;i++){
				//length-1 : 页面中有用于复制的隐藏文本
				//如果有员工编号那么就判定该行数据有效
				codeVal = document.getElementsByName('COMPANY_NAME')[i].value;
				if(codeVal!=''){
					codeVal = '';
					listData.push(document.getElementsByName('ids')[i].value);
					listData.push(document.getElementsByName('COMPANY_NAME')[i].value);
					listData.push(document.getElementsByName('ACCOUNT_DELAY')[i].value);
					listData.push(document.getElementsByName('ACCOUNT_UNLOCK')[i].value);
					listData.push(document.getElementsByName('NEW_ROLES')[i].value);
					listData.push(document.getElementsByName('DELETE_ROLES')[i].value);
					listData.push(document.getElementsByName('NEW_ACCOUNTS')[i].value);
					listData.push(document.getElementsByName('DELETE_ACCOUNTS')[i].value);
					listData.push(document.getElementsByName('NEW_FMIS_ROLES')[i].value);
					listData.push(document.getElementsByName('DELETE_FMIS_ROLES')[i].value);
					listData.push(document.getElementsByName('CHANGE_USER_GROUP')[i].value);
					listData.push(document.getElementsByName('CHANGE_PERSON_COUNT')[i].value);
					listData.push(document.getElementsByName('BILL_USER')[i].value);
					listData.push(document.getElementsByName('BILL_DATE')[i].value);
			}
		}
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>permissionchangestatistics/saveAndEdit.do?tm='+new Date().getTime(),
	    	data: {"listData":JSON.stringify(listData)},
			dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$(top.hangge());//关闭加载状态
					history.go(0); //刷新页面
					$("#subTitle").tips({
						side:3,
			            msg:'添加修改成功',
			            bg:'#009933',
			            time:3
			        });
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'添加修改失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(e) {
	    		$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'添加修改失败,'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
		});
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
							url: '<%=basePath%>permissionchangestatistics/deleteAll.do?tm='+new Date().getTime(),
					    	data: {DATA_IDS:str},
							dataType:'json',
							cache: false,
							success: function(response){
								if(response.code==0){
									$(top.hangge());//关闭加载状态
									history.go(0); //刷新页面
									$("#subTitle").tips({
										side:3,
							            msg:'删除成功',
							            bg:'#009933',
							            time:3
							        });
								}else{
									$(top.hangge());//关闭加载状态
									$("#subTitle").tips({
										side:3,
							            msg:'删除失败,'+response.message,
							            bg:'#cc0033',
							            time:3
							        });
								}
							},
					    	error: function(e) {
					    		$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'删除失败,'+response.responseJSON.message,
						            bg:'#cc0033',
						            time:3
						        });
					    	}
						});
					}
				}
			}
		});
	};
	
	/* 导入Excel */
	function importExcel(){
		   top.jzts();
    	   var diag = new top.Dialog();
    	   diag.Drag=true;
    	   diag.Title ="EXCEL 导入到数据库";
    	   diag.URL = '<%=basePath%>permissionchangestatistics/goUploadExcel.do';
    	   diag.Width = 300;
    	   diag.Height = 150;
    	   diag.CancelEvent = function(){ //关闭事件
    		  top.jzts();
    		  history.go(0); //刷新页面
    		  $(top.hangge());//关闭加载状态
    	      diag.close();
           };
           diag.show();
	}		
		
	//导出excel
	function toExcel(){
		window.location.href='<%=basePath%>permissionchangestatistics/excel.do';
	}
	</script>


</body>
</html>