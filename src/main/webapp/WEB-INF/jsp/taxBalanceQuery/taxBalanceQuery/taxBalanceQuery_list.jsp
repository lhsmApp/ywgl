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
								    <span class="label label-xlg label-success arrowed-right">人工成本</span>
									<!-- arrowed-in-right --> 
									<span class="label label-xlg label-yellow arrowed-in arrowed-right"
									    id="subTitle" style="margin-left: 2px;"> 个税差额查询</span> 
                                    <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
								
									<button id="btnQuery" class="btn btn-white btn-info btn-sm"
										onclick="showQueryCondi($('#jqGridBase'),null,true)">
										<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
									</button>
								
						            <div class="pull-right">
									    <span class="label label-xlg label-blue arrowed-left"
									        id = "showDur" style="background:#428bca; margin-right: 2px;"></span> 
								    </div>
					    </div><!-- /.page-header -->
			
						<div class="row">
						    <div class="col-xs-12">
							    <div class="widget-box" >
								    <div class="widget-body">
									    <div class="widget-main">
										    <form class="form-inline">
												<span class="input-icon pull-left" style="margin-right: 5px;">
													<input id="SelectedBusiDate" class="input-mask-date" type="text"
													    placeholder="请输入业务区间"> 
													<i class="ace-icon fa fa-calendar blue"></i>
												</span>
											    <span class="pull-left" style="margin-right: 5px;">
													<select class="chosen-select form-control"
														name="SelectedCustCol7" id="SelectedCustCol7"
														style="vertical-align: top; height:32px;width: 150px;">
														<option value="">请选择帐套</option>
														<c:forEach items="${FMISACC}" var="each">
															<option value="${each.DICT_CODE}">${each.NAME}</option>
														</c:forEach>
													</select>
												</span>
											    <span class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
											    	<div class="selectTree" id="selectTree" multiMode="false"
												        allSelectable="false" noGroup="false"></div>
											        <input id="SelectedDepartCode" type="hidden"></input>
											    </span>
											    <button type="button" class="btn btn-info btn-sm" onclick="tosearch();">
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
						        <table id="jqGridBase"></table>
						        <div id="jqGridBasePager"></div>
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

		//部门是否是最末层节点，是否显示
		var DepartTreeSource;

	    $(document).ready(function () {
			$(top.hangge());//关闭加载状态
			$('.input-mask-date').mask('999999');
		    
			//当前期间,取自tb_system_config的SystemDateTime字段
	        var SystemDateTime = '${SystemDateTime}';
			$("#SelectedBusiDate").val(SystemDateTime);
			//当前登录人所在二级单位
		    var DepartName = '${DepartName}';
		    $("#showDur").text('当前期间：' + SystemDateTime + ' 登录人责任中心：' + DepartName);
			//部门是否是最末层节点，是否显示
			DepartTreeSource = '${pd.departTreeSource}';

    		//resize to fit page size
    		$(window).on('resize.jqGrid', function () {
    			$(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
    			//$(gridBase_selector).jqGrid( 'setGridHeight', $(window).height() - 240);
    			resizeGridHeight($(gridBase_selector),null,true);
    	    });
    		
    		$(gridBase_selector).jqGrid({
    			url: '<%=basePath%>taxBalanceQuery/getPageList.do?'
                +'SelectedCustCol7='+$("#SelectedCustCol7").val()
	            + '&SelectedBusiDate='+$("#SelectedBusiDate").val()
                +'&SelectedDepartCode='+$("#SelectedDepartCode").val(),
                //+'&DepartTreeSource='+DepartTreeSource
    			datatype: "json",
		        colModel: [
						    //{ formoptions:{ rowpos:1, colpos:1}, label: '员工编号', name: 'USER_CODE', width: 120, editable: true, edittype:'text', editoptions:{maxLength:'30'}, editrules:{required:true}},
						    { label: '员工姓名', name: 'USER_NAME', width: 120, editable: false},
						    { label: '身份证号', name: 'STAFF_IDENT', width: 160, editable: false},
						    { label: '开户行', name: 'BANK_NAME', width: 120, editable: false},
						    { label: '银行帐号', name: 'BANK_CARD', width: 160, editable: false},
						    /* { label: '责任中心', name: 'DEPT_CODE', width: 140,editable: false,edittype: 'select',formatter:'select',formatoptions:{value:"${departmentStrSelect}"},editoptions:{value:"${departmentStrSelect}"},stype: 'select',searchoptions:{value:"${departmentStrAll}"}}, */
							
						    { label: '1月工资金额', name: 'ACT_SALY1', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '1月工资税额', name: 'ACCRD_TAX1', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '1月劳动报酬金额', name: 'LABOR_ACT_SALY1', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '1月劳动报酬税额', name: 'LABOR_ACCRD_TAX1', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '2月工资金额', name: 'ACT_SALY2', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '2月工资税额', name: 'ACCRD_TAX2', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '2月劳动报酬金额', name: 'LABOR_ACT_SALY2', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '2月劳动报酬税额', name: 'LABOR_ACCRD_TAX2', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '3月工资金额', name: 'ACT_SALY3', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '3月工资税额', name: 'ACCRD_TAX3', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '3月劳动报酬金额', name: 'LABOR_ACT_SALY3', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '3月劳动报酬税额', name: 'LABOR_ACCRD_TAX3', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '4月工资金额', name: 'ACT_SALY4', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '4月工资税额', name: 'ACCRD_TAX4', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '4月劳动报酬金额', name: 'LABOR_ACT_SALY4', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '4月劳动报酬税额', name: 'LABOR_ACCRD_TAX4', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '5月工资金额', name: 'ACT_SALY5', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '5月工资税额', name: 'ACCRD_TAX5', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '5月劳动报酬金额', name: 'LABOR_ACT_SALY5', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '5月劳动报酬税额', name: 'LABOR_ACCRD_TAX5', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '6月工资金额', name: 'ACT_SALY6', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '6月工资税额', name: 'ACCRD_TAX6', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '6月劳动报酬金额', name: 'LABOR_ACT_SALY6', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '6月劳动报酬税额', name: 'LABOR_ACCRD_TAX6', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '7月工资金额', name: 'ACT_SALY7', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '7月工资税额', name: 'ACCRD_TAX7', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '7月劳动报酬金额', name: 'LABOR_ACT_SALY7', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '7月劳动报酬税额', name: 'LABOR_ACCRD_TAX7', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '8月工资金额', name: 'ACT_SALY8', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '8月工资税额', name: 'ACCRD_TAX8', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '8月劳动报酬金额', name: 'LABOR_ACT_SALY8', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '8月劳动报酬税额', name: 'LABOR_ACCRD_TAX8', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '9月工资金额', name: 'ACT_SALY9', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '9月工资税额', name: 'ACCRD_TAX9', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '9月劳动报酬金额', name: 'LABOR_ACT_SALY9', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '9月劳动报酬税额', name: 'LABOR_ACCRD_TAX9', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '10月工资金额', name: 'ACT_SALY10', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '10月工资税额', name: 'ACCRD_TAX10', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '10月劳动报酬金额', name: 'LABOR_ACT_SALY10', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '10月劳动报酬税额', name: 'LABOR_ACCRD_TAX10', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '11月工资金额', name: 'ACT_SALY11', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '11月工资税额', name: 'ACCRD_TAX11', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '11月劳动报酬金额', name: 'LABOR_ACT_SALY11', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '11月劳动报酬税额', name: 'LABOR_ACCRD_TAX11', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '12月工资金额', name: 'ACT_SALY12', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '12月工资税额', name: 'ACCRD_TAX12', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '12月劳动报酬金额', name: 'LABOR_ACT_SALY12', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '12月劳动报酬税额', name: 'LABOR_ACCRD_TAX12', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    
						    { label: '合计发放额', name: 'ACT_SALY', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '合计扣税额', name: 'ACCRD_TAX', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '实际缴税额', name: 'ACCRD_TEX_EXT', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false},
						    { label: '缴费差额', name: 'TAX_BALANCE', width: 110, sorttype: 'number', align: 'right', searchrules: {number: true}, formatter: 'number',editable: false}
						],
    			reloadAfterSubmit: true, 
    			viewrecords: true, 
    			shrinkToFit: false,
    			rowNum: 100,
    			//rowList: [100,200,500],
                multiselect: true,
                multiboxonly: true,
                sortable: true,
    			altRows: true, //斑马条纹
    			editurl: '',
    			
    			pager: pagerBase_selector,
    			footerrow: true,
    			userDataOnFooter: true,
    			//ondblClickRow: doubleClickRow,
    			
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
    		
    		$(gridBase_selector).navGrid(pagerBase_selector, 
    					{
    			            //navbar options
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
    				id: "edit",
    				    width: 900,
    					closeAfterEdit: true,
    					recreateForm: true,
    					beforeShowForm :beforeEditOrAddCallback,
    		            afterSubmit: fn_addSubmit_extend
    		        },
    		        {
    					//new record form
    				id: "add",
    				    width: 900,
    					closeAfterAdd: true,
    					recreateForm: true,
    					viewPagerButtons: false,
    					//width: 700,
    					//reloadAfterSubmit: true,
    					beforeShowForm : beforeEditOrAddCallback,
    				    onclickSubmit: function(params, posdata) {
    						console.log("onclickSubmit");
    		            } , 
    		            afterSubmit: fn_addSubmit_extend
    		        },
    		        { },
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
    			
    		$(gridBase_selector).navSeparatorAdd(pagerBase_selector, {
    				sepclass : "ui-separator",
    				sepcontent: ""
    			});
    		$(gridBase_selector).navButtonAdd(pagerBase_selector, {
    			             caption : "导出",
    			             buttonicon : "ace-icon fa fa-cloud-download",
    			             onClickButton : exportItems,
    			             position : "last",
    			             title : "导出",
    			             cursor : "pointer"
    			         });
    		//tosearch();
		});
	    
        /**
         * 导出
         */
        function exportItems(){
        	window.location.href='<%=basePath%>taxBalanceQuery/excel.do?'
                +'SelectedCustCol7='+$("#SelectedCustCol7").val()
                +'&SelectedBusiDate='+$("#SelectedBusiDate").val()
                +'&SelectedDepartCode='+$("#SelectedDepartCode").val();
        }

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
    		return [ true ];
        }
	
	    //加载单位树
	    function initComplete(){
			//下拉树
			var nodes = ${zTreeNodes};
			var defaultNodes = {"treeNodes":nodes};
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
	
	    //检索
	    function tosearch() {
	    	if($("#SelectedBusiDate").val()=="0"){
		        bootbox.dialog({
					message: "<span class='bigger-110'>请您先输入业务区间,然后再进行查询!</span>",
					buttons: 			
					{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
				});
		        return;
	        }
			$(gridBase_selector).jqGrid('setGridParam',{  // 重新加载数据
				url:'<%=basePath%>taxBalanceQuery/getPageList.do?'
                    +'SelectedCustCol7='+$("#SelectedCustCol7").val()
	                +'&SelectedBusiDate='+$("#SelectedBusiDate").val()
	                +'&SelectedDepartCode='+$("#SelectedDepartCode").val()
			}).trigger("reloadGrid");
        }
 	</script>
</html>