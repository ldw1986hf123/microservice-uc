package com.ldw.microservice.entity;

import lombok.Data;

@Data
public class Contact {
    private String contactType;
    private String location;
    private String personId;
}
