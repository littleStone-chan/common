package com.chen.tools.token;

import com.alibaba.fastjson.JSON;
import com.chen.tools.cache.RedisService;
import com.chen.tools.util.WebUtil;
import com.chen.tools.cache.RedisService;
import com.chen.tools.commons.RespMsg;
import com.chen.tools.commons.enums.StatusEnum;
import com.chen.tools.util.PasswordUtil;
import com.chen.tools.util.StrUtil;
import com.chen.tools.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Component
public class AccessTokenUtil {

    @Autowired
    private RedisService redisServiceTemp;

    private static RedisService redisService;

    //APP登录
    public final static String USER_TOKEN_PREFIX = "SYS-USER-TOKEN";

    //后台登录
    public final static String USER_TOKEN_PREFIX_DIRECT = "SYS-USER-TOKEN-DIRECT";

    private static Logger logger = LoggerFactory.getLogger("appInterceptor");

    @PostConstruct
    public void init() {
        redisService = this.redisServiceTemp;
    }

    /**
     * 用户登录成功后往redis里面存储token
     *
     * @param userId
     * @param di
     * @param phone
     * @return
     */
    public static String putToken(String userId, String di, String phone, String prefixToken) {
        String token = PasswordUtil.encrypt(phone);

        if (StrUtil.isBlank(di)) {
            di = "di";
        }

        if (prefixToken == null) {
            redisService.set(USER_TOKEN_PREFIX + ":" + userId, token);
        } else {
            token = prefixToken + ":" + token;
            redisService.set(prefixToken + ":" + userId, token);
        }

        return token;
    }

    /**
     * 获取redis中的用户token
     *
     * @param accessToken
     * @return
     */
    public static String getToken(AccessToken accessToken, String prefixToken) {

        if (prefixToken == null) {
            String uid = "";
            if (accessToken.getUid() != null && !accessToken.getUid().equals(""))
                uid = accessToken.getUid();
            else if (accessToken.getUi() != null && !accessToken.getUi().equals(""))
                uid = accessToken.getUi();
            return redisService.get(USER_TOKEN_PREFIX + ":" + uid);
        } else {
            return redisService.get(prefixToken + ":" + accessToken.getUid());
        }
    }

    /**
     * 移除redis中token的值
     *
     * @param token
     */
    public static void removeToken(AccessToken token, String prefixToken) {

        if (prefixToken == null) {
            redisService.del(USER_TOKEN_PREFIX + ":" + token.getUid());
        } else {
            redisService.del(prefixToken + ":" + token.getUid());
        }
    }

    /**
     * 更新token存活周期
     *
     * @return
     */
    public static void updateToken(AccessToken accessToken, String prefixToken) {
        if (prefixToken == null) {
            redisService.set(USER_TOKEN_PREFIX + ":" + accessToken.getUid(), accessToken.getToken());
        } else {
            redisService.set(prefixToken + ":" + accessToken.getUid(), accessToken.getToken());
        }
    }

    /**
     * 判断是否是联盟用户
     *
     * @return
     */
    public static boolean isCarLeagueEmployee(HttpServletRequest request) {

        AccessToken accessToken = new AccessToken(request);
        String token = accessToken.getToken();

        if (token != null) {
            return !token.startsWith(USER_TOKEN_PREFIX_DIRECT);
        } else {
            return false;
        }
    }

    /**
     * 用戶信息認證
     * @param uid
     * @param token
     * @return
     */
    public static boolean certification(String uid,String token){

        AccessToken accessToken = new AccessToken(uid,token);

        //redis存储的token
        String redisToken = null;

        //赋值redis 的token
        if (token.startsWith(AccessTokenUtil.USER_TOKEN_PREFIX_DIRECT)) {
            redisToken = AccessTokenUtil.getToken(accessToken, AccessTokenUtil.USER_TOKEN_PREFIX_DIRECT);
        } else {
            redisToken = AccessTokenUtil.getToken(accessToken, null);
        }

        if (accessToken == null
                || (accessToken.getUid() == null && accessToken.getUi() == null)
                || (
                (accessToken.getUid() != null && accessToken.getUid().equals("")) &&
                        (accessToken.getUi() != null && accessToken.getUi().equals("")))
                || StrUtil.isBlank(accessToken.getToken())
                || StrUtil.isBlank(redisToken)
                ) {
            return false;
        } else if (!accessToken.getToken().equals(redisToken)) {
            return false;
        }
        WebUtil.putAccessToken(accessToken);
        return true;
    }
}
