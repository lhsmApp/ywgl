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
 			width:35px; height: 35px; float: left; background-color: gray; margin-left: 10px; margin-top: 3px; color: #FFF; font-size: 14px; text-align: center;
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

 	</style>
</head>
<body  class="no-skin">
	<div class="container" style="margin-top: 20px;">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						 <div class="col-xs-12">						 
						 	<div>
								<h4 style='color: #ff0033;'>您该试卷的得分为${pd.TEST_SCORE}分</h4>
							</div>
							<div style="margin-top: 20px;">
<%-- 					<span style="display: none;" id="exam-studentId">${sessionScope.loginStudent.studentId }</span> --%>
<%-- 					<span id="sumSubject" style="display: none;">${sumSubject }</span> --%>
							<c:choose>
									<c:when test="${not empty varList}">
									<c:set value="1" var="index"></c:set>
										<c:forEach items="${varList}" var="var" varStatus="vs">	
										<dd>
									<div>
										<h5 class=" lighter gray" id="${var.TEST_PAPER_ID}-${vs.index+1}">${vs.index+1}、${var.TEST_QUESTION_TITLE }</h5>
									</div>
									<div>
										<c:forEach items="${answerList}" var="ans" >
											<c:if test="${var.TEST_QUESTION_ID == ans.TEST_QUESTION_ID}">
											<%-- 单选 --%>
												<c:if test="${var.TEST_QUESTION_TYPE == 1 }">		
													<p class="aswer-p">
														<input class="aswer-option" id="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}-${ans.TEST_QUESTION_ITEM_TITLE}" type="radio" name="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}" value="${ans.TEST_QUESTION_ITEM_TITLE}"  disabled/>&nbsp;
														<span class="subjectOption">${ans.TEST_QUESTION_ITEM_TITLE}.</span>
														<span class="subjectOption">${ans.TEST_QUESTION_ITEM_CONTENT}</span>														
													</p>	
												</c:if>		
												<%-- 多选 --%>
												<c:if test="${var.TEST_QUESTION_TYPE == 2 }">
													<p class="aswer-p">
														<input class="aswer-option" id="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}-${ans.TEST_QUESTION_ITEM_TITLE}" type="checkbox" name="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}" value="${ans.TEST_QUESTION_ITEM_TITLE}"  disabled/>&nbsp;
														<span class="subjectOption">${ans.TEST_QUESTION_ITEM_TITLE}.</span>
														<span class="subjectOption">${ans.TEST_QUESTION_ITEM_CONTENT}</span>
													</p>
												</c:if>	
												<%-- 简答题 --%>
												<c:if test="${var.TEST_QUESTION_TYPE == 3 }">
													<p class="aswer-p subject-id-${ans.TEST_QUESTION_ID}" data-sid="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}" data-type="textarea" ">
													<textarea class="aswer-textarea" id="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}" name="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}" style="width:100%;height:50px;" ></textarea>
													</p>
												</c:if>						
											</c:if>
										</c:forEach>
										<div style="background:#82D900; color:#66FFcc"><span style='color: #F5FFE8;' >您的答案：${var.TEST_ANSWER}&nbsp;&nbsp;&nbsp;正确答案：${var.TEST_CORRECT_ANSWER}</span></div>
										<div style="background:#82D900; color:#66FFcc"><span style='color: #F5FFE8;' >答案解析：${var.TEST_ANSWER_NOTE}</span></div>
									</div>
									</dd>
										<c:set value="${index+1 }" var="index"></c:set>
									</c:forEach>																		
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >暂无考试记录!</td>
									</tr>
								</c:otherwise>
							</c:choose>			
				</dl>
							</div>
						 </div>
					</div>
				</div>
			</div>
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
			//对试卷试题及答案做循环		
			 <c:forEach items="${varList}" var="vas" varStatus="vs">  
			 	//获取单选框和复选框name
				var name='${vas.TEST_PAPER_ID}'+'-'+'${vas.TEST_QUESTION_ID}';
				//循环根据用户所选答案给单选框赋值
				 if('${vas.TEST_QUESTION_TYPE}'=='1'){
					 $("input[name="+name+"][value='${vas.TEST_CORRECT_ANSWER}']").attr("checked",true);				 
				 }	
				//循环根据用户所选答案给多选框赋值
				 var rightAnswer = '${vas.TEST_ANSWER}';//考试答案
				 var result = rightAnswer.split(",");//
				 var checkNames = document.getElementsByName(name);
				 for(var i=0;i<checkNames.length;i++){
					 for(var j=0;j<result.length;j++){
						 if(checkNames[i].value==result[j]){
							 checkNames[i].checked=true;
							 break;
						 }
					 }
				 }
			 </c:forEach> 
	 	});

		
				//根据选项卡按钮定位到指定题目
			   function showQuestionItem(node){
				   //console.log(node);
				   $("html,body").animate({scrollTop: $("#"+node).offset().top}, 500);			   
			   }
			   function getRadio(x)
			        {
			           d=document.getElementsByTagName('li')
			           for(p=d.length;p--;){
			                if(d[p].id==x){d[p].style.backgroundColor='green'}
			           } 
			         }
	</script>


</body>
</html>