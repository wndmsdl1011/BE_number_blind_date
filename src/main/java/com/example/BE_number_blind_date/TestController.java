package com.example.BE_number_blind_date;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(){
        System.out.println("test");
        return "test";
    }



}
