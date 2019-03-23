package mvc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class Tools {
	public static Map<String, String> checkNews(String title,String createtime,String content){
		Map<String,String> map = new HashMap<String,String>();
		if(title==null || title.trim()=="" ){
			map.put("title", "标题不能为空！");		
		}
		if(!isValidDate(createtime)){
			map.put("createtime", "日期不合法！格式：yyyy-MM-dd");
		}
		if(content==null || content.trim()==""){
			map.put("content", "内容不能为空！");
		}
		return map;
	}
	
	public static Map<String, String> checkJump(String num,int pageCount){
		Map<String,String> map = new HashMap<String,String>();
		int i = 0;
		if(num==null || num.trim()==""|| !Tools.isNumberic(num)){
			i++;
			map.put("page", "页码不合法！");
		}
		if(i==0){
			int pagenow = Integer.parseInt(num);
			if(pagenow < 1 || pagenow > pageCount){
				map.put("page", "页码不存在！");
			}
		}
		return map;
	}
	
	public static Map<String, String> checkUser(String userName,String userPwd,String userCode,String userage){
		Map<String,String> map = new HashMap<String,String>();
		if(userName==null || userName.trim()=="" ){
			map.put("userName", "用户名不能为空！");		
		}
		if(userPwd==null || userPwd.trim()==""){
			map.put("userPwd", "密码不能为空！");
		}
		if(userCode==null || userCode.trim()==""){
			map.put("userCode", "用户编号不能为空！");
		}
		if(userage==null || userage.trim()==""|| !Tools.isNumberic(userage)){
			map.put("userAge", "年龄不能为空且为数值！");
		}
		return map;
	}
	
	public static Map<String, String> checkDep(String depName,String depCreateTime,String sort){
		Map<String,String> map = new HashMap<String,String>();
		if(depName==null || depName.trim()=="" ){
			map.put("depName", "部门名不能为空！");		
		}
		if(!isValidDate(depCreateTime)){
			map.put("depCreateTime", "日期不合法！格式：yyyy-MM-dd");
		}
		if(sort!=null){
			if(!Tools.isNumberic(sort)){
				map.put("sort", "排序权限必须为数值！");
			}
		}
		
		return map;
	}
	
	public static Map<String, String> checkType(String typeName,String sort){
		Map<String,String> map = new HashMap<String,String>();
		if(typeName==null || typeName.trim()=="" ){
			map.put("typeName", "类别名不能为空！");		
		}
		if(sort!=null){
			if(!Tools.isNumberic(sort)){
				map.put("sort", "排序权限必须为数值！");
			}
		}
		
		return map;
	}
	
	public static int readVisited(){
		int visited = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = ConnUtil.getConnection();
			String sql = "select visited from tb_visited";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				visited = rs.getInt("visited");
			}
		}catch(Exception e){
			System.out.println("查找失败！");	
		}finally{
			ConnUtil.close(conn, ps, rs);
		}
		return visited;
	} 
	
	public static void writeVisited(int visited){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = ConnUtil.getConnection();
			String sql = "update tb_visited set visited = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, visited);
			ps.executeUpdate();	
		}catch(Exception e){
			System.out.println("修改失败！");	
		}finally{
			ConnUtil.close(conn, ps, null);
		}
	}
	
	public static boolean isNumberic(String str){
		for (int i = 0; i < str.length(); i++) {
			if(!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
	
	private static SimpleDateFormat dateFormat = null;
    static 
    {
    // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        dateFormat.setLenient(false);
    }
    public static boolean isValidDate(String s){
        try
        {
             dateFormat.parse(s);
             return true;
         }
        catch (Exception e)
        {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }
    
    /**
     * 
     * 字符串转换为对应日期
     * 
     * @param source
     * @param pattern
     * @return
     */
    public static Date stringToDate(String str) {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (Exception e) {
        }
        return date;
    }
	

}
