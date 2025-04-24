package com.ldw.microservice.mapper;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ldw.microservice.controller.UCApplication;
import com.ldw.microservice.entity.Contact;
import com.ldw.microservice.entity.Students;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

//@Import({FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class, FeignConfig.class})
//@TestPropertySource(properties = "lsy.url=http://120.79.138.205:7073")   //todo 有更好的办法，可以直接获取到配置文件的属性值的，待优化
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UCApplication.class)
public class StudentMapperTest {
    @Autowired
    StudentMapper studentMapper;


    @Test
    public void deviceCapability() {
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Students::getStudId, 0);
        Students students = studentMapper.selectOne(queryWrapper);
        log.info(JSONUtil.toJsonStr(students));
    }

    @Test
    public void getsssOne() {
        Contact contact = new Contact();
        contact.setLocation("11");
    }

    @Test
    public void selectCount() {
      /*  QueryWrapper<AustraliaDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("created", 0);
        queryWrapper.eq("power_grid", powerGrid);*/
        log.info("aaa");
    }
}