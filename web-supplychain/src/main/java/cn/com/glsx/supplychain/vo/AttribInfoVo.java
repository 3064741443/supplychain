package cn.com.glsx.supplychain.vo;

public class AttribInfoVo {

	private String id;
	private String type;
	private String name;
	private String comment;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "AttribInfoVo [id=" + id + ", type=" + type + ", name=" + name
				+ ", comment=" + comment + "]";
	}
	
}
