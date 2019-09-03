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
					
					<form action="pesoinfo/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">社会组织名称:</td>
								<td colspan="3"><input type="text" name="PESO_NAME" id="PESO_NAME" value="${pd.PESO_NAME}" onblur="hasDuplicateRecord()" maxlength="300" placeholder="这里输入社会组织名称" title="社会组织名称" style="width:98%;"/></td>
							</tr>
							<tr>
							    <td style="width:79px;text-align: right;padding-top: 13px;">成立时间:</td>
								<td><input type="text" name="ESTA_TIME" id="ESTA_TIME" value="${pd.ESTA_TIME}" maxlength="20" placeholder="成立时间" title="成立时间" style="width:98%;"/></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">所属区域:</td>
								<!--<td><input type="text" name="BELONG_AREA" id="BELONG_AREA" value="${pd.BELONG_AREA}" maxlength="4" placeholder="这里输入所属区域" title="所属区域" style="width:98%;"/></td>-->
								<td>
									<select class="chosen-select form-control" name="BELONG_AREA" id="BELONG_AREA" data-placeholder="请选择所属区域" style="vertical-align:top;"  title="所属区域" style="width:98%;" >
										<option value=""></option>
										<c:forEach items="${areaList}" var="area">
											<option value="${area.BIANMA}" <c:if test="${area.BIANMA == pd.BELONG_AREA}">selected</c:if>>${area.NAME}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">统一社会信用代码:</td>
								<td><input type="text" name="USCC" id="USCC" value="${pd.USCC}" maxlength="18" placeholder="这里输入统一社会信用代码" title="统一社会信用代码" style="width:98%;"/></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">负责人:</td>
								<td><input type="text" name="HEAD_NAME" id="HEAD_NAME" value="${pd.HEAD_NAME}" maxlength="20" placeholder="这里输入负责人" title="负责人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">负责人手机:</td>
								<td><input type="text" name="HEAD_TEL" id="HEAD_TEL" value="${pd.HEAD_TEL}" maxlength="11" placeholder="这里输入负责人手机" title="负责人手机" style="width:98%;"/></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">电子邮箱:</td>
								<td><input type="text" name="E_MAIL" id="E_MAIL" value="${pd.E_MAIL}" maxlength="50" placeholder="这里输入电子邮箱" title="电子邮箱" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">办公电话:</td>
								<td><input type="text" name="OFFICE_TEL" id="OFFICE_TEL" value="${pd.OFFICE_TEL}" maxlength="15" placeholder="这里输入办公电话" title="办公电话" style="width:98%;"/></td>
							    <td style="width:79px;text-align: right;padding-top: 13px;">传真:</td>
								<td><input type="text" name="FAX" id="FAX" value="${pd.FAX}" maxlength="15" placeholder="这里输入传真" title="传真" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">办公地址:</td>
								<td colspan="3"><input type="text" name="BETT_ADDR" id="BETT_ADDR" value="${pd.OFFICE_ADDR}" maxlength="300" placeholder="这里输入办公地址" title="办公地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">职能简介:</td>
								<td colspan=3><textarea name="PESO_INTR" id="PESO_INTR" rows="3" maxlength="1000" placeholder="这里输入职能简介" title="职能简介" style="width:98%;">${pd.PESO_INTR}</textarea></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">注册资金:</td>
								<td><input type="number" name="REGI_CAPI" id="REGI_CAPI" value="${pd.REGI_CAPI}" maxlength="13" placeholder="这里输入注册资金" title="注册资金" style="width:98%;"/></td>
								<td style="width:79px;text-align: right;padding-top: 13px;">状态:</td>
								<!-- <td><input type="text" name="STATE" id="STATE" value="${pd.STATE}" maxlength="1" placeholder="这里输入状态" title="状态" style="width:98%;"/></td> -->
								<td>
									<select class="chosen-select form-control" name="STATE" id="STATE" data-placeholder="请选择状态" style="vertical-align:top;"  title="状态" style="width:98%;" >
										<option value=""></option>
										<c:forEach items="${stateList}" var="sta">
											<option value="${sta.STATE_ID}" <c:if test="${sta.STATE_ID == pd.STATE}">selected</c:if>>${sta.STATE_NAME}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">地理坐标:</td>
								<td colspan="3">
								    <input type="text" readonly name="GEOG_COOR" id="GEOG_COOR" value="${pd.GEOG_COOR}" maxlength="20" placeholder="这里输入地理坐标" title="地理坐标" style="width:90%;"/>
								    <a class="btn btn-xs btn-success" title="编辑" onclick="openMap();" style="width:8%;margin-bottom:3px;">
										<i class="ace-icon fa fa-globe bigger-120" title="打开地图"></i>
									</a>
								</td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
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
			if($("#PESO_NAME").val()==""){
				$("#PESO_NAME").tips({
					side:3,
		            msg:'请输入社会组织名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PESO_NAME").focus();
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
			if($("#HEAD_NAME").val()==""){
				$("#HEAD_NAME").tips({
					side:3,
		            msg:'请输入负责人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#HEAD_NAME").focus();
			return false;
			}
			if($("#HEAD_TEL").val()==""){
				$("#HEAD_TEL").tips({
					side:3,
		            msg:'请输入负责人手机',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#HEAD_TEL").focus();
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
			if($("#OFFICE_ADDR").val()==""){
				$("#OFFICE_ADDR").tips({
					side:3,
		            msg:'请输入办公地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#OFFICE_ADDR").focus();
			return false;
			}
			if($("#PESO_INTR").val()==""){
				$("#PESO_INTR").tips({
					side:3,
		            msg:'请输入职能简介',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PESO_INTR").focus();
			return false;
			}
			if($("#STATE").val()==""){
				$("#STATE").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATE").focus();
			return false;
			}
			if(!(/^1[34578]\d{9}$/.test($("#HEAD_TEL").val()))){
				$("#HEAD_TEL").tips({
					side:3,
		            msg:'负责人手机有误，请重填',
		            bg:'#AE81FF',
		            time:2
		        });
		        $("#HEAD_TEL").focus();
			return false;
			}
			if(!/^\d+(\.\d{0,2})?$/.test($("#REGI_CAPI").val())){
				$("#REGI_CAPI").tips({
					side:3,
		            msg:'注册资金最多两位小数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#REGI_CAPI").focus();
			return false;
			}
            if($("#E_MAIL").val()!=null&&$("#E_MAIL").val().trim()!=""){
    			var filterEmail  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    			if(!(filterEmail.test($("#E_MAIL").val()))){
    				$("#E_MAIL").tips({
    					side:3,
    		            msg:'电子邮箱格式不正确',
    		            bg:'#AE81FF',
    		            time:2
    		        });
    				$("#E_MAIL").focus();
    			return false;
    			}
            }
			var ID = $("#ID").val();
			var PESO_NAME = $("#PESO_NAME").val();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>pesoinfo/hasDuplicateRecord.do',
		    	data: {ID:ID,PESO_NAME:PESO_NAME,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data.result){
							$("#Form").submit();
							$("#zhongxin").hide();
							$("#zhongxin2").show();
					 }else if("error" == data.result){
						$("#PESO_NAME").tips({
							side:3,
				            msg:'组织名称:'+PESO_NAME+' 已存在,重新输入',
				            bg:'#AE81FF',
				            time:2
				        });
						$("#PESO_NAME").focus();
						return false;
					 }else{
						 alert(data.result);  
							return false;
					 }
				}
			});
		}
		
		function hasDuplicateRecord(){
			var ID = $("#ID").val();
			var PESO_NAME = $("#PESO_NAME").val();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>pesoinfo/hasDuplicateRecord.do',
		    	data: {ID:ID,PESO_NAME:PESO_NAME,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data.result){
						 return true;
					 }else if("error" == data.result){
						$("#PESO_NAME").tips({
							side:3,
				            msg:'组织名称:'+PESO_NAME+' 已存在,重新输入',
				            bg:'#AE81FF',
				            time:2
				        });
						$("#PESO_NAME").focus();
						return false;
					 }else{
						 alert(data.result);  
							return false;
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