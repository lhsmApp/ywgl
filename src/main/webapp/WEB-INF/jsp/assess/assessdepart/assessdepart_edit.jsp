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
					
					<form action="assessdepart/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="DEPART_ID" id="DEPART_ID" value="${pd.DEPART_ID}"/>
						<%-- <input type="hidden" name="DEPART_CODE" id="DEPART_CODE" value="${pd.DEPART_CODE}"/> --%>
						<input type="hidden" name="DEPART_PARENT_CODE" id="DEPART_PARENT_CODE" value="${null == pd.DEPART_PARENT_CODE ? ASSESSDEPART_ID:pd.DEPART_PARENT_CODE}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">上级:</td>
								<td>
									<div class="col-xs-4 label label-lg label-light arrowed-in arrowed-right">
										<b>${null == pds.DEPART_NAME ?'(无) 此为顶级':pds.DEPART_NAME}</b>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位编码:</td>
								<td><input type="text" name="DEPART_CODE" id="DEPART_CODE" value="${pd.DEPART_CODE}" maxlength="100" placeholder="这里输入部门编码" title="部门编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">单位名称:</td>
								<td><input type="text" name="DEPART_NAME" id="DEPART_NAME" value="${pd.DEPART_NAME}" maxlength="100" placeholder="这里输入部门名称" title="部门名称" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">关联系统单位:</td>
								<td>
									<input type="hidden" name="LOCAL_DEPART_CODE" width="400px"
										id="LOCAL_DEPART_CODE" value="${pd.LOCAL_DEPART_CODE}" />
									<div class="selectTree" id="selectTree"></div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否是合并单位:</td>
								<td>
									<select name="IS_MERGE_DEPART" title="是否是合并单位">
										<option value="1" <c:if test="${pd.IS_MERGE_DEPART == '1' }">selected</c:if> >是</option>
										<option value="0" <c:if test="${pd.IS_MERGE_DEPART == '0' }">selected</c:if> >否</option>
									</select>
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
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
		$(top.hangge());
		//保存
		function save(){
			if($("#DEPART_CODE").val()==""){
				$("#DEPART_CODE").tips({
					side:3,
		            msg:'请输入单位编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_CODE").focus();
				return false;
			}
			
			if($("#DEPART_NAME").val()==""){
				$("#DEPART_NAME").tips({
					side:3,
		            msg:'请输入单位名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_NAME").focus();
				return false;
			}
			hasDepartCode();
			
		}
		
		//判断单位编码是否存在
		function hasDepartCode(){
			var departCode = $.trim($("#DEPART_CODE").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>assessdepart/hasDepartCode.do',
		    	data: {DEPART_ID:$("#DEPART_ID").val(),DEPART_CODE:departCode},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" != data.result){
						 $("#DEPART_CODE").tips({
								side:3,
					            msg:'单位编码 '+departCode+' 已存在',
					            bg:'#AE81FF',
					            time:3
					        });
						 
						 $("#DEPART_CODE").attr('tag','1');
					 }else{
						 $("#Form").submit();
						 $("#zhongxin").hide();
						 $("#zhongxin2").show();
					 }
				}
			});
		}
		
		//下拉树
		var defaultNodes = {"treeNodes":${zTreeNodes}};
		function initComplete(){
			
			//绑定change事件
			$("#selectTree").bind("change",function(){
				if(!$(this).attr("relValue")){
			      //  top.Dialog.alert("没有选择节点");
			    }else{
					//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
					$("#LOCAL_DEPART_CODE").val($(this).attr("relValue"));
			    }
			});
			
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			$("#selectTree2_input").val("${null==unitName?'请选择关联系统单位':unitName}");
			
		}
		</script>
</body>
</html>