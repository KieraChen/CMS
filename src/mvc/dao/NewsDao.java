package mvc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvc.domain.News;
import mvc.domain.User;
import mvc.util.ConnUtil;

public class NewsDao {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	
	public boolean findNewsByTitle(String title,int id){
		try{
			conn = ConnUtil.getConnection();
			StringBuffer buf =  new StringBuffer();  
	        buf.append("select * from tb_news where title ='"+title+"'");  
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
	
	public void deleteNewsByTypeId(int typeId){
		try{
			conn = ConnUtil.getConnection();
			String sql = "delete from tb_news where typeid = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, typeId);
			ps.executeUpdate();
		}catch(Exception e){
			System.out.println("É¾³ýÊ§°Ü£¡");	
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
	
	public void editNews(News news){
		try{
			conn = ConnUtil.getConnection();
			String sql = "update tb_news set title=?,content=?,createtime=?,flag=?,typeid=? where id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getContent());
			ps.setDate(3, news.getCreatetime());
			ps.setInt(4, news.getFlag());
			ps.setInt(5, news.getTypeid());
			ps.setInt(6, news.getId());
			ps.executeUpdate();
			
		}catch(Exception e){
			System.out.println("ÐÞ¸ÄÊ§°Ü£¡");
			
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
	
	public News findNewsById(int id){
		News news = new News();
		try{
			conn = ConnUtil.getConnection();
			String sql = "select * from tb_news where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				news.setId(rs.getInt("id"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setTypeid(rs.getInt("typeid"));
				news.setFlag(rs.getInt("flag"));
				news.setCreatetime(rs.getDate("createtime"));
				news.setUid(rs.getInt("uid"));
			}
		}catch(Exception e){
			System.out.println("²éÑ¯Ê§°Ü£¡");
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return news;
	}
	
    public void addNews(News news){
		try{
			conn = ConnUtil.getConnection();
			String sql = "insert into tb_news values(default,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getContent());
			ps.setInt(3, news.getTypeid());
			ps.setInt(4, news.getFlag());
			ps.setDate(5, news.getCreatetime());
			ps.setInt(6, news.getUid());
			ps.executeUpdate();
			
		}catch(Exception e){
			System.out.println("Ìí¼ÓÊ§°Ü£¡");
			
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
//Òª¸Ä¼Ó1¸öif
	public int getPageCount(int pageSize,int ntypeid,int nflag,int uid){
		int pageCount = 0;
		int rsCount = 0;
		
		try {
			conn = ConnUtil.getConnection();
			
			StringBuffer buf =  new StringBuffer();  
	        buf.append("select count(*) rsCount from tb_news where 1=1");  
	        if(ntypeid != -1){
	        	 buf.append(" and typeid = "+ntypeid);
	        }
	        if(nflag != -1){
	        	buf.append(" and flag = "+nflag);
	        } 
	        if(uid != -1){
	        	buf.append(" and uid = "+uid);
	        }
	       // System.out.println(buf.toString()+"------------");

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
	
	public List<News> getNewsList(int pageNow,int pageSize,int ntypeid,int nflag,int uid){
		List<News> newsList = new ArrayList<News>();
		try{
			conn = ConnUtil.getConnection();
			StringBuffer buf =  new StringBuffer();  
	        buf.append("select * from tb_news where 1=1");  
	        /*if(depId != -1 || userName.length() != 0){
	        	buf.append(" ");
	        }*/
	        if(ntypeid != -1){
	        	 buf.append(" and typeid = "+ntypeid);
	        }
	        if(nflag != -1){
	        	buf.append(" and flag = "+nflag);
	        } 
	        if(uid != -1){
	        	buf.append(" and uid = "+uid);
	        }
	        buf.append(" order by id desc limit "+ (pageNow - 1) * pageSize + "," + pageSize);
	        //System.out.println(buf.toString()+"+++++++++++");
			ps = conn.prepareStatement(buf.toString());
		    rs = ps.executeQuery();
		    while(rs.next()){
		    	News news = new News();
		    	news.setId(rs.getInt("id"));
		    	news.setTitle(rs.getString("title"));
		    	news.setContent(rs.getString("content"));
		    	news.setTypeid(rs.getInt("typeid"));
		    	news.setFlag(rs.getInt("flag"));
		    	news.setCreatetime(rs.getDate("createtime"));
		    	news.setUid(rs.getInt("uid"));
		    	newsList.add(news);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return newsList;
	}
	
	
    public void deleteNews(int id){
		
		try{
			conn = ConnUtil.getConnection();
			String sql = "delete from tb_news where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		}catch(Exception e){
			System.out.println("É¾³ýÊ§°Ü£¡");
			
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
	
	

}
