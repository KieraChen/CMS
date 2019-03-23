package mvc.service;

import java.util.List;
import java.util.Map;

import mvc.dao.UserDao;
import mvc.domain.User;

public class UserService {
	private UserDao userDao = new UserDao();
	
	public boolean findUserByName(String name,int id){
		return userDao.findUserByName(name,id);
	}
	
	public User checkUser(User user) {
		return userDao.findUser(user);
	}
	
	public int getPageCount(int pageSize,int depId,String userName){
		return userDao.getPageCount(pageSize, depId, userName);
	}
	
	public List<User> getUserList(int pageNow,int pageSize,int depId,String userName){
		return userDao.getUserList(pageNow, pageSize, depId, userName);
	}
	
	public void delUser(int id){
		userDao.deleteUser(id);
	}
	
	public void deleteUserByDepId(int depId){
		userDao.deleteUserByDepId(depId);
	}
	
	public void addUser(User user){
		userDao.addUser(user);
	}
	
	public User findUserById(int id){
		return userDao.findUserById(id);
	}
	
	public void editUser(User user){
		userDao.editUser(user);
	}
	
	
	

}
