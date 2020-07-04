package com.o2osys.tools.base;

/**
 * 登录异常
 *
 * @author chen
 * @create 2019-07-11 14:46
 **/
public class LoginException  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public LoginException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public LoginException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public LoginException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public LoginException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
