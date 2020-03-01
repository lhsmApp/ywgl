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
    <td align="center">ERP系统变更申请表</td>
  </tr>
    <tr>
    <td  height="10" ></td>
  </tr>
</table>
<table width="750" height="30" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td align="center"  width="40">编号：</td><td align="center" width="120"><u>${pd.UNIT_NAME}</u></td><td align="center" width="100">-<u>${pd.DEPT_NAME}</u></td><td align="center" width="70">-ERP-BG-01</td><td align="center" width="100">-<u>${pd.ENTRY_DATE}</u></td><td align="center" width="60">-<u>${pd.SERIAL_NUM}</u></td>
 </tr>
   <tr>
     <td align="center" width="40"> </td><td align="center" width="120">单位简称</td><td align="center" width="100">部门简称</td><td align="center" width="70">表单号</td><td align="center" width="100">填表日期</td><td align="center" width="60">序号</td>
  </tr>
      <tr>
    <td  height="10" ></td>
  </tr>
</table>
<table width="750"    border="1" align="center" cellspacing="0" bgcolor="#221144">
  <tr bgcolor="#FFFFFF">
    <td colspan="4"  height="30" align="center">变更申请（申请人及相关审批人填写）</td>
  </tr>
  <tr>
        <td height="40" align="center" width="100" bgcolor="EFEFFF">申请人</td>
        <td align="center" width="200" bgcolor="#FFFFFF">${pd.USERNAME}</td>
        <td align="center" width="100" bgcolor="EFEFFF">申请人部门</br>/岗位</td>
        <td align="center" width="200" bgcolor="#FFFFFF">${pd.USER_DEPTNAME}</br>${pd.USER_JOB}</td>
  </tr>
  <tr>
        <td height="40" align="center" bgcolor="EFEFFF">变更名称</td>
        <td align="center" bgcolor="#FFFFFF">${pd.BG_NAME}</td>
        <td align="center" bgcolor="EFEFFF">联系方式</br>(Email,电话)</td>
        <td align="center" bgcolor="#FFFFFF">${pd.USER_EMAIL},${pd.USER_TEL}</td>
  </tr>	
    <tr>
        <td height="40" align="center" bgcolor="EFEFFF">ERP系统名称</td>
        <td align="center" bgcolor="#FFFFFF">CP6勘探与生产ERP2.0系统</td>
        <td align="center" bgcolor="EFEFFF">变更类型</td>
        <td align="center" bgcolor="#FFFFFF" >
        <table><tr><td><div class="one"></div></td><td>日常变更</td><td>  <div class="one"></div></td><td>紧急变更</td></tr></table>       
        </td>
  </tr>	
  <tr>
          <td height="200" colspan="4" align="left"  bgcolor="#FFFFFF">
          <table width="750"  height="200">
          <tr><td align="left" colspan="3">变更原因：</td></tr>
          <tr><td align="left" colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;${pd.BG_REASON}</td></tr>
          <tr><td align="left" colspan="3"></br>变更内容：</br></br></td></tr>
          <tr><td align="left" colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;${pd.BG_NR}</td></tr>
          <tr><td align="left"></br>变更预期时间：</br></br></td><td align="left">&nbsp;&nbsp;&nbsp;&nbsp;${pd.EFFECTIVE_DATE}</td><td align="right">（本栏可为附件）</td></tr>          
          </table>
<%--           变更原因：</br></br>&nbsp;&nbsp;&nbsp;&nbsp;${pd.BG_REASON}</br>变更内容：</br></br>&nbsp;&nbsp;&nbsp;&nbsp;${pd.BG_NR}</br>变更预期时间：</br></br>&nbsp;&nbsp;&nbsp;&nbsp;${pd.EFFECTIVE_DATE} --%>
          </td>
  </tr>	
  <tr>
        <td  colspan="2"  height="60"  align="left" bgcolor="#FFFFFF">
          <table>
          <tr  colspan="5" ><td>  单位主管领导审批</td></tr>
          <tr><td>变更申请</td><td align="right" ><div class="one"></div></td><td>符合</td><td>  <div class="one"></div></td><td>不符合业务需求</td></tr></table>       
        </td>
		<td  colspan="2"  height="60"  align="left" bgcolor="#FFFFFF">
		<table><tr><td width="150">签字： </td><td width="75"> 年</td><td width="75">月</td><td width="75">日</td></tr></table>                           
		
		</td>
  </tr>
    <tr>
         <td  colspan="2"  height="60"  align="left" bgcolor="#FFFFFF">油田公司业务主管部门审批意见</td>
		<td  colspan="2"  height="60"  align="left" bgcolor="#FFFFFF">
				<table><tr><td width="150">签字： </td><td width="75"> 年</td><td width="75">月</td><td width="75">日</td></tr></table>          
				</td>  
  </tr>	
  <tr>
         <td  colspan="2"  height="60"  align="left" bgcolor="#FFFFFF">油田公司内控审批意见</td>
		<td  colspan="2"  height="60"  align="left" bgcolor="#FFFFFF">		
		<table><tr><td width="150">签字： </td><td width="75"> 年</td><td width="75">月</td><td width="75">日</td></tr></table>         
		 </td>  
  </tr>	
    <tr>
         <td  colspan="2"  height="60"  align="left" bgcolor="#FFFFFF">系统负责人审批意见</td>
		<td  colspan="2"  height="60"  align="left" bgcolor="#FFFFFF">
				<table><tr><td width="150">签字： </td><td width="75"> 年</td><td width="75">月</td><td width="75">日</td></tr></table>          
</td>  
  </tr>	
</table>
<table width="750" height="10" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
    <td  height="10" ></td>
  </tr>
</table>
<table width="750"    border="1" align="center" cellspacing="0" bgcolor="#221144">
  <tr bgcolor="#FFFFFF">
    <td colspan="4"  height="30" align="center">变更受理（总部ERP运维主管填写）</td>
  </tr>
  <tr>
        <td height="60" align="center" width="80" bgcolor="EFEFFF">是否可实施</br>该变更</td>
        <td align="center" width="150" bgcolor="#FFFFFF">
		 <table><tr><td align="left" ><div class="one"></div></td><td align="left" >是</td><td align="left" >  <div class="one"></div></td><td align="left" >否</td></tr></table>       </td>
        <td  rowspan="2" width="80" align="center"  bgcolor="EFEFFF">对该变更的</br>总体意见 <sup>1</sup></td>
        <td  rowspan="2" valign="bottom" width="150" bgcolor="EFEFFF">
        <table><tr><td width="100">签字： </td><td width="40"> 年</td><td width="40">月</td><td width="40">日</td></tr></table>           </td>
  </tr>
  <tr>
        <td height="70" align="center" bgcolor="EFEFFF">变更功能测试</td>
        <td align="center"  bgcolor="#FFFFFF">
 <table><tr><td><div class="one"></div></td><td>需要测试</td><td>  <div class="one"></div></td><td>不需要测试</td></tr></table>       </td>

  </tr>	
</table>

<hr/>
<div>1对变更是否涉及跨部门业务、是否涉及数据迁移以及对现有业务流程的影响等问题发表意见，根据这些意见判断变更是否需要进行功能测试以及用户接受性测试。</div>

</div>
<table width="750" height="20" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
 	<td align="right"><button id="printf" onClick="printf()">打印</button>&nbsp;</td>
  </tr>
</table>		
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