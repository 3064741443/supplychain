package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.mapper.bs.SalesRelationMapper;
import cn.com.glsx.supplychain.model.bs.SalesRelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SalesRelationService
 * @Author admin
 * @Param
 * @Date 2019/5/15 11:22
 * @Version
 **/
@Service
public class SalesRelationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SalesRelationService.class);

    @Autowired
    private SalesRelationMapper salesRelationMapper;

    /**
     *  查询销售管理与销售对账汇总关系列表
     */
    public List<SalesRelation> getSalesRelation (SalesRelation salesRelation){
        LOGGER.info("查询salesRelation的参数{}。", salesRelation);
        List<SalesRelation> result;
        result = salesRelationMapper.select(salesRelation);
        return result;
    }

    /**
     * 新增销售管理与销售对账汇总关系
     */
    public Integer add(SalesRelation salesRelation){
        LOGGER.info("新增salesRelation的参数{}。", salesRelation);
        Integer result;
        salesRelation.setCreatedDate(new Date());
        result = salesRelationMapper.add(salesRelation);
        return result;
    }

    /**
     * 修改销售管理与销售对账汇总关系
     *
     * @param salesRelation
     * @return
     */
    public Integer updateById(SalesRelation salesRelation) {
        LOGGER.info("修改的参数为：{}。", salesRelation);
        salesRelation.setUpdatedDate(new Date());
        Integer result;
        result = salesRelationMapper.updateByPrimaryKeySelective(salesRelation);
        return result;
    }

}
