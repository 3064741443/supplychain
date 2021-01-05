package cn.com.glsx.supplychain.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;
import cn.com.glsx.supplychain.model.RequestLog;


@Mapper
public interface RequestLogMapper extends OreMapper<RequestLog>  {
	int insert(RequestLog requestLog);
	
	RequestLog getRequestLog(RequestLog requestLog);
}
