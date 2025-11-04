package org.apache.ibatis.logging;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;

import java.lang.reflect.Constructor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 日志工厂类，用于创建和管理MyBatis的日志实现
 * <p>
 * 该类负责检测和初始化适当的日志实现，支持多种日志框架如SLF4J、Log4j等。
 * 通过反射机制动态加载日志实现类，并提供线程安全的日志实例创建功能
 * </p>
 *
 * @author heng
 * @date 2025-11-04 16:20:59
 */
public final class LogFactory {

    /**
     * MyBatis日志标记，用于标识MyBatis相关的日志输出
     */
    public static final String MARKER = "MYBATIS";

    private static final ReentrantLock lock = new ReentrantLock();
    private static Constructor<? extends Log> logConstructor;

    static {
        tryImplementation(LogFactory::useSlf4jLogging);
        tryImplementation(LogFactory::useLog4jLogging);
    }

    private LogFactory() {
        // disable construction
    }

    public static Log getLog(Class<?> clazz) {
        return getLog(clazz.getName());
    }

    public static Log getLog(String logger) {
        try {
            return logConstructor.newInstance(logger);
        } catch (Throwable t) {
            throw new LogException("Error creating logger for logger " + logger + ".  Cause: " + t, t);
        }
    }


    /**
     * 配置使用Log4j作为日志实现
     */
    private static void useLog4jLogging() {
        setImplementation(org.apache.ibatis.logging.log4j.Log4jImpl.class);
    }


    /**
     * 尝试使用指定的日志实现配置
     * <p>
     * 如果当前还没有设置日志实现，则执行传入的Runnable来尝试设置
     * </p>
     *
     * @param runnable 尝试设置日志实现的可执行代码
     */
    private static void tryImplementation(Runnable runnable) {
        if (logConstructor == null) {
            try {
                runnable.run();
            } catch (Throwable t) {
                // ignore
            }
        }
    }

    /**
     * 配置使用SLF4J作为日志实现
     */
    private static void useSlf4jLogging() {
        setImplementation(org.apache.ibatis.logging.slf4j.Slf4jImpl.class);
    }

    /**
     * 设置日志实现类
     * <p>
     * 通过反射获取日志实现类的构造函数，并创建实例进行初始化。
     * 该方法是线程安全的，使用ReentrantLock保证同一时间只有一个线程能设置日志实现
     * </p>
     *
     * @param implClass 日志实现类
     * @throws LogException 当设置日志实现过程中发生错误时抛出
     */
    private static void setImplementation(Class<? extends Log> implClass) {
        lock.lock();
        try {
            Constructor<? extends Log> candidate = implClass.getConstructor(String.class);
            Log log = candidate.newInstance(LogFactory.class.getName());
            if (log.isDebugEnabled()) {
                log.debug("Logging initialized using '" + implClass + "' adapter.");
            }
            logConstructor = candidate;
        } catch (Throwable t) {
            throw new LogException("Error setting Log implementation.  Cause: " + t, t);
        } finally {
            lock.unlock();
        }
    }
}