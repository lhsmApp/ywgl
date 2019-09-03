<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>选点获取经纬度</title>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=CNIU569ktnYiEkGikBLGlgScEaopEgng"></script>


<style type="text/css">
body, html {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

#allmap {
	width: 100%;
	height: 93%;
	overflow: hidden;
	margin: 0;
}

#l-map {
	height: 100%;
	width: 78%;
	float: left;
	border-right: 2px solid #bcbcbc;
}

#r-result {
	height: 100%;
	width: 20%;
	float: left;
}
</style>

</head>
<body>
	<div style="margin-top: 3px;">
		<table bgcolor="#E3E4D8" width="100%">
			<tr>
				<td>&nbsp;纬度：&nbsp; <input id="ZUOBIAO_X" value="" type="text" />
					&nbsp; 经度：&nbsp; <input id="ZUOBIAO_Y" value="" type="text" />
					&nbsp; <input type="button" class="btn btn-mini btn-primary"
					value="确定" onclick="choose();" />
				</td>
				<td width="100">
					<!-- <input type="text" id="suggestId" size="20"
					value="" placeholder="这里输入搜索地址" style="width: 150px;" /> --> <!-- <div id="searchResultPanel"
						style="border: 1px solid #C0C0C0; width: 150px; height: auto;">
					</div> -->

					<div class="input-group" style="width: 250px;margin-right:5px;">
						<span class="input-group-addon"> <i
							class="ace-icon fa fa-check"></i>
						</span> <input id="suggestId" type="text" value=""
							class="form-control search-query" placeholder="这里输入搜索地址" /> <span
							class="input-group-btn">
							<button type="button" class="btn btn-purple btn-sm"
								onclick="search()">
								<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>

							</button>
						</span>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div id="allmap" style="margin-top: 3px;"></div>

	<script type="text/javascript">
		$(top.hangge());
		

		//var dg;
		var isClose=false;
		var map = new BMap.Map("allmap");
		$(document).ready(
				function() {
					//dg = frameElement.lhgDG;

					map.centerAndZoom("盘锦", 12); // 初始化地图,设置城市和地图级别。

					map.addControl(new BMap.ScaleControl()); // 添加默认比例尺控件
					map.addControl(new BMap.NavigationControl()); //添加默认缩放平移控件

					map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
					map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用

					var ac = new BMap.Autocomplete( //建立一个自动完成的对象
					{
						"input" : "suggestId",
						"location" : map
					});

					//var myValue;
					ac.addEventListener("onconfirm", function(e) { //鼠标点击下拉列表后的事件
						var _value = e.item.value;
						myValue = _value.province + _value.city
								+ _value.district + _value.street
								+ _value.business;
						//G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
						setPlace(myValue);
					});
					map.addEventListener("click", showInfo);

					var addr = "${pd.addr}";
					setTimeout(function() {
						$("#suggestId").val(addr);
						console.log($("#suggestId").val());
					}, 1000);
					
					
					var geogCode = "${pd.GEOG_COOR}";
					if (geogCode != null && geogCode != "") {
						var geogCodes = geogCode.split(',');
						var x = geogCodes[0];
						var y = geogCodes[1];
						document.getElementById("ZUOBIAO_X").value = x;
						document.getElementById("ZUOBIAO_Y").value = y;
						map.clearOverlays(); //清除地图上所有覆盖物
						var point = new BMap.Point(y, x);
						//map.panTo(point);
						window.setTimeout(function() {
							map.setCenter(point)
							//map.panTo(point); 
							map.zoomTo(18);
						}, 1000);
						map.addOverlay(new BMap.Marker(point)); //添加标注
					} else {
						if (addr != null && addr != "")
							setPlace(addr);
					}

					$("#suggestId").keydown(function(event) {
						if (event.keyCode == 13) {
							var addr = $("#suggestId").val();
							if (addr != null && addr != "")
								setPlace(addr);
						}
					});
				});

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

		function showInfo(e) {

			document.getElementById("ZUOBIAO_X").value = e.point.lat;
			document.getElementById("ZUOBIAO_Y").value = e.point.lng;
		}

		// 百度地图API功能
		function G(id) {
			return document.getElementById(id);
		}

		function choose() {
			var ZUOBIAO_X = document.getElementById("ZUOBIAO_X").value;
			var ZUOBIAO_Y = document.getElementById("ZUOBIAO_Y").value;
			if (ZUOBIAO_X == "" || ZUOBIAO_Y == "") {
				alert("请先输入经纬度");
			} else {
				isClose=true;
				top.Dialog.close();
			}
		}

		function search() {
			var addr = $("#suggestId").val();
			if (addr != null && addr != "")
				setPlace(addr);
		}
		
	</script>
</body>

</html>










