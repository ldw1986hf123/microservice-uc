package com.ldw.microservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ldw.microservice.entity.Contact;

public interface ContractService extends IService<Contact> {
    void login();
}
