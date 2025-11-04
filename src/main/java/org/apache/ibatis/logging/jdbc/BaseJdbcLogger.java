package org.apache.ibatis.logging.jdbc;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.reflection.ArrayUtil;

import java.lang.reflect.Method;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JDBC日志记录器的基类，提供JDBC操作的日志记录功能。
 * 该类负责收集和格式化SQL参数信息，以及处理日志输出。
 *
 * @author heng
 * @date 2025-11-04 23:07:37
 */
public abstract class BaseJdbcLogger {
    /** PreparedStatement中所有以"set"开头的方法名称集合 */
    protected static final Set<String> SET_METHODS;
    /** 执行SQL操作的方法名称集合 */
    protected static final Set<String> EXECUTE_METHODS = new HashSet<>();

    /** 列名和列值的映射关系 */
    private final Map<Object, Object> columnMap = new HashMap<>();
    /** 列名列表 */
    private final List<Object> columnNames = new ArrayList<>();
    /** 列值列表 */
    private final List<Object> columnValues = new ArrayList<>();

    /** 日志记录器 */
    protected final Log statemenntLog;
    /** 查询堆栈深度 */
    protected final int queryStack;

    /**
     * 构造函数，初始化JDBC日志记录器
     *
     * @param log        日志记录器
     * @param queryStack 查询堆栈深度
     */
    protected BaseJdbcLogger(Log log, int queryStack) {
        this.statemenntLog = log;
        if (queryStack == 0) {
            this.queryStack = 1;
        } else {
            this.queryStack = queryStack;
        }
    }

    static {
        SET_METHODS =
                Arrays.stream(PreparedStatement.class.getDeclaredMethods()).filter(method -> method.getName().startsWith("set")).filter(method -> method.getParameterCount() > 1).map(Method::getName).collect(Collectors.toSet());
        EXECUTE_METHODS.add("execute");
        EXECUTE_METHODS.add("executeUpdate");
        EXECUTE_METHODS.add("executeQuery");
        EXECUTE_METHODS.add("addBatch");
    }

    /**
     * 设置列名和列值的映射关系
     *
     * @param key   列名
     * @param value 列值
     */
    protected void setColumn(Object key, Object value) {
        columnMap.put(key, value);
        columnNames.add(key);
        columnValues.add(value);
    }

    /**
     * 根据列名获取列值
     *
     * @param key 列名
     * @return 列值
     */
    protected Object getColumn(Object key) {
        return columnMap.get(key);
    }

    /**
     * 获取参数值字符串表示
     *
     * @return 格式化后的参数值字符串
     */
    protected String getParameterValueString() {
        List<Object> typeList = new ArrayList<>(columnValues.size());
        for (Object value : columnValues) {
            if (value == null) {
                typeList.add("null");
            } else {
                typeList.add(objectValueString(value) + "(" + value.getClass().getSimpleName() + ")");
            }
        }
        final String parameters = typeList.toString();
        return parameters.substring(1, parameters.length() - 1);
    }

    /**
     * 获取对象值的字符串表示
     *
     * @param value 对象值
     * @return 对象值的字符串表示
     */
    protected String objectValueString(Object value) {
        if (value instanceof Array) {
            try {
                return ArrayUtil.toString(((Array) value).getArray());
            } catch (SQLException e) {
                //Intentionally fall through to return value.toString()
            }
        }
        return value.toString();
    }

    /**
     * 获取列名字符串表示
     *
     * @return 列名的字符串表示
     */
    protected String getColumnString() {
        return columnNames.toString();
    }

    /**
     * 清除列信息
     */
    protected void clearColumnInfo() {
        columnMap.clear();
        columnNames.clear();
        columnValues.clear();
    }

    /**
     * 移除字符串中的多余空白字符
     *
     * @param original 原始字符串
     * @return 处理后的字符串
     */
    protected String removeExtraWhitespace(String original) {
        return SqlSourceBuilder.removeExtraWhitespaces(original);
    }

    /**
     * 判断是否启用DEBUG级别日志
     *
     * @return 如果启用DEBUG级别日志返回true，否则返回false
     */
    protected boolean isDebugEnabled() {
        return statemenntLog.isDebugEnabled();
    }

    /**
     * 判断是否启用TRACE级别日志
     *
     * @return 如果启用TRACE级别日志返回true，否则返回false
     */
    protected boolean isTraceEnabled() {
        return statemenntLog.isTraceEnabled();
    }

    /**
     * 输出DEBUG级别日志
     *
     * @param text  日志文本
     * @param input 是否为输入操作
     */
    protected void debug(String text, boolean input) {
        if (statemenntLog.isDebugEnabled()) {
            statemenntLog.debug(prefix(input) + text);
        }
    }

    /**
     * 输出TRACE级别日志
     *
     * @param text  日志文本
     * @param input 是否为输入操作
     */
    protected void trace(String text, boolean input) {
        if (statemenntLog.isTraceEnabled()) {
            statemenntLog.trace(prefix(input) + text);
        }
    }

    /**
     * 生成日志前缀
     *
     * @param isInput 是否为输入操作
     * @return 日志前缀字符串
     */
    private String prefix(boolean isInput) {
        char[] buffer = new char[queryStack * 2 + 2];
        Arrays.fill(buffer, '=');
        buffer[queryStack * 2 + 1] = ' ';
        if (isInput) {
            buffer[queryStack * 2] = '>';
        } else {
            buffer[0] = '<';
        }
        return new String(buffer);
    }
}