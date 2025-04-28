package com.ldw.microservice.wrapper;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QueryWrapper<T> {
    private final List<Condition> conditions = new ArrayList<>();
    private final Map<String, Object> paramMap = new HashMap<>();
    private final StringBuilder sql = new StringBuilder("1=1");


    private static class Condition {
        String field;
        String operator;
        Object value;
        String rawSql; // 用于 order by, limit 这类无需占位符的拼接

        Condition(String field, String operator, Object value) {
            this.field = field;
            this.operator = operator;
            this.value = value;
        }

        Condition(String rawSql) {
            this.rawSql = rawSql;
        }

        boolean isRaw() {
            return rawSql != null;
        }
    }

    // 获取字段名
    private String getColumnName(String fieldName) {
        StringBuilder result = new StringBuilder();
        char[] chars = fieldName.toCharArray();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                result.append('_').append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // eq: 相等查询
    public <R> QueryWrapper<T> eq(Function<T, R> func, R value) {
        if (value != null) {
            String fieldName = getFieldName(func);  // 获取字段名
            String column = getColumnName(fieldName); // 转换为数据库字段名
            String key = fieldName + "_eq";
            paramMap.put(key, value);
            sql.append(" AND ").append(column).append(" = #{params.").append(key).append("}");
        }
        return this;
    }

    // like: 模糊查询
    public QueryWrapper<T> like(Function<T, String> func, String value) {
        if (value != null && !value.isEmpty()) {
            String fieldName = getFieldName(func);  // 获取字段名
            String column = getColumnName(fieldName);
            String key = fieldName + "_like";
            paramMap.put(key, "%" + value + "%");
            sql.append(" AND ").append(column).append(" LIKE #{params.").append(key).append("}");
        }
        return this;
    }

    // in: 集合查询
    public <R> QueryWrapper<T> in(Function<T, R> func, Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            String fieldName = getFieldName(func);  // 获取字段名
            String column = getColumnName(fieldName);
            List<String> placeholders = new ArrayList<>();
            int index = 0;
            for (Object val : values) {
                String key = fieldName + "_in_" + index++;
                paramMap.put(key, val);
                placeholders.add("#{params." + key + "}");
            }
            sql.append(" AND ").append(column).append(" IN (")
                    .append(String.join(", ", placeholders)).append(")");
        }
        return this;
    }

    // orderByAsc: 排序
    public QueryWrapper<T> orderByAsc(Function<T, ?> func) {
        String fieldName = getFieldName(func);
        String column = getColumnName(fieldName);
        sql.append(" ORDER BY ").append(column).append(" ASC");
        return this;
    }

    // orderByDesc: 排序
    public QueryWrapper<T> orderByDesc(Function<T, ?> func) {
        String fieldName = getFieldName(func);
        String column = getColumnName(fieldName);
        sql.append(" ORDER BY ").append(column).append(" DESC");
        return this;
    }

    // limit: 限制查询
    public QueryWrapper<T> limit(int limit) {
        sql.append(" LIMIT ").append(limit);
        return this;
    }

    // 获取字段名：通过反射从方法引用中提取字段名称
    private String getFieldName(Function<T, ?> func) {
        try {
            Method method = func.getClass().getDeclaredMethod("apply", Object.class);
            String methodName = method.getName();
            String fieldName = methodName.replaceFirst("get", "");
            fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1); // 转成小写开头
            return fieldName;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failed to extract field name from method reference", e);
        }
    }

    // 获取 SQL 查询片段
    public String getSqlSegment() {
        return sql.toString();
    }


    public Map<String, Object> getParamMap() {
        for (Condition cond : conditions) {
            paramMap.put(cond.field, cond.value);
        }
        return paramMap;
    }
}

