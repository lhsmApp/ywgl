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
<!--自由拉动  -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<link rel="stylesheet" href="static/ace/css/jquery-ui.css" />
<style>
    .mtable{width:auto;border-collapse:collapse;}
    .mtable input{background: #FFF !important;border: none;}
</style>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="page-header">
						<table style="width:100%;">
							<tbody>
								<tr>
									<td>
										<div class="pull-right">
											<span class="green middle bolder">填报类型: &nbsp;</span>
											<div class="btn-toolbar inline middle no-margin">
												<div data-toggle="buttons" class="btn-group no-margin">
													<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toGRCPerson()">
														<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>GRC人员信息</span>
													</button>
													<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toGRCApprovalMatrix()">
															<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>GRC审批矩阵</span>
													</button>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
						<div class="row">
						<form action="grcapprovalmatrix/list.do" method="post" name="Form" id="Form">					
							<table style="margin-bottom: 6px;">
								<tr>
									<td>
										<select class="form-control" id="busiDate" name="busiDate" style="vertical-align:top; width:150px;margin-left: 5px;" data-placeholder="请选择业务期间" onchange="tosearch()">
											<option value="">全部</option>
											<c:forEach items="${listBusiDate}" var="var">
												<option value="${var.BUSI_DATE}" <c:if test="${pd.busiDate == var.BUSI_DATE}">selected="selected"</c:if>>${var.BUSI_DATE}</option>
											</c:forEach>
										</select>
									</td>
								<td style="vertical-align:top;padding-left:2px;">
								<a class="btn btn-info btn-sm" onclick="tosearch()"><i class="ace-icon fa fa-search bigger-110"></i></a>
								<c:if test="${pd.busiDate ==null ||pd.busiDate =='' || pd.month== pd.busiDate}">
								<a class="btn btn-white btn-info btn-bold" onclick="addRows()"><span class="ace-icon fa fa-plus-circle purple"></span>添加</a>						
								</c:if>
								<a class="btn btn-white btn-info btn-bold" onclick="edit()"><span class="ace-icon fa fa-pencil-square-o purple"></span>编辑</a>
								<a class="btn btn-white btn-info btn-bold" onclick="save()"><i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>保存</a>
								<a class="btn btn-white btn-info btn-bold" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class="ace-icon fa fa-trash-o bigger-120 orange"></i>删除</a>
								<c:if test="${pd.busiDate ==null ||pd.busiDate =='' || pd.month== pd.busiDate}">
								<a class="btn btn-white btn-info btn-bold" onclick="importExcel()"><span class="ace-icon fa fa-cloud-upload"></span>导入</a>
								</c:if>
								<a class="btn btn-white btn-info btn-bold" onclick="toExcel()"><span class="ace-icon fa fa-cloud-download"></span>导出</a>
								</tr>
							</table>
							<div style="width: 100%;overflow: auto;min-height: 500px;">
							<table id="simple-table" class="mtable table table-bordered" style="margin-top:5px; width: 99%;">	
								<thead style="height: 40px">
									<tr>
										<th class="center" style="width:35px; ">
										<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
										</th>
										<th style="width:150px;  text-align: center;">业务模块</th>
										<th style="width:150px;  text-align: center;">申请人员工编号</th>
										<th style="width:150px;  text-align: center;">申请人员工姓名</th>
										<th style="width:150px;  text-align: center;">一级审批人员工编号</th>
										<th style="width:150px;  text-align: center;">一级审批人员工姓名</th>
									</tr>
								</thead>
								<tbody id="copyTable">
								<!-- 开始循环 -->	
										<c:forEach items="${varList}" var="var" varStatus="vs">
											<tr>
												<td class='center'>
													<label class="pos-rel"><input type='checkbox' name='ids' value="${var.ID}" class="ace" /><span class="lbl"></span></label>
												</td>
												<th><input type="text" name="BUSINESS_MODULE" id="BUSINESS_MODULE" readonly="readonly" value="${var.BUSINESS_MODULE}" maxlength="50" title="业务模块" style="width:100%;"/></th>
												<th><input type="text" name="STAFF_CODE" id="STAFF_CODE" readonly="readonly" value="${var.STAFF_CODE}" maxlength="30" title="申请人员工编号" style="width:100%;"/></th>
												<th><input type="text" name="STAFF_NAME" id="STAFF_NAME" readonly="readonly" value="${var.STAFF_NAME}" maxlength="30" title="申请人员工姓名" style="width:100%;"/></th>
												<th><input type="text" name="STAFF_CODE_APPROVAL1" id="STAFF_CODE_APPROVAL1" readonly="readonly" value="${var.STAFF_CODE_APPROVAL1}" maxlength="30" title="一级审批人员工编号" style="width:100%;"/></th>
												<th><input type="text" name="STAFF_NAME_APPROVAL1" id="STAFF_NAME_APPROVAL1" readonly="readonly" value="${var.STAFF_NAME_APPROVAL1}" maxlength="30" title="一级审批人员工姓名" style="width:100%;"/></th>
											</tr>
										</c:forEach>
								</tbody>
							</table>
							</div>
							<!-- 分页 -->
							<div class="position-relative page-header pull-right">
								<table style="width:100%;">
									<tr>
										<td style="vertical-align:top;"><div class="pagination">${page.pageStr}</div></td>
									</tr>
								</table>
							</div>
							<!-- 分页结束 -->
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 返回顶部 -->
	<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>
	<!-- 复制用空表格 -->
	<div id="hideTable" style="display: none;">
		<table>
			<tbody>
				<tr>
					<td class='center'>
					<label class="pos-rel"><input style="" type='checkbox' name='ids' value="" class="ace"/><span class="lbl"></span></label>
					</td>
					<th><input type="text" name="BUSINESS_MODULE" id="BUSINESS_MODULE" value="" maxlength="50" title="业务模块" style="width:100%;"/></th>
					<th><input type="text" name="STAFF_CODE" id="STAFF_CODE" value="" maxlength="30" title="申请话其人员工编号" style="width:100%;"/></th>
					<th><input type="text" name="STAFF_NAME" id="STAFF_NAME" value="" maxlength="30" title="申请话其人员工姓名" style="width:100%;"/></th>
					<th><input type="text" name="STAFF_CODE_APPROVAL1" id="STAFF_CODE_APPROVAL1" value="" maxlength="30" title="一级审批人员工编号" style="width:100%;"/></th>
					<th><input type="text" name="STAFF_NAME_APPROVAL1" id="STAFF_NAME_APPROVAL1" value="" maxlength="30" title="一级审批人员工姓名" style="width:100%;"/></th>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 自由拉动 -->
	<script type="text/javascript" src="static/ace/js/jquery-ui.js"></script>
    <!-- js正则库 -->
    <script type="text/javascript" src="static/js/common/validation.js"></script>
	<script type="text/javascript">
		/* 关闭加载状态 */
		$(top.hangge());
		//table自由拉动
		var tablewidth = 0;
		$("th").resizable({
			start:function(event,ui){
				tablewidth = $("#simple-table").width()-ui.size.width;
			},resize:function(event,ui){
				$("#simple-table").css("width",tablewidth+ui.size.width+"px")
			},stop:function(event,ui){
				$("#simple-table").css("width",tablewidth+ui.size.width+"px")
			}
		})
		/* 复选框全选控制 */
		$(function() {
			$("th").resizable(); //调用方法，实现可自由调整
			$("th > div:last-child").removeClass();
			
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		
		/*初始化数据*/
		var month = '${pd.month}';
		var day = '${pd.LTD_DAY}';
		var date = new Date();
	    var year = '${pd.busiDate}';
	    var strDate = date.getDate();
	    var mandatory = '${pd.mandatory}';
        
        // 列名称与name的对应关系
        var colMapping = {
            "业务模块":"BUSINESS_MODULE",
            "申请人员工编号":"STAFF_CODE",
            "申请人员工姓名":"STAFF_NAME",
            "一级审批人员工编号":"STAFF_CODE_APPROVAL1",
            "一级审批人员工姓名":"STAFF_NAME_APPROVAL1"
        },
        fieldMandatory = {
            "ids":{isMust:false,valiType:''},
            'BUSINESS_MODULE':{isMust:false,valiType:''},
            'STAFF_CODE':{isMust:false,valiType:''},
            "STAFF_NAME":{isMust:false,valiType:''},
            "STAFF_CODE_APPROVAL1":{isMust:false,valiType:''},
            "STAFF_NAME_APPROVAL1":{isMust:false,valiType:''}
        }
        
        valida.initWhoIsMandatory(mandatory)//必填字段初始化
	    
	    /*权限控制*/
	    function checkPermission(){
			var state = false;
			if(day == ''){
				return true;
			}
			if(month == year){//先判断月份
			    if(day < strDate){
					bootbox.dialog({
						message: '<span class="bigger-110">请在'+day+'号前进行操作!</span>',
						buttons: 			
						{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
					});
			    }else{
			    	state = true;
			    }
		  	}else {
		  		bootbox.dialog({
					message: "<span class='bigger-110'>当前业务期间不支持操作!</span>",
					buttons: 			
					{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
				});
			} 
			return state;
		}
		
		/* 检索 */
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		
		/* GRC人员信息 */
		function toGRCPerson(){
			window.location.href='<%=basePath%>grcperson/list.do';
		}
		
		/* GRC审批矩阵 */
		function toGRCApprovalMatrix(){
			window.location.href='<%=basePath%>grcapprovalmatrix/list.do';
		}
		
		/* 新增一行 */
		function addRows(){
			if(!checkPermission()){return;} //权限控制
	    	$("#hideTable table tbody tr").clone().appendTo("#copyTable");	           
	    }
		
		/* 去除所有input标签的只读属性 */
		function edit(){
			if(!checkPermission()){return;} //权限控制
			$('input,select,textarea',$('form[name="Form"]')).prop('readonly',false);
		}
		
		/* 保存 */
		function save(){
			if(!checkPermission()){return;} //权限控制
			var ID = '';
			var BUSINESS_MODULE = '';
			var STAFF_CODE = '';
			var STAFF_NAME = '';
			var STAFF_CODE_APPROVAL1 = '';
			var STAFF_NAME_APPROVAL1 = '';
			var codeVal = '';
			var listData = new Array();
			for(var i=0;i < document.getElementsByName('STAFF_CODE').length-1;i++){
					//length-1 : 页面中有用于复制的隐藏文本
					//判断：如果每个必填项都为空，则判定本行无效
	                var not_null = true
	                for(var fm in fieldMandatory){
	                    if(fieldMandatory[fm]['isMust']){
	                        not_null = false
	                        var e = $("input[name="+fm+"]:eq("+i+")")
	                        if(e.val()){
	                            not_null = true
	                            break;
	                        }
	                    }
	                }
					if(not_null){
						/* 参数校验 begin */
	                    for(fm in fieldMandatory){
	                        if(fieldMandatory[fm]['isMust']){
	                            var e = $("input[name="+fm+"]:eq("+i+")")
	                            if(!e.val()){
	                                e.tips({
	                                    side:3,
	                                    msg:'这里是必填内容',
	                                    bg:'#cc0033',
	                                    time:3
	                                });
	                                return;
	                            }else if(fieldMandatory[fm]['valiType'] != ''){
	                                if(fieldMandatory[fm]['valiType'] == 'isTel'&&!valida.isTel(e.val())){
	                                    e.tips({
	                                        side:3,
	                                        msg:'请输入正确的座机号',
	                                        bg:'#cc0033',
	                                        time:3
	                                    });
	                                    return;
	                                }else if(fieldMandatory[fm]['valiType'] == 'isMobile'&&!valida.isMobile(e.val())){
	                                    e.tips({
	                                        side:3,
	                                        msg:'请输入正确的手机号',
	                                        bg:'#cc0033',
	                                        time:3
	                                    });
	                                    return;
	                                }else if(fieldMandatory[fm]['valiType'] == 'isEmail'&&!valida.isEmail(e.val())){
	                                    e.tips({
	                                        side:3,
	                                        msg:'请输入正确的邮箱格式',
	                                        bg:'#cc0033',
	                                        time:3
	                                    });
	                                    return;
	                                }
	                            }
	                        }
	                    }
	                    /* 参数校验 end */
	                    for(fm in fieldMandatory){
	                        var e = $("input[name="+fm+"]:eq("+i+")")
	                        listData.push(e.val())
	                    } 
				}
			}
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>grcapprovalmatrix/saveAndEdit.do?tm='+new Date().getTime(),
		    	data: {"listData":JSON.stringify(listData)},
				dataType:'json',
				cache: false,
				success: function(response){
					if(response.code==0){
						$(top.hangge());//关闭加载状态
						history.go(0); //刷新页面
						$("#subTitle").tips({
							side:3,
				            msg:'添加修改成功',
				            bg:'#009933',
				            time:3
				        });
					}else{
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side:3,
				            msg:'添加修改失败,'+response.message,
				            bg:'#cc0033',
				            time:3
				        });
					}
				},
		    	error: function(e) {
		    		$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'添加修改失败,'+response.responseJSON.message,
			            bg:'#cc0033',
			            time:3
			        });
		    	}
			});
		}		
		
		//批量操作
		function makeAll(msg){
			if(!checkPermission()){return;} //权限控制
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++){
			  if(document.getElementsByName('ids')[i].checked){
			  	if(str=='') str += document.getElementsByName('ids')[i].value;
			  	else str += ',' + document.getElementsByName('ids')[i].value;
			  }
			}
			if(str==''){
				bootbox.dialog({
					message: "<span class='bigger-110'>您没有选择任何内容!</span>",
					buttons: 			
					{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
				});
				$("#zcheckbox").tips({
					side:1,
		            msg:'点这里全选',
		            bg:'#AE81FF',
		            time:8
		        });
				return;
			}
			bootbox.confirm(msg, function(result) {
				if(result) {
					if(msg == '确定要删除选中的数据吗?'){
						top.jzts();
						$.ajax({
							type: "POST",
							url: '<%=basePath%>grcapprovalmatrix/deleteAll.do?tm='+new Date().getTime(),
					    	data: {DATA_IDS:str},
							dataType:'json',
							cache: false,
							success: function(response){
								if(response.code==0){
									$(top.hangge());//关闭加载状态
									history.go(0); //刷新页面
									$("#subTitle").tips({
										side:3,
							            msg:'删除成功',
							            bg:'#009933',
							            time:3
							        });
								}else{
									$(top.hangge());//关闭加载状态
									$("#subTitle").tips({
										side:3,
							            msg:'删除失败,'+response.message,
							            bg:'#cc0033',
							            time:3
							        });
								}
							},
					    	error: function(e) {
					    		$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'删除失败,'+response.responseJSON.message,
						            bg:'#cc0033',
						            time:3
						        });
					    	}
						});
					}
				}
			});
		};
		
		/* 导入Excel */
		function importExcel(){
			   if(!checkPermission()){return;} //权限控制
			   top.jzts();
	    	   var diag = new top.Dialog();
	    	   diag.Drag=true;
	    	   diag.Title ="EXCEL 导入到数据库";
	    	   diag.URL = '<%=basePath%>grcapprovalmatrix/goUploadExcel.do';
	    	   diag.Width = 300;
	    	   diag.Height = 150;
	    	   diag.CancelEvent = function(){ //关闭事件
	    		  top.jzts();
	    		  history.go(0); //刷新页面
	    		  $(top.hangge());//关闭加载状态
	    	      diag.close();
	           };
	           diag.show();
		}
		
		/* 导出excel */
		function toExcel(){
			window.location.href='<%=basePath%>grcapprovalmatrix/excel.do?BUSI_DATE='+$("#busiDate").val();
		}
	</script>
</body>
</html>