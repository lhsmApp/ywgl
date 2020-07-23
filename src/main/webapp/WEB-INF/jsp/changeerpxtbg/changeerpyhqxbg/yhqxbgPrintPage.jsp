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
    <td align="center">ERP系统用户权限变更申请表</td>
  </tr>
    <tr>
    <td  height="10" ></td>
  </tr>
</table>
<table width="750" height="30" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td align="center"  width="40">编号：</td><td align="center" width="120"><u>${pd.UNIT_NAME}</u></td><td align="center" width="100">-<u>${pd.DEPT_NAME}</u></td><td align="center" width="70">-ERP-AQ-04</td><td align="center" width="100">-<u>${pd.ENTRY_DATE}</u></td><td align="center" width="60">-<u>${pd.SERIAL_NUM}</u></td>
 </tr>
   <tr>
     <td align="center" width="40"> </td><td align="center" width="120">单位简称</td><td align="center" width="100">部门简称</td><td align="center" width="70">表单号</td><td align="center" width="100">填表日期</td><td align="center" width="60">序号</td>
  </tr>
      <tr>
    <td  height="10" ></td>
  </tr>
</table>
<table width="750"  align="center"  >  <tr bgcolor="#FFFFFF">
    <td height="30" align="left">申请情况（关键用户及相关审批人填写）</td>
  </tr></table>
<table width="750"    border="1" align="center" cellspacing="0" bgcolor="#221144">
  <tr>
        <td height="40" align="center" width="100" bgcolor="EFEFFF">申请人及</br>帐号名</td>
        <td align="center" width="200" bgcolor="#FFFFFF">${pd.USERNAME}</td>
        <td align="center" width="100" bgcolor="EFEFFF">申请人部门</br>/岗位<sup>1</sup></td>
        <td align="center" width="200" bgcolor="#FFFFFF">${pd.USER_DEPTNAME}</br>${pd.USER_JOB}</td>
  </tr>
  <tr>
        <td height="40" align="center" bgcolor="EFEFFF">联系方式</br>(Email,电话)</td>
        <td align="center" bgcolor="#FFFFFF">${pd.CONTACT}</td>
        <td align="center" bgcolor="EFEFFF">申请生效日期<sup>2</sup></td>
        <td align="center" bgcolor="#FFFFFF">${pd.EFFECTIVE_DATE}</td>
  </tr>	
    <tr>
        <td height="40" align="center" bgcolor="EFEFFF">SAP系统名称</td>
        <td  colspan="3" align="left" bgcolor="#FFFFFF">&nbsp;CP6勘探与生产ERP2.0系统</td>
     
  </tr>	
  <tr>
   		<td height="60" align="center"  bgcolor="EFEFFF">权限变更</br>申请原因</br>及内容 <sup>3</sup></td>
        <td height="60" colspan="3" align="left"  bgcolor="#FFFFFF">&nbsp;${pd.BG_REASON}</td>
  </tr>	
     <tr>
   		<td height="40"  align="center"  bgcolor="EFEFFF">申请类别</td>
       <td align="left" colspan="3" bgcolor="#FFFFFF">
		 <table><tr><td align="left" ><div class="one"></div></td><td align="left" >新增角色</td><td align="left" >  <div class="one"></div></td><td align="left" >删除角色</td></tr></table>       
		 </td>
  </tr>
   <tr>
   		<td height="80"  align="center"  bgcolor="EFEFFF">新增角色<sup>4</sup></td>
        <td height="80" colspan="3" align="left"  bgcolor="#FFFFFF">&nbsp;${pd.ADD_ROLE}</td>
  </tr>	
     <tr>
   		<td height="80"  align="center"  bgcolor="EFEFFF">删除角色</td>
        <td height="80" colspan="3" align="left"  bgcolor="#FFFFFF">&nbsp;${pd.DEL_ROLE}</td>
  </tr>	
    <tr>
        <td height="200" align="center" bgcolor="EFEFFF">是否符合敏感</br>事务权限设置</br>要求</td>
        <td align="left" bgcolor="#FFFFFF">
			<table>
			<tr><td colspan="4" >&nbsp;经核对《中国石油SAP系统敏</td></tr>
			<tr><td colspan="4" >&nbsp;感事务访问权限设置规则》，</td></tr>
			<tr><td colspan="4" >&nbsp;本次角色变更中涉及的敏感事</td></tr>
			<tr><td colspan="4" >&nbsp;务代码的权限分配：</td></tr>
			<tr><td><div class="one"></div></td><td width="75"> 符合</td><td><div class="one"></div></td><td width="75">不符合</td></tr>
			<tr><td colspan="4" >&nbsp;ERP系统中的敏感事务权限设</td></tr>
			<tr><td colspan="4" >置要求</td></tr></table>                           
		</td>
      <td height="200" align="center" bgcolor="EFEFFF">是否符合职责</br>分离要求</td>
        <td align="left" bgcolor="#FFFFFF">
			<table>
			<tr><td colspan="4" >&nbsp;经核对《中国石油SAP</td></tr>
			<tr><td colspan="4" >&nbsp;系统职责分离矩阵》，本</td></tr>
			<tr><td colspan="4" >&nbsp;次角色变更中涉及的权</td></tr>
			<tr><td colspan="4" >&nbsp;限分配：</td></tr>
			<tr><td><div class="one"></div></td><td width="75"> 符合</td><td><div class="one"></div></td><td width="75">不符合</td></tr>
			<tr><td colspan="4" >&nbsp;ERP系统中的职责分离</td></tr>
			<tr><td colspan="4" >要求</td></tr></table>                           
		</td>
  </tr>	
  <tr>
        <td height="40"  align="center" bgcolor="EFEFFF">支持人员</br>（关键用户）</td>
		<td  colspan="3"  height="70"  align="right" bgcolor="#FFFFFF">
			<table><tr><td width="150">签字： </td><td width="75"> 年</td><td width="75">月</td><td width="75">日</td></tr></table>          </td>
  </tr>
   <tr>
        <td height="40"  align="center" bgcolor="EFEFFF">本单位业务部门</br>主管领导意见</td>
		<td  colspan="3"  height="70"  align="right" bgcolor="#FFFFFF">
			<table><tr><td width="150">签字： </td><td width="75"> 年</td><td width="75">月</td><td width="75">日</td></tr></table>          </td>
  </tr>
 

</table>
<table width="750" height="10" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
    <td  height="10" ></td>
  </tr>
</table>
<table width="750"    border="1" align="center" cellspacing="0" bgcolor="#221144">
  <tr bgcolor="#FFFFFF">
    <td colspan="4"  height="30" align="left">实施情况（地区分公司SAP用户管理员填写）</td>
  </tr>
  <tr>
   
    <tr>
        <td height="40" align="center" width="100" bgcolor="EFEFFF">实施人</td>
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

<hr/>
<div>
	<div>1如申请人为非中国石油的外部人员，应明确填写单位信息。</div>
 	<div>2填写该变更应在SAP系统中生效的日期，例如填写申请人岗位变动的日期。</div>
 	<div>3关键用户根据申请人权限变更内容新增/删除申请人的角色。</div>
  	<div>4关键用户应根据《XX地区分公司岗位与SAP系统中角色的对应规则》为用户分配恰当的、符合工作职责的角色。</div>

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