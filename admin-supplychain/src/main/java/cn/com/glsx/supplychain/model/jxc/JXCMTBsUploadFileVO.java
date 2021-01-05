package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsUploadFileVO implements Serializable{

	@ApiModelProperty(name = "url", notes = "文件下载地址", dataType = "string", required = false, example = "")
	public String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "JXCMTBsUploadFileVO [url=" + url + "]";
	}
	
}
