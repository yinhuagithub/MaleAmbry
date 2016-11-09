package com.graduation.yinhua.maleambry.net;

/**
 * Created by mohyz on 2016/8/27.
 */
public class ApiException extends RuntimeException {

    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;
    public static final int ERROR = 2001;
    public static final int NEVER_LOGGED_IN = 102;


    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    private static String getApiExceptionMessage(int code){
        String message ;
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            case NEVER_LOGGED_IN:
                message = "从未登陆";
            default:
                message = "unknow ERROR";

        }
        return message;
    }

}
