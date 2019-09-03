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
						<span class="label label-xlg label-success arrowed-right">人工成本</span>
						<!-- arrowed-in-right --> 
						<span class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">凭证单据确认</span> 
                        <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
								
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGridBase'),gridHeight)">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
						</button>
						
						<div class="pull-right">
							<span class="green middle bolder">类型: &nbsp;</span>

							<div class="btn-toolbar inline middle no-margin">
								<div data-toggle="buttons" class="btn-group no-margin">
									            <label class="btn btn-sm btn-primary active"> <span
									    	        class="bigger-110">合同化</span> <input type="radio" value="6" />
									            </label> 
									            <label class="btn btn-sm btn-primary"> <span
									            	class="bigger-110">市场化</span> <input type="radio" value="7" />
									            </label> 
									            <label class="btn btn-sm btn-primary"> <span
									            	class="bigger-110">劳务人员在建</span> <input type="radio" value="8" />
									            </label>
									            <label class="btn btn-sm btn-primary"> <span
									    	        class="bigger-110">运行人员</span> <input type="radio" value="9" />
									            </label>
									            <label class="btn btn-sm btn-primary"> <span
										            class="bigger-110">劳务用工</span> <input type="radio" value="10" />
									            </label>
									            <label class="btn btn-sm btn-primary"> <span
										            class="bigger-110">社保</span> <input type="radio" value="22" />
									            </label>
									            <label class="btn btn-sm btn-primary"> <span
									            	class="bigger-110">公积金</span> <input type="radio" value="26" />
									            </label>
								</div>
							</div>
						</div>
					</div><!-- /.page-header -->
			        
					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box">
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<span class="input-icon pull-left" style="margin-right: 5px;">
												<input id="SelectedBusiDate" class="input-mask-date" type="text"
												  placeholder="请输入业务区间"> 
												<i class="ace-icon fa fa-calendar blue"></i>
											</span>
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control" data-placeholder="请选择帐套"
													    name="SelectedCustCol7" id="SelectedCustCol7"
													    style="vertical-align: top; height:32px;width: 150px;">
													<option value="">请选择帐套</option>
													<c:forEach items="${FMISACC}" var="each">
														<option value="${each.DICT_CODE}">${each.NAME}</option>
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
									        <button type="button" class="btn btn-info btn-sm" onclick="confirm()">
										        <i class="ace-icon fa bigger-120 blue"></i><span>批量确认</span>
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
									    <a data-toggle="tab" href="#voucherTransfer"> 
										    <i class="green ace-icon fa fa-user bigger-120"></i> 未确认
									    </a>
									</li>
									<li>
									    <a data-toggle="tab" href="#voucherMgr"> 
									        <i class="orange ace-icon fa fa-rss bigger-120"></i> 已确认
									    </a>
									</li>
								</ul>
								<div class="tab-content no-border ">
						            <table id="jqGridBase"></table>
						            <div id="jqGridBasePager"></div>
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
    var _table = "_table";
    var _pager = "_pager";

	var gridHeight;
	var which;
	var TabType = 1;
	//前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	var jqGridColModel;
	
	$(document).ready(function () {
		$(top.hangge());//关闭加载状态
		$('.input-mask-date').mask('999999');
	    
		//当前期间,取自tb_system_config的SystemDateTime字段
        var SystemDateTime = '${SystemDateTime}';
		$("#SelectedBusiDate").val(SystemDateTime);
	    
		//前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	    jqGridColModel = "[]";//此处记得用eval()行数将string转为array

		//初始化当前选择凭证类型
		if('${pd.which}'!=""){
			$('[data-toggle="buttons"] .btn').each(function(index, data){
				var target = $(this).find('input[type=radio]');
				$(this).removeClass('active');
				var whichCur = parseInt(target.val());
				console.log(which);
				if(whichCur=='${pd.which}'){
					$(this).addClass('active');
					which=whichCur;
				}
			});
		} 
	    
		//凭证类型变化
		$('[data-toggle="buttons"] .btn').on('click', function(e){
			var target = $(this).find('input[type=radio]');
			which = parseInt(target.val());
			window.location.href='<%=basePath%>fundssummyconfirm/list.do?SelectedTableNo='+which
            + '&SelectedBusiDate='+$("#SelectedBusiDate").val();
		});
		
		//tab页切换
		$('.nav-tabs li').on('click', function(e){
			if($(this).hasClass('active')) return;
			var target = $(this).find('a');
			
			if(target.attr('href')=='#voucherTransfer'){
				TabType=1;
			}else if(target.attr('href')=='#voucherMgr'){
				TabType=2;
			}
			tosearch();
		});
	});  

    // the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function showFirstChildGrid(parentRowID, parentRowKey) {
    	console.log(parentRowID+"  "+parentRowKey);
		var rowData = $(gridBase_selector).getRowData(parentRowKey);
    	var BILL_CODE = rowData.BILL_CODE__;
    	console.log(BILL_CODE);
    	var DEPT_CODE = rowData.DEPT_CODE__;
    	console.log(DEPT_CODE);
    	var CUST_COL7 = rowData.CUST_COL7__;
    	console.log(CUST_COL7);
    	
        var detailColModel = "[]";
		$.ajax({
			type: "GET",
			url: '<%=basePath%>fundssummyconfirm/getFirstDetailColModel.do?SelectedTableNo='+which
            + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
	    	data: {DataDeptCode:DEPT_CODE,DataCustCol7:CUST_COL7},
			dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$(top.hangge());//关闭加载状态
					detailColModel = response.message;

		            detailColModel = eval(detailColModel);
		            var childGridID = parentRowID + _table;
		            var childGridPagerID = parentRowID + _pager;
		            // send the parent row primary key to the server so that we know which grid to show
		            var childGridURL = '<%=basePath%>fundssummyconfirm/getFirstDetailList.do?SelectedTableNo='+which+'&DetailListBillCode='+BILL_CODE
		            + '&SelectedBusiDate='+$("#SelectedBusiDate").val();

		            // add a table and pager HTML elements to the parent grid row - we will render the child grid here
		            $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');

		            $("#" + childGridID).jqGrid({
		                url: childGridURL,
		                mtype: "GET",
		                datatype: "json",
		                colModel: detailColModel,
		                page: 1,
		                //width: '100%',
		                //height: '100%',
		                rowNum: 0,	
		                pager: "#" + childGridPagerID,
						pgbuttons: false, // 分页按钮是否显示 
						pginput: false, // 是否允许输入分页页数 
		                viewrecords: true,
		                recordpos: "left", // 记录数显示位置 

		    			shrinkToFit: false,
		    			altRows: true, //斑马条纹
		                
		    			//footerrow: true,
		    			//userDataOnFooter: true,

		    			subGrid: true,
		    			subGridOptions: {
		    				plusicon : "ace-icon fa fa-plus center bigger-110 blue",
		    				minusicon  : "ace-icon fa fa-minus center bigger-110 blue",
		    				openicon : "ace-icon fa fa-chevron-right center orange"
		                },
		                subGridRowExpanded: showSecondChildGrid,

		    			scroll: 1,
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
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取结构失败：'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(response) {
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取结构出错:'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
		});
    };

    // the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function showSecondChildGrid(parentRowID, parentRowKey) {
    	console.log(parentRowID+"  "+parentRowKey);
    	var gridSelect = parentRowID.toString().substring(0, parentRowID.toString().length - parentRowKey.toString().length - 1);
    	console.log("gridSelect  "+gridSelect);
		var rowData = $("#" + gridSelect).getRowData(parentRowKey);
    	var DEPT_CODE = rowData.DEPT_CODE__;
    	console.log(DEPT_CODE);
    	var CUST_COL7 = rowData.CUST_COL7__;
    	console.log(CUST_COL7);
    	
        var detailColModel = "[]";
		$.ajax({
			type: "GET",
			url: '<%=basePath%>fundssummyconfirm/getSecondDetailColModel.do?SelectedTableNo='+which
            + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
	    	data: {DataDeptCode:DEPT_CODE,DataCustCol7:CUST_COL7},
			dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$(top.hangge());//关闭加载状态
					detailColModel = response.message;

		            detailColModel = eval(detailColModel);
		            var childGridID = parentRowID + _table;
		            var childGridPagerID = parentRowID + _pager;
		            // send the parent row primary key to the server so that we know which grid to show
		            var childGridURL = '<%=basePath%>fundssummyconfirm/getSecondDetailList.do?SelectedTableNo='+which
		            + '&SelectedBusiDate='+$("#SelectedBusiDate").val();
		            //childGridURL = childGridURL + "&parentRowID=" + encodeURIComponent(parentRowKey)
                    var listData =new Array();
				    listData.push(rowData);

		            // add a table and pager HTML elements to the parent grid row - we will render the child grid here
		            $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');

		            $("#" + childGridID).jqGrid({
		                url: childGridURL,
		                postData: {DataRows:JSON.stringify(listData)},
		                mtype: "GET",
		                datatype: "json",
		                colModel: detailColModel,
		                page: 1,
		                //width: '100%',
		                //height: '100%',
		                rowNum: 0,	
		                pager: "#" + childGridPagerID,
						pgbuttons: false, // 分页按钮是否显示 
						pginput: false, // 是否允许输入分页页数 
		                viewrecords: true,
		                recordpos: "left", // 记录数显示位置 
		                
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
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取结构失败：'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(response) {
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取结构出错:'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
		});
    };
    
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
		});
		//赋给data属性
		$("#selectTree").data("data",defaultNodes);  
		$("#selectTree").render();
		$("#selectTree2_input").val("请选择单位");
	};
	
	//检索
	function tosearch() {
		$(gridBase_selector).jqGrid('GridUnload'); 
		//前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	    jqGridColModel = "[]";
		
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>fundssummyconfirm/getShowColModel.do?SelectedTableNo='+which
                +'&SelectedTabType='+TabType
                +'&SelectedDepartCode='+$("#SelectedDepartCode").val()
                +'&SelectedCustCol7='+$("#SelectedCustCol7").val()
                + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
			dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$(top.hangge());//关闭加载状态
					var mes = response.message;
					mes = mes.replace("[", "").replace("]", "");
					if($.trim(mes)!=""){
					    //前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
				        jqGridColModel = eval("(" + response.message + ")");//此处记得用eval()行数将string转为array
					    SetStructure();
					}
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取显示结构失败：'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(response) {
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取显示结构出错：'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
		});
	};  
	
	function SetStructure(){
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
			gridHeight=236;
			resizeGridHeight($(gridBase_selector),gridHeight);
	    });

		$(gridBase_selector).jqGrid({
			url: '<%=basePath%>fundssummyconfirm/getPageList.do?SelectedTableNo='+which
                +'&SelectedTabType='+TabType
                +'&SelectedDepartCode='+$("#SelectedDepartCode").val()
                +'&SelectedCustCol7='+$("#SelectedCustCol7").val()
                + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
			datatype: "json",
			colModel: jqGridColModel,
			viewrecords: true, 
			shrinkToFit: false,
			//width:'100%',
			reloadAfterSubmit: true, 
			rowNum: 0,
            multiselect: true,
            multiboxonly: true,
			altRows: true, //斑马条纹
			
			pager: pagerBase_selector,
			pgbuttons: false, // 分页按钮是否显示 
			pginput: false, // 是否允许输入分页页数 
			footerrow: true,
			userDataOnFooter: true,

            sortable: true,
            sortname: 'DEPT_CODE',
			sortorder: 'asc',
			
			subGrid: true,
			subGridOptions: {
				plusicon : "ace-icon fa fa-plus center bigger-110 blue",
				minusicon  : "ace-icon fa fa-minus center bigger-110 blue",
				openicon : "ace-icon fa fa-chevron-right center orange"
            },
            subGridRowExpanded: showFirstChildGrid,

			scroll: 1,

			cellEdit: true,
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
		        {},{});
	        		$(gridBase_selector).navSeparatorAdd(pagerBase_selector, {
	        			sepclass : "ui-separator",
	        			sepcontent: ""
	        		});
	        	console.log("TabType:" + TabType);
	        	if(TabType==1){
					$(gridBase_selector).navButtonAdd(pagerBase_selector, {
			             caption : "确认",
			             buttonicon : "ace-icon fa fa-cloud-download",
			             onClickButton : summyBillConfirm,
			             position : "last",
			             title : "确认",
			             cursor : "pointer"
			         });
	        	}
	        	if(TabType==2){
					$(gridBase_selector).navButtonAdd(pagerBase_selector, {
			             caption : "取消确认",
			             buttonicon : "ace-icon fa fa-cloud-download",
			             onClickButton : summyBillCancel,
			             position : "last",
			             title : "取消确认",
			             cursor : "pointer"
			         });
	        	}
	} 
	
	/**
	 * 确认
	 */
    function summyBillConfirm(){
    	//获得选中的行ids的方法
    	var ids = $(gridBase_selector).getGridParam("selarrrow");  
    	
		if(!(ids!=null && ids.length>0)){
			bootbox.dialog({
				message: "<span class='bigger-110'>您没有选择任何内容!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
		}else{
            var msg = '确定要确认选中的数据吗?';
            bootbox.confirm(msg, function(result) {
				if(result) {
					var listData =new Array();
					
					//遍历访问这个集合  
					$(ids).each(function (index, id){  
			            var rowData = $(gridBase_selector).getRowData(id);
			            listData.push(rowData);
					});
					
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>fundssummyconfirm/summyBillConfirm.do?SelectedTableNo='+which
		                        +'&SelectedTabType='+TabType
		                        + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
				    	data: {DataRows:JSON.stringify(listData)},
						dataType:'json',
						cache: false,
						success: function(response){
							if(response.code==0){
								$(gridBase_selector).trigger("reloadGrid");  
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'确认成功',
						            bg:'#009933',
						            time:3
						        });
							}else{
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'确认失败,'+response.message,
						            bg:'#cc0033',
						            time:3
						        });
							}
						},
				    	error: function(response) {
							$(top.hangge());//关闭加载状态
							$("#subTitle").tips({
								side:3,
					            msg:'确认出错:'+response.responseJSON.message,
					            bg:'#cc0033',
					            time:3
					        });
				    	}
					});
				}
            });
		}
    }
	
	/**
	 * 取消确认
	 */
    function summyBillCancel(){
    	//获得选中的行ids的方法
    	var ids = $(gridBase_selector).getGridParam("selarrrow");  
    	
		if(!(ids!=null && ids.length>0)){
			bootbox.dialog({
				message: "<span class='bigger-110'>您没有选择任何内容!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
		}else{
            var msg = '确定要取消确认选中的数据吗?';
            bootbox.confirm(msg, function(result) {
				if(result) {
					var listData =new Array();
					
					//遍历访问这个集合  
					$(ids).each(function (index, id){  
			            var rowData = $(gridBase_selector).getRowData(id);
			            listData.push(rowData);
					});
					
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>fundssummyconfirm/summyBillCancel.do?SelectedTableNo='+which
                             +'&SelectedTabType='+TabType
                             + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
				    	data: {DataRows:JSON.stringify(listData)},
						dataType:'json',
						cache: false,
						success: function(response){
							if(response.code==0){
								$(gridBase_selector).trigger("reloadGrid");  
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'取消确认成功',
						            bg:'#009933',
						            time:3
						        });
							}else{
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'取消确认失败,'+response.message,
						            bg:'#cc0033',
						            time:3
						        });
							}
						},
				    	error: function(response) {
							$(top.hangge());//关闭加载状态
							$("#subTitle").tips({
								side:3,
					            msg:'取消确认出错:'+response.responseJSON.message,
					            bg:'#cc0033',
					            time:3
					        });
				    	}
					});
				}
            });
		}
    }
	
	//确认
    function confirm() {
		var CustCol7 = $("#SelectedCustCol7").val();
		var DepartCode = $("#SelectedDepartCode").val(); 

		if(!(CustCol7!=null && $.trim(CustCol7)!="")){
			$("#SelectedCustCol7").tips({
				side:3,
	            msg:'请选择帐套',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SelectedCustCol7").focus();
			return false;
		}
		/*if(!(DepartCode!=null && $.trim(DepartCode)!="")){
			$("#SelectedDepartCode").tips({
				side:3,
	            msg:'请选择责任中心',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SelectedDepartCode").focus();
			return false;
		}*/
         var msg = '确定要确认吗?';
         bootbox.confirm(msg, function(result) {
        	 if(result) {
       	        top.jzts();

				$.ajax({
					type: "POST",
					url: '<%=basePath%>fundssummyconfirm/confirmAll.do?SelectedTableNo='+which
                     +'&SelectedCustCol7='+CustCol7
				 	 +'&SelectedDepartCode='+DepartCode
			            + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
					dataType:'json',
					cache: false,
					success: function(response){
						if(response.code==0){
							$(top.hangge());//关闭加载状态
							tosearch();
							$("#subTitle").tips({
								side:3,
					            msg:'确认成功',
					            bg:'#009933',
					            time:3
					        });
						}else{
							$(top.hangge());//关闭加载状态
							$("#subTitle").tips({
								side:3,
					            msg:'确认失败,'+response.message,
					            bg:'#cc0033',
					            time:3
					        });
						}
					},
			    	error: function(response) {
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side:3,
				            msg:'确认出错:'+response.responseJSON.message,
				            bg:'#cc0033',
				            time:3
				        });
			    	}
				});
        	 }
         });
	}
</script>
</html>