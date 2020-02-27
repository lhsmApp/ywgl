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
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<!-- /section:settings.box -->
					<div class="page-header">
						<span class="label label-xlg label-success arrowed-right">问题管理</span>
						<!-- arrowed-in-right --> 
						<span class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">问题查询</span> 
                        <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
								
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGridBase'),gridHeight)">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
						</button>
					</div><!-- /.page-header -->
			        
					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box">
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<label class="pull-left" style="padding: 5px;">筛选条件：</label>
											<span class="pull-left nav-search input-icon" style="margin-left:20px;margin-top:2px;">
												<input type="text" placeholder="这里输入问题标题" class="nav-search-input" id="keywords" autocomplete="off" name="keywords" value="${pd.keywords }"/>
												<i class="ace-icon fa fa-search nav-search-icon"></i>
											</span>
											<span class="pull-left" style="margin-right: 5px;margin-left:-10px;"> 
												<select
													class="chosen-select form-control" name="PRO_STATE"
													id="PRO_STATE" data-placeholder="请选择处理状态"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择处理状态</option>
														<c:forEach items="${proStateList}" var="proState">
															<option value="${proState.nameKey}"
																<c:if test="${pd.PRO_STATE==proState.nameKey}">selected</c:if>>${proState.nameValue}</option>
														</c:forEach>
												</select>
											</span>
											<span class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
												<div class="selectTree" id="selectTree" multiMode="true"
												    allSelectable="false" noGroup="false"></div>
											    <input id="PRO_DEPART" type="hidden"></input>
											</span>
											<!-- <span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="PRO_DEPART" id="PRO_DEPART"
													style="vertical-align: top; height:32px;width: 150px;">
												</select>
											</span> -->
											<button type="button" class="btn btn-info btn-xs" onclick="tosearch();">
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
							<div class="tabbable">
								<ul class="nav nav-tabs padding-18">
									<li class="active">
										<a data-toggle="tab" href="#problemInfo"> 
											<i class="fa fa-exchange purple bigger-120"></i> 问题提报
										</a>
									</li>
									<li>
										<a data-toggle="tab" href="#problemAssign"> 
											<i class="fa fa-cutlery green bigger-120"></i> 问题分配
										</a>
									</li>
									<li>
										<a data-toggle="tab" href="#problemAnswer"> 
											<i class="fa fa-credit-card orange bigger-120"></i> 问题受理
										</a>
									</li>
									<li>
										<a data-toggle="tab" href="#problemClose"> 
											<i class="fa fa-adjust brown bigger-120"></i> 问题关闭
										</a>
									</li>
								</ul>
								<div class="tab-content no-border">
									<div id="problemInfo" class="tab-pane active">
							            <table id="jqGridBase"></table>
							            <div id="jqGridBasePager"></div>
									</div>
									
									<div id="problemAssign" class="tab-pane">
									    <table id="jqGridBaseAssign"></table>
								    </div>
								    
								    <div id="problemAnswer" class="tab-pane">
									    <table id="jqGridBaseAnswer"></table>
								    </div>
								    
								    <div id="problemClose" class="tab-pane">
									    <table id="jqGridBaseClose"></table>
								    </div>
							    </div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
</body>

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
	<!-- 输入格式化 -->
	<script src="static/ace/js/jquery.maskedinput.js"></script>
	<!-- JqGrid统一样式统一操作 -->
	<script type="text/javascript" src="static/js/common/jqgrid_style.js"></script>
	<script type="text/javascript"
		src="static/js/common/cusElement_style.js"></script>
	<script type="text/javascript" src="static/js/util/toolkit.js"></script>
	<script src="static/ace/js/ace/ace.widget-box.js"></script>
	
<script type="text/javascript"> 
var gridBase_selector = "#jqGridBase";  
var pagerBase_selector = "#jqGridBasePager";  

var gridHeight;
$(document).ready(function () {
	$(top.hangge());//关闭加载状态
	//resize to fit page size
	$(window).on('resize.jqGrid', function () {
		$(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
		gridHeight=198;
		resizeGridHeight($(gridBase_selector),gridHeight);
    })
   

    var keywords=$("#keywords").val();
    var proState=$("#PRO_STATE").val();
    var proDepart=$("#PRO_DEPART").val();
    
	$(gridBase_selector).jqGrid({
		url: '<%=basePath%>mbp/queryPageList.do?keywords='+keywords
            +'&PRO_STATE='+proState
            +'&PRO_DEPART='+proDepart,
		datatype: "json",
		colModel:  [
		            
					{ label: '问题单号', name: 'PRO_CODE', width: 120,editable: false},
					{ label: '问题标题', name: 'PRO_TITLE', width: 150,editable: false},
					{ label: '上报人', name: 'PRO_REPORT_USER', width: 60,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${userStr}"},formatoptions:{value:"${userStr}"},stype: 'select',searchoptions:{value:"${userStr}"}},
					{ label: '受理人', name: 'PRO_ACCEPT_USER', width: 60,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${userStr}"},formatoptions:{value:"${userStr}"},stype: 'select',searchoptions:{value:"${userStr}"}},
					{ label: '上报单位', name: 'PRO_DEPART_NAME', width: 120,editable: false},
					{ label: '系统类型', name: 'PRO_SYS_TYPE_NAME', width: 80,editable: false},
					{ label: '问题类型', name: 'PRO_TYPE_NAME', width: 80,editable: false},
					{ label: '问题标签', name: 'PRO_TAG', width: 100,editable: false},
					{ label: '优先级', name: 'PRO_PRIORITY', width: 60,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${priorityStr}"},formatoptions:{value:"${priorityStr}"},stype: 'select',searchoptions:{value:"${priorityStr}"}},
					{ label: '问题解决时间', name: 'PRO_RESOLVE_TIME', width: 80,editable: false},
					{ label: '问题状态', name: 'PRO_STATE', width: 60,editable: false, formatter: customFmatterState},
					{ label: '更新时间', name: 'UPDATE_DATE', width: 120,editable: false},
					{ label: '详情', name: 'PRO_CODE1', width: 60,align:'center', editable: false,formatter: customFmatterDetail}                   
				],
		viewrecords: true, 
		reloadAfterSubmit: true, 
		rowNum: 0,
		altRows: true, //斑马条纹
		rownumbers: true, // show row numbers
        rownumWidth: 35, // the width of the row numbers columns
        
		pager: pagerBase_selector,
		/* pgbuttons: true, // 分页按钮是否显示 
		pginput: false, // 是否允许输入分页页数  */

        sortable: true,
        sortname: 'UPDATE_DATE,PRO_CODE',
		sortorder: 'desc',

		//scroll: 1,
		beforeSelectRow: function(rowid,status) {
			if(rowid != null) {
				var rowData = $(gridBase_selector).getRowData(rowid);
				jQuery("#jqGridBaseAssign").jqGrid('setGridParam',{url: '<%=basePath%>mbp/getProAssigns.do?PRO_CODE='+rowData.PRO_CODE});
				jQuery("#jqGridBaseAssign").trigger("reloadGrid");
				
				jQuery("#jqGridBaseAnswer").jqGrid('setGridParam',{url: '<%=basePath%>mbp/getProAnswers.do?PRO_CODE='+rowData.PRO_CODE});
				jQuery("#jqGridBaseAnswer").trigger("reloadGrid");
				
				jQuery("#jqGridBaseClose").jqGrid('setGridParam',{url: '<%=basePath%>mbp/getProCloses.do?PRO_CODE='+rowData.PRO_CODE});
				jQuery("#jqGridBaseClose").trigger("reloadGrid");
			}
			$(gridBase_selector).jqGrid('setSelection',rowid,true);
		}, 
		/* onSelectRow:function(rowid,status){
			console.log('sdfsdf');
		}, */
		// use the onSelectRow that is triggered on row click to show a details grid
		//onSortCol : clearSelection,
		onPaging : clearSelection,
		
		//cellEdit: true,
		loadComplete : function(data) {
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
	
	$(gridBase_selector).navGrid(pagerBase_selector, 
			{
	            //navbar options
		        edit: false,
	            editicon : 'ace-icon fa fa-pencil blue',
	            add: false,
	            addicon : 'ace-icon fa fa-plus-circle purple',
	            del: false,
	            delicon : 'ace-icon fa fa-trash-o red',
	            search: false,
	            searchicon : 'ace-icon fa fa-search orange',
	            refresh: true,
	            refreshicon : 'ace-icon fa fa-refresh green',
	            view: false,
	            viewicon : 'ace-icon fa fa-search-plus grey',
        }, { }, { }, { },
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
        },
        {},{});
}); 

//问题分配 grid
$("#jqGridBaseAssign").jqGrid({
	url: '',
    datatype: "json",
	colModel:  [
		{ label: '问题单号', name: 'PRO_CODE', width: 120,editable: false},
		/* { label: '问题标题', name: 'PRO_TITLE', width: 180,editable: false}, */
		
		
		{ label: '受理人', name: 'PRO_ACCEPT_USER', width: 90,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${userStr}"},formatoptions:{value:"${userStr}"},stype: 'select',searchoptions:{value:"${userStr}"}},
		{ label: '系统类型', name: 'PRO_SYS_TYPE_NAME', width: 90,editable: false},
		{ label: '问题类型', name: 'PRO_TYPE_NAME', width: 100,editable: false},
		
	
		{ label: '优先级', name: 'PRO_PRIORITY', width: 80,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${priorityStr}"},formatoptions:{value:"${priorityStr}"},stype: 'select',searchoptions:{value:"${priorityStr}"}},
		
		{ label: '分配人', name: 'BILL_USER', width: 90,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${userStr}"},formatoptions:{value:"${userStr}"},stype: 'select',searchoptions:{value:"${userStr}"}},
		{ label: '分配时间', name: 'BILL_DATE', width: 150,editable: false},
	],
	altRows: true, //斑马条纹
	autowidth:true,
});

//问题回复 grid
$("#jqGridBaseAnswer").jqGrid({
	url: '',
    datatype: "json",
	colModel:  [
		{ label: '问题单号', name: 'PRO_CODE', width: 120,editable: false},
		/* { label: '问题标题', name: 'PRO_TITLE', width: 180,editable: false}, */
		
		{ label: '回复人', name: 'BILL_USER', width: 90,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${userStr}"},formatoptions:{value:"${userStr}"},stype: 'select',searchoptions:{value:"${userStr}"}},
		{ label: '回复时间', name: 'BILL_DATE', width: 150,editable: false},
		{ label: '回复内容', name: 'ANSWER_CONTENT', width: 250,editable: false},
		{ label: '回复详情', name: 'ANSWER_ID', width: 100,align:'center', editable: false,formatter: customFmatterDetailAnswer}                   
	],
	altRows: true, //斑马条纹
});

//问题关闭 grid
$("#jqGridBaseClose").jqGrid({
	url: '',
    datatype: "json",
	colModel:  [
		{ label: '问题单号', name: 'PRO_CODE', width: 120,editable: false},
		/* { label: '问题标题', name: 'PRO_TITLE', width: 180,editable: false}, */
		
		{ label: '关闭人', name: 'BILL_USER', width: 90,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${userStr}"},formatoptions:{value:"${userStr}"},stype: 'select',searchoptions:{value:"${userStr}"}},
		{ label: '关闭时间', name: 'BILL_DATE', width: 150,editable: false},
		{ label: '关闭内容', name: 'CLOSE_CONTENT', width: 250,editable: false},
		{ label: '关闭详情', name: 'PRO_CODE1', width: 100,align:'center', editable: false,formatter: customFmatterDetailClose}                   
	],
	altRows: true, //斑马条纹
});

function clearSelection() {
	
	jQuery("#jqGridBaseAssign").jqGrid('clearGridData',true);
	jQuery("#jqGridBaseAnswer").jqGrid('clearGridData',true);
	jQuery("#jqGridBaseClose").jqGrid('clearGridData',true);
}

function customFmatterDetail(cellvalue, options, rowObject) {
	var detail='<div class="btn-group">'
					+'<button class="btn btn-xs btn-yellow" onClick=viewProblemDetail("'+cellvalue+'")>详情</button>'
				+'</div>';
	return detail;
};

function customFmatterDetailAnswer(cellvalue, options, rowObject) {
	var detail='<div class="btn-group">'
					+'<button class="btn btn-xs btn-yellow" onClick=viewProblemDetailAnswer("'+cellvalue+'")>详情</button>'
				+'</div>';
	return detail;
};

function customFmatterDetailClose(cellvalue, options, rowObject) {
	var detail='<div class="btn-group">'
					+'<button class="btn btn-xs btn-yellow" onClick=viewProblemDetailClose("'+cellvalue+'")>详情</button>'
				+'</div>';
	return detail;
};

function customFmatterState(cellvalue, options, rowObject) {
	if (cellvalue == 2) {
		return '<span class="label label-xs label-info">已提交</span>';
	} else if(cellvalue == 3){
		return '<span class="label label-xs label-warning">受理中</span>';
	}else if(cellvalue == 4){
		return '<span class="label label-xs label-inverse">已关闭</span>';
	}else{
		return '<span class="label label-xs label-success">新发起</span>';
	}
};

//检索
function tosearch() {
	var keywords=$("#keywords").val();
    var proState=$("#PRO_STATE").val();
    var proDepart=$("#PRO_DEPART").val();

	$(gridBase_selector).jqGrid('setGridParam',{  // 重新加载数据
		url: '<%=basePath%>mbp/queryPageList.do?keywords='+keywords
        	+'&PRO_STATE='+proState
        	+'&PRO_DEPART='+proDepart,
		datatype : 'json'
	}).trigger("reloadGrid");
}

/**
 * 查看知识详情
 */
function viewProblemDetail(problemCode){
	console.log(problemCode);
	top.jzts();
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title ="问题详情";
	diag.URL = '<%=basePath%>mbp/goView.do?PRO_CODE='+problemCode;
	diag.Width = 600;
	diag.Height = 460;
	diag.CancelEvent = function(){ //关闭事件
		diag.close();
	};
	diag.show();
}

/**
* 查看回复详情
*/
function viewProblemDetailAnswer(answerID){
	top.jzts();
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title ="回复详情";
	diag.URL = '<%=basePath%>mbp/goViewAnswer.do?ANSWER_ID='+answerID;
	diag.Width = 600;
	diag.Height = 460;
	diag.CancelEvent = function(){ //关闭事件
		diag.close();
	};
	diag.show();
}

/**
 * 查看关闭详情
 */
function viewProblemDetailClose(proCode){
	top.jzts();
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title ="关闭详情";
	diag.URL = '<%=basePath%>mbp/goViewClose.do?PRO_CODE='+proCode;
	diag.Width = 600;
	diag.Height = 460;
	diag.CancelEvent = function(){ //关闭事件
		diag.close();
	};
	diag.show();
}

//加载单位树
function initComplete(){
	//下拉树
	var defaultNodes = {"treeNodes":${zTreeNodes}};
	//绑定change事件
	$("#selectTree").bind("change",function(){
		$("#PRO_DEPART").val("");
		if($(this).attr("relValue")){
			$("#PRO_DEPART").val($(this).attr("relValue"));
	    } 
	});
	//赋给data属性
	$("#selectTree").data("data",defaultNodes);  
	$("#selectTree").render();
	$("#selectTree2_input").val("请选择单位");
}
</script>
</html>