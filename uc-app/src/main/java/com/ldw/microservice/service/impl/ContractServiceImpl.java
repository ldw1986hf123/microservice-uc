package com.ldw.microservice.service.impl;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldw.microservice.entity.Contact;
import com.ldw.microservice.entity.RedPacketRecord;
import com.ldw.microservice.mapper.ContractMapper;
import com.ldw.microservice.service.ContractService;
import com.ldw.microservice.service.RedPacketRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contact> implements ContractService {

    @Autowired
    RedPacketRecordService redPacketRecordService;

    @Override
    public void login() {
        Contact contact = getOne(new LambdaQueryWrapper<Contact>().eq(Contact::getPersonId, 1));
        log.info(JSONUtil.toJsonStr(contact));
        List<RedPacketRecord> redPacketRecords = redPacketRecordService.list(new LambdaQueryWrapper<RedPacketRecord>().eq(RedPacketRecord::getRedPacketId, 1));
        log.info(JSONUtil.toJsonStr(redPacketRecords));
    }

}
