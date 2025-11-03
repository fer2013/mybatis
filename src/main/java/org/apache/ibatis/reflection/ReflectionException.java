package org.apache.ibatis.reflection;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 反射异常类，用于处理MyBatis反射操作过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理反射相关的异常情况，
 * 如属性访问失败、方法调用错误、对象实例化异常等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:21:04
 */
public class ReflectionException extends PersistenceException {
    private static final long serialVersionUID = -641863039569057812L;

    /**
     * 默认构造方法，创建一个没有详细信息的反射异常
     */
    public ReflectionException() {
    }

    /**
     * 构造方法，创建一个带有指定详细消息的反射异常
     *
     * @param msg 异常的详细信息
     */
    public ReflectionException(String msg) {
        super(msg);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的反射异常
     *
     * @param msg   异常的详细信息
     * @param cause 异常的原因
     */
    public ReflectionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的反射异常
     *
     * @param cause 异常的原因
     */
    public ReflectionException(Throwable cause) {
        super(cause);
    }
}