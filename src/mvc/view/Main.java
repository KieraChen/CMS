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
		
		
		
		//����Ϊmainҳ׼��������
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
		out.println("<head><title>��ҳ11111111</title>");
		out.println("<style type='text/css'>"
				+ "a:hover{color:red;}"
				+ "a{color:#FFF;text-decoration:none;font-weight:bold;}"
				+ ".table a:hover{color:red;}"
				+ ".table a{color:blue;}"
				+ "</style>");
		out.println("<script language='javascript' src='tool.js'>");
		out.println("</script>");
		out.println("</head><body background='bg1.jpg'>");
		out.println("<p><a href='UserController?flag=exit'>��ȫ�˳�</a></p>");
		
		out.println("<center>");
		out.println("<p><h1>Ȩ�޹���ϵͳ</h1><h3>�û�����</h3></p>");
		out.println("<form method='post' action='UserController?flag=selectUser'>");
		out.println("<a href='UserController?flag=add&suserName="+suserName+"&sdepId="+sdepId+"'>����û�</a>");
		if(u.getUserPower()==0){
			out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}else{
			out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href='UserController?flag=depManage'>���Ź���</a>");
		}
		out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp���ţ�<select name='sdepId'>");
		out.println("<option value='-1'>��ѡ��</option>");
		for (Department dep : depList) {
			if(sdepId==dep.getId()){
				out.println("<option value='"+dep.getId()+"' selected='selected'>"+dep.getDepName()+"</option>");
			}else{
				out.println("<option value='"+dep.getId()+"'>"+dep.getDepName()+"</option>");
			}
		}
		out.println("</select></td></tr>");
		out.println("�û�����<input type='text' name='suserName' value='"+suserName+"'/>");
		out.println("<input type='submit' value='����'/>");
		out.println("</form>");
		
		out.println("<form method='post' action='UserController?flag=delbatch&pageNow="+pageNow+"&suserName="+suserName+"&sdepId="+sdepId+"' >");
		out.println("<table width='800' class='table'>");
		out.println("<tr bgcolor='#cccccc'><td><input type='submit' value='����ɾ��'/></td><td>id</td><td>����</td><td>�û���</td><td>����</td>"
				+ "<td>�û����</td><td>�Ա�</td><td>����</td><td>Ȩ��</td><td>����</td></tr>");
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
				out.println( "<td>��ͨ����Ա</td>");
			}else{
			    out.println( "<td>��������Ա</td>");
			}
			out.println( "<td><a href='UserController?flag=del&id="+user.getId()+"&pageNow="+pageNow+"&suserName="+suserName+"&sdepId="+sdepId+"'>��ɾ����</a>"
							+ "<a href='UserController?flag=edit&id="+user.getId()+"&pageNow="+pageNow+"&suserName="+suserName+"&sdepId="+sdepId+"'>���༭��</a></td></tr>");
		}
		out.println("</table>");
		out.println("</form>");
		//������
		out.println("<table>");
		out.println("<tr><td>");
		if(pageNow > 1){
			out.println("<a href=UserController?pageNow=1&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">��ҳ</a>");
			out.println("<a href=UserController?pageNow="+(pageNow-1)+"&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">��һҳ</a>");
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
			out.println("<a href=UserController?pageNow="+(pageNow+1)+"&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">��һҳ</a>");
			out.println("<a href=UserController?pageNow="+pageCount+"&flag=selectUser&suserName="+suserName+"&sdepId="+sdepId+">βҳ</a>");
		}
		//��ת
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
				+ "<input type='submit' value='��ת'/>&nbsp&nbsp��"+pageCount+"ҳ");
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
