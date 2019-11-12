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
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	<!-- 树形下拉框start -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
<link rel="stylesheet" type="text/css" href="plugins/selectZtree/import_fh.css" />
<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="plugins/selectZtree/ztree/ztree.css"></link>
<!-- 树形下拉框end -->
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="page-header">
					<table style="width:100%;">
						<tbody>
							<tr>
								<td style="vertical-align:top;">
									<span class="green middle bolder">试卷详细信息: &nbsp;</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="row">
					<div class="col-xs-12">
					<form action="testpaper/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="TEST_PAPER_ID" id="TEST_PAPER_ID" value="${pd.TEST_PAPER_ID}"/>
						<input type="hidden" id="questionDifficulty" value="${pd.TEST_QUESTION_DIFFICULTY}"/>
						<div id="zhongxin" style="padding-top: 13px;">
							<div id="task-tab" class="tab-pane active">
							<h5 class="smaller lighter gray">试卷名称:
							<input type="text" id="TEST_PAPER_TITLE" name="TEST_PAPER_TITLE" placeholder="请输入题目内容,请不要超过200个字符!" value="${pd.TEST_PAPER_TITLE}" class="inline" style="width: 90%;">
							</h5>
							<ul id="tasks_1" class="item-list ui-sortable">
									<li class="item-orange clearfix ui-sortable-handle">
										<label class="inline"><span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="menu-icon fa fa-tachometer orange"></i>试卷难度:</span></label>
									<select id="TEST_PAPER_DIFFICULTY" name="TEST_PAPER_DIFFICULTY" class="form-control inline" style="width:155px;">
										<option value="1">简单</option>
										<option value="2">正常</option>
										<option value="3">困难</option>
									</select>	
									</li>
									<li id="liBefore" class="item-green clearfix ui-sortable-handle">
										<label class="inline">
												<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="menu-icon fa fa-book green"></i>试卷分类:</span>
											</label>
													<input type="hidden" name="COURSE_TYPE_ID" id="COURSE_TYPE_ID"  value="${pd.COURSE_TYPE_ID}"/>
													<div class="inline"><div class="selectTree" id="selectTree"></div></div>
									</li>
									<li id="singleAnswer" class="item-blue clearfix ui-sortable-handle">
										<label class="inline">
											<i class="ace-icon fa fa-list green"></i>
											<span style="padding-left:5px;" class="lbl">试卷类型:</span>
										</label>
										<div class="inline">
											<div class="inline" id="${var.TEST_QUESTION_ITEM_TITLE}" style="margin-top: 5px;">
												<label class="pos-rel"><input style="padding-right: 3px;" type='radio' name='ids' value="1" class="ace" onclick="showPaper('1');"/>
												<span style="margin-right: 10px;" class="lbl">&nbsp;固定试卷</span></label>
											</div>
											<div class="inline" id="${var.TEST_QUESTION_ITEM_TITLE}" style="margin-top: 5px;">
												<label class="pos-rel"><input type='radio' name='ids' value="2" class="ace" onclick="showPaper('2');" />
												<span class="lbl">&nbsp;随机试卷</span></label>
											</div>
										</div>
									</li>
								<li id="fixedPaper" class="item-default clearfix ui-sortable-handle" hidden="hidden">
									<label class="inline">
										<span><a onclick="addPaper();" class="btn btn-minier bigger btn-primary"><i class="ace-icon fa fa-plus-circle"></i>添加试题</a></span>
									</label>
								</li>
								<li id="paperRule" class="item-default clearfix ui-sortable-handle" hidden="hidden">
									<label class="inline">
										<span style="padding-left:5px;" class="lbl"><i style="margin-right: 5px;" class="ace-icon glyphicon glyphicon-tags orange"></i>组卷规则:</span>
										<small><span id="countQuestion" style="padding-left:5px;" class="lbl red">*请选择试卷分类查看试卷提数</span></small>
									</label>
									<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="pd">
												<div id="1">
													<select id="TEST_QUESTION_TYPE" name="TEST_QUESTION_TYPE" class="inline" style="width:15%; margin-left: 5px;">
														<option value="1">单选题</option>
														<option value="2">多选题</option>
														<option value="3">简答题</option>
													</select>
													<select id="TEST_QUESTION_DIFFICULTY" name="TEST_QUESTION_DIFFICULTY" class="inline" style="width:15%; margin-left: 5px;">
														<option value="1">简单</option>
														<option value="2">正常</option>
														<option value="3">困难</option>
													</select>
													<label class="inline">
														<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon glyphicon glyphicon-tag blue"></i>题目数量:</span>
													</label>
														<input type="number" value="${pd.TEST_QUESTION_NUM}" name="TEST_QUESTION_NUM" id="TEST_QUESTION_NUM" placeholder="请输入题目数量" class="inline" style="width: 15%;">
													<label class="inline">
														<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon glyphicon glyphicon-tag blue"></i>单题分数:</span>
													</label>
														<input type="number" value="${pd.TEST_QUESTION_SCORE}" name="TEST_QUESTION_SCORE" id="TEST_QUESTION_SCORE" placeholder="请输入单题分数" class="inline" style="width: 15%;">
												</div>
										</c:forEach>
									</c:when>
									<c:otherwise>
											<div id="1">
												<select id="TEST_QUESTION_TYPE" name="TEST_QUESTION_TYPE" class="inline" style="width:15%; margin-left: 5px;">
													<option value="1">单选题</option>
													<option value="2">多选题</option>
													<option value="3">简答题</option>
												</select>
												<select id="TEST_QUESTION_DIFFICULTY" name="TEST_QUESTION_DIFFICULTY" class="inline" style="width:15%; margin-left: 5px;">
													<option value="1">简单</option>
													<option value="2">正常</option>
													<option value="3">困难</option>
												</select>
												<label class="inline">
													<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon glyphicon glyphicon-tag blue"></i>题目数量:</span>
												</label>
													<input type="number" value="" name="TEST_QUESTION_NUM" id="TEST_QUESTION_NUM" placeholder="请输入题目数量" class="inline" style="width: 15%;">
												<label class="inline">
													<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon glyphicon glyphicon-tag blue"></i>单题分数:</span>
												</label>
													<input type="number" value="" name="TEST_QUESTION_SCORE" id="TEST_QUESTION_SCORE" placeholder="请输入单题分数" class="inline" style="width: 15%;">
											</div>
											<div id="2">
												<select id="TEST_QUESTION_TYPE" name="TEST_QUESTION_TYPE" class="inline" style="width:15%; margin-left: 5px;">
													<option value="1">单选题</option>
													<option value="2" selected="selected">多选题</option>
													<option value="3">简答题</option>
												</select>
												<select id="TEST_QUESTION_DIFFICULTY" name="TEST_QUESTION_DIFFICULTY" class="inline" style="width:15%; margin-left: 5px;">
													<option value="1">简单</option>
													<option value="2">正常</option>
													<option value="3">困难</option>
												</select>
												<label class="inline">
													<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon glyphicon glyphicon-tag blue"></i>题目数量:</span>
												</label>
													<input type="number" value="" name="TEST_QUESTION_NUM" id="TEST_QUESTION_NUM" placeholder="请输入题目数量" class="inline" style="width: 15%;">
												<label class="inline">
													<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon glyphicon glyphicon-tag blue"></i>单题分数:</span>
												</label>
													<input type="number" value="" name="TEST_QUESTION_SCORE" id="TEST_QUESTION_SCORE" placeholder="请输入单题分数" class="inline" style="width: 15%;">
											</div>
											<div id="3">
												<select id="TEST_QUESTION_TYPE" name="TEST_QUESTION_TYPE" class="inline" style="width:15%; margin-left: 5px;">
													<option value="1">单选题</option>
													<option value="2">多选题</option>
													<option value="3" selected="selected">简答题</option>
												</select>
												<select id="TEST_QUESTION_DIFFICULTY" name="TEST_QUESTION_DIFFICULTY" class="inline" style="width:15%; margin-left: 5px;">
													<option value="1">简单</option>
													<option value="2">正常</option>
													<option value="3">困难</option>
												</select>
												<label class="inline">
													<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon glyphicon glyphicon-tag blue"></i>题目数量:</span>
												</label>
													<input type="number" value="${pd.TEST_QUESTION_SCORE}" name="TEST_QUESTION_SCORE" id="TEST_QUESTION_SCORE" placeholder="请输入题目数量" class="inline" style="width: 15%;">
												<label class="inline">
													<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon glyphicon glyphicon-tag blue"></i>单题分数:</span>
												</label>
													<input type="number" value="${pd.TEST_QUESTION_SCORE}" name="TEST_QUESTION_SCORE" id="TEST_QUESTION_SCORE" placeholder="请输入单题分数" class="inline" style="width: 15%;">
											</div>
										</c:otherwise>
									</c:choose>
								</li>
								<li id="radomPaper" class="item-default clearfix ui-sortable-handle" hidden="hidden">
									<label class="inline">
										<span><a onclick="appendRule();" class="btn btn-minier bigger btn-primary"><i class="ace-icon fa fa-plus-circle"></i>添加规则</a></span>
										<span><a onclick="autoPaper();" class="btn btn-minier bigger btn-purple"><i class="ace-icon fa fa-plus-circle"></i>生成试题</a></span>
										<span><a class="btn btn-minier bigger btn-primary btn-danger btn-danger" onclick="delRule();"><i class="ace-icon fa fa-trash-o bigger-130"></i>删除选项</a></span>
									</label>
								</li>
						<li id="liBefore" class="item-pink clearfix ui-sortable-handle">
							<label class="inline"><span style="padding-left:5px;" class="lbl"><i style="margin-right: 5px;" class="menu-icon fa fa-fire blue"></i>合格分数:</span></label>
							<input type="number" value="${pd.TEST_QUESTION_SCORE}" name="TEST_QUESTION_SCORE" id="TEST_QUESTION_SCORE" placeholder="请输入题目分数" class="inline" style="width: 15%;">
							<span id="score" style="padding-left:5px;" class="lbl">分,总分<b class="red"><font style="vertical-align: inherit;">0</font></b></span>
						</li>
						<li id="liBefore" class="item-grny clearfix ui-sortable-handle">
							<label class="inline"><span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="menu-icon fa fa-tachometer orange"></i>答题时间:</span></label>
							<input type="number" value="${pd.TEST_QUESTION_SCORE}" name="TEST_QUESTION_SCORE" id="TEST_QUESTION_SCORE" placeholder="请输入答题时间" class="inline" style="width: 15%;">
							<span style="padding-left:5px;" class="lbl">分</span>
						</li>
						<li id="liBefore" class="item-blue clearfix ui-sortable-handle">
							<div class="inline">
								<a class="btn btn-mini btn-primary" onclick="save();">保存试卷</a>
								<a class="btn btn-mini btn-danger" onclick="goPaper();">返回列表</a>
							</div>
						</li>
						<li id="showQuestion" class="item-default clearfix ui-sortable-handle" hidden="hidden">
								<div class="widget-box transparent">
									<div class="widget-header widget-header-flat">
										<h4 class="widget-title lighter"><i class="ace-icon fa fa-star orange"></i>
										<font style="vertical-align: inherit;"><font style="vertical-align: inherit;">查看列表</font></font></h4>
										<div class="widget-toolbar">
											<a href="#" data-action="collapse"><i class="ace-icon fa fa-chevron-up"></i></a>
										</div>
									</div>
									<div class="widget-body" style="display: block;">
										<div class="widget-main no-padding">
											<table id="table" class="table table-bordered table-striped">
												<thead class="thin-border-bottom">
													<tr>
														<th class="center">
															<i class="ace-icon fa fa-caret-right blue"></i><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">题目名称
														</font></font></th>
														<th class="hidden-480 center">
															<i class="ace-icon fa fa-caret-right blue"></i><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">题目分类
														</font></font></th>
														<th class="hidden-480 center">
															<i class="ace-icon fa fa-caret-right blue"></i><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">题目类型
														</font></font></th>
														<th class="hidden-480 center">
															<i class="ace-icon fa fa-caret-right blue"></i><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">试题难度
														</font></font></th>
														<th class="center">
															<i class="ace-icon fa fa-caret-right blue"></i><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">试题分数
														</font></font></th>
														<th class="hidden-480 center">
															<i class="ace-icon fa fa-caret-right blue"></i><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">正确答案
														</font></font></th>
														<th class="hidden-480 center">
															<i class="ace-icon fa fa-caret-right blue"></i><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">操作
														</font></font></th>
													</tr>
												</thead>
											<tbody id="addQuestion">
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
				</div>
				<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
</div>
<!-- /.main-container -->

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace -->
	<script src="static/ace/js/ace.js"></script>
		<script type="text/javascript">
		/*初始化参数*/
		$(top.hangge());
		function showPaper(data){
			if(data == 1){
				$("#fixedPaper").show();
				//$("#showQuestion").show();
				$("#radomPaper").hide();
				$("#paperRule").hide();
			}
			if(data == 2){
				$("#fixedPaper").hide();
				//$("#showQuestion").hide();
				$("#radomPaper").show();
				$("#paperRule").show();
			}
		}
		
		/*添加规则*/
		function appendRule(){
			
			var html = '<div style="margin-top:6px;"><select id="TEST_QUESTION_TYPE" name="TEST_QUESTION_TYPE" class="inline" style="width:15%; margin-left: 5px;">'
					 
					 + '<option value="1">单选题</option><option value="2">多选题</option><option value="3">简答题</option></select>'
					 
					 + '<select id="TEST_PAPER_DIFFICULTY" name="TEST_PAPER_DIFFICULTY" class="inline" style="width:15%; margin-left: 9px;">'
					 
					 + '<option value="1">简单</option><option value="2">正常</option><option value="3">困难</option></select>'
					 
					 + '<label class="inline"><span style="margin-left:9px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon glyphicon glyphicon-tag blue"></i>题目数量:</span>'
					 
					 + '</label><input type="number" value="${pd.TEST_QUESTION_SCORE}" name="TEST_QUESTION_SCORE" id="TEST_QUESTION_SCORE" placeholder="请输入题目数量" class="inline" style="width: 15%; margin-left:3px;">'
					 
					 + '<label class="inline"><span style="margin-left:9px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon glyphicon glyphicon-tag blue"></i>单题分数:</span></label>'
					 
					 + '<input type="number" value="${pd.TEST_QUESTION_SCORE}" name="TEST_QUESTION_SCORE" id="TEST_QUESTION_SCORE" placeholder="请输入单题分数" class="inline" style="width: 15%; margin-left:3px;"></div>';
			$("#paperRule").append(html);
		}
		
		/*生成随机试题*/
		function autoPaper(){
			top.jzts();
			var arr = new Array();
			for(var i=0;i < document.getElementsByName('TEST_QUESTION_TYPE').length;i++){
				if(document.getElementsByName('TEST_QUESTION_SCORE')[i].value != ""){
					arr.push({"TEST_QUESTION_TYPE":document.getElementsByName('TEST_QUESTION_TYPE')[i].value,
							  "TEST_QUESTION_DIFFICULTY":document.getElementsByName('TEST_QUESTION_DIFFICULTY')[i].value,
							  "TEST_QUESTION_NUM":document.getElementsByName('TEST_QUESTION_NUM')[i].value,
							  "TEST_QUESTION_SCORE":document.getElementsByName('TEST_QUESTION_SCORE')[i].value,
							  "COURSE_TYPE_ID":$("#COURSE_TYPE_ID").val()});
				}
			}
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>testpaper/radomPaper.do',
		    	data: {"listData":JSON.stringify(arr)},
				dataType:'json',
				cache: false,
				success: function(data){
					$(top.hangge());
					if(data.code == 0){
						bootbox.dialog({
		                    message: "<span class='bigger-110 red'>"+data.message+"</span>",
		                  });  
					}
					if(data.code == 1){
						setHtml(eval(data.message));
					}
				}
			});
		}
		
		/*删除规则*/
		function delRule(){
			$("#paperRule div").last().remove();
		}
		
		/*删除所选试题*/
		function del(id){
			$("#"+id).remove();
		}
		
		/*新增试题*/
		function addPaper(){
			 var id = $("#COURSE_TYPE_ID").val();
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>testpaper/goAddQuestion.do?COURSE_TYPE_ID='+id;
			 diag.Width = 800;
			 diag.Height = 500;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 listByQuestionId( diag.innerFrame.contentWindow.document.getElementById('questionId').value);
				 }
				diag.close();
			 };
			 diag.show();
		}
		
		/*查看列表*/
		function listByQuestionId(ids){
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>testpaper/listByQuestionId.do',
		    	data: {"ids":ids},
				dataType:'json',
				cache: false,
				success: function(data){
					$(top.hangge());
					setHtml(eval(data.message));
					console.log(eval(data.message));
				}
			});
		}
		
		//动态回显选择数据
		function setHtml(data){
			var html = '';
			for(var i = 0;i<data.length;i++){
				html += '<tr id="'+ data[i].TEST_QUESTION_ID +'">'
					
					  + '<input hidden="hidden" name="TEST_QUESTION_ID" id="TEST_QUESTION_ID" value="'+ data[i].TEST_QUESTION_ID +'"/>'
				
				 	  + '<td class="center">' + data[i].TEST_QUESTION_TITLE +'</td>'
				 	  
				 	  + '<td class="center">' + data[i].COURSE_TYPE_NAME +'</td>'
				 	  
				 	  + '<td class="center">' + data[i].TEST_QUESTION_TYPE +'</td>'
				 	  
				 	  + '<td class="center">' + data[i].TEST_QUESTION_DIFFICULTY +'</td>'
				 	  
				 	  + '<td name="scores" class="center">' + data[i].TEST_QUESTION_SCORE +'</td>'
				 	  
				 	  + '<td class="center">' + data[i].TEST_QUESTION_ANSWER +'</td>'
				 	  
				 	  + '<td class="center"><a onclick="del(\''+data[i].TEST_QUESTION_ID+'\');" data-action="delete" class="btn btn-xs btn-danger">'
				 	  
				 	  + '<i class="ace-icon fa fa-trash-o bigger-120"></i></a></td></tr>';
			}
			$("#addQuestion").append(html);
			$("#showQuestion").show();
			totalScore();
		}
		
		/*动态计算试题数量*/
		function countQuestion(){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>testpaper/countQuestion.do',
		    	data: {"COURSE_TYPE_ID":$("#COURSE_TYPE_ID").val()},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#countQuestion").text(data.message);
				}
			});
		}
		
		/*计算总分*/
		function totalScore(){
			var scores = 0;	
			for(var i=0;i < $("[name='scores']").length;i++){
				  	if(scores=='') scores += $("[name='scores']")[i].text();
				  	else scores +=$("[name='scores']").text();
				}
				console.log(scores);
		} 
		
		//保存
		function save(){
			if($("#TEST_PAPER_TITLE").val()==""){
				$("#TEST_PAPER_TITLE").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_PAPER_TITLE").focus();
			return false;
			}
			if($("#COURSE_TYPE_ID").val()==""){
				$("#COURSE_TYPE_ID").tips({
					side:3,
		            msg:'请输入备注3',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_TYPE_ID").focus();
			return false;
			}
			if($("#TEST_PAPER_TYPE").val()==""){
				$("#TEST_PAPER_TYPE").tips({
					side:3,
		            msg:'请输入备注4',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_PAPER_TYPE").focus();
			return false;
			}
			if($("#TEST_PAPER_DIFFICULTY").val()==""){
				$("#TEST_PAPER_DIFFICULTY").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_PAPER_DIFFICULTY").focus();
			return false;
			}
			if($("#TEST_QUESTION_SOURCE").val()==""){
				$("#TEST_QUESTION_SOURCE").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_QUESTION_SOURCE").focus();
			return false;
			}
			if($("#TEST_QUESTION_NUM").val()==""){
				$("#TEST_QUESTION_NUM").tips({
					side:3,
		            msg:'请输入备注7',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_QUESTION_NUM").focus();
			return false;
			}
			if($("#TEST_PAPER_SCORE").val()==""){
				$("#TEST_PAPER_SCORE").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_PAPER_SCORE").focus();
			return false;
			}
			if($("#ANSWER_TIME").val()==""){
				$("#ANSWER_TIME").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ANSWER_TIME").focus();
			return false;
			}
			if($("#TEST_CHANCE").val()==""){
				$("#TEST_CHANCE").tips({
					side:3,
		            msg:'请输入备注10',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_CHANCE").focus();
			return false;
			}
			if($("#QUALIFIED_SCORE").val()==""){
				$("#QUALIFIED_SCORE").tips({
					side:3,
		            msg:'请输入备注11',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#QUALIFIED_SCORE").focus();
			return false;
			}
			if($("#STATE").val()==""){
				$("#STATE").tips({
					side:3,
		            msg:'请输入备注12',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATE").focus();
			return false;
			}
			if($("#CREATE_USER").val()==""){
				$("#CREATE_USER").tips({
					side:3,
		            msg:'请输入备注13',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATE_USER").focus();
			return false;
			}
			if($("#CREATE_DATE").val()==""){
				$("#CREATE_DATE").tips({
					side:3,
		            msg:'请输入备注14',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATE_DATE").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		/*树形下拉框*/
		var defaultNodes = {"treeNodes":eval('${zTreeNodes}')};
		function initComplete(){
			//绑定change事件
			$("#selectTree").bind("change",function(){
				//$("#COURSE_TYPE_ID").val("");
				if($(this).attr("relValue")){
					$("#COURSE_TYPE_ID").val($(this).attr("relValue"));
					/*动态计算每种类型题数*/
					countQuestion();
			    }
			});
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			$("#selectTree2_input").val('${pd.COURSE_TYPE_NAME}');
		}
		
		/*返回列表*/
		function goPaper(){
			window.location.href='<%=basePath%>testpaper/list.do';
		}
		</script>
</body>
</html>