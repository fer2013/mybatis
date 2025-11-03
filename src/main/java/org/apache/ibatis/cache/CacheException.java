package org.apache.ibatis.cache;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 缓存异常类，用于处理MyBatis缓存操作过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理缓存相关的异常情况，
 * 如缓存读取失败、缓存写入失败、缓存清理异常等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:05:27
 */
public class CacheException extends PersistenceException {
    private static final long serialVersionUID = -708629638833637183L;

    /**
     * 默认构造方法，创建一个没有详细信息的缓存异常
     */
    public CacheException() {
        super();
    }

    /**
     * 构造方法，创建一个带有指定详细消息的缓存异常
     *
     * @param message 异常的详细信息
     */
    public CacheException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的缓存异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的缓存异常
     *
     * @param cause 异常的原因
     */
    public CacheException(Throwable cause) {
        super(cause);
    }
}