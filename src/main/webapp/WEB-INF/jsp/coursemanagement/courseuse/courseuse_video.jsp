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
<%@ include file="../../system/index/top.jsp"%>
<!--播放器-->
<link rel="stylesheet" href="static/ace/css/video-js.min.css" />
<script type="text/javascript" src="static/ace/js/video.min.js"></script>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div id="zhongxin" style="padding-top: 13px;">
						<div class="page-header">
								<table style="width:100%;">
							<tbody>
								<tr>
									<td style="vertical-align:top;">
										<span class="green middle bolder">小节内容: &nbsp;</span>
										<div class="pull-right">
											<div class="btn-toolbar inline middle no-margin">
												<div data-toggle="buttons" class="btn-group no-margin">
													<button class="btn btn-primary btn-xs btn-danger" onclick="goCourseDetail();">
														<i class="ace-icon fa fa-reply light-white bigger-110"></i><span>返回章节列表</span>
													</button>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						</div>
					<div class="row" style="margin-left: 30px;">		
						<div class="col-xs-8">
							<input type="hidden" id="CHAPTER_ID" name="CHAPTER_ID" value="${pd.CHAPTER_ID}">
							<h4 id="title" style="margin-bottom: 10px;"><span class="middle bolder">${pd.CHAPTER_NAME}</span></h4>
							<p>
							<video id="my-video" class="video-js vjs-default-skin vjs-big-play-centered" controls width="815" poster="" height="460">
	    						<source src="${pd.VIDEO_ADDRESS}">
	  						</video>
	  						</p>
  						</div>
  						<div class="col-xs-4">
							<h5 class="row header smaller lighter blue">
								 <span class="cl-sm-7">小节列表:<a onclick="change();" style="cursor:pointer;"><i id="change" style="margin-left: 5px;" class="ace-icon fa fa-list fa-th-large"></i></a></span>
								 <span class="cl-sm-5 pull-right">本次学习时长:<font id="time" class="red">0秒</font></span>
								 <input type="hidden" id="studyTime" value="0">
							</h5>
							<div class="well" style="height: 445px; overflow-y:scroll; position: relative;">
								<ul class="nav nav-pills nav-stacked" id="chapterList">
									<c:forEach items="${varList}" var="var">
									<li <c:if test="${pd.CHAPTER_ID == var.CHAPTER_ID}">class="active"</c:if> onclick="showVideo('${var.VIDEO_ADDRESS}','${var.CHAPTER_NAME}','${var.CHAPTER_ID}');">
										<a title="${var.CHAPTER_NAME}"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">${var.CHAPTER_NAME}</font></font></a>
									</li>
									</c:forEach>
								</ul>
							</div>
  						</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
  	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- ace -->
	<script src="static/ace/js/ace.js"></script>
	<script type="text/javascript">
	 var options = {
	          "poster": "",
	          "controls": true,
	          "autoplay" :false,
	         // "techOrder" : [ "html5", "flash" ],
	          "loop":false,
	          "muted":false,
	          "fluid":true,
	          "preload":"auto"
	      }
	 var time1;
     var t1 = 0;
     var t2 = 0;
     var time = ''; 
	 var myPlayer = videojs('my-video',options,function onPlayerReady() {
           myPlayer = this;
          function goTime() {
              t1 += 1;
              t2 += 1;
              document.getElementById('studyTime').value = t1;
              time = formatSeconds(t2);
              $("#time").text(time); 
          }
          //开始播放
          this.on('play', function () {
              console.log("开始播放")
             //开始播放时设置一个定时器
              time1 = setInterval(function () {
            	  goTime();
              }, 1000)

          });
          //暂停
          this.on('pause', function () {
              window.clearInterval(time1);
              console.log("暂停")
          });
          //播放结束
          this.on('ended', function () {
              window.clearInterval(time1);
              countTime();//播放结束写入数据
              console.log("播放结束")
          });
      });
	 
	 /*强制关闭写入数据*/
	 window.onbeforeunload = function(event) { 
		 countTime();
	 }
	 
	 /*写入数据*/
	 function countTime(){
		 //触发ajax
		var time = $("#studyTime").val();
		//将数值清零
		$("#studyTime").val("0");
		t1=0;
		if(time > 5){//防止反复提交产生垃圾数据
			//处理数据
			$.ajax({
			    url: '<%=basePath%>courseuse/saveTime.do?tm='+new Date().getTime(),
			    type: 'get',
			    data:{"PLAY_TIME":time,"CHAPTER_ID":$("#CHAPTER_ID").val()},
			    async: false
			});
		}
	 }
	 
	 //秒转换成时分秒
	 function formatSeconds(value) {

         var theTime = parseInt(value);// 秒
         var middle= 0;// 分
         var hour= 0;// 小时

         if(theTime > 60) {
             middle= parseInt(theTime/60);
             theTime = parseInt(theTime%60);
             if(middle> 60) {
                 hour= parseInt(middle/60);
                 middle= parseInt(middle%60);
             }
         }
         var result = ""+parseInt(theTime)+"秒";
         if(middle > 0) {
             result = ""+parseInt(middle)+"分"+result;
         }
         if(hour> 0) {
             result = ""+parseInt(hour)+"小时"+result;
         }
         return result;
     }
	 
	 //切换小节视频
	 function showVideo(address,name,id){
		 if(event){
				$("#chapterList li").each(function(){
					var item = this;
					$(item).removeClass("active");
				}); 
				$($(event.srcElement).closest('li')).addClass("active");
			}else{
				$("#chapterList li").first().addClass("active");
			}
         window.clearInterval(time1);
		 myPlayer.src({src:address});
         myPlayer.load(address);
         //切换视频时,上传上条视频观看时长
         countTime();
         $("#title").text(name);
         $("#CHAPTER_ID").val(id);
	 }
	 
	 var index = 1;
	 function change(){
		 $("#change").removeClass();
		 if(index == 1){
			 $("#chapterList").removeClass("nav-stacked");
			 $("#change").addClass("ace-icon fa fa-th-large");
			 index = 0;
		 }else if(index == 0){
			 $("#chapterList").addClass("nav-stacked");
			 index = 1;
			 $("#change").addClass("ace-icon fa fa-list fa-th-large");
		 }
	 }
	
	//返回章节列表
	function goCourseDetail(){
		$("#zhongxin").hide();
		$("#zhongxin2").show();
		window.location.href = '<%=basePath%>courseuse/listDetail.do?COURSE_ID='+'${COURSE_ID}';
	}
	</script>
</body>
</html>