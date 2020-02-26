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
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>

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
						<input type="hidden" name="TEST_PLAN_ID" id="TEST_PLAN_ID" value="${pd.TEST_PLAN_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
							<input type="hidden" name="PAPER_ID" id="PAPER_ID" value="${pd.TEST_PAPER_ID}"/>
							<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td width="25%"><span>考试计划名称：</span></td>
									<td width="40%"><input type="text" name="TEST_PLAN_NAME" id="TEST_PLAN_NAME" value="${pd.TEST_PLAN_NAME}" maxlength="32" placeholder="这里输入考试计划名称" title="计划名称" /></td>
									<td  width="35%" rowspan="2">
									<!-- <img src="static/images/rw.jpg"> -->
									<div id="uploadPic" class="col-xs-6" style="width: 100%;">
										<input id="pic" name="pic" type="file">
									</div>
									<input hidden="hidden" id="COURSE_COVER" name="COURSE_COVER">
									</td>
								</tr>									
								<tr>
									<td width="25%"><span>选择试卷：</span></td>
									<td width="40%">
										<select class="form-control" name="TEST_PAPER_ID" id="TEST_PAPER_ID"  >
												<option value="">请选择试卷</option>
												<c:forEach items="${paperList}" var="paper">
												<option value="${paper.TEST_PAPER_ID}">${paper.TEST_PAPER_TITLE}</option>
												</c:forEach>
										</select>					
									</td>																
								</tr>
								<tr><td><label> <i class="ace-icon  bigger-110"></i>计划时间范围：</label> </td>
								<td style="padding-left:10px;"><input class="span10 date-picker" name="START_DATE" id="START_DATE"  value="${pd.START_DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:95%;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:10px;"><input class="span10 date-picker" name="END_DATE" id="END_DATE"  value="${pd.END_DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:95%;" placeholder="结束日期" title="结束日期"/></td>
								</tr>
								<tr>
									<td>
									<a id="btnChoice"  onclick="choiceStudent()">
									<i class="ace-icon fa  glyphicon-plus bigger-110"></i>选择考试人群：</a>
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
								<textarea  id="uesrTextarea" name="uesrTextarea"  style="width:98%;height:30%;" readonly>${pd.TEST_PERSONSNAME}</textarea>
								<input type="hidden" style="width:98%;" name="TEST_PLAN_PERSONS" id="TEST_PLAN_PERSONS" value="${pd.TEST_PERSONSCODE}" />
								</td>
								</tr>
								<tr>
								<td colspan="3">
									<span>培训人员列表：</span>
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
											<td class='center'>${var.UNIT_CODE}</td>
											<td class='center'>${var.DEPART_CODE}</td>		
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
									<td colspan="3"><textarea name="a" id="TEST_PLAN_MEMO" style="width:98%;height:80px;">${pd.TEST_PLAN_MEMO}</textarea></td>
								</tr>
								<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();top.Dialog.close();">保存</a>
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
	<!-- 上传文件 -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<script type="text/javascript" src="static/ace/js/jquery.form.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		function save1(){
			top.jzts();
			$("#Form").submit();
		}
		$("#tobodyUser").hide();

		$("#TEST_PAPER_ID").val('${pd.TEST_PAPER_ID}');
		
		//保存
		function save(){
			top.jzts();		
			var testID=$("#TEST_PLAN_ID").val();//任务ID
			var testName=$("#TEST_PLAN_NAME").val();//任务名称
			var paperId=$("#TEST_PAPER_ID").val();//试卷ID
			var startDate=$("#START_DATE").val();//开始时间
			var endDate=$("#END_DATE").val();//结束时间
			var testPersons=$("#TEST_PLAN_PERSONS").val();//考试人员
			var testMemo=$("#TEST_PLAN_MEMO").val();//	任务描述
			var COURSE_COVER=$("#COURSE_COVER").val();//图片路径
			$.ajax({
				type: "POST",
				url: '<%=basePath%>testplan/save.do',
				data:{TEST_PLAN_ID:testID,TEST_PLAN_NAME:testName,TEST_PAPER_ID:paperId,START_DATE:startDate,END_DATE:endDate,TEST_PLAN_PERSONS:testPersons,TEST_PLAN_MEMO:testMemo,COURSE_COVER:COURSE_COVER},
		    	dataType:'json',
		    	async: false, 
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
				},
		    	error: function(response) {
		    		var msgObj=JSON.parse(response.responseText);
		    		$(top.hangge());//关闭加载状态
		    		bootbox.dialog({
						message: "<span class='bigger-110'>保存失败"+msgObj.message+"</span>",
					});		
		    	}
			});			
		}
		function init(){
			$("#TEST_PLAN_NAME").val('');//任务名称
			$("#START_DATE").val('');//开始时间
			$("#END_DATE").val('');//结束时间
			$("#TEST_PAPER_ID").val('');//清除试卷选项		
			$("#TEST_PLAN_PERSONS").val('');//申请人岗位
			$("#TEST_PLAN_MEMO").val('');//联系方式
			$("#uesrTextarea").val('');
			$("#tobodyUser").html('');
		}
		$(function() {
			//上传区域框
			$('#uploadPic').find('input[type=file]').ace_file_input({
				style:'well',
				btn_choose:'点击选择图片上传/修改',
				btn_change:null,
				no_icon:'ace-icon fa fa-picture-o',
				thumbnail:'large',
				droppable:true,
				before_change: function(files, dropped){
					var file = files[0];
					var name = file.name;
					//判断文件类型
					if (!name.endsWith(".jpg") && !name.endsWith(".jpeg") && !name.endsWith(".png") 
						&& !name.endsWith(".gif") && !name.endsWith(".bmp")){
						$("#pic").tips({
							side:3,
				            msg:'仅可上传 jpg jpeg png gif bmp 类型图片',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;	 
					}
					//判断文件大小
					if(file.size > 2097152){
						$("#pic").tips({
							side:3,
				            msg:'文件大小不能超过2M',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;
					}
					//上传图片
					var options = {
						url: '<%=basePath%>coursebase/uploadPic.do?tm='+new Date().getTime(),
						type: 'POST',
						dataType: 'json',
						cache: false,
						success: function(data){
							// 动态追加pic地址 
							$("#COURSE_COVER").attr("value",data.path);
							$("#pic").tips({
								side:3,
					            msg:'上传图片成功',
					            bg:'#AE81FF',
					            time:2
					        });
						},
						error: function(data){
							$("#pic").tips({
								side:3,
					            msg:'图片上传失败',
					            bg:'#AE81FF',
					            time:2
					        });
						}
					}
					$("#Form").ajaxSubmit(options);
					return true;
				}
			})
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
		
		function goBack(){
			 window.location.href = '<%=basePath%>trainplan/list.do';
		}
	
	
		//选择培训人群
		function choiceStudent(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="选择培训人员";
			 diag.URL = '<%=basePath%>testplan/listStudent.do';
			 diag.Width = 700;
			 diag.Height = 400;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 var str = '';
					for(var i=0;i < diag.innerFrame.contentWindow.document.getElementsByName('ids').length;i++){
					  if(diag.innerFrame.contentWindow.document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += diag.innerFrame.contentWindow.document.getElementsByName('ids')[i].value;
					  	else str += ',' + diag.innerFrame.contentWindow.document.getElementsByName('ids')[i].value;				 
					  }
					}
					$.ajax({
						   type: "POST",
						   url: '<%=basePath%>testplan/getChoiceStudent.do',
						   data: {'sturentStr':str},
						   dataType:'json',
						   cache: false,
						      success: function (data) {
						    	  if($("#uesrTextarea").val()==''){
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
							    	  $("#TEST_PLAN_PERSONS").val(userCodes); 
						    	  }else{
						    		  var userCodes='';
							    	  var userNames='';
							    	  var arry = $("#TEST_PLAN_PERSONS").val().split(",");
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
							    	  $("#TEST_PLAN_PERSONS").val($("#TEST_PLAN_PERSONS").val()+userCodes); 
						    	  }
						    	 
						         },
						            error: function () {

						            }
						  });
				diag.close();
			 };
			 diag.show();
		}
		function setTable(item,i){
			div='<tr><td class="center" style="width: 30px;">'
				+i 
				+"</td><td class='center'>"
				+item.STUDENT_CODE
				+"</td><td class='center'>"
				+item.STUDENT_NAME
				+"</td><td class='center'>"
				+item.UNIT_NAME
				+"</td><td class='center'>"
				+item.DEPART_NAME
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
		}
	</script>


</body>
</html>