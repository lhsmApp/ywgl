﻿
<div id="navbar" class="navbar navbar-default">
	<script type="text/javascript">
		try {
			ace.settings.check('navbar', 'fixed');
		} catch (e) {
		}
	</script>

	<div class="navbar-container" id="navbar-container">
		<!-- #section:basics/sidebar.mobile.toggle -->
		<button type="button" class="navbar-toggle menu-toggler pull-left"
			id="menu-toggler" data-target="#sidebar">
			<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>

			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>

		<!-- /section:basics/sidebar.mobile.toggle -->
		<div class="navbar-header pull-left">
			<!-- #section:basics/navbar.layout.brand -->
			<a class="navbar-brand"> <small> <i class="fa fa-leaf"></i>
					<span
					style="font-family: '微软雅黑'; color: #fff; font-weight: bold; font-size: 20px;">${pd.SYSNAME}</span>
			</small>
			</a>

			<!-- /section:basics/navbar.layout.brand -->

			<!-- #section:basics/navbar.toggle -->

			<!-- /section:basics/navbar.toggle -->
		</div>

		<!-- #section:basics/navbar.dropdown -->
		<div class="navbar-buttons navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
			<!-- <span class="badge badge-grey">2</span> -->
				<!--<li title="信息模块" class="grey"><a data-toggle="dropdown"
					class="dropdown-toggle" href="#"> <i
						class="ace-icon fa fa-tasks"></i> </a>
					 <ul
						class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
						<li class="dropdown-header"><i class="ace-icon fa fa-check"></i>
							信息板块</li>
						<li class="dropdown-content">
							<ul class="dropdown-menu dropdown-navbar">
								<li><a style="cursor:pointer"
									onclick="infoMapQuery('投注站信息查询','betting/mapQuery.do','z110','67')">
										<i class="ace-icon fa fa-circle green"></i>投注站信息查询
								</a></li>
								<li><a style="cursor:pointer"
									onclick="infoMapQuery('体育场所信息查询','stadium/mapQuery.do','z113','111')">
										<i class="ace-icon fa fa-bar-chart-o blue"></i>体育场所信息查询 
								</a></li>
								<li><a style="cursor:pointer"
									onclick="infoMapQuery('协会信息查询','pesoinfo/mapQuery.do','z120','114')">
										<i class="ace-icon fa fa-flag purple"></i>协会信息查询 
								</a></li>
							</ul>
						</li>
						<li class="dropdown-footer">
							<a href="http://www.pjtycp.com" target="_blank" style="cursor:pointer;text-align:left;">
								<img style="height:30px;width:150px;" src="static/images/caipiao.png" />
							</a>
						</li>
					</ul> 
				</li>-->
				<!-- <li title="即时聊天" class="purple"  onclick="creatw();">creatw()在 WebRoot\plugins\websocketInstantMsg\websocket.js中
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-bell icon-animated-bell"></i>
								<span class="badge badge-important"></span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-bell-o"></i>
									FH Aadmin 即时通讯
								</li>
							</ul>
						</li> -->

				 <!-- <li title="标准页面" class="purple" onclick="instframe();"
					id="instframe"><a data-toggle="dropdown"
					class="dropdown-toggle" href="#"> <i
						class="ace-icon fa fa-users icon-animated-vertical"></i>

				</a></li> -->

				<!--<li title="练习" class="green" onclick="fhsms();" id="fhsmstss">
					fhsms()在 WebRoot\static\js\myjs\head.js中 <a
					data-toggle="dropdown" class="dropdown-toggle" href="#"> <i
						class="ace-icon fa fa-briefcase icon-animated-vertical"></i> <span class="badge badge-success" id="fhsmsCount"></span>
				</a>
				</li>

				<!-- #section:basics/navbar.user_menu -->
				<li class="light-blue"><a data-toggle="dropdown"
					class="dropdown-toggle" href="#"> <img class="nav-user-photo"
						src="static/ace/avatars/user.jpg" alt="Jason's Photo"
						id="userPhoto" /> <span class="user-info" id="user_info">
					</span> <i class="ace-icon fa fa-caret-down"></i>
				</a>

					<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<!-- <li>
									<a onclick="editPhoto();" style="cursor:pointer;"><i class="ace-icon glyphicon glyphicon-picture"></i>修改头像</a>editUserH()在 WebRoot\static\js\myjs\head.js中
								</li>
								<li>
									<a onclick="editUserH();" style="cursor:pointer;"><i class="ace-icon fa fa-user"></i>修改资料</a>editUserH()在 WebRoot\static\js\myjs\head.js中
								</li>
								<li id="systemset">
									<a onclick="editSys();" style="cursor:pointer;"><i class="ace-icon fa fa-cog"></i>系统设置</a>editSys()在 WebRoot\static\js\myjs\head.js中
								</li>
								<li class="divider"></li> -->
							<li>
								<a onclick="editUserH();" style="cursor:pointer;"><i class="ace-icon fa fa-user"></i>修改资料</a>
							</li>
							<li class="divider"></li>
							<li><a href="logout"><i class="ace-icon fa fa-power-off"></i>退出登录</a></li>
					</ul></li>

				<!-- /section:basics/navbar.user_menu -->
			</ul>
			<div id="fhsmsobj" style="display: none;">站内信声音消息提示</div>
		</div>
		<!-- /section:basics/navbar.dropdown -->
	</div>
	<!-- /.navbar-container -->
</div>
