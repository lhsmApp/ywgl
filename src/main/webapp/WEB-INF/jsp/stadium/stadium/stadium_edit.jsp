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

							<form action="stadium/${msg }.do" name="Form" id="Form"
								method="post"  enctype="multipart/form-data">
								<input type="hidden" name="ID" id="ID" value="${pd.ID}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">场馆名称:</td>
											<td><input type="text" name="STAD_NAME" id="STAD_NAME"
												value="${pd.STAD_NAME}" maxlength="100"
												placeholder="这里输入场馆名称" title="场馆名称"
												onblur="hasStaName('${pd.ID}')" style="width: 98%;" /></td>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">场馆地址:</td>
											<td><input type="text" name="STADI_ADDR" id="STADI_ADDR"
												value="${pd.STADI_ADDR}" maxlength="300"
												placeholder="这里输入场馆地址" title="场馆地址" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">负责人姓名:</td>
											<td><input type="text" name="HEAD_NAME" id="HEAD_NAME"
												value="${pd.HEAD_NAME}" maxlength="20"
												placeholder="这里输入负责人姓名" title="负责人姓名" style="width: 98%;" /></td>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">负责人电话:</td>
											<td><input type="text" name="HEAD_TEL" id="HEAD_TEL"
												value="${pd.HEAD_TEL}" maxlength="11"
												placeholder="这里输入负责人电话" title="负责人电话" style="width: 98%;" /></td>
										</tr>

										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">所属区域:</td>
											<%-- <td><input type="text" name="BELONG_AREA"
												id="BELONG_AREA" value="${pd.BELONG_AREA}" maxlength="4"
												placeholder="这里输入所属区域" title="所属区域" style="width: 98%;" /></td> --%>
											<td id="ssqy"><select class="chosen-select form-control"
												name="BELONG_AREA" id="BELONG_AREA"
												data-placeholder="请选择所属区域" style="vertical-align: top;"
												title="所属区域" style="width:98%;">
													<option value=""></option>
													<c:forEach items="${areaList}" var="area">
														<option value="${area.BIANMA }"
															<c:if test="${area.BIANMA == pd.BELONG_AREA }">selected</c:if>>${area.NAME }</option>
													</c:forEach>
											</select></td>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">运动项目:</td>
											<td><input type="text" name="STADI_SPOT" id="STADI_SPOT"
												value="${pd.STADI_SPOT}" 
												placeholder="这里输入运动项目" title="运动项目" style="width: 98%;" /></td>
												
												
												<td><input type="hidden" name="OPER_NATURE" id="OPER_NATURE"
												value="${pd.OPER_NATURE}" maxlength="11"
												placeholder="这里输入经营性质" title="经营性质" style="width: 98%;" /></td>
											<%-- <td><select class="chosen-select form-control"
												name="OPER_NATURE" id="OPER_NATURE"
												data-placeholder="请选择运营性质" style="vertical-align: top;"
												title="运营性质" style="width:98%;">
													<option value=""></option>
													<c:forEach items="${natureList}" var="nature">
														<option value="${nature.nameValue }"
															<c:if test="${nature.nameValue == pd.OPER_NATURE }">selected</c:if>>${nature.nameValue }</option>
													</c:forEach>
											</select></td> --%>
										</tr>

										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">场馆基本情况:</td>
											<td colspan="3"><input type="text" name="STADI_INTR"
												id="STADI_INTR" value="${pd.STADI_INTR}" maxlength="2000"
												placeholder="这里输入场馆基本情况" title="场馆基本情况" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">地理坐标:</td>
											<td colspan="3"><input type="text" readonly
												name="GEOG_COOR" id="GEOG_COOR" value="${pd.GEOG_COOR}"
												maxlength="20" placeholder="这里输入地理坐标" title="地理坐标"
												style="width: 90%;" /> <a class="btn btn-xs btn-success"
												title="编辑" onclick="openStadiumMap();"
												style="width: 8%; margin-bottom: 3px;"> <i
													class="ace-icon fa fa-globe bigger-120" title="打开地图"></i>
											</a>
										</tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">备注:</td>
											<td colspan="3"><input type="text" name="REMARK"
												id="REMARK" value="${pd.REMARK}" maxlength="500"
												placeholder="这里输入备注" title="备注" style="width: 98%;" /></td>
										</tr>

										
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">图片:</td>
											 <td colspan="3"><input type="file" name="fileLogos" id="id-input-file-1" style="width: 98%;"/>
												

											</td> 
										

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


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 文件搜索 -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<!-- ace核心文件 -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--引入属于此页面的js -->
	<script type="text/javascript" src="static/js/myjs/mapSelect.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		//保存
		function save() {
			if ($("#STAD_NAME").val() == "") {
				$("#STAD_NAME").tips({
					side : 3,
					msg : '请输入场馆名称',
					bg : '#AE81FF',
					time : 2
				});
				$("#STAD_NAME").focus();
				return false;
			}
			if ($("#STADI_ADDR").val() == "") {
				$("#STADI_ADDR").tips({
					side : 3,
					msg : '请输入场馆地址',
					bg : '#AE81FF',
					time : 2
				});
				$("#STADI_ADDR").focus();
				return false;
			}
			if ($("#HEAD_NAME").val() == "") {
				$("#HEAD_NAME").tips({
					side : 3,
					msg : '请输入负责人姓名',
					bg : '#AE81FF',
					time : 2
				});
				$("#HEAD_NAME").focus();
				return false;
			}
			if ($("#HEAD_TEL").val() == "") {
				$("#HEAD_TEL").tips({
					side : 3,
					msg : '请输入负责人电话',
					bg : '#AE81FF',
					time : 2
				});
				$("#HEAD_TEL").focus();
				return false;
			}
			if ($("#BELONG_AREA").val() == "") {
				$("#BELONG_AREA").tips({
					side : 3,
					msg : '请输入所属区域',
					bg : '#AE81FF',
					time : 2
				});
				$("#BELONG_AREA").focus();
				return false;
			}
			/*  if ($("#OPER_NATURE").val() == "") {
				$("#OPER_NATURE").tips({
					side : 3,
					msg : '请输入运营性质',
					bg : '#AE81FF',
					time : 2
				});
				$("#OPER_NATURE").focus();
				return false;
			}  */
			 if ($("#STADI_SPOT").val() == "") {
					$("#STADI_SPOT").tips({
						side : 3,
						msg : '请输入运动项目',
						bg : '#AE81FF',
						time : 2
					});
					$("#OPER_NATURE").focus();
					return false;
				} 
			if ($("#STADI_INTR").val() == "") {
				$("#STADI_INTR").tips({
					side : 3,
					msg : '请输入场馆基本情况',
					bg : '#AE81FF',
					time : 2
				});
				$("#STADI_INTR").focus();
				return false;
			}
			if ($("#GEOG_COOR").val() == "") {
				$("#GEOG_COOR").tips({
					side : 3,
					msg : '请输入地理坐标',
					bg : '#AE81FF',
					time : 2
				});
				$("#GEOG_COOR").focus();
				return false;
			}
			if ($("#REMARK").val() == "") {
				$("#REMARK").tips({
					side : 3,
					msg : '请输入备注',
					bg : '#AE81FF',
					time : 2
				});
				$("#REMARK").focus();
				return false;
			}
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
		}

		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});
			
			$('#id-input-file-1').ace_file_input({
				no_file:'No File ...',
				file:'${pd.PHOTO_ADDR}',
				btn_choose:'Choose',
				btn_change:'Change',
				droppable:false,
				onchange:null,
				thumbnail:false //| true | large
				//whitelist:'gif|png|jpg|jpeg'
				//blacklist:'exe|php'
				//onchange:''
				//
			});
			
			$('#id-input-file').ace_file_input({
				style:'well',
				btn_choose:'Drop files here or click to choose',
				btn_change:null,
				no_icon:'ace-icon fa fa-cloud-upload',
				droppable:true,
				thumbnail:'small'//large | fit
				//,icon_remove:null//set null, to hide remove/reset button
				/**,before_change:function(files, dropped) {
					//Check an example below
					//or examples/file-upload.html
					return true;
				}*/
				/**,before_remove : function() {
					return true;
				}*/
				,
				preview_error : function(filename, error_code) {
					//name of the file that failed
					//error_code values
					//1 = 'FILE_LOAD_FAILED',
					//2 = 'IMAGE_LOAD_FAILED',
					//3 = 'THUMBNAIL_FAILED'
					//alert(error_code);
				}
		
			}).on('change', function(){
				//console.log($(this).data('ace_input_files'));
				//console.log($(this).data('ace_input_method'));
			});
			
			
		});
		
		
		//判断场馆名是否存在
		function hasStaName(ID){
			var STAD_NAME = $.trim($("#STAD_NAME").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>stadium/hasStaName.do',
				data : {
					STAD_NAME : STAD_NAME,
					ID : ID,
					tm : new Date().getTime()
				},
				dataType : 'json',
				cache : false,
				success : function(data) {
					if ("success" != data.result) {
						$("#STAD_NAME").tips({
							side : 3,
							msg : '体育场馆' + STAD_NAME + '已存在',
							bg : '#AE81FF',
							time : 3
						});
						$('#STAD_NAME').val('');
					}
				}
			});
		}
		
	</script>
</body>
</html>