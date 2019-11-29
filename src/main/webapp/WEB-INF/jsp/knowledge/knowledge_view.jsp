<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
						<div style="padding-top: 13px;" class="clearfix">
							<div>
								<label class="inline" style="margin-bottom:5px;">
									<span class="list-item-value-title">${pd.KNOWLEDGE_TITLE}</span>
								</label>
							<div>

							<div style="word-wrap:break-word">
								${pd.DETAIL}
							</div>
							
							<div>
								<label class="inline">
									<span class="list-item-info"> 作者：</span>
									<span class="list-item-value">${pd.AUTHOR}</span>
									&nbsp;&nbsp;
									<span class="list-item-info"><i class="ace-icon fa fa-clock-o"></i> 时间：</span>
									<span class="list-item-value grey">
										<span>${pd.CREATE_DATE}</span>
									</span>
								</label>
								
							</div>

							
						</div>
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


	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<script type="text/javascript">
	$(top.hangge());
	
	
	$(function() {
		
	});
	</script>
</body>
</html>