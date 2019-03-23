<%@page import="mvc.domain.NewsType"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站首页</title>
<link rel="stylesheet" type="text/css" href="css/asjy.css"/>
</head>

<body>
<center>
<div class="contianer">
   <!--头部开始-->
   <div class="header">
      <!--headerTop-->
      <div class="headerTop">
         <!--logo-->
         <div class="logo">
            <img src="images/logo.png"/>
         </div>
         
         <!--sogen-->
         <div class="sogen">
            做品质教育　　用事实说话
         </div>
         
         <!--tel-->
         <div class="tel">
           <img src="images/tel.jpg" align="left"/>
         </div>
      </div>
      <!--nav导航条-->
      <div class="nav">
        <ul class="ul1">
        <li><a href="MainController?nFlag=first">首页</a></li>
        <c:forEach items="${typeList}" var="type">
        <li><a href="MainController?nFlag=second&id=${type.id }">
        ${type.typeName}
        </a></li>
        </c:forEach>
         </ul>
      </div>
      <!--banner-->
      <div style="margin-top:5px"> 
        <img src="images/bigpic.png" />
      </div>
   </div>
   <!--头部结束-->
   
    <!--内容开始-->
   <div>
     <!--course-->
     <div class="course">
       <img src="images/1-01.png"/>
       <img src="images/1-02.png"/>
      <img src="images/1-03.png"/>
      <img src="images/1-04.png"/></div>
      
     <!--通知公告-->
     <div class="form1">
     <div class="top">
      <div class="top1">
      ${typeList[0].typeName}
     </div>
      <div class="top2">
      <a href="MainController?nFlag=second&id=${typeList[0].id}">更多</a>
     </div>
     </div>
     
      <div>
        <img src="images/tongzhi.png" width="110" height="150" class="image" /></div>
     
      <div class="1">
      <ul class="ul2">
        <%-- <c:set var="sid" value="${typeList[0].id}" ></c:set>
        <c:out value="${sid}"/>
        <c:forEach items="${map[sid]}" var="news">
        <li><a href="#${news.id }">${news.title}</a></li>
        </c:forEach> --%>
      <c:forEach items="${list[0]}" var="news">
      <li><a href="MainController?nFlag=third&id=${news.id }" target="_blank">
       <c:if test="${fn:length(news.title)>10}">
        ${fn:substring(news.title,0,10)}...
        </c:if>
        <c:if test="${fn:length(news.title)<=10}">
        ${news.title}
        </c:if>
      </a></li>
      </c:forEach>
     <!--  
      <li><a href="#">成都市女看电视女老大什么城市导论</a></li>
      <li><a href="#">产生的你看了暗沉真的哭了女看到你</a></li>
      <li><a href="#">大师VB可我卡了女可能</a></li>
      <li><a href="#">第三款女李开复你买了吗李韩剧快快快能</a></li>
      <li><a href="#">路口等你来看女看到女看到你考虑你了</a></li> -->
      </ul>
     </div>
     </div>
     
     <!--招生问答-->
      <div class="form1">
     <div class="top">
      <div class="top1">
      ${typeList[1].typeName}
     </div>
      <div class="top2">
      <a href="MainController?nFlag=second&id=${typeList[1].id}">更多</a>
     </div>
     </div>
     
      <div>
      <img src="images/wenda.png" width="110" height="150" class="image" /></div>
     
      <div class="1">
      <ul class="ul2">
      <c:forEach items="${list[1]}" var="news">
      <li><a href="MainController?nFlag=third&id=${news.id }" target="_blank"><c:if test="${fn:length(news.title)>10}">
        ${fn:substring(news.title,0,10)}...
        </c:if>
        <c:if test="${fn:length(news.title)<=10}">
        ${news.title}
        </c:if></a></li>
      </c:forEach>
      </ul>
     </div>
     </div>
     
     <!--教学环境-->
      <div class="form2">
     <div class="top3">
      <div class="top1">
      教学环境
     </div>
      <div class="top2">
      <a href="#">更多</a>
     </div>
     </div>
      <div>
        <img src="images/20130204031427.jpg" width="150" height="115" class="image1"/>
        <img src="images/20130203011532.jpg" width="150" height="115" class="image1"/>
        <img src="images/20130204031514.jpg" width="150" height="115" class="image1"/>
        <img src="images/20130204031528.jpg" width="150" height="115" class="image1"/>
        <img src="images/20130204031556.jpg" width="150" height="115" class="image1"/>
        <img src="images/20130204031606.jpg" width="150" height="115" class="image1"/></div>
        </div>
     <!--学习园地-->
     <div class="form1">
     <div class="top">
      <div class="top1">
      ${typeList[2].typeName}
     </div>
      <div class="top2">
      <a href="MainController?nFlag=second&id=${typeList[2].id}">更多</a>
     </div>
     </div>
     
      <div>
        <img src="images/xuyuan.png" width="110" height="150" class="image"/></div>
     
      <div class="1">
      <ul class="ul2">
      <c:forEach items="${list[2]}" var="news">
      <li><a href="MainController?nFlag=third&id=${news.id }" target="_blank"><c:if test="${fn:length(news.title)>10}">
        ${fn:substring(news.title,0,10)}...
        </c:if>
        <c:if test="${fn:length(news.title)<=10}">
        ${news.title}
        </c:if></a></li>
      </c:forEach>
      </ul>
     </div>
     </div>
     
     <!--就业园地-->
      <div class="form1">
     <div class="top">
      <div class="top1">
     ${typeList[3].typeName}
     </div>
      <div class="top2">
      <a href="MainController?nFlag=second&id=${typeList[3].id}">更多</a>
     </div>
     </div>
     
      <div>
      <img src="images/jiuye.png" width="110" height="150" class="image"/></div>
     
      <div class="1">
      <ul class="ul2">
      <c:forEach items="${list[3]}" var="news">
      <li><a href="MainController?nFlag=third&id=${news.id }" target="_blank"><c:if test="${fn:length(news.title)>10}">
        ${fn:substring(news.title,0,10)}...
        </c:if>
        <c:if test="${fn:length(news.title)<=10}">
        ${news.title}
        </c:if></a></li>
      </c:forEach>
      </ul>
     </div>
     </div>
     
     <!--学员风采-->
      <div class="form2">
     <div class="top3">
      <div class="top1">
      学员风采
     </div>
      <div class="top2">
      <a href="#">更多</a>
     </div>
     </div>
      <div>
        <img src="images/20130204025744.jpg" width="150" height="115" class="image1"/>
        <img src="images/20130204023811.jpg" width="150" height="115" class="image1"/>
        <img src="images/20130204023842.jpg" width="150" height="115" class="image1"/>
        <img src="images/20130204024024.jpg" width="150" height="115" class="image1"/>
        <img src="images/20130204024038.jpg" width="150" height="115" class="image1"/>
        <img src="images/20130204024143.jpg" width="150" height="115" class="image1"/></div>
        </div>
         <!--技术交流-->
     <div class="form1">
     <div class="top">
      <div class="top1">
      ${typeList[4].typeName}
     </div>
      <div class="top2">
      <a href="MainController?nFlag=second&id=${typeList[4].id}">更多</a>
     </div>
     </div>
     
      <div>
        <img src="images/jishu.png" width="110" height="150" class="image"/></div>
     
      <div class="1">
      <ul class="ul2">
      <c:forEach items="${list[4]}" var="news">
      <li><a href="MainController?nFlag=third&id=${news.id }" target="_blank"><c:if test="${fn:length(news.title)>10}">
        ${fn:substring(news.title,0,10)}...
        </c:if>
        <c:if test="${fn:length(news.title)<=10}">
        ${news.title}
        </c:if></a></li>
      </c:forEach>
      </ul>
     </div>
     </div>
     
     <!--资源共享-->
      <div class="form1">
     <div class="top">
      <div class="top1">
      ${typeList[5].typeName}
     </div>
      <div class="top2">
      <a href="MainController?nFlag=second&id=${typeList[5].id}">更多</a>
     </div>
     </div>
     
      <div>
      <img src="images/ziyuan.png" width="110" height="150" class="image"/></div>
     
      <div class="1">
      <ul class="ul2">
      <c:forEach items="${list[5]}" var="news">
      <li><a href="MainController?nFlag=third&id=${news.id }" target="_blank"><c:if test="${fn:length(news.title)>10}">
        ${fn:substring(news.title,0,10)}...
        </c:if>
        <c:if test="${fn:length(news.title)<=10}">
        ${news.title}
        </c:if></a></li>
      </c:forEach>
      </ul>
     </div>
     </div>
     <!--师资力量-->
     <div class="form3">
     <div class="top3">
      <div class="top1">
      师资力量
     </div>
      <div class="top2">
      <a href="#">更多</a>
     </div>
     </div>
     <div>
        <img src="images/20130202085930.jpg" width="150" height="200" class="image2"/>
        <img src="images/20130202085832.jpg" width="150" height="200" class="image2"/>
        <img src="images/20130202085642.jpg" width="150" height="200" class="image2"/>
        <img src="images/20130202085427.jpg" width="150" height="200" class="image2"/>
        <img src="images/20130202084041.jpg" width="150" height="200" class="image2"/>
        <img src="images/20130202083720.jpg" width="150" height="200" class="image2"/></div>
     </div>
     
   </div>
   <!--内容结束-->
   
   <!--底部开始-->
   <div>
   <!--底部头-->
   <div class="bottomTop">
      友情链接
     </div>
     <!--底部图片-->
   <div class="course">
       <img src="images/20130204013730.jpg" width="150" height="60"/>
       <img src="images/20130204013605.png" width="150" height="60"/>
      <img src="images/20130204013427.jpg" width="150" height="60"/>
      <img src="images/20130204012917.gif" width="150" height="60"/>
      <img src="images/20130203051430.png" width="150" height="60"/>
      <img src="images/logo.png"/ width="150" height="60"></div>
     <!--底部分界--> 
      <div class="bottomUnderline"></div>
      <!--底部字-->
      <div class="bottomWord">
      <br>速度快君离开你的老家买了都是吕经理是的了都是哪里都删了女老师的女拉开了沙除了你打算离开女</br>
      <br>你看V领的数量女扣篮大赛绿能大师吕美丽第三季莫老师来了发的，不能的，令</br>
      <br>第三周轮唠嗑呢上课了的代付款都是撒开了</br>
      </div>
      
   </div>
   <!--底部结束-->
</div>
</center>  
</body>
</html>
