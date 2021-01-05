package glsx.com.cn.task.manager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.oms.bigdata.service.fb.model.Material;

@Component
public class MaterialServiceManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IMaterialDubboService materialService;
	
	public Material getMaterialByCode(String materialCode)
	{
		Material material = null;
		try
		{
			Material condition = new Material();
			condition.setMaterialCode(materialCode);
			List<Material> materialList = materialService.list(condition);
			if(materialList != null && materialList.size() > 0)
			{
				material = materialList.get(0);
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("MaterialServiceManager::getMaterialByCode err,Failed to get Material materialCode:" + materialCode);
		}
		return material;
	}

}
