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
<title>添加新闻</title>
<!-- <script language='javascript' src='js/jquery-3.1.1.min.js'></script>
<script language="javascript">
$(function(){
	var msg = $("#msg");
	var title = $("#title");
	title.blur(function(){ //焦点移开事件
		var timer = new Date().getTime();
		$.ajax({   //ajax调用
			url:"${pageContext.request.contextPath}/NewsController?nFlag=CheckName&timer="+timer,
			type:"GET",
			dataType:"text",
			data:{
				title : title.val(), //传递参数
			},
			success:function(data){
				if(data == "exist"){
		        	msg.html("<font color='red'>标题已被占用</font>");
		        	$("#sb").attr("disabled",true);
		        }else{
		        	msg.html("<font color='blue'>标题可以使用</font>");
		        	$("#sb").attr("disabled",false);
		        }
		    }
		});
	});
});
</script> -->
<script language="javascript">
function aaa(){
	editor.updateElement(); 
	var title = document.getElementById("title").value;
	if(title.length==0){
		alert('标题不能为空！');
		theform.title.focus();
		return false;
	}
	var createtime = document.getElementById("createtime").value;
	if(createtime.length==0){
		alert('时间不能为空！');
		theform.createtime.focus();
		return false;
	}
	var content = document.getElementById("content").value;
	if(content.length==0){
		alert('内容不能为空！');
		theform.content.focus();
		return false;
	}
	return true;
}


</script>
<script type="text/javascript" src="ckeditor4.4/ckeditor.js"></script>
<script type="text/javascript" src="ckfinder2.3.1/ckfinder.js"></script>
<script language='javascript' src='js/tool.js'></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
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
    <td width="99%" align="left" valign="top">您的位置：新闻管理&nbsp;&nbsp;&nbsp;&nbsp;添加新闻</td>
  </tr>
  <tr>
    <td align="left" valign="top">
    <form name='theform' action='NewsController' method='post' onsubmit='return aaa();' id="detailForm">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="main-tab">
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">标题：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <c:if test="${not empty map && map.containsKey('title')}">
           <input class="text-word" type='text' name='title' id='title' value=''/>${map.title }<span id="msg"></span>
        </c:if>
        <c:if test="${empty map || !map.containsKey('title')}">
           <input class="text-word" type='text' name='title' id='title' value=''/><span id="msg"></span>
        </c:if>
        </td>
        </tr>
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">类别：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <select name='typeid'>
          <c:forEach items="${typeList}" var="type">
            <option value="${type.id}">${type.typeName}</option>
          </c:forEach>
        </select>
        </td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">创建时间：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <c:if test="${not empty map && map.containsKey('createtime')}">
           <input class="text-word Wdate" type='text' name='createtime' value='' id='createtime' onClick="WdatePicker()"/>${map.createtime }
        </c:if>
        <c:if test="${empty map || !map.containsKey('createtime')}">
           <input class="text-word Wdate" type='text' name='createtime' value='' id='createtime' onClick="WdatePicker()"/>
        </c:if>
        </td>
        </tr>
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">内容：</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <c:if test="${not empty map && map.containsKey('content')}">
        <textarea id="content" name="content"></textarea>${map.content }
        </c:if>
        <c:if test="${empty map || !map.containsKey('content')}">
        <textarea id="content" name="content"></textarea>
        </c:if>
        <!-- <input type="button" value="保存" id="save" onclick="save()" /> -->

        </td>
      </tr> 
      
    
      
      <tr onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#edf5ff'">
        <td align="right" valign="middle" class="borderright borderbottom bggray">&nbsp;</td>
        <td align="left" valign="middle" class="borderright borderbottom main-for">
        <input type='hidden' name='nFlag' value='addNews'/>
        <input type='hidden' name='flag' value='${sessionScope.user.userPower}'/>
        <input type='hidden' name='uid' value='${sessionScope.user.id}'/>
        <input type='hidden' name='ntypeid' value='${ntypeid}'/>
        <input type='hidden' name=nflag value='${nflag}'/>
        <input name="" type="submit" value="添加" class="text-but" id="save" onclick="save()" >
        <input name="" type="reset" value="重置" class="text-but"></td>
        </tr>
    </table>
    </form>
    
    <script type="text/javascript">
    var editor = null;
    window.onload = function(){
        editor = CKEDITOR.replace('content'); //参数‘content’是textarea元素的name属性值，而非id属性值
        CKFinder.setupCKEditor( editor, 'ckfinder2.3.1/' );
    }
    
    </script>  
            <script type="text/javascript">
            function save(){
            	//alert("aaaaaaaaa");
                editor.updateElement(); //非常重要的一句代码
                //前台验证工作
                //提交到后台
              
            }
</script>
    
    
    </td>
    </tr>
</table>
</body>
</html>