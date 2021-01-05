package cn.com.glsx.supplychain.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Component
public class ExcelXlsxStreamingViewStatementCollectSplit extends AbstractXlsxStreamingView {
    public static final String EXCEL_TEMPLATE_FILE_PATH_KEY = "excelTemplateFilePath";
    public static final String EXCEL_FILE_NAME_KEY = "excelFileName";
    public static final String EXCEL_SHEET_NAME_KEY = "excelSheetName";

    /**
     * 获取导出Excel的模板
     * @param excelTemplateFilePath
     * @return
     * @throws Exception
     */
    private XSSFWorkbook initWorkbook(String excelTemplateFilePath) throws Exception {
        //读取导出模板文件
        Resource resource = new ClassPathResource(excelTemplateFilePath);
        FileInputStream inputStream = new FileInputStream(resource.getFile());
        XSSFWorkbook workbookTemplate = new XSSFWorkbook(inputStream);
        inputStream.close();
        return workbookTemplate;
    }

    private Sheet cloneSheet(XSSFWorkbook workbook, int sheetIndex, String sheetName){
        //复制sheet：当第一条数据或数据超出单Sheet存放数据范围时，创建sheet
        Sheet sheet = workbook.cloneSheet(0);
        //sheet命名
        workbook.setSheetName(sheetIndex, sheetName + (sheetIndex == 1 ? "" : (sheetIndex - 1)));
        //删除第二行中的内容
      //  sheet.removeRow(sheet.getRow(1));
        return sheet;
    }
    /**
     * 自定义cell style
     * @param workbook
     * @return
     */
    private CellStyle customeCellStyle(XSSFWorkbook workbook){
        // 单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        //设置自动换行
        cellStyle.setWrapText(true);

        //设置字体
        Font cellFont = workbook.createFont();
        //cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellFont.setBold(true);
        cellStyle.setFont(cellFont);
        return cellStyle;
    }


    /**
     * 获取Excel模板中的属性列
     * @param workbook
     * @return
     */
    private String[] fetchPropertyColumns(XSSFWorkbook workbook){
        //获得模板sheet
        Sheet sheetTemplate = workbook.getSheetAt(0);
        //获取属性列，rowIndex=0代表第一行
        Row propertiesRow = sheetTemplate.getRow(0);
        //获取标题行的列数
        int headColumnsLength = propertiesRow.getLastCellNum();       
        String[] propertyColumns = new String[headColumnsLength];
        for (int i = 0; i < headColumnsLength; i++) {      
            propertyColumns[i]=propertiesRow.getCell(i).getStringCellValue();
        }
        return propertyColumns;
    }
    
    /**
     * 属性替换表
     * @param fieldName
     * @param object
     * @return
     */
    private String convertFieldName(String fieldName){
    	if(fieldName.equals("FBillHead(SAL_SaleOrder)")){
    		return "Number";
    	}else if(fieldName.equals("FBillTypeID")){
    		return "BillTypeCode";
    	}else if(fieldName.equals("FBillTypeID#Name")){
    		return "BillTypeName";
    	}else if(fieldName.equals("FBillNo")){
    		return "BillNumber";
    	}else if(fieldName.equals("FDate")){
    		return "Time";
    	}else if(fieldName.equals("FSaleOrgId")){
    		return "SalesCode";
    	}else if(fieldName.equals("FSaleOrgId#Name")){
    		return "SalesName";
    	}else if(fieldName.equals("FCustId")){
    		return "CustomerCode";
    	}else if(fieldName.equals("FCustId#Name")){
    		return "CustomerName";
    	}else if(fieldName.equals("FSaleGroupId")){
    		return "SaleGroupCode";
    	}else if(fieldName.equals("FSaleGroupId#Name")){
    		return "SaleGroupName";
    	}else if(fieldName.equals("*Split*1")){
    		return "CellOne";
    	}else if(fieldName.equals("FSaleOrderFinance")){
    		return "SaleOrderFinance";
    	}else if(fieldName.equals("FSettleCurrId")){
    		return "StatementCurrencyCode";
    	}else if(fieldName.equals("FSettleCurrId#Name")){
    		return "StatementCurrencyName";
    	}else if(fieldName.equals("*Split*2")){
    		return "CellTwo";
    	}else if(fieldName.equals("FSaleOrderEntry")){
    		return "SaleOrderEntry";
    	}else if(fieldName.equals("FMaterialId")){
    		return "MaterialCode";
    	}else if(fieldName.equals("FMaterialId#Name")){
    		return "MaterialName";
    	}else if(fieldName.equals("FUnitID")){
    		return "SalesUnitCode";
    	}else if(fieldName.equals("FUnitID#Name")){
    		return "SalesUnitName";
    	}else if(fieldName.equals("FQty")){
    		return "SalesQuantity";
    	}else if(fieldName.equals("FTaxPrice")){
    		return "Price";
    	}else if(fieldName.equals("FIsFree")){
    		return "Gift";
    	}else if(fieldName.equals("FEntryTaxRate")){
    		return "TaxRate";
    	}else if(fieldName.equals("FDeliveryDate")){
    		return "TakeGoodsDate";
    	}else if(fieldName.equals("FSettleOrgIds")){
    		return "StatementOrganizeCode";
    	}else if(fieldName.equals("FSettleOrgIds#Name")){
    		return "StatementOrganizeName";
    	}else if(fieldName.equals("FReserveType")){
    		return "ReserveType";
    	}else if(fieldName.equals("F_AAA_Base")){
    		return "WarehouseCode";
    	}else if(fieldName.equals("F_AAA_Base#Name")){
    		return "WarehouseName";
    	}else if(fieldName.equals("*Split*4")){
    		return "CellThree";
    	}else if(fieldName.equals("FPlanQty")){
    		return "Quantity";
    	}
		return fieldName;	
    }

    /**
     * 通过反响代理获取类中属性对应的值
     * @param fieldName
     * @param object
     * @return
     */
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
       //     fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
        	fieldName = convertFieldName(fieldName);
            Method method = object.getClass().getMethod("get" + fieldName);
            return  String.valueOf(method.invoke(object));
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public void buildExcelDocument(Map<String, Object> model,
                                   Workbook workbook,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        String fileName = model.getOrDefault(EXCEL_FILE_NAME_KEY,"my-xls-file.xlsx").toString();
        String excelTemplateFilePath = model.getOrDefault(EXCEL_TEMPLATE_FILE_PATH_KEY,"/templates/templateStatementCollectionSplitExport.xlsx").toString();
        String sheetName = model.getOrDefault(EXCEL_SHEET_NAME_KEY,"sheet").toString();
        List<Object> objs = (List<Object>)model.get("objs");

        XSSFWorkbook xssfWorkbook = initWorkbook(excelTemplateFilePath);
        String[] propertyColumns = fetchPropertyColumns(xssfWorkbook);
        CellStyle cellStyle = customeCellStyle(xssfWorkbook);
        // 遍历集合数据，产生数据行，填充Excel
        int rowIndex = 0;
        Sheet sheet = null;
        //sheet索引,从模板之后开始，所以为1
        int sheetIndex=1;
        if(objs != null && objs.size()>0) {
            for (int i = 0; i < objs.size(); i++) {
                Object vo = objs.get(i);
                if (rowIndex == 0 || rowIndex == 65535) {
                    sheet = cloneSheet(xssfWorkbook,sheetIndex,sheetName + (sheetIndex == 1 ? "" : (sheetIndex - 1)));
                    //数据内容从 rowIndex=1（第二行）开始
                    rowIndex = 2;
                    sheetIndex++;
                }
                //创建行
                Row dataRow = sheet.createRow(rowIndex);

                DataFormat  format = xssfWorkbook.createDataFormat();

                cellStyle.setDataFormat(format.getFormat("@"));

                for (int columnIndex = 0; columnIndex < propertyColumns.length; columnIndex++) {
                    //创建单元格
                    Cell newCell = dataRow.createCell(columnIndex);
                    newCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    String cellValue = "";
                    //序号列
                    cellValue = getFieldValueByFieldName(propertyColumns[columnIndex], vo);
                    if (cellValue == null || cellValue.equals("null")) {
                        cellValue = "--";
                    }

                    //单元格赋值
                    newCell.setCellValue(cellValue);
                    //单元格格式设置
                    newCell.setCellStyle(cellStyle);
                }
                rowIndex++;
            }
        }else{
            sheet = cloneSheet(xssfWorkbook,sheetIndex,sheetName + (sheetIndex == 1 ? "" : (sheetIndex - 1)));
        }
        //删除模板
        xssfWorkbook.removeSheetAt(0);
        response.setContentType("application/x-excel");
        //change the file name;
        response.setHeader("Content-disposition", "attachment;filename=\""+new String(fileName.getBytes("GBK"), "iso8859-1")+"\"");
        OutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
    /*
    @Override
    public void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String fileName = model.getOrDefault(EXCEL_FILE_NAME_KEY,"my-xls-file.xlsx").toString();
        response.setContentType("application/vnd.ms-excel");
        //change the file name;
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

        OutputStream ouputStream = response.getOutputStream();

        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }*/

    private static void deleteKeyOfMap(Map<String,Object> paramsMap, String deleteKey){
        Iterator<String> iter = paramsMap.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            if(deleteKey.equals(key)){
                iter.remove();
            }
        }
    }
}
