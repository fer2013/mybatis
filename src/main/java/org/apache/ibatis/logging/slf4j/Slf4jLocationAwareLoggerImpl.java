package org.apache.ibatis.logging.slf4j;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * SLF4J位置感知日志实现类，用于适配MyBatis的日志接口到SLF4J的位置感知日志记录器
 * <p>
 * 该类实现了MyBatis的Log接口，将日志操作委托给SLF4J的LocationAwareLogger处理，
 * 提供了完整的日志级别支持，包括ERROR、WARN、DEBUG、TRACE等级别。
 * 与普通SLF4J实现不同，该实现支持日志位置信息的准确报告
 * </p>
 *
 * @author heng
 * @date 2025-11-04 16:51:28
 */
public class Slf4jLocationAwareLoggerImpl implements Log {
    private static final Marker MARKER = MarkerFactory.getMarker(LogFactory.MARKER);

    private static final String FQCN = Slf4jImpl.class.getName();

    private final LocationAwareLogger logger;

    /**
     * 构造方法，使用指定的位置感知日志记录器创建实例
     *
     * @param logger SLF4J位置感知日志记录器
     */
    public Slf4jLocationAwareLoggerImpl(LocationAwareLogger logger) {
        this.logger = logger;
    }


    /**
     * 检查是否启用了DEBUG级别日志
     *
     * @return 如果启用了DEBUG级别日志则返回true，否则返回false
     */
    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * 检查是否启用了TRACE级别日志
     *
     * @return 如果启用了TRACE级别日志则返回true，否则返回false
     */
    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    /**
     * 记录ERROR级别日志，并包含异常信息
     *
     * @param s 错误消息
     * @param e 异常对象
     */
    @Override
    public void error(String s, Throwable e) {
        logger.log(MARKER, FQCN, LocationAwareLogger.ERROR_INT, s, null, e);
    }

    /**
     * 记录ERROR级别日志
     *
     * @param s 错误消息
     */
    @Override
    public void error(String s) {
        logger.log(MARKER, FQCN, LocationAwareLogger.ERROR_INT, s, null, null);
    }

    /**
     * 记录DEBUG级别日志
     *
     * @param s 调试消息
     */
    @Override
    public void debug(String s) {
        logger.log(MARKER, FQCN, LocationAwareLogger.DEBUG_INT, s, null, null);
    }

    /**
     * 记录WARN级别日志
     *
     * @param s 警告消息
     */
    @Override
    public void warn(String s) {
        logger.warn(s);
    }

    /**
     * 记录TRACE级别日志
     *
     * @param s 跟踪消息
     */
    @Override
    public void trace(String s) {
        logger.trace(s);
    }
}