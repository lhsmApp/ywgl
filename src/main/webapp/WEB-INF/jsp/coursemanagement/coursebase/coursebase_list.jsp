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
<link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"/>
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<style type="text/css">
	.course-box{
		margin:15px 0px 5px 0px;
		
	}
	.title-box{
		margin:10px 0px 0px 5px;
	}
	.btm-box{
		margin:5px 0px 0px 5px;
	}
	.course-box:hover{
    	color:red;
	}
</style>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="well well-lg">
						<form class="form-inline" action="coursebase/list.do" method="post" name="Form" id="Form">
							<div class="page-header">
								<label class="pull-left" style="padding: 5px;">筛选条件：</label>
										<span class="input-icon nav-search" style="margin-left: 14px;">
											<i class="ace-icon fa fa-search nav-search-icon"></i>
											<input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="keywords" value="${pd.keywords}" placeholder="请输入课件名称" />
										</span>
									<span style="margin-left:-15px;"> 
										<select class="chosen-select form-control" name="FMISACC" id="FMISACC" data-placeholder="请选择课件名称" style="vertical-align: top; height: 32px; width: 150px;">
												<option value="">课件名称</option>
												<%-- <c:forEach items="" var=""></c:forEach> --%>
													<option value="">1</option>
													<option value="">2</option>
													<option value="">3</option>
												
										</select>
									</span>
									<button style="margin-bottom:3px;" class="btn btn-info btn-sm" onclick="searchs();" title="检索">
											<i class="ace-icon fa fa-search bigger-110"></i>
									</button>
									<div class="pull-right">
										<a id="btnAdd" onclick="showQueryCondi()">
											<i class="ace-icon fa fa-plus-circle purple"></i><span>新增</span>
										</a>
									</div>
								</div>
								
						<!-- /.page-header -->
						<div class="row">
							<div class="col-xs-3">
								<div class="widget-box transparent">
									<div class="widget-body">
										<div class="widget-main no-padding">
											<!-- TODO -->
											<div class="widget-box">
												<div class="widget-header">
													<div class="widget-header widget-header-flat">
														<h5 class="widget-title lighter">
															<i class="ace-icon fa fa-list"></i> 知识结构
														</h5>
														<div class="widget-toolbar"></div>
													</div>
												</div>
												<div class="widget-body">
													<div class="widget-main padding-8">
														<div class="nav-search" style="margin:10px 0px;">
															<span class="input-icon">
																<input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="keywords" value="" placeholder="请输入知识结构名称">
																<i class="ace-icon fa fa-search nav-search-icon"></i>
															</span>
															<button style="margin-bottom:3px;" class="btn btn-light btn-minier" onclick="searchs();" title="检索">
																<i id="nav-search-icon" class="ace-icon fa fa-search bigger-120 nav-search-icon blue"></i>
																<!-- <i class="ace-icon fa fa-signal icon-only bigger-150"></i> -->
															</button>
														</div>
														<div>
															<ul id="leftTree" class="ztree"></ul>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						<!-- /.col-xs-3 -->
							<div class="col-xs-9">
								<div class="col-xs-12 widget-header-flat widget-header widget-body" style="margin-top: 3px; height: 55px ">
									<h5 class="lighter" style="float: right; margin-top:20px;"><i class="ace-icon fa fa-bar-chart-o"></i>课件创建时间</h5>
								</div>						
							</div>
							<div class="col-xs-9 widget-body font-size" style="margin-top: 30px">
							<!-- 循环遍历 -->
								<c:forEach items="${varList}" var="pd">
									<div class="col-sm-3 course-box"><!-- 整体 -->
										<div style="text-align:center">
											<a href="" data-rel="colorbox" class="cboxElement">
													<img width="160" height="160" alt="160x160" src="static/html_UI/assets/images/gallery/thumb-3.jpg">
												</a>
											<!-- <div class="text inner">
												<font style="vertical-align: inherit;">悬停时的示例字幕</font>
											</div>
										 -->
										</div>
										<div>
											<div class="title-box">
												<span class="list-item-value-title">${pd.COURSE_NAME}</span>
											</div>		
											<div class="title-box">
												<span class="list-item-info">${pd.COURSE_NOTE}|12人正在学习</span>
											</div>
											<div class="title-box">
												<i class="ace-icon fa fa-clock-o"></i>
												<span class="list-item-value red">${pd.CREATE_DATE}</span>
												<i style="padding-left: 6%" class="ace-icon glyphicon glyphicon-user"></i>
												<span class="list-item-value">${pd.COURSE_TEACHER}</span>
											</div>
											<div class="btm-box">
												<a class="btn btn-mini btn-info" title="编辑" onclick="edit('${pd.COURSE_ID}');"><i class="ace-icon fa fa-pencil-square-o bigger-130"></i>编辑</a>
												<a style="float:right; margin-right:10px" class="btn btn-mini btn-danger" title="删除" onclick="del('${pd.COURSE_ID}');">删除<i class="ace-icon fa fa-trash-o bigger-130"></i></a>
											</div>					
										</div>
									</div>
								</c:forEach>
							</div>							
							<div class="col-xs-9 page-header position-relative">
								<table style="width:100%;">
									<tr>
										<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
									</tr>
								</table>
							</div>
								<!-- 分页 -->
							</div>
						</form>
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
	<!--提示框-->
	<script src="static/js/jquery.tips.js"></script>
	<!-- 树形菜单 -->
	<script type="text/javascript" src="plugins/zTree/3.5/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.excheck.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		var zTreeObj={};
		var setting = {
			    showLine: true,
			    checkable: false
			};
		//页面加载生成树
		$(function(){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>coursebase/treeList.do?tm='+new Date().getTime(),
		    	//data: {"listData":JSON.stringify(e)},
				dataType:'json',
				cache: false,
				success: function(response){
					$(top.hangge());//关闭加载状态
					console.log(response.message);
					initZtree(response.message);
				}
			});		
			
		});
		//回调函数
		function initZtree(data){
			zTreeObj = $.fn.zTree.init($("#leftTree"), setting, eval(data));	
		}
		
		//删除
		function del(ID){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>coursebase/delete.do?tm='+new Date().getTime(),
				    	data: {"COURSE_ID":ID},
						dataType:'json',
						cache: false,
						success: function(response){
							$(top.hangge());//关闭加载状态
							history.go(0); //刷新页面
						},
						error:function(e){
							$(top.hangge());//关闭加载状态
							history.go(0); //刷新页面
						}
					});	
				}
			});
		}
		
		//修改
		function edit(COURSE_ID){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>coursebase/goEdit.do?COURSEBASE_ID='+COURSE_ID;
			 diag.Width = 600;
			 diag.Height = 380;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
	</script>


	
	<script type="text/javascript">
<%-- 		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
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
			 diag.URL = '<%=basePath%>coursebase/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
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
								url: '<%=basePath%>coursebase/deleteAll.do?tm='+new Date().getTime(),
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
		}; --%>
		
	</script>


</body>
</html>