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
							id="subTitle" style="margin-left: 2px;">关账设置</span> 
                        <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
								
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGridBase'))">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
						</button>
						
						<div class="pull-right">
							<span class="green middle bolder">类型: &nbsp;</span>

							<div class="btn-toolbar inline middle no-margin">
								<div data-toggle="buttons" class="btn-group no-margin">
									            <label class="btn btn-sm btn-primary active"> <span
									    	        class="bigger-110">合同化</span> <input type="radio" value="1" />
									            </label> 
									            <label class="btn btn-sm btn-primary"> <span
									            	class="bigger-110">市场化</span> <input type="radio" value="2" />
									            </label> 
									            <label class="btn btn-sm btn-primary"> <span
									            	class="bigger-110">劳务人员在建</span> <input type="radio" value="3" />
									            </label>
									            <label class="btn btn-sm btn-primary"> <span
									    	        class="bigger-110">运行人员</span> <input type="radio" value="4" />
									            </label>
									            <label class="btn btn-sm btn-primary"> <span
										            class="bigger-110">劳务用工</span> <input type="radio" value="5" />
									            </label>
									<label class="btn btn-sm btn-primary"> <span
										class="bigger-110">社保</span> <input type="radio" value="21" />
									</label>
									<label class="btn btn-sm btn-primary"> <span
										class="bigger-110">公积金</span> <input type="radio" value="25" />
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
										<!-- <form class="form-inline"> -->
										<div>
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedCustCol7" id="SelectedCustCol7" data-placeholder="请选择帐套"
													style="vertical-align: top; height:32px;width: 150px;">
													<option value="">请选择帐套</option>
													<c:forEach items="${FMISACC}" var="each">
														<option value="${each.DICT_CODE}">${each.NAME}</option>
													</c:forEach>
												</select>
											</span>
											
											<span class="pull-left" style="margin-right: 5px;">
												<div class="selectTree" id="selectTree" multiMode="true"
												    allSelectable="false" noGroup="false"></div>
											    <input id="SelectedDepartCode" type="hidden"/> 
											</span>
											<button type="button" class="pull-left btn btn-info btn-sm" onclick="tosearch();">
												<i class="ace-icon fa fa-search bigger-110"></i>
											</button>
											
											
											<span class="pull-left" style="margin-left: 5%;">
												<select class="chosen-select form-control"
													name="SelectedDay" id="SelectedDay" data-placeholder="请选择每月日"
													style="vertical-align: top; height:32px;width: 150px;">
													<option value="">请选择每月日</option>
													<c:forEach items="${Days}" var="each">
														<option value="${each.DICT_CODE}">${each.NAME}</option>
													</c:forEach>
												</select>
											</span>
											
											<button style="margin-left: 5px;"
												class="btn btn-sm btn-white btn-success"
												onclick="batchStart()">批量开启</button>
											
											<button class="btn btn-sm btn-white btn-warning"
												onclick="batchStop()">批量关闭</button>
										<!-- </form> -->
										</div>
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

        var which;
        
    	//页面显示的数据的查询信息，在tosearch()里赋值
		var ShowDataCustCol7 = "";
    	var ShowDataDepartCode = "";

	    $(document).ready(function () { 
		    $(top.hangge());//关闭加载状态
		 
		    //resize to fit page size
		    $(window).on('resize.jqGrid', function () {
		        $(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
		        //$(gridBase_selector).jqGrid( 'setGridHeight', $(window).height() - 230);
			    resizeGridHeight($(gridBase_selector));
			});
			
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
				//if(which!='${pd.which}'){
					window.location.href="<%=basePath%>sysDeptLtdTime/list.do?SelectedTableNo="+which;
				//}
			});
		
		    $(gridBase_selector).jqGrid({
    			url: '<%=basePath%>sysDeptLtdTime/getPageList.do?SelectedTableNo='+which
	                + '&SelectedCustCol7='+ShowDataCustCol7
    				+ '&SelectedDepartCode='+ShowDataDepartCode,
		        datatype: "json",
		        colModel: [
					{ label: '启用状态',name:'STATE__', width:90,hidden : true,editable: true},
					{ label: '业务类型',name:'BUSI_TYPE__', width:90,hidden : true,editable: true},
				    { label: '账套',name:'BILL_OFF__', width:90,hidden : true,editable: true},
					{ label: '责任中心', name: 'DEPT_CODE__', width: 90,hidden : true,editable: true},
				    { label: '每月日',name:'LTD_DAY__', width:90,hidden : true,editable: true},
				    
				    { label: '启用状态', name: 'STATE', width: 80,editable: true, editrules:{required:true}, align:'center',edittype:"checkbox",editoptions: {value:"1:0"},unformat: aceSwitch,formatter: customFmatterState},                   
				    { label: '帐套', name: 'BILL_OFF', width: 120,editable: false,editrules:{required:true},edittype: 'select',formatter:'select',formatteroptions:{value:"${billOffStrAll}"},editoptions:{value:"${billOffStrSelect}"},stype: 'select',searchoptions:{value:"${billOffStrAll}"}},
					{ label: '责任中心', name: 'DEPT_CODE', width: 140,editable: false, editrules:{required:true},edittype: 'select',formatter:'select',formatoptions:{value:"${departmentStrSelect}"},editoptions:{value:"${departmentStrSelect}"},stype: 'select',searchoptions:{value:"${departmentStrAll}"}},
				    { label: '每月日', name: 'LTD_DAY', width: 140,editable: true, editrules:{required:true},edittype: 'select',formatter:'select',formatoptions:{value:"${lidDayStrSelect}"},editoptions:{value:"${lidDayStrSelect}"},stype: 'select',searchoptions:{value:"${lidDayStrAll}"}}
				],
    			reloadAfterSubmit: true, 
    			viewrecords: true, 
    			rowNum: 0,
    			//rowList: [100,200,500],
                multiSort: true,
			    sortname: 'DEPT_CODE,BUSI_TYPE',
				altRows: true,
				//rownumbers: true, 
	            //rownumWidth: 35,		
	            
				multiselect: false,
		        multiboxonly: false, 

				ondblClickRow: doubleClickRow,
				
				pager: pagerBase_selector,
				pgbuttons: false, // 分页按钮是否显示 
				pginput: false, // 是否允许输入分页页数 
				
    			editurl: '<%=basePath%>sysDeptLtdTime/edit.do?SelectedTableNo='+which
                    + '&SelectedDepartCode='+$("#SelectedDepartCode").val()
    	            + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
    	            
    				+ '&ShowDataDepartCode='+ShowDataDepartCode
    	            + '&ShowDataCustCol7='+ShowDataCustCol7,
    			
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
	        /*$(gridBase_selector).navButtonAdd(pagerBase_selector, {
    			id : "batchDelete",
                caption : "",
                buttonicon : "ace-icon fa fa-trash-o red",
                onClickButton : batchDelete,
                position : "last",
                title : "删除",
                cursor : "pointer"
            });*/
            $(gridBase_selector).navSeparatorAdd(pagerBase_selector, {
    			sepclass : "ui-separator",
    			sepcontent: ""
    		});
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
    				        url: '<%=basePath%>sysDeptLtdTime/updateAll.do?',
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
    			getSelectBillCodeOptions();
    		});
    		//赋给data属性
    		$("#selectTree").data("data",defaultNodes);  
    		$("#selectTree").render();
    		$("#selectTree2_input").val("请选择单位");
    	}
	
		//检索
		function tosearch() {
	    	//页面显示的数据的查询信息，在tosearch()里赋值
	    	ShowDataDepartCode = $("#SelectedDepartCode").val();
	    	ShowDataCustCol7 = $("#SelectedCustCol7").val();
	    	
			$(gridBase_selector).jqGrid('setGridParam',{  // 重新加载数据
				url:'<%=basePath%>sysDeptLtdTime/getPageList.do?SelectedTableNo='+which
                    + '&SelectedDepartCode='+ShowDataDepartCode
    	            + '&SelectedCustCol7='+ShowDataCustCol7,
        		editurl: '<%=basePath%>sysDeptLtdTime/edit.do?SelectedTableNo='+which
                    + '&SelectedDepartCode='+$("#SelectedDepartCode").val()
        	        + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
        	            
        			+ '&ShowDataDepartCode='+ShowDataDepartCode
        	        + '&ShowDataCustCol7='+ShowDataCustCol7,
								datatype : 'json'
							}).trigger("reloadGrid");
		}
		
		//switch element when editing inline
		function aceSwitch( cellvalue, options, cell ) {
			setTimeout(function(){
				 $(cell).find('input[type=checkbox]')
					.addClass('ace ace-switch ace-switch-5')
					.after('<span class="lbl" data-lbl="启用        关闭"></span>'); 
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
		
		function customFmatterState(cellvalue, options, rowObject){  
			if (cellvalue==1) {
				 return '<span class="label label-important arrowed-in">启用</span>';
			} else {
				return '<span class="label label-success arrowed">关闭</span>';
			}
		};

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

        //批量删除
        function batchDelete(){
        	//获得选中的行ids的方法
            var ids = $(gridBase_selector).getGridParam("selarrrow");  
     		
     		if(!(ids!=null&&ids.length>0)){
    			bootbox.dialog({
    				message: "<span class='bigger-110'>您没有选择任何内容!</span>",
    				buttons: 			
    				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
    			});
    		}else{
                var msg = '确定要删除选中的数据吗??';
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
    						url: '<%=basePath%>sysDeptLtdTime/deleteAll.do?',
    				    	data: {DataRows:JSON.stringify(listData)},
    						dataType:'json',
    						cache: false,
    						success: function(response){
    							if(response.code==0){
    								$(gridBase_selector).trigger("reloadGrid");  
    								$(top.hangge());//关闭加载状态
    								$("#subTitle").tips({
    									side:3,
    						            msg:'删除成功',
    						            bg:'#009933',
    						            time:3
    						        });
    							}else{
    								$(top.hangge());//关闭加载状态
    								$("#subTitle").tips({
    									side:3,
    						            msg:'删除失败,'+response.message,
    						            bg:'#cc0033',
    						            time:3
    						        });
    							}
    						},
    				    	error: function(response) {
    							$(top.hangge());//关闭加载状态
    							$("#subTitle").tips({
    								side:3,
    					            msg:'删除出错:'+response.responseJSON.message,
    					            bg:'#cc0033',
    					            time:3
    					        });
    				    	}
    					});
    				}
                });
    		}
    	}
        
        //批量开启
		function batchStart() {
			if($("#SelectedCustCol7").val()==""){
				$("#SelectedCustCol7").tips({
					side:3,
		            msg:'请您先选择帐套',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedCustCol7").focus();
				return false;
			}
			
			if($("#SelectedDepartCode").val()==""){
				$("#selectTree").tips({
					side:3,
		            msg:'请您先选择单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#selectTree").focus();
				return false;
			}
			
			if($("#SelectedDay").val()==""){
				$("#SelectedDay").tips({
					side:3,
		            msg:'请您先选择每月日',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedDay").focus();
				return false;
			}
        	
	    	//页面显示的数据的查询信息，在tosearch()里赋值
	    	var SelectedTableNo=which;
	    	var SelectedDay = $("#SelectedDay").val();
	    	var SelectedCustCol7 = $("#SelectedCustCol7").val();
	    	var SelectedDepartCode = $("#SelectedDepartCode").val();
            
            var msg = '确定要批量开启吗？';
            bootbox.confirm(msg, function(result) {
				if(result) {
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>sysDeptLtdTime/batchUpdate.do?',
				    	data: {Operate:1,SelectedTableNo:which,SelectedCustCol7:SelectedCustCol7,SelectedDepartCode:SelectedDepartCode,SelectedDay:SelectedDay},
						dataType:'json',
						cache: false,
						success: function(response){
							if(response.code==0){
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'批量开启成功',
						            bg:'#009933',
						            time:3
						        });
							}else{
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'批量开启失败,'+response.message,
						            bg:'#cc0033',
						            time:3
						        });
							}
						},
				    	error: function(response) {
							$(top.hangge());//关闭加载状态
							$("#subTitle").tips({
								side:3,
					            msg:'批量开启出错:'+response.responseJSON.message,
					            bg:'#cc0033',
					            time:3
					        });
				    	}
					});
				}
           });
		}
        
		 //批量关闭
        function batchStop(){
        	if($("#SelectedCustCol7").val()==""){
				$("#SelectedCustCol7").tips({
					side:3,
		            msg:'请您先选择帐套',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedCustCol7").focus();
				return false;
			}
			
			if($("#SelectedDepartCode").val()==""){
				$("#selectTree").tips({
					side:3,
		            msg:'请您先选择单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#selectTree").focus();
				return false;
			}
        	
	    	//页面显示的数据的查询信息，在tosearch()里赋值
	    	var SelectedTableNo=which;
	    	var SelectedDay = $("#SelectedDay").val();
	    	var SelectedCustCol7 = $("#SelectedCustCol7").val();
	    	var SelectedDepartCode = $("#SelectedDepartCode").val();
            
            var msg = '确定要批量关闭吗？';
            bootbox.confirm(msg, function(result) {
				if(result) {
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>sysDeptLtdTime/batchUpdate.do?',
				    	data: {Operate:0,SelectedTableNo:which,SelectedCustCol7:SelectedCustCol7,SelectedDepartCode:SelectedDepartCode,SelectedDay:SelectedDay},
						dataType:'json',
						cache: false,
						success: function(response){
							if(response.code==0){
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'批量关闭成功',
						            bg:'#009933',
						            time:3
						        });
							}else{
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'批量关闭失败,'+response.message,
						            bg:'#cc0033',
						            time:3
						        });
							}
						},
				    	error: function(response) {
							$(top.hangge());//关闭加载状态
							$("#subTitle").tips({
								side:3,
					            msg:'批量关闭出错:'+response.responseJSON.message,
					            bg:'#cc0033',
					            time:3
					        });
				    	}
					});
				}
           });
        }
	</script>
</body>
</html>