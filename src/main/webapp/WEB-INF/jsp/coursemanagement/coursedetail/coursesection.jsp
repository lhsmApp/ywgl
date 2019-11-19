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
	<link rel="stylesheet" href="static/ace/css/bootstrap.css" />
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
					<form action="coursedetail/${msg}section.do" name="Form" id="Form" method="post">
						<div id="zhongxin" style="padding-top: 13px;">
							<div class="col-xs-6" style="padding-left:0px;">
							<input type="hidden" name="CHAPTER_ID" id="CHAPTER_ID" value="${pd.CHAPTER_ID}">
							<input type="hidden" name="CHAPTER_PARENT_ID" id="CHAPTER_PARENT_ID" value="${pd.CHAPTER_PARENT_ID}"/>
							<input type="hidden" name="COURSE_ID" id="COURSE_ID" value="${pd.COURSE_ID}"/>
							<input type="hidden" name="VIDEO_COVER" id="VIDEO_COVER" value="${pd.VIDEO_COVER}"/>
							<input type="hidden" name="VIDEO_ADDRESS" id="VIDEO_ADDRESS" value="${pd.VIDEO_ADDRESS}"/> 
							<table id="table_report" class="table table-striped table-bordered table-hover" style="height:125px;">
								<tr>
									<td style="width:80px;text-align: right;padding-top:16px;">小节名称:</td>
									<td>
										<input type="text" name="CHAPTER_NAME" id="CHAPTER_NAME" value="${pd.CHAPTER_NAME}" maxlength="100" placeholder="这里输入备注2" title="备注2" style="width:98%; margin-top: 3px;"/>
									</td>
								</tr>
								<tr>
									<td style="width:80px;text-align: right;padding-top: 16px;">内容形式:</td>
									<td>
										<select class="form-control" id="CHAPTER_TYPE" name="CHAPTER_TYPE" style="margin-top: 3px;">
											<option value="1">视频</option>
										</select>
									</td>
								</tr>
							</table>
						</div>
						<div id="uploadPic" class="col-xs-6" style="text-align:center;">
							<input id="pic" name="pic" type="file">
						</div>
						
						<table class="table table-bordered table-hover">
							<tr>
								<td style="width:80px;text-align: right;padding-top: 13px;">小节视频:</td>
								<td><input id="video" name="video" type="file"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a> 
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a></td>
							</tr>
						</table>
					</div>
					<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
				</form>
				</div>
			</div>
		</div>
	</div>
</div>


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
		//上传图片
		jQuery(function($){
			$('#pic').ace_file_input({
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
					var options = {
						url: '<%=basePath%>coursebase/uploadPic.do?tm='+new Date().getTime(),
						type: 'POST',
						dataType: 'json',
						cache: false,
						success: function(data){
							// 动态追加pic地址 
							$("#VIDEO_COVER").attr("value",data.path);
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
			//上传视频
			$('#video').ace_file_input({
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
						$("#video").tips({
							side:3,
				            msg:'仅可上传 .mp4 格式视频',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;	 
					}
					//判断文件大小
					if(file.size > 2147483648){
						$("#pic").tips({
							side:3,
				            msg:'文件大小不能超过2G',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;
					}
					var options = {
						url: '<%=basePath%>coursedetail/uploadVideo.do?tm='+new Date().getTime(),
						type: 'POST',
						dataType: 'json',
						cache: false,
						success: function(data){
							// 动态追加video地址 
							$("#VIDEO_ADDRESS").attr("value",data.path);
							$("#video").tips({
								side:3,
					            msg:'视频上传成功',
					            bg:'#AE81FF',
					            time:2
					        });
						},
						error: function(data){
							$("#video").tips({
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
			if($("#CHAPTER_NAME").val()==""){
				$("#CHAPTER_NAME").tips({
					side:3,
		            msg:'请输入视频名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CHAPTER_NAME").focus();
			return false;
			}
			/* if($("#VIDEO_COVER").val()==""){
				$("#pic").tips({
					side:3,
		            msg:'请上传视频封面',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#pic").focus();
			return false;
			} */

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