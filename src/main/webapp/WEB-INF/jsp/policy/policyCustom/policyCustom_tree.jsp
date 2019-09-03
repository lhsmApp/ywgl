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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
	<table style="width: 100%;" border="0">
		<tr>
			<td style="width: 20%;" valign="top">
				<div style="width: 90%; margin: 10px auto;">
					<div class="widget-box widget-color-blue2">
						<div class="widget-header">
							<h5 class="widget-title">政策分类</h5>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<div class="dd" id="nestable">
									<ol class="dd-list">
										<li class="dd-item" data-id="1"><c:choose>
												<c:when test="${not empty policyTypeList}">
													<c:forEach items="${policyTypeList}" var="policyType"
														varStatus="vs">
														<div style="cursor: pointer" class="dd-handle"
															onclick="policyDetail('${policyType.POLI_TYPE}')"><i class="ace-icon fa fa-bars smaller-90">&nbsp;&nbsp;&nbsp;</i>${policyType.POLI_TYPE}</div>
													</c:forEach>
												</c:when>
											</c:choose></li>
									</ol>
								</div>
							</div>
						</div>
					</div>
				</div>
			</td>
			<td style="width: 80%;" valign="top"><iframe name="treeFrame"
					style="width: 90%; margin: 10px auto;"
					id="treeFrame" frameborder="0"
					src="<%=basePath%>/policyCustom/listCustom.do?POLI_TYPE=${'' == POLI_TYPE?'':POLI_TYPE}"}"></iframe>
			</td>
		</tr>
	</table>
	
	<!-- basic scripts -->
	<script type="text/javascript">
	function treeFrameT(){
		var hmainT = document.getElementById("treeFrame");
		var bheightT = document.documentElement.clientHeight;
		hmainT .style.width = '100%';
		hmainT .style.height = (bheightT-26) + 'px';
	}
	treeFrameT();
	window.onresize=function(){  
		treeFrameT();
	};
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		//根据分类确定政策
		function policyDetail(str) {
			top.jzts();
			treeFrame.location.href="<%=basePath%>/policyCustom/listCustom.do?POLI_TYPE="+str+"&tm="+new Date().getTime();
		}
	</script>
</body>
</html>