package com.chen.tools.commons.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * web socket请求的状态枚举类
 */
public enum WSStatusEnum {
	SUCCESS(0, "操作成功"),
	FAIL(1, "操作失败"),
	REGISTER(100,"注册"),
    UNREGISTER(110,"注销"),
	HEARTBEAT(10000,"心跳");

	private int status;
	private String msg;

	private WSStatusEnum(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return this.status + ":" + this.msg;
	}

	public String msg() {
		return this.msg;
	}
	
	private static Map<Integer,WSStatusEnum> lookup = new HashMap();
	static {
		Iterator<WSStatusEnum> iterator = EnumSet.allOf(WSStatusEnum.class).iterator();
		while(iterator.hasNext()){
			WSStatusEnum wsStatusEnum = iterator.next();
			lookup.put(wsStatusEnum.status,wsStatusEnum);
		}
	}
	public int status() {
		return this.status;
	}

	public static WSStatusEnum get(int status){
		return lookup.get(status);
	}
}
