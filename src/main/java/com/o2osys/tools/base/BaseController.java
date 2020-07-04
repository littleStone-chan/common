package com.o2osys.tools.base;

import com.o2osys.tools.commons.RespMsg;
import com.o2osys.tools.commons.enums.StatusEnum;

/**
 * 基础的控制层
 *
 * @author chen
 * @create 2019-05-13 14:38
 **/
public abstract class BaseController {

    public RespMsg success(){
        return RespMsg.success(StatusEnum.SUCCESS.msg(),null);
    }

    public RespMsg success(Object data){
        return RespMsg.success(StatusEnum.SUCCESS.msg(),data);
    }

    public RespMsg success(String msg){
        return RespMsg.success(msg,null);
    }

    public RespMsg success(String msg, Object data){
        return RespMsg.success(msg,data);
    }

    public RespMsg fail(){
        return RespMsg.fail(StatusEnum.FAIL.msg());
    }

    public RespMsg fail(String msg){
        return RespMsg.fail(msg);
    }
}
