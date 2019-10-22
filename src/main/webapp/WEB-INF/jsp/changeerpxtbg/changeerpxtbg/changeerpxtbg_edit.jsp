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
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<!-- 树形下拉框start -->
	<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
	<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
	<link rel="stylesheet" type="text/css" href="plugins/selectZtree/import_fh.css"/>
	<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
	<link type="text/css" rel="stylesheet" href="plugins/selectZtree/ztree/ztree.css"></link>
	<!-- 树形下拉框end -->
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
					
					<form action="changeerpxtbg/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="CHANGEERPXTBG_ID" id="CHANGEERPXTBG_ID" value="${pd.CHANGEERPXTBG_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">申请单号:</td>
								<td><input type="text" name="BILL_CODE" id="BILL_CODE" value="${pd.BILL_CODE}" maxlength="100" placeholder="这里输入申请单号" title="申请单号" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">单位简称:</td>
<%-- 								<td><input type="hidden" name="UNIT_CODE" id="UNIT_CODE" value="${pd.UNIT_CODE}" maxlength="30" placeholder="这里输入单位简称" title="单位简称" style="width:0%;"/></td> --%>
								<td><input type="hidden" name="UNIT_CODE" style="width:98%; id="UNIT_CODE" value="${pd.DEPARTMENT_ID}" />
										<div class="selectTree" id="selectTree"></div></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">部门简称:</td>
								<td><input type="text" name="DEPT_CODE" id="DEPT_CODE" value="${pd.DEPT_CODE}" maxlength="30" placeholder="这里输入部门简称" title="部门简称" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">填表日期:</td>
								<td><input type="text" name="ENTRY_DATE" id="ENTRY_DATE" value="${pd.ENTRY_DATE}" maxlength="30" placeholder="这里输入填表日期" title="填表日期" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">序号:</td>
								<td><input type="text" name="SERIAL_NUM" id="SERIAL_NUM" value="${pd.SERIAL_NUM}" maxlength="20" placeholder="这里输入序号" title="序号" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">申请人:</td>
								<td><input type="text" name="USER_CODE" id="USER_CODE" value="${pd.USER_CODE}" maxlength="20" placeholder="这里输入申请人" title="申请人" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">申请人部门:</td>
								<td><input type="text" name="USER_DEPT" id="USER_DEPT" value="${pd.USER_DEPT}" maxlength="30" placeholder="这里输入申请人部门" title="申请人部门" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">申请人岗位:</td>
								<td><input type="text" name="USER_JOB" id="USER_JOB" value="${pd.USER_JOB}" maxlength="50" placeholder="这里输入申请人岗位" title="申请人岗位" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">联系方式:</td>
								<td><input type="text" name="USER_CONTACT" id="USER_CONTACT" value="${pd.USER_CONTACT}" maxlength="50" placeholder="这里输入联系方式" title="联系方式" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">变更名称:</td>
								<td><input type="text" name="BG_NAME" id="BG_NAME" value="${pd.BG_NAME}" maxlength="50" placeholder="变更名称" title="变更名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">ERP系统名称:</td>
								<td><input type="text" name="SYSTEM" id="SYSTEM" value="${pd.SYSTEM}" maxlength="20" placeholder="这里输入ERP系统名称" title="ERP系统名称" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">变更类型:</td>
								<td><input type="text" name="BG_TYPE" id="BG_TYPE" value="${pd.BG_TYPE}" maxlength="100" placeholder="这里输入变更类型" title="变更类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">变更原因:</td>
								<td><input type="text" name="BG_REASON" id="BG_REASON" value="${pd.BG_REASON}" maxlength="300" placeholder="这里输入变更原因" title="变更原因" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">单据状态:</td>
								<td><input type="text" name="BILL_STATE" id="BILL_STATE" value="${pd.BILL_STATE}" maxlength="10" placeholder="这里输入单据状态" title="单据状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">创建人:</td>
								<td><input type="text" name="BILL_USER" id="BILL_USER" value="${pd.BILL_USER}" maxlength="20" placeholder="这里输入创建人" title="创建人" style="width:98%;"/></td>
								<td style="width:115px;text-align: right;padding-top: 13px;">创建日期:</td>
								<td><input type="text" name="BILL_DATE" id="BILL_DATE" value="${pd.BILL_DATE}" maxlength="30" placeholder="这里输入创建日期" title="创建日期" style="width:98%;"/></td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td style="width:115px;text-align: right;padding-top: 13px;">备注17:</td> -->
<%-- 								<td><input type="text" name="CUS_COLUMN1" id="CUS_COLUMN1" value="${pd.CUS_COLUMN1}" maxlength="30" placeholder="这里输入备注17" title="备注17" style="width:98%;"/></td> --%>
<!-- 								<td style="width:115px;text-align: right;padding-top: 13px;">备注18:</td> -->
<%-- 								<td><input type="text" name="CUS_COLUMN2" id="CUS_COLUMN2" value="${pd.CUS_COLUMN2}" maxlength="30" placeholder="这里输入备注18" title="备注18" style="width:98%;"/></td> --%>
<!-- 							</tr> -->
<!-- 							<tr> -->
<!-- 								<td style="width:115px;text-align: right;padding-top: 13px;">备注19:</td> -->
<%-- 								<td><input type="text" name="CUS_COLUMN3" id="CUS_COLUMN3" value="${pd.CUS_COLUMN3}" maxlength="30" placeholder="这里输入备注19" title="备注19" style="width:98%;"/></td> --%>
<!-- 								<td style="width:115px;text-align: right;padding-top: 13px;">备注20:</td> -->
<%-- 								<td><input type="text" name="CUS_COLUMN4" id="CUS_COLUMN4" value="${pd.CUS_COLUMN4}" maxlength="30" placeholder="这里输入备注20" title="备注20" style="width:98%;"/></td> --%>
<!-- 							</tr> -->
<!-- 							<tr> -->
<!-- 								<td style="width:115px;text-align: right;padding-top: 13px;">备注21:</td> -->
<%-- 								<td><input type="text" name="CUS_COLUMN5" id="CUS_COLUMN5" value="${pd.CUS_COLUMN5}" maxlength="30" placeholder="这里输入备注21" title="备注21" style="width:98%;"/></td> --%>
<!-- 							</tr> -->
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
		$(top.hangge());
		//保存
		function save(){
			if($("#BILL_CODE").val()==""){
				$("#BILL_CODE").tips({
					side:3,
		            msg:'请输入备注1',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BILL_CODE").focus();
			return false;
			}
			if($("#UNIT_CODE").val()==""){
				$("#UNIT_CODE").tips({
					side:3,
		            msg:'请输入备注2',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UNIT_CODE").focus();
			return false;
			}
			if($("#DEPT_CODE").val()==""){
				$("#DEPT_CODE").tips({
					side:3,
		            msg:'请输入备注3',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPT_CODE").focus();
			return false;
			}
			if($("#ENTRY_DATE").val()==""){
				$("#ENTRY_DATE").tips({
					side:3,
		            msg:'请输入备注4',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ENTRY_DATE").focus();
			return false;
			}
			if($("#SERIAL_NUM").val()==""){
				$("#SERIAL_NUM").tips({
					side:3,
		            msg:'请输入备注5',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SERIAL_NUM").focus();
			return false;
			}
			if($("#USER_CODE").val()==""){
				$("#USER_CODE").tips({
					side:3,
		            msg:'请输入备注6',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_CODE").focus();
			return false;
			}
			if($("#USER_DEPT").val()==""){
				$("#USER_DEPT").tips({
					side:3,
		            msg:'请输入备注7',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_DEPT").focus();
			return false;
			}
			if($("#USER_JOB").val()==""){
				$("#USER_JOB").tips({
					side:3,
		            msg:'请输入备注8',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_JOB").focus();
			return false;
			}
			if($("#USER_CONTACT").val()==""){
				$("#USER_CONTACT").tips({
					side:3,
		            msg:'请输入备注9',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_CONTACT").focus();
			return false;
			}
			if($("#BG_NAME").val()==""){
				$("#BG_NAME").tips({
					side:3,
		            msg:'请输入备注10',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BG_NAME").focus();
			return false;
			}
			if($("#SYSTEM").val()==""){
				$("#SYSTEM").tips({
					side:3,
		            msg:'请输入备注11',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SYSTEM").focus();
			return false;
			}
			if($("#BG_TYPE").val()==""){
				$("#BG_TYPE").tips({
					side:3,
		            msg:'请输入备注12',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BG_TYPE").focus();
			return false;
			}
			if($("#BG_REASON").val()==""){
				$("#BG_REASON").tips({
					side:3,
		            msg:'请输入备注13',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BG_REASON").focus();
			return false;
			}
			if($("#BILL_STATE").val()==""){
				$("#BILL_STATE").tips({
					side:3,
		            msg:'请输入备注14',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BILL_STATE").focus();
			return false;
			}
			if($("#BILL_USER").val()==""){
				$("#BILL_USER").tips({
					side:3,
		            msg:'请输入备注15',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BILL_USER").focus();
			return false;
			}
			if($("#BILL_DATE").val()==""){
				$("#BILL_DATE").tips({
					side:3,
		            msg:'请输入备注16',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BILL_DATE").focus();
			return false;
			}
			if($("#CUS_COLUMN1").val()==""){
				$("#CUS_COLUMN1").tips({
					side:3,
		            msg:'请输入备注17',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CUS_COLUMN1").focus();
			return false;
			}
			if($("#CUS_COLUMN2").val()==""){
				$("#CUS_COLUMN2").tips({
					side:3,
		            msg:'请输入备注18',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CUS_COLUMN2").focus();
			return false;
			}
			if($("#CUS_COLUMN3").val()==""){
				$("#CUS_COLUMN3").tips({
					side:3,
		            msg:'请输入备注19',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CUS_COLUMN3").focus();
			return false;
			}
			if($("#CUS_COLUMN4").val()==""){
				$("#CUS_COLUMN4").tips({
					side:3,
		            msg:'请输入备注20',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CUS_COLUMN4").focus();
			return false;
			}
			if($("#CUS_COLUMN5").val()==""){
				$("#CUS_COLUMN5").tips({
					side:3,
		            msg:'请输入备注21',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CUS_COLUMN5").focus();
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
		function initComplete(){
			//下拉树
			var zTreeNodes=${zTreeNodes};
			//console.log(zTreeNodes);
			var defaultNodes = {"treeNodes":zTreeNodes};
			//绑定change事件
			$("#selectTree").bind("change",function(){
				if(!$(this).attr("relValue")){
			    }else{
					//$("#UNIT_CODE").val($(this).attr("relValue"));
					$("#UNIT_CODE").val($(this).attr("relText"));
			    }
			});
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			$("#selectTree2_input").val("${null==depname?'请选择单位':depname}");
		}
		</script>
</body>
</html>