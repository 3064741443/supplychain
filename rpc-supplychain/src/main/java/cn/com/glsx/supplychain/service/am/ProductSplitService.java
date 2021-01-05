package cn.com.glsx.supplychain.service.am;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.MaterialTypeEnum;
import cn.com.glsx.supplychain.enums.ProductHistoryPriceEnum;
import cn.com.glsx.supplychain.enums.ProductSplitServiceTypeEnum;
import cn.com.glsx.supplychain.mapper.am.*;
import cn.com.glsx.supplychain.model.am.*;
import cn.com.glsx.supplychain.util.StringUtil;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.oms.bigdata.service.fb.model.Material;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ProductSplitService
 * @Author leiming
 * @Param
 * @Date 2019/9/17 9:54
 * @Version 1.0
 **/
@Service
@Transactional
public class ProductSplitService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductSplitMapper productSplitMapper;
    
    @Autowired
    private ProductSplitSourceFlagMapper productSplitSourceFlagMapper;

    @Autowired
    private ProductSplitDetailMapper productSplitDetailMapper;

    @Autowired
    private ProductSplitHistoryPriceMapper productSplitHistoryPriceMapper;

    @Autowired
    private IMaterialDubboService iMaterialDubboService;

    @Autowired
    private ProductMerchantHideMapper productMerchantHideMapper;

    @Autowired
    private MaterialInfoMapper materialInfoMapper;
    
    private void fillProductCodesByMaterial(ProductSplit productSplit)
    {
    	if(StringUtils.isEmpty(productSplit))
    	{
    		return;
    	}
    	productSplit.setListCheckProductCodes(null);
    	if(StringUtils.isEmpty(productSplit.getMaterialCode()))
    	{
    		return;
    	}
    	List<String> listProductCode = productSplitDetailMapper.listProductCodesByMaterialCode(productSplit.getMaterialCode());
    	if(!StringUtils.isEmpty(listProductCode) && listProductCode.size() > 0)
    	{
    		productSplit.setListCheckProductCodes(listProductCode);	
    	}
    }

    /**
     * 获取产品拆分分页列表
     *
     * @param productSplit 产品拆分对象
     * @return
     */
    public Page<ProductSplit> listProductSplit(int pageNum, int pageSize, ProductSplit productSplit) {
        logger.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, productSplit);
        
        fillProductCodesByMaterial(productSplit);
        
        Page<ProductSplit> result;
        PageHelper.startPage(pageNum, pageSize);
        result = productSplitMapper.pageListProductSplit(productSplit);
        if (!StringUtil.isEmpty(result)) {
            for (int i = 0; i < result.getResult().size(); i++) {
                Double softwarePrice = 0.0; //网联智能硬件价格
                Double serviceSum = 0.0;//服务价格
                Double hardwarePrice = 0.0;//硬件价格
                Double pjPrice = 0.0;//配件价格

                ProductSplitDetail productSplitDetail = new ProductSplitDetail();
                productSplitDetail.setProductCode(result.getResult().get(i).getProductCode());
                productSplitDetail.setDeletedFlag("N");
                result.getResult().get(i).setProductSplitDetailList(productSplitDetailMapper.select(productSplitDetail));

                ProductSplitHistoryPrice productSplitHistoryPrice = new ProductSplitHistoryPrice();
                productSplitHistoryPrice.setProductCode(result.getResult().get(i).getProductCode());
                productSplitHistoryPrice.setType((byte) 0);
                productSplitHistoryPrice.setDeletedFlag("N");
                result.getResult().get(i).setProductSplitHistoryPriceList(productSplitHistoryPriceMapper.select(productSplitHistoryPrice));

                if (result.getResult().get(i).getProductSplitHistoryPriceList().size() > 0) {
                    for (ProductSplitHistoryPrice list : result.getResult().get(i).getProductSplitHistoryPriceList()) {
                        if (list.getType() == ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_NOW.getCode()) {
                            //价格
                            if ("1".equals(list.getProductType()) || "2".equals(list.getProductType())) {
                                softwarePrice += list.getPrice();
                            }
                            if ("3".equals(list.getProductType()) || "4".equals(list.getProductType()) || "5".equals(list.getProductType()) || "6".equals(list.getProductType())) {
                                serviceSum += list.getPrice();
                            }
                            if ("0".equals(list.getProductType())) {
                                if (list.getPrice() != 0.0) {
                                    hardwarePrice = list.getPrice();
                                }
                            }
                            if ("7".equals(list.getProductType())) {
                                if (list.getPrice() != 0.0) {
                                    pjPrice = list.getPrice();
                                }
                            }
                        }
                    }
                    result.getResult().get(i).setSoftWareSum(softwarePrice + hardwarePrice);
                    result.getResult().get(i).setServiceSum(serviceSum);
                    result.getResult().get(i).setSaleSum(softwarePrice + hardwarePrice + serviceSum + pjPrice);
                }
            }
        }
        return result;
    }

    /**
     * 获取产品拆分Info
     *
     * @param productSplit 产品拆分对象
     * @return
     */
    public ProductSplit getProductSplitInfo(ProductSplit productSplit) {
        logger.info("查询产品拆分列表入参:{}", productSplit);
        ProductSplit result;
        result = productSplitMapper.selectOne(productSplit);
        if (result != null) {
            logger.info("查询产品拆分详情列表List入参:{}", result.getProductCode());
            ProductSplitDetail productSplitDetail = new ProductSplitDetail();
            productSplitDetail.setProductCode(result.getProductCode());
            productSplitDetail.setDeletedFlag("N");
            result.setProductSplitDetailList(productSplitDetailMapper.select(productSplitDetail));

            logger.info("查询产品历史价格拆分详情列表List入参:{}", result.getProductCode());
            ProductSplitHistoryPrice productSplitHistoryPrice = new ProductSplitHistoryPrice();
            productSplitHistoryPrice.setProductCode(result.getProductCode());
            productSplitHistoryPrice.setType(productSplit.getType());
            productSplitHistoryPrice.setDeletedFlag("N");
            result.setProductSplitHistoryPriceList(productSplitHistoryPriceMapper.getProductSplitHistoryPrice(productSplitHistoryPrice));

            ProductMerchantHide productMerchantHide = new ProductMerchantHide();
            productMerchantHide.setProductCode(result.getProductCode());
            productMerchantHide.setDeletedFlag("N");
            result.setProductMerchantHideList(productMerchantHideMapper.select(productMerchantHide));
            
            ProductSplitSourceFlag productSplitSourceFlag = new ProductSplitSourceFlag();
            productSplitSourceFlag.setProductCode(result.getProductCode());
            productSplitSourceFlag = productSplitSourceFlagMapper.selectOne(productSplitSourceFlag);
            if(null != productSplitSourceFlag){
            	result.setSourceFlag(productSplitSourceFlag.getSourceFlag());
            }
        }
        
        return result;
    }

    /**
     * 获取产品拆分List
     *
     * @param productSplit 产品拆分对象
     * @return
     */
    public List<ProductSplit> getProductSplitList(ProductSplit productSplit) {
        logger.info("查询产品拆分列表List入参:{}", productSplit);
        List<ProductSplit> result;
        if(null != productSplit.getListCheckProductCodes()){
        	if(productSplit.getListCheckProductCodes().isEmpty()){
        		productSplit.setListCheckProductCodes(null);
        	}
        }
        result = productSplitMapper.getProductSplitList(productSplit);
        if (!StringUtil.isEmpty(result)) {
            for (int i = 0; i < result.size(); i++) {
                Double softwarePrice = 0.0; //网联智能硬件价格
                Double serviceSum = 0.0;//服务价格
                Double hardwarePrice = 0.0;//硬件价格
                Double pjPrice = 0.0;//配件

                ProductSplitHistoryPrice productSplitHistoryPrice = new ProductSplitHistoryPrice();
                productSplitHistoryPrice.setProductCode(result.get(i).getProductCode());
                productSplitHistoryPrice.setType((byte) 0);
                productSplitHistoryPrice.setDeletedFlag("N");
                List<ProductSplitHistoryPrice> productSplitHistoryPriceList = productSplitHistoryPriceMapper.select(productSplitHistoryPrice);
                if (productSplitHistoryPriceList != null && productSplitHistoryPriceList.size() > 0) {
                    for (int j = 0; j < productSplitHistoryPriceList.size(); j++) {
                        if ("1".equals(productSplitHistoryPriceList.get(j).getProductType()) || "2".equals(productSplitHistoryPriceList.get(j).getProductType())) {
                            softwarePrice += productSplitHistoryPriceList.get(j).getPrice();
                        }
                        if ("3".equals(productSplitHistoryPriceList.get(j).getProductType()) || "4".equals(productSplitHistoryPriceList.get(j).getProductType()) || "5".equals(productSplitHistoryPriceList.get(j).getProductType()) || "6".equals(productSplitHistoryPriceList.get(j).getProductType())) {
                            serviceSum += productSplitHistoryPriceList.get(j).getPrice();
                        }
                        if ("0".equals(productSplitHistoryPriceList.get(j).getProductType())) {
                            hardwarePrice = productSplitHistoryPriceList.get(j).getPrice();
                        }
                        if("7".equals(productSplitHistoryPriceList.get(j).getProductType())){
                            pjPrice = productSplitHistoryPriceList.get(j).getPrice();
                        }
                    }
                }
                result.get(i).setSaleSum(softwarePrice + hardwarePrice + serviceSum + pjPrice);
                result.get(i).setChannel(productSplit.getChannel());
            }
        }
        return result;
    }

    /**
     * 新增产品拆分信息
     *
     * @param productSplit 产品拆分对象
     * @return 返回成功条数
     */
    public Integer add(ProductSplit productSplit) {
        logger.info("新增产品拆分入参:{}", productSplit);
        Integer result;
        //产品编号生成
        String productSplitCode = StringUtil.getOrderNo();
        productSplit.setProductCode(productSplitCode);
        if (!StringUtils.isEmpty(productSplit) || productSplit.getProductSplitDetailList().size() > 0 || productSplit.getProductSplitHistoryPriceList().size() > 0) {
            logger.info("新增产品拆分详情入参List :{}", productSplit.getProductSplitDetailList());
            for (ProductSplitDetail list : productSplit.getProductSplitDetailList()) {
                Material material = new Material();
                material.setFirstLevelName(list.getMaterialCode());
                List<Material> materialList = iMaterialDubboService.list(material);
                if (materialList != null && materialList.size() > 0) {
                    list.setType((byte) 1);
                } else {
                    list.setType((byte) 2);
                }
                list.setProductCode(productSplitCode);
                list.setCreatedBy(productSplit.getCreatedBy());
                list.setCreatedDate(new Date());
                list.setUpdatedBy(productSplit.getUpdatedBy());
                list.setUpdatedDate(new Date());
                list.setDeletedFlag("N");
                productSplitDetailMapper.insert(list);
            }

            logger.info("新增产品拆分历史价格入参List =" + productSplit.getProductSplitHistoryPriceList());
            Date date = new Date();
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sp.format(date);
            try {
                date = sp.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (productSplit.getProductSplitHistoryPriceList() != null && productSplit.getProductSplitHistoryPriceList().size() > 0) {
                for (ProductSplitHistoryPrice list : productSplit.getProductSplitHistoryPriceList()) {
                    list.setTime(date);
                    list.setProductCode(productSplitCode);
                    list.setCreatedBy(productSplit.getCreatedBy());
                    list.setCreatedDate(new Date());
                    list.setUpdatedBy(productSplit.getUpdatedBy());
                    list.setUpdatedDate(new Date());
                    list.setDeletedFlag("N");
                    productSplitHistoryPriceMapper.insert(list);
                }
            }
        }
        productSplit.setDeletedFlag("N");
        if(StringUtils.isEmpty(productSplit.getPlusJrfk()))
        {
        	productSplit.setPlusJrfk("N");
        }
        result = productSplitMapper.insert(productSplit);
        if (productSplit.getProductMerchantHideList() != null && productSplit.getProductMerchantHideList().size() > 0) {
            for (ProductMerchantHide productMerchantHide : productSplit.getProductMerchantHideList()) {
                productMerchantHide.setProductCode(productSplitCode);
                productMerchantHide.setCreatedBy(productSplit.getCreatedBy());
                productMerchantHide.setCreatedDate(productSplit.getCreatedDate());
                productMerchantHide.setUpdatedBy(productSplit.getUpdatedBy());
                productMerchantHide.setUpdatedDate(productSplit.getUpdatedDate());
                productMerchantHide.setDeletedFlag("N");
            }
            productMerchantHideMapper.insertList(productSplit.getProductMerchantHideList());
        }
        
        //如果是金融风控插入有源无源标识
        if(productSplit.getServiceType().equals(ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_TWO.getCode())){
        	ProductSplitSourceFlag sourceFlagData = new ProductSplitSourceFlag();
        	sourceFlagData.setProductCode(productSplit.getProductCode());
        	sourceFlagData.setSourceFlag(productSplit.getSourceFlag());
        	sourceFlagData.setHardwareContainSource(productSplit.getHardwareContainSource());
        	sourceFlagData.setSourceProportion(productSplit.getSourceProportion());
        	sourceFlagData.setNotSourceProportion(productSplit.getNotSourceProportion());
        	sourceFlagData.setCreatedBy(productSplit.getCreatedBy());
        	sourceFlagData.setUpdatedBy(productSplit.getUpdatedBy());
        	sourceFlagData.setCreatedDate(productSplit.getCreatedDate());
        	sourceFlagData.setUpdatedDate(productSplit.getUpdatedDate());
        	productSplitSourceFlagMapper.insert(sourceFlagData);
        }
        
        return result;
    }

    /**
     * 修改产品拆分信息
     *
     * @param productSplit 产品拆分对象
     * @return 返回成功条数
     */
    public Integer updateById(ProductSplit productSplit) {
        logger.info("修改产品拆分入参:=" + productSplit);
        Integer result;
        Date newDate = new Date();
        ProductSplit productSplitInfo = productSplitMapper.getProductSplitByid(productSplit.getId());
        if (!StringUtil.isEmpty(productSplitInfo)) {
            MaterialInfo materialInfo = new MaterialInfo();
            for (ProductSplitDetail productSplitDetail : productSplit.getProductSplitDetailList()) {
                materialInfo = new MaterialInfo();
                materialInfo.setFirstLevelName(productSplitDetail.getMaterialCode());
                List<MaterialInfo> materialInfoList = materialInfoMapper.select(materialInfo);
                if(materialInfoList != null && materialInfoList.size() > 0){
                    productSplitDetail.setType((byte)1);
                }else{
                    productSplitDetail.setType((byte)2);
                }
            }
            productSplitDetailMapper.batchInsertOnDuplicateKeyUpdateProductSplitDetail(productSplit.getProductSplitDetailList());

            ProductSplitHistoryPrice price = new ProductSplitHistoryPrice();
            price.setProductCode(productSplitInfo.getProductCode());
            List<ProductSplitHistoryPrice> productSplitHistoryPriceList = productSplitHistoryPriceMapper.getProductSplitHistoryPriceByTypeNotTwo(price);
            List<ProductSplitHistoryPrice> productSplitHistoryPriceAddList = new ArrayList<>();
            ProductSplitHistoryPrice priceAddData;
            ProductSplitHistoryPrice priceDeleteData;
            ProductSplitHistoryPrice priceUpdate;
            for(ProductSplitHistoryPrice timeData : productSplitHistoryPriceList){
                for(ProductSplitHistoryPrice addData : productSplit.getProductSplitHistoryPriceAddList()){
                    priceAddData = new ProductSplitHistoryPrice();
                    priceAddData.setTime(timeData.getTime());
                    priceAddData.setProductCode(productSplitInfo.getProductCode());
                    priceAddData.setServiceType(addData.getServiceType());
                    priceAddData.setMaterialCode(addData.getMaterialCode());
                    priceAddData.setMaterialName(addData.getMaterialName());
                    priceAddData.setTaxRate(addData.getTaxRate());
                    priceAddData.setType(timeData.getType());
                    priceAddData.setPrice(addData.getPrice());
                    priceAddData.setProductType(addData.getProductType());
                    priceAddData.setCreatedBy(productSplitInfo.getUpdatedBy());
                    priceAddData.setCreatedDate(newDate);
                    priceAddData.setUpdatedBy(productSplitInfo.getUpdatedBy());
                    priceAddData.setUpdatedDate(newDate);
                    priceAddData.setDeletedFlag("N");
                    productSplitHistoryPriceAddList.add(priceAddData);
                }
                for(ProductSplitHistoryPrice deleteData : productSplit.getProductSplitHistoryPriceDeleteList()){
                    priceDeleteData = new ProductSplitHistoryPrice();
                    priceDeleteData.setTime(timeData.getTime());
                    priceDeleteData.setProductCode(productSplitInfo.getProductCode());
                    priceDeleteData.setMaterialCode(deleteData.getMaterialCode());
                    priceDeleteData.setProductType(deleteData.getProductType());
                    priceDeleteData.setUpdatedDate(newDate);
                    priceDeleteData.setUpdatedBy(productSplitInfo.getUpdatedBy());
                    priceDeleteData.setDeletedFlag("Y");
                    productSplitHistoryPriceMapper.updateDeletedFlagByProductCodeTimeMaterialCodeProductType(priceDeleteData);
                }
            }
            if(productSplitHistoryPriceAddList != null & productSplitHistoryPriceAddList.size() > 0){
                productSplitHistoryPriceMapper.insertList(productSplitHistoryPriceAddList);
            }
            for(ProductSplitHistoryPrice updateData : productSplit.getProductSplitHistoryPriceUpdateList()){
                priceUpdate = new ProductSplitHistoryPrice();
                priceUpdate.setProductCode(productSplitInfo.getProductCode());
                priceUpdate.setProductType(updateData.getProductType());
                priceUpdate.setPrice(updateData.getPrice());
                priceUpdate.setTaxRate(updateData.getTaxRate());
                priceUpdate.setMaterialCode(updateData.getMaterialCode());
                priceUpdate.setUpdatedDate(newDate);
                priceUpdate.setUpdatedBy(productSplitInfo.getUpdatedBy());
                productSplitHistoryPriceMapper.updateByProductCodeMaterialCodeProductType(priceUpdate);
           //     productSplitHistoryPriceMapper.updateTaxRateByProductType(priceUpdate);
            }
        }
        productSplit.setUpdatedDate(newDate);
        ProductMerchantHide productMerchantHide = new ProductMerchantHide();
        productMerchantHide.setProductCode(productSplitInfo.getProductCode());
        productMerchantHide.setDeletedFlag("N");
        List<ProductMerchantHide> productMerchantHides = productMerchantHideMapper.select(productMerchantHide);
        if(productMerchantHides != null && productMerchantHides.size() > 0){
            for (int i = 0; i < productSplit.getProductMerchantHideList().size(); i++) {
                for (int j = 0; j < productMerchantHides.size(); j++) {
                    if (productSplit.getProductMerchantHideList().get(i).getProductCode().equals(productMerchantHides.get(j).getProductCode())
                            && productSplit.getProductMerchantHideList().get(i).getMerchantCode().equals(productMerchantHides.get(j).getMerchantCode())) {
                        productMerchantHides.remove(j);
                        j--;
                    }
                }
                productSplit.getProductMerchantHideList().get(i).setCreatedBy(productSplit.getCreatedBy());
                productSplit.getProductMerchantHideList().get(i).setCreatedDate(newDate);
                productSplit.getProductMerchantHideList().get(i).setUpdatedBy(productSplit.getUpdatedBy());
                productSplit.getProductMerchantHideList().get(i).setUpdatedDate(productSplit.getUpdatedDate());
                productSplit.getProductMerchantHideList().get(i).setDeletedFlag("N");
            }
            for (ProductMerchantHide productMerchantHide1 : productMerchantHides) {
                productMerchantHide1.setDeletedFlag("Y");
                productSplit.getProductMerchantHideList().add(productMerchantHide1);
            }
        }
        result = productSplitMapper.updateById(productSplit);
        if (productSplit.getProductMerchantHideList() != null && productSplit.getProductMerchantHideList().size() > 0) {
            productMerchantHideMapper.batchInsertOnDuplicateKeyUpdateProductMerchantHide(productSplit.getProductMerchantHideList());
        }
        
        if(productSplit.getServiceType().equals(ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_TWO.getCode())){
        	
        	ProductSplitSourceFlag sourceFlagCondition = new ProductSplitSourceFlag();
        	sourceFlagCondition.setProductCode(productSplitInfo.getProductCode());
        	ProductSplitSourceFlag sourceFlagDb = productSplitSourceFlagMapper.selectOne(sourceFlagCondition);
        	if(null != sourceFlagDb){
        		sourceFlagDb.setSourceFlag(productSplit.getSourceFlag());
        		sourceFlagDb.setHardwareContainSource(productSplit.getHardwareContainSource());
        		sourceFlagDb.setSourceProportion(productSplit.getSourceProportion());
        		sourceFlagDb.setNotSourceProportion(productSplit.getNotSourceProportion());
        		sourceFlagDb.setCreatedBy(productSplit.getCreatedBy());
        		sourceFlagDb.setUpdatedBy(productSplit.getUpdatedBy());
        		productSplitSourceFlagMapper.updateByPrimaryKeySelective(sourceFlagDb);
        	}else{
        		sourceFlagDb = new ProductSplitSourceFlag();
        		sourceFlagDb.setProductCode(productSplitInfo.getProductCode());
        		sourceFlagDb.setSourceFlag(productSplit.getSourceFlag());
        		sourceFlagDb.setHardwareContainSource(productSplit.getHardwareContainSource());
        		sourceFlagDb.setSourceProportion(productSplit.getSourceProportion());
        		sourceFlagDb.setNotSourceProportion(productSplit.getNotSourceProportion());
        		sourceFlagDb.setCreatedBy(productSplit.getCreatedBy());
        		sourceFlagDb.setUpdatedBy(productSplit.getUpdatedBy());
        		sourceFlagDb.setCreatedDate(productSplit.getCreatedDate());
        		sourceFlagDb.setUpdatedDate(productSplit.getUpdatedDate());
            	productSplitSourceFlagMapper.insert(sourceFlagDb);
        	}
        }
        return result;
    }

    /**
     * 修改产品拆分信息状态
     *
     * @param productSplit 产品拆分对象
     * @return 返回成功条数
     */
    public Integer updateProductSplitStatus(ProductSplit productSplit) {
        Integer result;
        productSplit.setUpdatedDate(new Date());
        result = productSplitMapper.updateProductSplitStatus(productSplit);
        return result;
    }

    /**
     * 删除产品拆分信息
     *
     * @param productSplit 产品拆分对象
     * @return 返回成功条数
     */
    public Integer updateDeletedFlagById(ProductSplit productSplit) {
        logger.info("修改产品拆分入参:=" + productSplit);
        Integer result;
        result = productSplitMapper.updateDeletedFlagById(productSplit);
        return result;
    }
    
    public ProductSplit getProductSplitBaseInfo(String productCode) throws RpcServiceException
    {
    	try
    	{
        	ProductSplit condition = new ProductSplit();
        	condition.setProductCode(productCode);
        	condition.setDeletedFlag("N");
        	return productSplitMapper.selectOne(condition);
    	}
    	catch(Exception e)
    	{
    		logger.error(e.getMessage(),e);
    		throw new RpcServiceException(e.getMessage());
    	}
    	
    }
    
    public ProductSplit getProductSplitPrice(ProductSplit product) throws RpcServiceException
    {
    	try
    	{
    		if(StringUtils.isEmpty(product))
        	{
        		return product;
        	}
        	Example example = new Example(ProductSplitHistoryPrice.class);
        	example.createCriteria().andEqualTo("productCode",product.getProductCode())
    		.andEqualTo("type", (byte)0);
        	List<ProductSplitHistoryPrice> listPrice = productSplitHistoryPriceMapper.selectByExample(example);
        	Map<String,Integer> mapRepeated = new HashMap<String,Integer>();
        	Double doublePrice = 0.0;
        	Date dt = null;
        	if(!StringUtils.isEmpty(listPrice))
        	{
        		for(ProductSplitHistoryPrice price:listPrice)
        		{
        			dt = price.getTime();
        			Integer inter = mapRepeated.get(price.getProductType());
        			if(!StringUtils.isEmpty(inter))
    				{
    					continue;
    				}
        			mapRepeated.put(price.getProductType(), 1);
        			doublePrice += price.getPrice();
        		}
        	}
        	product.setPrice(doublePrice);
        	product.setTime(dt);
        	return product;
    	}
    	catch(Exception e)
    	{
    		logger.error(e.getMessage(),e);
    		throw new RpcServiceException(e.getMessage());
    	}	
    }
    
    public boolean isMaterialBelongProduct(String productCode,String materialCode,Map<String,Byte> mapMaterialType) throws RpcServiceException
    {
    	try
    	{
    		ProductSplitDetail condition = new ProductSplitDetail();
        	condition.setProductCode(productCode);
    		condition.setMaterialCode(materialCode);
    		condition.setProductType(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode()));
    		ProductSplitDetail detail = productSplitDetailMapper.selectOne(condition);
    		if(!StringUtils.isEmpty(detail))
    		{
    			if(!StringUtils.isEmpty(mapMaterialType))
    			{
    				mapMaterialType.put(materialCode, Byte.valueOf(detail.getProductType()));
    			}
    			return true;
    		}
    		condition.setProductType(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode()));
    		detail = productSplitDetailMapper.selectOne(condition);
    		if(!StringUtils.isEmpty(detail))
    		{
    			if(!StringUtils.isEmpty(mapMaterialType))
    			{
    				mapMaterialType.put(materialCode, Byte.valueOf(detail.getProductType()));
    			}
    			return true;
    		}
    		MaterialInfo materialCondition = new MaterialInfo();
    		materialCondition.setMaterialCode(materialCode);
    		MaterialInfo material = materialInfoMapper.selectOne(materialCondition);
    		if(StringUtils.isEmpty(material))
    		{
    			return false;
    		}
    		if(StringUtils.isEmpty(material.getFirstLevelName()))
    		{
    			return false;
    		}
    		condition.setMaterialCode(material.getFirstLevelName());
    		condition.setProductType(null);
    		detail = productSplitDetailMapper.selectOne(condition);
    		if(!StringUtils.isEmpty(detail))
    		{
    			if(!StringUtils.isEmpty(mapMaterialType))
    			{
    				mapMaterialType.put(materialCode, Byte.valueOf(detail.getProductType()));
    			}
    			return true;
    		}
    	}
    	catch(Exception e)
    	{
    		logger.error(e.getMessage(),e);
    		throw new RpcServiceException(e.getMessage());
    	}
    	return false;
    }
    
    public Map<String,List<ProductSplitDetail>> listProductSplitDetail(List<String> listProductCode, Map<String,List<ProductSplitDetail>>  mapProductDetail) throws RpcServiceException
    {
    	if(StringUtils.isEmpty(listProductCode) ||
    			listProductCode.size() == 0 ||
    			StringUtils.isEmpty(mapProductDetail))
    	{
    		return mapProductDetail;
    	}
    	try
    	{
    		Example example = new Example(ProductSplitDetail.class);
    		example.createCriteria().andIn("productCode", listProductCode)
    								.andEqualTo("deletedFlag", "N");
    		List<ProductSplitDetail> listSplitDetail = productSplitDetailMapper.selectByExample(example);
    		if(StringUtils.isEmpty(listSplitDetail))
    		{
    			return mapProductDetail;
    		}
    		for(ProductSplitDetail detail:listSplitDetail)
    		{
    			List<ProductSplitDetail> listDetail = mapProductDetail.get(detail.getProductCode());
    			if(StringUtils.isEmpty(listDetail))
    			{
    				listDetail = new ArrayList<ProductSplitDetail>();
    				mapProductDetail.put(detail.getProductCode(), listDetail);
    			}
    			listDetail.add(detail);
    		}
    	}
    	catch(Exception e)
    	{
    		logger.error(e.getMessage(),e);
    		throw new RpcServiceException(e.getMessage());
    	}
    	return mapProductDetail;
    }
    
    public Map<String,List<ProductSplitHistoryPrice>> listProductSplitPrice(List<String> listProductCode,Map<String,List<ProductSplitHistoryPrice>> mapProductPrice)throws RpcServiceException
    {
    	if(StringUtils.isEmpty(listProductCode) ||
    			listProductCode.size() == 0 ||
    			StringUtils.isEmpty(mapProductPrice))
    	{
    		return mapProductPrice;
    	}
    	try
    	{
    		Example example = new Example(ProductSplitHistoryPrice.class);
    		example.createCriteria().andIn("productCode", listProductCode)
    								.andEqualTo("type", "0")
    								.andEqualTo("deletedFlag", "N");
    		List<ProductSplitHistoryPrice> listSplitPrice = productSplitHistoryPriceMapper.selectByExample(example);
    		if(StringUtils.isEmpty(listSplitPrice))
    		{
    			return mapProductPrice;
    		}
    		for(ProductSplitHistoryPrice price:listSplitPrice)
    		{
    			List<ProductSplitHistoryPrice> listPrice = mapProductPrice.get(price.getProductCode());
    			if(StringUtils.isEmpty(listPrice))
    			{
    				listPrice = new ArrayList<ProductSplitHistoryPrice>();
    				mapProductPrice.put(price.getProductCode(), listPrice);
    			}
    			listPrice.add(price);
    		}
    	}
    	catch(Exception e)
    	{
    		logger.error(e.getMessage(),e);
    		throw new RpcServiceException(e.getMessage());
    	}
    	return mapProductPrice;
    }
    
    public Map<String,MaterialInfo> listMaterialInfo(List<String> listMaterialCode,Map<String,MaterialInfo> mapMaterialInfo)throws RpcServiceException
    {
    	if(StringUtils.isEmpty(listMaterialCode) ||
    			listMaterialCode.size() == 0 ||
    			StringUtils.isEmpty(mapMaterialInfo))
		{
			return mapMaterialInfo;
		}
    	try
    	{
    		Example example = new Example(MaterialInfo.class);
    		example.createCriteria().andIn("materialCode", listMaterialCode);
    		List<MaterialInfo> listMaterial = materialInfoMapper.selectByExample(example);
    		if(StringUtils.isEmpty(listMaterial))
    		{
    			return mapMaterialInfo;
    		}
    		for(MaterialInfo materialInfo:listMaterial)
    		{
    			mapMaterialInfo.put(materialInfo.getMaterialCode(), materialInfo);
    		}
    	}
    	catch(Exception e)
    	{
    		logger.error(e.getMessage(),e);
    		throw new RpcServiceException(e.getMessage());
    	}
    	return mapMaterialInfo;
    }
    
    //根据产品编号获取产品信息
    public ProductSplit getProductSplit(String productCode){
    	ProductSplit condition = new ProductSplit();
    	condition.setProductCode(productCode);
    	return productSplitMapper.selectOne(condition);
    }
    
    //根据渠道和商户号获取产品列表
    public List<ProductSplit> listProductSplitByMerchantCodeAndChannel(String merchantCode,Byte channel){
    	
    	Map<String,ProductSplit> mapResult = new HashMap<>();
    	Example example = new Example(ProductSplit.class);
    	example.createCriteria().andEqualTo("channel", channel)
    							.andEqualTo("deletedFlag", "N");
    	List<ProductSplit> listProductChannel = productSplitMapper.selectByExample(example);
    	example.clear();
    	example.createCriteria().andEqualTo("merchantCode", merchantCode)
    							.andEqualTo("deletedFlag", "N");
    	List<ProductSplit> listProductMerchant = productSplitMapper.selectByExample(example);
    	
    	ProductSplit productSplit = null;
    	if(null != listProductChannel){
    		for(ProductSplit product:listProductChannel){
    			productSplit = mapResult.get(product.getProductCode());
    			if(null != productSplit){
    				continue;
    			}
    			mapResult.put(product.getProductCode(), product);
    		}
    	}
    	if(null != listProductMerchant){
    		for(ProductSplit product:listProductMerchant){
    			productSplit = mapResult.get(product.getProductCode());
    			if(null != productSplit){
    				continue;
    			}
    			mapResult.put(product.getProductCode(), product);
    		}
    	}
    	return mapResult.values().stream().collect(Collectors.toList());
    	
    }
    
    
}
