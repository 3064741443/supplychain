package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTAttribManaSubmitVO implements Serializable{

	@ApiModelProperty(name = "attribCode", notes = "硬件配置编码等同物料编码", dataType = "string", required = true, example = "")
	private String attribCode;
	@ApiModelProperty(name = "attribName", notes = "硬件配置编码等同物料编码", dataType = "string", required = true, example = "")
	private String attribName;
	@ApiModelProperty(name = "devTypeId", notes = "设备大类型id", dataType = "int", required = true, example = "")
	private Integer devTypeId;
	@ApiModelProperty(name = "typeId", notes = "硬件类型id", dataType = "int", required = false, example = "")
	private Integer typeId;
	@ApiModelProperty(name = "modelId", notes = "机型id", dataType = "int", required = false, example = "")
    private Integer modelId;
	@ApiModelProperty(name = "configureId", notes = "设备配置id", dataType = "int", required = false, example = "")
    private Integer configureId;
	@ApiModelProperty(name = "msizeId", notes = "设备尺寸id", dataType = "int", required = false, example = "")
    private Integer msizeId;
	@ApiModelProperty(name = "boardVersion", notes = "主板版本", dataType = "string", required = false, example = "")
    private String boardVersion;
	@ApiModelProperty(name = "mcuVersion", notes = "MCU版本", dataType = "string", required = false, example = "")
    private String mcuVersion;
	@ApiModelProperty(name = "fastenerVersion", notes = "固件版本(OS系统版本)", dataType = "string", required = false, example = "")
    private String fastenerVersion;
	@ApiModelProperty(name = "softVersion", notes = "软件应用版本(didihu应用版本等)", dataType = "string", required = false, example = "")
    private String softVersion;
	@ApiModelProperty(name = "devMnumId", notes = "设备型号名称id", dataType = "int", required = false, example = "")
    private Integer devMnumId;
	@ApiModelProperty(name = "orNetId", notes = "是否联网id", dataType = "int", required = false, example = "")
    private Integer orNetId;
	@ApiModelProperty(name = "cardSelfId", notes = "自有卡还是外部卡id", dataType = "int", required = false, example = "")
    private Integer cardSelfId;
	@ApiModelProperty(name = "sourceId", notes = "有源还是无源id", dataType = "int", required = false, example = "")
    private Integer sourceId;
	@ApiModelProperty(name = "screenId", notes = "是否有屏id", dataType = "int", required = false, example = "")
    private Integer screenId;
	@ApiModelProperty(name = "orOpenId", notes = "专用还是通用id", dataType = "int", required = false, example = "")
    private Integer orOpenId;
	@ApiModelProperty(name = "verifyIccid", notes = "描入库时后台是否需要验证iccid Y:需要验证 N:不需要", dataType = "string", required = false, example = "")
    private String verifyIccid;
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
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public Integer getConfigureId() {
		return configureId;
	}
	public void setConfigureId(Integer configureId) {
		this.configureId = configureId;
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
	public Integer getDevMnumId() {
		return devMnumId;
	}
	public void setDevMnumId(Integer devMnumId) {
		this.devMnumId = devMnumId;
	}
	public Integer getOrNetId() {
		return orNetId;
	}
	public void setOrNetId(Integer orNetId) {
		this.orNetId = orNetId;
	}
	public Integer getCardSelfId() {
		return cardSelfId;
	}
	public void setCardSelfId(Integer cardSelfId) {
		this.cardSelfId = cardSelfId;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public Integer getScreenId() {
		return screenId;
	}
	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
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
	
	public Integer getDevTypeId() {
		return devTypeId;
	}
	public void setDevTypeId(Integer devTypeId) {
		this.devTypeId = devTypeId;
	}
	@Override
	public String toString() {
		return "JXCMTAttribManaSubmitVO [attribCode=" + attribCode
				+ ", attribName=" + attribName + ", devTypeId=" + devTypeId
				+ ", typeId=" + typeId + ", modelId=" + modelId
				+ ", configureId=" + configureId + ", msizeId=" + msizeId
				+ ", boardVersion=" + boardVersion + ", mcuVersion="
				+ mcuVersion + ", fastenerVersion=" + fastenerVersion
				+ ", softVersion=" + softVersion + ", devMnumId=" + devMnumId
				+ ", orNetId=" + orNetId + ", cardSelfId=" + cardSelfId
				+ ", sourceId=" + sourceId + ", screenId=" + screenId
				+ ", orOpenId=" + orOpenId + ", verifyIccid=" + verifyIccid
				+ "]";
	}
	
	
	
}
