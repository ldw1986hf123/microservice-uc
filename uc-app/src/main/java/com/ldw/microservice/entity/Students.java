package com.ldw.microservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("students")
public class Students {
    private String studId;
    private String name;
    private String email;
    private Date dob;
}
