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
<%@ include file="../../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!-- 树形下拉框start -->
<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
<link rel="stylesheet" type="text/css"
    href="plugins/selectZtree/import_fh.css" />
<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet"
    href="plugins/selectZtree/ztree/ztree.css"></link>
<!-- 树形下拉框end -->
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
    <div class="main-container" id="main-container">
        <!-- /section:basics/sidebar -->
        <div class="main-content">
            <div class="main-content-inner">
                <div class="page-content">
                    <div id="zhongxin1" class="row">
                        <div class="col-xs-12">
                        <!-- 检索  -->
                        <form action="trainplan/listStudent.do" method="post" name="Form" id="Form">    
                        <input type="hidden" name="TRAIN_PLAN_PERSONS" id="TRAIN_PLAN_PERSONS" value="${pd.TRAIN_PLAN_PERSONS}"/>   
                        <table style="margin-top:5px;">
                            <tr>
                                <td>
                                    <div style="margin:10px 0px;">
                                        <input type="hidden" name="DEPART_CODE" id="DEPART_CODE"  />
                                        <div class="selectTree" id="selectTree" style="float:none;display:block;">
                                        <div style="border: 1px solid #6fb3e0;border-radius: 4px !important;">
                                        <span class="input-icon">
                                            <input style="border: 1px solid #6fb3e0;border-radius: 4px !important;" type="text" placeholder="搜索角色" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="">
                                            <i class="ace-icon fa fa-search nav-search-icon"></i>
                                        </span>
                                    </div>
                                        </div>
                                    </div>      
                                </td>                               
                                <td style=padding-left:2px"><a class="btn btn-light btn-xs" style="width: 35px;height: 34px; margin-left: 5px;" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue" style="margin-top: 4px;"></i></a></td>
                            </tr>
                        </table>    
                    </form>         
                        <table id="student-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">   
                            <thead>
                                <tr>
                                    <th class="center" style="width:35px;">
                                    <label class="pos-rel">
                                    <input type="checkbox" class="ace" id="zcheckbox"  name="zcheckbox" /><span class="lbl"></span></label>
                                    </th>
                                    <th class="center" style="width:50px;">序号</th>
                                    <th class="center">角色</th>
                                </tr>
                            </thead>
                                                    
                            <tbody id="tobodyUser">
                            <!-- 开始循环 -->   
                            <c:choose>
                                <c:when test="${not empty varList}">
                                    
                                    <c:forEach items="${varList}" var="var" varStatus="vs">
                                        <tr>
                                            <td class='center'>
                                                <label class="pos-rel"><input type='checkbox' name='ids' value="${var.ROLE_ID}" class="ace" /><span class="lbl"></span></label>
                                            </td>
                                            <td class='center' style="width: 30px;">${vs.index+1}</td>                                          
                                            <td class='center' name='ids2'>${var.ROLE_NAME}</td>
                                        </tr>
                                    
                                    </c:forEach>
                                    
                                </c:when>
                                <c:otherwise>
                                    <tr class="main_info">
                                        <td colspan="100" class="center" >没有相关数据</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                            </tbody>
                        </table>
                        <div class="page-header position-relative">
                        <table style="width:100%;">
                            <tr>
                                <td style="vertical-align:top;">
                                	<input id="clickConfirm" name="clickConfirm" value="0" type=""hidden"">
                                    <a class="btn btn-mini btn-success" onclick="$('#clickConfirm').val('1');top.Dialog.close();">确定</a>
                                </td>
                                <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                            </tr>
                        </table>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 返回顶部 -->
        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>

    </div>
    <!-- 页面底部js¨ -->
    <%@ include file="../../../system/index/foot.jsp"%>
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
    <script type="text/javascript">
        $(top.hangge());//关闭加载状态
        //检索
        function tosearch(){
            top.jzts();
            $("#Form").submit();
        }
        $(function(){
          //日期框
            $('.date-picker').datepicker({
                autoclose: true,
                todayHighlight: true
            });
          //复选框全选控制
            var active_class = 'active';
            $('#student-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
                var th_checked = this.checked;//checkbox inside "TH" table header
                $(this).closest('table').find('tbody > tr').each(function(){
                    var row = this;
                    if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
                    else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
                });
            });
        })
    </script>
</body>
</html>