package cn.com.glsx.rpc.supplychain.rdn.remote;

import org.springframework.stereotype.Component;

import cn.com.glsx.rpc.supplychain.rdn.dto.DemoDTO;
import cn.com.glsx.rpc.supplychain.rdn.model.DemoModel;

import com.alibaba.dubbo.config.annotation.Service;

@Component("DemoRemo")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class DemoRemoteImpl implements DemoRemote{

	@Override
	public DemoModel get(String name) {
		DemoModel model = new DemoModel();
		model.setName(name);
		return model;
	}

	@Override
	public DemoDTO convert(DemoModel model) {
		DemoDTO dto = new DemoDTO();
		dto.setName(model.getName());
		return dto;
	}

}
