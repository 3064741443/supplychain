package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

/**
 * <P>Description： 文件上传参数</P>
 *
 * @author Alvin
 * @version 1.0
 */
public class FileUploadVO implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 传入的要后台生成的文件名的前缀（不包含扩展名的部分）
	 */
	private String preFileName;
	/**
	 * 传入的文件要保持的路径
	 */
	private String savePath;
	/**
	 * 返回系统生成的文件名
	 */
	private String outputFileName;
	/**
	 * 返回系统生成文件名的前缀（不包含扩展名的部分）
	 */
	private String outputPreFileName;
	/**
	 * 上传的文件的文件名
	 */
	private String fileUploadName;
	/**
	 * 上传文件的大小限制(单位:KB)  
	 */
	private long fileSize;
	
	
	public String getPreFileName() {
		return preFileName;
	}

	public void setPreFileName(String preFileName) {
		this.preFileName = preFileName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getOutputPreFileName() {
		return outputPreFileName;
	}

	public void setOutputPreFileName(String outputPreFileName) {
		this.outputPreFileName = outputPreFileName;
	}

	public String getFileUploadName() {
		return fileUploadName;
	}

	public void setFileUploadName(String fileUploadName) {
		this.fileUploadName = fileUploadName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "FileUploadVO [preFileName=" + preFileName + ", savePath=" + savePath + ", outputFileName=" + outputFileName + ", outputPreFileName=" + outputPreFileName
				+ ", fileUploadName=" + fileUploadName + ", fileSize=" + fileSize + "]";
	}
}
