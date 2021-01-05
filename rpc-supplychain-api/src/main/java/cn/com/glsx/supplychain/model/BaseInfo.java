package cn.com.glsx.supplychain.model;

import javax.persistence.Transient;

public class BaseInfo {
	@Transient
    private String modelName;
    
    @Transient
    private String typeName;
    
    @Transient
    private String configureName;
    
    @Transient  
    private String msizeName;
    
    @Transient 
    private String devTypeName;
    
    @Transient
    private String devMnumName;
    
    @Transient
    private String orNetName;
    
    @Transient
    private String cardSelfName;
    
    @Transient
    private String sourceName;
    
    @Transient
    private String screenName;
    
    @Transient
    private String orOpenName;
    
    
	public String getMsizeName() {
		return msizeName;
	}

	public void setMsizeName(String msizeName) {
		this.msizeName = msizeName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getConfigureName() {
		return configureName;
	}

	public void setConfigureName(String configureName) {
		this.configureName = configureName;
	}

	public String getDevTypeName() {
		return devTypeName;
	}

	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}

	public String getDevMnumName() {
		return devMnumName;
	}

	public void setDevMnumName(String devMnumName) {
		this.devMnumName = devMnumName;
	}

	public String getOrNetName() {
		return orNetName;
	}

	public void setOrNetName(String orNetName) {
		this.orNetName = orNetName;
	}

	public String getCardSelfName() {
		return cardSelfName;
	}

	public void setCardSelfName(String cardSelfName) {
		this.cardSelfName = cardSelfName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getOrOpenName() {
		return orOpenName;
	}

	public void setOrOpenName(String orOpenName) {
		this.orOpenName = orOpenName;
	}
	
	

}
