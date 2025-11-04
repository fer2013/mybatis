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

import java.sql.ResultSet;

/**
 * 结果集类型枚举，用于定义ResultSet的游标类型和并发性。
 *
 * @author heng
 * @date 2025-11-04 22:45:51
 */
public enum ResultSetType {
    /**
     * 默认行为，与未设置时相同（依赖驱动程序）。
     *
     * @since 3.5.0
     */
    DEFAULT(-1),
    /** 只能向前滚动的结果集 */
    FORWARD_ONLY(ResultSet.TYPE_FORWARD_ONLY),
    /** 可滚动但对数据变化不敏感的结果集 */
    SCROLL_INSENSITIVE(ResultSet.TYPE_SCROLL_INSENSITIVE),
    /** 可滚动且对数据变化敏感的结果集 */
    SCROLL_SENSITIVE(ResultSet.TYPE_SCROLL_SENSITIVE);

    private final int value;

    /**
     * 构造函数，为结果集类型枚举值分配对应的整数值
     *
     * @param value 与ResultSet类型常量对应的整数值
     */
    ResultSetType(int value) {
        this.value = value;
    }

    /**
     * 获取结果集类型对应的整数值
     *
     * @return 结果集类型对应的整数值
     */
    public int getValue() {
        return value;
    }
}