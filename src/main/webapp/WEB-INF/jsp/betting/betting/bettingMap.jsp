<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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

<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
	font-family: "微软雅黑";
}

.left-nav {
	background-color: #ffffff;
	width: 250px;
	height: 92%;
	position: absolute;
	left: 20px;
	top: 3%;
	bottom: 3%;
	z-index: 100;
	overflow-y: auto; 
	overflow-x: hidden; 
	/*opacity: 0.9;*/
}

.left-nav .top {
	height: 22%;
}

.left-nav .top .header-top {
	padding: 0px 15px 0px 15px;
}

.left-nav .bottom {
	/* height: 70%; */
}

.left-nav .header {
	height: 70px;
	padding: 0px 10px 15px 15px;
}

.left-nav hr {
	border: 4px solid rgb(225, 224, 222);
	margin-top: 15px;
	margin-bottom: 0px;
}

.left-nav .header hr {
	border: 1.5px solid rgb(146, 146, 146);
	margin-top: 15px;
}

.left-nav .header img {
	height: 20px;
}

.left-nav .bottom {
	padding: 0px 10px 15px 15px;
}

.left-nav .has-feedback {
	margin-bottom: 8px;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=CNIU569ktnYiEkGikBLGlgScEaopEgng"></script>
</head>
<body class="no-skin">
	<div id="allmap"></div>

	<div class="left-nav">
		<!-- <div class="top"> 
			<div class="header">
				<img src="static/images/东方雨虹-图.png" style="margin-right: 5px" /><img
					src="static/images/东方雨虹-字.png" />
				<hr />
			</div>
			<div class="header-top">
				<nav>
					<ul class="nav-stacked">
						统计报表
						<li role="presentation" class="active"><a href="#">进度报表查看</a></li>
						<li role="presentation"><a href="#">质量统计报表查看</a></li>
						<li role="presentation"><a href="#">质量统计报表修改</a></li>
						<li role="presentation"><a href="#">出勤统计报表查看</a></li>
					</ul>
				</nav>

			</div>
		</div>
		<hr />-->

		<div class="top">
			<div class="header">
				<img src="static/images/东方雨虹-图.png" style="margin-right: 5px" />
				<span style="font-family:'微软雅黑';color: #F10000;font-weight: bold;font-size: 16px;margin-top:5px;">投注站信息</span><!-- <img
					src="static/images/东方雨虹-字.png" /> -->
				<hr />
			</div>
		</div>
		<div class="bottom">
			<div class="bottom-form">
				<form action="betting/mapQuery.do" method="post" name="Form"
					id="Form">
					<div id="detail" style="margin-bottom: 20px;">
						<p class="text-info">
							<strong>营业执照注册号：<c:if
									test="${searchList!=null&&searchList.size()>0}">${searchList[0].LICENSE_NO}</c:if></strong>
						</p>
						<p>
							<span class="muted">所属区域：</span><span class="text-info"><c:if
									test="${searchList!=null&&searchList.size()>0}">${searchList[0].NAME}</c:if></span>
						</p>
						<p>
							<span class="muted">投注站地址：</span><span class="text-info"><c:if
									test="${searchList!=null&&searchList.size()>0}">${searchList[0].BETT_ADDR}</c:if></span>
						</p>
						<p>
							<abbr title="Phone" class="muted">手机号：</abbr><span
								class="text-info"><c:if
									test="${searchList!=null&&searchList.size()>0}">${searchList[0].MOBILE_TEL}</c:if></span>
						</p>
						<p>
							<span class="muted">简介：</span><span class="text-info"><c:if
									test="${searchList!=null&&searchList.size()>0}"><a style="cursor:pointer;" class="popover-notitle text-info" data-rel="popover" data-placement="bottom" data-content="${searchList[0].BETT_INTR}">${searchList[0].BETT_INTR_CUT}</a></c:if></span>
						</p>
					</div>
					<div class="form-inline has-feedback">
						<select class=" form-control" name="BELONG_AREA"
							id="belong_area" data-placeholder="请选择所属区域"
							style="vertical-align: top; width: 100%;" onchange="change(this.value)">
							<!-- <option value=""></option> -->
							<option value="">全部</option>
							<c:forEach items="${areaList}" var="area">
								<option value="${area.BIANMA }"
									<c:if test='${pd.BELONG_AREA==area.BIANMA}'>selected</c:if>>${area.NAME }</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-inline has-feedback">
					<!-- chosen-select -->
						<select class=" form-control" name="ID_CODE"
							id="id_code" data-placeholder="请选择投注站"
							style="vertical-align: top; width: 100%;">
							<!-- <option value=""></option> -->
							<option value="">全部</option>
							<c:forEach items="${varList}" var="bet">
								<option value="${bet.ID_CODE}" <c:if test="${pd.ID_CODE==bet.ID_CODE}">selected</c:if>>${bet.ID_CODE }</option>
							</c:forEach>
						</select>
					</div>
					<div class="input-group has-feedback">
						<span class="input-group-addon"> <i
							class="ace-icon fa fa-check"></i>
						</span> <input id="suggestId" type="text" name="keywords" value="${pd.keywords }"
							class="form-control search-query" placeholder="这里输入搜索地址" /> <span
							class="input-group-btn"> </span>
					</div>
					<button type="submit"
						class="btn btn-block btn-danger btn-sm has-feedback"
						onclick="search()">搜索</button>
				</form>
			</div>
		</div>
	</div>
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<script type="text/javascript">
		$(top.hangge());

		/**
		 * 控件及数据初始化
		 */
		$(function() {
			$('[data-rel=popover]').popover({html:true});
			
			//下拉框
			/* if (!ace.vars['touch']) {
				$('.chosen-select').chosen({
					allow_single_deselect : true
				});
				$(window).off('resize.chosen').on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						var $this = $(this);
						$this.next().css({
							'width' : $this.parent().width()
						});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen',
						function(e, event_name, event_val) {
							if (event_name != 'sidebar_collapsed')
								return;
							$('.chosen-select').each(function() {
								var $this = $(this);
								$this.next().css({
									'width' : $this.parent().width()
								});
							});
						});
				$('#chosen-multiple-style .btn').on(
						'click',
						function(e) {
							var target = $(this).find('input[type=radio]');
							var which = parseInt(target.val());
							if (which == 2)
								$('#form-field-select-4').addClass(
										'tag-input-style');
							else
								$('#form-field-select-4').removeClass(
										'tag-input-style');
						});
			} */
			
			change($("#belong_area").val());
			initPlases();
		});

		var map = new BMap.Map("allmap");
		map.centerAndZoom("盘锦", 12); // 初始化地图,设置城市和地图级别。

		//map.addControl(new BMap.ScaleControl()); // 添加默认比例尺控件
		map.addControl(new BMap.ScaleControl({
			anchor : BMAP_ANCHOR_BOTTOM_RIGHT
		}));
		//map.addControl(new BMap.NavigationControl()); //添加默认缩放平移控件
		map.addControl(new BMap.NavigationControl({
			anchor : BMAP_ANCHOR_TOP_RIGHT
		})); //右上角，仅包含平移和缩放按钮

		map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
		map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用

		/* var ac = new BMap.Autocomplete( //建立一个自动完成的对象
		{
			"input" : "suggestId",
			"location" : map
		});

		ac.addEventListener("onconfirm", function(e) { //鼠标点击下拉列表后的事件
			var _value = e.item.value;
			myValue = _value.province + _value.city + _value.district
					+ _value.street + _value.business;
			setPlace(myValue);
		});
		//map.addEventListener("click", showInfo);
		$("#suggestId").keydown(function(event) {
			if (event.keyCode == 13) {
				var addr = $("#suggestId").val();
				if (addr != null && addr != "")
					setPlace(addr);
			}
		}); */

		/**
		 * 根据搜索的条件设置地图坐标
		 */
		function initPlases() {
			//var bettingList=eval("("+bettingStr+")");
			//var bettingList=$.parseJSON(bettingStr);
			//var bettingStr = JSON.stringify("${varList}") 

			var bettingList = ${searchJson};
			map.clearOverlays(); //清除地图上所有覆盖物
			for (var i = 0; i < bettingList.length; i++) {
				var addr = bettingList[i].GEOG_COOR;
				setPlaceByGeog(addr);
			}
		}

		/**
		 * 根据坐标定位地图坐标
		 */
		function setPlaceByGeog(geogCode) {
			if (geogCode != null && geogCode != "") {
				var geogCodes = geogCode.split(',');
				var x = geogCodes[0];
				var y = geogCodes[1];
				var point = new BMap.Point(y, x);
				//map.panTo(point);
				window.setTimeout(function() {
					//map.setCenter(point);
					//map.panTo(point); 
					//map.zoomTo(18);
					map.addOverlay(new BMap.Marker(point)); //添加标注
				}, 1000);
			}
		}

		/**
		 * 根据地址定位地图坐标
		 */
		function setPlace(myValue) {
			map.clearOverlays(); //清除地图上所有覆盖物
			function myFun() {
				var point0 = local.getResults().getPoi(0);
				if (point0 != null) {
					var pp = point0.point; //获取第一个智能搜索的结果
					map.centerAndZoom(pp, 18);
					map.addOverlay(new BMap.Marker(pp)); //添加标注
					document.getElementById("ZUOBIAO_X").value = pp.lat;
					document.getElementById("ZUOBIAO_Y").value = pp.lng;
				}
			}
			var local = new BMap.LocalSearch(map, { //智能搜索
				onSearchComplete : myFun
			});
			local.search(myValue);
		}

		/**
		 * 根据指定搜索条件定位地图坐标
		 */
		function search() {
			top.jzts();
			$("#Form").submit();

			/* var addr = $("#suggestId").val();
			if (addr != null && addr != "")
				setPlace(addr); */
		}
		
		/**
		 * 第一级值改变事件(初始第二级)
		 */
		function change(value){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>betting/getBettingByBelongarea.do',
		    	data: {BELONG_AREA:value},
				dataType:'json',
				cache: false,
				success: function(data){
					//console.log($("#id_code_chosen .chosen-drop ul.chosen-results"));
					//console.log($("#id_code_chosen .chosen-drop ul.chosen-results").children());
					//$('#id_code_chosen .chosen-drop ul.chosen-results li').remove();
				
					
					//$("#id_code").html("<option value=''></option><option>全部</option>");
					$("#id_code").html("<option value=''>全部</option>");
					$.each(data.list, function(i, bet){
						console.log("ccc"+"${pd.ID_CODE}");
						//$("#id_code").append("<option value="+bet.ID_CODE+" <c:if test='${pd.ID_CODE=="+bet.ID_CODE+"}'> selected</c:if>>"+bet.ID_CODE+"</option>");
						if("${pd.ID_CODE}"==bet.ID_CODE)
							$("#id_code").append("<option value="+bet.ID_CODE+" selected>"+bet.ID_CODE+"</option>");
						else
							$("#id_code").append("<option value="+bet.ID_CODE+">"+bet.ID_CODE+"</option>");
					});  
				}
			});
		}
		
		function changeEmpty(){
			console.log($("#id_code_chosen .chosen-drop ul.chosen-results"));
			console.log($("#id_code_chosen .chosen-drop ul.chosen-results").children());
			console.log($("#id_code_chosen .chosen-drop ul.chosen-results li"));
			$('#id_code_chosen .chosen-drop ul.chosen-results li').remove();
		}
	</script>
</body>

</html>


