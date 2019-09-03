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
							id="subTitle" style="margin-left: 2px;">特殊凭证传输</span> <span
							style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>

						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGrid'),gridHeight)">
							<i class="ace-icon fa fa-chevron-up bigger-120 blue"></i> <span>隐藏查询</span>
						</button>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box"  >
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<!-- <input name="DEPT_CODE" id="departCode"
												type="hidden" value="" /> -->
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
													id="FMISACC" data-placeholder="请选择帐套" onchange="getSelectTypeOptions()"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="0">请选择帐套</option>
														<c:forEach items="${fmisacc}" var="fmi">
															<option value="${fmi.DICT_CODE}">${fmi.NAME}</option>
														</c:forEach>
												</select>
											</span>
											
											
											<span class="pull-left" style="margin-right: 5px;"> 
												<!-- <input id="TYPE_CODE" readonly='true' type="text" onchange="getSelectDepartOptions()"></input> -->
												<select
													class="chosen-select form-control" name="TYPE_CODE"
													id="TYPE_CODE" data-placeholder="请选择凭证类型" onchange="getSelectDepartOptions()"
													style="vertical-align: top; height: 32px; width: 150px;">
													<option value="0">请选择凭证类型</option>
												</select>
											</span>
											
											<span class="pull-left" style="margin-right: 5px;"> 
												<select
													class="chosen-select form-control" name="DEPARTS"
													id="DEPARTS" data-placeholder="请选择单位" onchange="getSelectBillCodeOptions()"
													style="vertical-align: top; height: 32px; width: 150px;">
														<%-- <option value="0">请选择单位</option>
														<c:forEach items="${departs}" var="depart">
															<option value="${depart.DEPT_CODE}">${depart.NAME}</option>
														</c:forEach> --%>
														<option value="0">请选择单位</option>
												</select>
											</span>
											<%-- <span class="pull-left" style="margin-right: 5px;" <c:if test="${pd.departTreeSource=='0'}">hidden</c:if>>
												<div class="selectTree" id="selectTree" multiMode="true"
													allSelectable="false" noGroup="false"></div>
											</span> --%>
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedBillCode" id="SelectedBillCode"
													style="vertical-align: top; height:32px;width: 150px;">
												</select>
											</span>
											
											<button type="button" id="btnSearch" class="btn btn-info btn-sm" onclick="tosearch();">
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
							<div class="tabbable">
								<ul class="nav nav-tabs padding-18">
									<li class="active"><a data-toggle="tab"
										href="#voucherTransfer"> <i
											class="green ace-icon fa fa-user bigger-120"></i> 凭证数据传输
									</a></li>

									<li><a data-toggle="tab" href="#voucherMgr"> <i
											class="orange ace-icon fa fa-rss bigger-120"></i> 凭证管理
									</a></li>
									<li><a data-toggle="tab" href="#voucherSyncDel"> <i
											class="red ace-icon fa fa-exchange bigger-120"></i> 同步删除
									</a></li>
								</ul>
								<div class="tab-content no-border ">
									<table id="jqGrid"></table>
									<div id="jqGridPager"></div>
								</div>
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
	//var jqGridColModelSub;
	var which='16';
	var gridHeight;
	var jqGridColModel;
	var voucherType=1;
	var tabIndex=1;
	
	
	//单号下拉列表
    var SelectNoBillCodeShowOption;
	var InitBillCodeOptions;
	
	
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
		
		
		//tab页切换
		$('.nav-tabs li').on('click', function(e){
			if($(this).hasClass('active')) return;
			var target = $(this).find('a');
			
			//data:{VOUCHER_TYPE:voucherType,TABLE_CODE:'${pd.which}'},
			if(target.attr('href')=='#voucherTransfer'){
				voucherType=1;
				//$("#SelectedBillCode").removeClass("hidden");
				tabIndex=1;
				$("[data-original-title='上传']").removeClass("hidden");
				$("[data-original-title='获取凭证号']").addClass("hidden");
				$("[data-original-title='获取冲销凭证号']").addClass("hidden");
				$("[data-original-title='同步删除']").addClass("hidden");
				if($("[data-original-title='上传']").length==0){
					$('#jqGrid').navButtonAdd('#jqGridPager',
			       {
			           buttonicon: "ace-icon fa fa-cloud-upload green",
			           title: "上传",
			           caption: "上传",
			           position: "last",
			           onClickButton: batchSave
			       });
				}
				
				jQuery('#jqGrid').hideCol(['CERT_CODE','REVCERT_CODE']);
				var busiDate = $("#busiDate").val(); 
				var deptCode = $("#DEPARTS").val(); 
				var billCode=$("#SelectedBillCode").val(); 
				var typeCode=$("#TYPE_CODE").val(); 
				$("#jqGrid").jqGrid("setGridParam",{url:"<%=basePath%>voucherSpecial/getPageList.do",postData:{"BILL_CODE":billCode,"TYPE_CODE":typeCode,"VOUCHER_TYPE":voucherType,"BUSI_DATE":busiDate,"DEPT_CODE":deptCode,"FMISACC":$("#FMISACC").val()}});
				$("#jqGrid").trigger("reloadGrid");  
			}else if(target.attr('href')=='#voucherMgr'){
				voucherType=2;
				//$("#SelectedBillCode").addClass("hidden");
				tabIndex=2;
				$("[data-original-title='上传']").addClass("hidden");
				$("[data-original-title='获取凭证号']").removeClass("hidden");
				$("[data-original-title='获取冲销凭证号']").removeClass("hidden");
				$("[data-original-title='同步删除']").addClass("hidden");
				
				if($("[data-original-title='获取凭证号']").length==0){
					//获取凭证号
			       $('#jqGrid').navButtonAdd('#jqGridPager',
			       {
			    	   /* bigger-150 */
			           buttonicon: "ace-icon fa fa-book purple",
			           title: "获取凭证号",
			           caption: "获取凭证号",
			           position: "last",
			           onClickButton: batchVoucher
			       });
				}
				if($("[data-original-title='获取冲销凭证号']").length==0){
					//获取冲销凭证号
			       $('#jqGrid').navButtonAdd('#jqGridPager',
			       {
			    	   /* bigger-150 */
			           buttonicon: "ace-icon fa fa-bookmark orange",
			           title: "获取冲销凭证号",
			           caption: "获取冲销凭证号",
			           position: "last",
			           onClickButton: batchWriteOffVoucher
			       });
				}
				jQuery('#jqGrid').showCol(['CERT_CODE','REVCERT_CODE']);
				var busiDate = $("#busiDate").val(); 
				var deptCode = $("#DEPARTS").val(); 
				$("#jqGrid").jqGrid("setGridParam",{url:"<%=basePath%>voucherSpecial/getPageList.do",postData:{"VOUCHER_TYPE":voucherType,"TYPE_CODE":typeCode,"BUSI_DATE":busiDate,"DEPT_CODE":deptCode,"FMISACC":$("#FMISACC").val()}});
				$("#jqGrid").trigger("reloadGrid");  
			}else{
				voucherType=2;
				//$("#SelectedBillCode").addClass("hidden");
				tabIndex=3;
				$("[data-original-title='上传']").addClass("hidden");
				$("[data-original-title='获取凭证号']").addClass("hidden");
				$("[data-original-title='获取冲销凭证号']").addClass("hidden");
				$("[data-original-title='同步删除']").removeClass("hidden");
				
				if($("[data-original-title='同步删除']").length==0){
					//同步删除
			       $('#jqGrid').navButtonAdd('#jqGridPager',
			       {
			    	   /* bigger-150 */
			           buttonicon: "ace-icon fa fa-exchange red",
			           title: "同步删除",
			           caption: "同步删除",
			           position: "last",
			           onClickButton: syncDel
			       });
				}
				jQuery('#jqGrid').hideCol(['CERT_CODE','REVCERT_CODE']);
				var busiDate = $("#busiDate").val(); 
				var deptCode = $("#DEPARTS").val(); 
				$("#jqGrid").jqGrid("setGridParam",{url:"<%=basePath%>voucherSpecial/getSyncDelList.do",postData:{"VOUCHER_TYPE":voucherType,"TYPE_CODE":typeCode,"BUSI_DATE":busiDate,"DEPT_CODE":deptCode,"FMISACC":$("#FMISACC").val()}});
				$("#jqGrid").trigger("reloadGrid");  
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
				gridHeight=236;
			}else{
				//gridHeight=213;
				gridHeight=198;
			}
			resizeGridHeight($("#jqGrid"),gridHeight);
	    });	
		
		var curUrl="<%=basePath%>voucherSpecial/getPageList.do";
		var curPostData={"VOUCHER_TYPE":voucherType,"TYPE_CODE":$("#TYPE_CODE").val(),"BILL_CODE":$("#SelectedBillCode").val(),"BUSI_DATE":$("#busiDate").val(),"DEPT_CODE":$("#DEPARTS").val(),"FMISACC":$("#FMISACC").val()};
		if(tabIndex==3){
			curUrl="<%=basePath%>voucherSpecial/getSyncDelList.do";
		}else{
			curUrl="<%=basePath%>voucherSpecial/getPageList.do";
		}
		if(tabIndex==1){
			curPostData={"VOUCHER_TYPE":voucherType,"TYPE_CODE":$("#TYPE_CODE").val(),"BILL_CODE":$("#SelectedBillCode").val(),"BUSI_DATE":$("#busiDate").val(),"DEPT_CODE":$("#DEPARTS").val(),"FMISACC":$("#FMISACC").val()}
		}else{
			curPostData={"VOUCHER_TYPE":voucherType,"TYPE_CODE":$("#TYPE_CODE").val(),"BUSI_DATE":$("#busiDate").val(),"DEPT_CODE":$("#DEPARTS").val(),"FMISACC":$("#FMISACC").val()}
		}
		$("#jqGrid").jqGrid({
			url: curUrl,
			postData:curPostData,
			datatype: "json",
			colModel: jqGridColModel,
			reloadAfterSubmit: true, 
			//autowidth:true,
			shrinkToFit:false,
			viewrecords: false,
			rowNum: -1,
			//rowList:[10,20,30,10000],
			pgbuttons: false,//上下按钮 
			pginput:false,//输入框
			//height: '100%', 
			width:'100%',
			sortname: 'BILL_CODE',
			footerrow: ${HasUserData},
			userDataOnFooter: ${HasUserData}, // the calculated sums and/or strings from server are put at footer row.
			/* grouping: true,
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
            }, */

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
			
			multiselect: true,
			//multikey: "ctrlKey",
	        multiboxonly: true,
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
		jQuery('#jqGrid').hideCol(['CERT_CODE','REVCERT_CODE']);
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

		console.log(tabIndex);
		if(tabIndex==1){
		   //批量传输
	       $('#jqGrid').navButtonAdd('#jqGridPager',
	       {
	           buttonicon: "ace-icon fa fa-cloud-upload green",
	           title: "上传",
	           caption: "上传",
	           position: "last",
	           onClickButton: batchSave
	       });
		   jQuery('#jqGrid').hideCol(['CERT_CODE','REVCERT_CODE']);
		}else if(tabIndex==2){
			
			//获取凭证号
	       $('#jqGrid').navButtonAdd('#jqGridPager',
	       {
	    	   /* bigger-150 */
	           buttonicon: "ace-icon fa fa-book purple",
	           title: "获取凭证号",
	           caption: "获取凭证号",
	           position: "last",
	           onClickButton: batchVoucher
	       });
			
			//获取冲销凭证号
	       $('#jqGrid').navButtonAdd('#jqGridPager',
	       {
	    	   /* bigger-150 */
	           buttonicon: "ace-icon fa fa-bookmark orange",
	           title: "获取冲销凭证号",
	           caption: "获取冲销凭证号",
	           position: "last",
	           onClickButton: batchWriteOffVoucher
	       });
			jQuery('#jqGrid').showCol(['CERT_CODE','REVCERT_CODE']);
		}else{
			//同步删除
	       $('#jqGrid').navButtonAdd('#jqGridPager',
	       {
	    	   /* bigger-150 */
	           buttonicon: "ace-icon fa fa-exchange red",
	           title: "同步删除",
	           caption: "同步删除",
	           position: "last",
	           onClickButton: syncDel
	       });
			jQuery('#jqGrid').hideCol(['CERT_CODE','REVCERT_CODE']);
		}
		
       //获取凭证号
       /* $('#jqGrid').navButtonAdd('#jqGridPager',
       {
           buttonicon: "ace-icon fa fa-book purple",
           title: "获取凭证号",
           caption: "获取凭证号",
           position: "last",
           onClickButton: batchVoucher
       }); */
	}
	

	//批量获取凭证号
	function batchVoucher(e) {
		var listData =new Array();
		var ids = $("#jqGrid").jqGrid('getGridParam','selarrrow');
		//遍历访问这个集合  
		var rowData;
		$(ids).each(function (index, id){  
            $("#jqGrid").saveRow(id, false, 'clientArray');
             rowData = $("#jqGrid").getRowData(id);
            listData.push(rowData);
		});
		if(listData.length==0){
			$("#subTitle").tips({
				side:3,
	            msg:'请选择单据后再进行【获取凭证号】',
	            bg:'#009933',
	            time:3
	        });
			return;
		}
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>voucherSpecial/batchVoucher.do',
	    	//data: rowData,//可以单独传入一个对象，后台可以直接通过对应模型接受参数。但是传入Array（listData）就不好用了，所以传list方式需将List转为Json字符窜。
			//data: '{"rows":listData}',
			data:{DATA_ROWS:JSON.stringify(listData)},
	    	dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$("#jqGrid").trigger("reloadGrid");  
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取凭证号成功',
			            bg:'#009933',
			            time:3
			        });
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取凭证号失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(e) {
	    		$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取凭证号失败,'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
		});
	}
	
	//批量获取冲销凭证号
	function batchWriteOffVoucher(e) {
		var listData =new Array();
		var ids = $("#jqGrid").jqGrid('getGridParam','selarrrow');
		//console.log(ids);
		//遍历访问这个集合  
		var rowData;
		$(ids).each(function (index, id){  
            $("#jqGrid").saveRow(id, false, 'clientArray');
             rowData = $("#jqGrid").getRowData(id);
            listData.push(rowData);
		});
		if(listData.length==0){
			$("#subTitle").tips({
				side:3,
	            msg:'请选择单据后再进行【获取冲销凭证号】',
	            bg:'#009933',
	            time:3
	        });
			return;
		}
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>voucherSpecial/batchWriteOffVoucher.do',
	    	//data: rowData,//可以单独传入一个对象，后台可以直接通过对应模型接受参数。但是传入Array（listData）就不好用了，所以传list方式需将List转为Json字符窜。
			//data: '{"rows":listData}',
			data:{DATA_ROWS:JSON.stringify(listData)},
	    	dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$("#jqGrid").trigger("reloadGrid");  
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取冲销凭证号成功',
			            bg:'#009933',
			            time:3
			        });
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取冲销凭证号失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(e) {
	    		$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取冲销凭证号失败,'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
		});
	}
	
	//同步删除
	function syncDel(e) {
		var listData =new Array();
		var ids = $("#jqGrid").jqGrid('getGridParam','selarrrow');
		//console.log(ids);
		//遍历访问这个集合  
		var rowData;
		$(ids).each(function (index, id){  
            $("#jqGrid").saveRow(id, false, 'clientArray');
             rowData = $("#jqGrid").getRowData(id);
            listData.push(rowData);
		});
		if(listData.length==0){
			$("#subTitle").tips({
				side:3,
	            msg:'请选择单据后再进行【同步删除】',
	            bg:'#009933',
	            time:3
	        });
			return;
		}
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>voucherSpecial/syncDel.do?',
	    	//data: rowData,//可以单独传入一个对象，后台可以直接通过对应模型接受参数。但是传入Array（listData）就不好用了，所以传list方式需将List转为Json字符窜。
			//data: '{"rows":listData}',
			data:{DATA_ROWS:JSON.stringify(listData)},
	    	dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$("#jqGrid").trigger("reloadGrid");  
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'同步删除成功',
			            bg:'#009933',
			            time:3
			        });
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'同步删除失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(e) {
	    		$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'同步删除失败,'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
		});
	}
	
	//批量传输
	function batchSave(e) {
		if($("#FMISACC").val()=="0"){
	        bootbox.dialog({
				message: "<span class='bigger-110'>请您先选择帐套,查询数据后,选择要上传的数据再进行上传!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
	        return;
        }
		
		var listData =new Array();
		var ids = $("#jqGrid").jqGrid('getGridParam','selarrrow');
		//console.log(ids);
		//遍历访问这个集合  
		var rowData;
		$(ids).each(function (index, id){  
            $("#jqGrid").saveRow(id, false, 'clientArray');
             rowData = $("#jqGrid").getRowData(id);
            listData.push(rowData);
		});
		if(listData.length==0){
			$("#subTitle").tips({
				side:3,
	            msg:'请选择单据后再进行【上传】',
	            bg:'#009933',
	            time:3
	        });
			return;
		}
		var options=$("#FMISACC option:selected"); //获取选中的项
		var msg = '您当前选择的帐套为【'+options.text()+'】,确定要将选择的数据上传到FMIS融合系统吗?';
        bootbox.confirm(msg, function(result) {
		if(result) {
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>voucherSpecial/voucherTransfer.do',
		    	//data: rowData,//可以单独传入一个对象，后台可以直接通过对应模型接受参数。但是传入Array（listData）就不好用了，所以传list方式需将List转为Json字符窜。
				//data: '{"rows":listData}',
				data:{DATA_ROWS:JSON.stringify(listData)},
		    	dataType:'json',
				cache: false,
				success: function(response){
					if(response.code==0){
						$("#jqGrid").trigger("reloadGrid");  
						$(top.hangge());//关闭加载状态
						getSelectBillCodeOptions();
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
		    	error: function(e) {
		    		$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'传输失败,'+response.responseJSON.message,
			            bg:'#cc0033',
			            time:3
			        });
		    	}
			});
		}});
		
    }
	
	// the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function showFirstChildGrid(parentRowID, parentRowKey) {
    	console.log(parentRowID+"  "+parentRowKey);
    	
    	var parentRowData=$("#jqGrid").jqGrid('getRowData',parentRowKey);
    	var BILL_CODE = parentRowData.BILL_CODE;
    	var DEPT_CODE = parentRowData.DEPT_CODE;
    	var BILL_OFF=parentRowData.BILL_OFF;

    	var detailColModel = "[]";
		$.ajax({
			type: "GET",
			url: '<%=basePath%>voucherSpecial/getShowColModel.do?',
	    	data: {TYPE_CODE:$("#TYPE_CODE").val(),TABLE_CODE:"tb_gen_summy",FMISACC:$("#FMISACC").val(),BUSI_DATE:$("#busiDate").val()},
			dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					detailColModel = response.message;
	            	detailColModel = eval(detailColModel);
	            	
			        var childGridID = parentRowID + "_table";
			        var childGridPagerID = parentRowID + "_pager";
			        // send the parent row primary key to the server so that we know which grid to show
			        var childGridURL = '<%=basePath%>voucherSpecial/getFirstDetailList.do?'
			            +'BILL_CODE='+BILL_CODE;
			        //childGridURL = childGridURL + "&parentRowID=" + encodeURIComponent(parentRowKey)
			
			        // add a table and pager HTML elements to the parent grid row - we will render the child grid here
			        $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
			
			        $("#" + childGridID).jqGrid({
			            url: childGridURL,
			            mtype: "GET",
			            datatype: "json",
			            colModel: detailColModel,
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
			        if(tabIndex=="1"||tabIndex=="3"){
			        	jQuery("#" + childGridID).hideCol(['CERT_CODE','REVCERT_CODE']);
			        }else{
			        	jQuery("#" + childGridID).showCol(['CERT_CODE','REVCERT_CODE']);
			        }
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
    	var fmi = parentRowData.BILL_OFF;
    	console.log(DEPT_CODE);
    	
    	var detailColModel = "[]";
		$.ajax({
			type: "GET",
			url: '<%=basePath%>voucherSpecial/getShowColModel.do',
			data: {TYPE_CODE:$("#TYPE_CODE").val(),TABLE_CODE:"tb_gen_bus_detail",FMISACC:$("#FMISACC").val(),BUSI_DATE:$("#busiDate").val()},
			dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					detailColModel = response.message;
	            	detailColModel = eval(detailColModel);
			        var childGridID = parentRowID + "_table";
			        var childGridPagerID = parentRowID + "_pager";
			     	//send the parent row primary key to the server so that we know which grid to show
			        var childGridURL = '<%=basePath%>voucherSpecial/getDetailList.do?BILL_CODE='+parentRowData.BILL_CODE;
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
        if($("#TYPE_CODE").val()=="0"){
	        bootbox.dialog({
				message: "<span class='bigger-110'>请您先选择凭证类型,然后再进行查询!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
	        return;
        }
        if($("#DEPARTS").val()=="0"){
	        bootbox.dialog({
				message: "<span class='bigger-110'>请您先选择单位,然后再进行查询!</span>",
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
			url: '<%=basePath%>voucherSpecial/getShowColModel.do?'
				+'TABLE_CODE=tb_gen_bus_summy_bill'
                +'&BUSI_DATE='+$("#busiDate").val()
                +'&FMISACC='+$("#FMISACC").val()
	            +'&TYPE_CODE='+$("#TYPE_CODE").val(),
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
		var fmi = $("#FMISACC").val();
		var partUserType = $("#PARTUSERTYPE").val(); 
		var salaryRange = $("#SALARYRANGE").val();
		var billCode=$("#SelectedBillCode").val();
		if(voucherType=="1"){
			console.log("cccc");
			$("#jqGrid").jqGrid("setGridParam",{postData:{"BILL_CODE":billCode,"VOUCHER_TYPE":voucherType,"TABLE_CODE":'${pd.which}',"BUSI_DATE":busiDate,"DEPT_CODE":deptCode,"FMISACC":fmi,"USER_CATG":partUserType,"SAL_RANGE":salaryRange}})
			.trigger("reloadGrid");
		}else{
			console.log("dddd");
			$("#jqGrid").jqGrid("setGridParam",{postData:{"VOUCHER_TYPE":voucherType,"TABLE_CODE":'${pd.which}',"BUSI_DATE":busiDate,"DEPT_CODE":deptCode,"FMISACC":fmi,"USER_CATG":partUserType,"SAL_RANGE":salaryRange}})
			.trigger("reloadGrid");
		} */
	}  
	
	//加载单位树
	/* function initComplete(){
		//下拉树
		var defaultNodes = {"treeNodes":${zTreeNodes}};
		//绑定change事件
		$("#selectTree").bind("change",function(){
			if($(this).attr("relValue")){
				$("#departCode").val($(this).attr("relValue"));
		    }else{
		    	$("#departCode").val("");
		    }
			getSelectBillCodeOptions();
		});
		//赋给data属性
		$("#selectTree").data("data",defaultNodes);  
		$("#selectTree").render();
		$("#selectTree2_input").val("请选择单位");
	} */
	
	function getSelectBillCodeOptions(){
    	console.log("getSelectBillCodeOptions()");
		//setSelectBillCodeOptions(InitBillCodeOptions);
		$("#SelectedBillCode").empty();   //先清空
		top.jzts();
		$.ajax({
		    type: "POST",
			url: '<%=basePath%>voucherSpecial/getSpecialBillCodeList.do?'
				+'BUSI_DATE='+$("#busiDate").val()
                +'&DEPT_CODE='+$("#DEPARTS").val()
                +'&TYPE_CODE='+$("#TYPE_CODE").val()
                +'&FMISACC='+$("#FMISACC").val(),
		    dataType:'json',
			cache: false,
			success: function(response){
				if(response.code==0){
					$(top.hangge());//关闭加载状态
					setSelectBillCodeOptions(response.message);
					//$("#btnSearch").attr("enabled",true);
				}else{
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'获取单号列表失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
					//$("#btnSearch").attr("enabled",false);
				}
			},
	    	error: function(response) {
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取单号列表出错:'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
	    });
    }
	
	function getSelectTypeOptions(){
    	console.log("getSelectTypeOptions()");
		//setSelectBillCodeOptions(InitBillCodeOptions);
		//$("#SelectedBillCode").empty();   //先清空
		top.jzts();
		$.ajax({
		    type: "POST",
			url: '<%=basePath%>voucherSpecial/getTypeCodeList.do?'
                +'BUSI_DATE='+$("#busiDate").val()
                +'&FMISACC='+$("#FMISACC").val(),
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
			            msg:'获取凭证类型失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(response) {
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取凭证类型出错:'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
	    });
    }
	
	function getSelectDepartOptions(){
    	console.log("getSelectDepartOptions()");
		//setSelectBillCodeOptions(InitBillCodeOptions);
		//$("#SelectedBillCode").empty();   //先清空
		top.jzts();
		$.ajax({
		    type: "POST",
			url: '<%=basePath%>voucherSpecial/getDepartCodeList.do?'
                +'&TYPE_CODE='+$("#TYPE_CODE").val()
                +'&FMISACC='+$("#FMISACC").val(),
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
			            msg:'获取单位失败,'+response.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
			},
	    	error: function(response) {
				$(top.hangge());//关闭加载状态
				$("#subTitle").tips({
					side:3,
		            msg:'获取单位出错:'+response.responseJSON.message,
		            bg:'#cc0033',
		            time:3
		        });
	    	}
	    });
    }
	
	function setSelectTypeCodeOptions(selectBillCodeOptions){
        $("#TYPE_CODE").empty();   //先清空
        $("#TYPE_CODE").append("<option value='0'>请选择凭证类型</option>");
        $("#TYPE_CODE").append(selectBillCodeOptions);  //再赋值
    }
	
	function setSelectDepartCodeOptions(selectBillCodeOptions){
        $("#DEPARTS").empty();   //先清空
        $("#DEPARTS").append("<option value='0'>请选择单位</option>");
        $("#DEPARTS").append(selectBillCodeOptions);  //再赋值
    }
	
	function setSelectBillCodeOptions(selectBillCodeOptions){
        $("#SelectedBillCode").empty();   //先清空
        $("#SelectedBillCode").append(selectBillCodeOptions);  //再赋值
    }
 	</script>
</body>
</html>