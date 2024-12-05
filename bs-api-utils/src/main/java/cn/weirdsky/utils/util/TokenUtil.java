package cn.weirdsky.utils.util;

import io.jsonwebtoken.Claims;

public interface TokenUtil {

    public String generateJwt(String username);

    public Claims verifyJwt(String token);

}
