<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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

							<form action="policy/${msg }.do" name="Form" id="Form"
								method="post">
								<input type="hidden" name="ID" id="ID"
									value="${pd.ID}" />
								<textarea name="TITL_CONT" id="TITL_CONT" style="display: none"></textarea>
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">政策分类:</td>
											<td><input type="text" name="POLI_TYPE" id="POLI_TYPE"
												value="${pd.POLI_TYPE}" maxlength="100"
												placeholder="这里输入政策分类" title="政策分类" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">标题:</td>
											<td><input type="text" name="TITLE" id="TITLE"
												value="${pd.TITLE}" maxlength="200" placeholder="这里输入标题"
												title="标题" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">发布时间:</td>
											<td><input class="span10 date-picker" name="PUB_DATE"
												id="PUB_DATE" value="${pd.PUB_DATE}" type="text"
												data-date-format="yyyy-mm-dd" readonly="readonly"
												placeholder="发布时间" title="发布时间" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">发布人:</td>
											<td><input type="text" name="PUB_USER" id="PUB_USER"
												value="${pd.PUB_USER}" maxlength="30" placeholder="这里输入发布人"
												title="发布人" style="width: 98%;" /></td>
										</tr>
										<%-- <tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">政策内容:</td>
											<td><input type="text" name="TITL_CONT" id="TITL_CONT"
												value="${pd.TITL_CONT}" maxlength="715827882"
												placeholder="这里输入政策内容" title="政策内容" style="width: 98%;" /></td>
										</tr> --%>
										<tr>
											<!-- <td style="width:75px;text-align: right;padding-top: 13px;">政策内容:</td> -->
											<td id="nr" colspan="2" style="padding-top: 13px;"><script
													id="editor" type="text/plain"
													style="width: 98%; height: 259px;"></script></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10"><a
												class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
												class="btn btn-mini btn-danger"
												onclick="top.Dialog.close();">取消</a></td>
										</tr>
									</table>
								</div>
								<div id="zhongxin2" class="center" style="display: none">
									<br /> <br /> <br /> <br /> <br /> <img
										src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green">提交中...</h4>
								</div>
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


	<!-- 百度富文本编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";
	</script>
	<script type="text/javascript" charset="utf-8"
		src="plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="plugins/ueditor/ueditor.all.js"></script>
	<!-- 百度富文本编辑框-->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());

		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});

			var ue = UE.getEditor('editor');
			//判断ueditor 编辑器是否创建成功
			ue.addListener("ready", function() {
				// editor准备好之后才可以使用
				setContent();
			});
		});

		//保存
		function save() {
			$("#TITL_CONT").val(getContent());

			if ($("#POLI_TYPE").val() == "") {
				$("#POLI_TYPE").tips({
					side : 3,
					msg : '请输入政策分类',
					bg : '#AE81FF',
					time : 2
				});
				$("#POLI_TYPE").focus();
				return false;
			}
			if ($("#TITLE").val() == "") {
				$("#TITLE").tips({
					side : 3,
					msg : '请输入标题',
					bg : '#AE81FF',
					time : 2
				});
				$("#TITLE").focus();
				return false;
			}
			if ($("#PUB_DATE").val() == "") {
				$("#PUB_DATE").tips({
					side : 3,
					msg : '请输入发布时间',
					bg : '#AE81FF',
					time : 2
				});
				$("#PUB_DATE").focus();
				return false;
			}
			if ($("#PUB_USER").val() == "") {
				$("#PUB_USER").tips({
					side : 3,
					msg : '请输入发布人',
					bg : '#AE81FF',
					time : 2
				});
				$("#PUB_USER").focus();
				return false;
			}
			if ($("#TITL_CONT").val() == "") {

				$("#nr").tips({
					side : 3,
					msg : '请输入政策内容',
					bg : '#AE81FF',
					time : 2
				});
				UE.getEditor('editor').focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		//ueditor有标签文本
		function getContent() {
			var arr = [];
			arr.push(UE.getEditor('editor').getContent());
			return arr.join("");
		}

		//ueditor有标签文本
		function setContent() {
			UE.getEditor('editor').setContent('${pd.TITL_CONT}', false);
		}
	</script>
</body>
</html>