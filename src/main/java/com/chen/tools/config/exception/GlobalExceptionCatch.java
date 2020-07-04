package com.chen.tools.config.exception;

import com.chen.tools.base.BaseException;
import com.chen.tools.commons.RespMsg;
import com.chen.tools.commons.enums.StatusEnum;
import com.chen.tools.base.BaseException;
import com.chen.tools.base.LoginException;
import com.chen.tools.base.PermissionException;
import com.chen.tools.commons.RespMsg;
import com.chen.tools.commons.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * controller 增强器
 *
 * @author sam
 * @since 2017/7/17
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionCatch {

    /**
     * 运行时异常拦截
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public RespMsg runtimeExceptionHandler(RuntimeException exception) {
        exception.printStackTrace();
        log.error("调用" + Thread.currentThread().getStackTrace()[1].getMethodName() + ",发生业务异常", exception);
        return RespMsg.fail(exception.getMessage());
    }


    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public RespMsg nullPointerExceptionHandler(NullPointerException exception) {
        exception.printStackTrace();
        log.error("调用" + Thread.currentThread().getStackTrace()[1].getMethodName() + ",发生业务异常", exception);
        return RespMsg.fail("服务器发生空指针错误。");
    }

    /**
     * 添加重复索引异常捕获
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = DuplicateKeyException.class)
    public RespMsg duplicateKeyExceptionErrorHandler(DuplicateKeyException ex) {
        ex.printStackTrace();
        return RespMsg.fail(ex.getCause().getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public RespMsg sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        ex.printStackTrace();
        return RespMsg.fail(ex.getCause().getMessage());
    }

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RespMsg errorHandler(Exception ex) {
        ex.printStackTrace();
        return RespMsg.fail(StatusEnum.SERVER_ERROR.msg());
    }


    /**
     * 登录异常
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = LoginException.class)
    public RespMsg loginExceptionHandler(LoginException ex) {
        ex.printStackTrace();
        return new RespMsg(StatusEnum.NEED_LOGIN.status(), ex.getMessage(), null);
    }

    /**
     * 自定义基础异常拦截
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public RespMsg baseExceptionHandler(BaseException exception) {
        log.error("调用" + Thread.currentThread().getStackTrace()[1].getMethodName() + ",发生业务异常", exception);
        return RespMsg.fail(exception.getMsg());
    }


    /**
     * 请求方式不支持异常拦截
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public RespMsg httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException exception) {
        log.error("调用" + Thread.currentThread().getStackTrace()[1].getMethodName() + ",发生业务异常", exception);
        return RespMsg.fail(exception.getMessage());
    }


    /**
     * Controller 接收的数据格式错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public RespMsg httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("数据格式错误，请核对数据格式：" + e.getLocalizedMessage());
        return RespMsg.fail("数据格式错误，请核对数据格式。");
    }

    /**
     * Controller 接收的数据格式错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public RespMsg httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("数据格式错误，请核对数据格式：" + e.getLocalizedMessage());
        return RespMsg.fail("数据格式错误，请核对数据格式。");
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public RespMsg SQLException(SQLException e) {
        e.printStackTrace();
        log.error("数据库错误：" + e.getCause());
        return RespMsg.fail("数据库错误，" + e.getCause());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RespMsg methodArgumentNotValidException(MethodArgumentNotValidException e) {
        if (e.getBindingResult().getAllErrors().size() > 0) {
            log.error("客户端请求数据校验错误:" + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return RespMsg.fail("客户端请求数据校验错误:" + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }
        return RespMsg.fail("客户端请求数据校验错误:末知错误");
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public RespMsg BindException(BindException e) {
        if (e.getBindingResult().getAllErrors().size() > 0) {
            log.error("客户端请求数据校验错误:" + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return RespMsg.fail("客户端请求数据校验错误:" + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }
        return RespMsg.fail("客户端请求数据校验错误:末知错误");
    }

    /**
     * 权限操作拦截
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = PermissionException.class)
    public RespMsg permissionExceptionHandler(PermissionException ex) {
        ex.printStackTrace();
        return new RespMsg(StatusEnum.NO_AUTHORITY.status(), ex.getMessage(), null);
    }
}
