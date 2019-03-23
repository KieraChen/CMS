package mvc.domain;

import java.sql.Date;


public class Department {
	private int id;
	private String depName;
	private Date depCreateTime;
	private int sort;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public Date getDepCreateTime() {
		return depCreateTime;
	}
	public void setDepCreateTime(Date depCreateTime) {
		this.depCreateTime = depCreateTime;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	

}
