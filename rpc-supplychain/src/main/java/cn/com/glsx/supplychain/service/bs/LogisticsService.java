package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.mapper.bs.AddressMapper;
import cn.com.glsx.supplychain.mapper.bs.LogisticsMapper;
import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.model.bs.Logistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class LogisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsService.class);

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private AddressMapper addressMapper;
    
    public Integer batchAddLogistics(List<Logistics> listLogistics)
	{
    	return logisticsMapper.insertList(listLogistics);
	}

    public Logistics getLogistics(Logistics logistics) {
        LOGGER.info("查询物流信息入参:", logistics);
        Logistics result;
        result = logisticsMapper.selectOne(logistics);
        Address address = new Address();
        if (result != null) {
            if (result.getSendId() != null) {
                //填充发货地址
                address.setId(result.getSendId());
                result.setSendAddress(addressMapper.selectOne(address));
            }
            if (result.getReceiveId() != null) {
                //填充收货地址
                address.setId(result.getReceiveId());
                result.setReceiveAddress(addressMapper.selectOne(address));
            }
        }
        return result;
    }

    public Integer add(Logistics logistics) {
        LOGGER.info("增加物流信息入参", logistics);
        Integer result;
        logistics.setCreatedDate(new Date());
        logistics.setUpdatedDate(new Date());
        logistics.setDeletedFlag("N");
        result = logisticsMapper.insert(logistics);
        return result;
    }

    public Integer updateById(Logistics logistics) {
        LOGGER.info("修改物流信息入参", logistics);
        Integer result;
        logistics.setUpdatedDate(new Date());
        result = logisticsMapper.updateById(logistics);
        return result;
    }

    public Integer updateByServiceCodeAndType(Logistics logistics) {
        LOGGER.info("修改物流信息入参", logistics);
        Integer result;
        logistics.setUpdatedDate(new Date());
        result = logisticsMapper.updateByServiceCodeAndType(logistics);
        return result;
    }

    public List<Logistics> getLogisticsInfoList(List<Logistics>logisticsList){
        LOGGER.info("查询物流信息List入参", logisticsList);
        List<Logistics> result;
        result = logisticsMapper.getLogisticsInfoList(logisticsList);
        return result;
    }

    public List<Logistics> getLogisticsInfoListByServiceCode(Logistics logistics){
        LOGGER.info("查询物流信息List入参", logistics);
        List<Logistics> result;
        result = logisticsMapper.getLogisticsInfoListByServiceCode(logistics);
        return result;
    }
    
    public Logistics addIfNotExist(Logistics record)
    {
    	Logistics result;
    	LOGGER.info("如果不存在添加", record);
    	result = logisticsMapper.selectByLogisticsNo(record);
    	if(!StringUtils.isEmpty(result))
    	{
    		return result;
    	}
    	logisticsMapper.insert(record);
    	result = logisticsMapper.selectByLogisticsNo(record);
    	return result;
    }
}
