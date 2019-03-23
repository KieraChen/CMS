package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvc.domain.Department;
import mvc.domain.NewsType;
import mvc.util.ConnUtil;

public class NewsTypeDao {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	
	public boolean findTypeByName(String name,int id){
		try{
			conn = ConnUtil.getConnection();
			StringBuffer buf =  new StringBuffer();  
	        buf.append("select * from tb_newstype where typeName ='"+name+"'");  
	        if(id != -1){
	        	 buf.append(" and id != "+id);
	        }
	        //System.out.println(buf.toString());
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
	
	public void editType(NewsType newsType){
		try{
			conn = ConnUtil.getConnection();
			if(newsType.getSort() == -1){
				String sql = "update tb_newstype set typeName=?,sort=default where id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, newsType.getTypeName());
				ps.setInt(2, newsType.getId());
			}else{
				String sql = "update tb_newstype set typeName=?,sort=? where id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, newsType.getTypeName());
				ps.setInt(2, newsType.getSort());
				ps.setInt(3, newsType.getId());
			}
			ps.executeUpdate();
		}catch(Exception e){
			System.out.println("ÐÞ¸ÄÊ§°Ü£¡");
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
	
	public NewsType findTypeById(int id){
		NewsType newsType = new NewsType();
		try{
			conn = ConnUtil.getConnection();
			String sql = "select * from tb_newstype where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				newsType.setId(rs.getInt("id"));
				newsType.setTypeName(rs.getString("typeName"));
				newsType.setSort(rs.getInt("sort"));
			}
		}catch(Exception e){
			System.out.println("²éÑ¯Ê§°Ü£¡");
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return newsType;
	}
	
	public void addType(NewsType newsType){
		try{
			conn = ConnUtil.getConnection();
			if(newsType.getSort() == -1){
				String sql = "insert into tb_newstype values(default,?,default)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, newsType.getTypeName());
			}else{
			    String sql = "insert into tb_newstype values(default,?,?)";
			    ps = conn.prepareStatement(sql);
			    ps.setString(1, newsType.getTypeName());
			    ps.setInt(2, newsType.getSort());
			}	
			ps.executeUpdate();
		}catch(Exception e){
			System.out.println("Ìí¼ÓÊ§°Ü£¡");
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
	
	public void deleteType(int id){
		try{
			conn = ConnUtil.getConnection();
			String sql = "delete from tb_newstype where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		}catch(Exception e){
			System.out.println("É¾³ýÊ§°Ü£¡");
			
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
	
	public List<NewsType> getTypeList(int nSort){
		List<NewsType> typeList = new ArrayList<NewsType>();
		try{
			conn = ConnUtil.getConnection();
			StringBuffer buf =  new StringBuffer();  
	        buf.append("select * from tb_newstype ");  
	        if(nSort != -1){
	        	buf.append("order by sort");
	        }
			ps = conn.prepareStatement(buf.toString());
		    rs = ps.executeQuery();
		    while(rs.next()){
		    	NewsType newsType = new NewsType();
		    	newsType.setId(rs.getInt("id"));
		    	newsType.setTypeName(rs.getString("typeName"));
		    	newsType.setSort(rs.getInt("sort"));
		    	typeList.add(newsType);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return typeList;
	}

}
