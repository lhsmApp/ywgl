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
<style type="text/css">
	.course-box{
		margin:5px 15px 15px 15px;
		width:170px;
		height:350px;
	}
	.title-box{
		margin:10px 0px 0px 5px;
		width:170px;
	}
 	.btm-box{
		margin:5px 0px 0px 5px;
		width:170px;
	}
	.high-font:hover{
    	color:red;
	}
</style>
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
									<!-- <span class="green middle bolder">课程内容: &nbsp;</span> -->
									<div class="pull-right">
										<div class="btn-toolbar inline middle no-margin">
											<div data-toggle="buttons" class="btn-group no-margin">
												<button id="btnSave" class="btn btn-info btn-xs" onclick="add()">
													<i class="ace-icon fa fa-chevron-down bigger-110"></i><span>新增章节内容</span>
												</button>
												<button class="btn btn-primary btn-xs btn-danger" onclick="goCourseDetail();">
													<i class="ace-icon fa fa-reply light-white bigger-110"></i><span>返回课程列表</span>
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
							<div class="col-xs-3">
									<input type="hidden" id="COURSE_ID" name="COURSE_ID" value="${COURSE_ID}"/>
									<ul id="chapterList" class="item-list">
											<li class="item-grey clearfix"><div><label style="margin-bottom:5px; text-align: center;display:block;"><span class="list-item-value-title">课程列表</span></label></div>
										<c:forEach items="${varList}" var="var">
										   <li class="item-grey clearfix" onclick="showDetail('${var.CHAPTER_ID}');">
										   		<div>
										   			<label class="inline" style="margin-bottom:5px;"><span class="list-item-value-title">${var.CHAPTER_NAME}</span></label>
										   		</div>
									       		<div>
									       			<label class="inline"><span class="list-item-info">类型:&nbsp;</span><span class="list-item-value">${var.CHAPTER_TYPE}</span></label>
										       		<%-- <label class="inline"><span class="list-item-info">时长:&nbsp;</span><span class="list-item-value">${var.VIDEO_DURATION}</span></label> --%>
										        	<label class="inline pull-right"><span class="list-item-info">小节:&nbsp;</span><span class="list-item-value">${var.count}小节</span></label>
									       		</div>
									       		<div>
											        <label class="inline"><span class="list-item-info">上传时间:&nbsp;</span><span class="list-item-value">${var.CREATE_TIME}</span></label>
											        <label class="inline pull-right"><span class="list-item-info">上传人:&nbsp;</span>${var.CREATE_USER}</label>
									       		</div>
									       		
									        </li>
										</c:forEach>
									</ul>					
							</div>
							<form action="coursedetail/list.do" method="post" name="Form" id="Form">
								<div id="changeDiv" class="col-xs-9">
									<c:forEach items="${listSection}" var="pd">
										<div class="col-xs-9 course-box"><!-- 整体 -->
											<div style="width:170px;height:170px;">
												<a data-rel="colorbox" class="cboxElement">
													<img width="170" height="170" alt="请上传图片"src="${pd.VIDEO_COVER}">
												</a>
											</div>
											<div>
												<div class="title-box">
													<span class="list-item-value-title">${pd.CHAPTER_NAME}</span>
												</div>		
												<div class="title-box">
													<!-- <a class="list-item-info high-font" target="_blank" href="" title="">
													视频
													</a>TODO视频时长 -->
												</div>
												<div class="title-box">
													<span class="list-item-info">类型:<i style="padding-left:5px;" class=" ace-icon fa fa-film file-video green"></i>
													</span>
												</div>
												<div  class="title-box">
													<i class="ace-icon fa fa-clock-o"></i>
													<span class="list-item-info">${pd.CREATE_TIME}</span>
													<i style="padding-left: 6%" class="ace-icon glyphicon glyphicon-user"></i>
													<span class="list-item-info">${pd.CREATE_USER}</span>
												</div>
												<div class="btm-box">
													<a class="btn btn-mini btn-info" title="编辑" onclick="goEditSection('${pd.CHAPTER_ID}');"><i class="ace-icon fa fa-pencil-square-o bigger-130"></i>编辑</a>
													<a style="float:right;" class="btn btn-mini btn-danger" title="删除" onclick="del('${pd.CHAPTER_ID}');">删除<i class="ace-icon fa fa-trash-o bigger-130"></i></a>
												</div>					
											</div>
										</div>
									</c:forEach>
								</div>
							</form>
						</div>
					</div>
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
		$(function(){
			//限制字符个数
	        $(".fyhoverflow").each(function() {
	            var maxwidth = 11;
	            if ($(this).text().length > maxwidth) {
	                $(this).text($(this).text().substring(0, maxwidth));
	                $(this).html($(this).html() + '...');
	            }
	        });
		});
	</script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		function showDetail(Id){
			if(event){
				$("#chapterList li").each(function(){
					var item = this;
					$(item).removeClass("bc-light-orange");
				}); 
				$($(event.srcElement).closest('li')).addClass("bc-light-orange");
			}else{
				$("#chapterList li").first().addClass("bc-light-orange");
			}
			$.ajax({
				type: "POST",
				url: '<%=basePath%>coursedetail/findsectionlist.do?tm='+new Date().getTime(),
		    	data: {"CHAPTER_PARENT_ID":Id,"COURSE_ID":'${COURSE_ID}'},
				dataType:'json',
				cache: false,
				success: function(response){
					setSection(eval(response.message));
				}
			}); 		
		}
		
		//动态回显数据
		function setSection(data){
			var html='';
			for(var i = 0;i<data.length;i++){
		        html += setDiv(data[i]);
			}
			$('#changeDiv').html(html);
		}

		//<a class="list-item-info high-font" target="_blank" href="" title="">视频</a>TODO视频时长
		//拼接数据
		function setDiv(data){
			var div = '<div class="col-xs-9 course-box">'
					
					+ '<div style="width:170px;height:170px;"><a data-rel="colorbox" class="cboxElement"><img width="170" height="170" alt="请上传图片"src="'+ data.VIDEO_COVER + '"></a></div>'
					
					+ '<div><div class="title-box"><span class="list-item-value-title">'+ data.CHAPTER_NAME +'</span></div>'
					
					+ '<div class="title-box"></div><div class="title-box"><span class="list-item-info">类型:<i style="padding-left:5px;" class=" ace-icon fa fa-film file-video green"></i>'
					
					+ '</span></div><div class="title-box"><i class="ace-icon fa fa-clock-o"></i><span class="list-item-info">'+ data.CREATE_TIME + '</span>'
					
					+ '<i style="padding-left: 6%" class="ace-icon glyphicon glyphicon-user"></i><span class="list-item-info">'+ data.CREATE_USER +'</span></div>'
					
					+'<div class="btm-box"><a class="btn btn-mini btn-info" title="编辑" onclick="goEditSection(\''+data.CHAPTER_ID+'\');"><i class="ace-icon fa fa-pencil-square-o bigger-130"></i>编辑</a>'
					
					+'<a style="float:right;" class="btn btn-mini btn-danger" title="删除" onclick="del(\''+data.CHAPTER_ID+'\');">删除<i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>'
					+'</div></div>';
					return div;
		}
		
		//新增
		function add(){
			 top.jzts();
			 var COURSE_ID = $("#COURSE_ID").val();
			 window.location.href = '<%=basePath%>coursedetail/goAdd.do?COURSE_ID='+COURSE_ID;
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
		//修改
		function edit(Id){
			widow.location.href='<%=basePath%>coursedetail/goEdit.do?COURSEDETAIL_ID='+Id;
		}
		//返回课程列表
		function goCourseDetail(){
			 window.location.href = '<%=basePath%>coursebase/list.do';
		}
	</script>
</body>
</html>