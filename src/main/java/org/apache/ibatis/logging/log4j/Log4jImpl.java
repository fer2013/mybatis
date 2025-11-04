package org.apache.ibatis.logging.log4j;

import org.apache.ibatis.logging.Log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * Log4j日志实现类，用于适配MyBatis的日志接口到Log4j
 * <p>
 * 该类实现了MyBatis的Log接口，将日志操作委托给Log4j的Logger处理，
 * 提供了完整的日志级别支持，包括ERROR、WARN、DEBUG、TRACE等级别
 * </p>
 *
 * @author heng
 * @date 2025-11-04 16:18:54
 */
public class Log4jImpl implements Log {
    private static final String FQCN = Log4jImpl.class.getName();

    private final Logger log;

    /**
     * 构造方法，根据指定的类名创建Log4jImpl实例
     *
     * @param clazz 需要记录日志的类名
     */
    public Log4jImpl(String clazz) {
        log = Logger.getLogger(clazz);
    }

    /**
     * 检查是否启用了DEBUG级别日志
     *
     * @return 如果启用了DEBUG级别日志则返回true，否则返回false
     */
    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    /**
     * 检查是否启用了TRACE级别日志
     *
     * @return 如果启用了TRACE级别日志则返回true，否则返回false
     */
    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    /**
     * 记录ERROR级别日志，并包含异常信息
     *
     * @param s 错误消息
     * @param e 异常对象
     */
    @Override
    public void error(String s, Throwable e) {
        log.log(FQCN, Level.ERROR, s, e);
    }

    /**
     * 记录ERROR级别日志
     *
     * @param s 错误消息
     */
    @Override
    public void error(String s) {
        log.log(FQCN, Level.ERROR, s, null);
    }

    /**
     * 记录DEBUG级别日志
     *
     * @param s 调试消息
     */
    @Override
    public void debug(String s) {
        log.log(FQCN, Level.DEBUG, s, null);
    }

    /**
     * 记录WARN级别日志
     *
     * @param s 警告消息
     */
    @Override
    public void warn(String s) {
        log.log(FQCN, Level.WARN, s, null);
    }

    /**
     * 记录TRACE级别日志
     *
     * @param s 跟踪消息
     */
    @Override
    public void trace(String s) {
        log.log(FQCN, Level.TRACE, s, null);
    }
}