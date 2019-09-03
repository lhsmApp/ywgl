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
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 ，其中包含旧版本（Ace）Jqgrid Css-->
<%@ include file="../../system/index/topWithJqgrid.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />

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
						<table>
							<tr>
								<td><span
									class="label label-xlg label-success arrowed-right">东部管道</span>
									<!-- arrowed-in-right --> <span
									class="label label-xlg label-yellow arrowed-in arrowed-right"
									id="subTitle" style="margin-left: 2px;">凭证结构定义</span> <span
									style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>

									<button id="btnQuery" class="btn btn-white btn-info btn-sm"
										onclick="showQueryCondi($('#jqGridBase'))">
										<i class="ace-icon fa fa-chevron-up bigger-120 blue"></i> <span>隐藏查询</span>
									</button>
                                 </td>
							</tr>
						</table>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box">
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedCustCol7" id="SelectedCustCol7" data-placeholder="请选择帐套"
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
												<select class="chosen-select form-control"
													name="SelectedStruMappingTableName" id="SelectedStruMappingTableName" 
													data-placeholder="请选择对应表"
													style="vertical-align: top; height:32px;width: 150px;">
													<option value="">请选择对应表</option>
														<c:forEach items="${MappingTable}" var="each">
															<option value="${each.DICT_CODE}">${each.NAME}</option>
														</c:forEach>
												</select>
											</span>
											<span class="input-icon pull-left" style="margin-right: 5px;">
												<input id="SelectedBusiDate" class="input-mask-date" type="text"
												   placeholder="请输入业务区间"> 
												<i class="ace-icon fa fa-calendar blue"></i>
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

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
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

	<script type="text/javascript"> 
    var gridBase_selector = "#jqGridBase";  
    var pagerBase_selector = "#jqGridBasePager";
    
    //COL_CODE数据源
    //var colCodeStrAll = "";
    //var colCodeStrSelect = "";
	
	//页面显示的数据的查询信息，在tosearch()里赋值
	var ShowDataTypeCode = "";
	var ShowDataCustCol7 = "";
	var ShowDataBusiDate = "";
	var ShowDataStruMappingTableName = "";
	
	$(document).ready(function () {
		$(top.hangge());//关闭加载状态
		$('.input-mask-date').mask('999999');
		//当前期间,取自tb_system_config的SystemDateTime字段
	    var SystemDateTime = '${SystemDateTime}';
		$("#SelectedBusiDate").val(SystemDateTime);
 	});
	
    //双击编辑行
    var lastSelection;
    function doubleClickRow(rowid,iRow,iCol,e){
        var grid = $(gridBase_selector);
        grid.restoreRow(lastSelection);
        grid.editRow(rowid, {
        	keys:true, //keys:true 这里按[enter]保存  
            restoreAfterError: false,  
        	oneditfunc: function(rowid){  
                console.log(rowid);  
            },  
            successfunc: function(response){
		        var responseJSON = JSON.parse(response.responseText);
				if(responseJSON.code==0){
					grid.trigger("reloadGrid");  
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'保存成功',
			            bg:'#009933',
			            time:3
			        });
					lastSelection = rowid;
					return [true,"",""];
				}//else{
		        //   grid.jqGrid('editRow',lastSelection);
				//	$(top.hangge());//关闭加载状态
				//	$("#subTitle").tips({
				//		side:3,
			    //        msg:'保存失败,'+response.responseJSON.message,
			    //        bg:'#cc0033',
			    //        time:3
			    //    });
				//}
            },  
            errorfunc: function(rowid, response){
                var responseJSON = JSON.parse(response.responseText);
	            grid.jqGrid('editRow',lastSelection);
				$(top.hangge());//关闭加载状态
				if(response.statusText == "success"){
					if(responseJSON.code != 0){
				        grid.jqGrid('editRow',lastSelection);
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side:3,
					        msg:'保存失败:'+responseJSON.message,
					        bg:'#cc0033',
					        time:3
					    });
					}
				} else {
					$("#subTitle").tips({
						side:3,
			            msg:'保存出错:'+responseJSON.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
            }  
        });
        lastSelection = rowid;
    } 

	 //批量编辑
	function batchEdit(e) {
		var grid = $(gridBase_selector);
        var ids = grid.jqGrid('getDataIDs');
        console.log("批量编辑"+ids);
        for (var i = 0; i < ids.length; i++) {
            grid.jqGrid('editRow',ids[i]);
        }
    }
	
	//取消批量编辑
	function batchCancelEdit(e) {
		var grid = $(gridBase_selector);
        var ids = grid.jqGrid('getDataIDs');
        for (var i = 0; i < ids.length; i++) {
            grid.jqGrid('restoreRow',ids[i]);
        }
    }
	
	//批量保存
	function batchSave(e) {
		var listData =new Array();
		var ids = $(gridBase_selector).jqGrid('getDataIDs');
		//遍历访问这个集合  
		$(ids).each(function (index, id){  
            $(gridBase_selector).saveRow(id, false, 'clientArray');
            var rowData = $(gridBase_selector).getRowData(id);
     	    console.log("rowData"+rowData);
            listData.push(rowData);
		}); 
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>sysStruMapping/updateAll.do?',
			data:{DataRows:JSON.stringify(listData)},
	    	dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$(gridBase_selector).trigger("reloadGrid");  
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'保存成功',
			            bg:'#009933',
			            time:3
			        });
				}else{
					batchEdit(null);
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'保存失败,'+response.responseJSON.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(e) {
				batchEdit(null);
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'保存出错:'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
		}); 
	 }
		
	//switch element when editing inline
	function aceSwitch( cellvalue, options, cell ) {
		setTimeout(function(){
			 $(cell).find('input[type=checkbox]')
				.addClass('ace ace-switch ace-switch-5')
				.after('<span class="lbl" data-lbl="是             否"></span>'); 
			 if (cellvalue=="是") {	
				$(cell).find('input[type=checkbox]').attr('checked','checked');
			 }else{
			 	$(cell).find('input[type=checkbox]').removeAttr('checked');
			 }
		}, 0);
		if (cellvalue=="是") {
			return 1;
		} else {
			return 0;
		} 
	}
	
	function customFmatterState(cellvalue, options, rowObject){  
		if (cellvalue==1) {
			 return '<span class="label label-important arrowed-in">是</span>';
		} else {
			return '<span class="label label-success arrowed">否</span>';
		}
	};
	
	//检索
	function tosearch() {
		var TypeCode = $("#SelectedTypeCode").val();
		var CustCol7 = $("#SelectedCustCol7").val();
		var BusiDate = $("#SelectedBusiDate").val(); 
		var StruMappingTableName = $("#SelectedStruMappingTableName").val(); 
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
		if(!(StruMappingTableName!=null && $.trim(StruMappingTableName)!="")){
			$("#SelectedStruMappingTableName").tips({
				side:3,
	            msg:'请选择对应表',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SelectedTypeCode").focus();
			return false;
		}
		if(!(BusiDate!=null && $.trim(BusiDate)!="")){
			$("#SelectedBusiDate").tips({
				side:3,
	            msg:'请填写区间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SelectedBusiDate").focus();
			return false;
		}
		ShowDataTypeCode = $("#SelectedTypeCode").val();
		ShowDataCustCol7 = $("#SelectedCustCol7").val();
		ShowDataBusiDate = $("#SelectedBusiDate").val(); 
		ShowDataStruMappingTableName = $("#SelectedStruMappingTableName").val(); 

	    //COL_CODE数据源
	    //colCodeStrAll = "";
	    //colCodeStrSelect = "";
		$(gridBase_selector).jqGrid('GridUnload'); 
		SetStructure();
		
		//top.jzts();
		//$.ajax({
		//	type: "POST",
		//	url: '<%=basePath%>sysStruMapping/getColCodeSource.do?SelectedCustCol7='+ShowDataCustCol7
        //        +'&SelectedTypeCode='+ShowDataTypeCode
       //         +'&SelectedBusiDate='+ShowDataBusiDate
       //         +'&SelectedStruMappingTableName='+ShowDataStruMappingTableName,
	   // 	dataType:'json',
		//	cache: false,
		//	success: function(response){
		//		if(response.code==0){
		//			$(top.hangge());//关闭加载状态
		//		    //COL_CODE数据源
		//		    var ColCodeSource = response.message;
		//		    colCodeStrAll = ":[All];" + ColCodeSource;
		//		    colCodeStrSelect = ":;" + ColCodeSource;
		//			SetStructure();
		//		}else{
		//			$(top.hangge());//关闭加载状态
		//			$("#subTitle").tips({
		//				side:3,
		//	            msg:'获取列数据源失败：'+response.message,
		//	            bg:'#cc0033',
		//	            time:3
		//	        });
		//		}
		//	},
	    //	error: function(response) {
		//		$(top.hangge());//关闭加载状态
		//		$("#subTitle").tips({
		//			side:3,
		//            msg:'获取列数据源出错：'+response.responseJSON.message,
		//            bg:'#cc0033',
		//            time:3
		//        });
	    //	}
		//}); 
	}  
    
    function SetStructure(){
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
			//$(gridBase_selector).jqGrid( 'setGridHeight', $(window).height() - 230);
			resizeGridHeight($(gridBase_selector));
	    })
		
		$(gridBase_selector).jqGrid({
			url: '<%=basePath%>sysStruMapping/getPageList.do?SelectedCustCol7='+ShowDataCustCol7
                +'&SelectedTypeCode='+ShowDataTypeCode
                +'&SelectedBusiDate='+ShowDataBusiDate
                +'&SelectedStruMappingTableName='+ShowDataStruMappingTableName,  
			datatype: "json",
			colModel: [
				//隐藏where条件
				{ label: '列状态', name: 'BILL_STATE__', hidden : true,editable: true,},
				{ label: '业务期间', name: 'BUSI_DATE__', hidden : true,editable: true,},
				{ label: '业务类型', name: 'TYPE_CODE__', hidden : true,editable: true,},
				{ label: '帐套', name: 'BILL_OFF__', hidden : true,editable: true,},
				{ label: '业务表', name: 'TABLE_NAME__', hidden : true,editable: true,},
				{ label: '映射业务表', name: 'TABLE_NAME_MAPPING__', hidden : true,editable: true,},
				{ label: '映射列编码', name: 'COL_MAPPING_CODE__', hidden : true,editable: true,},

				{ label: '列状态', name: 'BILL_STATE', width: 120,editable: false,edittype: 'select',formatter:'select',formatteroptions:{value:"${billStateStrAll}"},editoptions:{value:"${billStateStrSelect}"}},
		        { label: '业务期间', name: 'BUSI_DATE', width: 90,editable: false},
				{ label: '业务类型',name:'TYPE_CODE', width:120,editable: false,edittype: 'select',formatter:'select',formatteroptions:{value:"${typeCodeStrAll}"},editoptions:{value:"${typeCodeStrSelect}"}},
				{ label: '帐套', name: 'BILL_OFF', width: 120,editable: false,edittype: 'select',formatter:'select',formatteroptions:{value:"${billOffStrAll}"},editoptions:{value:"${billOffStrSelect}"}},
				{ label: '业务表', name: 'TABLE_NAME', width: 160,editable: false},
				{ label: '映射业务表', name: 'TABLE_NAME_MAPPING', width: 160,editable: false},
				
				//{ label: '列编码', name: 'COL_CODE', width: 140,editable: true,edittype: 'select',formatter:'select',formatteroptions:{value:colCodeStrAll},editoptions:{value:colCodeStrSelect}},
				{ label: '列编码', name: 'COL_CODE', width: 140,editable: true}, 
				{ label: '映射列编码',name:'COL_MAPPING_CODE', width:140,editable: false}, 
				{ label: '映射名称',name:'COL_MAPPING_NAME', width:140,editable: true}, 
				{ label: '业务表列值',name:'COL_VALUE', width:140,editable: true}, 
				{ label: '业务表条件',name:'COL_MAPPING_VALUE', width:140,editable: true}, 
				{ label: '列位数', name: 'COL_DGT', width: 80,editable: true,formatter: 'int', sorttype: 'number'},
				{ label: '列小数位数', name: 'DEC_PRECISION', width: 80,editable: true,formatter: 'int', sorttype: 'number'},
				{ label: '显示序号', name: 'DISP_ORDER', width: 80,editable: true,formatter: 'int', sorttype: 'number'},
				{ label: '字典翻译', name: 'DICT_TRANS', width: 160,editable: true,edittype: 'select',formatter:'select',formatteroptions:{value:"${dictStrAll}"},editoptions:{value:"${dictStrSelect}"}},                  
				{ label: '列显示', name: 'COL_HIDE', width: 80,editable: true, align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState},                   
				{ label: '列汇总', name: 'COL_SUM', width: 80,editable: true, align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState},                   
				{ label: '列平均值', name: 'COL_AVE', width: 80,editable: true, align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState},
				{ label: '是否传输', name: 'COL_TRANSFER', width: 80, editable: true,align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState},
				{ label: '列启用', name: 'COL_ENABLE', width: 80, editable: true,align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState}
			],
			reloadAfterSubmit: true, 
			//viewrecords: true, // show the current page, data rang and total records on the toolbar
    	    shrinkToFit: false,
			rowNum: 0,
			altRows: true, //斑马条纹,
			sortname: 'DISP_ORDER',
			pager: pagerBase_selector,
			pgbuttons: false,//上下按钮 
			pginput:false,//输入框
			
			rownumbers: true, // show row numbers
            rownumWidth: 35, // the width of the row numbers columns			
	        ondblClickRow: doubleClickRow,//双击表格编辑
	        editurl: '<%=basePath%>sysStruMapping/save.do',
	        
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
		jQuery(gridBase_selector).navGrid(pagerBase_selector,
			{ 	//navbar options
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
			}, { }, { }, { }, { }, { },{ }
		);

		// 批量编辑
        $(gridBase_selector).navButtonAdd(pagerBase_selector,
        {
            buttonicon: "ace-icon fa fa-pencil-square-o purple",
            title: "批量编辑",
            caption: "",
            position: "last",
            onClickButton: batchEdit
        });
		
     	// 取消批量编辑
        $(gridBase_selector).navButtonAdd(pagerBase_selector,
        {
            buttonicon: "ace-icon fa fa-undo",
            title: "取消批量编辑",
            caption: "",
            position: "last",
            onClickButton: batchCancelEdit
        });

        //批量保存
        $(gridBase_selector).navButtonAdd(pagerBase_selector,
        {
            buttonicon: "ace-icon fa fa-save green",
            title: "批量保存",
            caption: "",
            position: "last",
            onClickButton: batchSave
        });
    }
 	</script>
</body>
</html>