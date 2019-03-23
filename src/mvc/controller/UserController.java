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
		//登录验证
		if("check".equals(flag)){
			check(request, response);
		}
		//遍历user表到main页
		if("selectUser".equals(flag)){
			selectUser(request, response);
		}
		//跳转页面
		if("jumpCheck".equals(flag)){
			jumpCheck(request, response);
		}
		//删除
		if("del".equals(flag)){
			//接受参数
			int id = Integer.parseInt(request.getParameter("id"));
			//调用service进行删除操作
			UserService us = new UserService();
			us.delUser(id);
			//回到原页
			selectUser(request, response);
		}
		//批量删除
		if("delbatch".equals(flag)){
			if(request.getParameterValues("ids")!=null){//判断非空
				//接受参数
				String[] ids = request.getParameterValues("ids");
				//调用service进行删除操作
			    UserService us = new UserService();
			    for (int i = 0; i < ids.length; i++) {
				    int id = Integer.parseInt(ids[i]);
				    us.delUser(id);
			    }
			}
			//回到原页
			selectUser(request, response);	
		}
		//安全退出
		if("exit".equals(flag)){
			HttpSession session = request.getSession();
			session.invalidate();
			//完成后返回登录页
			response.sendRedirect("Index.jsp");
		}
		//添加准备
		if("add".equals(flag)){
			add(request, response);
		}
		//添加
		if("addUser".equals(flag)){
			addUser(request, response);	
		}
		//编辑准备
		if("edit".equals(flag)){
			edit(request, response);
		}
		//编辑
		if("editUser".equals(flag)){
			editUser(request, response);
		}
	
	}


	private void selectUser(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		//接收参数
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
		//调service类拿到指定userlist和deplist
		UserService us = new UserService();
		int pageCount = us.getPageCount(pageSize,sdepId,suserName);
		if(pageNow>pageCount){pageNow = pageCount;}
		if(pageCount==0){pageNow=1;}
		List<User> userList = us.getUserList(pageNow, pageSize, sdepId, suserName);		
		DepService ds = new DepService();
		List<Department> depList = ds.getDepList(-1);
		//封装参数，请求转发	
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
		//接收参数
		int id = Integer.parseInt(request.getParameter("id"));
		int depId = Integer.parseInt(request.getParameter("depId"));
		String userSex = request.getParameter("userSex");
		int userPower = Integer.parseInt(request.getParameter("userPower"));
		
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		String userCode = request.getParameter("userCode");
		String userage = request.getParameter("userAge");
		//调tool检查参数，map接收错误提示
		Map<String, String> map = Tools.checkUser(userName, userPwd, userCode, userage);
		
		if(map.size()==0){//通过检查 封装参数 
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
			//调service编辑
			UserService us = new UserService();
			us.editUser(user);
			//传参回到main页
			request.getRequestDispatcher("UserController?flag=selectUser").forward(request, response);
			/*int sdepId = Integer.parseInt(request.getParameter("sdepId"));
			String suserName = request.getParameter("suserName");*/
			//response.sendRedirect("UserController?flag=list&pageNow="+pageNow);
		}else{//检查不通过，回到原编辑页
			request.setAttribute("map", map);
			edit(request, response);
		}
	}

	private void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接受参数
		int pageNow = Integer.parseInt(request.getParameter("pageNow"));
		int id = Integer.parseInt(request.getParameter("id"));
		//调service准备数据
		UserService us = new UserService();
		User user = us.findUserById(id);
		DepService ds = new DepService();
		List<Department> depList = ds.getDepList(-1);
		//set数据
		request.setAttribute("depList", depList);
		request.setAttribute("user", user);
		int sdepId = Integer.parseInt(request.getParameter("sdepId"));
		String suserName = request.getParameter("suserName");
		request.setAttribute("suserName", suserName);
		request.setAttribute("sdepId", sdepId);
		//转发到EditUser页
		request.getRequestDispatcher("EditUser.jsp?pageNow="+pageNow).forward(request, response);
	}

	private void jumpCheck(HttpServletRequest request,HttpServletResponse response) 
			throws IOException, ServletException {
		//接收参数
		String page = request.getParameter("page");
		int pageCount = Integer.parseInt(request.getParameter("pageCount"));
		//检查跳转页，map集合放错误提示
		
		Map<String, String> map = Tools.checkJump(page, pageCount);
		
		if(map.size()==0){//检查通过进行跳转
			int pagenow = Integer.parseInt(request.getParameter("page"));
			request.getRequestDispatcher("UserController?flag=selectUser&pageNow="+pagenow).forward(request, response);
			//response.sendRedirect("UserController?flag=selectUser&pageNow="+pagenow+"&sdepId="+sdepId+"&suserName="+suserName);
			return;
		}else{
			//不通过回到原页重新判断
			request.setAttribute("map", map);
			selectUser(request, response);
		}
		
	}

	private void addUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		//接收AddUser页传来的参数
		int depId = Integer.parseInt(request.getParameter("depId"));
		String userSex = request.getParameter("userSex");
		int userPower = Integer.parseInt(request.getParameter("userPower"));
		
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		String userCode = request.getParameter("userCode");
		String userage = request.getParameter("userAge");
		//对参数进行验证，map集合放错误提示
		Map<String, String> map = Tools.checkUser(userName, userPwd, userCode, userage);
		
		if(map.size()==0){//通过验证 封装参数回main
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
		}else{//不通过返回原页
			request.setAttribute("map", map);
			add(request, response);
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//调service为AddUser页准备数据
		DepService ds = new DepService();
		List<Department> depList = ds.getDepList(-1);
		request.setAttribute("depList", depList);
		//set当前Main页参数
		int sdepId = Integer.parseInt(request.getParameter("sdepId"));
		String suserName = request.getParameter("suserName");
		request.setAttribute("suserName", suserName);
		request.setAttribute("sdepId", sdepId);
		//请求转发到AddUser页
		request.getRequestDispatcher("AddUser.jsp").forward(request, response);
	}



	private void check(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//接受封装参数
		String name = request.getParameter("username");//request.getParameter("username");
		String pass = request.getParameter("userpass");
		
		User user = new User();
	    user.setUserName(name);
	    user.setUserPwd(pass);
	    //调方法检查
		UserService us = new UserService();
		User u = us.checkUser(user);
		if(u!=null){//验证通过 
			//访问次数
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
			//传登录信息
			HttpSession session = request.getSession();
			session.setAttribute("user", u);
			/*String sex = "女";
			sex = URLEncoder.encode(sex, "UTF-8");*/
			//重定向
			response.sendRedirect("Index.jsp");//UserController?flag=selectUser
		}else{//不通过
			request.setAttribute("msg", "用户名或密码错误！");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
			
		}
	}

	
	@Override
	public void destroy() {
		//拿application对象
		ServletContext application = this.getServletContext();
		//接收访问次数参数
		int visited = Integer.parseInt(application.getAttribute("visited").toString());
		//将访问次数写入数据库
		Tools.writeVisited(visited);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//将访问次数从数据库读出
		int visited = Tools.readVisited();
		//拿application对象
		ServletContext application = this.getServletContext();
		//将数据写入application对象
		application.setAttribute("visited", visited);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
