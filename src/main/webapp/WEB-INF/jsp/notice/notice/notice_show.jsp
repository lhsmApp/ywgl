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
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	<!-- 图片上传 -->
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="static/ace/js/jquery.form.js"></script>
	<link rel="stylesheet" href="static/ace/css/bootstrap-editable.css" />
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
					<form action="notice/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="NOTICE_ID" id="NOTICE_ID" value="${pd.NOTICE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">	
						
						
									
							<div  style="text-align: center;" >
                                <label class="inline" style="width:100%;">
                                        <span style="padding-left:5px;color:#000000;font-size:18px;" class="lbl"><i style="margin-right: 3px;" ></i> ${pd.NOTICE_TITLE}                                
                                        </span>
                                 </label>
                                   <label class="inline" style="width:100%;text-align:left;">
                                  <span style="padding-left:5px;color:#000000;font-size:18px;" class="lbl"><i style="margin-right: 3px;" ></i>
                                  ${pd.NOTICE_CONTENT}</span>
                                  </label>
            				</div>                     
									<div style="width:90%;">
										<label class="inline"">
											<i class="ace-icon fa fa-download green"></i>
											<span style="padding-left:5px;" class="lbl">附件:</span>
										</label>
										<div style="width:90%;text-align: left;">
										<a href="<%=basePath%>notice/download.do?address=${pd.ATTACHMENT_PATH}"> ${pd.ATTACHMENT_PATH}</a>
										</div>
								  </div> 
                                
							<div>
							<div class="inline">
						
								<a class="btn btn-mini btn-danger" onclick="goPaper();">返回列表</a>
							</div>
							<div   style="text-align: center;" >
						</div>
						
						
						
						
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
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/elements.fileinput.js"></script>
	<script src="static/ace/js/ace/ace.js"></script>
		<script type="text/javascript">
		var scope_Arr = {}//记录发布范围
		$(top.hangge());
		$(function(){
			//删除范围
			$("#scope_deil").on("click",".close",function(){
				var key = $(this).attr("data-key")
				delete scope_Arr[key];
				$(this).parent().remove()
				scope_Arr_cookie = []
				for(let sa in scope_Arr){
					scope_Arr_cookie.push(sa+'='+scope_Arr[sa])
				}
	           	$.cookie('userIdList',scope_Arr_cookie.join('&'))
				console.log("notice_edit.jsp:",scope_Arr)
			})
			//日期框
			/* $('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			}); */
			  
			//如果发布范围不为全部，则显示添加框和添加按钮
			$("#TEST_PAPER_DIFFICULTY").change(function(){
			    var currVal = $(this).val()
			    if(currVal != 0){
			        //首先清空之前选择的内容，不然不好处理
			        $("#scope_deil").empty()
			        scope_Arr = {}
			        scope_Arr_cookie = []
			        $.cookie('userIdList','')
			        //再显示添加框和添加按钮
			        $("#select-scope,#addBtn").show()
			    }else{
			        $("#select-scope,#addBtn").hide()
			    }
			})
			
			//初始化 上传的文件
			setTimeout(function(){
				if("${pd.ATTACHMENT_PATH}"){
					$(".ace-file-container").addClass("selected")
					$(".ace-file-name").attr("data-title","${pd.ATTACHMENT_PATH}")
					$(".fa-upload").attr("class","ace-icon fa fa-film file-video")
					
				}
			},1000)
			//上传文件
			$('#file').ace_file_input({
				no_file:'未选择文件',
				btn_choose:'选择',
				btn_change:'选择',
				droppable:false,
				onchange:null,
				thumbnail:false,
				before_change:function(files,dropped){
					var file = files[0];
					var name = file.name;
					//判断文件大小
					if(file.size > 2147483648){
						$("#file").tips({
							side:3,
				            msg:'文件大小不能超过2G',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;
					}
					var options = {
						url: '<%=basePath%>notice/uploadFile.do?tm='+new Date().getTime(),
						type: 'POST',
						dataType: 'json',
						cache: false,
						success: function(data){
							// 动态追加video地址 
							$("#file_path").val(data.path)
							$("#file").tips({
								side:3,
					            msg:'上传成功',
					            bg:'#AE81FF',
					            time:2
					        });
						},
						error: function(data){
							$("#file").tips({
								side:3,
					            msg:'上传失败',
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
				$("#file_path").val('')
			})
		});
		var scope_Arr_cookie = [];
		<c:forEach items="${reItem}" var="var">
		  scope_Arr['${var.BUSINESS_CODE}']= '${var.BUSINESS_NAME}'
		  scope_Arr_cookie.push('${var.BUSINESS_CODE}=${var.BUSINESS_NAME}')
		</c:forEach>
		  
		//初始化 清空cookie  如果是编辑则获取以选中的id存到cookie中
		$.removeCookie('userIdList');
		$.cookie('userIdList',scope_Arr_cookie.join('&'))
		//添加发布范围
		function addExtent(){
		    top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="选择角色";
            diag.URL = '<%=basePath%>notice/goSelectScope.do';
            diag.Width = 700;
            diag.Height = 400;
            diag.Modal = true;             //有无遮罩窗口
            diag. ShowMaxButton = true;    //最大化按钮
            diag.ShowMinButton = true;     //最小化按钮
            diag.CancelEvent = function(){ //关闭事件
            	let is_clickConfirm = diag.innerFrame.contentWindow.document.getElementById("clickConfirm").value
           		if(is_clickConfirm == '0'){
           			diag.close();
           			$.cookie('userIdList',scope_Arr_cookie.join('&'))
           			return
           		}
                
                   /* for(var i=0;i < diag.innerFrame.contentWindow.document.getElementsByName('ids').length;i++){
                     if(diag.innerFrame.contentWindow.document.getElementsByName('ids')[i].checked){
                      var name = diag.innerFrame.contentWindow.document.getElementsByName('ids2')[i].innerHTML
                      var value = diag.innerFrame.contentWindow.document.getElementsByName('ids')[i].value
                       scope_Arr[value]=name//把发布范围添加到对象中
                       scope_Arr_cookie.push(value+'='+name)
                     }
                   }  */
                   
					var str = $.cookie('userIdList')
					scope_Arr_cookie = []
                   	scope_Arr = {}
                   console.log(str)
					var strArr = str.split('&')
					for (let i in strArr){
						let tArr = strArr[i].split('=')
						scope_Arr[tArr[0]] = tArr[1]
						scope_Arr_cookie.push(tArr[0]+'='+tArr[1])
					}
                   $.cookie('userIdList',str)
                   //生成标签
                   $("#scope_deil").empty()//清空
                   for(sa in scope_Arr){        	   
                   		$("#scope_deil").append('<span class="tag"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">'+scope_Arr[sa]+'</font></font><button type="button" class="close" data-key="'+sa+'"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">×</font></font></button></span>')
                   }
        		diag.close();
            };
            diag.show();
		}
		//返回
		function goPaper(){
			history.back(-1)
		}
		 //获取url中的参数  
        function getUrlParam(name) {   
             var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象  
             var r = window.location.search.substr(1).match(reg);  //匹配目标参数   
             if (r != null) return unescape(r[2]); return null; //返回参数值  
        } 
		//保存
		function save(){
			if(!$("#lastStart").val()){
				$("#lastStart").tips({
					side:1,
		            msg:'请选择日期',
		            bg:'#cc0033',
		            time:3
				})
				return;
			}
			if(!$("#lastEnd").val()){
				$("#lastEnd").tips({
					side:1,
		            msg:'请选择日期',
		            bg:'#cc0033',
		            time:3
				})
				return;
			}
			if($("#TEST_PAPER_DIFFICULTY").val()!="0"){
				if(Object.keys(scope_Arr).length==0){
					$("#scope_deil").tips({
						side:1,
			            msg:'发布范围不能为空',
			            bg:'#cc0033',
			            time:3
					})
					return;
				}
			}else{
				scope_Arr = {} //如果范围是面向全部，把这个值设置为空可以稍微节约服务器资源
				scope_Arr_cookie = []
				$.cookie('userIdList','')
			}
			if(!$("#notice_content").val()){
				$("#notice_content").tips({
					side:1,
		            msg:'请输入公告内容',
		            bg:'#cc0033',
		            time:3
				})
				return;
			}
			var scopeFormat = [];
			for(sa in scope_Arr){
				scopeFormat.push({id:sa,name:scope_Arr[sa]})
			}
		    var postData = {
		    		NOTICE_ID:getUrlParam("NOTICE_ID"),
		            START_TIME:$("#lastStart").val(),
		            END_TIME:$("#lastEnd").val(),
		            NOTICE_TYPE:$("#TEST_PAPER_DIFFICULTY").val(),
		            scope_Arr:JSON.stringify(scopeFormat),
		            ATTACHMENT_PATH:$("#file_path").val(),
		            NOTICE_CONTENT:$("#notice_content").val()
		    }
		    $("#zhongxin").hide();
            $("#zhongxin2").show();
		    $.ajax({
                type: "POST",
                url: '<%=basePath%>notice/save.do?tm='+new Date().getTime(),
                data: postData,
                //processData : false,
                //contentType : false,
                //async: false,
                cache: false,
                success: function(data){
                	var json = JSON.parse(data)
                	console.log(json);
                	if(json.ret == '0'){
	                    if(document.referrer){
		                    window.location.href = document.referrer
	                    }else{
	                    	window.location.href = '<%=basePath%>notice/list.do'                    	
	                    }
                	}else{
                		$("#zhongxin").show();
                        $("#zhongxin2").hide();
                		if(json.ret == '-1'){
                			$("#notice_content").tips({
								side:1,
					            msg:'公告内容太长了',
					            bg:'#cc0033',
					            time:3
					        });
                		}
                	}
                },
                complete:function(){
                    $("#zhongxin").show();
                    $("#zhongxin2").hide();
                }
            });
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
        });
</script>
</body>
</html>