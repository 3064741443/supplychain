package cn.com.glsx.supplychain.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ReasonInvalidDeviceNum;
import cn.com.glsx.supplychain.mapper.DeviceFileUnstockMapper;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.util.StringUtil;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glsx.cloudframework.core.util.BeanUtils;
import com.glsx.oms.fcservice.api.core.FlowResponse;
import com.glsx.oms.fcservice.api.entity.Flowcard;
import com.glsx.oms.fcservice.api.manager.OpsMgrManager;
import com.glsx.oms.fcservice.api.request.FlowCardRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceFileUnstockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceFileUnstockService.class);

    @Autowired
    private DeviceFileUnstockMapper deviceFileUnstockMapper;

    //@Autowired
   // private FlowCardService flowCardService;
    @Autowired
    private OpsMgrManager opsMgrManager;


    @Autowired
    private DeviceFileVirtualService deviceFileVirtualService;

    @Autowired
    private DeviceCardManagerService deviceCardManagerService;

    @Autowired
    private DeviceFileSnapshotService deviceFileSnapshotService;

    /**
     * 新增未录入的设备
     *
     * @param request
     * @return
     * @throws Exception
     */
    public Integer saveDeviceFileUnstock(SupplyDeviceFileRequest request) throws Exception {
        LOGGER.info("新增设备未入库记录表的IMSI:{},IMEI:{}", request.getImsi(), request.getSn());
        String imsi = request.getImsi();
        String imei = request.getSn();
        if (imsi != null) {
            //验证imsi是否已经被绑定
            DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByImsi(imsi);
            if (!StringUtils.isEmpty(deviceFileVirtual) && "N".equals(deviceFileVirtual.getDeletedFlag())) {
                LOGGER.error(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMSI.getValue());
                return 0;
            }
            //卡管理表查imsi
            DeviceCardManager cardManagerCondition = new DeviceCardManager();
            cardManagerCondition.setImsi(imsi);
            cardManagerCondition.setCompanyId(1);
            DeviceCardManager deviceCardManager = deviceCardManagerService.getDeviceCardByUniqueKey(cardManagerCondition);
            if (!StringUtils.isEmpty(deviceCardManager)) {
                //关系表查卡表imsi是否重复
                LOGGER.info("查询关系表查卡表imsi是否重复的入参:" + deviceCardManager.getId());
                DeviceFileSnapshot deviceSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(deviceCardManager.getId());
                LOGGER.info("查询关系表查卡表imsi是否重复的结果:{}", deviceSnapshot);
                if (!StringUtils.isEmpty(deviceSnapshot) && "N".equals(deviceSnapshot.getDeletedFlag())) {
                    LOGGER.error(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMSI.getValue());
                    return 0;
                }
            }
            DeviceFileUnstock deviceFileUnstock = new DeviceFileUnstock();
            deviceFileUnstock.setImsi(imsi);
            deviceFileUnstock.setSn(imei);
            List<DeviceFileUnstock> dfuList = deviceFileUnstockMapper.select(deviceFileUnstock);
            if (dfuList != null && dfuList.size() > 0) {
                for (DeviceFileUnstock dfu : dfuList) {
                    if ("N".equals(dfu.getDeletedFlag())) {
                        LOGGER.error(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMSI.getValue());
                        return 0;
                    }
                }
            }
            deviceFileUnstock.setCreatedBy("admin");
            deviceFileUnstock.setCreatedDate(new Date());
            deviceFileUnstock.setUpdatedBy("admin");
            deviceFileUnstock.setUpdatedDate(new Date());
            deviceFileUnstock.setDeletedFlag("N");
            FlowCardRequest flowCardRequest = new FlowCardRequest();
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            flowCardRequest.setTime(time);  //时间
            flowCardRequest.setVersion("2.2.0"); //版本号
            flowCardRequest.setConsumer("task-supplychain"); //项目名称
            flowCardRequest.setKeyWord(imsi);  
            flowCardRequest.setInvoker("glsx");
            FlowResponse<Flowcard> flowCard = opsMgrManager.getCardIccid(flowCardRequest);
            if (!StringUtil.isEmpty(flowCard) && !StringUtil.isEmpty(flowCard.getEntiy())) {
                deviceFileUnstock.setIccid(flowCard.getEntiy().getIccid());
            }
            return deviceFileUnstockMapper.insertSelective(deviceFileUnstock);
        }
        return 0;
    }

    public void updateDeviceFileUnstock(DeviceFileUnstock deviceFileUnstock) {
        LOGGER.info("修改设备未入库记录表的入参:{}", deviceFileUnstock);
        List<DeviceFileUnstock> deviceFileUnstockList = deviceFileUnstockMapper.selectDeviceFileUnstock(deviceFileUnstock);
        if (deviceFileUnstockList != null && deviceFileUnstockList.size() > 0) {
            deviceFileUnstock.setDeletedFlag("Y");
            deviceFileUnstock.setUpdatedDate(new Date());
            deviceFileUnstockMapper.updateByImsi(deviceFileUnstock);
        } else {
            deviceFileUnstock.setCreatedBy("admin");
            deviceFileUnstock.setCreatedDate(new Date());
            deviceFileUnstock.setUpdatedBy("admin");
            deviceFileUnstock.setUpdatedDate(new Date());
            deviceFileUnstock.setDeletedFlag("Y");
            deviceFileUnstockMapper.insertSelective(deviceFileUnstock);
        }
    }

    /**
     * 分页查询未入库设备列表
     *
     * @param pageNum
     * @param pageSize
     * @param deviceFileUnstock
     * @return
     */
    public Page<DeviceFileUnstock> listDeviceFileUnstock(int pageNum, int pageSize, DeviceFileUnstock deviceFileUnstock) {
        LOGGER.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, deviceFileUnstock);
        Page<DeviceFileUnstock> result;
        PageHelper.startPage(pageNum, pageSize);
        result = deviceFileUnstockMapper.listDeviceFileUnstock(deviceFileUnstock);
        return result;
    }

    /**
     * 根据ID修改未录入的设备
     *
     * @param deviceFileUnstock
     * @return
     */
    public Integer updateById(DeviceFileUnstock deviceFileUnstock) {
        LOGGER.info("修改未录入状态的入参:{}", deviceFileUnstock);
        Integer result;
        result = deviceFileUnstockMapper.updateByPrimaryKeySelective(deviceFileUnstock);
        return result;
    }

    /**
     * 查询未入库设备列表
     * @param deviceFileUnstock
     * @return
     */
    public List<DeviceFileUnstock> selectList(DeviceFileUnstock deviceFileUnstock){
        LOGGER.info("查询未录入设备的入参:{}", deviceFileUnstock);
        return deviceFileUnstockMapper.selectList(deviceFileUnstock);
    }
    
    public Map<String,DeviceFileUnstock> getDeviceFileUnstockByImsis(List<String> listImsis){
    	Map<String,DeviceFileUnstock> mapResult = new HashMap<>();
    	if(listImsis == null || listImsis.isEmpty()){
    		return mapResult;
    	}
    	Example example = new Example(DeviceFileUnstock.class);
    	example.createCriteria().andIn("imsi", listImsis)
    							.andEqualTo("deletedFlag", "N");
    	List<DeviceFileUnstock> listUnstock = deviceFileUnstockMapper.selectByExample(example);
    	if(null == listUnstock || listUnstock.isEmpty()){
    		return mapResult;
    	}
    	for(DeviceFileUnstock unstock:listUnstock){
    		mapResult.put(unstock.getImsi(), unstock);
    	}
    	return mapResult;
    }

}
