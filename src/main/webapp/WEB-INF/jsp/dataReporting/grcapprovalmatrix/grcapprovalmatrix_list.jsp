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
							<table style="width:100%;">
							<tbody>
							<tr>
								<td style="vertical-align:top;">
										<div class="pagination" style="padding-left: 935px;margin-top: 0px;">
											<button class="btn btn-sm btn-primary" onclick="toGRCPerson()"><font style="vertical-align: inherit;">GRC人员信息</font></button>						
											<button class="btn btn-sm btn-primary" onclick="toGRCApprovalMatrix()"><font style="vertical-align: inherit;  color: blue">GRC审批矩阵</font></button>	
									</div>
								</td>
							</tr>
						</tbody>
						</table>
						<!-- 检索  -->
						<form action="grcapprovalmatrix/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:-15px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
						<td style="vertical-align:top;padding-left:2px;">
						<a class="btn btn-info btn-sm" onclick="tosearch()"><i class="ace-icon fa fa-search bigger-110"></i></a>
						<a class="btn btn-white btn-info btn-bold" onclick="addRows()"><span class="ace-icon fa fa-plus-circle purple"></span>添加</a>						
						<a class="btn btn-white btn-info btn-bold" onclick="edit()"><span class="ace-icon fa fa-pencil-square-o purple"></span>编辑</a>
						<a class="btn btn-white btn-info btn-bold" onclick="save()"><i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>保存</a>
						<a class="btn btn-white btn-info btn-bold" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class="ace-icon fa fa-trash-o bigger-120 orange"></i>删除</a>
						<a class="btn btn-white btn-info btn-bold" onclick="importExcel()"><span class="ace-icon fa fa-cloud-upload"></span>导入</a>
						<a class="btn btn-white btn-info btn-bold" onclick="toExcel()"><span class="ace-icon fa fa-cloud-download"></span>导出</a>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" border='1' class="" style="margin-top:5px; width: 1150px;">	
							<thead style="height: 40px">
								<tr>
									<th class="center" style="width:35px; background-color: #BEBEC5;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th style="width:90px; background-color: #BEBEC5; text-align: center;">业务模块</th>
									<th style="width:90px; background-color: #BEBEC5; text-align: center;">申请人员工编号</th>
									<th style="width:90px; background-color: #BEBEC5; text-align: center;">申请人员工姓名</th>
									<th style="width:90px; background-color: #BEBEC5; text-align: center;">一级审批人员工编号</th>
									<th style="width:90px; background-color: #BEBEC5; text-align: center;">一级审批人员工姓名</th>
									<th style="width:90px; background-color: #BEBEC5; text-align: center;">创建人</th>
									<th style="width:90px; background-color: #BEBEC5; text-align: center;">创建日期</th>
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
											<th><input type="text" name="BUSINESS_MODULE" id="BUSINESS_MODULE" readonly="readonly" value="${var.BUSINESS_MODULE}" maxlength="50" title="业务模块" style="width:100%;"/></th>
											<th><input type="text" name="STAFF_CODE" id="STAFF_CODE" readonly="readonly" value="${var.STAFF_CODE}" maxlength="30" title="申请人员工编号" style="width:100%;"/></th>
											<th><input type="text" name="STAFF_NAME" id="STAFF_NAME" readonly="readonly" value="${var.STAFF_NAME}" maxlength="30" title="申请人员工姓名" style="width:100%;"/></th>
											<th><input type="text" name="STAFF_CODE_APPROVAL1" id="STAFF_CODE_APPROVAL1" readonly="readonly" value="${var.STAFF_CODE_APPROVAL1}" maxlength="30" title="一级审批人员工编号" style="width:100%;"/></th>
											<th><input type="text" name="STAFF_NAME_APPROVAL1" id="STAFF_NAME_APPROVAL1" readonly="readonly" value="${var.STAFF_NAME_APPROVAL1}" maxlength="30" title="一级审批人员工姓名" style="width:100%;"/></th>
											<th><input type="text" name="BILL_USER" id="BILL_USER" readonly="readonly" value="${var.BILL_USER}" maxlength="20" title="创建人" style="width:100%;"/></th>
											<th><input type="text" name="BILL_DATE" id="BILL_DATE" readonly="readonly" value="${var.BILL_DATE}" maxlength="30" title="创建日期" style="width:100%;"/></th>
										</tr>
									
									</c:forEach>
									<%-- <c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if> --%>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<!-- 分页 -->
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;"><div class="pagination" style="padding-left: 495px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						<!-- 分页结束 -->
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
	<!-- 复制用空表格 -->
	<div id="hideTable" style="display: none;">
		<table>
			<tbody>
				<tr>
					<td class='center'>
					<label class="pos-rel"><input style="background-color: #BEBEC5;" type='checkbox' name='ids' value="" class="ace"/><span class="lbl"></span></label>
					</td>
					<th><input type="text" name="BUSINESS_MODULE" id="BUSINESS_MODULE" value="" maxlength="50" title="业务模块" style="width:100%;"/></th>
					<th><input type="text" name="STAFF_CODE" id="STAFF_CODE" value="" maxlength="30" title="申请话其人员工编号" style="width:100%;"/></th>
					<th><input type="text" name="STAFF_NAME" id="STAFF_NAME" value="" maxlength="30" title="申请话其人员工姓名" style="width:100%;"/></th>
					<th><input type="text" name="STAFF_CODE_APPROVAL1" id="STAFF_CODE_APPROVAL1" value="" maxlength="30" title="一级审批人员工编号" style="width:100%;"/></th>
					<th><input type="text" name="STAFF_NAME_APPROVAL1" id="STAFF_NAME_APPROVAL1" value="" maxlength="30" title="一级审批人员工姓名" style="width:100%;"/></th>
					<th><input type="text" name="BILL_USER" id="BILL_USER" value="" maxlength="20" title="创建人" style="width:100%;"/></th>
					<th><input type="text" name="BILL_DATE" id="BILL_DATE" value="" maxlength="30" title="创建日期" style="width:100%;"/></th>
				</tr>
			</tbody>
		</table>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 自由拉动 -->
	<script type="text/javascript" src="static/ace/js/jquery-ui.js"></script>
	<script type="text/javascript">
		/* 关闭加载状态 */
		$(top.hangge());
		
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
		
		/* GRC人员信息 */
		function toGRCPerson(){
			window.location.href='<%=basePath%>grcperson/list.do';
		}
		
		/* GRC审批矩阵 */
		function toGRCApprovalMatrix(){
			window.location.href='<%=basePath%>grcapprovalmatrix/list.do';
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
			var BUSINESS_MODULE = '';
			var STAFF_CODE = '';
			var STAFF_NAME = '';
			var STAFF_CODE_APPROVAL1 = '';
			var STAFF_NAME_APPROVAL1 = '';
			var BILL_USER = '';
			var BILL_DATE = '';
			var codeVal = '';
			var listData = new Array();
			for(var i=0;i < document.getElementsByName('STAFF_CODE').length-1;i++){
					//length-1 : 页面中有用于复制的隐藏文本
					//如果有员工编号那么就判定该行数据有效
					codeVal = document.getElementsByName('STAFF_CODE')[i].value;
					if(codeVal!=''){
						codeVal = '';
						ID = document.getElementsByName('ids')[i].value;
						BUSINESS_MODULE = document.getElementsByName('BUSINESS_MODULE')[i].value;
						STAFF_CODE = document.getElementsByName('STAFF_CODE')[i].value;
						STAFF_NAME = document.getElementsByName('STAFF_NAME')[i].value;
						STAFF_CODE_APPROVAL1 = document.getElementsByName('STAFF_CODE_APPROVAL1')[i].value;
						STAFF_NAME_APPROVAL1 = document.getElementsByName('STAFF_NAME_APPROVAL1')[i].value;
						BILL_USER = document.getElementsByName('BILL_USER')[i].value;
						BILL_DATE = document.getElementsByName('BILL_DATE')[i].value;
						
						listData.push(ID);
						listData.push(BUSINESS_MODULE);
						listData.push(STAFF_CODE);
						listData.push(STAFF_NAME);
						listData.push(STAFF_CODE_APPROVAL1);
						listData.push(STAFF_NAME_APPROVAL1);
						listData.push(BILL_USER);
						listData.push(BILL_DATE);
				}
			}
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>grcapprovalmatrix/saveAndEdit.do?tm='+new Date().getTime(),
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
								url: '<%=basePath%>grcapprovalmatrix/deleteAll.do?tm='+new Date().getTime(),
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
	    	   diag.URL = '<%=basePath%>grcapprovalmatrix/goUploadExcel.do';
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
		
		/* 导出excel */
		function toExcel(){
			window.location.href='<%=basePath%>grcapprovalmatrix/excel.do';
		}
	</script>
</body>
</html>