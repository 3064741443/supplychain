package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.mapper.bs.SalesRelationMapper;
import cn.com.glsx.supplychain.mapper.bs.SalesSummarizingMaterialDetailMapper;
import cn.com.glsx.supplychain.model.bs.SalesRelation;
import cn.com.glsx.supplychain.model.bs.SalesSummarizingMaterialDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SalesSummarizingMaterialDetailService
 * @Author admin
 * @Param
 * @Date 2019/5/15 11:22
 * @Version
 **/
@Service
public class SalesSummarizingMaterialDetailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SalesSummarizingMaterialDetailService.class);

    @Autowired
    private SalesSummarizingMaterialDetailMapper salesSummarizingMaterialDetailMapper;

    /**
     *  查询销售汇总物料详情列表
     */
    public SalesSummarizingMaterialDetail getSalesSummarizingMaterialDetail (SalesSummarizingMaterialDetail salesSummarizingMaterialDetail){
        LOGGER.info("查询销售汇总物料详情的参数{}。", salesSummarizingMaterialDetail);
        SalesSummarizingMaterialDetail result;
        result = salesSummarizingMaterialDetailMapper.selectOne(salesSummarizingMaterialDetail);
        return result;
    }

    /**
     *  查询销售汇总物料详情List
     */
    public List<SalesSummarizingMaterialDetail> getSalesSummarizingMaterialDetailList (SalesSummarizingMaterialDetail salesSummarizingMaterialDetail){
        LOGGER.info("查询销售汇总物料详情的参数{}。", salesSummarizingMaterialDetail);
        List<SalesSummarizingMaterialDetail> result;
        result = salesSummarizingMaterialDetailMapper.getSalesSummarizingMaterialDetailList(salesSummarizingMaterialDetail);
        return result;
    }

    /**
     * 新增销售汇总物料详情
     */
    public Integer add(SalesSummarizingMaterialDetail salesSummarizingMaterialDetail){
        LOGGER.info("新增销售汇总物料详情的参数{}。", salesSummarizingMaterialDetail);
        Integer result;
        salesSummarizingMaterialDetail.setCreatedDate(new Date());
        result = salesSummarizingMaterialDetailMapper.add(salesSummarizingMaterialDetail);
        return result;
    }

    /**
     * 修改销售汇总物料详情
     *
     * @param salesSummarizingMaterialDetail
     * @return
     */
    public Integer updateBySalesSummarizingMaterialDetail(SalesSummarizingMaterialDetail salesSummarizingMaterialDetail) {
        LOGGER.info("修改销售汇总物料详情的参数为：{}。", salesSummarizingMaterialDetail);
        salesSummarizingMaterialDetail.setUpdatedDate(new Date());
        Integer result;
        result = salesSummarizingMaterialDetailMapper.updateByPrimaryKeySelective(salesSummarizingMaterialDetail);
        return result;
    }

}
