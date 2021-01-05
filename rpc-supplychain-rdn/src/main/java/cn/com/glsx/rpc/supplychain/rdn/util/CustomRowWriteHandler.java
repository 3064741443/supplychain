package cn.com.glsx.rpc.supplychain.rdn.util;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: supplychain
 * @description：自定义拦截器，用来写首行的序列号
 * @create: 2020-07-23 09:42
 * @author: luoqiang
 * @version: 1.0
 */
public class CustomRowWriteHandler extends AbstractColumnWidthStyleStrategy implements RowWriteHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRowWriteHandler.class);
    /**
     * description: 设置首列样式
     */
    private CellStyle firstCellStyle;

    /**
     * 列号
     */
    private  int count= 0;

    private Map<Integer, Map<Integer, Integer>> CACHE = new HashMap<>();

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {
       /* if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("第{}行创建完毕！", row.getRowNum());
        }*/
        Cell cell = row.createCell(0);
//        if (firstCellStyle == null) {
//            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
//            firstCellStyle = CellStyleUtil.firstCellStyle(workbook);
//            LOGGER.info("设置首列样式成功");
//        }
//        cell.setCellStyle(firstCellStyle);
        //设置列宽  0列   10个字符宽度
        writeSheetHolder.getSheet().setColumnWidth(0, 10 * 256);
        if (row.getRowNum() == 0) {
            cell.setCellValue("序号");
            return;
        }
        cell.setCellValue(++count);
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {

    }

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth) {
            Map<Integer, Integer> maxColumnWidthMap = CACHE.get(writeSheetHolder.getSheetNo());
            if (maxColumnWidthMap == null) {
                maxColumnWidthMap = new HashMap<>();
                CACHE.put(writeSheetHolder.getSheetNo(), maxColumnWidthMap);
            }

            Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
            if (columnWidth >= 0) {
                if (columnWidth > 255) {
                    columnWidth = 255;
                }

                Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
                if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                    maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
                    writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
                }

            }
        }
    }

    private Integer dataLength(List<CellData> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            CellData cellData = cellDataList.get(0);
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                switch (type) {
                    case STRING:
                        return cellData.getStringValue().getBytes().length;
                    case BOOLEAN:
                        return cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER:
                        return cellData.getNumberValue().toString().getBytes().length;
                    default:
                        return -1;
                }
            }
        }
    }
}
