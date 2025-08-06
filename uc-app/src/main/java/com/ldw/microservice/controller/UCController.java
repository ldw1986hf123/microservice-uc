package com.ldw.microservice.controller;

import com.ldw.common.vo.Result;
import com.ldw.microservice.entity.Contact;
import com.ldw.microservice.entity.RedPacketRecord;
import com.ldw.microservice.service.ContractService;
import com.ldw.microservice.service.RedPacketRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UCController {

    @Autowired
    RedPacketRecordService redPacketRecordService;

    @Autowired
    ContractService contractService;

    @GetMapping("/getUserById")
    public Result getUserById() {
        return Result.success("steven");
    }


    @GetMapping("/getByDeptId")
    public Result getByDeptId() {
        return Result.success("steven");
    }

    @GetMapping("/getById")
    public Contact getById() {
        return contractService.login();
    }

    @GetMapping("/getRedPacketRecordById")
    public RedPacketRecord getRedPacketRecordById() {
        return redPacketRecordService.getById(1);
    }
}