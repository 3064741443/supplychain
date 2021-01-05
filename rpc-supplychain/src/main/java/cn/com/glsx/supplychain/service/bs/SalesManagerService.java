package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.mapper.bs.DealerUserInfoMapper;
import cn.com.glsx.supplychain.mapper.bs.LogisticsMapper;
import cn.com.glsx.supplychain.mapper.bs.MerchantOrderDetailMapper;
import cn.com.glsx.supplychain.mapper.bs.SalesManagerMapper;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalesManagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesManagerService.class);

    @Autowired
    private SalesManagerMapper salesManagerMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private MerchantOrderDetailMapper merchantOrderDetailMapper;

    @Autowired
    private DealerUserInfoMapper dealerUserInfoMapper;

    /**
     * 分页查询销售管理列表
     *
     * @param pageNum
     * @param pageSize
     * @param sales
     * @return
     */
    public Page<Sales> listSalesManager(int pageNum, int pageSize, Sales sales) {
        LOGGER.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, sales);
        Page<Sales> result;
        PageHelper.startPage(pageNum, pageSize);
        result = salesManagerMapper.listSalesManager(sales);
        for (int i = 0; i < result.getResult().size(); i++) {
            result.getResult().get(i).setProductInfo(
                    productService.getProductByProductCode(result.getResult().get(i).getProductCode()));
            DealerUserInfo dealerUserInfo = new DealerUserInfo();
            dealerUserInfo.setMerchantCode(result.getResult().get(i).getMerchantCode());
            DealerUserInfo dealerUser = dealerUserInfoMapper.selectOne(dealerUserInfo);
            if(!StringUtil.isEmpty(dealerUser)){
                result.getResult().get(i).setSaleMode(dealerUser.getSaleMode());
            }
        }
        return result;
    }


    /**
     * 新增销售管理信息
     *
     * @param salesList
     * @return
     */
    public Integer add(List<Sales> salesList) {
        LOGGER.info("新增的参数为：{}。", salesList);
        Integer result;
        result = salesManagerMapper.insertList(salesList);
        return result;
    }

    /**
     * 修改销售管信息
     *
     * @param sales
     * @return
     */
    public Integer updateById(Sales sales) {
        LOGGER.info("新增的参数为：{}。", sales);
        sales.setUpdatedDate(new Date());
        Integer result;
        result = salesManagerMapper.updateByPrimaryKeySelective(sales);
        return result;
    }

    /**
     * 根据id查询销售管信息
     *
     * @param id
     * @return
     */
    public Sales getSalesInfoByid(Long id) {
        LOGGER.info("查询销售管理的参数为：{}。", id);
        Sales result;
        result = salesManagerMapper.selectByPrimaryKey(id);
        result.setProductInfo(productService.getProductByProductCode(result.getProductCode()));
        //根据商户订单号与产品编号查询发货单
        MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
        merchantOrderDetail.setMerchantOrderNumber(result.getOrderNumber());
        merchantOrderDetail.setProductCode(result.getProductCode());
        MerchantOrderDetail md = merchantOrderDetailMapper.selectOne(merchantOrderDetail);
        if(md != null){
            //填充物流信息
            Logistics logistics = new Logistics();
            logistics.setId(result.getLogisticsId());
            result.setLogisticsInfoList(logisticsMapper.select(logistics));
        }
        return result;
    }


    /**
     * 根据id批量修改销售管理开票信息
     *
     * @param ids status
     * @return
     */
    public Integer updateSalesInfoByid(List<Long> ids,Byte status){
        LOGGER.info("批量修改销售管理开票信息的参数为：{}。", ids ,status);
        Integer result;
        result = salesManagerMapper.updateSalesInfoByid(ids,status);
        return result;
    }
}
