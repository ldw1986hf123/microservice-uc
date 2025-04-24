package com.ldw.microservice.proxy;

import com.ldw.microservice.service.BaseMapper;
import com.ldw.microservice.wrapper.QueryWrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
public class BaseSqlProvider {

    public String selectById(ProviderContext context, Map<String, Object> params) {
        Class<?> entityClass = getEntityClassFromMapper(context);

        String table = entityClass.getSimpleName().toLowerCase();

        return String.format("SELECT * FROM %s WHERE id = #{id}", table);
    }


    public String selectList(ProviderContext context, @Param("wrapper") QueryWrapper<?> wrapper) {
        Class<?> entityClass = getEntityClassFromMapper(context);
        String table = entityClass.getSimpleName().toLowerCase();

        String where = wrapper != null ? wrapper.buildWhereClause() : "";

        return String.format("SELECT * FROM %s %s", table, where);
    }



    public String insert(ProviderContext context, Object entity) {
        Class<?> entityClass = entity.getClass();
        String table = entityClass.getSimpleName().toLowerCase();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : entityClass.getDeclaredFields()) {
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

        if (columns.length() > 0) columns.setLength(columns.length() - 1);
        if (values.length() > 0) values.setLength(values.length() - 1);

        return String.format("INSERT INTO %s (%s) VALUES (%s)", table, columns, values);
    }

    public String selectByField(ProviderContext context, Map<String, Object> params) {
        String field = (String) params.get("field");
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException("字段名不能为空");
        }

        Class<?> entityClass = getEntityClassFromMapper(context);
        String table = entityClass.getSimpleName().toLowerCase();

        return String.format("SELECT * FROM %s WHERE %s = #{value}", table, field);
    }





    private Class<?> getEntityClassFromMapper(ProviderContext context) {
        String mapperClassName = context.getMapperType().getName(); // 如 com.example.mapper.UserMapper
        Class<?> mapperClass = context.getMapperType();

        // 获取 BaseMapper 的泛型参数 T
        Type[] genericInterfaces = mapperClass.getGenericInterfaces();
        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                if (pt.getRawType() == BaseMapper.class) {
                    Type entityType = pt.getActualTypeArguments()[0];
                    if (entityType instanceof Class) {
                        return (Class<?>) entityType;
                    }
                }
            }
        }

        throw new IllegalStateException("无法解析实体类类型: " + mapperClassName);
    }
}
