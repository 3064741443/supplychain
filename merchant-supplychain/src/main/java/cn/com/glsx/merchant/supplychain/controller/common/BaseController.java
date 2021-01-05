package cn.com.glsx.merchant.supplychain.controller.common;



import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Created by IntelliJ IDEA.<br/>
 * User: liuyf<br/>
 * Date: 2017/9/18<br/>
 * Time: 15:31<br/>
 */
@Controller
public class BaseController {

  //  @Autowired
  //  protected HttpSession session;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;


    /**
     * 获取当前登录用户id
     *
     * @return
     */
    public Long getLoginUserId() {
    	Session session = ThreadLocalCache.getSession();
    	Object obj = (Object)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
       // Object obj = session.getAttribute(Constants.SESSION_LOGIN_USER);
        if (obj != null) {
            return ((DealerUserInfo) obj).getId();
        }
        return null;
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public DealerUserInfo getCurrentLoginUser() {
    	Session session = ThreadLocalCache.getSession();
    	Object obj = (Object)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
       // Object obj = session.getAttribute(Constants.SESSION_LOGIN_USER);
        if (obj != null) {
            return (DealerUserInfo) obj;
        }
        return null;
    }

}
