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

	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<form name="Form" id="Form" method="post" enctype="multipart/form-data">
								<div id="zhongxin">
								<table style="width:95%;" >
									<tr>
										<td style="padding-top: 20px;text-align: center;">
										    <textarea id="eduhistory" cols="57" rows="11"></textarea>
										     <!-- style="width:200px;height:80px;" -->
										</td>
									</tr>
									<tr>
										<td style="text-align: right;padding-top: 10px;;padding-right: 40px;">
											<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
										</td>
									</tr>
								</table>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
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
		$(document).ready(function () {
			$(top.hangge());
			
			var reg=new RegExp("<br/>","g"); //创建正则RegExp对象    
            var commonMessage = "${commonMessage}".replace(reg,'\r\n');
            console.log(commonMessage);
		    $("#eduhistory").html(commonMessage);
		})
	</script>
</body>
</html>