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
    <td align="center">权限管理系统用户帐号新增申请表</td>
  </tr>
    <tr>
    <td  height="10" ></td>
  </tr>
</table>
<table width="750" height="30" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td align="center"  width="40">编号：</td><td align="center" width="120"><u>${pd.UNIT_NAME}</u></td><td align="center" width="100">-<u>${pd.DEPT_NAME}</u></td><td align="center" width="70">-GRC-AQ-01</td><td align="center" width="100">-<u>${pd.ENTRY_DATE}</u></td><td align="center" width="60">-<u>${pd.SERIAL_NUM}</u></td>
 </tr>
   <tr>
     <td align="center" width="40"> </td><td align="center" width="120">单位简称</td><td align="center" width="100">部门简称</td><td align="center" width="70">表单号</td><td align="center" width="100">填表日期</td><td align="center" width="60">序号</td>
  </tr>
      <tr>
    <td  height="10" ></td>
  </tr>
</table>
<table width="750"  align="center"  >  <tr bgcolor="#FFFFFF">
    <td height="30" align="center">申请情况（三级运维及相关审批人填写）</td>
  </tr></table>
<table width="750"    border="1" align="center" cellspacing="0" bgcolor="#221144">
  <tr>
        <td height="40" align="center" width="100" bgcolor="EFEFFF">申请人</td>
        <td align="center" width="200" bgcolor="#FFFFFF">${pd.USERNAME}</td>
        <td align="center" width="100" bgcolor="EFEFFF">申请人部门</br>/岗位</td>
        <td align="center" width="200" bgcolor="#FFFFFF">${pd.USER_DEPTNAME}</br>${pd.USER_JOB}</td>
  </tr>
  <tr>
        <td height="40" align="center" bgcolor="EFEFFF">联系方式</br>(Email,电话)</td>
        <td align="center" bgcolor="#FFFFFF">${pd.USER_EMAIL},${pd.USER_TEL}</td>
        <td align="center" bgcolor="EFEFFF">申请生效日期</td>
        <td align="center" bgcolor="#FFFFFF">${pd.EFFECTIVE_DATE}</td>
  </tr>	
    <tr>
        <td height="40" align="center" bgcolor="EFEFFF">系统名称</td>
        <td  colspan="3" align="left" bgcolor="#FFFFFF"> 
        <table>			
			<tr><td><div class="one"></div></td><td> 权限管理系统</td></tr></table>
</td>
     
  </tr>	
  <tr>
   		<td height="60" align="center" rowspan="2" bgcolor="EFEFFF">帐号新增</td>
   		<td height="60" align="left"  bgcolor="#FFFFFF">&nbsp;帐号： ${pd.ACCOUNT_NEW}</td>
        <td height="60" colspan="2" align="left"  bgcolor="EFEFFF">&nbsp;帐号有效期： ${pd.ACCOUNT_VALIDITY}</td>
  </tr>	
   <tr>
   		<td height="60"colspan="3"   align="left"  bgcolor="#FFFFFF">&nbsp;新增帐号原因：${pd.ACCOUNT_REASON}</td>
  </tr>	
  <tr>
        <td height="40"  align="center" bgcolor="EFEFFF">帐号角色</td>
		<td  colspan="3"  height="70"  align="left" bgcolor="#FFFFFF"> ${pd.ACCOUNT_ROLES}</td>
  </tr>
  <tr>
        <td height="40"  align="center" bgcolor="EFEFFF">支持人员</br>（三级运维）</td>
		<td  colspan="3"  height="70"  align="right" bgcolor="#FFFFFF">
			<table><tr><td width="150">签字： </td><td width="75"> 年</td><td width="75">月</td><td width="75">日</td></tr></table>          </td>
  </tr>
   <tr>
        <td height="40"  align="center" bgcolor="EFEFFF">申请人主管领导</br>审批意见</td>
		<td  colspan="3"  height="70"  align="right" bgcolor="#FFFFFF">
			<table><tr><td width="150">签字： </td><td width="75"> 年</td><td width="75">月</td><td width="75">日</td></tr></table>          </td>
  </tr>

</table>
<table width="750" height="10" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
    <td  height="10" ></td>
  </tr>
</table>
<div>&nbsp;&nbsp;&nbsp;&nbsp;实施情况</div>
<table width="750"    border="1" align="center" cellspacing="0" bgcolor="#221144">

  <tr>
     <tr>
        <td height="50" align="center" width="100" bgcolor="EFEFFF">分配的</br>用户组</td>
        <td align="center" width="200" bgcolor="#FFFFFF"> </td>
        <td align="center" width="100" bgcolor="EFEFFF">备注</td>
        <td align="center" width="200" bgcolor="#FFFFFF"></td>
  </tr>
    <tr>
        <td height="50" align="center" width="100" bgcolor="EFEFFF">实施人</td>
        <td align="center" width="200" bgcolor="#FFFFFF"> </td>
        <td align="center" width="100" bgcolor="EFEFFF">实施日期</td>
        <td align="center" width="200" bgcolor="#FFFFFF"><table><tr><td width="150">签字： </td><td width="75"> 年</td><td width="75">月</td><td width="75">日</td></tr></table>  </td>
  </tr>


</table>
<table width="750" height="30" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
       <td align="right"><button id="printf" onClick="printf()">打印</button>&nbsp;</td>
  </tr>
</table>


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