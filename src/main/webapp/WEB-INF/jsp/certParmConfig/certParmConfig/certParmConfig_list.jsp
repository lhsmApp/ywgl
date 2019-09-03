<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 ，其中包含旧版本（Ace）Jqgrid Css-->
<%@ include file="../../system/index/topWithJqgrid.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />

<!-- 最新版的Jqgrid Css，如果旧版本（Ace）某些方法不好用，尝试用此版本Css，替换旧版本Css -->
<!-- <link rel="stylesheet" type="text/css" media="screen" href="static/ace/css/ui.jqgrid-bootstrap.css" /> -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!-- <script type="text/javascript" src="static/ace/js/jquery.js"></script> -->
<!-- 标准页面统一样式 -->
<link rel="stylesheet" href="static/css/normal.css" />

<style>

ul{list-style-type:none;}

li{border:solid 1px;width:100px;}

.liShow{display:block;background:while;}

.liHide{display:none;background:blue;}

</style>

</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<!-- /section:settings.box -->
					<div class="page-header">
						<span class="label label-xlg label-success arrowed-right">东部管道</span>
						<!-- arrowed-in-right -->
						<span
							class="label label-xlg label-yellow arrowed-in arrowed-right"
							id="subTitle" style="margin-left: 2px;">凭证参数配置管理 </span> <span
							style="border-left: 1px solid #e2e2e2; margin: 0px 10px;">&nbsp;</span>
						<button id="btnQuery" class="btn btn-white btn-info btn-sm"
							onclick="showQueryCondi($('#jqGrid'))">
							<i class="ace-icon fa fa-chevron-down bigger-120 blue"></i> <span>隐藏查询</span>
						</button>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<div class="widget-box">
								<div class="widget-body">
									<div class="widget-main">
										<form class="form-inline">
											<span class="pull-left" style="margin-right: 5px;">
												<select class="chosen-select form-control"
													name="SelectedCustCol7" id="SelectedCustCol7"
													data-placeholder="请选择帐套" 
													style="vertical-align: top; height:32px;width: 150px;">
													<option value="">请选择帐套</option>
													<c:forEach items="${FMISACC}" var="each">
														<option value="${each.DICT_CODE}"
														    <c:if test="${pd.SelectedCustCol7==each.DICT_CODE}">selected</c:if>>${each.NAME}</option>
													</c:forEach>
												</select>
											</span>
											<span style="margin-right: 5px;"> 
												<select class="chosen-select form-control" 
												    name="SelectedTypeCode" id="SelectedTypeCode" data-placeholder="请选择业务类型"
													style="vertical-align: top; height: 32px; width: 150px;">
														<option value="">请选择业务类型</option>
														<c:forEach items="${PZTYPE}" var="each">
															<option value="${each.DICT_CODE}"
																<c:if test="${pd.SelectedTypeCode==each.DICT_CODE}">selected</c:if>>${each.NAME}</option>
														</c:forEach>
												</select>
											</span>
											<span class="input-icon" style="margin-right: 5px;">
												<input id="SelectedBusiDate" class="input-mask-date" type="text"
												   placeholder="请输入业务区间"> 
												<i class="ace-icon fa fa-calendar blue"></i>
											</span>
											<button type="button" class="btn btn-info btn-sm"
												onclick="tosearch();">
												<i class="ace-icon fa fa-search bigger-110"></i>
											</button>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12">
							<table id="jqGrid"></table>
							<div id="jqGridPager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>


	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>

	<!-- 最新版的Jqgrid Js，如果旧版本（Ace）某些方法不好用，尝试用此版本Js，替换旧版本JS -->
	<!-- <script src="static/ace/js/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="static/ace/js/grid.locale-cn.js" type="text/javascript"></script> -->

	<!-- 旧版本（Ace）Jqgrid Js -->
	<script src="static/ace/js/jqGrid/jquery.jqGrid.src.js"></script>
	<script src="static/ace/js/jqGrid/i18n/grid.locale-cn.js"></script>
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
	<!-- JqGrid统一样式统一操作 -->
	<script type="text/javascript" src="static/js/common/jqgrid_style.js"></script>

	<script type="text/javascript"> 
    var grid_selector = "#jqGrid";  
    var pager_selector = "#jqGridPager";  
    
	$(document).ready(function () { 
		$(top.hangge());//关闭加载状态
		//$('.input-mask-date').mask('999999');
		//当前期间,取自tb_system_config的SystemDateTime字段
	    var SystemDateTime = '${SystemDateTime}';
		$("#SelectedBusiDate").val(SystemDateTime);
		 
		$(window).on('resize.jqGrid', function () {
			$(grid_selector).jqGrid( 'setGridWidth', $(".page-content").width());
			//$(gridBase_selector).jqGrid( 'setGridHeight', $(window).height() - 230);
			resizeGridHeight($(grid_selector));
	    })
		
		$(grid_selector).jqGrid({
			url: '<%=basePath%>certParmConfig/getPageList.do?'
					+ 'SelectedCustCol7='+$("#SelectedCustCol7").val()
                    + '&SelectedTypeCode=' + $("#SelectedTypeCode").val()
    	            + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
			datatype: "json",
			 colModel: [
				/*{label: ' ',name:'myac',index:'', width:70, fixed:true, sortable:false, resize:false,
					formatter:'actions', 
					formatoptions:{ 
					 onEdit:function(rowid){
							 //var curRow= $("tr[id="+rowid+"]");
							 //var curCol=curRow.find("td[aria-describedby='jqGrid_STATE']");
							 //if(curCol.attr('title')=='停用'){
							 //	 var cur=$("#jSaveButton_"+rowid);
							 //	 cur.find("span").css('display','none');
							 //}
						},
                        onSuccess: function(response) {
                        	var code=JSON.parse(response.responseText);
							if(code.code==0){
								return [true];
							}else{
								$("#subTitle").tips({
									side : 3,
									msg : '保存失败,' + code.message,
									bg : '#cc0033',
									time : 3
								});
								return [false, code.message];
							}                
                        },
                        onError :function(rowid, res, stat, err) {
                        	if(err!=null)
                        		console.log(err);
                        },
                        afterSave:function(rowid, res){
                        	$(".tooltip").remove();
                        	//$(grid_selector).trigger("reloadGrid"); 
                        	
                        },
						keys:true,
					    delbutton: false,//disable delete button
					}
				},*/

				{ label: '账套',name:'BILL_OFF__', width:90,hidden : true,editable: true},
				{ label: '业务类型', name: 'TYPE_CODE__', width: 60,hidden : true,editable: true,},
			    { label: '业务期间', name: 'BUSI_DATE__', width: 60,hidden : true,editable: true},
				{ label: '单位', name: 'DEPT_CODE__', width: 60,hidden : true,editable: true,},
				{ label: '单位', name: 'DEPT_CODE', width: 60,hidden : true,editable: true,},

				{ label: '账套', name: 'BILL_OFF', width: 120,editable: true,edittype: 'select',formatter:'select',formatoptions:{value:"${billOffStrSelect}"},editoptions:{value:"${billOffStrSelect}"},stype: 'select',searchoptions:{value:"${billOffStrAll}"}},
				{ label: '业务类型', name: 'TYPE_CODE', width: 120,editable: true,edittype: 'select',formatter:'select',formatoptions:{value:"${typeCodeStrSelect}"},editoptions:{value:"${typeCodeStrSelect}"},stype: 'select',searchoptions:{value:"${typeCodeStrAll}"}},
				{ label: '业务期间', name: 'BUSI_DATE', width: 80,editable: false},
				{ label: '明细汇总字段', name: 'GROUP_COND', width: 200, editable: true,edittype:'text', editoptions:{maxLength:'200'}},
				{ label: '总汇总字段', name: 'GROUP_COND1', width: 200, editable: true,edittype:'text', editoptions:{maxLength:'200'}},
				{ label: '确认类型', name: 'CUST_PARM1', width: 250, editable: true,edittype:'text', editoptions:{maxLength:'200'}},
				//{ label: '确认类型', name: 'CUST_PARM1', width: 250, editable: true,edittype: 'select',formatter:'select',formatoptions:{value:"${custParma1StrSelect}"},editoptions:{value:"${custParma1StrSelect}", multiple:true, size:3},stype: 'select',searchoptions:{value:"${custParma1StrAll}"}},
				/*{ label: '确认类型', name: 'CUST_PARM1', width: 250, editable: true,
					edittype: 'custom',formatter:'select',
					editoptions: {custom_element: custtomElem, custom_value:customValue},
					formatoptions:{value: "1:在用; 2:空闲; 3:故障"}
				},*/
				
				{ label: '启动确认类型', name: 'CUST_PARM1_DESC', width: 100, editable: true,edittype: 'select',formatter:'select',formatoptions:{value:"${custParma1DescStrSelect}"},editoptions:{value:"${custParma1DescStrSelect}"},stype: 'select',searchoptions:{value:"${custParma1DescStrAll}"}},

				{ label: '查询条件', name: 'QUERY_COND', width: 100, editable: true,edittype:'text', editoptions:{maxLength:'200'}},
				{ label: '自定义参数2', name: 'CUST_PARM2', width: 100, editable: true,edittype:'text', editoptions:{maxLength:'200'}},
				{ label: '自定义参数2说明', name: 'CUST_PARM2_DESC', width: 100, editable: true,edittype:'text', editoptions:{maxLength:'200'}},
				{ label: '自定义参数3', name: 'CUST_PARM3', width: 100, editable: true,edittype:'text', editoptions:{maxLength:'200'}},
				{ label: '自定义参数3说明', name: 'CUST_PARM3_DESC', width: 100, editable: true,edittype:'text', editoptions:{maxLength:'200'}}
			],
			reloadAfterSubmit: true, 
			viewrecords: true,
			shrinkToFit: false,
			rowNum: 0,
			//rowList: [100,200,500],
            multiSort: true,
			sortname: 'BILL_OFF,TYPE_CODE',
			altRows: true,
			//rownumbers: true, 
            //rownumWidth: 35,		
            
			multiselect: true,
	        multiboxonly: true, 

			ondblClickRow: doubleClickRow,
			
			pager: pager_selector,
			pgbuttons: false, // 分页按钮是否显示 
			pginput: false, // 是否允许输入分页页数 
			
	        editurl: '<%=basePath%>certParmConfig/save.do?'
				+ 'SelectedCustCol7='+$("#SelectedCustCol7").val()
                + '&SelectedTypeCode=' + $("#SelectedTypeCode").val()
	            + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
	        
			loadComplete : function() {
				var table = this;
				setTimeout(function(){
					styleCheckbox(table);
					updateActionIcons(table);
					updatePagerIcons(table);
					enableTooltips(table);
				}, 0);
			},
		});
		
		$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
	
		//navButtons
		jQuery(grid_selector).jqGrid('navGrid',pager_selector,
			{ 	//navbar options
				edit: true,
				editicon : 'ace-icon fa fa-pencil blue',
				add: true,
				addicon : 'ace-icon fa fa-plus-circle purple',
				del: false,
				delicon : 'ace-icon fa fa-trash-o red', 
				search: true,
				searchicon : 'ace-icon fa fa-search orange',
				refresh: true,
				refreshicon : 'ace-icon fa fa-refresh green',
				view: false,
				viewicon : 'ace-icon fa fa-search-plus grey',
			},
	        {
				//edit record form
			    id: "edit",
				closeAfterEdit: true,
				recreateForm: true,
				beforeShowForm :beforeEditOrAddCallback,
	            afterSubmit: fn_addSubmit_extend
	        },
	        {
				//new record form
			    id: "add",
				closeAfterAdd: true,
				recreateForm: true,
				viewPagerButtons: false,
				//width: 700,
				//reloadAfterSubmit: true,
				beforeShowForm : beforeEditOrAddCallback,
			    onclickSubmit: function(params, posdata) {
					console.log("onclickSubmit");
	            } , 
	            afterSubmit: fn_addSubmit_extend
	        },
	        { 
				//delete record form
				recreateForm: true,
				beforeShowForm : beforeDeleteCallback,
				onClick : function(e) {
					
				}
	        },
	        {
				//search form
				recreateForm: true,
				afterShowSearch: beforeSearchCallback,
				afterRedraw: function(){
					style_search_filters($(this));
				},
				multipleSearch: true,
				//multipleGroup:true,
				showQuery: false
	        },
	        {},{}
		);
		
	    $(grid_selector).navSeparatorAdd(pager_selector, {
			sepclass : "ui-separator",
			sepcontent: ""
		});
        $(grid_selector).navButtonAdd(pager_selector, {
			        id : "batchEdit",
                    buttonicon: "ace-icon fa fa-pencil-square-o purple",
                    title: "批量编辑",
                    caption: "",
                    position: "last",
                    onClickButton: batchEdit,
                    cursor : "pointer"
                });
        $(grid_selector).navButtonAdd(pager_selector, {
        	        id : "batchCancelEdit",
                    buttonicon: "ace-icon fa fa-undo",
                    title: "取消批量编辑",
                    caption: "",
                    position: "last",
                    onClickButton: batchCancelEdit,
                    cursor : "pointer"
                });
        $(grid_selector).navButtonAdd(pager_selector, {
        			id : "batchSave",
                     caption : "",
                     buttonicon : "ace-icon fa fa-save green",
                     onClickButton : batchSave,
                     position : "last",
                     title : "批量保存",
                     cursor : "pointer"
                 });
        $(grid_selector).navButtonAdd(pager_selector, {
        			id : "batchDelete",
                    caption : "",
                    buttonicon : "ace-icon fa fa-trash-o red",
                    onClickButton : batchDelete,
                    position : "last",
                    title : "删除",
                    cursor : "pointer"
                });
        $(grid_selector).navSeparatorAdd(pager_selector, {
        			sepclass : "ui-separator",
        			sepcontent: ""
        		});
 	});
	
 	var nextState=1;
 	function change(obj){
 	 	var liArray=document.getElementsByTagName("LI");
 	 	var i=1;

 	 	var length=liArray.length;

 	 	switch(nextState){
 	 	    case 1:
 	 	 	    obj.innerHTML="当前选择↑";
 	 	 	    for(;i<length;i++){
 	 	 	 	    liArray[i].className="liShow";
 	 	 	    }
 	 	 	    nextState=0;
 	 	 	    break;
 	 	 	case 0:
 	 	 	    obj.innerHTML="当前选择↓";
 	 	 	    for(;i<length;i++){
 	 	 	        liArray[i].className="liHide";
 	 	 	    }
 	 	 	    nextState=1;
 	 	}
 	}
	
	function custtomElem(value, options) {   
        //1:在用; 2:空闲; 3:故障
		
		
		var el = "<span class='pull-left' style='margin-right: 5px;'> <div class='selectTree' id='selectTree' multiMode='true' allSelectable='false' noGroup='false'></div> <input id='SelectedDepartCode' type='hidden'></input> </span>";
	    
		
        /*var el = $("<select></select>");  
        el.append($("<option value='1'>在用</option>"));
        el.append($("<option value='2'>空闲</option>"));
        el.append($("<option value='3'>故障</option>"));*/
        
        
        
		/*var el = $("<ul id='myUl'></ul>");  
		el.append($("<li class='liMenu' onclick='change(this);'>当前选择↓</li>"));

		el.append($("<li value='1' class='liHide'><input type='checkbox'>在用</li>"));
		el.append($("<li value='2' class='liHide'><input type='checkbox'>空闲</li>"));
		el.append($("<li value='3' class='liHide'><input type='checkbox'>故障</li>"));*/
        
        /*if(value != null && value.length > 0) {  
              var optvalues = value.split(',');   
              if (optvalues.length > 0) {  
                  for(var i=0;i<optvalues.length;i++) {  
                      var optvalue = optvalues[i];   
                      var optdisplay = optvalues[i];   
                      var optel = $("<option value='"+optvalues[i]+"'>"+optvalues[i]+"</option>");   
                      el.append(optel);   
                  }  
              }  
        }*/
        return el;   
    }   
           
    function customValue(elem, operation, value) {   
        if (operation === 'get') {   
            return $(elem).val();   
        } else if (operation === 'set') {  
            $(elem).val(value);  
        }  
    }  
	
    //双击编辑行
    var lastSelection;
    function doubleClickRow(rowid,iRow,iCol,e){
        var grid = $(grid_selector);
        grid.restoreRow(lastSelection);
        grid.editRow(rowid, {
        	keys:true, //keys:true 这里按[enter]保存  
            restoreAfterError: false,  
        	oneditfunc: function(rowid){  
                console.log(rowid);  
            },  
            successfunc: function(response){
                console.log(response);  
		        var responseJSON = JSON.parse(response.responseText);
				if(responseJSON.code==0){
					grid.trigger("reloadGrid");  
					$(top.hangge());//关闭加载状态
					$("#subTitle").tips({
						side:3,
			            msg:'保存成功',
			            bg:'#009933',
			            time:3
			        });
					lastSelection = rowid;
					return [true,"",""];
				}//else{
		        //   grid.jqGrid('editRow',lastSelection);
				//	$(top.hangge());//关闭加载状态
				//	$("#subTitle").tips({
				//		side:3,
			    //        msg:'保存失败,'+response.responseJSON.message,
			    //        bg:'#cc0033',
			    //        time:3
			    //    });
				//}
            },  
            errorfunc: function(rowid, response){
		        var responseJSON = JSON.parse(response.responseText);
	            grid.jqGrid('editRow',lastSelection);
				$(top.hangge());//关闭加载状态
				if(response.statusText == "success"){
					if(responseJSON.code != 0){
				        grid.jqGrid('editRow',lastSelection);
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side:3,
					        msg:'保存失败:'+responseJSON.message,
					        bg:'#cc0033',
					        time:3
					    });
					}
				} else {
					$("#subTitle").tips({
						side:3,
			            msg:'保存出错:'+responseJSON.message,
			            bg:'#cc0033',
			            time:3
			        });
				}
            }  
        });
        lastSelection = rowid;
    } 
	
	 //批量编辑
	function batchEdit(e) {
		var grid = $(grid_selector);
        var ids = grid.jqGrid('getDataIDs');
        for (var i = 0; i < ids.length; i++) {
            grid.jqGrid('editRow',ids[i]);
       	}
   	}
	
   	//取消批量编辑
	function batchCancelEdit(e) {
		var grid = $(grid_selector);
        var ids = grid.jqGrid('getDataIDs');
        for (var i = 0; i < ids.length; i++) {
            grid.jqGrid('restoreRow',ids[i]);
        }
    }
	
	//批量保存
	function batchSave(e) {
		var listData =new Array();
		var ids = $(grid_selector).jqGrid('getDataIDs');
		console.log(ids);
		//遍历访问这个集合  
		var rowData;
		$(ids).each(function (index, id){  
            $(grid_selector).saveRow(id, false, 'clientArray');
             rowData = $(grid_selector).getRowData(id);
            listData.push(rowData);
		});
		top.jzts();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>certParmConfig/updateAll.do?',
				data:{DataRows : JSON.stringify(listData)},
				dataType : 'json',
				cache : false,
				success : function(response) {
					if (response.code == 0) {
						$(grid_selector).trigger("reloadGrid");
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side : 3,
							msg : '保存成功',
							bg : '#009933',
							time : 3
						});
					} else {
						$(top.hangge());//关闭加载状态
						$("#subTitle").tips({
							side : 3,
							msg : '保存失败,' + response.message,
							bg : '#cc0033',
							time : 3
						});
					}
				},
				error : function(e) {
					$(top.hangge());//关闭加载状态
				}
			});
		}

    //批量删除
    function batchDelete(){
    	//获得选中的行ids的方法
        var ids = $(grid_selector).getGridParam("selarrrow");  
 		
 		if(!(ids!=null&&ids.length>0)){
			bootbox.dialog({
				message: "<span class='bigger-110'>您没有选择任何内容!</span>",
				buttons: 			
				{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
			});
		}else{
            var msg = '确定要删除选中的数据吗??';
            bootbox.confirm(msg, function(result) {
				if(result) {
					var listData =new Array();
					
					//遍历访问这个集合  
					$(ids).each(function (index, id){  
			            var rowData = $(grid_selector).getRowData(id);
			            listData.push(rowData);
					});
					
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>certParmConfig/deleteAll.do?',
				    	data: {DataRows:JSON.stringify(listData)},
						dataType:'json',
						cache: false,
						success: function(response){
							if(response.code==0){
								$(grid_selector).trigger("reloadGrid");  
								$(top.hangge());//关闭加载状态
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
				    	error: function(response) {
							$(top.hangge());//关闭加载状态
							$("#subTitle").tips({
								side:3,
					            msg:'删除出错:'+response.responseJSON.message,
					            bg:'#cc0033',
					            time:3
					        });
				    	}
					});
				}
            });
		}
	}

    /**
     * 增加成功
     * 
     * @param response
     * @param postdata
     * @returns
     */
    function fn_addSubmit_extend(response, postdata) {
        var responseJSON = JSON.parse(response.responseText);
    	if (responseJSON.code == 0) {
    		// console.log("Add Success");
    		$("#subTitle").tips({
    			side : 3,
    			msg : '保存成功',
    			bg : '#009933',
    			time : 3
    		});
    		return [ true ];
    	} else {
    		// console.log("Add Failed"+response.responseJSON.message);
    		$("#subTitle").tips({
    			side : 3,
    			msg : '保存失败,' + responseJSON.message,
    			bg : '#cc0033',
    			time : 3
    		});
    		return [ false, responseJSON.message ];
    	}
    }
	
		//检索
		function tosearch() {
			$(grid_selector).jqGrid('setGridParam',{  // 重新加载数据
				url:'<%=basePath%>certParmConfig/getPageList.do?'
					+ 'SelectedCustCol7='+$("#SelectedCustCol7").val()
                    + '&SelectedTypeCode=' + $("#SelectedTypeCode").val()
    	            + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
    		    editurl: '<%=basePath%>certParmConfig/save.do?'
    				+ 'SelectedCustCol7='+$("#SelectedCustCol7").val()
    	            + '&SelectedTypeCode=' + $("#SelectedTypeCode").val()
    		        + '&SelectedBusiDate='+$("#SelectedBusiDate").val(),
				datatype : 'json'
							}).trigger("reloadGrid");
		}
	</script>
</body>
</html>