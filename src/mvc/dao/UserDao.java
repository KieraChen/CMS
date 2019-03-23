package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mvc.domain.User;
import mvc.util.ConnUtil;
import mvc.util.Tools;



public class UserDao {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	
	public boolean findUserByName(String name,int id){
		try{
			conn = ConnUtil.getConnection();
			StringBuffer buf =  new StringBuffer();  
	        buf.append("select * from tb_users where userName ='"+name+"'");  
	        if(id != -1){
	        	 buf.append(" and id != "+id);
	        }
			ps = conn.prepareStatement(buf.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("²éÑ¯Ê§°Ü£¡");
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return false;
	}
	
	public void editUser(User user){
		try{
			conn = ConnUtil.getConnection();
			String sql = "update tb_users set depID=?,userName=?,userPwd=?,userCode=?,userSex=?,userAge=?,userPower=? where id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getDepId());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getUserPwd());
			ps.setString(4, user.getUserCode());
			ps.setString(5, user.getUserSex());
			ps.setInt(6, user.getUserAge());
			ps.setInt(7, user.getUserPower());
			ps.setInt(8, user.getId());
			ps.executeUpdate();
			
		}catch(Exception e){
			System.out.println("ÐÞ¸ÄÊ§°Ü£¡");
			
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
	
	public User findUserById(int id){
		User user = new User();
		try{
			conn = ConnUtil.getConnection();
			String sql = "select * from tb_users where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				user.setId(rs.getInt("id"));
		    	user.setDepId(rs.getInt("depId"));
		    	user.setUserName(rs.getString("userName"));
		    	user.setUserPwd(rs.getString("userPwd"));
		    	user.setUserCode(rs.getString("userCode"));
		    	user.setUserSex(rs.getString("userSex"));
		    	user.setUserAge(rs.getInt("userAge"));
		    	user.setUserPower(rs.getInt("userPower"));
			}
		}catch(Exception e){
			System.out.println("²éÑ¯Ê§°Ü£¡");
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return user;
	}
	
	public User findUser(User user){	
		String sql = "select * from tb_users where userName = ? and userPwd = ?";
		try{
			conn = ConnUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserPwd());
			rs = ps.executeQuery();

			if(rs.next()){
				user.setId(rs.getInt("id"));
				user.setUserPower(rs.getInt("userPower"));
				return user;
			}
			
		}catch(Exception e){
			System.out.println("²éÑ¯Ê§°Ü£¡");
			
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return null;
    }

	public int getPageCount(int pageSize,int depId,String userName){
		int pageCount = 0;
		int rsCount = 0;
		
		try {
			conn = ConnUtil.getConnection();
			
			StringBuffer buf =  new StringBuffer();  
	        buf.append("select count(*) rsCount from tb_users where 1=1");  
	        if(depId != -1){
	        	 buf.append(" and depId = "+depId);
	        }
	        if(userName.length()!=0){
	        	buf.append(" and userName like '%"+userName+"%'");
	        }   
			ps = conn.prepareStatement(buf.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				rsCount = rs.getInt("rsCount");
			}
			if(rsCount % pageSize == 0){
				pageCount = rsCount / pageSize;
			}else{
				pageCount = rsCount / pageSize + 1;
			}
		} catch (SQLException e) {
			System.out.println("pageCount²éÑ¯Ê§°Ü£¡");
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return pageCount;
	}
	
	public List<User> getUserList(int pageNow,int pageSize,int depId,String userName){
		List<User> userList = new ArrayList<User>();
		try{
			conn = ConnUtil.getConnection();
			/*int i = 0;*/
			StringBuffer buf =  new StringBuffer();  
	        buf.append("select * from tb_users where 1=1");  
	        if(depId != -1 || userName.length() != 0){
	        	buf.append(" ");
	        }
	        if(depId != -1){
	        	 buf.append(" and depId = "+depId);
	        	/* i++;*/
	        }
	        if(userName.length()!=0){
	        	/*if(i>0){
	        		buf.append(" and ");
	        	}*/
	        	buf.append(" and userName like '%"+userName+"%'");
	        }
	        buf.append(" order by id desc limit "+ (pageNow - 1) * pageSize + "," + pageSize);
	        /*System.out.println(buf.toString()+"+++++++++++");*/
			ps = conn.prepareStatement(buf.toString());
		    rs = ps.executeQuery();
		    while(rs.next()){
		    	User user = new User();
		    	user.setId(rs.getInt("id"));
		    	user.setDepId(rs.getInt("depId"));
		    	user.setUserName(rs.getString("userName"));
		    	user.setUserPwd(rs.getString("userPwd"));
		    	user.setUserCode(rs.getString("userCode"));
		    	user.setUserSex(rs.getString("userSex"));
		    	user.setUserAge(rs.getInt("userAge"));
		    	user.setUserPower(rs.getInt("userPower"));
		    	userList.add(user);
		    }
		} catch (SQLException e) {
			System.out.println("userList²éÑ¯Ê§°Ü£¡");
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return userList;
	}
	
	public void deleteUser(int id){
		
		try{
			conn = ConnUtil.getConnection();
			String sql = "delete from tb_users where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		}catch(Exception e){
			System.out.println("É¾³ýÊ§°Ü£¡");
			
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
public void deleteUserByDepId(int depId){
		try{
			conn = ConnUtil.getConnection();
			String sql = "delete from tb_users where depId = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, depId);
			ps.executeUpdate();
		}catch(Exception e){
			System.out.println("É¾³ýÊ§°Ü£¡");	
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
	
	public void addUser(User user){
		
		try{
			conn = ConnUtil.getConnection();
			String sql = "insert into tb_users values(default,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getDepId());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getUserPwd());
			ps.setString(4, user.getUserCode());
			ps.setString(5, user.getUserSex());
			ps.setInt(6, user.getUserAge());
			ps.setInt(7, user.getUserPower());
			ps.executeUpdate();
			
		}catch(Exception e){
			System.out.println("Ìí¼ÓÊ§°Ü£¡");
			
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
}
