package cn.com.glsx.merchant.supplychain.constants;

public class MerchantConstants {
	
	/** 文件服务器地址 */
    public static final String FILE_UPLOAD_URL = "file.upload.string.url";
    
    /** 文件服务器文件压缩地址 */
    public static final String FILE_ZIP_URL = "file.zip.string.url";

	
	public class PdfKey {

        /** 文件类型 */
        public static final String FILE_TYPE = "pdf";

        /**
         * pdf生成字体配置
         */
        public static final String DIAMOND_FONT_PATH = "create.pdf.font.path";
        
        /** 文件服务器文件压缩地址 */
        public static final String FILE_ZIP_URL = "file.zip.string.url";

        /**
         * pdf生成：申请单模板
         */
        public static final String HTML_TEMPLATE_APPLY = "templates/pdf/receiveSign.html";
      
        public static final String SIGN_YEAR = "signYear";
    	public static final String SIGN_MONTH = "signMonth";
    	public static final String SIGN_ORDER_NO = "signOrderNo";
    	public static final String SIGN_ORDER_DATE = "signOrderDate";
    	public static final String SIGN_ORDER_DETAIL = "signOrderDetail";
    	public static final String SIGN_RECV_COMPANY = "signRecvCompany";
    	public static final String SIGN_RECV_CONTACT = "signRecvContact";
    	public static final String SIGN_RECV_PHONE = "signRecvPhone";
    	public static final String SIGN_RECV_ADDR = "signRecvAddr";
    	public static final String SIGN_TOTAL = "signTotal";
    	 public static final String SIGN_ORDER_DETAIL_TEMPLATE = "<tr><td style=\"width:15px;\">{0}</td><td style=\"width:64px;\">{1}</td><td style=\"width:64px;\">{2}</td><td style=\"width:340px;\">{3}</td><td style=\"width:32px;\">{4}</td><td style=\"width:96px;\">{5}</td><td style=\"width:64px;\">{6}</td><td style=\"width:64px;\">{7}</td></tr>";
       
    }
	
}
