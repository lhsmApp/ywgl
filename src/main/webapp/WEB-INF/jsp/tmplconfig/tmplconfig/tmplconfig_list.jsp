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
<!-- <script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
<link rel="stylesheet" type="text/css"
	href="plugins/selectZtree/import_fh.css" />
<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet"
	href="plugins/selectZtree/ztree/ztree.css"></link> -->
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
									id="subTitle" style="margin-left: 2px;">指标维护</span> <span
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
													name="KPI_CODE" id="KPI_CODE" data-placeholder="请选择配置表"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择指标</option>
														<c:forEach items="${listBase}" var="kpi">
															<option value="${kpi.KPI_CODE}"
																<c:if test="${pd.KPI_CODE==kpi.KPI_CODE}">selected</c:if>>${kpi.KPI_NAME  }</option>
														</c:forEach>
												</select>
											</span>
											<span class="pull-left" style="margin-right: 5px;"> 
												<input id="BILL_OFF" value="9870" class="hidden" type="text">
											</span>
											<!-- <span class="pull-left" style="margin-right: 5px;">
												<div class="selectTree" id="selectTree" multiMode="false"
													allSelectable="false" noGroup="false"></div>
											</span> -->
											<!-- <span class="input-icon pull-left" style="margin-right: 5px;">
												<input id="busiDate" class="input-mask-date" type="text"
												placeholder="请输入业务区间"> <i
												class="ace-icon fa fa-calendar blue"></i>
											</span> -->
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
		/* $("#busiDate").val('${pd.busiDate}'); */
		
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$("#jqGrid").jqGrid( 'setGridWidth', $(".page-content").width());
			//$("#jqGrid").jqGrid( 'setGridHeight', $(window).height() - 230);
			resizeGridHeight($("#jqGrid"));
	    })
		
		$("#jqGrid").jqGrid({
			//url: '<%=basePath%>tmplconfig/getPageList.do',
			datatype: "json",
			 colModel: [
				//隐藏where条件
				/* { label: '期间', name: 'RPT_DUR', width: 60,hidden : true,editable: true,}, */
				/* { label: '帐套', name: 'BILL_OFF', width: 60,hidden : true,editable: true,}, */
				/* { label: '单位编码', name: 'DEPT_CODE', width: 60,hidden : true,editable: true,}, */
				{ label: '表编码', name: 'TABLE_CODE', width: 60,hidden : true,editable: true,},
				{ label: '列编码', name: 'COL_CODE', width: 60,hidden : true,editable: true,},
				
				/* { label: '业务期间',name:'RPT_DUR', width:60},  */
				/* { label: '帐套', name: 'BILL_OFF', width: 80,align:'center',editable: true,edittype: 'select',formatter:'select',formatteroptions:{value:"${dictBillOffString}"},editoptions:{value:"${dictBillOffString}"}}, */
				/* { label: '单位',name:'DNAME', width:100}, */ 
				{ label: '表名', name: 'TABLE_NAME', width: 90},
				{ label: '列编码', name: 'COL_CODE', width: 60},
				{ label: '列名称', name: 'COL_NAME', width: 60,editable: true,},
				{ label: '显示序号', name: 'DISP_ORDER', width: 80,formatter: 'int', sorttype: 'number',editable: true,},
				/* { label: '字典翻译', name: 'DICT_TRANS', width: 80,align:'center',editable: true,edittype: 'select',formatter:'select',formatteroptions:{value:"${dictString}"},editoptions:{value:"${dictString}"}},  */                
				{ label: '列显示', hidden : false,name: 'COL_HIDE', width: 80, editable: true,align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState},                   
				{ label: '列汇总', hidden : true,name: 'COL_SUM', width: 80, editable: true,align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState},                   
				{ label: '列平均值', hidden : true,name: 'COL_AVE', width: 80, editable: true,align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState} 
				/* { label: '是否传输', name: 'COL_TRANSFER', width: 80, editable: true,align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState} */
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
	        ondblClickRow: dbClickRow,//双击表格编辑
	        //editurl: '<%=basePath%>tmplconfig/edit.do?',
	        //editurl: '',
			
			editurl: '<%=basePath%>tmplconfig/edit.do?',
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
				//edit record formx
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
                afterSubmit: fn_addSubmit
			},
			{},
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

		// 批量编辑
        $('#jqGrid').navButtonAdd('#jqGridPager',
        {
            buttonicon: "ace-icon fa fa-pencil-square-o purple",
            title: "批量编辑",
            caption: "",
            position: "last",
            onClickButton: batchEdit
        });
		
     	// 取消批量编辑
        $('#jqGrid').navButtonAdd('#jqGridPager',
        {
            buttonicon: "ace-icon fa fa-undo",
            title: "取消批量编辑",
            caption: "",
            position: "last",
            onClickButton: batchCancelEdit
        });

        //批量保存
        $('#jqGrid').navButtonAdd('#jqGridPager',
        {
     	   /* bigger-150 */
            buttonicon: "ace-icon fa fa-save green",
            title: "批量保存",
            caption: "",
            position: "last",
            onClickButton: batchSave
        });
 	});

	 //批量编辑
	function batchEdit(e) {
		var grid = $("#jqGrid");
        var ids = grid.jqGrid('getDataIDs');
        console.log("批量编辑"+ids);
        for (var i = 0; i < ids.length; i++) {
            grid.jqGrid('editRow',ids[i]);
        }
    }
	
	//取消批量编辑
	function batchCancelEdit(e) {
		var grid = $("#jqGrid");
        var ids = grid.jqGrid('getDataIDs');
        for (var i = 0; i < ids.length; i++) {
            grid.jqGrid('restoreRow',ids[i]);
        }
    }
	
	//批量保存
	function batchSave(e) {
		 var listData =new Array();
		var ids = $("#jqGrid").jqGrid('getDataIDs');
		//console.log("ids"+ids);
		//遍历访问这个集合  
		var rowData;
		$(ids).each(function (index, id){  
			console.log("index"+index);
			console.log("id"+id);

           $("#jqGrid").saveRow(id, false, 'clientArray');
            
             rowData = $("#jqGrid").getRowData(id);
     		console.log("rowData"+rowData);
            listData.push(rowData);
		}); 
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>tmplconfig/updateAll.do?',
	    	//data: rowData,//可以单独传入一个对象，后台可以直接通过对应模型接受参数。但是传入Array（listData）就不好用了，所以传list方式需将List转为Json字符窜。
			//data: '{"rows":listData}',
			data:{DATA_ROWS:JSON.stringify(listData)},
	    	dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$("#jqGrid").trigger("reloadGrid");  
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'保存成功',
			            bg:'#009933',
			            time:3
			        });
				}else{
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
				$(top.hangge());//关闭加载状态
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
	
	/* function initComplete(){
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
	} */
	
	//检索
	function tosearch() {
		if($("#KPI_CODE").val()==""){
			$("#KPI_CODE").tips({
				side:3,
	            msg:'请选择配置表',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#KPI_CODE").focus();
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
		var KPI_CODE = $("#KPI_CODE").val(); 
		var fmi = $("#BILL_OFF").val(); 
		var DEPARTMENT_CODE = $("#DEPARTMENT_CODE").val(); 
		var TABLE_NAME = $("#KPI_CODE").find("option:selected").text();
		var DNAME = $("#DNAME").val(); 
		/* var busiDate = $("#busiDate").val();  */
		$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			url:'<%=basePath%>tmplconfig/getPageList.do?KPI_CODE='+KPI_CODE
			+'&BILL_OFF='+fmi
			+'&DEPARTMENT_CODE='+DEPARTMENT_CODE
			/* +'&RPT_DUR='+busiDate */
			+'&TABLE_NAME='+TABLE_NAME+'&DNAME='+DNAME,  
			datatype:'json',
		      page:1
		}).trigger("reloadGrid");
		
	}  
	
	//复制
    function copyData() {
    	if($("#KPI_CODE").val()==""){
			$("#KPI_CODE").tips({
				side:3,
	            msg:'请选择配置表',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#KPI_CODE").focus();
			return false;
		}
       	var KPI_CODE = $("#KPI_CODE").val(); 
		var DEPARTMENT_CODE = $("#DEPARTMENT_CODE").val(); 
		/* var busiDate = $("#busiDate").val();  */
       	 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag = true;
		 diag.Title = "复制";
		 diag.URL = '<%=basePath%>department/listAllDepartmentCopy.do?KPI_CODE='+KPI_CODE
				 +'&DEPARTMENT_CODE='+DEPARTMENT_CODE
				 /* +'&RPT_DUR='+busiDate */
				 +'&local=tmplconfig';
		 diag.Width = 320;
		 diag.Height = 450;
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
		 };
		 diag.show();
	}
	
	var lastSelection;
	function dbClickRow(rowId, rowIndex, colnumIndex, event){ 
		/*//if (rowId && rowId !== lastSelection) {
              var grid = $("#jqGrid");
              //grid.jqGrid('saveRow',lastSelection);
              grid.jqGrid('saveRow',lastSelection,false, 'clientArray');
              grid.jqGrid('restoreRow',lastSelection);
              grid.jqGrid('editRow',rowId, {keys: true} );
              lastSelection = rowId;
        //}*/
            var grid = $("#jqGrid");
	        grid.restoreRow(lastSelection);
	        grid.editRow(rowId, {
	        	keys:true, //keys:true 这里按[enter]保存  
	            restoreAfterError: false,  
	        	oneditfunc: function(rowId){  
	                console.log(rowId);  
	            },  
	            successfunc: function(response){
	                console.log(response);  
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
						lastSelection = rowId;
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
	            errorfunc: function(rowId, response){
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
	        lastSelection = rowId;
	}
 	</script>
</body>
</html>