package com.ldw.microservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldw.microservice.entity.RedPacketRecord;
import com.ldw.microservice.mapper.RedPacketRecordMapper;
import com.ldw.microservice.service.RedPacketRecordService;
import org.springframework.stereotype.Service;

@Service
public class RedPacketRecordServiceImpl extends ServiceImpl<RedPacketRecordMapper, RedPacketRecord>
        implements RedPacketRecordService {
    // 你可以在这里实现 RedPacketRecordService 中定义的自定义方法
}
