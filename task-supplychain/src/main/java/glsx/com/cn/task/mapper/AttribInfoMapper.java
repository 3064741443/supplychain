package glsx.com.cn.task.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.AttribInfo;

@Mapper
public interface AttribInfoMapper extends OreMapper<AttribInfo>  {
	
	List<AttribInfo> getAttribInfoByList(Integer type);

	Map<Integer, String> mapAttrInfo(Integer type);
	
	@MapKey("ID")
	//??????????
	Map<Integer, AttribInfo> getAttribInfoByMap();
	
	List<AttribInfo> getAttribInfoListByCondition(AttribInfo attribInfo);
	
	AttribInfo getAttribInfoById(Integer id);
	
	Page<AttribInfo> pageAttribInfo(AttribInfo record);
}