﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String user=request.getUserPrincipal().getName();
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
						<!-- 检索  -->
						<div class="row">
						    <div class="col-xs-12">
							    <div class="widget-box" >
								    <div class="widget-body">
									    <div class="widget-main">
<!-- 									    <label class="pull-left" style="padding: 5px;">选择业务类型：</label> -->
<!-- 										    <span class="pull-left" style="margin-right: 5px;">  -->
<!-- 												<select -->
<!-- 													class="chosen-select form-control" name="BUSINESS_TYPE" -->
<!-- 													id=BUSINESS_TYPE data-placeholder="请选择业务类型" -->
<!-- 													style="vertical-align: top; height: 32px; width: 150px;"> -->
<!-- 														<option  value="1">系统变更</option> -->
<!-- 														<option  value="2">角色变更</option> -->
<!-- 														<option  value="3">GRC帐号新增</option> -->
<!-- 														<option  value="4">GRC权限变更</option> -->
<!-- 														<option  value="5">GRC帐号撤销</option> -->
<!-- 												</select> -->
<!-- 											</span>		 -->
								<div class="btn-group" data-toggle="buttons" >
									<label class="btn btn-info">
									<input type="radio" class="level_select"  name="level_select" value="1">系统变更
									</label>
									<label class="btn btn-info">
									<input type="radio" class="level_select" name="level_select" value="2">角色变更
									</label>
									<label class="btn btn-info">
									<input type="radio" class="level_select" name="level_select" value="3">GRC帐号新增
									</label>
									<label class="btn btn-info">
									<input type="radio" class="level_select" name="level_select" value="4">GRC权限变更
									</label>
									<label class="btn btn-info">
									<input type="radio" class="level_select" name="level_select" value="5">GRC帐号撤销
									</label>
									</div> 		
												<span class="input-icon pull-left" style="margin-right: 5px;">
													<input id="SelectedBillCode" class="nav-search-input" autocomplete="off" type="text" name="keywords" value="${pd.keywords }" placeholder="在已有申请中搜索"> 
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>		
																																							 
												<button type="button" class="btn btn-info btn-sm" onclick="tosearch();">
												    <i class="ace-icon fa fa-search bigger-110"></i>
												</button>																			       
												            <label class="btn btn-sm btn-primary"onclick="passApproval()"> 
<!-- 												        <span  class="bigger-110">上报</span>  -->
												    	    <i class="ace-icon fa fa-share bigger-110"></i>审批通过
												            </label>
												            <label class="btn btn-sm btn-primary" onclick="returnApproval()">
													        <i class="ace-icon fa fa-undo bigger-110"></i>审批退回
												            </label>
															
									    </div>
								    </div>
							    </div>
						    </div>
						    
					    </div>	
					<div class="row">
						<div class="col-xs-4">
						<!-- 检索  -->
						<form style="margin:5px;" action="user/listUsers.do" method="post" name="userForm" id="userForm">														
							<ul id="tasks" class="item-list">
							
							</ul>						
	
				
						</form>
					</div>
					
					<div class="col-xs-8">
						<div class="widget-box transparent" id="recent-box">
							<div class="widget-header">
								<div style="height:25%">
									<ul  id="approvalProcess" class="steps">
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
		
<!-- 								<div class="widget-toolbar no-border"> -->
<!-- 									<ul class="nav nav-tabs" id="recent-tab"> -->
<!-- 										<li class="active"> -->
<!-- 											<a data-toggle="tab" href="#detail-tab">详情</a> -->
<!-- 										</li> -->
		
<!-- 										<li> -->
<!-- 											<a data-toggle="tab" href="#assigh-tab">分配</a> -->
<!-- 										</li> -->
		
<!-- 										<li> -->
<!-- 											<a data-toggle="tab" href="#comment-tab">关闭</a> -->
<!-- 										</li> -->
<!-- 									</ul> -->
<!-- 								</div> -->
							</div>
		
							<div class="widget-body">
								<div class="widget-main padding-4">
									<div class="tab-content padding-8">
										<div id="assigh-tab" class="tab-pane">
											<!-- <h4 class="smaller lighter green">
												<i class="ace-icon fa fa-list"></i>
												Sortable Lists
											</h4> -->
											<form action="problemAssign/savaProblemAssign.do" name="problemAssignForm" id="problemAssignForm" method="post">
												<input type="hidden" name="problemID" id="problemID" value="${pd.PROBLEM_ID }"/>
												<div id="zhongxin" style="padding-top: 13px;">
													<div style="margin:10px 0px;">
														<label for="form-field-select-1">受理人</label>
														<select class="form-control" id="form-field-select-1">
															<option value=""></option>
															<option value="AL">Alabama</option>
															<option value="AK">Alaska</option>
														</select>
													</div>
													
													<div style="margin:10px 0px;">
														<label for="form-field-select-2">提问系统</label>
														<select class="form-control" id="form-field-select-2">
															<option value=""></option>
															<option value="AL">ERP系统</option>
															<option value="AK">财务系统</option>
														</select>
													</div>
													
													<div style="margin:10px 0px;">
														<label for="form-field-select-3">优先级</label>
														<select class="form-control" id="form-field-select-3">
															<option value=""></option>
															<option value="AL">紧急</option>
															<option value="AK">一般</option>
														</select>
													</div>
													<%-- <input type="number" name="PHONE" id="PHONE"  value="${pd.PHONE }"  maxlength="32" placeholder="这里输入手机号" title="手机号" style="width:98%;"/> --%>
													<div style="margin:20px 0px;">
														<span>分配人：</span><span>张三</span>
														<span style="margin-left:30px;">分配时间：</span><span>2019-05-23</span>
													</div>
													<hr />
													<div>
														<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
														<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
													</div>		
												</div>
												<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
											</form>
										</div>
										<div id="detail-tab" class="tab-pane active">
											
										</div>
									</div>
								</div>
							</div>	
					</div>			
 					<div class="row">
						    <div class="col-xs-12">
						        <table id="jqGridBase"></table>
						        <div id="jqGridBasePager"></div>
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
	    var bill_code=undefined;//系统变更单号码
	    var current_level=undefined;//当前审批级别
	    varnext_level=undefined;//下一审批级别	    
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		

		$(function() {
			var data=${varList};
			showDetail(data[0].BILL_CODE,data[0].CURRENT_LEVEL,data[0].NEXT_LEVEL);
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			getChangeData();
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
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>changeerpxtbg/goAdd.do';
			 diag.Width = 750;
			 diag.Height = 455;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 //nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>changeerpxtbg/delete.do?BILL_CODE="+encodeURI(Id);
					$.get(url,function(data){
						console.log(data);
						//nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>changeerpxtbg/goEdit.do?BILL_CODE='+encodeURI(Id);
			 diag.Width = 750;
			 diag.Height = 455;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 //nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
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
								url: '<%=basePath%>changeerpxtbg/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		};
		var data=${varList};
		//循环加载到页面
		function getChangeData(){
		    var html = '';
		    for(var i = 0;i<data.length;i++){
		        html += setDiv1(data[i]);
		    }
		    //document.getElementById("tasks").innerHTML = html;
			$('#tasks').html(html);
		}		
		//动态加载变更数据
		function setDiv1(item){
		    var div = '<li  class="item-grey clearfix" onclick="showDetail(\''+item.BILL_CODE+'\',\''+item.CURRENT_LEVEL+'\',\''+item.NEXT_LEVEL+'\');"><div><label class="inline" style="margin-bottom:5px;"><span class="list-item-value-title">'
		        + item.BG_NAME
		        + '</span></label></div><div><label class="inline"><span class="list-item-info">单号:&nbsp;</span><span class="list-item-value">'
		        + item.BILL_CODE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">科室：</span><span class="list-item-value">'
		        + item.DEPT_CODE
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">状态:&nbsp;</span><span class="list-item-value">'
		        + item.APPROVAL_STATE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">单位:&nbsp;</span><span class="list-item-value">'
		        + item.UNIT_CODE
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">变更原因:&nbsp;</span><span class="list-item-value">'
		        + item.BG_REASON
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">申请日期:&nbsp;'
		        + item.ENTRY_DATE
		        +'</span></label></div></li>';
		    return div;
		}
		//GRC帐号新增
		function setDiv3(item){
		    var div = '<li  class="item-grey clearfix" onclick="showDetail(\''+item.BILL_CODE+'\',\''+item.CURRENT_LEVEL+'\',\''+item.NEXT_LEVEL+'\');"><div><label class="inline" style="margin-bottom:5px;"><span class="list-item-value-title">'
		        + "GRC帐号新增"
		        + '</span></label></div><div><label class="inline"><span class="list-item-info">单号:&nbsp;</span><span class="list-item-value">'
		        + item.BILL_CODE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">科室：</span><span class="list-item-value">'
		        + item.DEPT_CODE
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">状态:&nbsp;</span><span class="list-item-value">'
		        + item.APPROVAL_STATE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">单位:&nbsp;</span><span class="list-item-value">'
		        + item.UNIT_CODE
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">新增帐号原因:&nbsp;</span><span class="list-item-value">'
		        + item.ACCOUNT_REASON
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">申请日期:&nbsp;'
		        + item.ENTRY_DATE
		        +'</span></label></div></li>';
		    return div;
		}
		function setDiv4(item){
		    var div = '<li  class="item-grey clearfix" onclick="showDetail(\''+item.BILL_CODE+'\',\''+item.CURRENT_LEVEL+'\',\''+item.NEXT_LEVEL+'\');"><div><label class="inline" style="margin-bottom:5px;"><span class="list-item-value-title">'
		    + "GRC权限变更"
	        + '</span></label></div><div><label class="inline"><span class="list-item-info">单号:&nbsp;</span><span class="list-item-value">'
	        + item.BILL_CODE
	        +'</span></label><label class="inline pull-right"><span class="list-item-info">科室：</span><span class="list-item-value">'
	        + item.DEPT_CODE
	        +'</span></label></div><div><label class="inline"><span class="list-item-info">状态:&nbsp;</span><span class="list-item-value">'
	        + item.APPROVAL_STATE
	        +'</span></label><label class="inline pull-right"><span class="list-item-info">单位:&nbsp;</span><span class="list-item-value">'
	        + item.UNIT_CODE
	        +'</span></label></div><div><label class="inline"><span class="list-item-info">账号撤销原因:&nbsp;</span><span class="list-item-value">'
	        + item.BG_REASON
	        +'</span></label><label class="inline pull-right"><span class="list-item-info">申请日期:&nbsp;'
	        + item.ENTRY_DATE
	        +'</span></label></div></li>';
		    return div;
		}
		function setDiv5(item){
		    var div = '<li  class="item-grey clearfix" onclick="showDetail(\''+item.BILL_CODE+'\',\''+item.CURRENT_LEVEL+'\',\''+item.NEXT_LEVEL+'\');"><div><label class="inline" style="margin-bottom:5px;"><span class="list-item-value-title">'
		        + "账号撤销"
		        + '</span></label></div><div><label class="inline"><span class="list-item-info">单号:&nbsp;</span><span class="list-item-value">'
		        + item.BILL_CODE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">科室：</span><span class="list-item-value">'
		        + item.DEPT_CODE
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">状态:&nbsp;</span><span class="list-item-value">'
		        + item.APPROVAL_STATE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">单位:&nbsp;</span><span class="list-item-value">'
		        + item.UNIT_CODE
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">账号撤销原因:&nbsp;</span><span class="list-item-value">'
		        + item.CANCLE_REASON
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">申请日期:&nbsp;'
		        + item.ENTRY_DATE
		        +'</span></label></div></li>';
		    return div;
		}
		function showDetail(code,current,next){
			bill_code=code;
		 	current_level=current;//当前审批级别
		    next_level=next;//下一审批级别	 
// 			var businessType= $("#BUSINESS_TYPE").val();//业务类型
            var businessType= $(".level_select:checked").val();
            if(null==businessType||businessType==''){
            	businessType='1';
            }
			  $.ajax({
				  	type: "POST",
		            //提交的网址
		           	url: '<%=basePath%>approvalconfig/showDetail.do?BILL_CODE='+code+'&BUSINESS_CODE='+businessType,		      
		            //返回数据的格式
		        	dataType:'json',		          
		            success:function(datas){
		            	console.log(datas);
		            	var html = '';
		      		     html += setDetail(datas);
		      			$('#detail-tab').html(html);
		      		    if(datas.APPROVAL_STATE==2)
		    			{
		    			$("#step1").removeClass('active');
		    			$("#step2").removeClass('active');
		    			$("#step3").removeClass('active');
		    			}else if(datas.APPROVAL_STATE==0)//0为审批中
		    			{
		      			$("#step1").addClass('active');
		    			$("#step2").addClass('active');
		    			$("#step3").removeClass('active');
		    			}else if (datas.APPROVAL_STATE==1)//1 为完成状态
						{
		      			$("#step1").addClass('active');
		    			$("#step2").addClass('active');
			    		$("#step3").addClass('active');
		    			}else{
		    				$("#step1").addClass('active');
			    			$("#step2").removeClass('active');
			    			$("#step3").removeClass('active');
		    			}
		            } 
		         });
		}
		//动态加载变更单详情
		function setDetail(item){
			var title=undefined;
			var reason=undefined;
			var value=  $(".level_select:checked").val();
		      if(null==value||value==''){
	            	value='1';
	            }
			if(value==1||value==2){
				title=item.BG_NAME;
				reason=item.BG_REASON;
			}else if(value==3){
				title='GRC帐号新增';
				reason=item.ACCOUNT_REASON;
				
			}else if(value==4){
				title='GRC权限变更';
				reason=item.BG_REASON;
				
			}else{
				title='GRC帐号撤销';
				reason=item.CANCLE_REASON;
			}
			console.log(value);
		    var div = '<div class="profile-user-info"><div class="profile-info-row"><div class="profile-info-name">变更名称：</div><div class="profile-info-value"><span>'
		    	+title
		        + '</span></div></div><div class="profile-info-row"><div class="profile-info-name">申请单号：</div><div class="profile-info-value"><i class="fa fa-map-marker light-orange bigger-110"></i><span>'
		        + item.BILL_CODE		       
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请单位： </div><div class="profile-info-value"><span>'
		        + item.UNIT_CODE
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请部门： </div><div class="profile-info-value"><span>'
		        +item.DEPT_CODE
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 变更原因 ：</div><div class="profile-info-value"><span>'
		        +reason
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请人部门： </div><div class="profile-info-value"><span>'
		        +item.USER_DEPT
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请人： </div><div class="profile-info-value"><span>'
		        +item.USER_CODE
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请人岗位： </div><div class="profile-info-value"><span>'
		        +item.USER_JOB
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 联系方式： </div><div class="profile-info-value"><span>'
		        +item.USER_CONTACT
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请日期： </div><div class="profile-info-value"><span>'
		        +item.ENTRY_DATE
		        +'</span></div></div>'
		    return div;	
		}		
		//打印
		function printf(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="系统变更申请单打印";
			 diag.URL = '<%=basePath%>changeerpxtbg/goPrint.do?BILL_CODE='+encodeURI(Id);
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.show();
		}
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>changeerpxtbg/excel.do';
		}
	
		//审批通过
		function passApproval(){
		      var businessType= $(".level_select:checked").val();
			  $.ajax({
				  	type: "POST",
		            //提交的网址
		           	url: '<%=basePath%>approvalconfig/passApproval.do?BILL_CODE='+encodeURI(bill_code)+'&CURRENT_LEVEL='+current_level+'&NEXT_LEVEL='+next_level+'&BUSINESS_TYPE='+businessType,			      
		            //返回数据的格式
		        	dataType:'json',		          
		            success:function(datas){
		            	bootbox.dialog({
							message: "<span class='bigger-110'>"+datas.msg+"</span>",
						});	
		              	initList();
		            },
			  		 error:function(datas){
	            	bootbox.dialog({
						message: "<span class='bigger-110'>"+datas.msg+"</span>",
					});	
	              	initList();
	            }
		
		         });
		}
		//审批退回
		function returnApproval(){
		      var businessType= $(".level_select:checked").val();
			  $.ajax({
				  	type: "POST",
		            //提交的网址
		           	url: '<%=basePath%>approvalconfig/returnApproval.do?BILL_CODE='+encodeURI(bill_code)+'&CURRENT_LEVEL='+current_level+'&NEXT_LEVEL='+next_level+'&BUSINESS_TYPE='+businessType,	
		            //返回数据的格式
		        	dataType:'json',		          
		            success:function(datas){
		            	bootbox.dialog({
							message: "<span class='bigger-110'>"+datas.msg+"</span>",
						});	
		            	initList();
		            }
		
		         });
		}
		
		/**
		 * 初始化列表信息
		 */
		function initList(){
			$("#tasks li").remove(); 
			top.jzts();
			var keywords = $("#SelectedBillCode").val();
			 //获取选中项的值
            var value=$(".level_select:checked").val();
			$.ajax({
					type: "POST",
					url: '<%=basePath%>approvalconfig/getPageList.do?BUSINESS_CODE='+value,
			    	data: {keywords:keywords},
					dataType:'json',
					cache: false,
					success: function(data){
						if(data.length>0){
							$.each(data, function(i, item){
							    var html = '';
							    if(value==1||value==2){
							    	console.log(111111);
							    	html += setDiv1(item);
							    }else if(value==3){
							    	html += setDiv3(item);
				
							    }else if(value==4){
							    	html += setDiv4(item);		
							    }else{
							    	html += setDiv5(item);	
							    }
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
		//搜索
		function tosearch(){
			$("#tasks li").remove(); 
			top.jzts();
			var keywords = $("#SelectedBillCode").val();
            //获取选中项的值
//             var value = $("#BUSINESS_TYPE option:selected").attr("value");
            var value=$(".level_select:checked").val();
            if(null==value||value==''){
            	value='1';
            }
// 			console.log(value);
            $.ajax({
				type: "POST",
				url: '<%=basePath%>approvalconfig/getPageList.do?BUSINESS_CODE='+value,
		    	data: {keywords:keywords},
				dataType:'json',
				cache: false,
				success: function(data){
// 		        	$("#detail-tab").remove(); 
// 					var html = '';
// 	      		     html += setDetail(data[0]);
// 	      			$('#detail-tab').html(html);
// 					console.log(data[0]);
					if(data.length>0){
						$.each(data, function(i, item){
						    var html = '';
						    if(value==1||value==2){
						    	html += setDiv1(item);
						    }else if(value==3){
						    	html += setDiv3(item);
			
						    }else if(value==4){
						    	html += setDiv4(item);		
						    }else{
						    	html += setDiv5(item);	
						    }
						        
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
						+'<span class="list-item-value-title">没有需要审批的单据</span>'
					+'</label>'
				+'<div>'
			+'</li>';
			$("#tasks").append(htmlEmpty);
		}
	</script>


</body>
</html>