<%@page import="mvc.domain.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%User u = (User)session.getAttribute("user");
  		if(u==null){
  			response.sendRedirect("Login.jsp");
  			return;
  		} %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="shortcut icon" href="images/favicon.ico" />
<link href="css/css.css" type="text/css" rel="stylesheet" />
</head>
<!--框架样式-->
<frameset rows="65,*" cols="*" frameborder="no" border="0" framespacing="0">
<!--top样式-->
	<frame src="Top.jsp" name="topframe" scrolling="no" noresize id="topframe" title="topframe" />
<!--contact样式-->
	<frameset id="attachucp" framespacing="0" border="0" frameborder="no" cols="194,*" rows="*">
		<frame scrolling="auto" noresize="" frameborder="no" name="leftFrame" src="Left.jsp"></frame>
		<frame scrolling="auto" noresize="" border="0" name="mainFrame" src="First.jsp"></frame>
	</frameset>

</frameset><noframes></noframes>
<noframes></noframes>
</html>
