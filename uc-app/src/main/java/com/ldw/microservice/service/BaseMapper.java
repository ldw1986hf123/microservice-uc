package com.ldw.microservice.service;


import com.ldw.microservice.proxy.BaseSqlProvider;
import org.apache.ibatis.annotations.*;
import java.io.Serializable;

public interface BaseMapper<T> {

        @InsertProvider(type = BaseSqlProvider.class, method = "insert")
        int insert(T entity);

        @SelectProvider(type = BaseSqlProvider.class, method = "selectById")
        T selectById(@Param("id") Serializable id, @Param("modelClass") Class<T> modelClass);
}
