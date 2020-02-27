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
<%@ include file="../system/index/top.jsp"%>
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
						<div class="col-xs-4">
							<!-- 检索  -->
							<form action='knowledge/getPageList.do'>
							</form>
							<div class="nav-search" style="margin:10px 0px;">
								<span class="input-icon" style="width:86%">
									<input style="width:100%" class="nav-search-input" autocomplete="off" id="keywords" type="text" name="keywords" value="${pd.keywords }" placeholder="这里输入知识标题" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
								<button style="margin-bottom:3px;" class="btn btn-light btn-minier" onclick="searchs();"  title="检索">
									<i id="nav-search-icon" class="ace-icon fa fa-search bigger-120 nav-search-icon blue"></i>
									<!-- <i class="ace-icon fa fa-signal icon-only bigger-150"></i> -->
								</button>
							</div>
							
							<ul id="knowledgeList" class="item-list">
								
							</ul>						

							<div id="page" class="pagination" style="float: right;padding-top: 5px;margin-top: 0px;font-size:12px;"></div>
							
						</div>
						<!-- /.col4 -->
						
						<div class="col-xs-8">
							<!-- 创建新知识 -->
							<div>
								<h4>
									<!-- <i class="ace-icon fa fa-list"></i>
									Sortable Lists -->
									<button id="btnAdd" class="btn btn-success btn-xs"
										onclick="add()">
										<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>新增</span>
									</button>
									<button id="btnEdit" class="btn btn-warning btn-xs"
										onclick="edit()">
										<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>编辑</span>
									</button>
									<button id="btnSave" class="btn btn-info btn-xs"
										onclick="save()">
										<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>保存</span>
									</button>
									<button id="btndelete" class="btn btn-danger btn-xs"
										onclick="del()">
										<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>作废</span>
									</button>
								</h4>
								<div class="row">
									<div class="col-xs-12">
										<form name="knowledgeBaseForm" id="knowledgeBaseForm" method="post">
											<input type="hidden" name="KNOWLEDGE_ID" id="form-field-knowledge-id" value="${pd.KNOWLEDGE_ID }"/>
											<div style="padding-top: 13px;">
												<div style="margin:10px 0px;">
													<label for="form-field-knowledge-title">知识标题</label>
													<input type="text" name="KNOWLEDGE_TITLE" id="form-field-knowledge-title" class="form-control" placeholder="请输入知识标题"/>
												</div>
												
												<div style="margin:10px 0px;">
													<label for="form-field-knowledge-type">知识类别</label>
													<select class="form-control" name="KNOWLEDGE_TYPE" id="form-field-knowledge-type">
														<option value=""></option>
														<c:forEach items="${knowledgeTypeList}" var="knowledge">
															<option value="${knowledge.KNOWLEDGE_TYPE_ID }" <c:if test="${knowledge.KNOWLEDGE_TYPE_ID == pd.KNOWLEDGE_TYPE }">selected</c:if>>${knowledge.KNOWLEDGE_TYPE_NAME }</option>
															<%-- <option value="${knowledge.KNOWLEDGE_TYPE_ID}">${knowledge.KNOWLEDGE_TYPE_NAME}</option> --%>
														</c:forEach>
													</select>
												</div>
												
												<!-- <div style="margin:10px 0px;">
													<label for="form-field-pro-type-id">知识类别</label>
													<input type="hidden" name="KNOWLEDGE_TYPE" id="form-field-knowledge-type" />
													<div class="selectTree" id="selectTree" style="float:none;display:block;"></div>
													
												</div> -->
												
												<div style="margin:10px 0px;">
													<label for="form-field-knowledge-tag">标签（多个标签使用空格分隔）</label>
													<input type="text" name="KNOWLEDGE_TAG" id="form-field-knowledge-tag" class="form-control" placeholder="请输入标签"/>
												</div>
												
												<div style="margin:10px 0px;">
													<label for="form-field-author">作者/出处</label>
													<input type="text" name="AUTHOR" id="form-field-author" class="form-control" placeholder="请输入作者/出处"/>
												</div>
												<textarea name="DETAIL" id="form-field-detail" style="display:none" ></textarea>
												<div style="margin:10px 0px;">
													<label for="editor">正文</label>
													<script id="editor" type="text/plain" style="width:100%;height:259px;"></script>
												</div>
												
												<!-- <div style="margin:20px 0px;">
													<span>分配人：</span><span>张三</span>
													<span style="margin-left:30px;">分配时间：</span><span>2019-05-23</span>
												</div>	 -->
											</div>
										</form>
									</div>
									
								</div>
								<!-- <div class="row">
									<div style="margin:10px 12px;">
										<label for="editor">正文</label>
										<script id="editor" type="text/plain" style="width:100%;height:259px;"></script>
									</div>
								</div> -->
								<div class="row">
									<div style="margin:10px 12px;">
										<h4>
											<button id="btnAddAttachment" class="btn btn-success btn-xs"
												onclick="addAttachmentByType('KNOWLEDGE')">
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
																	
											<tbody id="tbodyAttachment">
												
											</tbody>
										</table>
									</div>
								</div>
							</div>
							
							<!-- 问题回复 -->
						<!-- /.col8 -->
						</div>
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
	<%@ include file="../system/index/foot.jsp"%>
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
	
	<!-- 编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
	<!-- 编辑框-->
	
	<script type="text/javascript">
		var currentItem;
	
		$(top.hangge());//关闭加载状态
		$(function() {
		
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
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
			
			/* 加载富文本 */
			setTimeout("ueditor()",500);
			
			//初始化字段为只读
			initFieldDisabled(true);
			
			console.log('init');
			//初始化问题列表数据
			initList('<%=basePath%>knowledge/getPageList.do');
		});		
		
		/**
		 * 加载富文本 
		 */
		function ueditor(){
			var ue = UE.getEditor('editor');
			//UE.getEditor('editor').setDisabled();
		}
		
		/**
		 * 初始化字段为只读
		 */
		function initFieldDisabled(disabled){
			//$("#form-field-knowledge-id").attr("readonly",true);
			$("#form-field-knowledge-title").attr("disabled",disabled);
			$("#form-field-knowledge-type").attr("disabled",disabled);
			$("#form-field-knowledge-tag").attr("disabled",disabled);
			$("#form-field-author").attr("disabled",disabled);
			
			UE.getEditor('editor').addListener("ready", function () {
				UE.getEditor('editor').setDisabled();
			});
			
			if(UE.getEditor('editor').isReady){
				if(disabled){
					UE.getEditor('editor').setDisabled();
	
				}else{
					UE.getEditor('editor').setEnabled();
				}
			}
		}
		
		//检索
		function searchs(){
			initList('<%=basePath%>knowledge/getPageList.do');
		}
		
		/**
		 * 初始化列表信息
		 */
		function initList(url){
			$("#knowledgeList li").remove(); 
			top.jzts();
			var keywords = $("#keywords").val();
			$.ajax({
					type: "POST",
					url: url,
			    	data: {keywords:keywords},
					dataType:'json',
					cache: false,
					success: function(data){
						//console.log(data.pageHtml);
						$("#page").html(data.pageHtml);
						var first;
						if(data.rows){
							$.each(data.rows, function(i, item){
								if(i==0){
									first=item;
								}
								//console.log(item);
								addItem(item); 
						 	});
							if(first){
								getDetail(first.KNOWLEDGE_ID);
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
			var htmlItem='<li class="item-grey clearfix list-item-hover" onclick=getDetail("'+item.KNOWLEDGE_ID+'")>'
			+'<input name="PRO_CODE" id="PRO_CODE" type="hidden" value="'+item.KNOWLEDGE_ID+'" />'
				+'<div>'
					+'<label class="inline" style="margin-bottom:5px;">'
						+'<span class="list-item-value-title">'+item.KNOWLEDGE_TITLE+'</span>'
					+'</label>'
				+'<div>'
				+'<div>'
					+'<label class="inline">'
						+'<span class="list-item-info"> 类别：</span>'
						+'<span class="list-item-value">'+item.KNOWLEDGE_TYPE_NAME+'</span>'
					+'</label>'
					/* +'<label class="inline pull-right">'
						+'<span class="list-item-info"> 浏览数：</span>'
						+'<span class="list-item-value">'+item.READ_NUM+'</span>'
					+'</label>' */
				+'</div>'
				
				+'<div>'
					+'<label class="inline">'
						+'<span class="list-item-info"> 标签：</span>'
						+'<span class="list-item-value">'+item.KNOWLEDGE_TAG+'</span>'
					+'</label>'
				+'</div>'
				+'<div class="time">'
					+'<i class="ace-icon fa fa-clock-o"></i>'
					+'<span class="grey">'+item.CREATE_DATE+'</span>'
				+'</div>'
			+'</li>';
			$("#knowledgeList").append(htmlItem);
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
			$("#knowledgeList").append(htmlEmpty);
		}
		
		/**
		 * 获取明细信息
		 */
		function getDetail(knowledgeID){
			if(event){
				$("#knowledgeList li").each(function(){
					var item = this;
					$(item).removeClass("bc-light-orange");
				}); 
				$($(event.srcElement).closest('li')).addClass("bc-light-orange");
			}else{
				$("#knowledgeList li").first().addClass("bc-light-orange");
				//$($(event.srcElement).parents('li')).addClass("bc-light-orange");
			}
			//();//清空原有问题提报中信息

			initFieldDisabled(true);
			
			$.ajax({
				type: "GET",
				url: '<%=basePath%>knowledge/getDetail.do?KNOWLEDGE_ID='+knowledgeID,
				dataType:'json',
				cache: false,
				success: function(data){
					 if(data){
						 currentItem=data;
						 
						 /* $("#valPRO_TITLE").text(data.PRO_TITLE);
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
						 $("#valPRO_CONTENT").html(data.PRO_CONTENT); */
						 
						 /* 设置提报中的字段动态获取值 */
						 setReportFieldValue(data);
						 
						 /* 获取提报中附件信息 */
						 getAttachment("KNOWLEDGE"); 
					 }
					 top.hangge();
				}
			});
		}
		
		/**
		 * 保存
		 */
		function save(){
			if ($("#form-field-knowledge-title").attr('disabled') == 'disabled') {
				$("#btnSave").tips({
					side : 3,
					msg : '请先编辑后再保存',
					bg : '#AE81FF',
					time : 2
				});
				$("#btnSave").focus();
				return false;
			}
			
			if ($.trim($("#form-field-knowledge-title").val()) == "") {
				$("#form-field-pro-title").tips({
					side : 3,
					msg : '请输入知识标题',
					bg : '#AE81FF',
					time : 2
				});
				$("#form-field-knowledge-title").focus();
				return false;
			}
			/* if ($("#form-field-pro-type-id").val()==null||$("#form-field-pro-type-id").val()=="") {
				$("#form-field-pro-type-id").tips({
					side : 3,
					msg : '请选择问题类型',
					bg : '#AE81FF',
					time : 2
				});
				$("#form-field-pro-type-id").focus();
				return false;
			} */
			
			var content;
			var arr = [];
		    arr.push(UE.getEditor('editor').getContent());
		    content=arr.join("");
			$("#form-field-detail").val(content);
			
			top.jzts();
			var knowledgeID=$("#form-field-knowledge-id").val();//知识ID
			var knowledgeTitle=$("#form-field-knowledge-title").val();//知识标题
			//上报单位
			var knowledgeType=$("#form-field-knowledge-type").val();//知识类别
			var knowledgeTag=$("#form-field-knowledge-tag").val();//标签
			
			var knowledgeAuthor=$("#form-field-author").val();//作者/出处
			var knowledgeDetail=$("#form-field-detail").val();//正文
			$.ajax({
				type: "POST",
				url: '<%=basePath%>knowledge/save.do',
				data:{KNOWLEDGE_ID:knowledgeID,KNOWLEDGE_TITLE:knowledgeTitle,KNOWLEDGE_TYPE:knowledgeType,KNOWLEDGE_TAG:knowledgeTag,AUTHOR:knowledgeAuthor,DETAIL:knowledgeDetail},
		    	dataType:'json',
				cache: false,
				success: function(response){
					if(response.code==0){
						$(top.hangge());//关闭加载状态
						$("#btnSave").tips({
							side:3,
				            msg:'保存任务成功',
				            bg:'#009933',
				            time:3
				        });
						initList('<%=basePath%>knowledge/getPageList.do');
					}else{
						$(top.hangge());//关闭加载状态
						$("#btnSave").tips({
							side:3,
				            msg:'保存任务失败,'+response.message,
				            bg:'#cc0033',
				            time:3
				        });
					}
				},
		    	error: function(response) {
		    		var msgObj=JSON.parse(response.responseText);
		    		$(top.hangge());//关闭加载状态
					$("#btnSave").tips({
						side:3,
			            msg:'保存任务失败,'+msgObj.message,
			            bg:'#cc0033',
			            time:3
			        });
		    	}
			});
		}

		/**
		 * 增加
		 */
		function add(){
			$("#knowledgeList li").each(function(){
				var item = this;
				$(item).removeClass("bc-light-orange");
			}); 
			currentItem=null;
			
			initFieldDisabled(false);
			
			$("#form-field-knowledge-id").val("");
			$("#form-field-knowledge-title").val("");
			$("#form-field-knowledge-type").val("");
			$("#selectTree2_input").val("");
			$("#form-field-knowledge-tag").val("");
			$("#form-field-author").val("");
			$("#form-field-detail").val("");
			UE.getEditor('editor').setContent("");
			
			$("#tbodyAttachment").html('');
		}

		/**
		 * 编辑
		 */
		function edit(){
			initFieldDisabled(false);
			
			//setReportFieldValue(currentItem);
		}

		/**
		 * 设置提报中字段值
		 */
		function setReportFieldValue(item){
			$("#form-field-knowledge-id").val(item.KNOWLEDGE_ID);
			$("#form-field-knowledge-title").val(item.KNOWLEDGE_TITLE);
			$("#form-field-knowledge-type").val(item.KNOWLEDGE_TYPE);
			$("#form-field-knowledge-tag").val(item.KNOWLEDGE_TAG);
			$("#form-field-author").val(item.AUTHOR);
			/* var knowledgeTypeName="";
			if(item.KNOWLEDGE_TYPE!=null&&item.KNOWLEDGE_TYPE!=""){
				knowledgeTypeName=item.KNOWLEDGE_TYPE_NAME;
			}else{
				knowledgeTypeName="请选择知识类别"
			}
			$("#selectTree2_input").val(knowledgeTypeName); */
			
			$("#form-field-detail").val(item.DETAIL);
			UE.getEditor('editor').addListener("ready", function () {
				UE.getEditor('editor').setContent(item.DETAIL);
			});
			
			if(UE.getEditor('editor').isReady&&item.DETAIL){
		    	UE.getEditor('editor').setContent(item.DETAIL);
			}
		}

		/**
		 * 作废
		 */
		function del(){
			bootbox.confirm("确定要作废当前问题吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>knowledge/delete.do?KNOWLEDGE_ID="+currentItem.KNOWLEDGE_ID;
					$.get(url,function(data){
						initList('<%=basePath%>knowledge/getPageList.do');
					});
				};
			});
		}
		
		/**
		 * 获取附件信息
		 */
		function getAttachment(attachmentType){
			$("#tbodyAttachment").html('');
			top.jzts();
			var knowledgeID=currentItem.KNOWLEDGE_ID;//知识ID
			$.ajax({
					type: "GET",
					url: '<%=basePath%>attachment/getAttachmentByType.do?BUSINESS_TYPE='+attachmentType+'&BILL_CODE='+knowledgeID,
			    	//data: {},
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
						+'<a class="btn btn-xs btn-danger" onclick=delAttachment("'+item.ATTACHMENT_ID+'","'+type+'")>'
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
		function addAttachmentByType(type){
			if(currentItem==null){
				$("#btnAddAttachment").tips({
					side:3,
		            msg:'请先保存知识信息后，再上传附件',
		            bg:'#cc0033',
		            time:3
		        });
				return;
			}
			 var knowledgeID=currentItem.KNOWLEDGE_ID;
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="上传附件";
			 diag.URL = '<%=basePath%>attachment/goAdd.do?BUSINESS_TYPE='+type+'&BILL_CODE='+knowledgeID;
			 diag.Width = 460;
			 diag.Height = 290;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				 	getAttachment(type);
				}
				diag.close();
			 };
			 diag.show();
		}

		//删除
		function delAttachment(Id,type){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>attachment/delete.do?ATTACHMENT_ID="+Id;
					$.get(url,function(data){
						getAttachment(type);
					});
				}
			});
		}
	</script>
</body>
</html>