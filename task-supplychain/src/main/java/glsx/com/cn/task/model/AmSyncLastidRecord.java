package glsx.com.cn.task.model;


import org.junit.Test;

import javax.persistence.Table;

/**
 * @Title: SyncLastidRecord
 * @Description: 同步数据库ID记录
 * @author liuquan 
 * @date 2018年4月21日 下午4:29:16
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@Table(name = "am_sync_lastid_record")
public class AmSyncLastidRecord {
	
	private Integer id;

	/**
	 * 供应链广汇采集拆分ID
	 */
	private Long lastStatementCollectionId;

	/**
	 * 供应链金融风控拆分ID
	 */
	private Long lastStatementFinanceId;

	public Long getLastStatementCollectionId() {
		return lastStatementCollectionId;
	}

	public void setLastStatementCollectionId(Long lastStatementCollectionId) {
		this.lastStatementCollectionId = lastStatementCollectionId;
	}

	public Long getLastStatementFinanceId() {
		return lastStatementFinanceId;
	}

	public void setLastStatementFinanceId(Long lastStatementFinanceId) {
		this.lastStatementFinanceId = lastStatementFinanceId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AmSyncLastidRecord{" +
				"id=" + id +
				", lastStatementCollectionId=" + lastStatementCollectionId +
				", lastStatementFinanceId=" + lastStatementFinanceId +
				'}';
	}
}
