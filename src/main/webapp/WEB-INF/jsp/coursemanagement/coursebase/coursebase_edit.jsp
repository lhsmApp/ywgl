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
	<!-- 图片上传 -->
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/ace/js/jquery.form.js"></script>
	<link rel="stylesheet" href="static/ace/css/bootstrap.css" />
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	<link rel="stylesheet" href="static/ace/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
	<link rel="stylesheet" href="static/ace/assets/css/bootstrap-editable.css" />
	
	<!-- bootstrap & fontawesome -->
		
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<form action="coursebase/${msg}.do" name="Form" id="Form" method="post">
						<div id="zhongxin" style="padding-top: 13px;" class="form-inline">
						<div class="col-xs-6" style="padding-left:0px; margin-bottom:-15px;">
						<input type="hidden" name="COURSE_ID" id="COURSE_ID" value="${pd.COURSE_ID}"/>
						<table id="table_report" class="table table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">课程名称:</td>
								<td><input type="text" name="COURSE_NAME" id="COURSE_NAME" value="${pd.COURSE_NAME}" maxlength="50" placeholder="请输入课程名称" title="请输入课程名称" style="width:99%;"/></td>
							</tr>
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 13px;">课程分类:</td>
								<td>
									<select class="chosen-select form-control" name="COURSE_TYPE" id="COURSE_TYPE" data-placeholder="请选择课程分类" style="vertical-align: top; height: 32px; width: 99%;">
										<c:forEach items="${treeList}" var="tl">
											<option value="${tl.COURSE_TYPE_ID}" <c:if test="${tl.COURSE_TYPE_ID==pd.COURSE_TYPE}">selected="selected"</c:if>>${tl.COURSE_TYPE_NAME}</option>
											
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 13px;">课程讲师:</td>
								<td><input type="text" name="COURSE_TEACHER" id="COURSE_TEACHER" value="${pd.COURSE_TEACHER}" maxlength="30" placeholder="请输入课程讲师" title="请输入课程讲师" style="width:99%;"/></td>
							</tr>
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 13px;">课程标签</td>
								<td><input type="text" name="COURSE_TAG" id="COURSE_TAG" value="${pd.COURSE_TAG}" maxlength="50" placeholder="请输入课程标签" title="请输入课程标签" style="width:99%;"/></td>
							</tr>
						</table>
					</div>
					<div id="uploadPic" class="col-xs-6" style="text-align:center; padding-top:10%;">
						<input id="pic" name="pic" type="file">
					</div>
						<input hidden="hidden" id="COURSE_COVER" name="COURSE_COVER" value="${pd.COURSE_COVER}">
					<table class="table table-bordered table-hover">
						<tr>
							<td style="width:75px;text-align: center;padding-top: 13px;">课程简介:</td>
								<td><textarea name="COURSE_NOTE" id="COURSE_NOTE" maxlength="50" placeholder="请输入课程介绍" title="请输入课程介绍" style="width:99%;">${pd.COURSE_NOTE}</textarea></td>
						</tr>
						<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr> 
					</table>
					</div>
					<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
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
	<script type="text/javascript" src="static/ace/js/ace/ace.js"></script>
	<script type="text/javascript" src="static/ace/js/x-editable/bootstrap-editable.js"></script>
	<script type="text/javascript" src="static/ace/js/x-editable/ace-editable.js"></script>
	<!--图片上传-->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<script src="static/ace/js/ace/ace.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		jQuery(function($){
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
		});
		
		//获取文件后缀名
		function endsWith(str, suffix) {
    		return str.indexOf(suffix, str.length - suffix.length) !== -1;
		}
		
		//保存
		function save(){
			if($("#COURSE_NAME").val()==""){
			$("#COURSE_NAME").tips({
				side:3,
	            msg:'请输入课程名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#COURSE_NAME").focus();
			return false;
			}
			if($("#COURSE_TYPE").val()==""){
				$("#COURSE_TYPE").tips({
					side:3,
		            msg:'请输入课程分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_TYPE").focus();
			return false;
			}
			if($("#COURSE_TEACHER").val()==""){
				$("#COURSE_TEACHER").tips({
					side:3,
		            msg:'请输入课程讲师',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_TEACHER").focus();
			return false;
			}
			if($("#COURSE_TAG").val()==""){
				$("#COURSE_TAG").tips({
					side:3,
		            msg:'请输入课程标签',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COURSE_TAG").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		</script>
</body>
</html>