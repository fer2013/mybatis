package org.apache.ibatis.type;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 类型异常类，用于处理MyBatis类型转换过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理类型处理器相关的异常情况，
 * 如Java类型与数据库类型转换错误、类型处理器配置失败等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:27:04
 */
public class TypeException extends PersistenceException {
    private static final long serialVersionUID = -4089514294020818730L;

    /**
     * 默认构造方法，创建一个没有详细信息的类型异常
     */
    public TypeException() {
    }

    /**
     * 构造方法，创建一个带有指定详细消息的类型异常
     *
     * @param message 异常的详细信息
     */
    public TypeException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的类型异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的类型异常
     *
     * @param cause 异常的原因
     */
    public TypeException(Throwable cause) {
        super(cause);
    }
}