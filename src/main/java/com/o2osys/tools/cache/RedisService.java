package com.o2osys.tools.cache;

import java.util.Set;

/**
 * @author chao.zheng
 * @ClassName: RedisService
 * @Description: redis缓存服务
 * @date 2016年11月8日 下午3:57:24
 */
public interface RedisService {

    /**
     * 通过key删除
     *
     * @param keys
     */
    long del(String... keys);

    Long delByPrex(final String prex);

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     */
    void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime 单位秒
     */
    void set(String key, String value, long liveTime);

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     */
    void set(byte[] key, byte[] value);

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     */
    Set<String> Setkeys(String pattern);

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    String flushDB();

    /**
     * 查看redis里有多少数据
     */
    long dbSize();

    /**
     * 检查是否连接成功
     *
     * @return
     */
    String ping();

    /**
     * 基于redis自增
     */
    Integer incRedisKey(String key);

    /**
     * 往set中添加一个键值对
     *
     * @param key
     * @param value
     */
    Integer setSortSet(String key, String value);


}
