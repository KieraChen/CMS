package mvc.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Login")
public class Login extends HttpServlet {

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		
		
		Cookie[] cookies = request.getCookies();
		String name = "";
		String pass = "";
		if(cookies != null){
		  for(Cookie c : cookies){
			if("c_name".equals(c.getName())){
				name = c.getValue();
			}
			if("c_pass".equals(c.getName())){
				pass = c.getValue();
			}
			
		  }
		}
		
		out.println("<html><head><title>登录页面11111111</title>"
				+ "<link href='css/NewFile.css' rel='stylesheet' type='text/css' />"
				+ "</head>");
		out.println("<body background='bg1.jpg'><h1>登录页面</h1><div class='login-form'>");
		out.println("<div class='head-info'>");
		if(request.getAttribute("msg")!= null){
			out.println(request.getAttribute("msg"));
		}
		out.println("<label class='lbl-1'> </label><label class='lbl-2'> </label><label class='lbl-3'> </label></div>");
		out.println("<div class='clear'> </div>");
		out.println("<div class='avtar'><img src='avtar.png' /></div>");
		out.println("<form action='UserController' method='post'><span style='font-weight:bold; color:#CCC'>用户名</span><input type='text' name='username' value='"+name+"'/>"
				+ "<div class='key'><span style='font-weight:bold; color:#CCC'>密码</span><input type='password' name='userpass' value='"+pass+"'/></div>"
				+ "<div class='save' style='margin-bottom:20px'><input type='checkbox' name='save' value='1'/><span style='font-weight:bold; color:#CCC;'>信息保存一周</span>"
				+ "<input type='hidden' name='flag' value='check'/></div>");
		out.println("<div class='signin'><input type='submit' value='Login' ></div></div><p>&nbsp;</p>");
				 
        out.println("</form>");
		out.println("</body>");
		out.println("</html>");
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
