package mvc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnUtil {
	private static String url = "jdbc:mysql://127.0.0.1:3306/ms";
	private static String user = "root";
	private static String pass = "";
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("注册驱动失败！");
		}
	}
	
	public static Connection getConnection(){
		try {
			conn = DriverManager.getConnection(url, user, pass);
		}catch (SQLException e) {
			System.out.println("连接数据库失败！请检查地址，用户名及密码！");
		}
		return conn;
	}
	
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs){
		try {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
		try {
			if(ps!= null){
				ps.close();
			}
			if(conn!=null){
			    conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	}
	
}
