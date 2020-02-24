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
	<%@ include file="../../system/index/top.jsp"%>
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
						<h5 class="lighter block blue"><i class="ace-icon fa fa-rss blue"></i>&nbsp;回复内容</h5>
						<hr />
						<div style="padding-top: 0px;" class="clearfix">
							<%-- <div>
								<label class="inline" style="margin-bottom:5px;">
									<span class="list-item-value-title">${pd.PRO_TITLE}</span>
								</label>
							<div> --%>

							<div style="word-wrap:break-word">
								${pd.ANSWER_CONTENT}
							</div>
							
							
						</div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
				
				<div class="row">
					<div style="margin:10px 12px;">
						<h5 class="lighter block blue"><i class="ace-icon fa fa-rss blue"></i>&nbsp;附件</h5>
						<hr />
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">附件名</th>
									<th class="center">附件说明</th>
									<th class="center">附件大小</th>
									<th class="center">上传人</th>
									<th class="center">上传日期</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody id="tbodyAttachment">
								
							</tbody>
						</table>
					</div>
				</div>
					
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<script type="text/javascript">
	$(top.hangge());
	
	/**
	 * 获取附件信息
	 */
	function getAttachment(attachmentID,attachmentType){
		$("#tbodyAttachment").html('');
		top.jzts();
		
		$.ajax({
				type: "GET",
				url: '<%=basePath%>attachment/getAttachmentByType.do?BUSINESS_TYPE='+attachmentType+'&BILL_CODE='+attachmentID,
		    	//data: {},
				//dataType:'json',
				cache: false,
				success: function(data){
					console.log(data);
					if(data){
						$.each(data, function(i, item){
							var tr=addItemAttachment(item,i+1,attachmentType); 
							$('#tbodyAttachment').append(tr);
							
					 	});
					}
					top.hangge();
				}
		});
	}

	/**
	 * 增加附件tr
	 */
	function addItemAttachment(item,index,type){
		var href='<%=basePath%>/attachment/download.do?ATTACHMENT_ID='+item.ATTACHMENT_ID;
		var ext=item.ATTACHMENT_PATH.substring(19,item.ATTACHMENT_PATH.length);
		var htmlLog='<tr>'
			+'<td class="center" style="width: 30px;">'+index+'</td>'
			+'<td class="center">'+item.ATTACHMENT_NAME+ext+'</td>'
			+'<td class="center">'+item.ATTACHMENT_MEMO+'</td>'
			+'<td class="center">'+item.ATTACHMENT_SIZE+'&nbsp;KB</td>'
			+'<td class="center">'+item.CREATE_USER+'</td>'
			+'<td class="center">'+item.CREATE_DATE+'</td>'
			+'<td class="center">'
				+'<div>'
					+'<a class="btn btn-xs btn-success" onclick=window.location.href="'+href+'">'
						+'<i class="ace-icon fa fa-cloud-download bigger-120" title="下载"></i>'
					+'</a>'
				+'</div>'
			+'</td>'
		+'</tr>';
		return htmlLog;
	}
	
	$(function() {
		getAttachment('${pd.ANSWER_ID}','PROBLEM_ANSWER');
	});
	</script>
</body>
</html>