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
									<tr>
										<td style="padding-top: 20px;">
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedCustCol7" id="SelectedCustCol7"
													data-placeholder="请选择帐套"
													style="vertical-align: top; height:32px;width: 250px;">
													<option value="">请选择帐套</option>
													<c:forEach items="${FMISACC}" var="each">
														<option value="${each.DICT_CODE}">${each.NAME}</option>
													</c:forEach>
												</select>
											</span>
										</td>
									</tr>
									<tr id='trSelectTree' <c:if test="${DepartTreeSource=='0'}">hidden</c:if>>
										<td style="padding-top: 20px;">
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedDepartCode" id="SelectedDepartCode"
													data-placeholder="请选择责任中心"
													style="vertical-align: top; height:32px;width: 250px;">
													<option value="">请选择责任中心</option>
													<c:forEach items="${DEPARTMENT}" var="each">
														<option value="${each.DICT_CODE}">${each.NAME}</option>
													</c:forEach>
												</select>
											</span>
										</td>
									</tr>
									<tr>
										<td style="text-align: right;padding-top: 20px;">
											<a class="btn btn-mini btn-success" onclick="downModel('<%=basePath%>')">导出</a>
											<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
										</td>
									</tr>
								</table>
								</div>
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
	<script type="text/javascript">
	    var local = '${local}';
	    var SelectedTableNo = '${SelectedTableNo}';
	    var SelectedBusiDate = '${SelectedBusiDate}';
	    var SalaryOrBonus = '${SalaryOrBonus}';
	    
		$(document).ready(function () {
			$(top.hangge());

		    var commonBaseCode = '${commonBaseCode}';
		    var commonMessage = "${commonMessage}";
		    console.log(commonMessage);
		    if(commonBaseCode != null && $.trim(commonBaseCode) != ""){
		        if($.trim(commonBaseCode) == 0){
		            $("#SelectedCustCol7").tips({
		                side:3,
		                msg:'导出成功! ' + commonMessage,
		                bg:'#AE81FF',
		                time:3
		            });
		        } else {
		        	if($.trim(commonBaseCode) != -1){
					    $("#SelectedCustCol7").tips({
					    	side:3,
			                msg:commonMessage,
			                bg:'#AE81FF',
			                time:3
			            });
			        }
			    }
		    };
		})
		
		//下载
		function downModel(basePath){
			var SelectedCustCol7 = $("#SelectedCustCol7").val();
			var SelectedDepartCode = $("#SelectedDepartCode").val();
			if(!(SelectedCustCol7!=null && $.trim(SelectedCustCol7)!="")){
	            $("#SelectedCustCol7").tips({
	                side:3,
	                msg:'请选择帐套! ',
	                bg:'#AE81FF',
	                time:3
	            });
	            return;
			}
			if(!$("#trSelectTree").is(":hidden") && !(SelectedDepartCode!=null && $.trim(SelectedDepartCode)!="")){
	            $("#SelectedDepartCode").tips({
	                side:3,
	                msg:'请选择责任中心! ',
	                bg:'#AE81FF',
	                time:3
	            });
	            return;
			}
			var url = basePath + local + '/excel.do?DownSelectedTableNo='+SelectedTableNo
                +'&DownSelectedBusiDate='+SelectedBusiDate
                +'&DownSalaryOrBonus='+SalaryOrBonus
                +'&DownSelectedCustCol7='+$("#SelectedCustCol7").val()
                +'&DownSelectedDepartCode='+$("#SelectedDepartCode").val();
			window.location.href = url;
		}
	</script>


</body>
</html>