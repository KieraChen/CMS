<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:if test="${empty sessionScope.user}">
<c:redirect url="Index.jsp"></c:redirect>
</c:if>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻类别主页</title>
<link href="css/css.css" type="text/css" rel="stylesheet" />
<link href="css/main.css" type="text/css" rel="stylesheet" />
<link rel="shortcut icon" href="images/main/favicon.ico" />
<style>
body{overflow-x:hidden; background:#f2f0f5; padding:15px 0px 10px 5px;}
#searchmain{ font-size:16px;}
#search{ font-size:16px; background:#548fc9; margin:10px 10px 0 0; display:inline; width:100%; color:#FFF; float:left}
#search form span{height:40px; line-height:40px; padding:0 0px 0 10px; float:left;}
#search form select.text-word{height:24px; line-height:24px; width:180px; margin:8px 0 6px 0; padding:10px 0px 0 10px; float:left; border:1px solid #FFF;}
#search form input.text-word{height:24px; line-height:24px; width:180px; margin:8px 0 6px 0; padding:10px 0px 0 10px; float:left; border:1px solid #FFF;}
#search form input.text-but{height:24px; line-height:24px; width:55px; background:url(images/main/list_input.jpg) no-repeat left top; border:none; cursor:pointer; font-family:"Microsoft YaHei","Tahoma","Arial",'宋体'; color:#666; float:left; margin:8px 0 0 6px; display:inline;}
#search a.add{ background:url(images/main/add.jpg) no-repeat -3px 7px #548fc9; padding:0 10px 0 26px; height:40px; line-height:40px; font-size:14px; font-weight:bold; color:#FFF; float:right}
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
.bggray{ background:#f9f9f9}
</style>
</head>
<body>
<!--main_top-->
<table width="99%" border="0" cellspacing="0" cellpadding="0" id="searchmain">
  <tr>
    <td width="99%" align="left" valign="top">您的位置：类别管理</td>
  </tr>
  <tr>
    <td align="left" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="search">
  		<tr>
   		 <td width="90%" align="left" valign="middle">
<form method='post' action='NewsTypeController?nFlag=typeManage'>	 
  <select name='nSort'>
  <c:if test="${nSort == -1}">
    <option value='-1' selected='selected'>随机</option>
    <option value='1'>按序</option>
  </c:if>
  <c:if test="${nSort == 1}">
    <option value='-1'>随机</option>
    <option value='1' selected='selected'>按序</option>
  </c:if>
  </select>  		 
  <input type='submit' value='排序'/>
</form>    		   
         </td>
         <c:if test="${sessionScope.user.userPower==1 }">
  		  <td width="10%" align="center" valign="middle" style="text-align:right; width:150px;">
  		  <a href='AddNewsType.jsp?nSort=${nSort }' target="mainFrame" onFocus="this.blur()" class="add">添加类别</a></td>
  		</c:if>
  		</tr>
	</table>
    </td>
  </tr>
  <tr>
    <td align="left" valign="top">
    <form method='post' action='NewsTypeController?nFlag=delbatchType'>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="main-tab">
      <tr>
      <%-- <c:if test="${sessionScope.user.userPower==1 }">
      <th align="center" valign="middle" class="borderright"><input type='hidden' value='${nSort }' name='nSort' />
      <input type='submit' value='批量删除'/></th>
      </c:if> --%>
        <!-- <th align="center" valign="middle" class="borderright">id</th> -->
        <th align="center" valign="middle" class="borderright">类别名称</th>
        <th align="center" valign="middle" class="borderright">排序权限</th>
        <c:if test="${sessionScope.user.userPower==1 }">
        <th align="center" valign="middle">管理</th>
        </c:if>
      </tr>
      
        <c:forEach items="${typeList }" var="type">
        <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <%-- <c:if test="${sessionScope.user.userPower==1 }">
        <td align="center" valign="middle" class="borderright borderbottom"><input type='checkbox' name='ids' value='${type.id }' /></td>
        </c:if> --%>
        <%-- <td align="center" valign="middle" class="borderright borderbottom">${type.id }</td> --%>
        <td align="center" valign="middle" class="borderright borderbottom">${type.typeName }</td>
        <td align="center" valign="middle" class="borderright borderbottom">${type.sort }</td>
      <c:if test="${sessionScope.user.userPower==1 }">
        <td align="center" valign="middle" class="borderbottom">
        <a href='NewsTypeController?nFlag=delType&id=${type.id }&nSort=${nSort}' target="mainFrame" onFocus="this.blur()" class="add">删除并删除该类别所有新闻</a>
        <span class="gray">&nbsp;|&nbsp;</span>
        <a href='NewsTypeController?nFlag=editT&id=${type.id }&nSort=${nSort}' target="mainFrame" onFocus="this.blur()" class="add">编辑</a></td>
    </c:if>
     </tr>
     </c:forEach>
      
       
    </table></form></td>
    </tr>
 
</table>

</body>
</html>