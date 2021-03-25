<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<style>
		.one{width:15px; height:15px; background-color:transparent;border:1px solid}
	</style>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		
		<script language="javascript" src="static/js/jquery.jqprint-0.3.js"></script>
	
	</head>
<body   bgcolor="#FFFFFF">
<div id="zhongxin">
<table width="750" height="30" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
  <tr  style ="font-size:50 px" >
    <td align="center">ERP帐号删除申请</td>
  </tr>
    <tr>
    <td  height="10" ></td>
  </tr>
</table>

<table width="750"    border="1" align="center" cellspacing="0" bgcolor="#221144">
  <tr>
        <td height="40" align="center" width="100" bgcolor="EFEFFF">申请人</td>
        <td align="center" width="200" bgcolor="#FFFFFF">${pd.BILL_USER}</td>
        <td align="center" width="100" bgcolor="EFEFFF">申请人所属单位</td>
        <td align="center" width="200" bgcolor="#FFFFFF">${pd.USER_DEPART}</td>
  </tr>
  <tr>
        <td height="40" align="center" bgcolor="EFEFFF">联系方式</br>(Email,电话)</td>
        <td align="center" bgcolor="#FFFFFF">${pd.CONTACT}</td>
        <td align="center" bgcolor="EFEFFF">申请日期</td>
        <td align="center" bgcolor="#FFFFFF">${pd.APPLY_DATE}</td>
  </tr>	
    <tr>
        <td height="40" align="center" bgcolor="EFEFFF">系统名称</td>
        <td  colspan="3" align="left" bgcolor="#FFFFFF"> 
        <table>			
			<tr><td><div class="one"></div></td><td> 权限管理系统</td></tr></table>
</td>
     
  </tr>	
  <tr>
   		<td height="60" align="center" rowspan="2" bgcolor="EFEFFF">帐号删除</td>
        <td height="60" colspan="3" align="left"  bgcolor="EFEFFF">&nbsp;帐号名： ${pd.STAFF_CODE}</td>
  </tr>	
   <tr>
   		<td height="60"colspan="3"   align="left"  bgcolor="#FFFFFF">&nbsp帐号删除原因：${pd.ACCOUNT_DELETE_REASON}</td>
  </tr>	


</table>
<table width="750" height="10" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
    <td  height="10" ></td>
  </tr>
</table>
<!-- <table width="750" height="30" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"> -->
<!--   <tr> -->
<!--        <td align="right"><button id="printf" onClick="printf()">打印</button>&nbsp;</td> -->
<!--   </tr> -->
<!-- </table> -->


<div>


</div>

</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript">
		$(top.hangge());
		
		function printf(){
			$("#printf").css('display','none');//隐藏
			window.print();
			$("#printf").css('display','block');//显示
		}
		</script>
</body>
</html>