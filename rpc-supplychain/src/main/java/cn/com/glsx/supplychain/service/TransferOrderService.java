package cn.com.glsx.supplychain.service;

import cn.com.glsx.supplychain.mapper.TransferOrderMapper;
import cn.com.glsx.supplychain.mapper.TransferOrderSnMapper;
import cn.com.glsx.supplychain.model.MdbTransferOrder;
import cn.com.glsx.supplychain.model.MdbTransferOrderSn;
import cn.com.glsx.supplychain.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/12/1 17:56
 */
@Service
public class TransferOrderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private  TransferOrderMapper transferOrderMapper;

    @Autowired
    private TransferOrderSnMapper transferOrderSnMapper;

    /**
     * @author: luoqiang
     * @description: 获取sn对应的List集合
     * @date: 2020/12/1 18:20
     * @param snList
     * @return: java.lang.String
     */
    public Map<String,List<MdbTransferOrderSn>> mapTransferOrderSn(List<String> snList){
        if(StringUtil.isEmpty(snList)||snList.size()==0){
            return new HashMap<>();
        }
        Map<String,List<MdbTransferOrderSn>> mapResult=new HashMap<>();
        List<String> tranOrderCodes=new ArrayList<>();
        List<MdbTransferOrderSn> mdbTransferOrderSnList=listMdbTransferOrderSn(snList);
        if(StringUtil.isEmpty(mdbTransferOrderSnList)){
            return new HashMap<>();
        }
        mapResult = mdbTransferOrderSnList.stream().collect(Collectors.groupingBy(MdbTransferOrderSn::getSn));

        /*for (Map.Entry<String, List<MdbTransferOrderSn>> entry : mapResult.entrySet()) {
            if(!StringUtil.isEmpty(entry.getValue())&&entry.getValue().size()>0){
                tranOrderCodes.add(entry.getValue().get(0).getTranOrderCode());
            }
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }*/
        return mapResult;
    }

    public List<MdbTransferOrderSn> listMdbTransferOrderSn(List<String> listSn)
    {
        Example example = new Example(MdbTransferOrderSn.class);
        example.setOrderByClause("CREATED_DATE DESC");
        example.createCriteria().andIn("sn", listSn)
                .andEqualTo("deletedFlag","N");
        return transferOrderSnMapper.selectByExample(example);
    }

    public List<MdbTransferOrder> listMdbTransferOrder(List<String> tranOrderCodes)
    {
        Example example = new Example(MdbTransferOrder.class);
        example.createCriteria().andIn("tranOrderCode", tranOrderCodes)
                .andEqualTo("deletedFlag","N");
        return transferOrderMapper.selectByExample(example);
    }

    public Map<String, MdbTransferOrder> mapMdbTransferOrder(List<String> tranOrderCodes){
        if(StringUtil.isEmpty(tranOrderCodes)||tranOrderCodes.size()==0){
            return new HashMap<>();
        }
        Map<String,MdbTransferOrder> mapResult=null;
        List<MdbTransferOrder> transferOrders=listMdbTransferOrder(tranOrderCodes);
        if(transferOrders==null || transferOrders.size()==0){
            mapResult= new HashMap<>();
            return  mapResult;
        }
        mapResult = transferOrders.stream().collect(Collectors.toMap(MdbTransferOrder::getTranOrderCode, a->a));
        if(null == mapResult){
            mapResult = new HashMap<>();
        }
        return mapResult;
    }

}
