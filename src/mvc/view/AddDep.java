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


@WebServlet("/AddDep")
public class AddDep extends HttpServlet {
	
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
		
		//List<Department> depList= (List<Department>) request.getAttribute("depList");
		Map<String,String> errors = (Map<String,String>)request.getAttribute("map");
		int dSort = -1;
		if(request.getParameter("dSort")!= null){
			dSort = Integer.parseInt(request.getParameter("dSort"));
		}
		
		out.println("<html>");
		out.println("<head><title>添加部门11111</title>");
		out.println("<script language='javascript' src='tool.js'></script>");//
		out.println("</head><body background='bg1.jpg'>");
		
		out.println("<p><h2>添加部门</h2></p>");
		out.println("<form name='theform' action='UserController?flag=addDep' method='post' onsubmit='return checkDep(this);' >");
		out.println("<table>");
		if(errors!=null && errors.containsKey("depName")){
			out.println("<tr><td align='right'>部门名称：</td><td><input type='text' name='depName' value=''/>"+errors.get("depName")+"</td></tr>");
		}else{
		out.println("<tr><td align='right'>部门名称：</td><td><input type='text' name='depName' value=''/></td></tr>");
		}
		if(errors!=null && errors.containsKey("depCreateTime")){
		    out.println("<tr><td align='right'>创建时间：</td><td><input type='text' name='depCreateTime' value=''/>"+errors.get("depCreateTime")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>创建时间：</td><td><input type='text' name='depCreateTime' value=''/></td></tr>");
		}
		if(errors!=null && errors.containsKey("sort")){
		    out.println("<tr><td align='right'>排序权限：</td><td><input type='text' name='sort' value=''/>"+errors.get("sort")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>排序权限：</td><td><input type='text' name='sort' value=''/></td></tr>");
		}
		out.println("<tr><td><input type='hidden' value='"+dSort+"' name='dSort' /></td><td><input type='submit' value='添加'/></td></tr>");
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
