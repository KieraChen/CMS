package mvc.service;

import java.util.List;

import mvc.dao.NewsTypeDao;
import mvc.domain.NewsType;

public class NewsTypeService {
	NewsTypeDao newsTypeDao = new NewsTypeDao();
	public List<NewsType> getTypeList(int nSort){
		return newsTypeDao.getTypeList(nSort);
	}
	
	public void deleteType(int id){
		newsTypeDao.deleteType(id);
	}
	
	public void addType(NewsType newsType){
		newsTypeDao.addType(newsType);
	}
	
	public NewsType findTypeById(int id){
		return newsTypeDao.findTypeById(id);
	}
	
	public void editType(NewsType newsType){
		newsTypeDao.editType(newsType);
	}
	
	public boolean findTypeByName(String name,int id){
		return newsTypeDao.findTypeByName(name, id);
	}

}
