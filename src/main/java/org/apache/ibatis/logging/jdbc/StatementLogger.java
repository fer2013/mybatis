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
import java.sql.Statement;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.reflection.ExceptionUtil;

/**
 * Statement代理类，用于添加日志记录功能。
 *
 * @author heng
 * @date 2025-11-04 23:43:46
 */
public final class StatementLogger extends BaseJdbcLogger implements InvocationHandler {

    /** 被代理的Statement对象 */
    private final Statement statement;

    /**
     * 构造函数，创建StatementLogger实例
     *
     * @param stmt         被代理的Statement对象
     * @param statementLog 日志记录器
     * @param queryStack   查询堆栈深度
     */
    private StatementLogger(Statement stmt, Log statementLog, int queryStack) {
        super(statementLog, queryStack);
        this.statement = stmt;
    }

    /**
     * 方法调用处理器，拦截Statement的方法调用并添加日志记录
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
            if (EXECUTE_METHODS.contains(method.getName())) {
                if (isDebugEnabled()) {
                    debug(" Executing: " + removeExtraWhitespace((String) params[0]), true);
                }
                if ("executeQuery".equals(method.getName())) {
                    ResultSet rs = (ResultSet) method.invoke(statement, params);
                    return rs == null ? null : ResultSetLogger.newInstance(rs, statementLog, queryStack);
                } else {
                    return method.invoke(statement, params);
                }
            }
            if ("getResultSet".equals(method.getName())) {
                ResultSet rs = (ResultSet) method.invoke(statement, params);
                return rs == null ? null : ResultSetLogger.newInstance(rs, statementLog, queryStack);
            } else {
                return method.invoke(statement, params);
            }
        } catch (Throwable t) {
            throw ExceptionUtil.unwrapThrowable(t);
        }
    }

    /**
     * 创建带日志记录功能的Statement代理实例
     *
     * @param stmt         要代理的Statement
     * @param statementLog 语句日志记录器
     * @param queryStack   查询堆栈深度
     * @return 带日志记录功能的Statement代理
     */
    public static Statement newInstance(Statement stmt, Log statementLog, int queryStack) {
        InvocationHandler handler = new StatementLogger(stmt, statementLog, queryStack);
        ClassLoader cl = Statement.class.getClassLoader();
        return (Statement) Proxy.newProxyInstance(cl, new Class[]{Statement.class}, handler);
    }

    /**
     * 获取被代理的Statement对象
     *
     * @return 被代理的Statement对象
     */
    public Statement getStatement() {
        return statement;
    }

}