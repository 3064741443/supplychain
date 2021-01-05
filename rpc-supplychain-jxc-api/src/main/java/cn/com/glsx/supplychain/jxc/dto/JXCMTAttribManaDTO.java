package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JXCMTAttribManaDTO extends JXCMTBaseDTO implements Serializable{
	@ApiModelProperty(name = "attribCode", notes = "硬件配置编码等同物料编码", dataType = "string", required = false, example = "")
	private String attribCode;
	@ApiModelProperty(name = "attribName", notes = "硬件配置编码等同物料编码", dataType = "string", required = false, example = "")
	private String attribName;
	@ApiModelProperty(name = "typeName", notes = "硬件类型名称", dataType = "string", required = false, example = "")
	private String typeName;
	@ApiModelProperty(name = "typeId", notes = "硬件类型id", dataType = "string", required = false, example = "")
	private Integer typeId;
	@ApiModelProperty(name = "modelName", notes = "机型", dataType = "string", required = false, example = "")
    private String modelName;
	@ApiModelProperty(name = "modelId", notes = "机型id", dataType = "string", required = false, example = "")
    private Integer modelId;
	@ApiModelProperty(name = "configureName", notes = "设备配置", dataType = "string", required = false, example = "")
    private String configureName;
	@ApiModelProperty(name = "configureId", notes = "设备配置id", dataType = "string", required = false, example = "")
    private Integer configureId;
	@ApiModelProperty(name = "msizeName", notes = "设备尺寸", dataType = "string", required = false, example = "")
    private String msizeName;
	@ApiModelProperty(name = "msizeId", notes = "设备尺寸id", dataType = "string", required = false, example = "")
    private Integer msizeId;
	@ApiModelProperty(name = "boardVersion", notes = "主板版本", dataType = "string", required = false, example = "")
    private String boardVersion;
	@ApiModelProperty(name = "mcuVersion", notes = "MCU版本", dataType = "string", required = false, example = "")
    private String mcuVersion;
	@ApiModelProperty(name = "fastenerVersion", notes = "固件版本(OS系统版本)", dataType = "string", required = false, example = "")
    private String fastenerVersion;
	@ApiModelProperty(name = "fastenerVersion", notes = "软件应用版本(didihu应用版本等)", dataType = "string", required = false, example = "")
    private String softVersion;
	@ApiModelProperty(name = "devTypeName", notes = "设备大类名称", dataType = "string", required = false, example = "")
    private String devTypeName;
	@ApiModelProperty(name = "devTypeId", notes = "设备大类id", dataType = "string", required = false, example = "")
	private Integer devTypeId;
	@ApiModelProperty(name = "devMnumName", notes = "设备型号名称", dataType = "string", required = false, example = "")
    private String devMnumName;
	@ApiModelProperty(name = "devMnumId", notes = "设备型号名称id", dataType = "string", required = false, example = "")
    private Integer devMnumId;
	@ApiModelProperty(name = "orNetName", notes = "是否联网", dataType = "string", required = false, example = "")
    private String orNetName;
	@ApiModelProperty(name = "orNetId", notes = "是否联网id", dataType = "string", required = false, example = "")
    private Integer orNetId;
	@ApiModelProperty(name = "cardSelfName", notes = "自有卡还是外部卡", dataType = "string", required = false, example = "")
    private String cardSelfName;
	@ApiModelProperty(name = "cardSelfId", notes = "自有卡还是外部卡id", dataType = "string", required = false, example = "")
    private Integer cardSelfId;
	@ApiModelProperty(name = "sourceName", notes = "有源还是无源", dataType = "string", required = false, example = "")
    private String sourceName;
	@ApiModelProperty(name = "sourceId", notes = "有源还是无源id", dataType = "string", required = false, example = "")
    private Integer sourceId;
	@ApiModelProperty(name = "screenName", notes = "是否有屏", dataType = "string", required = false, example = "")
    private String screenName;
	@ApiModelProperty(name = "screenId", notes = "是否有屏id", dataType = "string", required = false, example = "")
    private Integer screenId;
	@ApiModelProperty(name = "orOpenName", notes = "专用还是通用", dataType = "string", required = false, example = "")
    private String orOpenName;
	@ApiModelProperty(name = "orOpenId", notes = "专用还是通用id", dataType = "string", required = false, example = "")
    private Integer orOpenId;
	@ApiModelProperty(name = "verifyIccid", notes = "描入库时后台是否需要验证iccid Y:需要验证 N:不需要", dataType = "string", required = false, example = "")
    private String verifyIccid;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;

	public String getAttribCode() {
		return attribCode;
	}
	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}
	public String getAttribName() {
		return attribName;
	}
	public void setAttribName(String attribName) {
		this.attribName = attribName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public String getConfigureName() {
		return configureName;
	}
	public void setConfigureName(String configureName) {
		this.configureName = configureName;
	}
	public Integer getConfigureId() {
		return configureId;
	}
	public void setConfigureId(Integer configureId) {
		this.configureId = configureId;
	}
	public String getMsizeName() {
		return msizeName;
	}
	public void setMsizeName(String msizeName) {
		this.msizeName = msizeName;
	}
	public Integer getMsizeId() {
		return msizeId;
	}
	public void setMsizeId(Integer msizeId) {
		this.msizeId = msizeId;
	}
	public String getBoardVersion() {
		return boardVersion;
	}
	public void setBoardVersion(String boardVersion) {
		this.boardVersion = boardVersion;
	}
	public String getMcuVersion() {
		return mcuVersion;
	}
	public void setMcuVersion(String mcuVersion) {
		this.mcuVersion = mcuVersion;
	}
	public String getFastenerVersion() {
		return fastenerVersion;
	}
	public void setFastenerVersion(String fastenerVersion) {
		this.fastenerVersion = fastenerVersion;
	}
	public String getSoftVersion() {
		return softVersion;
	}
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	public String getDevTypeName() {
		return devTypeName;
	}
	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}
	public Integer getDevTypeId() {
		return devTypeId;
	}
	public void setDevTypeId(Integer devTypeId) {
		this.devTypeId = devTypeId;
	}
	public String getDevMnumName() {
		return devMnumName;
	}
	public void setDevMnumName(String devMnumName) {
		this.devMnumName = devMnumName;
	}
	public Integer getDevMnumId() {
		return devMnumId;
	}
	public void setDevMnumId(Integer devMnumId) {
		this.devMnumId = devMnumId;
	}
	public String getOrNetName() {
		return orNetName;
	}
	public void setOrNetName(String orNetName) {
		this.orNetName = orNetName;
	}
	public Integer getOrNetId() {
		return orNetId;
	}
	public void setOrNetId(Integer orNetId) {
		this.orNetId = orNetId;
	}
	public String getCardSelfName() {
		return cardSelfName;
	}
	public void setCardSelfName(String cardSelfName) {
		this.cardSelfName = cardSelfName;
	}
	public Integer getCardSelfId() {
		return cardSelfId;
	}
	public void setCardSelfId(Integer cardSelfId) {
		this.cardSelfId = cardSelfId;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public Integer getScreenId() {
		return screenId;
	}
	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
	}
	public String getOrOpenName() {
		return orOpenName;
	}
	public void setOrOpenName(String orOpenName) {
		this.orOpenName = orOpenName;
	}
	public Integer getOrOpenId() {
		return orOpenId;
	}
	public void setOrOpenId(Integer orOpenId) {
		this.orOpenId = orOpenId;
	}
	public String getVerifyIccid() {
		return verifyIccid;
	}
	public void setVerifyIccid(String verifyIccid) {
		this.verifyIccid = verifyIccid;
	}
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "JXCMTAttribManaDTO [attribCode=" + attribCode + ", attribName="
				+ attribName + ", typeName=" + typeName + ", typeId=" + typeId
				+ ", modelName=" + modelName + ", modelId=" + modelId
				+ ", configureName=" + configureName + ", configureId="
				+ configureId + ", msizeName=" + msizeName + ", msizeId="
				+ msizeId + ", boardVersion=" + boardVersion + ", mcuVersion="
				+ mcuVersion + ", fastenerVersion=" + fastenerVersion
				+ ", softVersion=" + softVersion + ", devTypeName="
				+ devTypeName + ", devTypeId=" + devTypeId + ", devMnumName="
				+ devMnumName + ", devMnumId=" + devMnumId + ", orNetName="
				+ orNetName + ", orNetId=" + orNetId + ", cardSelfName="
				+ cardSelfName + ", cardSelfId=" + cardSelfId + ", sourceName="
				+ sourceName + ", sourceId=" + sourceId + ", screenName="
				+ screenName + ", screenId=" + screenId + ", orOpenName="
				+ orOpenName + ", orOpenId=" + orOpenId + ", verifyIccid="
				+ verifyIccid + "]";
	}
	
	
	
	
	
	
}
