package glsx.com.cn.task.service.impl;

import cn.com.glsx.supplychain.model.am.MaterialInfo;

import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;

import glsx.com.cn.task.mapper.am.MaterialInfoMapper;
import glsx.com.cn.task.service.MaterialInfoService;

import com.glsx.oms.bigdata.service.fb.model.Material;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialInfoServiceImpl implements MaterialInfoService {

    private Logger logger = LoggerFactory.getLogger(MaterialInfoServiceImpl.class);

    @Autowired
    private IMaterialDubboService iMaterialDubboService;

    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    @Override
    public void add() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<MaterialInfo> materialInfoList = new ArrayList<>();
        MaterialInfo materialInfo = materialInfoMapper.getMaxTime();
        Material material = new Material();
        material.setUpdateTime(materialInfo.getUpdateTime());
        logger.info("MaterialInfoServiceImpl::add material.getupdateTime:{}",material.getUpdateTime());
        List<Material> materialList = iMaterialDubboService.list(material);
        logger.info("MaterialInfoServiceImpl::add materialList.size:{}",(StringUtils.isEmpty(materialList))?0:materialList.size());
        for (Material material1 : materialList) {
            materialInfo = new MaterialInfo();
            materialInfo = this.toMaterialInfo(material1);
            materialInfoList.add(materialInfo);         
        }
        if (materialInfoList != null && materialInfoList.size() > 0) {
            materialInfoMapper.batchInsertOnDuplicateKeyUpdate(materialInfoList);
        }
    }


    private MaterialInfo toMaterialInfo(Material material) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MaterialInfo infoData = new MaterialInfo();
        infoData.setMaterialCode(material.getMaterialCode());
        infoData.setMaterialName(material.getMaterialName());
        infoData.setTerm(material.getTerm());
        infoData.setPrice(material.getPrice());
        infoData.setTaxRate(material.getTaxRate());
        infoData.setProductId(material.getProductId());
        infoData.setProductName(material.getProductName());
        infoData.setProductTypeName(material.getProductTypeName());
        infoData.setMaterialTypeId(material.getMaterialTypeId());
        infoData.setMaterialTypeName(material.getMaterialTypeName());
        infoData.setDeviceTypeId(material.getDeviceTypeId());
        infoData.setDeviceType(material.getDeviceType());
        infoData.setFirstLevelId(material.getFirstLevelId());
        infoData.setFirstLevelName(material.getFirstLevelName());
        infoData.setSecondLevelId(material.getSecondLevelId());
        infoData.setSecondLevelName(material.getSecondLevelName());
        infoData.setLastOperatorId(material.getLastOperatorId());
        infoData.setLastOperatorName(material.getLastOperatorName());
        infoData.setCreateTime(material.getCreateTime());
        infoData.setUpdateTime(material.getUpdateTime());  
        infoData.setNdScan(material.getNdScan());
        return infoData;
    }


}
