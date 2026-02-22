package com.xd.xdclasslearnbackend.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 康志阳
 * @date 2026/2/20 16:58
 */
public class Md5Util {
    private static final String FIXED_SALT = "xdlearn2025";

    public  static String md5WithFixedSalt(String input){
        return DigestUtils.md5Hex(input+FIXED_SALT);
    }

    public static boolean verifyPassword(String input,String storedEncryptedPassword){
        return md5WithFixedSalt(input).equals(storedEncryptedPassword);
    }
}
