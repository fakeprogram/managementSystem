package com.example.managementSystem.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/* *
 * @Author lsc
 * <p> JWT工具类 </p>
 * @Param
 * @Return
 */
public class JWTUtil {

    // Token过期时间3h
    public static final long EXPIRE_TIME = 180 * 60 * 1000;

    //密钥
    private static final String SING = "managementSystem";

    /* *
     * @Author lsc
     * <p>生成签名,3h后过期 </p>
     * @Param [user_id, secret]
     * @Return java.lang.String
     */
    public static String getToken(Integer user_id, String pass_word) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        builder.withClaim("user_id",user_id)
                .withClaim("password",pass_word);
        String token = builder.withExpiresAt(date)//有效期
                .sign(Algorithm.HMAC256(SING));//密钥
        return token;
    }

    /* *
     * @Author lsc
     * <p> 获得用ID </p>
     * @Param [request]
     * @Return java.lang.String
     */
    public static Integer getUserIdByToken(HttpServletRequest request)  {
        String token = request.getHeader("token");
        DecodedJWT jwt = JWT.decode(token);
        Integer id = jwt.getClaim("user_id")
                .asInt();
        return id;
    }

    /**
     * 验证token合法性
     */
    public static DecodedJWT verify(String token){
        //返回验证结果（结果是内置的）
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    /* *
     * @Author lsc
     * <p> 校验token是否正确 </p>
     * @Param token
     * @Param username
     * @Param secret
     * @Return boolean
     */
    /*public static boolean verify(String token, Integer user_id, String secret) {
        try {
            // 设置加密算法
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("user_id", user_id)
                    .build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
*/

}