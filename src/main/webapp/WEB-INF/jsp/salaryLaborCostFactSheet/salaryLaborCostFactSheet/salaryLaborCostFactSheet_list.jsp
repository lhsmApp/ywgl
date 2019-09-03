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
									    id="subTitle" style="margin-left: 2px;">工资、劳务费情况</span> 
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
											<!-- <span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedCustCol7" id="SelectedCustCol7" data-placeholder="请选择帐套"
													style="vertical-align: top; height:32px;width: 150px;">
													<option value="">请选择帐套</option>
													<c:forEach items="${FMISACC}" var="each">
														<option value="${each.DICT_CODE}" 
														    <c:if test="${pd.SelectedCustCol7==each.DICT_CODE}">selected</c:if>>${each.NAME}</option>
													</c:forEach>
												</select>
											</span> -->
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
								<span class="bigger-120">2018年6月工资、劳务费情况表</span>
							</div>
					    </div>
					    <div class="col-xs-12">
						    <div class="pull-right" style="margin-right: 20px;">
								<span class="bigger-100">单位：元</span>
							</div>
					    </div>
						<div class="col-xs-12">
						    <table id="jqGridBase"></table>
						</div>
					    <div class="col-xs-12" >
					        <div class="pull-left" style="margin-left: 120px;margin-top: 7px;margin-bottom: 10px;">
								<span class="bigger-100">核对人：</span>
							</div>
						    <div class="pull-right" style="margin-right: 120px;margin-top: 7px;margin-bottom: 10px;">
								<span class="bigger-100">统计人：</span>
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

			$(gridBase_selector).jqGrid('GridUnload'); 
			SetStructure();
 	    });
	
		//检索
		function tosearch() {
			var BusiDate = $("#SelectedBusiDate").val(); 
			//var CustCol7 = $("#SelectedCustCol7").val();
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
			/*if(!(CustCol7!=null && $.trim(CustCol7)!="")){
				$("#SelectedCustCol7").tips({
					side:3,
		            msg:'请选择帐套',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SelectedCustCol7").focus();
				return false;
			}*/
			$(gridBase_selector).jqGrid('GridUnload'); 
			SetStructure();
		}
	    
	    function SetStructure(){
		    //resize to fit page size
		    $(window).on('resize.jqGrid', function () {
		        $(gridBase_selector).jqGrid( 'setGridWidth', $(".page-content").width());
		        $(gridBase_selector).jqGrid( 'setGridHeight', $(window).height() - 210);
			    //resizeGridHeight($(gridBase_selector));
			})
		
		    $(gridBase_selector).jqGrid({
    			url: '<%=basePath%>salaryLaborCostFactSheet/getPageList.do?'
    	            + 'SelectedBusiDate='+$("#SelectedBusiDate").val(),
    	            //+ '&SelectedCustCol7='+$("#SelectedCustCol7").val(),
		        datatype: "json",
				colNames:['类别','类别','类别','类别',
				          '总额合计',
				          '工资','无房补贴','交通补贴','通讯补贴','节日补贴','误餐补贴','项目补贴','',
				          '儿贴',
				          '防暑降温费',
				          '疗养费',
				          '总额外单项奖',
				          '单独制表',
				          '期末人数', ''],
			    colModel:[
			              {name:'name01',width:30,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name01' + rowId + "\'";
		                    }},
			              {name:'name02',width:30,sortable:false,align:'center',
				            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
		                              //合并单元格
			                          return 'id=\'name02' + rowId + "\'";
			                    }},
			              {name:'name03',width:30,sortable:false,align:'center',
					            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
			                              //合并单元格
				                          return 'id=\'name03' + rowId + "\'";
				                    }},
			              {name:'name04',width:150,sortable:false,align:'center',
						            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
				                              //合并单元格
					                          return 'id=\'name04' + rowId + "\'";
					                    }},

			              {name:'name05',width:150,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name05' + rowId + "\'";
		                    }},

			              {name:'name06',width:130,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name06' + rowId + "\'";
		                    }},
			              {name:'name07',width:130,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name07' + rowId + "\'";
		                    }},
			              {name:'name08',width:130,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name08' + rowId + "\'";
		                    }},
			              {name:'name09',width:130,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name09' + rowId + "\'";
		                    }},
			              {name:'name10',width:130,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name10' + rowId + "\'";
		                    }},
			              {name:'name11',width:130,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name11' + rowId + "\'";
		                    }},
			              {name:'name12',width:130,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name12' + rowId + "\'";
		                    }},
			              {name:'name13',width:130,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name13' + rowId + "\'";
		                    }},

			              {name:'name14',width:120,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name14' + rowId + "\'";
		                    }},

			              {name:'name15',width:120,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name15' + rowId + "\'";
		                    }},

			              {name:'name16',width:120,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name16' + rowId + "\'";
		                    }},

			              {name:'name17',width:120,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name17' + rowId + "\'";
		                    }},

			              {name:'name18',width:120,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name18' + rowId + "\'";
		                    }},

			              {name:'name19',width:120,sortable:false,align:'center',
			            	  cellattr: function(rowId, tv, rawObject, cm, rdata) {
	                              //合并单元格
		                          return 'id=\'name19' + rowId + "\'";
		                    }},

				          {name:'isRowAllGroup',width:120,sortable:false,hidden:true,editable:true}
		                    
			          ],
				//caption: "2018年6月工资、劳务费情况表",
	    	    shrinkToFit: false,
    			viewrecords: true, 
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
    				
    				MergerStatistics(gridBase_selector, 
    						'name01,name02,name03,name04,name05,name06,name07,name08,name09,name10,name11,name12,name13,name14,name15,name16,name17,name18,name19', 
    						"name01,name02,name03,name04"); 
    				
    	            //var ids = $("#gridTable").getDataIDs();
    	            //for(var i=0;i<ids.length;i++){
    	            //    var rowData = $("#gridTable").getRowData(ids[i]);
    	            //    if(rowData.overdueDays==0){//如果天数等于0，则背景色置灰显示
    	            //        $('#'+ids[i]).find("td").addClass("SelectBG");
    	            //    }
    	            //}
    			},
	        });

		    $(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
		    
		    //合并表头
		    $(gridBase_selector).jqGrid("setGroupHeaders", {
		    	useColSpanStyle : true ,//没有表头的列是否与表头所在行的空单元格合并
		    	groupHeaders : [{
	    			    startColumnName : "name01",//合并列的起始位置 colModel中的name
	    			    numberOfColumns : 4, //合并列数 包含起始列
	    			    titleText : "类别"//表头
	    		    }, {
		    			startColumnName : "name06",//合并列的起始位置 colModel中的name
		    			numberOfColumns : 8, //合并列数 包含起始列
		    			titleText : "其中"//表头
		    		}
		    	]
		    });
		    
		    //隐藏表头 全部隐藏
		    $('.ui-jqgrid-hdiv').hide();
	    }
	    
	    function MergerStatistics(jqGrid, CellNameAll, CellNameColHeader) {
            var cellNamesAll = CellNameAll.split(",");
            var cellNamesHeader = CellNameColHeader.split(",");
	        //得到显示到界面的id集合
	        var mya = $(jqGrid).getDataIDs();
	        //当前显示多少条
	        var length = mya.length;
	        for (var i = 0; i < length; i++) {
	            //从上到下获取一条信息
	            var before = $(jqGrid).jqGrid('getRowData', mya[i]);
	        	var isRowAllGroup = before.isRowAllGroup;
	        	if(isRowAllGroup.toUpperCase() == ("true").toUpperCase()){
	                for (var n = 0; n < cellNamesAll.length; n++) {
	    	            //定义合并列数
	    	            var colSpanTaxCount = 1;
	    	            for (m = n + 1; m <= cellNamesAll.length; m++) {
	                        if (before[cellNamesAll[n]] == before[cellNamesAll[m]] ) {
	                            colSpanTaxCount++;
	                            $(jqGrid).setCell(mya[i], cellNamesAll[m], '', { display: 'none' });
	                        } else {
	                        	colSpanTaxCount = 1;
	                            break;
	                        }
	                        $("#" + cellNamesAll[n] + "" + mya[i] + "").attr("colspan", colSpanTaxCount);//最后合并需要合并的行与合并的行数
	    	            }
	                } 
	        	}
	        }
	        //头行数
	        var rowsHeaderNum = 1;
            //标题两行
	        /*for (var i = 0; i < rowsHeaderNum; i++) {
	            //从上到下获取一条信息
	            var before = $(jqGrid).jqGrid('getRowData', mya[i]);
                for (var n = 0; n < cellNamesAll.length; n++) {
    	            //定义合并列数
    	            var colSpanTaxCount = 1;
    	            for (m = n + 1; m <= cellNamesAll.length; m++) {
                        if (before[cellNamesAll[n]] == before[cellNamesAll[m]] ) {
                            colSpanTaxCount++;
                            $(jqGrid).setCell(mya[i], cellNamesAll[m], '', { display: 'none' });
                        } else {
                        	colSpanTaxCount = 1;
                            break;
                        }
                        $("#" + cellNamesAll[n] + "" + mya[i] + "").attr("colspan", colSpanTaxCount);//最后合并需要合并的行与合并的行数
    	            }
                } 
	        }*/
            for (var n = 0; n < cellNamesAll.length; n++) {
    	        for (var i = 0; i < rowsHeaderNum; i++) {
    	            //从上到下获取一条信息
    	            var before = $(jqGrid).jqGrid('getRowData', mya[i]);
    	            //定义合并行数
    	            var rowSpanTaxCount = 1;
    	            for (j = i + 1; j <= rowsHeaderNum; j++) {
    	                //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
    	                var end = $(jqGrid).jqGrid('getRowData', mya[j]);
        	            //定义合并列数
        	            var colSpanTaxCount = 1;
    	                if (before[cellNamesAll[n]] == end[cellNamesAll[n]]) {
                            rowSpanTaxCount++;
                            $(jqGrid).setCell(mya[j], cellNamesAll[n], '', { display: 'none' });
                        } else {
                            rowSpanTaxCount = 1;
                            break;
                        }
    	                $("#" + cellNamesAll[n] + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
    	            }
    	        }
            }
	        /*for (var i = 0; i < length; i++) {
	            //从上到下获取一条信息
	            var before = $(jqGrid).jqGrid('getRowData', mya[i]);
                for (var n = 0; n < cellNamesHeader.length; n++) {
    	            //定义合并列数
    	            var colSpanTaxCount = 1;
    	            for (m = n + 1; m <= cellNamesHeader.length; m++) {
                        if (before[cellNamesHeader[n]] == before[cellNamesHeader[m]] ) {
                            colSpanTaxCount++;
                            $(jqGrid).setCell(mya[i], cellNamesHeader[m], '', { display: 'none' });
                        } else {
                        	colSpanTaxCount = 1;
                            break;
                        }
                        $("#" + cellNamesHeader[n] + "" + mya[i] + "").attr("colspan", colSpanTaxCount);//最后合并需要合并的行与合并的行数
    	            }
                } 
	        }
            for (var n = 0; n < cellNamesHeader.length; n++) {
    	        for (var i = 0; i < length; i++) {
    	            //从上到下获取一条信息
    	            var before = $(jqGrid).jqGrid('getRowData', mya[i]);
    	            //定义合并行数
    	            var rowSpanTaxCount = 1;
    	            for (j = i + 1; j <= length; j++) {
    	                //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
    	                var end = $(jqGrid).jqGrid('getRowData', mya[j]);
        	            //定义合并列数
        	            var colSpanTaxCount = 1;
    	                if (before[cellNamesHeader[n]] == end[cellNamesHeader[n]]) {
                            rowSpanTaxCount++;
                            $(jqGrid).setCell(mya[j], cellNamesHeader[n], '', { display: 'none' });
                        } else {
                            rowSpanTaxCount = 1;
                            break;
                        }
    	                $("#" + cellNamesHeader[n] + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
    	            }
    	        }
            }*/
            //中间列标题
	        for (var i = rowsHeaderNum; i < length-1; i++) {
	            //从上到下获取一条信息
	            var before = $(jqGrid).jqGrid('getRowData', mya[i]);
                for (var n = 0; n < cellNamesHeader.length; n++) {
    	            //定义合并列数
    	            var colSpanTaxCount = 1;
    	            for (m = n + 1; m <= cellNamesHeader.length; m++) {
                        if (before[cellNamesHeader[n]] == before[cellNamesHeader[m]] ) {
                            colSpanTaxCount++;
                            $(jqGrid).setCell(mya[i], cellNamesHeader[m], '', { display: 'none' });
                        } else {
                        	colSpanTaxCount = 1;
                            break;
                        }
                        $("#" + cellNamesHeader[n] + "" + mya[i] + "").attr("colspan", colSpanTaxCount);//最后合并需要合并的行与合并的行数
    	            }
                } 
	        }
            for (var n = 0; n < cellNamesHeader.length; n++) {
    	        for (var i = rowsHeaderNum; i < length-1; i++) {
    	            //从上到下获取一条信息
    	            var before = $(jqGrid).jqGrid('getRowData', mya[i]);
    	            //定义合并行数
    	            var rowSpanTaxCount = 1;
    	            for (j = i + 1; j <= length-1; j++) {
    	                //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
    	                var end = $(jqGrid).jqGrid('getRowData', mya[j]);
        	            //定义合并列数
        	            var colSpanTaxCount = 1;
    	                if (before[cellNamesHeader[n]] == end[cellNamesHeader[n]]) {
                            rowSpanTaxCount++;
                            $(jqGrid).setCell(mya[j], cellNamesHeader[n], '', { display: 'none' });
                        } else {
                            rowSpanTaxCount = 1;
                            break;
                        }
    	                $("#" + cellNamesHeader[n] + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
    	                //$("#" + cellNamesHeader[n] + "" + mya[i] + "").addClass("WriteErect");
    	            }
    	        }
            }
            //最后一行
	        /*for (var i = length-1; i < length; i++) {
	            //从上到下获取一条信息
	            var before = $(jqGrid).jqGrid('getRowData', mya[i]);
                for (var n = 0; n < cellNamesAll.length; n++) {
    	            //定义合并列数
    	            var colSpanTaxCount = 1;
    	            for (m = n + 1; m <= cellNamesAll.length; m++) {
                        if (before[cellNamesAll[n]] == before[cellNamesAll[m]] ) {
                            colSpanTaxCount++;
                            $(jqGrid).setCell(mya[i], cellNamesAll[m], '', { display: 'none' });
                        } else {
                        	colSpanTaxCount = 1;
                            break;
                        }
                        $("#" + cellNamesAll[n] + "" + mya[i] + "").attr("colspan", colSpanTaxCount);//最后合并需要合并的行与合并的行数
    	            }
                }
                //$(jqGrid).setCell(mya[i], cellNamesAll[0], '', { align: 'left' });
                $('#'+mya[i]).find("td").addClass("SelectTextLeft");
	            $('#'+mya[i]).find("td").addClass("SelectBG");
	        }*/
	    }

        /**
         * 导出
         */
        function exportItems(){
        	window.location.href='<%=basePath%>salaryLaborCostFactSheet/excel.do?'
	            + 'SelectedBusiDate='+$("#SelectedBusiDate").val();
        }
	</script>
</body>
</html>