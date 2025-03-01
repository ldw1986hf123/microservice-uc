package com.ldw.microservice.controller;

import com.ldw.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UCController {

    @GetMapping("/getUserById")
    public Result getUserById() {
        return Result.success("steven");
    }


    @GetMapping("/getByDeptId")
    public Result getByDeptId() {
        return Result.success("steven");
    }

}