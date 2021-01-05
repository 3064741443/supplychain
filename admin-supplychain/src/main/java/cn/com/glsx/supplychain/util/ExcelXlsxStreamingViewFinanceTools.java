package cn.com.glsx.supplychain.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class ExcelXlsxStreamingViewFinanceTools{
	public static final String EXCEL_TEMPLATE_FILE_PATH_KEY = "excelTemplateFilePath";
    public static final String EXCEL_FILE_NAME_KEY = "excelFileName";
    public static final String EXCEL_SHEET_NAME_KEY = "excelSheetName";
    
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
        sheet.removeRow(sheet.getRow(1));
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


    private String[] fetchPropertyColumns(XSSFWorkbook workbook){
    	//获得模板sheet
        Sheet sheetTemplate = workbook.getSheetAt(0);
        //获取标题行，rowIndex=0代表第一行
        Row headRow = sheetTemplate.getRow(0);
        //获取属性列，rowIndex=1代表第二行
        Row propertiesRow = sheetTemplate.getRow(1);
        //获取标题行的列数
        int headColumnsLength = headRow.getLastCellNum();
        //标题行的列数组
        String[] headColumns = new String[headColumnsLength];
        String[] propertyColumns = new String[headColumnsLength];
        for (int i = 0; i < headColumnsLength; i++) {
            headColumns[i]=headRow.getCell(i).getStringCellValue();
            propertyColumns[i]=propertiesRow.getCell(i).getStringCellValue();
        }
        return propertyColumns;
    }
    
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            fieldName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
            Method method = object.getClass().getMethod("get" + fieldName);
            return  String.valueOf(method.invoke(object));
        } catch (Exception e) {
            return null;
        }
    }
    
    public void buildExcelDocument(Map<String, Object> model,
                                   Workbook workbook,
                                   FileOutputStream fileOutputStream) throws Exception {
        String fileName = model.getOrDefault(EXCEL_FILE_NAME_KEY,"my-xls-file.xlsx").toString();
        String excelTemplateFilePath = model.getOrDefault(EXCEL_TEMPLATE_FILE_PATH_KEY,"/templates/templateSalesSummarizing.xlsx").toString();
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
                    rowIndex = 1;
                    sheetIndex++;
                }
                //创建行
                Row dataRow = sheet.createRow(rowIndex);

                for (int columnIndex = 0; columnIndex < propertyColumns.length; columnIndex++) {
                    //创建单元格
                    Cell newCell = dataRow.createCell(columnIndex);
                    String cellValue = "";
                    //序号列              
                    cellValue = getFieldValueByFieldName(propertyColumns[columnIndex], vo);
                    if (cellValue == null) {
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
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
    
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
