package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceFileDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.kafka.ExportDeviceFile;
import cn.com.glsx.supplychain.jxc.kafka.SendKafkaService;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTDeviceFileMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTDeviceFileSnapshotMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceFile;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceFileSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JXCMTDeviceFileService {
    private static final Logger logger = LoggerFactory.getLogger(JXCMTDeviceFileService.class);
    @Autowired
    private JXCMTDeviceFileMapper jxcmtDeviceFileMapper;
    @Autowired
    private JXCMTDeviceFileSnapshotMapper jxcmtDeviceFileSnapshotMapper;
    @Autowired
    private SendKafkaService sendKafkaService;

    public JXCMTDeviceFile getDeviceFileBySn(String sn) {
        JXCMTDeviceFile condition = new JXCMTDeviceFile();
        condition.setSn(sn);
        condition.setDeletedFlag("N");
        return jxcmtDeviceFileMapper.selectOne(condition);
    }

    public JXCMTDeviceFileSnapshot getDeviceFileSnapshotBySn(String sn) {
        JXCMTDeviceFileSnapshot conditon = new JXCMTDeviceFileSnapshot();
        conditon.setSn(sn);
        conditon.setDeletedFlag("N");
        return jxcmtDeviceFileSnapshotMapper.selectOne(conditon);
    }

    public Integer updateDeviceFile(JXCMTDeviceFile record) {
        if (null == record) {
            return 0;
        }
        return jxcmtDeviceFileMapper.updateByPrimaryKeySelective(record);
    }

    public Integer updateDeviceFileSnapshot(JXCMTDeviceFileSnapshot record) {
        if (null == record) {
            return 0;
        }
        return jxcmtDeviceFileSnapshotMapper.updateByPrimaryKeySelective(record);
    }

    public List<JXCMTDeviceFileDTO> exportDeviceFile(JXCMTDeviceFileDTO deviceFileDTO) throws RpcServiceException{
        logger.info("JXCMTDeviceService::exportDeviceFile start deviceFileDTO:", deviceFileDTO);
        ExportDeviceFile exportDeviceFile = generatorExportDeviceFile(deviceFileDTO);
        sendKafkaService.notifyDeviceFile(deviceFileDTO.getConsumer(), Constants.TASK_CFG_ID_DEVICE_FILE, exportDeviceFile);
//        List<JXCMTDeviceFileDTO> deviceFileDTOList = jxcmtDeviceFileMapper.exportDeviceFile(deviceFileDTO);
//        return deviceFileDTOList;
         throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_EXPORT_EQUIPMENT_DETAILS);
    }

    private ExportDeviceFile generatorExportDeviceFile(JXCMTDeviceFileDTO deviceFileDTO) {
        ExportDeviceFile exportDeviceFile = new ExportDeviceFile();
        exportDeviceFile.setDevTypeId(deviceFileDTO.getDevTypeId());
        exportDeviceFile.setPackageStatu(deviceFileDTO.getPackageStatu());
        exportDeviceFile.setSn(deviceFileDTO.getSn());
        exportDeviceFile.setIccid(deviceFileDTO.getIccid());
        exportDeviceFile.setImsi(deviceFileDTO.getImsi());
        exportDeviceFile.setUserFlag(deviceFileDTO.getUserFlag());
        exportDeviceFile.setDeviceCode(deviceFileDTO.getDeviceCode());
        exportDeviceFile.setPackageId(deviceFileDTO.getPackageId());
        exportDeviceFile.setSendMerchantNo(deviceFileDTO.getSendMerchantNo());
        exportDeviceFile.setOutStorageStartDate(deviceFileDTO.getOutStorageStartDate());
        exportDeviceFile.setOutStorageEndDate(deviceFileDTO.getOutStorageEndDate());
        exportDeviceFile.setPackageUserStartDate(deviceFileDTO.getPackageUserStartDate());
        exportDeviceFile.setPackageUserEndDate(deviceFileDTO.getPackageUserEndDate());
        return exportDeviceFile;
    }

}
