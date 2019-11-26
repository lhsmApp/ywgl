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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!--自由拉动  -->
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
					<div class="row" style="width: 100%;overflow: auto;">
						<div class="col-xs-12">
							
						<!-- 检索  -->
						<form action="operationstatistics/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:15px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								
							<%-- 	<td style="vertical-align:top;padding-left:2px;">
								 	<span class="pull-left" style="margin-right: 5px;">
										<div class="selectTree" id="selectTree" multiMode="true" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if> allSelectable="false" noGroup="false"></div>
									    <input type="text" id="SelectedDepartCode" hidden></input>
									</span>
								</td> --%>
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
						<!-- 检索  -->
						<table id="simple-table" class="mtable" style="margin-top:10px; width:99%;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th style="width:150px; height:30px; background-color: #BEBEC5; text-align: center;">单位</th>
									<th style="width:150px; background-color: #BEBEC5; text-align: center;">机关计算机运维数量</th>
									<th style="width:150px; background-color: #BEBEC5; text-align: center;">网络运维数量</th>
									<th style="width:150px; background-color: #BEBEC5; text-align: center;">信息安全运维数量</th>
									<th style="width:150px; background-color: #BEBEC5; text-align: center;">ERP运维数量</th>
									<th style="width:150px; background-color: #BEBEC5; text-align: center;">云桌面运维数量</th>
									<th style="width:150px; background-color: #BEBEC5; text-align: center;">合计</th>
								</tr>
							</thead>
													
							<tbody id="copyTable">
							<!-- 开始循环 -->	
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<th><input type="text" name="COMPANY_NAME" id="COMPANY_NAME" readonly="readonly" value="${var.COMPANY_NAME}" maxlength="100" title="单位" style="width:100%;"/></th>
											<th><input type="text" name="AGENCY_OPER_NUM" id="AGENCY_OPER_NUM" readonly="readonly" value="${var.AGENCY_OPER_NUM}" maxlength="11" title="机关计算机运维数量" style="width:100%;"/></th>
											<th><input type="text" name="NETWORK_OPER_NUM" id="NETWORK_OPER_NUM" readonly="readonly" value="${var.NETWORK_OPER_NUM}" maxlength="21" title="网络运维数量" style="width:100%;"/></th>
											<th><input type="text" name="SECURITY_OPER_NUM" id="SECURITY_OPER_NUM" readonly="readonly" value="${var.SECURITY_OPER_NUM}" maxlength="11" title="信息安全运维数量" style="width:100%;"/></th>
											<th><input type="text" name="ERP_OPER_NUM" id="ERP_OPER_NUM" readonly="readonly" value="${var.ERP_OPER_NUM}" maxlength="11" title="ERP运维数量" style="width:100%;"/></th>
											<th><input type="text" name="CLOUD_OPER_NUM" id="CLOUD_OPER_NUM" readonly="readonly" value="${var.CLOUD_OPER_NUM}" maxlength="11" title="云桌面运维数量" style="width:100%;"/></th>
											<th><input type="text" name="TOTAL_OPER_NUM" id="TOTAL_OPER_NUM" readonly="readonly" value="${var.TOTAL_OPER_NUM}" maxlength="11" title="合计" style="width:100%;"/></th>
										</tr>
									</c:forEach>
							</tbody>
						</table>
						
						<div class="position-relative page-header pull-right">
						
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;"><div class="pagination">${page.pageStr}</div></td>
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
		<div id="hideTable" style="display: none;">
		<table>
			<tbody>
			<tr>
				<td class='center'>
					<label class="pos-rel"><input type='checkbox' name='ids' value="${var.ID}" class="ace" /><span class="lbl"></span></label>
				</td>
				<th><input type="text" name="COMPANY_NAME" id="COMPANY_NAME" value="" maxlength="50" title="单位" style="width:100%;"/></th>
				<th><input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" name="AGENCY_OPER_NUM" id="AGENCY_OPER_NUM" value="" maxlength="100" title="机关计算机运维数量" style="width:100%;"/></th>
				<th><input type="text" name="NETWORK_OPER_NUM" id="NETWORK_OPER_NUM" value="" maxlength="11" title="网络运维数量" style="width:100%;"/></th>
				<th><input type="text" name="SECURITY_OPER_NUM" id="SECURITY_OPER_NUM" value="" maxlength="11" title="信息安全运维数量" style="width:100%;"/></th>
				<th><input type="text" name="ERP_OPER_NUM" id="ERP_OPER_NUM" value="" maxlength="11" title="ERP运维数量" style="width:100%;"/></th>
				<th><input type="text" name="CLOUD_OPER_NUM" id="CLOUD_OPER_NUM" value="" maxlength="11" title="云桌面运维数量" style="width:100%;"/></th>
				<th><input type="text" name="TOTAL_OPER_NUM" id="TOTAL_OPER_NUM"  value="" maxlength="11" title="合计" style="width:100%;"/></th>
			</tr>
			</tbody>
		</table>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 自由拉动 -->
	<script type="text/javascript" src="static/ace/js/jquery-ui.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
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
			})
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
						listData.push(document.getElementsByName('AGENCY_OPER_NUM')[i].value);
						listData.push(document.getElementsByName('NETWORK_OPER_NUM')[i].value);
						listData.push(document.getElementsByName('SECURITY_OPER_NUM')[i].value);
						listData.push(document.getElementsByName('ERP_OPER_NUM')[i].value);
						listData.push(document.getElementsByName('CLOUD_OPER_NUM')[i].value);
						listData.push(document.getElementsByName('TOTAL_OPER_NUM')[i].value);
				}
			}
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>operationstatistics/saveAndEdit.do?tm='+new Date().getTime(),
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
								url: '<%=basePath%>operationstatistics/deleteAll.do?tm='+new Date().getTime(),
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
	    	   diag.URL = '<%=basePath%>operationstatistics/goUploadExcel.do';
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
			window.location.href='<%=basePath%>operationstatistics/excel.do';
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