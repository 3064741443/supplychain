package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.TransferOrderMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.MdbTransferOrder;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.BsTransferOrderBssVo;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.JxcTransferOrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 11:10
 */
@Service
public class TransferOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransferOrderService.class);
    @Autowired
    private TransferOrderMapper transferOrderMapper;

    public List<BsTransferOrderBssVo> exportBsTransferOrder(MdbTransferOrder mdbTransferOrder){
        return transferOrderMapper.exportBsTransferOrder(mdbTransferOrder);
    }

    public List<JxcTransferOrderVo> exportJXCTransferOrder(MdbTransferOrder mdbTransferOrder){
        return transferOrderMapper.exportJXCTransferOrder(mdbTransferOrder);
    }

}
