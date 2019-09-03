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
							id="subTitle" style="margin-left: 2px;">组织机构分线关系 </span> <span
							style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGrid'),null,true)">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>显示查询</span>
						</button>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box" hidden>
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<span style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedCustCol7" id="SelectedCustCol7"
													data-placeholder="请选择帐套"
													style="vertical-align: top; height:32px;width: 150px;">
													<option value="">请选择帐套</option>
													<c:forEach items="${FMISACC}" var="each">
														<option value="${each.DICT_CODE}">${each.NAME}</option>
													</c:forEach>
												</select>
											</span>
											<span style="margin-right: 5px;">
												<div class="selectTree" id="selectTree" multiMode="true"
													allSelectable="false" noGroup="true"></div>
												<input type="text" id="SelectedDepartCode" hidden></input>
											 </span> 
											<span style="margin-right: 5px;"> 
												<select
													class="chosen-select form-control" name="SelectedfxCode"
													id="SelectedfxCode" data-placeholder="请选择分线"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择分线</option>
														<c:forEach items="${fxList}" var="fx">
															<option value="${fx.DICT_CODE}">${fx.NAME}</option>
														</c:forEach>
												</select>
											</span>
											<span style="margin-right: 5px;"> 
												<select class="chosen-select form-control"
													name="SelectedstateCode" id="SelectedstateCode" data-placeholder="请选状态"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择状态</option>
														<option value="0">停用</option>
														<option value="1">启用</option>
												</select>
											</span>
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
	$(document).ready(function () { 
		$(top.hangge());//关闭加载状态
		 
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$("#jqGrid").jqGrid( 'setGridWidth', $(".page-content").width());
			resizeGridHeight($("#jqGrid"),null,true);
	    })
		
		$("#jqGrid").jqGrid({
			url: '<%=basePath%>glZrzxFx/getPageList.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
                    + '&SelectedDepartCode=' + $("#SelectedDepartCode").val()
		        	+ '&SelectedfxCode=' + $("#SelectedfxCode").val()
			        + '&SelectedstateCode=' + $("#SelectedstateCode").val(),
			datatype: "json",
			 colModel: [
				{label: ' ',name:'myac',index:'', width:70, fixed:true, sortable:false, resize:false,
					formatter:'actions', 
					formatoptions:{ 
					 onEdit:function(rowid){
							 //var curRow= $("tr[id="+rowid+"]");
							 //var curCol=curRow.find("td[aria-describedby='jqGrid_STATE']");
							 //if(curCol.attr('title')=='停用'){
							 //	 var cur=$("#jSaveButton_"+rowid);
							 //	 cur.find("span").css('display','none');
							 //}
						},
                        onSuccess: function(response) {
                        	var code=JSON.parse(response.responseText);
							if(code.code==0){
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
                        	/* $("#jqGrid").trigger("reloadGrid"); */
                        	
                        },
						keys:true,
					    delbutton: false,//disable delete button
					}
				},

				{ label: '账套', name: 'BILL_OFF__', width: 60,hidden : true,editable: true,},
				{ label: '责任中心',name:'DEPT_CODE__', width:90,hidden : true,editable: true},
				{ label: '分线', name: 'LINE_NO__', width: 60,hidden : true,editable: true,},

				{ label: '账套', name: 'BILL_OFF', width: 60,editable: true,edittype: 'select',formatter:'select',formatoptions:{value:"${billOffStrSelect}"},editoptions:{value:"${billOffStrSelect}"},stype: 'select',searchoptions:{value:"${billOffStrAll}"}},
				{ label: '责任中心', name: 'DEPT_CODE', width: 90,editable: true,edittype: 'select',formatter:'select',formatoptions:{value:"${departmentStrSelect}"},editoptions:{value:"${departmentStrSelect}"},stype: 'select',searchoptions:{value:"${departmentStrAll}"}},
				{ label: '分线', name: 'LINE_NO', width: 60,editable: true,edittype: 'select',formatter:'select',formatoptions:{value:"${lineNoStrSelect}"},editoptions:{value:"${lineNoStrSelect}"},stype: 'select',searchoptions:{value:"${lineNoStrAll}"}},
				{ label: '状态', name: 'STATE', width: 80, editable: true,align:'center',formatter: customFmatterState,edittype:"checkbox",editoptions: {value:"0:1"},unformat: aceSwitch,search:false}                   
			],
			reloadAfterSubmit: true, 
			viewrecords: true,
			rowNum: 100,
			rowList: [100,200,500],
            multiSort: true,
			sortname: 'BILL_OFF,DEPT_CODE,LINE_NO',
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
			rownumbers: true, 
            rownumWidth: 35,		
			/* multiselect: true,
	        multiboxonly: true, */
	        editurl: '<%=basePath%>glZrzxFx/save.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
            + '&SelectedDepartCode=' + $("#SelectedDepartCode").val()
        	+ '&SelectedfxCode=' + $("#SelectedfxCode").val()
	        + '&SelectedstateCode=' + $("#SelectedstateCode").val(),
		});
		
		$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
		//navButtons
		jQuery("#jqGrid").jqGrid('navGrid',"#jqGridPager",
			{ 	//navbar options
				edit: false,
				editicon : 'ace-icon fa fa-pencil blue',
				add: true,
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
				recreateForm: true,
				beforeShowForm :beforeEditOrAddCallback
			},
			 {
				//new record form
			    id: "add",
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
                afterSubmit: fn_addSubmit_extend
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
 	});

	//switch element when editing inline
	function aceSwitch( cellvalue, options, cell ) {
		setTimeout(function(){
			 $(cell).find('input[type=checkbox]')
				.addClass('ace ace-switch ace-switch-5')
				.after('<span class="lbl" data-lbl="启用        停用"></span>'); 
			 if (cellvalue=="停用") {	
				$(cell).find('input[type=checkbox]').attr('checked','checked');
				$(cell).find('input[type=checkbox]').attr('disabled','true');
			 }else{
			 	$(cell).find('input[type=checkbox]').removeAttr('checked');
			 	$(cell).find('input[type=checkbox]').attr('disabled');
			 }
		}, 0);
		if (cellvalue=="启用") {
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
			url: '<%=basePath%>glZrzxFx/updateAll.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
            + '&SelectedDepartCode=' + $("#SelectedDepartCode").val()
        	+ '&SelectedfxCode=' + $("#SelectedfxCode").val()
	        + '&SelectedstateCode=' + $("#SelectedstateCode").val(),
				data:{UpdataDataRows : JSON.stringify(listData)},
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
	
		//检索
		function tosearch() {
			$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
				url:'<%=basePath%>glZrzxFx/getPageList.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
                                        + '&SelectedDepartCode=' + $("#SelectedDepartCode").val()
										+ '&SelectedfxCode=' + $("#SelectedfxCode").val()
										+ '&SelectedstateCode=' + $("#SelectedstateCode").val(),
				editurl: '<%=basePath%>glZrzxFx/save.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
							            + '&SelectedDepartCode=' + $("#SelectedDepartCode").val()
							        	+ '&SelectedfxCode=' + $("#SelectedfxCode").val()
								        + '&SelectedstateCode=' + $("#SelectedstateCode").val()
							}).trigger("reloadGrid");
		}

		function customFmatterState(cellvalue, options, rowObject) {
			if (cellvalue == 1) {
				return '<span class="label label-important arrowed-in">启用</span>';
			} else {
				return '<span class="label label-success arrowed">停用</span>';
			}
		};
		
		//加载单位树
		function initComplete(){
			//下拉树
			var defaultNodes = {"treeNodes":${zTreeNodes}};
			//绑定change事件
			$("#selectTree").bind("change",function(){
				if($(this).attr("relValue")){
					$("#SelectedDepartCode").val($(this).attr("relValue"));
			    }else{
			    	$("#SelectedDepartCode").val("");
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