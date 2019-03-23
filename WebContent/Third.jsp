<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三级页</title>
<script language='javascript' src='js/tool.js'></script>
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
     <div class="nav" style="background-color:#999; margin-top:5px;">
        <ul class="ul1">
          <li>
          <c:forEach items="${typeList}" var="type">
         <c:if test="${news.typeid == type.id }">${type.typeName }</c:if>
          </c:forEach>
          </li>
         </ul>
      </div>
      
   <div class="news-item  ">
					<h2><a href="MainController?nFlag=third&id=${news.id }">${news.title }</a></h2>
					<div class="c clearfix">
						<div class="txt">
							<div class="p">
                                <table width="1000px" height="500px"><tr><td colspan="2">内容：${news.content }</td></tr>
                                <tr><td align="left">${news.createtime }</td><td align="right"></td></tr>
                                </table>
                            </div>	
						</div>
					</div>
				</div>
		<div class="nav" style="background-color:#999; margin-top:5px; height:2px;">
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
