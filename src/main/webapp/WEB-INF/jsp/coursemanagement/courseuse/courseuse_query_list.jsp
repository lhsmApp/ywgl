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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!-- 树形下拉框start -->
<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
<link rel="stylesheet" type="text/css" href="plugins/selectZtree/import_fh.css" />
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
                    <form action="courseuse/queryList.do" method="post"
                        name="Form" id="Form">
                        <div class="page-header">
                            <!-- 检索  -->
                            <label class="pull-left" style="padding: 5px;">筛选条件：</label>
                            <span class="input-icon nav-search"
                                style="margin-left: 14px;">
                                <i class="ace-icon fa fa-search nav-search-icon"></i>
                                <input class="nav-search-input"
                                    autocomplete="off" id="nav-search-input"
                                    type="text" name="keywords"
                                    value="${pd.keywords}" placeholder="请输入观看人" />
                            </span>
                            <span style="margin-left: -15px;"> </span>
                            <input type="hidden" name="COURSE_TYPE"
                                id="COURSE_TYPE" value="${pd.COURSE_TYPE}" />
                            <input type="hidden" name="COURSE_NAME"
                                id="COURSE_NAME" value="${pd.COURSE_NAME}" />
                            <div class="inline">
                                <div class="selectTree" id="selectTree"></div>
                            </div>
                            <button style="margin-bottom: 3px;"
                                class="btn btn-info btn-sm" onclick="search();"
                                title="检索">
                                <i class="ace-icon fa fa-search bigger-110"></i>
                            </button>
                            <a class="btn btn-primary btn-xs inline pull-right"
                                onclick="toExcel()">
                                <i
                                    class="ace-icon fa fa-chevron-down bigger-110"></i>
                                <span>导出</span>
                            </a>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">


                                <table id="simple-table"
                                    class="table table-striped table-bordered table-hover"
                                    style="margin-top: 5px;">
                                    <thead>
                                        <tr>
                                            <th class="center"
                                                style="width: 50px;">序号</th>
                                            <th class="center">课程名称</th>
                                            <th class="center">课程分类</th>
                                            <th class="center">小节名称</th>
                                            <th class="center">观看时长</th>
                                            <th class="center">观看人</th>
                                            <th class="center">观看时间</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <!-- 开始循环 -->
                                        <c:choose>
                                            <c:when test="${not empty varList}">
                                                <c:forEach items="${varList}"
                                                    var="var" varStatus="vs">
                                                    <tr>
                                                        <td class='center'
                                                            style="width: 30px;">${vs.index+1}</td>
                                                        <td class='center'>${var.COURSE_NAME}</td>
                                                        <td class='center'>${var.COURSE_TYPE_NAME}</td>
                                                        <td class='center'>${var.CHAPTER_NAME}</td>
                                                        <td class='center'>${var.PLAY_TIME}</td>
                                                        <td class='center'>${var.STUDENT_NAME}</td>
                                                        <td class='center'>${var.RPT_TIME}</td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr class="main_info">
                                                    <td colspan="100"
                                                        class="center">没有相关数据</td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>
                                <div class="page-header position-relative">
                                    <table style="width: 100%;">
                                        <tr>
                                            <td style="vertical-align: top;">
                                                <div class="pagination"
                                                    style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <!-- /.col -->
                        </div>
                    </form>
                    <!-- /.row -->
                </div>
                <!-- /.page-content -->
            </div>
        </div>
        <!-- /.main-content -->

        <!-- 返回顶部 -->
        <a href="#" id="btn-scroll-up"
            class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>

    </div>
    <!-- /.main-container -->

    <!-- basic scripts -->
    <!-- 页面底部js¨ -->
    <%@ include file="../../system/index/foot.jsp"%>
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
    
    /*树形下拉框*/
    var defaultNodes = {"treeNodes":eval('${zTreeNodes}')};
    function initComplete(){
        //绑定change事件
        $("#selectTree").bind("change",function(){
            if($(this).attr("relValue")){
                    $("#COURSE_TYPE").val($(this).attr("relValue"));
                    $("#COURSE_NAME").val($(this).attr("reltext"));
                }
        });
        //赋给data属性
        $("#selectTree").data("data",defaultNodes);  
        $("#selectTree").render();
        $("#selectTree2_input").val('${pd.COURSE_NAME}');
    }
    
    /* 导出Excel */
    function toExcel(){
        window.location.href="<%=basePath%>courseuse/excel.do";
    }
    </script>
</body>
</html>