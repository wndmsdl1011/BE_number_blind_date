package com.example.BE_number_blind_date.member;


import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(){
        System.out.println("test");
        return "test";
    }



}
