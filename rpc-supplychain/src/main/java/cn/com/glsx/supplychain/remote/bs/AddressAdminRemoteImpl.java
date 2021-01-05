package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.service.bs.AddressService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: AddressAdminRemote.java
 * @Description: 地址管理(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("AddressAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class AddressAdminRemoteImpl implements AddressAdminRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressAdminRemoteImpl.class);

    @Autowired
    private AddressService addressService;

    @Override
    public RpcResponse<List<Address>> getAddressListByName(String name) {
        try {
            return RpcResponse.success(addressService.getAddressListByName(name));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<Address>> getAddressListByMerchantCode(String merchantCode) {
        try {
            return RpcResponse.success(addressService.getAddressListByMerchantCode(merchantCode));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<Address>> getAddressList(Address address) {
        try{
            return RpcResponse.success(addressService.getAddressList(address));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> add(Address address) {
        try {
            RpcAssert.assertNotNull(address, DefaultErrorEnum.PARAMETER_NULL, "address must not be null");
            return RpcResponse.success(addressService.add(address));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }
}
