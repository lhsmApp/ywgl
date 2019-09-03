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
									class="label label-xlg label-success arrowed-right">东部管道</span>
									<!-- arrowed-in-right --> <span
									class="label label-xlg label-yellow arrowed-in arrowed-right"
									id="subTitle" style="margin-left: 2px;">导入规则</span> <span
									style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>

									<button id="btnQuery" class="btn btn-white btn-info btn-sm"
										onclick="showQueryCondi($('#jqGrid'))">
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
													name="SelectedTableCode" id=SelectedTableCode data-placeholder="请选择配置表"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择配置表</option>
														<c:forEach items="${listBase}" var="tableName">
															<option value="${tableName.TABLE_CODE}"
																<c:if test="${pd.SelectedTableCode==tableName.TABLE_CODE}">selected</c:if>>${tableName.TABLE_NAME}</option>
														</c:forEach>
												</select>
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
											<span class="pull-left" style="margin-right: 5px;">
												<div class="selectTree" id="selectTree" multiMode="false"
													allSelectable="false" noGroup="false"></div>
											    <input name="SelectedDepartCode" id="SelectedDepartCode" type="hidden" value="${pd.SelectedDepartCode}" /> 
												<input name="SelectedDepartName" id="SelectedDepartName" type="hidden" value="${pd.SelectedDepartName}" />
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

	//当前期间,取自tb_system_config的SystemDateTime字段
    var SystemDateTime = '';
	
	$(document).ready(function () {
		$(top.hangge());//关闭加载状态
		SystemDateTime = '${pd.SystemDateTime}';
		
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$("#jqGrid").jqGrid( 'setGridWidth', $(".page-content").width());
			//$("#jqGrid").jqGrid( 'setGridHeight', $(window).height() - 230);
			resizeGridHeight($("#jqGrid"));
	    })
		
		$("#jqGrid").jqGrid({
			url: '<%=basePath%>tmplInputTips/getPageList.do?'
                +'SelectedTableCode='+$("#SelectedTableCode").val()
    	        +'&SelectedCustCol7='+$("#SelectedCustCol7").val()
                +'&SelectedDepartCode='+$("#SelectedDepartCode").val()
                +'&SystemDateTime='+SystemDateTime,
			datatype: "json",
			colModel: [
						//隐藏where条件
						{ label: '期间', name: 'RPT_DUR__', width: 60,hidden : true,editable: true,},
						{ label: '帐套', name: 'BILL_OFF__', width: 60,hidden : true,editable: true,},
						{ label: '单位编码', name: 'DEPT_CODE__', width: 60,hidden : true,editable: true,},
						{ label: '表编码', name: 'TABLE_CODE__', width: 60,hidden : true,editable: true,},
						{ label: '列编码', name: 'COL_CODE__', width: 60,hidden : true,editable: true,},

						{ label: '账套', name: 'BILL_OFF', width: 150,edittype: 'select',formatter:'select',formatoptions:{value:"${billOffStrSelect}"},editoptions:{value:"${billOffStrSelect}"}},
						{ label: '单位',name:'DEPT_NAME', width:110},
						{ label: '表名', name: 'TABLE_NAME', width: 150},
						{ label: '列编码', name: 'COL_CODE', width: 120},
						{ label: '列名称', name: 'COL_NAME', width: 120},
						
						{ label: '字典翻译', name: 'DICT_TRANS_DETAIL', width: 170,align:'center',edittype: 'select',formatter:'select',formatteroptions:{value:"${dictString}"},editoptions:{value:"${dictString}"}}, 
						{ label: '必有翻译', name: 'DICT_TRANS', width: 80, editable: true,align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState}, 
						
						{ label: '非空状态', name: 'COL_NULL', width: 80, editable: true,align:'center',edittype:"checkbox",editoptions: {value:"1:0"},
							unformat: aceSwitch,formatter: customFmatterState},
						{ label: '启用状态', name: 'COL_STATE', width: 80, editable: true,align:'center',edittype:"checkbox",editoptions: {value:"1:0"},
								unformat: aceSwitch,formatter: customFmatterState},

						{ label: '条件列',name:'COL_COND', width:150,editable: true}, 
						{ label: '映射列',name:'COL_MAPPING', width:150,editable: true}, 
						
						{ label: '空值校验前缀',name:'NULL_VALUE_PREFIX', width:140,editable: true}, 
						{ label: '空值校验后缀',name:'NULL_VALUE_SUFFIX', width:140,editable: true}, 
						{ label: '字典校验前缀',name:'DIC_PREFIX', width:140,editable: true}, 
						{ label: '字典校验后缀',name:'DIC_SUFFIX', width:140,editable: true}, 
						{ label: '条件校验前缀',name:'COND_PREFIX', width:140,editable: true}, 
						{ label: '条件校验后缀',name:'COND_SUFFIX', width:140,editable: true}, 
						{ label: '映射校验前缀',name:'MAPPING_PREFIX', width:140,editable: true}, 
						{ label: '映射校验后缀',name:'MAPPING_SUFFIX', width:140,editable: true}
					],
			reloadAfterSubmit: true, 
			shrinkToFit: false,
			//viewrecords: true, // show the current page, data rang and total records on the toolbar
			rowNum: 0,
			sortname: 'DISP_ORDER',
			pager: "#jqGridPager",
			pgbuttons: false,//上下按钮 
			pginput:false,//输入框

			altRows: true,
			rownumbers: true, // show row numbers
            rownumWidth: 35, // the width of the row numbers columns			
	        ondblClickRow: dbClickRow,//双击表格编辑
			
			editurl: '<%=basePath%>tmplInputTips/edit.do?'
                +'SystemDateTime='+SystemDateTime,
			
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


        function createFreightEditElement(value, editOptions) {
            var div =$("<div style='margin-bottom:5px;margin-top:-10px;'></div>");
            var label = $("<label class='radio-inline'></label>");
            var radio = $("<input>", { type: "radio", value: "0", name: "freight", id: "zero", checked: (value != 25 && value != 50 && value != 100) });
			label.append(radio).append("0");
            var label1 = $("<label class='radio-inline'></label>");
            var radio1 = $("<input>", { type: "radio", value: "25", name: "freight", id: "twentyfive", checked: value == 25 });
			label1.append(radio1).append("25");
            div.append(label).append(label1).append(label2).append(label3);

            return div;
        }
		function getFreightElementValue(elem, oper, value) {
            if (oper === "set") {
                var radioButton = $(elem).find("input:radio[value='" + value + "']");
                if (radioButton.length > 0) {
                    radioButton.prop("checked", true);
                }
            }
            if (oper === "get") {
                return $(elem).find("input:radio:checked").val();
            }
        }
		
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
			    id: "edit",
				closeAfterEdit: true,
				recreateForm: true,
				beforeShowForm :beforeEditOrAddCallback,
	            afterSubmit: fn_addSubmit_extend
	        }, { }, { }, { }
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
		var grid = $("#jqGrid");
        var ids = grid.jqGrid('getDataIDs');
    	
		if(!(ids!=null && ids.length>0)){
			bootbox.dialog({
				message: "<span class='bigger-110'>您没有选择任何内容!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
		}else{
            var msg = '确定要保存数据吗?';
            bootbox.confirm(msg, function(result) {
				if(result) {
					var listData =new Array();
					//遍历访问这个集合  
					$(ids).each(function (index, id){  
			            $("#jqGrid").saveRow(id, false, 'clientArray');
			            var rowData = $("#jqGrid").getRowData(id);
			            listData.push(rowData);
					}); 
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>tmplInputTips/updateAll.do?'
			                +'SystemDateTime='+SystemDateTime,
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
            })
		}
	 }
	
	var lastSelection;
	function dbClickRow(rowId, rowIndex, colnumIndex, event){ 
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
			 if (cellvalue=="是" || cellvalue=="1" || cellvalue==1) {	
				$(cell).find('input[type=checkbox]').attr('checked','checked');
			 }else{
			 	$(cell).find('input[type=checkbox]').removeAttr('checked');
			 }
		}, 0);
		if (cellvalue=="是" || cellvalue=="1" || cellvalue==1) {
			return 1;
		} else {
			return 0;
		} 
	}
	
	
	
	function aceSwitchss( cellvalue, options, cell ) {	
		setTimeout(function(){	
			$(cell) .find('input[type=checkbox]')	
			.addClass('ace ace-switch ace-switch-5')	
			.after('<span class="lbl" data-lbl="是             否"></span>'); 
			if (cellvalue=="是" || cellvalue=="1" || cellvalue==1) {	
				$(cell).find('input[type=checkbox]').attr('checked','checked');
			}else{
				$(cell).find('input[type=checkbox]').removeAttr('checked');
			}
		}, 0);	
	}
	
	
	
	function customFmatterState(cellvalue, options, rowObject){  
		if (cellvalue=="是" || cellvalue=="1" || cellvalue==1) {
			 return '<span class="label label-important arrowed-in">是</span>';
		} else {
			return '<span class="label label-success arrowed">否</span>';
		}
	};
	
	function initComplete(){
		//下拉树
		var defaultNodes = {"treeNodes":${zTreeNodes}};
		//绑定change事件
		$("#selectTree").bind("change",function(){
			if($(this).attr("relValue")){
				$("#SelectedDepartCode").val($(this).attr("relValue"));
				$("#SelectedDepartName").val($(this).attr("relText"));
		    }
		});
		//赋给data属性
		$("#selectTree").data("data",defaultNodes);  
		$("#selectTree").render();
		console.log('${pd.rootDepartName}');
		$("#selectTree2_input").val('${pd.rootDepartName}');
		document.getElementById("SelectedDepartCode").value='${pd.rootDepartCode}'; 
		document.getElementById("SelectedDepartName").value='${pd.rootDepartName}'; //总部
	}
	
	//检索
	function tosearch() {
		if($("#SelectedTableCode").val()==""){
			$("#SelectedTableCode").tips({
				side:3,
	            msg:'请选择配置表',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SelectedTableCode").focus();
			return false;
		}
		if($("#SelectedCustCol7").val()==""){
			$("#SelectedCustCol7").tips({
				side:3,
	            msg:'请选择账套',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SelectedCustCol7").focus();
			return false;
		}
		var SelectedTableCode = $("#SelectedTableCode").val(); 
		var SelectedDepartCode = $("#SelectedDepartCode").val(); 
		
		$("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据
			url:'<%=basePath%>tmplInputTips/getPageList.do?SelectedTableCode='+SelectedTableCode
	        +'&SelectedCustCol7='+$("#SelectedCustCol7").val()
			+'&SelectedDepartCode='+SelectedDepartCode
            +'&SystemDateTime='+SystemDateTime
		}).trigger("reloadGrid");
	} 
 	</script>
</body>
</html>