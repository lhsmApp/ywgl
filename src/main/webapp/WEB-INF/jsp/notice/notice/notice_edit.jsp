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
	<!-- 图片上传 -->
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/ace/js/jquery.form.js"></script>
	<link rel="stylesheet" href="static/ace/css/bootstrap-editable.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					<form action="notice/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="NOTICE_ID" id="NOTICE_ID" value="${pd.NOTICE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
							<div id="task-tab" class="tab-pane active">
							<ul id="tasks_1" class="item-list ui-sortable">
									<li class="item-orange clearfix ui-sortable-handle">
										<label class="inline"><span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="menu-icon fa fa-tachometer orange"></i>起止时间:</span></label>
										<div class="inline">
										<div style="padding-left:2px;" class="inline"><input class="span10 date-picker inline" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></div>
										<div style="padding-left:2px;" class="inline"><input class="span10 date-picker inline" name="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></div>
										</div>
									</li>
									<li id="liBefore" class="item-green clearfix ui-sortable-handle">
										<label class="inline">
												<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="menu-icon fa fa-book green"></i>发布范围:</span>
											</label>
									<select id="TEST_PAPER_DIFFICULTY" name="TEST_PAPER_DIFFICULTY" class="form-control inline" style="width:155px;">
										<option value="">全部</option>
										<option value="1" <c:if test="${pd.TEST_PAPER_DIFFICULTY == 1}">selected</c:if>>按角色选择</option>
										<option value="2" <c:if test="${pd.TEST_PAPER_DIFFICULTY == 2}">selected</c:if>>按单位选择</option>
										<option value="3" <c:if test="${pd.TEST_PAPER_DIFFICULTY == 3}">selected</c:if>>按人员选择</option>
									</select>	
									</li>
									<li id="singleAnswer" class="item-blue clearfix ui-sortable-handle">
										<label class="inline">
											<i class="ace-icon fa fa-list green"></i>
											<span style="padding-left:5px;" class="lbl">附件:</span>
										</label>
										<div>
											<input id="file" name="file" type="file"/>
											<input type="hidden" name="ATTACHMENT_PATH" id="ATTACHMENT_PATH" value="${pd.ATTACHMENT_PATH}"/> 
										</div>
									</li>
								<li id="fixedPaper" class="item-default clearfix ui-sortable-handle" hidden="hidden">
									<label class="inline">
										<span><a onclick="addPaper();" class="btn btn-minier bigger btn-primary"><i class="ace-icon fa fa-plus-circle"></i>添加试题</a></span>
									</label>
								</li>
						<li id="liBefore" class="item-blue clearfix ui-sortable-handle">
							<div class="inline">
								<!-- <a class="btn btn-mini btn-primary" onclick="save();">保存试卷</a>
								<a class="btn btn-mini btn-danger" onclick="goPaper();">返回列表</a> -->
							</div>
						</li>
						<li id="showQuestion" class="item-default clearfix ui-sortable-handle" hidden="hidden">
								<div class="widget-box transparent">
									<div class="widget-header widget-header-flat">
										<h4 class="widget-title lighter"><i class="ace-icon fa fa-star orange"></i>
										<font style="vertical-align: inherit;"><font style="vertical-align: inherit;">试题列表</font></font></h4>
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
	<!-- ace scripts -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<script src="static/ace/js/ace/ace.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		$(function(){
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
		});
		
		jQuery(function($){
			//上传视频
			$('#file').ace_file_input({
				no_file:'No File ...',
				btn_choose:'选择',
				btn_change:'选择',
				droppable:false,
				onchange:null,
				thumbnail:false,
				before_change:function(files,dropped){
					var file = files[0];
					var name = file.name;
					//判断文件类型
					if (!name.endsWith(".mp4")){
						$("#file").tips({
							side:3,
				            msg:'仅可上传 .mp4 格式视频',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;	 
					}
					//判断文件大小
					if(file.size > 2147483648){
						$("#file").tips({
							side:3,
				            msg:'文件大小不能超过2G',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;
					}
					var options = {
						url: '<%=basePath%>notice/uploadFile.do?tm='+new Date().getTime(),
						type: 'POST',
						dataType: 'json',
						cache: false,
						success: function(data){
							// 动态追加video地址 
							$("#ATTACHMENT_PATH").attr("value",data.path);
							$("#video").tips({
								side:3,
					            msg:'视频上传成功',
					            bg:'#AE81FF',
					            time:2
					        });
						},
						error: function(data){
							$("#file").tips({
								side:3,
					            msg:'视频上传失败',
					            bg:'#AE81FF',
					            time:2
					        });
						}
					}
					$("#Form").ajaxSubmit(options);
					return true;
				}
			});
			$(".remove").bind('click',function(){
				//取消上传
			})
		});
		//保存
		function save(){
			if($("#NOTICE_CONTENT").val()==""){
				$("#NOTICE_CONTENT").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NOTICE_CONTENT").focus();
			return false;
			}
			if($("#NOTICE_USER").val()==""){
				$("#NOTICE_USER").tips({
					side:3,
		            msg:'请输入备注3',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NOTICE_USER").focus();
			return false;
			}
			if($("#NOTICE_TYPE").val()==""){
				$("#NOTICE_TYPE").tips({
					side:3,
		            msg:'请输入备注4',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NOTICE_TYPE").focus();
			return false;
			}
			if($("#START_TIME").val()==""){
				$("#START_TIME").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#START_TIME").focus();
			return false;
			}
			if($("#END_TIME").val()==""){
				$("#END_TIME").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#END_TIME").focus();
			return false;
			}
			if($("#ATTACHMENT_PATH").val()==""){
				$("#ATTACHMENT_PATH").tips({
					side:3,
		            msg:'请输入备注7',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ATTACHMENT_PATH").focus();
			return false;
			}
			if($("#STATE").val()==""){
				$("#STATE").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATE").focus();
			return false;
			}
			if($("#CREATE_USER").val()==""){
				$("#CREATE_USER").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATE_USER").focus();
			return false;
			}
			if($("#USER_DEPART").val()==""){
				$("#USER_DEPART").tips({
					side:3,
		            msg:'请输入备注10',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_DEPART").focus();
			return false;
			}
			if($("#CREATE_DATE").val()==""){
				$("#CREATE_DATE").tips({
					side:3,
		            msg:'请输入备注11',
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
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>