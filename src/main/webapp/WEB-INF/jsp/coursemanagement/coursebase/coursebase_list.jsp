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
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<link type="text/css" rel="stylesheet" href="plugins/zTree/2.6/zTreeStyle.css"/>
<script type="text/javascript" src="plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<style type="text/css">
	.course-box{
		margin:5px 15px 15px 15px;
		width:170px;
		height:350px;
	}
	.title-box{
		margin:10px 0px 0px 5px;
		width:170px;
	}
 	.btm-box{
		margin:5px 0px 0px 5px;
		width:170px;
	}
	.high-font:hover{
    	color:red;
	}
</style>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
						<form class="form-inline" action="coursebase/list.do" method="post" name="Form" id="Form">
							<div class="page-header">
								<label class="pull-left" style="padding: 5px;">筛选条件：</label>
										<span class="input-icon nav-search" style="margin-left: 14px;">
											<i class="ace-icon fa fa-search nav-search-icon"></i>
											<input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="keywords" value="${pd.keywords}" placeholder="请输入课件名称" />
										</span>
									<span style="margin-left:-15px;"> 
									</span>
									<button style="margin-bottom:3px;" class="btn btn-info btn-sm" onclick="search();" title="检索">
											<i class="ace-icon fa fa-search bigger-110"></i>
									</button>
									<div class="pull-right">
									<a id="btnSave" class="btn btn-info btn-xs" onclick="add()">
										<i class="ace-icon fa fa-chevron-down bigger-110"></i> <span>新增</span>
									</a>
								</div>
							</div>
						<div class="row">
							<div class="col-xs-3">
								<div class="widget-box transparent" style="width:100%;height: 100%">
									<div class="widget-body" style="width:100%;height: 600px;">
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
														<div>
															<ul id="leftTree" class="tree"></ul>
															<input id="SelectedDepartCode" type="hidden"></input>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="changeDiv" class="col-xs-9">
							<!-- 循环遍历 -->
								<c:forEach items="${varList}" var="pd">
									<div class="col-xs-9 course-box"><!-- 整体 -->
										<div style="width:170px;height:170px;">
											<a href="<%=basePath%>coursedetail/list.do?COURSE_ID=${pd.COURSE_ID}" data-rel="colorbox" class="cboxElement" title="${pd.COURSE_NOTE}">
												<img width="170" height="170" alt="请上传图片" src="${pd.COURSE_COVER}">
											</a>
										</div>
										<div>
											<div class="title-box">
												<span class="list-item-value-title">${pd.COURSE_NAME}</span>
											</div>		
											<div class="title-box">
												<a class="list-item-info fyhoverflow high-font" href="<%=basePath%>coursedetail/list.do?COURSE_ID=${pd.COURSE_ID}" title="${pd.COURSE_NOTE}">${pd.COURSE_NOTE}</a>
											</div>
											<div class="title-box">
												<span class="list-item-info">${pd.COURSE_TAG}</span>
											</div>
											<div  class="title-box">
												<i class="ace-icon fa fa-clock-o"></i>
												<span class="list-item-info">${pd.CREATE_DATE}</span>
												<i style="padding-left: 6%" class="ace-icon glyphicon glyphicon-user"></i>
												<span class="list-item-info">${pd.COURSE_TEACHER}</span>
											</div>
											<div class="btm-box">
												<a class="btn btn-mini btn-info" title="编辑" onclick="edit('${pd.COURSE_ID}');"><i class="ace-icon fa fa-pencil-square-o bigger-130"></i>编辑</a>
												<a style="float:right;" class="btn btn-mini btn-danger" title="删除" onclick="del('${pd.COURSE_ID}');">删除<i class="ace-icon fa fa-trash-o bigger-130"></i></a>
											</div>					
										</div>
									</div>
								</c:forEach>
							</div>							
							 <div class="col-xs-9 position-relative">
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
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		var zTree;
		$(document).ready(function(){
			var setting = {
			    showLine: true,
			    checkable: false,
			    callback:{
			    	beforeClick: getCurrentNode,
			    	onClick: zTreeBeforeClick
			    }
			};
			var zn = '${zTreeNodes}';
			var zTreeNodes = eval(zn);
			zTree = $("#leftTree").zTree(setting, zTreeNodes);
			//限制字符个数
            $(".fyhoverflow").each(function() {
                var maxwidth = 11;
                if ($(this).text().length > maxwidth) {
                    $(this).text($(this).text().substring(0, maxwidth));
                    $(this).html($(this).html() + '...');
                }
            });
		});
		function getCurrentNode(leftTree, treeNode) {
            curNode = treeNode;
            zTreeBeforeClick(curNode);
        }
		
		function zTreeBeforeClick(treeNode){
			//通过id树查询数据
			$.ajax({
				type: "POST",
				url: '<%=basePath%>coursebase/listById.do?tm='+new Date().getTime(),
		    	data: {"COURSE_TYPE":treeNode.id},
				dataType:'json',
				cache: false,
				success: function(response){
					$(top.hangge());//关闭加载状态
					//动态加载变更数据
					getchangeDiv(eval(response.message));
				}
			});		
		}
		
		function getchangeDiv(data){
			var html = '';
			for(var i = 0;i<data.length;i++){
		        html += setDiv(data[i]);
			}
			//$('#changeDiv').remove();
			$('#changeDiv').html(html);
		}
		
		function setDiv(data){//上传以后这里动态回显
			var id = data.COURSE_ID;
			var url = '<%=basePath%>coursedetail/list.do?COURSE_ID='+id;
			var div = '<div class="col-xs-9 course-box">'
					+ '<div style="width:170px;height:170px;"><a href="'+ url + '" data-rel="colorbox" class="cboxElement" title="'+ data.COURSE_NOTE +'">'
					
					+ '<img width="170" height="170" alt="请上传图片" src="'+ data.COURSE_COVER +'"></a></div>'
					
					+'<div class="title-box"><span class="list-item-value-title">' + data.COURSE_NAME + '</span></div>'
					
					+'<div class="title-box"><a class="list-item-info fyhoverflow" href="'+ url +'" title="'+ data.COURSE_NOTE +'">' + data.COURSE_NOTE +'</a></div>'
					
					+'<div class="title-box"><span class="list-item-info">' + data.COURSE_TAG + '</span></div>'
				
					+'<div class="title-box"><i class="ace-icon fa fa-clock-o"></i><span class="list-item-info">'+ data.CREATE_DATE +'</span>'
					
					+'<i style="padding-left: 6%" class="ace-icon glyphicon glyphicon-user"></i><span class="list-item-info">'+ data.COURSE_TEACHER +'</span></div>'
					
					+'<div class="btm-box"><a class="btn btn-mini btn-info" title="编辑" onclick="edit(\''+data.COURSE_ID+'\');"><i class="ace-icon fa fa-pencil-square-o bigger-130"></i>编辑</a>'
					
					+'<a style="float:right;" class="btn btn-mini btn-danger" title="删除" onclick="del(\''+data.COURSE_ID+'\');">删除<i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>'
					+'</div></div>';
			return div;
		}
		
		//检索数据
		function search(){
			top.jzts();
			$("#Form").submit();
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
			 diag.URL = '<%=basePath%>coursebase/goEdit.do?COURSE_ID='+COURSE_ID;
			 diag.Width = 600;
			 diag.Height = 410;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 history.go(0); //刷新页面
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>coursebase/goAdd.do';
			 diag.Width = 600;
			 diag.Height = 410;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 history.go(0); //刷新页面
				}
				diag.close();
			 };
			 diag.show();
		}
		
	</script>
</body>
</html>