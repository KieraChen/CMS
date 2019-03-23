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
import mvc.service.DepService;
import mvc.service.UserService;
import mvc.util.Tools;


@WebServlet("/DepController")
public class DepController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			String depName = request.getParameter("depName");
			DepService ds = new DepService();
			
			if(ds.findDepByName(depName, id)){
						out.print("exist");
			}

		}
		if("editD".equals(flag)){
			editD(request, response);
			//return;
		}
		if("editDep".equals(flag)){
			//接收EditDep页传来的参数
			int id = Integer.parseInt(request.getParameter("id"));
			String depName = request.getParameter("depName");
			String date = request.getParameter("depCreateTime");
			String Sort = request.getParameter("sort");
			//对参数进行验证，map集合放错误提示
			Map<String, String> map = Tools.checkDep(depName,date,Sort);
			
			if(map.size()==0){//通过验证 封装参数回main
				int sort = -1;
				if(Sort!=""){
					sort = Integer.parseInt(request.getParameter("sort"));
				}
				Date depcreateTime = Tools.stringToDate(date);
				java.sql.Date depCreateTime =new java.sql.Date(depcreateTime.getTime());
				Department dep = new Department();
				dep.setDepName(depName);
				dep.setDepCreateTime(depCreateTime);
				dep.setSort(sort);
				dep.setId(id);
				DepService ds = new DepService();
				ds.editDep(dep);
					
				request.getRequestDispatcher("DepController?flag=depManage").forward(request, response);
				return;
			}else{//不通过返回原页
				request.setAttribute("map", map);
				editD(request, response);
			}
		}
		if("delbatchDep".equals(flag)){
			if(request.getParameterValues("ids")!=null){//判断非空
				//接受参数
				String[] ids = request.getParameterValues("ids");
				//调用service进行删除操作
				UserService us = new UserService();
				for (int i = 0; i < ids.length; i++) {
					    int id = Integer.parseInt(ids[i]);
					    us.deleteUserByDepId(id);
				}
				DepService ds = new DepService();
			    for (int i = 0; i < ids.length; i++) {
				    int id = Integer.parseInt(ids[i]);
				    ds.deleteDep(id);
			    }
			}
			//回到原页
			depManage(request, response);	
		}
		if("delDep".equals(flag)){
			//接受参数
			int id = Integer.parseInt(request.getParameter("id"));
			//调用service进行删除操作
			UserService us = new UserService();
			us.deleteUserByDepId(id);
			DepService ds = new DepService();
			ds.deleteDep(id);
			//回到原页
			depManage(request, response);
		}
		if("addDep".equals(flag)){
			addDep(request, response);
			//return;
		}
		//遍历dep表到DepMain页
		if("depManage".equals(flag)){
			depManage(request, response);
			//return;
		}
	}
	
	private void editD(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接受参数
		int dSort = -1;
		if(request.getParameter("dSort")!= null){
			dSort = Integer.parseInt(request.getParameter("dSort"));
		}
		int id = Integer.parseInt(request.getParameter("id"));
		//调service准备数据
		DepService ds = new DepService();
		Department dep = ds.findDepById(id);
		//set数据
		request.setAttribute("dep", dep);
		request.setAttribute("dSort", dSort);
		//转发到EditDep页
		request.getRequestDispatcher("EditDep.jsp").forward(request, response);
		return;
	}
	
	private void addDep(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接收AddDep页传来的参数
		int dSort = -1;
		if(request.getParameter("dSort")!= null){
			dSort = Integer.parseInt(request.getParameter("dSort"));
		}
		String depName = request.getParameter("depName");
		String date = request.getParameter("depCreateTime");
		String Sort = request.getParameter("sort");
		//对参数进行验证，map集合放错误提示
		Map<String, String> map = Tools.checkDep(depName,date,Sort);
		
		if(map.size()==0){//通过验证 封装参数回main
			int sort = -1;
			if(Sort!=""){
				sort = Integer.parseInt(request.getParameter("sort"));
			}
			
			Date depcreateTime = Tools.stringToDate(date);
			java.sql.Date depCreateTime =new java.sql.Date(depcreateTime.getTime());
			Department dep = new Department();
			dep.setDepName(depName);
			dep.setDepCreateTime(depCreateTime);
			dep.setSort(sort);
			DepService ds = new DepService();////////////////////
			ds.addDep(dep);
			
			depManage(request, response);
			//request.getRequestDispatcher("UserController?flag=depManage&dSort="+dSort).forward(request, response);
			//return;
		}else{//不通过返回原页
			request.setAttribute("map", map);
			request.getRequestDispatcher("AddDep.jsp?dSort="+dSort).forward(request, response);
			return;
		}
	}
	
	private void depManage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int dSort = -1;
		if(request.getParameter("dSort")!= null){
			dSort = Integer.parseInt(request.getParameter("dSort"));
		}
		DepService ds = new DepService();
		List<Department> depList = ds.getDepList(dSort);
		request.setAttribute("depList", depList);
		request.setAttribute("dSort", dSort);
		request.getRequestDispatcher("DepMain.jsp").forward(request, response);
		return;
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
