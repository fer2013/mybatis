package org.apache.ibatis.builder;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 构建器异常类，用于处理MyBatis构建过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理SQL映射配置解析、
 * XML配置文件解析等构建阶段的异常情况
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:03:17
 */
public class BuilderException extends PersistenceException {
    private static final long serialVersionUID = -6454355096277548352L;

    /**
     * 默认构造方法，创建一个没有详细信息的构建器异常
     */
    public BuilderException() {
    }

    /**
     * 构造方法，创建一个带有指定详细消息的构建器异常
     *
     * @param msg 异常的详细信息
     */
    public BuilderException(String msg) {
        super(msg);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的构建器异常
     *
     * @param msg   异常的详细信息
     * @param cause 异常的原因
     */
    public BuilderException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的构建器异常
     *
     * @param cause 异常的原因
     */
    public BuilderException(Throwable cause) {
        super(cause);
    }
}