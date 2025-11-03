package org.apache.ibatis.transaction;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 事务异常类，用于处理MyBatis事务操作过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理事务相关的异常情况，
 * 如事务开启失败、事务提交错误、事务回滚异常等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:29:04
 */
public class TransactionException extends PersistenceException {
    private static final long serialVersionUID = -1761058646896269824L;

    /**
     * 默认构造方法，创建一个没有详细信息的事务异常
     */
    public TransactionException() {
    }

    /**
     * 构造方法，创建一个带有指定详细消息的事务异常
     *
     * @param message 异常的详细信息
     */
    public TransactionException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的事务异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的事务异常
     *
     * @param cause 异常的原因
     */
    public TransactionException(Throwable cause) {
        super(cause);
    }
}