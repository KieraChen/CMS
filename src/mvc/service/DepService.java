package mvc.service;

import java.util.List;

import mvc.dao.DepDao;
import mvc.domain.Department;

public class DepService {
	private DepDao depdao = new DepDao();
	
	public List<Department> getDepList(int dSort){
		return  depdao.getDepList(dSort);
	}
	public void addDep(Department dep){
		depdao.addDep(dep);
	}
	public void deleteDep(int id){
		depdao.deleteDep(id);
	}
	public Department findDepById(int id){
		return depdao.findDepById(id);
	}
	public void editDep(Department dep){
		depdao.editDep(dep);
	}
	public boolean findDepByName(String name,int id){
		return depdao.findDepByName(name, id);
	}

}
