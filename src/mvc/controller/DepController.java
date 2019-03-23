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
			//����EditDepҳ�����Ĳ���
			int id = Integer.parseInt(request.getParameter("id"));
			String depName = request.getParameter("depName");
			String date = request.getParameter("depCreateTime");
			String Sort = request.getParameter("sort");
			//�Բ���������֤��map���ϷŴ�����ʾ
			Map<String, String> map = Tools.checkDep(depName,date,Sort);
			
			if(map.size()==0){//ͨ����֤ ��װ������main
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
			}else{//��ͨ������ԭҳ
				request.setAttribute("map", map);
				editD(request, response);
			}
		}
		if("delbatchDep".equals(flag)){
			if(request.getParameterValues("ids")!=null){//�жϷǿ�
				//���ܲ���
				String[] ids = request.getParameterValues("ids");
				//����service����ɾ������
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
			//�ص�ԭҳ
			depManage(request, response);	
		}
		if("delDep".equals(flag)){
			//���ܲ���
			int id = Integer.parseInt(request.getParameter("id"));
			//����service����ɾ������
			UserService us = new UserService();
			us.deleteUserByDepId(id);
			DepService ds = new DepService();
			ds.deleteDep(id);
			//�ص�ԭҳ
			depManage(request, response);
		}
		if("addDep".equals(flag)){
			addDep(request, response);
			//return;
		}
		//����dep��DepMainҳ
		if("depManage".equals(flag)){
			depManage(request, response);
			//return;
		}
	}
	
	private void editD(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//���ܲ���
		int dSort = -1;
		if(request.getParameter("dSort")!= null){
			dSort = Integer.parseInt(request.getParameter("dSort"));
		}
		int id = Integer.parseInt(request.getParameter("id"));
		//��service׼������
		DepService ds = new DepService();
		Department dep = ds.findDepById(id);
		//set����
		request.setAttribute("dep", dep);
		request.setAttribute("dSort", dSort);
		//ת����EditDepҳ
		request.getRequestDispatcher("EditDep.jsp").forward(request, response);
		return;
	}
	
	private void addDep(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//����AddDepҳ�����Ĳ���
		int dSort = -1;
		if(request.getParameter("dSort")!= null){
			dSort = Integer.parseInt(request.getParameter("dSort"));
		}
		String depName = request.getParameter("depName");
		String date = request.getParameter("depCreateTime");
		String Sort = request.getParameter("sort");
		//�Բ���������֤��map���ϷŴ�����ʾ
		Map<String, String> map = Tools.checkDep(depName,date,Sort);
		
		if(map.size()==0){//ͨ����֤ ��װ������main
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
		}else{//��ͨ������ԭҳ
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
