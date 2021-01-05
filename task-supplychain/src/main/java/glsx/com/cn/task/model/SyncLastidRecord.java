package glsx.com.cn.task.model;


/**
 * @Title: SyncLastidRecord
 * @Description: 同步数据库ID记录
 * @author liuquan 
 * @date 2018年4月21日 下午4:29:16
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
public class SyncLastidRecord {
	
	private Integer id;
	
	private Integer lastFlowCardId;
	
	private Integer lastPhysicalDeviceId;
	
	private Integer lastInfoFileId;

	/**
	 * 向财务同步数据结果ID
	 */
	private Integer settlementId;
	
	/**
     * 向外部系统分发时记录数据update 时间戳 同LAST_INFO_FILE_ID
     */
    private Integer lastSyncExterSystemId;

	public Integer getLastSyncExterSystemId() {
		return lastSyncExterSystemId;
	}

	public void setLastSyncExterSystemId(Integer lastSyncExterSystemId) {
		this.lastSyncExterSystemId = lastSyncExterSystemId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLastFlowCardId() {
		return lastFlowCardId;
	}

	public void setLastFlowCardId(Integer lastFlowCardId) {
		this.lastFlowCardId = lastFlowCardId;
	}

	public Integer getLastPhysicalDeviceId() {
		return lastPhysicalDeviceId;
	}

	public void setLastPhysicalDeviceId(Integer lastPhysicalDeviceId) {
		this.lastPhysicalDeviceId = lastPhysicalDeviceId;
	}
	
	public Integer getLastInfoFileId() {
		return lastInfoFileId;
	}

	public void setLastInfoFileId(Integer lastInfoFileId) {
		this.lastInfoFileId = lastInfoFileId;
	}

	public Integer getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Integer settlementId) {
		this.settlementId = settlementId;
	}

	@Override
	public String toString() {
		return "SyncLastidRecord [id=" + id + ", lastFlowCardId="
				+ lastFlowCardId + ", lastPhysicalDeviceId="
				+ lastPhysicalDeviceId + ", lastInfoFileId=" + lastInfoFileId
				+ ", settlementId=" + settlementId + ", lastSyncExterSystemId="
				+ lastSyncExterSystemId + "]";
	}

	
}
