package cn.com.glsx.merchant.supplychain.controller;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.config.OmsProperty;
import cn.com.glsx.merchant.supplychain.config.StaticProperty;
import cn.com.glsx.merchant.supplychain.framework.common.ResultCodeConstants;
import cn.com.glsx.merchant.supplychain.framework.utils.ErrorMsgUtils;
import cn.com.glsx.merchant.supplychain.util.CreateMD5;
import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
import cn.com.glsx.supplychain.remote.bs.DealerUserInfoAdminRemote;

import org.oreframework.boot.autoconfigure.cas.config.CasProperties;
import org.oreframework.web.ui.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author zengqi
 * @version V1.0
 * @Title: IndexController.java
 * @Description: 首页
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpSession session;

    @Autowired
    private OmsProperty omsProperty;

    @Autowired
    private CasProperties casProperties;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    /**
     * 登录检测（接入cas生效）
     *
     * @return
     * @author Alvin.zengqi
     * @date 2018年4月4日 下午1:56:42
     */
    @Autowired
    private StaticProperty staticProperty;

    @ResponseBody
    @RequestMapping("checkLogin")
    public String checkLogin() {
        return "success";
    }

    @RequestMapping("")
    public String toIndex() {
        return "redirect:/login";
    }

    @RequestMapping("index")
    public String index(ModelMap map) {
        map.addAttribute("cdnPath", omsProperty.getStaticPath());
        map.addAttribute("logoutPath", casProperties.getServerLogoutUrl());
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo user = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
     //   DealerUserInfo user = (DealerUserInfo)session.getAttribute(Constants.SESSION_LOGIN_USER);
        map.addAttribute("user",user);
        return "index";
    }

    @GetMapping("login")
    public String login(ModelMap map) {
        map.addAttribute("cdnPath", omsProperty.getStaticPath());
        map.addAttribute("logoutPath", casProperties.getServerLogoutUrl());
        return "login";
    }

    @RequestMapping(value = "/loginUser")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "loginUser";
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/loginPost")
    @ResponseBody
    public ResultEntity<DealerUserInfo> loginPost(String username, String password) {
        ResultEntity<DealerUserInfo> resultEntity = new ResultEntity<>();
        RpcResponse<DealerUserInfo> dealerUserInfo = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(username);
        if (dealerUserInfo.getResult() == null) {
            ErrorMsgUtils.createErrorMsg(resultEntity, ResultCodeConstants.USERNAME_NOT_EXIST);
        } else if (password != null && !CreateMD5.md5(password).equals(dealerUserInfo.getResult().getPassword())) {
            ErrorMsgUtils.createErrorMsg(resultEntity, ResultCodeConstants.PASSWORD_ERROR);
        } else {
            resultEntity.setData(dealerUserInfo.getResult());
            Session session = ThreadLocalCache.getSession();
    		session.put(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue(), dealerUserInfo.getResult());
            logger.info("当前登入的用户“:{}", dealerUserInfo.getResult());
           // session.setAttribute(Constants.SESSION_LOGIN_USER, dealerUserInfo.getResult());
        }
        return resultEntity;
    }
}
