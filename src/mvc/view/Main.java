package mvc.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.domain.Department;
import mvc.domain.User;


@WebServlet("/Main")
public class Main extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//接收访问次数
		ServletContext application = this.getServletContext();
		out.println("访问次数："+application.getAttribute("visited"));
		//接收登录者信息
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		if(u==null){
			response.sendRedirect("Login");
			return;
		}
		if(u.getUserPower()==0){
			out.println("恭喜您！普通管理员"+u.getUserName());
		}else{
			out.println("恭喜您！超级管理员"+u.getUserName());
		}
		
		
		
		//接收为main页准备的数据
		int pageNow = Integer.parseInt(request.getAttribute("pageNow").toString());
		int pageCount = Integer.parseInt(request.getAttribute("pageCount").toString());
		List<User> userList = (List<User>)request.getAttribute("userList");
		List<Department> depList= (List<Department>) request.getAttribute("depList");
		int sdepId = -1;
		String suserName ="";
		if(request.getAttribute("sdepId")!=null){
			sdepId =(int) request.getAttribute("sdepId");
		}
		if(request.getAttribute("suserName")!=null){
			suserName = (String) request.getAttribute("suserName");
		} 
		
		
		
		out.println("<html>");
		out.println("<head><title>主页11111111</title>");
		out.println("<style type='text/css'>"
				+ "a:hover{color:red;}"
				+ "a{color:#FFF;text-decoration:none;font-weight:bold;}"
				+ ".table a:hover{color:red;}"
				+ ".table a{color:blue;}"
				+ "</style>");
		out.println("<script language='javascript' src='tool.js'>");
		out.println("</script>");
		out.println("</head><body background='bg1.jpg'>");
		out.println("<p><a href='UserController?flag=exit'>安全退出</a></p>");
		
		out.println("<center>");
		out.println("<p><h1>权限管理系统</h1><h3>用户管理</h3></p>");
		out.println("<form method='post' action='UserController?flag=selectUser'>");
		out.println("<a href='UserController?flag=add&suserName="+suserName+"&sdepId="+sdepId+"'>添加用户</a>");
		if(u.getUserPower()==0){
			out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}else{
			out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href='UserController?flag=depManage'>部门管理</a>");
		}
		out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp部门：<select name='sdepId'>");
		out.println("<option value='-1'>请选择</option>");
		for (Department dep : depList) {
			if(sdepId==dep.getId()){
				out.println("<option value='"+dep.getId()+"' selected='selected'>"+dep.getDepName()+"</option>");
			}else{
				out.println("<option value='"+dep.getId()+"'>"+dep.getDepName()+"</option>");
			}
		}
		out.println("</select></td></tr>");
		out.println("用户名：<input type='text' name='suserName' value='"+suserName+"'/>");
		out.println("<input type='submit' value='搜索'/>");
		out.println("</form>");
		
		out.println("<form method='post' action='UserController?flag=delbatch&pageNow="+pageNow+"&suserName="+suserName+"&sdepId="+sdepId+"' >");
		out.println("<table width='800' class='table'>");
		out.println("<tr bgcolor='#cccccc'><td><input type='submit' value='批量删除'/></td><td>id</td><td>部门</td><td>用户名</td><td>密码</td>"
				+ "<td>用户编号</td><td>性别</td><td>年龄</td><td>权限</td><td>管理</td></tr>");
		for (User user : userList) {
			out.println("<tr bgcolor='#F2F2F2'><td><input type='checkbox' name='ids' value='"+user.getId()+"' /></td><td>"+user.getId()+"</td>");
			for (Department dep : depList) {
				if(user.getDepId()==dep.getId()){
					out.println( "<td>"+dep.getDepName()+"</td>");
				}
			}
			out.println("<td>"+user.getUserName()+"</td><td>"+user.getUserPwd()+"</td>"
					+ "<td>"+user.getUserCode()+"</td><td>"+user.getUserSex()+"</td>"
					+ "<td>"+user.getUserAge()+"</td>");
			if(user.getUserPower()==0){
				out.println( "<td>普通管理员</td>");
			}else{
			    out.println( "<td>超级管理员</td>");
			}
			out.println( "<td><a href='UserController?flag=del&id="+user.getId()+"&pageNow="+pageNow+"&suserName="+suserName+"&sdepId="+sdepId+"'>【删除】</a>"
							+ "<a href='UserController?flag=edit&id="+user.getId()+"&pageNow="+pageNow+"&suserName="+suserName+"&sdepId="+sdepId+"'>【编辑】</a></td></tr>");
		}
		out.println("</table>");
		out.println("</form>");
		//数字条
		out.println("<table>");
		out.println("<tr><td>");
		if(pageNow > 1){
			out.println("<a href=UserController?pageNow=1&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">首页</a>");
			out.println("<a href=UserController?pageNow="+(pageNow-1)+"&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">上一页</a>");
		}
		if(pageCount>10){
			int flag = 0;
			if(pageNow < 5){flag = 5;}
			else if(pageNow+5 > pageCount){
				flag = pageCount-5;	}
			else{ flag= pageNow;}
			for (int i = (flag-4); i <= flag+5; i++) {
				if(i == pageNow){
					out.println("<font color='red'>[<a href=UserController?pageNow="+i+"&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">"+i+"</a>]</font>");
				}else{
					out.println("[<a href=UserController?pageNow="+i+"&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">"+i+"</a>]");
				}
			}
		}else{
			for (int i = 1; i <= pageCount; i++) {
				if(i == pageNow){
					out.println("<font color='red'>[<a href=UserController?pageNow="+i+"&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">"+i+"</a>]</font>");
				}else{
					out.println("[<a href=UserController?pageNow="+i+"&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">"+i+"</a>]");
				}
			}
		}
		
		
		if(pageNow < pageCount){
			out.println("<a href=UserController?pageNow="+(pageNow+1)+"&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">下一页</a>");
			out.println("<a href=UserController?pageNow="+pageCount+"&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">尾页</a>");
		}
		//跳转
		if(pageCount>10){
		Map<String,String> error = (Map<String,String>)request.getAttribute("map");
		out.println("<form method='post' action='UserController?flag=jumpCheck' onsubmit='return checkJump();'>");
		if(error!=null && error.containsKey("page")){
			out.println("<input type='text' name='page' id='pageNow' size='3' value=''/>"+error.get("page"));
		}else{
			out.println("<input type='text' name='page' id='pageNow' size='3' value=''/>");
		}
				out.println( "<input type='hidden' name='pageCount' id='pageCount' value='"+pageCount+"' />"
				+ "<input type='hidden' name='pageNow' value='"+pageNow+"'>"
				+ "<input type='hidden' name='sdepId' value='"+sdepId+"'>"
				+ "<input type='hidden' name='suserName' value='"+suserName+"'>"
				+ "<input type='submit' value='跳转'/>&nbsp&nbsp共"+pageCount+"页");
		out.println("</form>");
		}
		
		out.println("</td></tr>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
