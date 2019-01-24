package com.ltf.rpc.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rpcservice.MyService;

@RestController
public class TestController {
    @Autowired
    private MyService myService;

    @GetMapping(value = "/v1/get")
    public String get(){
        int res=myService.div(3,5);
        return res+"===hello";
    }
}
