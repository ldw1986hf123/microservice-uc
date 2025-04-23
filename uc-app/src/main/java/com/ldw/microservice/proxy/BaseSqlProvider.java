package com.ldw.microservice.proxy;

import java.lang.reflect.Field;
import java.util.Map;

public class BaseSqlProvider {

    public String insert(Object entity) {
        Class<?> clazz = entity.getClass();
        String table = clazz.getSimpleName().toLowerCase();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object val = field.get(entity);
                if (val != null) {
                    columns.append(field.getName()).append(",");
                    values.append("#{").append(field.getName()).append("},");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        // Remove trailing commas
        columns.setLength(columns.length() - 1);
        values.setLength(values.length() - 1);

        return String.format("INSERT INTO %s (%s) VALUES (%s)", table, columns, values);
    }

    public String selectById(Map<String, Object> params) {
        // 获取方法调用的类
        Class<?> modelClass = (Class<?>) params.get("modelClass");
        if (modelClass == null) {
            throw new IllegalArgumentException("实体类类型不能为空");
        }
        // 获取表名，假设表名与类名相同（小写）
        String table = modelClass.getSimpleName().toLowerCase();
        // 获取 ID 参数
        Object id = params.get("id");
        return String.format("SELECT * FROM %s WHERE id = #{id}", table);
    }
}
