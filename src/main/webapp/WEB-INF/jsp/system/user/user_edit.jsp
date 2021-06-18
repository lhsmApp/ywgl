<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
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
								<form action="user/${msg }.do" name="userForm" id="userForm" method="post">
									<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<c:if test="${fx != 'head'}">
											<tr>
												<td style="width:79px;text-align: right;padding-top: 13px;">角色:</td>
												<td id="juese">
												<select class="chosen-select form-control" name="ROLE_ID" id="role_id" data-placeholder="请选择角色" style="vertical-align:top;" style="width:98%;" >
													<c:if test="${pd.ROLE_TYPE  == 'gly'}">
														<option value=""></option>
														<c:forEach items="${roleList}" var="role">
															<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
														</c:forEach>
													</c:if>
													<c:if test="${pd.ROLE_TYPE  == 'ejdwedit'}">	
														<option value=""></option>
														<c:forEach items="${roleList}" var="role">
															<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
														</c:forEach>
													</c:if>
														<c:if test="${pd.ROLE_TYPE  == 'ejdwadd'}">					
															<option value="a8bcf49238da4d13a445d162a361be7a" <c:if test="${role.ROLE_ID == 'a8bcf49238da4d13a445d162a361be7a' }">selected</c:if>>ERP普通用户（临时）</option>
													</c:if>
												</select>
												</td>
											</tr>
											<tr>
												<td style="width:79px;text-align: right;padding-top: 13px;">单位:</td>
												<td>
													<input type="hidden" name="UNIT_CODE" width="400px"
														id="UNIT_CODE" value="${pd.UNIT_CODE}" />
													<div <c:if test="${pd.ADD_QX <= '2' }">disabled</c:if>  class="selectTree" id="selectTreeUnit"></div>
												</td>
											</tr>
											<tr>
												<td style="width:79px;text-align: right;padding-top: 13px;">部门:</td>
												<td>
													<input type="hidden" name="DEPARTMENT_ID" width="400px"
														id="DEPARTMENT_ID" value="${pd.DEPARTMENT_ID}" />
													<div class="selectTree" id="selectTree" style="float:none;display:block;"></div>
												</td>
											</tr>
										</c:if>
										
										<c:if test="${fx == 'head'}">
											<input name="ROLE_ID" id="role_id" value="${pd.ROLE_ID }" type="hidden" />
											<input name="UNIT_CODE" id="department_id" value="${pd.UNIT_CODE }" type="hidden" />
											<input name="DEPARTMENT_ID" id="department_id" value="${pd.DEPARTMENT_ID }" type="hidden" />
										</c:if>
										<tr>
<!-- 											<td style="width:79px;text-align: right;padding-top: 13px;">用户名:</td> -->
<%-- 											<td><input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }" maxlength="32" placeholder="这里输入用户名(员工编号)" title="用户名" onblur="hasU('${pd.USER_ID }')" style="width:98%;"/></td> --%>
<!-- 										</tr> -->
												<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">用户名:</td>
											<td><input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }" maxlength="8" placeholder="这里输入用户名(员工编号)" title="用户名" onblur="hasU('${pd.USER_ID }')" style="width:98%;"/></td>
										</tr>
										<%-- <tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">编号:</td>
											<td><input type="text" name="NUMBER" id="NUMBER" value="${pd.NUMBER }" maxlength="32" placeholder="这里输入编号" title="编号" onblur="hasN('${pd.USERNAME }')" style="width:98%;"/></td>
										</tr> --%>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">密码:</td>
											<td><input type="password" name="PASSWORD" id="password"  maxlength="32" placeholder="输入密码" title="密码" value="erp123" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">确认密码:</td>
											<td><input type="password" name="chkpwd" id="chkpwd"  maxlength="32" placeholder="确认密码" title="确认密码" value="erp123"  style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">姓名:</td>
											<td><input type="text" name="NAME" id="name"  value="${pd.NAME }"  maxlength="32" placeholder="这里输入姓名" title="姓名" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">办公电话:</td>
											<td><input type="text" name="WORK_PHONE" id="WORK_PHONE"  value="${pd.WORK_PHONE }"  maxlength="32" placeholder="这里输入办公电话" title="手机号" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">手机号:</td>
											<td><input type="number" name="PHONE" id="PHONE"  value="${pd.PHONE }"  maxlength="32" placeholder="这里输入手机号" title="手机号" style="width:98%;"/></td>
										</tr>
										<%-- <tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">邮箱:</td>
											<td><input type="email" name="EMAIL" id="EMAIL"  value="${pd.EMAIL }" maxlength="32" placeholder="这里输入邮箱" title="邮箱" onblur="hasE('${pd.USERNAME }')" style="width:98%;"/></td>
										</tr> --%>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
											<td><input type="text" name="BZ" id="BZ"value="${pd.BZ }" placeholder="这里输入备注" maxlength="64" title="备注" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">状态:</td>
											<td>
												<select name="STATUS" title="状态">
												<option value="1" <c:if test="${pd.STATUS == '1' }">selected</c:if> >正常</option>
												<option value="0" <c:if test="${pd.STATUS == '0' }">selected</c:if> >停用</option>
												</select>
											</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">帐号类别:</td>
<%-- 											<td><input type="text" name="USER_PROPERTY" id="USER_PROPERTY"  value="${pd.USER_PROPERTY }"  maxlength="32" placeholder="这里输入帐号类别" title="手机号" style="width:98%;" readonly /></td> --%>
											<td>
												<select name="USER_PROPERTY" title="帐号类别" disabled="disabled">
												<option value="1" <c:if test="${pd.USER_PROPERTY == '1' }">selected</c:if> >SAP用户</option>
												<option value="0" <c:if test="${pd.USER_PROPERTY == '0' }">selected</c:if> >非SAP用户</option>
												<option value="0" <c:if test="${pd.USER_PROPERTY == null|| pd.USER_PROPERTY ==''}">selected</c:if> >未维护</option>
												</select>
											</td>
										</tr>
<!-- 										<tr> -->
<!-- 											<td style="width:79px;text-align: right;padding-top: 13px;"></td> -->
<!-- 											<td style="width:79px;text-align: left;padding-top: 13px;"> -->
<!-- 												<input name="USER_PROPERTY" id="USER_PROPERTY" type="checkbox" class="ace" readonly="readonly" /> -->
												
<!-- 											<span class="lbl">是否SAP账号</span> -->
<!-- 											</td> -->
	
<!-- 										</tr> -->
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
												<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
											</td>
										</tr>
									</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
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
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	if("${pd.USER_PROPERTY}"=="1"){
		$('#USER_PROPERTY').prop('checked','checked');
		$('#USER_PROPERTY').val('1');
	}
	if("${pd.USER_PROPERTY}"=="0"){
		$('#USER_PROPERTY').removeAttr("checked");
		$('#USER_PROPERTY').val('0');
	}
	//保存
	function save(){
		var idValue =$("#loginname").val();
        var reg = new RegExp(/^\d{8}$/);   //用户名必须是8位数字
        if(!reg.test(idValue)) {
        	$("#loginname").tips({
				side:3,
	            msg:'用户名为员工编号,必须为8位数字！',
	            bg:'#AE81FF',
	            time:2
	        });
            return;
        }
// 		if($("#role_id").val()==""){
// 			$("#juese").tips({
// 				side:3,
// 	            msg:'选择角色',
// 	            bg:'#AE81FF',
// 	            time:2
// 	        });
// 			$("#role_id").focus();
// 			return false;
// 		}

		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			$("#loginname").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}
		
		/* if($("#NUMBER").val()==""){
			$("#NUMBER").tips({
				side:3,
	            msg:'输入编号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#NUMBER").focus();
			return false;
		}else{
			$("#NUMBER").val($.trim($("#NUMBER").val()));
		} */
		if($("#user_id").val()=="" && $("#password").val()==""){
			$("#password").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#password").focus();
			return false;
		}
		if($("#password").val()!=$("#chkpwd").val()){
			
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#chkpwd").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		if($("#department_id").val()==""){
			$("#department_id").tips({
				side:3,
	            msg:'输入单位',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		if($("#WORK_PHONE").val()==""){
			$("#WORK_PHONE").tips({
				side:3,
	            msg:'输入办公电话',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#WORK_PHONE").focus();
			return false;
		}
		/*var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		 if($("#PHONE").val()==""){
			
			$("#PHONE").tips({
				side:3,
	            msg:'输入手机号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		} else if($("#PHONE").val().length != 11 && !myreg.test($("#PHONE").val())){
			$("#PHONE").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		}*/
		/* if($("#EMAIL").val()==""){
			
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		} */
		/* if($("#user_id").val()==""){
			hasU();
		}else{ */
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		/* } */
	}
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
		}
	
	//判断用户名是否存在
	function hasU(userID){
		var USERNAME = $.trim($("#loginname").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasU.do',
	    	data: {USERNAME:USERNAME,USER_ID:userID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 /* if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#loginname").css("background-color","#D16E6C");
					setTimeout("$('#loginname').val('此用户名已存在!')",500);
				 } */
				if("success" != data.result){
					 $("#loginname").tips({
							side:3,
				            msg:'用户名 '+USERNAME+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#loginname").val('');
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $.trim($("#EMAIL").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱 '+EMAIL+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#EMAIL").val('');
				 }
			}
		});
	}
	 $('#USER_PROPERTY').on('change',function(e){        
		 e.preventDefault(); 
		 if ($('#USER_PROPERTY').attr('checked')) {
			   	this.value=1;
			}else{
				this.value=0;
			}
	  });
	//判断编码是否存在
	function hasN(USERNAME){
		var NUMBER = $.trim($("#NUMBER").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasN.do',
	    	data: {NUMBER:NUMBER,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#NUMBER").tips({
							side:3,
				            msg:'编号 '+NUMBER+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#NUMBER").val('');
				 }
			}
		});
	}
	$(function() {
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
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		}
	});
	
	//下拉树
	var defaultNodes = {"treeNodes":${zTreeNodes}};
	function initComplete(){
		
		//绑定change事件
		$("#selectTreeUnit").bind("change",function(){
			if(!$(this).attr("relValue")){
		      //  top.Dialog.alert("没有选择节点");
		    }else{
				//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
				$("#UNIT_CODE").val($(this).attr("relValue"));
		    }
		});
		//赋给data属性
		$("#selectTreeUnit").data("data",defaultNodes);  
		$("#selectTreeUnit").render();
		$("#selectTree2_input").val("${null==depnameUnit?'请选择单位':depnameUnit}");
		
		
		//绑定change事件
		$("#selectTree").bind("change",function(){
			if(!$(this).attr("relValue")){
		      //  top.Dialog.alert("没有选择节点");
		    }else{
				//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
				$("#DEPARTMENT_ID").val($(this).attr("relValue"));
		    }
		});
		//赋给data属性
		$("#selectTree").data("data",defaultNodes);  
		$("#selectTree").render();
		$("#selectTree3_input").val("${null==depname?'请选择部门':depname}");
	}
</script>
</html>