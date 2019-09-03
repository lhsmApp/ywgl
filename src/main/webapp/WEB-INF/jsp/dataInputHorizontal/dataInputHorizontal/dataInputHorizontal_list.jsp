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
							id="subTitle" style="margin-left: 2px;">参数输入</span> 
                        <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
								
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGridBase'),null,true)">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
						</button>
								
						<div class="pull-right">
							<span class="label label-xlg label-blue arrowed-left"
								id = "showDur" style="background:#428bca; margin-right: 2px;"></span> 
						</div>
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
													onchange="ChangeSelectedCustCol7()"
													data-placeholder="请选择帐套" 
													style="vertical-align: top; height:32px;width: 150px;">
													    <option value="">请选择帐套</option>
													    <c:forEach items="${FMISACC}" var="each">
													    	<option value="${each.DICT_CODE}">${each.NAME}</option>
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
									        <button type="button" class="btn btn-white btn-info btn-sm" onclick="copyData()">
										        <i class="ace-icon fa  fa-exchange  bigger-120 blue"></i><span>复制到之后区间</span>
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
        
    	//页面显示的数据的责任中心和账套信息，在tosearch()里赋值
    	var ShowDataCustCol7 = "";
    	var ShowDataTypeCode = "";
    	var ShowDataDepartCode = "";

		//当前期间,取自tb_system_config的SystemDateTime字段
	    var SystemDateTime = '';
	    
		//结构
	    var jqGridColModel;
		
		//下拉列表
	    var SelectedTypeCodeFirstShow;
		var InitSelectedTypeCodeOptions;
		var SelectedDepartCodeFirstShow;
	    var InitSelectedDepartCodeOptions;
		
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
				url: '<%=basePath%>dataInputHorizontal/getTypeCodeList.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
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
			top.jzts();
			$.ajax({
			    type: "POST",
				url: '<%=basePath%>dataInputHorizontal/getDepartCodeList.do?SelectedCustCol7='+$("#SelectedCustCol7").val()
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
	    
	    $(document).ready(function () {
			$(top.hangge());//关闭加载状态
		    
			//当前期间,取自tb_system_config的SystemDateTime字段
		    SystemDateTime = '${SystemDateTime}';
			//当前登录人所在二级单位
		    var DepartName = '${DepartName}';
		    $("#showDur").text('当前期间：' + SystemDateTime + ' 登录人责任中心：' + DepartName);
			
			//结构
		    jqGridColModel = "[]";
		    
			//选择下拉列表
		    SelectedTypeCodeFirstShow =  "${pd.SelectedTypeCodeFirstShow}";
		    InitSelectedTypeCodeOptions =  "${pd.InitSelectedTypeCodeOptions}";
		    SelectedDepartCodeFirstShow =  "${pd.SelectedDepartCodeFirstShow}";
		    InitSelectedDepartCodeOptions =  "${pd.InitSelectedDepartCodeOptions}";
			setSelectTypeCodeOptions(InitSelectedTypeCodeOptions);
			setSelectDepartCodeOptions(InitSelectedDepartCodeOptions);
		});
	
	    //检索
	    function tosearch() {
			var CustCol7 = $("#SelectedCustCol7").val();
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
			var TypeCode = $("#SelectedTypeCode").val();
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
			var DepartCode = $("#SelectedDepartCode").val();
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

			ShowDataDepartCode = $("#SelectedDepartCode").val();
			ShowDataCustCol7 = $("#SelectedCustCol7").val();
			ShowDataTypeCode = $("#SelectedTypeCode").val();
			
			$(gridBase_selector).jqGrid('GridUnload'); 
			//结构
		    jqGridColModel = "[]";

			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>dataInputHorizontal/getShowColModel.do?SelectedCustCol7='+ShowDataCustCol7
	                +'&SelectedTypeCode='+ShowDataTypeCode
	                +'&SelectedDepartCode='+ShowDataDepartCode
	                + '&SystemDateTime='+SystemDateTime,
		    	dataType:'json',
				cache: false,
				success: function(response){
					if(response.code==0){
						$(top.hangge());//关闭加载状态
						var mes = response.message;
						mes = mes.replace("[", "").replace("]", "");
						if($.trim(mes)!=""){
							//结构
						    jqGridColModel = eval("(" + response.message + ")");//此处记得用eval()行数将string转为array
							SetStructure();
						}
						SetStructure();
					}else{
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side:3,
				            msg:'获取列数据源失败：'+response.message,
				            bg:'#cc0033',
				            time:3
				        });
					}
				},
		    	error: function(response) {
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取列数据源出错：'+response.responseJSON.message,
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
    			url: '<%=basePath%>dataInputHorizontal/getPageList.do?'
    				+ 'SelectedCustCol7='+ShowDataCustCol7
                    + '&SelectedTypeCode=' + ShowDataTypeCode
                    + '&SelectedDepartCode='+ShowDataDepartCode
    	            + '&SystemDateTime='+SystemDateTime,
    			datatype: "json",
    			colModel: jqGridColModel, 
    			       /*[
    						{ label: '凭证类型', name: 'TYPE_CODE__', width: 60,hidden : true,editable: true,},
    						{ label: '账套', name: 'BILL_OFF__', width: 60,hidden : true,editable: true,},
    						{ label: '业务期间', name: 'BUSI_DATE__', width: 60,hidden : true,editable: true,},
    						{ label: '责任中心', name: 'DEPT_CODE__', width: 60,hidden : true,editable: true,},
    						{ label: '变动列', name: 'CHANGE_COL__', width: 60,hidden : true,editable: true,},

    						{ label: '业务期间', name: 'BUSI_DATE', hidden : false,editable: false},
    						{ label: '凭证类型', name: 'TYPE_CODE', hidden : true,editable: true},//,edittype: 'select',formatter:'select',formatoptions:{value:"${typeCodeStrSelect}"},editoptions:{value:"${typeCodeStrSelect}"},stype: 'select',searchoptions:{value:"${typeCodeStrAll}"} 
    						{ label: '账套', name: 'BILL_OFF', hidden : true,editable: true},//,edittype: 'select',formatter:'select',formatoptions:{value:"${billOffStrSelect}"},editoptions:{value:"${billOffStrSelect}"},stype: 'select',searchoptions:{value:"${billOffStrAll}"} 
    						{ label: '责任中心', name: 'DEPT_CODE', hidden : true,editable: true},//,edittype: 'select',formatter:'select',formatoptions:{value:"${departmentStrSelect}"},editoptions:{value:"${departmentStrSelect}"},stype: 'select',searchoptions:{value:"${departmentStrAll}"} 

    						{ label: '变动列', name: 'CHANGE_COL', editable: true,edittype: 'select',formatter:'select',formatoptions:{value:changeColStrAll},editoptions:{value:changeColStrSelect},stype: 'select',searchoptions:{value:changeColStrAll}},
    						{ label: '数值值', name: 'DATA_VALUE', editable: true, edittype:'text',search:false, sorttype: 'number',align:'right', searchrules: {number: true},
    							formatter: "number", formatoptions: {thousandsSeparator:",", decimalSeparator:".", defaulValue:"0.00",decimalPlaces:2}, editoptions: {maxlength:'15', number: true}
    					    }
    					],*/
    			reloadAfterSubmit: true, 
    			viewrecords: false, 
    			shrinkToFit: false,
    			width: "100%",
    			rowNum: 0,
    			//rowNum: 100,
    			//rowList: [100,200,500],
                multiselect: true,
                multiboxonly: true,
                sortable: true,
    			altRows: true, //斑马条纹
    			editurl: '<%=basePath%>dataInputHorizontal/edit.do?'
    				+ 'SelectedDepartCode='+$("#SelectedDepartCode").val()
    	            + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
    	            + '&SelectedTypeCode='+$("#SelectedTypeCode").val()
    	            + '&ShowDataDepartCode='+ShowDataDepartCode
    	            + '&ShowDataCustCol7='+ShowDataCustCol7
    	            + '&ShowDataTypeCode='+ShowDataTypeCode
    	            + '&SystemDateTime='+SystemDateTime,
    			
    			pager: pagerBase_selector,
				pgbuttons: false, // 分页按钮是否显示 
				pginput: false, // 是否允许输入分页页数 
    			footerrow: false,
    			ondblClickRow: doubleClickRow,
    			
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

    		$(gridBase_selector).navGrid(pagerBase_selector, 
    				{
    			            //navbar options
    				        edit: true,
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
    				    width: 900,
    					closeAfterEdit: true,
    					recreateForm: true,
    					beforeShowForm :beforeEditOrAddCallback,
    		            afterSubmit: fn_addSubmit_extend
    		        },
    		        { },
    		        { },
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
    			id : "batchEdit",
    	        buttonicon: "ace-icon fa fa-pencil-square-o purple",
    	        title: "批量编辑",
    	        caption: "",
    	        position: "last",
    	        onClickButton: batchEdit,
    	        cursor : "pointer"
    	    });
            $(gridBase_selector).navButtonAdd(pagerBase_selector, {
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
    		$(gridBase_selector).navSeparatorAdd(pagerBase_selector, {
    			sepclass : "ui-separator",
    			sepcontent: ""
    		});
    	    $(gridBase_selector).navButtonAdd(pagerBase_selector, {
				 id : "importItems",
	             caption : "导入",
	             buttonicon : "ace-icon fa fa-cloud-upload",
	             onClickButton : importItems,
	             position : "last",
	             title : "导入",
	             cursor : "pointer"
	         });
             $(gridBase_selector).navButtonAdd(pagerBase_selector, {
				 id : "exportItems",
                 caption : "导出",
                 buttonicon : "ace-icon fa fa-cloud-download",
                 onClickButton : exportItems,
                 position : "last",
                 title : "导出",
                 cursor : "pointer"
             });
	    }
		
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
        	//var ids = $(gridBase_selector).getGridParam("selarrrow");  
            var ids = $(gridBase_selector).getDataIDs();  
        	
        	if(!(ids!=null&&ids.length>0)){
        		bootbox.dialog({
        			message: "<span class='bigger-110'>您没有任何内容!</span>",
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
        		            $(gridBase_selector).saveRow(id, false, 'clientArray');
        		            var rowData = $(gridBase_selector).getRowData(id);
        		            listData.push(rowData);
        				});
        				
        				top.jzts();
        				$.ajax({
        					type: "POST",
        					url: '<%=basePath%>dataInputHorizontal/updateAll.do?'
        	    				+ 'SelectedDepartCode='+$("#SelectedDepartCode").val()
        	    	            + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
        	    	            + '&SelectedTypeCode='+$("#SelectedTypeCode").val()
        	    	            + '&ShowDataDepartCode='+ShowDataDepartCode
        	    	            + '&ShowDataCustCol7='+ShowDataCustCol7
        	    	            + '&ShowDataTypeCode='+ShowDataTypeCode
        	    	            + '&SystemDateTime='+SystemDateTime,
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
		
		//复制
	    function copyData() {
			var TypeCode = $("#SelectedTypeCode").val();
			var CustCol7 = $("#SelectedCustCol7").val();
			var DepartCode = $("#SelectedDepartCode").val(); 

			if(!(SystemDateTime!=null && $.trim(SystemDateTime)!="")){
				$("#SelectedCustCol7").tips({
					side:3,
		            msg:'当前区间不能为空,请联系管理员',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedCustCol7").focus();
				return false;
			}
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
			if(CustCol7!=ShowDataCustCol7){
				$("#SelectedCustCol7").tips({
					side:3,
		            msg:'查询条件中所选账套与页面显示数据账套不一致，请单击查询再进行操作',
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
			if(TypeCode!=ShowDataTypeCode){
				$("#SelectedTypeCode").tips({
					side:3,
		            msg:'查询条件中所选类型与页面显示数据类型不一致，请单击查询再进行操作',
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
			if(DepartCode!=ShowDataDepartCode){
				$("#SelectedDepartCode").tips({
					side:3,
		            msg:'查询条件中所选责任中心与页面显示数据责任中心不一致，请单击查询再进行操作',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedDepartCode").focus();
				return false;
			}
             var msg = '确定要复制吗?';
             bootbox.confirm(msg, function(result) {
            	 if(result) {
	       	        top.jzts();

    				$.ajax({
    					type: "POST",
    					url: '<%=basePath%>dataInputHorizontal/copyAll.do?'
						 +'SelectedCustCol7='+CustCol7
					 	 +'&SelectedTypeCode='+TypeCode
					 	 +'&SelectedDepartCode='+DepartCode
				            + '&SystemDateTime='+SystemDateTime,
    					dataType:'json',
    					cache: false,
    					success: function(response){
    						if(response.code==0){
    							$(top.hangge());//关闭加载状态
    							$("#subTitle").tips({
    								side:3,
    					            msg:'复制成功',
    					            bg:'#009933',
    					            time:3
    					        });
    						}else{
    							batchEdit(null);
    							$(top.hangge());//关闭加载状态
    							$("#subTitle").tips({
    								side:3,
    					            msg:'复制失败,'+response.message,
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
    				            msg:'复制出错:'+response.responseJSON.message,
    				            bg:'#cc0033',
    				            time:3
    				        });
    			    	}
    				});
            	 }
             });
		}

        /**
         * 导入
         */
        function importItems(){
			var TypeCode = $("#SelectedTypeCode").val();
			var CustCol7 = $("#SelectedCustCol7").val();
			var DepartCode = $("#SelectedDepartCode").val(); 

			if(!(SystemDateTime!=null && $.trim(SystemDateTime)!="")){
				$("#SelectedCustCol7").tips({
					side:3,
		            msg:'当前区间不能为空,请联系管理员',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedCustCol7").focus();
				return false;
			}
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
			if(CustCol7!=ShowDataCustCol7){
				$("#SelectedCustCol7").tips({
					side:3,
		            msg:'查询条件中所选账套与页面显示数据账套不一致，请单击查询再进行操作',
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
			if(TypeCode!=ShowDataTypeCode){
				$("#SelectedTypeCode").tips({
					side:3,
		            msg:'查询条件中所选类型与页面显示数据类型不一致，请单击查询再进行操作',
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
			if(DepartCode!=ShowDataDepartCode){
				$("#SelectedDepartCode").tips({
					side:3,
		            msg:'查询条件中所选责任中心与页面显示数据责任中心不一致，请单击查询再进行操作',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedDepartCode").focus();
				return false;
			}
     	   top.jzts();
    	   var diag = new top.Dialog();
    	   diag.Drag=true;
    	   diag.Title ="EXCEL 导入到数据库";
    	   diag.URL = '<%=basePath%>dataInputHorizontal/goUploadExcel.do?'
				+ 'SelectedDepartCode='+$("#SelectedDepartCode").val()
	            + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
	            + '&SelectedTypeCode='+$("#SelectedTypeCode").val()
	            + '&ShowDataDepartCode='+ShowDataDepartCode
	            + '&ShowDataCustCol7='+ShowDataCustCol7
	            + '&ShowDataTypeCode='+ShowDataTypeCode
	            + '&SystemDateTime='+SystemDateTime;
    	   diag.Width = 300;
    	   diag.Height = 150;
    	   diag.CancelEvent = function(){ //关闭事件
    		  top.jzts();
    		  $(gridBase_selector).trigger("reloadGrid");  
    		  $(top.hangge());//关闭加载状态
    	      diag.close();
           };
           diag.show();
        }

        /**
         * 导出
         */
        function exportItems(){
			var TypeCode = $("#SelectedTypeCode").val();
			var CustCol7 = $("#SelectedCustCol7").val();
			var DepartCode = $("#SelectedDepartCode").val(); 

			if(!(SystemDateTime!=null && $.trim(SystemDateTime)!="")){
				$("#SelectedCustCol7").tips({
					side:3,
		            msg:'当前区间不能为空,请联系管理员',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedCustCol7").focus();
				return false;
			}
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
			if(CustCol7!=ShowDataCustCol7){
				$("#SelectedCustCol7").tips({
					side:3,
		            msg:'查询条件中所选账套与页面显示数据账套不一致，请单击查询再进行操作',
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
			if(TypeCode!=ShowDataTypeCode){
				$("#SelectedTypeCode").tips({
					side:3,
		            msg:'查询条件中所选类型与页面显示数据类型不一致，请单击查询再进行操作',
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
			if(DepartCode!=ShowDataDepartCode){
				$("#SelectedDepartCode").tips({
					side:3,
		            msg:'查询条件中所选责任中心与页面显示数据责任中心不一致，请单击查询再进行操作',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedDepartCode").focus();
				return false;
			}
        	window.location.href='<%=basePath%>dataInputHorizontal/excel.do?'
				+ 'SelectedDepartCode='+$("#SelectedDepartCode").val()
	            + '&SelectedCustCol7='+$("#SelectedCustCol7").val()
	            + '&SelectedTypeCode='+$("#SelectedTypeCode").val()
	            + '&ShowDataDepartCode='+ShowDataDepartCode
	            + '&ShowDataCustCol7='+ShowDataCustCol7
	            + '&ShowDataTypeCode='+ShowDataTypeCode
	            + '&SystemDateTime='+SystemDateTime;
        }

 	</script>
</html>