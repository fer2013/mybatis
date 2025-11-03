package org.apache.ibatis.plugin;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 插件异常类，用于处理MyBatis插件操作过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理插件系统相关的异常情况，
 * 如插件初始化失败、插件应用错误等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:18:01
 */
public class PluginException extends PersistenceException {
    private static final long serialVersionUID = -2212268410512043556L;

    /**
     * 默认构造方法，创建一个没有详细信息的插件异常
     */
    public PluginException() {
    }

    /**
     * 构造方法，创建一个带有指定详细消息的插件异常
     *
     * @param msg 异常的详细信息
     */
    public PluginException(String msg) {
        super(msg);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的插件异常
     *
     * @param msg   异常的详细信息
     * @param cause 异常的原因
     */
    public PluginException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的插件异常
     *
     * @param cause 异常的原因
     */
    public PluginException(Throwable cause) {
        super(cause);
    }
}