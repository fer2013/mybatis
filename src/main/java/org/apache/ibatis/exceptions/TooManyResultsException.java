package org.apache.ibatis.exceptions;

/**
 * 结果过多异常类，当查询返回的结果数量超过预期时抛出
 * <p>
 * 该类继承自PersistenceException，专门用于处理查询结果数量超出预期的情况
 * </p>
 */
public class TooManyResultsException extends PersistenceException{
    private static final long serialVersionUID = -6659262067256031637L;

    /**
     * 默认构造方法，创建一个没有详细信息的结果过多异常
     */
    public TooManyResultsException() {
        super();
    }

    /**
     * 构造方法，创建一个带有指定详细消息的结果过多异常
     *
     * @param message 异常的详细信息
     */
    public TooManyResultsException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的结果过多异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public TooManyResultsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的结果过多异常
     *
     * @param cause 异常的原因
     */
    public TooManyResultsException(Throwable cause) {
        super(cause);
    }
}