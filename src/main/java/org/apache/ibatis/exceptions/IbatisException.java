package org.apache.ibatis.exceptions;

/**
 * Ibatis异常类，继承自RuntimeException
 * <p>
 * 注意：此类已被标记为@Deprecated，建议使用更新的异常处理机制
 *
 * @deprecated 请使用MyBatis提供的新异常处理类
 */
@Deprecated
public class IbatisException extends RuntimeException {

    private static final long serialVersionUID = -8127840816241509579L;

    /**
     * 构造一个IbatisException实例
     */
    public IbatisException() {

    }

    /**
     * 构造一个带有指定详细消息的IbatisException实例
     *
     * @param message 详细消息
     */
    public IbatisException(String message) {
        super(message);
    }

    /**
     * 构造一个带有指定详细消息和原因的IbatisException实例
     *
     * @param message 详细消息
     * @param cause   异常原因
     */
    public IbatisException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造一个带有指定原因的IbatisException实例
     *
     * @param cause 异常原因
     */
    public IbatisException(Throwable cause) {
        super(cause);
    }

}