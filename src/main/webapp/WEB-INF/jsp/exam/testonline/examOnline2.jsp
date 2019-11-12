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
 		li {
 			list-style: none;
 			width:35px; height: 35px; float: left; background-color: green; margin-left: 10px; margin-top: 3px; color: #FFF; font-size: 14px; text-align: center;
 			line-height: 35px; cursor: pointer;
 			border-radius: 3px;
 		}
 		.aswer-p {
 			width: 100%;
 			height: 30px;
 			line-height: 30px;
			cursor: pointer;
 		}
 		.aswer-p:hover {
 			background-color: #FFF;
 		}
 		input {
		}
		.subjectOption {
			padding-top: 0px;
		}
		.hidden_info {
			display: none;
		}
 	</style>
</head>
<body style="background-color: #F4F4F4;">
	<div class="container" style="margin-top: 20px;">
		<!-- 试题 -->
		<div style="width: 60%; height: 100%; float: left;">
			<div style="width: 100%; height: 80%;">
			<div>
			<h4>共${pd.TEST_QUESTION_NUM }题/总分${pd.TEST_PAPER_SCORE}分</h4></div>
				<dl subject="1" style="width:100%;">
<%-- 					<span style="display: none;" id="exam-studentId">${sessionScope.loginStudent.studentId }</span> --%>
<%-- 					<span id="sumSubject" style="display: none;">${sumSubject }</span> --%>
							<c:choose>
									<c:when test="${not empty varList}">
									<c:set value="1" var="index"></c:set>
										<c:forEach items="${varList}" var="var" varStatus="vs">	
										<dd>
									<div>
										<h5 class=" lighter gray">${vs.index+1}、${var.TEST_QUESTION_TITLE }</h5>
									</div>
									<div>
										<c:forEach items="${answerList}" var="ans" >
											<c:if test="${var.TEST_QUESTION_ID == ans.TEST_QUESTION_ID}">
											<%-- 单选 --%>
												<c:if test="${var.TEST_QUESTION_TYPE == 1 }">		
													<p class="aswer-p">
														<input class="aswer-option" id="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}-${ans.TEST_QUESTION_ITEM_TITLE}" type="radio" name="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}-${ans.TEST_QUESTION_ITEM_TITLE}" value="${ans.TEST_QUESTION_ITEM_TITLE}"/>&nbsp;
														<span class="subjectOption">${ans.TEST_QUESTION_ITEM_TITLE}</span>
														<span class="subjectOption">${ans.TEST_QUESTION_ITEM_CONTENT}</span>
													</p>	
												</c:if>		
												<%-- 多选 --%>
												<c:if test="${var.TEST_QUESTION_TYPE == 2 }">
													<p class="aswer-p">
														<input class="aswer-option" id="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}-${ans.TEST_QUESTION_ITEM_TITLE}" type="checkbox" name="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}-${ans.TEST_QUESTION_ITEM_TITLE}" value="${ans.TEST_QUESTION_ITEM_TITLE}"/>&nbsp;
														<span class="subjectOption">${ans.TEST_QUESTION_ITEM_TITLE}</span>
														<span class="subjectOption">${ans.TEST_QUESTION_ITEM_CONTENT}</span>
													</p>
												</c:if>	
												<%-- 简答题 --%>
												<c:if test="${var.TEST_QUESTION_TYPE == 3 }">
													<p class="aswer-p subject-id-${ans.TEST_QUESTION_ID}" data-sid="${ans.TEST_QUESTION_ID }" data-type="textarea" ">
													<textarea class="aswer-textarea" name="chooseRight-${esm.subject.subjectId }" style="width:100%;height:100px;" onblur="textareaSubmit(this)"></textarea>
													</p>
												</c:if>						
											</c:if>
										</c:forEach>			
									</div>
									</dd>
										<c:set value="${index+1 }" var="index"></c:set>
									</c:forEach>																		
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >暂无试题数据，请联系管理员!</td>
									</tr>
								</c:otherwise>
							</c:choose>			
				</dl>
			</div>
			<div style="width:100%; height:20%; margin-top: 170px;">
				<button style="float: left;" id="preSubject" type="button" class="btn btn-default btn-lg" onclick="preQuestion()">上一题</button>
				<button style="float: left;margin-left: 10px;" id="nextSubject" type="button" class="btn btn-default btn-lg" onclick="nextQuestion()">下一题</button>
			</div>
		</div>
		<span id="examEndRefresh_classId" class="hidden_info">${classId }</span>
		<span id="examEndRefresh_gradeId" class="hidden_info">${gradeId }</span>
		
		<!-- 答题卡 -->
		<span id="beginTime" style="display: none;">${beginTime }</span>
		<span id="examTime" style="display: none;">${EXAM_TIME }</span>
		<div style="width: 38%; float: right;margin-top: 20px;">
			<div style="width:100%; height:63px;text-align: center;">
				<div style="float: left; width: 42%;height: 100%;">
					<h2>答题卡</h2>
				</div>
				<div style="float: right; width: 55%;height: 100%;line-height: 63px; text-align: left;">
					<a href="submit?studentId=${sessionScope.loginStudent.studentId }&examPaperId=${examPaperId }&classId=${classId }&gradeId=${gradeId }" type="button" class="btn btn-default btn-sm" onclick="return confirm('确定提交吗?')">提交</a>
					<%--隐藏表单，用于考试结束且考生未手动提交试卷 自动提交 --%>
					<form action="submit"method="post" style="display: none;">
						<input type="hidden" value="${sessionScope.loginStudent.studentId }" name="studentId" />
						<input type="hidden" value="${examPaperId }" name="examPaperId" />
						<input type="hidden" value="${classId }" name="classId" />
						<input type="hidden" value="${gradeId }" name="gradeId" />
					</form>
					<span style="font-weight: 600;">剩余时间：
						<span id="lastTime" style="color: #00A06B;font-size: 16px;font-weight: 900;">
							<span id="time_min">${pd.ANSWER_TIME}</span>"
							<span id="time_sec">00</span>'
						</span>
					</span>
				</div>
			</div>
<!-- 			页面生成答题序号按钮 -->
			<div style="width: 100%; height: 100%;margin-top: 10px;">
				<ul>
					<c:if test="${varList != null }">
						<c:set value="1" var="indexAswer"></c:set>
						<c:forEach items="${varList }" var="var">
								<c:if test="${indexAswer == 1 }">
									<li style="background-color: red;">
										${indexAswer }
									</li>
								</c:if>
								<c:if test="${indexAswer != 1 }">
									<li>
										${indexAswer }
									</li>
								</c:if>
								<c:set value="${indexAswer+1 }" var="indexAswer"></c:set>
						</c:forEach>
					</c:if>
				</ul>
			</div>		
		</div>
		<!--   答题卡  end -->
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
		
			//将已选单选试题对应题号变为已选状态
    		var temp_subjectIds = $(".temp-subjectId").text();
    		var temp_results = $(".temp-result").text();
			var subjectArr = temp_subjectIds.split(",");
			var resultArr = temp_results.split(",");
    		for(var i=0; i<subjectArr.length-1; i++) {
    			$(".aswer-option[name=chooseRight-"+subjectArr[i]+"][value="+resultArr[i]+"]").attr("checked", "checked");
    		}
    		
    		//将已选多选试题对应题号变为已选状态
    		$(".temp-checkbox-subjectId").each(function(i,e){
    			var rst = $(e).next("span.temp-checkbox--result").text();
    			var rstArr = rst.split(",");
    			for(var i=0;i<rstArr.length;i++){
    				$(".aswer-option[name=chooseRight-"+$(e).text()+"][value="+rstArr[i]+"]").attr("checked", "checked");
    			}
    		});
    		
    		//将已做简答试题对应题号变为已选状态
    		$(".temp-textarea-subjectId").each(function(i,e){
    			var rst = $(e).next("span.temp-textarea--result").text();
    			$(".aswer-textarea[name=chooseRight-"+$(e).text()+"]").val(rst);
    		});
    		
    		//加载已选试题对应答题卡信息
    		for(var i=0; i<$("li").size(); i++) {
    			console.log($("li").size());
    			for(var j=0; j<4; j++) {
    				//如果是简答
    				if($("dd").eq(i).children("div").eq(1).children("p").eq(j).attr('data-type') == 'textarea'){
    					var e = $("dd").eq(i).children("div").eq(1).children("p").eq(j).children("textarea");
    					if($(e).val() != ''){
    						$("li").eq(i).css("background-color", "orange");
    					}
    					break;
    				}
    				if($("dd").eq(i).children("div").eq(1).children("p").eq(j).children("input").get(0).checked) {
    					$("li").eq(i).css("background-color", "orange");
    					break;
    				}
    			}
    		}
    		$("li").first().css("background-color", "red");
	 	});

	</script>


</body>
</html>