package cn.weirdsky.utils.util.impl;

import cn.weirdsky.entity.entity.LoginUser;
import cn.weirdsky.utils.util.MD5Util;
import cn.weirdsky.utils.util.StringUtil;
import cn.weirdsky.utils.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Calendar;
import java.util.Date;

@Service
public class StringUtilImpl implements StringUtil {

    @Autowired
    private MD5Util md5Util;
    @Autowired
    private TokenUtil tokenUtil;


    public Boolean IsNotEmpty(String string) {
        return !(string == null || string.equals("") || string.trim().length() == 0);
    }

    public Boolean IsNotEmpty(String[] string) {
        return !(string.length == 0);
    }

    @Override
    public String getToken(LoginUser user) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String sessionId = requestAttributes.getSessionId();
        if (IsEmpty(sessionId)) {
            return "";
        }
//        String token = md5Util.encrypt(user.getUserId() + user.getUserCode() + user.getUserName() + user.getUserType() + sessionId);
        String token = tokenUtil.generateJwt(user.getUserId());
        user.setToken(token);
        return token;
    }

    @Override
    public boolean IsEmpty(String string) {
        return (string == null || string.equals("") || string.trim().length() == 0);
    }

    @Override
    public boolean IsEmpty(String[] string) {
        return (string.length == 0);
    }

    @Override
    public String getWeek(Date date) {
        String[] weeks = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }
}
