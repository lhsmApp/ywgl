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
<link rel="stylesheet" type="text/css"
	href="plugins/selectZtree/import_fh.css" />
<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet"
	href="plugins/selectZtree/ztree/ztree.css"></link>
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
									<span class="green middle bolder">题目详细信息: &nbsp;</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="row">
					<div class="col-xs-12">
					<form action="testquestion/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name=TEST_QUESTION_ID id="TEST_QUESTION_ID" value="${pd.TEST_QUESTION_ID}"/>
						<input type="hidden" id="questionType" value="${pd.TEST_QUESTION_TYPE}"/>
						<input type="hidden" id="questionDifficulty" value="${pd.TEST_QUESTION_DIFFICULTY}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<div id="task-tab" class="tab-pane active">
							<h4 class="smaller lighter gray">题目:
							<input type="text" id="TEST_QUESTION_TITLE" name="TEST_QUESTION_TITLE" placeholder="请输入题目内容,请不要超过200个字符!" value="${pd.TEST_QUESTION_TITLE}" class="inline" style="width: 90%;">
							</h4>
							<!-- <input type="hidden" name="CHAPTER_ID" id="CHAPTER_ID" value=""/> -->
							<ul id="tasks_1" class="item-list ui-sortable">
									<li class="item-orange clearfix ui-sortable-handle">
										<label class="inline">
											<span style="padding-left:5px;" class="lbl"><i style="padding-right: 3px;" class="menu-icon fa fa-cog pick"></i>题目类型:</span>
										</label>
										<select onchange="change()" id="TEST_QUESTION_TYPE" name="TEST_QUESTION_TYPE" class="inline" style="width:15%;">
											<option value="1">单选题</option>
											<option value="2">多选题</option>
											<option value="3">简答题</option>
										</select>
									</li>
									<li id="singleAnswer" class="item-blue clearfix ui-sortable-handle">
										<label class="inline">
											<i class="ace-icon fa fa-list green"></i>
											<span style="padding-left:5px;" class="lbl">答案:</span>
										</label>
										<c:choose>
											<c:when test="${not empty varList}">
												<div id="beforeAdd">
													<c:forEach items="${varList}" var="var">
														<div id="${var.TEST_QUESTION_ITEM_TITLE}" style="margin-top: 5px;">
															<input type="hidden" name="charCode" id="charCode" value="${var.TEST_QUESTION_ITEM_TITLE}" >
															<label class="pos-rel"><input type='checkbox' name='ids' value="${var.TEST_QUESTION_ITEM_TITLE}"  class="ace" />
															<span style="margin-right: 3px;" class="lbl"></span></label>选项${var.TEST_QUESTION_ITEM_TITLE}:
															<input type="text" name="TEST_QUESTION_ITEM_CONTENT" id="TEST_QUESTION_ITEM_CONTENT" value="${var.TEST_QUESTION_ITEM_CONTENT}" maxlength="32" placeholder="请输入选项内容,您最多可以输入200个字符!" style="width:90%;"/>
															
														</div>
													</c:forEach>
												</div>
											</c:when>
											<c:otherwise>
												<div id="beforeAdd">
													<div id="A" style="margin-top: 5px;">
														<input type="hidden" name="charCode" id="charCode" value="A">
														<label class="pos-rel"><input type='checkbox' id="ids" name='ids' value="A" class="ace" />
														<span style="margin-right: 3px;" class="lbl"></span></label>选项A:
														<input type="text" name="TEST_QUESTION_ITEM_CONTENT" id="TEST_QUESTION_ITEM_CONTENT" value="" maxlength="200" placeholder="请输入选项内容,您最多可以输入200个字符!" style="width:90%;"/>
													</div>
													<div id="B" style="margin-top: 5px;">
														<input type="hidden" name="charCode" id="charCode" value="B">
														<label class="pos-rel"><input type='checkbox' id="ids" name='ids' value="B" class="ace" />
														<span style="margin-right: 3px;" class="lbl"></span></label>选项B:
														<input type="text" name="TEST_QUESTION_ITEM_CONTENT" id="TEST_QUESTION_ITEM_CONTENT" value="" maxlength="200" placeholder="请输入选项内容,您最多可以输入200个字符!"style="width:90%;"/>
													</div>
													<div id="C" style="margin-top: 5px;">
														<input type="hidden" name="charCode" id="charCode" value="C">
														<label class="pos-rel"><input type='checkbox' id="ids" name='ids' value="C" class="ace" />
														<span style="margin-right: 3px;" class="lbl"></span></label>选项C:
														<input type="text" name="TEST_QUESTION_ITEM_CONTENT" id="TEST_QUESTION_ITEM_CONTENT" value="" maxlength="200" placeholder="请输入选项内容,您最多可以输入200个字符!" style="width:90%;"/>
													</div>
													<div id="D" style="margin-top: 5px;">
														<input type="hidden" name="charCode" id="charCode" value="D">
														<label class="pos-rel"><input type='checkbox' id="ids" name='ids' value="D" class="ace" />
														<span style="margin-right: 3px;" class="lbl"></span></label>选项D:
														<input type="text" name="TEST_QUESTION_ITEM_CONTENT" id="TEST_QUESTION_ITEM_CONTENT" value="" maxlength="200" placeholder="请输入选项内容,您最多可以输入200个字符!"style="width:90%;"/>
													</div>
												</div>
											</c:otherwise>
										</c:choose>
									</li>
								<li id="liBefore" class="item-default clearfix ui-sortable-handle">
									<label class="inline">
										<span><a onclick="beforeAdd();" class="btn btn-minier bigger btn-primary"><i class="ace-icon fa fa-plus-circle"></i>增加选项</a></span>
										<a class="btn btn-minier bigger btn-primary btn-danger btn-danger" onclick="del();"><i class="ace-icon fa fa-trash-o bigger-130"></i><span>删除选项</span></a>
									</label>
								</li>
								
								<li id="liBefore" class="item-green clearfix ui-sortable-handle">
									<label class="inline">
											<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="menu-icon fa fa-book green"></i>题目分类:</span>
										</label>
												<input type="hidden" name="COURSE_TYPE_ID" id="COURSE_TYPE_ID"  value="${pd.COURSE_TYPE_ID}"/>
												<div class="inline"><div class="selectTree" id="selectTree"></div></div>
								</li>
								<li id="liBefore" class="item-pink clearfix ui-sortable-handle">
									<label class="inline"><span style="padding-left:5px;" class="lbl"><i style="margin-right: 5px;" class="menu-icon fa fa-fire blue"></i>题目分数:</span></label>
									<input type="number" value="${pd.TEST_QUESTION_SCORE}" name="TEST_QUESTION_SCORE" id="TEST_QUESTION_SCORE" placeholder="请输入题目分数" class="inline" style="width: 15%;">
								</li>
								<li id="liBefore" class="item-grny clearfix ui-sortable-handle">
									<label class="inline"><span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="menu-icon fa fa-tachometer orange"></i>题目难度:</span></label>
									<select id="TEST_QUESTION_DIFFICULTY" name="TEST_QUESTION_DIFFICULTY" class="form-control inline" style="width:15%;">
										<option value="1">简单</option>
										<option value="2">正常</option>
										<option value="3">困难</option>
									</select>	
								</li>
								<li id="liBefore" class="item-orange clearfix ui-sortable-handle">
									<label class="inline"><span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="menu-icon fa fa-pencil-square-o orange"></i>答案解析:</span></label>
									<textarea id="TEST_ANSWER_NOTE" name="TEST_ANSWER_NOTE" class="autosize-transition form-control inline" style="overflow: hidden; overflow-wrap: break-word; resize: horizontal; height: 152px;">${pd.TEST_ANSWER_NOTE}</textarea>
								</li>
								<li id="liBefore" class="item-blue clearfix ui-sortable-handle">
									<div class="inline">
										<a class="btn btn-mini btn-primary" onclick="save();">保存题目</a>
										<a class="btn btn-mini btn-danger" onclick="goQuestion();">返回列表</a>
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
		<script type="text/javascript">
		$(function(){
			//edit 类型回显下拉框
			var questionType = document.getElementById("questionType").value;
			var objType = document.getElementById("TEST_QUESTION_TYPE");
			for(i=0;i<objType.length;i++){
			    if(objType[i].value==questionType){
			    	objType[i].selected = true;
			    	if(questionType==3){
			    		//如果是简答题,就让答案失效
			    		$('input[type="text"],select,textarea',$("#singleAnswer")).prop('readonly',true);
			    	}
			    }
			}
			//edit难度回显下拉框
			var questionDifficulty = document.getElementById("questionDifficulty").value;
			var objDifficulty = document.getElementById("TEST_QUESTION_DIFFICULTY");
			for(i=0;i<objDifficulty.length;i++){
			    if(objDifficulty[i].value==questionDifficulty)
			    	objDifficulty[i].selected = true;
			}
			//复选框回显
			var answer ='${pd.TEST_QUESTION_ANSWER}';
			var items = answer.split(",");
			for(var i=0;i < document.getElementsByName('ids').length;i++){
				for(var j=0;j<items.length;j++){
					if(items[j] == document.getElementsByName('ids')[i].value){
						document.getElementsByName('ids')[i].checked = true;
					}
				}
			}
		});
		
		/*树形下拉框*/
		var defaultNodes = {"treeNodes":eval('${zTreeNodes}')};
		function initComplete(){
			//绑定change事件
			$("#selectTree").bind("change",function(){
				//$("#COURSE_TYPE_ID").val("");
				if($(this).attr("relValue")){
					$("#COURSE_TYPE_ID").val($(this).attr("relValue"));
			    }
			});
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			$("#selectTree2_input").val('${pd.COURSE_TYPE_NAME}');
		}
		</script>
		
		<script type="text/javascript">
		$(top.hangge());
		function change(){
			var data = $("#TEST_QUESTION_TYPE").val();
			if(data == 3){
				$(":checked").removeAttr('checked');
				$('input[type="text"],select,textarea',$("#singleAnswer")).val("");
				$('input[type="text"],select,textarea',$("#singleAnswer")).prop('readonly',true);
			}else{
				$('input[type="text"],select,textarea',$("#singleAnswer")).prop('readonly',false);
			}
		}
			//初始化新增选项索引
			var charCode= 64 + document.getElementsByName("charCode").length;
			function beforeAdd(){
				charCode += 1;
				var index = String.fromCharCode(charCode);
				var html = '<div id="'+ index + '"style="margin-top: 5px;"><input type="hidden" name="charCode" id="charCode" value="'+ index +'">' 
					 
					 	 + '<label class="pos-rel"><input type="checkbox" id="ids" name="ids" value="'+ index +'" class="ace" /><span style="margin-right: 3px;" class="lbl">'
					 
					 	 + '</span></label>选项' + index + ': <input type="text" name="TEST_QUESTION_ITEM_CONTENT" id="TEST_QUESTION_ITEM_CONTENT" value="" maxlength="12" placeholder="请输入选项内容,您最多可以输入200个字符"style="width:90%;"/>'
					 
					     + '</div>';
				$("#beforeAdd").append(html);
		}
		
		function del(){
			var index = String.fromCharCode(charCode);
			$('#' + index).remove();
			charCode -= 1;
		}
		
		function goQuestion(){
			window.location.href='<%=basePath%>testquestion/list.do';
		}
		
		//保存
		function save(){
			if($("#TEST_QUESTION_TITLE").val()==""){
				$("#TEST_QUESTION_TITLE").tips({
					side:3,
		            msg:'请输入题目!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_QUESTION_TITLE").focus();
			return false;
			}
			if($("#TEST_QUESTION_TYPE").val()==""){
				$("#TEST_QUESTION_TYPE").tips({
					side:3,
		            msg:'请选择题目类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_QUESTION_TYPE").focus();
			return false;
			}
			if($("#TEST_QUESTION_DIFFICULTY").val()==""){
				$("#TEST_QUESTION_DIFFICULTY").tips({
					side:3,
		            msg:'请选择题目难度!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_QUESTION_DIFFICULTY").focus();
			return false;
			}
			if($("#COURSE_TYPE_ID").val()==""){
				$("#selectTree").tips({
					side:3,
		            msg:'请选择分类!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_QUESTION_DIFFICULTY").focus();
			return false;
			}
			if($("#TEST_QUESTION_SCORE").val()==""){
				$("#TEST_QUESTION_SCORE").tips({
					side:3,
		            msg:'请输入题目分数!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_QUESTION_SCORE").focus();
			return false;
			}
			if($("#TEST_ANSWER_NOTE").val()==""){
				$("#TEST_ANSWER_NOTE").tips({
					side:3,
		            msg:'请输入答案解析!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TEST_ANSWER_NOTE").focus();
			return false;
			}
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++){
			  if(document.getElementsByName('ids')[i].checked){
			  	if(str=='') str += document.getElementsByName('ids')[i].value;
			  	else str += ',' + document.getElementsByName('ids')[i].value;
			  }
			}
			var TEST_QUESTION_ITEM_CONTENT = '';
			for(var i=0;i < document.getElementsByName('TEST_QUESTION_ITEM_CONTENT').length;i++){
				  	if(document.getElementsByName('TEST_QUESTION_ITEM_CONTENT')[i].value != ""){
						if(TEST_QUESTION_ITEM_CONTENT=='') TEST_QUESTION_ITEM_CONTENT += document.getElementsByName('TEST_QUESTION_ITEM_CONTENT')[i].value;
					  	else TEST_QUESTION_ITEM_CONTENT += ',' + document.getElementsByName('TEST_QUESTION_ITEM_CONTENT')[i].value;
				  	}
				}
			var questionArray = new Array();
			questionArray.push({"TESTQUESTION_ID":$("#TESTQUESTION_ID").val(),
								"TEST_QUESTION_TITLE":$("#TEST_QUESTION_TITLE").val(),
								"TEST_QUESTION_TYPE":$("#TEST_QUESTION_TYPE").val(),
								"TEST_QUESTION_ITEM_CONTENT":TEST_QUESTION_ITEM_CONTENT,
								"ids":str,
								"COURSE_TYPE_ID":$("#COURSE_TYPE_ID").val(),
								"TEST_QUESTION_SCORE":$("#TEST_QUESTION_SCORE").val(),
								"TEST_QUESTION_DIFFICULTY":$("#TEST_QUESTION_DIFFICULTY").val(),
								"TEST_ANSWER_NOTE":$("#TEST_ANSWER_NOTE").val(),
								"TEST_QUESTION_ID":$("#TEST_QUESTION_ID").val()});
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>testquestion/'+ '${msg}' +'.do?tm='+new Date().getTime(),
		    	data: {"listData":JSON.stringify(questionArray)},
				dataType:'json',
				cache: false,
				success: function(response){
					goQuestion();
				}
		    
			});
		}
</script>
</body>
</html>