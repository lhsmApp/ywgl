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
									    id="subTitle" style="margin-left: 2px;">数据导入</span> 
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
											<span class="pull-left" style="margin-right: 5px;">
													<select class="chosen-select form-control"
														name="KPI_CODE" id="KPI_CODE"
														style="vertical-align: top; height:32px;width: 150px;">
														<option value="">请选择考核指标</option>
														<c:forEach items="${listKPI}" var="each">
															<%-- <option value="${each.KPI_CODE}">${each.KPI_NAME}</option> --%>
															
															<option value="${each.KPI_CODE}"
																<c:if test="${pd.KPI_CODE==each.KPI_CODE}">selected</c:if>>${each.KPI_NAME}
															</option>
														</c:forEach>
													</select>
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
							<ul class="nav nav-tabs" id="assess-tab">
								<li class="active" tag="kpi-tab">
									<a data-toggle="tab" href="#kpi-tab">考核指标说明</a>
								</li>

								<li tag="import-tab">
									<a data-toggle="tab" href="#import-tab">考核数据查询</a>
								</li>
							</ul>
							<div class="tab-content padding-8">
								<div id="kpi-tab" class="tab-pane active">
									<h3 class="lighter block black" style="text-align:center;"><i class="ace-icon fa fa-rss"></i>&nbsp;
										<span>考核指标说明</span>
									</h3>
									<div class="profile-user-info " >
										<div class="profile-info-row">
											<div class="profile-info-name">分类</div>
	
											<div class="profile-info-value">
												<span id="valKPI_TYPE">${KPI.KPI_TYPE}</span>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">指标代码</div>
	
											<div class="profile-info-value">
												<span id="valKPI_CODE">${KPI.KPI_CODE}</span>
											</div>
										</div>
										
										<div class="profile-info-row">
											<div class="profile-info-name"> 指标作用 </div>
	
											<div class="profile-info-value">
												<span id="valKPI_EFFECT">${KPI.KPI_EFFECT}</span>
											</div>
										</div>
										
										<div class="profile-info-row">
											<div class="profile-info-name">考核对象</div>
	
											<div class="profile-info-value">
												<span id="valASSESS_OBJECT">${KPI.ASSESS_OBJECT}</span>
											</div>
										</div>
									</div>
									<h3 class="lighter block" style="text-align:center;"><i class="ace-icon fa fa-rss"></i>&nbsp;指标评分标准</h3>
									
									<div class="profile-user-info " >
										<div class="profile-info-row">
											<div class="profile-info-name">指标名称</div>
	
											<div class="profile-info-value">
												<span id="valKPI_NAME">${KPI.KPI_NAME}</span>
											</div>
										</div>
										
										<div class="profile-info-row">
											<div class="profile-info-name">判断标准</div>
	
											<div class="profile-info-value">
												<span id="valJUDGEMENT_STANDARD">${KPI.JUDGEMENT_STANDARD}</span>
											</div>
										</div>
										
										<div class="profile-info-row">
											<div class="profile-info-name">计算公式</div>
	
											<div class="profile-info-value">
												<span id="valFORMULA">${KPI.FORMULA}</span>
											</div>
										</div>
										
									</div>
								</div>
								
								<div id="import-tab" class="tab-pane">
								    <table id="jqGridBase"></table>
								    <div id="jqGridBasePager"></div>
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
			ShowDataBusiDate = SystemDateTime;
			//当前登录人所在二级单位
		    var DepartName = '${DepartName}';
		    //$("#showDur").text('当前期间：' + SystemDateTime + ' 登录人单位：' + DepartName);
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
        	window.location.href='<%=basePath%>assessData/excel.do?'
        		+ 'SelectedBusiDate='+$("#SelectedBusiDate").val()
                +'&ShowDataBusiDate='+ShowDataBusiDate
                +'&KPI_CODE='+$("#KPI_CODE").val();
        }
	
	    //检索
	    function tosearch() {
	    	var kpiCode=$("#KPI_CODE").val();
			var selectDate=$("#SelectedBusiDate").val();
	    	
	    	$.ajax({
				type: "POST",
				url: '<%=basePath%>assessData/getKpi.do?KPI_CODE='+kpiCode,
				dataType:'json',
				cache: false,
				success: function(response){
						$("#valKPI_TYPE").text(response.KPI_TYPE);
						$("#valKPI_CODE").text(response.KPI_CODE);
						$("#valKPI_NAME").text(response.KPI_NAME);
						$("#valKPI_EFFECT").text(response.KPI_EFFECT);
						$("#valASSESS_OBJECT").text(response.ASSESS_OBJECT);
						$("#valJUDGEMENT_STANDARD").text(response.JUDGEMENT_STANDARD);
						$("#valFORMULA").text(response.FORMULA);
				},
		    	error: function() {
					$("#subTitle").tips({
						side:3,
			            msg:'获取指标信息出错',
			            bg:'#cc0033',
			            time:3
			        });
		    	}
			});
	    	
			

			$(gridBase_selector).jqGrid('GridUnload'); 
			//前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		    jqGridColModel = "[]";
			
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>assessData/getShowColModel.do?KPI_CODE='+kpiCode
    	            + '&SystemDateTime='+selectDate,
				dataType:'json',
				cache: false,
				success: function(response){
					if(response.code==0){
						$(top.hangge());//关闭加载状态
						var mes = response.message;
						mes = mes.replace("[", "").replace("]", "");
						if($.trim(mes)!=""){
						    //前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
					        jqGridColModel = eval("(" + response.message + ")");//此处记得用eval()行数将string转为array
						    SetStructure();
						}
					}else{
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side:3,
				            msg:'获取显示结构失败：'+response.message,
				            bg:'#cc0033',
				            time:3
				        });
					}
				},
		    	error: function(response) {
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取显示结构出错：'+response.responseJSON.message,
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
    			gridHeight=240;
    			resizeGridHeight($(gridBase_selector),gridHeight);
    	    });
    		
    		$(gridBase_selector).jqGrid({
    			url: '<%=basePath%>assessData/getPageList.do?'
            		+ 'SelectedBusiDate='+$("#SelectedBusiDate").val()
                    + '&ShowDataBusiDate='+ShowDataBusiDate
                    + '&KPI_CODE='+$("#KPI_CODE").val()
                    + '&funcType='+'${pd.funcType}',
    			datatype: "json",
    			colModel: jqGridColModel,
    			reloadAfterSubmit: true, 
    			viewrecords: true, 
    			shrinkToFit:false,
    			rowNum: 100,
    			//rowList: [100,200,500],
                multiselect: true,
                multiboxonly: true,
                sortable: true,
    			altRows: true, //斑马条纹
    			editurl: '<%=basePath%>assessData/edit.do?'
            		+ 'SelectedBusiDate='+$("#SelectedBusiDate").val()
                    + '&ShowDataBusiDate='+ShowDataBusiDate
                    + '&KPI_CODE='+$("#KPI_CODE").val(),
    			
    			pager: pagerBase_selector,
    			footerrow: true,
    			userDataOnFooter: true,

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