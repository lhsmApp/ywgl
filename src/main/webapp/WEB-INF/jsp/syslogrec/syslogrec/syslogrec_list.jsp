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
	<!-- jsp文件头和头部 ，其中包含旧版本（Ace）Jqgrid Css-->
	<%@ include file="../../system/index/topWithJqgrid.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	
	<!-- 最新版的Jqgrid Css，如果旧版本（Ace）某些方法不好用，尝试用此版本Css，替换旧版本Css -->
	<!-- <link rel="stylesheet" type="text/css" media="screen" href="static/ace/css/ui.jqgrid-bootstrap.css" /> -->
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
    <!-- 标准页面统一样式 -->
    <link rel="stylesheet" href="static/css/normal.css" />
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <style>
		.page-header{
			padding-top: 9px;
			padding-bottom: 9px;
			margin: 0 0 8px;
		}
	</style>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<!-- /section:settings.box -->
					<div class="page-header">
						<span class="label label-xlg label-success arrowed-right">系统设置</span>
						<!-- arrowed-in-right -->
						<span class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">日志查询</span> <span
							style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGrid'),null,true)">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
						</button>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box">
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<span class="pull-left" style="margin-right: 5px;">
											    <span class="input-icon" style="margin-right: 5px;">
												    <input class="input-mask-date" type="text" placeholder="请填写日期"
												        name="SelectedBusiDate" id="SelectedBusiDate" > 
												    <i class="ace-icon fa fa-calendar blue"></i>
											    </span>
						                        <!-- <input name="SelectedBusiDate" id="SelectedBusiDate"
						                            type="text" class="form-control Wdate"
						                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/> -->
											</span>
											<span class="pull-left" style="margin-right: 5px;"> 
												<select class="chosen-select form-control" 
												    name="SelectedTypeCode" id="SelectedTypeCode" data-placeholder="请选择业务类型"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择业务类型</option>
														<c:forEach items="${PZTYPE}" var="each">
															<option value="${each.DICT_CODE}"
																<c:if test="${pd.SelectedTypeCode==each.DICT_CODE}">selected</c:if>>${each.NAME}</option>
														</c:forEach>
												</select>
											</span>
											<span class="pull-left" style="margin-right: 5px;">
												<div class="selectTree" id="selectTree" multiMode="true"
												    allSelectable="false" noGroup="false"></div>
											    <input id="SelectedDepartCode" type="hidden"></input>
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
	<!-- 上传控件 -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>

	<script type="text/javascript"> 
    var grid_selector = "#jqGrid";  
    var pager_selector = "#jqGridPager";  
    
	$(document).ready(function () { 
		$(top.hangge());//关闭加载状态

		// $('.input-mask-date').mask('9999-99-99');
        var CurryDateTime = '${CurryDateTime}';
        //CurryDateTime = Date.parse(new Date(CurryDateTime.replace(/-/g, "/")));
		$("#SelectedBusiDate").val(CurryDateTime);
		 
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$(grid_selector).jqGrid( 'setGridWidth', $(".page-content").width());
			resizeGridHeight($(grid_selector),null,true);
	    })
		
		$(grid_selector).jqGrid({
			 url: '<%=basePath%>syslogrec/getPageList.do?SelectedDepartCode='+$("#SelectedDepartCode").val()
                    + '&SelectedTypeCode=' + $("#SelectedTypeCode").val()
                    + '&SelectedBusiDate=' + $("#SelectedBusiDate").val(),
			 datatype: "json",
			 colModel: [
				{ label: '日志时间', name: 'REC_DATE', width: 60,editable: false,},
				{ label: '业务类型', name: 'TYPE_CODE', width: 90,editable: false, edittype: 'select',formatter:'select',formatoptions:{value:"${typeCodeStrSelect}"},editoptions:{value:"${typeCodeStrSelect}"},stype: 'select',searchoptions:{value:"${typeCodeStrAll}"}},
				{ label: '登录单位', name: 'DEPT_CODE', width: 90,editable: false, edittype: 'select',formatter:'select',formatoptions:{value:"${departmentStrSelect}"},editoptions:{value:"${departmentStrSelect}"},stype: 'select',searchoptions:{value:"${departmentStrAll}"}},
				{ label: '用户', name: 'USER_CODE', width: 60, editable: false,edittype: 'select',formatter:'select',formatoptions:{value:"${userCodeStrSelect}"},editoptions:{value:"${userCodeStrSelect}"},stype: 'select',searchoptions:{value:"${userCodeStrAll}"}},
				{ label: '单号', name: 'BILL_CODE', width: 90,editable: false},
				{ label: 'SQL信息', name: 'SQL_STR', width: 200,editable: false,}
			],
			reloadAfterSubmit: true, 
			viewrecords: true,
			rowNum: 0,
			//rowList: [100,200,500],
            multiSort: true,
			sortname: 'TYPE_CODE,DEPT_CODE',
			altRows: true,
			//rownumbers: true, 
            //rownumWidth: 35,		
            
			pager: pager_selector,
			pgbuttons: false, // 分页按钮是否显示 
			pginput: false, // 是否允许输入分页页数 
			
			loadComplete : function() {
				var table = this;
				setTimeout(function(){
					styleCheckbox(table);
					updateActionIcons(table);
					updatePagerIcons(table);
					enableTooltips(table);
				}, 0);
			},
		});
		
		$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
		//navButtons
		jQuery(grid_selector).jqGrid('navGrid',pager_selector,
			{ 	//navbar options
				edit: false,
				editicon : 'ace-icon fa fa-pencil blue',
				add: false,
				addicon : 'ace-icon fa fa-plus-circle purple',
				del: false,
				delicon : 'ace-icon fa fa-trash-o red', 
				search: true,
				searchicon : 'ace-icon fa fa-search orange',
				refresh: true,
				refreshicon : 'ace-icon fa fa-refresh green',
				view: false,
				viewicon : 'ace-icon fa fa-search-plus grey',
			}, { }, { }, { }, {
				//search form
				recreateForm: true,
				afterShowSearch: beforeSearchCallback,
				afterRedraw: function(){
					style_search_filters($(this));
				},
				multipleSearch: true,
				//multipleGroup:true,
				showQuery: false
	        },
	        {},{}
		);
		
	    $(grid_selector).navSeparatorAdd(pager_selector, {
			sepclass : "ui-separator",
			sepcontent: ""
		});
        $(grid_selector).navButtonAdd(pager_selector, {
			 id : "exportItems",
            caption : "导出",
            buttonicon : "ace-icon fa fa-cloud-download",
            onClickButton : exportItems,
            position : "last",
            title : "导出",
            cursor : "pointer"
        });
 	});
	
	//加载单位树
    function initComplete(){
	    //下拉树
	    var defaultNodes = {"treeNodes":${zTreeNodes}};
		//绑定change事件
		$("#selectTree").bind("change",function(){
			$("#SelectedDepartCode").val("");
			if($(this).attr("relValue")){
				$("#SelectedDepartCode").val($(this).attr("relValue"));
		    }
			getSelectBillCodeOptions();
		});
		//赋给data属性
		$("#selectTree").data("data",defaultNodes);  
		$("#selectTree").render();
		$("#selectTree2_input").val("请选择单位");
    }
	
		//检索
		function tosearch() {
			$(grid_selector).jqGrid('setGridParam',{  // 重新加载数据
				url:'<%=basePath%>syslogrec/getPageList.do?SelectedDepartCode='+$("#SelectedDepartCode").val()
                + '&SelectedTypeCode=' + $("#SelectedTypeCode").val()
                + '&SelectedBusiDate=' + $("#SelectedBusiDate").val(),
								datatype : 'json'
							}).trigger("reloadGrid");
		}

        /**
         * 导出
         */
        function exportItems(){
        	window.location.href='<%=basePath%>syslogrec/excel.do?SelectedDepartCode='+$("#SelectedDepartCode").val()
            + '&SelectedTypeCode=' + $("#SelectedTypeCode").val()
            + '&SelectedBusiDate=' + $("#SelectedBusiDate").val();
        }
	</script>
</body>
</html>