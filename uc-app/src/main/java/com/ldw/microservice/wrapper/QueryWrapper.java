package com.ldw.microservice.wrapper;

import java.util.*;
import java.util.stream.Collectors;

public class QueryWrapper<T> {
    private final List<Condition> conditions = new ArrayList<>();
    private final Map<String, Object> paramMap = new HashMap<>();
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


    public QueryWrapper<T> eq(String field, Object value) {
        conditions.add(new Condition(field, "=", value));
        return this;
    }

    public QueryWrapper<T> like(String field, Object value) {
        conditions.add(new Condition(field, "LIKE", value));
        return this;
    }

    public QueryWrapper<T> gt(String field, Object value) {
        conditions.add(new Condition(field, ">", value));
        return this;
    }
    public QueryWrapper<T> in(String field, Collection<?> values) {
        if (values == null || values.isEmpty()) {
            // 空集合时拼接无结果的条件
            conditions.add(new Condition("1 = 0")); // 永不满足
            return this;
        }

        StringBuilder placeholderList = new StringBuilder();
        int index = 0;
        for (Object val : values) {
            String key = field + "_in_" + index++;
            placeholderList.append("#{params.").append(key).append("}, ");
            paramMap.put(key, val);
        }

        // 去掉最后一个逗号
        String placeholders = placeholderList.substring(0, placeholderList.length() - 2);
        Condition cond = new Condition(field, "IN", placeholders);
        conditions.add(cond);

        return this;
    }

    public QueryWrapper<T> lt(String field, Object value) {
        conditions.add(new Condition(field, "<", value));
        return this;
    }

    public QueryWrapper<T> orderByAsc(String field) {
        conditions.add(new Condition("ORDER BY " + field + " ASC"));
        return this;
    }

    public QueryWrapper<T> orderByDesc(String field) {
        conditions.add(new Condition("ORDER BY " + field + " DESC"));
        return this;
    }


    public QueryWrapper<T> limit(int offset, int count) {
        conditions.add(new Condition("LIMIT " + offset + ", " + count));
        return this;
    }

    public String buildWhereClause() {
        List<String> wheres = new ArrayList<>();
        List<String> others = new ArrayList<>();

        for (Condition cond : conditions) {
            if (cond.isRaw()) {
                others.add(cond.rawSql);
            } else if ("IN".equals(cond.operator)) {
                wheres.add(cond.field + " IN (" + cond.value + ")");
            } else if ("LIKE".equals(cond.operator)) {
                wheres.add(cond.field + " LIKE CONCAT('%', #{params." + cond.field + "}, '%')");
            } else {
                wheres.add(cond.field + " " + cond.operator + " #{params." + cond.field + "}");
            }
        }

        String where = wheres.isEmpty() ? "" : "WHERE " + String.join(" AND ", wheres);
        return where + " " + String.join(" ", others);
    }


    public Map<String, Object> getParamMap() {
        for (Condition cond : conditions) {
            paramMap.put(cond.field, cond.value);
        }
        return paramMap;
    }
}

