package com.o2osys.tools.base;

import com.o2osys.tools.commons.enums.StatusEnum;

/**
 * @Author chen
 * @Description
 * @Date 2020/6/29 09:21
 **/
public class PermissionException   extends RuntimeException {


    private static final long serialVersionUID = 1L;

    private String msg ;
    private int code = 403;

    public PermissionException() {
        super(StatusEnum.NO_AUTHORITY.msg());
        this.msg = StatusEnum.NO_AUTHORITY.msg();
    }

    public PermissionException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public PermissionException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public PermissionException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public PermissionException(String msg, int code, Throwable e) {
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
