package com.ldw.microservice.mapper;

import com.ldw.microservice.entity.Contact;
import com.ldw.microservice.service.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 */
@Mapper
public interface ContractMapper extends BaseMapper<Contact> {


}
