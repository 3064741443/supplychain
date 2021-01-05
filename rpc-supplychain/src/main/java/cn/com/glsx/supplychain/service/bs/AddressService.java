package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.mapper.bs.AddressMapper;
import cn.com.glsx.supplychain.model.bs.Address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AddressService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 根据姓名获取地址
     *
     * @param name
     * @return
     */
    public List<Address> getAddressListByName(String name) {
        LOGGER.info("查询地址薄的入参:{}", name);
        List<Address> result;
        Address address = new Address();
        address.setName(name);
        address.setDeletedFlag("N");
        result = addressMapper.select(address);
        return result;
    }

    /**
     * 根据商户编号获取地址获取地址
     *
     * @param merchantCode
     * @return
     */
    public List<Address> getAddressListByMerchantCode(String merchantCode) {
        LOGGER.info("查询地址薄的入参:{}", merchantCode);
        List<Address> result;
        Address address = new Address();
        address.setMerchantCode(merchantCode);
        address.setDeletedFlag("N");
        result = addressMapper.select(address);
        return result;
    }

    /**
     * 查询List是否重复
     * @param address
     * @return
     */
    public List<Address> getAddressList(Address address){
        LOGGER.info("查询地址薄的入参:{}", address);
        List<Address> result;
        Address addressData = new Address();
        addressData.setMerchantCode(address.getMerchantCode());
        addressData.setName(address.getName());
        addressData.setMobile(address.getMobile());
        addressData.setAddress(address.getAddress());
        result = addressMapper.select(addressData);
        return result;
    }

    /**
     * 增加地址薄
     *
     * @param address
     * @return
     */
    public Integer add(Address address) {
        LOGGER.info("新增地址薄:{}", address);
        Integer result;
        address.setCreatedDate(new Date());
        address.setUpdatedDate(new Date());
        address.setDeletedFlag("N");
        result = addressMapper.insert(address);
        return result;
    }
    
    /**
     * 根据电话和地址获取地址
     *
     * @param address
     * @return
     */
    public Address addIfNotExist(Address address)
    {
    	Address result;
    	LOGGER.info("根据电话和地址获取地址:{}", address);
    	result = addressMapper.selectByPhoneAndAddress(address);
    	if(!StringUtils.isEmpty(result))
    	{
    		return result;
    	}
    	address.setDeletedFlag("N");
    	addressMapper.insert(address);
    	return address;
    }
}
