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
<style>
/* html { overflow-y:hidden; } */
</style>
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="static/css/style.css" media="screen"
	type="text/css" />

<script src="static/js/modernizr.js"></script>
<!-- <link rel="stylesheet" href="static/html_UI/assets/css/jquery-ui.custom.css" />
<link rel="stylesheet" href="static/html_UI/assets/css/jquery.gritter.css" /> -->
</head>

<body class="no-skin">
	<!-- #section:elements.tab.position -->
	<div class="tabbable tabs-left">
		<!-- tabs-left -->
		<ul class="nav nav-tabs" id="myTab3">
			<li class="active"><a data-toggle="tab" href="#home3"> <i
					class="pink ace-icon fa fa-tachometer bigger-110"></i>组织机构
			</a></li>

			<li><a data-toggle="tab" href="#profile3"> <i
					class="blue ace-icon fa fa-user bigger-110"></i>职责
			</a></li>
		</ul>

		<div class="tab-content">
			<div id="home3" class="tab-pane in active">
				<div class="content">
					<!-- <h1>Responsive Organization Chart (updated)</h1> -->
					<figure class="org-chart cf">
						<div class="board ">
							<ul class="columnOne">
								<li><span class="lvl-b"> <strong>局党组书记、局长</strong><br>张密堂<br>办公电话：2937755
								</span></li>
							</ul>
							<ul class="columnFour">
								<li class="department"><span> <strong>局党组成员、副局长</strong>
										<br>王振学 <br>办公电话：2839405
								</span></li>
								<li class="department two"><span> <strong>副局长</strong>
										<br>龙志彪 <br>办公电话：2839166
								</span></li>
								<li class="department three"><span> <strong>局党组成员、副局长</strong>
										<br>刘占民 <br>办公电话：2837998
								</span></li>
								<li class="department"><span> <strong>局党组成员、副局长</strong>
										<br>王安辉 <br>办公电话：2839412
								</span></li>
							</ul>
							<div
								style="width: 100%; height: 22px; margin-top: 82px; margin-bottom: -20px;">
								<span
									style="height: 22px; width: 20%; margin-left: 10%; border-left: 2px solid orange; padding-bottom: 4px;"></span>
								<span
									style="height: 22px; width: 20%; margin-left: 38%; border-left: 2px solid orange; padding-bottom: 4px;"></span>
								<span
									style="height: 22px; width: 20%; margin-left: 26.9%; border-left: 0px solid orange; padding-bottom: 4px;"></span>
								<span
									style="height: 22px; width: 20%; margin-left: 18.4%; border-left: 0px solid orange; padding-bottom: 4px;"></span>
							</div>
						</div>

						<ul class="departments">
							<li class="department department1"><span> <strong>机关科室</strong>
									<br>&nbsp <br>办公电话：2839408
							</span></li>
							<li class="department"><span> <strong>群体科</strong> <br>&nbsp
									<br>办公电话：2839381
							</span></li>
							<li class="department department1"><span> <strong>竞训科</strong>
									<br>&nbsp <br>&nbsp
							</span></li>
							<li class="department"><span> <strong>体育运动学校</strong>
									<br>&nbsp <br>&nbsp
							</span>
								<ul class="sections">
									<li class="section"><span> <strong>训练科</strong> <br>
											<br>办公电话：2839429
									</span></li>
									<li class="section"><span> <strong>学生科</strong> <br>
											<br>办公电话：2839459
									</span></li>
									<li class="section"><span> <strong>教务科</strong> <br>
											<br>办公电话：2839442
									</span></li>
									<li class="section"><span> <strong>办公室</strong> <br>
											<br>办公电话：2838988
									</span></li>
									<li class="section"><span> <strong>科研所</strong> <br>
											<br>办公电话：2838906
									</span></li>
								</ul></li>
							<li class="department"><span> <strong>体育彩票管理中心</strong>
									<br>&nbsp <br>&nbsp
							</span></li>
							<li class="department"><span> <strong>体育总会</strong> <br>&nbsp
									<br>办公电话：2839495
							</span></li>
						</ul>
						<!-- <div class="footer">
									<ul class="departments departments1">
										<li class="department">
							               <span  >
							               <strong>训练科</strong>
							               <br>龙志彪
							               <br>办公电话：2839429
							               </span> 
							            </li>
							            <li class="department">
							               <span  >
							               <strong>学生科</strong>
							               <br>龙志彪
							               <br>办公电话：2839459
							               </span> 
							            </li>
							            <li class="department">
							               <span  >
							               <strong>教务科</strong>
							               <br>龙志彪
							               <br>办公电话：2839442		               
							               </span> 
							            </li>
							            <li class="department">
							               <span  >
							               <strong>办公室</strong>
							               <br>龙志彪
							               <br>办公电话：2838988
							               </span> 
							            </li>
							            <li class="department">
							               <span  >
							               <strong>科研所</strong>
							               <br>龙志彪
							               <br>办公电话：2838906
							               </span> 
							            </li>
									</ul>
								</div> -->
					</figure>
				</div>
			</div>

			<div id="profile3" class="tab-pane">
				<p style="text-align: center">
					<span style="font-size: 29px; font-family: 黑体">盘锦市体育局职责</span>
				</p>
				<p>
					<span style="font-size: 29px; font-family: 黑体">&nbsp;</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（一）贯彻执行国家体育工作的各项法律法规和方针政策，拟定相关政策并督促实施；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（二）推动多元化体育服务体系建设，推进体育公共服务体制改革。研究全市体育工作发展战略和发展目标。编制全市体育事业的中长期发展规划和年度计划，并对执行情况进行监督检查；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（三）统筹规划全市群众体育发展，推动全民健身计划。组织实施国家体育锻炼标准，推动国民体质监测和社会体育指导员队伍制度建设。指导公共体育设施的建设及其监督管理；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（四）统筹规划全市竞技体育发展，确定运动项目设置和重点布局，指导协调体育训练和体育竞赛，组织参加和承办重大体育竞赛。指导运动员队伍建设，协助运动员社会保障工作；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（五）统筹规划全市青少年体育发展，指导和推进青少年体育工作；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（六）拟定全市体育产业发展规划、政策，培育、引导和扶持体育产业，推动产业结构优化升级。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（七）领导市体育总会，负责全市体育社会团体的资格审查和业务指导工作；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（八）负责全市体育彩票发行管理；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（九）承办市政府交办的其他事项。</span>
				</p>
				<p>
					<span style="font-size: 21px; font-family: 仿宋_GB2312"><br
						style="page-break-before: always" clear="all" /> </span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;</span>
				</p>
				<p>
					<span style="font-size: 21px; font-family: 仿宋_GB2312"><br
						style="page-break-before: always" clear="all" /> </span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;</span>
				</p>
				<p style="text-align: center; text-indent: 43px">
					<span style="font-size: 29px; font-family: 黑体; color: red">领导班子分工（标题）</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体; color: red">局党组书记、局长：张密堂（一级）</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">主持体育局全面工作。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">办公电话：2937755</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体; color: red">局党组成员、副局长：王振学（二级）</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">分管局办公室、财务、群众体育等。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">办公电话：2839405</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体; color: red">副局长：龙志彪（二级）</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">分管体育运动学校、竞训科等。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">办公电话：2839166</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体; color: red">局党组成员、副局长：刘占民（二级）</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">分管体彩中心、综治、安全生产等。</span>
				</p>
				<p>
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;&nbsp;&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">办公电话：2837998</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体; color: red">局党组成员、副局长：王安辉（二级）</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">分管体育总会、人事、机关党建、体育产业等。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">办公电话：2839412</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体">&nbsp;</span>
				</p>
				<p style="text-align: center; text-indent: 43px">
					<span style="font-size: 29px; font-family: 黑体">盘锦市体育局机构设置</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体; color: red">机关科室：(王振学-三级)</span>
				</p>
				<p style="text-align: center; text-indent: 42px">
					<span style="font-size: 21px; font-family: 黑体">办公室工作职责</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（一）负责各种会议的安排、通知和记录，并督促检查会议决议的贯彻执行情况；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（二）负责全系统精神文明、思想宣传教育、体育宣传工作；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（三）负责机关文电、机要、保密、档案、印章管理等工作。负责信访、政务公开等工作；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（四）负责消防安全、综合社会治理、劳动保险、离退休干部的管理工作；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（五）负责全系统干部选拔任用，专业技术干部职称评定、申报、晋升聘用，工资等工作。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312; color: red">办公电话：2839408</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</span><span style="font-size: 29px; font-family: 黑体">&nbsp;</span><span
						style="font-size: 21px; font-family: 黑体; color: red">群体科工作职责</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体; color: red">群体科
						(王振学-三级)</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体">&nbsp;</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（一）贯彻落实全民健身计划，制定全市各类群众体育管理实施办法，安排组织全市群众体育竞赛及各类参赛活动；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（二）配合有关部门指导和督促全市群众体育活动的开展，协同教育部门抓好全市学校体育工作，贯彻落实《学校体育工作条例》，实施《国家体育锻炼标准》。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（三）指导和推进青少年体育工作，组织节假日和季节性群众体育活动及体育先进单位的评选推荐工作；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（四）会同有关部门积极开展职工、民族、伤残人等体育活动；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（五）负责二级社会体育指导员的培训、管理、审批和一级社会体育指导员的推荐工作；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（六）承办局交办的其他事项。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">办公电话：2839381</span>
				</p>
				<p style="text-align: center; text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体">竞训科工作职责</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体; color: red">竞训科（龙志彪
					</span><span style="font-size: 21px; color: red">–</span><span
						style="font-size: 21px; font-family: 黑体; color: red">三级）</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（一）协调、指导、管理全市业余体育训练和竞赛工作。计划安排全市综合性运动会和年度体育竞赛，制定竞赛规程并组织实施；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（二）组织实施省体育局委托我市承办的全省性体育竞赛，协助社会各界举办各类赞助和补助性体育竞赛；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（三）制定全市业余体育训练发展规划和项目布局。检查指导市体校和各区县业余训练，培训优秀运动员后备人才，为训练提供科技指导、比赛信息、成绩统计等工作；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（四）负责全市等级运动员、裁判员的管理工作。制定裁判员培训计划、组织、考核、审批。负责教练员业务培训，签订训练任务指标，检查任务完成情况；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（五）负责全市运动员档案的建档和管理工作，收集、整理全省大赛及各地体育竞赛资料；</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（六）承办局交办的其他事项。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体">&nbsp;</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体">直属事业单位</span>
				</p>
				<p style="text-align: center; text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体">体育总会</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体; color: red">体育总会-王安辉
						三级</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体">职责：</span>
				</p>
				<p>
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;&nbsp;&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">（一）制定全市体育社会团体发展规划，拟定有规章制度和办法，并组织实施。</span>
				</p>
				<p>
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;&nbsp;&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">（二）负责市级体育社会团体的组建和管理，指导检查县（区）及行业体协工作。</span>
				</p>
				<p>
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;&nbsp;&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">（三）组织体育社会团体开展活动，监督指导社会团体遵守宪法、法律、法规和国家政策，依据其章程开展活动。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（四）配合有关部门管理和开展全市工人、农民、学校、社区、少数民族和伤残人的体育活动。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（五）负责民间体育项目和新兴体育项目的推广普及。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">（六）开展跨地区民间体育交流。</span>
				</p>
				<p>
					<strong><span
						style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;</span></strong><strong><span
						style="font-size: 21px; font-family: 黑体">&nbsp; </span></strong><strong><span
						style="font-size: 21px; font-family: 黑体">服务指南：</span></strong>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">盘锦市体育总会坐落于兴隆台区辽河南路115号，隶属于盘锦市体育局，属于具有独立法人资质的科级事业单位。</span>
				</p>
				<p style="text-indent: 42px">
					<strong><span
						style="font-size: 21px; font-family: 仿宋_GB2312">申请成立市级体育社会团体应提交下列材料
					</span></strong>
				</p>
				<p>
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;&nbsp;&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">（一）成立申请</span>
				</p>
				<p style="text-indent: 20px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">（二）筹备工作小组成员名单</span>
				</p>
				<p style="text-indent: 20px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">（三）拟任负责人人选</span>
				</p>
				<p style="text-indent: 20px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">（四）副秘书长以上人员身份证复印件</span>
				</p>
				<p style="text-indent: 20px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">（五）《章程》草案</span>
				</p>
				<p style="text-indent: 20px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">（六）住所证明</span>
				</p>
				<p style="text-indent: 20px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;
					</span><span style="font-size: 21px; font-family: 仿宋_GB2312">（七）与社团运动项目相符的人员资质证件</span>
				</p>
				<p style="text-indent: 32px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;</span><span
						style="font-size: 21px; font-family: 仿宋_GB2312">具备成立条件，材料齐全，30个工作日内携体育总会批文及相关材料，到民政局办理成立申请注册登记。批复后，携批文一份到体育总会备案。材料不全或不具备成立条件的，30个工作日内补齐，超出期限，不予审批。
					</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">市体育总会办公室电话：0427-2839495</span>
				</p>
				<p style="text-indent: 42px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">&nbsp;</span>
				</p>
				<p style="text-align: center">
					<strong><span style="font-size: 21px; font-family: 黑体">体育运动学校</span></strong>
				</p>
				<p>
					<strong><span
						style="font-size: 21px; font-family: 黑体; color: red">体育运动学校-</span></strong><span
						style="font-size: 21px; font-family: 黑体; color: red">龙志彪-三级</span>
				</p>
				<p>
					<span style="font-size: 20px">&nbsp; </span><span
						style="font-size: 20px; font-family: 仿宋_GB2312">&nbsp;&nbsp;</span><span
						style="font-size: 20px; font-family: 仿宋_GB2312">盘锦市体育运动学校坐落在盘锦市兴隆台区双兴南路153号，于1993年经辽宁省教委批准成立。隶属盘锦市体育局，属市职业教育类中等专业学校。</span>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">学校自建校以来，已经为国家培养输送一大批优秀竞技体育人才。如冬奥会季军郭鑫鑫；奥运会第七名刘振；世锦赛冠军孙亚楠；亚运会冠军冷雪艳等。</span>
				</p>
				<p style="text-indent: 40px">
					<strong><span style="font-size: 21px; font-family: 宋体">基本情况：</span></strong>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">盘锦市体育运动学校含体育中学、体育中专。下设训练科、教务科、学生科、办公室、体育科研所等五个职能部门。教职员工69人，其中教练28人（高级教练15人），教师26人（高级教师12人），行政职员
						15人。开设田径、举重、跆拳道、摔跤、散打、和拳击六个训练<a name="_GoBack"></a>项目。训练场2个，训练馆4个。现有六个教学班级，分属于体育中专、体育中学各三个。目前，在校运动员93人，外训运动员125人。
					</span>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">教学场所：盘锦市体育运动学校教学楼</span>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">训练场馆：盘锦市体育场
					</span>
				</p>
				<p style="text-indent: 40px">
					<strong><span style="font-size: 21px; font-family: 宋体">工作任务：</span></strong>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">（一）按文化课课程标准和大纲要求，完成中专、中学两个层&nbsp;&nbsp;
						次的教学任务。</span>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">（二）向上级体育专业队伍培养和输送体育后备人才。</span>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">（三）按训练大纲要求，完成在校运动员的体育训练任务。</span>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">（四）代表盘锦市参加省级体育比赛。</span>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">（五）为基层单位培养中小学体育师资和体育管理人才。</span>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">（六）通过训练，学生获得高等级运动员技术资格。给体育院校输送生员。也可以作为高水平运动员被高等院校（非体育类）录取。</span>
				</p>
				<p>
					<strong><span style="font-size: 20px">&nbsp;&nbsp;
					</span></strong><strong><span style="font-size: 21px">&nbsp;</span></strong><strong><span
						style="font-size: 21px; font-family: 宋体">特色项目：</span></strong>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">1</span><span
						style="font-size: 21px; font-family: 仿宋_GB2312">、为发挥我校体育专业优势，更多服务社会。对有志于报考体育院校的高中生，择选优秀教练员为其量身打造，提供专业的体育技术指导和培训，帮助他们实现大学梦想。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 仿宋_GB2312">2</span><span
						style="font-size: 21px; font-family: 仿宋_GB2312">、通过先进的仪器设备为广大5-18岁的青少年提供骨龄测定，科学预测孩子未来的身高和发育情况。测试评定后，为其提供改善、健身的科学运动处方，为运动员科学选材提供重要依据。</span>
				</p>
				<p>
					<span style="font-size: 20px; font-family: 仿宋_GB2312">&nbsp;&nbsp;&nbsp;
					</span><span style="font-size: 20px; font-family: 仿宋_GB2312">各职能部门电话：</span>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">&nbsp;&nbsp;
					</span><span style="font-size: 20px; font-family: 仿宋_GB2312">训练科</span><span
						style="font-size: 21px; font-family: 黑体; color: red">四级</span><span
						style="font-size: 20px; font-family: 仿宋_GB2312">2839429&nbsp;
					</span><span style="font-size: 20px; font-family: 仿宋_GB2312">学生科</span><span
						style="font-size: 21px; font-family: 黑体; color: red">四级</span><span
						style="font-size: 20px; font-family: 仿宋_GB2312">&nbsp;
						2839459&nbsp; </span><span
						style="font-size: 20px; font-family: 仿宋_GB2312">教务科&nbsp; </span><span
						style="font-size: 21px; font-family: 黑体; color: red">四级</span><span
						style="font-size: 20px; font-family: 仿宋_GB2312">2839442</span>
				</p>
				<p style="text-indent: 40px">
					<span style="font-size: 20px; font-family: 仿宋_GB2312">&nbsp;&nbsp;
					</span><span style="font-size: 20px; font-family: 仿宋_GB2312">办公室 </span><span
						style="font-size: 21px; font-family: 黑体; color: red">四级</span><span
						style="font-size: 20px; font-family: 仿宋_GB2312">2838988&nbsp;
					</span><span style="font-size: 20px; font-family: 仿宋_GB2312">科研所 </span><span
						style="font-size: 21px; font-family: 黑体; color: red">四级</span><span
						style="font-size: 20px; font-family: 仿宋_GB2312"> 2838906</span>
				</p>
				<p style="text-align: center">
					<strong><span style="font-size: 21px; font-family: 宋体">体育彩票管理中心</span></strong>
				</p>
				<p style="text-align: left">
					<strong><span
						style="font-size: 21px; font-family: 宋体; color: red">体育彩票管理中心-</span></strong><span
						style="font-size: 21px; font-family: 黑体; color: red">刘占民-三级</span>
				</p>
				<p style="text-indent: 37px">
					<span style="font-size: 19px; font-family: 仿宋_GB2312">盘锦市体育彩票管理中心坐落于兴隆台区双兴南路163号体育场二区，隶属于市体育局的事业单位。是由辽宁省体育彩票发行中心授权，负责中国体育彩票在盘锦地区唯一的承销单位和管理机构。</span>
				</p>
				<p style="text-indent: 37px">
					<strong><span style="font-size: 19px; font-family: 宋体">基本情况：</span></strong>
				</p>
				<p style="text-indent: 37px">
					<span style="font-size: 19px; font-family: 仿宋_GB2312">盘锦市体育彩票管理中心成立于2001年，是由辽宁省体育彩票发行中心授权，负责中国体育彩票在盘锦地区唯一的承销单位和管理机构。</span>
				</p>
				<p style="text-indent: 46px">
					<strong><span style="font-size: 19px; font-family: 宋体">工作职责：</span></strong>
				</p>
				<p style="text-indent: 46px">
					<span style="font-size: 19px; font-family: 仿宋_GB2312">中心负责全市体育彩票管理、宣传、销售工作；负责全市体育彩票投注站布局规划及市场开发工作；负责设备安装维护、业务培训工作；负责发布体育彩票的中奖信息和兑奖工作。</span>
				</p>
				<p style="text-indent: 42px">
					<strong><span style="font-size: 21px; font-family: 黑体">服务指南：</span></strong>
				</p>
				<p style="text-indent: 38px">
					<span style="font-size: 19px; font-family: 仿宋_GB2312">为方便彩民兑奖，中心专门开设兑奖室，接待兑奖彩民及解答彩民疑问。</span>
				</p>
				<p style="text-indent: 38px">
					<span style="font-size: 19px; font-family: 仿宋_GB2312">兑奖时间：上午8：30-11：30，下午1:30-4:30。咨询电话：2333005。</span>
				</p>
				<p style="text-indent: 37px">
					<strong><span style="font-size: 19px; font-family: 宋体">常规业务</span></strong>
				</p>
				<p style="text-indent: 32px">
					<span style="font-size: 21px; font-family: 黑体">（一）终端机申请</span>
				</p>
				<p style="text-indent: 28px">
					<span style="font-size: 19px; font-family: 仿宋_GB2312">&nbsp;</span><span
						style="font-size: 19px; font-family: 仿宋_GB2312">根据省体彩中心要求，为规范终端机申请业务，制定终端机申请业务流程：</span>
				</p>
				<p style="text-indent: 38px">
					<img
						src="<%=basePath%>uploadFiles/uploadImgs/zhize.png" />
				</p>
				<p style="text-indent: 37px">
					<span style="font-size: 19px; font-family: 仿宋_GB2312">&nbsp;咨询电话：2333005。监督电话：2333006。</span>
				</p>
				<p style="text-indent: 43px">
					<span style="font-size: 21px; font-family: 黑体">（二）终端机维修</span>
				</p>
				<p style="text-indent: 37px">
					<span style="font-size: 19px; font-family: 仿宋_GB2312">中心现有专管员4名，专职负责终端机维修、维护工作，保障彩票销售站正常销售。</span>
				</p>
				<p>
					<span style="font-size: 19px; font-family: 仿宋_GB2312">咨询电话：2333006.</span>
				</p>
				<p>
					<br />
				</p>
			</div>
		</div>
	</div>

	<!-- /section:elements.tab.position -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<script>
		$(top.hangge());
	</script>
</body>

</html>