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
package org.apache.ibatis.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;

/**
 * 表示经过动态内容处理后得到的实际SQL语句的封装类。
 * 包含SQL语句、参数映射信息以及额外参数等。
 *
 * @author heng
 * @date 2025年11月04日22:06:25
 */
public class BoundSql {

    private final String sql;
    private final List<ParameterMapping> parameterMappings;
    private final Object parameterObject;
    private final Map<String, Object> additionalParameters;
    private final MetaObject metaParameters;

    /**
     * 构造一个BoundSql对象
     *
     * @param configuration     配置信息
     * @param sql               SQL语句
     * @param parameterMappings 参数映射列表
     * @param parameterObject   参数对象
     */
    public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings,
                    Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.additionalParameters = new HashMap<>();
        this.metaParameters = configuration.newMetaObject(additionalParameters);
    }

    /**
     * 获取SQL语句
     *
     * @return SQL语句字符串
     */
    public String getSql() {
        return sql;
    }

    /**
     * 获取参数映射列表
     *
     * @return 参数映射列表
     */
    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    /**
     * 获取参数对象
     *
     * @return 参数对象
     */
    public Object getParameterObject() {
        return parameterObject;
    }

    /**
     * 判断是否包含指定名称的额外参数
     *
     * @param name 参数名称
     * @return 如果包含返回true，否则返回false
     */
    public boolean hasAdditionalParameter(String name) {
        String paramName = new PropertyTokenizer(name).getName();
        return additionalParameters.containsKey(paramName);
    }

    /**
     * 设置额外参数的值
     *
     * @param name  参数名称
     * @param value 参数值
     */
    public void setAdditionalParameter(String name, Object value) {
        metaParameters.setValue(name, value);
    }

    /**
     * 获取指定名称的额外参数值
     *
     * @param name 参数名称
     * @return 额外参数的值
     */
    public Object getAdditionalParameter(String name) {
        return metaParameters.getValue(name);
    }

    /**
     * 获取所有额外参数
     *
     * @return 额外参数映射
     */
    public Map<String, Object> getAdditionalParameters() {
        return additionalParameters;
    }
}