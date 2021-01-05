package glsx.com.cn.task.model;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sysnc_monitor_record")
public class SysncMonitorRecord {
	@Id
    private Integer id;

    private String flagDesc;

    private Integer monitorFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlagDesc() {
        return flagDesc;
    }

    public void setFlagDesc(String flagDesc) {
        this.flagDesc = flagDesc == null ? null : flagDesc.trim();
    }

    public Integer getMonitorFlag() {
        return monitorFlag;
    }

    public void setMonitorFlag(Integer monitorFlag) {
        this.monitorFlag = monitorFlag;
    }
}