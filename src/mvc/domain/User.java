package mvc.domain;
/*"<tr bgcolor='#cccccc'><td>id</td><td>部门id</td><td>用户名</td><td>密码</td>"
+ "<td>用户编号</td><td>性别</td><td>年龄</td><td>权限</td></tr>"*/
/*"<tr><td>"+rs.getInt("id")+"</td><td>"+rs.getInt("depId")+"</td>"
+ "<td>"+rs.getString("userName")+"</td><td>"+rs.getString("userPwd")+"</td>"
+ "<td>"+rs.getString("userCode")+"</td><td>"+rs.getString("userSex")+"</td>"
+ "<td>"+rs.getInt("userAge")+"</td><td>"+rs.getInt("userPower")+"</td></tr>"*/
public class User {
	private int id;
	private int depId;
	private String userName;
	private String userPwd;
	private String userCode;
	private String userSex;
	private int userAge;
	private int userPower;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDepId() {
		return depId;
	}
	public void setDepId(int depId) {
		this.depId = depId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public int getUserPower() {
		return userPower;
	}
	public void setUserPower(int userPower) {
		this.userPower = userPower;
	}
	

}
