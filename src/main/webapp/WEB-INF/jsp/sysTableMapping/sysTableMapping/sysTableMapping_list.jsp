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
						<span class="label label-xlg label-success arrowed-right">东部管道</span>
						<!-- arrowed-in-right -->
									<span class="label label-xlg label-yellow arrowed-in arrowed-right"
									    id="subTitle" style="margin-left: 2px;">凭证数据表定义</span> 
                                    <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
								
									<button id="btnQuery" class="btn btn-white btn-info btn-sm"
										onclick="showQueryCondi($('#jqGridBase'))">
										<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
									</button>
					</div><!-- /.page-header -->

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
    	var ShowDataBusiDate = "";

	    $(document).ready(function () { 
		    $(top.hangge());//关闭加载状态
			$('.input-mask-date').mask('999999');
			//当前期间,取自tb_system_config的SystemDateTime字段
		    var SystemDateTime = '${SystemDateTime}';
			$("#SelectedBusiDate").val(SystemDateTime);
		 
		    //resize to fit page size
		    $(window).on('resize.jqGrid', function () {
		        $(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
		        //$(gridBase_selector).jqGrid( 'setGridHeight', $(window).height() - 230);
			    resizeGridHeight($(gridBase_selector));
			})
		
		    $(gridBase_selector).jqGrid({
    			url: '<%=basePath%>sysTableMapping/getPageList.do?'
    				+ 'SelectedTypeCode='+ShowDataTypeCode
    	            + '&SelectedCustCol7='+ShowDataCustCol7
    	            + '&SelectedBusiDate='+ShowDataBusiDate,
		        datatype: "json",
		        colModel: [
				    { label: '业务类型',name:'TYPE_CODE__', width:90,hidden : true,editable: true},
				    { label: '业务期间', name: 'BUSI_DATE__', width: 90,hidden : true,editable: true},
				    { label: '业务表', name: 'TABLE_NAME__', width: 60,hidden : true,editable: true,},
				    { label: '映射业务表', name: 'TABLE_NAME_MAPPING__', width: 60,hidden : true,editable: true,},
				    { label: '状态', name: 'STATE__', width: 60,hidden : true,editable: true,},
				    { label: '帐套', name: 'BILL_OFF__', width: 60,hidden : true,editable: true,},
				    
				    { label: '业务期间', name: 'BUSI_DATE', width: 90,editable: false},
				    { label: '映射业务表', name: 'TABLE_NAME_MAPPING', width: 160,editable: false},
				    { label: '帐套', name: 'BILL_OFF', width: 140,editable: true, editrules:{required:true},edittype: 'select',formatter:'select',formatoptions:{value:"${billOffStrSelect}"},editoptions:{value:"${billOffStrSelect}"},stype: 'select',searchoptions:{value:"${billOffStrAll}"}},
				    { label: '业务类型',name:'TYPE_CODE', width:160,editable: true, editrules:{required:true},edittype: 'select',formatter:'select',formatoptions:{value:"${typeCodeStrSelect}"},editoptions:{value:"${typeCodeStrSelect}"},stype: 'select',searchoptions:{value:"${typeCodeStrAll}"}}, 
				    { label: '业务表', name: 'TABLE_NAME', width: 160,editable: true, editrules:{required:true}},
				    //{ label: '业务表类型', name: 'TABLE_TYPE', width: 140,editable: true, editrules:{required:true},edittype: 'select',formatter:'select',formatoptions:{value:"${tableTypeStrSelect}"},editoptions:{value:"${tableTypeStrSelect}"},stype: 'select',searchoptions:{value:"${tableTypeStrAll}"}},
					{ label: '状态', name: 'STATE', width: 60, editable: true,align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState}
				],
    			reloadAfterSubmit: true, 
    			viewrecords: true, 
    			rowNum: 0,
    			//rowList: [100,200,500],
                multiSort: true,
			    sortname: 'BUSI_DATE,TYPE_CODE,BILL_OFF,TABLE_NAME',
				altRows: true,
				//rownumbers: true, 
	            //rownumWidth: 35,		
	            
				multiselect: true,
		        multiboxonly: true, 

				ondblClickRow: doubleClickRow,
				
				pager: pagerBase_selector,
				pgbuttons: false, // 分页按钮是否显示 
				pginput: false, // 是否允许输入分页页数 
				
    			editurl: '<%=basePath%>sysTableMapping/edit.do?'
    				+ 'SelectedTypeCode='+$("#SelectedTypeCode").val()
    	            + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
    	            + '&SelectedBusiDate='+$("#SelectedBusiDate").val()
    	            
    				+ '&ShowDataTypeCode='+ShowDataTypeCode
    	            + '&ShowDataCustCol7='+ShowDataCustCol7
    	            + '&ShowDataBusiDate='+ShowDataBusiDate,
    			
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
			{ 
	            //navbar options
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
				//edit record form
			    id: "edit",
				closeAfterEdit: true,
				recreateForm: true,
				beforeShowForm :beforeEditOrAddCallback,
	            afterSubmit: fn_addSubmit_extend
	        },
	        {
				//new record form
			    id: "add",
				closeAfterAdd: true,
				recreateForm: true,
				viewPagerButtons: false,
				//width: 700,
				//reloadAfterSubmit: true,
				beforeShowForm : beforeEditOrAddCallback,
			    onclickSubmit: function(params, posdata) {
					console.log("onclickSubmit");
	            } , 
	            afterSubmit: fn_addSubmit_extend
	        },
	        { 
				//delete record form
				recreateForm: true,
				beforeShowForm : beforeDeleteCallback,
				onClick : function(e) {
					
				}
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
	        },
	        {},{});
			
			$(gridBase_selector).navSeparatorAdd(pagerBase_selector, {
				sepclass : "ui-separator",
				sepcontent: ""
			});
	        $(gridBase_selector).navButtonAdd(pagerBase_selector,
	        {
				id : "batchEdit",
	            buttonicon: "ace-icon fa fa-pencil-square-o purple",
	            title: "批量编辑",
	            caption: "",
	            position: "last",
	            onClickButton: batchEdit,
	            cursor : "pointer"
	        });
            $(gridBase_selector).navButtonAdd(pagerBase_selector,
	        {
            	id : "batchCancelEdit",
	            buttonicon: "ace-icon fa fa-undo",
	            title: "取消批量编辑",
	            caption: "",
	            position: "last",
	            onClickButton: batchCancelEdit,
	            cursor : "pointer"
	        });
	        $(gridBase_selector).navButtonAdd(pagerBase_selector, {
	        	id : "batchSave",
	            caption : "",
	            buttonicon : "ace-icon fa fa-save green",
	            onClickButton : batchSave,
	            position : "last",
	            title : "批量保存",
	            cursor : "pointer"
	        });
 	    });
		
		//switch element when editing inline
		function aceSwitch( cellvalue, options, cell ) {
			setTimeout(function(){
				 $(cell).find('input[type=checkbox]')
					.addClass('ace ace-switch ace-switch-5')
					.after('<span class="lbl" data-lbl="启用        停用"></span>'); 
				 if (cellvalue=="启用") {	
					$(cell).find('input[type=checkbox]').attr('checked','checked');
				 }else{
				 	$(cell).find('input[type=checkbox]').removeAttr('checked');
				 }
			}, 0);
			if (cellvalue=="启用") {
				return 1;
			} else {
				return 0;
			} 
		}

		function customFmatterState(cellvalue, options, rowObject) {
			if (cellvalue == 1) {
				return '<span class="label label-important arrowed-in">启用</span>';
			} else {
				return '<span class="label label-success arrowed">停用</span>';
			}
		};
		
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

        /**
         * 批量保存
         */
        function batchSave(){
        	    //获得选中行ids的方法
		        var ids = $(gridBase_selector).jqGrid('getDataIDs');
    	        var msg = '确定要保存数据吗?';
    	        bootbox.confirm(msg, function(result) {
    	            if(result) {
    	                var listData =new Array();
    				
    	                //遍历访问这个集合  
    				    $(ids).each(function (index, id){  
    				        $(gridBase_selector).saveRow(id, false, 'clientArray');
    				        var rowData = $(gridBase_selector).getRowData(id);
    				        listData.push(rowData);
    				    });
    				
    				    top.jzts();
    				    $.ajax({
    				        type: "POST",
    				        url: '<%=basePath%>sysTableMapping/updateAll.do?',
    				        data: {DataRows:JSON.stringify(listData)},
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
    				                    msg:'保存失败,'+response.message,
    				                    bg:'#cc0033',
    				                    time:3
    				                });
    				            }
    				        },
    				        error: function(response) {
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
                });
        }
	
		//检索
		function tosearch() {
	    	//页面显示的数据的查询信息，在tosearch()里赋值
	    	ShowDataTypeCode = $("#SelectedTypeCode").val();
	    	ShowDataCustCol7 = $("#SelectedCustCol7").val();
	    	ShowDataBusiDate = $("#SelectedBusiDate").val();
	    	
			$(gridBase_selector).jqGrid('setGridParam',{  // 重新加载数据
				url:'<%=basePath%>sysTableMapping/getPageList.do?'
    				+ 'SelectedTypeCode='+ShowDataTypeCode
    	            + '&SelectedCustCol7='+ShowDataCustCol7
    	            + '&SelectedBusiDate='+ShowDataBusiDate,
        		editurl: '<%=basePath%>sysTableMapping/edit.do?'
        			+ 'SelectedTypeCode='+$("#SelectedTypeCode").val()
        	        + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
        	        + '&SelectedBusiDate='+$("#SelectedBusiDate").val()
        	            
        			+ '&ShowDataTypeCode='+ShowDataTypeCode
        	        + '&ShowDataCustCol7='+ShowDataCustCol7
        	        + '&ShowDataBusiDate='+ShowDataBusiDate,
								datatype : 'json'
							}).trigger("reloadGrid");
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
	</script>
</body>
</html>