<%@ page language="java" contentType="text/html; charset=UTF-8"
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
					<!-- /section:settings.box -->
					<div class="page-header">
						<span class="label label-xlg label-success arrowed-right">变更管理</span>
			            <span class="label label-xlg label-yellow arrowed-in arrowed-right"
			              id="subTitle" style="margin-left: 2px;">变更审批
			            </span> 
			            <span style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;
			            </span>
						<div class="pull-right">
							<span class="green middle bolder">变更业务类型: &nbsp;</span>
							<div class="btn-toolbar inline middle no-margin">
								<div data-toggle="buttons" class="btn-group no-margin">
									<label class="btn btn-sm btn-primary active">
										<input type="radio" class="level_select"  name="level_select" value="1" checked>系统变更
									</label>
									<label class="btn btn-sm btn-primary"> 
										<input type="radio" class="level_select" name="level_select" value="2">角色变更
									</label>
									<label class="btn btn-sm btn-primary">
										<input type="radio" class="level_select" name="level_select" value="3">GRC帐号新增
									</label>
									<label class="btn btn-sm btn-primary"> 
										<input type="radio" class="level_select" name="level_select" value="4">GRC权限变更
									</label>
									<label class="btn btn-sm btn-primary">
										<input type="radio" class="level_select" name="level_select" value="5">GRC帐号撤销
									</label> 
								
								</div>
							</div>
						</div>
					</div>	
					<div class="row">
						<div class="col-xs-12">
							<div>
								<span class="input-icon pull-left" style="margin-right: 5px;">
									<input id="SelectedBillCode" class="nav-search-input" autocomplete="off" type="text" name="keywords" value="${pd.keywords }" placeholder="在已有申请中搜索"> 
										<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>		
																																								 
								<button type="button" class="btn btn-info btn-sm" onclick="tosearch();">
								    <i class="ace-icon fa fa-search bigger-110"></i>
								</button>																			       
					            <button class="btn btn-sm btn-primary"onclick="passApproval()"> 
					    	    	<i class="ace-icon fa fa-share bigger-110"></i>审批通过
					            </button>
					            <button class="btn btn-sm btn-primary" onclick="returnApproval()">
						        	<i class="ace-icon fa fa-undo bigger-110"></i>审批退回
					            </button>
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
								</div>
								<div class="widget-body">
									<div class="widget-main padding-4">
										<div class="tab-content padding-8">
											<div id="detail-tab" class="tab-pane active">
												<div id="detail-info"></div>
												<hr />
												<div id="attachment" style="visibility:hidden;">
													<h5 class="lighter block blue"><i class="ace-icon fa fa-rss blue"></i>&nbsp;附件</h5>
													<hr />
													<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
														<thead>
															<tr>
																<th class="center" style="width:50px;">序号</th>
																<th class="center">附件名</th>
																<th class="center">附件说明</th>
																<th class="center">附件大小</th>
																<th class="center">上传人</th>
																<th class="center">上传日期</th>
																<th class="center">操作</th>
															</tr>
														</thead>
																				
														<tbody id="tbodyAttachment">
															
														</tbody>
													</table>
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
	    <a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
    </div>

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
    var globalFocus_BUSINESS_CODE = null
	    var bill_code=undefined;//系统变更单号码
	    var current_level=undefined;//当前审批级别
	    varnext_level=undefined;//下一审批级别	    
		$(top.hangge());//关闭加载状态
		
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
			
			/* $('input:radio[name="level_select"]').change( function(){
				var value=$(".level_select:checked").val();
				console.log(value);
				if(null==value||value==''){
	            	value='1';
	            }
				if(value==3){
					$('#attachment').css("visibility",'visible');
					
				}else{
					$('#attachment').css("visibility",'hidden');
				}
		    }); */
		});
		
		/**
		 * 获取问题附件信息
		 */
		function getProAttachment(billCode,attachmentType){
			$("#tbodyAttachment").html('');
			top.jzts();
			$.ajax({
					type: "GET",
					url: '<%=basePath%>attachment/getAttachmentByType.do?BUSINESS_TYPE='+attachmentType+'&BILL_CODE='+billCode,
			    	data: {},
					//dataType:'json',
					cache: false,
					success: function(data){
						if(data){
							$.each(data, function(i, item){
								var tr=addItemAttachment(item,i+1,attachmentType); 
								$('#tbodyAttachment').append(tr);
						 	});
						}
						top.hangge();
					}
			});
		}

		/**
		 * 增加附件tr
		 */
		function addItemAttachment(item,index,type){
			var href='<%=basePath%>/attachment/download.do?ATTACHMENT_ID='+item.ATTACHMENT_ID;
			var ext=item.ATTACHMENT_PATH.substring(19,item.ATTACHMENT_PATH.length);
			console.log(ext);
			var htmlLog='<tr>'
				+'<td class="center" style="width: 30px;">'+index+'</td>'
				+'<td class="center">'+item.ATTACHMENT_NAME+ext+'</td>'
				+'<td class="center">'+item.ATTACHMENT_MEMO+'</td>'
				+'<td class="center">'+item.ATTACHMENT_SIZE+'&nbsp;KB</td>'
				+'<td class="center">'+item.CREATE_USER+'</td>'
				+'<td class="center">'+item.CREATE_DATE+'</td>'
				+'<td class="center">'
					+'<div class="hidden-sm hidden-xs btn-group">'
						+'<a class="btn btn-xs btn-success" onclick=window.location.href="'+href+'">'
							+'<i class="ace-icon fa fa-cloud-download bigger-120" title="下载"></i>'
						+'</a>'
						/* +'<a class="btn btn-xs btn-danger" onclick=delProAttachment("'+item.ATTACHMENT_ID+'","'+type+'")>'
							+'<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>'
						+'</a>' */
					+'</div>'
				+'</td>'
			+'</tr>';
			return htmlLog;
		}
		
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
						//console.log(data);
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
											//nextPage(${page.currentPage});
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
 		    	if(data[i].BG_NAME==""){
		    		if(data[i].BUSINESS_CODE==3){
		    			data[i].BG_NAME='GRC帐号新增';
		    			//data[i].BG_REASON=data[i].ACCOUNT_REASON;
						
					}else if(data[i].BUSINESS_CODE==4){
						data[i].BG_NAME='GRC权限变更';
						//data[i].BG_REASON=data[i].BG_REASON;
						
					}else{
						data[i].BG_NAME='GRC帐号撤销';
						//data[i].BG_REASON=data[i].CANCLE_REASON;
					}
		    	} 
				data[i].BG_REASON=data[i].REASON;
				//console.log(data);
		        html += setDiv(data[i]);
		    }
		    //document.getElementById("tasks").innerHTML = html;
			$('#tasks').html(html);
		}		
		
		//动态加载变更数据
		function setDiv(item){
		    var div = '<li  class="item-grey clearfix" onclick="showDetail(\''+item.BILL_CODE+'\',\''+item.CURRENT_LEVEL+'\',\''+item.NEXT_LEVEL+'\','+ item.BUSINESS_CODE+');"><div><label class="inline" style="margin-bottom:5px;"><span class="list-item-value-title">'
		        + item.BG_NAME
		        + '</span></label></div><div><label class="inline"><span class="list-item-info">单号:&nbsp;</span><span class="list-item-value">'
		        + item.BILL_CODE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">科室：</span><span class="list-item-value">'
		        + item.DEPT_NAME
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
		//GRC帐号新增
		function setDiv3(item){
		    var div = '<li  class="item-grey clearfix" onclick="showDetail(\''+item.BILL_CODE+'\',\''+item.CURRENT_LEVEL+'\',\''+item.NEXT_LEVEL+'\');"><div><label class="inline" style="margin-bottom:5px;"><span class="list-item-value-title">'
		        + "GRC帐号新增"
		        + '</span></label></div><div><label class="inline"><span class="list-item-info">单号:&nbsp;</span><span class="list-item-value">'
		        + item.BILL_CODE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">科室：</span><span class="list-item-value">'
		        + item.DEPT_NAME
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">状态:&nbsp;</span><span class="list-item-value">'
		        + item.APPROVAL_STATE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">单位:&nbsp;</span><span class="list-item-value">'
		        + item.UNIT_NAME
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
		function setDiv5(item){
		    var div = '<li  class="item-grey clearfix" onclick="showDetail(\''+item.BILL_CODE+'\',\''+item.CURRENT_LEVEL+'\',\''+item.NEXT_LEVEL+'\');"><div><label class="inline" style="margin-bottom:5px;"><span class="list-item-value-title">'
		        + "账号撤销"
		        + '</span></label></div><div><label class="inline"><span class="list-item-info">单号:&nbsp;</span><span class="list-item-value">'
		        + item.BILL_CODE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">科室：</span><span class="list-item-value">'
		        + item.DEPT_NAME
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">状态:&nbsp;</span><span class="list-item-value">'
		        + item.APPROVAL_STATE
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">单位:&nbsp;</span><span class="list-item-value">'
		        + item.UNIT_NAME
		        +'</span></label></div><div><label class="inline"><span class="list-item-info">账号撤销原因:&nbsp;</span><span class="list-item-value">'
		        + item.CANCLE_REASON
		        +'</span></label><label class="inline pull-right"><span class="list-item-info">申请日期:&nbsp;'
		        + item.ENTRY_DATE
		        +'</span></label></div></li>';
		    return div;
		}
		function showDetail(code,current,next,businessType=0){
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
			bill_code=code;
		 	current_level=current;//当前审批级别
		    next_level=next;//下一审批级别	 
// 			var businessType= $("#BUSINESS_TYPE").val();//业务类型
            //var businessType= $(".level_select:checked").val();
            if(businessType==0){
                businessType= $(".level_select:checked").val();
            }
            globalFocus_BUSINESS_CODE = businessType
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
		            	//console.log(datas);
		            	var html = '';
		      		     html += setDetail(datas, businessType);
		      			$('#detail-info').html(html);
		      			console.log(datas.APPROVAL_STATE);
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
		function setDetail(item, businessType){
			var title=undefined;
			var reason=undefined;
			var value=  businessType//$(".level_select:checked").val();
		      if(null==value||value==''){
	            	value='1';
	            }
			if(value==1||value==2){
				title=item.BG_NAME;
				reason=item.BG_REASON;
				$('#attachment').css("visibility",'hidden');
			}else if(value==3){
				title='GRC帐号新增';
				reason=item.ACCOUNT_REASON;
				
				$('#attachment').css("visibility",'visible');
				/* 获取附件信息 */
				 getProAttachment(item.BILL_CODE,"CHANGE_GRC_ZHXZ");
			}else if(value==4){
				title='GRC权限变更';
				reason=item.BG_REASON;
				$('#attachment').css("visibility",'hidden');
				
			}else{
				title='GRC帐号撤销';
				reason=item.CANCLE_REASON;
				$('#attachment').css("visibility",'hidden');
			}
		    var div = '<div class="profile-user-info"><div class="profile-info-row"><div class="profile-info-name">变更名称：</div><div class="profile-info-value"><span>'
		    	+title
		        + '</span></div></div><div class="profile-info-row"><div class="profile-info-name">申请单号：</div><div class="profile-info-value"><i class="fa fa-map-marker light-orange bigger-110"></i><span>'
		        + item.BILL_CODE		       
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请单位： </div><div class="profile-info-value"><span>'
		        + item.UNIT_NAME
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 申请部门： </div><div class="profile-info-value"><span>'
		        +item.DEPT_NAME
		        +'</span></div></div><div class="profile-info-row"><div class="profile-info-name"> 变更原因 ：</div><div class="profile-info-value"><span>'
		        +reason
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
		      var businessType= globalFocus_BUSINESS_CODE//$(".level_select:checked").val();
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
		      var businessType= globalFocus_BUSINESS_CODE//$(".level_select:checked").val();
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
            if(null==value||value==''){
            	value='1';
            }
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
							    	html += setDiv(item);
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
						    	html += setDiv(item);
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