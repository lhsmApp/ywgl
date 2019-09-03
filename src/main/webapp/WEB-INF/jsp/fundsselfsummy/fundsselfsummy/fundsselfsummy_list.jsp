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
							id="subTitle" style="margin-left: 2px;">凭证单据汇总</span> 
                        <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
								
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGridBase'),null,true)">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
						</button>
						
						<!-- <div class="pull-right">
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
						 </div>-->
					</div><!-- /.page-header -->
			
					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box">
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedCustCol7" id="SelectedCustCol7"
													data-placeholder="请选择帐套" 
													onchange="ChangeSelectedCustCol7()"
													style="vertical-align: top; height:32px;width: 150px;">
													    <option value="">请选择帐套</option>
													    <c:forEach items="${FMISACC}" var="each">
													    	<option value="${each.DICT_CODE}" 
														        <c:if test="${pd.SelectedCustCol7==each.DICT_CODE}">selected</c:if>>${each.NAME}</option>
													    </c:forEach>
												</select>
											</span>
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedTypeCode" id="SelectedTypeCode"
													onchange="ChangeSelectedTypeCode()"
													style="vertical-align: top; height:32px;width: 150px;">
												</select>
											</span>
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedDepartCode" id="SelectedDepartCode"
													onchange="getSelectBillCodeOptions()"
													style="vertical-align: top; height:32px;width: 150px;">
												</select>
											</span>
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedBillCode" id="SelectedBillCode"
													style="vertical-align: top; height:32px;width: 160px;">
												</select>
											</span>
										    <button type="button" class="btn btn-info btn-sm" onclick="btnSummyClick();">
												<i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i> <span>汇总</span>
											</button>
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
    var _table = "_table";
    var _pager = "_pager";

	//前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	var jqGridColModel;
	
	//页面显示的数据的查询信息，在tosearch()里赋值
	var ShowDataTypeCode = "";
	var ShowDataCustCol7 = "";
	var ShowDataDepartCode = "";
	var ShowDataBillCode = "";
	//单号下拉列表
    var SelectedTypeCodeFirstShow;
	var InitSelectedTypeCodeOptions;
	var SelectedDepartCodeFirstShow;
    var InitSelectedDepartCodeOptions;
	var SelectedBillCodeFirstShow;
	var InitSelectedBillCodeOptions;

	//当前期间,取自tb_system_config的SystemDateTime字段
    var SystemDateTime = '';
	
	$(document).ready(function () {
		$(top.hangge());//关闭加载状态
	    
		//当前期间,取自tb_system_config的SystemDateTime字段
	    SystemDateTime = '${SystemDateTime}';
	    
		//前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	    jqGridColModel = "[]";//此处记得用eval()行数将string转为array
	    
		//选择下拉列表
	    SelectedTypeCodeFirstShow =  "${pd.SelectedTypeCodeFirstShow}";
	    InitSelectedTypeCodeOptions =  "${pd.InitSelectedTypeCodeOptions}";
	    SelectedDepartCodeFirstShow =  "${pd.SelectedDepartCodeFirstShow}";
	    InitSelectedDepartCodeOptions =  "${pd.InitSelectedDepartCodeOptions}";
	    SelectedBillCodeFirstShow =  "${pd.SelectedBillCodeFirstShow}";
	    InitSelectedBillCodeOptions =  "${pd.InitSelectedBillCodeOptions}";
		setSelectTypeCodeOptions(InitSelectedTypeCodeOptions);
		setSelectDepartCodeOptions(InitSelectedDepartCodeOptions);
		setSelectBillCodeOptions(InitSelectedBillCodeOptions);
	});  
	
    function setSelectTypeCodeOptions(selectOptions){
        $("#SelectedTypeCode").empty();   //先清空
        $("#SelectedTypeCode").append(selectOptions);  //再赋值
    }
    function setSelectDepartCodeOptions(selectOptions){
        $("#SelectedDepartCode").empty();   //先清空
        $("#SelectedDepartCode").append(selectOptions);  //再赋值
    }
    function setSelectBillCodeOptions(selectOptions){
        $("#SelectedBillCode").empty();   //先清空
        $("#SelectedBillCode").append(selectOptions);  //再赋值
    }
	
	function ChangeSelectedCustCol7(){
		setSelectTypeCodeOptions(InitSelectedTypeCodeOptions);
		setSelectDepartCodeOptions(InitSelectedDepartCodeOptions);
		setSelectBillCodeOptions(InitSelectedBillCodeOptions);
		
		var CustCol7 = $("#SelectedCustCol7").val();
		if(CustCol7!=null && $.trim(CustCol7)!=""){
			getSelectTypeCodeOptions();
			getSelectDepartCodeOptions();
		}
	}
	
	function ChangeSelectedTypeCode(){
		setSelectDepartCodeOptions(InitSelectedDepartCodeOptions);
		setSelectBillCodeOptions(InitSelectedBillCodeOptions);
		
		var TypeCode = $("#SelectedTypeCode").val();
		if(TypeCode!=null && $.trim(TypeCode)!=""){
			getSelectDepartCodeOptions();
		}
	}
    
    function getSelectTypeCodeOptions(){
		setSelectTypeCodeOptions(InitSelectedTypeCodeOptions);
		setSelectDepartCodeOptions(InitSelectedDepartCodeOptions);
		setSelectBillCodeOptions(InitSelectedBillCodeOptions);
		top.jzts();
		$.ajax({
		    type: "POST",
			url: '<%=basePath%>fundsselfsummy/getTypeCodeList.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
            + '&SystemDateTime='+SystemDateTime,
		    dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$(top.hangge());//关闭加载状态
					setSelectTypeCodeOptions(response.message);
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取账套列表失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(response) {
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取账套列表出错:'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
	    });
    }
    
    function getSelectDepartCodeOptions(){
		setSelectDepartCodeOptions(InitSelectedDepartCodeOptions);
		setSelectBillCodeOptions(InitSelectedBillCodeOptions);
		top.jzts();
		$.ajax({
		    type: "POST",
			url: '<%=basePath%>fundsselfsummy/getDepartCodeList.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
					+'&SelectedTypeCode='+$("#SelectedTypeCode").val()
    	            + '&SystemDateTime='+SystemDateTime,
		    dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$(top.hangge());//关闭加载状态
					setSelectDepartCodeOptions(response.message);
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取责任中心列表失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(response) {
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取责任中心出错:'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
	    });
    }
    
    function getSelectBillCodeOptions(){
		setSelectBillCodeOptions(InitSelectedBillCodeOptions);
		top.jzts();
		$.ajax({
		    type: "POST",
			url: '<%=basePath%>fundsselfsummy/getBillCodeList.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
			    +'&SelectedTypeCode='+$("#SelectedTypeCode").val()
                +'&SelectedDepartCode='+$("#SelectedDepartCode").val()
	            + '&SystemDateTime='+SystemDateTime,
		    dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$(top.hangge());//关闭加载状态
					setSelectBillCodeOptions(response.message);
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取单号列表失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(response) {
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取单号列表出错:'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
	    });
    }
    
    // the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function showFirstChildGrid(parentRowID, parentRowKey) {
    	console.log(parentRowID+"  "+parentRowKey);
		var rowData = $(gridBase_selector).getRowData(parentRowKey);
    	var BILL_OFF = rowData.BILL_OFF__;
    	console.log(BILL_OFF);
    	var TYPE_CODE = rowData.TYPE_CODE__;
    	console.log(TYPE_CODE);
    	var DEPT_CODE = rowData.DEPT_CODE__;
    	console.log(DEPT_CODE);
    	var BILL_CODE = rowData.BILL_CODE__;
    	console.log(BILL_CODE);
    	var BUSI_DATE = rowData.BUSI_DATE__;
    	console.log(BUSI_DATE);
    	
        var detailColModel = "[]";
		$.ajax({
			type: "GET",
			url: '<%=basePath%>fundsselfsummy/getFirstDetailColModel.do?'
	            + 'SystemDateTime='+SystemDateTime,
	    	data: {DataCustCol7:BILL_OFF,DataTypeCode:TYPE_CODE,DataBusiDate:BUSI_DATE,DataDeptCode:DEPT_CODE},
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
		            var childGridURL = '<%=basePath%>fundsselfsummy/getFirstDetailList.do?DetailListBillCode='+BILL_CODE
    	            + '&SystemDateTime='+SystemDateTime;

		            // add a table and pager HTML elements to the parent grid row - we will render the child grid here
		            $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');

		            $("#" + childGridID).jqGrid({
		                url: childGridURL,
		    	    	postData: {DataCustCol7:BILL_OFF,DataTypeCode:TYPE_CODE,DataBusiDate:BUSI_DATE,DataDeptCode:DEPT_CODE},
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
    	var BILL_OFF = rowData.BILL_OFF__;
    	console.log(BILL_OFF);
    	var TYPE_CODE = rowData.TYPE_CODE__;
    	console.log(TYPE_CODE);
    	var DEPT_CODE = rowData.DEPT_CODE__;
    	console.log(DEPT_CODE);
    	
        var detailColModel = "[]";
		$.ajax({
			type: "GET",
			url: '<%=basePath%>fundsselfsummy/getSecondDetailColModel.do?'
	            + 'SystemDateTime='+SystemDateTime,
	    	data: {DataCustCol7:BILL_OFF,DataTypeCode:TYPE_CODE,DataDeptCode:DEPT_CODE},
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
		            var childGridURL = '<%=basePath%>fundsselfsummy/getSecondDetailList.do?'
	    	            + 'SystemDateTime='+SystemDateTime;
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
	
	//检索
	function tosearch() {
		ShowDataTypeCode = $("#SelectedTypeCode").val();
		ShowDataCustCol7 = $("#SelectedCustCol7").val();
		ShowDataDepartCode = $("#SelectedDepartCode").val();
		ShowDataBillCode = $("#SelectedBillCode").val();
		
		$(gridBase_selector).jqGrid('GridUnload'); 
		//前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	    jqGridColModel = "[]";
		
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>fundsselfsummy/getShowColModel.do?SelectedCustCol7='+ShowDataCustCol7
                +'&SelectedTypeCode='+ShowDataTypeCode
                +'&SelectedDepartCode='+ShowDataDepartCode
                +'&SelectedBillCode='+ShowDataBillCode
	            + '&SystemDateTime='+SystemDateTime,
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
	}  
	
	function SetStructure(){
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
			//$(gridBase_selector).jqGrid( 'setGridHeight', $(window).height() - 240);
			resizeGridHeight($(gridBase_selector),null,true);
	    });

		$(gridBase_selector).jqGrid({
			url: '<%=basePath%>fundsselfsummy/getPageList.do?SelectedCustCol7='+ShowDataCustCol7
                +'&SelectedTypeCode='+ShowDataTypeCode
                +'&SelectedDepartCode='+ShowDataDepartCode
                +'&SelectedBillCode='+ShowDataBillCode
	            + '&SystemDateTime='+SystemDateTime,
			datatype: "json",
			colModel: jqGridColModel,
			viewrecords: true, 
			shrinkToFit: false,
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
					$(gridBase_selector).navButtonAdd(pagerBase_selector, {
			             caption : "取消汇总",
			             buttonicon : "ace-icon fa fa-cloud-download",
			             onClickButton : btnCancelSummy,
			             position : "last",
			             title : "取消汇总",
			             cursor : "pointer"
			         });
	}
	
	/**
	 * 汇总
	 */
    function btnSummyClick(){
		var TypeCode = $("#SelectedTypeCode").val();
		var CustCol7 = $("#SelectedCustCol7").val();
		var DepartCode = $("#SelectedDepartCode").val();
		var BillCode = $("#SelectedBillCode").val();
		
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
		if(!(TypeCode!=null && $.trim(TypeCode)!="")){
			$("#SelectedTypeCode").tips({
				side:3,
	            msg:'请选择凭证类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SelectedTypeCode").focus();
			return false;
		}
		if(!(DepartCode!=null && $.trim(DepartCode)!="")){
			$("#SelectedDepartCode").tips({
				side:3,
	            msg:'请选择责任中心',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SelectedDepartCode").focus();
			return false;
		}
			
        var msg = '确定要汇总吗?';
        bootbox.confirm(msg, function(result) {
			if(result) {
				top.jzts();
				$.ajax({
					type: "POST",
					url: '<%=basePath%>fundsselfsummy/summyBill.do?SelectedCustCol7='+CustCol7
                        +'&SelectedTypeCode='+TypeCode
	                    +'&SelectedDepartCode='+DepartCode
	                    +'&SelectedBillCode='+BillCode
	    	            + '&SystemDateTime='+SystemDateTime,
					dataType:'json',
					cache: false,
					success: function(response){
						if(response.code==0){
							$(top.hangge());//关闭加载状态
							getSelectBillCodeOptions();
							$(gridBase_selector).trigger("reloadGrid");  
							$("#subTitle").tips({
								side:3,
					            msg:'汇总成功',
					            bg:'#009933',
					            time:3
					        });
						}else{
							var message = response.message;
							try{
								$.ajax({
									type: "POST",
									url: '<%=basePath%>fundsselfsummy/summyBillLog.do?SelectedCustCol7='+CustCol7
				                        +'&SelectedTypeCode='+TypeCode
					                    +'&SelectedDepartCode='+DepartCode
					                    +'&SelectedBillCode='+BillCode
					    	            + '&SystemDateTime='+SystemDateTime
					                    +'&message='+encodeURIComponent(message),
									dataType:'json',
									cache: false,
									success: function(response){
										$(top.hangge());//关闭加载状态
									},
							    	error: function(response) {
										$(top.hangge());//关闭加载状态
							    	}
								});
							} catch(err){}
							console.log(encodeURIComponent(message));
							$(top.hangge());//关闭加载状态
							alert('汇总失败,'+response.message);
							/*$("#subTitle").tips({
								side:3,
					            msg:'汇总失败,'+response.message,
					            bg:'#cc0033',
					            time:3
					        });*/
						}
					},
			    	error: function(response) {
						var message = response.responseJSON.message;
						try{
							$.ajax({
								type: "POST",
								url: '<%=basePath%>fundsselfsummy/summyBillLog.do?SelectedCustCol7='+CustCol7
			                        +'&SelectedTypeCode='+TypeCode
				                    +'&SelectedDepartCode='+DepartCode
				                    +'&SelectedBillCode='+BillCode
				    	            + '&SystemDateTime='+SystemDateTime
				                    +'&message='+encodeURIComponent(message),
								dataType:'json',
								cache: false,
								success: function(response){
									$(top.hangge());//关闭加载状态
								},
						    	error: function(response) {
									$(top.hangge());//关闭加载状态
						    	}
							});
						} catch(err){}
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side:3,
				            msg:'汇总出错:'+message,
				            bg:'#cc0033',
				            time:3
				        });
			    	}
				});
			}
        });
    }
	
	/**
	 * 取消
	 */
    function btnCancelSummy(){
    	//获得选中的行ids的方法
    	var ids = $(gridBase_selector).getGridParam("selarrrow");  
    	
		if(!(ids!=null && ids.length>0)){
			bootbox.dialog({
				message: "<span class='bigger-110'>您没有选择任何内容!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
		}else{
            var msg = '确定要取消汇总选中的数据吗?';
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
						url: '<%=basePath%>fundsselfsummy/cancelSummy.do?'
		    	            + 'SystemDateTime='+SystemDateTime,
				    	data: {DataRows:JSON.stringify(listData)},
						dataType:'json',
						cache: false,
						success: function(response){
							if(response.code==0){
								$(top.hangge());//关闭加载状态
							    getSelectBillCodeOptions();
								$(gridBase_selector).trigger("reloadGrid");  
								$("#subTitle").tips({
									side:3,
						            msg:'取消汇总成功',
						            bg:'#009933',
						            time:3
						        });
							}else{
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'取消汇总失败,'+response.message,
						            bg:'#cc0033',
						            time:3
						        });
							}
						},
				    	error: function(response) {
							$(top.hangge());//关闭加载状态
							$("#subTitle").tips({
								side:3,
					            msg:'取消汇总出错:'+response.responseJSON.message,
					            bg:'#cc0033',
					            time:3
					        });
				    	}
					});
				}
            });
		}
    }
</script>
</html>