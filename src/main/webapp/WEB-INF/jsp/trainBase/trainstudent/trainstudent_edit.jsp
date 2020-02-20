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
	<link rel="stylesheet" type="text/css"
		href="plugins/selectZtree/import_fh.css" />
	<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
	<link type="text/css" rel="stylesheet"
		href="plugins/selectZtree/ztree/ztree.css"></link>
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
					
					<form action="trainstudent/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="STUDENT_ID" id="STUDENT_ID" value="${pd.STUDENT_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">学员编号:</td>
								<td><input type="text" name="STUDENT_CODE" id="STUDENT_CODE" value="${pd.STUDENT_CODE}" maxlength="20" placeholder="这里输入学员编号" title="学员编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">密码:</td>
								<td><input type="text" name="PASSWORD" id="PASSWORD" maxlength="20" placeholder="这里输入密码" title="密码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">学员名称:</td>
								<td><input type="text" name="STUDENT_NAME" id="STUDENT_NAME" value="${pd.STUDENT_NAME}" maxlength="30" placeholder="这里输入学员名称" title="学员名称" style="width:98%;"/></td>
							</tr>
							<%-- <tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">负责人账户:</td>
								<td>
									<select class="chosen-select form-control" name="ACCOUNT"
										id="ACCOUNT" data-placeholder="请选择负责人账户"
										style="vertical-align: top; height: 32px; width: 150px;">
											<option value="">请选择负责人账户</option>
											<c:forEach items="${userList}" var="user">
												<option value="${user.USER_ID}"
													<c:if test="${pd.ACCOUNT==user.USER_ID}">selected</c:if>>${user.USERNAME}</option>
											</c:forEach>
									</select>
								</td>
							</tr> --%>
							<%-- <tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">角色:</td>
								<td style="padding-top:13px;">
								<span>学员</span>
								<input name="ROLE_ID" type="hidden" value=“a8bcf49238da4d13a445d162a361be7a”>
									<select class="chosen-select form-control" name="ROLE_ID"
										id="ROLE_ID" data-placeholder="请选择角色"
										style="vertical-align: top; height: 32px; width: 150px;">
											<option value="">请选择角色</option>
											<c:forEach items="${roleList}" var="role">
												<option value="${role.ROLE_ID}"
													<c:if test="${pd.ROLE_ID==role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME}</option>
											</c:forEach>
									</select>
								</td>
							</tr> --%>
							
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">所属单位:</td>
								<td>
									<input type="hidden" name="UNIT_CODE" width="400px"
										id="UNIT_CODE" value="${pd.UNIT_CODE}" />
									<div class="selectTree" id="selectTree"></div>
								</td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">所属部门:</td>
								<td>
									<input type="hidden" name="DEPART_CODE" width="400px"
										id="DEPART_CODE" value="${pd.DEPART_CODE}" />
									<div class="selectTree" id="selectTree1"></div>
								</td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="100" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">状态:</td>
								<td>
									<select name="STATE" title="状态">
									<option value="1" <c:if test="${pd.STATE == '1' }">selected</c:if> >正常</option>
									<option value="0" <c:if test="${pd.STATE == '0' }">selected</c:if> >停用</option>
									</select>
								</td>
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
			
			if($("#STUDENT_CODE").val()==""){
				$("#STUDENT_CODE").tips({
					side:3,
		            msg:'请输入学员编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STUDENT_CODE").focus();
			return false;
			}
			if($("#PASSWORD").val()==""){
				$("#PASSWORD").tips({
					side:3,
		            msg:'请输入密码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PASSWORD").focus();
			return false;
			}
			if($("#STUDENT_NAME").val()==""){
				$("#STUDENT_NAME").tips({
					side:3,
		            msg:'请输入学员名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STUDENT_NAME").focus();
			return false;
			}
			/* if($("#ACCOUNT").val()==""){
				$("#ACCOUNT").tips({
					side:3,
		            msg:'请选择员工账户',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ACCOUNT").focus();
			return false;
			} */
			if($("#ROLE_ID").val()==""){
				$("#ROLE_ID").tips({
					side:3,
		            msg:'请选择角色',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ROLE_ID").focus();
			return false;
			}
			if($("#UNIT_CODE").val()==""){
				$("#UNIT_CODE").tips({
					side:3,
		            msg:'请选择所属单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UNIT_CODE").focus();
			return false;
			}
			if($("#DEPART_CODE").val()==""){
				$("#DEPART_CODE").tips({
					side:3,
		            msg:'请选择所属部门',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_CODE").focus();
			return false;
			}
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
			
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
			}
		});
		
		//下拉树
		var defaultNodes = {"treeNodes":${zTreeNodes}};
		function initComplete(){
			
			//绑定change事件
			$("#selectTree").bind("change",function(){
				if(!$(this).attr("relValue")){
			      //  top.Dialog.alert("没有选择节点");
			    }else{
					//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
					$("#UNIT_CODE").val($(this).attr("relValue"));
			    }
			});
			
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			$("#selectTree2_input").val("${null==unitName?'请选择所属单位':unitName}");
			
			
			//绑定change事件
			$("#selectTree1").bind("change",function(){
				if(!$(this).attr("relValue")){
			      //  top.Dialog.alert("没有选择节点");
			    }else{
					//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
					$("#DEPART_CODE").val($(this).attr("relValue"));
			    }
			});
			//赋给data属性
			$("#selectTree1").data("data",defaultNodes);  
			$("#selectTree1").render();
			$("#selectTree3_input").val("${null==departName?'请选择所属部门':departName}");
		}
		</script>
</body>
</html>