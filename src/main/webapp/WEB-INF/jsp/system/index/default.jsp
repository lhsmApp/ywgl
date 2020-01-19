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

<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>

</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content" >
			<img alt="" style="width:100%;height:auto;" src="static/images/mainbg.jpg">
		
			<!-- <div class="main-content-inner">
				<div class="page-content">
					<h5 class="header smaller lighter green">
						<i class="ace-icon fa fa-cog"></i>
					导航</h5>
					<div class="row">
						<div class="col-xs-12 infobox-container">

							<div class="row">
								<div class="col-xs-3">
									<div class="infobox infobox-green infobox-dark btn btn-success" onclick="nav1()">	
										<div class="infobox-icon">
											<i class="ace-icon fa fa-rss"></i>
										</div>
					
										<div class="infobox-data">
											<div class="infobox-content">150</div>
											<div class="infobox-content">当前问题总数</div>
										</div>
									</div>
								</div>
							
								<div class="col-xs-3">
									<div class="infobox infobox-blue infobox-dark btn btn-info" onclick="nav2()">
										<div class="infobox-icon">
											<i class="ace-icon fa fa-signal"></i>
										</div>
					
										<div class="infobox-data">
											<div class="infobox-content"><span class="percent">61</span>%</div>
											<div class="infobox-content">本周受理完成率</div>
										</div>
									</div>
								</div>
				
								<div class="col-xs-3">
									<div class="infobox infobox-orange infobox-dark btn btn-warning" onclick="nav3()">
										<div class="infobox-icon">
											<i class="ace-icon fa fa-download"></i>
										</div>
					
										<div class="infobox-data">
											<div class="infobox-content">44</div>
											<div class="infobox-content">在线用户</div>
										</div>
									</div>
								</div>
								
								<div class="col-xs-3">
									<div class="infobox infobox-red infobox-dark btn btn-danger" onclick="nav4()">
										<div class="infobox-icon">
											<i class="ace-icon fa fa-facebook-square"></i>
										</div>
					
										<div class="infobox-data">
											<div class="infobox-content">65</div>
											<div class="infobox-content">本周业务统计</div>
											
										</div>
									</div>
								</div>
							</div>

						</div>

					</div>
					<div class="hr hr20 hr-dotted"></div>
					
					<h5 class="header smaller lighter blue">
							<i class="ace-icon fa fa-wrench"></i>
						我受理的问题</h5>
					<div class="row">
						<div class="col-xs-12">
							<div class="row" style="margin-left:20px;margin-right:20px;margin-top:0px;margin-bottom:0px;">
								<div class="col-xs-3">
									<div>
										<div class="easy-pie-chart percentage" data-percent="0" data-color="#D15B47">
											<span class="percent">0</span>%
										</div>
										<div style="padding: 5px;" class="blue">今日受理问题</div>
									</div>
								</div>
								
								<div class="col-xs-3">
									<div class="easy-pie-chart percentage" data-percent="55" data-color="#87CEEB">
										<span class="percent">55</span>%
									</div>
									<div style="padding: 5px;" class="blue">受理紧急问题</div>
								</div>
				
								<div class="col-xs-3">
									<div class="easy-pie-chart percentage" data-percent="41" data-color="#87B87F">
										<span class="percent">41</span>%
									</div>
									<div style="padding: 5px;" class="blue">受理普通问题</div>
								</div>
								
								<div class="col-xs-3">
									<div class="easy-pie-chart percentage" data-percent="100" data-color="#87B87F">
										<span class="percent">100</span>%
									</div>
									<div style="padding: 5px;" class="blue">受理好评率</div>
								</div>
							</div>
						</div>
					</div>
					<div class="hr hr20 hr-dotted"></div>
					
					<div class="row">
						<div class="col-sm-6">
							<div class="widget-box transparent">
								<div class="widget-header widget-header-flat">
									<h5 class="widget-title lighter">
										<i class="ace-icon fa fa-star orange"></i>
										信息提醒
									</h5>

									<div class="widget-toolbar">
										<a href="#" data-action="collapse">
											<i class="ace-icon fa fa-chevron-up"></i>
										</a>
									</div>
								</div>

								<div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered table-striped">
											<thead class="thin-border-bottom">
												<tr>
													<th>
														<i class="ace-icon fa fa-caret-right blue"></i>name
													</th>

													<th>
														<i class="ace-icon fa fa-caret-right blue"></i>price
													</th>

													<th class="hidden-480">
														<i class="ace-icon fa fa-caret-right blue"></i>status
													</th>
												</tr>
											</thead>

											<tbody>
												<tr>
													<td>internet.com</td>

													<td>
														<small>
															<s class="red">$29.99</s>
														</small>
														<b class="green">$19.99</b>
													</td>

													<td class="hidden-480">
														<span class="label label-info arrowed-right arrowed-in">on sale</span>
													</td>
												</tr>

												<tr>
													<td>online.com</td>

													<td>
														<b class="blue">$16.45</b>
													</td>

													<td class="hidden-480">
														<span class="label label-success arrowed-in arrowed-in-right">approved</span>
													</td>
												</tr>

												<tr>
													<td>newnet.com</td>

													<td>
														<b class="blue">$15.00</b>
													</td>

													<td class="hidden-480">
														<span class="label label-danger arrowed">pending</span>
													</td>
												</tr>

												<tr>
													<td>web.com</td>

													<td>
														<small>
															<s class="red">$24.99</s>
														</small>
														<b class="green">$19.95</b>
													</td>

													<td class="hidden-480">
														<span class="label arrowed">
															<s>out of stock</s>
														</span>
													</td>
												</tr>

												<tr>
													<td>domain.com</td>

													<td>
														<b class="blue">$12.00</b>
													</td>

													<td class="hidden-480">
														<span class="label label-warning arrowed arrowed-right">SOLD</span>
													</td>
												</tr>
											</tbody>
										</table>
									</div>/.widget-main
								</div>/.widget-body
							</div>/.widget-box
						</div>/.col

						<div class="col-sm-6">
							<div class="widget-box transparent">
								<div class="widget-header widget-header-flat">
									<h5 class="widget-title lighter">
										<i class="ace-icon fa fa-signal"></i>
										受理问题列表
									</h5>

									<div class="widget-toolbar">
										<a href="#" data-action="collapse">
											<i class="ace-icon fa fa-chevron-up"></i>
										</a>
									</div>
								</div>

								<div class="widget-body">
									<div class="widget-main padding-4">
										<table class="table table-bordered table-striped">
											<thead class="thin-border-bottom">
												<tr>
													<th>
														<i class="ace-icon fa fa-caret-right blue"></i>name
													</th>

													<th>
														<i class="ace-icon fa fa-caret-right blue"></i>price
													</th>

													<th class="hidden-480">
														<i class="ace-icon fa fa-caret-right blue"></i>status
													</th>
												</tr>
											</thead>

											<tbody>
												<tr>
													<td>internet.com</td>

													<td>
														<small>
															<s class="red">$29.99</s>
														</small>
														<b class="green">$19.99</b>
													</td>

													<td class="hidden-480">
														<span class="label label-info arrowed-right arrowed-in">on sale</span>
													</td>
												</tr>

												<tr>
													<td>online.com</td>

													<td>
														<b class="blue">$16.45</b>
													</td>

													<td class="hidden-480">
														<span class="label label-success arrowed-in arrowed-in-right">approved</span>
													</td>
												</tr>

												<tr>
													<td>newnet.com</td>

													<td>
														<b class="blue">$15.00</b>
													</td>

													<td class="hidden-480">
														<span class="label label-danger arrowed">pending</span>
													</td>
												</tr>

												<tr>
													<td>web.com</td>

													<td>
														<small>
															<s class="red">$24.99</s>
														</small>
														<b class="green">$19.95</b>
													</td>

													<td class="hidden-480">
														<span class="label arrowed">
															<s>out of stock</s>
														</span>
													</td>
												</tr>

												<tr>
													<td>domain.com</td>

													<td>
														<b class="blue">$12.00</b>
													</td>

													<td class="hidden-480">
														<span class="label label-warning arrowed arrowed-right">SOLD</span>
													</td>
												</tr>
											</tbody>
										</table>
									</div>/.widget-main
								</div>/.widget-body
							</div>/.widget-box
						</div>/.col
					</div>/.row
					
					<div class="hr hr20 hr-dotted"></div>
					
					<div class="row">
						<div class="col-sm-6">
							<div class="widget-box transparent">
								<div class="widget-header widget-header-flat">
									<h5 class="widget-title lighter">
										<i class="ace-icon fa fa-star orange"></i>
										我的变更
									</h5>

									<div class="widget-toolbar">
										<a href="#" data-action="collapse">
											<i class="ace-icon fa fa-chevron-up"></i>
										</a>
									</div>
								</div>

								<div class="widget-body">
									<div class="widget-main no-padding">
										<table class="table table-bordered table-striped">
											<thead class="thin-border-bottom">
												<tr>
													<th>
														<i class="ace-icon fa fa-caret-right blue"></i>name
													</th>

													<th>
														<i class="ace-icon fa fa-caret-right blue"></i>price
													</th>

													<th class="hidden-480">
														<i class="ace-icon fa fa-caret-right blue"></i>status
													</th>
												</tr>
											</thead>

											<tbody>
												<tr>
													<td>internet.com</td>

													<td>
														<small>
															<s class="red">$29.99</s>
														</small>
														<b class="green">$19.99</b>
													</td>

													<td class="hidden-480">
														<span class="label label-info arrowed-right arrowed-in">on sale</span>
													</td>
												</tr>

												<tr>
													<td>online.com</td>

													<td>
														<b class="blue">$16.45</b>
													</td>

													<td class="hidden-480">
														<span class="label label-success arrowed-in arrowed-in-right">approved</span>
													</td>
												</tr>

												<tr>
													<td>newnet.com</td>

													<td>
														<b class="blue">$15.00</b>
													</td>

													<td class="hidden-480">
														<span class="label label-danger arrowed">pending</span>
													</td>
												</tr>

												<tr>
													<td>web.com</td>

													<td>
														<small>
															<s class="red">$24.99</s>
														</small>
														<b class="green">$19.95</b>
													</td>

													<td class="hidden-480">
														<span class="label arrowed">
															<s>out of stock</s>
														</span>
													</td>
												</tr>

												<tr>
													<td>domain.com</td>

													<td>
														<b class="blue">$12.00</b>
													</td>

													<td class="hidden-480">
														<span class="label label-warning arrowed arrowed-right">SOLD</span>
													</td>
												</tr>
											</tbody>
										</table>
									</div>/.widget-main
								</div>/.widget-body
							</div>/.widget-box
						</div>/.col

						<div class="col-sm-6">
							<div class="widget-box transparent">
								<div class="widget-header widget-header-flat">
									<h5 class="widget-title lighter">
										<i class="ace-icon fa fa-signal"></i>
										我的培训
									</h5>

									<div class="widget-toolbar">
										<a href="#" data-action="collapse">
											<i class="ace-icon fa fa-chevron-up"></i>
										</a>
									</div>
								</div>

								<div class="widget-body">
									<div class="widget-main padding-4">
										<table class="table table-bordered table-striped">
											<thead class="thin-border-bottom">
												<tr>
													<th>
														<i class="ace-icon fa fa-caret-right blue"></i>name
													</th>

													<th>
														<i class="ace-icon fa fa-caret-right blue"></i>price
													</th>

													<th class="hidden-480">
														<i class="ace-icon fa fa-caret-right blue"></i>status
													</th>
												</tr>
											</thead>

											<tbody>
												<tr>
													<td>internet.com</td>

													<td>
														<small>
															<s class="red">$29.99</s>
														</small>
														<b class="green">$19.99</b>
													</td>

													<td class="hidden-480">
														<span class="label label-info arrowed-right arrowed-in">on sale</span>
													</td>
												</tr>

												<tr>
													<td>online.com</td>

													<td>
														<b class="blue">$16.45</b>
													</td>

													<td class="hidden-480">
														<span class="label label-success arrowed-in arrowed-in-right">approved</span>
													</td>
												</tr>

												<tr>
													<td>newnet.com</td>

													<td>
														<b class="blue">$15.00</b>
													</td>

													<td class="hidden-480">
														<span class="label label-danger arrowed">pending</span>
													</td>
												</tr>

												<tr>
													<td>web.com</td>

													<td>
														<small>
															<s class="red">$24.99</s>
														</small>
														<b class="green">$19.95</b>
													</td>

													<td class="hidden-480">
														<span class="label arrowed">
															<s>out of stock</s>
														</span>
													</td>
												</tr>

												<tr>
													<td>domain.com</td>

													<td>
														<b class="blue">$12.00</b>
													</td>

													<td class="hidden-480">
														<span class="label label-warning arrowed arrowed-right">SOLD</span>
													</td>
												</tr>
											</tbody>
										</table>
									</div>/.widget-main
								</div>/.widget-body
							</div>/.widget-box
						</div>/.col
					</div>/.row
				</div>
			</div>
		 -->
		
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
	<%@ include file="../index/foot.jsp"%>
	<script src="static/ace/js/jquery-ui.custom.js"></script>
	<script src="static/ace/js/jquery.ui.touch-punch.js"></script>
	<script src="static/ace/js/jquery.easypiechart.js"></script>
	<script src="static/ace/js/jquery.sparkline.js"></script>
	<script src="static/ace/js/flot/jquery.flot.js"></script>
	<script src="static/ace/js/flot/jquery.flot.pie.js"></script>
	<script src="static/ace/js/flot/jquery.flot.resize.js"></script>
	
	<!-- ace scripts -->
	<script src="static/ace/js/ace.js"></script>
	<!-- <script src="static/ace/js/ace/ace.widget-box.js"></script> -->
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
	
		jQuery(function($) {
			var oldie = /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase());
			$('.easy-pie-chart.percentage').each(function(){
				$(this).easyPieChart({
					barColor: $(this).data('color'),
					trackColor: '#EEEEEE',
					scaleColor: false,
					lineCap: 'butt',
					lineWidth: 8,
					animate: oldie ? false : 1000,
					size:75
				}).css('color', $(this).data('color'));
			});
		})
	
		$(top.hangge());
		function nav1(){
			console.log('当前问题总数');
		}
		function nav2(){
			console.log('本周受理完成率');
		}
		function nav3(){
			console.log('在线用户');
		}
		function nav4(){
			console.log('本周业务统计');
		}
	</script>
<script type="text/javascript" src="static/ace/js/jquery.js"></script>
</body>
</html>