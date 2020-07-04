package com.o2osys.tools.commons.enums;

/**
 * 请求返回状态枚举类
 */
public enum StatusEnum {
	SUCCESS(0, "操作成功"),
	FAIL(1, "操作失败"),
	ANALYZE(2, "解析参数"),
	BAD_REQUEST(400, "参数异常"),
	PAGE_NOT_FOUND(404, "页面不存在"),
	NO_AUTHORITY(403, "权限不足"),
	SERVER_ERROR(500, "服务器开小差"),
	NEED_LOGIN(600, "请先登录"),
	DISABLED_ACCOUNT(800, "无效帐号"),
	NEED_LOGIN_AGAIN(600, "您的帐号已在其它移动设备登录，如果不是您本人操作，请尽快修改密码。"),
	ACCOUNT_CHANGED(600, "您的账号信息已修改，请重新登录。"),
	ACCOUNT_INVALID(600, "您的账号当前不允许登陆。"),
	RATE_LIMIT(900,"当前访问人数过多，请稍后再试");

	private int status;
	private String msg;

	private StatusEnum(int status, String msg) {
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
	
	public int status() {
		return this.status;
	}
}
