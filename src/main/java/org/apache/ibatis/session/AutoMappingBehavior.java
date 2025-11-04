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
 * 自动映射行为枚举，指定MyBatis是否以及如何自动将数据库列映射到对象的字段/属性。
 *
 * @author Eduardo Macarron
 */
public enum AutoMappingBehavior {

  /**
   * 禁用自动映射。
   */
  NONE,

  /**
   * 只会自动映射没有嵌套结果映射定义的结果。
   */
  PARTIAL,

  /**
   * 将自动映射任何复杂度的结果映射（包含嵌套或其他复杂结构）。
   */
  FULL
}