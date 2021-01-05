package cn.com.glsx.merchant.supplychain.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @program: supplychain
 * @description：
 * @create: 2020-07-19 20:34
 * @author: luoqiang
 * @version: 1.0
 */

public class ExcelReadAndWriteUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReadAndWriteUtil.class);

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static String getPath() {
        return ExcelReadAndWriteUtil.class.getResource("/").getPath();
    }

    public static File createNewFile(String pathName) {
        File file = new File(getPath() + pathName);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    public static File readFile(String pathName) {
        return new File(getPath() + pathName);
    }

    public static File readUserHomeFile(String pathName) {
        return new File(System.getProperty("user.home") + File.separator + pathName);
    }

    /**
     * 导出 Excel ：一个 sheet，带表头, 带序号
     *
     * @param response  HttpServletResponse
     * @param data      数据 list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param clazz     映射实体类，Excel 模型
     */
    public static void writeExcel(HttpServletResponse response, List<? extends Object> data,
                                  String fileName, String sheetName, Class clazz) throws Exception {
        EasyExcel.write(getOutputStream(fileName, response), clazz).registerWriteHandler(new CustomRowWriteHandler()).sheet(sheetName).doWrite(data);
    }

    /**
     * 导出 Excel ：一个 sheet，带表头,不带序号
     *
     * @param response  HttpServletResponse
     * @param data      数据 list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param clazz     映射实体类，Excel 模型
     */
    public static void writeExcelNoNumber(HttpServletResponse response, List<? extends Object> data,
                                          String fileName, String sheetName, Class clazz) throws Exception {
        EasyExcel.write(getOutputStream(fileName, response), clazz).sheet(sheetName).doWrite(data);
    }


    /**
     * 导出 Excel ：一个 sheet，带表头,根据模板导出
     *
     * @param response  HttpServletResponse
     * @param data      数据 list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param clazz     映射实体类，Excel 模型
     */
    public static void writeExcelWithTemplate(HttpServletResponse response, List<? extends Object> data, String templateFileName,
                                              String fileName, String sheetName, Class clazz) throws Exception {
        //表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置内容靠左对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).withTemplate(templateFileName).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy).doWrite(data);
    }


    /**
     * description:   Excel导出
     * version: 1.0
     * date 2020/7/19 23:46
     * author luoqiang
     *
     * @param response         HttpServletResponse
     * @param data             数据 list，每个元素为一个 Object
     * @param templateFileName 模板名字
     * @param fileName         导出文件的名字
     * @param sheetName        表单名字
     * @param clazz            导出的实体类
     * @return void
     */
    public static void writeExcel(HttpServletResponse response, List<? extends Object> data, String templateFileName, String fileName, String sheetName, Class clazz) throws Exception {
        //表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置内容靠左对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).withTemplate(templateFileName).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy).doWrite(data);
    }


    /**
     * 导出文件时为Writer生成OutputStream
     *
     * @param response
     * @return
     */
    private static OutputStream getOutputStream(HttpServletResponse response) throws Exception {
        try {
            response.setHeader("Content-disposition", "attachment; filename=" + "DistributionReconciliationDetails.xlsx");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头
            return response.getOutputStream();
        } catch (IOException e) {
            throw new Exception("导出excel表格失败!", e);
        }
    }

    /**
     * 导出文件时为Writer生成OutputStream
     *
     * @param fileName
     * @param response
     * @return
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e) {
            throw new Exception("导出excel表格失败!", e);
        }
    }
}
