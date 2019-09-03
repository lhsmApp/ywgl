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
    <!-- 对JQGrid 列标题和单元格内容换行 -->
    <style>
        .ui-jqgrid tr.jqgrow td 
        {
            /* jqGrid cell content wrap  */
	        white-space: normal !important;
            height :auto;
        }
 
        th.ui-th-column div
        {
	        /* jqGrid columns name wrap  */
	        white-space:normal !important;
	        height:auto !important;
	        padding:0px;
        }
        /* 边框 */
        .ui-jqgrid tr.jqgrow td{border-right:0.5px solid #000000;border-bottom:0.5px solid #000000} 
        .ui-jqgrid{border-left:0.5px solid #000000;border-top:0.5px solid #000000}
        
        /* 竖着显示 */
        .WriteErect{
            Writing-mode:tb-rl;
            text-align:center;
            margin:auto;
            }
        /*  */
        /*  */
        /*  */
        /*  */
        .SelectBG{
            background-color:#AAAAAA;
            }
        .SelectTextLeft{
            align: left;
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
						<span class="label label-xlg label-success arrowed-right">东部管道</span>
						<!-- arrowed-in-right -->
									<span class="label label-xlg label-yellow arrowed-in arrowed-right"
									    id="subTitle" style="margin-left: 2px;">开票申请单</span> 
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
											<span class="input-icon pull-left" style="margin-right: 5px;">
												<input id="SelectedBusiDate" class="input-mask-date" type="text"
												   placeholder="请输入业务区间"> 
												<i class="ace-icon fa fa-calendar blue"></i>
											</span>
											<button type="button" class="btn btn-info btn-sm" onclick="tosearch();">
												<i class="ace-icon fa fa-search bigger-110"></i>
											</button>
											<button type="button" class="btn btn-info btn-sm" onclick="exportItems();">
												导出
											</button>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
					    <div class="col-xs-12">
						    <div class="pull-center" style="text-align: center;">
								<h4 style="font-weight:bold">中国石油天然气股份有限公司西气东输管道分公司</h4>
							</div>
					    </div>
					    <div class="col-xs-12">
						    <div class="pull-center" style="text-align: center;">
								<h4 style="font-weight:bold">开票申请单</h4>
							</div>
					    </div>
					    <div class="col-xs-4">
					    	<div class="pull-left" style="margin-left:2px;">
								<span class="bigger-100">编号JSDF-12</span>
							</div>
						</div>
						<div class="col-xs-4">
							<div style="margin-left:2px;">
								<span class="bigger-100" id="kprq"></span>
							</div>
						</div>
						<div class="col-xs-4">
						    <div class="pull-right" style="margin-right: 20px;">
								<span class="bigger-100">单位：元（不含税）</span>
							</div>
					    </div>
						<div class="col-xs-12">
						    <table id="jqGridBase"></table>
						</div>
					    <div class="col-xs-4" >
					        <div class="pull-left" style="margin-left:2px;margin-top: 7px;margin-bottom: 10px;">
								<span class="bigger-100">主管领导：</span>
							</div>
						</div>
						<div class="col-xs-4" >
							<div class="pull-left" style="margin-top: 7px;margin-bottom: 10px;">
								<span class="bigger-100">财务审核：</span>
							</div>
						</div>
						<div class="col-xs-4" >
						    <div class="pull-left" style="margin-top: 7px;margin-bottom: 10px;">
								<span class="bigger-100">经办人：</span>
							</div>
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
    	
	    $(document).ready(function () { 
		    $(top.hangge());//关闭加载状态
			$('.input-mask-date').mask('999999');
			//当前期间,取自tb_system_config的SystemDateTime字段
		    var SystemDateTime = '${SystemDateTime}';
			$("#SelectedBusiDate").val(SystemDateTime);
			$("#kprq").text(SystemDateTime.substring(0,4)+"年"+SystemDateTime.substring(4,6)+"月");
			$(gridBase_selector).jqGrid('GridUnload'); 
			SetStructure();
 	    });
	
		//检索
		function tosearch() {
			var BusiDate = $("#SelectedBusiDate").val(); 
			$("#kprq").text(BusiDate.substring(0,4)+"年"+BusiDate.substring(4,6)+"月");
			if(!(BusiDate!=null && $.trim(BusiDate)!="")){
				$("#SelectedBusiDate").tips({
					side:3,
		            msg:'请填写区间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedBusiDate").focus();
				return false;
			} 
			//$(gridBase_selector).jqGrid('GridUnload'); 
			//SetStructure();
			
			$(gridBase_selector).jqGrid('setGridParam',{  // 重新加载数据
				url:'<%=basePath%>kpSheet/getPageList.do?'
						+ 'BusiDate='+$("#SelectedBusiDate").val(),  
				datatype:'json'
			}).trigger("reloadGrid");
		}
	    
	    function SetStructure(){
		    //resize to fit page size
		    $(window).on('resize.jqGrid', function () {
		        $(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
			    resizeGridHeight($(gridBase_selector));
			})
		
		    $(gridBase_selector).jqGrid({
    			url: '<%=basePath%>kpSheet/getPageList.do?'
    	            + 'BusiDate='+$("#SelectedBusiDate").val(),
		        datatype: "json",
		        colModel: [
		   				{ label: '申请单位（部门）',name:'sqdw', editable: false,width:150},
		   				{ label: '财务处', name: 'cwc', editable: false,width: 300},
		   				{ label: '开票事由', name: 'kpsy',editable: false, width: 150},  
		   				{ label: '劳务管理费', name: 'lwglf',editable: false, width: 150},    
		   		],
	    	    shrinkToFit: true,
    			viewrecords: false, 
    			sortable: false,
    			rowNum: 0,
				altRows: true,
				
				//在配置里加一个toolbar: [true, "top"]，即在顶部添加一个toolbar ,然后在页面代码里加一句$("#t_JQGridName").append（这里写你想添加的东西，比如想在toolbar里加一个table就写           "<table>表格</table>"）。另外注意#后的格式是“t_你的JQGrid的名字”。
	            
				pgbuttons: false, // 分页按钮是否显示 
				pginput: false, // 是否允许输入分页页数 
				
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
		    
		    //隐藏表头 全部隐藏
		    $('.ui-jqgrid-hdiv').hide();
	    }

        /**
         * 导出
         */
        function exportItems(){
        	<%-- window.location.href='<%=basePath%>kpSheet/excel.do?'
	            + 'BusiDate='+$("#SelectedBusiDate").val(); --%>
        }
	</script>
</body>
</html>