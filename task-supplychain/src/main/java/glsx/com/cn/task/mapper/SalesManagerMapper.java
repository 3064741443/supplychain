package glsx.com.cn.task.mapper;

import cn.com.glsx.supplychain.model.bs.Sales;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalesManagerMapper {

    int insertList(List<Sales> salesList);
}
