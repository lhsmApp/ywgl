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
<%@ include file="../system/index/topWithJqgrid.jsp"%>

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
					<div class="page-header">
						<span class="label label-xlg label-success arrowed-right">财务核算</span>
						<!-- arrowed-in-right -->
						<span
							class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">字典数据传输</span> <span
							style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGrid'))">
							<i class="ace-icon fa fa-chevron-up bigger-120 blue"></i> <span>隐藏查询</span>
						</button>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box" >
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<!-- <span class="input-icon"> <input id="keywords"
												type="text" placeholder="这里输入关键词"> <i
												class="ace-icon fa fa-leaf blue"></i>
											</span> -->
											<span> 
												<select
													class="chosen-select form-control" name="dicType"
													id="dicType" data-placeholder="请选择字典类型"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择字典类型</option>
														<c:forEach items="${dicTypeList}" var="dicType">
															<option value="${dicType.DICT_CODE}">${dicType.NAME}</option>
														</c:forEach>
												</select>
											</span>
											<span style="margin-right: 5px;"> 
												<select
													class="chosen-select form-control" name="FMISACC"
													id="FMISACC" data-placeholder="请选择帐套"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择帐套</option>
														<c:forEach items="${fmisacc}" var="fmi">
															<option value="${fmi.DICT_CODE}">${fmi.NAME}</option>
														</c:forEach>
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
	<%@ include file="../system/index/foot.jsp"%>

	<!-- 最新版的Jqgrid Js，如果旧版本（Ace）某些方法不好用，尝试用此版本Js，替换旧版本JS -->
	<!-- <script src="static/ace/js/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="static/ace/js/grid.locale-cn.js" type="text/javascript"></script> -->

	<!-- 旧版本（Ace）Jqgrid Js -->
	<script src="static/ace/js/jqGrid/jquery.jqGrid.src.js"></script>
	<script src="static/ace/js/jqGrid/i18n/grid.locale-cn.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- JqGrid统一样式统一操作 -->
	<script type="text/javascript" src="static/js/common/jqgrid_style.js"></script>
	

	<script type="text/javascript"> 
	//var gridHeight=155;
	$(document).ready(function () { 
		$(top.hangge());//关闭加载状态
		 
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$("#jqGrid").jqGrid( 'setGridWidth', $(".page-content").width());
			resizeGridHeight($("#jqGrid"));
	    })
		
		$("#jqGrid").jqGrid({
			url: '<%=basePath%>dictTransfer/getPageList.do',
			datatype: "json",
			 colModel: [
				{ label: 'ID',name:'DICTIONARIES_ID', key:true,width:60,hidden:true},
				{ label: '编码',name:'DICT_CODE', width:60},
				{ label: '名称', name: 'NAME', width: 90},
				{ label: '英文', name: 'NAME_EN', width: 60},
				{ label: '排序', name: 'ORDER_BY', width: 60},
				{ label: '上级编码', name: 'PARENT_CODE', width: 80,hidden:true},
				{ label: '上级', name: 'PARENT_NAME', width: 80},
				{ label: '备注', name: 'BZ', width: 80}
			],
			reloadAfterSubmit: true, 
			//viewrecords: true, // show the current page, data rang and total records on the toolbar
			rowNum: -1,
			//rowList:[10,20,30,1000],
			pgbuttons: false,//上下按钮 
			pginput:false,//输入框
			
			sortname: 'ORDER_BY',
			
			grouping: true,
			groupingView: {
                groupField: ["PARENT_NAME"],
                groupColumnShow: [true],
                groupText: ["<b>{0}</b>"],
                groupOrder: ["asc"],
                groupDataSorted : false,
                groupSummary: [false],
                groupCollapse: false,
                plusicon : 'fa fa-chevron-down bigger-110',
				minusicon : 'fa fa-chevron-up bigger-110'
            },
			
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
			rownumbers: true, // show row numbers
            rownumWidth: 35, // the width of the row numbers columns			
			/* multiselect: true,
	        multiboxonly: true, */
	        //editurl: "<%=basePath%>dictTransfer/edit.do",//nothing is save
		});
		
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
				refreshicon : 'ace-icon fa fa-refresh blue',
				view: false,
				viewicon : 'ace-icon fa fa-search-plus grey',
			},
			{
				//edit record form
			},
			 {
				//new record form
			},
			{
				//删除
			},
			{
				//search form
			}
		);
	
        //上传
        $('#jqGrid').navButtonAdd('#jqGridPager',
        {
     	   /* bigger-150 */
            buttonicon: "ace-icon fa fa-cloud-upload green",
            title: "上传",
            caption: "上传",
            position: "last",
            onClickButton: batchSave
        });
 	});
	
	//批量传输
	function batchSave(e) {
		if($("#FMISACC").val()==""){
			bootbox.dialog({
				message: "<span class='bigger-110'>请您先选择帐套,然后再进行上传!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
	        return;
		}
		
		var listData =new Array();
		//var ids = $("#jqGrid").jqGrid('getGridParam','selarrrow');
		var ids = $("#jqGrid").jqGrid('getDataIDs');
		//console.log(ids);
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
			url: '<%=basePath%>dictTransfer/dictTransfer.do?',
	    	//data: rowData,//可以单独传入一个对象，后台可以直接通过对应模型接受参数。但是传入Array（listData）就不好用了，所以传list方式需将List转为Json字符窜。
			//data: '{"rows":listData}',
			data:{BILL_OFF:$("#FMISACC").val(),DATA_ROWS:JSON.stringify(listData)},
	    	dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$("#jqGrid").trigger("reloadGrid");  
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'传输成功',
			            bg:'#009933',
			            time:3
			        });
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'传输失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(response) {
	    		console.log(response);
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'传输失败,'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
		});
		}
	
		//检索
		function tosearch() {
			//var keywords = $("#keywords").val();
			var dicType=$("#dicType").val();
			$("#jqGrid").jqGrid('setGridParam',
					{  // 重新加载数据
						//url:'<%=basePath%>dictTransfer/getPageList.do?keywords='+ keywords+'&DICT_CODE='+dicType,
						url:'<%=basePath%>dictTransfer/getPageList.do?DICT_CODE='+dicType,
						datatype : 'json',
						page : 1
					}).trigger("reloadGrid");
		}
	</script>
</body>
</html>