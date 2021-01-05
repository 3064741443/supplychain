package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.dto.JXCMTWarehouseInfoDTO;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTWarehouseInfoMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTWarehouseInfo;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JXCMTWarehouseService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTWarehouseService.class);
	@Autowired
	private JXCMTWarehouseInfoMapper jxcmtWarehouseInfoMapper;
	
	public List<JXCMTWarehouseInfo> listWarehouseInfoByIds(List<Integer> listIds){
		if(null == listIds || listIds.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTWarehouseInfo.class);
		example.createCriteria().andIn("id", listIds);
		return jxcmtWarehouseInfoMapper.selectByExample(example);
	}
	
	public List<JXCMTWarehouseInfoDTO> listWarehouseInfoDtoByName(JXCMTWarehouseInfoDTO record){
		List<JXCMTWarehouseInfoDTO> listResult = new ArrayList<>();
		List<JXCMTWarehouseInfo> listWarehouseInfo = null;
		if(StringUtils.isEmpty(record.getName())){
			Example example = new Example(JXCMTWarehouseInfo.class);
			example.createCriteria().andLike("name", "%"+record.getName()+"%");
			listWarehouseInfo = jxcmtWarehouseInfoMapper.selectByExample(example);
		}else{
			listWarehouseInfo = jxcmtWarehouseInfoMapper.selectAll();
		}
		if(null == listWarehouseInfo || listWarehouseInfo.isEmpty()){
			return listResult;
		}
		JXCMTWarehouseInfoDTO dto = null;
		for(JXCMTWarehouseInfo info:listWarehouseInfo){
			dto = new JXCMTWarehouseInfoDTO();
			dto.setId(info.getId());
			dto.setName(info.getName());
			listResult.add(dto);
		}
		return listResult;
	}

	/**
	 * @author: luoqiang
	 * @description: 根据仓库名字查询仓库信息
	 * @date: 2020/9/10 13:42
	 * @param name
	 * @return: cn.com.glsx.supplychain.jxc.model.JXCMTWarehouseInfo
	 */
	public JXCMTWarehouseInfo getWarehouseByName(String name) throws RpcServiceException {
		logger.info("获取仓库信息参数为：" + name);
		try
		{
			/*Example example=new Example(JXCMTWarehouseInfo.class);
			example.createCriteria().andEqualTo("name",name)
					                .andEqualTo("deletedFlag","N");*/
			JXCMTWarehouseInfo jxcmtWarehouseInfo=new JXCMTWarehouseInfo();
			jxcmtWarehouseInfo.setName(name);
			jxcmtWarehouseInfo.setDeletedFlag("N");
			JXCMTWarehouseInfo warehouseInfo=jxcmtWarehouseInfoMapper.selectOne(jxcmtWarehouseInfo);
			return warehouseInfo;
		}catch (Exception e) {
			logger.error("根据仓库名字查询仓库信息异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,"根据仓库名字查询仓库信息异常");
		}
	}

	/**
	 * @author: luoqiang
	 * @description: 根据仓库名字查询仓库信息
	 * @date: 2020/9/10 13:42
	 * @param names
	 * @return: cn.com.glsx.supplychain.jxc.model.JXCMTWarehouseInfo
	 */
	public Map<String,JXCMTWarehouseInfo> listMapWareHouseInfo(List<String> names) throws RpcServiceException {
		logger.info("获取仓库信息参数为：" + JSON.toJSON(names));
		try
		{
			Example example=new Example(JXCMTWarehouseInfo.class);
			example.createCriteria().andIn("name",names)
					                .andEqualTo("deletedFlag","N");
			List<JXCMTWarehouseInfo>  warehouseInfoList=jxcmtWarehouseInfoMapper.selectByExample(example);
			if(StringUtils.isEmpty(warehouseInfoList)|| warehouseInfoList.size()==0){
				return new HashMap<>();
			}
			//mapResult = listBsAddress.stream().collect(Collectors.toMap(BsAddress::getId, a->a));
			Map<String,JXCMTWarehouseInfo> warehouseInfoMap=warehouseInfoList.stream().collect(Collectors.toMap(JXCMTWarehouseInfo::getName,a->a));
			return warehouseInfoMap;
		}catch (Exception e) {
			logger.error("根据仓库名字查询仓库信息异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,"根据仓库名字查询仓库信息异常");
		}
	}
	public String getWarehouseNameById(Integer id){
		if(id == null)
			return "";
		JXCMTWarehouseInfo condition = new JXCMTWarehouseInfo();
		condition.setId(id);
		JXCMTWarehouseInfo jxcmtWarehouseInfo = jxcmtWarehouseInfoMapper.selectOne(condition);
		if(null == jxcmtWarehouseInfo){
			return "";
		}
		return jxcmtWarehouseInfo.getName();
	}
	
	
	
	
	
	
	
	
}
