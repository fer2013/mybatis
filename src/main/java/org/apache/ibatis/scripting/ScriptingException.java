package org.apache.ibatis.scripting;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 脚本异常类，用于处理MyBatis脚本处理过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理脚本引擎相关的异常情况，
 * 如SQL脚本解析错误、动态SQL处理失败等操作中出现的错误
 * </p>
 * @author heng
 * @date 2025-11-03 20:23:37
 */
public class ScriptingException extends PersistenceException {
    private static final long serialVersionUID = -4961320790065046224L;

    /**
     * 默认构造方法，创建一个没有详细信息的脚本异常
     */
    public ScriptingException() {
        super();
    }

    /**
     * 构造方法，创建一个带有指定详细消息的脚本异常
     *
     * @param message 异常的详细信息
     */
    public ScriptingException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的脚本异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public ScriptingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的脚本异常
     *
     * @param cause 异常的原因
     */
    public ScriptingException(Throwable cause) {
        super(cause);
    }
}