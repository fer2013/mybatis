package org.apache.ibatis.exceptions;

import org.apache.ibatis.executor.ErrorContext;

/**
 * 异常工厂类，用于创建和包装MyBatis相关的异常
 * <p>
 * 该类提供了静态方法来统一处理和包装各种异常为PersistenceException
 * </p>
 *
 * @author heng
 * @date 2025年11月03日
 */
public class ExceptionFactory {
    /**
     * 私有构造方法，防止实例化该工具类
     */
    private ExceptionFactory() {
    }

    /**
     * 将给定的异常包装为RuntimeException(PersistenceException)
     * <p>
     * 使用ErrorContext来构建详细的错误信息，并将原始异常作为原因保存
     * </p>
     *
     * @param message 错误消息
     * @param e       需要被包装的原始异常
     * @return 包装后的RuntimeException(PersistenceException)
     */
    public static RuntimeException wrapException(String message, Exception e) {
        return new PersistenceException(ErrorContext.instance().message(message).cause(e).toString(), e);
    }
}