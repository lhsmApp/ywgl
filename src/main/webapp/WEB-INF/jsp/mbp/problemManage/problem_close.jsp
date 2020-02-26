<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
					<div class="row">
						<div class="col-xs-4">
						<!-- 检索  -->
							<form action='mbp/getPageList.do?ProOperType=PROBLEM_CLOSE'></form>
							<div class="nav-search" style="margin:10px 0px;">
								<span class="input-icon" style="width:86%">
									<input style="width:100%" class="nav-search-input" autocomplete="off" id="keywords" type="text" name="keywords" value="${pd.keywords }" placeholder="这里输入问题标题" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
								<button style="margin-bottom:3px;" class="btn btn-light btn-minier" onclick="searchs();"  title="检索">
									<i id="nav-search-icon" class="ace-icon fa fa-search bigger-120 nav-search-icon blue"></i>
									<!-- <i class="ace-icon fa fa-signal icon-only bigger-150"></i> -->
								</button>
							</div>
							
							<ul id="problemList" class="item-list">
								
							</ul>						

							<div id="page" class="pagination" style="float: right;padding-top: 5px;margin-top: 0px;font-size:12px;"></div>
						
					</div>
					<!-- /.col4 -->
					
					<div class="col-xs-8">
						<div class="widget-box transparent" id="recent-box">
							<div class="widget-header">
								<h4 class="widget-title lighter smaller blue">
									<i class="ace-icon fa fa-rss orange"></i><span id="currentTabTitle">详情</span>
								</h4>
		
								<div class="widget-toolbar no-border">
									<ul class="nav nav-tabs" id="problem-tab">
										<li class="active" tag="detail-tab">
											<a data-toggle="tab" href="#detail-tab">详情</a>
										</li>
		
										<li tag="close-tab">
											<a data-toggle="tab" href="#close-tab">关闭</a>
										</li>
										
									</ul>
								</div>
							</div>
		
							<div class="widget-body">
								<div class="widget-main padding-4">
									<div class="tab-content padding-8">
										<!-- 详细信息 -->
										<div id="detail-tab" class="tab-pane active">
											<!-- <h4>
												
											</h4> -->
											<div id="modal-wizard-container">
												<ul class="steps">
													<li data-step="1" class="active">
														<span class="step">1</span>
														<span class="title">新发起</span>
													</li>
													<li data-step="2">
														<span class="step">2</span>
														<span class="title">已提交</span>
													</li>
													<li data-step="3">
														<span class="step">3</span>
														<span class="title">受理中</span>
													</li>
	
													<li data-step="4">
														<span class="step">4</span>
														<span class="title">已关闭</span>
													</li>
												</ul>
												<hr />
												<h5 class="lighter block blue"><i class="ace-icon fa fa-rss"></i>&nbsp;基本信息</h5>
												<hr />
												
												<div class="profile-user-info " >
													<div class="profile-info-row">
														<div class="profile-info-name"> 问题 </div>
			
														<div class="profile-info-value">
															<span id="valPRO_TITLE"></span>
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name"> 上报人 </div>
			
														<div class="profile-info-value">
															<span id="valPRO_REPORT_USER"></span>
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name"> 受理人 </div>
			
														<div class="profile-info-value">
															<span id="valPRO_ACCEPT_USER"></span>
														</div>
													</div>
			
			
													<div class="profile-info-row">
														<div class="profile-info-name"> 上报单位 </div>
			
														<div class="profile-info-value">
															<!-- <i class="fa fa-map-marker light-orange bigger-110"></i> -->
															<!-- <span>中国石油</span> -->
															<span id="valPRO_DEPART"></span>
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name"> 系统 </div>
			
														<div class="profile-info-value">
															<!-- <span>中国石油</span> -->
															<span id="valPRO_SYS_TYPE"></span>
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name"> 问题类型 </div>
			
														<div class="profile-info-value">
															<span id="valPRO_TYPE_ID"></span>
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name"> 问题标签 </div>
			
														<div class="profile-info-value">
															<span id="valPRO_TAG"></span>
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name"> 优先级 </div>
			
														<div class="profile-info-value">
															<span id="valPRO_PRIORITY"></span>
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name"> 问题解决时间 </div>
			
														<div class="profile-info-value">
															<span id="valPRO_RESOLVE_TIME"></span>
														</div>
													</div>
			
													<div class="profile-info-row">
														<div class="profile-info-name"> 处理状态 </div>
			
														<div class="profile-info-value">
															<span id="valPRO_STATE"></span>
														</div>
													</div>
			
													<div class="profile-info-row">
														<div class="profile-info-name"> 更新日期 </div>
			
														<div class="profile-info-value">
															<span id="valUPDATE_DATE"></span>
														</div>
													</div>
												</div>
												<hr />
												<h5 class="lighter block blue"><i class="ace-icon fa fa-rss blue"></i>&nbsp;问题描述</h5>
												<hr />
												<div id="valPRO_CONTENT" style="word-wrap:break-word">
													
												</div>
												<hr />
												<div >
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
																				
														<tbody id="tbodyProInfoAttachment">
															
														</tbody>
													</table>
												</div>
											</div>
										</div>
										
										<!-- 问题关闭 -->
										<div id="close-tab" class="tab-pane">
											<h4 class="smaller lighter green">
												<!-- <i class="ace-icon fa fa-list"></i>
												Sortable Lists -->
												<button id="btnAddClose" class="btn btn-success btn-xs"
													onclick="addClose()">
													<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>提交</span>
												</button>
												<button id="btnDeleteClose" class="btn btn-danger btn-xs"
													onclick="deleteClose()">
													<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>取消</span>
												</button>
											</h4>
											<form id="problemCloseForm" method="post">
												<input type="hidden" id="ff-close-pro-code"/>
												<div>
													<div style="margin:10px 0px;">
														<!-- <label for="editorClose">关闭信息</label> -->
														<script id="editorClose" type="text/plain" style="width:100%;height:259px;"></script>
													</div>

													<%-- <div style="margin:20px 0px;">
														<span>回复人：</span><span>${pd.USER_NAME}</span>
														<span style="margin-left:30px;">回复日期：</span><span>${pd.CURRENT_DATE}</span>
													</div>	 --%>
												</div>
											</form>
										
											<div >
												<h4>
													<button id="btnAddProCloseAttachment" class="btn btn-success btn-xs"
														onclick="addProAttachmentByType('PROBLEM_CLOSE')">
														<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>上传附件</span>
													</button>
												</h4>
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
																			
													<tbody id="tbodyProCloseAttachment">
														
													</tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
							</div>	
					</div>
					<!-- /.col8 -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

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
	
	<!-- 步骤条 -->
	<script src="static/ace/js/fuelux/fuelux.wizard.js"></script>
	<script src="static/ace/js/ace/elements.wizard.js"></script>
	
	<!-- ace scripts -->
	<!-- <script src="static/ace/js/ace.js"></script> -->
	<!-- <script src="static/ace/js/ace/ace.js"></script> -->
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	
	<!-- 编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
	<!-- 编辑框-->
	</body>

<script type="text/javascript">
var currentItem;

$(top.hangge());

/**
 * html文档加载完成后执行初始化方法，初始化界面元素样式，初始化基础数据，列表等信息
 */
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
	
	/* 加载进度条 */
	$('#modal-wizard-container').ace_wizard();
	
	/* 加载富文本 */
	setTimeout("ueditor()",500);
	
	//初始化问题列表数据
	initList('<%=basePath%>mbp/getPageList.do?ProOperType=PROBLEM_CLOSE');
	
	$("#problem-tab a").click(function(e){
		$('#currentTabTitle').text($(this).text());
		e.preventDefault();
    });
	
	/* $("#problem-tab li[tag='report-tab'] a").click(function(e){
        
    }); */
});

/**
 * 加载富文本 
 */
function ueditor(){
	//UE.getEditor('editor').setDisabled();
	var ueClose = UE.getEditor('editorClose');
}

function jumpStep(step){
	//jump to a step
	var wizard = $('#modal-wizard-container').data('fu.wizard')
	wizard.currentStep = step;
	wizard.setState();

	//determine selected step
	//wizard.selectedItem().step
}

/**
 * 初始化列表信息
 */
function initList(url){
	$("#problemList li").remove(); 
	top.jzts();
	var keywords = $("#keywords").val();
	$.ajax({
			type: "POST",
			url: url,
	    	data: {keywords:keywords},
			dataType:'json',
			cache: false,
			success: function(data){
				$("#page").html(data.pageHtml);
				var first;
				if(data&&data.rows&&data.rows.length>0){
					$.each(data.rows, function(i, item){
						if(i==0){
							first=item;
						}
						//console.log(item);
						addItem(item); 
				 	});
					if(first){
						getDetail(first.PRO_CODE);
					}
				}
				else{
					addEmpty();
				}
				top.hangge();
			}
	});
}

/**
 * 增加Item数据
 */
function addItem(item){
	var htmlProState='';
	if(item.PRO_STATE=="2"){
		htmlProState='<span class="label label-xs label-info">'+item.PRO_STATE_NAME+'</span>'
	}else if(item.PRO_STATE=="3"){
		htmlProState='<span class="label label-xs label-warning">'+item.PRO_STATE_NAME+'</span>'
	}else if(item.PRO_STATE=="4"){
		htmlProState='<span class="label label-xs label-inverse">'+item.PRO_STATE_NAME+'</span>'
	}else{
		htmlProState='<span class="label label-xs label-success">'+item.PRO_STATE_NAME+'</span>'
	}
	var htmlItem='<li class="item-grey clearfix list-item-hover" onclick=getDetail("'+item.PRO_CODE+'")>'
	+'<input name="PRO_CODE" id="PRO_CODE" type="hidden" value="'+item.PRO_CODE+'" />'
		+'<div>'
			+'<label class="inline" style="margin-bottom:5px;">'
				+'<span class="list-item-value-title">'+item.PRO_TITLE+'</span>'
			+'</label>'
		+'<div>'
		+'<div>'
			+'<label class="inline">'
				+'<span class="list-item-info"> 单位：</span>'
				+'<span class="list-item-value">'+item.PRO_DEPART_NAME+'</span>'
			+'</label>'
			+'<label class="inline pull-right">'
				+'<span class="list-item-info"> 系统：</span>'
				+'<span class="list-item-value">'+item.PRO_SYS_TYPE_NAME+'</span>'
			+'</label>'
		+'</div>'
		
		+'<div>'
			+'<label class="inline">'
				+'<span class="list-item-info"> 处理状态：</span>'
				/* +'<span class="list-item-value green">'+item.PRO_STATE_NAME+'</span>' */
				+htmlProState
				+'</label>'
			+'<label class="inline pull-right">'
				+'<span class="list-item-info"> 问题类型：</span>'
				+'<span class="list-item-value">'+item.PRO_TYPE_NAME+'</span>'
			+'</label>'
		+'</div>'
		+'<div class="time">'
			+'<i class="ace-icon fa fa-clock-o"></i>'
			+'<span class="grey">'+item.UPDATE_DATE+'</span>'
		+'</div>'
	+'</li>';
	$("#problemList").append(htmlItem);
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
	$("#problemList").append(htmlEmpty);
}


/**
 * 获取明细信息
 */
function getDetail(problemCode){
	/* $("#problem-tab li").each(function(){
		var item = this;
		if($(item).attr("tag")=="detail-tab"){
			$(this).tab("show");
			$(this).click();
		}
	}); */
	
	/* $("#report-tab").removeClass("active");
	$("#assign-tab").removeClass("active");
	$("#close-tab").removeClass("active");
	$("#detail-tab").addClass("active"); */
	
	if(event){
		$("#problemList li").each(function(){
			var item = this;
			$(item).removeClass("bc-light-orange");
		}); 
		$($(event.srcElement).closest('li')).addClass("bc-light-orange");
	}else{
		$("#problemList li").first().addClass("bc-light-orange");
		//$($(event.srcElement).parents('li')).addClass("bc-light-orange");
	}
	//add();//清空原有问题提报中信息
	$("#problem-tab li[tag='detail-tab'] a").click();
	
	$.ajax({
		type: "GET",
		url: '<%=basePath%>mbp/getDetail.do?PRO_CODE='+problemCode,
		dataType:'json',
		cache: false,
		success: function(data){
			 if(data){
				 currentItem=data;
				 
				 $("#valPRO_TITLE").text(data.PRO_TITLE);
				 $("#valPRO_REPORT_USER").text(data.PRO_REPORT_USER_NAME);
				 $("#valPRO_ACCEPT_USER").text(data.PRO_ACCEPT_USER_NAME);
				 $("#valPRO_DEPART").text(data.PRO_DEPART_NAME);
				 $("#valPRO_SYS_TYPE").text(data.PRO_SYS_TYPE_NAME);
				 $("#valPRO_TYPE_ID").text(data.PRO_TYPE_NAME);
				 $("#valPRO_TAG").text(data.PRO_TAG);
				 $("#valPRO_PRIORITY").text(data.PRO_PRIORITY_NAME);
				 $("#valPRO_RESOLVE_TIME").text(data.PRO_RESOLVE_TIME);
				 $("#valPRO_STATE").text(data.PRO_STATE_NAME);
				 $("#valUPDATE_DATE").text(data.UPDATE_DATE);
				 $("#valPRO_CONTENT").html(data.PRO_CONTENT);
				 
				 
				 /* 获取提报中附件信息 */
				 getProAttachment("PROBLEM_INFO");

				 /* 获取关闭中附件信息 */
				 getProAttachment("PROBLEM_CLOSE");
				 
				 if(data.PRO_STATE=="2"){
					 jumpStep(2);
				 }else if(data.PRO_STATE=="3"){
					 jumpStep(3);
				 }else if(data.PRO_STATE=="4"){
					 jumpStep(4);
				 }else{
					 jumpStep(1);
				 }
				 
			 }
			 top.hangge();
		}
	});
}

//检索
function searchs(){
	initList('<%=basePath%>mbp/getPageList.do?ProOperType=PROBLEM_CLOSE');
	//$("#problemForm").submit();
}

/**
 * 问题关闭
 */
function addClose(){
	console.log('addClose');
	var contentClose=UE.getEditor('editorClose').getContent();
	if (contentClose == "") {
		$("#editorClose").tips({
			side : 3,
			msg : '请输入回复内容',
			bg : '#AE81FF',
			time : 2
		});
		$("#editorClose").focus();
		return false;
	}
	var content;
	var arr = [];
    arr.push(contentClose);
    content=arr.join("");
    var proCode=currentItem.PRO_CODE;//问题单号
    
	top.jzts();
	
	$.ajax({
		type: "POST",
		url: '<%=basePath%>mbp/addClose.do',
		data:{PRO_CODE:proCode,CLOSE_CONTENT:content},
    	dataType:'json',
		cache: false,
		success: function(response){
			if(response.code==0){
				$(top.hangge());//关闭加载状态
				$("#btnAddClose").tips({
					side:3,
		            msg:'关闭成功',
		            bg:'#009933',
		            time:3
		        });
				initList('<%=basePath%>mbp/getPageList.do?ProOperType=PROBLEM_CLOSE');
				jumpStep(4);
			}else{
				$(top.hangge());//关闭加载状态
				$("#btnAddClose").tips({
					side:3,
		            msg:'关闭失败,'+response.message,
		            bg:'#cc0033',
		            time:3
		        });
			}
		},
    	error: function(response) {
    		$(top.hangge());//关闭加载状态
    		var msgObj=JSON.parse(response.responseText);
			$("#btnAddClose").tips({
				side:3,
	            msg:'关闭失败,'+msgObj.message,
	            bg:'#cc0033',
	            time:3
	        });
    	}
	});
}

/**
 * 问题关闭取消
 */
function deleteClose(){
	console.log('deleteClose');
	var proCode=currentItem.PRO_CODE;//问题单号
    
	top.jzts();
	
	$.ajax({
		type: "POST",
		url: '<%=basePath%>mbp/deleteClose.do',
		data:{PRO_CODE:proCode},
    	dataType:'json',
		cache: false,
		success: function(response){
			if(response.code==0){
				$(top.hangge());//关闭加载状态
				$("#btnDeleteClose").tips({
					side:3,
		            msg:'取消关闭成功',
		            bg:'#009933',
		            time:3
		        });
				/* if(currentItem.PRO_STATE=="1"){
					
				} */
				jumpStep(3);
				initList('<%=basePath%>mbp/getPageList.do?ProOperType=PROBLEM_CLOSE');
			}else{
				$(top.hangge());//关闭加载状态
				$("#btnDeleteClose").tips({
					side:3,
		            msg:'取消关闭失败,'+response.message,
		            bg:'#cc0033',
		            time:3
		        });
			}
		},
    	error: function(response) {
    		$(top.hangge());//关闭加载状态
    		var msgObj=JSON.parse(response.responseText);
			$("#btnDeleteClose").tips({
				side:3,
	            msg:'取消关闭失败,'+msgObj.message,
	            bg:'#cc0033',
	            time:3
	        });
    	}
	});
}

/**
 * 获取问题附件信息
 */
function getProAttachment(attachmentType){
	if(attachmentType=="PROBLEM_INFO"){
		$("#tbodyProInfoAttachment").html('');
	}else if(attachmentType=="PROBLEM_ANSWER"){
		$("#tbodyProAnswerAttachment").html('');
	}else if(attachmentType=="PROBLEM_CLOSE"){
		$("#tbodyProCloseAttachment").html('');
	}
	
	top.jzts();
	var proCode=currentItem.PRO_CODE;//问题单号
	$.ajax({
			type: "GET",
			url: '<%=basePath%>attachment/getAttachmentByType.do?BUSINESS_TYPE='+attachmentType+'&BILL_CODE='+proCode,
	    	data: {PRO_CODE:proCode},
			//dataType:'json',
			cache: false,
			success: function(data){
				if(data){
					$.each(data, function(i, item){
						var tr=addItemAttachment(item,i+1,attachmentType); 
						if(attachmentType=="PROBLEM_INFO"){
							$('#tbodyProInfoAttachment').append(tr);
						}else if(attachmentType=="PROBLEM_ANSWER"){
							$('#tbodyProAnswerAttachment').append(tr);
						}else if(attachmentType=="PROBLEM_CLOSE"){
							$('#tbodyProCloseAttachment').append(tr);
						}
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
				+'<a class="btn btn-xs btn-danger" onclick=delProAttachment("'+item.ATTACHMENT_ID+'","'+type+'")>'
					+'<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>'
				+'</a>'
			+'</div>'
		+'</td>'
	+'</tr>';
	return htmlLog;
}

/**
 * 上传附件
 */
function addProAttachmentByType(type){
	 var proCode=currentItem.PRO_CODE;
	 console.log(type);
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="上传附件";
	 diag.URL = '<%=basePath%>attachment/goAdd.do?BUSINESS_TYPE='+type+'&BILL_CODE='+proCode;
	 diag.Width = 460;
	 diag.Height = 290;
	 diag.Modal = true;				//有无遮罩窗口
	 diag. ShowMaxButton = true;	//最大化按钮
     diag.ShowMinButton = true;		//最小化按钮
	 diag.CancelEvent = function(){ //关闭事件
		if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			console.log('gggg');
		 	getProAttachment(type);
		}
		diag.close();
	 };
	 diag.show();
}

//删除
function delProAttachment(Id,type){
	bootbox.confirm("确定要删除吗?", function(result) {
		if(result) {
			top.jzts();
			var url = "<%=basePath%>attachment/delete.do?ATTACHMENT_ID="+Id;
			$.get(url,function(data){
				getProAttachment(type);
			});
		}
	});
}
</script>
</html>
