package mvc.service;

import java.util.List;

import mvc.dao.NewsDao;
import mvc.domain.News;

public class NewsService {
	NewsDao newsDao = new NewsDao();
	public boolean findNewsByTitle(String title,int id){
		return newsDao.findNewsByTitle(title, id);
	}
	
	public int getPageCount(int pageSize,int ntypeid,int nflag,int uid){
		return newsDao.getPageCount(pageSize, ntypeid, nflag,uid);
	}
	
	public List<News> getNewsList(int pageNow,int pageSize,int ntypeid,int nflag,int uid){
		return newsDao.getNewsList(pageNow, pageSize, ntypeid, nflag,uid);
	}
	
	public void deleteNews(int id){
		newsDao.deleteNews(id);
	}
	
	public void addNews(News news){
		newsDao.addNews(news);
	}
	
	public News findNewsById(int id){
		return newsDao.findNewsById(id);
	}
	
	public void editNews(News news){
		newsDao.editNews(news);
	}
	
	public void deleteNewsByTypeId(int typeId){
		newsDao.deleteNewsByTypeId(typeId);
	}

}
