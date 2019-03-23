<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <%
    Cookie[] cookies = request.getCookies();
	String name = "";
	String pass = "";
	if(cookies != null){
	  for(Cookie c : cookies){
		if("c_name".equals(c.getName())){
			name = URLDecoder.decode(c.getValue(), "UTF-8");
		}
		if("c_pass".equals(c.getName())){
			pass = URLDecoder.decode(c.getValue(), "UTF-8");
		}
		
	  }
	}
    %>

<html>
<head>
<title>登录页面</title>
<link href="css/style.css" rel='stylesheet' type='text/css' />
</head>
<body>
<h1>登录</h1>
<div class="login-form">
		<div class="head-info">
		<%if(request.getAttribute("msg")!= null){ %>
			<%=request.getAttribute("msg") %>
		<%} %>
			<label class="lbl-1"> </label>
			<label class="lbl-2"> </label>
			<label class="lbl-3"> </label>
		</div>
	<div class="avtar">
		<img src="images/avtar.png" />
	</div>
			<form action='UserController' method='post'> 
					<input type="text" class="text" name='username' value="<%=name%>" />
						<div class="key"> 
					    <input type="password" name='userpass' value="<%=pass%>" />
						</div>
                        <div style='margin-bottom:20px'>
                    <input type='checkbox' name='save' value='1'/>
                    <span style='font-weight:bold; color:#CCC;'>信息保存一周</span>
                    <input type='hidden' name='flag' value='check'/>
                        </div>
			
	<div class="signin">
		<input type="submit" value="Login" >
	</div>
	        </form>
</div>

</body>
</html>