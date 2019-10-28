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
    .mtable{width:auto;border-collapse:collapse;border:1px solid black;}
    .mtable th, .mtable td{text-align:center;border:1px solid black;}
    .mtable th, .mtable td{position:relative;background-clip:padding-box;}
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
										<span class="green middle bolder">填报类型: &nbsp;</span>
											<div class="btn-toolbar inline middle no-margin">
												<div data-toggle="buttons" class="btn-group no-margin">
													<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toERPOfficialAcctApplication()">
														<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>ERP正式账号申请</span>
													</button>
													<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toERPTempacctApplication()">
														<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>ERP临时账号申请</span>
													</button>
													<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toERPDelAcctApplication()">
														<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>ERP删除账号申请</span>
													</button>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="row" style="width:100%;overflow: auto; height:600px;">
						<form action="erpofficialacctapplication/list.do" method="post" name="Form" id="Form">
							<table style="margin-top:0px; float:left;">
								<tr>
									<td>
										<div class="nav-search">
											<span class="input-icon">
												<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
												<i class="ace-icon fa fa-search nav-search-icon"></i>
											</span>
										</div>
									</td>
									<td style="vertical-align:top;padding-left:5px;">
									 	<span class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
												<div class="selectTree" id="selectTree" multiMode="true"
												    allSelectable="false" noGroup="false"></div>
											    <input type="text" id="SelectedDepartCode" hidden></input>
											</span>
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
							<table id="simple-table" class="mtable" style="margin-top:5px; width:1404px;">	
								<thead style="height: 40px;">
									<tr>
										<th class="center" style="width:35px; background-color: #BEBEC5; padding-left: 5px;padding-right:5px;">
										<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
										</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center; padding-left: 12px;padding-right:12px;">员工编号</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">员工姓名</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">二级单位</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">三级单位</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">职务</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">岗位</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">模块</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">联络电话</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">电子邮件</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">是否培训</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">培训方式</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">培训时间</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">培训成绩</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">证书编号</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">UKey编号</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;padding-left: 12px;padding-right:12px;">申请日期</th>
										<th style="width:110px; background-color: #BEBEC5; text-align: center;">备注</th>
									</tr>
								</thead>
														
								<tbody id="copyTable">
								<!-- 开始循环 -->	
										<c:forEach items="${varList}" var="var" varStatus="vs">
											<tr>
												<td class='center'>
													<label class="pos-rel"><input type='checkbox' name='ids' value="${var.ID}" class="ace" /><span class="lbl"></span></label>
												</td>
												<th><input type="text" class="ui-state-default ui-th-column ui-th-ltr" name="STAFF_CODE" id="STAFF_CODE" readonly="readonly" value="${var.STAFF_CODE}" maxlength="30" title="员工编号" style="width:100%;"/></th>
												<th><input type="text" name="STAFF_NAME" id="STAFF_NAME" readonly="readonly" value="${var.STAFF_NAME}" maxlength="30" title="员工姓名" style="width:100%;"/></th>
												<th><input type="text" name="STAFF_UNIT_LEVEL2" id="STAFF_UNIT_LEVEL2" readonly="readonly" value="${var.STAFF_UNIT_LEVEL2}" maxlength="30" title="二级单位" style="width:100%;"/></th>
												<th><input type="text" name="STAFF_UNIT_LEVEL3" id="STAFF_UNIT_LEVEL3" readonly="readonly" value="${var.STAFF_UNIT_LEVEL3}" maxlength="30" title="三级单位" style="width:100%;"/></th>
												<th><input type="text" name="STAFF_POSITION" id="STAFF_POSITION" readonly="readonly" value="${var.STAFF_POSITION}" maxlength="50" title="职务" style="width:100%;"/></th>
												<th><input type="text" name="STAFF_JOB" id="STAFF_JOB" readonly="readonly" value="${var.STAFF_JOB}" maxlength="10" title="岗位" style="width:100%;"/></th>
												<th><input type="text" name="STAFF_MODULE" id="STAFF_MODULE" readonly="readonly" value="${var.STAFF_MODULE}" maxlength="20" title="模块" style="width:100%;"/></th>
												<th><input type="text" name="PHONE" id="PHONE" readonly="readonly" value="${var.PHONE}" maxlength="30" title="联络电话" style="width:100%;"/></th>
												<th><input type="text" name="MAIL" id="MAIL" readonly="readonly" value="${var.MAIL}" maxlength="30" title="电子邮件" style="width:100%;"/></th>
												<th><input type="text" name="IF_TRAINING" id="IF_TRAINING" readonly="readonly" value="${var.IF_TRAINING}" maxlength="1" title="是否培训" style="width:100%;"/></th>
												<th><input type="text" name="TRAINING_METHOD" id="TRAINING_METHOD" readonly="readonly" value="${var.TRAINING_METHOD}" maxlength="30" title="培训方式" style="width:100%;"/></th>
												<th><input type="text" name="TRAINING_TIME" id="TRAINING_TIME" readonly="readonly" value="${var.TRAINING_TIME}" maxlength="30" title="培训时间" style="width:100%;"/></th>
												<th><input type="text" name="TRAINING_RECORD" id="TRAINING_RECORD" readonly="readonly" value="${var.TRAINING_RECORD}" maxlength="30" title="培训成绩" style="width:100%;"/></th>
												<th><input type="text" name="CERTIFICATE_NUM" id="CERTIFICATE_NUM" readonly="readonly" value="${var.CERTIFICATE_NUM}" maxlength="30" title="证书编号" style="width:100%;"/></th>
												<th><input type="text" name="UKEY_NUM" id="UKEY_NUM" readonly="readonly" value="${var.UKEY_NUM}" maxlength="30" title="UKey编号" style="width:100%;"/></th>
												<th><input type="text" name="APPLY_DATE" id="APPLY_DATE" readonly="readonly" value="${var.APPLY_DATE}" maxlength="30" title="申请日期" style="width:100%;"/></th>
												<th><input type="text" name="NOTE" id="NOTE" readonly="readonly" value="${var.NOTE}" maxlength="30" title="备注" style="width:100%;"/></th>
											</tr>
										</c:forEach>
								</tbody>
							</table>
								<div class="position-relative">
									<table style="width:100%;">
										<tr>
											<td style="vertical-align:top;"><div class="pagination" style="padding-left: 640px;margin-top: 0px;">${page.pageStr}</div></td>
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
	
	<!-- 复制用空表格 -->
		<div id="hideTable" style="display: none;">
			<table>
				<tbody>
					<tr>
						<td class='center'>
							<label class="pos-rel"><input type='checkbox' name='ids' value="" class="ace" /><span class="lbl"></span></label>
						</td>
						<th><input type="text" name="STAFF_CODE" id="STAFF_CODE" value="" maxlength="30" title="员工编号" style="width:100%;"/></th>
						<th><input type="text" name="STAFF_NAME" id="STAFF_NAME" value="" maxlength="30" title="员工姓名" style="width:100%;"/></th>
						<th><input type="text" name="STAFF_UNIT_LEVEL2" id="STAFF_UNIT_LEVEL2" value="" maxlength="30" title="二级单位" style="width:100%;"/></th>
						<th><input type="text" name="STAFF_UNIT_LEVEL3" id="STAFF_UNIT_LEVEL3" value="" maxlength="30" title="三级单位" style="width:100%;"/></th>
						<th><input type="text" name="STAFF_POSITION" id="STAFF_POSITION" value="" maxlength="50" title="职务" style="width:100%;"/></th>
						<th><input type="text" name="STAFF_JOB" id="STAFF_JOB" value="" maxlength="30" title="岗位" style="width:100%;"/></th>
						<th><input type="text" name="STAFF_MODULE" id="STAFF_MODULE" value="" maxlength="20" title="模块" style="width:100%;"/></th>
						<th><input type="text" name="PHONE" id="PHONE" value="" maxlength="30" title="联络电话" style="width:100%;"/></th>
						<th><input type="text" name="MAIL" id="MAIL" value="" maxlength="30" title="电子邮箱" style="width:100%;"/></th>
						<th><input type="text" name="IF_TRAINING" id="IF_TRAINING" value="" maxlength="1" title="是否培训" style="width:100%;"/></th>
						<th><input type="text" name="TRAINING_METHOD" id="TRAINING_METHOD" value="" maxlength="30" title="培训方式" style="width:100%;"/></th>
						<th><input type="text" name="TRAINING_TIME" id="TRAINING_TIME" value="" maxlength="30" title="培训时间" style="width:100%;"/></th>
						<th><input type="text" name="TRAINING_RECORD" id="TRAINING_RECORD" value="" maxlength="30" title="培训成绩" style="width:100%;"/></th>
						<th><input type="text" name="CERTIFICATE_NUM" id="CERTIFICATE_NUM" value="" maxlength="30" title="证书编号" style="width:100%;"/></th>
						<th><input type="text" name="UKEY_NUM" id="UKEY_NUM" value="" maxlength="30" title="UKey编号" style="width:100%;"/></th>
						<th><input type="text" name="APPLY_DATE" id="APPLY_DATE" value="" maxlength="30" title="申请日期" style="width:100%;"/></th>
						<th><input type="text" name="NOTE" id="NOTE" value="" maxlength="30" title="备注" style="width:100%;"/></th>
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
		
		/* 复选框全选控制 */
		$(function() {
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
			for(var i=0;i < document.getElementsByName('STAFF_CODE').length-1;i++){
					//length-1 : 页面中有用于复制的隐藏文本
					//如果有员工编号那么就判定该行数据有效
					codeVal = document.getElementsByName('STAFF_CODE')[i].value;
					if(codeVal!=''){
						codeVal = '';
						listData.push(document.getElementsByName('ids')[i].value);
						listData.push(document.getElementsByName('STAFF_CODE')[i].value);
						listData.push(document.getElementsByName('STAFF_NAME')[i].value);
						listData.push(document.getElementsByName('STAFF_UNIT_LEVEL2')[i].value);
						listData.push(document.getElementsByName('STAFF_UNIT_LEVEL3')[i].value);
						listData.push(document.getElementsByName('STAFF_POSITION')[i].value);
						listData.push(document.getElementsByName('STAFF_JOB')[i].value);
						listData.push(document.getElementsByName('STAFF_MODULE')[i].value);
						listData.push(document.getElementsByName('PHONE')[i].value);
						listData.push(document.getElementsByName('MAIL')[i].value);
						listData.push(document.getElementsByName('IF_TRAINING')[i].value);
						listData.push(document.getElementsByName('TRAINING_METHOD')[i].value);
						listData.push(document.getElementsByName('TRAINING_TIME')[i].value);
						listData.push(document.getElementsByName('TRAINING_RECORD')[i].value);
						listData.push(document.getElementsByName('CERTIFICATE_NUM')[i].value);
						listData.push(document.getElementsByName('UKEY_NUM')[i].value);
						listData.push(document.getElementsByName('APPLY_DATE')[i].value);
						listData.push(document.getElementsByName('NOTE')[i].value);
				}
			}
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>erpofficialacctapplication/saveAndEdit.do?tm='+new Date().getTime(),
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
								url: '<%=basePath%>erpofficialacctapplication/deleteAll.do?tm='+new Date().getTime(),
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
	    	   diag.URL = '<%=basePath%>erpofficialacctapplication/goUploadExcel.do';
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
			window.location.href='<%=basePath%>erpofficialacctapplication/excel.do';
		}
		
		//下拉树
		var defaultNodes = {"treeNodes":${zTreeNodes}};
		function initComplete(){
			//绑定change事件
			$("#selectTree").bind("change",function(){
				$("#SelectedDepartCode").val("");
				if($(this).attr("relValue")){
					$("#SelectedDepartCode").val($(this).attr("relValue"));
			    }
			});
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			$("#selectTree2_input").val("请选择单位");
		}
	</script>
	

</body>
</html>