var locat = (window.location+'').split('/'); 
$(function(){if('main'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});

var fmid = "fhindex";	//菜单点中状态
var mid = "fhindex";	//菜单点中状态
var fhsmsCount = 0;		//站内信总数
var USER_ID;			//用户ID
var user = "FH";		//用于即时通讯（ 当前登录用户）
var TFHsmsSound = '1';	//站内信提示音效
var websocket;			//websocket对象
var wimadress="";		//即时聊天服务器IP和端口
var oladress="";		//在线管理和站内信服务器IP和端口

function siMenu(id,fid,MENU_NAME,MENU_URL){
	if(id != mid){
		$("#"+mid).removeClass();
		mid = id;
	}
	if(fid != fmid){
		$("#"+fmid).removeClass();
		fmid = fid;
	}
	$("#"+fid).attr("class","active open");
	$("#"+id).attr("class","active");
	top.mainFrame.tabAddHandler(id,MENU_NAME,MENU_URL);
	if(MENU_URL != "druid/index.html"){
		jzts();
	}
}

$(function(){
	getHeadMsg();	//初始页面最顶部信息

});

//初始页面信息
function getHeadMsg(){
	$.ajax({
		type: "POST",
		url: locat+'/head/getList.do?tm='+new Date().getTime(),
    	data: encodeURI(""),
		dataType:'json',
		//beforeSend: validateData,
		cache: false,
		success: function(data){
			 $.each(data.list, function(i, list){
				 $("#user_info").html('<small>Welcome</small> '+list.NAME+'');//登陆者资料
				 user = list.USERNAME;
				 USER_ID = list.USER_ID;		//用户ID
				 if(list.USERNAME != 'admin'){
					 $("#systemset").hide();	//隐藏系统设置
				 }
			 });
			 updateUserPhoto(data.userPhoto);			//用户头像
			 fhsmsCount = Number(data.fhsmsCount);
			 $("#fhsmsCount").html(Number(fhsmsCount));	//站内信未读总数
			 TFHsmsSound = data.FHsmsSound;				//站内信提示音效
			 wimadress = data.wimadress;				//即时聊天服务器IP和端口
			 oladress = data.oladress;					//在线管理和站内信服务器IP和端口
			 online();									//连接在线
		}
	});
}

//获取站内信未读总数(在站内信删除未读新信件时调用此函数更新未读数)
function getFhsmsCount(){
	$.ajax({
		type: "POST",
		url: locat+'/head/getFhsmsCount.do?tm='+new Date().getTime(),
    	data: encodeURI(""),
		dataType:'json',
		cache: false,
		success: function(data){
			 fhsmsCount = Number(data.fhsmsCount);
			 $("#fhsmsCount").html(Number(fhsmsCount));	//站内信未读总数
		}
	});
}

//加入在线列表
function online(){
	if (window.WebSocket) {
		websocket = new WebSocket(encodeURI('ws://'+oladress)); //oladress在main.jsp页面定义
		websocket.onopen = function() {
			//连接成功
			websocket.send('[join]'+user);
		};
		websocket.onerror = function() {
			//连接失败
		};
		websocket.onclose = function() {
			//连接断开
		};
		//消息接收
		websocket.onmessage = function(message) {
			var message = JSON.parse(message.data);
			if(message.type == 'goOut'){
				$("body").html("");
				goOut("1");
			}else if(message.type == 'thegoout'){
				$("body").html("");
				goOut("2");
			}else if(message.type == 'senFhsms'){
				fhsmsCount = Number(fhsmsCount)+1;
				$("#fhsmsCount").html(Number(fhsmsCount));
				$("#fhsmsobj").html('<audio style="display: none;" id="fhsmstsy" src="static/sound/'+TFHsmsSound+'.mp3" autoplay controls></audio>');
				$("#fhsmstss").tips({
					side:3,
		            msg:'有新消息',
		            bg:'#AE81FF',
		            time:30
		        });
			}
		};
	}
}

//下线
function goOut(msg){
	window.location.href=locat+"/logout.do?msg="+msg;
}

//去通知收信人有站内信接收
function fhsmsmsg(USERNAME){
	var arrUSERNAME = USERNAME.split(';');
	for(var i=0;i<arrUSERNAME.length;i++){
		websocket.send('[fhsms]'+arrUSERNAME[i]);//发送通知
	}
}

//读取站内信时减少未读总数
function readFhsms(){
	fhsmsCount = Number(fhsmsCount)-1;
	$("#fhsmsCount").html(Number(fhsmsCount) <= 0 ?'0':fhsmsCount);
}

//修改头像
function editPhoto(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="修改头像";
	 diag.URL = locat+'/head/editPhoto.do';
	 diag.Width = 650;
	 diag.Height = 530;
	 diag. ShowMaxButton = true;	//最大化按钮
     diag.ShowMinButton = true;		//最小化按钮
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//修改个人资料
function editUserH(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="个人资料";
	 diag.URL = locat+'/user/goEditMy.do';
	 diag.Width = 469;
	 diag.Height = 320;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//系统设置
function editSys(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="系统设置";
	 diag.URL = locat+'/head/goSystem.do';
	 diag.Width = 600;
	 diag.Height = 526;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//站内信
function fhsms(){
	 /*jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="政策法规";
	 diag.URL = locat+'/policyCustom/listCustom.do';
	 diag.Width = 1000;
	 diag.Height = 800;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();*/
	//console.log(user);
	 if(user=="Guest"){
		 $("#mainFrame").attr("src",locat+"/policyCustom/listAllPolicyType.do");
		 $("#subTitle").text("政策法规浏览");
		 $("#title").text("政策法规浏览");
	 }
	 else
		 //siMenu('118','117','政策法规','policy/list.do');
//		 siMenu('10000','1000','政策法规浏览','policyCustom/listAllPolicyType.do');
		 siMenu('10005','1000','jqgridTest','jqgrid/listTest.do');

}

//组织机构
function instframe(){
	 if(user=="Guest"){
		 $("#mainFrame").attr("src",locat+"/instframe/getInstframeGuest.do");
		 $("#subTitle").text("组织机构浏览");
		 $("#title").text("组织机构浏览");
	 }
	 else
		 //siMenu('10001','1000','组织机构浏览','instframe/getInstframeGuest.do');
	 	siMenu('10005','1000','成本核算','jqgridJia/list.do');
}

//信息查询
function infoMapQuery(title,url,id,parentId){
	 if(user=="Guest"){
		 $("#mainFrame").attr("src",locat+"/"+url);
		 $("#subTitle").text(title);
		 $("#title").text(title);
	 }
	 else
		 siMenu(id,parentId,title,url);
}

//切换菜单
function changeMenus(){
	window.location.href=locat+'/main/yes';
}

//清除加载进度
function hangge(){
	$("#jzts").hide();
}

//显示加载进度
function jzts(){
	$("#jzts").show();
}

//刷新用户头像
function updateUserPhoto(value){
	$("#userPhoto").attr("src",value);//用户头像
}
