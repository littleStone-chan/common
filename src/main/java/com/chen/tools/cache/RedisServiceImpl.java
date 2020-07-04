package com.chen.tools.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author chao.zheng
 * @ClassName: RedisServiceImpl
 * @Description: redis缓存服务
 * @date 2016年11月8日 下午3:58:00
 */
@Service
public class RedisServiceImpl implements RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    private static String redisCode = "utf-8";

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    /**
     * @param keys
     */
    public long del(final String... keys) {
        return stringRedisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }
        });
    }

    /**
     * 通配符删除
     *
     * @param prex
     * @return
     */
    public Long delByPrex(final String prex) {
        Set<String> keys = stringRedisTemplate.keys(prex);
        if (!ObjectUtils.isEmpty(keys)) {
            return stringRedisTemplate.delete(keys);
        }
        return 0L;
    }

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        stringRedisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key, String value, long liveTime) {
        try {
            this.set(key.getBytes(redisCode), value.getBytes(redisCode), liveTime);
        } catch (UnsupportedEncodingException e) {
            logger.error("insert into  redis change UTF-8 has Exception", e);
        }
    }

    /**
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

    /**
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

    /**
     * @param key
     * @return
     */
    public String get(final String key) {
        return stringRedisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    byte[] bytes = connection.get(key.getBytes());
                    if (bytes == null) {
                        return null;
                    } else {
                        return new String(bytes, redisCode);
                    }

                } catch (Exception e) {
                }
                return "";
            }
        });
    }

    /**
     * @param pattern
     * @return
     */
    public Set<String> Setkeys(String pattern) {
        return stringRedisTemplate.keys(pattern);

    }

    /**
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    /**
     * @return
     */
    public String flushDB() {
        return stringRedisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * @return
     */
    public long dbSize() {
        return stringRedisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * @return
     */
    public String ping() {
        return stringRedisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }

    @Override
    public Integer incRedisKey(String key) {
        stringRedisTemplate.execute(new RedisCallback<Integer>() {
            public Integer doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.incr(key.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error("setSortSet has error", e);
                }
                return 1;
            }
        });
        return 1;
    }

    /**
     * 往set中添加一个键值对
     *
     * @param key
     * @param value
     */
    public Integer setSortSet(String key, String value) {
        stringRedisTemplate.execute(new RedisCallback<Integer>() {
            public Integer doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    // 往set中添加元素，已存在的追加 不存在的新建
                    connection.zIncrBy(key.getBytes("UTF-8"), 1, value.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error("setSortSet has error", e);
                }
                return 1;
            }
        });
        return 1;
    }


    /**
     * 查询前十个最热门的所有车辆
     *
     * @param key
     * @return
     */
    public LinkedHashSet<String> getSortSet(final String key) {
        return stringRedisTemplate.execute(new RedisCallback<LinkedHashSet<String>>() {
            public LinkedHashSet<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    // 降序查询set中的前十个热门搜索的车辆
                    Set<byte[]> bytesSet = connection.zRevRange(key.getBytes(), 0L, 9L);
                    if (bytesSet == null) {
                        return null;
                    } else {
                        LinkedHashSet<String> set = new LinkedHashSet<String>();
                        for (byte[] bt : bytesSet) {
                            set.add(new String(bt, redisCode));
                        }
                        return set;
                    }
                } catch (UnsupportedEncodingException e) {
                    logger.error("getSortSet has error", e);
                }
                return null;
            }
        });
    }


}
