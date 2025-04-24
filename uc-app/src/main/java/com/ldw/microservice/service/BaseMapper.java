package com.ldw.microservice.service;


import com.ldw.microservice.proxy.BaseSqlProvider;
import com.ldw.microservice.wrapper.QueryWrapper;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

    @InsertProvider(type = BaseSqlProvider.class, method = "insert")
    int insert(T entity);

    @SelectProvider(type = BaseSqlProvider.class, method = "selectById")
    T selectById(@Param("id") Serializable id);

    @SelectProvider(type = BaseSqlProvider.class, method = "selectByField")
    T selectByField(@Param("field") String field, @Param("value") Object value);
    @SelectProvider(type = BaseSqlProvider.class, method = "selectList")
    List<T> selectList(@Param("wrapper") QueryWrapper<T> wrapper, @Param("params") Map<String, Object> params);

}
