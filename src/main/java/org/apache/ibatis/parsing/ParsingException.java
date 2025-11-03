package org.apache.ibatis.parsing;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 解析异常类，用于处理MyBatis解析过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理SQL映射文件、配置文件等解析相关的异常情况，
 * 如XML解析错误、表达式解析失败等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:16:04
 */
public class ParsingException extends PersistenceException {
    private static final long serialVersionUID = -132079421002787086L;

    /**
     * 默认构造方法，创建一个没有详细信息的解析异常
     */
    public ParsingException() {
        super();
    }

    /**
     * 构造方法，创建一个带有指定详细消息的解析异常
     *
     * @param message 异常的详细信息
     */
    public ParsingException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的解析异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的解析异常
     *
     * @param cause 异常的原因
     */
    public ParsingException(Throwable cause) {
        super(cause);
    }
}