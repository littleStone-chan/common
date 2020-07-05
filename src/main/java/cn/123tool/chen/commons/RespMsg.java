package com.chen.tools.commons;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.chen.tools.base.BaseException;
import com.chen.tools.commons.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
@Slf4j
@ApiModel
public class RespMsg<T> extends BaseSerializable {
    @ApiModelProperty(value = "状态", position = 1)
    private int status;
    @ApiModelProperty(value = "消息", position = 2)
    private String msg;
    @ApiModelProperty(value = "结果", position = 3)
    private T data;

    public RespMsg() {
    }

    public RespMsg(int status, String msg, T data) {
        this.status = status;
        this.data = data;
        this.msg = msg;

    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, new SerializerFeature[]{SerializerFeature.WriteMapNullValue});
    }

    public static <T> RespMsg<T> success(String msg, T data) {
        return new RespMsg(0, msg, data);
    }

    public static <T> RespMsg<T> fail(String msg) {
        return new RespMsg(1, msg, (Object) null);
    }

    /**
     * 校验响应是否正确
     * @param respMsg
     * @param <T>
     * @return
     */
    public static <T> boolean checkData(RespMsg<T> respMsg){
        if(Objects.nonNull(respMsg)&&Objects.nonNull(respMsg.getData())){
            return true;
        }
        return false;
    }
}
