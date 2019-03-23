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


@WebServlet("/EditUser")
public class EditUser extends HttpServlet {
	
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
		
		int pageNow = Integer.parseInt(request.getParameter("pageNow"));
		User user = (User) request.getAttribute("user");
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
		out.println("<head><title>�༭�û�11111</title>");
		out.println( "<script language='javascript' src='tool.js'></script>");
		out.println("</head><body background='bg1.jpg'>");
		
		out.println("<p><h2>�༭��Ϣ</h2></p>");
		out.println("<form name='theform' action='UserController?flag=editUser' method='post' onsubmit='return checkUser(this);' >");
		out.println("<table>");
		if(errors!=null && errors.containsKey("userName")){
			out.println("<tr><td align='right'>�û�����</td><td><input type='text' name='userName' value='"+user.getUserName()+"'/>"+errors.get("userName")+"</td></tr>");
		}else{
		out.println("<tr><td align='right'>�û�����</td><td><input type='text' name='userName' value='"+user.getUserName()+"'/></td></tr>");
		}
		if(errors!=null && errors.containsKey("userPwd")){
		    out.println("<tr><td align='right'>���룺</td><td><input type='password' name='userPwd' value='"+user.getUserPwd()+"'/>"+errors.get("userPwd")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>���룺</td><td><input type='password' name='userPwd' value='"+user.getUserPwd()+"'/></td></tr>");
		}
		out.println("<tr><td align='right'>���ţ�</td><td><select name='depId'>");
		for (Department dep : depList) {
			if(user.getDepId()==dep.getId()){
				out.println("<option value='"+dep.getId()+"' selected='selected'>"+dep.getDepName()+"</option>");
			}else{
				out.println("<option value='"+dep.getId()+"'>"+dep.getDepName()+"</option>");
			}
			
		}
		out.println("</select></td></tr>");
		if(errors!=null && errors.containsKey("userCode")){
		    out.println("<tr><td align='right'>�û���ţ�</td><td><input type='text' name='userCode' value='"+user.getUserCode()+"'/>"+errors.get("userCode")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>�û���ţ�</td><td><input type='text' name='userCode' value='"+user.getUserCode()+"'/></td></tr>");
		}
		out.println("<tr><td align='right'>�Ա�</td><td>");
		if("��".equals(user.getUserSex())){
			out.println( "<input type='radio' name='userSex' value='��' id='sex' checked='checked'/>��");
		}else{
			out.println( "<input type='radio' name='userSex' value='��' id='sex'/>��");
		}
		if("Ů".equals(user.getUserSex())){
			out.println( "<input type='radio' name='userSex' value='Ů' id='sex' checked='checked'/>Ů");
		}else{
			out.println( "<input type='radio' name='userSex' value='Ů' id='sex'/>Ů");
		}
		out.println( "</td></tr>");
		if(errors!=null && errors.containsKey("userAge")){
		    out.println("<tr><td align='right'>���䣺</td><td><input type='text' name='userAge' value='"+user.getUserAge()+"'/>"+errors.get("userAge")+"</td></tr>");
		}else{
			out.println("<tr><td align='right'>���䣺</td><td><input type='text' name='userAge' value='"+user.getUserAge()+"'/></td></tr>");
		}
		
		out.println("<tr><td align='right'>Ȩ�ޣ�</td><td><select name='userPower'>");
		if(u.getUserPower()==0){
			if(user.getUserPower()==0){
				out.println( "<option value='0' selected='selected'>��ͨ����Ա</option>");
			}else{
				out.println( "<option value='1' selected='selected'>��������Ա</option>");
			}
		}else{
			if(user.getUserPower()==0){
				out.println( "<option value='0' selected='selected'>��ͨ����Ա</option>");
			}else{
				out.println( "<option value='0'>��ͨ����Ա</option>");
			}
			if(user.getUserPower()==1){
				out.println( "<option value='1' selected='selected'>��������Ա</option>");
			}else{
				out.println( "<option value='1'>��������Ա</option>");
			}
		}
		out.println( "</select></td></tr>");
		out.println("<tr><td colspan='2'><input type='hidden' name='pageNow' value='"+pageNow+"'/>"
				+ "<input type='hidden' name='id' value='"+user.getId()+"'/>"
				+ "<input type='hidden' name='suserName' value='"+suserName+"'/>"
				+ "<input type='hidden' name='sdepId' value='"+sdepId+"'/></td></tr>");
		out.println("<tr><td></td><td><input type='submit' value='�޸�'/></td></tr>");
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
