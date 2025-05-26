package com.ldw.microservice.service.impl;

import com.ldw.microservice.controller.UCApplication;
import com.ldw.microservice.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UCApplication.class)
public class ContractServiceImplTest {

    @Autowired
    ContractService contractService;

    @Test
    public void login() {
        contractService.login();
    }
}