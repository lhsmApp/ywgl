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
<!-- 树形下拉框start -->
<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
<link rel="stylesheet" type="text/css"
	href="plugins/selectZtree/import_fh.css" />
<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet"
	href="plugins/selectZtree/ztree/ztree.css"></link>
<!-- 树形下拉框end -->
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
									class="label label-xlg label-success arrowed-right">考核管理</span>
									<!-- arrowed-in-right --> <span
									class="label label-xlg label-yellow arrowed-in arrowed-right"
									id="subTitle" style="margin-left: 2px;">计算公式</span> <span
									style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>

									<button id="btnQuery" class="btn btn-white btn-info btn-sm"
										onclick="showQueryCondi($('#jqGrid'))">
										<i class="ace-icon fa fa-chevron-up bigger-120 blue"></i> <span>隐藏查询</span>
									</button>

									<!-- <button id="btnCopy" class="btn btn-white btn-info btn-sm" onclick="copyData()">
										<i class="ace-icon fa  fa-exchange  bigger-120 blue"></i><span>复制</span>
									</button> --></td>
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
											<input name="DEPARTMENT_CODE" id="DEPARTMENT_CODE"
												type="hidden" value="${pd.DEPARTMENT_CODE }" /> <input
												name="DNAME" id="DNAME" type="hidden" value="${pd.DNAME }" />
											<span class="pull-left" style="margin-right: 5px;"> 
												<select class="chosen-select form-control"
													name="TABLE_NO" id=TABLE_NO data-placeholder="请选择配置表"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择配置表</option>
														<c:forEach items="${listBase}" var="tableName">
															<option value="${tableName.TABLE_NO}"
																<c:if test="${pd.TABLE_NO==tableName.TABLE_NO}">selected</c:if>>${tableName.TABLE_NAME  }</option>
														</c:forEach>
												</select>
											</span>
											
											<span class="pull-left" style="margin-right: 5px;"> 
												<%-- <select
													class="chosen-select form-control" name="BILL_OFF"
													id="BILL_OFF" data-placeholder="请选择帐套"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择帐套</option>
														<c:forEach items="${fmisacc}" var="fmi">
															<option value="${fmi.DICT_CODE}">${fmi.NAME}</option>
														</c:forEach>
												</select> --%>
												<input id="BILL_OFF" value="9870" class="hidden" type="text">
											</span>
											<span class="pull-left" style="margin-right: 5px;">
												<div class="selectTree" id="selectTree" multiMode="false"
													allSelectable="false" noGroup="false"></div>
											</span>
											<span class="input-icon pull-left" style="margin-right: 5px;">
												<input id="busiDate" class="input-mask-date" type="text"
												placeholder="请输入业务区间"> <i
												class="ace-icon fa fa-calendar blue"></i>
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
	
	$(document).ready(function () {
		
		$(top.hangge());//关闭加载状态
		$('.input-mask-date').mask('999999');
		$("#busiDate").val('${pd.busiDate}');
		
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$("#jqGrid").jqGrid( 'setGridWidth', $(".page-content").width());
			//$("#jqGrid").jqGrid( 'setGridHeight', $(window).height() - 230);
			resizeGridHeight($("#jqGrid"));
	    })
		
		$("#jqGrid").jqGrid({
			//url: '<%=basePath%>calculateFormula/getPageList.do',
			datatype: "json",
			 colModel: [
				{
					label: ' ',name:'myac',index:'', width:70, fixed:true, sortable:false, resize:false,
					formatter:'actions', 
					formatoptions:{ 
				       onSuccess: function(response) {
				    	console.log(response);
				       	//console.log(response.responseText.code);
				       	var code=JSON.parse(response.responseText);
							if(code.code==0){
								$(".tooltip").remove();
								//$("#jqGrid").trigger("reloadGrid");
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
				       },
				       keys:true,
					   delbutton: false,//disable delete button
					}
				},
				//隐藏where条件
				{ label: '期间', name: 'RPT_DUR', width: 60,hidden : true,editable: true,},
				/* { label: '帐套', name: 'BILL_OFF', width: 60,hidden : true,editable: true,}, */
				{ label: '单位编码', name: 'DEPT_CODE', width: 60,hidden : true,editable: true,},
				{ label: '表编码', name: 'TABLE_CODE', width: 60,hidden : true,editable: true,},
				{ label: '列编码', name: 'COL_CODE', width: 60,hidden : true,editable: true,},
				
				{ label: '业务期间',name:'RPT_DUR', width:60}, 
				/* { label: '帐套', name: 'BILL_OFF', width: 80,align:'center',editable: true,edittype: 'select',formatter:'select',formatteroptions:{value:"${dictBillOffString}"},editoptions:{value:"${dictBillOffString}"}}, */
				{ label: '单位',name:'DNAME', width:100}, 
				{ label: '表名', name: 'TABLE_NAME', width: 90},
				{ label: '列编码', name: 'COL_CODE', width: 60},
				{ label: '列名称', name: 'COL_NAME', width: 60,editable: false,},
				
				{ label: '计算公式', name: 'COL_FORMULA', width: 150,editable: true,},
				{ label: '计算顺序', name: 'CAL_ORDER', width: 80, sorttype: 'int',editable: true,editrules:{number: true}},                 
				{ label: '长度', name: 'NUM_DGT', width: 80, sorttype: 'int',editable: true,editrules:{number: true}},
				{ label: '精度', name: 'DEC_PRECISION', width: 80, sorttype: 'int',editable: true,editrules:{number: true}}
			],
			reloadAfterSubmit: true, 
			//viewrecords: true, // show the current page, data rang and total records on the toolbar
			rowNum: 1000,
			sortname: 'DISP_ORDER',
			pager: "#jqGridPager",
			pgbuttons: false,//上下按钮 
			pginput:false,//输入框
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
			rownumbers: true, // show row numbers
            rownumWidth: 35, // the width of the row numbers columns			
	        //ondblClickRow: dbClickRow,//双击表格编辑
	        editurl: '<%=basePath%>calculateFormula/edit.do',
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
				search: false,
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
                //afterSubmit: fn_addSubmit_extend
			},
			{	id: "del",
				recreateForm: true,
				beforeShowForm : beforeDeleteCallback,
				onClick : function(e) {}
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
	
	function initComplete(){
		//下拉树
		var defaultNodes = {"treeNodes":${zTreeNodes}};
		//绑定change事件
		$("#selectTree").bind("change",function(){
			if($(this).attr("relValue")){
				$("#DEPARTMENT_CODE").val($(this).attr("relValue"));
				$("#DNAME").val($(this).attr("relText"));
		    }
		});
		//赋给data属性
		$("#selectTree").data("data",defaultNodes);  
		$("#selectTree").render();
		console.log('${pd.rootDepartName}');
		$("#selectTree2_input").val('${pd.rootDepartName}');
		document.getElementById("DEPARTMENT_CODE").value='${pd.rootDepartCode}'; 
		document.getElementById("DNAME").value='${pd.rootDepartName}'; //总部
	}
	
	//检索
	function tosearch() {
		if($("#TABLE_NO").val()==""){
			$("#TABLE_NO").tips({
				side:3,
	            msg:'请选择配置表',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TABLE_NO").focus();
			return false;
		}
		if($("#BILL_OFF").val()==""){
			$("#BILL_OFF").tips({
				side:3,
	            msg:'请选择帐套',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BILL_OFF").focus();
			return false;
		}
		/* if($("#selectTree2_input").val()=="请选择"){
			document.getElementById("DEPARTMENT_CODE").value="001"; 
			document.getElementById("DNAME").value="总部"; 
		} */
		var TABLE_NO = $("#TABLE_NO").val(); 
		var fmi = $("#BILL_OFF").val(); 
		var DEPARTMENT_CODE = $("#DEPARTMENT_CODE").val(); 
		var TABLE_NAME = $("#TABLE_NO").find("option:selected").text();
		var DNAME = $("#DNAME").val(); 
		var busiDate = $("#busiDate").val(); 
		$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			url:'<%=basePath%>calculateFormula/getPageList.do?TABLE_NO='+TABLE_NO
			+'&BILL_OFF='+fmi
			+'&DEPARTMENT_CODE='+DEPARTMENT_CODE
			+'&RPT_DUR='+busiDate+'&TABLE_NAME='+TABLE_NAME+'&DNAME='+DNAME,  
			datatype:'json',
		      page:1
		}).trigger("reloadGrid");
		
	}  
	
	var lastSelection;
	function dbClickRow(rowId, rowIndex, colnumIndex, event){ 
		//if (rowId && rowId !== lastSelection) {
              var grid = $("#jqGrid");
              //grid.jqGrid('saveRow',lastSelection);
              grid.jqGrid('saveRow',lastSelection,false, 'clientArray');
              grid.jqGrid('restoreRow',lastSelection);
              grid.jqGrid('editRow',rowId, {keys: true} );
              lastSelection = rowId;
        //}
	}
 	</script>
</body>
</html>