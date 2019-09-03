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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>

</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row" >
						<div >
							<!-- 表单  -->
							<form action="policyCustom/listCustom.do" method="post"
								name="Form" id="Form">
								<input type="hidden" name="POLI_TYPE" id="POLI_TYPE" value="${pd.POLI_TYPE}"/>
								<div >
									<div class="widget-box" style="margin: 0px 0px;">
										<!-- #section:custom/widget-box.options -->
										<div class="widget-header" >
										<h5 class="widget-title"><i class="ace-icon fa fa-table"></i> 政策法规</h5>
										</div>
										<!-- /section:custom/widget-box.options -->
										<div class="widget-body">
											<div class="widget-main no-padding">
												<table
													class="table table-striped table-bordered table-hover">
													<thead class="thin-border-bottom">
														<tr>
															<th class="center">政策分类</th>
															<th class="center">标题</th>
															<th class="center">发布时间</th>
															<th class="center">发布人</th>
														</tr>
													</thead>
													<tbody>
														<!-- 开始循环 -->
														<c:choose>
															<c:when test="${not empty varList}">
																<c:forEach items="${varList}" var="var" varStatus="vs">
																	<tr>
																		<td class='center'>${var.POLI_TYPE}</td>
																		<td class='center'><a style="cursor: pointer;"
																			onclick="viewContent('${var.ID}')">${var.TITLE}</a></td>
																		<td class='center'>${var.PUB_DATE}</td>
																		<td class='center'>${var.PUB_USER}</td>
																	</tr>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<tr class="main_info">
																	<td colspan="100" class="center">没有相关数据</td>
																</tr>
															</c:otherwise>
														</c:choose>
													</tbody>
												</table>
												
											</div>
										</div>
									</div>
								</div>
								<!-- /.span -->
<div class="page-header position-relative">
													<table style="width: 100%;">
														<tr>
															<td style="vertical-align: top;"><div
																	class="pagination"
																	style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
														</tr>
													</table>
												</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
	
		//查看详情
		function viewContent(Id){
			self.location.href="<%=basePath%>/policy/content.do?ID=" + Id+ "&tm=" + new Date().getTime();
		}
	</script>
</body>
</html>