package org.apache.ibatis.executor;

/**
 * 错误上下文
 *
 * @author heng
 * @date 2025年11月03日16:22:51
 */
public class ErrorContext {
    /**
     * 行分隔符，用于在不同操作系统上保持一致的换行格式
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();
    /**
     * 线程本地变量，用于存储当前线程的错误上下文实例
     */
    private static final ThreadLocal<ErrorContext> LOCAL = ThreadLocal.withInitial(ErrorContext::new);

    /**
     * 存储的错误上下文实例
     */
    private ErrorContext stored;

    /**
     * 资源信息
     */
    private String resource;

    /**
     * 操作活动信息
     */
    private String activity;

    /**
     * 操作对象信息
     */
    private String object;

    /**
     * 错误消息
     */
    private String message;

    /**
     * SQL语句
     */
    private String sql;

    /**
     * 异常原因
     */
    private Throwable cause;

    /**
     * 私有构造函数，防止直接实例化
     */
    private ErrorContext() {

    }

    /**
     * 获取当前线程的错误上下文实例
     *
     * @return 当前线程的错误上下文实例
     */
    public static ErrorContext instance() {
        return LOCAL.get();
    }

    /**
     * 存储当前错误上下文并创建新的上下文实例
     *
     * @return 新的错误上下文实例
     */
    public ErrorContext store() {
        ErrorContext newContext = new ErrorContext();
        newContext.stored = this;
        LOCAL.set(newContext);
        return LOCAL.get();
    }

    /**
     * 恢复到之前存储的错误上下文
     *
     * @return 恢复后的错误上下文实例
     */
    public ErrorContext recall() {
        if (stored != null) {
            LOCAL.set(stored);
            stored = null;
        }
        return LOCAL.get();
    }

    /**
     * 设置资源信息
     *
     * @param resource 资源信息
     * @return 当前错误上下文实例
     */
    public ErrorContext resource(String resource) {
        this.resource = resource;
        return this;
    }

    /**
     * 设置操作活动信息
     *
     * @param activity 操作活动信息
     * @return 当前错误上下文实例
     */
    public ErrorContext activity(String activity) {
        this.activity = activity;
        return this;
    }

    /**
     * 设置操作对象信息
     *
     * @param object 操作对象信息
     * @return 当前错误上下文实例
     */
    public ErrorContext object(String object) {
        this.object = object;
        return this;
    }

    /**
     * 设置错误消息
     *
     * @param message 错误消息
     * @return 当前错误上下文实例
     */
    public ErrorContext message(String message) {
        this.message = message;
        return this;
    }

    /**
     * 设置SQL语句
     *
     * @param sql SQL语句
     * @return 当前错误上下文实例
     */
    public ErrorContext sql(String sql) {
        this.sql = sql;
        return this;
    }

    /**
     * 设置异常原因
     *
     * @param cause 异常原因
     * @return 当前错误上下文实例
     */
    public ErrorContext cause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    /**
     * 重置错误上下文的所有属性
     *
     * @return 当前错误上下文实例
     */
    public ErrorContext reset() {
        resource = null;
        activity = null;
        object = null;
        message = null;
        sql = null;
        cause = null;
        LOCAL.remove();
        return this;
    }

    /**
     * 将错误上下文信息格式化为字符串
     *
     * @return 格式化后的错误信息字符串
     */
    @Override
    public String toString() {
        StringBuilder description = new StringBuilder();
        if (this.message != null) {
            description.append(LINE_SEPARATOR);
            description.append("### ");
            description.append(this.message);
        }

        if (this.resource != null) {
            description.append(LINE_SEPARATOR);
            description.append("### The error may exist in ");
            description.append(this.resource);
        }
        if (this.object != null) {
            description.append(LINE_SEPARATOR);
            description.append("### The error may involve ");
            description.append(this.object);
        }
        if (this.activity != null) {
            description.append(LINE_SEPARATOR);
            description.append("### The error occurred while ");
            description.append(this.activity);
        }
        if (this.sql != null) {
            description.append(LINE_SEPARATOR);
            description.append("### SQL: ");
            description.append(sql.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ').trim());
        }
        if (this.cause != null) {
            description.append(LINE_SEPARATOR);
            description.append("### Cause: ");
            description.append(cause.toString());
        }
        return description.toString();

    }

}
