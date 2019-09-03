<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	/* String username="cc";
	if(session.getAttribute("SESSION_USERNAME")!=null){
		username=session.getAttribute("SESSION_USERNAME").toString();
		out.println("ssss"+username);
	}
	out.println("kkkkk"+session.getAttribute("SESSION_USERNAME")); */
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="top.jsp"%>
<style type="text/css">
.commitopacity {
	position: absolute;
	width: 100%;
	height: 100px;
	background: #7f7f7f;
	filter: alpha(opacity = 50);
	-moz-opacity: 0.8;
	-khtml-opacity: 0.5;
	opacity: 0.5;
	top: 0px;
	z-index: 99999;
}
</style>

<!-- 即时通讯 -->
<link rel="stylesheet" type="text/css"
	href="plugins/websocketInstantMsg/ext4/resources/css/ext-all.css">
<link rel="stylesheet" type="text/css"
	href="plugins/websocketInstantMsg/css/websocket.css" />
<script type="text/javascript"
	src="plugins/websocketInstantMsg/ext4/ext-all-debug.js"></script>
<script type="text/javascript"
	src="plugins/websocketInstantMsg/websocket.js"></script>
<!-- 即时通讯 -->

</head>
<body class="no-skin">
	<!-- #section:basics/navbar.layout -->

	<!-- 页面顶部¨ -->
	<%@ include file="head.jsp"%>
	<!-- <div id="websocket_button"></div> -->
	<!-- 少了此处，聊天窗口就无法关闭 -->
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
				/* ace.settings.main_container_fixed(null, false);
				ace.settings.navbar_fixed(null, false);//固定头部
				ace.settings.sidebar_fixed(null, false);//固定导航 */
			} catch (e) {
			}
		</script>
		<!-- #section:basics/sidebar -->
		<c:if test="${pd.USERNAME!='Guest'}">
			<!-- 左侧菜单 -->
			<%@ include file="left.jsp"%>
		</c:if>
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				
				<c:if test="${pd.USERNAME=='Guest'}">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>
	
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="main/index">首页</a>
							</li>
	
							<!-- <li>
								<a href="#">Tables</a>
							</li> -->
							<li class="active"><span id="subTitle">工作面板</span></li>
						</ul><!-- /.breadcrumb -->
					</div>
	
					<!-- /section:basics/content.breadcrumbs -->
				</c:if>
				
				<div class="page-content">
					<!-- #section:settings.box -->
					<div class="ace-settings-container" id="ace-settings-container">
						<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
							id="ace-settings-btn">
							<i class="ace-icon fa fa-cog bigger-130"></i>
						</div>

						<div class="ace-settings-box clearfix" id="ace-settings-box">
							<div class="pull-left width-50">
								<!-- #section:settings.skins -->
								<div class="ace-settings-item">
									<div class="pull-left">
										<select id="skin-colorpicker" class="hide">
											<option data-skin="no-skin" value="#438EB9">#438EB9</option>
											<option data-skin="skin-1" value="#222A2D">#222A2D</option>
											<option data-skin="skin-2" value="#C6487E">#C6487E</option>
											<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
										</select>
									</div>
									<span>&nbsp; 选择皮肤</span>
								</div>

								<!-- #section:settings.breadcrumbs -->
								<!-- <div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
										<label class="lbl" for="ace-settings-breadcrumbs">固定面包屑</label>
									</div> -->

								<!-- #section:settings.container -->
								<!-- <div class="ace-settings-item">
										<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
										<label class="lbl" for="ace-settings-add-container">
											居中风格
										</label>
									</div> -->

								<!-- #section:settings.sidebar -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-sidebar" /> <label class="lbl"
										for="ace-settings-sidebar"> 固定导航</label>
								</div>
								<!-- #section:settings.navbar -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-navbar" /> <label class="lbl"
										for="ace-settings-navbar">固定头部</label>
								</div>

								<!-- /section:settings.container -->
							</div>
							<!-- /.pull-left -->

							<div class="pull-left width-50">
								<!-- #section:basics/sidebar.options -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-hover" /> <label class="lbl"
										for="ace-settings-hover">折叠菜单</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-compact" /> <label class="lbl"
										for="ace-settings-compact">压缩菜单</label>
								</div>

								<!-- <div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-highlight" /> <label class="lbl"
										for="ace-settings-highlight">弹出风格</label>
								</div> -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
									<label class="lbl" for="ace-settings-add-container">
										居中风格
									</label>
								</div>

								<!-- /section:basics/sidebar.options -->
							</div>
							<!-- /.pull-left -->
						</div>
						<!-- /.ace-settings-box -->
					</div>
					<!-- /section:settings.box -->
					
					<%-- <c:if test="${pd.USERNAME=='Guest'}">
						<div class="page-header">
							<h1>
								<span id="title">工作面板</span>
								<!-- <small>
									<i class="ace-icon fa fa-angle-double-right"></i>
									静态  &amp; <span id="subTitle">工作面板</span>
								</small> -->
							</h1>
						</div>
					</c:if> --%>
					
					<div class="row">
						<c:choose>
							<c:when test="${pd.USERNAME=='Guest'}">
								<div>
									<iframe name="mainFrame" id="mainFrame" frameborder="0"
										src="login_default.do" style="margin: 0 auto; width: 100%; height: 100%;"></iframe>
								</div>
							</c:when>
							<c:otherwise>
								<div id="jzts"
									style="display: none; width: 100%; position: fixed; z-index: 99999999;">
									<div class="commitopacity" id="bkbgjz"></div>
									<div style="padding-left: 70%; padding-top: 1px;">
										<div style="float: left; margin-top: 3px;">
											<img src="static/images/loadingi.gif" />
										</div>
										<div style="margin-top: 6px;">
											<h4 class="lighter block red">&nbsp;加载中 ...</h4>
										</div>
									</div>
								</div>
								<div>
									<iframe name="mainFrame" id="mainFrame" frameborder="0"
										src="tab.do" style="margin: 0 auto; width: 100%; height: 100%;"></iframe>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->

			</div>
		</div>
		<!-- /.main-content -->
		
		<!-- <div class="footer">
				<div class="footer-inner">
					#section:basics/footer
					<div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Ace</span>
							Application &copy; 2013-2014
						</span>

						&nbsp; &nbsp;
						<span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
					</div>

					/section:basics/footer
				</div>
			</div> -->

		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="foot.jsp"%>

	<!-- page specific plugin scripts -->

	<!-- ace scripts -->
	<!-- <script src="static/ace/js/ace/elements.scroller.js"></script>
	<script src="static/ace/js/ace/elements.colorpicker.js"></script>
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<script src="static/ace/js/ace/elements.typeahead.js"></script>
	<script src="static/ace/js/ace/elements.wysiwyg.js"></script>
	<script src="static/ace/js/ace/elements.spinner.js"></script>
	<script src="static/ace/js/ace/elements.treeview.js"></script>
	<script src="static/ace/js/ace/elements.wizard.js"></script>
	<script src="static/ace/js/ace/elements.aside.js"></script>
	<script src="static/ace/js/ace/ace.js"></script>
	<script src="static/ace/js/ace/ace.ajax-content.js"></script>
	<script src="static/ace/js/ace/ace.touch-drag.js"></script>
	<script src="static/ace/js/ace/ace.sidebar.js"></script>
	<script src="static/ace/js/ace/ace.sidebar-scroll-1.js"></script>
	<script src="static/ace/js/ace/ace.submenu-hover.js"></script>
	<script src="static/ace/js/ace/ace.widget-box.js"></script>
	<script src="static/ace/js/ace/ace.settings.js"></script>
	<script src="static/ace/js/ace/ace.settings-rtl.js"></script>
	<script src="static/ace/js/ace/ace.settings-skin.js"></script>
	<script src="static/ace/js/ace/ace.widget-on-reload.js"></script>
	<script src="static/ace/js/ace/ace.searchbox-autocomplete.js"></script> -->
	
	<script src="static/ace/js/ace/elements.colorpicker.js"></script>
	<script src="static/ace/js/ace.js"></script>
	<script src="static/ace/js/ace-elements.js"></script>
	<!-- inline scripts related to this page -->

	<!-- the following scripts are used in demo only for onpage help and you don't need them -->
	<link rel="stylesheet" href="static/ace/css/ace.onpage-help.css" />

	<script type="text/javascript">
		ace.vars['base'] = '..';
	</script>
	<script src="static/ace/js/ace/elements.onpage-help.js"></script>
	<script src="static/ace/js/ace/ace.onpage-help.js"></script>

	<!--引入属于此页面的js -->
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
	<!--引入属于此页面的js -->
	<script type="text/javascript" src="static/js/myjs/index.js"></script>

	<!--引入弹窗组件1start-->
	<script type="text/javascript" src="plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="plugins/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件1end-->

	<!--引入弹窗组件2start-->
	<!-- <script type="text/javascript" src="plugins/attention/drag/drag.js"></script>
	<script type="text/javascript" src="plugins/attention/drag/dialog.js"></script> -->
	<link type="text/css" rel="stylesheet"
		href="plugins/attention/drag/style.css" />
	<!--引入弹窗组件2end-->

	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	
	<!-- <script>
		window.onload=function(){
			console.log($("#mainFrame").css("height"));
			var source=$("#mainFrame").css("height").toString().replace("px","");
			console.log(source);
			var a=source-102;
			console.log(a);
			$("#mainFrame").css("height",a+"px");
		}
	</script> -->
</body>
</html>