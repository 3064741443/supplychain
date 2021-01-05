package cn.com.glsx.supplychain.jxc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.ModelMap;
import cn.com.glsx.supplychain.jxc.config.OmsProperty;

/**
 * @Title: IndexController.java
 * @Description:
 * @author deployer name  
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
public class IndexController {
	
	@Autowired
	private OmsProperty omsProperty;
	
	@ResponseBody
	@RequestMapping("home")
	public String home(){
		return "hello, welcome to home!";
	}
	
	@RequestMapping("index")
	public String index(ModelMap map){
		map.addAttribute("cdnPath", omsProperty.getStaticPath());
		return "index";
	}
}
