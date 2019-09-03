<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 ，其中包含旧版本（Ace）Jqgrid Css-->
<%@ include file="../../system/index/topWithJqgrid.jsp"%>
<!-- 最新版的Jqgrid Css，如果旧版本（Ace）某些方法不好用，尝试用此版本Css，替换旧版本Css -->
<!-- <link rel="stylesheet" type="text/css" media="screen" href="static/ace/css/ui.jqgrid-bootstrap.css" /> -->
<!-- 标准页面统一样式 -->
<link rel="stylesheet" href="static/css/normal.css" />

</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<!-- /section:settings.box -->
					<div class="page-header">
						<span class="label label-xlg label-success arrowed-right">综合管理</span>
						<!-- arrowed-in-right -->
						<span
							class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">系统配置</span> <span
							style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<table id="jqGrid"></table>
							<div id="jqGridPager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>


	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>

	<!-- 最新版的Jqgrid Js，如果旧版本（Ace）某些方法不好用，尝试用此版本Js，替换旧版本JS -->
	<!-- <script src="static/ace/js/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="static/ace/js/grid.locale-cn.js" type="text/javascript"></script> -->

	<!-- 旧版本（Ace）Jqgrid Js -->
	<script src="static/ace/js/jqGrid/jquery.jqGrid.src.js"></script>
	<script src="static/ace/js/jqGrid/i18n/grid.locale-cn.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- JqGrid统一样式统一操作 -->
	<script type="text/javascript" src="static/js/common/jqgrid_style.js"></script>

	<script type="text/javascript"> 
	//var gridHeight=155;
	$(document).ready(function () { 
		$(top.hangge());//关闭加载状态
		 
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$("#jqGrid").jqGrid( 'setGridWidth', $(".page-content").width());
			resizeGridHeight($("#jqGrid"));
	    })
		
		$("#jqGrid").jqGrid({
			url: '<%=basePath%>sysconfig/getPageList.do',
			datatype: "json",
			 colModel: [
				{label: ' ',name:'myac',index:'', width:70, fixed:true, sortable:false, resize:false,
					formatter:'actions', 
					formatoptions:{ 
                        onSuccess: function(response) {
							if(response.responseJSON.code==0){
								return [true];
							}else{
								return [false, response.responseJSON.message];
							}                
                        },
                        onError :function(rowid, res, stat, err) {
                        	if(err!=null)
                        		console.log(err);
                        },
                        afterSave:function(rowid, res){
                        	$(".tooltip").remove();
                        	/* $("#jqGrid").trigger("reloadGrid"); */
                        },
						keys:true,
					    //delbutton: false,//disable delete button
					    
					    delOptions:{
							recreateForm: true, 
							beforeShowForm:beforeDeleteCallback,
							afterSubmit: function(response, postData) {
								if(response.responseJSON.code==0){
									return [true];
								}else{
									return [false, response.responseJSON.message];
								}
                            },
						}
					}
				},
				
				{ label: '配置键',name:'KEY_CODE__',key:true, width:100,hidden : true,editable: true},
				
				{ label: '配置键',name:'KEY_CODE', editable: true,editoptions:{maxlength:"20"},width:200},
				{ label: '配置名称', name: 'KEY_VALUE', editable: true,editoptions:{maxlength:"500"},width: 500},
				{ label: '说明', name: 'REMARK',editable: true, width: 400},                  
			],
			reloadAfterSubmit: true, 
			viewrecords: false, // show the current page, data rang and total records on the toolbar
			rowNum: -1,
			pgbuttons: false,//上下按钮 
			pginput:false,//输入框
			shrinkToFit:false,
			
			sortname: 'KEY_CODE',
			pager: "#jqGridPager",
			loadComplete : function() {
				var table = this;
				setTimeout(function(){
					styleCheckbox(table);
					updateActionIcons(table);
					updatePagerIcons(table);
					enableTooltips(table);
				}, 0);
			},
			
			altRows: true,
			rownumbers: true, // show row numbers
            rownumWidth: 35, // the width of the row numbers columns			
			/* multiselect: true,
	        multiboxonly: true, */
	        editurl: "<%=basePath%>sysconfig/edit.do",//nothing is save
		});
		
		$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
		//navButtons
		jQuery("#jqGrid").jqGrid('navGrid',"#jqGridPager",
			{ 	//navbar options
				edit: false,
				editicon : 'ace-icon fa fa-pencil blue',
				add: true,
				addicon : 'ace-icon fa fa-plus-circle purple',
				del: false,
				delicon : 'ace-icon fa fa-trash-o red', 
				search: false,
				searchicon : 'ace-icon fa fa-search orange',
				refresh: true,
				refreshicon : 'ace-icon fa fa-refresh green',
				view: false,
				viewicon : 'ace-icon fa fa-search-plus grey',
			},
			{
				//edit record form
				//closeAfterEdit: true,
				//width: 700,
				recreateForm: true,
				beforeShowForm :beforeEditOrAddCallback
			},
			 {
				//new record form
				//width: 700,
				closeAfterAdd: true,
				recreateForm: true,
				viewPagerButtons: false,
				//reloadAfterSubmit: true,
				beforeShowForm : beforeEditOrAddCallback,
			    onclickSubmit: function(params, posdata) {
					console.log("onclickSubmit");
                    //console.log(posdata	);
                } , 
                afterSubmit: fn_addSubmit
			},
			{
				//删除
			},
			{
				//search form
			}
		);
 	});
	</script>
</body>
</html>