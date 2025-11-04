/*
 *    Copyright 2009-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.logging.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.reflection.ExceptionUtil;

/**
 * ResultSet代理类，用于添加日志记录功能。
 *
 * @author heng
 * @date 2025-11-04 23:26:32
 */
public final class ResultSetLogger extends BaseJdbcLogger implements InvocationHandler {

    /** BLOB类型字段的SQL类型集合 */
    private static final Set<Integer> BLOB_TYPES = new HashSet<>();
    /** 是否为第一行数据的标识 */
    private boolean first = true;
    /** 结果集行数计数器 */
    private int rows;
    /** 被代理的ResultSet对象 */
    private final ResultSet rs;
    /** BLOB类型列的索引集合 */
    private final Set<Integer> blobColumns = new HashSet<>();

    static {
        BLOB_TYPES.add(Types.BINARY);
        BLOB_TYPES.add(Types.BLOB);
        BLOB_TYPES.add(Types.CLOB);
        BLOB_TYPES.add(Types.LONGNVARCHAR);
        BLOB_TYPES.add(Types.LONGVARBINARY);
        BLOB_TYPES.add(Types.LONGVARCHAR);
        BLOB_TYPES.add(Types.NCLOB);
        BLOB_TYPES.add(Types.VARBINARY);
    }

    /**
     * 构造函数，创建ResultSetLogger实例
     *
     * @param rs           被代理的ResultSet对象
     * @param statementLog 日志记录器
     * @param queryStack   查询堆栈深度
     */
    private ResultSetLogger(ResultSet rs, Log statementLog, int queryStack) {
        super(statementLog, queryStack);
        this.rs = rs;
    }

    /**
     * 方法调用处理器，拦截ResultSet的方法调用并添加日志记录
     *
     * @param proxy  代理对象
     * @param method 被调用的方法
     * @param params 方法参数
     * @return 方法调用结果
     * @throws Throwable 方法调用可能抛出的异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, params);
            }
            Object o = method.invoke(rs, params);
            if ("next".equals(method.getName())) {
                if ((Boolean) o) {
                    rows++;
                    if (isTraceEnabled()) {
                        ResultSetMetaData rsmd = rs.getMetaData();
                        final int columnCount = rsmd.getColumnCount();
                        if (first) {
                            first = false;
                            printColumnHeaders(rsmd, columnCount);
                        }
                        printColumnValues(columnCount);
                    }
                } else {
                    debug("     Total: " + rows, false);
                }
            }
            clearColumnInfo();
            return o;
        } catch (Throwable t) {
            throw ExceptionUtil.unwrapThrowable(t);
        }
    }

    /**
     * 打印列头信息
     *
     * @param rsmd        ResultSet元数据
     * @param columnCount 列数量
     * @throws SQLException SQL异常
     */
    private void printColumnHeaders(ResultSetMetaData rsmd, int columnCount) throws SQLException {
        StringJoiner row = new StringJoiner(", ", "   Columns: ", "");
        for (int i = 1; i <= columnCount; i++) {
            if (BLOB_TYPES.contains(rsmd.getColumnType(i))) {
                blobColumns.add(i);
            }
            row.add(rsmd.getColumnLabel(i));
        }
        trace(row.toString(), false);
    }

    /**
     * 打印列值信息
     *
     * @param columnCount 列数量
     */
    private void printColumnValues(int columnCount) {
        StringJoiner row = new StringJoiner(", ", "       Row: ", "");
        for (int i = 1; i <= columnCount; i++) {
            try {
                if (blobColumns.contains(i)) {
                    row.add("<<BLOB>>");
                } else {
                    row.add(rs.getString(i));
                }
            } catch (SQLException e) {
                // generally can't call getString() on a BLOB column
                row.add("<<Cannot Display>>");
            }
        }
        trace(row.toString(), false);
    }

    /**
     * 创建带日志记录功能的ResultSet代理实例
     *
     * @param rs           要代理的ResultSet
     * @param statementLog 语句日志记录器
     * @param queryStack   查询堆栈深度
     * @return 带日志记录功能的ResultSet代理
     */
    public static ResultSet newInstance(ResultSet rs, Log statementLog, int queryStack) {
        InvocationHandler handler = new ResultSetLogger(rs, statementLog, queryStack);
        ClassLoader cl = ResultSet.class.getClassLoader();
        return (ResultSet) Proxy.newProxyInstance(cl, new Class[]{ResultSet.class}, handler);
    }

    /**
     * 获取被代理的ResultSet对象
     *
     * @return 被代理的ResultSet对象
     */
    public ResultSet getRs() {
        return rs;
    }

}