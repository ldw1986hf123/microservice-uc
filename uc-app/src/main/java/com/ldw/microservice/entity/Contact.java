package com.ldw.microservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("contact")
public class Contact {
    /*      `contact_type` varchar(255) DEFAULT NULL,
      `location` varchar(255) DEFAULT NULL,
      `person_id` bigint(20) DEFAULT NULL*/
    private String contractType;
    private String location;
    private String personId;
}
