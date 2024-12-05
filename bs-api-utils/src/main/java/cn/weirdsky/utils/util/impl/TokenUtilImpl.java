package cn.weirdsky.utils.util.impl;

import cn.weirdsky.utils.util.TokenUtil;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenUtilImpl implements TokenUtil {

    private static final String SECRET_KEY = "key";// 请使用更安全的密钥

    @Override
    public String generateJwt(String username)  {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact(); // 生成紧凑的JWT字符串
//        return "";
    }

    @Override
    public Claims verifyJwt(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

}
