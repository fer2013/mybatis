/*
 *    Copyright 2009-2022 the original author or authors.
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
package org.apache.ibatis.session;

/**
 * 执行器类型枚举，用于定义MyBatis中不同的SQL执行策略。
 *
 * @author heng
 * @date 2025-11-04 22:47:20
 */
public enum ExecutorType {

    /** 简单执行器，每条语句都会创建一个新的PreparedStatement对象 */
    SIMPLE,

    /** 可重用执行器，会缓存预处理语句以提高性能 */
    REUSE,

    /** 批处理执行器，用于批量执行语句以提高大量数据操作的性能 */
    BATCH

}