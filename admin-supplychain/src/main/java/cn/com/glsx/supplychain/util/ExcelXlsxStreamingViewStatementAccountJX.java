package cn.com.glsx.supplychain.util;

import cn.com.glsx.supplychain.config.Constants;
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
import java.util.List;
import java.util.Map;

@Component
public class ExcelXlsxStreamingViewStatementAccountJX extends AbstractXlsxStreamingView {
    public static final String EXCEL_TEMPLATE_FILE_PATH_KEY = "excelTemplateFilePath";
    public static final String EXCEL_FILE_NAME_KEY = "excelFileName";
    public static final String EXCEL_SHEET_NAME_KEY = "excelSheetName";

    private XSSFWorkbook initWorkbook(String excelTemplateFilePath) throws Exception {
        //读取导出模板文件
        Resource resource = new ClassPathResource(excelTemplateFilePath);
        // 创建FileInputStream 对象用于读取excel 文件
        FileInputStream inputStream = new FileInputStream(resource.getFile());
        //创建工作薄
        XSSFWorkbook workbookTemplate = new XSSFWorkbook(inputStream);
        Sheet sheet=workbookTemplate.getSheetAt(0);
        Row row1=sheet.createRow(1);
        row1.createCell(0).setCellValue("kehu");
        row1.createCell(1).setCellValue("11");
        row1.createCell(6).setCellValue("销售组织");
        row1.createCell(7).setCellValue("11");

        Row row2=sheet.createRow(2);
        row2.createCell(0).setCellValue("客户地址");
        row2.createCell(1).setCellValue("11");
        row2.createCell(6).setCellValue("销售组地址");
        row2.createCell(7).setCellValue("11");

        Row row3=sheet.createRow(3);
        row3.createCell(0).setCellValue("客户联系人");
        row3.createCell(1).setCellValue("11");
        row3.createCell(6).setCellValue("销售组联系人");
        row3.createCell(7).setCellValue("11");

        Row row4=sheet.createRow(4);
        row4.createCell(0).setCellValue("客户联系电话");
        row1.createCell(1).setCellValue("");
        row4.createCell(6).setCellValue("销售组联系电话");
        row4.createCell(7).setCellValue("");


        inputStream.close();
        return workbookTemplate;
    }

    private Sheet cloneSheet(XSSFWorkbook workbook, int sheetIndex, String sheetName) {
        //复制sheet：当第一条数据或数据超出单Sheet存放数据范围时，创建sheet
        Sheet sheet = workbook.cloneSheet(0);
        //sheet命名
        //workbook.setSheetName(sheetIndex, sheetName + (sheetIndex == 1 ? "" : (sheetIndex - 1)));
        workbook.setSheetName(sheetIndex,"sheet");
        //删除第二行中的内容
        sheet.removeRow(sheet.getRow(7));
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
     *
     * @param workbook
     * @return
     */
    private String[] fetchPropertyColumns(XSSFWorkbook workbook) {
        //获得模板sheet
        Sheet sheetTemplate = workbook.getSheetAt(0);
        //获取标题行，rowIndex=6代表第七行
        Row headRow = sheetTemplate.getRow(6);
        //获取属性列，rowIndex=7代表第八行
        Row propertiesRow = sheetTemplate.getRow(7);
        //获取标题行的列数
        int headColumnsLength = headRow.getLastCellNum();
        //标题行的列数组
        String[] headColumns = new String[headColumnsLength];
        String[] propertyColumns = new String[headColumnsLength];
        for (int i = 0; i < headColumnsLength; i++) {
            headColumns[i] = headRow.getCell(i).getStringCellValue();
            propertyColumns[i] = propertiesRow.getCell(i).getStringCellValue();
        }
        return propertyColumns;
    }


    /**
     * 通过反响代理获取类中属性对应的值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = object.getClass().getMethod("get" + fieldName);
            return String.valueOf(method.invoke(object));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void buildExcelDocument(Map<String, Object> model,
                                   Workbook workbook,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        String fileName = model.getOrDefault(EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_STATEMENT_ACCOUNT_JX).toString();
        String excelTemplateFilePath = model.getOrDefault(EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_STATEMENT_ACCOUNT_JX).toString();
        String sheetName = model.getOrDefault(EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_STATEMENT_ACCOUNT_JX).toString();
        List<Object> objs = (List<Object>) model.get("objs");

        XSSFWorkbook xssfWorkbook = initWorkbook(excelTemplateFilePath);
        String[] propertyColumns = fetchPropertyColumns(xssfWorkbook);
        //String []propertyColumns2=fet
        CellStyle cellStyle = customeCellStyle(xssfWorkbook);
        // 遍历集合数据，产生数据行，填充Excel
        int rowIndex = 0;
        int sheetIndex=1;
        Sheet sheet = cloneSheet(xssfWorkbook, sheetIndex, sheetName + (sheetIndex == 1 ? "" : (sheetIndex - 1)));
        Object sales=model.get("sales");
        //创建行
        Row row = sheet.createRow(rowIndex);
        for (int columnIndex = 0; columnIndex < propertyColumns.length; columnIndex++) {
            //创建单元格
            Cell newCell = row.createCell(columnIndex);
            String cellValue = getFieldValueByFieldName(propertyColumns[columnIndex], sales);
            if (cellValue == null) {
                cellValue = "--";
            }
            //单元格赋值
            newCell.setCellValue(cellValue);
            //单元格格式设置
            newCell.setCellStyle(cellStyle);
        }
        //sheet索引,从模板之后开始，所以为1
        if (objs != null && objs.size() > 0) {
            for (int i = 0; i < objs.size(); i++) {
                Object vo = objs.get(i);
                if (rowIndex == 0 || rowIndex == 65535) {
                    //数据内容从 rowIndex=7（第八行）开始
                    rowIndex = 7;
                }
                //创建行
                Row dataRow = sheet.createRow(rowIndex);

                for (int columnIndex = 0; columnIndex < propertyColumns.length; columnIndex++) {
                    //创建单元格
                    Cell newCell = dataRow.createCell(columnIndex);
                    String cellValue = getFieldValueByFieldName(propertyColumns[columnIndex], vo);
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
        } else {
            sheet = cloneSheet(xssfWorkbook, sheetIndex, sheetName + (sheetIndex == 1 ? "" : (sheetIndex - 1)));
        }
        //删除模板
        xssfWorkbook.removeSheetAt(0);
        response.setContentType("application/x-excel");
        //change the file name;
        response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes("GBK"), "iso8859-1") + "\"");
        OutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
