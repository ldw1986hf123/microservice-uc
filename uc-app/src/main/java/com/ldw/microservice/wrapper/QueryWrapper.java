package com.ldw.microservice.wrapper;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.lang.invoke.SerializedLambda;

public class QueryWrapper<T> {

    private final List<String> sqlSegments = new ArrayList<>();
    private final Map<String, Object> paramMap = new HashMap<>();
    private String alias = ""; // 表别名（如果需要）

    // 设置表别名
    public QueryWrapper<T> alias(String alias) {
        if (alias != null && !alias.trim().isEmpty()) {
            this.alias = alias.trim() + ".";
        }
        return this;
    }

    // eq =
    public <R> QueryWrapper<T> eq(Function<T, R> func, R value) {
        if (value != null) {
            addCondition(func, "=", value);
        }
        return this;
    }

    // ne !=
    public <R> QueryWrapper<T> ne(Function<T, R> func, R value) {
        if (value != null) {
            addCondition(func, "<>", value);
        }
        return this;
    }

    // gt >
    public <R> QueryWrapper<T> gt(Function<T, R> func, R value) {
        if (value != null) {
            addCondition(func, ">", value);
        }
        return this;
    }

    // lt <
    public <R> QueryWrapper<T> lt(Function<T, R> func, R value) {
        if (value != null) {
            addCondition(func, "<", value);
        }
        return this;
    }

    // ge >=
    public <R> QueryWrapper<T> ge(Function<T, R> func, R value) {
        if (value != null) {
            addCondition(func, ">=", value);
        }
        return this;
    }

    // le <=
    public <R> QueryWrapper<T> le(Function<T, R> func, R value) {
        if (value != null) {
            addCondition(func, "<=", value);
        }
        return this;
    }

    // like
    public QueryWrapper<T> like(Function<T, String> func, String value) {
        if (value != null && !value.isEmpty()) {
            String column = getColumnName(func);
            String key = buildKey(column, "like");
            sqlSegments.add("AND " + alias + column + " LIKE #{" + "params." + key + "}");
            paramMap.put(key, "%" + value + "%");
        }
        return this;
    }

    // in
    public <R> QueryWrapper<T> in(Function<T, R> func, Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            String column = getColumnName(func);
            List<String> placeholders = new ArrayList<>();
            int idx = 0;
            for (Object val : values) {
                String key = buildKey(column, "in" + idx++);
                placeholders.add("#{" + "params." + key + "}");
                paramMap.put(key, val);
            }
            sqlSegments.add("AND " + alias + column + " IN (" + String.join(", ", placeholders) + ")");
        }
        return this;
    }

    // orderByAsc
    public QueryWrapper<T> orderByAsc(Function<T, ?> func) {
        String column = getColumnName(func);
        sqlSegments.add("ORDER BY " + alias + column + " ASC");
        return this;
    }

    // orderByDesc
    public QueryWrapper<T> orderByDesc(Function<T, ?> func) {
        String column = getColumnName(func);
        sqlSegments.add("ORDER BY " + alias + column + " DESC");
        return this;
    }

    // limit
    public QueryWrapper<T> limit(int limit) {
        sqlSegments.add("LIMIT " + limit);
        return this;
    }

    // =========== 工具方法 ==========

    private <R> void addCondition(Function<T, R> func, String operator, Object value) {
        String column = getColumnName(func);
        String key = buildKey(column, operator);
        sqlSegments.add("AND " + alias + column + " " + operator + " #{" + "params." + key + "}");
        paramMap.put(key, value);
    }

    // 从方法引用提取字段名
    private String getColumnName(Function<T, ?> func) {
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda lambda = (SerializedLambda) method.invoke(func);
            String getter = lambda.getImplMethodName(); // getApplyStatus
            if (getter.startsWith("get")) {
                getter = getter.substring(3);
            } else if (getter.startsWith("is")) {
                getter = getter.substring(2);
            }
            String field = Character.toLowerCase(getter.charAt(0)) + getter.substring(1);
            return camelToUnderscore(field); // 转成 apply_status
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract field name from lambda", e);
        }
    }

    // 驼峰转下划线
    private String camelToUnderscore(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // 参数 key 生成
    private String buildKey(String column, String suffix) {
        return column + "_" + suffix.replaceAll("[^a-zA-Z0-9]", "");
    }

    // 获取 SQL 片段
    public String getSqlSegment() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("WHERE 1=1");
        for (String segment : sqlSegments) {
            joiner.add(segment);
        }
        return joiner.toString();
    }

    // 获取参数
    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
