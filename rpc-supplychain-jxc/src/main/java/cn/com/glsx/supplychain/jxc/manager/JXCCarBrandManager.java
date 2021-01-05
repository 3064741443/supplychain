package cn.com.glsx.supplychain.jxc.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glsx.biz.common.base.entity.Carbrand;
import com.glsx.biz.common.base.entity.Carseries;
import com.glsx.biz.common.base.service.CarbrandService;
import com.glsx.biz.common.base.service.CarseriesService;
import com.glsx.biz.common.base.service.CartypeService;

@Service
public class JXCCarBrandManager {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CarbrandService carBrandService;
	@Autowired
	private CarseriesService carSeriesSevice;
	@Autowired
	private CartypeService carTypeSevice;
	
	private Map<String,Integer> mapParentBrand = null;
	
	public Integer getParentBrandIdByName(String parentBrandName){
		Integer result = null;
		if(null != mapParentBrand){
			return mapParentBrand.get(parentBrandName);
		}
		mapParentBrand = new HashMap<>();
		List<Carbrand> listCarBrands = carBrandService.getParentCarbrandList();
		if(null == listCarBrands){
			return result;
		}
		for(Carbrand brand:listCarBrands){
			mapParentBrand.put(brand.getBrand(), brand.getBid());
		}
		return mapParentBrand.get(parentBrandName);
	}
	
	public Integer getSubBrandIdByName(Integer parantBrandId,String subBrandName){
		Integer result = null;
		if(null == parantBrandId){
			return result;
		}
		List<Carbrand> listCardbrand  = carBrandService.listSubCarbrandByParentId(parantBrandId);
		if(null == listCardbrand || listCardbrand.isEmpty()){
			return result;
		}
		for(Carbrand carbrand:listCardbrand){
			if(carbrand.getBrand().equals(subBrandName)){
				result = carbrand.getBid();
				break;
			}
		}
		return result;
	}
	
	public Integer getAudiIdByName(Integer subParentId,String audiName){
		Integer result = null;
		if(null == subParentId){
			return result;
		}
		List<Carseries> listCarseries =carSeriesSevice.getByCarbrandId(subParentId);
		if(null == listCarseries || listCarseries.isEmpty()){
			return result;
		}
		for(Carseries carseries:listCarseries){
			if(carseries.getCarseries().equals(audiName)){
				result = carseries.getCsid();
				break;
			}
		}
		return result;
	}
}
