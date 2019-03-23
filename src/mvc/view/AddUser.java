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


@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
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
		
		List<Department> depList= (List<Department>) request.getAttribute("depList");
		Map<String,String> errors = (Map<String,String>)request.getAttribute("map");
		
		int sdepId = -1;
		String suserName ="";
		if(request.getAttribute("sdepId")!=null){
			sdepId =(int) request.getAttribute("sdepId");
		}
		if(request.getAttribute("suserName")!=null){
			suserName = (String) request.getAttribute("suserName");
		} 
		out.println("<html>");
		out.println("<head><title>添加用户111111</title>");
		out.println("<script language='javascript' src='tool.js'></script>");
		out.println("</head><body background='bg1.jpg'>");
		
		out.println("<p><h2>添加信息</h2></p>");
		out.println("<form name='theform' action='UserController?flag=addUser' method='post' onsubmit='return checkUser(this);' >");//
		out.println("<table>");
		if(errors!=null && errors.containsKey("userName")){
			out.println("<tr><td align='right'>用户名：</td><td><input type='text' name='userName' value=''/>"+errors.get("userName")+"</td></tr>");
		}else{
		out.println("<tr><td align='right'>用户名：</td><td><input type='text' name='userName' value=''/></td></tr>");
		}
		if(errors!=null && errors.containsKey("userPwd")){
		    out.println("<tr><td align='right'>密码：</td><td><input type='password' name='userPwd' value=''/>"+errors.get("userPwd")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>密码：</td><td><input type='password' name='userPwd' value=''/></td></tr>");
		}
		out.println("<tr><td align='right'>部门：</td><td><select name='depId'>");
		for (Department dep : depList) {
			out.println("<option value='"+dep.getId()+"'>"+dep.getDepName()+"</option>");
		}
		out.println("</select></td></tr>");
		if(errors!=null && errors.containsKey("userCode")){
		    out.println("<tr><td align='right'>用户编号：</td><td><input type='text' name='userCode' value=''/>"+errors.get("userCode")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>用户编号：</td><td><input type='text' name='userCode' value=''/></td></tr>");
		}
		out.println("<tr><td align='right'>性别：</td><td><input type='radio' name='userSex' value='男' id='sex' checked='checked'/>男"
				+ "<input type='radio' name='userSex' value='女' id='sex'/>女</td></tr>");
		if(errors!=null && errors.containsKey("userAge")){
		    out.println("<tr><td align='right'>年龄：</td><td><input type='text' name='userAge' value=''/>"+errors.get("userAge")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>年龄：</td><td><input type='text' name='userAge' value=''/></td></tr>");
		}
		
		out.println("<tr><td align='right'>权限：</td><td><select name='userPower'>"
				+ "<option value='0'>普通管理员</option><option value='1'>超级管理员</option></select></td></tr>");
		out.println("<tr><td colspan='2'><input type='hidden' name='flag' value='addcheck'/></td></tr>");
		out.println("<tr><td><input type='hidden' name='suserName' value='"+suserName+"'/>"
				+ "<input type='hidden' name='sdepId' value='"+sdepId+"'/></td>"
						+ "<td><input type='submit' value='添加'/></td></tr>");
		out.println("</table>");
		out.println("</form>");
		
		out.println("</body>");
		out.println("</html>");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
