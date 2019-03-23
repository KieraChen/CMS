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
<title>添加用户</title>
<script language="javascript">
   window.onload = function(){
	  var userName =  document.getElementById("userName");
	  var msg = document.getElementById("msg");
	  var sb = document.getElementById("sb");
	  userName.onblur = function(){
		  var timer = new Date().getTime();
		  //1.创建XMLHttpRequest对象
		  var req = xmlHttpRequest();
		  //2.处理响应结果 设置回调函数
		  req.onreadystatechange = function(){
			  if(req.status == 200 && req.readyState == 4){
				  var data = req.responseText;
				  if(data == "exist"){
					  msg.innerHTML = "<font color='red'>用户名已被占用</font>";
					  sb.disabled = true;
				  }else{
					  msg.innerHTML = "<font color='blue'>用户名可以使用</font>";
					  sb.disabled = false;
				  }
			  }
		  };
		  //3.建立连接
req.open("get","${pageContext.request.contextPath}/UserController?flag=CheckName&userName="+userName.value+"&timer="+timer);
		  //4.发送请求
		  req.send(null);
	};
	   
};
</script>

<script language='javascript' src='js/tool.js'></script>
<link href="css/css.css" type="text/css" rel="stylesheet" />
<link href="css/main.css" type="text/css" rel="stylesheet" />
<link rel="shortcut icon" href="images/main/favicon.ico" />
<style>
body{overflow-x:hidden; background:#f2f0f5; padding:15px 0px 10px 5px;}
#searchmain{ font-size:16px;}
#search{ font-size:16px; background:#548fc9; margin:10px 10px 0 0; display:inline; width:100%; color:#FFF}
#search form span{height:40px; line-height:40px; padding:0 0px 0 10px; float:left;}
#search form input.text-word{height:24px; line-height:24px; width:180px; margin:8px 0 6px 0; padding:0 0px 0 10px; float:left; border:1px solid #FFF;}
#search form input.text-but{height:24px; line-height:24px; width:55px; background:url(images/main/list_input.jpg) no-repeat left top; border:none; cursor:pointer; font-family:"Microsoft YaHei","Tahoma","Arial",'宋体'; color:#666; float:left; margin:8px 0 0 6px; display:inline;}
#search a.add{ background:url(images/main/add.jpg) no-repeat 0px 6px; padding:0 10px 0 26px; height:40px; line-height:40px; font-size:14px; font-weight:bold; color:#FFF}
#search a:hover.add{ text-decoration:underline; color:#d2e9ff;}
#main-tab{ border:1px solid #eaeaea; background:#FFF; font-size:16px;}
#main-tab th{ font-size:16px; background:url(images/main/list_bg.jpg) repeat-x; height:32px; line-height:32px;}
#main-tab td{ font-size:16px; line-height:40px;}
#main-tab td a{ font-size:16px; color:#548fc9;}
#main-tab td a:hover{color:#565656; text-decoration:underline;}
.bordertop{ border-top:1px solid #ebebeb}
.borderright{ border-right:1px solid #ebebeb}
.borderbottom{ border-bottom:1px solid #ebebeb}
.borderleft{ border-left:1px solid #ebebeb}
.gray{ color:#dbdbdb;}
td.fenye{ padding:10px 0 0 0; text-align:right;}
.bggray{ background:#f9f9f9; font-size:14px; font-weight:bold; padding:10px 10px 10px 0; width:120px;}
.main-for{ padding:10px;}
.main-for input.text-word{ width:310px; height:36px; line-height:36px; border:#ebebeb 1px solid; background:#FFF; font-family:"Microsoft YaHei","Tahoma","Arial",'宋体'; padding:0 10px;}
.main-for select{ width:310px; height:36px; line-height:36px; border:#ebebeb 1px solid; background:#FFF; font-family:"Microsoft YaHei","Tahoma","Arial",'宋体'; color:#666;}
.main-for input.text-but{ width:100px; height:40px; line-height:30px; border: 1px solid #cdcdcd; background:#e6e6e6; font-family:"Microsoft YaHei","Tahoma","Arial",'宋体'; color:#969696; float:left; margin:0 10px 0 0; display:inline; cursor:pointer; font-size:14px; font-weight:bold;}
#addinfo a{ font-size:14px; font-weight:bold; background:url(images/main/addinfoblack.jpg) no-repeat 0 1px; padding:0px 0 0px 20px; line-height:45px;}
#addinfo a:hover{ background:url(images/main/addinfoblue.jpg) no-repeat 0 1px;}
</style>
</head>
<body>
<!--main_top-->
<table width="99%" border="0" cellspacing="0" cellpadding="0" id="searchmain">
  <tr>
    <td width="99%" align="left" valign="top">您的位置：用户管理&nbsp;&nbsp;&nbsp;&nbsp;添加用户</td>
  </tr>
  <tr>
    <td align="left" valign="top">
    <form name='theform' action='UserController?flag=addUser' method='post' onsubmit='return checkUser(this);'>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="main-tab">
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">用户名：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <c:if test="${not empty map && map.containsKey('userName')}">
           <input class="text-word" type='text' name='userName' id='userName' value=''/>${map.userName }<span id="msg"></span>
        </c:if>
        <c:if test="${empty map || !map.containsKey('userName')}">
           <input class="text-word" type='text' name='userName' id='userName' value=''/><span id="msg"></span>
        </c:if>
        </td>
        </tr>
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">密码：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <c:if test="${not empty map && map.containsKey('userPwd')}">
           <input class="text-word" type='password' name='userPwd' value=''/>${map.userPwd }
        </c:if>
        <c:if test="${empty map || !map.containsKey('userPwd')}">
           <input class="text-word" type='password' name='userPwd' value=''/>
        </c:if>
        </td>
        </tr>
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">部门：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <select name='depId'>
          <c:forEach items="${depList}" var="dep">
            <option value="${dep.id}">${dep.depName}</option>
          </c:forEach>
        </select>
        </td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">用户编号：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <c:if test="${not empty map && map.containsKey('userCode')}">
           <input class="text-word" type='text' name='userCode' value=''/>${map.userCode }
        </c:if>
        <c:if test="${empty map || !map.containsKey('userCode')}">
           <input class="text-word" type='text' name='userCode' value=''/>
        </c:if>
        </td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">性别：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for" style="font-size:14px">
        <input type='radio' name='userSex' value='男' id='sex' checked='checked'/>男
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='userSex' value='女' id='sex'/>女
        </td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">年龄：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <c:if test="${not empty map && map.containsKey('userAge')}">
           <input class="text-word" type='text' name='userAge' value=''/>${map.userAge }
        </c:if>
        <c:if test="${empty map || !map.containsKey('userAge')}">
           <input class="text-word" type='text' name='userAge' value=''/>
        </c:if>
        </td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">权限：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <select name='userPower' id="level">
	    <option value="0" >&nbsp;&nbsp;普通管理员</option>
	    <option value="1" >&nbsp;&nbsp;超级管理员</option>
        </select>
        </td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">&nbsp;</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <input type='hidden' name='suserName' value='${suserName}'/>
        <input type='hidden' name='sdepId' value='${sdepId}'/>
        <input name="" type="submit" value="添加" class="text-but" id="sb">
        <input name="" type="reset" value="重置" class="text-but"></td>
        </tr>
    </table>
    </form>
    </td>
    </tr>
</table>
</body>
</html>