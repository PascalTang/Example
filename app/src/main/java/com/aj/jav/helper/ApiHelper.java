package com.aj.jav.helper;

/**
 * Created by pascal on 2018/3/1.
 */

public class ApiHelper {

    /**
     * 帳號是否重新登入
     */
    public static boolean isTokenStatusOk(String message, int code) {
        return isTokenStatusOk(message, String.valueOf(code));
    }

    public static boolean isTokenStatusOk(String message, String code) {
        return !(code.equals("497") || code.equals("498") || code.equals("499"));
    }

    public static boolean isSuccess(int code) {
        return code == 200 || code == 201;
    }

    public static boolean isTokenFail(int code) {
        return (code == 497 || code == 498 || code == 499);
    }

    public static boolean isTokenChange(int code) {
        return code == 496;
    }

    public static boolean isAlreadyVip(String message, int code) {
        return (message.equals("Already vip") && code == 401);
    }
}
