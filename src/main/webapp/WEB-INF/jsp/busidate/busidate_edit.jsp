<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../system/index/top.jsp"%>
<style>
	.page-header{
		padding-top: 9px;
		padding-bottom: 9px;
		margin: 0 0 8px;
	}
</style>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="page-header">
						<span class="label label-xlg label-success arrowed-right">综合管理</span>
						<!-- arrowed-in-right -->
						<span
							class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">期间设置</span>
					</div>
					<!-- /.page-header -->
					<div class="row">
						<div class="col-xs-12">
							<div
								style="margin: 0 0 12px; border-bottom: 1px dotted blue; padding-left: 10px; padding-bottom: 10px; padding-top: 7px;">
								<div>
									<span class="blue bolder bigger-110">当前期间：&nbsp;</span> 
									<%-- <input
										id="busiDate" type="text" readonly
										class="green bolder bigger-110" value='${BUSI_DATE}'>
									</input> --%>
									<span class="input-icon">
										<input id="busiDate" class="input-mask-date green bolder bigger-110" type="text" value='${BUSI_DATE}'
										placeholder="请输入业务区间"> <i class="ace-icon fa fa-calendar blue"></i>
									</span>	
									
									<span class="blue bolder bigger-110" style="margin-left: 10px;">自动结转状态：&nbsp;</span>
									<span id="jobstate" class="bolder bigger-110">
										<c:if test="${JOB_INFO.STATUS=='1'}"><h7 class="green">正在运行</h7><img src="static/images/runing.gif" width="12px;" /></c:if>
										<c:if test="${JOB_INFO.STATUS=='2'}"><h7 class="red">已经停止</h7></c:if>
									</span>
									<button style="margin-left: 5%;" id="btnQuery"
										class="btn btn-sm btn-white btn-success"
										onclick="autoBusidate()">自动结转设置</button>
									<button id="btnQuery" class="btn btn-sm btn-white btn-primary"
										onclick="manualBusidate()">手动结转</button>
									<button id="btnQuery" class="btn btn-sm btn-white btn-warning"
										onclick="pastBusidate()">调整往期</button>
								</div>
							</div>
							<form name="Form" id="Form" 
								<c:if test="${JOB_INFO.STATUS=='1'}">style="display: block;"</c:if>
								<c:if test="${JOB_INFO.STATUS=='2'}">style="display: none;"</c:if>
								method="post" class="col-xs-12 col-md-8">
								<input type="hidden" name="TIMINGBACKUP_ID" id="TIMINGBACKUP_ID"
									value="1" />
								<input type="hidden" name="JOBNAME" id="JOBNAME"
								value="${JOB_INFO.JOBNAME}" />
								<table id="table_report" class="table table-bordered">
									<tr>
										<td style="width: 75px; text-align: right; padding-top: 13px;"
											id="setFHTIME">设定:</td>
										<td style="text-align: center;"><select
											onchange="setTimegz(this.value,'month')">
												<option value="*">每</option>
												<option value="1">一</option>
												<option value="2">二</option>
												<option value="3">三</option>
												<option value="4">四</option>
												<option value="5">五</option>
												<option value="6">六</option>
												<option value="7">七</option>
												<option value="8">八</option>
												<option value="9">九</option>
												<option value="10">十</option>
												<option value="11">十一</option>
												<option value="12">十二</option>
										</select>&nbsp;&nbsp;&nbsp;月</td>
										<td style="text-align: center;"><select
											onchange="setTimegz(this.value,'week')" id="weekId">
												<option value="*">每</option>
												<option value="MON">一</option>
												<option value="TUES">二</option>
												<option value="WED">三</option>
												<option value="THUR">四</option>
												<option value="FTI">五</option>
												<option value="SAT">六</option>
												<option value="SUN">七</option>
										</select>&nbsp;&nbsp;&nbsp;周</td>
										<td style="text-align: center;"><select
											onchange="setTimegz(this.value,'day')" id="dayId">
												<option value="*">每</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
												<option value="9">9</option>
												<option value="10">10</option>
												<option value="11">11</option>
												<option value="12">12</option>
												<option value="13">13</option>
												<option value="14">14</option>
												<option value="15">15</option>
												<option value="16">16</option>
												<option value="17">17</option>
												<option value="18">18</option>
												<option value="19">19</option>
												<option value="20">20</option>
												<option value="21">21</option>
												<option value="22">22</option>
												<option value="23">23</option>
												<option value="24">24</option>
												<option value="25">25</option>
												<option value="26">26</option>
												<option value="27">27</option>
												<option value="28">28</option>
												<option value="28">29</option>
												<option value="28">30</option>
												<option value="28">31</option>
										</select>&nbsp;&nbsp;&nbsp;日</td>

										<td style="text-align: center;"><select
											onchange="setTimegz(this.value,'hour')">
												<option value="*">每</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
												<option value="9">9</option>
												<option value="10">10</option>
												<option value="11">11</option>
												<option value="12">12</option>
												<option value="13">13</option>
												<option value="14">14</option>
												<option value="15">15</option>
												<option value="16">16</option>
												<option value="17">17</option>
												<option value="18">18</option>
												<option value="19">19</option>
												<option value="20">20</option>
												<option value="21">21</option>
												<option value="22">22</option>
												<option value="23">23</option>
												<option value="24">24</option>
										</select>&nbsp;&nbsp;&nbsp;时</td>
									</tr>

									<tr>
										<td style="width: 75px; text-align: right; padding-top: 13px;">时间规则:</td>
										<td colspan="9"><input type="text" name="FHTIME"
											id="FHTIME" value="${JOB_INFO.FHTIME}" maxlength="30"
											placeholder="这里输入时间规则" title="时间规则" style="width: 98%;" /></td>
									</tr>
									<tr>
										<td class="center">规则说明:</td>
										<td colspan="8">
											<lable class="center" name="TIMEEXPLAIN" id="TIMEEXPLAIN">${JOB_INFO.TIMEEXPLAIN}</lable>
										</td>
									</tr>
									<%-- <tr>
										<td style="width: 75px; text-align: right; padding-top: 13px;">备注:</td>
										<td colspan="9"><input type="text" name="BZ" id="BZ"
											value="${JOB_INFO.BZ}" maxlength="255" placeholder="这里输入备注"
											title="备注" style="width: 98%;" /></td>
									</tr> --%>
									<tr>
										<td class="center" colspan="8">
											<a class="btn btn-app btn-xs btn-primary" onclick="saveJob();">
												<i class="ace-icon fa fa-floppy-o bigger-160"></i>保存
											</a> 
											<a id="on" class="btn btn-app btn-xs btn-success <c:if test="${JOB_INFO.STATUS=='1'}">hidden</c:if>" onclick="exeJob(1);">
												<i class="ace-icon glyphicon glyphicon-play"></i>启动
											</a>
											<a id="off" class="btn btn-app btn-xs btn-danger <c:if test="${JOB_INFO.STATUS=='2'}">hidden</c:if>" onclick="exeJob(2);">
												<i class="ace-icon glyphicon glyphicon-off"></i>关闭
											</a>
										</td>
									</tr>
									</div>
								</table>

							</form>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- 输入格式化 -->
	<script src="static/ace/js/jquery.maskedinput.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		$('.input-mask-date').mask('999999');
		var hour = "*";
		var day = "*";
		var week = "*";
		var month = "*";
		//设置时间规则
		function setTimegz(value, type) {
			if ('hour' == type) {
				hour = value;
			} else if ('day' == type) {
				day = value;
			} else if ('week' == type) {
				week = value;
			} else {
				month = value;
			}
			var strM = "";
			var strW = "";
			var strD = "";
			var strH = "";
			if ("*" == month) {
				strM = "每个月";
			} else {
				strM = "每年 " + month + " 月份";
			}
			if ("?" != week) {
				if ("*" == week) {
					strW = "每周";
					strD = "每天";
					$("#dayId").removeAttr("disabled");
					$("#dayId").css("background", "#FFFFFF");
				} else {
					$("#dayId").attr("disabled", "disabled");
					$("#dayId").css("background", "#F5F5F5");
					day = "?";
					strD = "";
					strW = "每个星期" + getWeek(week);
				}
			}
			if ("?" != day) {
				if ("*" == day) {
					strD = "每天";
					strW = "每周";
					$("#weekId").removeAttr("disabled");
					$("#weekId").css("background", "#FFFFFF");
				} else {
					$("#weekId").attr("disabled", "disabled");
					$("#weekId").css("background", "#F5F5F5");
					week = "?";
					strW = "";
					strD = day + "号";
				}
			}
			if ("*" == hour) {
				strH = "每小时";
			} else {
				strH = hour + "点时";
			}
			if ("*" == day && "*" == week) {
				day = "*";
				week = "?";
			}
			$("#FHTIME").val(
					"0 0 " + hour + " " + day + " " + month + " " + week);
			$("#TIMEEXPLAIN").text(
					strM + "的 " + strW + " " + strD + " " + strH + "执行一次");
		}

		//获取星期汉字
		function getWeek(value) {
			var arrW = new Array("MON", "TUES", "WED", "THUR", "FTI", "SAT",
					"SUN");
			var arrH = new Array("一", "二", "三", "四", "五", "六", "日");
			for (var i = 0; i < arrW.length; i++) {
				if (value == arrW[i])
					return arrH[i];
			}
		}

		//保存
		function saveJob() {
			if ($("#FHTIME").val() == "") {
				$("#setFHTIME").tips({
					side : 3,
					msg : '请设置时间规则',
					bg : '#AE81FF',
					time : 2
				});
				$("#FHTIME").focus();
				return false;
			}
			/* if ($("#BZ").val() == "") {
				$("#BZ").tips({
					side : 3,
					msg : '请输入备注',
					bg : '#AE81FF',
					time : 2
				});
				$("#BZ").focus();
				return false;
			} */
			top.jzts();
			var TIMINGBACKUP_ID=$("#TIMINGBACKUP_ID").val();
			var FHTIME=$("#FHTIME").val();
			var TIMEEXPLAIN=$("#TIMEEXPLAIN").text();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>busidate/saveJob.do',
				data:{TIMINGBACKUP_ID:TIMINGBACKUP_ID,FHTIME:FHTIME,TIMEEXPLAIN:TIMEEXPLAIN},
		    	dataType:'json',
				cache: false,
				success: function(response){
					if(response.code==0){
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side:3,
				            msg:'保存任务成功',
				            bg:'#009933',
				            time:3
				        });
					}else{
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side:3,
				            msg:'保存任务失败,'+response.message,
				            bg:'#cc0033',
				            time:3
				        });
					}
				},
		    	error: function(e) {
		    		$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'保存任务失败,'+response.responseJSON.message,
			            bg:'#cc0033',
			            time:3
			        });
		    	}
			});
		}

		//自动结转
		function autoBusidate() {
			$("#Form").toggle("fast");
		}
		
		//手动结转
		function manualBusidate() {
			$("#Form").hide("fast");
			if ($("#busiDate").val() == "") {
				$("#busiDate").tips({
					side : 3,
					msg : '请设置当前业务期间',
					bg : '#AE81FF',
					time : 2
				});
				$("#busiDate").focus();
				return false;
			}
			var msg = '确定要结转当前业务期间【'+$("#busiDate").val()+'】?';
            bootbox.confirm(msg, function(result) {
				if(result) {
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>busidate/saveBusidate.do',
						data:{BUSI_DATE:$('#busiDate').val()},
				    	dataType:'json',
						cache: false,
						success: function(response){
							if(response.code==0){
								//刷新期间
								$("#busiDate").val(response.message);
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'手动结转成功',
						            bg:'#009933',
						            time:3
						        });
							}else{
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'手动结转失败,'+response.message,
						            bg:'#cc0033',
						            time:3
						        });
							}
						},
				    	error: function(e) {
				    		$(top.hangge());//关闭加载状态
							$("#subTitle").tips({
								side:3,
					            msg:'手动结转失败,'+response.responseJSON.message,
					            bg:'#cc0033',
					            time:3
					        });
				    	}
					});
				}
            });
		}
		
		//调整往期
		function pastBusidate() {
			$("#Form").hide("fast");
			if ($("#busiDate").val() == "") {
				$("#busiDate").tips({
					side : 3,
					msg : '请设置当前业务期间',
					bg : '#AE81FF',
					time : 2
				});
				$("#busiDate").focus();
				return false;
			}
			var msg = '确定要调整往期业务区间为【'+$("#busiDate").val()+'】?';
            bootbox.confirm(msg, function(result) {
				if(result) {
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>busidate/pastBusidate.do',
						data:{BUSI_DATE:$('#busiDate').val()},
				    	dataType:'json',
						cache: false,
						success: function(response){
							if(response.code==0){
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'调整往期业务期间成功',
						            bg:'#009933',
						            time:3
						        });
							}else{
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'调整往期业务期间失败,'+response.message,
						            bg:'#cc0033',
						            time:3
						        });
							}
						},
				    	error: function(e) {
				    		$(top.hangge());//关闭加载状态
							$("#subTitle").tips({
								side:3,
					            msg:'调整往期业务期间失败,'+response.responseJSON.message,
					            bg:'#cc0033',
					            time:3
					        });
				    	}
					});
				}
			});
		}
		
		//开始、停止任务执行
		function exeJob(status) {
			var jobId=$("#TIMINGBACKUP_ID").val();
			var jobName=$("#JOBNAME").val();
			var jobTime=$("#FHTIME").val();
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>busidate/changeStatus.do?tm='+new Date().getTime(),
		    	data: {TIMINGBACKUP_ID:jobId,JOBNAME:jobName,FHTIME:jobTime,STATUS:status},
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(response){
					if(response.code==1){
						 $("#subTitle").tips({
								side:3,
					            msg:'启动成功',
					            bg:'#009933',
					            time:2
					        });
						 $("#jobstate").html('<h7 class="green">正在运行</h7><img src="static/images/runing.gif" width="12px;" />');
						 $("#on").addClass('hidden');
						 $("#off").removeClass('hidden');
					 }else{
						 $("#subTitle").tips({
								side:3,
					            msg:'关闭成功',
					            bg:'#009933',
					            time:2
					        });
						 $("#jobstate").html('<h7 class="red">已经停止</h7>');
						 $("#on").removeClass('hidden');
						 $("#off").addClass('hidden');
					 }
				 	top.hangge();
				},
				error: function(e) {
		    		$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'失败,'+response.responseJSON.message,
			            bg:'#cc0033',
			            time:3
			        });
		    	}
			});
		}
	</script>
</body>
</html>