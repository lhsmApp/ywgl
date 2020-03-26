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
								    <span class="label label-xlg label-success arrowed-right">考核管理</span>
									<!-- arrowed-in-right --> 
									<span class="label label-xlg label-yellow arrowed-in arrowed-right"
									    id="subTitle" style="margin-left: 2px;">排行榜</span> 
                                    <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
								
									<button id="btnQuery" class="btn btn-white btn-info btn-sm"
										onclick="showQueryCondi($('#jqGridBase'),gridHeight)">
										<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
									</button>
								
						            <div class="pull-right">
									    <span class="label label-xlg label-blue arrowed-left"
									        id = "showDur" style="background:#428bca; margin-right: 2px;"></span> 
								    </div>
					</div><!-- /.page-header -->
			
						<div class="row">
						<div class="col-xs-12">
							<div class="widget-box"  >
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
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
	<!-- 上传控件 -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<script type="text/javascript" src="static/js/common/cusElement_style.js"></script>
	<script type="text/javascript" src="static/js/util/toolkit.js"></script>
	<script src="static/ace/js/ace/ace.widget-box.js"></script>
	
	<script type="text/javascript"> 
        var gridBase_selector = "#jqGridBase";  
        var pagerBase_selector = "#jqGridBasePager";  

	    //页面显示的数据的责任中心，在tosearch()里赋值
	    var ShowDataBusiDate = "";
		
        //前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
        var jqGridColModel = "[]";
        var gridHeight;
    
	    $(document).ready(function () {
			$(top.hangge());//关闭加载状态
			$('.input-mask-date').mask('999999');
			$(gridBase_selector).jqGrid('GridUnload'); 
		    
			//当前期间,取自tb_system_config的SystemDateTime字段
	        var SystemDateTime = '${SystemDateTime}';
			$("#SelectedBusiDate").val(SystemDateTime);

		    $("#showDur").text('当前期间：' + SystemDateTime);
	        //前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	        jqGridColModel = "[]";
            var mes = "${jqGridColModel}";
			var mesCheckNull = mes.replace("[", "").replace("]", "");
			if($.trim(mesCheckNull)!=""){
			    //前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		        jqGridColModel = eval("(" + mes + ")");//此处记得用eval()行数将string转为array
			}
            var jqGridColModelMessage = '${jqGridColModelMessage}';
            if(jqGridColModelMessage!=null && $.trim(jqGridColModelMessage)!=""){
				$("#subTitle").tips({
					side:3,
		            msg:'获取显示结构失败：'+jqGridColModelMessage,
		            bg:'#cc0033',
		            time:3
		        });
            } else {
			    SetStructure();
            }
		});
	    

        /**
         * 导出
         */
        function exportItems(){
        	window.location.href='<%=basePath%>assessData/excelRank.do?'
        		+ 'BUSI_DATE='+$("#SelectedBusiDate").val();
        }
	
	    //检索
	    function tosearch() {
			var selectDate=$("#SelectedBusiDate").val();
			$(gridBase_selector).jqGrid('setGridParam',{  // 重新加载数据
			url:'<%=basePath%>assessData/getRankList.do?BUSI_DATE='+selectDate,
							datatype : 'json',
							page : 1
				}).trigger("reloadGrid");
		}  
    
        function SetStructure(){
    		//resize to fit page size
    		$(window).on('resize.jqGrid', function () {
    			$(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
    			//$(gridBase_selector).jqGrid( 'setGridHeight', $(window).height() - 240);
    			//gridHeight=240;
    			resizeGridHeight($(gridBase_selector));
    	    });
    		
    		$(gridBase_selector).jqGrid({
    			url: '<%=basePath%>assessData/getRankList.do?'
            		+ 'BUSI_DATE='+$("#SelectedBusiDate").val(),
    			datatype: "json",
    			colModel: jqGridColModel,
    			reloadAfterSubmit: true, 
    			viewrecords: true, 
    			shrinkToFit:false,
    			rowNum: -1,
    			//rowList: [100,200,500],
                multiselect: false,
                multiboxonly: false,
                sortable: true,
    			altRows: true, //斑马条纹
    			editurl: '',
    			pager: pagerBase_selector,
    			footerrow: false,
    			//userDataOnFooter: true,

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
			    
	        },
	        {
				//new record form 
	        },
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
	             caption : "导出",
	             buttonicon : "ace-icon fa fa-cloud-download",
	             onClickButton : exportItems,
	             position : "last",
	             title : "导出",
	             cursor : "pointer"
	         });
        }

 	</script>
</html>