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
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />

<!-- 最新版的Jqgrid Css，如果旧版本（Ace）某些方法不好用，尝试用此版本Css，替换旧版本Css -->
<!-- <link rel="stylesheet" type="text/css" media="screen" href="static/ace/css/ui.jqgrid-bootstrap.css" /> -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!-- <script type="text/javascript" src="static/ace/js/jquery.js"></script> -->
<!-- 树形下拉框start -->
<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
<link rel="stylesheet" type="text/css"
	href="plugins/selectZtree/import_fh.css" />
<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet"
	href="plugins/selectZtree/ztree/ztree.css"></link>
<!-- 树形下拉框end -->
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
						<span class="label label-xlg label-success arrowed-right">东部管道</span>
						<!-- arrowed-in-right -->
						<span
							class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">内部员工调动管理 </span> <span
							style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGrid'),null,true)">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>显示查询</span>
						</button>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box" hidden>
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<span style="margin-right: 5px;">
												<div class="selectTree" id="selectTree" multiMode="true"
													allSelectable="false" noGroup="true"></div>
												<input type="text" id="SelectedDepartCode" hidden></input>
											 </span> 
										
											<button type="button" class="btn btn-info btn-sm"
												onclick="tosearch();">
												<i class="ace-icon fa fa-search bigger-110"></i>
											</button>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>

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
	<!-- JqGrid统一样式统一操作 -->
	<script type="text/javascript" src="static/js/common/jqgrid_style.js"></script>

	<script type="text/javascript"> 
	$(document).ready(function () { 
		$(top.hangge());//关闭加载状态
		 
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$("#jqGrid").jqGrid( 'setGridWidth', $(".page-content").width());
			resizeGridHeight($("#jqGrid"),null,true);
	    })
		
		$("#jqGrid").jqGrid({
			url: '<%=basePath%>stafftransferreg/getPageList.do?'
                    + '&SelectedDepartCode=' + $("#SelectedDepartCode").val(),
			datatype: "json",
			 colModel: [
				{label: ' ',name:'myac',index:'', width:70, fixed:true, sortable:false, resize:false,
					formatter:'actions', 
					formatoptions:{ 
                        onSuccess: function(response) {
                        	var code=JSON.parse(response.responseText);
							if(code.code==0){
								return [true];
							}else{
								$("#subTitle").tips({
									side : 3,
									msg : '保存失败,' + code.message,
									bg : '#cc0033',
									time : 3
								});
								return [false, code.message];
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
						delOptions:{
							recreateForm: true, 
							beforeShowForm:beforeDeleteCallback,
							afterSubmit: function(response, postData) {
								var code=JSON.parse(response.responseText);
								if(code.code==0){
									return [true];
								}else{
									return [false, code.message];
								}
                            },
						}
					}
				},

				{ label: '身份证号', name: 'STAFF_IDENT__',key:true, width: 60,hidden : true,editable: true,},

				{ label: '员工编号',name:'USER_CODE', editable: true,editoptions:{maxlength:"30"},width:200},
				{ label: '员工姓名',name:'USER_NAME', editable: true,editoptions:{maxlength:"20"},width:200},
				{ label: '身份证号',name:'STAFF_IDENT', editable: true,editoptions:{maxlength:"18"},width:200},
				
				{ label: '责任中心', name: 'DEPT_CODE', width: 200,editable: true,edittype: 'select',formatter:'select',formatoptions:{value:"${departmentStrSelect}"},editoptions:{value:"${departmentStrSelect}"},stype: 'select',searchoptions:{value:"${departmentStrAll}"}},
				{ label: '所属二级单位', name: 'UNITS_CODE', width: 200,editable: true,edittype: 'select',formatter:'select',formatoptions:{value:"${departmentStrSelect}"},editoptions:{value:"${departmentStrSelect}"},stype: 'select',searchoptions:{value:"${departmentStrAll}"}}
			],
			reloadAfterSubmit: true, 
			viewrecords: false,
			rowNum: -1,
			pgbuttons: false,//上下按钮 
			pginput:false,//输入框
            multiSort: false,
			sortname: 'USER_CODE',
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
			rownumbers: true, 
            rownumWidth: 35,		
			/* multiselect: true,
	        multiboxonly: true, */
	        editurl: "<%=basePath%>stafftransferreg/edit.do",//nothing is save
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
				recreateForm: true,
				beforeShowForm :beforeEditOrAddCallback
			},
			 {
				//new record form
			    id: "add",
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
                afterSubmit: fn_addSubmit_extend
			},
			{
				//删除
			},
			{
				//search form
				recreateForm: true,
				afterShowSearch: beforeSearchCallback,
				afterRedraw: function(){
					style_search_filters($(this));
				},
				multipleSearch: true,
				
				//multipleGroup:true,
				showQuery: false
				
			}
		);
 	});

    /**
     * 增加成功
     * 
     * @param response
     * @param postdata
     * @returns
     */
    function fn_addSubmit_extend(response, postdata) {
        var responseJSON = JSON.parse(response.responseText);
    	if (responseJSON.code == 0) {
    		// console.log("Add Success");
    		$("#subTitle").tips({
    			side : 3,
    			msg : '保存成功',
    			bg : '#009933',
    			time : 3
    		});
    		return [ true ];
    	} else {
    		// console.log("Add Failed"+response.responseJSON.message);
    		$("#subTitle").tips({
    			side : 3,
    			msg : '保存失败,' + responseJSON.message,
    			bg : '#cc0033',
    			time : 3
    		});
    		return [ false, responseJSON.message ];
    	}
    }
	
		//检索
		function tosearch() {
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
				url:'<%=basePath%>stafftransferreg/getPageList.do?'
                                        + 'SelectedDepartCode=' + $("#SelectedDepartCode").val()
							}).trigger("reloadGrid");
		}

		function customFmatterState(cellvalue, options, rowObject) {
			if (cellvalue == 1) {
				return '<span class="label label-important arrowed-in">启用</span>';
			} else {
				return '<span class="label label-success arrowed">停用</span>';
			}
		};
		
		//加载单位树
		function initComplete(){
			//下拉树
			var defaultNodes = {"treeNodes":${zTreeNodes}};
			//绑定change事件
			$("#selectTree").bind("change",function(){
				if($(this).attr("relValue")){
					$("#SelectedDepartCode").val($(this).attr("relValue"));
			    }else{
			    	$("#SelectedDepartCode").val("");
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