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
										    <form class="form-inline">
												<span class="input-icon pull-left" style="margin-right: 5px;">
													<input id="SelectedBusiDate" class="nav-search-input" autocomplete="off" type="text" name="keywords" value="${pd.keywords }" placeholder="在已有申请中搜索"> 
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>																			 
												<button type="button" class="btn btn-info btn-sm" onclick="tosearch();">
												    <i class="ace-icon fa fa-search bigger-110"></i>
												</button>									
												            <label class="btn btn-sm btn-danger " onclick="add()"> 
												    	          <i class="ace-icon fa  glyphicon-plus bigger-160"></i>新增
												            </label> 
												            <label class="btn btn-sm btn-primary" onclick="edit(bill_code)"> 
												            <i class="ace-icon fa fa-pencil-square-o bigger-160"></i>编辑
												            </label> 
												            <label class="btn btn-sm btn-success" onclick="del(bill_code)"> 	
												            <i class="ace-icon fa fa-trash-o bigger-160"></i>删除
												            </label>
												            <label class="btn btn-sm btn-purple"> 
<!-- 												        <span  class="bigger-110">上报</span>  -->
												    	    <i class="ace-icon fa fa-share bigger-160"></i>上报
												            </label>
												            <label class="btn btn-sm btn-warning">
													        <i class="ace-icon fa fa-undo bigger-160"></i>撤销上报
												            </label>
												             <label class="btn btn-sm btn-pink"onclick="printf(bill_code)">
												             <i class="ace-icon fa fa-print bigger-160"></i>
															打印
												            </label>										
										    </form>
									    </div>
								    </div>
							    </div>
						    </div>
						    
					    </div>	
					<div class="row">
						<div class="col-xs-4">
						<!-- 检索  -->
						<form style="margin:5px;" action="user/listUsers.do" method="post" name="userForm" id="userForm">
<!-- 							<div class="nav-search" style="margin:10px 0px;"> -->
<!-- 								<span class="input-icon" style="width:86%"> -->
<%-- 									<input style="width:100%" class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词" /> --%>
<!-- 									<i class="ace-icon fa fa-search nav-search-icon"></i> -->
<!-- 								</span> -->
<!-- 								<button style="margin-bottom:3px;" class="btn btn-light btn-minier" onclick="searchs();"  title="检索"> -->
<!-- 									<i id="nav-search-icon" class="ace-icon fa fa-search bigger-120 nav-search-icon blue"></i> -->
<!-- 									<i class="ace-icon fa fa-signal icon-only bigger-150"></i> -->
<!-- 								</button> -->
<!-- 							</div> -->
							
							
							<ul id="tasks" class="item-list">
							
							</ul>						
	
				
						</form>
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
														<%-- <select class="chosen-select form-control" name="ROLE_ID" id="role_id" data-placeholder="请选择角色" style="vertical-align:top;" style="width:98%;" >
															<option value=""></option>
															<c:forEach items="${roleList}" var="role">
																<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
															</c:forEach>
														</select> --%>
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
	    var bill_code=undefined;
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			var data=${varList};
			showDetail(data[0].BILL_CODE);
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
			 diag.URL = '<%=basePath%>changeerpzhxz/goAdd.do';
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
					var url = "<%=basePath%>changeerpzhxz/delete.do?BILL_CODE="+encodeURI(Id);
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
			 diag.URL = '<%=basePath%>changeerpzhxz/goEdit.do?BILL_CODE='+encodeURI(Id);
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
								url: '<%=basePath%>changeerpzhxz/deleteAll.do?tm='+new Date().getTime(),
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
		//console.log(${varList});
		var data=${varList};
		//循环加载到页面
		function getChangeData(){
		    var html = '';
		    for(var i = 0;i<data.length;i++){
		        html += setDiv(data[i]);
		    }
		    //document.getElementById("tasks").innerHTML = html;
			$('#tasks').html(html);
		}		
		//动态加载变更数据
		function setDiv(item){
		    var div = '<li  class="item-grey clearfix" onclick="showDetail(\''+item.BILL_CODE+'\');"><div><label class="inline" style="margin-bottom:5px;"><span class="list-item-value-title">新增账号</span></label></div><div><label class="inline"><span class="list-item-info">单位:&nbsp;</span><span class="list-item-value">'
		        + item.UNIT_CODE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">科室：<span class="list-item-value">'
		        + item.DEPT_CODE
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">新增账号原因:&nbsp;</span><span class="list-item-value">'
		        + item.ACCOUNT_REASON
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">单号:&nbsp;</span><span class="list-item-value">'
		        + item.BILL_CODE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">账号角色:&nbsp;'
		        + item.ACCOUNT_ROLES
		        +'</span></label></div></li>';
		    return div;
		}
		function showDetail(code){
			  $.ajax({
				  	type: "POST",
		            //提交的网址
		           	url: '<%=basePath%>changeerpzhxz/showDetail.do?BILL_CODE='+code,		      
		            //返回数据的格式
		        	dataType:'json',		          
		            success:function(datas){
		            	//全局变量存放当前点击的变更申请单号
		            	bill_code=datas.BILL_CODE;
		            	console.log(datas);
		            	var html = '';
		      		     html += setDetail(datas);
			            //console.log(html);
		      			$('#detail-tab').html(html);
 		      		    //console.log(datas.BILL_STATE);
		      		  if(datas.BILL_STATE==1)
		    			{
		    			$("#step1").addClass('active');
		    			$("#step2").removeClass('active');
		    			$("#step3").removeClass('active');
		    			}else if(datas.BILL_STATE==2)
		    			{
		      			$("#step1").addClass('active');
		    			$("#step2").addClass('active');
		    			$("#step3").removeClass('active');
		    			}else
						{
		      			$("#step1").addClass('active');
		    			$("#step2").addClass('active');
			    		$("#step3").addClass('active');
		    			}
		            } 
		         });
		}
		//动态加载变更单详情
		function setDetail(item){
		    var div = '<div class="profile-user-info"><div class="profile-info-row"><div class="profile-info-name">问题：</div><div class="profile-info-value"><span>新增账号</span></div></div><div class="profile-info-row"><div class="profile-info-name">单位：</div><div class="profile-info-value"><i class="fa fa-map-marker light-orange bigger-110"></i><span>'
		        + item.UNIT_CODE		       
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 科室： </div><div class="profile-info-value"><span>'
		        + item.DEPT_CODE
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 变更原因 ：</div><div class="profile-info-value"><span>'
		        +item.ACCOUNT_REASON
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 单号： </div><div class="profile-info-value"><span>'
		        +item.BILL_CODE
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请人： </div><div class="profile-info-value"><span>'
		        +item.USER_CODE
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请人部门： </div><div class="profile-info-value"><span>'
		        +item.USER_DEPT
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请人岗位： </div><div class="profile-info-value"><span>'
		        +item.USER_JOB
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请日期： </div><div class="profile-info-value"><span>'
		        +item.ENTRY_DATE
		        +'</span></div></div>'
		    return div;	
		}	
		function printf1(){
			//具体需要跳转的地址
			var printURL = "PrintReport.jsp?title=" + encodeURIComponent("报表打印")
				+ "&report=" + encodeURIComponent("grf/erpSystemChange.grf")
				+ "&data=" + encodeURIComponent('<%=basePath%>changeerpzhxz/showDetail.do');
			//再新窗口打开这个打印页面
			window.open(printURL, '_blank');

		}		
		//修改
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
		
	</script>


</body>
</html>