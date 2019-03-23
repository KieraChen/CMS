package mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.domain.News;
import mvc.domain.NewsType;
import mvc.service.NewsService;
import mvc.service.NewsTypeService;
import mvc.util.Tools;


@WebServlet("/MainController")
public class MainController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		String nFlag = "";
		nFlag = request.getParameter("nFlag");
		if("third".equals(nFlag)){
			int id = Integer.parseInt(request.getParameter("id"));
			NewsService ns = new NewsService();
			News news = ns.findNewsById(id);
			request.setAttribute("news", news);
			NewsTypeService nts = new NewsTypeService();
			List<NewsType> typeList = nts.getTypeList(1);
			request.setAttribute("typeList", typeList);
			request.getRequestDispatcher("Third.jsp").forward(request, response);
			
		}
		if("jumpCheck".equals(nFlag)){
			//接收参数
			String page = request.getParameter("page");
			int pageCount = Integer.parseInt(request.getParameter("pageCount"));
			//检查跳转页，map集合放错误提示
			
			Map<String, String> map = Tools.checkJump(page, pageCount);
			
			if(map.size()==0){//检查通过进行跳转
				int pagenow = Integer.parseInt(request.getParameter("page"));
				request.getRequestDispatcher("MainController?nFlag=second&pageNow="+pagenow).forward(request, response);
				return;
			}else{
				//不通过回到原页重新判断
				request.setAttribute("map", map);
				second(request, response);
			}
		
		}
		if("second".equals(nFlag)){
			second(request, response);
		}
		if("first".equals(nFlag)){
			NewsTypeService nts = new NewsTypeService();
			List<NewsType> typeList = nts.getTypeList(1);
			request.setAttribute("typeList", typeList);
			NewsService ns = new NewsService();
			/*Map<Long, List<News>> map = new HashMap<Long, List<News>>();;
			for (NewsType newsType : typeList) {
				List<News> newsList = ns.getNewsList(1, 6, newsType.getId(), 1);
				map.put((long) newsType.getId(), newsList);
			}
			request.setAttribute("map", map);*/
			List list = new ArrayList();
			for (NewsType newsType : typeList) {
				List<News> newsList = ns.getNewsList(1, 6, newsType.getId(), 1,-1);
				list.add(newsList);
			}
			request.setAttribute("list", list);
			request.getRequestDispatcher("Main.jsp").forward(request, response);
		}
		
	}


	private void second(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNow = 1;
		if(request.getParameter("pageNow")!=null){
			pageNow = Integer.parseInt(request.getParameter("pageNow"));
		}
		int id = Integer.parseInt(request.getParameter("id"));
		NewsTypeService nts = new NewsTypeService();
		List<NewsType> typeList = nts.getTypeList(1);
		
		NewsService ns = new NewsService();
		int pageCount = ns.getPageCount(10, id, 1,-1);
		if(pageNow>pageCount){pageNow = pageCount;}
		if(pageCount==0){pageNow=1;}
		List<News> newsList = ns.getNewsList(pageNow, 10, id, 1,-1);
		
		request.setAttribute("pageNow", pageNow);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("typeList", typeList);
		request.setAttribute("newsList", newsList);
		request.setAttribute("id", id);
		request.getRequestDispatcher("Second.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
