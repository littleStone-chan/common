package com.o2osys.tools.token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.o2osys.tools.commons.StringUtils;
import com.o2osys.tools.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局Token对象，用户获取从Header传递过来的全局参数
 */
public class AccessToken {
    /**
     * 用户ID
     */
    private String uid;


    /**
     * 车联盟用户id
     */
    private String ui;

    /**
     * 设备ID
     */
    private String did;
    /**
     * 登录成功后生成的token对象
     */
    private String token;

    /**
     * 门店ID
     */
    private Long storeId = 0L;

    /**
     * 部门id
     */
    private String departmentId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getUid() {
        if (uid != null && !"".equals(uid))
            return uid;
        else if (ui != null && !"".equals(ui))
            return ui;
        else
            return "";
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public AccessToken(String uid,String token){
        this.uid = uid;
        this.ui = uid;
        this.token = token;
    }

    public AccessToken(HttpServletRequest request) {
        if (request != null) {
            this.ui = request.getHeader("X-ui");
            if (StringUtils.isBlank(this.ui))
                this.ui = request.getHeader("x-ui");
            this.uid = request.getHeader("X-uid");
            if (StringUtils.isBlank(this.uid))
                this.uid = request.getHeader("x-uid");
            this.did = StrUtil.fixNull(request.getHeader("X-did"), "did");
            this.token = request.getHeader("X-token");
            if (StringUtils.isBlank(this.token))
                this.token = request.getHeader("x-token");

            if(StringUtils.isNotBlank(request.getHeader("X-store"))) {
                this.storeId = Long.parseLong( request.getHeader("x-store"));
            }

            if(StringUtils.isNotBlank(request.getHeader("X-departmentId"))) {
                this.departmentId = request.getHeader("X-departmentId");
            }
        }
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public AccessToken(String uid, String did, String token) {
        this.uid = uid;
        this.did = did;
        this.token = token;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }

}
