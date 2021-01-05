package cn.com.glsx.supplychain.model;

import io.swagger.models.auth.In;

import javax.persistence.Transient;
import java.util.List;

public class PageInfo {

	@Transient
	private Integer targetPage;
	
	@Transient
	private Integer pageStart;
	
	//重新定义分页（参考百度）
	@Transient
	private Integer pageFirstId;   //每页首个id
	
	//@Transient
	//private Integer pageOffset;    //相对偏移页面
	
	@Transient
	private Integer pageSize;		//每页显示个数
	
	//@Transient
	//private Integer preOrNext;		//1:向后 2:向前
	
	@Transient
	private Integer checkNumber;    //
	
	//@Transient
	//private Integer prePages;		//前面还有多少页
	
	@Transient
	private Integer nexPages;		//后面还有多少页
	
	@Transient
	private Integer totalDisPages;  //页面格子数量

	@Transient
	private List<Integer> nexIdList; //后面ID

	@Transient
	private List<Integer> agoIdList; //前面ID

	public List<Integer> getNexIdList() {
		return nexIdList;
	}

	public void setNexIdList(List<Integer> nexIdList) {
		this.nexIdList = nexIdList;
	}

	public List<Integer> getAgoIdList() {
		return agoIdList;
	}

	public void setAgoIdList(List<Integer> agoIdList) {
		this.agoIdList = agoIdList;
	}

	public Integer getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(Integer targetPage) {
		this.targetPage = targetPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

	public Integer getPageFirstId() {
		return pageFirstId;
	}

	public void setPageFirstId(Integer pageFirstId) {
		this.pageFirstId = pageFirstId;
	}

/*	public Integer getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(Integer pageOffset) {
		this.pageOffset = pageOffset;
	}

	public Integer getPreOrNext() {
		return preOrNext;
	}

	public void setPreOrNext(Integer preOrNext) {
		this.preOrNext = preOrNext;
	}*/

	public Integer getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(Integer checkNumber) {
		this.checkNumber = checkNumber;
	}

	/*public Integer getPrePages() {
		return prePages;
	}

	public void setPrePages(Integer prePages) {
		this.prePages = prePages;
	}*/

	public Integer getNexPages() {
		return nexPages;
	}

	public void setNexPages(Integer nexPages) {
		this.nexPages = nexPages;
	}

	public Integer getTotalDisPages() {
		return totalDisPages;
	}

	public void setTotalDisPages(Integer totalDisPages) {
		this.totalDisPages = totalDisPages;
	}
	
	
	
	
	
	
}
