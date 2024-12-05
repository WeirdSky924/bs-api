package cn.weirdsky.common.interceptor;

import cn.weirdsky.common.entity.Code;
import cn.weirdsky.common.entity.LoginUser;
import cn.weirdsky.common.entity.R;
import cn.weirdsky.common.exception.MySessionException;
import cn.weirdsky.common.util.LoginUtil;
import cn.weirdsky.common.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.PrintWriter;
import java.util.Map;

@Component
public class CommonInterceptor implements HandlerInterceptor {

    private static Logger logger= LoggerFactory.getLogger(CommonInterceptor.class);

    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private LoginUtil loginUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HttpSession session = request.getSession();
        LoginUser user = (LoginUser) session.getAttribute("user");
        String token = request.getHeader("X-Token");
        Map<String, String> attr = (Map<String, String>) session.getAttribute("attr");
        if (user == null || stringUtil.IsEmpty(token) || !(stringUtil.IsNotEmpty(token) && token.equals(attr.getOrDefault("token", "")))){
            logger.error("session error, please login");
            try{
                session.invalidate();
                int i = 1 / 0;
            }catch (Exception e){
                e.printStackTrace();
                throw new MySessionException("session error, please login", Code.BUSINESS_ERR);
            }
            return false;
        } else {
            loginUtil.setLoginUser(user);
            logger.info("===>loginUser:" + user.getToken());
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
