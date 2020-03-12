<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.fh.util.Const"%>
<%@ page import="com.fh.entity.system.User"%>
<%@ page import="com.fh.util.Jurisdiction"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
    String userId=user.getUSER_ID();//用户ID 
    String userName=user.getNAME();//用户姓名
    String departId=user.getDEPARTMENT_ID();//部门ID
    String departName=user.getDEPARTMENT_NAME();//部门名称
    String unitCode=user.getUNIT_CODE();//单位ID
    String unitName=user.getUNIT_NAME();//单位名称
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
						<!-- 检索  -->
						<div class="row">
						    <div class="col-xs-12">
							    <div class="widget-box" >
								    <div class="widget-body">
									    <div class="widget-main">
										     <form action="changegrcqxbg/getPageList.do" >
								 		</form>
												<span class="input-icon pull-left" style="margin-right: 5px;">
													<input id="SelectedBillCode" class="nav-search-input" autocomplete="off" type="text" name="SelectedBillCode" value="${pd.keywords }" placeholder="请输入变更原因"> 
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>																			 
												<button type="button" class="btn btn-info btn-xs" onclick="tosearch();">
												    <i class="ace-icon fa fa-search bigger-110"></i>
												</button>									
									            <label class="btn btn-sm btn-danger " onclick="add()"> 
									    	          <i class="ace-icon fa  glyphicon-plus bigger-110"></i>新增
									            </label> 
									            <label id="editButton" class="btn btn-sm btn-primary" onclick="edit(bill_code)"> 
									            <i class="ace-icon fa fa-pencil-square-o bigger-110"></i>编辑
									            </label> 
									            <label id="delButton" class="btn btn-sm btn-success" onclick="del(bill_code)"> 	
									            <i class="ace-icon fa fa-trash-o bigger-110"></i>删除
									            </label>
									            <label class="btn btn-sm btn-purple" onclick="report(bill_code)"> 
<!-- 												        <span  class="bigger-110">上报</span>  -->
									    	    <i class="ace-icon fa fa-share bigger-110"></i>上报
									            </label>
									            <label class="btn btn-sm btn-warning" onclick="cancleReport(bill_code)">
										        <i class="ace-icon fa fa-undo bigger-110"></i>撤销上报
									            </label>
									             <label class="btn btn-sm btn-pink"onclick="printf(bill_code)">
									             <i class="ace-icon fa fa-print bigger-110"></i>
												打印
									            </label>										
										
									    </div>
								    </div>
							    </div>
						    </div>
						    
					    </div>	
				<div class="row">
					<div class="col-xs-4">												
						<ul id="tasks" class="item-list">
							
						</ul>		
						<div id="page" class="pagination" style="float: right;padding-top: 5px;margin-top: 0px;font-size:12px;"></div>
												
					</div>
					
					<div class="col-xs-8">
						<div class="widget-box transparent" id="recent-box">
							<div class="widget-header">
								<div style="height:25%">
									<ul class="steps">
									<li id="step1" data-step="1" class="active">
										<span class="step">1</span>
										<span class="title">发起</span>
									</li>
									<li id="step2"  data-step="2">
										<span class="step">2</span>
										<span class="title">审批中</span>
									</li>
									<li id="step3" data-step="3">
										<span class="step">3</span>
										<span class="title">完成</span>
									</li>
									</ul>
								</div>		
								<h4 class="widget-title lighter smaller">
									<i class="ace-icon fa fa-rss orange"></i>详情
								</h4>
		
								<div class="widget-toolbar no-border">
									<ul class="nav nav-tabs" id="grcqxbg-tab">
										<li class="active" tag="detail-tab">
											<a id="tabDetailButton" data-toggle="tab" href="#detail-tab">详情</a>
										</li>		
										<li  tag="report-tab">
											<a id="tabEditButton" data-toggle="tab" href="#report-tab">提报</a>
										</li>
									</ul>
								</div>
							</div>
		
							<div class="widget-body">
								<div class="widget-main padding-4">
									<div class="tab-content padding-8">
								
										<div id="detail-tab" class="tab-pane active">
											
										</div>
										<div id="report-tab" class="tab-pane">
										<div class="row">
											<div class="col-xs-5">									
												<input type="hidden" name="BILL_CODE" id="BILL_CODE" value="${pd.BILL_CODE }"/>
												<div id="zhongxin" style="padding-top: 13px;">
														<div style="margin:10px 0px;">
														<label for="form-field-jsbg-report-reason">变更原因</label>
														<input type="text" name="BG_REASON" id="BG_REASON" class="form-control" placeholder="请输入变更内容及原因"/>
													</div>
<!-- 													<div style="margin:10px 0px;"> -->
<!-- 														<label for="form-field-jsbg-report-depart">单位</label> -->
<!-- 														<input type="text" name="UNIT_CODE" id="UNIT_CODE" class="form-control" placeholder="请输入申请人单位"/> -->
<!-- 													</div> -->
<!-- 													<div style="margin:10px 0px;"> -->
<!-- 														<input type="hidden" name="UNIT_CODE" id="UNIT_CODE"   /> -->
<!-- 														<div class="selectTree" id="selectTree" style="float:none;display:block;"></div>												 -->
<!-- 													</div> -->
													<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-user">单位</label>
														<input type="hidden" name="UNIT_CODE" id="UNIT_CODE"/> 
														<input type="text" name="UNIT_NAME" id="UNIT_NAME" class="form-control" readonly="readonly" placeholder="请输入单位" /> 
													</div>
<!-- 													 <div style="margin:10px 0px;"> -->
<!-- 														<label for="form-field-xtbg-report-dept">部门</label> -->
<!-- 														<input type="hidden" name="DEPT_CODE" id="DEPT_CODE"/> -->
<!-- 														<input type="text" name="DEPT_NAME" id="DEPT_NAME" class="form-control" placeholder="请输入部门"/> -->
<!-- 													</div> -->
		 											<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-dept">部门</label>
														<select class="form-control" name="DEPT_CODE" id="DEPT_CODE">
															<option value=""></option>
															<c:forEach items="${userDeptList}" var="dept">
															<option value="${dept.DEPARTMENT_CODE}">${dept.NAME}</option>
															</c:forEach>
														</select>
													</div>
<!-- 													<div style="margin:10px 0px;"> -->
<!-- 														<label for="form-field-jsbg-report-user">申请人</label> -->
<!-- 															<select class="form-control" name="USER_CODE" id="USER_CODE"> -->
<!-- 																	<option value=""></option> -->
<%-- 																	<c:forEach items="${userList}" var="user"> --%>
<%-- 																	<option value="${user.USER_ID}">${user.USERNAME}</option> --%>
<%-- 																	</c:forEach> --%>
<!-- 																</select> -->
<!-- 													</div> -->
													<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-user">申请人</label>
														<input type="hidden" name="USER_CODE" id="USER_CODE"/> 
														<input type="text" name="USER_NAME" id="USER_NAME" class="form-control" readonly="readonly" placeholder="请输入申请人"/> 
													</div>
<!-- 												    <div style="margin:10px 0px;"> -->
<!-- 														<label for="form-field-jsbg-report-depart">申请人部门</label> -->
<!-- 														<input type="text" name="USER_DEPT" id="USER_DEPT" class="form-control" placeholder="请输入申请人单位"/> -->
<!-- 													</div> -->
													<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-depart">申请人部门</label>
														<input type="hidden" name="USER_DEPT" id="USER_DEPT" />
														<input type="text" name="USER_DEPTNAME" id="USER_DEPTNAME" class="form-control" readonly="readonly" placeholder="请输入申请人部门"/>
													</div>
										   			<div style="margin:10px 0px;">
														<label for="form-field-jsbg-report-job">申请人岗位</label>
														<input type="text" name="USER_JOB" id="USER_JOB" class="form-control" placeholder="请输入申请人部门"/>
													</div>
													<div style="margin:10px 0px;">
														<label for="form-field-jsbg-report-addrole">新增角色</label>
														<input type="text" name="ADD_ROLES" id="ADD_ROLES" class="form-control" placeholder="请输入新增角色"/>
													</div>
													<div style="margin:10px 0px;">
														<label for="form-field-jsbg-report-delrole">删除角色</label>
														<input type="text" name="DELETE_ROLES" id="DELETE_ROLES" class="form-control" placeholder="请输入新增角色"/>
													</div>
<!-- 													<div style="margin:10px 0px;"> -->
<!-- 														<label for="form-field-jsbg-report-contact">联系方式</label> -->
<!-- 														<input type="text" name="USER_CONTACT" id="USER_CONTACT" class="form-control" placeholder="请输入联系方式"/> -->
<!-- 													</div> -->
													<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-contact">Email</label>
														<input type="text" name="USER_EMAIL" id="USER_EMAIL" class="form-control" placeholder="请输入联系方式"/>
													</div>
													<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-contact">电话</label>
														<input type="text" name="USER_TEL" id="USER_TEL" class="form-control" placeholder="请输入联系方式"/>
													</div>
													<div style="margin:10px 0px;">
														<label for="form-field-select-1">变更预期时间</label>
<!-- 														<input type="text" name="EFFECTIVE_DATE" id="EFFECTIVE_DATE" class="form-control" placeholder="请输入联系方式"/> -->
													</div>
													<div style="margin:10px 0px;">
														<div class="input-group input-group-sm">
															<input type="text" id="EFFECTIVE_DATE" name="EFFECTIVE_DATE"  class="form-control"  data-date-format="yyyy-mm-dd" placeholder="请选择变更预期时间"/>
															<span class="input-group-addon">
																<i class="ace-icon fa fa-calendar" ></i>
															</span>
														</div>
													</div>
													<hr />
													<div>
														<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
														<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
													</div>		
												</div>											
											</div>
										</div>
									</div>
								</div>
							</div>	
					</div>			
				    </div>
			    </div>
		    </div>
	
		    <!-- 返回顶部 -->
		    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			    <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		    </a>
	    </div>
	 </div>
	</div>
</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
	    var bill_code=undefined;
	    var initUrl='<%=basePath%>changegrcqxbg/getPageList.do';
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			//日期框
			$("#EFFECTIVE_DATE" ).datepicker({
				showOtherMonths: true,
				selectOtherMonths: false,
				autoclose: true,
				todayHighlight: true
			});
			var data=${varList};
			showDetail(data[0].BILL_CODE);
		
			getChangrData();
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
				initList(initUrl);
				
			}
			
			
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		//保存
		function save(){
			top.jzts();
			//$("#xtbgForm").submit();

			var bgReason=$("#BG_REASON").val();//变更原因
			var unitCode=$("#UNIT_CODE").val();//单位
			var deptCode=$("#DEPT_CODE").val();//部门
			var userCode=$("#USER_CODE").val();//申请人
			var uesrDept=$("#USER_DEPT").val();//申请人部门
			var uesrJob=$("#USER_JOB").val();//申请人岗位
			var uesrContact=$("#USER_CONTACT").val();//联系方式
			var bgyqDate=$("#EFFECTIVE_DATE").val();//申请生效日期
			var billCode=$("#BILL_CODE").val();//申请单号
			var addRole=$("#ADD_ROLES").val();//新增角色
			var delRole=$("#DELETE_ROLES").val();//删除角色
			var uesrEmail=$("#USER_EMAIL").val();//Email
			var uesrTel=$("#USER_TEL").val();//电话
			if(bgReason==""||bgReason==null){    
		        alert('变更原因不能为空！');
		        $("#BG_REASON").focus();
		        $(top.hangge());//关闭加载状态
		        return ;
		    }
			if(unitCode==""||unitCode==null){    
		        alert('单位不能为空！');
		        $("#UNIT_CODE").focus();
		        $(top.hangge());//关闭加载状态
		        return ;
		    }
			if(userCode==""||userCode==null){    
		        alert('申请人不能为空！');
		        $("#USER_CODE").focus();
		        $(top.hangge());//关闭加载状态
		        return ;
		    }
			$.ajax({
				type: "POST",
				url: '<%=basePath%>changegrcqxbg/save.do',
				data:{USER_EMAIL:uesrEmail,USER_TEL:uesrTel,BG_REASON:bgReason,UNIT_CODE:unitCode,DEPT_CODE:deptCode,USER_CODE:userCode,USER_DEPT:uesrDept,USER_JOB:uesrJob,USER_CONTACT:uesrContact,EFFECTIVE_DATE:bgyqDate,BILL_CODE:billCode,ADD_ROLES:addRole,DELETE_ROLES:delRole},
		    	dataType:'json',
				cache: false,
				success: function(response){
					if(response.code==0){
						$(top.hangge());//关闭加载状态
						bootbox.dialog({
							message: "<span class='bigger-110'>保存成功</span>",
						});		
						initList(initUrl);
					}else{
						$(top.hangge());//关闭加载状态
						bootbox.dialog({
							message: "<span class='bigger-110'>保存失败</span>",
						});		
					}
				},
		    	error: function(response) {
		    		var msgObj=JSON.parse(response.responseText);
		    		$(top.hangge());//关闭加载状态
		    		bootbox.dialog({
						message: "<span class='bigger-110'>保存失败"+msgObj.message+"</span>",
					});		
		    	}
			});
			$("#grcqxbg-tab li[tag='detail-tab'] a").click();
		}
		//新增
		function add(){
			yesEdit();
			//点击新增按钮，弹到提报tab页
			$("#grcqxbg-tab li[tag='report-tab'] a").click();
			//新增清空文本框
			$("#BG_REASON").val("");//变更原因
			$("#UNIT_CODE").val('<%=unitCode%>');//单位编码
			$("#UNIT_NAME").val('<%=unitName%>');//单位名称
<%-- 			$("#selectTree2_input").val('<%=unitName%>');//单位名称 --%>
			$("#DEPT_CODE").val('<%=departId%>');//部门编码
			$("#DEPT_NAME").val('<%=departName%>');//部门名称	
<%-- 			$("#"+'<%=userId%>').val('<%=userName%>');//申请人 --%>
			$("#USER_CODE").val('<%=userId%>');//申请人编码
			$("#USER_NAME").val('<%=userName%>');//申请人姓名
			$("#USER_DEPT").val('<%=departId%>');//申请人部门编码
			$("#USER_DEPTNAME").val('<%=departName%>');//申请人部门名称
			$("#USER_JOB").val("");//申请人岗位
			$("#USER_CONTACT").val("");//联系方式
			$("#EFFECTIVE_DATE").val("");//申请生效日期
			$("#BILL_CODE").val("");//申请单号
			$("#ADD_ROLES").val("");//新增角色
			$("#DELETE_ROLES").val("");//删除角色
			$("#USER_EMAIL").val("");//Email
			$("#USER_TEL").val("");//电话
		}
		
		//删除
		function del(Id){
			if($("#step2").hasClass("active")){
				$("#delButton").tips({
					side : 3,
					msg : '该单据已上报，不能删除!',
					bg : '#AE81FF',
					time : 2
				});
				return false;
			}
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>changegrcqxbg/delete.do?BILL_CODE="+encodeURI(Id);
					$.get(url,function(data){
						bootbox.dialog({
							message: "<span class='bigger-110'>删除成功</span>",
						});		
						initList(initUrl);
						//nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			if($("#step2").hasClass("active")){
				$("#editButton").tips({
					side : 3,
					msg : '该单据已上报，不能修改!',
					bg : '#AE81FF',
					time : 2
				});
				return false;
			}
			//点击新增按钮，弹到提报tab页
			$("#grcqxbg-tab li[tag='report-tab'] a").click();
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>changegrcqxbg/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											//nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		};
		//console.log(${varList});
		var data=${varList};
		//循环加载到页面
		function getChangrData(){
		    var html = '';
		    for(var i = 0;i<data.length;i++){
		        html += setDiv(data[i]);
		    }
		    //document.getElementById("tasks").innerHTML = html;
			$('#tasks').html(html);
		}		
		//动态加载变更数据
		function setDiv(item){
		    var div = '<li  class="item-grey clearfix" onclick="showDetail(\''+item.BILL_CODE+'\');"><div><label class="inline" style="margin-bottom:5px;"><span class="list-item-value-title">'
		    + "GRC权限变更"
	        + '</span></label></div><div><label class="inline"><span class="list-item-info">单号:&nbsp;</span><span class="list-item-value">'
	        + item.BILL_CODE
	        +'</span></label><label class="inline pull-right"><span class="list-item-info">科室：</span><span class="list-item-value">'
	        + item.DEPT_NAME
	        +'</span></label></div><div><label class="inline"><span class="list-item-info">状态:&nbsp;</span><span class="list-item-value">'
	        + item.APPROVAL_STATE
	        +'</span></label><label class="inline pull-right"><span class="list-item-info">单位:&nbsp;</span><span class="list-item-value">'
	        + item.UNIT_NAME
	        +'</span></label></div><div><label class="inline"><span class="list-item-info">账号撤销原因:&nbsp;</span><span class="list-item-value">'
	        + item.BG_REASON
	        +'</span></label><label class="inline pull-right"><span class="list-item-info">申请日期:&nbsp;'
	        + item.ENTRY_DATE
	        +'</span></label></div></li>';
		    return div;
		}
		function showDetail(code){
			if(event){
				$("#tasks li").each(function(){
					var item = this;
					$(item).removeClass("bc-light-orange");
				}); 
				$($(event.srcElement).closest('li')).addClass("bc-light-orange");
			}else{
				$("#tasks li").first().addClass("bc-light-orange");
				//$($(event.srcElement).parents('li')).addClass("bc-light-orange");
			}
			  $.ajax({
				  	type: "POST",
		            //提交的网址
		           	url: '<%=basePath%>changegrcqxbg/showDetail.do?BILL_CODE='+code,		      
		            //返回数据的格式
		        	dataType:'json',		          
		            success:function(datas){
		            	$("#tabEditButton").show()
		            	//全局变量存放当前点击的变更申请单号
		            	bill_code=datas.BILL_CODE;
		      			
		            	$('#detail-tab').html(html);
		            	var html = '';
		      		     html += setDetail(datas);			          
		      			$('#detail-tab').html(html);
		      		 if(datas.APPROVAL_STATE==2)
		    			{
		    			$("#step1").removeClass('active');
		    			$("#step2").removeClass('active');
		    			$("#step3").removeClass('active');
		    			yesEdit();
		    			setValue(datas);
		    			}else if(datas.APPROVAL_STATE==0)//0为审批中
		    			{
		    				$("#tabDetailButton").click();
			    			$("#tabEditButton").hide()
		      			$("#step1").addClass('active');
		    			$("#step2").addClass('active');
		    			$("#step3").removeClass('active');
		    			noEdit();
		    			}else if (datas.APPROVAL_STATE==1)//1 为完成状态
						{
		    				$("#tabDetailButton").click();
			    			$("#tabEditButton").hide()
		      			$("#step1").addClass('active');
		    			$("#step2").addClass('active');
			    		$("#step3").addClass('active');
			    		noEdit();
		    			}else{
		    				$("#step1").addClass('active');
			    			$("#step2").removeClass('active');
			    			$("#step3").removeClass('active');
			    			yesEdit();
			    			setValue(datas);
		    			}
		            } 
		         });
		}
		//编辑文本框赋值
		function setValue(datas){
			$("#BILL_CODE").val(datas.BILL_CODE);//申请单号
			$("#BG_REASON").val(datas.BG_REASON);//变更原因
			$("#UNIT_CODE").val(datas.UNIT_CODE);//单位
// 			$("#selectTree2_input").val(datas.depnameUnit);			
			$("#UNIT_NAME").val(datas.UNIT_NAME);
			$("#DEPT_CODE").val(datas.DEPT_CODE);//部门
			$("#USER_CODE").val(datas.USER_CODE);//申请人
			$("#USER_NAME").val(datas.USERNAME);//申请人
			$("#USER_DEPT").val(datas.USER_DEPT);//申请人部门
			$("#USER_DEPTNAME").val(datas.USER_DEPTNAME);//申请人部门
			$("#USER_JOB").val(datas.USER_JOB);//申请人岗位
			$("#USER_CONTACT").val(datas.USER_CONTACT);//联系方式
			$("#EFFECTIVE_DATE").val(datas.EFFECTIVE_DATE);//申请生效日期
			$("#ADD_ROLES").val(datas.ADD_ROLES);//新增角色
			$("#DELETE_ROLES").val(datas.DELETE_ROLES);//删除角色
   			$("#USER_EMAIL").val(datas.USER_EMAIL);//Email
			$("#USER_TEL").val(datas.USER_TEL);//电话
		}
		//设置文本框不可编辑
		function noEdit(){
			$("#BILL_CODE").attr("disabled","disabled");//申请单号
			$("#BG_REASON").attr("disabled","disabled");//变更原因
			$("#UNIT_CODE").attr("disabled","disabled");//单位编码
// 			$("#selectTree2_input").attr("disabled","disabled");//单位名称
			$("#UNIT_NAME").attr("disabled","disabled");//单位名称
			$("#DEPT_CODE").attr("disabled","disabled");//创建人
			$("#USER_CODE").attr("disabled","disabled");//申请人单位
			$("#USER_NAME").attr("disabled","disabled");
			$("#USER_DEPT").attr("disabled","disabled");//申请部门
			$("#USER_DEPTNAME").attr("disabled","disabled");//申请人部门
			$("#USER_JOB").attr("disabled","disabled");//申请人岗位
			$("#USER_CONTACT").attr("disabled","disabled");//联系方式
			$("#EFFECTIVE_DATE").attr("disabled","disabled");//变更预期时间
			$("#ADD_ROLES").attr("disabled","disabled");
			$("#DELETE_ROLES").attr("disabled","disabled");
			$("#USER_EMAIL").attr("disabled","disabled");
			$("#USER_TEL").attr("disabled","disabled");//申请人部门
		
			
		}
		//设置文本框可编辑
		function yesEdit(){
			$("#BILL_CODE").removeAttr("disabled");//申请单号
			$("#BG_REASON").removeAttr("disabled");//变更原因
			$("#UNIT_CODE").removeAttr("disabled");//单位编码
			$("#UNIT_NAME").removeAttr("disabled");//单位名称
			$("#DEPT_CODE").removeAttr("disabled");//创建人
			$("#USER_CODE").removeAttr("disabled");//申请人单位
			$("#USER_NAME").removeAttr("disabled");
			$("#USER_DEPT").removeAttr("disabled");//申请部门
			$("#USER_DEPTNAME").removeAttr("disabled");//申请部门名称
			$("#USER_JOB").removeAttr("disabled");//申请人岗位
			$("#USER_CONTACT").removeAttr("disabled");//联系方式
			$("#EFFECTIVE_DATE").removeAttr("disabled");//变更预期时间
			$("#ADD_ROLES").removeAttr("disabled");
			$("#DELETE_ROLES").removeAttr("disabled");
			$("#USER_EMAIL").removeAttr("disabled");
			$("#USER_TEL").removeAttr("disabled");//申请人部门
						
		}
		//动态加载变更单详情
		function setDetail(item){
		    var div = '<div class="profile-user-info"><div class="profile-info-row"><div class="profile-info-name">问题：</div><div class="profile-info-value"><span>'
		    	  + "GRC权限变更"
			        + '</span></div></div><div class="profile-info-row"><div class="profile-info-name">申请单号：</div><div class="profile-info-value"><i class="fa fa-map-marker light-orange bigger-110"></i><span>'
			        + item.BILL_CODE		       
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请单位： </div><div class="profile-info-value"><span>'
			        + item.UNIT_NAME
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请部门： </div><div class="profile-info-value"><span>'
			        +item.DEPT_NAME
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 账号撤销原因 ：</div><div class="profile-info-value"><span>'
			        +item.BG_REASON
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 新增角色 ：</div><div class="profile-info-value"><span>'
			        +item.ADD_ROLES
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name">删除角色 ：</div><div class="profile-info-value"><span>'
			        +item.DELETE_ROLES
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请人部门： </div><div class="profile-info-value"><span>'
			        +item.USER_DEPTNAME
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请人： </div><div class="profile-info-value"><span>'
			        +item.USERNAME
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请人岗位： </div><div class="profile-info-value"><span>'
			        +item.USER_JOB
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 邮箱： </div><div class="profile-info-value"><span>'
			        +item.USER_EMAIL
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 电话： </div><div class="profile-info-value"><span>'
			        +item.USER_TEL
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请生效日期： </div><div class="profile-info-value"><span>'
			        +item.EFFECTIVE_DATE
			        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请日期： </div><div class="profile-info-value"><span>'
			        +item.ENTRY_DATE
			        +'</span></div></div>'
		    return div;	
		}	
		function printf1(){
			//具体需要跳转的地址
			var printURL = "PrintReport.jsp?title=" + encodeURIComponent("报表打印")
				+ "&report=" + encodeURIComponent("grf/erpSystemChange.grf")
				+ "&data=" + encodeURIComponent('<%=basePath%>changegrcqxbg/showDetail.do');
			//再新窗口打开这个打印页面
			window.open(printURL, '_blank');

		}		
		//修改
		function printf(Id){
			if(typeof Id== null || Id== "" || Id== undefined) {
				return;
			}else{
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="系统变更申请单打印";
				 diag.URL = '<%=basePath%>changegrcqxbg/printf.do?BILL_CODE='+encodeURI(Id);
				 diag.Width = 800;
				 diag.Height = 600;
				 diag.Modal = true;				//有无遮罩窗口
				 diag. ShowMaxButton = true;	//最大化按钮
			     diag.ShowMinButton = true;		//最小化按钮 
				 diag.show(); 
			}
				
		
		}
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>changegrcqxbg/excel.do';
		}
		function tosearch(){
			initList(initUrl);
		}
	
		//搜索
		function tosearch1(){
			$("#tasks li").remove(); 
			top.jzts();
			var keywords = $("#SelectedBillCode").val();
			$.ajax({
					type: "POST",
					url: '<%=basePath%>changegrcqxbg/getPageList.do',
			    	data: {keywords:keywords},
					dataType:'json',
					cache: false,
					success: function(data){
						if(data.length>0){
							$.each(data, function(i, item){
							    var html = '';
							        html += setDiv(item);
								$("#tasks").append(html);
						 	});
						}
						else{
							addEmpty();
						}
						top.hangge();
					}
			});
		}
		//上报
		function report(billCode){
			bootbox.confirm("确定要对单据"+billCode+"进行上报吗?", function(result) {
				if(result) {
				  $.ajax({
					  	type: "POST",
			            //提交的网址
			           	url: '<%=basePath%>approvalconfig/bgReport.do?BUSINESS_CODE=4&BILL_CODE='+encodeURI(billCode),		      
			            //返回数据的格式
			        	dataType:'json',		          
			            success:function(datas){
			            		bootbox.dialog({
									message: "<span class='bigger-110'>"+datas.msg+"</span>",
								});			            					            	
			            	initList(initUrl);
			            }
			         });
				}
			});
				 
		}
		//撤销上报
		function cancleReport(billCode){
			bootbox.confirm("确定要对单据"+billCode+"撤销上报吗?", function(result) {
				if(result) {
// 					top.jzts();
					 $.ajax({
						  	type: "POST",
				            //提交的网址
				           	url: '<%=basePath%>approvalconfig/cancleReport.do?BILL_CODE='+encodeURI(billCode),		      
				            //返回数据的格式
				        	dataType:'json',		          
				            success:function(datas){	
				            	bootbox.dialog({
									message: "<span class='bigger-110'>"+datas.msg+"</span>",
								});					            					            	
				            	initList(initUrl);
				            }
				
				         });
				}
			});
				 
		}
		
		/**
		 * 初始化列表信息
		 */
		function initList(initUrl){
			$("#tasks li").remove(); 
			top.jzts();
			var keywords = $("#SelectedBillCode").val();
			$.ajax({
					type: "POST",
					url: initUrl,
			    	data: {keywords:keywords},
					dataType:'json',
					cache: false,
					success: function(data){
						$("#page").html(data.pageHtml);
						if(data.rows){
							$.each(data.rows, function(i, item){
							    var html = '';
							        html += setDiv(item);
								$("#tasks").append(html);
						 	});
						}
						else{
							addEmpty();
						}
						top.hangge();
					}
			});
		}
		
		/**
		 * 增加空数据提示
		 */
		function addEmpty(){
			var htmlEmpty='<li class="item-grey clearfix">'
				+'<div>'
					+'<label class="inline" style="margin-bottom:5px;">'
						+'<span class="list-item-value-title">没有相关数据</span>'
					+'</label>'
				+'<div>'
			+'</li>';
			$("#tasks").append(htmlEmpty);
		}
		function initComplete(){
			//下拉树
			var defaultNodes = {"treeNodes":${zTreeNodes}};
			//绑定change事件
			$("#selectTree").bind("change",function(){

				if(!$(this).attr("relValue")){
			    }else{
					$("#UNIT_CODE").val($(this).attr("relValue"));	
			    }
				 //清空select框中数据
				   $('#USER_CODE').empty();
				$.ajax({
					   type: "POST",
					   url: '<%=basePath%>changeerpxtbg/getUsers.do',
					   data: {'UNIT_CODE':$("#UNIT_CODE").val()},
					   dataType:'json',
					   cache: false,
					   success: function (data) {
					           $('#USER_CODE').append("<option value='0'>--请选择申请人--</option>");
					            //遍历成功返回的数据
					            $.each(data, function (index,item) {
					                var userName = data[index].NAME;
					                var userId = data[index].USER_ID;
					                //构造动态option
					                $('#USER_CODE').append("<option value='"+userId+"'>"+userName+"</option>")
					             });
					    },
					    error: function () {

					    }
					  });

			});
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
		}		
	</script>


</body>
</html>