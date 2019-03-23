package mvc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvc.domain.Department;
import mvc.domain.User;
import mvc.util.ConnUtil;

public class DepDao {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	public boolean findDepByName(String name,int id){
		try{
			conn = ConnUtil.getConnection();
			StringBuffer buf =  new StringBuffer();  
	        buf.append("select * from tb_department where depName ='"+name+"'");  
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
	
	public List<Department> getDepList(int dSort){
		List<Department> depList = new ArrayList<Department>();
		try{
			conn = ConnUtil.getConnection();
			StringBuffer buf =  new StringBuffer();  
	        buf.append("select * from tb_department ");  
	        if(dSort != -1){
	        	buf.append("order by sort");
	        }
			ps = conn.prepareStatement(buf.toString());
		    rs = ps.executeQuery();
		    while(rs.next()){
		    	Department dep = new Department();
		    	dep.setId(rs.getInt("id"));
		    	dep.setDepName(rs.getString("depName"));
		    	dep.setDepCreateTime(rs.getDate("depCreateTime"));
		    	dep.setSort(rs.getInt("sort"));
		    	depList.add(dep);
		    	
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return depList;
	}
	
	public void addDep(Department dep){
		try{
			conn = ConnUtil.getConnection();
			if(dep.getSort() == -1){
				String sql = "insert into tb_department values(default,?,?,default)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dep.getDepName());
				ps.setDate(2, dep.getDepCreateTime());
			}else{
			    String sql = "insert into tb_department values(default,?,?,?)";
			    ps = conn.prepareStatement(sql);
			    ps.setString(1, dep.getDepName());
			    ps.setDate(2, dep.getDepCreateTime());
			    ps.setInt(3, dep.getSort());
			}
			
			ps.executeUpdate();
			
		}catch(Exception e){
			System.out.println("Ìí¼ÓÊ§°Ü£¡");
			
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
	
    public void deleteDep(int id){
		try{
			conn = ConnUtil.getConnection();
			String sql = "delete from tb_department where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		}catch(Exception e){
			System.out.println("É¾³ýÊ§°Ü£¡");
			
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
    
	public Department findDepById(int id){
		Department dep = new Department();
		try{
			conn = ConnUtil.getConnection();
			String sql = "select * from tb_department where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				dep.setId(rs.getInt("id"));
				dep.setDepName(rs.getString("depName"));
				dep.setDepCreateTime(rs.getDate("depCreateTime"));
				dep.setSort(rs.getInt("sort"));
			}
		}catch(Exception e){
			System.out.println("²éÑ¯Ê§°Ü£¡");
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return dep;
	}
	
	public void editDep(Department dep){
		try{
			conn = ConnUtil.getConnection();
			if(dep.getSort() == -1){
				String sql = "update tb_department set depName=?,depCreateTime=?,sort=default where id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dep.getDepName());
				ps.setDate(2, dep.getDepCreateTime());
				ps.setInt(3, dep.getId());
			}else{
				String sql = "update tb_department set depName=?,depCreateTime=?,sort=? where id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dep.getDepName());
				ps.setDate(2, dep.getDepCreateTime());
				ps.setInt(3, dep.getSort());
				ps.setInt(4, dep.getId());
			}
			ps.executeUpdate();
		}catch(Exception e){
			System.out.println("ÐÞ¸ÄÊ§°Ü£¡");
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}

}
