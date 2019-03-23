package mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.domain.Department;
import mvc.domain.News;
import mvc.domain.NewsType;
import mvc.domain.User;
import mvc.service.DepService;
import mvc.service.NewsService;
import mvc.service.NewsTypeService;
import mvc.service.UserService;
import mvc.util.Tools;



@WebServlet("/NewsController")
public class NewsController extends HttpServlet {
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		String nFlag = "";
		nFlag = request.getParameter("nFlag");
		if("CheckName".equals(nFlag)){
			int id = -1;
			if(request.getParameter("id")!= null){
				id = Integer.parseInt(request.getParameter("id"));
			}
			String title = request.getParameter("title");
			NewsService ns = new NewsService();

			if(ns.findNewsByTitle(title, id)){
						out.print("exist");
			}
		}
		if("selectNews".equals(nFlag)){
			selectNews(request, response);
		}
		if("jumpCheck".equals(nFlag)){
			jumpCheck(request, response);
		}
		if("del".equals(nFlag)){
			//接受参数
			int id = Integer.parseInt(request.getParameter("id"));
			//调用service进行删除操作
			NewsService ns = new NewsService();
			ns.deleteNews(id);
			//回到原页
			selectNews(request, response);
		}
		if("delbatch".equals(nFlag)){
			if(request.getParameterValues("ids")!=null){//判断非空
				//接受参数
				String[] ids = request.getParameterValues("ids");
				//调用service进行删除操作
				NewsService ns = new NewsService();
			    for (int i = 0; i < ids.length; i++) {
				    int id = Integer.parseInt(ids[i]);
				    ns.deleteNews(id);
			    }
			}
			//回到原页
			selectNews(request, response);	
		}
		if("add".equals(nFlag)){
			add(request, response);
		}
		if("addNews".equals(nFlag)){
			addNews(request, response);
		
		}
		if("edit".equals(nFlag)){
			edit(request, response);
		}
		if("editNews".equals(nFlag)){
			//接收参数
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String createTime = request.getParameter("createtime");
			String content = request.getParameter("content");
			int typeid = Integer.parseInt(request.getParameter("typeid"));
			int flag = 0;
			if(request.getParameter("flag")!= null){
				flag = Integer.parseInt(request.getParameter("flag"));
			}
			//调tool检查参数，map接收错误提示
			Map<String, String> map = Tools.checkNews(title, createTime, content);
			
			if(map.size()==0){//通过检查 封装参数 
				Date time = Tools.stringToDate(createTime);
				java.sql.Date createtime =new java.sql.Date(time.getTime());
				News news = new News();
				news.setId(id);
				news.setTitle(title);
				news.setContent(content);
				news.setCreatetime(createtime);
				news.setFlag(flag);
				news.setTypeid(typeid);
				//调service编辑
				NewsService ns = new NewsService();
				ns.editNews(news);
				//传参回到main页
				request.getRequestDispatcher("NewsController?nFlag=selectNews").forward(request, response);
			}else{//检查不通过，回到原编辑页
				request.setAttribute("map", map);
				edit(request, response);
			}
		
		}
		
	}


	private void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接受参数
		int pageNow = Integer.parseInt(request.getParameter("pageNow"));
		int id = Integer.parseInt(request.getParameter("id"));
		//调service准备数据
		NewsService ns = new NewsService();
		News news = ns.findNewsById(id);
		NewsTypeService nts = new NewsTypeService();
		List<NewsType> typeList = nts.getTypeList(-1);
		//set数据
		request.setAttribute("typeList", typeList);
		request.setAttribute("news", news);
		int nflag = Integer.parseInt(request.getParameter("nflag"));
		int ntypeid = Integer.parseInt(request.getParameter("ntypeid"));
		request.setAttribute("nflag", nflag);
		request.setAttribute("ntypeid", ntypeid);
		//转发到EditUser页
		request.getRequestDispatcher("EditNews.jsp?pageNow="+pageNow).forward(request, response);
	}


	private void addNews(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//接收AddNews页传来的参数
		String title = request.getParameter("title");
		String createTime = request.getParameter("createtime");
		String content = request.getParameter("content");
		int typeid = Integer.parseInt(request.getParameter("typeid"));
		int flag = Integer.parseInt(request.getParameter("flag"));
		int uid = Integer.parseInt(request.getParameter("uid"));
		//对参数进行验证，map集合放错误提示
		Map<String, String> map = Tools.checkNews(title, createTime, content);
		
		if(map.size()==0){//通过验证 封装参数回main
			Date time = Tools.stringToDate(createTime);
			java.sql.Date createtime =new java.sql.Date(time.getTime());
			News news = new News();
			news.setTitle(title);
			news.setContent(content);
			news.setCreatetime(createtime);
			news.setFlag(flag);
			news.setTypeid(typeid);
			news.setUid(uid);
			NewsService ns = new NewsService();
			ns.addNews(news);
			request.getRequestDispatcher("NewsController?nFlag=selectNews").forward(request, response);
			return;
		}else{//不通过返回原页
			request.setAttribute("map", map);
			add(request, response);
		}
	}


	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//调service为AddUser页准备数据
		NewsTypeService nts = new NewsTypeService();
		List<NewsType> typeList = nts.getTypeList(-1);
		request.setAttribute("typeList", typeList);
		//set当前Main页参数
		int ntypeid = Integer.parseInt(request.getParameter("ntypeid"));
		int nflag = Integer.parseInt(request.getParameter("nflag"));
		request.setAttribute("nflag", nflag);
		request.setAttribute("ntypeid", ntypeid);
		//请求转发到AddUser页
		request.getRequestDispatcher("AddNews.jsp").forward(request, response);
	}


	private void jumpCheck(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//接收参数
		String page = request.getParameter("page");
		int pageCount = Integer.parseInt(request.getParameter("pageCount"));
		//检查跳转页，map集合放错误提示
		
		Map<String, String> map = Tools.checkJump(page, pageCount);
		
		if(map.size()==0){//检查通过进行跳转
			int pagenow = Integer.parseInt(request.getParameter("page"));
			request.getRequestDispatcher("NewsController?nFlag=selectNews&pageNow="+pagenow).forward(request, response);
			return;
		}else{
			//不通过回到原页重新判断
			request.setAttribute("map", map);
			selectNews(request, response);
		}
	}


	private void selectNews(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//接收参数
		int ntypeid = -1;
		if(request.getParameter("ntypeid")!= null){
			ntypeid = Integer.parseInt(request.getParameter("ntypeid"));
		}
		int nflag = 0;
		int uid = -1;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user.getUserPower()==0){
			uid = user.getId();
		}
		if(request.getParameter("nflag")!= null){
			nflag = Integer.parseInt(request.getParameter("nflag"));
		}
		
		int pageSize = 10;
		int pageNow = 1;
		if(request.getParameter("pageNow")!=null){
			pageNow = Integer.parseInt(request.getParameter("pageNow"));
		}
		//调service类拿到指定newslist和typelist
		NewsService ns = new NewsService();
		int pageCount = ns.getPageCount(pageSize, ntypeid, nflag,uid);
		if(pageNow>pageCount){pageNow = pageCount;}
		if(pageCount==0){pageNow=1;}
		List<News> newsList = ns.getNewsList(pageNow, pageSize, ntypeid, nflag,uid);		
		NewsTypeService nts = new NewsTypeService();
		List<NewsType> typeList = nts.getTypeList(-1);
		//封装参数，请求转发	
		request.setAttribute("pageNow", pageNow);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("newsList", newsList);
		request.setAttribute("typeList", typeList);
		request.setAttribute("ntypeid", ntypeid);
		request.setAttribute("nflag", nflag);
		request.getRequestDispatcher("NewsMain.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
