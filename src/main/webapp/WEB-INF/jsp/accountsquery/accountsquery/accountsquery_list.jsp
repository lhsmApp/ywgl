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

    <style>
		.page-header{
			padding-top: 9px;
			padding-bottom: 9px;
			margin: 0 0 8px;
		}
	</style>
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
							id="subTitle" style="margin-left: 2px;">对账查询</span> 
                        <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
								
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondiFour($('#jqGridBase'),$('#jqGridDetail'),null,true)">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>显示查询</span>
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
							<div class="widget-box" hidden>
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<span class="input-icon pull-left" style="margin-right: 5px;">
												<input id="SelectedBusiDate" class="input-mask-date" type="text"
												placeholder="请输入业务区间"> <i
												class="ace-icon fa fa-calendar blue"></i>
											</span>
											<span class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
												<div class="selectTree" id="selectTree" multiMode="true"
												    allSelectable="false" noGroup="false"></div>
											    <input type="text" id="SelectedDepartCode" hidden></input>
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
					
					<div class="row">
						<div class="col-xs-12">
							<div class="tabbable">
								<ul class="nav nav-tabs padding-18">
									<li class="active">
									<a data-toggle="tab" href="#voucherTransfer"> <i
											class="green ace-icon fa fa-cogs bigger-120"></i> 导入差异
									</a></li>
									<li>
									<a data-toggle="tab" href="#voucherMgr"> <i
											class="orange ace-icon fa fa-cog bigger-120"></i> 对账差异
									</a></li>
								</ul>
								<div class="tab-content no-border ">
									<table id="jqGridDetail"></table>
									<div id="jqGridPagerDetail"></div>
								</div>
							</div>
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
    var gridDetail_selector = "#jqGridDetail";  
    var pagerDetail_selector = "#jqGridPagerDetail";  

	var which;
	var jqGridColModel;
	var TabType = 1;
	
	$(document).ready(function () {
		$(top.hangge());//关闭加载状态
		$('.input-mask-date').mask('999999');
	    
		//当前期间,取自tb_system_config的SystemDateTime字段
	    var SystemDateTime = '${SystemDateTime}';
		$("#SelectedBusiDate").val(SystemDateTime);
		//前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	    jqGridColModel = eval("(${jqGridColModel})");//此处记得用eval()行数将string转为array
	    
		//resize to fit page size
		//$(window).on('resize.jqGrid', function () {
		//	$(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
		//	$(gridDetail_selector).jqGrid( 'setGridWidth', $(".page-content").width());
		//	var gridHeight = 424 + 398;
		//	resizeGridHeightFour($(gridBase_selector), gridHeight * (2.6/5));
		//	resizeGridHeightFour($(gridDetail_selector), gridHeight * (2.4/5));
	    //});
		
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
			if(which!='${pd.which}'){
				window.location.href="<%=basePath%>accountsquery/list.do?SelectedTableNo="+which;
			}
		});
		
		//tab页切换
		$('.nav-tabs li').on('click', function(e){
			if($(this).hasClass('active')) return;
			var target = $(this).find('a');
			
			if(target.attr('href')=='#voucherTransfer'){
				TabType=1;
			}else{
				TabType=2;
			}
			getDetail();
		});
		
		$(gridBase_selector).jqGrid({
			url: '<%=basePath%>accountsquery/getPageList.do?SelectedTableNo='+which
            +'&SelectedBusiDate='+$("#SelectedBusiDate").val()
            +'&SelectedDepartCode='+$("#SelectedDepartCode").val(),
     			datatype: "json",
    			colModel: jqGridColModel,
    			viewrecords: true, 
    			shrinkToFit: false,
    			rowNum: 100,
    			//scroll: 1,
    			rowList: [100,200,500],
                sortable: true,
    			altRows: true, //斑马条纹
    			
    			pager: pagerBase_selector,
    			footerrow: false,
    			userDataOnFooter: false,
    			onSelectRow : function(ids) {
    				getDetail();
                },  
    			
    			loadComplete : function() {
    				var table = this;
    				setTimeout(function(){
    					styleCheckbox(table);
    					updateActionIcons(table);
    					updatePagerIcons(table);
    					enableTooltips(table);
    				}, 0);
    				var ids = $(gridBase_selector).getDataIDs();
    				if(ids!=null && ids.length>0){
    					$(gridBase_selector).setSelection(ids[0]);
    					getDetail();
    				}
    			},
		});
		
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
	        		$(gridBase_selector).navSeparatorAdd(pagerBase_selector, {
	        			sepclass : "ui-separator",
	        			sepcontent: ""
	        		});
					
					function getDetail(){
						$(gridDetail_selector).GridUnload();
						
						var id=$(gridBase_selector).getGridParam('selrow');
			            var rowData = $(gridBase_selector).getRowData(id);
			            
						//var deptcode = rowData.DEPT_CODE__;

						var listData =new Array();
			            listData.push(rowData);

			            var detailColModel = "[]";
						$.ajax({
							type: "GET",
							url: '<%=basePath%>accountsquery/getDetailColModel.do?SelectedTableNo='+which+'&SelectedTabType='+TabType,
					    	data: {GetDetailTransferList:JSON.stringify(listData)},
							dataType:'json',
							cache: false,
							success: function(response){
								if(response.code==0){
									$(top.hangge());//关闭加载状态
									detailColModel = response.message;

						            detailColModel = eval(detailColModel);
						            var childGridURL = '<%=basePath%>accountsquery/getDetailList.do?SelectedTableNo='+which+'&SelectedTabType='+TabType;
						            
						            $(gridDetail_selector).jqGrid({
						                url: childGridURL,
						                postData: {GetDetailListTransferList:JSON.stringify(listData)},
						                mtype: "GET",
						                datatype: "json",
						                colModel: detailColModel,
						                viewrecords: true,
						    			shrinkToFit: false,
						                rowNum: 0,	
						    			scroll: 1,
						    			sortable: true,
						    			sortname: 'USER_CODE',
						                //width: '100%',
						                //height: '100%',
										//pgbuttons: false, // 分页按钮是否显示 
										//pginput: false, // 是否允许输入分页页数 
						    			altRows: true, //斑马条纹

						                pager: pagerDetail_selector,
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
						    	    
						    		resizeGridHeightFour($(gridBase_selector),$(gridDetail_selector),null,true);

						    		$(gridDetail_selector).navGrid(pagerDetail_selector, 
						    				{
						    			        edit: false,
						    		            editicon : 'ace-icon fa fa-pencil blue',
						    		            add: false,
						    		            addicon : 'ace-icon fa fa-plus-circle purple',
						    		            del: false,
						    		            delicon : 'ace-icon fa fa-trash-o red',
						    		            search: false,
						    		            searchicon : 'ace-icon fa fa-search orange',
						    		            refresh: false,
						    		            refreshicon : 'ace-icon fa fa-refresh green',
						    		            view: false,
						    		            viewicon : 'ace-icon fa fa-search-plus grey',
						    	        });
									$(gridDetail_selector).navButtonAdd(pagerDetail_selector, {
							             caption : "",
							             id : "exportItems",
							             buttonicon : "ace-icon fa fa-cloud-download",
							             onClickButton : exportItems,
							             position : "last",
							             title : "导出",
							             cursor : "pointer"
							         });
								}else{
									$(top.hangge());//关闭加载状态
									$("#subTitle").tips({
										side:3,
							            msg:'获取结构失败：'+response.message,
							            bg:'#cc0033',
							            time:3
							        });
								}
							},
					    	error: function(response) {
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'获取结构出错:'+response.responseJSON.message,
						            bg:'#cc0033',
						            time:3
						        });
					    	}
						});
					}
					
					/**
					 * 导出
					 */
				    function exportItems(){
						var ids = $(gridDetail_selector).getDataIDs();
						if(!(ids!=null && ids.length>0)){
							bootbox.dialog({
								message: "<span class='bigger-110'>没有任何内容!</span>",
								buttons: 			
								{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
							});
						} else {
							var listData =new Array();
							
							//遍历访问这个集合  
							$(ids).each(function (index, id){  
					            var rowData = $(gridDetail_selector).getRowData(id);
					            listData.push(rowData);
							});
							
							var id=$(gridBase_selector).getGridParam('selrow');
				            var rowData = $(gridBase_selector).getRowData(id);
							var deptcode = rowData.DEPT_CODE__;
							
					    	window.location.href='<%=basePath%>accountsquery/excel.do';
							
						}
				    }
				});
	
	//检索
	function tosearch() {
		$(gridDetail_selector).GridUnload();
		$(gridBase_selector).setGridParam({  // 重新加载数据 
			url:'<%=basePath%>accountsquery/getPageList.do?SelectedTableNo='+which
            +'&SelectedBusiDate='+$("#SelectedBusiDate").val()
            +'&SelectedDepartCode='+$("#SelectedDepartCode").val(),  
			datatype:'json',
		      page:1
		}).trigger("reloadGrid");
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
		});
		//赋给data属性
		$("#selectTree").data("data",defaultNodes);  
		$("#selectTree").render();
		$("#selectTree2_input").val("请选择单位");
	}
</script>
</html>