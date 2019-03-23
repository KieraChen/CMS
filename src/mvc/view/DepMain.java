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


@WebServlet("/DepMain")
public class DepMain extends HttpServlet {
	
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
	/*	int pageNow = Integer.parseInt(request.getAttribute("pageNow").toString());
		int pageCount = Integer.parseInt(request.getAttribute("pageCount").toString());
		List<User> userList = (List<User>)request.getAttribute("userList");*/
		List<Department> depList= (List<Department>) request.getAttribute("depList");
	/*	int sdepId = -1;
		String suserName ="";
		if(request.getAttribute("sdepId")!=null){
			sdepId =(int) request.getAttribute("sdepId");
		}
		if(request.getAttribute("suserName")!=null){
			suserName = (String) request.getAttribute("suserName");
		} 
		*/
		int dSort = -1;
		if(request.getAttribute("dSort")!=null){
			dSort =(int) request.getAttribute("dSort");
		}
		
		
		out.println("<html>");
		out.println("<head><title>������ҳ</title>");
		out.println("<style type='text/css'>"
				+ "a:hover{color:red;}"
				+ "a{color:#FFF;text-decoration:none;font-weight:bold;}"
				+ ".table a:hover{color:red;}"
				+ ".table a{color:blue;}"
				+ "</style>");
		out.println("<script language='javascript'>");////// src='tool.js'
		out.println("</script>");
		out.println("</head><body background='bg1.jpg'>");
		out.println("<p><a href='UserController?flag=exit'>��ȫ�˳�</a></p>");
		
		out.println("<center>");
		out.println("<p><h1>Ȩ�޹���ϵͳ</h1><h3>���Ź���111111</h3></p>");
		out.println("<form method='post' action='UserController?flag=depManage'>");///////////��ѯform
		out.println("<a href='AddDep?dSort="+dSort+"'>��Ӳ���</a>");/////////////////
		out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href='UserController?flag=goBack'>�����û�����</a>");/////////
		out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<select name='dSort'>");
		if(dSort == -1){
			out.println("<option value='-1' selected='selected'>���</option>");
		}else{
			out.println("<option value='-1'>���</option>");
		}
		if(dSort == 1){
			out.println("<option value='1' selected='selected'>����</option>");
		}else{
			out.println("<option value='1'>����</option>");
		}
		out.println("</select>");//</td></tr>
		out.println("<input type='submit' value='����'/>");
		out.println("</form>");//////////////////////��ѯ����!!
		
		out.println("<form method='post' action='UserController?flag=delbatchDep' >");/////////����ɾ��flag
		out.println("<table width='800' class='table'>");
		out.println("<tr bgcolor='#cccccc'><td><input type='hidden' value='"+dSort+"' name='dSort' /><input type='submit' value='����ɾ��'/></td><td>id</td><td>��������</td><td>����ʱ��</td><td>����Ȩ��</td>"
				+ "<td>����</td></tr>");
		for (Department dep : depList) {
			out.println("<tr bgcolor='#F2F2F2'><td><input type='checkbox' name='ids' value='"+dep.getId()+"' /></td><td>"+dep.getId()+"</td>");
			out.println("<td>"+dep.getDepName()+"</td><td>"+dep.getDepCreateTime()+"</td>"
					+ "<td>"+dep.getSort()+"</td>");
			out.println( "<td><a href='UserController?flag=delDep&id="+dep.getId()+"&dSort="+dSort+"'>��ɾ����</a>"/////////flag
							+ "<a href='UserController?flag=editD&id="+dep.getId()+"&dSort="+dSort+"'>���༭��</a></td></tr>");/////////flag
		}
		out.println("</table>");
		out.println("</form>");
		/*//������
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
		out.println("</table>");*/
		
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
