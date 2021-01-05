package cn.com.glsx.supplychain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.ModelMap;
import cn.com.glsx.supplychain.config.SystemProperty;

/**
 * @Title: IndexController.java
 * @Description:
 * @author lyj  
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
@RequestMapping("index")
public class IndexController {
	
	@Autowired
	private SystemProperty systemProperty;
	
	@ResponseBody
	@RequestMapping("home")
	public String home(){
		return "hello, welcome to home!";
	}
	
	@RequestMapping("index")
	@ResponseBody 
	public String index(ModelMap map){
		map.addAttribute("name", systemProperty.getName());
		return "index";
	}
}
