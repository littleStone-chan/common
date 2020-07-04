package com.o2osys.tools.enums;

import com.o2osys.tools.base.BaseException;
import com.o2osys.tools.token.AccessTokenUtil;
import com.o2osys.tools.util.WebUtil;

/**
 * 登录来源
 */
public enum LoginSourceEnum {

    WEB("WEB","从WEB端登录"),
    APP("APP","从APP端登录");

    String key;
    String msg;

    LoginSourceEnum(String key,String msg){
        this.key = key;
        this.msg = msg;
    }

    //获取当前登录来源
    public static LoginSourceEnum get(){

        String token = WebUtil.getAccessToken().getToken();
        if (token.startsWith(AccessTokenUtil.USER_TOKEN_PREFIX_DIRECT))
            return WEB;

        if (token.startsWith(AccessTokenUtil.USER_TOKEN_PREFIX))
            return APP;

        throw new BaseException("token错误");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
