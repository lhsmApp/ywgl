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
			text-indent:2em;
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
						 <div class="col-xs-12" >
						 	<div >
								<!-- 答题卡 -->
								<span id="beginTime" style="display: none;">${beginTime }</span>
								<span id="examTime" style="display: none;">${EXAM_TIME }</span>
								<div style="width: 38%; float: right;margin-top: 20px;">
									<div style="width:100%; height:63px;text-align: center;">
										<div style="float: left; width: 42%;height: 100%;" >
											<h2>答题卡</h2>
										</div>
										<div style="float: right; width: 55%;height: 100%;line-height: 63px; text-align: left;">
<%-- 											<a href="submit?studentId=${sessionScope.loginStudent.studentId }&examPaperId=${examPaperId }&classId=${classId }&gradeId=${gradeId }" type="button" class="btn btn-default btn-sm" onclick="return confirm('确定提交吗?')">提交</a> --%>
											   <label class="btn btn-sm btn-warning" onclick="report(2)"> 
												            <i class="ace-icon  bigger-110"></i>提交
												            </label> 
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
									<span id="testPlanId" style="display: none;">${pd.TEST_PLAN_ID}</span>
										<ul id="answerNo">
											<c:if test="${varList != null }">
												<c:set value="1" var="indexAswer"></c:set>
												<c:forEach items="${varList }" var="var">
														<c:if test="${indexAswer == 1 }">
															<li  id="${indexAswer }"  onclick="showQuestionItem('${var.TEST_PAPER_ID}-${indexAswer}');">
																${indexAswer }
															</li>
														</c:if>
														<c:if test="${indexAswer != 1 }">
															<li id="${indexAswer }" onclick="showQuestionItem('${var.TEST_PAPER_ID}-${indexAswer}');">
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
						 	<div style="margin-top: 20px;">
						 		<a style="cursor: pointer;" onclick="returnTestMain()"><span class="label label-success arrowed">返回考试列表</span></a>
						 		<div>---------------------------</div>
								<h4>共${pd.TEST_QUESTION_NUM }题,总分${pd.TEST_PAPER_SCORE}分</h4>
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
										<h5 class=" lighter gray" id="${var.TEST_PAPER_ID}-${vs.index+1}">${vs.index+1}、${var.TEST_QUESTION_TITLE }(${var.TEST_QUESTION_SCORE}分)</h5>
									</div>
									<div>
										<c:forEach items="${answerList}" var="ans" >
											<c:if test="${var.TEST_QUESTION_ID == ans.TEST_QUESTION_ID}">
											<%-- 单选 --%>
												<c:if test="${var.TEST_QUESTION_TYPE == 1 }">		
													<p class="aswer-p">
														<input class="aswer-option" id="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}-${ans.TEST_QUESTION_ITEM_TITLE}" type="radio" name="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}" value="${ans.TEST_QUESTION_ITEM_TITLE}"onclick="getRadio('${vs.index+1}')"/>&nbsp;
														<span class="subjectOption">${ans.TEST_QUESTION_ITEM_TITLE}.</span>
														<span class="subjectOption">${ans.TEST_QUESTION_ITEM_CONTENT}</span>
													</p>	
												</c:if>		
												<%-- 多选 --%>
												<c:if test="${var.TEST_QUESTION_TYPE == 2 }">
													<p class="aswer-p">
														<input class="aswer-option" id="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}-${ans.TEST_QUESTION_ITEM_TITLE}" type="checkbox" name="${ans.TEST_PAPER_ID}-${ans.TEST_QUESTION_ID}" value="${ans.TEST_QUESTION_ITEM_TITLE}"/>&nbsp;
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
			
			if('${pd.ANSWER_TIME}'>0){
				Time();
				 //report()数字1代表自动提交，2代表手工提交
				//var test1 =window.setTimeout('report(1)','${pd.ANSWER_TIME}'*60000);
				//var test1 =window.setTimeout('report(1)',360000);
				// 定时器每秒调用一次Time()
				 timer =setInterval("Time()", 1000); 
			}
			 
			 <c:forEach items="${varList}" var="vas" varStatus="vs">            	         
	           if('${vas.TEST_QUESTION_TYPE}'=='2'){
	            //判断复选框是否被选中   
		        var nameCheck='${vas.TEST_PAPER_ID}'+'-'+'${vas.TEST_QUESTION_ID}'; 
		        //注册复选框改变事件
		        $("input[name='"+nameCheck+"']").change(function (){
		        	if("input:checkbox[name='"+nameCheck+"']:checked"){
		        		$("#"+'${vs.index+1}').css({"background-color":"green"});	
		        	}else{
		        		$("#"+'${vs.index+1}').css({"background-color":"gray"});		 
		        	}
		        	       	 
		        })
	      	}
	        </c:forEach> 
	 	});

		 var examTime='${pd.ANSWER_TIME}'*60;
		 //var examTime=70;
		// console.log(examTime);
		 function Time(){	
		     if (examTime >= 0) {
		    	 minutes = Math.floor(examTime / 60);
		    	 seconds = Math.floor(examTime % 60);
		    	msg = "距离结束还有" + minutes + "分" + seconds + "秒";
		    	//if (examTime == 1 * 60)alert("还剩5分钟");
	    	    --examTime;		
		    	} else{
		    	clearInterval(timer);
		    	report(1);
		    	alert("答题时间到，结束!");
		    	      }
		        time_min.innerHTML=minutes;
		        time_sec.innerHTML=seconds
		    }

		//提交时,获取单选和复选框选择答案
		function report(type){
			 //report()数字1代表自动提交，2代表手工提交
			 console.log("已提交");
			var message;
			if(type==2){
				message=confirm('确定提交吗?');//手动提交
			}
			if(type==1){
				message=true;//自动提交
			}	
			 var paperId=undefined;
			 if(message==true){
				//存放试题ID和用户提交的答案  
					//var m = new Map();	
					var data={begin:''};
			        <c:forEach items="${varList}" var="vas">
			        paperId='${vas.TEST_PAPER_ID}';
				        if('${vas.TEST_QUESTION_TYPE}'=='1'){
				        	//获取单选题选项
					        var nameRadio='${vas.TEST_PAPER_ID}'+'-'+'${vas.TEST_QUESTION_ID}';
							var val=$('input:radio[name="'+nameRadio+'"]:checked').val();
					        if(val!=null){
					        	 //m.put('${vas.TEST_QUESTION_ID}',val );
					        	 data.${vas.TEST_QUESTION_ID}=val;					           
					        }
				        }
				        if('${vas.TEST_QUESTION_TYPE}'=='2'){
				        	//获取多选题选项
						      var str = '';
						  	  var nameCheck='${vas.TEST_PAPER_ID}'+'-'+'${vas.TEST_QUESTION_ID}';	
							  for(var i=0;i < document.getElementsByName(nameCheck).length;i++){
							  	if(document.getElementsByName(nameCheck)[i].checked){
							  	if(str=='') str += document.getElementsByName(nameCheck)[i].value;
							  	else str += '#&#' + document.getElementsByName(nameCheck)[i].value;				 
							  }
							  	if(typeof str == "undefined" || str == null || str == ""){
							  		
							  	}else{
							  		 //m.put('${vas.TEST_QUESTION_ID}',str );
							  		 data.${vas.TEST_QUESTION_ID}=str;
							  	}
							  	
							}
				        }
				        if('${vas.TEST_QUESTION_TYPE}'=='3'){
				        	var nameCheck='${vas.TEST_PAPER_ID}'+'-'+'${vas.TEST_QUESTION_ID}';	
				        	var tx =  $("#"+nameCheck).val();
				        	if(typeof tx == "undefined" || tx == null || tx == ""){
						  		
						  	}else{
						  		//m.put('${vas.TEST_QUESTION_ID}',tx );
						  		 data.${vas.TEST_QUESTION_ID}=tx;
						  	}				        	 
				        }
			        </c:forEach>		     	
			        $.ajax({
						   type: "POST",
						   url: '<%=basePath%>testpaper/examResult.do',
						   data: {'result':JSON.stringify(data),TEST_PAPER_ID:paperId,TEST_PLAN_ID:$("#testPlanId").text()},
						   dataType:'json',
						   //beforeSend: validateData,
						   cache: false,
						   success: function (data) {
							   $(top.hangge());//关闭加载状态
								if(typeof data.msg == "undefined" || data.msg == null || data.msg == ""){
									//考试未通过
									   if(data.IF_QUALIFIED==0){
											bootbox.dialog({
												message: "<div><span class='bigger-110' style='color: red;'>您的分数"+data.TEST_SCORE+",考试未通过</span></div><div><span class='bigger-110'>满分"+data.TEST_PAPER_SCORE+"</span></div><div><span class='bigger-110'>及格分数"+data.QUALIFIED_SCORE+"</span></div>",
											});	
									   }else{
										   bootbox.dialog({
										   message: "<div><span class='bigger-110' style='color: red;'>您的分数"+data.TEST_SCORE+",恭喜您通过考试</span></div><div><span class='bigger-110'>满分"+data.TEST_PAPER_SCORE+"</span></div><div><span class='bigger-110'>及格分数"+data.QUALIFIED_SCORE+"</span></div>",
										   });	
									   }
								}else{
									bootbox.dialog({
										message: "<span class='bigger-110'>"+data.msg+"</span>",
									});	
								}

						         },
						   error: function () {
								bootbox.dialog({
									message: "<span class='bigger-110'>考试失败,请联系相关人员</span>",
								});	
						          }
						  });
			 }
		 }
				//根据选项卡按钮定位到指定题目
			   function showQuestionItem(node){
				   //console.log(node);
				   $("html,body").animate({scrollTop: $("#"+node).offset().top}, 500);			   
			   }
			   function getRadio(x)
			        {
			           d=document.getElementsByTagName('li')
			           for(p=d.length;p--;){
			                if(d[p].id==x){
			                	d[p].style.backgroundColor='green';
			                	}
			           } 
			         }
			   function returnTestMain(){		   
					   window.location.href='<%=basePath%>testmain/list.do?tabNo=tabExam';
			   }
	</script>


</body>
</html>