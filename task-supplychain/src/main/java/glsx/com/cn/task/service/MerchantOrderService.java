package glsx.com.cn.task.service;

/**
 * @ClassName MerchantOrderService
 * @Author admin
 * @Param
 * @Date 2019/11/8 17:46
 * @Version
 **/
public interface MerchantOrderService {
    void generateMerchantOrder();

    /**
     * @author: luoqiang
     * @description: 同步商户订单的状态到
     * @date: 2020/9/4 11:18
     * @param
     * @return: void
     */
    void synchronizeMerchantOrderStatus();
}
