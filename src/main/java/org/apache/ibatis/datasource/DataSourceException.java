package org.apache.ibatis.datasource;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 数据源异常类，用于处理MyBatis数据源操作过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理数据源连接、配置等相关异常情况，
 * 如数据库连接失败、数据源配置错误等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:07:36
 */
public class DataSourceException extends PersistenceException {
    private static final long serialVersionUID = -6840944203915393993L;

    /**
     * 默认构造方法，创建一个没有详细信息的数据源异常
     */
    public DataSourceException() {
        super();
    }

    /**
     * 构造方法，创建一个带有指定详细消息的数据源异常
     *
     * @param message 异常的详细信息
     */
    public DataSourceException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的数据源异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的数据源异常
     *
     * @param cause 异常的原因
     */
    public DataSourceException(Throwable cause) {
        super(cause);
    }
}