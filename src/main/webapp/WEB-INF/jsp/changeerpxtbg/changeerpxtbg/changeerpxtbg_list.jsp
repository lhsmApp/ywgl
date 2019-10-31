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
	<div class="main-container" id="main-container">
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
													<input id="SelectedBillCode" class="nav-search-input" autocomplete="off" type="text" name="SelectedBillCode" value="${pd.keywords }" placeholder="请输入变更申请名称"> 
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>																			 
												<button type="button" class="btn btn-info btn-xs" onclick="tosearch();">
												    <i class="ace-icon fa fa-search bigger-110"></i>
												</button>									
												            <label id="btnAdd" class="btn btn-sm btn-danger " onclick="add()"> 
												    	          <i class="ace-icon fa  glyphicon-plus bigger-110"></i>新增
												            </label> 
												            <label class="btn btn-sm btn-primary" onclick="edit(bill_code)"> 
												            <i class="ace-icon fa fa-pencil-square-o bigger-110"></i>编辑
												            </label> 
												            <label class="btn btn-sm btn-success" onclick="del(bill_code)"> 	
												            <i class="ace-icon fa fa-trash-o bigger-110"></i>删除
												            </label>
												            <label class="btn btn-sm btn-purple" onclick="report(bill_code)"> 
<!-- 												        <span  class="bigger-110">上报</span>  -->
												    	    <i class="ace-icon fa fa-share bigger-110"></i>上报
												            </label>
												            <label class="btn btn-sm btn-warning" onclick="cancleXtbgReport(bill_code)">
													        <i class="ace-icon fa fa-undo bigger-110"></i>撤销上报
												            </label>
												             <label class="btn btn-sm btn-pink" onclick="printf(bill_code)">
												             <i class="ace-icon fa fa-print bigger-110"></i>
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
							
							<ul id="tasks" class="item-list">
							
							</ul>						
	
				

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
									<ul class="nav nav-tabs" id="xtbg-tab">
										<li class="active"  tag="detail-tab">
											<a data-toggle="tab" href="#detail-tab">详情</a>
										</li>
										<li tag="report-tab">
											<a data-toggle="tab" href="#report-tab">提报</a>
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
<!-- 											<form action="changeerpxtbg/save.do" name="xtbgForm" id="xtbgForm" method="post"> -->
												<input type="hidden" name="BILL_CODE" id="BILL_CODE"/>
												<div id="zhongxin" style="padding-top: 13px;">
													<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-bgname">系统变更名称</label>
														<input type="text" name="BG_NAME" id="BG_NAME" class="form-control" placeholder="请输入系统变更名称"/>
													</div>
														<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-reason">变更原因</label>
														<input type="text" name="BG_REASON" id="BG_REASON" class="form-control" placeholder="请输入变更内容及原因"/>
													</div>
													    <div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-depart">单位</label>
														<input type="text" name="UNIT_CODE" id="UNIT_CODE" class="form-control" placeholder="请输入申请人单位"/>
													</div>
													   <div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-dept">部门</label>
														<input type="text" name="DEPT_CODE" id="DEPT_CODE" class="form-control" placeholder="请输入申请人部门"/>
													</div>
													<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-user">申请人</label>
															<select class="form-control" name="USER_CODE" id="USER_CODE">
																	<option value=""></option>
																	<c:forEach items="${userList}" var="user">
																	<option value="${user.USER_ID}">${user.USERNAME}</option>
																	</c:forEach>
																</select>
													</div>
												    <div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-depart">申请人部门</label>
														<input type="text" name="USER_DEPT" id="USER_DEPT" class="form-control" placeholder="请输入申请人单位"/>
													</div>
										   			<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-job">申请人岗位</label>
														<input type="text" name="USER_JOB" id="USER_JOB" class="form-control" placeholder="请输入申请人部门"/>
													</div>
													<div style="margin:10px 0px;">
														<label for="form-field-xtbg-report-contact">联系方式</label>
														<input type="text" name="USER_CONTACT" id="USER_CONTACT" class="form-control" placeholder="请输入联系方式"/>
													</div>
													<div style="margin:10px 0px;">
														<label for="form-field-select-1">变更预期时间</label>
														<input type="text" name="EFFECTIVE_DATE" id="EFFECTIVE_DATE" class="form-control" placeholder="请输入联系方式"/>
													</div>
													<hr />
													<div>
														<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
														<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
													</div>		
												</div>											
<!-- 											</form> -->
										</div>
								     </div>
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
	    //var unit_Code=undefined;
		$(top.hangge());//关闭加载状态
		$(function() {
			var data=${varList};
			showDetail(data[0].BILL_CODE);
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
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
			//点击新增按钮，弹到提报tab页
			$("#xtbg-tab li[tag='report-tab'] a").click();
			//新增清空文本框
			$("#BG_NAME").val("");//变更名称
			$("#BG_REASON").val("");//变更原因
			$("#UNIT_CODE").val("");//单位
			$("#DEPT_CODE").val("");//部门
			$("#USER_CODE").val("");//申请人
			$("#USER_DEPT").val("");//申请人部门
			$("#USER_JOB").val("");//申请人岗位
			$("#USER_CONTACT").val("");//联系方式
			$("#EFFECTIVE_DATE").val("");//变更预期时间
			$("#BILL_CODE").val("");//申请单号
// 			 top.jzts();
// 			 var diag = new top.Dialog();
// 			 diag.Drag=true;
// 			 diag.Title ="新增";
<%-- 			 diag.URL = '<%=basePath%>changeerpxtbg/goAdd.do'; --%>
// 			 diag.Width = 750;
// 			 diag.Height = 455;
// 			 diag.Modal = true;				//有无遮罩窗口
// 			 diag. ShowMaxButton = true;	//最大化按钮
// 		     diag.ShowMinButton = true;		//最小化按钮
// 			 diag.CancelEvent = function(){ //关闭事件
// 				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
// 					 if('${page.currentPage}' == '0'){
// 						 top.jzts();
// 						 setTimeout("self.location=self.location",100);
// 					 }else{
// 						 //nextPage(${page.currentPage});
// 					 }
// 				}
// 				diag.close();
// 			 };
// 			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>changeerpxtbg/delete.do?BILL_CODE="+encodeURI(Id);
					$.get(url,function(data){
						//nextPage(${page.currentPage});
						initList();
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			//点击新增按钮，弹到提报tab页
			$("#xtbg-tab li[tag='report-tab'] a").click();

		}
		//保存
		function save(){
			top.jzts();
			//$("#xtbgForm").submit();
			var bgName=$("#BG_NAME").val();//变更名称
			var bgReason=$("#BG_REASON").val();//变更原因
			var unitCode=$("#UNIT_CODE").val();//单位
			var deptCode=$("#DEPT_CODE").val();//部门
			var uesrCode=$("#USER_CODE").val();//申请人
			var uesrDept=$("#USER_DEPT").val();//申请人部门
			var uesrJob=$("#USER_JOB").val();//申请人岗位
			var uesrContact=$("#USER_CONTACT").val();//联系方式
			var bgyqDate=$("#EFFECTIVE_DATE").val();//变更预期时间
			var billCode=$("#BILL_CODE").val();//申请单号
			$.ajax({
				type: "POST",
				url: '<%=basePath%>changeerpxtbg/save.do',
				data:{BG_NAME:bgName,BG_REASON:bgReason,UNIT_CODE:unitCode,DEPT_CODE:deptCode,USER_CODE:uesrCode,USER_DEPT:uesrDept,USER_JOB:uesrJob,USER_CONTACT:uesrContact,EFFECTIVE_DATE:bgyqDate,BILL_CODE:billCode},
		    	dataType:'json',
				cache: false,
				success: function(response){
					if(response.code==0){
						$(top.hangge());//关闭加载状态
						bootbox.dialog({
							message: "<span class='bigger-110'>保存成功</span>",
						});		
						initList();
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
			$("#xtbg-tab li[tag='detail-tab'] a").click();
		}
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
		        + item.BG_NAME
		        + '</span></label></div><div><label class="inline"><span class="list-item-info">单号:&nbsp;</span><span class="list-item-value">'
		        + item.BILL_CODE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">科室：</span><span class="list-item-value">'
		        + item.DEPT_CODE
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">状态:&nbsp;</span><span class="list-item-value">'
		        + item.APPROVAL_STATE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">单位:&nbsp;</span><span class="list-item-value">'
		        + item.UNIT_NAME
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">变更原因:&nbsp;</span><span class="list-item-value">'
		        + item.BG_REASON
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">申请日期:&nbsp;'
		        + item.ENTRY_DATE
		        +'</span></label></div></li>';
		    return div;
		}
		function showDetail(code){
			  $.ajax({
				  	type: "POST",
		            //提交的网址
		           	url: '<%=basePath%>changeerpxtbg/showDetail.do?BILL_CODE='+code,		      
		            //返回数据的格式
		        	dataType:'json',		          
		            success:function(datas){
		            	//全局变量存放当前点击的变更申请单号
		            	bill_code=datas.BILL_CODE;
		            	var html = '';
		      		     html += setDetail(datas);
		      			$('#detail-tab').html(html);
		      			$("#BILL_CODE").val(datas.BILL_CODE);//申请单号
		      			$("#BG_NAME").val(datas.BG_NAME);//变更名称
		    			$("#BG_REASON").val(datas.BG_REASON);//变更原因
		    			$("#USER_CODE").val(datas.USER_CODE);//申请人
		    			$("#UNIT_CODE").val(datas.UNIT_CODE);//申请人单位
		    			$("#DEPT_CODE").val(datas.DEPT_CODE);//申请人部门
		    			$("#USER_JOB").val(datas.USER_JOB);//申请人岗位
		    			$("#USER_CONTACT").val(datas.USER_CONTACT);//联系方式
		    			$("#EFFECTIVE_DATE").val(datas.EFFECTIVE_DATE);//变更预期时间
                      //2为退回状态
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
		    var div = '<div class="profile-user-info"><div class="profile-info-row"><div class="profile-info-name">变更名称：</div><div class="profile-info-value"><span>'
		        + item.BG_NAME
		        + '</span></div></div><div class="profile-info-row"><div class="profile-info-name">申请单号：</div><div class="profile-info-value"><i class="fa fa-map-marker light-orange bigger-110"></i><span>'
		        + item.BILL_CODE		       
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请单位： </div><div class="profile-info-value"><span>'
		        + item.UNIT_NAME
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请部门： </div><div class="profile-info-value"><span>'
		        +item.DEPT_CODE
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 变更原因 ：</div><div class="profile-info-value"><span>'
		        +item.BG_REASON
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请人部门： </div><div class="profile-info-value"><span>'
		        +item.DEPT_CODE
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
			 diag.Height = 550;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.show();
		}
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>changeerpxtbg/excel.do';
		}

		//搜索
		function tosearch(){
			$("#tasks li").remove(); 
			top.jzts();
			var keywords = $("#SelectedBillCode").val();
			$.ajax({
					type: "POST",
					url: '<%=basePath%>changeerpxtbg/getPageList.do',
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
			           	url: '<%=basePath%>approvalconfig/bgReport.do?BUSINESS_CODE=1&BILL_CODE='+encodeURI(billCode),		      
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
			});
				 
		}
		//撤销上报
		function cancleXtbgReport(billCode){
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
				            	initList();
				            }
				
				         });
				}
			});
				 
		}
		
		/**
		 * 初始化列表信息
		 */
		function initList(){
			$("#tasks li").remove(); 
			top.jzts();
			var keywords = $("#keywords").val();
			$.ajax({
					type: "POST",
					url: '<%=basePath%>changeerpxtbg/getPageList.do',
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
	</script>


</body>
</html>