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
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
</head>
<body class="no-skin">
<div class="main-container" id="main-container">
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="page-header">
					<table style="width:100%;">
						<tbody>
							<tr>
								<td style="vertical-align:top;">
									<span class="green middle bolder">课程内容: &nbsp;</span>
									<div class="pull-right">
										<div class="btn-toolbar inline middle no-margin">
											<div data-toggle="buttons" class="btn-group no-margin">
												<button id="btnAdd" class="btn btn-primary btn-xs" onclick="addChapter();">
													<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>新增章节</span>
												</button>
												
												<button class="btn btn-primary btn-xs btn-danger" onclick="goCourseDetail();">
													<i class="ace-icon fa fa-reply light-white bigger-110"></i><span>返回列表</span>
												</button>
												
											</div>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="row">
					<div class="col-xs-12">
					<form action="coursedetail/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="COURSE_ID" id="COURSE_ID" value="${COURSE_ID}"/> 
						 <c:forEach items="${varList}" var="var" varStatus="index">
							<div id="task-tab" class="tab-pane active">
								<h4 class="smaller lighter gray">
									<font style="vertical-align: inherit;">第${index.count}章 :${var.CHAPTER_NAME}
										<a onclick="del('${var.CHAPTER_ID}');" class="red pull-right"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>
										<a onclick="goEditChapter('${var.CHAPTER_ID}');" class="blue pull-right" style="padding-right:5px"><i style="padding-left:5px;" class="ace-icon fa fa-pencil bigger-130"></i></a>
									</font>
								</h4>
								<input type="hidden" name="CHAPTER_ID" id="CHAPTER_ID" value="${var.CHAPTER_ID}"/>
								<ul id="tasks_1" class="item-list ui-sortable">
									 <c:forEach items="${var.subCourseDetails}" var="pd" varStatus="index">
										<li class="item-red clearfix ui-sortable-handle">
											<label class="inline">
												<i class="ace-icon fa fa-list green"></i><span style="padding-left:5px;" class="lbl">第${index.count}小节 :${pd.CHAPTER_NAME}</span>
											</label>
											<div class="pull-right action-buttons">
												<%-- ${pd.CHAPTER_ID} --%>
												<span class="vbar">
												<i class=" ace-icon fa fa-film file-video green"></i>
												<a onclick="del('${pd.CHAPTER_ID}');" class="red pull-right"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>
												<a onclick="goEditSection('${pd.CHAPTER_ID}')" class="blue pull-right" style="padding-right:5px"><i style="padding-left:5px;" class="ace-icon fa fa-pencil bigger-130"></i></a>
												</span>
											</div>
										</li>
									</c:forEach>
									<li id="liBefore" class="item-default clearfix ui-sortable-handle">
										<label class="inline">
											<span><a onclick="addSection('${var.CHAPTER_ID}');" class="btn btn-minier bigger btn-primary">新增小节</a></span>
										</label>
									</li>
								</ul>
							</div>
						 </c:forEach> 
					</form>
					<table>
						<tbody>
							<tr>
								<td style="text-align: center;" colspan="10"></td>
							</tr>
						</tbody>
					</table>
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
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//修改章节 
		function goEditChapter(CHAPTER_ID){
			 top.jzts();
			 var COURSE_ID = $("#COURSE_ID").val();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>coursedetail/goeditchapter.do?CHAPTER_ID='+CHAPTER_ID;
			 diag.Width = 400;
			 diag.Height = 180;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 history.go(0); //刷新页面
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//修改小节
		function goEditSection(CHAPTER_ID){
			 top.jzts();
			 var COURSE_ID = $("#COURSE_ID").val();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>coursedetail/goeditsection.do?CHAPTER_ID='+CHAPTER_ID;
			 diag.Width = 600;
			 diag.Height = 280;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 history.go(0); //刷新页面
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//新增章节
		function addChapter(){
			 top.jzts();
			 var COURSE_ID = $("#COURSE_ID").val();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>coursedetail/gochapter.do?COURSE_ID='+COURSE_ID;
			 diag.Width = 400;
			 diag.Height = 180;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 history.go(0); //刷新页面
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//新增小节
		function addSection(CHAPTER_ID){
			 top.jzts();
			 var COURSE_ID = $("#COURSE_ID").val();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>coursedetail/gosection.do?COURSE_ID=' + COURSE_ID + '&CHAPTER_PARENT_ID='+CHAPTER_ID;
			 diag.Width = 600;
			 diag.Height = 280;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 history.go(0); //刷新页面
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					$.ajax({
						type: "POST",
						url: url = "<%=basePath%>coursedetail/delete.do",
				    	data: {"CHAPTER_ID":Id},
						dataType:'json',
						cache: false,
						success: function(data){
							$(top.hangge());
							history.go(0); //刷新页面
						}
					});
				
				}
			});
		}
		
		//返回列表
		function goCourseDetail(){
			var COURSE_ID = $("#COURSE_ID").val();
			 window.location.href = '<%=basePath%>coursedetail/list.do?COURSE_ID='+COURSE_ID;
		}
		</script>
</body>
</html>