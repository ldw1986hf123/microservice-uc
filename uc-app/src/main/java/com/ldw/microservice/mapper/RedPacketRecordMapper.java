package com.ldw.microservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldw.microservice.entity.RedPacketRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RedPacketRecordMapper extends BaseMapper<RedPacketRecord> {
    // 如果需要自定义 SQL，可以在这里添加 @Select / @Insert 等注解
}
