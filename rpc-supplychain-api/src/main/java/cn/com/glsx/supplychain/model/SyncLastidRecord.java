package cn.com.glsx.supplychain.model;



import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName SyncLastidRecord
 * @Author admin
 * @Param
 * @Date 2019/7/8 17:34
 * @Version
 **/
@Table(name = "sync_lastid_record")
public class SyncLastidRecord implements Serializable {
    /**
     * ID
     */
    private Integer id;
    /**
     * 向上网卡管理同步数据时同步device_info表最后同步ID
     */
    private Integer lastFlowCardId;
    /**
     * 向老设备管理同步数据时同步device_info表最后同步ID
     */
    private Integer lastPhysicalDeviceId;
    /**
     * device_info向device_file同步数据id标识
     */
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
}
