package com.ldw.microservice.mapper;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ldw.microservice.controller.UCApplication;
import com.ldw.microservice.entity.Contact;
import com.ldw.microservice.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Import({FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class, FeignConfig.class})
//@TestPropertySource(properties = "lsy.url=http://120.79.138.205:7073")   //todo 有更好的办法，可以直接获取到配置文件的属性值的，待优化
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UCApplication.class)
public class AustraliaDeviceMapperTest {
    @Autowired
    ContractMapper contractMapper;



    @Autowired
    ContractService contractService;


    @Test
    public void deviceCapability() {
       /* QueryWrapper<AustraliaDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("created", 0);
        queryWrapper.eq("power_grid", powerGrid);
        LambdaQueryWrapper<Contact> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Contact::getLocation,1);
        List australiaDevice = contractMapper.selectList(queryWrapper);
        log.info(JSONUtil.toJsonStr(australiaDevice));*/
    }

    @Test
    public void getsssOne() {
        Contact contact = new Contact();
        contact.setLocation("11");
        contractService.login();
    }




    @Test
    public void selectCount() {
   /*     QueryWrapper<Contact> wrapper = new QueryWrapper<Contact>();
        wrapper.eq("location", "11");

        List<Contact> contactList = contractMapper
        log.info(JSONUtil.toJsonStr(contactList));
*/
    }


}