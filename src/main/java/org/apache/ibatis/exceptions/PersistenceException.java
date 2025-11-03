package org.apache.ibatis.exceptions;

/**
 * 持久化异常类，用于封装MyBatis持久化操作过程中发生的异常
 * <p>
 * 该类继承自IbatisException，提供了多种构造方法以适应不同的异常处理场景
 * </p>
 *
 * @author heng
 * @date 2025-11-03 10:16:04
 */
public class PersistenceException extends IbatisException {

    private static final long serialVersionUID = -8134130541706323542L;

    /**
     * 默认构造方法，创建一个没有详细信息的持久化异常
     */
    public PersistenceException() {
    }

    /**
     * 构造方法，创建一个带有指定详细消息的持久化异常
     *
     * @param message 异常的详细信息
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的持久化异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的持久化异常
     *
     * @param cause 异常的原因
     */
    public PersistenceException(Throwable cause) {
        super(cause);
    }

}