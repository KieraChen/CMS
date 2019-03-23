<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:if test="${empty sessionScope.user}">
<c:redirect url="Index.jsp"></c:redirect>
</c:if>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户主页</title>
<script language='javascript' src='js/tool.js'></script>
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
    <td width="99%" align="left" valign="top">您的位置：新闻管理</td>
  </tr>
  <tr>
    <td align="left" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="search">
  		<tr>
   		 <td width="90%" align="left" valign="middle">
   		 
   		 <%--复合查询 selectuser 回原页--%>
	         <form method="post" action="NewsController?nFlag=selectNews">
	         类别：<select name='ntypeid'>
<option value='-1'>请选择</option>
<c:forEach items="${typeList}" var="type">
<c:if test="${type.id==ntypeid}">
<option value='${type.id}' selected='selected'>${type.typeName}</option>
</c:if>
<c:if test="${type.id!=ntypeid}">
<option value="${type.id}">${type.typeName}</option>
</c:if>
</c:forEach>
</select>
<%-- <c:if test="${sessionScope.user.userPower==1 }"> --%>
审核：<select name='nflag'>
<!-- <option value='-1'>全部</option> -->
<option value='0' ${nflag==0 ? "selected='selected'" : ""}>未审核</option>
<option value='1' ${nflag==1 ? "selected='selected'" : ""}>已审核</option>
</select>
<%-- </c:if>
<c:if test="${sessionScope.user.userPower!=1 }">
审核：<select name='nflag'>
<option value='0' selected='selected'>未审核</option>
</select>
</c:if> --%>
<input type='submit' value='搜索'/>
	         </form>
	         
         </td>
  		  <td width="10%" align="center" valign="middle" style="text-align:right; width:150px;">
  		  <%--添加新闻 --%>
  		  <a href='NewsController?nFlag=add&ntypeid=${ntypeid}&nflag=${nflag}' target="mainFrame" onFocus="this.blur()" class="add">添加新闻</a></td>
  		</tr>
	</table>
    </td>
  </tr>
  <tr>
    <td align="left" valign="top">
     <%--批量删除新闻 --%>
    <form method='post' action='NewsController?nFlag=delbatch&pageNow=${pageNow }&ntypeid=${ntypeid}&nflag=${nflag}' >
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="main-tab">
      <tr><th align="center" valign="middle" class="borderright"><input type='submit' value='批量删除'/></th>
        <!-- <th align="center" valign="middle" class="borderright">id</th> -->
        <th align="center" valign="middle" class="borderright">标题</th>
        <th align="center" valign="middle" class="borderright">内容</th>
        <th align="center" valign="middle" class="borderright">类别</th>
        <th align="center" valign="middle" class="borderright">审核</th>
        <th align="center" valign="middle" class="borderright">发布时间</th>
        <th align="center" valign="middle">管理</th>
      </tr>
      
        <c:forEach items="${newsList }" var="news">
        <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="center" valign="middle" class="borderright borderbottom"><input type='checkbox' name='ids' value='${news.id }' /></td>
        <%-- <td align="center" valign="middle" class="borderright borderbottom">${news.id }</td> --%>
        
        <td align="center" valign="middle" class="borderright borderbottom">
        <c:if test="${fn:length(news.title)>5}">
        ${fn:substring(news.title,0,5)}...
        </c:if>
        <c:if test="${fn:length(news.title)<=5}">
        ${news.title}
        </c:if>
        </td>
        
        <td align="center" valign="middle" class="borderright borderbottom">
        <c:if test="${fn:length(news.content)>10}">
        ${fn:substring(news.content,0,10)}...
        </c:if>
        <c:if test="${fn:length(news.content)<=10}">
        ${news.content}
        </c:if>
        </td>
        
        <c:forEach items="${typeList}" var="type">
          <c:if test="${news.typeid == type.id }">
            <td align="center" valign="middle" class="borderright borderbottom">${type.typeName }</td>
          </c:if>
        </c:forEach>
        
        <td align="center" valign="middle" class="borderright borderbottom">${news.flag == 0 ? "未审核" : "已审核"}</td>
        <td align="center" valign="middle" class="borderright borderbottom">${news.createtime}</td>
        <td align="center" valign="middle" class="borderbottom">
        <a href="NewsController?nFlag=del&id=${news.id }&pageNow=${pageNow }&ntypeid=${ntypeid}&nflag=${nflag}" target="mainFrame" onFocus="this.blur()" class="add">删除</a>
        <span class="gray">&nbsp;|&nbsp;</span>
        <a href="NewsController?nFlag=edit&id=${news.id }&pageNow=${pageNow }&ntypeid=${ntypeid}&nflag=${nflag}" target="mainFrame" onFocus="this.blur()" class="add">编辑</a></td>
     </tr>
     </c:forEach>
      
       
    </table>
    </form>
    
  <tr><td align="center">
  <%--数字条 --%>
  <aside class="paging fenye">
  <c:if test="${pageNow > 1 }">
    <a href="NewsController?pageNow=1&nFlag=selectNews&ntypeid=${ntypeid}&nflag=${nflag}">首页</a>
    <a href="NewsController?pageNow=${(pageNow-1)}&nFlag=selectNews&ntypeid=${ntypeid}&nflag=${nflag}">上一页</a>
  </c:if>
  <%--页数 --%>
  <c:if test="${pageCount > 10}">
    <c:set var="aa" value="0"></c:set>
    <c:if test="${pageNow < 5}">
      <c:set var="aa" value="5"></c:set>
    </c:if>
    <c:if test="${(pageNow+5) > pageCount}">
      <c:set var="aa" value="${pageCount-5}"></c:set>
    </c:if>
    <c:if test="${pageNow >= 5 && (pageNow+5) <= pageCount}">
      <c:set var="aa" value="${pageNow}"></c:set>
    </c:if>
    
    <c:forEach begin="${aa-4 }" end="${aa+5 }" step="1" var="i">
      <c:if test="${i==pageNow }">
        <font color='red'>[<a href="NewsController?pageNow=${i }&nFlag=selectNews&ntypeid=${ntypeid}&nflag=${nflag}">${i }</a>]</font>
      </c:if>
      <c:if test="${i!=pageNow }">
        [<a href="NewsController?pageNow=${i }&nFlag=selectNews&ntypeid=${ntypeid}&nflag=${nflag}">${i }</a>]
      </c:if>
    </c:forEach>
    
  </c:if>
  
  <c:if test="${pageCount <= 10}">
    <c:forEach begin="1" end="${pageCount}" step="1" var="i">
      <c:if test="${i==pageNow }">
        <font color='red'>[<a href="NewsController?pageNow=${i }&nFlag=selectNews&ntypeid=${ntypeid}&nflag=${nflag}">${i }</a>]</font>
      </c:if>
      <c:if test="${i!=pageNow }">
        [<a href="NewsController?pageNow=${i }&nFlag=selectNews&ntypeid=${ntypeid}&nflag=${nflag}">${i }</a>]
      </c:if>
    </c:forEach>
  </c:if>

  <c:if test="${pageNow < pageCount }">
    <a href="NewsController?pageNow=${(pageNow+1)}&nFlag=selectNews&ntypeid=${ntypeid}&nflag=${nflag}">下一页</a>
    <a href="NewsController?pageNow=${pageCount}&nFlag=selectNews&ntypeid=${ntypeid}&nflag=${nflag}">尾页</a>
  </c:if>
<%--跳转 --%>
  <c:if test="${pageCount > 10}">
    <form method='post' action='NewsController?nFlag=jumpCheck' onsubmit='return checkJump();' style=" display:inline-block">
    <c:if test="${not empty map && map.containsKey('page')}">
      <input type='text' name='page' id='pageNow' size='3' value=''/>${map.page }
    </c:if>
    <c:if test="${empty map || !map.containsKey('page')}">
      <input type='text' name='page' id='pageNow' size='3' value=''/>
    </c:if>
    <input type='hidden' name='pageCount' id='pageCount' value='${pageCount}' />
    <input type='hidden' name='pageNow' value='${pageNow}'>
    <input type='hidden' name='ntypeid' value='${ntypeid}'>
    <input type='hidden' name='nflag' value='${nflag}'>
    <input type='submit' value='跳转'/>&nbsp;&nbsp;共${pageCount}页
    </form>
  </c:if>
  </aside>	
  </td></tr>
  
</table>

</body>
</html>