package org.apache.ibatis.session;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * SQL会话异常类，用于处理MyBatis SQL会话操作过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理SQL会话相关的异常情况，
 * 如会话创建失败、会话操作错误、事务处理异常等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:25:33
 */
public class SqlSessionException extends PersistenceException {
    private static final long serialVersionUID = -1856680787283263187L;

    /**
     * 默认构造方法，创建一个没有详细信息的SQL会话异常
     */
    public SqlSessionException() {
    }

    /**
     * 构造方法，创建一个带有指定详细消息的SQL会话异常
     *
     * @param message 异常的详细信息
     */
    public SqlSessionException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的SQL会话异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public SqlSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的SQL会话异常
     *
     * @param cause 异常的原因
     */
    public SqlSessionException(Throwable cause) {
        super(cause);
    }
}