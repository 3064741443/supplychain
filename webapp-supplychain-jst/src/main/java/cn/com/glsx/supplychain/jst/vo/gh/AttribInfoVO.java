package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;

public class AttribInfoVO implements Serializable{
	
	private Integer id;
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "AttribInfoVO [id=" + id + ", name=" + name + "]";
	}
	
}
