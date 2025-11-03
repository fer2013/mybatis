package org.apache.ibatis.binding;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 绑定异常类，用于处理MyBatis绑定过程中发生的异常
 * <p>
 * 该类继承自PersistenceException，专门用于处理SQL映射绑定相关的异常情况，
 * 如Mapper接口绑定、参数绑定等操作中出现的错误
 * </p>
 *
 * @author heng
 * @date 2025-11-03 20:00:02
 */
public class BindingException extends PersistenceException {

    private static final long serialVersionUID = -1857959106209464795L;

    /**
     * 默认构造方法，创建一个没有详细信息的绑定异常
     */
    public BindingException() {
        super();
    }

    /**
     * 构造方法，创建一个带有指定详细消息的绑定异常
     *
     * @param message 异常的详细信息
     */
    public BindingException(String message) {
        super(message);
    }

    /**
     * 构造方法，创建一个带有指定详细消息和原因的绑定异常
     *
     * @param message 异常的详细信息
     * @param cause   异常的原因
     */
    public BindingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法，创建一个带有指定原因的绑定异常
     *
     * @param cause 异常的原因
     */
    public BindingException(Throwable cause) {
        super(cause);
    }

}