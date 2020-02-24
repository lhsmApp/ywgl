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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
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
							
						<!-- 检索  -->
						<form action="notice/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="搜索内容关键词" class="nav-search-input" id="NOTICE_CONTENT" autocomplete="off" name="NOTICE_CONTENT" value="${pd.NOTICE_CONTENT}"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
                                <td style="vertical-align:top;padding-left:2px;">
                                    <!-- <select class="chosen-select form-control" name="name" id="id" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
                                    <option value=""></option>
                                    <option value="">全部</option>
                                    <option value="">1</option>
                                    <option value="">2</option>
                                    </select> -->
                                    <div class="nav-search">
                                        <span class="input-icon">
                                            <input type="text" placeholder="搜索发布人" class="nav-search-input" id="NAME" autocomplete="off" name="NAME" value="${pd.NAME}"/>
                                            <i class="ace-icon fa fa-search nav-search-icon"></i>
                                        </span>
                                    </div>
                                </td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" autocomplete="off" id="lastStart"  value="${pd.lastStart}" type="text" data-date-format="yyyy-mm-dd 00:00:00"  style="width:116px;" placeholder="发布时间（开始）" title="发布时间（开始）"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" autocomplete="off" id="lastEnd"  value="${pd.lastEnd}" type="text" data-date-format="yyyy-mm-dd 00:00:00" style="width:116px;" placeholder="发布日期（结束）" title="发布日期（结束）"/></td>
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								<c:if test="${accessSourceType==2}">
                                <td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs"  title="检索" onclick="add();"><i id="nav-add-icon" class="ace-icon glyphicon glyphicon-plus blue"></i>新建公告</a></td>
								</c:if>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">公告内容</th>
									<th class="center">发布人</th>
									<th class="center">开始时间</th>
									<th class="center">结束时间</th>
									<th class="center">附件</th>
									<c:if test="${accessSourceType==2}">
									<th class="center">操作</th>
									</c:if>
								</tr>
							</thead>
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.NOTICE_CONTENT}</td>
											<td class='center'>${var.NAME}</td>
											<td class='center'><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${var.START_TIME}" /></td>
											<td class='center'><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${var.END_TIME}" /></td>
											<td class='center'>
											<c:if test="${'' != var.ATTACHMENT_PATH}">
												<a href="<%=basePath%>notice/download.do?address=${var.ATTACHMENT_PATH}">
													<i class="ace-icon fa fa-cloud-download bigger-120" title="下载"></i>
												</a>
											</c:if>
											<c:if test="${'' == var.ATTACHMENT_PATH}">
												无
											</c:if>
											<c:if test="${accessSourceType==2}">
											</td>
											<td class="center">
												<div class="hidden-sm hidden-xs btn-group">
													
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.NOTICE_ID}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													<a class="btn btn-xs btn-danger" onclick="del('${var.NOTICE_ID}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
												</div>
											</td>
											</c:if>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
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
		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
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
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
		});
		
		//新增
		function add(){
			window.location.href='<%=basePath%>notice/goAdd.do';
		}
		
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>notice/delete.do?NOTICE_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						$(top.hangge());//关闭加载状态
						console.log(data)
						location.reload()
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			window.location.href='<%=basePath%>notice/goEdit.do?NOTICE_ID='+Id;
		}
		
	</script>


</body>
</html>