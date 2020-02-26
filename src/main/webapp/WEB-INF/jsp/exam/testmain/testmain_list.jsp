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
				<form action="testmain/list.do" method="post" name="Form" id="Form">
				<div class="page-header"> 
				 	<label class="pull-left" style="padding: 5px;">筛选条件：</label>
							<span class="input-icon nav-search" style="margin-left: 14px;">
								<i class="ace-icon fa fa-search nav-search-icon"></i>
								<input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="keywords" value="${pd.keywords}" placeholder="请输入课件名称" />
							</span>
						<span style="margin-left:-15px;"> 
						</span>
						<button style="margin-bottom:3px;" class="btn btn-info btn-sm" onclick="search();" title="检索">
								<i class="ace-icon fa fa-search bigger-110"></i>
						</button>
				</div>
					<div class="row">
						<div class="col-xs-12">
							 <div class="widget-box transparent" id="recent-box">
						        <div class="widget-header">
						        	<h4 class="widget-title lighter smaller">
						          		<i class="ace-icon fa fa-rss orange"></i><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">我的考试列表</font></font></h4>
						        <div class="widget-toolbar no-border">
						          <ul class="nav nav-tabs" id="recent-tab">
						              <li class="active" tag="task-tab">
						              	<a data-toggle="tab" href="#task-tab" aria-expanded="true"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">未完成</font></font></a>
						              </li>
						              <li class="" tag="member-tab">
						              	<a data-toggle="tab" href="#member-tab" aria-expanded="false"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">已完成</font></font></a>
						              </li>
						          </ul>
						        </div>
						   </div>
						</div>
							<div class="widget-body">
        						<div class="widget-main padding-4">
        							<div class="tab-content padding-8">
		        						<div id="task-tab" class="tab-pane active">
										 <c:choose>
											<c:when test="${not empty paperList}">
												<c:forEach items="${paperList}" var="var">
													<div class="item-box inline"><!-- 整体 -->
											      		<div style="width:185px;height:200px;">
											        		<a onclick="beginExam('${var.TEST_PLAN_ID}','${var.TEST_PAPER_ID}')" data-rel="colorbox" class="cboxElement" title="">
											          		<img width="230" height="200" alt="请上传图片" src="${var.COURSE_COVER}"></a>
											      		</div>
													    <div>
													        <div class="box-title"><span class="list-item-value-title">${var.TEST_PLAN_NAME}</span></div>
													          <div class="box-body"><i class="ace-icon fa fa-clock-o"></i><a style="padding-left:3px;" class="list-item-info high-font" onclick="beginExam('${var.TEST_PLAN_ID}','${var.TEST_PAPER_ID}')" title="">考试时间 :${var.START_DATE}至${var.END_DATE}</a></div>
													          <div  class="box-foot">
													              <span class="list-item-info"><i style="padding-right: 2px;" class="ace-icon fa fa-trophy no-hover orange"></i>总分:${var.TEST_PAPER_SCORE}</span>
													              <span class="list-item-info" style="padding-left:3px;"> <i style="padding-right: 2px;" class="green ace-icon fa fa-pencil"></i>及格分:${var.QUALIFIED_SCORE}</span>
													           	  <span class="list-item-info" style="padding-left:3px;"><i style="padding-right: 2px;" class="blue ace-icon fa fa-inbox"></i>试题数:${var.TEST_QUESTION_NUM}</span>
													          </div>
													          <div class="btm-box">
														          <a style="width:100%;" class="btn btn-round btn-info" onclick="beginExam('${var.TEST_PLAN_ID}','${var.TEST_PAPER_ID}')">
															          <font style="vertical-align: inherit;">开始考试</font>
														       	  </a>
				               								  </div>
													    </div>
													</div>
													</c:forEach>
													<!-- 分页 -->
													<%-- <div class="position-relative">
														<table style="width:100%;">
															<tr>
																<td style="vertical-align:top;"><div class="pagination" style="padding-left: 495px;margin-top: 0px;">${page.pageStr}</div></td>
															</tr>
														</table>
													</div> --%>
												</c:when>
											<c:otherwise>
												<!--
													暂无待考信息,请联系工作人员发放试卷
												-->
											</c:otherwise>
										</c:choose> 
									</div>
									<!-- 已完成 -->
									<div id="member-tab" class="tab-pane">
										 <c:choose>
											<c:when test="${not empty varList}">
												<c:forEach items="${varList}" var="var"> 
													<div class="item-box inline"><!-- 整体 -->
											      		<div style="width:185px;height:200px;">
											        		<a onclick="checkPaper('${var.TEST_PLAN_ID}','${var.TEST_PAPER_ID}')" data-rel="colorbox" class="cboxElement" title="">
											          		<img width="230" height="200" alt="请上传图片" src="${var.COURSE_COVER}"></a>
											      		</div>
													    <div>
													        <div class="box-title"><span class="list-item-value-title">${var.TEST_PLAN_NAME}</span></div>
													          <div class="box-body"><i class="ace-icon fa fa-clock-o"></i><a style="padding-left:3px;" class="list-item-info high-font" onclick="checkPaper('${var.TEST_PLAN_ID}','${var.TEST_PAPER_ID}')" title="">考试时间 :${var.TEST_TIME}</a></div>
													          <div  class="box-foot">
													              <span class="list-item-info">
													              	<c:if test=""></c:if>
													              	<i style="padding-right: 2px;" class="ace-icon fa fa-trophy no-hover orange"></i>是否合格 : ${var.IF_QUALIFIED}
													              	<c:if test=""></c:if>
													              
													              </span>
													              <span class="list-item-info" style="padding-left:3px;"> <i style="padding-right: 2px;" class="green ace-icon fa fa-pencil"></i>分数:${var.TEST_SCORE}</span>
													           	  <span class="list-item-info" style="padding-left:3px;"><i style="padding-right: 2px;" class="blue ace-icon fa fa-inbox"></i>试题数:${var.TEST_QUESTION_NUM}</span>
													          </div>
													          <div class="btm-box">
														          <a style="width:100%;" class="btn btn-round btn-info" onclick="checkPaper('${var.TEST_PLAN_ID}','${var.TEST_PAPER_ID}')">
															          <font style="vertical-align: inherit;">回顾试卷</font>
														       	  </a>
				               								  </div>
													    </div>
													</div>
													</c:forEach>
												</c:when>
											<c:otherwise>
												<!--
													暂无待考信息,请联系工作人员发放试卷
												-->
											</c:otherwise>
										</c:choose> 
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			</div>
		</div>
		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		/* 检索 */
		function search() {
			top.jzts();
			$("#Form").submit();
		}  	
		$(function() {
			//试卷回顾页面返回时，激活已完成tab
			if('${pd.tabNo}'=="tabReview"){
				$("#recent-tab li[tag='member-tab'] a").click();
			}
			//考试页面返回时，激活未完成tab
			if('${pd.tabNo}'=="tabExam"){
				$("#recent-tab li[tag='task-tab'] a").click();
			}
		})
		
		/*开始考试*/
		function beginExam(planId,paperId){
			bootbox.confirm("确认要开始进行考试吗?", function(result) {
				if(result){
					top.jzts();
					/*
						开始考试跳转	
					*/
			window.location.href='<%=basePath%>testpaper/goExam.do?TEST_PLAN_ID='+planId+'&TEST_PAPER_ID='+paperId;
				}
			});
		}
		/*回顾考试*/
		function checkPaper(planId,paperId){
			window.location.href='<%=basePath%>testpaper/goReviewExam.do?TEST_PLAN_ID='+planId+'&TEST_PAPER_ID='+paperId;
		}
	</script>


</body>
</html>