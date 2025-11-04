package org.apache.ibatis.builder;

import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;
import java.util.StringTokenizer;

public class SqlSourceBuilder {
    private SqlSourceBuilder() {
    }
    public static SqlSource buildSqlSource(Connfiguration connfiguration, String sql, List<ParameterMapping> parameterMappings){
        return new StaticSqlSource(connfiguration, configuration.isShrinkWhitespacesInSql() ?
                SqlSourceBuilder.removeExtraWhitespaces(sql) : sql, parameterMappings);
    }
    public static String removeExtraWhitespaces(String original) {
        StringTokenizer tokenizer = new StringTokenizer(original);
        StringBuilder builder = new StringBuilder();
        boolean hasMoreTokens = tokenizer.hasMoreTokens();
        while (hasMoreTokens) {
            builder.append(tokenizer.nextToken());
            hasMoreTokens = tokenizer.hasMoreTokens();
            if (hasMoreTokens) {
                builder.append(' ');
            }
        }
        return builder.toString();
    }
}
