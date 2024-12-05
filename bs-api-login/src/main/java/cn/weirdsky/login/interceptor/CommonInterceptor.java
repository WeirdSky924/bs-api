package cn.weirdsky.login.interceptor;

import cn.weirdsky.entity.entity.Code;
import cn.weirdsky.entity.entity.LoginUser;
import cn.weirdsky.login.exception.MySessionException;
import cn.weirdsky.utils.util.LoginUtil;
import cn.weirdsky.utils.util.StringUtil;
import cn.weirdsky.utils.util.TokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Component
public class CommonInterceptor implements HandlerInterceptor {

    private static Logger logger= LoggerFactory.getLogger(CommonInterceptor.class);

    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private TokenUtil tokenUtil;

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
            try {
                Claims claims = tokenUtil.verifyJwt(token);
                if (claims == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }

                // 在Spring Security上下文中设置认证信息
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, null); // 如果有权限信息，可以在这里传递
                SecurityContextHolder.getContext().setAuthentication(authentication);

                logger.info("===>claims:" + claims.getSubject());
            }catch (Exception e)
            {
                logger.error("token error");
                return false;
            }
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
