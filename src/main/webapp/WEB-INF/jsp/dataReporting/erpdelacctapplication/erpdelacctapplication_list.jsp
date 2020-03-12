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
 <link rel="stylesheet" href="static/ace/css/jquery-ui.css" />
 <style>
    .mtable{width:auto;border-collapse:collapse;}
    .mtable input{background: #FFF !important;border: none;}
</style>
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
									<td>
										<div class="pull-right">
											<span class="green middle bolder">填报类型: &nbsp;</span>
												<div class="btn-toolbar inline middle no-margin">
													<div data-toggle="buttons" class="btn-group no-margin">
														<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toERPOfficialAcctApplication()">
															<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>ERP正式账号申请</span>
														</button>
														<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toERPTempacctApplication()">
															<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>ERP临时账号申请</span>
														</button>
														<button id="btnEdit" class="btn btn-primary btn-xs" onclick="toERPDelAcctApplication()">
															<i class="ace-icon fa fa-chevron-right bigger-110"></i> <span>ERP删除账号申请</span>
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
						<!-- 检索  -->
						<form action="erpdelacctapplication/list.do" method="post" name="Form" id="Form">
						<table style="margin-bottom: 8px;float: left;">
							<tr>
								<td>
									 <c:if test="${not empty listBusiDate}"> 	
										<select class="form-control" id="busiDate" name="busiDate" style="width:150px;margin-left: 5px;" onchange="tosearch()">
											<option value="">全部</option>
											<c:forEach items="${listBusiDate}" var="var">
												<option value="${var.BUSI_DATE}" <c:if test="${pd.busiDate == var.BUSI_DATE}">selected="selected"</c:if>>${var.BUSI_DATE}</option>
											</c:forEach>
										</select>
									</c:if>
								</td>
								<td>
									<select class="form-control" id="confirmState" name="confirmState" style="width:150px;margin-left: 5px;" onchange="tosearch()">
										<option value="1" <c:if test="${pd.confirmState == 1}">selected="selected"</c:if>>待上报</option>
										<option value="2" <c:if test="${pd.confirmState == 2}">selected="selected"</c:if>>已上报</option>
										<option value="4" <c:if test="${pd.confirmState == 4}">selected="selected"</c:if>>已驳回</option>
									</select>
								</td>
								<td style="vertical-align:top;padding-left:3px;">
									<a class="btn btn-info btn-sm" onclick="tosearch()"><i class="ace-icon fa fa-search bigger-110"></i></a>
									<c:if test="${pd.confirmState==1 }">
									<c:if test="${pd.busiDate ==null ||pd.busiDate =='' || pd.month== pd.busiDate}">
									<a class="btn btn-white btn-info btn-bold" onclick="addRows()"><span class="ace-icon fa fa-plus-circle purple"></span>添加</a>						
									</c:if>
									<a class="btn btn-white btn-info btn-bold" onclick="edit()"><span class="ace-icon fa fa-pencil-square-o purple"></span>编辑</a>
									<a class="btn btn-white btn-info btn-bold" onclick="save()"><i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>保存</a>
									</c:if>
									<c:if test="${pd.confirmState!=2 }">
									<a class="btn btn-white btn-info btn-bold" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class="ace-icon fa fa-trash-o bigger-120 orange"></i>删除</a>
									</c:if>
									<c:if test="${pd.confirmState==1 }">
									<a class="btn btn-white btn-info btn-bold" onclick="report('确定要上报当前数据吗?')"><i class="ace-icon fa fa-check-square-o bigger-110"></i>批量上报</a>
									</c:if>
									<c:if test="${pd.confirmState==2 }">
									<a class="btn btn-white btn-info btn-bold" onclick="backReport('确定要撤回上报吗?');" title="批量驳回" ><i class="ace-icon fa fa-exclamation-triangle red bigger-110"></i>撤销上报</a>
									</c:if>
									<c:if test="${pd.confirmState==1 }">
									<c:if test="${pd.busiDate ==null ||pd.busiDate =='' || pd.month== pd.busiDate}">
									<a class="btn btn-white btn-info btn-bold" onclick="importExcel()"><span class="ace-icon fa fa-cloud-upload"></span>导入</a>
									</c:if>
									</c:if>
									<a class="btn btn-white btn-info btn-bold" onclick="toExcel()"><span class="ace-icon fa fa-cloud-download"></span>导出</a>
								</td>
							</tr>
						</table>
						<!-- 检索  -->
						<div  style="width:100%;overflow: auto;min-height: 500px;">
						<table id="simple-table" class="mtable table table-bordered" style="margin-top:5px;width: 1575px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th style="width:110px; height:30px; text-align: center;">员工编号</th>
									<th style="width:110px; text-align: center;">员工姓名</th>
									<th style="width:110px; text-align: center;">二级单位</th>
									<th style="width:110px; text-align: center;">三级单位</th>
									<th style="width:110px; text-align: center;">职务</th>
									<th style="width:110px; text-align: center;">岗位</th>
									<th style="width:110px; text-align: center;">模块</th>
									<th style="width:110px; text-align: center;">联络电话</th>
									<th style="width:110px; text-align: center;">电子邮箱</th>
									<th style="width:110px; text-align: center;">权限变更</th>
									<th style="width:110px; text-align: center;">申请日期</th>
									<th style="width:110px; text-align: center;">账号删除原因</th>
									<th style="width:110px; text-align: center;">备注</th>
									<th style="width:110px; text-align: center;">审批状态</th>
									</tr>
							</thead>	
							<tbody id="copyTable">
							<!-- 开始循环 -->	
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<th><input type="text" name="STAFF_CODE" id="STAFF_CODE" readonly="readonly" value="${var.STAFF_CODE}" maxlength="30" title="员工编号" style="width:100%;"/></th>
											<th><input type="text" name="STAFF_NAME" id="STAFF_NAME" readonly="readonly" value="${var.STAFF_NAME}" maxlength="30" title="员工姓名" style="width:100%;"/></th>
											<th><input type="text" name="DEPART_CODE" id="DEPART_CODE" readonly="readonly" value="${var.DEPART_CODE}" maxlength="30" title="二级单位" style="width:100%;"/></th>
											<th><input type="text" name="UNITS_DEPART" id="UNITS_DEPART" readonly="readonly" value="${var.UNITS_DEPART}" maxlength="30" title="三级单位" style="width:100%;"/></th>
											<th><input type="text" name="STAFF_POSITION" id="STAFF_POSITION" readonly="readonly" value="${var.STAFF_POSITION}" maxlength="50" title="职务" style="width:100%;"/></th>
											<th><input type="text" name="STAFF_JOB" id="STAFF_JOB" readonly="readonly" value="${var.STAFF_JOB}" maxlength="50" title="岗位" style="width:100%;"/></th>
											<th><input type="text" name="STAFF_MODULE" id="STAFF_MODULE" readonly="readonly" value="${var.STAFF_MODULE}" maxlength="50" title="模块" style="width:100%;"/></th>
											<th><input type="text" name="PHONE" id="PHONE" readonly="readonly" value="${var.PHONE}" maxlength="20" title="联络电话" style="width:100%;"/></th>
											<th><input type="text" name="MAIL" id="MAIL" readonly="readonly" value="${var.MAIL}" maxlength="50" title="电子邮箱" style="width:100%;"/></th>
											<th><input type="text" name="PERMISSION_CHANGE" id="PERMISSION_CHANGE" readonly="readonly" value="${var.PERMISSION_CHANGE}" maxlength="100" title="权限变更" style="width:100%;"/></th>
											<th><input type="text" name="APPLY_DATE" id="APPLY_DATE" readonly="readonly" value="${var.APPLY_DATE}" maxlength="30" title="申请日期" style="width:100%;"/></th>
											<th><input type="text" name="ACCOUNT_DELETE_REASON" id="ACCOUNT_DELETE_REASON" readonly="readonly" value="${var.ACCOUNT_DELETE_REASON}" maxlength="200" title="账号删除原因" style="width:100%;"/></th>
											<th><input type="text" name="NOTE" id="NOTE" readonly="readonly" value="${var.NOTE}" maxlength="300" title="备注" style="width:100%;"/></th>
											<th class="center">
												<input type="hidden" id="CONFIRM_STATE" name="CONFIRM_STATE" value="${var.CONFIRM_STATE}"/>
												<c:if test="${var.CONFIRM_STATE == 1}"><span class="label label-success arrowed">待上报</span></c:if>
												<c:if test="${var.CONFIRM_STATE == 2}"><span class="label label-warning arrowed">已上报</span></c:if>
												<c:if test="${var.CONFIRM_STATE == 4}"><span class="label label-danger arrowed">已驳回</span></c:if>
											</th>
										</tr>
									</c:forEach>
							</tbody>
						</table>
						</div>
						<div class="position-relative page-header pull-right">
							<table style="width:100%;">
								<tr>
									<td style="vertical-align:top;"><div class="pagination">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
						</form>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->
	<!-- 复制用空表格 -->
			<div id="hideTable" style="display: none;">
				<table>
					<tbody>
					<tr>
						<td class='center'>
							<label class="pos-rel"><input type='checkbox' name='ids' value="${var.ID}" class="ace" /><span class="lbl"></span></label>
						</td>
						<th><input type="text" name="STAFF_CODE" id="STAFF_CODE" value="" maxlength="30" title="员工编号" style="width:100%;"/></th>
						<th><input type="text" name="STAFF_NAME" id="STAFF_NAME" value="" maxlength="30" title="员工姓名" style="width:100%;"/></th>
						<th><input type="text" name="DEPART_CODE" id="DEPART_CODE" value="${pd.DEPART_CODE}" maxlength="30" title="二级单位" style="width:100%;"/></th>
						<th><input type="text" name="UNITS_DEPART" id="UNITS_DEPART" value="" maxlength="30" title="三级单位" style="width:100%;"/></th>
						<th><input type="text" name="STAFF_POSITION" id="STAFF_POSITION" value="" maxlength="50" title="职务" style="width:100%;"/></th>
						<th><input type="text" name="STAFF_JOB" id="STAFF_JOB" value="" maxlength="50" title="岗位" style="width:100%;"/></th>
						<th><input type="text" name="STAFF_MODULE" id="STAFF_MODULE" value="" maxlength="50" title="模块" style="width:100%;"/></th>
						<th><input type="text" name="PHONE" id="PHONE" value="" maxlength="20" title="联络电话" style="width:100%;"/></th>
						<th><input type="text" name="MAIL" id="MAIL" value="" maxlength="50" title="电子邮箱" style="width:100%;"/></th>
						<th><input type="text" name="PERMISSION_CHANGE" id="PERMISSION_CHANGE" value="" maxlength="100" title="权限变更" style="width:100%;"/></th>
						<th><input type="text" name="APPLY_DATE" id="APPLY_DATE" value="" maxlength="30" title="申请日期" style="width:100%;"/></th>
						<th><input type="text" name="ACCOUNT_DELETE_REASON" id="ACCOUNT_DELETE_REASON" value="" maxlength="200" title="账号删除原因" style="width:100%;"/></th>
						<th><input type="text" name="NOTE" id="NOTE" value="" maxlength="300" title="备注" style="width:100%;"/></th>
						<th class="center"><span class="label label-success arrowed">待上报</span><input type="hidden" id="CONFIRM_STATE" name="CONFIRM_STATE" value="1"/></th>
					</tr>
					</tbody>
				</table>
			</div>
	<!-- basic scripts -->
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
<script type="text/javascript" >
	$(top.hangge());//关闭加载状态
	
	/* 复选框全选控制 */
	$(function() {
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
    //console.log(mandatory) 
    
    // 列名称与name的对应关系
    var colMapping = {
        "员工编号":"STAFF_CODE",
        "员工姓名":"STAFF_NAME",
        "二级单位":"DEPART_CODE",
        "三级单位":"UNITS_DEPART",
        "职务":"STAFF_POSITION",
        "岗位":"STAFF_JOB",
        "模块":"STAFF_MODULE",
        "联络电话":"PHONE",
        "电子邮箱":"MAIL",
        "权限变更":"PERMISSION_CHANGE",
        "申请日期":"APPLY_DATE",
        "账号删除原因":"ACCOUNT_DELETE_REASON",
        "备注":"NOTE",
    },
    fieldMandatory = {
        "ids":{isMust:false,valiType:''},
        'STAFF_CODE':{isMust:false,valiType:''},
        'STAFF_NAME':{isMust:false,valiType:''},
        "DEPART_CODE":{isMust:false,valiType:''},
        "UNITS_DEPART":{isMust:false,valiType:''},
        "STAFF_POSITION":{isMust:false,valiType:''},
        "STAFF_JOB":{isMust:false,valiType:''},
        "STAFF_MODULE":{isMust:false,valiType:''},
        "PHONE":{isMust:false,valiType:'isTelOrMobile'},
        "MAIL":{isMust:false,valiType:'isEmail'},
        "PERMISSION_CHANGE":{isMust:false,valiType:''},
        "APPLY_DATE":{isMust:false,valiType:''},
        "ACCOUNT_DELETE_REASON":{isMust:false,valiType:''},
        "NOTE":{isMust:false,valiType:''},
    }
    
    valida.initWhoIsMandatory(mandatory)//必填字段初始化
    //console.log(fieldMandatory)
	
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
	
	/* ERP正式账号申请 */
	function toERPOfficialAcctApplication(){
		window.location.href='<%=basePath%>erpofficialacctapplication/list.do';
	}
	
	/* ERP临时账号申请 */
	function toERPTempacctApplication(){
		window.location.href='<%=basePath%>erptempacctapplication/list.do';
	}
	/* ERP删除账号申请 */
	function toERPDelAcctApplication(){
		window.location.href='<%=basePath%>erpdelacctapplication/list.do';
	}
	
	/* 新增一行 */
	function addRows(){
		if(!checkPermission()){return;} //权限控制
    	$("#hideTable table tbody tr").clone().appendTo("#copyTable");	           
    }
	
	/* 去除所有input标签的只读属性 */
	function edit(){
		if(!checkPermission()){return;} //权限控制
		if($("#confirmState").val() == 2){
			bootbox.dialog({
				message: "<span class='bigger-110'>已上报内容不可修改!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
			return;
		}
		$('input,select,textarea',$('form[name="Form"]')).prop('readonly',false);
	}
	
	/* 保存 */
	function save(){
		if(!checkPermission()){return;} //权限控制
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
                                if(fieldMandatory[fm]['valiType'] == 'isTelOrMobile'&&!valida.isTelOrMobile(e.val())){
                                    e.tips({
                                        side:3,
                                        msg:'请输入正确的手机号或座机号',
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
			url: '<%=basePath%>erpdelacctapplication/saveAndEdit.do?tm='+new Date().getTime(),
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
				if($("#confirmState").val() == 2){
					bootbox.dialog({
						message: "<span class='bigger-110'>已上报内容不可删除!</span>",
						buttons: 			
						{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
					});
					return;
				}
				
				if(msg == '确定要删除选中的数据吗?'){
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>erpdelacctapplication/deleteAll.do?tm='+new Date().getTime(),
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
    	   diag.URL = '<%=basePath%>erpdelacctapplication/goUploadExcel.do';
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
	
	/*全部上报*/
	function report(msg){
		if(!checkPermission()){return;} //权限控制
		var str = '';
  		var confirm_state = '';
		for(var i=0;i < document.getElementsByName('ids').length;i++){
			confirm_state = document.getElementsByName('CONFIRM_STATE')[i].value;
			if(document.getElementsByName('ids')[i].checked){
				if(str=='') str += document.getElementsByName('ids')[i].value;
			  	else str += ',' + document.getElementsByName('ids')[i].value;
			}
  		}
		if(str == ''){
			bootbox.dialog({
				message: "<span class='bigger-110'>当前暂无待上报数据,请重新筛选!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
			return;
	  	} 
		bootbox.confirm(msg, function(result) {
		  	if(result){
				if(msg == '确定要上报当前数据吗?'){
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>erpdelacctapplication/report.do?tm='+new Date().getTime(),
				    	data: {DATA_IDS:str,"CONFIRM_STATE":"2"},
						dataType:'json',
						cache: false,
						success: function(response){
							if(response.code==0){
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'上报成功',
						            bg:'#009933',
						            time:3
						        });
								history.go(0); //刷新页面
							}else{
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'上报失败,'+response.message,
						            bg:'#cc0033',
						            time:3
						        });
							}
						}
					});
				}
		  	}
		});
	};
	
	/*撤销上报*/
	function backReport(msg){
		if(!checkPermission()){return;} //权限控制
		var str = '';
		var confirm_state = '';
		for(var i=0;i < document.getElementsByName('ids').length;i++){
		  	confirm_state = document.getElementsByName('CONFIRM_STATE')[i].value;
		  	if(document.getElementsByName('ids')[i].checked){
				if(str=='') str += document.getElementsByName('ids')[i].value;
		  		else str += ',' + document.getElementsByName('ids')[i].value;
				console.log(document.getElementsByName('CONFIRM_STATE')[i].value);
		  	}
		}
		if(str == ''){
			bootbox.dialog({
				message: "<span class='bigger-110'>请选择需要撤销的内容!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
			return;
	  	}
		bootbox.confirm(msg, function(result) {
			if(result){
				if(msg == '确定要撤回上报吗?'){
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>erpdelacctapplication/report.do?tm='+new Date().getTime(),
				    	data: {DATA_IDS:str,"CONFIRM_STATE":"1"},
						dataType:'json',
						cache: false,
						success: function(response){
							if(response.code==0){
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'撤回上报成功',
						            bg:'#009933',
						            time:3
						        });
								history.go(0); //刷新页面
							}else{
								$(top.hangge());//关闭加载状态
								$("#subTitle").tips({
									side:3,
						            msg:'撤回上报失败,'+response.message,
						            bg:'#cc0033',
						            time:3
						        });
							}
						}
					});
				}
			}
		});
	};
			
	//导出excel
	function toExcel(){
		window.location.href='<%=basePath%>erpdelacctapplication/excel.do?BUSI_DATE='+$("#busiDate").val()+'&confirmState='+$("#confirmState").val();
	}
	</script>
</body>
</html>