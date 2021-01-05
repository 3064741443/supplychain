package cn.com.glsx.supplychain.controller;

import org.oreframework.boot.autoconfigure.cas.config.CasProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.glsx.supplychain.config.OmsProperty;

/**
 * @Title: IndexController.java
 * @Description: 首页
 * @author zengqi  
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
public class IndexController {
	
	@Autowired
	private OmsProperty omsProperty;
	
	@Autowired
	private CasProperties casProperties;
	
	/**
	 * 登录检测（接入cas生效）
	 * @return
	 * @author Alvin.zengqi  
	 * @date 2018年4月4日 下午1:56:42
	 */
	
	@ResponseBody
	@RequestMapping("checkLogin")
	public String checkLogin(){
		return "success";
	}
	
	@RequestMapping("")
	public String toIndex(){
		return "redirect:index";
	}
	
	@RequestMapping("index")
	public String index(ModelMap map){
		map.addAttribute("cdnPath", omsProperty.getStaticPath());
		map.addAttribute("logoutPath", casProperties.getServerLogoutUrl());
		return "index";
	}

}
