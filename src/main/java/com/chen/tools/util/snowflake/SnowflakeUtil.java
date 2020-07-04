package com.chen.tools.util.snowflake;


import java.util.Random;

/**
 * 雪花算法生成ID
 * @author yeyc
 */
public class SnowflakeUtil {

    private final static Sequence sequence = new Sequence(new Random().nextInt(3));

    public static String getId() {
        return sequence.nextId() + "";
    }


}
