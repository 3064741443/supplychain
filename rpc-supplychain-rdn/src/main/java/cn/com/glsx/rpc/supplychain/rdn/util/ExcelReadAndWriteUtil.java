package cn.com.glsx.rpc.supplychain.rdn.util;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.Address;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.ShipmentOrder;
import cn.com.glsx.supplychain.model.am.StatementSellReconInstall;
import cn.com.glsx.supplychain.vo.StatementSellReconDetailFinancialExcelVo;
import cn.com.glsx.supplychain.vo.StatementSellReconDetailGuangHuiExcelVo;
import cn.com.glsx.supplychain.vo.StatementSellReconExcelVo;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * 导出 Excel ：一个 sheet，带表头, 带序号
     *
     * @param fileOutputStream  HttpServletResponse
     * @param data      数据 list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param clazz    映射实体类，Excel 模型
     */
    public static void writeExcel(FileOutputStream fileOutputStream, List<? extends Object> data,
                                  String fileName, String sheetName, Class clazz) throws Exception {
        EasyExcel.write(fileOutputStream, clazz).registerWriteHandler(new CustomRowWriteHandler()).sheet(sheetName).doWrite(data);
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
     * 导出 Excel ：一个 sheet，带表头,代码手动调节样式
     *
     * @param fileOutputStream  HttpServletResponse
     * @param data      数据 list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param clazz    映射实体类，Excel 模型
     */
    public static void writeExcelWithAuto(FileOutputStream fileOutputStream, List<? extends Object> data,
                                          String fileName, String sheetName, Class clazz) throws Exception {
        EasyExcel.write(fileOutputStream, clazz).sheet(sheetName).doWrite(data);
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
     * @author: luoqiang
     * @description: 填充货物签收单信息
     * @date: 2020/9/27 17:36
     * @param response
     * @param map
     * @param templateFileName
     * @param fileName
     * @param sheetName
     * @return: void
     */
    public static void compositeFillGoodsReceipt(HttpServletResponse response, Map<String,Object> map, String templateFileName, String fileName, String sheetName) throws Exception {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量 {前缀.} 前缀可以区分不同的list
        ExcelWriter excelWriter = EasyExcel.write( getOutputStream(response)).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        FillConfig fillConfig2 = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
        excelWriter.fill(new FillWrapper("data1", ( List<Address>)map.get("addressList")), fillConfig, writeSheet);
        excelWriter.fill(new FillWrapper("data2", (List<ShipmentOrder>)map.get("shipmentOrderList")),fillConfig2, writeSheet);
        Map<String, Object> goodsReceiptMap = new HashMap<String, Object>();
        goodsReceiptMap.put("documentNo", map.get("documentNo"));
        goodsReceiptMap.put("consignee", map.get("consignee"));
        goodsReceiptMap.put("totalCount", map.get("totalCount"));
        excelWriter.fill(goodsReceiptMap, writeSheet);
        //EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).withTemplate(templateFileName).sheet(sheetName).doWrite(data);
        // 别忘记关闭流
        excelWriter.finish();
        return;
    }

    public static void compositeFillGoodsReceipt(FileOutputStream fileOutputStream, Map<String,Object> map, String templateFileName, String fileName, String sheetName) throws Exception {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量 {前缀.} 前缀可以区分不同的list
        ExcelWriter excelWriter = EasyExcel.write(fileOutputStream).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        FillConfig fillConfig2 = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
        excelWriter.fill(new FillWrapper("data1", ( List<Address>)map.get("addressList")), fillConfig, writeSheet);
        excelWriter.fill(new FillWrapper("data2", (List<ShipmentOrder>)map.get("shipmentOrderList")),fillConfig2, writeSheet);
        Map<String, Object> goodsReceiptMap = new HashMap<String, Object>();
        goodsReceiptMap.put("documentNo", map.get("documentNo"));
        goodsReceiptMap.put("consignee", map.get("consignee"));
        goodsReceiptMap.put("totalCount", map.get("totalCount"));
        excelWriter.fill(goodsReceiptMap, writeSheet);
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
            response.setHeader("Content-disposition", "attachment; filename=" + "GoodsReceipt.xlsx");
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
}
