package org.apache.ibatis.executor;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 执行器异常类，用于处理MyBatis SQL执行过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理SQL执行器相关的异常情况，
 * 如SQL执行失败、结果处理异常、事务处理错误等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:11:20
 */
public class ExecutorException extends PersistenceException {
    private static final long serialVersionUID = -8128652687686423965L;

    /**
     * 默认构造方法，创建一个没有详细信息的执行器异常
     */
    public ExecutorException() {
        super();
    }

    /**
     * 构造方法，创建一个带有指定详细消息的执行器异常
     *
     * @param message 异常的详细信息
     */
    public ExecutorException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的执行器异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public ExecutorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的执行器异常
     *
     * @param cause 异常的原因
     */
    public ExecutorException(Throwable cause) {
        super(cause);
    }

}