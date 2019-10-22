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
					
					<form action="problemtype/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PRO_TYPE_ID" id="PRO_TYPE_ID" value="${pd.PRO_TYPE_ID}"/>
						<input type="hidden" name="PRO_TYPE_PARENT_ID" id="PRO_TYPE_PARENT_ID" value="${null == pd.PRO_TYPE_PARENT_ID ? PROBLEMTYPE_ID:pd.PRO_TYPE_PARENT_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">上级:</td>
								<td>
									<div class="col-xs-4 label label-lg label-light arrowed-in arrowed-right">
										<b>${null == pds.PRO_TYPE_NAME ?'(无) 此为顶级':pds.PRO_TYPE_NAME}</b>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">问题类型名称:</td>
								<td><input type="text" name="PRO_TYPE_NAME" id="PRO_TYPE_NAME" value="${pd.PRO_TYPE_NAME}" maxlength="100" placeholder="这里输入问题类型名称" title="问题类型名称" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属部门:</td>
								<td>
									<input type="hidden" name="DEPART_CODE" width="400px"
										id="DEPART_CODE" value="${pd.DEPART_CODE}" />
										<div class="selectTree" id="selectTree"></div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">激活状态:</td>
								<td>
									<select name="STATE" title="状态">
										<option value="1" <c:if test="${pd.STATE == '1' }">selected</c:if> >正常</option>
										<option value="0" <c:if test="${pd.STATE == '0' }">selected</c:if> >停用</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">问题描述:</td>
								<td>
									
									<textarea rows="2" cols="46" name="PRO_TYPE_CONTENT" id="PRO_TYPE_CONTENT" placeholder="这里输入问题描述" title="问题描述"  style="width:98%;">${pd.PRO_TYPE_CONTENT}</textarea>
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
			if($("#PRO_TYPE_NAME").val()==""){
				$("#PRO_TYPE_NAME").tips({
					side:3,
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRO_TYPE_NAME").focus();
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
		
		//下拉树
		var defaultNodes = {"treeNodes":${zTreeNodes}};
		function initComplete(){
			
			//绑定change事件
			$("#selectTree").bind("change",function(){
				if(!$(this).attr("relValue")){
			      //  top.Dialog.alert("没有选择节点");
			    }else{
					//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
					$("#DEPART_CODE").val($(this).attr("relValue"));
			    }
			});
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			$("#selectTree2_input").val("${null==proTypeName?'请选择所属部门':proTypeName}");
		}
		</script>
</body>
</html>