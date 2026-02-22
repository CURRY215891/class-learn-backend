package com.xd.xdclasslearnbackend.util;

import com.xd.xdclasslearnbackend.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 康志阳
 * @date 2026/2/20 15:58
 */
@Component
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;

    private static SecretKey secretKey;

    static {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    /*生成token*/

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtConfig.getExpiration()*1000))
                .signWith(secretKey)
                .compact();
    }

    /*刷新token*/

    public String generateRefreshToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+7*24*60*60*1000))
                .signWith(secretKey)
                .compact();
    }


    /*解析*/

    public Claims getClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    /*获取过期时间*/

    public Date extractExpiration(String token){
        return getClaimsFromToken(token).getExpiration();
    }

    /*验证token是否过期*/

    public boolean isTokenExpired(String token){
       final Date expiration = extractExpiration(token);
       return expiration.before(new Date());
    }

    /*验证token是否非法*/
    public Boolean validateToken(String token){
        try {
            return !isTokenExpired(token);
        }catch (Exception e){
            return false;
        }
    }

    /*从token提取用户名*/

    public String extractUsername(String token){
        return getClaimsFromToken(token).getSubject();
    }

    public String getUsernameFromToken(String token){
        return extractUsername(token);
    }
}
