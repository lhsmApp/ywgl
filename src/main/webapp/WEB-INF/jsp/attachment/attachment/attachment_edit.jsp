<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- <script type="text/javascript" src="static/ace/js/jquery.js"></script> -->
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/ace/js/jquery.form.js"></script>
	<link rel="stylesheet" href="static/ace/css/bootstrap.css" />
	<link rel="stylesheet" href="static/ace/css/bootstrap-editable.css" />
	<!-- 上传插件 -->
	<!-- <link href="plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="plugins/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="plugins/uploadify/jquery.uploadify.v2.1.4.min.js"></script> -->
	
	<%-- <link href="plugins/uploadify3.2.1/uploadify.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="plugins/uploadify3.2.1/jquery.uploadify.js"></script>
	<!-- 上传插件 -->
	<script type="text/javascript">
		var jsessionid = "<%=session.getId()%>";  //勿删，uploadify兼容火狐用到
	</script> --%>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="attachment/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" value="${pd.BUSINESS_TYPE}" name="BUSINESS_TYPE" id="BUSINESS_TYPE" />
						<input type="hidden" value="${pd.BILL_CODE}" name="BILL_CODE" id="BILL_CODE" />
						<input type="hidden"  value="${pd.ATTACHMENT_PATH}" name="ATTACHMENT_PATH" id="ATTACHMENT_PATH"/> 
						<!-- <input type="hidden" value="no" id="hasTp1" /> -->
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">附件名:</td>
								<td><input type="text" name="ATTACHMENT_NAME" id="ATTACHMENT_NAME" value="" maxlength="30" placeholder="这里输入附件名" title="文件名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">文件:</td>
								<!-- <td>
									<div id="fileQueue">
										<input type="file" name="File_name" id="uploadify1" keepDefaultStyle = "true"/>
										<input type="hidden" name="ATTACHMENT_PATH" id="ATTACHMENT_PATH" value=""/>
									</div>
								</td> -->
								<td><input id="attachment" name="attachment" type="file"/></td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">附件说明:</td>
								<td><input type="text" name="ATTACHMENT_MEMO" id="ATTACHMENT_MEMO" value="" maxlength="100" placeholder="这里输入附件说明" title="附件说明" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
	<%@ include file="../../system/index/foot.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<script src="static/ace/js/ace/ace.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
		
		//保存
		function save(){
			if($("#ATTACHMENT_NAME").val()==""){
				$("#ATTACHMENT_NAME").tips({
					side:3,
		            msg:'请输入文件名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ATTACHMENT_NAME").focus();
				return false;
			}
			if($("#ATTACHMENT_PATH").val()==""){
				$("#attachment").tips({
					side:3,
		            msg:'请选择附件',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#attachment").focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
			/* if($("#hasTp1").val()=="no"){
				$("#FILEPATHn").tips({
					side:2,
			        msg:'请选择文件',
			        bg:'#AE81FF',
			        time:2
			    });
			return false;
			} */
			//$('#uploadify1').uploadifyUpload();
			
			//$('#uploadify1').uploadify('upload', '*');
		}
		
		//====================上传=================
		$(document).ready(function(){
			//上传视频
			$('#attachment').ace_file_input({
				no_file:'选择文件',
				btn_choose:'选择',
				btn_change:'选择',
				droppable:false,
				onchange:null,
				thumbnail:false,
				before_change:function(files,dropped){
					var file = files[0];
					var name = file.name;
					//判断文件类型
					/* if (!name.endsWith(".mp4")){
						$("#attachment").tips({
							side:3,
				            msg:'仅可上传 .mp4 格式视频',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;	 
					} */
					//判断文件大小
					if(file.size > 2147483648){
						$("#attachment").tips({
							side:3,
				            msg:'文件大小不能超过2G',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;
					}
					var options = {
						url: '<%=basePath%>attachment/uploadAttachment.do?tm='+new Date().getTime(),
						type: 'POST',
						dataType: 'json',
						cache: false,
						success: function(data){
							// 动态追加video地址 
							$("#ATTACHMENT_PATH").attr("value",data.path);
							$("#attachment").tips({
								side:3,
					            msg:'文件上传成功',
					            bg:'#AE81FF',
					            time:2
					        });
						},
						error: function(data){
							$("#attachment").tips({
								side:3,
					            msg:'文件上传失败',
					            bg:'#AE81FF',
					            time:2
					        });
						}
					}
					$("#Form").ajaxSubmit(options);
					return true;
				}
			});
			$(".remove").bind('click',function(){
				//取消上传
			})
			
			
			<%-- /* if ($.browser.msie) {
		    	$(&quot;.swfupload&quot;).attr(&quot;classid&quot;,&quot;clsid:D27CDB6E-AE6D-11cf-96B8-444553540000&quot;);
		    } */
			
			var str='';
			$("#uploadify1").uploadify({
				'buttonImg'	: 	"<%=basePath%>static/images/fileup.png",
				'uploader'	:	"<%=basePath%>plugins/uploadify/uploadify.swf",
				'script'    :	"<%=basePath%>plugins/uploadify/uploadFile.jsp;jsessionid="+jsessionid,
				'cancelImg' :	"<%=basePath%>plugins/uploadify/cancel.png",
				'folder'	:	"<%=basePath%>uploadFiles/uploadFile",//上传文件存放的路径,请保持与uploadFile.jsp中PATH的值相同
				'queueId'	:	"fileQueue",
				'queueSizeLimit'	:	1,//限制上传文件的数量
				//'fileExt'	:	"*.rar,*.zip",
				//'fileDesc'	:	"RAR *.rar",//限制文件类型
				'fileExt'     : '*.*;*.*;*.*',
				'fileDesc'    : 'Please choose(.*, .*, .*)',
				'auto'		:	false,
				'multi'		:	true,//是否允许多文件上传
				'simUploadLimit':	2,//同时运行上传的进程数量
				'buttonText':	"files",
				'scriptData':	{'uploadPath':'/uploadFiles/uploadFile/'},//这个参数用于传递用户自己的参数，此时'method' 必须设置为GET, 后台可以用request.getParameter('name')获取名字的值
				'method'	:	"GET",
				'onComplete':function(event,queueId,fileObj,response,data){
					str = response.trim();//单个上传完毕执行
				},
				'onAllComplete' : function(event,data) {
					//alert(str);	//全部上传完毕执行
					$("#ATTACHMENT_PATH").val(str);
					$("#Form").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
		    	},
		    	'onSelect' : function(event, queueId, fileObj){
		    		$("#hasTp1").val("ok");
		    	}
		    	
		    	
		        'formData':{
		            'uploadPath':'/uploadFiles/uploadFile/'
		          },
		    	'width': 120,  
		        //uploadify v3.1之前的用法
	            //uploader:"${pageContext.request.contextPath}common/upload/swf/uploadify.swf",        
	            //script: "${pageContext.request.contextPath}/uploadify.action?random="+Math.random(),  
		        
	            //uploadify v3.1之后的用法
		        //flash文件位置，包含BROWSE的按钮，点击打开文件选择框
		        'swf' : '<%=basePath%>plugins/uploadify3.2.1/uploadify.swf', 
		        //后台处理的请求(sevlet)  
		        'uploader': '<%=basePath%>plugins/uploadify/uploadFile.jsp;jsessionid='+jsessionid,
		        //选定文件后是否自动上传，默认false  
		        'auto': false,  
		        //取消按钮图片  
		        'cancelImg': '<%=basePath%>plugins/uploadify3.2.1/uploadify-cancel.png',  
		        //与下面的上传文件列表id对应  
		        'queueID': 'fileQueue',
		        //上传文件的数量  
		        'queueSizeLimit': 1,  
		        //上传文件类型说明  
		        /* 'fileTypeExts': '*.doc;*.docx;*.jpg;*.png', */ 
		        'fileTypeExts': '*.*',
		        //浏览按钮上的文字  
		        'buttonText' : '选择文件',
		        //设置提交方式，默认为post
		        'method': 'get',  
		        //设置是否允许多文件上传
		        'multi': false,  
		        //是否自动将已上传文件删除
		        'removeCompleted':false,
		           
		        //上传文件的大小限制。默认单位kb
		        'fileSizeLimit' : '256MB',
		        //当Uploadify初始化过程中检测到当前浏览器不支持flash时触发。
		        'onFallBack' : function(){
		          alert("当前浏览器不支持Flash");
		         },
		        //当文件即将开始上传时立即触发
		        'onUploadStart' : function (file) {
		          /* alert("id:" + file.id + " -索引:" + file.index + " -文件名称:" + file.name +
		          " -文件大小:" + file.size + " -文件类型:" + file.type + " -创建日期:" + file.creationdate +
		          " -修改日期:" + file.modificationdate + " -文件状态:" + file.filestatus); */
		         },
		        //文件上传队列处理完毕后触发。
		        'onQueueComplete' : function (stats) {
			        /* alert("成功上传的文件数：" + stats.uploadsSuccessful + " -上传出错的文件数：" +
			        stats.uploadsErrored + " -上传的文件总大小：" + stats.uploadSize); */
	        	  
				  $("#Form").submit();
				  $("#zhongxin").hide();
				  $("#zhongxin2").show();
		        },
		        /* onUploadSuccess为成功上传后的回调函数 file 为上传的文件，可通过file.name 获取文件名 size 可获取大小
		        data 为后台reponse输出的字符串，上例中输出的是 json 对象，故使用eval 进行转换
		        response 为 结果 true or false，具体可参考官方文档 */
		        'onUploadSuccess' : function(file, data, response){  
		        	$("#ATTACHMENT_PATH").val(data.trim());
		        } ,
			}); --%>
		});
		//====================上传=================
			//清除空格
		String.prototype.trim=function(){
		     return this.replace(/(^\s*)|(\s*$)/g,'');
		};
		</script>
</body>
</html>