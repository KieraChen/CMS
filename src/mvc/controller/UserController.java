package mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.domain.Department;
import mvc.domain.User;
import mvc.service.DepService;
import mvc.service.UserService;
import mvc.util.Tools;


@WebServlet("/UserController")
public class UserController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		String flag = "";
		flag = request.getParameter("flag");
		if("CheckName".equals(flag)){
			int id = -1;
			if(request.getParameter("id")!= null){
				id = Integer.parseInt(request.getParameter("id"));
			}
			String userName = request.getParameter("userName");
			UserService us = new UserService();
			
			if(us.findUserByName(userName,id)){
						out.print("exist");
			}

		}
		//��¼��֤
		if("check".equals(flag)){
			check(request, response);
		}
		//����user��mainҳ
		if("selectUser".equals(flag)){
			selectUser(request, response);
		}
		//��תҳ��
		if("jumpCheck".equals(flag)){
			jumpCheck(request, response);
		}
		//ɾ��
		if("del".equals(flag)){
			//���ܲ���
			int id = Integer.parseInt(request.getParameter("id"));
			//����service����ɾ������
			UserService us = new UserService();
			us.delUser(id);
			//�ص�ԭҳ
			selectUser(request, response);
		}
		//����ɾ��
		if("delbatch".equals(flag)){
			if(request.getParameterValues("ids")!=null){//�жϷǿ�
				//���ܲ���
				String[] ids = request.getParameterValues("ids");
				//����service����ɾ������
			    UserService us = new UserService();
			    for (int i = 0; i < ids.length; i++) {
				    int id = Integer.parseInt(ids[i]);
				    us.delUser(id);
			    }
			}
			//�ص�ԭҳ
			selectUser(request, response);	
		}
		//��ȫ�˳�
		if("exit".equals(flag)){
			HttpSession session = request.getSession();
			session.invalidate();
			//��ɺ󷵻ص�¼ҳ
			response.sendRedirect("Index.jsp");
		}
		//���׼��
		if("add".equals(flag)){
			add(request, response);
		}
		//���
		if("addUser".equals(flag)){
			addUser(request, response);	
		}
		//�༭׼��
		if("edit".equals(flag)){
			edit(request, response);
		}
		//�༭
		if("editUser".equals(flag)){
			editUser(request, response);
		}
	
	}


	private void selectUser(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		//���ղ���
		int sdepId = -1;
		if(request.getParameter("sdepId")!= null){
			sdepId = Integer.parseInt(request.getParameter("sdepId"));
		}
		String suserName = "";
		if(request.getParameter("suserName")!=null){
			suserName = request.getParameter("suserName");
		}
		
		int pageSize = 10;
		int pageNow = 1;
		if(request.getParameter("pageNow")!=null){
			pageNow = Integer.parseInt(request.getParameter("pageNow"));
		}
		//��service���õ�ָ��userlist��deplist
		UserService us = new UserService();
		int pageCount = us.getPageCount(pageSize,sdepId,suserName);
		if(pageNow>pageCount){pageNow = pageCount;}
		if(pageCount==0){pageNow=1;}
		List<User> userList = us.getUserList(pageNow, pageSize, sdepId, suserName);		
		DepService ds = new DepService();
		List<Department> depList = ds.getDepList(-1);
		//��װ����������ת��	
		request.setAttribute("pageNow", pageNow);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("userList", userList);
		request.setAttribute("depList", depList);
		request.setAttribute("sdepId", sdepId);
		request.setAttribute("suserName", suserName);
		request.getRequestDispatcher("UserMain.jsp").forward(request, response);
	}

	private void editUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		//int pageNow = Integer.parseInt(request.getParameter("pageNow"));
		//���ղ���
		int id = Integer.parseInt(request.getParameter("id"));
		int depId = Integer.parseInt(request.getParameter("depId"));
		String userSex = request.getParameter("userSex");
		int userPower = Integer.parseInt(request.getParameter("userPower"));
		
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		String userCode = request.getParameter("userCode");
		String userage = request.getParameter("userAge");
		//��tool��������map���մ�����ʾ
		Map<String, String> map = Tools.checkUser(userName, userPwd, userCode, userage);
		
		if(map.size()==0){//ͨ����� ��װ���� 
			int userAge = Integer.parseInt(request.getParameter("userAge"));
			User user = new User();
			user.setUserName(userName);
			user.setUserPwd(userPwd);
			user.setDepId(depId);
			user.setUserCode(userCode);
			user.setUserSex(userSex);
			user.setUserAge(userAge);
			user.setUserPower(userPower);
			user.setId(id);
			//��service�༭
			UserService us = new UserService();
			us.editUser(user);
			//���λص�mainҳ
			request.getRequestDispatcher("UserController?flag=selectUser").forward(request, response);
			/*int sdepId = Integer.parseInt(request.getParameter("sdepId"));
			String suserName = request.getParameter("suserName");*/
			//response.sendRedirect("UserController?flag=list&pageNow="+pageNow);
		}else{//��鲻ͨ�����ص�ԭ�༭ҳ
			request.setAttribute("map", map);
			edit(request, response);
		}
	}

	private void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//���ܲ���
		int pageNow = Integer.parseInt(request.getParameter("pageNow"));
		int id = Integer.parseInt(request.getParameter("id"));
		//��service׼������
		UserService us = new UserService();
		User user = us.findUserById(id);
		DepService ds = new DepService();
		List<Department> depList = ds.getDepList(-1);
		//set����
		request.setAttribute("depList", depList);
		request.setAttribute("user", user);
		int sdepId = Integer.parseInt(request.getParameter("sdepId"));
		String suserName = request.getParameter("suserName");
		request.setAttribute("suserName", suserName);
		request.setAttribute("sdepId", sdepId);
		//ת����EditUserҳ
		request.getRequestDispatcher("EditUser.jsp?pageNow="+pageNow).forward(request, response);
	}

	private void jumpCheck(HttpServletRequest request,HttpServletResponse response) 
			throws IOException, ServletException {
		//���ղ���
		String page = request.getParameter("page");
		int pageCount = Integer.parseInt(request.getParameter("pageCount"));
		//�����תҳ��map���ϷŴ�����ʾ
		
		Map<String, String> map = Tools.checkJump(page, pageCount);
		
		if(map.size()==0){//���ͨ��������ת
			int pagenow = Integer.parseInt(request.getParameter("page"));
			request.getRequestDispatcher("UserController?flag=selectUser&pageNow="+pagenow).forward(request, response);
			//response.sendRedirect("UserController?flag=selectUser&pageNow="+pagenow+"&sdepId="+sdepId+"&suserName="+suserName);
			return;
		}else{
			//��ͨ���ص�ԭҳ�����ж�
			request.setAttribute("map", map);
			selectUser(request, response);
		}
		
	}

	private void addUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		//����AddUserҳ�����Ĳ���
		int depId = Integer.parseInt(request.getParameter("depId"));
		String userSex = request.getParameter("userSex");
		int userPower = Integer.parseInt(request.getParameter("userPower"));
		
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		String userCode = request.getParameter("userCode");
		String userage = request.getParameter("userAge");
		//�Բ���������֤��map���ϷŴ�����ʾ
		Map<String, String> map = Tools.checkUser(userName, userPwd, userCode, userage);
		
		if(map.size()==0){//ͨ����֤ ��װ������main
			int userAge = Integer.parseInt(request.getParameter("userAge"));
			User user = new User();
			user.setUserName(userName);
			user.setUserPwd(userPwd);
			user.setDepId(depId);
			user.setUserCode(userCode);
			user.setUserSex(userSex);
			user.setUserAge(userAge);
			user.setUserPower(userPower);
			UserService us = new UserService();
			us.addUser(user);
			
			request.getRequestDispatcher("UserController?flag=selectUser").forward(request, response);
			/*int sdepId = Integer.parseInt(request.getParameter("sdepId"));
			String suserName = request.getParameter("suserName");*/
			//response.sendRedirect("UserController?flag=selectUser&suserName="+suserName+"&sdepId="+sdepId);
		}else{//��ͨ������ԭҳ
			request.setAttribute("map", map);
			add(request, response);
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��serviceΪAddUserҳ׼������
		DepService ds = new DepService();
		List<Department> depList = ds.getDepList(-1);
		request.setAttribute("depList", depList);
		//set��ǰMainҳ����
		int sdepId = Integer.parseInt(request.getParameter("sdepId"));
		String suserName = request.getParameter("suserName");
		request.setAttribute("suserName", suserName);
		request.setAttribute("sdepId", sdepId);
		//����ת����AddUserҳ
		request.getRequestDispatcher("AddUser.jsp").forward(request, response);
	}



	private void check(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//���ܷ�װ����
		String name = request.getParameter("username");//request.getParameter("username");
		String pass = request.getParameter("userpass");
		
		User user = new User();
	    user.setUserName(name);
	    user.setUserPwd(pass);
	    //���������
		UserService us = new UserService();
		User u = us.checkUser(user);
		if(u!=null){//��֤ͨ�� 
			//���ʴ���
			ServletContext application = this.getServletContext();
			if(application.getAttribute("visited")==null){
				application.setAttribute("visited", 1);
			}else{
				int visited = Integer.parseInt(application.getAttribute("visited").toString());
				visited++;
				application.setAttribute("visited", visited);
			}
			
			//Cookie
			if("1".equals(request.getParameter("save"))){
				name = URLEncoder.encode(request.getParameter("username"), "UTF-8");
				pass = URLEncoder.encode(request.getParameter("userpass"), "UTF-8");
				Cookie cookie1 = new Cookie("c_name",name);
				Cookie cookie2 = new Cookie("c_pass",pass);
				cookie1.setMaxAge(60*60*24*7);
				cookie2.setMaxAge(60*60*24*7);
				response.addCookie(cookie1);
				response.addCookie(cookie2);
			}
			//����¼��Ϣ
			HttpSession session = request.getSession();
			session.setAttribute("user", u);
			/*String sex = "Ů";
			sex = URLEncoder.encode(sex, "UTF-8");*/
			//�ض���
			response.sendRedirect("Index.jsp");//UserController?flag=selectUser
		}else{//��ͨ��
			request.setAttribute("msg", "�û������������");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
			
		}
	}

	
	@Override
	public void destroy() {
		//��application����
		ServletContext application = this.getServletContext();
		//���շ��ʴ�������
		int visited = Integer.parseInt(application.getAttribute("visited").toString());
		//�����ʴ���д�����ݿ�
		Tools.writeVisited(visited);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//�����ʴ��������ݿ����
		int visited = Tools.readVisited();
		//��application����
		ServletContext application = this.getServletContext();
		//������д��application����
		application.setAttribute("visited", visited);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
