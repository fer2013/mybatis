package org.apache.ibatis.logging;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 日志异常类，用于处理MyBatis日志记录过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理日志系统相关的异常情况，
 * 如日志配置错误、日志输出失败等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:14:08
 */
public class LogException extends PersistenceException {
    private static final long serialVersionUID = -6843024797443799509L;

    /**
     * 默认构造方法，创建一个没有详细信息的日志异常
     */
    public LogException() {
        super();
    }

    /**
     * 构造方法，创建一个带有指定详细消息的日志异常
     *
     * @param message 异常的详细信息
     */
    public LogException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的日志异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public LogException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的日志异常
     *
     * @param cause 异常的原因
     */
    public LogException(Throwable cause) {
        super(cause);
    }
}