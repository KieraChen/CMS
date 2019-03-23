<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty sessionScope.user}">
<c:redirect url="Index.jsp"></c:redirect>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台页面头部</title>
<link href="css/css.css" type="text/css" rel="stylesheet" />
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="header">
  <tr>
    
    <td width="1096" align="left" valign="bottom">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="27%" align="left" id="header-name" style="font-size:36px">后台管理系统</td>
        <td width="73%" align="right"  id="header-right" style="font-size:15px">
        	<a href="UserController?flag=exit" target="_top" onFocus="this.blur()" class="admin-out" style="font-size:15px">安全退出</a>
            <a href="First.jsp" onFocus="this.blur()" class="admin-home" style="font-size:15px" target="mainFrame">管理首页</a>
        	<a href="MainController?nFlag=first" target="_blank" onFocus="this.blur()" class="admin-index" style="font-size:15px">网站首页</a>       	
            <span style="font-size:15px">
<!-- 日历 -->
<SCRIPT type=text/javascript src="js/clock.js"></SCRIPT>
<SCRIPT type=text/javascript>showcal();</SCRIPT>
            </span>
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td align="left" valign="bottom">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="top" id="header-admin">&nbsp;</td>
        <td align="left" valign="bottom" id="header-menu">
        
        </td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>