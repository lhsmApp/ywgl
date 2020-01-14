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
                <div class="page-header">
                    <table style="width:100%;">
                        <tbody>
                            <tr>
                                <td style="vertical-align:top;">
                                    <span class="green middle bolder">新建公告: &nbsp;</span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
				<div class="row">
					<div class="col-xs-12">
					<form action="notice/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="NOTICE_ID" id="NOTICE_ID" value="${pd.NOTICE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
							<div id="task-tab" class="tab-pane active">
							<ul id="tasks_1" class="item-list ui-sortable">
									<li class="item-orange clearfix ui-sortable-handle">
										<label class="inline"><span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon fa fa-calendar orange"></i>起止时间:</span></label>
										<div class="inline">
										<div style="padding-left:2px;" class="inline"><input class="span10 date-picker inline" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd 00:00:00" readonly="readonly" style="width:143px;" placeholder="开始日期" title="开始日期"/></div>
										<div style="padding-left:2px;" class="inline"><input class="span10 date-picker inline" name="lastEnd" id="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd 23:59:59" readonly="readonly" style="width:143px;" placeholder="结束日期" title="结束日期"/></div>
										</div>
									</li>
									<li id="liBefore" class="item-green clearfix ui-sortable-handle">
										<label class="inline">
											<span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon fa fa-asterisk green"></i>发布范围:</span>
										</label>
    									<select id="TEST_PAPER_DIFFICULTY" name="TEST_PAPER_DIFFICULTY" class="form-control inline" style="width:155px;">
    										<option value="0">全部</option>
    										<%-- <option value="1" <c:if test="${pd.TEST_PAPER_DIFFICULTY == 1}">selected</c:if>>按角色选择</option>
    										<option value="2" <c:if test="${pd.TEST_PAPER_DIFFICULTY == 2}">selected</c:if>>按单位选择</option> --%>
    										<option value="3" <c:if test="${pd.TEST_PAPER_DIFFICULTY == 3}">selected</c:if>>按人员选择</option>
    									</select>
                                        <a id="addBtn" class="btn btn-mini btn-primary" onclick="addExtent()" style="display: none;"><i id="nav-add-icon" class="ace-icon glyphicon glyphicon-plus while"></i>添加</a>
									</li>
                                    <li id="select-scope" class="item-blue clearfix ui-sortable-handle" style="display:none;">
                                        <label class="inline" style="width:100%;"><span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon fa fa-users orange"></i>当前发布范围包括：</span></label>
                                        <div class="inline" style="width:100%;">
                                            <div class="tags" id="scope_deil" style="width:100%;min-height: 82px;max-height: 200px;overflow: auto;"></div>
                                        </div>
                                    </li>
									<li id="attachmentLi" class="item-blue clearfix ui-sortable-handle">
										<label class="inline">
											<i class="ace-icon fa fa-download green"></i>
											<span style="padding-left:5px;" class="lbl">附件:</span>
										</label>
										<div>
											<input id="file" name="video" type="file"/>
                                            <input id="file_path" type="hidden">
											<input type="hidden" name="ATTACHMENT_PATH" id="ATTACHMENT_PATH" value="${pd.ATTACHMENT_PATH}"/> 
										</div>
									</li>
                                    <li id="ContentLi" class="item-blue clearfix ui-sortable-handle">
                                        <label class="inline" style="width:100%;"><span style="padding-left:5px;" class="lbl"><i style="margin-right: 3px;" class="ace-icon fa fa-pencil-square-o orange"></i>公告内容：</span></label>
                                        <div class="inline" style="width:100%;">
                                            <textarea id="notice_content" style="width:100%;"></textarea>
                                        </div>
                                    </li>
						<li id="liBefore" class="item-blue clearfix ui-sortable-handle">
							<div class="inline">
								<a class="btn btn-mini btn-primary" onclick="save();">发布公告</a>
								<a class="btn btn-mini btn-danger" onclick="goPaper();">清除内容</a>
							</div>
						</li>
					</ul>
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
		var scope_Arr = []//记录发布范围
		$(top.hangge());
		$(function(){
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
			        scope_Arr = []
			        //再显示添加框和添加按钮
			        $("#select-scope,#addBtn").show()
			    }else{
			        $("#select-scope,#addBtn").hide()
			    }
			})
			
			//上传视频
			$('#file').ace_file_input({
				no_file:'No File ...',
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
						$("#file").tips({
							side:3,
				            msg:'仅可上传 .mp4 格式视频',
				            bg:'#AE81FF',
				            time:2
				        });
						return false;	 
					} */
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
						url: '<%=basePath%>coursedetail/uploadVideo.do?tm='+new Date().getTime(),
						type: 'POST',
						dataType: 'json',
						cache: false,
						success: function(data){
							// 动态追加video地址 
							$("#file_path").val(data.path)
							$("#file").tips({
								side:3,
					            msg:'视频上传成功',
					            bg:'#AE81FF',
					            time:2
					        });
						},
						error: function(data){
							$("#file").tips({
								side:3,
					            msg:'视频上传失败',
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
                $("#scope_deil").empty()//清空
                var str = '';
                   for(var i=0;i < diag.innerFrame.contentWindow.document.getElementsByName('ids').length;i++){
                     if(diag.innerFrame.contentWindow.document.getElementsByName('ids')[i].checked){
                      var name = diag.innerFrame.contentWindow.document.getElementsByName('ids2')[i].innerHTML
                      var value = diag.innerFrame.contentWindow.document.getElementsByName('ids')[i].value
                      $("#scope_deil").append('<span class="tag"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">'+name+'</font></font><button type="button" class="close" data-key="'+value+'"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">×</font></font></button></span>')
                       scope_Arr.push({id:value,name:name})
                     }
                   }
                   //code
                   //selected_Arr["scope_Arr"] = scope_Arr
               diag.close();
            };
            diag.show();
		}
		
		//保存
		function save(){
		    var postData = {
		            START_TIME:$("#lastStart").val(),
		            END_TIME:$("#lastEnd").val(),
		            NOTICE_TYPE:$("#TEST_PAPER_DIFFICULTY").val(),
		            scope_Arr:JSON.stringify(scope_Arr),
		            ATTACHMENT_PATH:$("#file_path").val(),
		            NOTICE_CONTENT:$("#notice_content").val()
		    }
		    console.log(postData)
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
                    window.location.href = '<%=basePath%>notice/list.do'
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