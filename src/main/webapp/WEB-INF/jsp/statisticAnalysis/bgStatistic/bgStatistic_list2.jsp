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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- jsp文件头和头部 ，其中包含旧版本（Ace）Jqgrid Css-->
<%@ include file="../../system/index/topWithJqgrid.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
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
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">							
						<!-- 检索  -->
							<form action="approvalconfig/listBgStatistic.do" method="post" name="Form" id="Form">
							<table style="margin-top:5px;">
								<tr>							
								<td style="padding-left:2px;">
									<div class="input-group input-group-sm">
										<input type="text" id="START_DATE" name="START_DATE"  class="form-control"  data-date-format="yyyy-mm-dd" placeholder="请选择开始日期"/>
										<span class="input-group-addon">
											<i class="ace-icon fa fa-calendar" ></i>
										</span>
									</div>
								</td>	
								<td style="padding-left:2px;">
									<div class="input-group input-group-sm">
										<input type="text" id="END_DATE" name="END_DATE"  class="form-control"  data-date-format="yyyy-mm-dd" placeholder="请选择结束日期"/>
										<span class="input-group-addon">
											<i class="ace-icon fa fa-calendar" ></i>
										</span>
									</div>
								</td>							
	<!-- 								<td style="padding-left:2px;"><input class="span10 date-picker" name="START_DATE" id="START_DATE"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td> -->
	<!-- 								<td style="padding-left:2px;"><input class="span10 date-picker" name="END_DATE" name="END_DATE"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td> -->
									<td><label> <i class="ace-icon  bigger-110"></i>请选择单位：</label> </td>
									<td>
											<div style="margin:10px 0px;">
													<input type="hidden" name="UNIT_CODE" id="UNIT_CODE"   />
													<div class="selectTree" id="selectTree" style="float:none;display:block;"></div>												
												</div>				
										</td>	
									<td style="padding-left:2px;">																		 
													<button type="button" class="btn btn-info btn-xs" onclick="tosearch();">
													    <i class="ace-icon fa fa-search bigger-110"></i>
													</button>	
									</td>	
<!-- 									<td style="padding-left:10px;">   -->
<!-- 	   									<div class="btn-toolbar inline middle no-margin"> -->
<!-- 											<div data-toggle="buttons" class="btn-group no-margin"> -->
<!-- 												<label class="btn btn-sm btn-primary active"> -->
<!-- 													<input type="radio" class="level_select"  name="level_select" value="1" checked>本周 -->
<!-- 												</label> -->
<!-- 												<label class="btn btn-sm btn-primary">  -->
<!-- 													<input type="radio" class="level_select" name="level_select" value="2">本月 -->
<!-- 												</label> -->
<!-- 												<label class="btn btn-sm btn-primary"> -->
<!-- 													<input type="radio" class="level_select" name="level_select" value="3">今日 -->
<!-- 												</label>								 -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</td>	 -->
									<td style="padding-left:6px;"><a class="btn btn-primary btn-xs inline pull-right" onclick="toExcel()">
								<i class="ace-icon fa fa-share bigger-110"></i> <span>导出</span>
							</a></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">	
						<div class="tab-content no-border ">
				            <table id="jqGridBase"></table>
				            <div id="jqGridBasePager"></div>
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

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
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
    var gridBase_selector = "#jqGridBase";  
    var pagerBase_selector = "#jqGridBasePager";  
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			$(gridBase_selector).jqGrid('GridUnload'); 
			//前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		    jqGridColModel = "[]";
			top.jzts();
			$.ajax({
				type: "POST",
				 url: '<%=basePath%>approvalconfig/listStatistic.do?START_DATE='+$("#START_DATE").val()
	             +'&END_DATE='+$("#END_DATE").val()
	             +'&UNIT_CODE='+$("#UNIT_CODE").val(),
				dataType:'json',
				cache: false,
				success: function(response){
						$(top.hangge());//关闭加载状态			
						    SetStructure();
					
				},
		    	error: function(response) {
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取数据出错',
			            bg:'#cc0033',
			            time:3
			        });
		    	}
			});
		}
		$(function() {
			SetStructure();
			//开始日期
			$("#START_DATE" ).datepicker({
			showOtherMonths: true,
			selectOtherMonths: false,
			autoclose: true,
			todayHighlight: true
			});
				//结束日期
				$("#END_DATE" ).datepicker({
				showOtherMonths: true,
				selectOtherMonths: false,
				autoclose: true,
				todayHighlight: true
			});
			});
		$(window).on('resize.jqGrid', function () {
			$(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
			gridHeight=236;
			resizeGridHeight($(gridBase_selector),gridHeight);
			//resizeGridHeight($(gridBase_selector),null,true);
	    });
		function SetStructure(){
			 jqGridColModel =[ //这里会根据index去解析jsonReader中root对象的属性，填充cell   
//	             {name:'id', index:'id', width:200, halign:"center",align:"center",sortable:false}, 
	             {name:'BUSINESS_NAME', index:'BUSINESS_NAME', width:200,halign:"center",align:"center", sortable:false},   
	             {name:'total', index:'total', width:200,halign:"center",align:"center", sortable:false},   
	             {name:'solve', index:'solve', width:200,halign:"center",align:"center", sortable:false},   
	             {name:'solverate', index:'solverate', width:200,halign:"center",align:"center", sortable:false}
	         ],   
			 $(gridBase_selector).jqGrid({ 
		    	 url: '<%=basePath%>approvalconfig/listStatistic.do?START_DATE='+$("#START_DATE").val()
	             +'&END_DATE='+$("#END_DATE").val()
	             +'&UNIT_CODE='+$("#UNIT_CODE").val(),
		         datatype:"json", //为local时初始化不加载，支持json，xml等   
		         type: "POST",   
		         colNames:['变更类型', '提交变更总数', '已处理的变更','解决率'], //表头   
		         colModel:jqGridColModel,   
		         width: '100%', //数字 & 'auto','100%'   
		         height: 200,   
		         rowNum: 10, //每页记录数   
		         rowList:[10,20,30], //每页记录数可选列表   
		         pager: '#jqGridBasePager', //分页标签divID   
		         viewrecords: true, //显示记录数信息，如果这里设置为false,下面的不会显示 recordtext: "第{0}到{1}条, 共{2}条记录", //默认显示为{0}-{1} 共{2}条 scroll: false, //滚动翻页，设置为true时翻页栏将不显示  
		         /**这里是排序的默认设置，这些值会根据列表header点击排序时覆盖*/ sortable: false,   
		         sortname: "warename",   
		         sortorder: "desc",   
		       
		         caption:"变更统计", //显示查询结果表格标题   
		         rownumbers: true, //设置列表显示序号,需要注意在colModel中不能使用rn作为index   
		         rownumWidth: 20, //设置显示序号的宽度，默认为25   
		         multiselect: true, //多选框   
		         multiboxonly: true, //在点击表格row时只支持单选，只有当点击checkbox时才多选，需要multiselect=true是有效   
		         prmNames : { //如当前查询实体为ware，这些可以在查询对象的superObject中设定   
		             page: "wareDetail.page",   
		             rows: "wareDetail.rows",   
		             sort: "wareDetail.sidx",   
		             order: "wareDetail.sord",   
		             search: "wareDetail.search"   
		         },   
		         jsonReader:{ //server返回Json解析设定   
		             root: "list", //对于json中数据列表   
		             page: "page",   
		             total: "totalPage",   
		             records: "totalCount",  
		             repeatitems: false,   
		         }   
		     });
		}
		  function toExcel(){
			    	window.location.href='<%=basePath%>approvalconfig/listStatisticExcel.do?START_DATE='+$("#START_DATE").val()
		             +'&END_DATE='+$("#END_DATE").val()
		             +'&UNIT_CODE='+$("#UNIT_CODE").val();	    
		    }
		    function initComplete(){
				//下拉树
				var defaultNodes = {"treeNodes":${zTreeNodes}};
				//绑定change事件
				$("#selectTree").bind("change",function(){

					if(!$(this).attr("relValue")){
				    }else{
						$("#UNIT_CODE").val($(this).attr("relValue"));	
				    }
				});
				//赋给data属性
				$("#selectTree").data("data",defaultNodes);  
				$("#selectTree").render();
			}	
	    //$("#jqGridBase").jqGrid('navGrid','#jqGridBasePager'{edit:false,add:false,del:false,search:false});//这里设定分页bar显示的信息  
	</script>


</body>
</html>