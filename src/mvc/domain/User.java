package mvc.domain;
/*"<tr bgcolor='#cccccc'><td>id</td><td>����id</td><td>�û���</td><td>����</td>"
+ "<td>�û����</td><td>�Ա�</td><td>����</td><td>Ȩ��</td></tr>"*/
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
