package com.zbl.springboot.util;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.zbl.springboot.dto.LoginUserDTO;

import java.text.ParseException;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/12/16 11:53
 */
public class TokenUtil {

    private static final String secret = "lsjkdlfjakdjhfgoishkdfhueiqbkbqikkwkjhkahsdwerq";

    public static String generateToken(LoginUserDTO loginUserDTO, String encSecret) throws JOSEException {
        encSecret = (encSecret + secret).substring(0, 32);

        //建立一个密钥
        MACSigner macSigner = new MACSigner(encSecret.getBytes());

        //头部
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        //载荷
        Payload payload = new Payload(JSON.toJSONString(loginUserDTO));

        //头部和载荷相结合
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        //签名
        jwsObject.sign(macSigner);

        //生成token
        return jwsObject.serialize();
    }

    public static LoginUserDTO parseToken(String token, String encSecret) throws JOSEException {
        encSecret = (encSecret + secret).substring(0, 32);

        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(token);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        Payload payload = jwsObject.getPayload();

        //建立一个解锁密钥
        MACVerifier macVerifier = new MACVerifier(encSecret.getBytes());

        if (jwsObject.verify(macVerifier)) {
            return JSON.parseObject(payload.toString(), LoginUserDTO.class);
        }

        return null;
    }
}
