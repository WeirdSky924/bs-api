package cn.weirdsky.common.util.impl;

import cn.weirdsky.common.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

@Service
public class MD5UtilImpl implements MD5Util {
    private final String slat = "&%5123***&&%%$$#@";

    public String encrypt(String dataStr) {
        try {
            dataStr = dataStr + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getMD5(String str) {
        String base = str + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
