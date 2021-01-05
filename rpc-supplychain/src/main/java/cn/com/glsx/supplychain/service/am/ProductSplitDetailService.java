package cn.com.glsx.supplychain.service.am;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.mapper.am.MaterialInfoMapper;
import cn.com.glsx.supplychain.mapper.am.ProductSplitDetailMapper;
import cn.com.glsx.supplychain.mapper.am.ProductSplitMapper;
import cn.com.glsx.supplychain.model.am.MaterialInfo;
import cn.com.glsx.supplychain.model.am.ProductSplit;
import cn.com.glsx.supplychain.model.am.ProductSplitDetail;
import cn.com.glsx.supplychain.util.StringUtil;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.oms.bigdata.service.fb.model.Material;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName ProductSplitDetailService
 * @Author leiming
 * @Param
 * @Date 2019/9/17 9:57
 * @Version 1.0
 **/
@Service
@Transactional
public class ProductSplitDetailService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductSplitDetailMapper productSplitDetailMapper;

    @Autowired
    private IMaterialDubboService iMaterialDubboService;

    @Autowired
    private ProductSplitMapper productSplitMapper;

    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    /**
     * 产品拆分详情分页
     *
     * @param record 产品拆分详情对象
     * @return
     */
    public Page<ProductSplitDetail> listProductSplitDetail(int pageNum, int pageSize, ProductSplitDetail record) {
        Page<ProductSplitDetail> result;
        Page<ProductSplitDetail> list = new Page<>();
        ProductSplitDetail productSplitDetail = new ProductSplitDetail();
        PageHelper.startPage(pageNum, 999999);
        result = productSplitDetailMapper.getlistProductSplitDetail(record);
        if (result.getResult() != null && result.getResult().size() > 0) {
            MaterialInfo material = new MaterialInfo();
            for (ProductSplitDetail data : result) {
                if (data.getChannel() == null) {
                    data.setSource("客户");
                } else {
                    data.setSource("渠道");
                }
                if (data.getType() == (byte) 1) {
                    material.setFirstLevelName(data.getMaterialCode());
                    List<MaterialInfo> materialList = materialInfoMapper.select(material);
                    for (MaterialInfo materialdate : materialList) {
                        if (data.getMaterialCode().equals(materialdate.getFirstLevelName())) {
                            productSplitDetail = new ProductSplitDetail();
                            productSplitDetail.setProductCode(data.getProductCode());
                            productSplitDetail.setProductName(data.getProductName());
                            productSplitDetail.setMaterialName(materialdate.getMaterialName());
                            productSplitDetail.setMaterialCode(materialdate.getMaterialCode());
                            productSplitDetail.setOnePackage(data.getOnePackage());
                            productSplitDetail.setServiceTime(data.getServiceTime());
                            productSplitDetail.setMerchantCode(data.getMerchantCode());
                            list.add(productSplitDetail);
                        }
                    }
                } else {
                    list.add(data);
                }
            }

            //去重一个产品内的物料
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (i != j
                            && list.get(i).getProductCode().equals(list.get(j).getProductCode())
                            && list.get(i).getMaterialCode().equals(list.get(j).getMaterialCode())) {
                        list.remove(list.get(j));
                    }
                }
            }

            //是否有物料搜索
            if (record.getMaterialCode() != null && record.getMaterialName() != null && !"".equals(record.getMaterialCode()) && !"".equals(record.getMaterialName())) {
                Page<ProductSplitDetail> lists = new Page<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getMaterialName().equals(record.getMaterialName())
                            || list.get(i).getMaterialCode().equals(record.getMaterialCode())) {
                        lists.add(list.get(i));
                    }
                }
                return lists;
            }

        }
        return list;
    }


    /**
     * 获取产品拆分详情信息
     *
     * @param productSplitDetail 产品拆分详情对象
     * @return
     */
    public ProductSplitDetail getProductSplitDetailInfo(ProductSplitDetail productSplitDetail) {
        logger.info("查询产品拆分信息入参:{}", productSplitDetail);
        ProductSplitDetail result;
        result = productSplitDetailMapper.selectOne(productSplitDetail);
        return result;
    }

    /**
     * 获取产品拆分详情信息List
     *
     * @param productSplitDetail 产品拆分详情对象
     * @return
     */
    public List<ProductSplitDetail> getProductSplitDetailList(ProductSplitDetail productSplitDetail) {
        logger.info("查询产品拆分详情信息入参:{}", productSplitDetail);
        List<ProductSplitDetail> result;
        result = productSplitDetailMapper.select(productSplitDetail);
        return result;
    }


    /**
     * 经销商下单的产品详情信息(productType为0的产品)
     *
     * @param productSplitDetailList 产品拆分详情对象
     * @return
     */
    public List<ProductSplitDetail> getProductSplitDetailByProductTypeZeroList(List<ProductSplitDetail> productSplitDetailList) {
        logger.info("查询产品拆分信息入参:{}", productSplitDetailList);
        List<ProductSplitDetail> result;
        List<ProductSplitDetail> productSplitDetailTypeList = productSplitDetailMapper.getProductSplitDetailByProductTypeList(productSplitDetailList);
        result = productSplitDetailMapper.getProductSplitDetailByProductTypeZeroList(productSplitDetailList);
        Material material = new Material();
        List<ProductSplitDetail> list = new ArrayList<>();
        ProductSplitDetail splitDetail;
        for (ProductSplitDetail data : result) {
            if (data.getType() != null && data.getType().equals((byte) 1)) {
                logger.info("查询产品拆分信息的物料大类:{}", data.getMaterialCode());
                material.setFirstLevelName(data.getMaterialCode());
                List<Material> materialList = iMaterialDubboService.list(material);
                logger.info("根据物料大类查询的返回结果条数" + materialList.size());
                for (Material material1 : materialList) {
                    if (data.getMaterialCode().equals(material1.getFirstLevelName())) {
                        splitDetail = new ProductSplitDetail();
                        splitDetail.setId(data.getId());
                        splitDetail.setProductCode(data.getProductCode());
                        splitDetail.setMaterialName(material1.getMaterialName());
                        splitDetail.setMaterialCode(material1.getMaterialCode());
                        splitDetail.setChannel(data.getChannel());
                        splitDetail.setMerchantCode(data.getMerchantCode());
                        list.add(splitDetail);
                    }
                }
            } else {
                list.add(data);
            }
        }

        logger.info("根据物料大类查询的返回结果  ----结束");
        //拼装所有物料
        for (int i = 0; i < productSplitDetailTypeList.size(); i++) {
            String materialCodeString = productSplitDetailTypeList.get(i).getMaterialCode();
            for (int j = 0; j < productSplitDetailTypeList.size(); j++) {
                if (i != j
                        && productSplitDetailTypeList.get(i).getProductCode().equals(productSplitDetailTypeList.get(j).getProductCode())) {
                    materialCodeString = materialCodeString + productSplitDetailTypeList.get(j).getMaterialCode();
                    productSplitDetailTypeList.get(i).setMaterialCodeString(materialCodeString);
                }
            }
        }
        logger.info("拼装所有物料  ----结束");

        //物料字符串写入到硬件物料
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < productSplitDetailTypeList.size(); j++) {
                if (list.get(i).getProductCode().equals(productSplitDetailTypeList.get(j).getProductCode())) {
                    list.get(i).setMaterialCodeString(productSplitDetailTypeList.get(j).getMaterialCodeString());
                }
            }
        }
        logger.info("物料字符串写入到硬件物料  ----结束");
        //去重一个产品内的物料
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i != j
                        && list.get(i).getProductCode().equals(list.get(j).getProductCode())
                        && list.get(i).getMaterialCode().equals(list.get(j).getMaterialCode())) {
                    list.remove(list.get(j));
                }
            }
        }
        logger.info("去重一个产品内的物料  ----结束");
        //补空
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaterialCodeString() == null) {
                list.get(i).setMaterialCodeString("null");
            }
        }
        logger.info("补空  ----结束");
        //去重所有物料一样的
        List<ProductSplitDetail> productSplitDetails = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int key = 0;
            for (int j = 0; j < productSplitDetails.size(); j++) {
                if (list.get(i).getMaterialCode().equals(productSplitDetails.get(j).getMaterialCode())
                        && list.get(i).getMaterialCodeString().equals(productSplitDetails.get(j).getMaterialCodeString())) {
                    key++;
                    break;
                }
            }
            if(key == 0 ){
                productSplitDetails.add(list.get(i));
            }
        }
        logger.info("去重所有物料一样的  ----结束");
        return list;
    }

    /**
     * 新增产品拆分详情
     *
     * @param productSplitDetail 产品拆分详情对象
     * @return 返回成功条数
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer add(ProductSplitDetail productSplitDetail) {
        logger.info("新增产品拆分详情入参:{}", productSplitDetail);
        Integer result;
        result = productSplitDetailMapper.add(productSplitDetail);
        return result;
    }

    /**
     * 修改产品拆分详情
     *
     * @param productSplitDetail 产品拆分详情对象
     * @return 返回成功条数
     */
    public Integer updateById(ProductSplitDetail productSplitDetail) {
        logger.info("修改产品拆分详情入参:{}", productSplitDetail);
        Integer result;
        result = productSplitDetailMapper.updateById(productSplitDetail);
        return result;
    }
    
    public List<ProductSplitDetail> listProductSplitDetailByProductCode(String productCode){
    	Example example = new Example(ProductSplitDetail.class);
    	example.createCriteria().andEqualTo("productCode", productCode)
    							.andEqualTo("deletedFlag","N");
    	return productSplitDetailMapper.selectByExample(example);
    }

}
