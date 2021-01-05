package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.mapper.bs.MerchantOrderDetailMapper;
import cn.com.glsx.supplychain.model.bs.MerchantOrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantOrderDetailService {

    private static final Logger logger = LoggerFactory.getLogger(MerchantOrderDetailService.class);

    @Autowired
    private MerchantOrderDetailMapper merchantOrderDetailMapper;

    public List<MerchantOrderDetail> getMerchantOrderDetailListByDispatchOrderNumberList(List<MerchantOrderDetail> merchantOrderDetailList) {
        List<MerchantOrderDetail> merchantOrderDetails = new ArrayList<>();
        try {
            merchantOrderDetails = merchantOrderDetailMapper.getMerchantOrderDetailListByDispatchOrderNumberList(merchantOrderDetailList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return merchantOrderDetails;
    }


}
