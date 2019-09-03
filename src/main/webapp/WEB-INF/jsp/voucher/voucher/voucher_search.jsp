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
						<span class="label label-xlg label-success arrowed-right">财务核算</span>
						<!-- arrowed-in-right -->
						<span
							class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">数据传输查询</span> <span
							style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>

						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGrid'),gridHeight)">
							<i class="ace-icon fa fa-chevron-up bigger-120 blue"></i> <span>隐藏查询</span>
						</button>
						<div class="pull-right">
							<span class="green middle bolder">凭证数据类型: &nbsp;</span>

							<div class="btn-toolbar inline middle no-margin">
								<div data-toggle="buttons" class="btn-group no-margin">
									<label class="btn btn-sm btn-primary active"> <span
										class="bigger-110">合同化</span> <input type="radio" value="16" />
									</label>
									<label class="btn btn-sm btn-primary"> <span
										class="bigger-110">市场化</span> <input type="radio" value="17" />
									</label>
									<label class="btn btn-sm btn-primary"> <span
										class="bigger-110">劳务人员在建</span> <input type="radio" value="18" />
									</label>
									<label class="btn btn-sm btn-primary"> <span
										class="bigger-110">运行人员</span> <input type="radio" value="19" />
									</label>
									<label class="btn btn-sm btn-primary"> <span
										class="bigger-110">劳务用工</span> <input type="radio" value="20" />
									</label> 
									<label class="btn btn-sm btn-primary"> <span
										class="bigger-110">社保</span> <input type="radio" value="24" />
									</label> 
									<label class="btn btn-sm btn-primary"> <span
										class="bigger-110">公积金</span> <input type="radio" value="28" />
									</label>
								</div>
							</div>
						</div>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box">
								<div class="widget-body">
									<div class="widget-main">
											<form class="form-inline">
											<input name="DEPT_CODE" id="departCode"
												type="hidden" value="" />
											<!-- <span class="input-icon pull-left" style="margin-right: 5px;">
												<input id="form-field-icon-1" type="text"
												placeholder="这里输入关键词"> <i
												class="ace-icon fa fa-leaf blue"></i>
											</span> -->
											<span class="input-icon pull-left" style="margin-right: 5px;">
												<input id="busiDate" class="input-mask-date" type="text"
												placeholder="请输入业务区间"> <i
												class="ace-icon fa fa-calendar blue"></i>
											</span>
											<span class="pull-left" style="margin-right: 5px;"> 
												<select
													class="chosen-select form-control" name="FMISACC"
													id="FMISACC" data-placeholder="请选择帐套"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="0">请选择帐套</option>
														<c:forEach items="${fmisacc}" var="fmi">
															<option value="${fmi.DICT_CODE}">${fmi.NAME}</option>
														</c:forEach>
												</select>
											</span>
											<span class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
												<div class="selectTree" id="selectTree" multiMode="true"
													allSelectable="false" noGroup="false"></div>
											</span>
											<!-- <span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedBillCode" id="SelectedBillCode"
													style="vertical-align: top; height:32px;width: 150px;">
												</select>
											</span> -->
											
											<%-- <span style="margin-right: 5px;"> 
												<select
													class="chosen-select form-control" name="EMPLGRP"
													id="EMPLGRP" data-placeholder="请选择员工组"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择员工组</option>
														<c:forEach items="${emplgrp}" var="empl">
															<option value="${empl.DICT_CODE}">${empl.NAME}</option>
														</c:forEach>
												</select>
											</span> --%>
											
											<!--  <span style="margin-right: 5px;"> 
												<select
													class="chosen-select form-control" name="PARTUSERTYPE"
													id="PARTUSERTYPE" data-placeholder="请选择企业特定员工分类"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择企业特定员工分类</option>
														<c:forEach items="${partusertype}" var="part">
															<option value="${part.DICT_CODE}">${part.NAME}</option>
														</c:forEach>
												</select>
											</span>
											<span style="margin-right: 5px;"> 
												<select
													class="chosen-select form-control" name="SALARYRANGE"
													id="SALARYRANGE" data-placeholder="请选择工资范围"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择工资范围</option>
														<c:forEach items="${salaryrange}" var="salary">
															<option value="${salary.DICT_CODE}">${salary.NAME}</option>
														</c:forEach>
												</select>
											</span> -->
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
	//var jqGridColModelSub;
	var which='1';
	var gridHeight;
	var jqGridColModel;
	var voucherType;
	$(document).ready(function () {
		$(top.hangge());//关闭加载状态
		$('.input-mask-date').mask('999999');
		$("#busiDate").val('${pd.busiDate}');
		//dropDownStyle();
		//前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	    //jqGridColModel = eval("(${jqGridColModel})");//此处记得用eval()行数将string转为array
	    //jqGridColModelSub = eval("(${jqGridColModelSub})");
	    
	    
	    
	    //jqGridColModel1=jqGridColModel.concat();
	    //jqGridColModel1.unshift(certCode,revcertCode);
		//resize to fit page size
		
		
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
				window.location.href="<%=basePath%>voucher/voucherSearch.do?TABLE_CODE="+which;
			}
		});
	});
	
	function SetStructure(){
		var certCode={label: '凭证号',name:'CERT_CODE', width:100};
	    var revcertCode={label: '冲销凭证号',name:'REVCERT_CODE', width:100};
	    var certBillDate={label: '凭证日期',name:'CERT_BILL_DATE', width:100,hidden:true};
	    jqGridColModel.unshift(certCode,revcertCode,certBillDate);
	    
	  	//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$("#jqGrid").jqGrid( 'setGridWidth', $(".page-content").width());
			if(${HasUserData}){
				//gridHeight=251;
				resizeGridHeight($("#jqGrid"),gridHeight,true);
			}else{
				//gridHeight=213;
				resizeGridHeight($("#jqGrid"),gridHeight,false);
			}
			
	    });

		$("#jqGrid").jqGrid({
			url: "<%=basePath%>voucher/getPageList.do",
			postData:{"VOUCHER_TYPE":2,"TABLE_CODE":"${pd.which}","BUSI_DATE":$("#busiDate").val()},
			datatype: "json",
			colModel: jqGridColModel,
			reloadAfterSubmit: true, 
			//autowidth:true,
			shrinkToFit:false,
			viewrecords: false,
			rowNum: 10,
			//rowList:[10,20,30,10000],
			pgbuttons: false,//上下按钮 
			pginput:false,//输入框
			//height: '100%', 
			width:'100%',
			sortname: 'BILL_CODE',
			footerrow: ${HasUserData},
			userDataOnFooter: ${HasUserData}, // the calculated sums and/or strings from server are put at footer row.
			grouping: true,
			groupingView: {
                groupField: ["DEPT_CODE"],
                groupColumnShow: [true],
                groupText: ["<b>{0}</b>"],
                groupOrder: ["asc"],
                groupDataSorted : false,
                groupSummary: [true],
                groupCollapse: false,
                plusicon : 'fa fa-chevron-down bigger-110',
				minusicon : 'fa fa-chevron-up bigger-110'
            },

			pager: "#jqGridPager",
			//pagerpos: 'left' ,
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
			//toppager: true,
			rownumbers: true, // show row numbers
            rownumWidth: 35, // the width of the row numbers columns
			
			multiselect: false,
			//multikey: "ctrlKey",
	        //multiboxonly: true,
	        //editurl: "<%=basePath%>jqgridJia/edit.do",//nothing is saved
	        
	      	//subgrid options
			subGrid : true,
			//subGridModel: [{ name : ['No','Item Name','Qty'], width : [55,200,80] }],
			//datatype: "xml",
			subGridOptions : {
				plusicon : "ace-icon fa fa-plus center bigger-110 blue",
				minusicon  : "ace-icon fa fa-minus center bigger-110 blue",
				openicon : "ace-icon fa fa-chevron-right center orange"
			},
			subGridRowExpanded: showFirstChildGrid,
			/*beforeSelectRow: function (rowid, e) { 
				  i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),  
			       cm = $("#jqGrid").jqGrid('getGridParam', 'colModel');  
			   return (cm[i].name === 'cb');   
			},*/
			cellEdit: true,
			gridComplete:function(){

		    }
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
				search: true,
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
				//delete record form
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
			}
		);
	}
	
	// the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function showFirstChildGrid(parentRowID, parentRowKey) {
    	console.log(parentRowID+"  "+parentRowKey);
    	
    	var parentRowData=$("#jqGrid").jqGrid('getRowData',parentRowKey);
    	var BILL_CODE = parentRowData.BILL_CODE;
    	var DEPT_CODE = parentRowData.DEPT_CODE;
    	var CUST_COL7=parentRowData.CUST_COL7;
    	console.log(BILL_CODE);
    	console.log(DEPT_CODE);
    	console.log(CUST_COL7);
    	
        var childGridID = parentRowID + "_table";
        var childGridPagerID = parentRowID + "_pager";
        // send the parent row primary key to the server so that we know which grid to show
        var childGridURL = '<%=basePath%>voucher/getFirstDetailList.do?'
        	+'TABLE_CODE='+which
            +'&BILL_CODE='+BILL_CODE;
        //childGridURL = childGridURL + "&parentRowID=" + encodeURIComponent(parentRowKey)

        // add a table and pager HTML elements to the parent grid row - we will render the child grid here
        $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');

        $("#" + childGridID).jqGrid({
            url: childGridURL,
            mtype: "GET",
            datatype: "json",
            colModel: jqGridColModel,
            page: 1,
            width: '100%',
            //height: '100%',
            rowNum: 0,	
            //pager: "#" + childGridPagerID,
			pgbuttons: false, // 分页按钮是否显示 
			pginput: false, // 是否允许输入分页页数 
            viewrecords: false,
            recordpos: "left", // 记录数显示位置 
            
			shrinkToFit: false,
			autowidth:false,
			altRows: true, //斑马条纹
            
			//footerrow: true,
			//userDataOnFooter: true,

			subGrid: true,
			subGridOptions: {
				plusicon : "ace-icon fa fa-plus center bigger-110 blue",
				minusicon  : "ace-icon fa fa-minus center bigger-110 blue",
				openicon : "ace-icon fa fa-chevron-right center orange"
            },
            subGridRowExpanded: showSecondChildGrid,

			scroll: 1,
            
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
        if(voucherType=="1"){
        	jQuery("#" + childGridID).hideCol(['CERT_CODE','REVCERT_CODE']);
        }else{
        	jQuery("#" + childGridID).showCol(['CERT_CODE','REVCERT_CODE']);
        }
        
    };
	
  //显示明细信息
	// the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function showSecondChildGrid(parentRowID, parentRowKey) {
    	/* console.log(parentRowID+"  "+parentRowKey);
    	var parentRowData=$("#jqGrid").jqGrid('getRowData',parentRowKey);
    	var DEPT_CODE = parentRowData.DEPT_CODE; */
    	
    	console.log(parentRowID+"  "+parentRowKey);
    	var gridSelect = parentRowID.toString().substring(0, parentRowID.toString().length - parentRowKey.toString().length - 1);
    	console.log("gridSelect  "+gridSelect);
		var parentRowData = $("#" + gridSelect).getRowData(parentRowKey);
    	var DEPT_CODE = parentRowData.DEPT_CODE;
    	var fmi = parentRowData.CUST_COL7;
    	console.log(DEPT_CODE);
    	
    	var detailColModel = "[]";
		$.ajax({
			type: "GET",
			url: '<%=basePath%>voucher/getDetailColModel.do?',
	    	data: {DEPT_CODE:DEPT_CODE,TABLE_CODE:which,FMISACC:fmi},
			dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					detailColModel = response.message;
	            	detailColModel = eval(detailColModel);
			        var childGridID = parentRowID + "_table";
			        var childGridPagerID = parentRowID + "_pager";
			     	//send the parent row primary key to the server so that we know which grid to show
			        var childGridURL = '<%=basePath%>voucher/getDetailList.do?BILL_CODE='+parentRowData.BILL_CODE+'&TABLE_CODE='+which;
			        //childGridURL = childGridURL + "&parentRowID=" + encodeURIComponent(parentRowKey)
			        // add a table and pager HTML elements to the parent grid row - we will render the child grid here
			        
			        var listData =new Array();
				    listData.push(parentRowData);
			        
			        $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
			        $("#" + childGridID).jqGrid({
			            url: childGridURL,
			            postData: {DataRows:JSON.stringify(listData)},
			            mtype: "GET",
			            datatype: "json",
			            page: 1,
			            colModel: detailColModel,
			            width: '100%',
			            //height: '100%',
			            rowNum: 0,	
			            /* shrinkToFit:false,
			            autowidth:false, */
			            toppager : "#"+childGridPagerID,
			            pgbuttons: false,//上下按钮 
						pginput:false,//输入框
			            //toolbar: [true,"top"],
			            //toppager:true ,
			            //pagerpos:"left",
			            //pager: "#" + childGridPagerID,
			            loadComplete : function() {
							var table = this;
							setTimeout(function(){
								styleCheckbox(table);
								updateActionIcons(table);
								updatePagerIcons(table);
								enableTooltips(table);
							}, 0);
						},
						gridComplete:function(){
						    //$("#" + childGridID).parents(".ui-jqgrid-bdiv").css("overflow-x","hidden");
							//$(".ui-jqgrid-btable").removeAttr("style");
						}
			        });
			        
			      	//navButtons
					jQuery("#" + childGridID).jqGrid('navGrid',"#"+childGridPagerID,
						{ 	//navbar options
							edit: false,
							add: false,
							del: false,
							search: true,
							searchicon : 'ace-icon fa fa-search orange',
							/* searchtext:"查询明细",
							searchtitle:"查询明细", */
							refresh: false,
							refreshicon : 'ace-icon fa fa-refresh blue',
							view: false,
							cloneToTop :true
						},
						{
							//edit record form
						},
						{
							//new record form
						},
						{
							//delete record form
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
						}
					);
				}else{
					$("#subTitle").tips({
						side:3,
			            msg:'获取结构失败：'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},error: function(response) {
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
	
	//检索
	function tosearch() {
		if($("#FMISACC").val()=="0"){
	        bootbox.dialog({
				message: "<span class='bigger-110'>请您先选择帐套,然后再进行查询!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
	        return;
        }
		$("#jqGrid").jqGrid('GridUnload'); 
        //前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	    jqGridColModel = "[]";
	    top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>voucher/getShowColModel.do?'
				+'TABLE_CODE='+which
                +'&SelectedDepartCode='+$("#departCode").val()
                +'&SelectedCustCol7='+$("#FMISACC").val()
	            +'&SelectedBillCode='+$("#SelectedBillCode").val(),
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
		
		
		/* var busiDate = $("#busiDate").val(); 
		var deptCode = $("#departCode").val(); 
		var empl = $("#EMPLGRP").val(); 
		var fmi = $("#FMISACC").val(); 
		var partUserType = $("#PARTUSERTYPE").val(); 
		var salaryRange = $("#SALARYRANGE").val(); 
		$("#jqGrid").jqGrid("setGridParam",{postData:{"VOUCHER_TYPE":voucherType,"TABLE_CODE":'${pd.which}',"BUSI_DATE":busiDate,"DEPT_CODE":deptCode,"FMISACC":fmi,"USER_CATG":partUserType,"SAL_RANGE":salaryRange}})
		.trigger("reloadGrid"); */
	}  
	
	//加载单位树
	function initComplete(){
		//下拉树
		var defaultNodes = {"treeNodes":${zTreeNodes}};
		//绑定change事件
		$("#selectTree").bind("change",function(){
			if($(this).attr("relValue")){
				$("#departCode").val($(this).attr("relValue"));
		    }else{
		    	$("#departCode").val("");
		    }
		});
		//赋给data属性
		$("#selectTree").data("data",defaultNodes);  
		$("#selectTree").render();
		$("#selectTree2_input").val("请选择单位");
	}
 	</script>
</body>
</html>