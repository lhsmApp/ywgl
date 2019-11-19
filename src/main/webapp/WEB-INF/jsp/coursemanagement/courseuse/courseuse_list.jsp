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
<style type="text/css">
    .item-box{
      padding:5px 5px 5px 5px;
      width:245px;
      height:350px;
     /*  
		用来调试背景色     
     	background-color: rgb(194, 194, 194); 
     */ 
    }
    .box-title{
      padding:2px 1px 1px 2px;
      height:30px;
      width:230px;
    }
    .box-body{
      padding:2px 1px 1px 2px;
      height:30px;
      width:230px;
    }
    .box-foot{
      padding:2px 1px 1px 2px;
      height:30px;
      width:230px;
    }
    .btm-box{
      margin:1px 1px 1px 1px;
      width:230px;
    }
    .high-font:hover{
      color:red;
    }
  </style>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<form action="courseuse/list.do" method="post" name="Form" id="Form">
						<div class="page-header">
							<div class="nav-search">
								<span class="input-icon">
									<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords}" placeholder="这里输入关键词"/>
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</div>
						</div>
					<div class="row">
						<div class="col-xs-12">
							<c:forEach items="${varList}" var="var">
								<div class="item-box inline"><!-- 整体 -->
						      		<div style="width:185px;height:200px;">
						        		<a onclick="goChapters('${var.COURSE_ID}');" data-rel="colorbox" class="cboxElement" title="">
						          		<img width="230" height="200" alt="请上传图片" src="${var.COURSE_COVER}"></a>
						      		</div>
								    <div>
								        <div class="box-title"><span class="list-item-value-title">${var.TRAIN_PLAN_NAME}</span></div>
								        <div class="box-body"><span class="list-item-info">
								        	<i style="padding-right: 2px;" class="ace-icon fa fa-trophy no-hover orange"></i><a style="padding-left:3px;" class="list-item-info high-font" onclick="goChapters('${var.COURSE_ID}');" title="">课程名:${var.COURSE_NAME}</a></span>
								        </div>
								          <div  class="box-foot">
								          	<i class="ace-icon fa fa-clock-o"></i><span class="list-item-info">观看时间 :${var.START_DATE}至${var.END_DATE}</span>
								              <%-- <span class="list-item-info"><i style="padding-right: 2px;" class="ace-icon fa fa-trophy no-hover orange"></i>总分:${var.TEST_PAPER_SCORE}</span>
								              <span class="list-item-info" style="padding-left:3px;"> <i style="padding-right: 2px;" class="green ace-icon fa fa-pencil"></i>及格分:${var.QUALIFIED_SCORE}</span>
								           	  <span class="list-item-info" style="padding-left:3px;"><i style="padding-right: 2px;" class="blue ace-icon fa fa-inbox"></i>试题数:${var.TEST_QUESTION_NUM}</span> --%>
								          </div>
								          <div class="btm-box">
									          <a style="width:100%;" class="btn btn-round btn-info" onclick="goChapters('${var.COURSE_ID}');">
										          <font style="vertical-align: inherit;">查看章节</font>
									       	  </a>
	             								  </div>
								    </div>
								</div>
							</c:forEach>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
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
		function goChapters(id){
			 window.location.href = '<%=basePath%>courseuse/listDetail.do?COURSE_ID='+id;
		}
		
	</script>
</body>
</html>