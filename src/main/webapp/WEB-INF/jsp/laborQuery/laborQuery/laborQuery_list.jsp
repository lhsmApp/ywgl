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
					    <div class="page-header">
					    	<span class="label label-xlg label-success arrowed-right">人工成本</span>
						    <!-- arrowed-in-right --> 
						    <span class="label label-xlg label-yellow arrowed-in arrowed-right"
						    	id="subTitle" style="margin-left: 2px;">劳务报酬所得查询</span> 
                            <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
								
							<button id="btnQuery" class="btn btn-white btn-info btn-sm"
									onclick="showQueryCondi($('#jqGridBase'),null,true)">
								<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
							</button>
								
						    <div class="pull-right">
								<span class="label label-xlg label-blue arrowed-left"
									id = "showDur" style="background:#428bca; margin-right: 2px;"></span> 
							</div>
					    </div>
			
						<div class="row">
						    <div class="col-xs-12">
							    <div class="widget-box"  >
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
													    data-placeholder="请选择帐套"
													    style="vertical-align: top; height:32px;width: 150px;">
													    <option value="">请选择帐套</option>
													    <c:forEach items="${FMISACC}" var="each">
														    <option value="${each.DICT_CODE}" 
														        <c:if test="${pd.SelectedCustCol7==each.DICT_CODE}">selected</c:if>>${each.NAME}</option>
													    </c:forEach>
												    </select>
											    </span>
											    <span class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
												    <div class="selectTree" id="selectTree" multiMode="true"
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
	<!-- JqGrid统一样式统一操作 -->
	<script type="text/javascript" src="static/js/common/jqgrid_style.js"></script>
	<!-- 上传控件 -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	
	<script type="text/javascript"> 
        var gridBase_selector = "#jqGridBase";  
        var pagerBase_selector = "#jqGridBasePager";  

        //有权限导出表的部门
        var bolCanExportTable;

		//当前期间,取自tb_system_config的SystemDateTime字段
	    var SystemDateTime = '';
        
        function SetStructure(){
	        //resize to fit page size
		    $(window).on('resize.jqGrid', function () {
		        $(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
		        resizeGridHeight($(gridBase_selector),null,true);
		    });
		
		    $(gridBase_selector).jqGrid({
			    url: '<%=basePath%>laborQuery/getPageList.do?'
                    + 'SelectedBusiDate='+$("#SelectedBusiDate").val()
					+ '&SelectedDepartCode='+$("#SelectedDepartCode").val()
		            + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
    	            + '&SystemDateTime='+SystemDateTime,
			    datatype: "json",
			    colModel: [
						//{ label: '编号', name: 'USER_CODE', 
						//	editable: true, edittype:'text', editoptions:{maxLength:'30'}, editrules:{required:true}
						//},
						{ label: '姓名', name: 'USER_NAME',
							editable: true, edittype:'text', editoptions:{maxLength:'20'}, editrules:{required:true}
						},
						{ label: '身份证号', name: 'STAFF_IDENT',
							editable: true, edittype:'text', editoptions:{maxLength:'20'}, editrules:{required:true}
						},
						{ label: '应发评审费', name: 'GROSS_PAY', sorttype: 'number', align: 'right', summaryType:'sum', summaryTpl:'<b>sum:{0}</b>',
							editable: true, edittype:'text', editoptions:{maxlength:'12', number: true}, editrules: {number: true}, 
						    searchrules: {number: true}, formatter: 'number'
						},
						{ label: '个人所得税', name: 'ACCRD_TAX', sorttype: 'number', align: 'right', summaryType:'sum', summaryTpl:'<b>sum:{0}</b>',
							editable: true, edittype:'text', editoptions:{maxlength:'12', number: true}, editrules: {number: true}, 
						    searchrules: {number: true}, formatter: 'number'
						},
						{ label: '实发评审费', name: 'ACT_SALY', sorttype: 'number', align: 'right', summaryType:'sum', summaryTpl:'<b>sum:{0}</b>',
							editable: true, edittype:'text', editoptions:{maxlength:'12', number: true}, editrules: {number: true}, 
						    searchrules: {number: true}, formatter: 'number'
						},
						{ name: 'SERIAL_NO', key: true, hidden: true, editable: true, editrules: {edithidden: false}},
						{ name: 'BUSI_DATE', hidden: true, editable: true, editrules: {edithidden: false}},
						{ name: 'BILL_OFF', hidden: true, editable: true, editrules: {edithidden: false}},
						{ name: 'DEPT_CODE', hidden: true, editable: true, editrules: {edithidden: false}}],
			    viewrecords: true, 
			    shrinkToFit: true,
			    rowNum: 0,
				scroll: 1,
                altRows: true, //斑马条纹

                sortable: true,
                sortname: 'STAFF_IDENT',
    			sortorder: 'asc',
    			
	            pager: pagerBase_selector,
	            footerrow: true,
	            userDataOnFooter: true,
	            
	            /*grouping: true,
				groupingView: {
					groupField: ['STAFF_IDENT'],
					groupOrder: ['asc'],
					groupColumnShow: [true],
					groupText: ['<b>{0}</b>'],
					groupSummary: [true],
					groupSummaryPos: ['footer'], //header
					groupCollapse: false,
	                plusicon : 'fa fa-chevron-down bigger-110',
					minusicon : 'fa fa-chevron-up bigger-110'
				},*/
    			
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
		        {},{}
		    );
			
			$(gridBase_selector).navSeparatorAdd(pagerBase_selector, {
				sepclass : "ui-separator",
				sepcontent: ""
			});
			$(gridBase_selector).navButtonAdd(pagerBase_selector, {
			            caption : "导出",
			            buttonicon : "ace-icon fa fa-cloud-download",
			            onClickButton : exportItems,
			            position : "last",
			            title : "劳务报酬所得导出",
			            cursor : "pointer"
			         });
        }
	    
        $(document).ready(function () {
            $(top.hangge());//关闭加载状态
	    
            //当前期间,取自tb_system_config的SystemDateTime字段
	        SystemDateTime = '${SystemDateTime}';
			$("#SelectedBusiDate").val(SystemDateTime);
			//当前登录人所在二级单位
		    var DepartName = '${DepartName}';
		    $("#showDur").text('当前期间：' + SystemDateTime + ' 登录人责任中心：' + DepartName);
	        //有权限导出表的部门
	        bolCanExportTable = ${pd.CanExportTable};
		    
		    SetStructure();
	    });

        /**
         * 导出
         */
        function exportItems(){
	    	//有权限导出表的部门
	    	if(bolCanExportTable){
		     	top.jzts();
		    	var diag = new top.Dialog();
		    	diag.Drag=true;
		    	diag.Title ="导出劳务报酬所得";
		    	diag.URL = '<%=basePath%>laborQuery/goDownExcel.do?'
                    + 'SelectedBusiDate='+$("#SelectedBusiDate").val()
					+ '&SelectedDepartCode='+$("#SelectedDepartCode").val()
		            + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
    	            + '&SystemDateTime='+SystemDateTime;
		    	diag.Width = 300;
		    	diag.Height = 170;
		    	diag.CancelEvent = function(){ //关闭事件
		    		top.jzts();
		    		$(top.hangge());//关闭加载状态
		    	    diag.close();
		        };
		        diag.show();
	    	} else {
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'无此功能权限！',
		            bg:'#cc0033',
		            time:3
		        });
	    	}
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
			$(gridBase_selector).jqGrid('setGridParam',{  // 重新加载数据
				url:'<%=basePath%>laborQuery/getPageList.do?'
                    + 'SelectedBusiDate='+$("#SelectedBusiDate").val()
					+ '&SelectedDepartCode='+$("#SelectedDepartCode").val()
		            + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
    	            + '&SystemDateTime='+SystemDateTime,
				datatype : 'json',
			}).trigger("reloadGrid");
		}  
 	</script>
</html>