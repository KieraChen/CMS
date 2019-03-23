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

import mvc.domain.Department;
import mvc.domain.NewsType;
import mvc.service.DepService;
import mvc.service.NewsService;
import mvc.service.NewsTypeService;
import mvc.service.UserService;
import mvc.util.Tools;

@WebServlet("/NewsTypeController")
public class NewsTypeController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			String typeName = request.getParameter("typeName");
			NewsTypeService nts = new NewsTypeService();

			if(nts.findTypeByName(typeName, id)){
						out.print("exist");
			}
		}
		if("typeManage".equals(nFlag)){
			typeManage(request, response);
		}
		if("delType".equals(nFlag)){
			//接受参数
			int id = Integer.parseInt(request.getParameter("id"));
			//调用service进行删除操作
			NewsService ns = new NewsService();
			ns.deleteNewsByTypeId(id);
			NewsTypeService nts = new NewsTypeService();
			nts.deleteType(id);
			//回到原页
			typeManage(request, response);
		}
		if("delbatchType".equals(nFlag)){
			if(request.getParameterValues("ids")!=null){//判断非空
				//接受参数
				String[] ids = request.getParameterValues("ids");
				//调用service进行删除操作
				NewsService ns = new NewsService();
				for (int i = 0; i < ids.length; i++) {
					    int id = Integer.parseInt(ids[i]);
					    ns.deleteNewsByTypeId(id);
				}
				NewsTypeService nts = new NewsTypeService();
			    for (int i = 0; i < ids.length; i++) {
				    int id = Integer.parseInt(ids[i]);
				    nts.deleteType(id);
			    }
			}
			//回到原页
			typeManage(request, response);	
		}
		if("addType".equals(nFlag)){
			//接收AddDep页传来的参数
			int nSort = -1;
			if(request.getParameter("nSort")!= null){
				nSort = Integer.parseInt(request.getParameter("nSort"));
			}
			String typeName = request.getParameter("typeName");
			String Sort = request.getParameter("sort");
			//对参数进行验证，map集合放错误提示
			Map<String, String> map = Tools.checkType(typeName, Sort);
			
			if(map.size()==0){//通过验证 封装参数回main
				int sort = -1;
				if(Sort!=""){
					sort = Integer.parseInt(request.getParameter("sort"));
				}
				NewsType newsType = new NewsType();
				newsType.setSort(sort);
				newsType.setTypeName(typeName);
				
				NewsTypeService nts = new NewsTypeService();
				nts.addType(newsType);
				
				typeManage(request, response);
				//request.getRequestDispatcher("UserController?flag=depManage&dSort="+dSort).forward(request, response);
				//return;
			}else{//不通过返回原页
				request.setAttribute("map", map);
				request.getRequestDispatcher("AddNewsType.jsp?nSort="+nSort).forward(request, response);
				return;
			}
		
		}
		if("editT".equals(nFlag)){
			editT(request, response);
		}
		if("editType".equals(nFlag)){
			//接收EditDep页传来的参数
			int id = Integer.parseInt(request.getParameter("id"));
			String typeName = request.getParameter("typeName");
			String Sort = request.getParameter("sort");
			//对参数进行验证，map集合放错误提示
			Map<String, String> map = Tools.checkType(typeName, Sort);
			
			if(map.size()==0){//通过验证 封装参数回main
				int sort = -1;
				if(Sort!=""){
					sort = Integer.parseInt(request.getParameter("sort"));
				}
				NewsType newsType = new NewsType();
				newsType.setSort(sort);
				newsType.setTypeName(typeName);
				newsType.setId(id);
				NewsTypeService nts = new NewsTypeService();
				nts.editType(newsType);
					
				request.getRequestDispatcher("NewsTypeController?nFlag=typeManage").forward(request, response);
				return;
			}else{//不通过返回原页
				request.setAttribute("map", map);
				editT(request, response);
			}
		
		}
	}


	private void editT(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接受参数
		int nSort = -1;
		if(request.getParameter("nSort")!= null){
			nSort = Integer.parseInt(request.getParameter("nSort"));
		}
		int id = Integer.parseInt(request.getParameter("id"));
		//调service准备数据
		NewsTypeService nts = new NewsTypeService();
		NewsType type = nts.findTypeById(id);
		//set数据
		request.setAttribute("type", type);
		request.setAttribute("nSort", nSort);
		//转发到EditDep页
		request.getRequestDispatcher("EditNewsType.jsp").forward(request, response);
	}


	private void typeManage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int nSort = -1;
		if(request.getParameter("nSort")!= null){
			nSort = Integer.parseInt(request.getParameter("nSort"));
		}
		NewsTypeService nts = new NewsTypeService();
		List<NewsType> typeList = nts.getTypeList(nSort);
		request.setAttribute("typeList", typeList);
		request.setAttribute("nSort", nSort);
		request.getRequestDispatcher("NewsTypeMain.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
