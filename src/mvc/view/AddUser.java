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
		//���շ��ʴ���
		ServletContext application = this.getServletContext();
		out.println("���ʴ�����"+application.getAttribute("visited"));
		//���յ�¼����Ϣ
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		if(u==null){
			response.sendRedirect("Login");
			return;
		}
		if(u.getUserPower()==0){
			out.println("��ϲ������ͨ����Ա"+u.getUserName());
		}else{
			out.println("��ϲ������������Ա"+u.getUserName());
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
		out.println("<head><title>����û�111111</title>");
		out.println("<script language='javascript' src='tool.js'></script>");
		out.println("</head><body background='bg1.jpg'>");
		
		out.println("<p><h2>�����Ϣ</h2></p>");
		out.println("<form name='theform' action='UserController?flag=addUser' method='post' onsubmit='return checkUser(this);' >");//
		out.println("<table>");
		if(errors!=null && errors.containsKey("userName")){
			out.println("<tr><td align='right'>�û�����</td><td><input type='text' name='userName' value=''/>"+errors.get("userName")+"</td></tr>");
		}else{
		out.println("<tr><td align='right'>�û�����</td><td><input type='text' name='userName' value=''/></td></tr>");
		}
		if(errors!=null && errors.containsKey("userPwd")){
		    out.println("<tr><td align='right'>���룺</td><td><input type='password' name='userPwd' value=''/>"+errors.get("userPwd")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>���룺</td><td><input type='password' name='userPwd' value=''/></td></tr>");
		}
		out.println("<tr><td align='right'>���ţ�</td><td><select name='depId'>");
		for (Department dep : depList) {
			out.println("<option value='"+dep.getId()+"'>"+dep.getDepName()+"</option>");
		}
		out.println("</select></td></tr>");
		if(errors!=null && errors.containsKey("userCode")){
		    out.println("<tr><td align='right'>�û���ţ�</td><td><input type='text' name='userCode' value=''/>"+errors.get("userCode")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>�û���ţ�</td><td><input type='text' name='userCode' value=''/></td></tr>");
		}
		out.println("<tr><td align='right'>�Ա�</td><td><input type='radio' name='userSex' value='��' id='sex' checked='checked'/>��"
				+ "<input type='radio' name='userSex' value='Ů' id='sex'/>Ů</td></tr>");
		if(errors!=null && errors.containsKey("userAge")){
		    out.println("<tr><td align='right'>���䣺</td><td><input type='text' name='userAge' value=''/>"+errors.get("userAge")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>���䣺</td><td><input type='text' name='userAge' value=''/></td></tr>");
		}
		
		out.println("<tr><td align='right'>Ȩ�ޣ�</td><td><select name='userPower'>"
				+ "<option value='0'>��ͨ����Ա</option><option value='1'>��������Ա</option></select></td></tr>");
		out.println("<tr><td colspan='2'><input type='hidden' name='flag' value='addcheck'/></td></tr>");
		out.println("<tr><td><input type='hidden' name='suserName' value='"+suserName+"'/>"
				+ "<input type='hidden' name='sdepId' value='"+sdepId+"'/></td>"
						+ "<td><input type='submit' value='���'/></td></tr>");
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
