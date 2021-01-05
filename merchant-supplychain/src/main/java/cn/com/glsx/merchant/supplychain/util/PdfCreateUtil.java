package cn.com.glsx.merchant.supplychain.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.env.Environment;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.xhtmlrenderer.pdf.ITextRenderer;

import cn.com.glsx.merchant.supplychain.constants.MerchantConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;


public class PdfCreateUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdfCreateUtil.class);
	
	private static String default_path = "E:\\svn_supplychain\\trunk\\java\\merchant-supplychain\\src\\main\\resources\\templates\\ttc\\simsun.ttc";
	
	private static PdfCreateUtil pdfCreateAPI;
	
	private Environment env;
	
	public static PdfCreateUtil getInstance() {
		if (pdfCreateAPI == null) {
			return new PdfCreateUtil();
		}
		return pdfCreateAPI;
	}
	
	/**
	 * pdf预览
	 * 
	 * @param tmpl
	 * @param map
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public byte[] createPDF(String tmpl, Map<String, Object> map) {

		byte[] pdfByte = null;

		InputStream inputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		File createTempFile = null;
		FileWriter fileWriter = null;
		// 然后将 html 转成 pdf
		// String filePath = null;
		try {
			// freemaker 模板变量文件, 先生成 html ,无变量文件
			Configuration configuration = new Configuration(
					Configuration.VERSION_2_3_0);
			configuration.setDefaultEncoding("utf-8");
			configuration.setClassLoaderForTemplateLoading(this.getClass()
					.getClassLoader(), "/");
			Template template = configuration.getTemplate(tmpl);
			createTempFile = File.createTempFile(tmpl, "pdfhtmltemp");
			fileWriter = new FileWriter(createTempFile);
			template.process(map, fileWriter);

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(createTempFile);
			renderer.getSharedContext().setBaseURL(
					this.getClass().getResource("/").toURI().toString());

			byteArrayOutputStream = new ByteArrayOutputStream();
			String configPath = "";
			String fontPath = getConfigFontPath();
			LOGGER.info("create pdf font:{}", fontPath);
			// 解决中文支持问题
			org.xhtmlrenderer.pdf.ITextFontResolver fontResolver = renderer
					.getFontResolver();
			// fontResolver.addFont("/simsun.ttc", BaseFont.IDENTITY_H,
			// BaseFont.NOT_EMBEDDED);
			fontResolver.addFont(fontPath, BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			renderer.layout();
			renderer.createPDF(byteArrayOutputStream);
			// inputStream = new
			// ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			pdfByte = byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("create pdf document exception:{}", e.getMessage(), e);
		} finally {
			if (null != fileWriter) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != createTempFile) {
				// 删除 html 文件
				createTempFile.delete();
			}
			if (null != byteArrayOutputStream) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return pdfByte;
	}

	/**
	 * 获取字体路径
	 * 
	 * @return
	 */
	private String getConfigFontPath() {
		String configPath = "";
		String fontPath = "";
		try {
			configPath = env.getProperty(MerchantConstants.PdfKey.DIAMOND_FONT_PATH);
		} catch (Exception e) {
			System.out.println("==get font path exception:" + e.getMessage());
		}
		if (StringUtils.isEmpty(configPath)) {
			fontPath = default_path;
		} else {
			fontPath = configPath;
		}
		return fontPath;
	}

	/**
	 * 将文件上传至文件服务器
	 * 
	 * @param uploadData
	 * @param fileType
	 * @return
	 */
	public FileUploadServerData uploadToFileSystem(String fileName,
			byte[] uploadData, String fileType) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("systemSign", "suplymerchantpdf");
		form.add("filePath", "");
		form.add("param1", fileName);
		form.add("param2", "");
		form.add("param3", "");
		form.add("files",
				fileType + "@" + Base64.encodeBase64String(uploadData));
		return restTemplate.postForObject(
				env.getProperty(MerchantConstants.FILE_UPLOAD_URL), form,
				FileUploadServerData.class);
	}

	/**
	 * 将文件压缩至文件服务器
	 * 
	 * @param pdfPathList
	 * @param
	 * @return
	 */
	public FileUploadServerData zipToFileSystem(List pdfPathList) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("systemSign", "suplymerchantzip");
		form.add("filePath", "");
		form.add("param1", "");
		form.add("param2", "");
		form.add("param3", "");
		form.add("zipfiles", JSONArray.fromObject(pdfPathList).toString());
		return restTemplate.postForObject(
				env.getProperty(MerchantConstants.FILE_ZIP_URL), form,
				FileUploadServerData.class);
	}

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}
	
}
