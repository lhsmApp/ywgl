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
						<span class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">凭证单据状态查询</span> 
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
						</div> -->
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
													style="vertical-align: top; height:32px;width: 150px;">
												</select>
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

//页面显示的数据的查询信息，在tosearch()里赋值
var ShowDataTypeCode = "";
var ShowDataCustCol7 = "";
var ShowDataDepartCode = "";
var ShowDataBusiDate = "";
//单号下拉列表
var SelectedTypeCodeFirstShow;
var InitSelectedTypeCodeOptions;
var SelectedDepartCodeFirstShow;
var InitSelectedDepartCodeOptions;

$(document).ready(function () {
	$(top.hangge());//关闭加载状态
	$('.input-mask-date').mask('999999');
    
	//当前期间,取自tb_system_config的SystemDateTime字段
    var SystemDateTime = '${SystemDateTime}';
	$("#SelectedBusiDate").val(SystemDateTime);
    
	//选择下拉列表
    SelectedTypeCodeFirstShow =  "${pd.SelectedTypeCodeFirstShow}";
    InitSelectedTypeCodeOptions =  "${pd.InitSelectedTypeCodeOptions}";
    SelectedDepartCodeFirstShow =  "${pd.SelectedDepartCodeFirstShow}";
    InitSelectedDepartCodeOptions =  "${pd.InitSelectedDepartCodeOptions}";
	setSelectTypeCodeOptions(InitSelectedTypeCodeOptions);
	setSelectDepartCodeOptions(InitSelectedDepartCodeOptions);
	 
	//resize to fit page size
	$(window).on('resize.jqGrid', function () {
		$(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
		resizeGridHeight($(gridBase_selector),null,true);
    })

	$(gridBase_selector).jqGrid({
		url: '<%=basePath%>fundsconfirminfo/getPageList.do?SelectedCustCol7='+ShowDataCustCol7
            +'&SelectedTypeCode='+ShowDataTypeCode
            +'&SelectedDepartCode='+ShowDataDepartCode
            +'&SelectedBusiDate='+ShowDataBusiDate,
		datatype: "json",
		colModel:  [{ label: '账套', name: 'BILL_OFF', width: 60,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${billOffStrSelect}"},formatoptions:{value:"${billOffStrSelect}"},stype: 'select',searchoptions:{value:"${billOffStrAll}"}},
					{ label: '业务类型', name: 'TYPE_CODE', width: 60,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${typeCodeStrSelect}"},formatoptions:{value:"${typeCodeStrSelect}"},stype: 'select',searchoptions:{value:"${typeCodeStrAll}"}},
					{ label: '单位', name: 'DEPT_CODE', width: 90,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${departmentStrSelect}"},formatoptions:{value:"${departmentStrSelect}"},stype: 'select',searchoptions:{value:"${departmentStrAll}"}},
					{ label: '单位映射', name: 'MAPPING_CODE', width: 90,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${departmentStrSelect}"},formatoptions:{value:"${departmentStrSelect}"},stype: 'select',searchoptions:{value:"${departmentStrAll}"}},
					//{ label: '确认类型', name: 'BILL_TYPE', width: 90,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${billTypeStrSelect}"},formatoptions:{value:"${billTypeStrSelect}"},stype: 'select',searchoptions:{value:"${billTypeStrAll}"}},
					{ label: 'FMIS凭证号', name: 'CERT_CODE', width: 90,editable: false},
					{ label: '数据状态', name: 'STATE', width: 90,editable: false, edittype: 'select',formatter:'select',editoptions:{value:"${stateStrSelect}"},formatoptions:{value:"${stateStrSelect}"},stype: 'select',searchoptions:{value:"${stateStrAll}"}}
				],
		viewrecords: true, 
		reloadAfterSubmit: true, 
		rowNum: 0,
		altRows: true, //斑马条纹
		
		pager: pagerBase_selector,
		pgbuttons: false, // 分页按钮是否显示 
		pginput: false, // 是否允许输入分页页数 

        sortable: true,
        sortname: 'MAPPING_CODE',
		sortorder: 'asc',

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
});  

function setSelectTypeCodeOptions(selectOptions){
    $("#SelectedTypeCode").empty();   //先清空
    $("#SelectedTypeCode").append(selectOptions);  //再赋值
}
function setSelectDepartCodeOptions(selectOptions){
    $("#SelectedDepartCode").empty();   //先清空
    $("#SelectedDepartCode").append(selectOptions);  //再赋值
}

function ChangeSelectedCustCol7(){
	setSelectTypeCodeOptions(InitSelectedTypeCodeOptions);
	setSelectDepartCodeOptions(InitSelectedDepartCodeOptions);
	
	var CustCol7 = $("#SelectedCustCol7").val();
	if(CustCol7!=null && $.trim(CustCol7)!=""){
		getSelectTypeCodeOptions();
		getSelectDepartCodeOptions();
	}
}

function ChangeSelectedTypeCode(){
	setSelectDepartCodeOptions(InitSelectedDepartCodeOptions);
	
	var TypeCode = $("#SelectedTypeCode").val();
	if(TypeCode!=null && $.trim(TypeCode)!=""){
		getSelectDepartCodeOptions();
	}
}

function getSelectTypeCodeOptions(){
	setSelectTypeCodeOptions(InitSelectedTypeCodeOptions);
	setSelectDepartCodeOptions(InitSelectedDepartCodeOptions);
	top.jzts();
	$.ajax({
	    type: "POST",
		url: '<%=basePath%>fundsconfirminfo/getTypeCodeList.do?SelectedCustCol7='+$("#SelectedCustCol7").val(),
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
	top.jzts();
	$.ajax({
	    type: "POST",
		url: '<%=basePath%>fundsconfirminfo/getDepartCodeList.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
				+'&SelectedTypeCode='+$("#SelectedTypeCode").val(),
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

//检索
function tosearch() {
	ShowDataTypeCode = $("#SelectedTypeCode").val();
	ShowDataCustCol7 = $("#SelectedCustCol7").val();
	ShowDataDepartCode = $("#SelectedDepartCode").val();
	ShowDataBusiDate = $("#SelectedBusiDate").val(); 

	$(gridBase_selector).jqGrid('setGridParam',{  // 重新加载数据
		url:'<%=basePath%>fundsconfirminfo/getPageList.do?SelectedCustCol7='+ShowDataCustCol7
        +'&SelectedTypeCode='+ShowDataTypeCode
        +'&SelectedDepartCode='+ShowDataDepartCode
        +'&SelectedBusiDate='+ShowDataBusiDate,
						datatype : 'json'
					}).trigger("reloadGrid");
}

</script>
</html>