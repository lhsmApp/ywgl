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

<!-- jsp文件头和头部 -->
<%@ include file="../system/index/top.jsp"%>

	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
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

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<form name="Form" id="Form" method="post" enctype="multipart/form-data">
								<div id="zhongxin">
								<table style="width:95%;" >
									<div style="padding-top: 10px;" <c:if test="${tip!='1'}">hidden</c:if>>温馨提示:支持某个指标的整体数据导入,不支持某个指标的部分导入</div>
									<tr>
										<td style="padding-top: 20px;"><input type="file" id="excel" name="excel" style="width:50px;" onchange="fileType(this)" /></td>
									</tr>
									<tr>
										<td style="text-align: center;padding-top: 10px;">
											<a class="btn btn-mini btn-primary" onclick="save();">导入</a>
											<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
											<a class="btn btn-mini btn-success" onclick="downModel('<%=basePath%>')">下载模版</a>
										</td>
									</tr>
								</table>
								</div>
								<div id="zhongxin2" class="center" style="display:none"><br/><img src="static/images/jzx.gif" /><br/><h4 class="lighter block green"></h4></div>
							</form>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 上传控件 -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	
	<!-- 最新版的Jqgrid Js，如果旧版本（Ace）某些方法不好用，尝试用此版本Js，替换旧版本JS -->
	<!-- <script src="static/ace/js/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="static/ace/js/grid.locale-cn.js" type="text/javascript"></script> -->
	<!-- 旧版本（Ace）Jqgrid Js -->
	<script src="static/ace/js/jqGrid/jquery.jqGrid.src.js"></script>
	<script src="static/ace/js/jqGrid/i18n/grid.locale-cn.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 输入格式化 -->
	<script src="static/ace/js/jquery.maskedinput.js"></script>
	<!-- JqGrid统一样式统一操作 -->
	<script type="text/javascript" src="static/js/common/jqgrid_style.js"></script>
	<script type="text/javascript" src="static/js/common/cusElement_style.js"></script>
	<script type="text/javascript" src="static/js/util/toolkit.js"></script>
	<script src="static/ace/js/ace/ace.widget-box.js"></script>
	
	<script type="text/javascript">
	    var local = '${local}';
	    var which = '${which}';
	    var SelectedDepartCode = '${SelectedDepartCode}';
	    var SelectedCustCol7 = '${SelectedCustCol7}';
	    var SelectedBillCode = '${SelectedBillCode}';
	    var SelectedTypeCode = '${SelectedTypeCode}';
	    var SelectedBusiDate = '${SelectedBusiDate}';
	    var DepartTreeSource = '${DepartTreeSource}';
	    var ShowDataDepartCode = '${ShowDataDepartCode}';
	    var ShowDataCustCol7 = '${ShowDataCustCol7}';
	    var ShowDataBillCode = '${ShowDataBillCode}';
	    var ShowDataTypeCode = '${ShowDataTypeCode}';
	    var ShowDataBusiDate = '${ShowDataBusiDate}';
	    var SystemDateTime = '${SystemDateTime}';
	    var KpiCode = '${KPI_CODE}';
	    var tipfiles = "请选择xls格式的文件";
	    
		$(document).ready(function () {
			$(top.hangge());
			
			document.getElementById("Form").action = local + "/readExcel.do?TABLE_CODE="+which+"&SelectedTableNo="+which
                +'&SelectedDepartCode='+SelectedDepartCode+'&SelectedCustCol7='+SelectedCustCol7+'&SelectedBillCode='+SelectedBillCode+'&SelectedTypeCode='+SelectedTypeCode+'&SelectedBusiDate='+SelectedBusiDate
                +'&DepartTreeSource='+DepartTreeSource
                +'&ShowDataDepartCode='+ShowDataDepartCode+'&ShowDataCustCol7='+ShowDataCustCol7+'&ShowDataBillCode='+ShowDataBillCode+'&ShowDataTypeCode='+ShowDataTypeCode+'&ShowDataBusiDate='+ShowDataBusiDate
                +'&SystemDateTime='+SystemDateTime
                +'&KPI_CODE='+KpiCode;
			
		    var commonBaseCode = '${commonBaseCode}';
		    var commonMessage = "${commonMessage}";
		    console.log(commonMessage);
		    if(commonBaseCode != null && $.trim(commonBaseCode) != ""){
		        if($.trim(commonBaseCode) == 0){
		            $("#excel").tips({
		                side:3,
		                msg:'导入成功! ' + commonMessage,
		                bg:'#AE81FF',
		                time:3
		            });
		        } else {
		        	if($.trim(commonBaseCode) == 3){
		        	    //top.jzts();
		        	    //var diag = new top.Dialog();
		        	    //diag.Drag=true;
		        	    //diag.Title ="填写的数据不正确，无法导入！";
		        	    //diag.URL = '<%=basePath%>' + local + '/showErrorTaxMessage.do?ErrorTaxMessage='+commonMessage;
		        	    //diag.Width = 500;
		        	    //diag.Height = 350;
		        	    //diag.CancelEvent = function(){ //关闭事件
		        	    //    top.jzts();
		        	    //    $(top.hangge());//关闭加载状态
		        	    //    diag.close();
		                //};
		                //diag.show();

			        	alert(commonMessage);
		        	} else if($.trim(commonBaseCode) == 9){
		                var msg = commonMessage + '确定覆盖吗??';
		                bootbox.confirm(msg, function(result) {
		    				if(result) {
		    					$.ajax({
		    						type: "POST",
		    						url: '<%=basePath%>' + local + '/coverAdd.do?TABLE_CODE='+which+'&SelectedTableNo='+which
		    		                +'&SelectedDepartCode='+SelectedDepartCode+'&SelectedCustCol7='+SelectedCustCol7+'&SelectedBillCode='+SelectedBillCode+'&SelectedTypeCode='+SelectedTypeCode+'&SelectedBusiDate='+SelectedBusiDate
		    		                +'&DepartTreeSource='+DepartTreeSource
		    		                +'&ShowDataDepartCode='+ShowDataDepartCode+'&ShowDataCustCol7='+ShowDataCustCol7+'&ShowDataBillCode='+ShowDataBillCode+'&ShowDataTypeCode='+ShowDataTypeCode+'&ShowDataBusiDate='+ShowDataBusiDate
		    		                +'&SystemDateTime='+SystemDateTime
		    		                +'&KPI_CODE='+KpiCode,
		    				    	data: {StringDataRows:'${StringDataRows}'},
		    						dataType:'json',
		    						cache: false,
		    						success: function(response){
		    							if(response.code==0){
			    						    $("#excel").tips({
			    						    	side:3,
			    				                msg:'成功',
			    				                bg:'#AE81FF',
			    				                time:3
			    				            });
		    							}else{
			    						    $("#excel").tips({
			    						    	side:3,
			    				                msg:'失败,'+response.message,
			    				                bg:'#AE81FF',
			    				                time:3
			    				            });
		    							}
		    						},
		    				    	error: function(response) {
		    						    $("#excel").tips({
		    						    	side:3,
		    				                msg:'出错:'+response.responseJSON.message,
		    				                bg:'#AE81FF',
		    				                time:3
		    				            });
		    				    	}
		    					});
		    				}
		                });
			        } else if($.trim(commonBaseCode) != -1){
					    $("#excel").tips({
					    	side:3,
			                msg:commonMessage,
			                bg:'#AE81FF',
			                time:3
			            });
			        }
		        }
		    }
		});
		
		$(function() {
			//上传
			$('#excel').ace_file_input({
				no_file:'请选择EXCEL ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'xls|xls',
				blacklist:'gif|png|jpg|jpeg'
				//onchange:''
			});
		});
		
		//下载模板
		function downModel(basePath){
			var url = basePath + local + '/downExcel.do?TABLE_CODE='+which+"&SelectedTableNo="+which
                +'&SelectedDepartCode='+SelectedDepartCode+'&SelectedCustCol7='+SelectedCustCol7+'&SelectedBillCode='+SelectedBillCode+'&SelectedTypeCode='+SelectedTypeCode+'&SelectedBusiDate='+SelectedBusiDate
                +'&DepartTreeSource='+DepartTreeSource
                +'&ShowDataDepartCode='+ShowDataDepartCode+'&ShowDataCustCol7='+ShowDataCustCol7+'&ShowDataBillCode='+ShowDataBillCode+'&ShowDataTypeCode='+ShowDataTypeCode+'&ShowDataBusiDate='+ShowDataBusiDate
                +'&SystemDateTime='+SystemDateTime
                +'&KPI_CODE='+KpiCode;
			window.location.href = url;
		}
		//保存
		function save(){
			if($("#excel").val() == "" || document.getElementById("excel").files[0] == tipfiles){
				$("#excel").tips({
					side:3,
		            msg:'请选择文件',
		            bg:'#AE81FF',
		            time:3
		        });
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		function fileType(obj){
			var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
		    if(fileType != '.xls' && fileType != '.xlsx' ){
		    	$("#excel").tips({
					side:3,
		            msg:tipfiles,
		            bg:'#AE81FF',
		            time:3
		        });
		    	$("#excel").val('');
		    	document.getElementById("excel").files[0] = tipfiles;
		    }
		}
	</script>


</body>
</html>