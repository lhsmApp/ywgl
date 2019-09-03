<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<!-- 标准页面统一样式 -->
<link rel="stylesheet" href="static/css/normal.css" />
<!-- 最新版的Jqgrid Css，如果旧版本（Ace）某些方法不好用，尝试用此版本Css，替换旧版本Css -->
<!-- <link rel="stylesheet" type="text/css" media="screen" href="static/ace/css/ui.jqgrid-bootstrap.css" /> -->
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<!-- /section:settings.box -->
					<div class="page-header">
						<span class="green middle bolder">凭证数据类型: &nbsp;</span>
						<%-- <span class="label label-xlg label-info"><c:choose><c:when test='${pd.TABLE_CODE=="1"}'>工资</c:when><c:when test='${pd.TABLE_CODE=="2"}'>社保</c:when><c:otherwise>公积金</c:otherwise></c:choose></span> --%>
						<span class="label label-xlg label-info">${pd.TABLE_NAME}</span>
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
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- JqGrid统一样式统一操作 -->
	<script type="text/javascript" src="static/js/common/jqgrid_style.js"></script>
	<script type="text/javascript" src="static/js/common/cusElement_style.js"></script>
	<script type="text/javascript"> 
	$(document).ready(function () {
		$(top.hangge());//关闭加载状态
		console.log('${pd.TABLE_CODE}');
		var deptCode="${pd.DEPT_CODE}";
		var fmi="${pd.FMISACC}";
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$("#jqGrid").jqGrid( 'setGridWidth', $(".page-content").width());
			//console.log("ccc"+$("iframe").height());
			$("#jqGrid").jqGrid( 'setGridHeight', $(window).height() - 160);
	    })
		
		$("#jqGrid").jqGrid({
			<%-- url: '<%=basePath%>static/data/data.json', --%>
			url: "<%=basePath%>voucher/getTransferValidate.do?TABLE_CODE="+${pd.TABLE_CODE}+"&DEPT_CODE="+deptCode+"&FMISACC="+fmi,
			datatype: "json",
			 colModel: [
				{ label: '单据编号', name: 'BILL_CODE', width: 75,editable: false},
				{ label: '业务区间', name: 'BUSI_DATE', width: 90,editable: false},
				{ label: '责任中心', name: 'DEPT_CODE', width: 100,editable: false,edittype: 'select',editoptions:{value:"${pd.strDict}"},formatter:'select',formatoptions:{value:"${pd.strDict}"},stype: 'select',searchoptions:{value:"${pd.strDict}"},}, 
				{ label: '帐套', name: 'CUST_COL7', width: 100,editable: false,edittype: 'select',editoptions:{value:"${pd.strBillOff}"},formatter:'select',formatoptions:{value:"${pd.strBillOff}"},stype: 'select',searchoptions:{value:"${pd.strBillOff}"},},
				/* { label: '封存状态', name: 'STATE', width: 90,editable: false,align:'center',formatter: customFmatterState} */
			],
			//reloadAfterSubmit: true, 
			//caption: "jqGrid with inline editing",
			//autowidth:true,
			//shrinkToFit:true,
			viewrecords: false, // show the current page, data rang and total records on the toolbar
			rowNum: 10,
			pgbuttons: false,//上下按钮 
			pginput:false,//输入框

			//rowList:[10,20,30,100000],
			
			//loadonce: true, // this is just for the demo
			//height: '100%', 
			
			//sortname: 'PRODUCTNAME',
			
			footerrow: false,
			userDataOnFooter: false, // the calculated sums and/or strings from server are put at footer row.
			grouping: true,
			groupingView: {
                groupField: ["DEPT_CODE"],
                groupColumnShow: [true],
                groupText: ["<b>{0}</b>"],
                groupOrder: ["desc"],
                groupDataSorted : false,
                groupSummary: [false],
                groupCollapse: false,
                plusicon : 'fa fa-chevron-down bigger-110',
				minusicon : 'fa fa-chevron-up bigger-110'
            },
			
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
			//toppager: true,
			rownumbers: true, // show row numbers
            rownumWidth: 35, // the width of the row numbers columns
			
			multiselect: false,
			//multikey: "ctrlKey",
	        multiboxonly: true,
		});
		
		$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
		
		//navButtons
		jQuery("#jqGrid").jqGrid('navGrid',"#jqGridPager",
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
			},
			{
				//delete record form
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
   	
	//显示隐藏查询
	function showQueryCondi(){
		if($(".widget-box").css("display")=="block"){
			$("#btnQuery").find("i").removeClass('fa-chevron-up').addClass('fa-chevron-down');
			$("#btnQuery").find("span").text("显示查询");
			$(window).triggerHandler('resize.jqGrid');
		}
		else{
			$("#btnQuery").find("i").removeClass('fa-chevron-down').addClass('fa-chevron-up');
			$("#btnQuery").find("span").text("隐藏查询");
		}
		$(".widget-box").toggle("fast");
		
	}
	
	//格式化封存状态
	function customFmatterState(cellvalue, options, rowObject) {
		if (cellvalue == '0') {
			return '<span class="label label-warning arrowed-in">解封</span>';
		} else {
			return '<span class="label label-success arrowed">未上报</span>';
		}
	};
 	</script>
</body>
</html>