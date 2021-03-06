﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/jquery.cookie.js"></script>

<!-- 树形下拉框start -->
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

	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
				<div class="col-xs-12">							
					<form action="trainplan/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="TRAINPLAN_ID" id="TRAINPLAN_ID" value="${pd.TRAIN_PLAN_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
							<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td width="25%"><span>任务名称：</span></td>
									<td width="40%"><input type="text" name="TRAIN_PLAN_NAME" id="TRAIN_PLAN_NAME" value="${pd.TRAIN_PLAN_NAME}" maxlength="32" placeholder="这里输入任务名称" title="任务名称" /></td>
								<td  width="35%" rowspan="3"><img src="static/images/rw.jpg"></td>
								</tr>									
								<tr>
									<td width="25%"><span>课程分类：</span></td>
									<td width="40%">
										<div style="margin:10px 0px;">
												<input type="hidden" name="COURSE_TYPE_ID" id="COURSE_TYPE_ID"   />
												<div class="selectTree" id="selectTree" style="float:none;display:block;"></div>												
											</div>				
									</td>																
								</tr>
										<tr>
									<td width="25%"><span>课程名称：</span></td>
									<td width="40%">
										<select class="form-control" name="COURSE_CODE" id="COURSE_CODE"  >
												<option value=""></option>
												<c:forEach items="${courseList}" var="course">
												<option value="${course.COURSE_ID}">${course.COURSE_NAME}</option>
												</c:forEach>
										</select>					
									</td>																
								</tr>
								<tr><td><label> <i class="ace-icon  bigger-110"></i>任务时间范围：</label> </td>
								<td style="padding-left:10px;"><input class="span10 date-picker" name="START_DATE" id="START_DATE"  value="${pd.START_DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:95%;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:10px;"><input class="span10 date-picker" name="END_DATE" id="END_DATE"  value="${pd.END_DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:95%;" placeholder="结束日期" title="结束日期"/></td>
								</tr>
								<tr>
									<td>
									<a id="btnChoice"  onclick="choiceStudent()">
									<i class="ace-icon fa  glyphicon-plus bigger-110"></i>选择培训人群：</a>
									</td>
									<td>
									<a id="btnShow"  onclick="showTable()">
									<i class="ace-icon fa   bigger-110"></i>显示/隐藏人员列表：</a>
									</td>
									<td>
									<a id="btnClear"  onclick="clearTable()">
									<i class="ace-icon fa   bigger-110"></i>清空人员列表：</a>
									</td>
								</tr>
								<tr>	
								<td  colspan="3">
								<textarea  id="uesrTextarea" name="uesrTextarea"  style="width:98%;height:30%;" readonly>${pd.TRAIN_PERSONSNAME}</textarea>
								<input type="hidden" style="width:98%; name="TRAIN_PLAN_PERSONS" id="TRAIN_PLAN_PERSONS" value="${pd.TRAIN_PERSONSCODE}" />
								</td>
								</tr>
								<tr>
								<td colspan="3">
									<span id="btnChoice">培训人员列表：</span>
									</td></tr>
								<tr>
								<td colspan="3">
								<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">学员编号</th>
									<th class="center">学员名称</th>
									<th class="center">所属单位</th>
									<th class="center">所属部门</th>
								</tr>
							</thead>
													
							<tbody id="tobodyUser">
							<!-- 开始循环 -->		
							<c:choose>
									<c:when test="${not empty studentList}">
										<c:forEach items="${studentList}" var="var" varStatus="vs">	
											<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.STUDENT_CODE}</td>
											<td class='center'>${var.STUDENT_NAME}</td>
											<td class='center'>${var.UNIT_NAME}</td>
											<td class='center'>${var.DEPART_NAME}</td>		
											</tr>
										</c:forEach>
									</c:when>									
								</c:choose>								
							</tbody>
						</table>	
						</td>							
						</tr>
							<tr>
									<td colspan="3"><span>任务描述：</span></td>
								</tr>
								<tr>
									<td colspan="3"><textarea name="a" id="TRAIN_PLAN_MEMO" style="width:98%;height:80px;">${pd.TRAIN_PLAN_MEMO}</textarea></td>
								</tr>
								<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</table>
						</div>
					</form>
					

						</div>
					</div>
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
		function save1(){
			top.jzts();
			$("#Form").submit();
		}
		$("#COURSE_CODE").val('${pd.COURSE_ID}');
		//保存
		function save(){
			var trainID=$("#TRAINPLAN_ID").val();//任务ID
			var planName=$("#TRAIN_PLAN_NAME").val();//任务名称
			var couseTypeId=$("#COURSE_TYPE_ID").val();//课程分类
			var couseCode=$("#COURSE_CODE").val();//课程名称编码
			var startDate=$("#START_DATE").val();//开始时间
			var endDate=$("#END_DATE").val();//结束时间
			var planPersons=$("#TRAIN_PLAN_PERSONS").val();//培训人员
			var planMemo=$("#TRAIN_PLAN_MEMO").val();//	任务描述
			if(!planName){
				$("#TRAIN_PLAN_NAME").tips({
					side:3,
		            msg:'请输入任务名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TRAIN_PLAN_NAME").focus()
				return
			}
			if(!couseTypeId){
				$("#selectTree2_input").tips({
					side:3,
		            msg:'请选择课程分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#selectTree2_input").focus()
				return
			}
			if(couseCode=='0'){
				$("#COURSE_CODE").tips({
					side:3,
		            msg:'请选择课程',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_CODE").focus()
				return
			}
			if(!startDate){
				$("#START_DATE").tips({
					side:3,
		            msg:'请选择日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#START_DATE").focus()
				return
			}
			if(!endDate){
				$("#END_DATE").tips({
					side:3,
		            msg:'请选择日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#END_DATE").focus()
				return
			}
			if(!planPersons){
				$("#uesrTextarea").tips({
					side:3,
		            msg:'请选择培训人员',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#uesrTextarea").focus()
				return
			}
			top.jzts();		
			$.ajax({
				type: "POST",
				url: '<%=basePath%>trainplan/save.do',
				data:{TRAIN_PLAN_ID:trainID,TRAIN_PLAN_NAME:planName,COURSE_TYPE_ID:couseTypeId,COURSE_CODE:couseCode,START_DATE:startDate,END_DATE:endDate,TRAIN_PLAN_PERSONS:planPersons,TRAIN_PLAN_MEMO:planMemo},
		    	dataType:'json',
				cache: false,
				success: function(response){
					if(response.code==0){
						$(top.hangge());//关闭加载状态
						bootbox.dialog({
							message: "<span class='bigger-110'>保存成功</span>",
						});		
						init();
					}else{
						$(top.hangge());//关闭加载状态
						bootbox.dialog({
							message: "<span class='bigger-110'>保存失败</span>",
						});		
					}
					top.Dialog.close();
				},
		    	error: function(response) {
		    		var msgObj=JSON.parse(response.responseText);
		    		$(top.hangge());//关闭加载状态
		    		bootbox.dialog({
						message: "<span class='bigger-110'>保存失败"+msgObj.message+"</span>",
					});		
		    		top.Dialog.close();
		    	}
			});			
		}
		function init(){
			$("#TRAIN_PLAN_NAME").val('');//任务名称
			$("#COURSE_CODE").val('');//课程名称编码
			$("#START_DATE").val('');//开始时间
			$("#END_DATE").val('');//结束时间
			$("#TRAIN_PLAN_PERSONS").val('');//申请人岗位
			$("#TRAIN_PLAN_MEMO").val('');//联系方式
			$("#uesrTextarea").val('');
			$("#tobodyUser").html('');
			initComplete();
		}
		var gStr = ''//人员
		$(function() {
			//初始化 清空cookie  如果是编辑则获取以选中的id存到cookie中
			$.removeCookie('userIdList');
			let TRAIN_PLAN_PERSONS_T = '${pd.TRAIN_PLAN_PERSONS}'
			gStr = TRAIN_PLAN_PERSONS_T
			if(TRAIN_PLAN_PERSONS_T){
				$.cookie('userIdList',TRAIN_PLAN_PERSONS_T)
			}
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
			
			
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		
		$("#COURSETYPE_CODE").change(function(){
		    var opt=$("#COURSETYPE_CODE").val();
		
		});
		function goBack(){
			 window.location.href = '<%=basePath%>trainplan/list.do';
		}
	
		function initComplete(){
			//下拉树
			var defaultNodes = {"treeNodes":${zTreeNodes}};
			//绑定change事件
			$("#selectTree").bind("change",function(){

				if(!$(this).attr("relValue")){
			    }else{
					$("#COURSE_TYPE_ID").val($(this).attr("relValue"));	
			    }	
// 				$("#selectTree").data("data",defaultNodes);  
// 				$("#selectTree").render();
				 //清空select框中数据
				$('#COURSE_CODE').empty();
				$.ajax({
					   type: "POST",
					   url: '<%=basePath%>coursebase/getCourse.do',
					   data: {'COURSE_TYPE':$("#COURSE_TYPE_ID").val()},
					   dataType:'json',
					   //beforeSend: validateData,
					   cache: false,
					      success: function (data) {
					                $('#COURSE_CODE').append("<option value='0'>--请选择课程--</option>");
					                //遍历成功返回的数据
					                $.each(data, function (index,item) {
					                    var courseName = data[index].COURSE_NAME;
					                    var courseId = data[index].COURSE_ID;
					                    //构造动态option
					                    $('#COURSE_CODE').append("<option value='"+courseId+"'>"+courseName+"</option>")
					                });
					            },
					            error: function () {

					            }
					  });
			});

			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			
			$("#COURSE_TYPE_ID").val('${pd.COURSE_TYPE_ID}');
			$("#selectTree2_input").val('${coursetype}');		

		}	
		
		//选择培训人群
		function choiceStudent(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="选择培训人员";
			 diag.URL = '<%=basePath%>trainplan/listStudent.do';
			 diag.Width = 768;
			 diag.Height = 400;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 /* var str = '';
					for(var i=0;i < diag.innerFrame.contentWindow.document.getElementsByName('ids').length;i++){
					  if(diag.innerFrame.contentWindow.document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += diag.innerFrame.contentWindow.document.getElementsByName('ids')[i].value;
					  	else str += ',' + diag.innerFrame.contentWindow.document.getElementsByName('ids')[i].value;				 
					  }
					} */
					
					let is_clickConfirm = diag.innerFrame.contentWindow.document.getElementById("clickConfirm").value
	           		if(is_clickConfirm == '0'){
	           			diag.close();
	           			$.cookie('userIdList',gStr)
	           			return
	           		}
					gStr = $.cookie('userIdList')
					if(!gStr){
						diag.close();
						return
					}
					$.ajax({
						   type: "POST",
						   url: '<%=basePath%>trainplan/getChoiceStudent.do',
						   data: {'sturentStr':gStr},
						   dataType:'json',
						   //beforeSend: validateData,
						   cache: false,
						      success: function (data) {
						    	  
						    	  /* if($("#uesrTextarea").val()==''){ */
						    		  var userCodes='';
							    	  var userNames='';
							    	  $.each(data, function(i, item){
							    		  if(userCodes=='') {
							    			  userCodes += item.STUDENT_ID;
							    			  userNames += item.STUDENT_NAME;
							    		  }else {
							    			  userCodes += ',' + item.STUDENT_ID;
							    			  userNames += ',' + item.STUDENT_NAME;
							    		  }
										    var html = '';
										        html += setTable(item,i+1);
											$("#tobodyUser").append(html);
											$("#tobodyUser").hide();
									 	});
							    	  $("#uesrTextarea").val(userNames);
							    	  $("#TRAIN_PLAN_PERSONS").val(userCodes); 
						    	  /* }else{
						    		  var userCodes='';
							    	  var userNames='';
							    	  var arry = $("#TRAIN_PLAN_PERSONS").val().split(",");
						    		  var num=0;
							    	  $.each(data, function(i, item){	
								    	  if(arry.indexOf(item.STUDENT_ID.toString())==-1){
								      			num=num+1;
								    		  userCodes += ',' + item.STUDENT_ID;
							    			  userNames += ',' + item.STUDENT_NAME;							    		  	
										    	var html = '';
										        html += setTable(item,arry.length+num);
											$("#tobodyUser").append(html);
								    	  } 
							    			 
									 	});	
							       	  $("#uesrTextarea").val($("#uesrTextarea").val()+userNames);
							    	  $("#TRAIN_PLAN_PERSONS").val($("#TRAIN_PLAN_PERSONS").val()+userCodes); 
						    	  } */
						    	 
						         },
						            error: function () {

						            }
						  });
				diag.close();
			 };
			 diag.show();
		}
		function setTable(item,i){
			var UNIT_NAME = item.UNIT_NAME?item.UNIT_NAME:''
			var DEPART_NAME = item.DEPART_NAME?item.DEPART_NAME:''
			div='<tr><td class="center" style="width: 30px;">'
				+i 
				+"</td><td class='center'>"
				+item.STUDENT_CODE
				+"</td><td class='center'>"
				+item.STUDENT_NAME
				+"</td><td class='center'>"
				+UNIT_NAME
				+"</td><td class='center'>"
				+DEPART_NAME
				+'</td></tr>';				
				return div;	
		}
		function showTable(){
			if($("#tobodyUser").is(':hidden')){
				$("#tobodyUser").show();
			}else{
				$("#tobodyUser").hide();
			}
			
		}
		function clearTable(){
			$("#tobodyUser").html('');
			$("#uesrTextarea").val('');
			$.removeCookie('userIdList');
			gStr = ''
		}
	</script>


</body>
</html>