//package cn.com.glsx.supplychain.jxc.service;
//
//import cn.com.glsx.supplychain.jxc.common.Constants;
//import cn.com.glsx.supplychain.jxc.dto.JXCMTAttribInfoDTO;
//import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMaterialInfoDTO;
//import cn.com.glsx.supplychain.jxc.dto.JXCMTProductTypeDTO;
//import cn.com.glsx.supplychain.jxc.mapper.JXCMTAttribInfoMapper;
//import cn.com.glsx.supplychain.jxc.mapper.JXCMTMaterialInfoMapper;
//import cn.com.glsx.supplychain.jxc.model.JXCMTAttribInfo;
//import cn.com.glsx.supplychain.jxc.model.JXCMTMaterialInfo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tk.mybatis.mapper.entity.Example;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class JXCMTAmMaterialInfoService {
//
//    private static final Logger logger = LoggerFactory.getLogger(JXCMTAmMaterialInfoService.class);
//    @Autowired
//    private JXCMTMaterialInfoMapper jxcmtMaterialInfoMapper;
//
//    @Autowired
//    private JXCMTAttribInfoMapper jxcmtAttribInfoMapper;
//
//    public List<JXCMTBsMaterialInfoDTO> listJxcMaterialInfo(JXCMTBsMaterialInfoDTO record) {
//        List<JXCMTBsMaterialInfoDTO> jxcmtBsMaterialInfoDTOS = new ArrayList<>();
//        JXCMTBsMaterialInfoDTO jxcmtBsMaterialInfoDTO = null;
//        Example example = new Example(JXCMTMaterialInfo.class);
//        if (record.getDeviceTypeId() != null) {
//            example.createCriteria().andEqualTo("deviceTypeId", record.getDeviceTypeId());
//        }
//        List<JXCMTMaterialInfo> jxcmtMaterialInfos = jxcmtMaterialInfoMapper.selectByExample(example);
//        if (jxcmtMaterialInfos == null && jxcmtMaterialInfos.size() == 0) {
//            return null;
//        }
//        JXCMTProductTypeDTO dtoObject = null;
//        for (JXCMTMaterialInfo jxcmtMaterialInfo : jxcmtMaterialInfos) {
//            jxcmtBsMaterialInfoDTO = new JXCMTBsMaterialInfoDTO();
//            jxcmtBsMaterialInfoDTO.setMaterialCode(jxcmtMaterialInfo.getMaterialCode());
//            jxcmtBsMaterialInfoDTO.setMaterialName(jxcmtMaterialInfo.getMaterialName());
//            jxcmtBsMaterialInfoDTOS.add(jxcmtBsMaterialInfoDTO);
//        }
//        return jxcmtBsMaterialInfoDTOS;
//    }
//
//    public List<JXCMTBsMaterialInfoDTO> listDeviceMaterialByLike(JXCMTBsMaterialInfoDTO jxcmtBsMaterialInfoDTO) {
//        Example example = new Example(JXCMTMaterialInfo.class);
//        List<JXCMTBsMaterialInfoDTO> jxcmtBsMaterialInfoDTOS = new ArrayList<>();
//        JXCMTBsMaterialInfoDTO materialInfoDTO = null;
//        example.createCriteria().andLike("materialCode", "%" + jxcmtBsMaterialInfoDTO.getMaterialCode() + "%");
//        example.createCriteria().andLike("materialName", "%" + jxcmtBsMaterialInfoDTO.getMaterialName() + "%");
//        List<JXCMTMaterialInfo> jxcmtMaterialInfos = jxcmtMaterialInfoMapper.selectByExample(example);
//        if (jxcmtMaterialInfos == null && jxcmtMaterialInfos.size() == 0) {
//            return null;
//        }
//        for (JXCMTMaterialInfo jxcmtMaterialInfo : jxcmtMaterialInfos) {
//            materialInfoDTO = new JXCMTBsMaterialInfoDTO();
//            materialInfoDTO.setMaterialCode(jxcmtMaterialInfo.getMaterialCode());
//            materialInfoDTO.setMaterialName(jxcmtMaterialInfo.getMaterialName());
//            jxcmtBsMaterialInfoDTOS.add(materialInfoDTO);
//        }
//        return jxcmtBsMaterialInfoDTOS;
//    }
//
//    public JXCMTBsMaterialInfoDTO getDeviceMaterial(JXCMTBsMaterialInfoDTO jxcmtBsMaterialInfoDTO) {
//        Example example = new Example(JXCMTMaterialInfo.class);
//        List<JXCMTBsMaterialInfoDTO> jxcmtBsMaterialInfoDTOS = new ArrayList<>();
//        JXCMTBsMaterialInfoDTO materialInfoDTO = new JXCMTBsMaterialInfoDTO();
//        example.createCriteria().andEqualTo("materialCode", jxcmtBsMaterialInfoDTO.getMaterialCode());
//        List<JXCMTMaterialInfo> jxcmtBsMaterialInfoDTOList = jxcmtMaterialInfoMapper.selectByExample(example);
//        if (jxcmtBsMaterialInfoDTOList == null && jxcmtBsMaterialInfoDTOList.size() == 0) {
//            return null;
//        }
//        materialInfoDTO.setMaterialName(jxcmtBsMaterialInfoDTOList.get(0).getMaterialName());
//        return materialInfoDTO;
//    }
//
//
//    public Integer saveDeviceModel(JXCMTAttribInfoDTO jxcmtAttribInfoDTO) {
//        JXCMTAttribInfo jxcmtAttribInfo = null;
//        List<JXCMTAttribInfo> jxcmtAttribInfoList = new ArrayList<>();
//        Integer result=0;
//        Example example = new Example(JXCMTAttribInfo.class);
//        Example.Criteria criteria = example.createCriteria();
//        //OBD
//        if (jxcmtAttribInfoDTO.getTypeId() == 1) {
//            criteria.andEqualTo("type", Constants.ATTRIB_INFO_OBD);
//            criteria.andEqualTo("name", jxcmtAttribInfoDTO.getName());
//            jxcmtAttribInfoList = jxcmtAttribInfoMapper.selectByExample(example);
//            if (jxcmtAttribInfoList != null && jxcmtAttribInfoList.size() > 0) {
//                return 0;
//            }
//            jxcmtAttribInfo = new JXCMTAttribInfo();
//            jxcmtAttribInfo.setType(Constants.ATTRIB_INFO_OBD);
//            jxcmtAttribInfo.setName(jxcmtAttribInfoDTO.getName());
//            JXCMTAttribInfo attribInfoMaxValue = jxcmtAttribInfoMapper.getAttribInfoMaxValue(Constants.ATTRIB_INFO_OBD);
//            Integer maxValue=attribInfoMaxValue.getValue();
//            if (maxValue != null) {
//                jxcmtAttribInfo.setValue(maxValue + 1);
//            } else {
//                jxcmtAttribInfo.setValue(1);
//            }
//            jxcmtAttribInfo.setComment("OBD");
//            jxcmtAttribInfo.setCreatedBy("admin");
//            jxcmtAttribInfo.setCreatedDate(new Date());
//            jxcmtAttribInfo.setUpdatedBy("admin");
//            jxcmtAttribInfo.setUpdatedDate(new Date());
//            jxcmtAttribInfo.setDeletedFlag("N");
//            result = jxcmtAttribInfoMapper.insertJXCMTAttribInfo(jxcmtAttribInfo);
//        }
//
//        //车载导航(DVD)
//        if (jxcmtAttribInfoDTO.getTypeId() == 2) {
//            criteria.andEqualTo("type", Constants.ATTRIB_INFO_CAR_NAVIGATION);
//            criteria.andEqualTo("name", jxcmtAttribInfoDTO.getName());
//            jxcmtAttribInfoList = jxcmtAttribInfoMapper.selectByExample(example);
//            if (jxcmtAttribInfoList != null && jxcmtAttribInfoList.size() > 0) {
//                return 0;
//            }
//            jxcmtAttribInfo = new JXCMTAttribInfo();
//            jxcmtAttribInfo.setType(Constants.ATTRIB_INFO_CAR_NAVIGATION);
//            jxcmtAttribInfo.setName(jxcmtAttribInfoDTO.getName());
//            JXCMTAttribInfo attribInfoMaxValue= jxcmtAttribInfoMapper.getAttribInfoMaxValue(Constants.ATTRIB_INFO_CAR_NAVIGATION);
//            Integer maxValue=attribInfoMaxValue.getValue();
//            if (maxValue != null) {
//                jxcmtAttribInfo.setValue(maxValue + 1);
//            } else {
//                jxcmtAttribInfo.setValue(1);
//            }
//            jxcmtAttribInfo.setComment("车载导航设备型号(DVD)");
//            jxcmtAttribInfo.setCreatedBy("admin");
//            jxcmtAttribInfo.setCreatedDate(new Date());
//            jxcmtAttribInfo.setUpdatedBy("admin");
//            jxcmtAttribInfo.setUpdatedDate(new Date());
//            jxcmtAttribInfo.setDeletedFlag("N");
//            result = jxcmtAttribInfoMapper.insertJXCMTAttribInfo(jxcmtAttribInfo);
//        }
//
//        //智能云镜（后视镜）
//        if (jxcmtAttribInfoDTO.getTypeId() == 12134) {
//            criteria.andEqualTo("type", Constants.ATTRIB_INFO_REARVIEW_MIRROR);
//            criteria.andEqualTo("name", jxcmtAttribInfoDTO.getName());
//            jxcmtAttribInfoList = jxcmtAttribInfoMapper.selectByExample(example);
//            if (jxcmtAttribInfoList != null && jxcmtAttribInfoList.size() > 0) {
//                return 0;
//            }
//            jxcmtAttribInfo = new JXCMTAttribInfo();
//            jxcmtAttribInfo.setType(Constants.ATTRIB_INFO_REARVIEW_MIRROR);
//            jxcmtAttribInfo.setName(jxcmtAttribInfoDTO.getName());
//            JXCMTAttribInfo attribInfoMaxValue = jxcmtAttribInfoMapper.getAttribInfoMaxValue(Constants.ATTRIB_INFO_REARVIEW_MIRROR);
//            Integer maxValue=attribInfoMaxValue.getValue();
//            if (maxValue != null) {
//                jxcmtAttribInfo.setValue(maxValue + 1);
//            } else {
//                jxcmtAttribInfo.setValue(1);
//            }
//            jxcmtAttribInfo.setComment("后视镜设备型号(智能云镜)");
//            jxcmtAttribInfo.setCreatedBy("admin");
//            jxcmtAttribInfo.setCreatedDate(new Date());
//            jxcmtAttribInfo.setUpdatedBy("admin");
//            jxcmtAttribInfo.setUpdatedDate(new Date());
//            jxcmtAttribInfo.setDeletedFlag("N");
//            result = jxcmtAttribInfoMapper.insertJXCMTAttribInfo(jxcmtAttribInfo);
//        }
//
//        //行车记录仪
//        if (jxcmtAttribInfoDTO.getTypeId() == 6) {
//            criteria.andEqualTo("type", Constants.ATTRIB_INFO_DRIVING_RECORDER);
//            criteria.andEqualTo("name", jxcmtAttribInfoDTO.getName());
//            jxcmtAttribInfoList = jxcmtAttribInfoMapper.selectByExample(example);
//            if (jxcmtAttribInfoList != null && jxcmtAttribInfoList.size() > 0) {
//                return 0;
//            }
//            jxcmtAttribInfo = new JXCMTAttribInfo();
//            jxcmtAttribInfo.setType(Constants.ATTRIB_INFO_DRIVING_RECORDER);
//            jxcmtAttribInfo.setName(jxcmtAttribInfoDTO.getName());
//            JXCMTAttribInfo attribInfoMaxValue = jxcmtAttribInfoMapper.getAttribInfoMaxValue(Constants.ATTRIB_INFO_DRIVING_RECORDER);
//            Integer maxValue=attribInfoMaxValue.getValue();
//            if (maxValue != null) {
//                jxcmtAttribInfo.setValue(maxValue + 1);
//            } else {
//                jxcmtAttribInfo.setValue(1);
//            }
//            jxcmtAttribInfo.setComment("OBD");
//            jxcmtAttribInfo.setCreatedBy("admin");
//            jxcmtAttribInfo.setCreatedDate(new Date());
//            jxcmtAttribInfo.setUpdatedBy("admin");
//            jxcmtAttribInfo.setUpdatedDate(new Date());
//            jxcmtAttribInfo.setDeletedFlag("N");
//            result = jxcmtAttribInfoMapper.insertJXCMTAttribInfo(jxcmtAttribInfo);
//        }
//
//        //GPS(追踪器)  和  车载精灵
//        if (jxcmtAttribInfoDTO.getTypeId() == 8 || jxcmtAttribInfoDTO.getTypeId() == 12501) {
//            criteria.andEqualTo("type", Constants.ATTRIB_INFO_GPS);
//            criteria.andEqualTo("name", jxcmtAttribInfoDTO.getName());
//            jxcmtAttribInfoList = jxcmtAttribInfoMapper.selectByExample(example);
//            if (jxcmtAttribInfoList != null && jxcmtAttribInfoList.size() > 0) {
//                return 0;
//            }
//            jxcmtAttribInfo = new JXCMTAttribInfo();
//            jxcmtAttribInfo.setType(Constants.ATTRIB_INFO_GPS);
//            jxcmtAttribInfo.setName(jxcmtAttribInfoDTO.getName());
//            JXCMTAttribInfo attribInfoMaxValue= jxcmtAttribInfoMapper.getAttribInfoMaxValue(Constants.ATTRIB_INFO_OBD);
//            Integer maxValue=attribInfoMaxValue.getValue();
//            if (maxValue != null) {
//                jxcmtAttribInfo.setValue(maxValue + 1);
//            } else {
//                jxcmtAttribInfo.setValue(1);
//            }
//            jxcmtAttribInfo.setComment("GPS设备型号(追踪器)和车载精灵");
//            jxcmtAttribInfo.setCreatedBy("admin");
//            jxcmtAttribInfo.setCreatedDate(new Date());
//            jxcmtAttribInfo.setUpdatedBy("admin");
//            jxcmtAttribInfo.setUpdatedDate(new Date());
//            jxcmtAttribInfo.setDeletedFlag("N");
//            result = jxcmtAttribInfoMapper.insertJXCMTAttribInfo(jxcmtAttribInfo);
//        }
//        return result;
//    }
//}
//
//
