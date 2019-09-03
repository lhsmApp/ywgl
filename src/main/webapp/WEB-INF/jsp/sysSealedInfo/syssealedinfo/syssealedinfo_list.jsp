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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 ，其中包含旧版本（Ace）Jqgrid Css-->
<%@ include file="../../system/index/topWithJqgrid.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />

<!-- 最新版的Jqgrid Css，如果旧版本（Ace）某些方法不好用，尝试用此版本Css，替换旧版本Css -->
<!-- <link rel="stylesheet" type="text/css" media="screen" href="static/ace/css/ui.jqgrid-bootstrap.css" /> -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!-- <script type="text/javascript" src="static/ace/js/jquery.js"></script> -->
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
						<span class="label label-xlg label-success arrowed-right">东部管道</span>
						<!-- arrowed-in-right -->
						<span
							class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">业务封存</span> <span
							style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGrid'))">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>显示查询</span>
						</button>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box" hidden>
								<div class="widget-body">
									<div class="widget-main">
										<!-- <p class="alert alert-info">Nunc aliquam enim ut arcu.</p> -->
										<form class="form-inline">
											<span class="pull-left" style="margin-right: 5px;"> 
												<select
													class="chosen-select form-control" name="FMISACC"
													id="FMISACC" data-placeholder="请选择帐套"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择帐套</option>
														<c:forEach items="${fmisacc}" var="fmi">
															<option value="${fmi.DICT_CODE}">${fmi.NAME}</option>
														</c:forEach>
												</select>
											</span> 
											<span  class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
												<div class="selectTree" id="selectTree" multiMode="true"
													allSelectable="false" noGroup="false"></div>
												<input type="text" id="RPT_DEPT" hidden></input>
											 </span>
											<span class="pull-left" style="margin-right: 5px;"> 
												<select
													class="chosen-select form-control" name="BILL_TYPE"
													id="BILL_TYPE" data-placeholder="请选择类型"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择类型</option>
														<c:forEach items="${billTypeList}" var="billType">
															<option value="${billType.nameKey}"
																<c:if test="${pd.BILL_TYPE==billType.nameKey}">selected</c:if>>${billType.nameValue}</option>
														</c:forEach>
												</select>
											</span>
											
											<!-- <span style="margin-right: 5px;"> 
												<select class="chosen-select form-control"
													name="STATUS" id="STATUS" data-placeholder="请选状态"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择状态</option>
														<option value="0">解封</option>
														<option value="1">封存</option>
												</select>
											</span> -->
											<button type="button" class="btn btn-info btn-sm"
												onclick="tosearch();">
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

	<script type="text/javascript"> 
	//var gridHeight=155;
	$(document).ready(function () { 
		$(top.hangge());//关闭加载状态
		 
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$("#jqGrid").jqGrid( 'setGridWidth', $(".page-content").width());
			//$("#jqGrid").jqGrid( 'setGridHeight', $(window).height() - 160);
			resizeGridHeight($("#jqGrid"));
			$(".ui-jqgrid-btable").removeAttr("style");
	    })
		
		$("#jqGrid").jqGrid({
			url: '<%=basePath%>syssealedinfo/getPageList.do',
			datatype: "json",
			 colModel: [
				{label: ' ',name:'myac',index:'', width:70, fixed:true, sortable:false, resize:false,
					formatter:'actions', 
					formatoptions:{ 
					 onEdit:function(rowid){
							 var curRow= $("tr[id="+rowid+"]");
							 var curCol=curRow.find("td[aria-describedby='jqGrid_STATE']");
							 if(curCol.attr('title')=='解封'){
								 var cur=$("#jSaveButton_"+rowid);
								 cur.find("span").css('display','none');
							 }
						},
                        onSuccess: function(response) {
                        	//console.log(response.responseText.code);
                        	var code=JSON.parse(response.responseText);
							if(code.code==0){
								$(".tooltip").remove();
								$("#jqGrid").trigger("reloadGrid");
								return [true];
							}else{
								/* console.log(code.code);
								console.log(code.message);
								$("#subTitle").tips({
									side : 3,
									msg : '保存成功',
									bg : '#009933',
									time : 3
								}); */
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
                        	/* $("#jqGrid").trigger("reloadGrid"); */
                        	
                        },
						keys:true,
					    delbutton: false,//disable delete button
					}
				},
				
				{ label: '单据编码',name:'BILL_CODE', width:90,hidden : true,editable: true},
				{ label: '单据单位', name: 'RPT_DEPT', width: 90,hidden : true,editable: true,edittype: 'select',formatter:'select',formatoptions:{value:"${departmentStr}"},editoptions:{value:"${departmentStr}"},stype: 'select',searchoptions:{value:"${departmentStr}"}},
				{ label: '单据期间', name: 'RPT_DUR', width: 60,hidden : true,editable: true,},
				{ label: '单据类型', name: 'BILL_TYPE', width: 60,hidden : true,editable: true,},
				{ label: '帐套', name: 'BILL_OFF', width: 60,hidden : true,editable: true,},
				
				{ label: '单据编码',name:'BILL_CODE', width:90},
				{ label: '单据单位', name: 'RPT_DEPT', width: 90,edittype: 'select',formatter:'select',formatoptions:{value:"${departmentStr}"},editoptions:{value:"${departmentStr}"},stype: 'select',searchoptions:{value:"${departmentStr}"}},
				{ label: '单据期间', name: 'RPT_DUR', width: 60},
				{ label: '上传人', name: 'RPT_USER', width: 60,edittype: 'select',formatter:'select',formatoptions:{value:"${userStr}"},editoptions:{value:"${userStr}"},stype: 'select',searchoptions:{value:"${userStr}"}},
				{ label: '上传时间', name: 'RPT_DATE', width: 80, formatter: 'data'},
				{ label: '单据类型', name: 'BILL_TYPE', width: 80,edittype: 'select',formatter:'select',formatoptions:{value:"${billTypeStr}"},editoptions:{value:"${billTypeStr}"},stype: 'select',searchoptions:{value:"${billTypeStr}"}}, 
				{ label: '帐套', name: 'BILL_OFF', width: 80,edittype: 'select',formatter:'select',formatoptions:{value:"${billOffStr}"},editoptions:{value:"${billOffStr}"},stype: 'select',searchoptions:{value:"${billOffStr}"}},
				{ label: '状态', name: 'STATE', width: 80, editable: true,align:'center',formatter: customFmatterState,edittype:"checkbox",editoptions: {value:"0:1"},unformat: aceSwitch,search:false}                   
			],
			reloadAfterSubmit: true, 
			viewrecords: true, // show the current page, data rang and total records on the toolbar
			rowNum: 100,
			rowList: [100,200,500],
			sortname: 'BILL_CODE',
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
			rownumbers: true, // show row numbers
            rownumWidth: 35, // the width of the row numbers columns			
			/* multiselect: true,
	        multiboxonly: true, */
	        editurl: "<%=basePath%>syssealedinfo/edit.do",//nothing is save
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
			{
				//删除
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
	
		/* // 批量编辑
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
            buttonicon: "ace-icon fa fa-save green",
            title: "批量保存",
            caption: "",
            position: "last",
            onClickButton: batchSave
        }); */
 	});

	//switch element when editing inline
	function aceSwitch( cellvalue, options, cell ) {
		setTimeout(function(){
			 $(cell).find('input[type=checkbox]')
				.addClass('ace ace-switch ace-switch-5')
				.after('<span class="lbl" data-lbl="封存        解封"></span>'); 
			 if (cellvalue=="解封") {	
				$(cell).find('input[type=checkbox]').attr('checked','checked');
				$(cell).find('input[type=checkbox]').attr('disabled','true');
			 }else{
			 	$(cell).find('input[type=checkbox]').removeAttr('checked');
			 	$(cell).find('input[type=checkbox]').attr('disabled');
			 }
		}, 0);
		if (cellvalue=="封存") {
			return 1;
		} else {
			return 0;
		} 
	}
	
	 //批量编辑
	function batchEdit(e) {
		var grid = $("#jqGrid");
        var ids = grid.jqGrid('getDataIDs');
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
		console.log(ids);
		//遍历访问这个集合  
		var rowData;
		$(ids).each(function (index, id){  
            $("#jqGrid").saveRow(id, false, 'clientArray');
             rowData = $("#jqGrid").getRowData(id);
            listData.push(rowData);
		});
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>syssealedinfo/updateAll.do?',
				//data: rowData,//可以单独传入一个对象，后台可以直接通过对应模型接受参数。但是传入Array（listData）就不好用了，所以传list方式需将List转为Json字符窜。
				//data: '{"rows":listData}',
				data : {
					DATA_ROWS : JSON.stringify(listData)
				},
				dataType : 'json',
				cache : false,
				success : function(response) {
					if (response.code == 0) {
						$("#jqGrid").trigger("reloadGrid");
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side : 3,
							msg : '保存成功',
							bg : '#009933',
							time : 3
						});
					} else {
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side : 3,
							msg : '保存失败,' + response.message,
							bg : '#cc0033',
							time : 3
						});
					}
				},
				error : function(e) {
					$(top.hangge());//关闭加载状态
				}
			});
		}
	
		//检索
		function tosearch() {
			console.log($("#RPT_DEPT").val());
			var RPT_DEPT = $("#RPT_DEPT").val();
			//var STATUS = $("#STATUS").val();
			var BILL_TYPE = $("#BILL_TYPE").val();
			var BILL_OFF=$("#FMISACC").val();
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
				url:'<%=basePath%>syssealedinfo/getPageList.do?RPT_DEPT='
										+ RPT_DEPT
										//+ '&STATUS='+ STATUS
										+ '&BILL_TYPE=' + BILL_TYPE
										+ '&BILL_OFF=' + BILL_OFF,
								datatype : 'json',
								page : 1
							}).trigger("reloadGrid");
		}

		function customFmatterState(cellvalue, options, rowObject) {
			if (cellvalue == 1) {
				return '<span class="label label-important arrowed-in">封存</span>';
			} else {
				return '<span class="label label-success arrowed">解封</span>';
			}
		};
		
		//加载单位树
		function initComplete(){
			//下拉树
			var defaultNodes = {"treeNodes":${zTreeNodes}};
			//绑定change事件
			$("#selectTree").bind("change",function(){
				if($(this).attr("relValue")){
					$("#RPT_DEPT").val($(this).attr("relValue"));
			    }else{
			    	$("#RPT_DEPT").val("");
			    }
			});
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			$("#selectTree2_input").val("请选择单位");
		}
	</script>
</body>
</html>