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
					
					<form action="betting/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ID" id="BETTING_ID" value="${pd.ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">编号:</td>
								<td><input type="text" name="ID_CODE" id="ID_CODE" value="${pd.ID_CODE}" maxlength="30" placeholder="这里输入编号" title="编号" onblur="hasIDCode('${pd.ID}')" style="width:98%;"/></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">营业执照注册号:</td>
								<td><input type="text" name="LICENSE_NO" id="LICENSE_NO" value="${pd.LICENSE_NO}" maxlength="15" placeholder="这里输入营业执照注册号" title="营业执照注册号" style="width:98%;"/></td>
							</tr>

							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">业主姓名:</td>
								<td><input type="text" name="USER_NAME" id="USER_NAME" value="${pd.USER_NAME}" maxlength="20" placeholder="这里输入业主姓名" title="业主姓名" style="width:98%;"/></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">手机号:</td>
								<td><input type="text" name="MOBILE_TEL" id="MOBILE_TEL" value="${pd.MOBILE_TEL}" maxlength="11" placeholder="这里输入手机号" title="手机号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">办公电话:</td>
								<td><input type="text" name="OFFICE_TEL" id="OFFICE_TEL" value="${pd.OFFICE_TEL}" maxlength="15" placeholder="这里输入办公电话" title="办公电话" style="width:98%;"/></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">传真号:</td>
								<td><input type="text" name="FAX" id="FAX" value="${pd.FAX}" maxlength="15" placeholder="这里输入传真号" title="传真号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">所属区域:</td>
								<%-- <td><input type="text" name="BELONG_AREA" id="BELONG_AREA" value="${pd.BELONG_AREA}" maxlength="10" placeholder="这里输入所属区域" title="所属区域" style="width:98%;"/></td> --%>
								<td id="ssqy">
									<select class="form-control" name="BELONG_AREA" id="BELONG_AREA" data-placeholder="请选择所属区域" style="vertical-align:top;"  title="所属区域" style="width:98%;" >
										<option value=""></option>
										<c:forEach items="${areaList}" var="area">
											<option value="${area.BIANMA }" <c:if test="${area.BIANMA == pd.BELONG_AREA }">selected</c:if>>${area.NAME }</option>
										</c:forEach>
									</select>
								</td>
								<td style="width:79px;text-align: right;padding-top: 13px;">投注站地址:</td>
								<td><input type="text" name="BETT_ADDR" id="BETT_ADDR" value="${pd.BETT_ADDR}" maxlength="300" placeholder="这里输入投注站地址" title="投注站地址" style="width:98%;"/></td>
								
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">投注站面积:</td>
								<td><input type="text" name="BETT_AREA" id="BETT_AREA" value="${pd.BETT_AREA}" maxlength="30" placeholder="这里输入投注站面积" title="投注站面积" style="width:98%;"/></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">简介:</td>
								<td><input type="text" name="BETT_INTR" id="BETT_INTR" value="${pd.BETT_INTR}" maxlength="1000" placeholder="这里输入简介" title="简介" style="width:98%;"/></td>
								
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">地理坐标:</td>
								<td colspan="3">
									<input type="text" readonly name="GEOG_COOR" id="GEOG_COOR" value="${pd.GEOG_COOR}" maxlength="20" placeholder="这里输入地理坐标" title="地理坐标" style="width:90%;"/>
									<a class="btn btn-xs btn-success" title="编辑" onclick="openMap();" style="width:8%;margin-bottom:3px;">
										<i class="ace-icon fa fa-globe bigger-120" title="打开地图"></i>
									</a>
									
									<%-- <div class="input-group" style="width: 100%;">
										 <input id="GEOG_COOR" type="text" name="GEOG_COOR"  value="${pd.GEOG_COOR}" readonly 
											 placeholder="这里输入地理坐标" title="地理坐标"/> 
										<span class="input-group-btn">
											<button type="button" class="btn btn-success btn-xs"
												onclick="openMap()">
												<span class="ace-icon fa fa-globe icon-on-right bigger-120"></span>
											</button>
										</span>
									</div> --%>
								</td>							
							</tr>
							<tr>
								<td  style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
								<td colspan="3"><input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="500" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="4">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
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
	<!--引入属于此页面的js -->
	<script type="text/javascript" src="static/js/myjs/mapSelect.js"></script>
	
	<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#LICENSE_NO").val()==""){
				$("#LICENSE_NO").tips({
					side:3,
		            msg:'请输入营业执照注册号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LICENSE_NO").focus();
			return false;
			}
			if($("#USER_NAME").val()==""){
				$("#USER_NAME").tips({
					side:3,
		            msg:'请输入业主姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_NAME").focus();
			return false;
			}
			if($("#MOBILE_TEL").val()==""){
				$("#MOBILE_TEL").tips({
					side:3,
		            msg:'请输入手机号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MOBILE_TEL").focus();
			return false;
			}
			if($("#OFFICE_TEL").val()==""){
				$("#OFFICE_TEL").tips({
					side:3,
		            msg:'请输入办公电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#OFFICE_TEL").focus();
			return false;
			}
			if($("#FAX").val()==""){
				$("#FAX").tips({
					side:3,
		            msg:'请输入传真号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FAX").focus();
			return false;
			}
			if($("#BELONG_AREA").val()==""){
				$("#BELONG_AREA").tips({
					side:3,
		            msg:'请输入所属区域',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BELONG_AREA").focus();
			return false;
			}
			if($("#BETT_ADDR").val()==""){
				$("#BETT_ADDR").tips({
					side:3,
		            msg:'请输入投注站地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BETT_ADDR").focus();
			return false;
			}
			if($("#BETT_AREA").val()==""){
				$("#BETT_AREA").tips({
					side:3,
		            msg:'请输入投注站面积',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BETT_AREA").focus();
			return false;
			}
			if($("#BETT_INTR").val()==""){
				$("#BETT_INTR").tips({
					side:3,
		            msg:'请输入简介',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BETT_INTR").focus();
			return false;
			}
			if($("#GEOG_COOR").val()==""){
				$("#GEOG_COOR").tips({
					side:3,
		            msg:'请输入地理坐标',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GEOG_COOR").focus();
			return false;
			}
			if($("#REMARK").val()==""){
				$("#REMARK").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#REMARK").focus();
			return false;
			}
			if($("#ID_CODE").val()==""){
				$("#ID_CODE").tips({
					side:3,
		            msg:'请输入编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ID_CODE").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		//判断编码是否存在
		function hasIDCode(ID){
			var ID_CODE = $("#ID_CODE").val();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>betting/hasIDCode.do',
		    	data: {ID_CODE:ID_CODE,ID:ID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" != data.result){
						 $("#ID_CODE").tips({
								side:3,
					            msg:'编号'+ID_CODE+'已存在',
					            bg:'#AE81FF',
					            time:3
					        });
						 $('#ID_CODE').val('');
					 }
				}
			});
		}
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>