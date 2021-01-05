package cn.com.glsx.supplychain.util;

import cn.com.glsx.supplychain.model.am.StatementSellReconInstall;
import cn.com.glsx.supplychain.vo.StatementSellReconDetailFinancialExcelVo;
import cn.com.glsx.supplychain.vo.StatementSellReconDetailGuangHuiExcelVo;
import cn.com.glsx.supplychain.vo.StatementSellReconExcelVo;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 读取 Excel(多个 sheet)
     *
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return null;
        }
        for (Sheet sheet : reader.getSheets()) {
            if (rowModel != null) {
                sheet.setClazz(rowModel.getClass());
            }
            reader.read(sheet);
        }
        return excelListener.getDatas();
    }

    /**
     * 读取某个 sheet 的 Excel
     *
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @param sheetNo  sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel, int sheetNo) {
        return readExcel(excel, rowModel, sheetNo, 1);
    }

    /**
     * 读取某个 sheet 的 Excel
     *
     * @param excel       文件
     * @param rowModel    实体类映射，继承 BaseRowModel 类
     * @param sheetNo     sheet 的序号 从1开始
     * @param headLineNum 表头行数，默认为1
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel, int sheetNo,
                                         int headLineNum) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return null;
        }
        reader.read(new Sheet(sheetNo, headLineNum, rowModel.getClass()));
        return excelListener.getDatas();
    }

    /**
     * 导出 Excel ：一个 sheet，带表头, 带序号
     *
     * @param response  HttpServletResponse
     * @param data      数据 list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param clazz    映射实体类，Excel 模型
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
     * @param clazz    映射实体类，Excel 模型
     */
    public static void writeExcelNoNumber(HttpServletResponse response, List<? extends Object> data,
                                  String fileName, String sheetName, Class clazz) throws Exception {
        EasyExcel.write(getOutputStream(fileName, response), clazz).sheet(sheetName).doWrite(data);
    }

    /**
     * 导出 Excel ：一个 sheet，带表头,代码手动调节样式
     *
     * @param response  HttpServletResponse
     * @param data      数据 list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param clazz    映射实体类，Excel 模型
     */
    public static void writeExcelWithAuto(HttpServletResponse response, List<? extends Object> data,
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
     * @param clazz    映射实体类，Excel 模型
     */
    public static void writeExcelWithTemplate(HttpServletResponse response, List<? extends Object> data,
                                          String fileName, String sheetName, Class clazz) throws Exception {
        EasyExcel.write(getOutputStream(fileName, response), clazz).registerWriteHandler(new CustomRowWriteHandler()).sheet(sheetName).doWrite(data);
    }


 /**
  * description:   Excel导出
  * version: 1.0
  * date 2020/7/19 23:46
  * author luoqiang
  * @param response  HttpServletResponse
  * @param data       数据 list，每个元素为一个 Object
  * @param templateFileName  模板名字
  * @param fileName          导出文件的名字
  * @param sheetName         表单名字
  * @param clazz             导出的实体类
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
        //EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy).doWrite(data);
        EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).withTemplate(templateFileName).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy).doWrite(data);
    }


    /**
     * 多列表组合填充填充
     *
     * @since 2.2.0-beta1
     */
    public static void compositeFillGuangHui(HttpServletResponse response, Map<String,Object> map, String templateFileName, String fileName, String sheetName) throws Exception {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量 {前缀.} 前缀可以区分不同的list
        ExcelWriter excelWriter = EasyExcel.write( getOutputStream(response)).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        //excel数据横向填充
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        FillConfig fillConfig2 = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
        excelWriter.fill(new FillWrapper("data1", ( List<StatementSellReconExcelVo>)map.get("data1")), fillConfig, writeSheet);
        excelWriter.fill(new FillWrapper("data2", (List<StatementSellReconDetailGuangHuiExcelVo>)map.get("data2")),fillConfig2,writeSheet);
        Map<String, Object> totalMap = new HashMap<String, Object>();
        totalMap.put("total", map.get("total"));
        totalMap.put("reconTimeStart1",map.get("reconTimeStart1"));
        totalMap.put("reconTimeEnd1",map.get("reconTimeEnd1"));
        excelWriter.fill(totalMap, writeSheet);
        //EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).withTemplate(templateFileName).sheet(sheetName).doWrite(data);
        // 别忘记关闭流
        excelWriter.finish();
        return;
    }

    public static void compositeFillFinancial(HttpServletResponse response, Map<String,Object> map, String templateFileName, String fileName, String sheetName) throws Exception {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量 {前缀.} 前缀可以区分不同的list
        ExcelWriter excelWriter = EasyExcel.write( getOutputStream(response)).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        FillConfig fillConfig2 = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
        excelWriter.fill(new FillWrapper("data3", ( List<StatementSellReconExcelVo>)map.get("data3")), fillConfig, writeSheet);
        excelWriter.fill(new FillWrapper("data4", (List<StatementSellReconDetailFinancialExcelVo>)map.get("data4")),fillConfig2, writeSheet);
        excelWriter.fill(new FillWrapper("data5", (List<StatementSellReconInstall>)map.get("data5")),fillConfig2, writeSheet);
        Map<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("total", map.get("total"));
        newMap.put("hardwaretotalCount", map.get("hardwaretotalCount"));
        newMap.put("serviceTotalCount", map.get("serviceTotalCount"));
        newMap.put("totalPriceCount", map.get("totalPriceCount"));
        newMap.put("installTotalPriceCount",map.get("installTotalPriceCount"));
        newMap.put("summaryPriceCount",map.get("summaryPriceCount"));
        newMap.put("reconTimeStart2",map.get("reconTimeStart2"));
        newMap.put("reconTimeEnd2",map.get("reconTimeEnd2"));
        excelWriter.fill(newMap, writeSheet);
        //EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).withTemplate(templateFileName).sheet(sheetName).doWrite(data);
        // 别忘记关闭流
        excelWriter.finish();
        return;
    }


    /**
     * 导出文件时为Writer生成OutputStream
     *
     * @param response
     * @return
     */
    private static OutputStream getOutputStream(HttpServletResponse response) throws Exception {
        try {
//            fileName = URLEncoder.encode(fileName, "UTF-8");
//            response.setContentType("application/vnd.ms-excel");
//            response.setCharacterEncoding("utf8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
//            response.setHeader("Pragma", "public");
//            response.setHeader("Cache-Control", "no-store");
//            response.addHeader("Cache-Control", "max-age=0");
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
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e) {
            throw new Exception("导出excel表格失败!", e);
        }
    }

//    /**
//     * 导出文件时为Writer生成OutputStream
//     */
//    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
//        //创建本地文件
//        String filePath = fileName + ".xlsx";
//        File dbfFile = new File(filePath);
//        try {
//            if (!dbfFile.exists() || dbfFile.isDirectory()) {
//                dbfFile.createNewFile();
//            }
//            fileName = new String(filePath.getBytes(), StandardCharsets.ISO_8859_1);
//            response.addHeader("Content-Disposition", "filename=" + fileName);
//            return response.getOutputStream();
//        } catch (IOException e) {
//            throw new ExcelException("创建文件失败！");
//        }
//    }

    /**
     * 导出 Excel ：多个 sheet，带表头
     *
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object    映射实体类，Excel 模型
     */
    public static ExcelWriterFactroy writeExcelWithSheets(HttpServletResponse response, List<? extends BaseRowModel> list,
                                                          String fileName, String sheetName, BaseRowModel object) throws Exception {
        ExcelWriterFactroy writer = new ExcelWriterFactroy(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        return writer;
    }


    /**
     * 返回 ExcelReader
     *
     * @param excel         需要解析的 Excel 文件
     * @param excelListener new ExcelListener()
     */
    private static ExcelReader getReader(MultipartFile excel,
                                         ExcelListener excelListener) {
        String filename = excel.getOriginalFilename();
        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
            throw new ExcelException("文件格式错误！");
        }
        InputStream inputStream;
        try {
            inputStream = new BufferedInputStream(excel.getInputStream());
            return new ExcelReader(inputStream, null, excelListener, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
