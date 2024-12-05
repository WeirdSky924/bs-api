package cn.weirdsky.department.interceptor;

import cn.weirdsky.entity.entity.LoginUser;
import cn.weirdsky.entity.entity.SysLog;
import cn.weirdsky.mapper.mapper.SysLogMapper;
import cn.weirdsky.utils.util.LoginUtil;
import cn.weirdsky.utils.util.StringUtil;
import cn.weirdsky.utils.util.TableUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class SysLogInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(SysLogInterceptor.class);

    @Autowired
    private SysLogMapper sysLogMapper;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private TableUtil tableUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //支持请求方式
        response.setHeader("Access-Control-Allow-Methods", "*");
        //是否支持cookie跨域
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");
        logger.info(request.getMethod() + "====" + request.getSession().getId());
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        SysLog sysLog = new SysLog();
        sysLog.setSysLogId(tableUtil.getTableId("sys_log"));
        sysLog.setDeleteMark("0");
        sysLog.setMethodName(method.getDeclaringClass() + " " + method.getName());
        sysLog.setTime(new Date());
        LoginUser loginUser = loginUtil.getLoginUser();
        if ("OPTIONS".equals(request.getMethod())){
            return false;
        }
        if (loginUser == null) {
            sysLog.setUserId("");
        } else {
            sysLog.setUserId(stringUtil.IsEmpty(loginUser.getUserId()) ? "" : loginUser.getUserId());
        }
        sysLog.setInfo(method.getReturnType().toString());
        sysLog.setMethodParams(Arrays.stream(method.getParameterTypes()).map(s -> s.getName()).collect(Collectors.toList()).toString());
        if (sysLogMapper.insert(sysLog) != 1) {
            logger.error("错误的日志写入！");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
