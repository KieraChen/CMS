/**
 * 
 */
function xmlHttpRequest()
{
	xmlhttp=null;
	if (window.XMLHttpRequest)
	  {// code for all new browsers
	  xmlhttp=new XMLHttpRequest();
	  }
	else if (window.ActiveXObject)
	  {// code for IE5 and IE6
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
      return xmlhttp;
}


/*function checkNews(theform){
	CKEDITOR.replace('content').updateElement();
	if(theform.title.value.length==0){
		alert('标题不能为空！');
		theform.title.focus();
		return false;
	}
	if(theform.createtime.value.length==0){
		alert('时间不能为空！');
		theform.createtime.focus();
		return false;
	}
	if(theform.content.value.length==0){
		alert('内容不能为空！');
		theform.content.focus();
		return false;
	}
	var content=CKEDITOR.instances.mckeditor.getData(); 
    
    content = content.replace(/^\s+/g,""); 
    content = content.replace(/\s+$/g,""); 
            if (content =="") 
             { 
               alert("请填写话题内容."); 
               f.mckeditor.focus(); 
               return false; 
             }
	return true;
	                       
 }*/
function checkUser(theform){
	if(theform.userName.value.length==0){
		alert('用户名不能为空！');
		theform.userName.focus();
		return false;
	}
	if(theform.userPwd.value.length==0){
		alert('密码不能为空！');
		theform.userPwd.focus();
		return false;
	}
	if(theform.userCode.value.length==0){
		alert('用户编号不能为空！');
		theform.userCode.focus();
		return false;
	}
	if(isNaN(theform.userAge.value) || theform.userAge.value<0 || theform.userAge.value.length==0 || theform.userAge.value>120){
		alert('年龄不合法！');
		theform.userAge.focus();
		return false;
	}
	return true;
	                       
 }
function checkDep(theform){
	if(theform.depName.value.length==0){
		alert('部门名称不能为空！');
		theform.depName.focus();
		return false;
	}
	if(theform.depCreateTime.value.length==0){
		alert('创建时间不能为空！');
		theform.depCreateTime.focus();
		return false;
	}
	if(theform.sort.value.length != 0){
		if(isNaN(theform.sort.value)|| theform.sort.value>100){
			alert('权限必须为数值小于100！');
			theform.sort.focus();
			return false;
		}
	}
	return true;
}

function checkType(theform){
	if(theform.typeName.value.length==0){
		alert('类别名称不能为空！');
		theform.typeName.focus();
		return false;
	}
	if(theform.sort.value.length != 0){
		if(isNaN(theform.sort.value)|| theform.sort.value>100){
			alert('权限必须为数值小于100！');
			theform.sort.focus();
			return false;
		}
	}
	return true;
}

function checkJump(){
	var num = document.getElementById('pageNow').value;
	var pageCount = document.getElementById('pageCount').value;
    if(isNaN(num)||num==null){
    	alert('不能为空且必须为数值！');
        document.getElementById('pageNow').focus();
        return false;
       }
    if(num < 1){
    	alert('不能小于1！');
		document.getElementById('pageNow').focus();
		return false;
	   }
	if(num > pageCount){
		alert('不能大于总页数！总页数'+pageCount); 
		document.getElementById('pageNow').focus();
		return false;
	   }
	return true;
}














