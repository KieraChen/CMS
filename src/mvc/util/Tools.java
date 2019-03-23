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
			map.put("title", "���ⲻ��Ϊ�գ�");		
		}
		if(!isValidDate(createtime)){
			map.put("createtime", "���ڲ��Ϸ�����ʽ��yyyy-MM-dd");
		}
		if(content==null || content.trim()==""){
			map.put("content", "���ݲ���Ϊ�գ�");
		}
		return map;
	}
	
	public static Map<String, String> checkJump(String num,int pageCount){
		Map<String,String> map = new HashMap<String,String>();
		int i = 0;
		if(num==null || num.trim()==""|| !Tools.isNumberic(num)){
			i++;
			map.put("page", "ҳ�벻�Ϸ���");
		}
		if(i==0){
			int pagenow = Integer.parseInt(num);
			if(pagenow < 1 || pagenow > pageCount){
				map.put("page", "ҳ�벻���ڣ�");
			}
		}
		return map;
	}
	
	public static Map<String, String> checkUser(String userName,String userPwd,String userCode,String userage){
		Map<String,String> map = new HashMap<String,String>();
		if(userName==null || userName.trim()=="" ){
			map.put("userName", "�û�������Ϊ�գ�");		
		}
		if(userPwd==null || userPwd.trim()==""){
			map.put("userPwd", "���벻��Ϊ�գ�");
		}
		if(userCode==null || userCode.trim()==""){
			map.put("userCode", "�û���Ų���Ϊ�գ�");
		}
		if(userage==null || userage.trim()==""|| !Tools.isNumberic(userage)){
			map.put("userAge", "���䲻��Ϊ����Ϊ��ֵ��");
		}
		return map;
	}
	
	public static Map<String, String> checkDep(String depName,String depCreateTime,String sort){
		Map<String,String> map = new HashMap<String,String>();
		if(depName==null || depName.trim()=="" ){
			map.put("depName", "����������Ϊ�գ�");		
		}
		if(!isValidDate(depCreateTime)){
			map.put("depCreateTime", "���ڲ��Ϸ�����ʽ��yyyy-MM-dd");
		}
		if(sort!=null){
			if(!Tools.isNumberic(sort)){
				map.put("sort", "����Ȩ�ޱ���Ϊ��ֵ��");
			}
		}
		
		return map;
	}
	
	public static Map<String, String> checkType(String typeName,String sort){
		Map<String,String> map = new HashMap<String,String>();
		if(typeName==null || typeName.trim()=="" ){
			map.put("typeName", "���������Ϊ�գ�");		
		}
		if(sort!=null){
			if(!Tools.isNumberic(sort)){
				map.put("sort", "����Ȩ�ޱ���Ϊ��ֵ��");
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
			System.out.println("����ʧ�ܣ�");	
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
			System.out.println("�޸�ʧ�ܣ�");	
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
    // ָ�����ڸ�ʽΪ��λ��/��λ�·�/��λ���ڣ�ע��yyyy/MM/dd���ִ�Сд��
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // ����lenientΪfalse. ����SimpleDateFormat��ȽϿ��ɵ���֤���ڣ�����2007/02/29�ᱻ���ܣ���ת����2007/03/01
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
            // ���throw java.text.ParseException����NullPointerException����˵����ʽ����
            return false;
        }
    }
    
    /**
     * 
     * �ַ���ת��Ϊ��Ӧ����
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
