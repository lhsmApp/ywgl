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
	/*height: 70%;*/
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

		<div class="top">
			<div class="header">
				<img src="static/images/东方雨虹-图.png" style="margin-right: 5px" /><!-- <img
					src="static/images/东方雨虹-字.png" /> -->
					<span style="font-family:'微软雅黑';color: #F10000;font-weight: bold;font-size: 16px;margin-top:5px;">协会组织信息</span>
				<hr />
			</div>
		</div>
		<div class="bottom">
			<div class="bottom-form">
				<form action="pesoinfo/mapQuery.do" method="post" name="Form"
					id="Form">
					<div id="detail" style="margin-bottom: 20px;">
						<p class="text-info">
							<strong>社会组织名称：<c:if
									test="${getList!=null&&getList.size()>0}">${getList[0].PESO_NAME}</c:if></strong>
						</p>
						<p>
							<span class="muted">所属区域：</span>
							<span class="text-info">
							    <c:if test="${getList!=null&&getList.size()>0}">
							        <c:forEach items="${areaList}" var="area">
								        <c:if test="${getList[0].BELONG_AREA==area.BIANMA}">${area.NAME }</c:if>
							        </c:forEach>
							    </c:if>
							</span>
						</p>
						<p>
							<span class="muted">办公地址：</span>
							<span class="text-info">
							    <c:if test="${getList!=null&&getList.size()>0}">
							        <a style="cursor:pointer;" class="popover-notitle text-info" data-rel="popover" data-placement="bottom" data-content="${getList[0].OFFICE_ADDR}">${getList[0].OFFICE_ADDR_CUT}</a>
							    </c:if>
							</span>
						</p>
						<p>
							<span class="muted">统一社会信用代码：</span><span class="text-info"><c:if
									test="${getList!=null&&getList.size()>0}">${getList[0].USCC}</c:if></span>
						</p>
						<p>
							<span class="muted">职能简介：</span>
							<span class="text-info">
							    <c:if test="${getList!=null&&getList.size()>0}">
							        <a style="cursor:pointer;" class="popover-notitle text-info" data-rel="popover" data-placement="bottom" data-content="${getList[0].PESO_INTR}">${getList[0].PESO_INTR_CUT}</a>
							    </c:if>
							</span>
						</p>
					</div>
					<div class="form-inline has-feedback">
						<select class="chosen-select form-control" name="BELONG_AREA"
							id="belong_area" data-placeholder="请选择所属区域"
							style="vertical-align: top; width: 100%;" onchange="change(this.value)">
							<option value="">全部</option>
							<c:forEach items="${areaList}" var="area">
								<option value="${area.BIANMA }"
									<c:if test="${pd.BELONG_AREA==area.BIANMA}">selected</c:if>>${area.NAME }</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-inline has-feedback">
						<select class="chosen-select form-control" name="ID" id="ID" data-placeholder="请选择协会"
							style="vertical-align: top; width: 100%;">
							<option value="">全部</option>
							<c:forEach items="${dicList }" var="each">
                                  <option value="${each.ID }" <c:if test="${each.ID== pd.PESO_NAME}">selected</c:if>>${each.PESO_NAME}</option>
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
			
			//$('.popover').css('height','50px');
			
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

		/**
		 * 根据搜索的条件设置地图坐标
		 */
		function initPlases() {
			var list = ${searchJson};
			map.clearOverlays(); //清除地图上所有覆盖物
			for (var i = 0; i < list.length; i++) {
				var addr = list[i].GEOG_COOR;
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
				window.setTimeout(function() {
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
		}
		
		/**
		 * 第一级值改变事件(初始第二级)
		 */
		function change(value){
			console.log("deleteCom");
		    $.ajax({
				type: "POST",
				url: '<%=basePath%>pesoinfo/getListByCondition.do',
		    	data: {BELONG_AREA:value},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#ID").html("<option value=''>全部</option>");
					$.each(data.list, function(i, each){
						if("${pd.ID}"==each.ID)
							$("#ID").append("<option value="+each.ID+" selected>"+each.PESO_NAME+"</option>");
						else
							$("#ID").append("<option value="+each.ID+">"+each.PESO_NAME+"</option>");
					});  
				},
	            error: function() {  
	                //alert('对不起失败了');  
	            }  
			}); 
		}
	</script>
</body>

</html>


