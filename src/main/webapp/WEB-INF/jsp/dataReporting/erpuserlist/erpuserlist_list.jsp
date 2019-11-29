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
<!--自由拉动  -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<link rel="stylesheet" href="static/ace/css/jquery-ui.css" />
<style>
    .mtable{width:auto;border-collapse:collapse;border:1px solid black;}
    .mtable th, .mtable td{height:30px;text-align:center;border:1px solid black;}
    .mtable th, .mtable td{position:relative;background-clip:padding-box;}
</style>
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="page-header">
					</div>
					<div class="row">
						<form action="erpuserlist/list.do" method="post" name="Form" id="Form">
							<table style="margin-bottom: 6px;">
								<tr>
									<td>
										<select class="form-control" id="busiDate" name="busiDate" style="vertical-align:top; width:150px;margin-left: 5px;" data-placeholder="请选择业务期间">
											<option value=""></option>
											<c:forEach items="${listBusiDate}" var="var">
												<option value="${var.BUSI_DATE}" <c:if test="${pd.busiDate == var.BUSI_DATE}">selected="selected"</c:if>>${var.BUSI_DATE}</option>
											</c:forEach>
										</select>
									</td>
									<td style="vertical-align:top;padding-left:2px;">
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
							<div  style="width:100%;overflow:auto;height: 430px;">
								<table id="simple-table" class="mtable" style="margin-top:5px;width:99%">	
									<thead>
										<tr>
											<th class="center" style="width:35px; background-color: #BEBEC5;">
												<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox"/><span class="lbl"></span></label>
											</th>
											<th style="width:110px; height:30px; background-color: #BEBEC5; text-align: center;">用户名</th>
											<th style="width:110px; background-color: #BEBEC5; text-align: center;">姓名</th>
											<th style="width:110px; background-color: #BEBEC5; text-align: center;">用户组</th>
											<th style="width:110px; background-color: #BEBEC5; text-align: center;">账号状态</th>
											<th style="width:110px; background-color: #BEBEC5; text-align: center;">有效期自</th>
											<th style="width:110px; background-color: #BEBEC5; text-align: center;">有效期至</th>
											<th style="width:100px; background-color: #BEBEC5; text-align: center;">单位</th>
											<th style="width:110px; background-color: #BEBEC5; text-align: center;">岗位</th>
											<th style="width:120px; background-color: #BEBEC5; text-align: center;">变更序号</th>
											<th style="width:120px; background-color: #BEBEC5; text-align: center;">电话</th>
											
										</tr>
									</thead>
									<tbody id="copyTable">
										<!-- 开始循环 -->	
										<c:forEach items="${varList}" var="pd" varStatus="vs">
											<tr>
												<td class='center'>
													<label class="pos-rel"><input style="background-color: #BEBEC5;" type='checkbox' name='ids' value="${pd.ID}" class="ace"/><span class="lbl"></span></label>
												</td>
												<th><input type="text" name="USER_NAME" id="USER_NAME" readonly="readonly" value="${pd.USER_NAME}" maxlength="30" title="用户名" style="width:100%;"/></th>
												<th><input type="text" name="NAME" id="NAME" readonly="readonly" value="${pd.NAME}" maxlength="30" title="姓名" style="width:100%;"/></th>
												<th><input type="text" name="USER_GROUP" id="USER_GROUP" readonly="readonly" value="${pd.USER_GROUP}" maxlength="30" title="用户组" style="width:100%;"/></th>
												<th><input type="text" name="ACCOUNT_STATE" id="ACCOUNT_STATE" readonly="readonly" value="${pd.ACCOUNT_STATE}" maxlength="30" title="账号状态" style="width:100%;"/></th>
												<th><input type="text" name="START_DATE" id="START_DATE" readonly="readonly" value="${pd.START_DATE}" maxlength="50" title="有效期自" style="width:100%;"/></th>
												<th><input type="text" name="END_DATE" id="END_DATE" readonly="readonly" value="${pd.END_DATE}" maxlength="50" title="有效期至" style="width:100%;"/></th>
												<th><input type="text" name="DEPART" id="DEPART" readonly="readonly" value="${pd.DEPART}" maxlength="20" title="单位" style="width:100%;"/></th>
												<th><input type="text" name="JOB" id="JOB" readonly="readonly" value="${pd.JOB}" maxlength="20" title="岗位" style="width:100%;"/></th>
												<th><input type="text" name="CHANGE_NO" id="CHANGE_NO" readonly="readonly" value="${pd.CHANGE_NO}" maxlength="50" title="变更序号" style="width:100%;"/></th>
												<th><input type="text" name="PHONE" id="PHONE" readonly="readonly" value="${pd.PHONE}" maxlength="50" title="电话" style="width:100%;"/></th>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- 分页 -->
							<div class="position-relative page-header pull-right">
								<table style="width:100%;">
									<tr>
										<td style="vertical-align:top;"><div class="pagination">${page.pageStr}</div></td>
									</tr>
								</table>
							</div>
						</form>
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
	<div id="hideTable" style="display: none;">
	<table>
		<tbody>
			<tr>
				<td class='center'>
					<label class="pos-rel"><input style="background-color: #BEBEC5;" type='checkbox' name='ids' value="" class="ace"/><span class="lbl"></span></label>
				</td>
				<th><input type="text" name="USER_NAME" id="USER_NAME" value="" maxlength="30" title="用户名" style="width:100%;"/></th>
				<th><input type="text" name="NAME" id="NAME" value="" maxlength="30" title="姓名" style="width:100%;"/></th>
				<th><input type="text" name="USER_GROUP" id="USER_GROUP" value="" maxlength="30" title="用户组" style="width:100%;"/></th>
				<th><input type="text" name="ACCOUNT_STATE" id="ACCOUNT_STATE" value="" maxlength="30" title="账号状态" style="width:100%;"/></th>
				<th><input type="text" name="START_DATE" id="START_DATE" value="" maxlength="50" title="有效期自" style="width:100%;"/></th>
				<th><input type="text" name="END_DATE" id="END_DATE" value="" maxlength="50" title="有效期至" style="width:100%;"/></th>
				<th><input type="text" name="DEPART" id="DEPART" value="" maxlength="20" title="单位" style="width:100%;"/></th>
				<th><input type="text" name="JOB" id="JOB" value="" maxlength="20" title="岗位" style="width:100%;"/></th>
				<th><input type="text" name="CHANGE_NO" id="CHANGE_NO" value="" maxlength="50" title="变更序号" style="width:100%;"/></th>
				<th><input type="text" name="PHONE" id="PHONE" value="" maxlength="50" title="电话" style="width:100%;"/></th>
			</tr>
		</tbody>
	</table>
</div>
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
	<!-- 自由拉动 -->
	<script type="text/javascript" src="static/ace/js/jquery-ui.js"></script>
	
	<script type="text/javascript">
	/* 关闭加载状态 */
	$(top.hangge());
	
	/* 复选框全选控制 */
	$(function() {
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
		});
		$("th > div:last-child").removeClass();
		//全选按钮
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
	function tosearch() {
		top.jzts();
		$("#Form").submit();
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
		var ID = '';
		var USER_NAME = '';
		var NAME = '';
		var USER_GROUP = '';
		var ACCOUNT_STATE = '';
		var START_DATE = '';
		var END_DATE = '';
		var DEPART = '';
		var JOB = '';
		var CHANGE_NO = '';
		var PHONE = '';
		var codeVal = '';
		var listData = new Array();
		for(var i=0;i < document.getElementsByName('USER_NAME').length-1;i++){
			//length-1 : 页面中有用于复制的隐藏文本
				//如果有员工编号那么就判定该行数据有效
				codeVal = document.getElementsByName('USER_NAME')[i].value;
				if(codeVal!=''){
					codeVal = '';
					ID = document.getElementsByName('ids')[i].value;
					USER_NAME = document.getElementsByName('USER_NAME')[i].value;
					NAME = document.getElementsByName('NAME')[i].value;
					USER_GROUP = document.getElementsByName('USER_GROUP')[i].value;
					ACCOUNT_STATE = document.getElementsByName('ACCOUNT_STATE')[i].value;
					START_DATE = document.getElementsByName('START_DATE')[i].value;
					END_DATE = document.getElementsByName('END_DATE')[i].value;
					DEPART = document.getElementsByName('DEPART')[i].value;
					JOB = document.getElementsByName('JOB')[i].value;
					CHANGE_NO = document.getElementsByName('CHANGE_NO')[i].value;
					PHONE = document.getElementsByName('PHONE')[i].value;
					listData.push(ID);
					listData.push(USER_NAME);
					listData.push(NAME);
					listData.push(USER_GROUP);
					listData.push(ACCOUNT_STATE);
					listData.push(START_DATE);
					listData.push(END_DATE);
					listData.push(DEPART);
					listData.push(JOB);
					listData.push(CHANGE_NO);
					listData.push(PHONE);
			}
		}
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>erpuserlist/saveAndEdit.do?tm='+new Date().getTime(),
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
	
	/* 批量操作 */
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
							url: '<%=basePath%>erpuserlist/deleteAll.do?tm='+new Date().getTime(),
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
	
	/* 导出Excel */
	function toExcel(){
		window.location.href='<%=basePath%>erpuserlist/excel.do';
	}
	
	/* 导入Excel */
	function importExcel(){
		   top.jzts();
    	   var diag = new top.Dialog();
    	   diag.Drag=true;
    	   diag.Title ="EXCEL 导入到数据库";
    	   diag.URL = '<%=basePath%>erpuserlist/goUploadExcel.do';
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
	</script>
	<script type="text/javascript">
		<%-- $(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
		
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
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>erpuserlist/delete.do?ERPUSERLIST_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>erpuserlist/goEdit.do?ERPUSERLIST_ID='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
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
								url: '<%=basePath%>erpuserlist/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>erpuserlist/excel.do';
		}

 --%>
	</script>
</body>
</html>