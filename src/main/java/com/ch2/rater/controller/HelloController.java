package com.ch2.rater.controller;


import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hello";
    }

    @GetMapping("/rate")
    public String rate() throws UnknownHostException {
        return "Rate " + InetAddress.getLocalHost().getHostName() ;
    }
}
