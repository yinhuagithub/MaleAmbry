package com.graduation.yinhua.maleambry.model;

/**
 * StatusCode.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/27.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public enum StatusCode {
    SUCCESS(1000), FAILURE(1001);

    private int status_code;
    private StatusCode(int status_code){
        this.status_code = status_code;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
}
