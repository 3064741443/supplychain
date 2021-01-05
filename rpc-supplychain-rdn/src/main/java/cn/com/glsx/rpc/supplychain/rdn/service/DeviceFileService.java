package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.DeviceFileMapper;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceFileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceFileService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceFileService.class);
    @Autowired
    private DeviceFileMapper deviceFileMapper;
    public List<JXCMTDeviceFileDTO> exportDeviceFile(JXCMTDeviceFileDTO deviceFileDTO) {
        List<JXCMTDeviceFileDTO> deviceFileDTOList = deviceFileMapper.exportDeviceFile(deviceFileDTO);
        return deviceFileDTOList;
    }

}
